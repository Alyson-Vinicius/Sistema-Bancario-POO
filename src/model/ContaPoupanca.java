package model;

public class ContaPoupanca extends ContaBancaria {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final float RENDIMENTO = 0.005f;

    public ContaPoupanca(Integer numeroConta) {
        super(numeroConta);
    }

    public void aplicarRendimento() {
        if (saldo > 0) {
            saldo += saldo * RENDIMENTO;
            System.out.println("Rendimento aplicado. Novo saldo: R$ " + saldo);
        } else {
            System.out.println("Erro: Não é possível aplicar rendimento em saldo zerado.");
        }
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
        if (valor > 0 && saldo >= valor) {
            saldo -= valor;
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

	@Override
	public void depositar(double valor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean sacar(double valor) {
		// TODO Auto-generated method stub
		return false;
	}
}
