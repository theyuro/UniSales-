class Lampada {
    private boolean ligada;
    private int vezesLigada;

    public boolean ligar() {
        if (!ligada) {
            ligada = true;
            vezesLigada++;
        }

        return ligada;
    }

    public boolean desligar() {
        ligada = false;
        return ligada;
    }

    public boolean alternar() {
        if (ligada) {
            desligar();
        } else {
            ligar();
        }

        return ligada;
    }

    public boolean estaLigada() {
        return ligada;
    }

    public int getVezesLigada() {
        return vezesLigada;
    }
}

public class Main {
    public static void main(String[] args) {
        Lampada l = new Lampada();

        l.ligar();
        l.ligar();
        l.alternar();
        l.alternar();

        System.out.println("A lâmpada está ligada? " + l.estaLigada());
        System.out.println("Quantas vezes foi ligada: " + l.getVezesLigada());
    }
}