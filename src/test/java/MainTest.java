
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
    void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
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

        Main.main(new String[]{}); // Inicializa o sistema (opcional)

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

        // Cria pedidos
        Pedidos p1 = new Pedidos(LocalDate.now(), null, "Aberto");
        p1.adicionarItem(new Item("Item1", 100, 2)); // total 200

        Pedidos p2 = new Pedidos(LocalDate.now(), null, "Aberto");
        p2.adicionarItem(new Item("Item2", 300, 1)); // total 300

        Pedidos p3 = new Pedidos(LocalDate.now(), null, "Aprovado");
        p3.adicionarItem(new Item("Item3", 500, 1)); // total 500

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
