import java.util.Scanner;

class Fila {
    private String[] nomes;
    private int inicio;
    private int fim;
    private int quantidade;

    public Fila() {
        nomes = new String[3];
        inicio = 0;
        fim = 0;
        quantidade = 0;
    }

    public boolean entrar(String nome) {
        if (quantidade == nomes.length) {
            return false;
        }

        nomes[fim] = nome;
        fim = fim + 1;

        // Faz o fim voltar para a posição zero.
        if (fim == nomes.length) {
            fim = 0;
        }

        quantidade = quantidade + 1;
        return true;
    }

    public String atender() {
        if (estaVazia()) {
            return null;
        }

        String nomeAtendido = nomes[inicio];

        // A posição fica livre para ser reutilizada.
        nomes[inicio] = null;
        inicio = inicio + 1;

        // Faz o início voltar para a posição zero.
        if (inicio == nomes.length) {
            inicio = 0;
        }

        quantidade = quantidade - 1;
        return nomeAtendido;
    }

    public String proximo() {
        if (estaVazia()) {
            return null;
        }

        return nomes[inicio];
    }

    public int tamanho() {
        return quantidade;
    }

    public boolean estaVazia() {
        if (quantidade == 0) {
            return true;
        } else {
            return false;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Fila fila = new Fila();

        String comando = "";

        while (!comando.equals("FIM")) {
            comando = teclado.nextLine();

            if (comando.startsWith("ENTRA ")) {
                String nome = comando.substring(6);

                boolean conseguiuEntrar = fila.entrar(nome);

                if (conseguiuEntrar) {
                    System.out.println(nome + " entrou na fila");
                } else {
                    System.out.println(
                        nome + " não entrou porque a fila está cheia"
                    );
                }
            } else if (comando.equals("ATENDE")) {
                String nomeAtendido = fila.atender();

                if (nomeAtendido == null) {
                    System.out.println("A fila está vazia");
                } else {
                    System.out.println("Atendendo: " + nomeAtendido);
                }
            } else if (comando.equals("PROXIMO")) {
                String proximoNome = fila.proximo();

                if (proximoNome == null) {
                    System.out.println("A fila está vazia");
                } else {
                    System.out.println("Próximo: " + proximoNome);
                }
            } else if (comando.equals("TAMANHO")) {
                System.out.println(
                    "Pessoas na fila: " + fila.tamanho()
                );
            } else if (comando.equals("FIM")) {
                // O while terminará na próxima verificação.
            } else {
                System.out.println("Comando inválido");
            }
        }

        teclado.close();
    }
}