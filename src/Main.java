import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcao = 0;

        while (opcao != 3) {
            System.out.println(" --------------------------------------------- ");
            System.out.println(" --------------------------------------------- ");
            System.out.println(" -------------- MENU DE INICIAL -------------- ");
            System.out.println(" --------------------------------------------- ");
            System.out.println(" --------------------------------------------- ");
            System.out.println(" -------- Escolha a opcao entre 1 a 3 -------- \n");
            System.out.println("1. Crie sua rota espacial");
            System.out.println("2. Sair");
            opcao = sc.nextInt();

            switch (opcao) {

                case 1:
                    MenuDeCriacaoProblema.criarGrafo();
                    break;
                case 2:
                    System.out.println("\n\n --------------------------------------------- \n\n");
                    System.out.println("##     ## ##     ## #### ########  #######        #######  ########  ########  ####  ######      ###    ########   #######  \r\n" + //
                                       "###   ### ##     ##  ##     ##    ##     ##      ##     ## ##     ## ##     ##  ##  ##    ##    ## ##   ##     ## ##     ## \r\n" + //
                                       "#### #### ##     ##  ##     ##    ##     ##      ##     ## ##     ## ##     ##  ##  ##         ##   ##  ##     ## ##     ## \r\n" + //
                                       "## ### ## ##     ##  ##     ##    ##     ##      ##     ## ########  ########   ##  ##   #### ##     ## ##     ## ##     ## \r\n" + //
                                       "##     ## ##     ##  ##     ##    ##     ##      ##     ## ##     ## ##   ##    ##  ##    ##  ######### ##     ## ##     ## \r\n" + //
                                       "##     ## ##     ##  ##     ##    ##     ##      ##     ## ##     ## ##    ##   ##  ##    ##  ##     ## ##     ## ##     ## \r\n" + //
                                       "##     ##  #######  ####    ##     #######        #######  ########  ##     ## ####  ######   ##     ## ########   #######   \n");
                    System.out.println("\n\n --------------------------------------------- \n\n");
                    break;
                default:
                    System.out.println("\n Por favor digite um número válido \n");

            }
        }
        sc.close();
    }
}
