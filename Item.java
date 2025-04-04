public class Item {
    private String descricao;
    private double valorUnitario;
    private int quantidade;
    private double total;

public Item(String descricao, double valorUnitario, int quantidade, double total){
    this.descricao = descricao;
    this.valorUnitario = valorUnitario;
    this.quantidade = quantidade;
    this.total = total;
}

public String getDescricao(){
      return descricao;
}

public void setDescricao(String descricao){
    this.descricao = descricao;
}

public double getValorUnitario (){
    return valorUnitario;
}

public void setValorUnitario (double valorUnitario){
    this.valorUnitario = valorUnitario;
}

public int getQuantidade (){
    return quantidade;
}

public void setQuantidade (int quantidade){
    this.quantidade = quantidade;
}

public double getTotal (){
    return total;
}

public void setTotal (double total){
  this.total = total;
}
}
