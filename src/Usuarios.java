import java.util.ArrayList;
import java.util.List;

public class Usuarios {
    private String nome;
    private String tipo; // "Administrador" ou "Funcionário"
    private Departamento departamento;
    private List<Pedidos> pedidos = new ArrayList<>();

    public Usuarios(String nome, String tipo, Departamento departamento) {
        this.nome = nome;
        this.tipo = tipo;
        this.departamento = departamento;
    }

    public void adicionarPedido(Pedidos pedido) {
        pedidos.add(pedido);
    }

    public List<Pedidos> getPedidos() {
        return pedidos;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public String getIniciais() {
        String[] partes = nome.split(" ");
        StringBuilder iniciais = new StringBuilder();
        for (String parte : partes) {
            if (!parte.isEmpty()) {
                iniciais.append(parte.charAt(0));
            }
        }
        return iniciais.toString().toUpperCase();
    }
}
