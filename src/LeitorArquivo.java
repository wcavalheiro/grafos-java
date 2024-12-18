import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import problema.PontoDeSalto;
import problema.Caminho;

public class LeitorArquivo {

    private static Map<Integer, PontoDeSalto> pontosDeSaltoMap = new HashMap<>();
    private static Map<Integer, Caminho> caminhosMap = new HashMap<>();
    private static Grafo grafo;
    private static int indexMapCaminhos = 1;

    public LeitorArquivo() {
        // Construtor vazio
    }

    public static Grafo lerGrafoDeArquivoTxt(String caminhoArquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String line;

            line = br.readLine();
            int numNodos = Integer.parseInt(line.trim());
            grafo = new Grafo(0, false);

            for (int i = 0; i < numNodos; i++) {
                line = br.readLine();
                String[] partes = line.trim().split("\\s+");

                if (partes.length == 3) {
                    int id = Integer.parseInt(partes[0]);
                    String nome = partes[1];
                    double fatorDeSeguranca = Double.parseDouble(partes[2]);

                    PontoDeSalto pontoDeSalto = new PontoDeSalto(nome, fatorDeSeguranca);
                    pontosDeSaltoMap.put(id, pontoDeSalto);
                    grafo.adicionarNodo(pontoDeSalto);
                } else {
                    System.out.println("Formato de linha inválido para ponto de salto: " + line);
                }
            }

            while ((line = br.readLine()) != null) {
                String[] partes = line.trim().split("\\s+");

                if (partes.length == 3) {
                    int pontoInicial = Integer.parseInt(partes[0]);
                    int pontoFinal = Integer.parseInt(partes[1]);
                    int parsecs = Integer.parseInt(partes[2]);

                    grafo.adicionarAresta(pontoInicial, pontoFinal, parsecs);
                    Caminho caminho = new Caminho(parsecs, pontoInicial, pontoFinal);
                    caminhosMap.put(indexMapCaminhos++, caminho);
                } else {
                    System.out.println("Formato de linha inválido para caminho: " + line);
                }
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Erro no formato do arquivo: " + e.getMessage());
        }

        return grafo;
    }

    public static Map<Integer, PontoDeSalto> getPontosDeSaltoMap() {
        return pontosDeSaltoMap;
    }

    public static void setPontosDeSaltoMap(Map<Integer, PontoDeSalto> pontosDeSaltoMap) {
        LeitorArquivo.pontosDeSaltoMap = pontosDeSaltoMap;
    }

    public static Map<Integer, Caminho> getCaminhosMap() {
        return caminhosMap;
    }

    public static void setCaminhosMap(Map<Integer, Caminho> caminhosMap) {
        LeitorArquivo.caminhosMap = caminhosMap;
    }
}
