package model;

public class ContaPoupanca extends ContaBancaria {
    private static final long serialVersionUID = 1L;
    private static final float RENDIMENTO = 0.005f;

    public ContaPoupanca(Integer numeroConta) {
        super(numeroConta);
    }

    public void aplicarRendimento() {
        if (saldo > 0) {
            saldo += saldo * RENDIMENTO;
            System.out.printf("Rendimento aplicado. Novo saldo: R$ %.2f\n", saldo);
        } else {
            System.out.println("Erro: Não é possível aplicar rendimento em saldo zerado.");
        }
    }

    @Override
    public boolean sacar(float valor) {
        if (valor > 0 && valor <= saldo) {
            saldo -= valor;
            
            // Registra a transação
            registrarTransacao(TipoTransacao.DEBITO, valor);
            
            System.out.printf("Saque de R$ %.2f realizado com sucesso.\n", valor);
            return true;
        } else {
            System.out.println("Erro: Saldo insuficiente para saque. Saldo atual: R$ " + saldo);
            return false;
        }
    }

    @Override
    public boolean transferir(ContaBancaria destino, float valor) {
        if (valor > 0 && valor <= saldo) {
            saldo -= valor;
            destino.depositar(valor);

            // Registra transações nas duas contas
            registrarTransacao(TipoTransacao.TRANSFERENCIA_DEBITO, valor);
            destino.registrarTransacao(TipoTransacao.TRANSFERENCIA_CREDITO, valor);

            System.out.printf("Transferência de R$ %.2f realizada com sucesso para a conta %d.\n",
                    valor, destino.getNumeroConta());
            return true;
        } else {
            System.out.println("Erro: Saldo insuficiente para transferência.");
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
