import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedidos {
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private String status;
    private List<Item> itens = new ArrayList<>();

    public Pedidos(LocalDate dataInicial, LocalDate dataFinal, String status) {
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.status = status;
    }

    public void adicionarItem(Item item) {
        itens.add(item);
    }

    public double getValorTotal() {
        double total = 0;
        for (Item item : itens) {
            total += item.getTotal();
        }
        return total;
    }

    public LocalDate getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(LocalDate dataInicial) {
        this.dataInicial = dataInicial;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }
}
