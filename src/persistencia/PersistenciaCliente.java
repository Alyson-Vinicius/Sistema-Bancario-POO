package persistencia;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.Cliente;

public class PersistenciaCliente {
    private static final String ARQUIVO_CLIENTES = "clientes.dat";
    public static List<Cliente> clientes; // Lista de clientes

    public PersistenciaCliente() {
        PersistenciaCliente.clientes = carregarClientes(); // Carregar clientes do arquivo
    }

    public boolean cadastrarCliente(String cpf, String nome, String senha) {
        cpf = cpf.replaceAll("\\D", ""); // Remove caracteres não numéricos

        // Valida o CPF
        if (!Cliente.validarCPF(cpf)) {
            System.err.println("Erro: CPF inválido! O cliente NÃO foi cadastrado.");
            return false; // Retorna antes de criar o cliente
        }

        // Verifica se o cliente já está cadastrado
        if (localizarClientePorCpf(cpf) != null) {
            System.err.println("Erro: Cliente já cadastrado!");
            return false; // Retorna imediatamente se o cliente já existir
        }

        // Cria e adiciona o novo cliente
        Cliente novoCliente = new Cliente(cpf, nome, senha);
        clientes.add(novoCliente);
        salvarClientes(); // Salva a lista atualizada no arquivo
        System.out.println("Cliente cadastrado com sucesso!"); // Confirmação de cadastro
        return true;
    }


    public static Cliente localizarClientePorCpf(String cpf) {
        cpf = cpf.replaceAll("\\D", "").trim(); // Remove caracteres não numéricos

        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null; // Retorna null se não encontrar
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
        if (!Cliente.validarCPF(cliente.getCpf())) {
            throw new IllegalArgumentException("CPF inválido!"); // Lança erro se CPF for inválido
        }

        if (localizarClientePorCpf(cliente.getCpf()) != null) {
            throw new IllegalArgumentException("Cliente já cadastrado!");
        }

        clientes.add(cliente);
        salvarClientes();
    }

}
