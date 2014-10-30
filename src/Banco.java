import java.util.ArrayList;


public class Banco {
	
	// atributos
	private ArrayList<Conta> contas;
	
	// Construtor da classe
	public Banco() {
		
		this.contas = new ArrayList<Conta>();
		
		// Cadastrando contas no banco
		addContaCliente(new Conta("Dilma Rousseff", "1010", "123", 500));
		addContaCliente(new Conta("Aécio Neves", "1020", "123", 300));
		addContaCliente(new Conta("Marina Silva", "1040", "123", 100));

	}
	
	// Adicionando as contas dos clientes no Array
	public void addContaCliente(Conta conta){
		this.contas.add(conta);
	}
	
	// Pega conta cliente no Array
	public ArrayList<Conta> getContas(){
		return this.contas;
	}
}
