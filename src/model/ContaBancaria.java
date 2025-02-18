package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public abstract class ContaBancaria implements IConta, Serializable {
    private static final long serialVersionUID = 1L;

    protected Integer numeroConta;
    protected float saldo;
    protected String dataAbertura;
    protected boolean ativa;

    public ContaBancaria(Integer numeroConta) {
        this.numeroConta = numeroConta;
        this.saldo = 0f;
        this.dataAbertura = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        this.ativa = true;
    }

    public int getNumeroConta() {
        return numeroConta;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void encerrarConta() {
        if (saldo == 0) {
            this.ativa = false;
            System.out.println("Conta encerrada com sucesso.");
        } else {
            System.out.println("Não é possível encerrar a conta. O saldo precisa estar zerado.");
        }
    }

    @Override
    public float getSaldo() {
        return saldo;
    }

    @Override
    public void depositar(float valor) {
        if (valor > 0) {
            saldo += valor;
            System.out.printf("Depósito de R$ %.2f realizado com sucesso.\n", valor);
        } else {
            System.out.println("Valor inválido para depósito.");
        }
    }

    public boolean sacar(float valor, boolean exibirMensagem) {
        if (valor <= 0) {
            System.out.println("Valor inválido para saque.");
            return false;
        }
        if (valor <= saldo) {
            saldo -= valor;
            if (exibirMensagem) {
                System.out.printf("Saque de R$ %.2f realizado com sucesso.\n", valor);
            }
            return true;
        } else {
            System.out.println("Saldo insuficiente.");
            return false;
        }
    }


    @Override
    public boolean transferir(ContaBancaria destino, float valor) {
        float taxa = 2.50f;
        float valorTotal = valor + taxa;

        if (!sacar(valorTotal, false)) { // Não exibe mensagem de saque
            System.out.println("Erro: Saldo insuficiente para transferência.");
            return false;
        }

        destino.depositar(valor);
        System.out.printf("Transferência de R$ %.2f realizada com sucesso (Taxa: R$ %.2f).\n", valor, taxa);
        return true;
    }



    @Override
    public int hashCode() {
        return Objects.hash(numeroConta);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ContaBancaria other = (ContaBancaria) obj;
        return Objects.equals(numeroConta, other.numeroConta);
    }

    @Override
    public String toString() {
        return String.format("Conta: %d | Saldo: R$ %.2f | Status: %s | Abertura: %s",
                numeroConta, saldo, ativa ? "Ativa" : "Encerrada", dataAbertura);
    }
}
