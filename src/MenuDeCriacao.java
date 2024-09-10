import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MenuDeCriacao {

    private static final String MENSAGEM_FORMATO_INVALIDO = "\nFormato inválido: a entrada deve conter apenas números inteiros.\n";

    private static final Scanner sc = new Scanner(System.in);
    private static Grafo grafo;
    private static int numNodos = 0;

    public static void criarGrafo() {

        boolean direcionado = verificarGrafoDirecionado();

        grafo = new Grafo(numNodos, direcionado);

        boolean running = true;
        while (running) {
            System.out.println("\nMenu:");

            System.out.println("1. Adicionar Nodo");
            System.out.println("2. Adicionar Aresta");
            System.out.println("3. Visualizar Grafo/Digrafo");
            System.out.println("4. Limpar Grafo/Digrafo");
            System.out.println("5. Sair");
            System.out.print("\nEscolha uma opção: ");
            int opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    adicionarNodo();
                    break;
                case 2:
                    adicionarArestasDinamicante();
                    break;
                case 3:
                    grafo.imprimirMatrizAdjacencia();
                    grafo.listarGrauVertices();
                    break;
                case 4:
                    grafo.setNumNodos(0);
                    numNodos = 0;
                    System.out.println("\nGrafo/Digrafo foi limpo.");
                    break;
                case 5:
                    running = false;
                    System.out.println("\nEncerrando...");
                    break;
                default:
                    System.out.println("\nOpção inválida.");
            }
        }
    }

    public static void adicionarNodo(){
        while (true) {
            try {
                System.out.print("Digite o número de nodos a adicionar: ");
                int novosNodos = sc.nextInt();

                if (grafo.getNumNodos() == 0 && novosNodos <= 1) {
                    System.out.println("\nPara inicializar o Grafo/Digrafo é necessário adicionar mais de um Nodo.");
                    continue;
                }

                if (novosNodos < 1) {
                    System.out.println("\nVocê deve adicionar pelo menos 1 nodo.");
                    continue;
                }

                numNodos += novosNodos;
                grafo.setNumNodos(numNodos);
                grafo.imprimirMatrizAdjacencia();
                System.out.println("\nNodo adicionado com sucesso!!");
                break;
            } catch (InputMismatchException e) {
                System.out.println(MENSAGEM_FORMATO_INVALIDO);
                sc.nextLine();
            }
        }
    }


    private static void adicionarArestasDinamicante(){
        while (true) {
            if (grafo.getNumNodos() <= 1) {
                System.out.println("\nVocê possui: [" + grafo.getNumNodos() + "] nodos." + "\nÉ necessário adicionar mais Nodos antes de prosseguir com a adição de arestas.");
                break;
            }

            System.out.print("Digite a aresta (formato: nodo1 nodo2) ou 's s' para parar: ");
            String input1 = sc.next();
            String input2 = sc.next();

            if (input1.equalsIgnoreCase("s") || input2.equalsIgnoreCase("s")) {
                System.out.println("\nParando a inserção de arestas...\n");
                break;
            }

            try {
                int nodo1 = Integer.parseInt(input1);
                int nodo2 = Integer.parseInt(input2);
                grafo.adicionarAresta(nodo1, nodo2);
                System.out.println("Aresta adicionada com sucesso.");

            } catch (NumberFormatException e) {
                System.out.println(MENSAGEM_FORMATO_INVALIDO);
            }
        }
    }

    public static boolean verificarGrafoDirecionado() {
        int direcionadoOpcao;

        do {
            try {
                System.out.print("O grafo é direcionado? 1 - [TRUE] | 2 - [FALSE]: ");
                direcionadoOpcao = sc.nextInt();

                switch (direcionadoOpcao) {
                    case 1:
                        return true;
                    case 2:
                        return false;
                    default:
                        System.out.println("\nOpção inválida. Digite 1 para [TRUE] ou 2 para [FALSE].\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Por favor, insira 1 ou 2.");
                sc.nextLine();
            }
        } while (true);
    }

    private static void exibirMensagemTemporaria(String mensagem) {
        try {
            System.out.println(mensagem);

            Thread.sleep(2000);

            limparConsole();
        } catch (InterruptedException e) {
            System.err.println("Erro ao pausar a execução: " + e.getMessage());
        }
    }

    private static void limparConsole() {
        String sistemaOperacional = System.getProperty("os.name");

        try {
            if (sistemaOperacional.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.err.println("\nErro ao limpar o console: " + e.getMessage());
        }
    }

    public static <T> boolean validarTipoCaractere(T valor) {
        String regex = "^\\d+$";
        String valorStr = String.valueOf(valor);

        return Pattern.matches(regex, valorStr);
    }
}