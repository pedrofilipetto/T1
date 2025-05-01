import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static List<Departamento> departamentos = new ArrayList<>();
    private static List<Usuarios> usuarios = new ArrayList<>();
    private static List<Pedidos> pedidos = new ArrayList<>();
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
            System.out.println("3. Excluir pedido");
            System.out.println("4. Avaliar pedido");
            System.out.println("5. Listar pedidos entre duas datas");
            System.out.println("6. Buscar pedidos por funcionário solicitante");
            System.out.println("7. Buscar pedidos pela descrição de um item");
            System.out.println("8. Visualizar totais de pedidos");
            System.out.println("9. Visualizar total de pedidos nos últimos 30 dias");
            System.out.println("10. Mostrar pedido de maior valor ainda aberto");
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
                case 3:
                    excluirPedido(scanner);
                    break;
                case 4:
                    avaliarPedido(scanner);
                    break;
                case 5:
                    listarPedidosEntreDuasDatas(scanner);
                    break;
                case 6:
                    buscarPedidosPorSolicitante(scanner);
                    break;
                case 7:
                    buscarPedidosPelaDescricao(scanner);
                    break;
                case 8:
                    visualizarTotaisPedidos();
                    break;
                case 9:
                    visualizarTotal30Dias();
                    break;
                case 10:
                    mostrarPedidoValorMaiorAberto();
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

        Pedidos pedido = new Pedidos(LocalDate.now(), null, "Aberto");

        while (true) {
            System.out.print("Descrição do item (ou 'fim' para encerrar): ");
            String desc = scanner.nextLine();
            if (desc.equalsIgnoreCase("fim"))
                break;

            System.out.print("Valor unitário: ");
            double valor = scanner.nextDouble();

            System.out.print("Quantidade: ");
            int qtd = scanner.nextInt();
            scanner.nextLine();

            Item item = new Item(desc, valor, qtd);
            pedido.adicionarItem(item);
        }

        double total = pedido.getValorTotal();
        double limite = usuarioAtual.getDepartamento().getValorMaximoPorPedido();

        if (total > limite) {
            System.out.println("Valor total excede o limite do departamento. Pedido cancelado.");
            return;
        }

        pedido.setDepartamento(usuarioAtual.getDepartamento());
        pedido.setSolicitante(usuarioAtual);

        usuarioAtual.adicionarPedido(pedido);

        pedidos.add(pedido);

        System.out.println("Pedido criado com sucesso! Valor total: R$" + total);
    }

    private static void excluirPedido(Scanner scanner) {
        if (usuarioAtual == null) {
            System.out.println("Nenhum usuário logado.");
            return;
        }

        System.out.println("\n=== Excluir Pedido ===");
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido encontrado.");
            return;
        }

        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).getSolicitante().equals(usuarioAtual) && pedidos.get(i).getStatus().equals("Aberto")) {
                System.out.println(i + 1 + ". " + pedidos.get(i));
            }
        }
        System.out.print("Escolha o número do pedido: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha >= 1 && escolha <= pedidos.size()) {
            Pedidos pedido = pedidos.get(escolha - 1);
            pedidos.remove(pedido);
            usuarioAtual.getPedidos().remove(pedido);
            System.out.println("Pedido excluído com sucesso.");
        } else {
            System.out.println("Escolha inválida.");
        }
    }

    private static void avaliarPedido(Pedidos pedido, Scanner scanner) {
        if (pedido == null) {
            System.out.println("Nenhum pedido encontrado.");
            return;
        }

        if (usuarioAtual == null) {
            System.out.println("Nenhum usuário logado.");
            return;
        }

        if (!usuarioAtual.getTipo().equals("Administrador")) {
            System.out.println("Apenas administradores podem avaliar pedidos.");
            return;
        }

        if (!pedido.getStatus().equals("Aberto")) {
            System.out.println("Pedido já avaliado.");
            return;
        }

        System.out.print("Avaliar pedido (Aprovar/Rejeitar): ");
        String avaliacao = scanner.nextLine();

        if (avaliacao.equalsIgnoreCase("Aprovar")) {
            pedido.setStatus("Aprovado");
            System.out.println("Pedido aprovado.");
        } else if (avaliacao.equalsIgnoreCase("Rejeitar")) {
            pedido.setStatus("Rejeitado");
            System.out.println("Pedido rejeitado.");
        } else {
            System.out.println("Avaliação inválida.");
        }

    }

    private static void avaliarPedido(Scanner scanner) {
        if (usuarioAtual == null) {
            System.out.println("Nenhum usuário logado.");
            return;
        }

        if (!usuarioAtual.getTipo().equals("Administrador")) {
            System.out.println("Apenas administradores podem avaliar pedidos.");
            return;
        }

        System.out.println("\n=== Avaliar Pedido ===");
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido encontrado.");
            return;
        }

        int i = 0;
        List<Pedidos> pedidosFiltrados = new ArrayList<>();
        for (Pedidos pedido : pedidos) {
            if (pedido.getStatus().equals("Aberto")) {
                System.out.println(i + 1 + ". " + pedido);
                pedidosFiltrados.add(pedido);
                i++;
            }
        }

        if (pedidosFiltrados.isEmpty()) {
            System.out.println("Nenhum pedido encontrado.");
            return;
        }

        // Exibir detalhes do pedido
        System.out.print("Escolha o número do pedido: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha >= 1 && escolha <= pedidosFiltrados.size()) {
            Pedidos pedidoSelecionado = pedidosFiltrados.get(escolha - 1);
            exibirDetalhesPedido(pedidoSelecionado);
            avaliarPedido(pedidoSelecionado, scanner);

        } else {
            System.out.println("Escolha inválida.");
        }

    }

    private static void exibirDetalhesPedido(Pedidos pedido) {
        if (pedido == null) {
            System.out.println("Nenhum pedido encontrado.");
            return;
        }

        if (!usuarioAtual.getTipo().equals("Administrador")) {
            System.out.println("Apenas administradores podem ver os detalhes do pedido.");
            return;
        }

        System.out.println("\n=== Detalhes do Pedido ===");
        System.out.println("Data Inicial: " + pedido.getDataInicial());
        System.out.println("Data Final: " + pedido.getDataFinal());
        System.out.println("Status: " + pedido.getStatus());
        System.out.println("Solicitante: " + pedido.getSolicitante().getNome());
        System.out.println("Departamento: " + pedido.getDepartamento().getNome());
        System.out.println("Itens:");
        for (Item item : pedido.getItens()) {
            System.out.println("- " + item);
        }
    }

    // Listar todos os pedidos entre duas datas
    private static void listarPedidosEntreDuasDatas(Scanner scanner) {
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido encontrado.");
            return;
        }

        if (!usuarioAtual.getTipo().equals("Administrador")) {
            System.out.println("Apenas administradores podem listar pedidos entre datas.");
            return;
        }

        System.out.println("\n=== Listar Pedidos Entre Duas Datas ===");
        System.out.print("Data inicial (YYYY-MM-DD): ");
        String dataInicialStr = scanner.nextLine();
        System.out.print("Data final (YYYY-MM-DD): ");
        String dataFinalStr = scanner.nextLine();

        LocalDate dataInicial = LocalDate.parse(dataInicialStr);
        LocalDate dataFinal = LocalDate.parse(dataFinalStr);

        int i = 0;
        List<Pedidos> pedidosFiltrados = new ArrayList<>();
        for (Pedidos pedido : pedidos) {
            if ((pedido.getDataInicial().isAfter(dataInicial) || pedido.getDataInicial().isEqual(dataInicial)) &&
                    (pedido.getDataInicial().isBefore(dataFinal) || pedido.getDataInicial().isEqual(dataFinal))) {
                System.out.println(i + 1 + ". " + pedido);
                pedidosFiltrados.add(pedido);
                i++;
            }
        }

        if (pedidosFiltrados.isEmpty()) {
            System.out.println("Nenhum pedido encontrado entre as datas informadas.");
            return;
        }

        // Exibir detalhes do pedido
        System.out.print("Deseja ver os detalhes de algum pedido? (s/n): ");
        String resposta = scanner.nextLine();
        if (resposta.equalsIgnoreCase("s")) {
            System.out.print("Escolha o número do pedido: ");
            int escolha = scanner.nextInt();
            scanner.nextLine();

            if (escolha >= 1 && escolha <= pedidosFiltrados.size()) {
                Pedidos pedidoSelecionado = pedidosFiltrados.get(escolha - 1);
                exibirDetalhesPedido(pedidoSelecionado);

                System.out.print("Deseja avaliar o pedido? (s/n): ");
                String avaliarResposta = scanner.nextLine();
                if (avaliarResposta.equalsIgnoreCase("s")) {
                    avaliarPedido(pedidoSelecionado, scanner);
                }

            } else {
                System.out.println("Escolha inválida.");
            }
        }

    }

    // Buscar pedidos por funcionário solicitante
    private static void buscarPedidosPorSolicitante(Scanner scanner) {
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido encontrado.");
            return;
        }

        if (!usuarioAtual.getTipo().equals("Administrador")) {
            System.out.println("Apenas administradores podem buscar pedidos por funcionário solicitante.");
            return;
        }

        System.out.println("\n=== Buscar Pedidos por Funcionário Solicitante ===");
        System.out.print("Nome do funcionário: ");
        String nomeFuncionario = scanner.nextLine();

        int i = 0;
        List<Pedidos> pedidosFiltrados = new ArrayList<>();
        for (Pedidos pedido : pedidos) {
            if (pedido.getSolicitante().getNome().equalsIgnoreCase(nomeFuncionario)) {
                System.out.println(i + 1 + ". " + pedido);
                pedidosFiltrados.add(pedido);
                i++;
            }
        }

        if (pedidosFiltrados.isEmpty()) {
            System.out.println("Nenhum pedido encontrado para o funcionário informado.");
            return;
        }

        // Exibir detalhes do pedido
        System.out.print("Deseja ver os detalhes de algum pedido? (s/n): ");
        String resposta = scanner.nextLine();
        if (resposta.equalsIgnoreCase("s")) {
            System.out.print("Escolha o número do pedido: ");
            int escolha = scanner.nextInt();
            scanner.nextLine();

            if (escolha >= 1 && escolha <= pedidosFiltrados.size()) {
                Pedidos pedidoSelecionado = pedidosFiltrados.get(escolha - 1);
                exibirDetalhesPedido(pedidoSelecionado);

                System.out.print("Deseja avaliar o pedido? (s/n): ");
                String avaliarResposta = scanner.nextLine();
                if (avaliarResposta.equalsIgnoreCase("s")) {
                    avaliarPedido(pedidoSelecionado, scanner);
                }

            } else {
                System.out.println("Escolha inválida.");
            }
        }

    }

    // Buscar pedidos pela descrição de um item
    private static void buscarPedidosPelaDescricao(Scanner scanner) {
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido encontrado.");
            return;
        }

        if (!usuarioAtual.getTipo().equals("Administrador")) {
            System.out.println("Apenas administradores podem buscar pedidos pela descrição de um item.");
            return;
        }

        System.out.println("\n=== Buscar Pedidos pela Descrição de um Item ===");
        System.out.print("Descrição do item: ");
        String descricaoItem = scanner.nextLine();

        int i = 0;
        List<Pedidos> pedidosFiltrados = new ArrayList<>();
        for (Pedidos pedido : pedidos) {
            for (Item item : pedido.getItens()) {
                if (item.getDescricao().equalsIgnoreCase(descricaoItem)) {
                    System.out.println(i + 1 + ". " + pedido);
                    pedidosFiltrados.add(pedido);
                    i++;
                }
            }
        }

        if (pedidosFiltrados.isEmpty()) {
            System.out.println("Nenhum pedido encontrado com a descrição informada.");
            return;
        }

        // Exibir detalhes do pedido
        System.out.print("Deseja ver os detalhes de algum pedido? (s/n): ");
        String resposta = scanner.nextLine();
        if (resposta.equalsIgnoreCase("s")) {
            System.out.print("Escolha o número do pedido: ");
            int escolha = scanner.nextInt();
            scanner.nextLine();

            if (escolha >= 1 && escolha <= pedidosFiltrados.size()) {
                Pedidos pedidoSelecionado = pedidosFiltrados.get(escolha - 1);
                exibirDetalhesPedido(pedidoSelecionado);

                System.out.print("Deseja avaliar o pedido? (s/n): ");
                String avaliarResposta = scanner.nextLine();
                if (avaliarResposta.equalsIgnoreCase("s")) {
                    avaliarPedido(pedidoSelecionado, scanner);
                }

            } else {
                System.out.println("Escolha inválida.");
            }
        }
    }

    // Número de pedidos total, divididos entre aprovados e reprovados (com
    // percentuais).
    private static void visualizarTotaisPedidos() {

    }

    // Número de pedidos nos últimos 30 dias e seu valor médio.
    private static void visualizarTotal30Dias() {

    }

    // Detalhes do pedido de aquisição de maior valor ainda aberto.
    private static void mostrarPedidoValorMaiorAberto() {

    }

}
