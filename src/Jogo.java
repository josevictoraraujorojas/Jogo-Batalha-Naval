import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Jogo{

    static String nomeJogador1, nomeJogador2;
    static int tamanhoTabuleiro = 7;
    static int navioTamanho = 4;
    static int quantidadeBarcos = 3;
    static String[][] tabuleiroJogador1 = new String[tamanhoTabuleiro][tamanhoTabuleiro];
    static String[][] tabuleiroJogador2 = new String[tamanhoTabuleiro][tamanhoTabuleiro];
    static String[][] jogoDoJogador1 = new String[tamanhoTabuleiro][tamanhoTabuleiro];
    static String[][] jogoDoJogador2 = new String[tamanhoTabuleiro][tamanhoTabuleiro];
    static int[] tentativasP1 = {4};
    static int[] tentativasP2 = {4};
    static int[] jogadasP1 = new int[1];
    static int[] jogadasP2 = new int[1];
    static int[] parteBarcoP1 = new int[1];
    static int[] parteBarcoP2 = new int[1];
    static Scanner input = new Scanner(System.in);
    static String casaVazia = "*";
    static int linha;
    static int coluna;
    static int possibilidade;

    public static void imprimirMenu(){ //metodo que imprimi o menu para o usuário//
        String  tit = "|   ~ __ ~ Batalha  Naval ~  __ ~   |",
                lin = "|-----------------------------------|",
                jo = "jogar(1)", aj="ajuda(2)",cr="creditos(3)",
                sr = "\t\t\t|\n|\t\t\t\tsair(4)";

        System.out.println(lin+"\n"+tit+"\n"+lin+"\n|\t\t\t   "+jo+"\t\t\t\t|\n|\t\t\t   "
                +aj+"\t\t\t\t|\n|\t\t      "+cr+sr+"\t\t\t\t|\n"+lin);

    }public static void menuPrincipal() throws InterruptedException {//chama o metodo imprimir menu e controla a interção do usuário//
        String escolha="x",lin ="|-----------------------------------|";
        while (!(escolha.equals("4"))){
            imprimirMenu();
            System.out.print("|\t digite um numero de 1 a 4: \t|");
            escolha = input.next();
            switch (escolha) {
                case "1" -> modosDoJogo();
                case "2" -> voltaraomenuprincipal();//-> opção ajuda********
                case "3" -> creditos();
                case "4" -> sair();
                default -> System.out.println("|erro!\t\t\t\t\t\t\t\t|\n"+lin);
            }
        }
    }
    public static void imprimirmodosdojogo(){//define qual vai ser o modo de jogo
        String tit = "|   ~ __ ~ opcoes  do jogo ~ __ ~   |",
               lin = "|-----------------------------------|",
               mul = "multiplayer(1)", sin = "single player(2)",
               vmp = "voltar ao menu principal(3)";

        System.out.println(lin+"\n"+tit+"\n"+lin+"\n|\t\t\t"+mul+
                "\t\t\t|\n|\t\t   "+sin+" \t\t|\n|\t "+vmp+"\t|\n"+lin);
        }
    public static void modosDoJogo() throws InterruptedException {
        String escolha="x",lin ="|-----------------------------------|";
        imprimirmodosdojogo();
        while (!(escolha.equals("1")||escolha.equals("2")||escolha.equals("3"))) {

            System.out.println("|     digite um numero de 1 a 3     |" +"\n" +lin);
            escolha = input.next();
            switch (escolha) {
                case "1" -> p1xp2();
                case "2" -> p1xbot();
                case "3" -> System.out.println("|voltando!!\t\t\t\t\t\t\t|");
                default -> System.out.println("erro!!");
            }
        }
    }

    public static void p1xp2() throws InterruptedException {
        inicializarTabuleiro(tabuleiroJogador1);
        inicializarTabuleiro(tabuleiroJogador2);
        inicializarTabuleiro(jogoDoJogador1);
        inicializarTabuleiro(jogoDoJogador2);
        int derrotajogador1=0;
        int derrotajogador2=0;
        int vezJogador1=0;
        int vezJogador2=0;
        obterNomesDosJogadores();
        escolherOpcaoDeModos();
        imprimirTabuleiro(jogoDoJogador1,jogoDoJogador2);
        while (parteBarcoP1[0]!= navioTamanho * quantidadeBarcos && parteBarcoP2[0] !=navioTamanho*quantidadeBarcos){
            vezJogador2++;
            vezJogador1++;
            System.out.println(vezJogador1);
            System.out.println(vezJogador2);
            System.out.println(derrotajogador1);
            System.out.println(derrotajogador2);
            System.out.println(parteBarcoP1[0]);
            System.out.println(parteBarcoP2[0]);

            if (jogadasP1[0]>=tentativasP1[0]){
                derrotajogador1++;
            }
            if (jogadasP2[0]>=tentativasP2[0]){
                derrotajogador2++;
            }

            if (vezJogador2%2==0 && derrotajogador2==0) {
                combate(nomeJogador2,jogadasP2,tentativasP2, parteBarcoP2, jogoDoJogador2, tabuleiroJogador2);
            }
            else if (vezJogador1%2!=0 && derrotajogador1==0) {
                combate(nomeJogador1,jogadasP1,tentativasP1, parteBarcoP1, jogoDoJogador1, tabuleiroJogador1);
            }
            else if (jogadasP1[0]>=tentativasP1[0]&&jogadasP2[0]>=tentativasP2[0]){
                break;
            }
        }
        if (parteBarcoP1[0]==navioTamanho*quantidadeBarcos){
            System.out.println(nomeJogador1+" parabens marinheiro você destruiu todos os navios");
        }else if (parteBarcoP2[0]==navioTamanho*quantidadeBarcos){
            System.out.println(nomeJogador2+" parabens marinheiro você destruiu todos os navios");
        }
        else {
            System.out.println("Os dois marinheiros foram destriuidos");
        }
    }
    public static void combate(String nome,int[] jogada,int[] tentativas,int[] parteBarco,String[][] jogo,String[][] hack) throws InterruptedException {
        String resposta;
        System.out.println("jogada do jogador: "+nome);
        //faz a jogada
        System.out.println(nome+" tem esta quantidade de tentativas para ganhar: "+tentativas[0]);
        jogada[0]++;
        Thread.sleep(1000);
        System.out.println("Jogada: " + jogada[0]);
        Thread.sleep(1000);
        System.out.println("voce que ir a loja se sim digite sim");
        resposta= input.next();
        if (resposta.equals("sim")){
            loja(hack,tentativas);
        }
        System.out.println("escolha uma coordenada para jogar a bomba");
        linha = input.nextInt()-1;
        coluna = input.nextInt()-1;
        //verifica se a jogada é diferente
        while (!diferentesJogadas(linha, coluna,jogo))
        {
            System.out.println("Jogada ja feita escolha outra jogada");
            System.out.println("Escolha a linha e a coluna ");
            linha = input.nextInt()-1;
            coluna = input.nextInt()-1;

        }
        //verifica se alguma parte do barco foi encontrada
        if (parteEncontrada(linha, coluna,hack))
        {
            parteBarco[0]++;
            Thread.sleep(1000);
            System.out.println("total de partes destruída " + parteBarco[0]);
            Thread.sleep(1000);
            barcoMostra(linha,coluna,jogo,hack);
            Thread.sleep(1000);
        }
        //se nenhuma parte foi encontrada ele considera como bomba
        else
        {
            bombaMostra(linha,coluna,jogo);
            Thread.sleep(1000);
            System.out.println("Nenhum barco encontrado");
        }
        Thread.sleep(1000);
        imprimirTabuleiro(jogoDoJogador1,jogoDoJogador2);
    }
    public static boolean diferentesJogadas(int f, int c,String[][] jogo)
    {
        return jogo[f][c].equals("*");
    }
    //atribui o valor do barco
    public static void barcoMostra (int f,int c,String[][] jogo,String[][] hack)
    {
        jogo[f][c]=hack[f][c];
    }
    //atribui o valor bomba para o indicie
    public static void bombaMostra (int f,int c,String[][] jogo)
    {
        jogo[f][c]="O";
    }
    //verifica se alguma parte foi encontrada
    public static boolean parteEncontrada(int f, int c,String[][] hack)
    {
        return hack[f][c].equals("-") || hack[f][c].equals("|") ;
    }
    public static void loja (String[][]hack,int[] tentativas) {
        int resposta=0;
        while (resposta!=1) {
            System.out.println("esta é a loja");
            System.out.println("1)dica -4 tentativas");
            System.out.println("escolha");
            resposta = input.nextInt();
            if (resposta == 1) {
                int linha;
                System.out.println("informe a linha que voce quer saber se tem barco");
                linha = input.nextInt()-1;
                dicas(linha, hack);
                tentativas[0] -= 4;
            } else {
                System.out.println("resposta invalida");
            }
        }
    }
    public static void dicas(int x,String[][] hack) {
        int count =0;
        for (int i = 0; i < tamanhoTabuleiro ; i++) {
            if (hack[x][i].equals("-")||hack[x][i].equals("")){
                System.out.println("dica da linha");
                System.out.println("linha: "+x);
                System.out.println("coluna: "+(i+1));
                count++;
                break;
            }
        }
        if (count==0){
            System.out.println("nao tem barco nessa linha");
        }
    }
    public static void p1xbot(){

    }

    public static void escolherOpcaoDeModos(){
        Scanner ler = new Scanner(System.in);
        String op = "x";
        while (!(op.equals("1")||op.equals("2")||op.equals("3"))){
            System.out.println("voce quer navios em posicoes aleatoria ou quer escolher as posicoes do navio\nmodo aletorio(1) escolher posicoes(2)");
            op=ler.next();
            switch (op){

                case "1" -> {
                    modoAletorios(nomeJogador1, tabuleiroJogador1);
                    modoAletorios(nomeJogador2, tabuleiroJogador2);
                }
                case "2" ->{
                    modoEscolha(nomeJogador1,tabuleiroJogador2);
                    modoEscolha(nomeJogador2,tabuleiroJogador1);
                }
                case "3" -> System.out.println("voltando");
                default -> System.out.println("erro!!");
            }
        }
    }
    public static void modoAletorios(String nome,String[][] x){
        System.out.println("modo aleatorio");
        Random aleatorio = new Random();
        int count = 0;
        while (count < quantidadeBarcos){
            possibilidade = aleatorio.nextInt(1,4);
            linha = aleatorio.nextInt(1,tamanhoTabuleiro-1);
            coluna = aleatorio.nextInt(1, tamanhoTabuleiro-1);
            verificaCasaVazia(x, possibilidade, linha, coluna);
            while (!(verificaCasaVazia(x, possibilidade, linha, coluna))){
                linha=aleatorio.nextInt(1, tamanhoTabuleiro-1);
                coluna=aleatorio.nextInt(1, tamanhoTabuleiro-1);
                verificaCasaVazia(x, possibilidade, linha, coluna);
            }
            geraEscolha(x, possibilidade, linha, coluna);
            imprimirTabuleiro(tabuleiroJogador1, tabuleiroJogador2);
            count++;
        }
    }
/*    public static boolean confereBarcos (String[][] x){
        int count = 0;
        for (int i = 0; i < tamanhoTabuleiro; i++) {
            for (int j = 0; j < tamanhoTabuleiro; j++) {
                if (x[i][j] != casaVazia){
                    count++;
                }
            }
        }
        if (count < navioTamanho*quantidadeBarcos){
            return false;
        }else return true;
    }*/
    public static void modoEscolha(String nome,String[][] x){
        System.out.println("modo escolha");
        int count = 0;
        imprimirTabuleiro(jogoDoJogador1,jogoDoJogador2);
        System.out.println(nome+" escolha 4 barcos para o jogo do seu adversário");
        while (count < quantidadeBarcos) {
            System.out.println("escolha se vc que colocar na 1)linha ou na 2)coluna ou na 3)diagonal");
            possibilidade=input.nextInt();
            while (possibilidade < 1 || possibilidade > 3){
                System.out.println("Número não aceito! Tente um número entre 1 e 3!");
                possibilidade=input.nextInt();
            }
            System.out.println("em qual linha e em qual coluna voce quer colocar o barco");
            linha=input.nextInt()-1;
            coluna=input.nextInt()-1;
            verificaCasaVazia(x, possibilidade, linha, coluna);
            if (!(verificaCasaVazia(x, possibilidade, linha, coluna))){
                System.out.println("Não cabe um barco aqui! Escolha novamente!");
                System.out.println("em qual linha e em qual coluna voce quer colocar o barco");
                linha=input.nextInt()-1;
                coluna=input.nextInt()-1;
            }
            geraEscolha(x, possibilidade, linha, coluna);
            imprimirTabuleiro(tabuleiroJogador1, tabuleiroJogador2);
            count++;
        }
    }
    public static boolean verificaCasaVazia (String[][] jogo,int x,int y,int z) {
        int count = 0;
        boolean t = false;
        if (x == 1) {
            if (verificaEspacoBarcoLinha(jogo, y, z)){
                for (int j = 0; j < navioTamanho; j++) {
                    if (jogo[y][z + j].equals(casaVazia)) {
                        count++;
                    }
                }
            }
            if (!(verificaEspacoBarcoLinha(jogo, y, z))){
                for (int j = 0; j < navioTamanho; j++) {
                    if (jogo[y][z - j].equals(casaVazia)) {
                        count++;
                    }
                }
            }
            if (count >=  navioTamanho){
                return t = true;
            }else {
                return t = false;
            }
        }
        if (x == 2) {
            if (verificaEspacoBarcoColuna(jogo, y, z)){
                for (int j = 0; j < navioTamanho; j++) {
                    if (jogo[y + j][z].equals(casaVazia)) {
                        count++;
                    } else return t = false;
                }
            }
            if (!(verificaEspacoBarcoColuna(jogo, y, z))){
                for (int j = 0; j < navioTamanho; j++) {
                    if (jogo[y - j][z].equals(casaVazia)) {
                        count++;
                    } else return t = false;
                }
            }
            if (count >=  navioTamanho){
                return t = true;
            }else return t = false;
        }
        if (x == 3) {
            t = true;
        }
        return t;
    }

    public static void geraEscolha (String[][] jogo,int x,int y,int z){
        if (x==1) {
            if (verificaEspacoBarcoLinha(jogo, y, z)){
                jogo[y][z]="|";
                jogo[y][z+navioTamanho-1]="|";
                for (int j = 1; j < navioTamanho-1; j++)
                    jogo[y][z + j] = "-";
            }
            else if (!verificaEspacoBarcoLinha(jogo, y, z)){
                jogo[y][z]="|";
                jogo[y][z-(navioTamanho-1)]="|";
                for (int j = 1; j < navioTamanho-1; j++)
                    jogo[y][z - j] = "-";
            }
        }
        if (x==2) {
            if (verificaEspacoBarcoColuna(jogo, y, z)){
                jogo[y+navioTamanho-1][z]="|";
                jogo[y][z]="|";
                for (int j = 1; j < navioTamanho-1; j++)
                    jogo[y+j][z] = "-";

            }
            else if (!verificaEspacoBarcoColuna(jogo, y, z)){
                jogo[y-(navioTamanho-1)][z]="|";
                jogo[y][z]="|";
                for (int j = 1; j < navioTamanho-1; j++)
                    jogo[y-j][z] = "-";
            }
        }
        if (x==3){
             if (verificaEspacoDiagonalPrincipal1(jogo, y, z)){
                jogo[y][z] = "|"; // diagonal principal pra baixo
                jogo[y+(navioTamanho-1)][z+(navioTamanho-1)] = "|";
                for (int i = 1; i < navioTamanho-1; i++) {
                    jogo[y+i][z+i] = "-";
                }
            } else if (verificaEspacoDiagonalSecundaria1(jogo, y, z)){
                jogo[y][z] = "|"; // diagonal secundária pra baixo
                jogo[y+navioTamanho-1][z-navioTamanho+1] = "|";
                for (int i = 1; i < navioTamanho-1; i++) {
                    jogo[y+i][z-i] = "-";
                }
            } else if (verificaEspacoDiagonalPrincipal2(jogo, y, z)){
                 jogo[y][z] = "|"; //diagonal principal pra cima
                 jogo[y-navioTamanho+1][z-navioTamanho+1] = "|";
                 for (int i = 1; i < navioTamanho-1; i++) {
                     jogo[y-i][z-i] = "-";
                 }
             } else if (verificaEspacoDiagonalSecundaria2(jogo, y, z)){
                 jogo[y][z] = "|"; //diagonal secundária pra cima
                 jogo[y-navioTamanho+1][z+navioTamanho-1] = "|";
                 for (int i = 1; i < navioTamanho-1; i++) {
                     jogo[y-i][z+i] = "-";
                 }
             }
        }
    }

    public static boolean verificaEspacoBarcoLinha(String[][] jogo,int y,int z){
        boolean t = false;
        if (z+navioTamanho>tamanhoTabuleiro){
            t = false;
        }
        else if (z+navioTamanho<=tamanhoTabuleiro){
            t = true;
        }
        return t;
    }
    public static boolean verificaEspacoBarcoColuna(String[][] jogo,int y,int z){
        boolean t = false;
        if (y+navioTamanho>tamanhoTabuleiro){
            t = false;
        }
        else if (y+navioTamanho<=tamanhoTabuleiro){
            t = true;
        }
        return t;
    }
    public static boolean verificaEspacoDiagonalPrincipal1(String[][] jogo,int y,int z){
        int count = 0;
        boolean t = false;
        if (y+navioTamanho>tamanhoTabuleiro && z+navioTamanho>tamanhoTabuleiro){// diagonal principal pra baixo
            t = false;
        }
        else if (y+navioTamanho<=tamanhoTabuleiro && z+navioTamanho<=tamanhoTabuleiro){
            for (int j = 0; j < navioTamanho; j++) {
                if (jogo[y + j][z + j].equals(casaVazia)) {// diagonal principal pra baixo
                    count++;
                }
            }if (count >=  navioTamanho){
                return t = true;
            }else {
                return t = false;
            }
        }
        return t;
    }
    public static boolean verificaEspacoDiagonalSecundaria1(String[][] jogo,int y,int z){
        int count = 0;
        boolean t = false;
        if (y+navioTamanho>tamanhoTabuleiro && z-navioTamanho<0){
            t = false;
        }
        else if (y+navioTamanho<=tamanhoTabuleiro && z-navioTamanho>0){ // diagonal secundária pra baixo
            for (int j = 0; j < navioTamanho; j++) {
                 if (jogo[y + j][z - j].equals(casaVazia)) { // diagonal secundária pra baixo
                    count++;
                }
            }if (count >=  navioTamanho){
                return t = true;
            }else {
                return t = false;
            }
        }
        return t;
    }
    public static boolean verificaEspacoDiagonalPrincipal2(String[][] jogo,int y,int z){
        int count = 0;
        boolean t = false;
        if (y-navioTamanho<0 && z-navioTamanho<0){ //diagonal principal pra cima
            t = false;
        }
        else if(y-navioTamanho>0 &&z-navioTamanho>0){
            for (int j = 0; j < navioTamanho; j++) {
                if (jogo[y - j][z - j].equals(casaVazia)) { //diagonal principal pra cima
                    count++;
                }
            }if (count >=  navioTamanho){
                return t = true;
            }else {
                return t = false;
            }
        }
        return t;
    }
    public static boolean verificaEspacoDiagonalSecundaria2(String[][] jogo,int y,int z){
        int count = 0;
        boolean t = false;
        if (z+navioTamanho>tamanhoTabuleiro && y-navioTamanho<0){ //diagonal secundária pra cima
            t = false;
        }
        else if (y-navioTamanho>0 && z+navioTamanho<=tamanhoTabuleiro){
            for (int j = 0; j < navioTamanho; j++) {
               if (jogo[y - j][z + j].equals(casaVazia)){ //diagonal secundária pra cima
                    count++;
                }
            }if (count >=  navioTamanho){
                return t = true;
            }else {
                return t = false;
            }
        }
        return t;
    }

    public static void imprimirTextoDeAjuda(){
        // /, limiteMaximoDeNavios;/
        String textodeajuda =
                """
                        |  Cada jogador, na sua vez de      |
                        | jogar, seguira o seguinte         |
                        | procedimento:                     |
                        | 1. Disparara 3 tiros, indicando   |
                        | a coordenadas do alvo através     |
                        | do numero da linha e da letra da  |
                        | coluna que definem a posição.     |
                        | Para que o jogador tenha o        |
                        | controle dos tiros disparados,    |
                        | devera marcar cada um deles no    |
                        | reticulado intitulado Seu :jogo:. |
                        | 2. Apos cada um dos tiros, o;     |
                        | oponente avisara se acertou e,    |
                        | nesse caso, qual a arma foi       |
                        | atingida. Se ela for afundada,    |
                        | esse fato também devera ser       |
                        | informado.                        |
                        | 3. A cada tiro acertado em um     |
                        | alvo, o oponente devera marcar    |
                        | em seu tabuleiro para que possa   |
                        | informar quando a arma for        |
                        | afundada.                         |
                        | 4. Uma arma e afundada            |
                        | quando todas as casas que         |
                        | formam essa arma forem            |
                        | atingidas.                        |
                        |5. Apos os 3 tiros e as respostas  |
                        | do opoente, a vez para para o     |
                        | outro jogador.                    |
                        | O jogo termina quando um dos      |
                        | jogadores afundar todas as        |
                        | armas do seu oponente.            |""";

        String titulo = "|  ~ ~ ~ ~ __ ~ ajuda ~ __ ~ ~ ~ ~  |",
               linhas = "|-----------------------------------|";
        System.out.print(linhas + "\n" + titulo + "\n" + linhas + "\n" + textodeajuda +"\n" +linhas + "\n");
    }

    public static void voltaraomenuprincipal(){
    String escolha = "x", lin ="|-----------------------------------|";
    imprimirTextoDeAjuda();
    while (!(escolha.equals("1"))){

        System.out.print("| didite (1) para voltar ao menu    |\n"+lin);
        escolha = input.next();
        if ("1".equals(escolha)) {
            System.out.println("|voltando!!\t\t\t\t\t\t\t|");
        } else {
            System.out.println("| erro!!!\t\t\t\t\t\t\t|");
        }
    }
}

    public static void creditos(){
        String lin ="|-----------------------------------|" ,tit= "\n|   ~ ~ ~ __   creditos  __ ~ ~ ~   |",
                n1 ="\n| * ~ * ~ -Ariana Mesquita- ~ * ~ * |",
                n2 ="\n|\t\t\t\t\t\t\t\t\t|\n| <....> -Jose Victor Rojas- <....> |",
                n3 ="\n|\t\t\t\t\t\t\t\t\t|\n|    ~ _ - Pedro  Ferreira - _ ~    |\n";
        System.out.println(lin+tit+"\n"+lin+"\n|jogo desenvolvido por:             |"+n1+n2+n3+lin);
        voltaraomenuprincipal();

    }
    public static void sair(){
        String escolha="x", lin ="\n|-----------------------------------|";
        while (!(escolha.equals("2"))){
            System.out.print("| deseja sair do jogo? sim(1) nao(2)|"+lin);
            escolha = input.next();
            switch (escolha){
                case "1" -> {
                    System.out.println("|   saindo..... ate a proxima!!:)   |");
                    System.exit(0);
                }
                case "2"-> System.out.println("|voltando!!\t\t\t\t\t\t\t|");
                default -> System.out.println("| erro!!!\t\t\t\t\t\t\t|");}}

        System.out.println("hello world");


    }

    public static void obterNomesDosJogadores() {
        System.out.println("Digite o nome do Jogador 1: ");
        nomeJogador1 = input.next();
        System.out.println("Digite o nome do Jogador 2: ");
        nomeJogador2 = input.next();
    }

    public static void inicializarTabuleiro(String[][] x){
        for (int i = 0; i < tamanhoTabuleiro ; i++){
            Arrays.fill(x[i],casaVazia);
        }
    }

    public static void imprimirTabuleiro(String [][] y,String [][] x){
        System.out.printf("\t\t\t %s \t\t\t\t\t\t\t\t\t\t %s \n",nomeJogador1,nomeJogador2);
        for (int i = 1; i <=tamanhoTabuleiro; i++)
            System.out.printf("\t%2s", i );
        System.out.print("              ");
        for (int i = 1; i <=tamanhoTabuleiro; i++)
            System.out.printf("\t%s", i );
        System.out.println();
        for (int i = 0; i < tamanhoTabuleiro; i++)
        {
            System.out.printf("%3s| ",(i+1));
            for (int j = 0; j < tamanhoTabuleiro; j++)
            {
                System.out.printf("%s\t",y[i][j]);
            }
            System.out.print("           ");
            System.out.printf("%3s|",(i+1));
            for (int j = 0; j < tamanhoTabuleiro; j++)
            {
                System.out.printf("\t%s", x[i][j]);
            }
            System.out.println();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        menuPrincipal();

    }
}
