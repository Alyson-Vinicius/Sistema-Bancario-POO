package service;

import java.util.List;
import java.util.Optional;

import model.Cliente;
import persistencia.PersistenciaCliente;

public class ClienteService {
    private PersistenciaCliente persistencia;

    public ClienteService(PersistenciaCliente persistencia) {
        this.persistencia = persistencia;
    }

    public ClienteService() {
        this.persistencia = new PersistenciaCliente();
    }

    public boolean cadastrarCliente(Cliente cliente) {
        return persistencia.cadastrarCliente(cliente.getCpf(), cliente.getNome(), cliente.getSenha());
    }

    public List<Cliente> listarClientes() {
        return persistencia.getClientes();
    }

    public Optional<Cliente> buscarPorCpf(String cpf) {
        Cliente cliente = PersistenciaCliente.localizarClientePorCpf(cpf);
        return Optional.ofNullable(cliente); // Retorna Optional corretamente
    }

    public boolean removerCliente(String cpf) {
        return persistencia.removerCliente(cpf);
    }
}
