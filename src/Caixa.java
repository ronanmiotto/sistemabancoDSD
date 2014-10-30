import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Caixa {
   public static void main(String[] args) throws UnknownHostException, IOException {
     
	 Socket caixa = new Socket("127.0.0.1", 12345);
     System.out.println("O caixa se conectou ao servidor!\n");
     
     // login
     Scanner ler = new Scanner(System.in);
     
     boolean funcionando = true;
	 
	 while(funcionando == true){     
	     System.out.println("Seja Bem Vindo(a) ao BANCO DO POVO.\nPor favor insira os dados de sua conta.\n");
	     
	     System.out.print("Número da Conta: ");
	     String conta = ler.nextLine();
	     
	     System.out.print("Senha: ");
	     String senha = ler.nextLine();
	     
	     PrintStream enviaSocket = new PrintStream(caixa.getOutputStream());     
	     enviaSocket.println(conta + "-" + senha);
	     
	     Scanner recebeSocket = new Scanner(caixa.getInputStream());
	     String [] validandoCliente = recebeSocket.nextLine().split("-"); 
	     
	     if(validandoCliente[0].equals("true")){
	    	 System.out.println("\nOlá Senhor(a), " + validandoCliente[1] + ".");
	    
	    	 int opc = 0;
   
	    	 while(opc != 6){
		    	 System.out.println("\n****** BANCO DO POVO ******");
		         System.out.println("1 - Depósito");
		         System.out.println("2 - Saque");
		         System.out.println("3 - Saldo");
		         System.out.println("4 - Extrato");
		         System.out.println("5 - Ajuda");
		         System.out.println("6 - Sair");
		         
		         System.out.print("\nDigite a opção que deseja realizar: ");
		         opc = Integer.parseInt(ler.nextLine());
		         
		         switch(opc){
		         	case 1:
		         		System.out.println("\n__________DEPÓSITO__________");
		         		System.out.print("Digite o número da conta: ");
		         		String numContaDeposito = ler.nextLine();
		         		
		         		System.out.print("Valor do depósito: ");
		         		double valorDeposito = Double.parseDouble(ler.nextLine());
		         			
		         		enviaSocket.println(opc + "-" + numContaDeposito + "-" + valorDeposito);
		         		
		         		String dadosDeposito[] = recebeSocket.nextLine().split("-");
		         		
		         		if(dadosDeposito[0].equalsIgnoreCase("true")){
		         			
		         			System.out.println("\nOperação realizada com sucesso!!!");
		         			System.out.println("Cliente: " + dadosDeposito[2]);
		         			System.out.println("Valor Depositado: " + dadosDeposito[1]);
		         			
		         		} else {
		         			System.out.println("\n" + dadosDeposito[1]);
		         		}
		         		
		         		break;
		         		
		         	case 2:
		         		System.out.println("\n__________SAQUE__________");
		         		System.out.print("Digite o número da conta: ");
		         		String contaSaque = ler.nextLine();
		         		
		         		System.out.print("Digite o valor do saque: ");
		         		double valorSaque = Double.parseDouble(ler.nextLine());
		         		
		         		enviaSocket.println(opc + "-" + contaSaque + "-" + valorSaque);
		         		
		         		String dadosSaque[] = recebeSocket.nextLine().split("-");
		         		
		         		if(dadosSaque[0].equalsIgnoreCase("true")){
		         			System.out.println("\nOperação realizada com sucesso!!!");
		         			System.out.println("Titular: " + dadosSaque[2]);
		         			System.out.println("Saldo: " + dadosSaque[1]);
		         		} else {
		         			System.out.println("\n" + dadosSaque[1]);
		         		}
		         		
		         		break;
		         		
		         	case 3:
		         		System.out.println("\n__________SALDO__________");	         		
		         		enviaSocket.println(opc + "-" + validandoCliente[2]);
		         		
		         		String dadosSaldo[] = recebeSocket.nextLine().split("-");
		         		
		         		if(dadosSaldo[0].equalsIgnoreCase("true")){
		         			System.out.println("Saldo: " + dadosSaldo[1]);
		         		} else {
		         			System.out.println("\n" + dadosSaldo[1]);
		         		}
		         		
		         		break;
		         		
		         	case 4:
		         		System.out.println("\n__________EXTRATO__________");	         		
		         		enviaSocket.println(opc + "-" + validandoCliente[2]);
		         		
		         		String dadosExtrato[] = recebeSocket.nextLine().split("-");
		         		
		         		if(dadosExtrato[0].equalsIgnoreCase("true")){
		         			for(int i = 1; i < dadosExtrato.length; i++){
		         				System.out.println(dadosExtrato[i]);
		         			}
		         		} else {
		         			System.out.println("\n" + dadosExtrato[1]);
		         		}
		         		
		         	case 5: 
		         		System.out.println("\n__________AJUDA__________");
		         		
		         	case 6:
		         		System.out.println("\nConta desconectada\n\n");
		         		enviaSocket.println(opc);
		         }
	    	 }
	     }
     }
     
   }
}