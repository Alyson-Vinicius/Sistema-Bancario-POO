package model;

public interface IConta {
    void depositar(double valor);
    boolean sacar(double valor);
    boolean transferir(ContaBancaria destino, float valor);
    float getSaldo();
    int getNumeroConta();

    default void imprimirSaldo() {
        System.out.println("Saldo da conta " + getNumeroConta() + ": R$" + getSaldo());
    }

    default void registrarTransacao(TipoTransacao debito, float valor) {
        System.out.println("Transação registrada: " + debito + " - Valor: R$" + valor);
    }
	void depositar(float valor);
	boolean sacar(float valor);
}
