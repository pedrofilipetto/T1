import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedidos {
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private String status; // "Aprovado", "Rejeitado", "Concluído", "Aberto"
    private List<Item> itens = new ArrayList<>();
    private Departamento departamento;
    private Usuarios solicitante;

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
        if (!status.equals("Aprovado") && !status.equals("Rejeitado") && !status.equals("Concluído") && !status.equals("Aberto")) {
            throw new IllegalArgumentException("Status inválido. Deve ser 'Aprovado', 'Rejeitado', 'Concluído' ou 'Aberto'.");
        }
        if ((this.status.equals("Aprovado") || this.status.equals("Rejeitado") || this.status.equals("Concluído")) && status.equals("Aberto")) {
            throw new IllegalArgumentException("Não é possível reabrir um pedido já aprovado ou rejeitado ou concluído.");
        }

        if (status.equals("Concluído")) {
            this.dataFinal = LocalDate.now();
        }
        
        this.status = status;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Usuarios getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Usuarios usuarioAtual) {
        this.solicitante = usuarioAtual;
    }           

}
