class Pilha{

  private int[] dados;
  private int topo;
  int valorRemovido;


  public boolean empilhar(int x){
    if (estaCheia()){
      return false;
    }

    topo++;
    dados[topo] = x;
    return true;    
  }

  public int desempilhar() {
    if (estaVazia()) {
      System.out.println("A pilha está vazia.");
      return -1;
    }

    valorRemovido = dados[topo];
    topo--;
    return valorRemovido;
  }

  public int espiar() {
    if (estaVazia()) {
      System.out.println("A pilha está vazia.");
      return -1;
    }
    return dados[topo];
  }
  public boolean estaVazia() {
    return topo == -1;
  }

  public boolean estaCheia() {
    return topo == dados.length - 1;
  }

  public int tamanho() {
    return topo + 1;
  }

}

public class Main{
  public static void main(String[] Args){
    Pilha p = new Pilha(3);
    p.empilhar(10);
    p.empilhar(20);
    p.empilhar(30);
    System.out.println(p.empilhar(40));   // false
    System.out.println(p.desempilhar());  // 30
    System.out.println(p.espiar());       // 20
    System.out.println(p.tamanho());      // 2
  }
}