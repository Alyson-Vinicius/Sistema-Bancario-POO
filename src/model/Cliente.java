package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    private String cpf;
    private String nome;
    private String senha;
    private List<ContaBancaria> contas; // Lista de contas associadas ao cliente

    public Cliente(String cpf, String nome, String senha) {
        if (!validarCPF(cpf)) {
            throw new IllegalArgumentException("CPF inválido!");
        }
        this.cpf = cpf.replaceAll("\\D", ""); // Remove caracteres não numéricos
        this.nome = nome;
        this.senha = senha;
        this.contas = new ArrayList<>(); // Inicializa a lista de contas
    }

    // Getters
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

    // Localizar uma conta pelo número
    public ContaBancaria localizarContaPorNumero(int numeroConta) {
        return contas.stream()
                .filter(conta -> conta.getNumeroConta() == numeroConta)
                .findFirst()
                .orElse(null);
    }

    // Adicionar uma nova conta
    public void adicionarConta(ContaBancaria novaConta) {
        if (novaConta != null) {
            contas.add(novaConta);
            System.out.println("Conta adicionada com sucesso!");
        } else {
            System.out.println("Erro: Conta inválida.");
        }
    }

    // Remover uma conta
    public void removerConta(ContaBancaria conta) {
        if (conta != null && contas.contains(conta)) {
            contas.remove(conta);
            System.out.println("Conta removida com sucesso!");
        } else {
            System.out.println("Erro: Conta não encontrada.");
        }
    }

    @Override
    public String toString() {
        return String.format("""
            ____________________________________
            | Cliente: %s
            | CPF: %s
            | Contas: %d
            ____________________________________""", 
            nome, cpf, contas.size());
    }

    // Validação de CPF
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




	public ContaBancaria localizarContaPorNumero1(int numeroConta) {
		// TODO Auto-generated method stub
		return null;
	}

	public void removerConta1(ContaBancaria localizarContaPorNumero) {
		// TODO Auto-generated method stub
		
	}

	public void adicionarConta1(ContaBancaria novaConta) {
		// TODO Auto-generated method stub
		
	}

	public List<Cliente> getContas1() {
		// TODO Auto-generated method stub
		return null;
	}
}
