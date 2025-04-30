import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Departamento> departamentos = new ArrayList<>();
        

        System.out.println("Pedido criado com sucesso!");
    }

    private void inicializar() {

        Departamento departamento1 = new Departamento("Financeiro", 1000);
        Departamento departamento2 = new Departamento("RH", 1000);
        Departamento departamento3 = new Departamento("Engenharia", 1000);
        Departamento departamento4 = new Departamento("Manuntenção", 1000);
        Departamento departamento5 = new Departamento("Vendas", 1000);



        departamentos.add(departamento1);
        departamentos.add(departamento2);
        departamentos.add(departamento3);
        departamentos.add(departamento4);
        departamentos.add(new Departamento("Vendas", 1000));

        Item item1 = new Item("Produto A", 10.0, 2, 20.0);
        Item item2 = new Item("Produto B", 15.0, 1, 15.0);

        Pedidos pedido = new Pedidos(LocalDate.now(), LocalDate.now().plusDays(5), "Pendente", 35.0);
        pedido.adicionarItem(item1);
        pedido.adicionarItem(item2);

        Usuarios usuario = new Usuarios("João", "Funcionário", "Vendas");
        usuario.adicionarPedido(pedido);

    }
}