class Item {
    private String nome;
    private double peso;

    public Item(String nome, double peso) {
        this.nome = nome;
        this.peso = peso;
    }

    public String getNome() {
        return nome;
    }

    public double getPeso() {
        return peso;
    }
}

class Inventario {
    private ArrayList<Item> itens;

    public Inventario() {
        itens = new ArrayList<Item>();
    }

    public boolean adicionar(Item item) {
        if (itens.size() >= 5) {
            return false;
        }

        double pesoAtual = pesoTotal();
        double pesoDepoisDeAdicionar = pesoAtual + item.getPeso();

        if (pesoDepoisDeAdicionar > 20.0) {
            return false;
        }

        itens.add(item);
        return true;
    }

    public boolean remover(String nome) {
        for (int i = 0; i < itens.size(); i++) {
            Item itemAtual = itens.get(i);

            if (itemAtual.getNome().equals(nome)) {
                itens.remove(i);
                return true;
            }
        }

        return false;
    }

    public double pesoTotal() {
        double soma = 0;

        for (int i = 0; i < itens.size(); i++) {
            Item itemAtual = itens.get(i);
            soma = soma + itemAtual.getPeso();
        }

        return soma;
    }

    public void listar() {
        if (itens.size() == 0) {
            System.out.println("O inventário está vazio.");
        } else {
            for (int i = 0; i < itens.size(); i++) {
                Item itemAtual = itens.get(i);

                System.out.println(
                    "Nome: " + itemAtual.getNome()
                    + " | Peso: " + itemAtual.getPeso() + " kg"
                );
            }
        }
    }

    /*
     * Não existe get pros itens, pois ele poderia permitir que alguém
     * alterasse a lista sem passar pelo método adicionar().
     * Assim, seria possível ultrapassar os limites do inventário.
     */
}

public class Main {
    public static void main(String[] args) {
        Inventario inv = new Inventario();

        Item espada = new Item("Espada", 8);
        Item escudo = new Item("Escudo", 7);
        Item pocao = new Item("Poção", 1);
        Item armadura = new Item("Armadura", 10);

        inv.adicionar(espada);
        inv.adicionar(escudo);
        inv.adicionar(pocao);

        boolean conseguiuAdicionar = inv.adicionar(armadura);

        System.out.println(conseguiuAdicionar); // false
        System.out.println(inv.pesoTotal());    // 16.0

        inv.listar();
    }
}