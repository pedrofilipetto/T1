import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static List<Departamento> departamentos = new ArrayList<>();
    private static List<Usuarios> usuarios = new ArrayList<>();
    private static Usuarios usuarioAtual;

    public static void main(String[] args) {
        inicializar(); 
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== Sistema de Controle de Aquisições ===");
            System.out.println("Usuário atual: " + (usuarioAtual != null ? usuarioAtual.getNome() : "Nenhum"));
            System.out.println("1. Trocar usuário");
            System.out.println("2. Criar novo pedido");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    trocarUsuario(scanner);
                    break;
                case 2:
                    criarPedido(scanner);
                    break;
                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
        scanner.close();
    }

    private static void inicializar() {
        
        Departamento d1 = new Departamento("Financeiro", 1000);
        Departamento d2 = new Departamento("RH", 1000);
        Departamento d3 = new Departamento("Engenharia", 1000);
        Departamento d4 = new Departamento("Manutenção", 1000);
        Departamento d5 = new Departamento("Vendas", 1000);
        departamentos.add(d1);
        departamentos.add(d2);
        departamentos.add(d3);
        departamentos.add(d4);
        departamentos.add(d5);

       
        Usuarios u1 = new Usuarios("João", "Funcionario", d5);
        Usuarios u2 = new Usuarios("Maria", "Administrador", d1);
        Usuarios u3 = new Usuarios("Lucas", "Funcionario", d2);
        Usuarios u4 = new Usuarios("Ana", "Funcionario", d3);
        Usuarios u5 = new Usuarios("Pedro", "Funcionario", d4);
        usuarios.add(u1);
        usuarios.add(u2);
        usuarios.add(u3);
        usuarios.add(u4);
        usuarios.add(u5);

        usuarioAtual = u1; 
    }

    private static void trocarUsuario(Scanner scanner) {
        System.out.println("\n=== Trocar Usuário ===");
        for (int i = 0; i < usuarios.size(); i++) {
            System.out.println(i + 1 + ". " + usuarios.get(i).getNome() + " (" + usuarios.get(i).getTipo() + ")");
        }
        System.out.print("Escolha o número do usuário: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha >= 1 && escolha <= usuarios.size()) {
            usuarioAtual = usuarios.get(escolha - 1);
            System.out.println("Usuário alterado para: " + usuarioAtual.getNome());
        } else {
            System.out.println("Escolha inválida.");
        }
    }

    private static void criarPedido(Scanner scanner) {
        if (usuarioAtual == null) {
            System.out.println("Nenhum usuário logado.");
            return;
        }

        Pedidos pedido = new Pedidos(LocalDate.now(), null, "Aberto", 0.0);

        while (true) {
            System.out.print("Descrição do item (ou 'fim' para encerrar): ");
            String desc = scanner.nextLine();
            if (desc.equalsIgnoreCase("fim")) break;

            System.out.print("Valor unitário: ");
            double valor = scanner.nextDouble();

            System.out.print("Quantidade: ");
            int qtd = scanner.nextInt();
            scanner.nextLine(); 

            Item item = new Item(desc, valor, qtd, valor * qtd);
            pedido.adicionarItem(item);
        }

        double total = pedido.calcularTotal();
        double limite = usuarioAtual.getDepartamento().getLimite();

        if (total > limite) {
            System.out.println("Valor total excede o limite do departamento. Pedido cancelado.");
            return;
        }

        pedido.setValorTotal(total);
        pedido.setDepartamento(usuarioAtual.getDepartamento());
        pedido.setSolicitante(usuarioAtual);

        usuarioAtual.adicionarPedido(pedido);

        System.out.println("Pedido criado com sucesso! Valor total: R$" + total);
    }
}
