package persistencia;

import model.Cliente;
import model.ContaBancaria;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaCliente {
    private static final String ARQUIVO_CLIENTES = "clientes.data";
    private static List<Cliente> clientes = new ArrayList<>();

    public PersistenciaCliente() {
        clientes = carregarClientes();
    }

    public boolean cadastrarCliente(String cpf, String nome, String senha) {
        if (localizarClientePorCpf(cpf) == null) {
            Cliente novoCliente = new Cliente(cpf, nome, senha);
            clientes.add(novoCliente);
            salvarClientes();
            return true;
        }
        return false;
    }

    public boolean removerCliente(String cpf) {
        Cliente cliente = localizarClientePorCpf(cpf);
        if (cliente != null) {
            clientes.remove(cliente);
            salvarClientes();
            return true;
        }
        return false;
    }

    public boolean adicionarContaCliente(String cpf, ContaBancaria conta) {
        Cliente cliente = localizarClientePorCpf(cpf);
        if (cliente != null) {
            cliente.adicionarConta(conta);
            salvarClientes(); // Salvar automaticamente após adicionar conta
            return true;
        }
        return false;
    }

    public static Cliente localizarClientePorCpf(String cpf) {
        return clientes.stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    private void salvarClientes() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ARQUIVO_CLIENTES))) {
            out.writeObject(clientes);
        } catch (IOException e) {
            System.out.println("Erro ao salvar clientes: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private List<Cliente> carregarClientes() {
        File arquivo = new File(ARQUIVO_CLIENTES);
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(ARQUIVO_CLIENTES))) {
            return (List<Cliente>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar clientes: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
