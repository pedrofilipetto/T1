import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedidos { 
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private String status;
    private float valorTotal;
    private List<Item> itens = new ArrayList<>();

    public Pedidos(LocalDate dataInicial, LocalDate dataFinal, String status, float valorTotal) {
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.status = status;
        this.valorTotal = valorTotal;
    }
}
