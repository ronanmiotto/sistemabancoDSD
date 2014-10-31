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

			System.out
					.println("Seja Bem Vindo(a) ao BANCO DO POVO.\nPor favor, insira os dados de sua conta.\n");

			System.out.print("N�mero da CONTA: ");
			String numeroConta = ler.nextLine();

			System.out.print("Senha: ");
			String senha = ler.nextLine();

			// Enviando a conta e a senha para o tratamento
			PrintStream enviaSocket = new PrintStream(caixa.getOutputStream());
			enviaSocket.println(numeroConta + "-" + senha);

			// Recebendo as informa��es da conta
			Scanner recebeSocket = new Scanner(caixa.getInputStream());
			String[] validandoCliente = recebeSocket.nextLine().split("-");

			// Conferindo se cliente existe
			if (validandoCliente[0].equals("true")) {
				System.out.println("\nOl� Senhor(a), " + validandoCliente[1] + ".");

				int opc = 0;

				while (opc >= 1 || opc <= 6) {

					// Menu de op��es
					System.out.println("\n****** BANCO DO POVO ******");
					System.out.println("1 - Dep�sito");
					System.out.println("2 - Saque");
					System.out.println("3 - Saldo");
					System.out.println("4 - Extrato");
					System.out.println("5 - Ajuda");
					System.out.println("6 - Sair");

					System.out.print("\nDigite a op��o que deseja realizar: ");

					// //Transforma entrada do teclado em n�mero
					opc = Integer.parseInt(ler.nextLine());

					if (opc == 1) {

						System.out.println("\n__________DEP�SITO__________");
						System.out.print("Digite o n�mero da CONTA: ");
						String numContaDeposito = ler.nextLine();

						System.out.print("Valor do dep�sito: ");
						
						// Transforma entrada do teclado em n�meros
						double valorDeposito = Double.parseDouble(ler.nextLine());
						System.out.println("\n____________________________________\n");

						// Enviando dados do dep�sito (N�mero da conta e valor do dep�sito)
						enviaSocket.println(opc + "-" + numContaDeposito + "-" + valorDeposito);

						// Recebendo os dados quebrados e transformando em string
						String dadosDeposito[] = recebeSocket.nextLine().split("-");

					} else {
						if (opc == 2) {

							System.out.println("\n__________SAQUE__________");
							System.out.print("Digite o n�mero da CONTA: ");
							String contaSaque = ler.nextLine();

							System.out.print("Digite o valor do saque: ");

							// Transforma entrada do teclado em n�meros
							double valorSaque = Double.parseDouble(ler.nextLine());

							// Enviando socket para tratamento
							enviaSocket.println(opc + "-" + contaSaque + "-" + valorSaque);

							// Recebendo os dados quebrados e transformando em
							// string
							String dadosSaque[] = recebeSocket.nextLine().split("-");
							System.out.println("\n____________________________________\n");

							if (dadosSaque[0].equals("true")) {

								// Apresenta nome do cliente e valor do saldo
								System.out.println("\nOpera��o realizada com sucesso!!!" + "Cliente: "+ dadosSaque[2] + "Saldo: " + dadosSaque[1]);
								System.out.println("\n____________________________________\n");

								//double totalSaque = Double.parseDouble(dadosSaque[1]);

							} else {

								System.out.println("\n" + dadosSaque[1]);
								//double totalSaque = Double.parseDouble(dadosSaque[1]);
							}
						} else {
							if (opc == 3) {

								System.out.println("\n__________SALDO__________");

								// Enviando socket para tratamento
								enviaSocket.println(opc + "-" + validandoCliente[2]);

								// Recebendo os dados quebrados e transformando em string
								String dadosSaldo[] = recebeSocket.nextLine().split("-");

								if (dadosSaldo[0].equals("true")) {
									
									System.out.println("Saldo: " + dadosSaldo[1]);
									//double totalSaldo = Double.parseDouble(dadosSaldo[1]);
									System.out.println("\n____________________________________\n");

								} else {

									System.out.println("\n" + dadosSaldo[1]);
									//double totalSaldo = Double.parseDouble(dadosSaldo[1]);
								}

							} else {
								if (opc == 4) {

									System.out.println("\n__________EXTRATO__________");

									// Enviando socket para tratamento
									enviaSocket.println(opc + "-" + validandoCliente[2]);

									// Recebendo os dados quebrados e
									// transformando em string
									String dadosExtrato[] = recebeSocket.nextLine().split("-");

									if (dadosExtrato[0].equals("true")) {

										for (int i = 1; i < dadosExtrato.length; i++) {
											
											System.out.println("\n" + dadosExtrato[1]);
											//System.out.println(dadosExtrato[i] += "\nDep�sito: R$ " +dadosDeposito[i] + "\nSaque: R$ " + totalSaque + "\nSaldo: R$ " +totalSaldo);
											System.out.println("...\n...\n...\n...\n...\n");
										}

									} else {

										System.out.println("\n" + dadosExtrato[1]);
										System.out.println("...\n...\n...\n...\n...\n");
									}

								} else {

									if (opc == 5) {

										System.out.println("\n__________AJUDA__________\n");
										System.out.println("=/ \n" + "\nDesculpe estamos em manuten��o!\nProcure seu gerente de contas para mais informa��es!");
										System.out.println("************************************");
									} else {

										if (opc == 6) {

											System.out.println("\nConta desconectada\n\n");
											enviaSocket.println(opc);

										}
									}
								}
							}
						}
					}
				}

				// System.out.println("\n***Conta ou Senha inv�lida***\nTente Novamente!\n");
			}
		}
	}
}
