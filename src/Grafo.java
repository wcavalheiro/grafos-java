import java.util.Arrays;
import java.util.PriorityQueue;

class Grafo {

    private int[][] matrizAdjacencia;
    private int numNodos;
    private boolean direcionado;

    public Grafo(int numNodos, boolean direcionado) {
        this.numNodos = numNodos;
        this.direcionado = direcionado;
        matrizAdjacencia = new int[numNodos][numNodos];
    }

    /*public void adicionarAresta(int nodo1, int nodo2) {
        if (nodo1 <= numNodos && nodo2 <= numNodos) {
            matrizAdjacencia[nodo1 - 1][nodo2 - 1] = 1;
            if (!direcionado) {
                matrizAdjacencia[nodo2 - 1][nodo1 - 1] = 1;
            }
        } else {
            System.out.println("Nodos inválidos.");
        }
    }*/

    public void adicionarAresta(int nodo1, int nodo2, int peso) {
        if (nodo1 <= numNodos && nodo2 <= numNodos) {
            matrizAdjacencia[nodo1 - 1][nodo2 - 1] = peso;
            if (!direcionado) {
                matrizAdjacencia[nodo2 - 1][nodo1 - 1] = peso;
            }
        } else {
            System.out.println("Nodos inválidos.");
        }
    }

    public void removerAresta(int nodo1, int nodo2) {
        if (nodo1 <= numNodos && nodo2 <= numNodos) {
            matrizAdjacencia[nodo1 - 1][nodo2 - 1] = 0;
            if (!direcionado) {
                matrizAdjacencia[nodo2 - 1][nodo1 - 1] = 0;
            }
            System.out.println("Aresta removida com sucesso.");
        } else {
            System.out.println("Nodos inválidos.");
        }
    }

    public void removerNodo(int nodo) {
        if (nodo <= numNodos) {
            int[][] novaMatriz = new int[numNodos - 1][numNodos - 1];

            for (int i = 0, ni = 0; i < numNodos; i++) {
                if (i == nodo - 1)
                    continue;
                for (int j = 0, nj = 0; j < numNodos; j++) {
                    if (j == nodo - 1)
                        continue;
                    novaMatriz[ni][nj] = matrizAdjacencia[i][j];
                    nj++;
                }
                ni++;
            }

            matrizAdjacencia = novaMatriz;
            numNodos--;
            System.out.println("Nodo removido com sucesso.");
        } else {
            System.out.println("Nodo inválido.");
        }
    }

    public void imprimirMatrizAdjacencia() {
        System.out.println("\nMatriz de Adjacência:");
        for (int i = 0; i < numNodos; i++) {
            for (int j = 0; j < numNodos; j++) {
                System.out.print(matrizAdjacencia[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void listarGrauNodos() {
        for (int i = 0; i < numNodos; i++) {
            int grauEntrada = 0;
            int grauSaida = 0;

            for (int j = 0; j < numNodos; j++) {
                if (matrizAdjacencia[i][j] != 0) {
                    grauSaida++; // Conta as arestas saindo do nodo i
                }
                if (matrizAdjacencia[j][i] != 0) {
                    grauEntrada++; // Conta as arestas chegando ao nodo i
                }
            }

            if (direcionado) {
                System.out.println("Nodo " + (i + 1) + " - Grau de Entrada: " + grauEntrada + ", Grau de Saída: " + grauSaida);
            } else {
                System.out.println("Nodo " + (i + 1) + " - Grau: " + (grauEntrada));
            }
        }
    }

    public int[][] getMatrizAdjacencia() {
        return matrizAdjacencia;
    }

    public void setMatrizAdjacencia(int[][] matrizAdjacencia) {
        this.matrizAdjacencia = matrizAdjacencia;
    }

    public int getNumNodos() {
        return numNodos;
    }

    public void setNumNodos(int novosNodos) {
        if (novosNodos != numNodos) {
            int[][] novaMatriz = new int[novosNodos][novosNodos];

            for (int i = 0; i < Math.min(numNodos, novosNodos); i++) {
                for (int j = 0; j < Math.min(numNodos, novosNodos); j++) {
                    novaMatriz[i][j] = matrizAdjacencia[i][j];
                }
            }
            matrizAdjacencia = novaMatriz;
            numNodos = novosNodos;
        }
    }

    public boolean isDirecionado() {
        return direcionado;
    }

    public void calculaMelhorCaminho(int inicio, int destino) {
        inicio -= 1;
        destino -= 1;

        int[] dist = new int[numNodos];  // Distâncias mínimas até cada nodo
        int[] predecessor = new int[numNodos];  // Armazena o nodo anterior no caminho mais curto
        Arrays.fill(dist, Integer.MAX_VALUE);  // Inicializa distâncias com infinito
        Arrays.fill(predecessor, -1);  // Inicializa predecessor com -1 (nenhum predecessor ainda)
        dist[inicio] = 0;

        boolean[] visitado = new boolean[numNodos];  // Marca nodos visitados

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(inicio, 0));

        while (!pq.isEmpty()) {
            Node atual = pq.poll();
            int u = atual.nodo;

            if (visitado[u]) continue;
            visitado[u] = true;

            for (int v = 0; v < numNodos; v++) {
                if (matrizAdjacencia[u][v] != 0 && !visitado[v]) {
                    int novaDist = dist[u] + matrizAdjacencia[u][v];

                    if (novaDist < dist[v]) {
                        dist[v] = novaDist;
                        predecessor[v] = u;  // Armazena o nodo de onde viemos
                        pq.add(new Node(v, dist[v]));
                    }
                }
            }
        }

        // Agora que temos a distância mínima e os predecessores, reconstruímos o caminho
        if (dist[destino] == Integer.MAX_VALUE) {
            System.out.println("Não existe caminho entre os nodos " + (inicio + 1) + " e " + (destino + 1));
        } else {
            System.out.println("Distância mínima: " + dist[destino] + " parsecs");
            System.out.print("Melhor caminho: ");
            imprimirCaminho(predecessor, destino);
        }
    }

    private void imprimirCaminho(int[] predecessor, int nodo) {
        if (predecessor[nodo] == -1) {
            System.out.print((nodo + 1));
            return;
        }
        imprimirCaminho(predecessor, predecessor[nodo]);
        System.out.print(" -> " + (nodo + 1));

    }

    // Classe auxiliar para representar um nodo na fila de prioridade
    private static class Node implements Comparable<Node> {
        int nodo;
        int distancia;

        public Node(int nodo, int distancia) {
            this.nodo = nodo;
            this.distancia = distancia;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.distancia, other.distancia);
        }
    }

}
