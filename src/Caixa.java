import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Caixa {
	public static void main(String[] args) throws UnknownHostException,
			IOException {

		Socket caixa = new Socket("127.0.0.1", 12345);
		System.out.println("O caixa conectou-se ao servidor!\n");

		// login
		Scanner ler = new Scanner(System.in);

		boolean funcionando = true;

		while (funcionando == true) {

			System.out.println("Seja Bem Vindo(a) ao BANCO DO POVO.\nPor favor, insira os dados de sua conta.\n");

			System.out.print("Número da Conta: ");
			String numeroConta = ler.nextLine();

			System.out.print("Senha: ");
			String senha = ler.nextLine();

			// Enviando a conta e a senha para o tratamento
			PrintStream enviaSocket = new PrintStream(caixa.getOutputStream());
			enviaSocket.println(numeroConta + "-" + senha);

			// Recebendo as informações da conta
			Scanner recebeSocket = new Scanner(caixa.getInputStream());
			String[] validandoCliente = recebeSocket.nextLine().split("-");

			// Conferindo se cliente existe
			if (validandoCliente[0] == ("true")) {
				System.out.println("\nOlá Senhor(a), " + validandoCliente[1] + ".");
				
				int opc = 0;

				while (opc >= 1 || opc <= 6) {
					

					// Menu de opções
					System.out.println("\n****** BANCO DO POVO ******");
					System.out.println("1 - Depósito");
					System.out.println("2 - Saque");
					System.out.println("3 - Saldo");
					System.out.println("4 - Extrato");
					System.out.println("5 - Ajuda");
					System.out.println("6 - Sair");

					System.out.print("\nDigite a opção que deseja realizar: ");
					
					////Transforma entrada do teclado em número
					opc = Integer.parseInt(ler.nextLine());

					if (opc == 1) {

						System.out.println("\n__________DEPÓSITO__________");
						System.out.print("Digite o número da conta: ");
						String numContaDeposito = ler.nextLine();

						System.out.print("Valor do depósito: ");
						
						//Transforma entrada do teclado em números
						double valorDeposito = Double.parseDouble(ler.nextLine());

						// Enviando dados do depósito (Número da conta e valor do depósito)
						enviaSocket.println(opc + "-" + numContaDeposito + "-" + valorDeposito);
						
						//Recebendo os dados quebrados e transformando em string
						String dadosDeposito[] = recebeSocket.nextLine().split("-");

						if (dadosDeposito[0] == ("true")) {
							
							//Apresenta nome do cliente e valor do depositado
							System.out.println("\nOperação realizada com sucesso!!!");
							System.out.println("Cliente: " + dadosDeposito[2]);
							System.out.println("Valor Depositado: " + dadosDeposito[1]);

						} else {
							
							//Apresenta valor do depósito
							System.out.println("\n" + dadosDeposito[1]);
						}

						if (opc == 2) {
							
							int i;
							System.out.println("\n__________SAQUE__________");
							System.out.print("Digite o número da conta: ");
							String contaSaque = ler.nextLine();

							System.out.print("Digite o valor do saque: ");
							
							//Transforma entrada do teclado em números
							double valorSaque = Double.parseDouble(ler.nextLine());

							//Enviando socket para tratamento
							enviaSocket.println(opc + "-" + contaSaque + "-" + valorSaque);
							
							//Recebendo os dados quebrados e transformando em string
							String dadosSaque[] = recebeSocket.nextLine().split("-");

							if (dadosSaque[0] == ("true")) {
								
								//Apresenta nome do cliente e valor do saldo
								System.out.println("\nOperação realizada com sucesso!!!");
								System.out.println("Cliente: " + dadosSaque[2]);
								System.out.println("Saldo: " + dadosSaque[1]);
								
								double totalSaque = Double.parseDouble(dadosSaque[2]);

							} else {
								
								System.out.println("\n" + dadosSaque[1]);
								double totalSaque = Double.parseDouble(dadosSaque[2]);
							}

						}

						if (opc == 3) {
							
							System.out.println("\n__________SALDO__________");
						
							//Enviando socket para tratamento
							enviaSocket.println(opc + "-" + validandoCliente[2]);
							
							//Recebendo os dados quebrados e transformando em string
							String dadosSaldo[] = recebeSocket.nextLine().split("-");

							if (dadosSaldo[0] == ("true")) {

								System.out.println("Saldo: " + dadosSaldo[1]);
								double totalSaldo = Double.parseDouble(dadosSaldo[1]);
								
							} else {
								
								System.out.println("\n" + dadosSaldo[1]);
								double totalSaldo = Double.parseDouble(dadosSaldo[1]);
							}

						}

						if (opc == 4) {
							
							System.out.println("\n__________EXTRATO__________");
							
							//Enviando socket para tratamento
							enviaSocket.println(opc + "-" + validandoCliente[2]);
							
							//Recebendo os dados quebrados e transformando em string
							String dadosExtrato[] = recebeSocket.nextLine().split("-");

							if (dadosExtrato[0] == ("true")) {

								for (int i = 1; i < dadosExtrato.length; i++) {
									
									//System.out.println(dadosExtrato[i] += "\nDepósito: R$ " +   dadosDeposito[i] + "\nSaque: R$ " + totalSaque + "\nSaldo: R$ "  + totalSaldo);
									System.out.println("...\n...\n...\n...\n...\n");
								}
								
							} else {
								
								System.out.println("\n" + dadosExtrato[1]);
								System.out.println("...\n...\n...\n...\n...\n");
							}

						}

						if (opc == 5) {

							System.out.println("\n__________AJUDA__________");
							
						} else {

							System.out.println("\nConta desconectada\n\n");
							enviaSocket.println(opc);
						}

					}
				}
			}else{
				
				System.out.println("\n***Conta ou Senha inválida***\nTente Novamente!\n");
			}
		}

	}
}