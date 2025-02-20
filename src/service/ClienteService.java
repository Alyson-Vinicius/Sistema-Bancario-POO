package service;

import java.util.List;
import java.util.Optional;
import model.Cliente;
import model.ContaBancaria;
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
    
    public float consultarBalancoCliente(String cpf) {
        Optional<Cliente> clienteOpt = buscarPorCpf(cpf);

        if (clienteOpt.isEmpty()) {
            System.out.println("Erro: Cliente não encontrado.");
            return 0;
        }

        Cliente cliente = clienteOpt.get();
        float balanco = cliente.calcularBalancoTotal();
        System.out.printf("Saldo total do cliente %s: R$ %.2f\n", cliente.getNome(), balanco);
        
        return balanco;
    }


    public List<Cliente> listarClientes() {
        return persistencia.getClientes();
    }

    public Optional<Cliente> buscarPorCpf(String cpf) {
        Cliente cliente = PersistenciaCliente.localizarClientePorCpf(cpf); // ✅ Corrigido
        return Optional.ofNullable(cliente);
    }

    public boolean removerCliente(String cpf) {
        return persistencia.removerCliente(cpf);
    }

    public boolean transferirEntreClientes(String cpfOrigem, int numContaOrigem, String cpfDestino, int numContaDestino, float valor) {
        if (valor <= 0) {
            System.out.println("Erro: O valor da transferência deve ser maior que zero.");
            return false;
        }

        Optional<Cliente> clienteOrigemOpt = buscarPorCpf(cpfOrigem);
        Optional<Cliente> clienteDestinoOpt = buscarPorCpf(cpfDestino);

        if (clienteOrigemOpt.isEmpty() || clienteDestinoOpt.isEmpty()) {
            System.out.println("Erro: Um dos clientes não foi encontrado.");
            return false;
        }

        Cliente clienteOrigem = clienteOrigemOpt.get();
        Cliente clienteDestino = clienteDestinoOpt.get();

        ContaBancaria contaOrigem = clienteOrigem.localizarContaPorNumero(numContaOrigem);
        ContaBancaria contaDestino = clienteDestino.localizarContaPorNumero(numContaDestino);

        if (contaOrigem == null || contaDestino == null) {
            System.out.println("Erro: Uma das contas não foi encontrada.");
            return false;
        }

        if (!contaOrigem.isAtiva() || !contaDestino.isAtiva()) {
            System.out.println("Erro: Uma das contas está inativa.");
            return false;
        }

        if (!contaOrigem.transferir(contaDestino, valor)) {
            System.out.println("Erro: Transferência não realizada.");
            return false;
        }

        System.out.println("Transferência realizada com sucesso!");
        return true;
    }
}
