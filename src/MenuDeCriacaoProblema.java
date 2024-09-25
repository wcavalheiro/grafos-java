import problema.Caminho;
import problema.PontoDeSalto;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

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
        System.out.println(caminhosMap);
        grafo = new Grafo(numNodos, false);

        boolean running = true;
        while (running) {
            System.out.println("\nMenu:");

            System.out.println("1. Adicionar problema por arquivo .txt");
            System.out.println("2. Adicionar Ponto de Salto");
            System.out.println("3. Adicionar Caminho");
            System.out.println("4. Atualizar Ponto de Salto");
            System.out.println("5. Atualizar Caminho");
            System.out.println("6. Remover Ponto de Salto");
            System.out.println("7. Remover Caminho");
            System.out.println("8. Visualizar Pontos de Salto");
            System.out.println("9. Visualizar Caminhos");
            System.out.println("10. Visualizar Dados do Grafo");
            System.out.println("11. Visualizar Melhor Caminho");
            System.out.println("12. Limpar Rota");
            System.out.println("13. Sair");

            System.out.print("\nEscolha uma opção: ");
            int opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    String nomeDoArquivo = lerAPartirDeUmArquivo();
                    grafo = LeitorArquivo.lerGrafoDeArquivoTxt(nomeDoArquivo);
                    pontosDeSaltoMap = LeitorArquivo.getPontosDeSaltoMap();
                    caminhosMap = LeitorArquivo.getCaminhosMap();
                    break;
                case 2:
                    adicionarPontoDeSaltoDinamicamente();
                    break;
                case 3:
                    adicionarCaminhosDinamicamente();
                    break;
                case 4:
                    // atualizarPontoDeSalto(); // Implementar método para atualizar ponto de salto
                    break;
                case 5:
                    // atualizarCaminho(); // Implementar método para atualizar caminho
                    break;
                case 6:
                    removerNodo();
                    break;
                case 7:
                    removerAresta();
                    break;
                case 8:
                    listarPontosDeSalto();
                    grafo.imprimirMatrizAdjacencia();
                    grafo.listarGrauNodos();
                    break;
                case 9:
                    listarCaminhos();
                    break;
                case 10:
                    visualizarDadosDoGrafo();
                    break;
                case 11:
                    encontrarMelhorCaminho();
                    break;
                case 12:
                    limparGrafo();
                    System.out.println("\nO Grafo de Pontos de Salto foi limpo..");
                    break;
                case 13:
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
                System.out.print("Digite a porcentagem de segurança [0 a 100, de inseguro a muito seguro]: ");
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
            grafo.setNumNodos(numNodos);

            System.out.println(ANSI_BLUE + "\nPonto de Salto adicionado com sucesso!\n" + ANSI_RESET);
        }
    }

    private static void adicionarCaminhosDinamicamente() {
        if (grafo.getNumNodos() <= 1) {
            System.err.println(ANSI_RED + "\nVocê possui: [" + grafo.getNumNodos() + "] Pontos de Salto."
                    + "\nÉ necessário adicionar mais Pontos de Salto antes de prosseguir com a adição de Caminhos." + ANSI_RESET);
            return;
        }

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
                            System.out.println("\nParando a inserção de arestas...\n");
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
                    System.out.println("\nParando a inserção de arestas...\n");
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
                    int nodo1 = Integer.parseInt(pontoInicial);
                    int nodo2 = Integer.parseInt(pontoFinal);

                    if (!pontosDeSaltoMap.containsKey(nodo1) || !pontosDeSaltoMap.containsKey(nodo2)) {
                        System.out.println(ANSI_RED + "\nUm ou ambos os Pontos de Salto não existem.\n" + ANSI_RESET);
                        continue;
                    }

                    Caminho caminho = new Caminho(Integer.parseInt(parsecs), nodo1, nodo2);
                    caminhosMap.put(indexMapCaminhos++, caminho);
                    grafo.adicionarAresta(nodo1, nodo2, Integer.parseInt(parsecs));
                } catch (NumberFormatException e) {
                    System.out.println(MENSAGEM_FORMATO_INVALIDO_DOUBLE);
                    continue;
                }

                System.out.println(ANSI_BLUE + "\nCaminho adicionada com sucesso.\n" + ANSI_RESET);

            } catch (IllegalArgumentException e) {
                System.out.println(MENSAGEM_FORMATO_INVALIDO_GENERICO);
            }
        }
    }

    private static void removerAresta() {
        while (true) {
            if (grafo.getNumNodos() <= 1) {
                System.out.println("\nVocê possui: [" + grafo.getNumNodos() + "] nodos."
                        + "\nNão é possível remover arestas com menos de dois nodos.");
                break;
            }

            System.out.print("Digite a aresta a ser removida (formato: nodo1 nodo2) ou 'x x' para parar: ");
            String input1 = sc.next();
            String input2 = sc.next();

            if (input1.equalsIgnoreCase("x") || input2.equalsIgnoreCase("x")) {
                System.out.println("\nParando a remoção de arestas...\n");
                break;
            }

            try {
                int nodo1 = Integer.parseInt(input1);
                int nodo2 = Integer.parseInt(input2);
                grafo.removerAresta(nodo1, nodo2);
                System.out.println("Aresta removida com sucesso.");

            } catch (NumberFormatException e) {
                System.out.println(MENSAGEM_FORMATO_INVALIDO_GENERICO);
            }
        }
    }

    private static void removerNodo() {
        while (true) {
            System.out.print("Digite o número do nodo a ser removido ou 'x' para parar: ");
            String input = sc.next();

            if (input.equalsIgnoreCase("x")) {
                System.out.println("\nParando a remoção de nodos...\n");
                break;
            }

            try {
                int nodo = Integer.parseInt(input);
                grafo.removerNodo(nodo);
                grafo.imprimirMatrizAdjacencia();

            } catch (NumberFormatException e) {
                System.out.println(MENSAGEM_FORMATO_INVALIDO_GENERICO);
            }
        }
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
            System.out.print("Digite o caminho [Ponto de Partida - Ponto de Chegada ]: ");
            String caminho = sc.nextLine();

            String[] partes = caminho.split("-", 2);
            if (partes.length != 2) {
                System.out.println(ANSI_RED + "\nFormato inválido! Use o formato Ponto Inicial - Ponto Final.\n" + ANSI_RESET);
                continue;
            }

            int pontoDePartida = 0;
            int pontoDeChegada = 0;

            try {
                pontoDePartida = Integer.parseInt(partes[0].trim());
                pontoDeChegada = Integer.parseInt(partes[1].trim());

                if (!pontosDeSaltoMap.containsKey(pontoDePartida) || !pontosDeSaltoMap.containsKey(pontoDeChegada)) {
                    System.out.println(ANSI_RED + "\nUm ou ambos os Pontos de Salto não existem.\n" + ANSI_RESET);
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println(MENSAGEM_FORMATO_INVALIDO_DOUBLE);
            }
            System.out.println(ANSI_BLUE + "\nPonto de Partida: [" + pontoDePartida + "] " + pontosDeSaltoMap.get(pontoDePartida).getNome() + " - " + " Ponto de Chegada: [" + pontoDeChegada + "] " + pontosDeSaltoMap.get(pontoDeChegada).getNome() + ANSI_RESET);

            grafo.calculaMelhorCaminho(pontoDePartida, pontoDeChegada);
        }

    }

    public static void limparGrafo() {
        pontosDeSaltoMap.clear();
        caminhosMap.clear();
        grafo.setNumNodos(0);
        numNodos = 0;
    }

    public static void visualizarDadosDoGrafo(){
        System.out.println("\n-------------------------------------------------\n");
        grafo.imprimirMatrizAdjacencia();
        grafo.listarGrauNodos();
        System.out.println("\n--------------------------------------------------\n");
        System.out.println("\n--------------- DADOS DO PROBLEMA ----------------\n");
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

                Grafo grafoDoArquivo = LeitorArquivo.lerGrafoDeArquivoTxt(caminhoArquivo);

            } catch(InputMismatchException E) {
                System.out.println(E.getMessage());
            }
            return caminhoArquivo;
        }

    }
}
