import java.util.Arrays;

import static java.util.Arrays.*;

public class Impressao {
   public static int jogoTamanho=7;
    public static void main(String[] args) {
        String primeiro ="*";
        String segundo ="ç";

        String[][] x = new String[jogoTamanho][jogoTamanho];
        String[][] u = new String[jogoTamanho][jogoTamanho];
        criaMatriz(x,primeiro);
        criaMatriz(u,segundo);
        imprimi(x,u);
    }
    public static void imprimi(String[][] x,String[][] y)
    {
        for (int i = 1; i <=jogoTamanho; i++)
            System.out.printf("\t%2s", i );
        System.out.print("              ");
        for (int i = 1; i <=jogoTamanho; i++)
            System.out.printf("\t%s", i );
        System.out.println();
        for (int i = 0; i < jogoTamanho; i++)
        {
            System.out.printf("%3s| ",(i+1));
            for (int j = 0; j < jogoTamanho; j++)
            {
                System.out.printf("%s\t", x[i][j]);
            }
            System.out.print("           ");
            System.out.printf("%3s|",(i+1));
            for (int j = 0; j < jogoTamanho; j++)
            {
                System.out.printf("\t%s", y[i][j]);
            }
            System.out.println();
        }
    }
    public static void criaMatriz(String[][] x,String y){
        for (int i = 0; i < jogoTamanho ; i++){
            Arrays.fill(x[i],y);
        }

    }
}

