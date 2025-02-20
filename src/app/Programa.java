package app;

import model.Cliente;
import model.ContaBancaria;
import model.ContaCorrente;
import model.ContaPoupanca;
import model.Transacao;
import persistencia.PersistenciaCliente;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Programa {
    public static void main(String[] args) {
        PersistenciaCliente persistencia = new PersistenciaCliente();
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        System.out.println("Bem-vindo ao sistema bancário!");

        while (continuar) {
            try {
            	System.out.println(" ____________________________________ ");
            	System.out.println("|                                    |");
                System.out.println("|           Menu Principal           |");
                System.out.println("|____________________________________|");
                System.out.println("|        O que deseja fazer?         |");
                System.out.println("|1 -> Cadastrar novo cliente         |");
                System.out.println("|2 -> Login                          |");
                System.out.println("|3 -> Listar todos os clientes       |");
                System.out.println("|4 -> Buscar cliente via cpf         |");
                System.out.println("|5 -> Remover cliente                |");
                System.out.println("|6 -> Sair                           |");
                System.out.println("|____________________________________|");

                int opcao = Integer.parseInt(scanner.nextLine()); // Lê como String e converte

                switch (opcao) {
                    case 1 -> cadastrarCliente(persistencia, scanner);
                    case 2 -> login(persistencia, scanner);
                    case 3 -> listarClientes(persistencia);
                    case 4 -> BuscarClienteViaCpf(persistencia, scanner);
                    case 5 -> removerCliente(persistencia, scanner);
                    case 6 -> {
                        System.out.println("Saindo...");
                        continuar = false;
                    }
                    default -> System.out.println("Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Entrada inválida. Digite um número.");
            }
        }
    }

    private static void BuscarClienteViaCpf(PersistenciaCliente persistencia, Scanner scanner) {
        System.out.print("Digite o CPF do cliente: ");
        String cpf = scanner.nextLine().replaceAll("\\D", "").trim(); // Normaliza o CPF

        Cliente cliente = PersistenciaCliente.localizarClientePorCpf(cpf);

        if (cliente != null) {
            System.out.println("Cliente encontrado:");
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("CPF: " + cliente.getCpf());
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }


    private static void cadastrarCliente(PersistenciaCliente persistencia, Scanner scanner) {
        System.out.print("Insira o CPF: ");
        String cpf = scanner.nextLine().replaceAll("\\D", ""); // Remove caracteres não numéricos

        System.out.print("Insira o nome: ");
        String nome = scanner.nextLine();
        
        System.out.print("Insira a senha: ");
        String senha = scanner.nextLine();

        try {
            if (persistencia.cadastrarCliente(cpf, nome, senha)) {
                System.out.println("Cliente cadastrado com sucesso!");
            } else {
                System.out.println("Falha ao cadastrar cliente.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }



    private static void login(PersistenciaCliente persistencia, Scanner scanner) {
        System.out.print("Digite o CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();

        Cliente cliente = PersistenciaCliente.localizarClientePorCpf(cpf);

        if (cliente != null && cliente.getSenha().equals(senha)) {
            System.out.println("Bem-vindo, " + cliente.getNome() + "!");
            opcoesDeCliente(persistencia, scanner, cliente);
        } else {
            System.out.println("CPF ou senha inválidos.");
        }
    }

    private static void listarClientes(PersistenciaCliente persistencia) {
        persistencia.getClientes().forEach(System.out::println);
    }

    private static void removerCliente(PersistenciaCliente persistencia, Scanner scanner) {
        System.out.print("Digite o CPF do cliente a ser removido: ");
        String cpf = scanner.nextLine();

        if (persistencia.removerCliente(cpf)) {
            System.out.println("Cliente removido com sucesso!");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private static void opcoesDeCliente(PersistenciaCliente persistencia, Scanner scanner, Cliente cliente) {
        boolean voltar = false;

        while (!voltar) {
            try {
                System.out.println(" ____________________________________ ");
                System.out.println("|            Menu Cliente            |");
                System.out.println("|1  -> Criar Conta                   |");
                System.out.println("|2  -> Depositar                     |");
                System.out.println("|3  -> Sacar                         |");
                System.out.println("|4  -> Transferir                    |");
                System.out.println("|5  -> Saldo de uma conta            |");
                System.out.println("|6  -> Saldo total das Contas        |");
                System.out.println("|7  -> Listar Contas                 |");
                System.out.println("|8  -> Remover Conta                 |");
                System.out.println("|9  -> Extrato Bancario              |");
                System.out.println("|10 -> Voltar ao Menu Principal      |");
                System.out.println("|____________________________________|");

                int opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1 -> criarConta(cliente, scanner);
                    case 2 -> depositarSaldo(cliente, scanner);
                    case 3 -> saqueSaldo(cliente, scanner);
                    case 4 -> transferirSaldo(cliente, scanner);
                    case 5 -> saldoConta(cliente, scanner);
                    case 6 -> saldoContaTotal(cliente);
                    case 7 -> listarContas(cliente);
                    case 8 -> removerConta(cliente, scanner);
                    case 9 -> {
                        System.out.print("Digite o número da conta: ");
                        int numeroConta = Integer.parseInt(scanner.nextLine());

                        ContaBancaria conta = cliente.localizarContaPorNumero(numeroConta);
                        if (conta == null) {
                            System.out.println("Conta não encontrada.");
                            break;
                        }

                        System.out.print("Digite o mês do extrato (1-12): ");
                        int mes = Integer.parseInt(scanner.nextLine());

                        System.out.print("Digite o ano do extrato: ");
                        int ano = Integer.parseInt(scanner.nextLine());

                        extratoBancario(conta, mes, ano);
                    }
                    case 10 -> voltar = true;
                    default -> System.out.println("Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }
    }

    
 

    private static Object extratoBancario(Object extratoBancario) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Object extratoBancario(int opcao, int opcao2) {
		// TODO Auto-generated method stub
		return null;
	}

	private static void extratoBancario(ContaBancaria conta, int mes, int ano) {
	    if (conta == null) {
	        System.out.println("Erro: Conta não encontrada.");
	        return;
	    }

	    List<Transacao> transacoes = conta.obterTransacoes(); // Pegando a lista de transações

	    if (transacoes.isEmpty()) {
	        System.out.println("Nenhuma transação registrada.");
	        return;
	    }

	    System.out.printf("\nExtrato da Conta %d - %02d/%d\n", conta.getNumeroConta(), mes, ano);
	    System.out.println("--------------------------------------------------");

	    boolean encontrouMovimentacao = false;
	    for (Transacao transacao : transacoes) {
	        if (transacao.getDataHora().getMonthValue() == mes && transacao.getDataHora().getYear() == ano) {
	            System.out.println(transacao);
	            encontrouMovimentacao = true;
	        }
	    }

	    if (!encontrouMovimentacao) {
	        System.out.println("Nenhuma movimentação encontrada para esse período.");
	    }
	    System.out.println("--------------------------------------------------");
	}





    
	public List<Transacao> obterTransacoes() {
	    return new ArrayList<>(transacoes); // Retorna uma cópia da lista de transações
	}




	private static void transferirSaldo(Cliente cliente, Scanner scanner) {
        System.out.print("Digite o número da conta de origem: ");
        int numeroContaOrigem = Integer.parseInt(scanner.nextLine());

        ContaBancaria contaOrigem = cliente.localizarContaPorNumero(numeroContaOrigem);
        if (contaOrigem == null) {
            System.out.println("Conta de origem não encontrada.");
            return;
        }

        System.out.print("Digite o número da conta de destino: ");
        int numeroContaDestino = Integer.parseInt(scanner.nextLine());

        // Verifica se a conta de destino está na lista do mesmo cliente
        ContaBancaria contaDestino = cliente.localizarContaPorNumero(numeroContaDestino);

        // Se a conta não foi encontrada no próprio cliente, buscar em todos os clientes
        if (contaDestino == null) {
            System.out.print("Digite o CPF do destinatário: ");
            String cpfDestino = scanner.nextLine().replaceAll("\\D", "");

            Cliente destinatario = PersistenciaCliente.localizarClientePorCpf(cpfDestino);
            if (destinatario != null) {
                contaDestino = destinatario.localizarContaPorNumero(numeroContaDestino);
            }
        }

        if (contaDestino == null) {
            System.out.println("Conta de destino não encontrada.");
            return;
        }

        System.out.print("Digite o valor a transferir: ");
        float valor = Float.parseFloat(scanner.nextLine());

        if (contaOrigem.sacar(valor)) {
            contaDestino.depositar(valor);
            System.out.println("Transferência realizada com sucesso!");
        } else {
            System.out.println("Saldo insuficiente para a transferência.");
        }
    }


	private static void criarConta(Cliente cliente, Scanner scanner) {
        System.out.println("Escolha o tipo de conta:");
        System.out.println("1 -> Conta Corrente");
        System.out.println("2 -> Conta Poupança");

        int tipoConta = Integer.parseInt(scanner.nextLine());

        ContaBancaria novaConta;
        if (tipoConta == 1) {
            novaConta = new ContaCorrente(cliente.getContas().size() + 1);
        } else if (tipoConta == 2) {
            novaConta = new ContaPoupanca(cliente.getContas().size() + 1);
        } else {
            System.out.println("Tipo de conta inválido.");
            return;
        }

        cliente.adicionarConta(novaConta);
        System.out.println("Conta criada com sucesso!");
    }

    private static void depositarSaldo(Cliente cliente, Scanner scanner) {
        System.out.print("Digite o número da conta: ");
        int numeroConta = Integer.parseInt(scanner.nextLine());

        ContaBancaria conta = (ContaBancaria) cliente.localizarContaPorNumero(numeroConta);

        if (conta != null) {
            System.out.print("Digite o valor do depósito: ");
            float valor = Float.parseFloat(scanner.nextLine());
            conta.depositar(valor);
            System.out.println("Depósito realizado!");
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private static void saqueSaldo(Cliente cliente, Scanner scanner) {
        System.out.print("Digite o número da conta: ");
        int numeroConta = Integer.parseInt(scanner.nextLine());

        ContaBancaria conta = (ContaBancaria) cliente.localizarContaPorNumero(numeroConta);

        if (conta != null) {
            System.out.print("Digite o valor do saque: ");
            float valor = Float.parseFloat(scanner.nextLine());
            if (conta.sacar(valor)) {
                System.out.println("Saque realizado!");
            } else {
                System.out.println("Saldo insuficiente.");
            }
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private static void saldoConta(Cliente cliente, Scanner scanner) {
        System.out.print("Digite o número da conta: ");
        int numeroConta = Integer.parseInt(scanner.nextLine());

        ContaBancaria conta = (ContaBancaria) cliente.localizarContaPorNumero(numeroConta);

        if (conta != null) {
            System.out.println("O saldo da conta " + conta.getNumeroConta() + " é: R$" + conta.getSaldo());
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private static void saldoContaTotal(Cliente cliente) {
        float saldoTotal = (float) cliente.getContas().stream()
                                          .mapToDouble(ContaBancaria::getSaldo)
                                          .sum();
        System.out.println("Saldo total de todas as contas: " + saldoTotal);
    }

    private static void listarContas(Cliente cliente) {
        cliente.getContas().forEach(System.out::println);
    }

    private static void removerConta(Cliente cliente, Scanner scanner) {
        System.out.print("Digite o número da conta a ser removida: ");
        int numeroConta = Integer.parseInt(scanner.nextLine());
        cliente.removerConta(cliente.localizarContaPorNumero(numeroConta));
    }
}