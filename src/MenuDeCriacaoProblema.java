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

        boolean direcionado = false;

        grafo = new Grafo(numNodos, direcionado);

        boolean running = true;
        while (running) {
            System.out.println("\nMenu:");

            System.out.println("1. Adicionar Ponto de Salto");
            System.out.println("2. Adicionar Caminho");
            System.out.println("3. Remover Ponto de Salto");
            System.out.println("4. Remover Caminho");
            System.out.println("5. Visualizar Saltos");
            System.out.println("6. Limpar Rota");
            System.out.println("7. Sair");
            System.out.print("\nEscolha uma opção: ");
            int opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    adicionarPontoDeSaltoDinamicamente();
                    break;
                case 2:
                    adicionarCaminhosDinamicamente();
                    break;
                case 3:
                    removerNodo();
                    break;
                case 4:
                    removerAresta();
                    break;
                case 5:
                    listarPontosDeSalto();
                    grafo.imprimirMatrizAdjacencia();
                    grafo.listarGrauNodos();
                    break;
                case 6:
                    limparGrafo();
                    System.out.println("\nGrafo/Digrafo foi limpo.");
                    break;
                case 7:
                    limparGrafo();
                    running = false;
                    System.out.println("\nEncerrando...");
                    break;
                default:
                    System.out.println("\nOpção inválida.");
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
                System.out.println( ANSI_RED + "\nÉ necessário possuir um caractere ao menos o nome do Ponto de Salto.\n" + ANSI_RESET);
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

            System.out.println("\nPonto de Salto adicionado com sucesso!\n");
        }
    }


    /*public static void adicionarPontoDeSaltoDinamicamente() {
        String nome = "";
        double fatorDeSegurancaNumero = 0;

        while (true) {
            try {
                System.out.print("Digite o nome do Ponto de Salto ou 'x' para sair: ");
                nome = sc.next();

                if (nome.equalsIgnoreCase("x")) {
                    System.out.println("\nParando a inserção de Pontos de Salto...\n");
                    break;
                }

                if (nome.isEmpty()) {
                    throw new IllegalArgumentException("\nO nome do Ponto de Salto não pode estar vazio.\n");
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
                            System.out.println("\nO fator de segurança deve estar entre 0 e 100.\n");
                        } else {
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.err.println(MENSAGEM_FORMATO_INVALIDO_DOUBLE);
                    }
                }
                PontoDeSalto pontoDeSaltoObjeto = new PontoDeSalto(nome, fatorDeSegurancaNumero);
                pontosDeSaltoMap.put(index, pontoDeSaltoObjeto);
                index += 1;
                numNodos += 1;
                grafo.setNumNodos(numNodos);

                System.out.println("\nPonto de Salto adicionado com sucesso!\n");

            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
                sc.nextLine();
            }
        }
    }*/

    private static void adicionarCaminhosDinamicamente() {
        if (grafo.getNumNodos() <= 1) {
            System.err.println(ANSI_RED + "\nVocê possui: [" + grafo.getNumNodos() + "] Pontos de Salto."
                    + "\nÉ necessário adicionar mais Pontos de Salto antes de prosseguir com a adição de Caminhos." + ANSI_RESET);
            return;
        }

        String parsecs = " ";
        int parsecsNumero = 0;
        listarPontosDeSalto();
        System.out.println("\n");

        while (true) {
            try {
                sc.nextLine(); //Limpeza do Buffer

                while (true) {
                    try {

                        System.out.print("Digite a distância em Parsecs [3 mil anos luz por unidade] ou 'x' para sair: ");
                        parsecs = sc.nextLine();

                        if (parsecs.equalsIgnoreCase("x")) {
                            System.out.println("\nParando a inserção de arestas...\n");
                            return;
                        }

                            try {
                                parsecsNumero = Integer.parseInt(parsecs);
                            } catch (NumberFormatException e) {
                                System.out.println(MENSAGEM_FORMATO_INVALIDO_DOUBLE);
                            }

                        if (parsecsNumero < 1 || parsecsNumero > 100) {
                            System.out.println( ANSI_RED + "\nA distância deve ser de no máximo 100 Parsecs.\n" + ANSI_RESET);
                        } else {
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(MENSAGEM_FORMATO_INVALIDO_DOUBLE);
                        sc.nextLine();
                    } catch (InputMismatchException e){
                        System.out.println(MENSAGEM_FORMATO_INVALIDO_STRING);
                        sc.nextLine();
                    }
                }

                System.out.print("Digite o caminho entre os Pontos de Salto (formato: Ponto Inicial - Ponto Final) ou 'x' para sair: ");
                String aresta = sc.next();

                if (aresta.equalsIgnoreCase("x")) {
                    System.out.println("\nParando a inserção de arestas...\n");
                    break;
                }

                String[] partes = aresta.split("-", 2);
                if (partes.length != 2) {
                    System.out.println(ANSI_RED + "\nFormato inválido! Use o formato Ponto Inicial - Ponto Final.\n" + ANSI_RESET);
                    continue;
                }

                String pontoInicial = partes[0].trim();
                String pontoFinal = partes[1].trim();

                try {
                    int nodo1 = Integer.parseInt(pontoInicial);
                    int nodo2 = Integer.parseInt(pontoFinal);

                    if (!pontosDeSaltoMap.containsKey(nodo1) || !pontosDeSaltoMap.containsKey(nodo2)) {
                        System.out.println(ANSI_RED + "\nUm ou ambos os nodos não existem.\n" + ANSI_RESET);
                        continue;
                    }

                    Caminho caminho = new Caminho(parsecsNumero, nodo1, nodo2);
                    caminhosMap.put(indexMapCaminhos++, caminho);
                    grafo.adicionarAresta(nodo1, nodo2, parsecsNumero);
                } catch (NumberFormatException e) {
                    System.out.println(MENSAGEM_FORMATO_INVALIDO_DOUBLE);
                    continue;
                }

                System.out.println("\nCaminho adicionada com sucesso.\n");

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
    }

    public static void limparGrafo() {
        grafo.setNumNodos(0);
        numNodos = 0;
    }
}
