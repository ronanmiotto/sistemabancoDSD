
public class Conta {
	
	//Atributos
	private String nomeCliente;
	private String senha;
	private String numeroConta;
	private double saldo;
	private String extrato;
	
	// Construtor da classe
	public Conta(String nomeCliente, String numeroConta, String senha, double saldo){
		
		this.nomeCliente = nomeCliente;
		this.senha = senha;
		this.numeroConta = numeroConta;
		this.saldo = saldo;
		this.extrato = "";
	}
	
	// Criando os getters and setters 
	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public String getExtrato() {
		return extrato;
	}

	public void setExtrato(String extrato) {
		this.extrato = extrato;
	}
	
}
