package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    private String cpf;
    private String nome;
    private String senha;
    private List<ContaBancaria> contas;

    public Cliente(String cpf, String nome, String senha) {
        if (!validarCPF(cpf)) {
            throw new IllegalArgumentException("CPF inválido!");
        }
        this.cpf = cpf.replaceAll("\\D", "");
        this.nome = nome;
        this.senha = senha;
        this.contas = new ArrayList<>();
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    public List<ContaBancaria> getContas() {
        return contas;
    }

    public ContaBancaria localizarContaPorNumero(int numeroConta) {
        return contas.stream()
                .filter(conta -> conta.getNumeroConta() == numeroConta)
                .findFirst()
                .orElse(null);
    }

    public void adicionarConta(ContaBancaria novaConta) {
        if (novaConta != null) {
            contas.add(novaConta);
            System.out.println("Conta adicionada com sucesso!");
        }
    }

    public void removerConta(ContaBancaria conta) {
        if (contas.remove(conta)) {
            System.out.println("Conta removida com sucesso!");
        } else {
            System.out.println("Erro: Conta não encontrada.");
        }
    }

    @Override
    public String toString() {
        return String.format("Cliente: %s | CPF: %s | Contas: %d", nome, cpf, contas.size());
    }

    public static boolean validarCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) return false;
        return calcularDigitoVerificador(cpf);
    }

    private static boolean calcularDigitoVerificador(String cpf) {
        int soma1 = 0, soma2 = 0;
        int peso1 = 10, peso2 = 11;

        for (int i = 0; i < 9; i++) {
            int num = Character.getNumericValue(cpf.charAt(i));
            soma1 += num * peso1--;
            soma2 += num * peso2--;
        }

        int primeiroDigito = (soma1 * 10) % 11;
        if (primeiroDigito == 10) primeiroDigito = 0;

        soma2 += primeiroDigito * peso2;
        int segundoDigito = (soma2 * 10) % 11;
        if (segundoDigito == 10) segundoDigito = 0;

        return primeiroDigito == Character.getNumericValue(cpf.charAt(9)) &&
               segundoDigito == Character.getNumericValue(cpf.charAt(10));
    }
}