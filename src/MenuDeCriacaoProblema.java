import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import problema.Caminho;
import problema.PontoDeSalto;

import java.util.*;

public class MenuDeCriacaoProblema {

    private static final String MENSAGEM_FORMATO_INVALIDO_DOUBLE = "\nFormato inválido: a entrada deve conter apenas números no formato 0.00.\n";
    private static final String MENSAGEM_FORMATO_INVALIDO_STRING = "\nFormato inválido: a entrada deve conter apenas letras\n";
    private static final String MENSAGEM_FORMATO_INVALIDO_GENERICO = "\nFormato de entrada inválido.\n";
    private static final String ANSI_BLUE = "\033[34m";
    private static final String ANSI_RED = "\033[31m";
    private static final String ANSI_RESET = "\033[0m";

    private static final Scanner sc = new Scanner(System.in);
    private static Grafo grafo;
    static int indexNodos = 1;
    static int indexMapCaminhos = 1;
    private static int numNodos = 0;
    private static Map<Integer, PontoDeSalto> pontosDeSaltoMap = new HashMap<>();
    private static Map<Integer, Caminho> caminhosMap = new HashMap<>();

    public static void criarGrafo() {
        grafo = new Grafo(numNodos, false);

        boolean running = true;
        while (running) {
            System.out.println("\nMenu:");

            System.out.println("1. Adicionar Mapa por arquivo.txt");
            System.out.println("2. Visualizar Informações do Mapa");
            System.out.println("3. Limpar Rota");
            System.out.println("4. Ver o desenho do Mapa");
            System.out.println("5. Visualizar melhor caminho por Dijkstra");
            System.out.println("6. Visualizar melhor caminho por Profundidade");
            System.out.println("7. Visualizar melhor caminho por Largura");
            System.out.println("8. Visualizar melhor caminho por Floyd");
            System.out.println("9. Visualizar melhor caminho por Ford");
            System.out.println("10. Sair");

            System.out.print("\nEscolha uma opção: ");
            int opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    limparGrafo();
                    String nomeDoArquivo = lerAPartirDeUmArquivo();
                    grafo = LeitorArquivo.lerGrafoDeArquivoTxt(nomeDoArquivo);
                    pontosDeSaltoMap = LeitorArquivo.getPontosDeSaltoMap();
                    caminhosMap = LeitorArquivo.getCaminhosMap();
                    indexNodos = pontosDeSaltoMap.size() + 1;
                    indexMapCaminhos = caminhosMap.size() + 1;
                    break;
                case 2:
                    visualizarDadosDoGrafo();
                    break;
                case 3:
                    limparGrafo();
                    System.out.println("\nO Grafo de Pontos de Salto foi limpo..");
                    break;
                case 4:
                    visualizarMapa();
                    break;
                case 5: {
                    long startTime = System.nanoTime();
                    encontrarMelhorCaminho();
                    long endTime = System.nanoTime();
                    System.out.printf("\nTempo de execução (Dijkstra): %.3f ms%n", (endTime - startTime) / 1e6);
                    break;
                }
                case 6: {
                    long startTime = System.nanoTime();
                    encontrarMelhorCaminhoProfundidade();
                    long endTime = System.nanoTime();
                    System.out.printf("\nTempo de execução (Profundidade): %.3f ms%n", (endTime - startTime) / 1e6);
                    break;
                }
                case 7: {
                    long startTime = System.nanoTime();
                    encontrarMelhorCaminhoLargura();
                    long endTime = System.nanoTime();
                    System.out.printf("\nTempo de execução (Largura): %.3f ms%n", (endTime - startTime) / 1e6);
                    break;
                }
                case 8: {
                    long startTime = System.nanoTime();
                    encontrarMelhorCaminhoFloyd();
                    long endTime = System.nanoTime();
                    System.out.printf("\nTempo de execução (Floyd): %.3f ms%n", (endTime - startTime) / 1e6);
                    break;
                }
                case 9: {
                    long startTime = System.nanoTime();
                    System.out.println("Este algoritmo não é compatível com o sistema utilizado, pois é necessário que o grafo seja direcionado. Nosso sistema utiliza somente grafos não direcionados.");
                    long endTime = System.nanoTime();
                    System.out.printf("\nTempo de execução (Ford): %.3f ms%n", (endTime - startTime) / 1e6);
                    break;
                }
                case 10:
                    limparGrafo();
                    running = false;
                    System.out.println("\nEncerrando...");
                    break;
                default:
                    System.out.println(ANSI_RED + "\nOpção inválida." + ANSI_RESET);
            }
        }
    }


    public static void adicionarPontoDeSaltoDinamicamente() {
        String nome;
        double fatorDeSegurancaNumero;

        while (true) {
            sc.nextLine(); //Limpeza do Buffer

            System.out.print("Digite o nome do Ponto de Salto ou 'x' para sair: ");
            nome = sc.nextLine().trim();

            if(nome.isEmpty()){
                System.out.println( ANSI_RED + "\nÉ necessário possuir um caractere ao menos no nome do Ponto de Salto.\n" + ANSI_RESET);
                break;
            }

            if (nome.equalsIgnoreCase("x")) {
                System.out.println("\nParando a inserção de Pontos de Salto...\n");
                break;
            }

            while (true) {
                System.out.print("Digite a porcentagem de segurança [0 a 100, Sendo 0 Inseguro e 100 Muito Seguro]: ");
                String fatorDeSeguranca = sc.next();

                if (fatorDeSeguranca.equalsIgnoreCase("x")) {
                    System.out.println("\nParando a inserção de Pontos de Salto...\n");
                    return;
                }

                try {
                    fatorDeSegurancaNumero = Double.parseDouble(fatorDeSeguranca);
                    if (fatorDeSegurancaNumero < 0 || fatorDeSegurancaNumero > 100) {
                        System.out.println( ANSI_RED + "\nO fator de segurança deve estar entre 0 e 100.\n" + ANSI_RESET);
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println(MENSAGEM_FORMATO_INVALIDO_DOUBLE);
                }
            }

            PontoDeSalto pontoDeSaltoObjeto = new PontoDeSalto(nome, fatorDeSegurancaNumero);
            pontosDeSaltoMap.put(indexNodos++, pontoDeSaltoObjeto);
            numNodos = pontosDeSaltoMap.size();
            grafo.adicionarNodo(pontoDeSaltoObjeto);

            System.out.println(ANSI_BLUE + "\nPonto de Salto adicionado com sucesso!\n" + ANSI_RESET);
        }
    }


    private static void adicionarCaminhosDinamicamente() {
        if (grafo.getNumNodos() <= 1) {
            System.err.println(ANSI_RED + "\nVocê possui: [" + grafo.getNumNodos() + "] Pontos de Salto."
                    + "\nÉ necessário adicionar mais Pontos de Salto antes de prosseguir com a adição de Caminhos." + ANSI_RESET);
            return;
        }

        int nodo1 = 0;
        int nodo2 = 0;

        listarPontosDeSalto();

        while (true) {
            try {
                sc.nextLine(); //Limpeza do Buffer
                String parsecs = "";
                while (true) {
                    try {
                        System.out.print("Digite a distância em Parsecs [3 mil anos luz UN.] ou 'x' para sair: ");
                        parsecs = sc.nextLine();

                        if (parsecs.equalsIgnoreCase("x")) {
                            System.out.println("\nParando a inserção de Caminhos...\n");
                            return;
                        }

                            try {
                                int parsecsNumero = Integer.parseInt(parsecs);
                                if (parsecsNumero < 1 || parsecsNumero > 100) {
                                    System.out.println( ANSI_RED + "\nA distância deve ser de no máximo 100 Parsecs.\n" + ANSI_RESET);
                                } else {
                                    break;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println(MENSAGEM_FORMATO_INVALIDO_DOUBLE);
                            }
                    } catch (InputMismatchException e){
                        System.out.println(MENSAGEM_FORMATO_INVALIDO_STRING);
                        sc.nextLine();
                    }
                }

                System.out.print("Digite o caminho entre os Pontos de Salto (formato: Ponto Inicial-Ponto Final) ou 'x' para sair: ");
                String aresta = sc.next();

                if (aresta.equalsIgnoreCase("x")) {
                    System.out.println("\nParando a inserção de Caminhos...\n");
                    break;
                }

                String[] partes = aresta.split("-", 2);
                if (partes.length != 2) {
                    System.out.println(ANSI_RED + "\nFormato inválido! Use o formato Ponto Inicial-Ponto Final.\n" + ANSI_RESET);
                    continue;
                }

                String pontoInicial = partes[0].trim();
                String pontoFinal = partes[1].trim();

                try {
                    nodo1 = Integer.parseInt(pontoInicial);
                    nodo2 = Integer.parseInt(pontoFinal);

                    if (!pontosDeSaltoMap.containsKey(nodo1) || !pontosDeSaltoMap.containsKey(nodo2)) {
                        System.out.println(ANSI_RED + "\nUm ou ambos os Pontos de Salto não existem.\n" + ANSI_RESET);
                        continue;
                    }

                } catch (NumberFormatException e) {
                    System.out.println(MENSAGEM_FORMATO_INVALIDO_DOUBLE);
                    continue;
                }

                Caminho caminho = new Caminho(Integer.parseInt(parsecs), nodo1, nodo2);
                caminhosMap.put(indexMapCaminhos++, caminho);
                grafo.adicionarAresta(nodo1, nodo2, Integer.parseInt(parsecs));

                System.out.println(ANSI_BLUE + "\nCaminho adicionada com sucesso.\n" + ANSI_RESET);

            } catch (IllegalArgumentException e) {
                System.out.println(MENSAGEM_FORMATO_INVALIDO_GENERICO);
            }
        }
    }

    private static void removerCaminho() {
        while (true) {
            if (grafo.getNumNodos() <= 1) {
                System.out.println("\nVocê possui: [" + grafo.getNumNodos() + "] nodos."
                        + "\nNão é possível remover Caminhos com menos de dois Pontos de Salto.");
                break;
            }

            System.out.print("Digite o Caminho a ser removido (formato: PontoDeSalto-PontoDeSalto) ou 'x' para parar: ");
            String caminhoCompleto = sc.nextLine();

            if (caminhoCompleto.equalsIgnoreCase("x")) {
                System.out.println("\nParando a remoção de Caminhos...\n");
                break;
            }

            String[] partes = caminhoCompleto.split("-", 2);
            if (partes.length != 2) {
                System.out.println(ANSI_RED + "\nFormato inválido! Use o formato Ponto Inicial-Ponto Final.\n" + ANSI_RESET);
                continue;
            }

            String pontoInicial = partes[0].trim();
            String pontoFinal = partes[1].trim();

            try {
                int nodo1 = Integer.parseInt(pontoInicial);
                int nodo2 = Integer.parseInt(pontoFinal);

                caminhosMap.entrySet().removeIf(caminho -> caminho.getValue().getPontoInicial() == nodo1 && caminho.getValue().getPontoFinal() == nodo2);

                grafo.removerAresta(nodo1, nodo2);
                System.out.println("Caminho removido com sucesso.");

            } catch (NumberFormatException e) {
                System.out.println(MENSAGEM_FORMATO_INVALIDO_GENERICO);
            }
        }
        Map<Integer, Caminho> novoMapa = new HashMap<>();
        int novoIndice = 1;

        for (int chave : caminhosMap.keySet()) {
            novoMapa.put(novoIndice++, caminhosMap.get(chave));
        }

        caminhosMap = novoMapa;
    }

    private static void removerPontoDeSalto() {
        while (true) {
            System.out.print("Digite o número do Ponto de Salto a ser removido ou 'x' para parar: ");
            String input = sc.next();

            if (input.equalsIgnoreCase("x")) {
                System.out.println("\nParando a remoção de Pontos de Salto...\n");
                break;
            }

            try {
                int nodo = Integer.parseInt(input);

                pontosDeSaltoMap.remove(nodo);
                caminhosMap.entrySet().removeIf(caminho -> caminho.getValue().getPontoInicial() == nodo || caminho.getValue().getPontoFinal() == nodo);
                grafo.removerNodo(nodo);

            } catch (NumberFormatException e) {
                System.out.println(MENSAGEM_FORMATO_INVALIDO_GENERICO);
            }
        }

        Map<Integer, PontoDeSalto> novoMapa = new HashMap<>();
        int novoIndice = 1;

        for (int chave : pontosDeSaltoMap.keySet()) {
            novoMapa.put(novoIndice++, pontosDeSaltoMap.get(chave));
        }

        pontosDeSaltoMap = novoMapa;
    }

    private static void listarPontosDeSalto(){
        for (Map.Entry<Integer, PontoDeSalto> ponto : pontosDeSaltoMap.entrySet()) {
            System.out.println(ANSI_BLUE + " | " + ponto.getKey() + " | " + ponto.getValue().getNome() + " | Fator de SEG: " + ponto.getValue().getFatorDeSeguranca() + ANSI_RESET);
        }
        System.out.println("\n");
    }

    private static void listarCaminhos(){
        for (Map.Entry<Integer, Caminho> caminho : caminhosMap.entrySet()) {
            System.out.println(ANSI_BLUE + " | " + caminho.getKey() + " | " + caminho.getValue().getPontoInicial() + " - " + caminho.getValue().getPontoFinal()+ " | Parsecs: " + caminho.getValue().getParsec() + ANSI_RESET);
        }
        System.out.println("\n");
    }

    private static void encontrarMelhorCaminho(){
        while (true) {
            sc.nextLine(); //Limpeza do Buffer
            System.out.print("Digite o caminho [Ponto de Partida-Ponto de Chegada]: ");
            String caminho = sc.nextLine();

            System.out.print("Fator de segurança aceitável [Somente números 0 a 100]:  ");
            String fatorSeguranca = sc.nextLine();

            String[] partes = caminho.split("-", 2);
            if (partes.length != 2) {
                System.out.println(ANSI_RED + "\nFormato inválido! Use o formato Ponto Inicial - Ponto Final.\n" + ANSI_RESET);
                continue;
            }

            int pontoDePartida = 0;
            int pontoDeChegada = 0;
            int fatorDeSegurancaInt = 0;

            try {
                pontoDePartida = Integer.parseInt(partes[0].trim());
                pontoDeChegada = Integer.parseInt(partes[1].trim());

                if (!pontosDeSaltoMap.containsKey(pontoDePartida) || !pontosDeSaltoMap.containsKey(pontoDeChegada)) {
                    System.out.println(ANSI_RED + "\nUm ou ambos os Pontos de Salto não existem.\n" + ANSI_RESET);
                    break;
                }

                fatorDeSegurancaInt = Integer.parseInt(fatorSeguranca);

                if(fatorDeSegurancaInt <= 0 || fatorDeSegurancaInt > 100){
                    System.out.println(ANSI_RED + "\nO fator de segurança deve estar entre 0 e 100%.\n" + ANSI_RESET);
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println(MENSAGEM_FORMATO_INVALIDO_DOUBLE);
            }
            System.out.println(ANSI_BLUE + "\nPonto de Partida: [" + pontoDePartida + "] " + pontosDeSaltoMap.get(pontoDePartida).getNome() + "\n" + "Ponto de Chegada: [" + pontoDeChegada + "] " + pontosDeSaltoMap.get(pontoDeChegada).getNome() + ANSI_RESET + "\n");

            grafo.algoritmoDijkstra(pontoDePartida, pontoDeChegada, fatorDeSegurancaInt);
            System.out.println("\n");
            break;
        }
    }

    private static void encontrarMelhorCaminhoProfundidade(){
        while (true) {
            sc.nextLine(); //Limpeza do Buffer
            System.out.print("Digite o caminho [Ponto de Partida-Ponto de Chegada]: ");
            String caminho = sc.nextLine();

            System.out.print("Fator de segurança aceitável [Somente números 0 a 100]:  ");
            String fatorSeguranca = sc.nextLine();

            String[] partes = caminho.split("-", 2);
            if (partes.length != 2) {
                System.out.println(ANSI_RED + "\nFormato inválido! Use o formato Ponto Inicial - Ponto Final.\n" + ANSI_RESET);
                continue;
            }

            int pontoDePartida = 0;
            int pontoDeChegada = 0;
            int fatorDeSegurancaInt = 0;

            try {
                pontoDePartida = Integer.parseInt(partes[0].trim());
                pontoDeChegada = Integer.parseInt(partes[1].trim());

                if (!pontosDeSaltoMap.containsKey(pontoDePartida) || !pontosDeSaltoMap.containsKey(pontoDeChegada)) {
                    System.out.println(ANSI_RED + "\nUm ou ambos os Pontos de Salto não existem.\n" + ANSI_RESET);
                    break;
                }

                fatorDeSegurancaInt = Integer.parseInt(fatorSeguranca);

                if(fatorDeSegurancaInt <= 0 || fatorDeSegurancaInt > 100){
                    System.out.println(ANSI_RED + "\nO fator de segurança deve estar entre 0 e 100%.\n" + ANSI_RESET);
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println(MENSAGEM_FORMATO_INVALIDO_DOUBLE);
            }
            System.out.println(ANSI_BLUE + "\nPonto de Partida: [" + pontoDePartida + "] " + pontosDeSaltoMap.get(pontoDePartida).getNome() + "\n" + "Ponto de Chegada: [" + pontoDeChegada + "] " + pontosDeSaltoMap.get(pontoDeChegada).getNome() + ANSI_RESET + "\n");

            grafo.algoritmoBF(pontoDePartida, pontoDeChegada, fatorDeSegurancaInt);
            System.out.println("\n");
            break;
        }
    }

    private static void encontrarMelhorCaminhoLargura(){
        while (true) {
            sc.nextLine(); //Limpeza do Buffer
            System.out.print("Digite o caminho [Ponto de Partida-Ponto de Chegada]: ");
            String caminho = sc.nextLine();

            System.out.print("Fator de segurança aceitável [Somente números 0 a 100]:  ");
            String fatorSeguranca = sc.nextLine();

            String[] partes = caminho.split("-", 2);
            if (partes.length != 2) {
                System.out.println(ANSI_RED + "\nFormato inválido! Use o formato Ponto Inicial - Ponto Final.\n" + ANSI_RESET);
                continue;
            }

            int pontoDePartida = 0;
            int pontoDeChegada = 0;
            int fatorDeSegurancaInt = 0;

            try {
                pontoDePartida = Integer.parseInt(partes[0].trim());
                pontoDeChegada = Integer.parseInt(partes[1].trim());

                if (!pontosDeSaltoMap.containsKey(pontoDePartida) || !pontosDeSaltoMap.containsKey(pontoDeChegada)) {
                    System.out.println(ANSI_RED + "\nUm ou ambos os Pontos de Salto não existem.\n" + ANSI_RESET);
                    break;
                }

                fatorDeSegurancaInt = Integer.parseInt(fatorSeguranca);

                if(fatorDeSegurancaInt <= 0 || fatorDeSegurancaInt > 100){
                    System.out.println(ANSI_RED + "\nO fator de segurança deve estar entre 0 e 100%.\n" + ANSI_RESET);
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println(MENSAGEM_FORMATO_INVALIDO_DOUBLE);
            }
            System.out.println(ANSI_BLUE + "\nPonto de Partida: [" + pontoDePartida + "] " + pontosDeSaltoMap.get(pontoDePartida).getNome() + "\n" + "Ponto de Chegada: [" + pontoDeChegada + "] " + pontosDeSaltoMap.get(pontoDeChegada).getNome() + ANSI_RESET + "\n");

            grafo.algoritmoBFS(pontoDePartida, pontoDeChegada, fatorDeSegurancaInt);
            System.out.println("\n");
            break;
        }
    }
    private static void encontrarMelhorCaminhoFloyd(){
        while (true) {
            sc.nextLine(); //Limpeza do Buffer
            System.out.print("Digite o caminho [Ponto de Partida-Ponto de Chegada]: ");
            String caminho = sc.nextLine();

            System.out.print("Fator de segurança aceitável [Somente números 0 a 100]:  ");
            String fatorSeguranca = sc.nextLine();

            String[] partes = caminho.split("-", 2);
            if (partes.length != 2) {
                System.out.println(ANSI_RED + "\nFormato inválido! Use o formato Ponto Inicial - Ponto Final.\n" + ANSI_RESET);
                continue;
            }

            int pontoDePartida = 0;
            int pontoDeChegada = 0;
            int fatorDeSegurancaInt = 0;

            try {
                pontoDePartida = Integer.parseInt(partes[0].trim());
                pontoDeChegada = Integer.parseInt(partes[1].trim());

                if (!pontosDeSaltoMap.containsKey(pontoDePartida) || !pontosDeSaltoMap.containsKey(pontoDeChegada)) {
                    System.out.println(ANSI_RED + "\nUm ou ambos os Pontos de Salto não existem.\n" + ANSI_RESET);
                    break;
                }

                fatorDeSegurancaInt = Integer.parseInt(fatorSeguranca);

                if(fatorDeSegurancaInt <= 0 || fatorDeSegurancaInt > 100){
                    System.out.println(ANSI_RED + "\nO fator de segurança deve estar entre 0 e 100%.\n" + ANSI_RESET);
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println(MENSAGEM_FORMATO_INVALIDO_DOUBLE);
            }
            System.out.println(ANSI_BLUE + "\nPonto de Partida: [" + pontoDePartida + "] " + pontosDeSaltoMap.get(pontoDePartida).getNome() + "\n" + "Ponto de Chegada: [" + pontoDeChegada + "] " + pontosDeSaltoMap.get(pontoDeChegada).getNome() + ANSI_RESET + "\n");

            grafo.algoritmoFloyd(pontoDePartida, pontoDeChegada, fatorDeSegurancaInt);
            System.out.println("\n");
            break;
        }
    }

    public static void limparGrafo() {
        pontosDeSaltoMap.clear();
        caminhosMap.clear();
        List<PontoDeSalto> pontoDeSaltosVazio = new ArrayList<>();
        grafo.setNumNodos(0);
        grafo.setPontosDeSalto(pontoDeSaltosVazio);
        indexNodos = 1;
        indexMapCaminhos = 1;
    }

    public static void visualizarDadosDoGrafo(){
        System.out.println("\n-------------------------------------------------\n");
        System.out.println("\n-------------DADOS DO GRAFO----------------------\n");
        System.out.println("\n-------------------------------------------------\n");
        grafo.imprimirMatrizAdjacencia();
        System.out.println("\n");
        grafo.listarGrauNodos();
        System.out.println("\n");
        System.out.println("\n--------------------------------------------------\n");
        System.out.println("\n--------------- DADOS DO MAPA ----------------\n");
        listarPontosDeSalto();
        listarCaminhos();
        System.out.println("\n-------------------------------------------------\n");
    }

    public static String lerAPartirDeUmArquivo(){
        String caminhoArquivo = "";
        while(true){
            try{
                sc.nextLine();
                System.out.print("Digite o caminho do arquivo .txt: ");
                caminhoArquivo = sc.nextLine();

            } catch(InputMismatchException E) {
                System.out.println(ANSI_RED + MENSAGEM_FORMATO_INVALIDO_STRING + ANSI_RESET);
                continue;
            }
            return caminhoArquivo;
        }
    }
    private static void visualizarMapa() {
        System.setProperty("org.graphstream.ui", "swing");

        Graph graph = new SingleGraph("Grafo Lido");

        for (Map.Entry<Integer, PontoDeSalto> entry : pontosDeSaltoMap.entrySet()) {
            int id = entry.getKey();
            PontoDeSalto ponto = entry.getValue();
            graph.addNode(String.valueOf(id)).setAttribute("ui.label", ponto.getNome());
        }

        for (int i = 1; i < indexMapCaminhos; i++) {
            Caminho caminho = caminhosMap.get(i);
            graph.addEdge(caminho.getPontoInicial() + "-" + caminho.getPontoFinal(),
                            String.valueOf(caminho.getPontoInicial()),
                            String.valueOf(caminho.getPontoFinal()))
                    .setAttribute("ui.label", String.valueOf(caminho.getParsec()));
        }
        graph.display();
    }
}
