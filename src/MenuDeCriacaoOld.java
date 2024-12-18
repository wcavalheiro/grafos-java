//import java.util.InputMismatchException;
//import java.util.Scanner;
//
//public class MenuDeCriacaoOld {
//
//    private static final String MENSAGEM_FORMATO_INVALIDO = "\nFormato inválido: a entrada deve conter apenas números inteiros.\n";
//
//    private static final Scanner sc = new Scanner(System.in);
//    private static Grafo grafo;
//    private static int numNodos = 0;
//
//    public static void criarGrafo() {
//
//        boolean direcionado = verificarGrafoDirecionado();
//
//        grafo = new Grafo(numNodos, direcionado);
//
//        boolean running = true;
//        while (running) {
//            System.out.println("\nMenu:");
//
//            System.out.println("1. Adicionar Nodo");
//            System.out.println("2. Adicionar Aresta");
//            System.out.println("3. Remover Nodo");
//            System.out.println("4. Remover Aresta");
//            System.out.println("5. Visualizar Grafo/Digrafo");
//            System.out.println("6. Limpar Grafo/Digrafo");
//            System.out.println("7. Sair");
//            System.out.print("\nEscolha uma opção: ");
//            int opcao = sc.nextInt();
//
//            switch (opcao) {
//                case 1:
//                    adicionarNodo();
//                    break;
//                case 2:
//                    adicionarArestasDinamicamente();
//                    break;
//                case 3:
//                    removerNodo();
//                    break;
//                case 4:
//                    removerAresta();
//                    break;
//                case 5:
//                    if(grafo.getNumNodos() == 0) {
//                        System.out.println("\nO Grafo non ecxiste");
//                        break;
//                    }
//                    grafo.imprimirMatrizAdjacencia();
//                    grafo.listarGrauNodos();
//                    break;
//                case 6:
//                    limparGrafo();
//                    System.out.println("\nGrafo/Digrafo foi limpo.");
//                    break;
//                case 7:
//                    limparGrafo();
//                    running = false;
//                    System.out.println("\nEncerrando...");
//                    break;
//                default:
//                    System.out.println("\nOpção inválida.");
//            }
//        }
//    }
//
//    public static void adicionarNodo() {
//        while (true) {
//            try {
//                System.out.print("Digite o número de nodos a adicionar: ");
//                int novosNodos = sc.nextInt();
//
//                if (grafo.getNumNodos() == 0 && novosNodos <= 1) {
//                    System.out.println("\nPara inicializar o Grafo/Digrafo é necessário adicionar mais de um Nodo.");
//                    continue;
//                }
//
//                if (novosNodos < 1) {
//                    System.out.println("\nVocê deve adicionar pelo menos 1 nodo.");
//                    continue;
//                }
//                numNodos += novosNodos;
//                grafo.setNumNodos(numNodos);
//                grafo.imprimirMatrizAdjacencia();
//                System.out.println("\nNodo adicionado com sucesso!!");
//                break;
//            } catch (InputMismatchException e) {
//                System.out.println(MENSAGEM_FORMATO_INVALIDO);
//                sc.nextLine();
//            }
//        }
//    }
//
//    private static void adicionarArestasDinamicamente() {
//        while (true) {
//            if (grafo.getNumNodos() <= 1) {
//                System.out.println("\nVocê possui: [" + grafo.getNumNodos() + "] nodos."
//                        + "\nÉ necessário adicionar mais Nodos antes de prosseguir com a adição de arestas.");
//                break;
//            }
//
//            System.out.print("Digite a aresta (formato: nodo1 nodo2) ou 'x x' para parar: ");
//            String input1 = sc.next();
//            String input2 = sc.next();
//
//            if (input1.equalsIgnoreCase("x") || input2.equalsIgnoreCase("x")) {
//                System.out.println("\nParando a inserção de arestas...\n");
//                break;
//            }
//
//            try {
//                int nodo1 = Integer.parseInt(input1);
//                int nodo2 = Integer.parseInt(input2);
//                grafo.adicionarAresta(nodo1, nodo2, 2);
//                System.out.println("Aresta adicionada com sucesso.");
//
//            } catch (NumberFormatException e) {
//                System.out.println(MENSAGEM_FORMATO_INVALIDO);
//            }
//        }
//    }
//
//    private static void removerAresta() {
//        while (true) {
//            if (grafo.getNumNodos() <= 1) {
//                System.out.println("\nVocê possui: [" + grafo.getNumNodos() + "] nodos."
//                        + "\nNão é possível remover arestas com menos de dois nodos.");
//                break;
//            }
//
//            System.out.print("Digite a aresta a ser removida (formato: nodo1 nodo2) ou 'x x' para parar: ");
//            String input1 = sc.next();
//            String input2 = sc.next();
//
//            if (input1.equalsIgnoreCase("x") || input2.equalsIgnoreCase("x")) {
//                System.out.println("\nParando a remoção de arestas...\n");
//                break;
//            }
//
//            try {
//                int nodo1 = Integer.parseInt(input1);
//                int nodo2 = Integer.parseInt(input2);
//                grafo.removerAresta(nodo1, nodo2);
//                System.out.println("Aresta removida com sucesso.");
//
//            } catch (NumberFormatException e) {
//                System.out.println(MENSAGEM_FORMATO_INVALIDO);
//            }
//        }
//    }
//
//    private static void removerNodo() {
//        while (true) {
//            System.out.print("Digite o número do nodo a ser removido ou 'x' para parar: ");
//            String input = sc.next();
//
//            if (input.equalsIgnoreCase("x")) {
//                System.out.println("\nParando a remoção de nodos...\n");
//                break;
//            }
//
//            try {
//                int nodo = Integer.parseInt(input);
//                grafo.removerNodo(nodo);
//                grafo.imprimirMatrizAdjacencia();
//
//            } catch (NumberFormatException e) {
//                System.out.println(MENSAGEM_FORMATO_INVALIDO);
//            }
//        }
//    }
//
//    public static boolean verificarGrafoDirecionado() {
//        int direcionadoOpcao;
//
//        do {
//            try {
//                System.out.print("O grafo é direcionado? 1 - [DIRECIONADO] | 2 - [NÃO DIRECIONADO]: ");
//                direcionadoOpcao = sc.nextInt();
//
//                switch (direcionadoOpcao) {
//                    case 1:
//                        return true;
//                    case 2:
//                        return false;
//                    default:
//                        System.out.println("\nOpção inválida. Digite 1 para [DIRECIONADO] ou 2 para [NÃO DIRECIONADO].\n");
//                }
//            } catch (InputMismatchException e) {
//                System.out.println("Entrada inválida! Por favor, insira 1 ou 2.");
//                sc.nextLine();
//            }
//        } while (true);
//    }
//
//    public static void limparGrafo() {
//        grafo.setNumNodos(0);
//        numNodos = 0;
//    }
//}
