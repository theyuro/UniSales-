import java.util.ArrayList;
import java.util.List;

// Missão: O Labirinto de Salesiania – A Jornada dos Algoritmos Perdidos
// Encontra a saída do labirinto usando recursividade e coleta tesouros usando um algoritmo guloso
public class LabirintoSalesiania {

    // Direções de movimento na ordem: cima, baixo, esquerda, direita
    static int[] deslocamentoLinha = { -1, 1, 0, 0 };
    static int[] deslocamentoColuna = { 0, 0, -1, 1 };
    static String[] nomeDirecao = { "cima", "baixo", "esquerda", "direita" };

    // Variáveis usadas pela busca recursiva para guardar o melhor caminho encontrado
    // (antes eram parâmetros da função, mas ficou parâmetro demais e passei pra cá)
    static List<Posicao> melhorCaminho;
    static boolean[][] visitado;

    // Variáveis do modo rastreio (mostra as chamadas recursivas para conferir o teste de mesa)
    static boolean modoRastreio = false;
    static int contadorChamadas = 0;

    // Classe que representa uma posição (linha, coluna) dentro do labirinto
    static class Posicao {

        int linha;
        int coluna;

        // Cria uma posição com a linha e a coluna informadas
        Posicao(int linha, int coluna) {
            this.linha = linha;
            this.coluna = coluna;
        }

        // Verifica se esta posição é igual a outra posição
        // (o == não funciona pra objeto, compara o endereço e não os valores)
        boolean igual(Posicao outra) {
            return this.linha == outra.linha && this.coluna == outra.coluna;
        }

        // Devolve a posição no formato (linha,coluna)
        public String toString() {
            return "(" + linha + "," + coluna + ")";
        }
    }

    // Transforma as linhas de texto ("S . . # . .") em uma matriz de caracteres
    static char[][] criarLabirinto(String... linhasDoLabirinto) {
        char[][] labirinto = new char[linhasDoLabirinto.length][];

        for (int linha = 0; linha < linhasDoLabirinto.length; linha++) {
            String[] celulas = linhasDoLabirinto[linha].trim().split(" ");
            labirinto[linha] = new char[celulas.length];

            for (int coluna = 0; coluna < celulas.length; coluna++) {
                labirinto[linha][coluna] = celulas[coluna].charAt(0);
            }
        }

        return labirinto;
    }

    // Procura no labirinto a primeira posição que contém o símbolo informado (usado para achar S e E)
    static Posicao encontrarSimbolo(char[][] labirinto, char simbolo) {
        for (int linha = 0; linha < labirinto.length; linha++) {
            for (int coluna = 0; coluna < labirinto[linha].length; coluna++) {
                if (labirinto[linha][coluna] == simbolo) {
                    return new Posicao(linha, coluna);
                }
            }
        }

        // se chegou aqui é porque não tem o símbolo no labirinto
        return null;
    }

    // Monta uma lista com todos os tesouros do labirinto, lendo linha por linha
    static List<Posicao> listarTesouros(char[][] labirinto) {
        List<Posicao> tesouros = new ArrayList<>();

        for (int linha = 0; linha < labirinto.length; linha++) {
            for (int coluna = 0; coluna < labirinto[linha].length; coluna++) {
                if (labirinto[linha][coluna] == 'T') {
                    tesouros.add(new Posicao(linha, coluna));
                }
            }
        }

        // System.out.println("qtd tesouros: " + tesouros.size());
        return tesouros;
    }

    // Verifica se a posição está dentro do labirinto e não é parede
    // OBS: tem que checar a linha e a coluna ANTES de acessar a matriz senão dá ArrayIndexOutOfBounds
    static boolean posicaoLivre(char[][] labirinto, int linha, int coluna) {
        if (linha < 0 || linha >= labirinto.length) {
            return false;
        }

        if (coluna < 0 || coluna >= labirinto[linha].length) {
            return false;
        }

        if (labirinto[linha][coluna] == '#') {
            return false;
        }

        return true;
    }

    // Verifica se uma posição está dentro de uma lista de posições
    // (o contains da lista não funcionava porque não comparava linha e coluna)
    static boolean listaContem(List<Posicao> lista, Posicao posicao) {
        for (Posicao item : lista) {
            if (item.igual(posicao)) {
                return true;
            }
        }

        return false;
    }

    // Remove uma posição da lista e devolve true se ela estava na lista
    static boolean removerDaLista(List<Posicao> lista, Posicao posicao) {
        for (int indice = 0; indice < lista.size(); indice++) {
            if (lista.get(indice).igual(posicao)) {
                lista.remove(indice);
                return true;
            }
        }

        return false;
    }

    // Gera os espaços de recuo do modo rastreio, de acordo com o nível da recursão
    static String recuo(int nivel) {
        String espacos = "";

        for (int contador = 0; contador < nivel; contador++) {
            espacos = espacos + "  ";
        }

        return espacos;
    }

    // Mostra a matriz de visitados (usei pra achar o erro do backtracking, deixei aqui caso precise de novo)
    static void imprimirVisitados() {
        for (int linha = 0; linha < visitado.length; linha++) {
            String textoDaLinha = "";

            for (int coluna = 0; coluna < visitado[linha].length; coluna++) {
                if (visitado[linha][coluna]) {
                    textoDaLinha = textoDaLinha + " X";
                } else {
                    textoDaLinha = textoDaLinha + " .";
                }
            }

            System.out.println(textoDaLinha);
        }
    }

    // Prepara as variáveis e chama a busca recursiva para achar o menor caminho entre origem e destino
    // Devolve o caminho (com origem e destino incluídos) ou null se não existir caminho
    static List<Posicao> menorCaminho(char[][] labirinto, Posicao origem, Posicao destino) {
        melhorCaminho = null;
        visitado = new boolean[labirinto.length][labirinto[0].length];
        List<Posicao> caminhoAtual = new ArrayList<>();

        if (modoRastreio) {
            System.out.println("   >> Buscando menor caminho de " + origem + " até " + destino);
            contadorChamadas = 0;
        }

        buscarRecursivo(labirinto, origem.linha, origem.coluna, destino, caminhoAtual, 0, "inicio");

        return melhorCaminho;
    }

    // Função recursiva: tenta andar a partir da posição (linha, coluna) até o destino
    // Marca a casa como visitada, chama a si mesma para as 4 direções e depois desmarca a casa (backtracking)
    static void buscarRecursivo(char[][] labirinto, int linha, int coluna, Posicao destino,
                                List<Posicao> caminhoAtual, int nivel, String direcaoDeOrigem) {

        // Caso base 1: posição fora do labirinto, parede ou casa já visitada neste caminho
        if (!posicaoLivre(labirinto, linha, coluna)) {
            return;
        }

        if (visitado[linha][coluna]) {
            return;
        }

        // Caso base 2 (poda): o caminho atual já não pode ser menor que o melhor encontrado
        // sem essa parte ele testava TODOS os caminhos e demorava muito no labirinto maior
        if (melhorCaminho != null && caminhoAtual.size() + 1 >= melhorCaminho.size()) {
            return;
        }

        // Mostra a chamada atual quando o modo rastreio está ligado
        if (modoRastreio) {
            contadorChamadas++;
            System.out.println("   " + recuo(nivel) + "#" + contadorChamadas
                    + " buscar(" + linha + "," + coluna + ") via " + direcaoDeOrigem);
        }

        // Entra na casa: marca como visitada e adiciona ao caminho atual
        visitado[linha][coluna] = true;
        caminhoAtual.add(new Posicao(linha, coluna));
        // System.out.println("entrou em " + linha + "," + coluna);

        // Caso base 3: chegou ao destino, então guarda uma cópia deste caminho como o melhor
        // (tem que ser cópia! se guardar a mesma lista ela é apagada no backtracking)
        if (linha == destino.linha && coluna == destino.coluna) {
            melhorCaminho = new ArrayList<>(caminhoAtual);
            // melhorCaminho = caminhoAtual;   <- versão errada, voltava lista vazia

            if (modoRastreio) {
                System.out.println("   " + recuo(nivel) + "   ✔ destino alcançado com "
                        + (caminhoAtual.size() - 1) + " passos (novo melhor)");
            }
        }

        // Passo recursivo: tenta andar para cima, baixo, esquerda e direita
        else {
            for (int direcao = 0; direcao < 4; direcao++) {
                int proximaLinha = linha + deslocamentoLinha[direcao];
                int proximaColuna = coluna + deslocamentoColuna[direcao];

                buscarRecursivo(labirinto, proximaLinha, proximaColuna, destino,
                        caminhoAtual, nivel + 1, nomeDirecao[direcao]);
            }

            // versão antiga, antes de usar os vetores de direção:
            // buscarRecursivo(labirinto, linha - 1, coluna, destino, caminhoAtual, nivel + 1, "cima");
            // buscarRecursivo(labirinto, linha + 1, coluna, destino, caminhoAtual, nivel + 1, "baixo");
            // buscarRecursivo(labirinto, linha, coluna - 1, destino, caminhoAtual, nivel + 1, "esquerda");
            // buscarRecursivo(labirinto, linha, coluna + 1, destino, caminhoAtual, nivel + 1, "direita");
        }

        // Backtracking: sai da casa para que outros caminhos possam passar por ela
        // (esqueci disso na primeira vez e ele só achava um caminho e parava)
        caminhoAtual.remove(caminhoAtual.size() - 1);
        visitado[linha][coluna] = false;
    }

    // Encontra a saída do labirinto coletando os tesouros com a estratégia gulosa
    // Estratégia gulosa: sempre ir até o tesouro mais próximo da posição atual
    static void acharCaminho(char[][] labirinto) {
        Posicao inicio = encontrarSimbolo(labirinto, 'S');
        Posicao saida = encontrarSimbolo(labirinto, 'E');

        // Verifica se o labirinto tem início e saída
        if (inicio == null || saida == null) {
            System.out.println("Não há saída possível!");
            return;
        }

        // Verifica se existe algum caminho do início até a saída
        if (menorCaminho(labirinto, inicio, saida) == null) {
            System.out.println("Não há saída possível!");
            return;
        }

        List<Posicao> tesourosPendentes = listarTesouros(labirinto);
        List<Posicao> tesourosColetados = new ArrayList<>();
        List<Posicao> rotaCompleta = new ArrayList<>();

        rotaCompleta.add(inicio);
        Posicao posicaoAtual = inicio;
        boolean aindaTemTesouro = true;

        // Laço guloso: repete enquanto houver tesouro alcançável
        while (aindaTemTesouro) {
            Posicao tesouroEscolhido = null;
            List<Posicao> caminhoEscolhido = null;

            if (modoRastreio) {
                System.out.println("\n[GULOSO] Herói em " + posicaoAtual + ". Avaliando tesouros:");
            }

            // Calcula a distância até cada tesouro e guarda o mais próximo
            for (Posicao tesouro : tesourosPendentes) {
                List<Posicao> caminho = menorCaminho(labirinto, posicaoAtual, tesouro);

                if (modoRastreio) {
                    if (caminho == null) {
                        System.out.println("   - Tesouro " + tesouro + ": inalcançável");
                    } else {
                        System.out.println("   - Tesouro " + tesouro + ": " + (caminho.size() - 1) + " passos");
                    }
                }

                // tesouro inalcançável (caminho null) é ignorado, senão dava NullPointerException
                if (caminho != null) {
                    if (caminhoEscolhido == null || caminho.size() < caminhoEscolhido.size()) {
                        tesouroEscolhido = tesouro;
                        caminhoEscolhido = caminho;
                    }
                }
            }

            // Se nenhum tesouro é alcançável, encerra o laço
            if (tesouroEscolhido == null) {
                aindaTemTesouro = false;
            }

            // Senão, anda até o tesouro escolhido, coletando qualquer tesouro que estiver no caminho
            else {
                if (modoRastreio) {
                    System.out.println("   => Escolha gulosa: tesouro " + tesouroEscolhido);
                }

                // começa do 1 porque o 0 é a posição onde o herói já está (senão repetia no caminho)
                for (int indice = 1; indice < caminhoEscolhido.size(); indice++) {
                    Posicao passo = caminhoEscolhido.get(indice);
                    rotaCompleta.add(passo);

                    if (removerDaLista(tesourosPendentes, passo)) {
                        tesourosColetados.add(passo);
                    }
                }

                posicaoAtual = tesouroEscolhido;
            }
        }

        // Sem tesouros restantes: vai pelo menor caminho até a saída
        if (modoRastreio) {
            System.out.println("\n[GULOSO] Sem tesouros pendentes. Indo para a saída " + saida);
        }

        List<Posicao> caminhoAteSaida = menorCaminho(labirinto, posicaoAtual, saida);

        for (int indice = 1; indice < caminhoAteSaida.size(); indice++) {
            rotaCompleta.add(caminhoAteSaida.get(indice));
        }

        imprimirResultado(rotaCompleta, tesourosColetados);
    }

    // Imprime o caminho no formato do enunciado: S → (0,1) → ... → (2,4) → T → ... → E
    static void imprimirResultado(List<Posicao> rotaCompleta, List<Posicao> tesourosColetados) {
        String texto = "Caminho percorrido: S";
        List<Posicao> tesourosJaMarcados = new ArrayList<>();

        for (int indice = 1; indice < rotaCompleta.size(); indice++) {
            Posicao passo = rotaCompleta.get(indice);

            // Último passo da rota é a saída
            if (indice == rotaCompleta.size() - 1) {
                texto = texto + " → E";
            }

            // Demais passos: escreve a posição e marca "T" na primeira vez que passa por um tesouro coletado
            // (o herói volta por (4,3) e (4,4) no labirinto do enunciado, por isso o controle de já marcados)
            else {
                texto = texto + " → " + passo;

                if (listaContem(tesourosColetados, passo) && !listaContem(tesourosJaMarcados, passo)) {
                    texto = texto + " → T";
                    tesourosJaMarcados.add(passo);
                }
            }
        }

        System.out.println(texto);
        System.out.println("Tesouros coletados: " + tesourosColetados.size());
    }

    // Mostra a matriz do labirinto na tela
    static void imprimirLabirinto(char[][] labirinto) {
        for (int linha = 0; linha < labirinto.length; linha++) {
            String textoDaLinha = "  ";

            for (int coluna = 0; coluna < labirinto[linha].length; coluna++) {
                textoDaLinha = textoDaLinha + " " + labirinto[linha][coluna];
            }

            System.out.println(textoDaLinha);
        }
    }

    // Programa principal: monta os labirintos dos casos de teste e executa cada um
    // Use "java LabirintoSalesiania --rastreio" para ver as chamadas recursivas e as decisões gulosas
    public static void main(String[] args) {

        // Liga o modo rastreio se o argumento --rastreio for informado
        if (args.length > 0 && args[0].equals("--rastreio")) {
            modoRastreio = true;
        }

        // Labirinto do enunciado (usado no teste de mesa)
        char[][] labirintoEnunciado = criarLabirinto(
                "S . . # . .",
                "# # . # . #",
                ". . . . T .",
                ". # # # . #",
                ". . T . . E",
                "# # # # # #"
        );

        // Caso 1: caminho direto para a saída, sem tesouros
        // (no labirinto do enunciado o (3,3) é parede, então abri ele pra bater com a saída esperada)
        char[][] labirintoCaso1 = criarLabirinto(
                "S . . # . .",
                "# # . # . #",
                ". . . . # .",
                ". # # . # #",
                ". . . . . E",
                "# # # # # #"
        );

        // Caso 2: vários tesouros, com a saída no canto inferior esquerdo
        // (precisou de 7 linhas porque a saída esperada passa por (5,0) e termina no E abaixo dele)
        char[][] labirintoCaso2 = criarLabirinto(
                "S . . # . .",
                "# # . # . #",
                ". . . . T .",
                ". # # # . #",
                ". . T . . .",
                ". # # # # #",
                "E # # # # #"
        );

        // Caso 3: saída cercada de paredes
        char[][] labirintoCaso3 = criarLabirinto(
                "S . . # . .",
                "# # . # . #",
                ". . . . T .",
                ". # # # . #",
                ". . T . # #",
                "# # # # # E"
        );

        // TODO bônus: armadilhas (casa 'A' que faz perder turno) se der tempo

        // Execução de cada labirinto
        System.out.println("=== Labirinto do enunciado ===");
        imprimirLabirinto(labirintoEnunciado);
        acharCaminho(labirintoEnunciado);

        System.out.println("\n=== 1) Caminho direto para a saída ===");
        imprimirLabirinto(labirintoCaso1);
        acharCaminho(labirintoCaso1);

        System.out.println("\n=== 2) Múltiplos tesouros (rota gulosa) ===");
        imprimirLabirinto(labirintoCaso2);
        acharCaminho(labirintoCaso2);

        System.out.println("\n=== 3) Sem saída possível ===");
        imprimirLabirinto(labirintoCaso3);
        acharCaminho(labirintoCaso3);
    }
}
