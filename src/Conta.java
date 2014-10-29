
public class Conta {
	
	//Atributos
	private String nomeCliente;
	private String numeroConta;
	private String senha;
	private double saldo;
	private String extrato;
	
	// Construtor da classe
	public Conta(String nomeCliente, String numeroConta, String senha, double saldo){
		
		this.nomeCliente = nomeCliente;
		this.numeroConta = numeroConta;
		this.senha = senha;
		this.saldo = saldo;
		this.extrato = "";
	}
	
	// getters and setters
	public String getnomeCliente() {
		return nomeCliente;
	}

	public String getExtrato() {
		return extrato;
	}

	public void setExtrato(String extrato) {
		this.extrato = extrato;
	}

	public void setnomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getnumeroConta() {
		return numeroConta;
	}
	
	public void setnumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public double getSaldo() {
		return saldo;
	}
	
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
}
