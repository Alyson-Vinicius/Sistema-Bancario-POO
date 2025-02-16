package persistencia;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.Cliente;

public class PersistenciaCliente {
    private static final String ARQUIVO_CLIENTES = "clientes.dat";
    public List<Cliente> clientes; // Lista de clientes

    public PersistenciaCliente() {
        this.clientes = carregarClientes(); // Carregar clientes do arquivo
    }

    public boolean cadastrarCliente(String cpf, String nome, String senha) {
        cpf = cpf.replaceAll("\\D", ""); // Remove caracteres não numéricos

        if (!Cliente.validarCPF(cpf)) {
            return false; // CPF inválido
        }

        if (localizarClientePorCpf(cpf) != null) {
            return false; // CPF já cadastrado
        }

        Cliente novoCliente = new Cliente(cpf, nome, senha);
        clientes.add(novoCliente);
        salvarClientes(); // Salvar no arquivo
        return true;
    }

    public Cliente localizarClientePorCpf(String cpf) {
        cpf = cpf.replaceAll("\\D", "").trim(); // Normaliza o CPF

        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }

    public boolean removerCliente(String cpf) {
        Cliente cliente = localizarClientePorCpf(cpf);
        if (cliente != null) {
            clientes.remove(cliente);
            salvarClientes(); // Salvar mudanças
            return true;
        }
        return false;
    }

    public List<Cliente> getClientes() {
        return new ArrayList<>(clientes); // Retorna uma cópia para evitar modificações externas
    }

    // Salvar clientes em um arquivo
    private void salvarClientes() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_CLIENTES))) {
            oos.writeObject(clientes);
        } catch (IOException e) {
            System.err.println("Erro ao salvar clientes: " + e.getMessage());
        }
    }

    // Carregar clientes do arquivo
    @SuppressWarnings("unchecked")
    private List<Cliente> carregarClientes() {
        File arquivo = new File(ARQUIVO_CLIENTES);
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO_CLIENTES))) {
            return (List<Cliente>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar clientes: " + e.getMessage());
            return new ArrayList<>();
        }
    }

	public void cadastrarCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		
	}
}
