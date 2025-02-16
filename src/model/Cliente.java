package model;
import java.io.Serializable;

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L; // Versão para compatibilidade
    private String cpf;
    private String nome;
    private String senha;

    public Cliente(String cpf, String nome, String senha) {
        this.cpf = cpf;
        this.nome = nome;
        this.senha = senha;
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

    @Override
    public String toString() {
        return " ____________________________________\n"
             + "| Cliente: " + nome + "\n"
             + "| CPF: " + cpf + "\n"
             + "| Contas: 0\n"
             + " ____________________________________";
    }

	public static boolean validarCPF(String cpf2) {
		// TODO Auto-generated method stub
		return false;
	}

	public void adicionarConta(ContaBancaria novaConta) {
		// TODO Auto-generated method stub
		
	}

	public Object localizarContaPorNumero(int numeroConta) {
		// TODO Auto-generated method stub
		return null;
	}

	public void removerConta(Object localizarContaPorNumero) {
		// TODO Auto-generated method stub
		
	}
}
