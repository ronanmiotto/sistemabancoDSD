import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;


public class Tratamento extends Thread {
	
	//Atributos
	private Socket caixa;
	private String name;
	private Banco banco;
	
	//Construtor da classe
	public Tratamento(Socket caixa, String name, Banco banco) {
		
		this.caixa = caixa;
		this.name = name;
		this.banco = banco;
	}
	
	//Conectando com os caixas
	public void run(){
		System.out.println(this.name + ": " + caixa.getInetAddress().getHostAddress() + " conectou-se.");
		
		while(true){
			Scanner recebeSocket = null;
			
			try {
				recebeSocket = new Scanner(caixa.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String texto = new String();
			texto = recebeSocket.nextLine();
			
		   	PrintStream enviaSocket;
		   	
		   	try {
		   		enviaSocket = new PrintStream(caixa.getOutputStream());
		   		enviaSocket.println(autenticaLogin(texto, banco));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		   	
		   	if(autenticaLogin(texto, banco).split("-")[0].equals("true")){
		   		try {
					enviaSocket = new PrintStream(caixa.getOutputStream());
					
					texto = recebeSocket.nextLine();
					String dadosRecebidos[] = texto.split("-");
					
					int opcao = Integer.parseInt(dadosRecebidos[0]);
					
					while(opcao != 9){						
						switch(opcao){
							// Enviando dados do depósito 
							case 1:
								enviaSocket.println(depositoConta(dadosRecebidos[1], Double.parseDouble(dadosRecebidos[2]), banco));
								break;
							// Enviando dados do saque
							case 2:
								enviaSocket.println(saqueConta(dadosRecebidos[1], Double.parseDouble(dadosRecebidos[2]), banco));
								break;
							// Enviando dados do saldo
							case 3:
								enviaSocket.println(saldoConta(dadosRecebidos[1], banco));
								break;
							// Enviando dados do extrato
							case 4:
								enviaSocket.println(extratoConta(dadosRecebidos[1], banco));
								break;
							// Enviando dados da ajuda
							case 5:
								enviaSocket.println("Procure seu gerente de contas!");
								break;
							// sair
							case 6:
								System.out.println("Sair");
								break;
						}
						
						if(opcao != 6 && opcao != 5){
							texto = recebeSocket.nextLine();
							dadosRecebidos = texto.split("-");
							
							opcao = Integer.parseInt(dadosRecebidos[0]);
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		   	}
		}
	}
	
	private String autenticaLogin(String texto, Banco banco){		
		String[] info = texto.split("-");
		
		
		for (Conta conta : banco.getContas()){
			if(conta.getnumeroConta().equalsIgnoreCase(info[0]) && conta.getSenha().equalsIgnoreCase(info[1])){
				return "true-" + conta.getnomeCliente() + "-" + conta.getnumeroConta();
			}
		}
		
		return "false-Autenticação inválida!";
	}
	
	private String depositoConta(String numero, double valor, Banco banco){
		if(numero.equalsIgnoreCase("")){
			return "false-Erro!\n O Campo conta não foi preenchido";
		}
		
		if(valor == 0){
			return "false-Erro!\n O Campo valor não foi preenchido";
		}
		
		if(valor < 0){
			return "false-Erro!\nValor negativo";
		}
		
		for(int i = 0; i < banco.getContas().size(); i++){
			Conta conta = banco.getContas().get(i);
			
			if(conta.getnumeroConta().equalsIgnoreCase(numero)){				
				conta.setSaldo(conta.getSaldo() + valor);
				conta.setExtrato(conta.getExtrato() + "-Valor do Depósito: " + valor);
				banco.getContas().set(i, conta);
			
				return "true-" + valor + "-" + conta.getnomeCliente();
			}
		}
		
		return "false-Erro!\nDepósito não efetuado!";
	}
	
	private String saqueConta(String numeroConta, double valor, Banco banco){
		if(numeroConta.equalsIgnoreCase("")){
			return "false-Erro!\n O Campo conta não foi preenchido";
		}
		
		if(valor == 0){
			return "false-Erro!\n O Campo valor não foi preenchido";
		}
		
		if(valor < 0){
			return "false-Erro!\n Valor negativo";
		}
		
		if(valor > 0){
			for(int i = 0; i < banco.getContas().size(); i++){
				Conta conta = banco.getContas().get(i);
				
				if(conta.getnumeroConta().equalsIgnoreCase(numeroConta)){
					if(conta.getSaldo() < valor){
						return "false-Saldo insuficiente!";
					} else {
						conta.setSaldo(conta.getSaldo() - valor);
						conta.setExtrato(conta.getExtrato() + "-Valor do Saque: " + valor);
						banco.getContas().set(i, conta);
						
						return "true-" + conta.getSaldo() + "-" + conta.getnomeCliente();
					}
				}
			}
		}
		
		return "false-Saque não efetuado.";
	}
	
	private String saldoConta(String numero, Banco banco){
		if(numero.equalsIgnoreCase("")){
			return "false-Erro!\nO Campo conta não foi preenchido";
		}
		
		for(int i = 0; i < banco.getContas().size(); i++){
			Conta conta = banco.getContas().get(i);
			
			if(conta.getnumeroConta().equalsIgnoreCase(numero)){
				return "true-" + conta.getSaldo();
			}
		}
		
		return "false-Erro!";
	}
	
	private String extratoConta(String numero, Banco banco){
		if(numero.equalsIgnoreCase("")){
			return "false-Erro!\n O Campo conta não foi preenchido";
		}
		
		for(int i = 0; i < banco.getContas().size(); i++){
			Conta conta = banco.getContas().get(i);
			
			if(conta.getnumeroConta().equalsIgnoreCase(numero)){
				return "true" + conta.getExtrato();
			}
		}
		
		return "false-Erro!";
	}
}
