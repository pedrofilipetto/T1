import java.util.ArrayList;
import java.util.List;

public class Usuarios {
    private String nome;
    private String tipo;
    private String departamento;
    private List<Pedidos> pedidos = new ArrayList<>();

    public Usuarios(String nome, String tipo, String departamento) {
        this.nome = nome;
        this.tipo = tipo;
        this.departamento = departamento;
    }
}
