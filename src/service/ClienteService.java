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

    public void cadastrarCliente(Cliente cliente) {
        persistencia.cadastrarCliente(cliente);
    }

    public List<Cliente> listarClientes() {
        return persistencia.getClientes(); // Agora funciona corretamente
    }

    public Optional<Cliente> buscarPorCpf(String cpf) {
        return Optional.empty();
    }

    public void removerCliente(String cpf) {
        persistencia.removerCliente(cpf);
    }
}
