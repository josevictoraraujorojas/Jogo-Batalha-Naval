import java.util.Arrays;
import java.util.Scanner;

public class Jogo<limiteMaximoDeNavios> {

    public static void imprimirmenu(){
        String tit = "\n|   ~ __ ~ Batalha  Naval ~  __ ~   |",
                lin ="\n|-----------------------------------|",
                es = "\t\t\t\t|\n|\t\t\t   ",ess="\t\t\t\t|\n|\t\t      ",
                jo= "jogar(1)", aj="ajuda(2)",cr="creditos(3)",sr="\t\t\t|\n|\t\t\t\tsair(4)";

        System.out.println(lin+tit+lin+"\n|\t\t\t   "+jo+es+aj+ess+cr+sr+"\t\t\t\t|"+lin);


    }public static void controlemenu() {//controla as acões do menu
        String selecionarop,lin ="|-----------------------------------|";
        Scanner input= new Scanner(System.in);
        while (true){
            imprimirmenu();
            System.out.print("|\t digite um numero de 1 a 4: \t|");
            selecionarop = input.next();
            switch (selecionarop) {
                case "1" -> imprimiropjogo();
                case "2" -> textodeajuda();
                case "3" -> creditos();
                case "4" -> sair();
                default -> System.out.println("|erro!\t\t\t\t\t\t\t\t|\n"+lin);
            }
        }
    }
    public static void imprimiropjogo(){//define qual vai ser o modo de jogo
        String tit = "|~ __ ~ opcoes  do jogo ~ __ ~|", lin ="|-----------------------------------|",
                mul = "multiplayer(1)", sin = "single player(2)",vol = "voltar ao menu principal(3)";
        System.out.println(lin+"\n"+tit+"\n"+lin+"\n|\t\t\t"+mul+"\t\t\t|\n|\t\t   "+sin+" \t\t|\n|\t "+vol+"\t|\n"+lin);
        opcaopdojogo();
    }public static void opcaopdojogo(){
        Scanner input =new Scanner(System.in);String op="x",lin ="|-----------------------------------|";
        while (!(op.equals("1")||op.equals("2")||op.equals("3"))) {
            System.out.println("|     digite um numero de 1 a 3     |\n" + lin);
            op = input.next();
            switch (op) {
                case "1" -> p1xp2();
                case "2" -> p1xbot();
                case "3" -> System.out.println("voltando menu");
                default -> System.out.println("erro!!");
            }
        }

    }
    public static void textodeajuda(){
        String textodeajuda =
                "  Cada jogador, na sua vez de      |\n| jogar, seguira o seguinte         |\n| procedimento:                     |\n|"+
                        " 1. Disparara 3 tiros, indicando   |\n| a coordenadas do alvo atraves     |\n| do numero da linha e da letra da  |\n|"+
                        " coluna que definem a posicao.     |\n| Para que o jogador tenha o        |\n| controle dos tiros disparados,    |\n|"+
                        " devera marcar cada um deles no    |\n| reticulado intitulado Seu :jogo:. |\n| 2. Apos cada um dos tiros, o;     |\n|"+
                        " oponente avisara se acertou e,    |\n| nesse caso, qual a arma foi       |\n| atingida. Se ela for afundada,    |\n|"+
                        " esse fato tambem devera ser       |\n| informado.                        |\n| 3. A cada tiro acertado em um     |\n|"+
                        " alvo, o oponente devera marcar    |\n| em seu tabuleiro para que possa   |\n| informar quando a arma for        |\n|"+
                        " afundada.                         |\n| 4. Uma arma e afundada            |\n| quando todas as casas que         |\n|"+
                        " formam essa arma forem            |\n| atingidas.                        |\n|5. Apos os 3 tiros e as respostas  |\n|"+
                        " do opoente, a vez para para o     |\n| outro jogador.                    |\n| O jogo termina quando um dos      |\n|"+
                        " jogadores afundar todas as        |\n| armas do seu oponente.            |";

        String titulo = "|~ ~ ~ ~ __ ~ ajuda ~ __ ~ ~ ~ ~|",
                linhas ="\n|-----------------------------------|";
        System.out.print(linhas+"\n"+titulo+"\n"+linhas+"\n|"+textodeajuda+linhas+"\n");opajuda();

    } public static void opajuda(){
        Scanner input = new Scanner(System.in);String res="x",s = "\n|\t\t\t\tsim(1)\t\t\t\t|", lin ="\n|-----------------------------------|";
        while (!(res.equals("1"))){
            System.out.print("|  deseja voltar ao menu principal? |"+s+lin);
            res = input.next();
            switch (res){
                case "1"-> {
                    System.out.println("Saindo da opção ajuda");
                }
                default -> System.out.println("| erro!!!\t\t\t\t\t\t\t|");}}
    }
    static String nomeJogador1, nomeJogador2;
    static int tamanho =7;
    public static void creditos(){
        String lin ="|-----------------------------------|" ,tit= "\n| ~ ~ ~ __   creditos  __ ~ ~ ~ |",
                n1 ="\n| * ~ * ~ -Ariana Mesquita- ~ * ~ * |",
                n2 ="\n|\t\t\t\t\t\t\t\t\t|\n| <....> -Jose Victor Rojas- <....> |",
                n3 ="\n|\t\t\t\t\t\t\t\t\t|\n| ~ _ -Pedro  Ferreira- _ ~ |\n";
        System.out.println(lin+tit+"\n"+lin+"\n|jogo desenvolvido por:             |"+n1+n2+n3+lin);
        Scanner input = new Scanner(System.in);String res="x",s = "\n|\t\t\t\tsim(1)\t\t\t\t|", linn ="\n|-----------------------------------|";
        while (!(res.equals("1"))){
            System.out.print("|  deseja voltar ao menu principal? |"+s+linn);
            res = input.next();
            switch (res){
                case "1"->{
                    System.out.println("saindo");
                }
                default -> System.out.println("| erro!!!\t\t\t\t\t\t\t|");}}
    }
    public static void sair(){
        Scanner input = new Scanner(System.in);String res="x", lin ="\n|-----------------------------------|";
        while (!(res.equals("2"))){
            System.out.print("| deseja sair do jogo? sim(1) nao(2)|"+lin);
            res = input.next();
            switch (res){
                case "1" -> {
                    System.out.println("saindo..... ate a proxima!!:)");
                    System.exit(0);
                }
                case "2"-> System.out.println("voltando");
                default -> System.out.println("| erro!!!\t\t\t\t\t\t\t|");}}


    }
    // /, limiteMaximoDeNavios;/
    static String[][] tabuleiroJogador1 = new String[tamanho][tamanho];
    static String[][] tabuleiroJogador2 = new String[tamanho][tamanho];
    static Scanner input = new Scanner(System.in);
    public static void p1xp2(){
        obterNomesDosJogadores();
        System.out.printf("\t\t\t %s \t\t\t\t\t\t\t\t\t\t %s \n",nomeJogador1,nomeJogador2);
       inicializarTabuleiro(tabuleiroJogador1);
       inicializarTabuleiro(tabuleiroJogador2);
        imprimirTabuleiro(tabuleiroJogador1,tabuleiroJogador2);
    }
    public static void p1xbot(){

    }

    public static void obterNomesDosJogadores() {
        System.out.println("Digite o nome do Jogador 1: ");
        nomeJogador1 = input.next();
        System.out.println("Digite o nome do Jogador 2: ");
        nomeJogador2 = input.next();
    }
    public static void inicializarTabuleiro(String[][] x){
        for (int i = 0; i < tamanho ; i++){
            Arrays.fill(x[i],"*");
        }
    }

    public static void imprimirTabuleiro(String [][] y,String [][] x){
        for (int i = 1; i <=tamanho; i++)
            System.out.printf("\t%2s", i );
        System.out.print("              ");
        for (int i = 1; i <=tamanho; i++)
            System.out.printf("\t%s", i );
        System.out.println();
        for (int i = 0; i < tamanho; i++)
        {
            System.out.printf("%3s| ",(i+1));
            for (int j = 0; j < tamanho; j++)
            {
                System.out.printf("%s\t",x[i][j]);
            }
            System.out.print("           ");
            System.out.printf("%3s|",(i+1));
            for (int j = 0; j < tamanho; j++)
            {
                System.out.printf("\t%s", y[i][j]);
            }
            System.out.println();
        }

    }

    public static void main(String[] args) {
        controlemenu();

    }


}
