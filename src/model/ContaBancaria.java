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
        this.ativa = false;
    }

    @Override
    public float getSaldo() {
        return saldo;
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
