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

    private static final Scanner sc = new Scanner(System.in);
    private static Grafo grafo;
    private static int numNodos = 0;
    static int index = 0;
    private static Map<Integer, PontoDeSalto> pontosDeSaltoMap = new HashMap<>();;
    private static Map<Integer, Caminho> caminhosMap = new HashMap<>();;

    public static void criarGrafo() {

        boolean direcionado = false;

        grafo = new Grafo(numNodos, direcionado);

        boolean running = true;
        while (running) {
            System.out.println("\nMenu:");

            System.out.println("1. Adicionar Ponto de Salto");
            System.out.println("2. Adicionar Caminho");
            System.out.println("3. Remover Ponto de Salto");
            System.out.println("4. Remover Aresta");
            System.out.println("5. Visualizar Saltos");
            System.out.println("6. Limpar Grafo/Digrafo");
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
                    for (Map.Entry<Integer, PontoDeSalto> ponto : pontosDeSaltoMap.entrySet()) {
                        System.out.println(ponto.getKey() + " | "+ ponto.getValue().getNome());
                    }
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

                System.out.print("Digite a porcentagem de segurança [0 a 100, de inseguro a muito seguro]: ");
                String fatorDeSeguranca = sc.next();

                if (fatorDeSeguranca.equalsIgnoreCase("x")) {
                    System.out.println("\nParando a inserção de Pontos de Salto...\n");
                    break;
                }

                try {
                    fatorDeSegurancaNumero = Double.parseDouble(fatorDeSeguranca);
                } catch (NumberFormatException e) {
                    System.out.println(MENSAGEM_FORMATO_INVALIDO_DOUBLE);
                    continue;
                }

                if (fatorDeSegurancaNumero < 0 || fatorDeSegurancaNumero > 100) {
                    throw new IllegalArgumentException("O fator de segurança deve estar entre 0 e 100.");
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
    }

    private static void adicionarCaminhosDinamicamente() {
        for (Map.Entry<Integer, PontoDeSalto> ponto : pontosDeSaltoMap.entrySet()) {
            System.out.println(ponto.getKey() + " | "+ ponto.getValue().getNome());
        }

        while (true) {
            try {

                if (grafo.getNumNodos() <= 1) {
                    System.err.println("\nVocê possui: [" + grafo.getNumNodos() + "] Pontos de Salto."
                            + "\nÉ necessário adicionar mais Pontos de Salto antes de prosseguir com a adição de arestas.");
                    break;
                }

                System.out.print("Digite o caminho entre os Pontos de Salto (formato: Ponto Inicial - Ponto Final) ou 'x' para sair: ");
                String aresta = sc.next();

                if (aresta.equalsIgnoreCase("x")) {
                    System.out.println("\nParando a inserção de arestas...\n");
                    break;
                }

                String[] partes = aresta.split("-", 2);
                String pontoInicial = partes[0];
                String pontoFinal = partes[1];

                try {
                    int nodo1 = Integer.parseInt(pontoInicial);
                    int nodo2 = Integer.parseInt(pontoFinal);
                    grafo.adicionarAresta(nodo1, nodo2);
                } catch (NumberFormatException e) {
                    System.out.println(MENSAGEM_FORMATO_INVALIDO_DOUBLE);
                    continue;
                }

                System.out.println("Caminho adicionada com sucesso.");

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



    public static void limparGrafo() {
        grafo.setNumNodos(0);
        numNodos = 0;
    }
}
