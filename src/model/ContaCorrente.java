package model;

public class ContaCorrente extends ContaBancaria {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final float TAXA_MANUTENCAO = 12.50f;

    public ContaCorrente(Integer numeroConta) {
        super(numeroConta);
    }

    @Override
    public void depositar(float valor) {
        if (valor > 0) {
            saldo += valor;
        } else {
            System.out.println("Erro: O depósito deve ser positivo.");
        }
    }

    @Override
    public boolean sacar(float valor) {
        float totalSaque = valor + TAXA_MANUTENCAO;
        if (valor > 0 && totalSaque <= saldo) {
            saldo -= totalSaque;
            return true;
        } else {
            System.out.println("Erro: Saldo insuficiente para saque.");
            return false;
        }
    }

    @Override
    public boolean transferir(ContaBancaria destino, float valor) {
        if (this.sacar(valor)) {
            destino.depositar(valor);
            return true;
        } else {
            System.out.println("Erro: Transferência falhou.");
            return false;
        }
    }
}
