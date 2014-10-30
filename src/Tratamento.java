import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Tratamento extends Thread {

	// Atributos
	private Socket caixa;
	private String name;
	private Banco banco;

	// Construtor da classe
	public Tratamento(Socket caixa, String name, Banco banco) {

		this.caixa = caixa;
		this.name = name;
		this.banco = banco;
	}

	// Conectando com os caixas
	public void run() {
		System.out.println(this.name + ": " + caixa.getInetAddress().getHostAddress() + " conectou-se.");

		while (true) {

			Scanner recebeSocket = null;

			try {
				recebeSocket = new Scanner(caixa.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String textoEntrada = new String();
			textoEntrada = recebeSocket.nextLine();

			PrintStream enviaSocket;

			try {
				enviaSocket = new PrintStream(caixa.getOutputStream());
				enviaSocket.println(autenticaLogin(textoEntrada, banco));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if (autenticaLogin(textoEntrada, banco).split("-")[0].equals("true")) {
				try {
					enviaSocket = new PrintStream(caixa.getOutputStream());

					textoEntrada = recebeSocket.nextLine();
					String dadosRecebidos[] = textoEntrada.split("-");

					int opc = Integer.parseInt(dadosRecebidos[0]);

					while (opc >= 1 || opc <= 6) {
						
						if (opc == 1) {
						// Enviando dados do depósito
							enviaSocket.println(realizarDeposito(dadosRecebidos[1],Double.parseDouble(dadosRecebidos[2]),banco));
							
						}else{
							if (opc == 2){
								// Enviando dados do saque
								enviaSocket.println(realizarSaque(dadosRecebidos[1],Double.parseDouble(dadosRecebidos[2]),banco));		
							}else{
								if (opc == 3){
									// Enviando dados do saldo
									enviaSocket.println(consultarSaldo(dadosRecebidos[1],banco));
								}else{
									if (opc == 4){
										// Enviando dados do extrato
										enviaSocket.println(exibeExtrato(dadosRecebidos[1],banco));
									}else{
										if (opc == 5){
											// Enviando dados da ajuda
											enviaSocket.println("\nDesculpe estamos em manutenção!\nProcure seu gerente de contas para mais informações!");
										}else{
											// sair
											System.out.println("Sair");
										}
									}
								}
									
							}
					
						}

						if (opc != 5 && opc != 6) {
							
							textoEntrada = recebeSocket.nextLine();
							dadosRecebidos = textoEntrada.split("-");

							opc = Integer.parseInt(dadosRecebidos[0]);
						}
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	//Método de autenticação de Login
	private String autenticaLogin(String textoEntrada, Banco banco) {
		
		String[] info = textoEntrada.split("-");

		for (Conta conta : banco.getContas()) {
			
			if (conta.getNumeroConta().equals(info[0]) && conta.getSenha().equals(info[1])) {
				
				return "true-" + conta.getNomeCliente() + "-" + conta.getNumeroConta();
			}
		}

		return "false-Autenticação inválida!";
	}
	
	//Método realizar Depósito em conta
	private String realizarDeposito(String num, double valor, Banco banco) {
		
		if (num.equals("")) {
			return "false-O Campo conta não foi preenchido";
		}

		if (valor == 0) {
			return "false-O Campo valor não foi preenchido";
		}

		if (valor < 0) {
			return "false-Valor Negativo";
		}

		for (int i = 0; i < banco.getContas().size(); i++) {
			
			Conta conta = banco.getContas().get(i);

			if (conta.getNumeroConta().equals(num)) {
				conta.setSaldo(conta.getSaldo() + valor);
				conta.setExtrato(conta.getExtrato() + "Valor do Depósito: " + valor);
				banco.getContas().set(i, conta);

				return "true-Operação efetuada com sucesso!" + valor + "-" + conta.getNomeCliente();
			}
		}

		return "false-Depósito não efetuado!";
	}

	//Método  realizar Saque em conta
	private String realizarSaque(String numeroConta, double valor, Banco banco) {

		if (numeroConta.equals("")) {
			return "false-O Campo conta não foi preenchido";
		}

		if (valor == 0) {
			return "false-O Campo valor não foi preenchido";
		}

		if (valor < 0) {
			return "false-Valor negativo";
		}

		if (valor > 0) {
			for (int i = 0; i < banco.getContas().size(); i++) {
				
				Conta conta = banco.getContas().get(i);
				
				if (valor > conta.getSaldo()){
					
					return"false-Saldo insuficiente";
				}

				if (conta.getNumeroConta().equals(numeroConta)) {
					if (conta.getSaldo() < valor) {
						
						return "false-Saldo insuficiente!";
						
					} else {
						
						conta.setSaldo(conta.getSaldo() - valor);
						conta.setExtrato(conta.getExtrato() + "Valor do Saque: " + valor);
						banco.getContas().set(i, conta);

						return "true-Operação efetuada com sucesso!" + conta.getSaldo() + "-" + conta.getNomeCliente();
					}
				}
			}
		}

		return "false-Saque não efetuado.";
	}

	//Método consultar saldo da conta
	private String consultarSaldo(String num, Banco banco) {
		
		if (num.equals("")) {
			
			return "false-O Campo conta não foi preenchido";
		}

		for (int i = 0; i < banco.getContas().size(); i++) {
			
			Conta conta = banco.getContas().get(i);

			if (conta.getNumeroConta().equals(num)) {
				
				return "true-" + conta.getSaldo();
			}
		}

		return "false-Erro!";
	}

	//Método exibir extrato da conta
	private String exibeExtrato(String num, Banco banco) {
		
		if (num.equals("")) {
			
			return "false-O Campo conta não foi preenchido";
		}

		for (int i = 0; i < banco.getContas().size(); i++) {
			
			Conta conta = banco.getContas().get(i);

			if (conta.getNumeroConta().equals(num)) {
				
				return "true-Operação efetuada com sucesso!" + conta.getExtrato();
			}
		}

		return "false-Erro!";
	}
}
