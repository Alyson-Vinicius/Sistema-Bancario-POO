package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public abstract class ContaBancaria implements IConta, Serializable {
    private static final long serialVersionUID = 1L;

    protected Integer numeroConta;
    protected float saldo;
    protected String dataAbertura;
    protected boolean ativa;
    protected List<Transacao> transacoes = new ArrayList<>();  // Lista para armazenar transações

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
            transacoes.add(new Transacao(TipoTransacao.CREDITO, BigDecimal.valueOf(valor), "Depósito")); // Adicionando ao extrato
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
            transacoes.add(new Transacao(TipoTransacao.DEBITO, BigDecimal.valueOf(-valor), "Saque")); // Adicionando ao extrato
            if (exibirMensagem) {
                System.out.printf("Saque de R$ %.2f realizado com sucesso.\n", valor);
            }
            return true;
        } else {
            System.out.println("Saldo insuficiente.");
            return false;
        }
    }

    public boolean transferir(ContaBancaria destino, float valor, TipoTransacao tipoOrigem) {
        if (!this.ativa) {
            System.out.println("Erro: Conta de origem está inativa.");
            return false;
        }
        if (!destino.isAtiva()) {
            System.out.println("Erro: Conta de destino está inativa.");
            return false;
        }

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Escolha o tipo de transferência:");
            System.out.println("1 - Transferência como Crédito");
            System.out.println("2 - Transferência como Débito");
            int escolha = scanner.nextInt();

            TipoTransacao tipoOrigem1;
            float taxa = 0f;

            switch (escolha) {
                case 1:
                    tipoOrigem1 = TipoTransacao.TRANSFERENCIA_CREDITO;
                    break;
                case 2:
                    tipoOrigem1 = TipoTransacao.TRANSFERENCIA_DEBITO;
                    taxa = 2.50f;
                    break;
                default:
                    System.out.println("Opção inválida. Operação cancelada.");
                    return false;
            }

            float valorTotal = valor + taxa;

            if (!sacar(valorTotal, false)) {
                System.out.println("Erro: Saldo insuficiente para transferência.");
                return false;
            }

            destino.depositar(valor);
            transacoes.add(new Transacao(tipoOrigem1, BigDecimal.valueOf(-valorTotal), "Transferência")); // Adicionando ao extrato
            destino.transacoes.add(new Transacao(TipoTransacao.TRANSFERENCIA_DEBITO, BigDecimal.valueOf(valor), "Transferência Recebida"));

            System.out.printf("Transferência de R$ %.2f realizada com sucesso (%s).\n",
                    valor, tipoOrigem1.getDescricao());
            if (taxa > 0) {
                System.out.printf("Taxa de transferência: R$ %.2f\n", taxa);
            }

            return true;
        }
    }

  

    public void imprimirExtrato(int mes, int ano) {
        String nomeArquivo = "clientes.data";
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo, true))) {
            writer.write("\n===== EXTRATO BANCÁRIO (" + String.format("%02d", mes) + "/" + ano + ") =====\n");
            writer.write("Conta: " + numeroConta + " | Saldo Atual: R$ " + String.format("%.2f", saldo) + "\n");
            writer.write("--------------------------------------------------\n");

            boolean encontrouMovimentacao = false;
            for (Transacao transacao : transacoes) {
                if (transacao.getDataHora().getMonthValue() == mes && transacao.getDataHora().getYear() == ano) {
                    writer.write(transacao.toString() + "\n");
                    encontrouMovimentacao = true;
                }
            }

            if (!encontrouMovimentacao) {
                writer.write("Nenhuma movimentação encontrada para esse período.\n");
            }
            writer.write("===============================================\n");
            
            System.out.println("Extrato salvo em " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao salvar o extrato: " + e.getMessage());
        }
    }



    // Método para obter todas as transações da conta
    public List<Transacao> obterTransacoes() {
        return new ArrayList<>(transacoes); // Retorna uma cópia da lista para evitar modificações externas
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
