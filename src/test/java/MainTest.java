import org.junit.jupiter.api.*;

import models.Item;
import models.Pedidos;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

class MainTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;    

    @BeforeEach
    void setUpStreams() throws Exception {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        // Inicializa o sistema como no uso real
        var inicializar = Main.class.getDeclaredMethod("inicializar");
        inicializar.setAccessible(true);
        inicializar.invoke(null);
        // Troca usuarioAtual por um administrador
        var usuariosField = Main.class.getDeclaredField("usuarios");
        usuariosField.setAccessible(true);
        List<?> usuarios = (List<?>) usuariosField.get(null);
        Object admin = usuarios.stream()
            .filter(u -> {
                try {
                    return u.getClass().getMethod("getTipo").invoke(u).equals("Administrador");
                } catch (Exception e) {
                    return false;
                }
            })
            .findFirst()
            .orElse(null);
        var usuarioAtualField = Main.class.getDeclaredField("usuarioAtual");
        usuarioAtualField.setAccessible(true);
        usuarioAtualField.set(null, admin);
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testMostrarPedidoValorMaiorAberto_semPedidos() throws Exception {
        // Limpa a lista de pedidos
        var pedidosField = Main.class.getDeclaredField("pedidos");
        pedidosField.setAccessible(true);
        ((List<?>) pedidosField.get(null)).clear();

        // Main.main(new String[]{}); // Remova ou comente esta linha para evitar travamento

        // Executa o método
        var method = Main.class.getDeclaredMethod("mostrarPedidoValorMaiorAberto");
        method.setAccessible(true);
        method.invoke(null);

        Assertions.assertTrue(outContent.toString().contains("Nenhum pedido encontrado"));
    }

    @Test
    void testMostrarPedidoValorMaiorAberto_comPedidosEmAberto() throws Exception {
        // Prepara pedidos
        var pedidosField = Main.class.getDeclaredField("pedidos");
        pedidosField.setAccessible(true);
        List<Pedidos> pedidos = (List<Pedidos>) pedidosField.get(null);
        pedidos.clear();

        var usuarioAtualField = Main.class.getDeclaredField("usuarioAtual");
        usuarioAtualField.setAccessible(true);
        Object admin = usuarioAtualField.get(null);
        // Obtem o departamento do usuário atual
        var departamentoField = admin.getClass().getDeclaredField("departamento");
        departamentoField.setAccessible(true);
        Object departamento = departamentoField.get(admin);

        // Cria pedidos
        Pedidos p1 = new Pedidos(LocalDate.now(), null, "Aberto");
        p1.adicionarItem(new Item("Item1", 100, 2)); // total 200
        p1.setDepartamento((models.Departamento) departamento);
        p1.setSolicitante((models.Usuarios) admin);
        admin.getClass().getMethod("adicionarPedido", Pedidos.class).invoke(admin, p1);

        Pedidos p2 = new Pedidos(LocalDate.now(), null, "Aberto");
        p2.adicionarItem(new Item("Item2", 300, 1)); // total 300
        p2.setDepartamento((models.Departamento) departamento);
        p2.setSolicitante((models.Usuarios) admin);
        admin.getClass().getMethod("adicionarPedido", Pedidos.class).invoke(admin, p2);

        Pedidos p3 = new Pedidos(LocalDate.now(), null, "Aprovado");
        p3.adicionarItem(new Item("Item3", 500, 1)); // total 500
        p3.setDepartamento((models.Departamento) departamento);
        p3.setSolicitante((models.Usuarios) admin);
        admin.getClass().getMethod("adicionarPedido", Pedidos.class).invoke(admin, p3);

        pedidos.add(p1);
        pedidos.add(p2);
        pedidos.add(p3);

        // Executa o método
        var method = Main.class.getDeclaredMethod("mostrarPedidoValorMaiorAberto");
        method.setAccessible(true);
        method.invoke(null);

        String output = outContent.toString();
        Assertions.assertTrue(output.contains("Pedido de maior valor em aberto: R$300.0"));
        Assertions.assertTrue(output.contains("Item2"));
        Assertions.assertFalse(output.contains("Item3")); // Não deve mostrar pedido aprovado
    }

    @Test
    void testMostrarPedidoValorMaiorAberto_semPedidosEmAberto() throws Exception {
        // Prepara pedidos
        var pedidosField = Main.class.getDeclaredField("pedidos");
        pedidosField.setAccessible(true);
        List<Pedidos> pedidos = (List<Pedidos>) pedidosField.get(null);
        pedidos.clear();

        Pedidos p1 = new Pedidos(LocalDate.now(), null, "Aprovado");
        p1.adicionarItem(new Item("Item1", 100, 2));

        pedidos.add(p1);

        // Executa o método
        var method = Main.class.getDeclaredMethod("mostrarPedidoValorMaiorAberto");
        method.setAccessible(true);
        method.invoke(null);

        Assertions.assertTrue(outContent.toString().contains("Nenhum pedido em aberto encontrado"));
    }
}
