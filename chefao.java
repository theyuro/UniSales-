import java.util.Random;

abstract class Personagem {
    private String nome;
    private int vida;

    public Personagem(String nome) {
        this.nome = nome;
        vida = 100;
    }

    public void receberDano(int dano) {
        if (dano > 0) {
            vida = vida - dano;
        }

        if (vida < 0) {
            vida = 0;
        }

        if (vida > 100) {
            vida = 100;
        }
    }

    public boolean estaVivo() {
        if (vida > 0) {
            return true;
        } else {
            return false;
        }
    }

    public int getVida() {
        return vida;
    }

    public String getNome() {
        return nome;
    }

    public int atacar(Personagem alvo) {
        if (estaVivo() == false) {
            System.out.println(nome + " está morto e não pode atacar.");
            return 0;
        }

        int dano = calcularAtaque();
        alvo.receberDano(dano);

        return dano;
    }

    protected abstract int calcularAtaque();
}

class Guerreiro extends Personagem {
    private Random sorteio;

    public Guerreiro(String nome, Random sorteio) {
        super(nome);
        this.sorteio = sorteio;
    }

    @Override
    protected int calcularAtaque() {
        // nextInt(8) gera um número de 0 até 7.
        // Somando 8, o resultado fica entre 8 e 15.
        int ataque = sorteio.nextInt(8) + 8;
        return ataque;
    }

    @Override
    public void receberDano(int dano) {
        int reducao = dano * 20 / 100;
        int danoReduzido = dano - reducao;

        super.receberDano(danoReduzido);
    }
}

class Mago extends Personagem {
    private int mana;

    public Mago(String nome) {
        super(nome);
        mana = 30;
    }

    @Override
    protected int calcularAtaque() {
        if (mana >= 10) {
            mana = mana - 10;
            return 20;
        } else {
            return 5;
        }
    }

    public int getMana() {
        return mana;
    }
}

public class Main {
    public static void main(String[] args) {
        Random sorteio = new Random(42);

        Guerreiro guerreiro = new Guerreiro("Guerreiro", sorteio);
        Mago mago = new Mago("Mago");

        int turno = 1;

        while (guerreiro.estaVivo() && mago.estaVivo()) {
            int danoGuerreiro = guerreiro.atacar(mago);

            System.out.println(
                "Turno " + turno + ": "
                + guerreiro.getNome() + " causa "
                + danoGuerreiro + " de dano. "
                + mago.getNome() + " está com "
                + mago.getVida() + " de vida."
            );

            turno = turno + 1;

            if (mago.estaVivo()) {
                int vidaAntes = guerreiro.getVida();
                int ataqueDoMago = mago.atacar(guerreiro);
                int danoRecebido = vidaAntes - guerreiro.getVida();

                System.out.println(
                    "Turno " + turno + ": "
                    + mago.getNome() + " tenta causar "
                    + ataqueDoMago + " de dano. "
                    + guerreiro.getNome() + " recebe "
                    + danoRecebido + " após a redução e está com "
                    + guerreiro.getVida() + " de vida."
                );

                turno = turno + 1;
            }
        }

        if (guerreiro.estaVivo()) {
            System.out.println(
                "O vencedor foi " + guerreiro.getNome() + "!"
            );
        } else {
            System.out.println(
                "O vencedor foi " + mago.getNome() + "!"
            );
        }
    }
}