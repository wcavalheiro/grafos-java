import problema.PontoDeSalto;

import java.util.*;

class Grafo {
    private int[][] matrizAdjacencia;
    private int numNodos;
    private boolean direcionado;
    private List<PontoDeSalto> pontosDeSalto = new ArrayList<>();

    public Grafo(int numNodos, boolean direcionado) {
        this.numNodos = numNodos;
        this.direcionado = direcionado;
        matrizAdjacencia = new int[numNodos][numNodos];
        this.pontosDeSalto = getPontosDeSalto();
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

    public void setNumNodos(int numNodos) {
        this.numNodos = numNodos;
    }

    public void setNodos(int novosNodos, PontoDeSalto pontoDeSalto) {
        if (novosNodos != numNodos) {
            int[][] novaMatriz = new int[novosNodos][novosNodos];
            for (int i = 0; i < Math.min(numNodos, novosNodos); i++) {
                for (int j = 0; j < Math.min(numNodos, novosNodos); j++) {
                    novaMatriz[i][j] = matrizAdjacencia[i][j];
                }
            }
            matrizAdjacencia = novaMatriz;
            numNodos = novosNodos;

            if (novosNodos > numNodos - 1) {
                pontosDeSalto.add(pontoDeSalto);
            }
        }
    }

    public void adicionarNodo(PontoDeSalto pontoDeSalto) {
        setNodos(numNodos + 1, pontoDeSalto);
    }

    public List<PontoDeSalto> getPontosDeSalto() {
        return pontosDeSalto;
    }

    public void setPontosDeSalto(List<PontoDeSalto> pontosDeSalto) {
        this.pontosDeSalto = pontosDeSalto;
    }

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
                System.out.print(matrizAdjacencia[i][j] + "   ");
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
                    grauSaida++;
                }
                if (matrizAdjacencia[j][i] != 0) {
                    grauEntrada++;
                }
            }

            if (direcionado) {
                System.out.println("Nodo " + (i + 1) + " - Grau de Entrada: " + grauEntrada + ", Grau de Saída: " + grauSaida);
            } else {
                System.out.println("Nodo " + (i + 1) + " - Grau: " + (grauEntrada));
            }
        }
    }

    /*public void calculaMelhorCaminho(int inicio, int destino) {
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
                        predecessor[v] = u;
                        pq.add(new Node(v, dist[v]));
                    }
                }
            }
        }

        if (dist[destino] == Integer.MAX_VALUE) {
            System.out.println("Não existe caminho entre os nodos " + (inicio + 1) + " e " + (destino + 1));
        } else {
            System.out.println("Distância mínima: " + dist[destino] + " parsecs");
            System.out.print("Melhor caminho: ");
            imprimirCaminho(predecessor, destino);
        }
    }*/

    public void algoritmoDijkstra(int inicio, int destino, int fatorSegurancaAceitavel) {
        inicio -= 1;
        destino -= 1;

        int[] dist = new int[numNodos];
        int[] predecessor = new int[numNodos];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(predecessor, -1);
        dist[inicio] = 0;

        boolean[] visitado = new boolean[numNodos];

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(inicio, 0));

        while (!pq.isEmpty()) {
            Node atual = pq.poll();
            int u = atual.nodo;

            if (visitado[u]) continue;
            visitado[u] = true;

            for (int v = 0; v < numNodos; v++) {
                if (matrizAdjacencia[u][v] != 0 && !visitado[v]) {
                    PontoDeSalto pontoV = pontosDeSalto.get(v);
                    if (pontoV.getFatorDeSeguranca() >= fatorSegurancaAceitavel) {
                        int novaDist = dist[u] + matrizAdjacencia[u][v];

                        if (novaDist < dist[v]) {
                            dist[v] = novaDist;
                            predecessor[v] = u;
                            pq.add(new Node(v, dist[v]));
                        }
                    }
                }
            }
        }

        if (dist[destino] == Integer.MAX_VALUE || predecessor[destino] == -1) {
            System.out.println("Não existe um caminho entre os nodos " + (inicio + 1) + " e " + (destino + 1) + " que atenda ao fator de segurança especificado.");
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

    public void algoritmoBF(int inicio, int destino, int fatorSegurancaAceitavel) {
        inicio -= 1;
        destino -= 1;
        boolean[] visitado = new boolean[numNodos];
        List<Integer> caminhoAtual = new ArrayList<>();

        if (bf(inicio, destino, fatorSegurancaAceitavel, visitado, caminhoAtual)) {
            System.out.println("Caminho encontrado:");
            for (int i = 0; i < caminhoAtual.size(); i++) {
                System.out.print((caminhoAtual.get(i) + 1) + (i < caminhoAtual.size() - 1 ? " -> " : ""));
            }
        } else {
            System.out.println("Não existe um caminho entre os nodos " + (inicio + 1) + " e " + (destino + 1) + " que atenda ao fator de segurança especificado.");
        }
    }

    public void algoritmoBFS(int inicio, int destino, int fatorSegurancaAceitavel) {
        inicio -= 1;
        destino -= 1;
        boolean[] visitado = new boolean[numNodos];
        int[] anterior = new int[numNodos];

        Arrays.fill(anterior, -1);

        if (bfs(inicio, destino, fatorSegurancaAceitavel, visitado, anterior)) {
            System.out.println("Caminho encontrado:");
            List<Integer> caminho = reconstruirCaminho(anterior, inicio, destino);
            for (int i = 0; i < caminho.size(); i++) {
                System.out.print((caminho.get(i) + 1) + (i < caminho.size() - 1 ? " -> " : ""));
            }
        } else {
            System.out.println("Não existe um caminho entre os nodos " + (inicio + 1) + " e " + (destino + 1) + " que atenda ao fator de segurança especificado.");
        }
    }

    private boolean bfs(int inicio, int destino, int fatorSegurancaAceitavel, boolean[] visitado, int[] anterior) {
        Queue<Integer> fila = new LinkedList<>();
        visitado[inicio] = true;
        fila.add(inicio);

        while (!fila.isEmpty()) {
            int atual = fila.poll();

            if (atual == destino) {
                return true;
            }

            for (int v = 0; v < numNodos; v++) {
                if (matrizAdjacencia[atual][v] != 0 && !visitado[v]) {
                    PontoDeSalto pontoV = pontosDeSalto.get(v);
                    if (pontoV.getFatorDeSeguranca() >= fatorSegurancaAceitavel) {
                        visitado[v] = true;
                        anterior[v] = atual;
                        fila.add(v);
                    }
                }
            }
        }
        return false;
    }

    private List<Integer> reconstruirCaminho(int[] anterior, int inicio, int destino) {
        List<Integer> caminho = new ArrayList<>();
        for (int at = destino; at != -1; at = anterior[at]) {
            caminho.add(at);
        }
        Collections.reverse(caminho);
        return caminho;
    }


    private boolean bf(int atual, int destino, int fatorSegurancaAceitavel, boolean[] visitado, List<Integer> caminhoAtual) {
        visitado[atual] = true;
        caminhoAtual.add(atual);

        if (atual == destino) {
            return true;
        }

        for (int v = 0; v < numNodos; v++) {
            if (matrizAdjacencia[atual][v] != 0 && !visitado[v]) {
                PontoDeSalto pontoV = pontosDeSalto.get(v);
                if (pontoV.getFatorDeSeguranca() >= fatorSegurancaAceitavel) {
                    if (bf(v, destino, fatorSegurancaAceitavel, visitado, caminhoAtual)) {
                        return true;
                    }
                }
            }
        }

        caminhoAtual.remove(caminhoAtual.size() - 1); // remove o último nodo se caminho não encontrado
        return false;
    }

    public void algoritmoFloyd(int inicio, int destino, int fatorSegurancaAceitavel) {
        // Criação da matriz de distâncias
        int[][] dist = new int[numNodos][numNodos];
        int[][] predecessor = new int[numNodos][numNodos];

        // Inicializando a matriz de distâncias e predecessores
        for (int i = 0; i < numNodos; i++) {
            for (int j = 0; j < numNodos; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                    predecessor[i][j] = -1; // Nenhum predecessor
                } else if (matrizAdjacencia[i][j] != 0) {
                    dist[i][j] = matrizAdjacencia[i][j];
                    predecessor[i][j] = i;
                } else {
                    dist[i][j] = Integer.MAX_VALUE; // Sem conexão
                    predecessor[i][j] = -1;
                }
            }
        }

        // Executando o algoritmo de Floyd-Warshall
        for (int k = 0; k < numNodos; k++) {
            for (int i = 0; i < numNodos; i++) {
                for (int j = 0; j < numNodos; j++) {
                    if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE) {
                        int novaDistancia = dist[i][k] + dist[k][j];
                        if (novaDistancia < dist[i][j]) {
                            dist[i][j] = novaDistancia;
                            predecessor[i][j] = predecessor[k][j];
                        }
                    }
                }
            }
        }

        // Verificando se existe um caminho entre início e destino
        if (dist[inicio][destino] == Integer.MAX_VALUE) {
            System.out.println("Não há caminho entre os nós " + inicio + " e " + destino);
            return;
        }

        // Verificando o fator de segurança aceitável
        if (dist[inicio][destino] > fatorSegurancaAceitavel) {
            System.out.println("O caminho entre os nós " + inicio + " e " + destino +
                    " não atende ao fator de segurança aceitável (" + fatorSegurancaAceitavel + ").");
            return;
        }

        // Exibindo o caminho seguro
        System.out.println("Caminho seguro encontrado entre os nós " + inicio + " e " + destino +
                " com peso total " + dist[inicio][destino] + ":");
        imprimirCaminho(predecessor, inicio, destino);
    }

    private void imprimirCaminho(int[][] predecessor, int inicio, int destino) {
        if (inicio == destino) {
            System.out.print(inicio + " ");
        } else if (predecessor[inicio][destino] == -1) {
            System.out.println("Não há caminho entre " + inicio + " e " + destino);
        } else {
            imprimirCaminho(predecessor, inicio, predecessor[inicio][destino]);
            System.out.print(destino + " ");
        }
    }

    // abaixo é o algoritmo pra encontrar todos os caminhos
    public void encontrarTodosOsCaminhosDijkstra(int inicio, int destino, int fatorSegurancaAceitavel) {
        inicio -= 1;
        destino -= 1;

        List<List<Integer>> todosCaminhos = new ArrayList<>();
        List<Integer> caminhoAtual = new ArrayList<>();

        caminhoAtual.add(inicio);
        dfs(inicio, destino, fatorSegurancaAceitavel, caminhoAtual, todosCaminhos);

        if (todosCaminhos.isEmpty()) {
            System.out.println("Não existem caminhos que atendam ao fator de segurança especificado.");
        } else {
            System.out.println("Todos os caminhos encontrados:");
            for (List<Integer> caminho : todosCaminhos) {
                System.out.println(formatarCaminho(caminho));
            }
        }
    }

    private void dfs(int atual, int destino, int fatorSegurancaAceitavel, List<Integer> caminhoAtual, List<List<Integer>> todosCaminhos) {
        if (atual == destino) {
            todosCaminhos.add(new ArrayList<>(caminhoAtual));
            return;
        }

        for (int v = 0; v < numNodos; v++) {
            if (matrizAdjacencia[atual][v] != 0 && !caminhoAtual.contains(v)) {
                PontoDeSalto pontoV = pontosDeSalto.get(v);
                if (pontoV.getFatorDeSeguranca() >= fatorSegurancaAceitavel) {
                    caminhoAtual.add(v);
                    dfs(v, destino, fatorSegurancaAceitavel, caminhoAtual, todosCaminhos);
                    caminhoAtual.remove(caminhoAtual.size() - 1);
                }
            }
        }
    }

    private String formatarCaminho(List<Integer> caminho) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < caminho.size(); i++) {
            sb.append(caminho.get(i) + 1);
            if (i < caminho.size() - 1) {
                sb.append(" -> ");
            }
        }
        return sb.toString();
    }


    // Implementação do Algoritmo de Bellman-Ford
    public boolean bellmanFord(int origem, int destino, int fatorSegurancaAceitavel, List<Integer> caminho) {
        int numNodos = pontosDeSalto.size();
        int[] dist = new int[numNodos];
        int[] predecessor = new int[numNodos];


        for (int i = 0; i < numNodos; i++) {
            dist[i] = Integer.MAX_VALUE;
            predecessor[i] = -1;
        }
        dist[origem] = 0;


        for (int i = 0; i < numNodos - 1; i++) {
            for (int u = 0; u < numNodos; u++) {
                for (int v = 0; v < numNodos; v++) {
                    if (matrizAdjacencia[u][v] != 0) {
                        PontoDeSalto pontoV = pontosDeSalto.get(v);
                        if (pontoV.getFatorDeSeguranca() >= fatorSegurancaAceitavel) {
                            int peso = matrizAdjacencia[u][v];
                            if (dist[u] != Integer.MAX_VALUE && dist[u] + peso < dist[v]) {
                                dist[v] = dist[u] + peso;
                                predecessor[v] = u;
                            }
                        }
                    }
                }
            }
        }


        for (int u = 0; u < numNodos; u++) {
            for (int v = 0; v < numNodos; v++) {
                if (matrizAdjacencia[u][v] != 0) {
                    int peso = matrizAdjacencia[u][v];
                    if (dist[u] != Integer.MAX_VALUE && dist[u] + peso < dist[v]) {
                        System.out.println("O grafo contém um ciclo negativo.");
                        return false;
                    }
                }
            }
        }

        // Construir o caminho do origem ao destino
        if (dist[destino] == Integer.MAX_VALUE) {
            System.out.println("Não há caminho disponível do origem ao destino.");
            return false;
        }

        // Reconstruir o caminho do destino para a origem usando o array predecessor
        caminho.clear();
        int passoAtual = destino;
        while (passoAtual != -1) {
            caminho.add(0, passoAtual); // Adiciona ao início da lista para reconstruir o caminho
            passoAtual = predecessor[passoAtual];
        }
        return true;
    }
}
