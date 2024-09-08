import excecoes.CaractereInvalido;

import java.util.Scanner;
import java.util.regex.Pattern;

public class MenuDeCriacao {

    private static final Scanner sc = new Scanner(System.in);
    private static Grafo grafo;
    private static int numNodos = 0;

    public static void criarGrafo() throws CaractereInvalido {
        System.out.print("O grafo é direcionado? (true/false): ");
        boolean direcionado = sc.nextBoolean();

        grafo = new Grafo(numNodos, direcionado);

        boolean running = true;
        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Adicionar Nodo");
            System.out.println("2. Adicionar Aresta");
            System.out.println("3. Visualizar Grafo/Digrafo");
            System.out.println("4. Limpar Grafo/Digrafo");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
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
                    System.out.println("\nGrafo/Digrafo limpo...\n");
                    break;
                case 5:
                    running = false;
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void adicionarNodo() throws CaractereInvalido {
        System.out.print("Digite o número de nodos a adicionar: ");
        int novosNodos = sc.nextInt();

        if (!validarTipoCaractere(novosNodos)) {
            throw new CaractereInvalido("Formato inválido: a entrada deve conter apenas números inteiros.");
        } else if (novosNodos < 1) {
            System.out.println("Deve adicionar pelo menos 1 nodo.");
            return;
        }

        // REVER LOGICA DE QUANTIDADE DE NODOS PARA + 1

        numNodos += novosNodos;
        grafo.setNumNodos(numNodos);
        grafo.imprimirMatrizAdjacencia();
        System.out.println("\nNodo adicionada com sucesso!!\n");
    }

    private static void adicionarArestasDinamicante() throws CaractereInvalido {
        while (true) {
            try{
                System.out.print("Digite a aresta (formato: nodo1 nodo2) ou 's s' para parar: ");
                String input1 = sc.next();
                String input2 = sc.next();

                if (input1.equalsIgnoreCase("s") || input2.equalsIgnoreCase("s") ) {
                    System.out.println("\nParando a inserção de arestas...\n");
                    break;
                }

                if (!validarTipoCaractere(input1) || !validarTipoCaractere(input2)) {
                    throw new CaractereInvalido("Formato inválido: a entrada deve conter apenas números inteiros.");
                }

                grafo.adicionarAresta(Integer.parseInt(input1), Integer.parseInt(input2));
                System.out.println("Aresta adicionada com sucesso.");
            } catch (ArrayIndexOutOfBoundsException e){
                System.out.println(e.getMessage());
            }
        }
    }


    public static <T> boolean validarTipoCaractere(T valor) {
        String regex = "^\\d+$";
        String valorStr = String.valueOf(valor);

        return Pattern.matches(regex, valorStr);
    }
}
