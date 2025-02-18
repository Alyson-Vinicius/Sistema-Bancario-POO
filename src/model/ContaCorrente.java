package model;

public class ContaCorrente extends ContaBancaria {
    private static final long serialVersionUID = 1L;
    private static final float TAXA_MANUTENCAO = 12.50f;

    public ContaCorrente(Integer numeroConta) {
        super(numeroConta);
    }

    @Override
    public boolean sacar(float valor) {
        float totalSaque = valor + TAXA_MANUTENCAO;

        if (valor > 0 && totalSaque <= saldo) {
            saldo -= totalSaque;
            
            // Registra a transação no histórico
            registrarTransacao(TipoTransacao.DEBITO, totalSaque);
            
            System.out.printf("Saque de R$ %.2f realizado com sucesso. (Taxa de manutenção aplicada: R$ %.2f)\n", valor, TAXA_MANUTENCAO);
            return true;
        } else {
            System.out.println("Erro: Saldo insuficiente para saque. Saldo atual: R$ " + saldo);
            return false;
        }
    }

    @Override
    public boolean transferir(ContaBancaria destino, float valor) {
        float taxa = 2.50f;
        float valorTotal = valor + taxa;

        if (valorTotal <= saldo) {
            saldo -= valorTotal;
            destino.depositar(valor);
            
            // Registra transação de débito para a conta de origem
            registrarTransacao(TipoTransacao.TRANSFERENCIA_DEBITO, valorTotal);
            
            // Registra transação de crédito para a conta de destino
            destino.registrarTransacao(TipoTransacao.TRANSFERENCIA_CREDITO, valor);

            System.out.printf("Transferência de R$ %.2f realizada com sucesso para a conta %d (Taxa: R$ %.2f).\n",
                    valor, destino.getNumeroConta(), taxa);
            return true;
        } else {
            System.out.println("Erro: Saldo insuficiente para transferência. Saldo atual: R$ " + saldo);
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
