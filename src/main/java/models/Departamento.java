package models;
public class Departamento {
    private String nome;
    private double valorMaximoPorPedido;

    public Departamento(String nome, double valorMaximoPorPedido) {
        if (valorMaximoPorPedido <= 0) {
            throw new IllegalArgumentException("O valor máximo por pedido deve ser maior que zero.");
        }
        this.nome = nome;
        this.valorMaximoPorPedido = valorMaximoPorPedido;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do departamento não pode ser vazio.");
        }
        this.nome = nome;
    }

    public double getValorMaximoPorPedido() {
        return valorMaximoPorPedido;
    }

    public void setValorMaximoPorPedido(double valorMaximoPorPedido) {
        if (valorMaximoPorPedido <= 0) {
            throw new IllegalArgumentException("O valor máximo por pedido deve ser maior que zero.");
        }
        this.valorMaximoPorPedido = valorMaximoPorPedido;
    }

    @Override
    public String toString() {
        return nome + " (Limite por pedido: R$" + valorMaximoPorPedido + ")";
    }
}
