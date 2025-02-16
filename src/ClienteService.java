import java.util.List;
import java.util.Optional;

import model.Cliente;
import persistencia.PersistenciaCliente;

public class ClienteService {
    private PersistenciaCliente persistencia;

    public ClienteService(PersistenciaCliente persistencia) {
        this.persistencia = persistencia;
    }

    public void cadastrarCliente(Cliente cliente) {
        persistencia.cadastrarCliente(cliente);
    }

    public List<Cliente> listarClientes() {
        return persistencia.clientes;
    }

    public Optional<Cliente> buscarPorCpf(String cpf) {
        return Optional.ofNullable(persistencia.localizarClientePorCpf(cpf));
    }

    public void removerCliente(String cpf) {
        persistencia.removerCliente(cpf);
    }
}
