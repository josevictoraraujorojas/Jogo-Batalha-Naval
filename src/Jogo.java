import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Jogo{

    static String nomeJogador1, nomeJogador2;
    final static int tamanhoTabuleiro = 7; //se vc colocar para o usuario escolher, deixa do jeito que t?
    final static int navioTamanho = 4;// se nao, precisa ser final e com letra maiuscula
    final static int quantidadeBarcos = 4;
    final static int quantidadeTentativas = (quantidadeBarcos*navioTamanho)+5; //cuidado pq o usuario nao tem bola de cristal!

    static String[][] tabuleiroJogador1 = new String[tamanhoTabuleiro][tamanhoTabuleiro];
    static String[][] tabuleiroJogador2 = new String[tamanhoTabuleiro][tamanhoTabuleiro];
    static String[][] jogoDoJogador1 = new String[tamanhoTabuleiro][tamanhoTabuleiro];
    static String[][] jogoDoJogador2 = new String[tamanhoTabuleiro][tamanhoTabuleiro];
    static int[] tentativasP1 = {quantidadeTentativas};
    static int[] tentativasP2 = {quantidadeTentativas};
    static int[] jogadasP1 = new int[1];
    static int[] jogadasP2 = new int[1];
    static int[] parteBarcoP1 = new int[1];
    static int[] parteBarcoP2 = new int[1];
    static Scanner input = new Scanner(System.in);
    final static String casaVazia = "*";
    static int linha;
    static int coluna;
    static int possibilidade;
    static Random aleatorio = new Random();

    public static void imprimirMenu(){ //metodo que imprime o menu para o usu?rio//
        String  tit = "|   ~ __ ~ Batalha  Naval ~  __ ~   |",
                lin = "|-----------------------------------|",
                jo = "jogar(1)", aj="ajuda(2)",cr="creditos(3)",
                sr = "\t\t\t|\n|\t\t\t\tsair(4)";

        System.out.println(lin+"\n"+tit+"\n"+lin+"\n|\t\t\t   "+jo+"\t\t\t\t|\n|\t\t\t   "
                +aj+"\t\t\t\t|\n|\t\t      "+cr+sr+"\t\t\t\t|\n"+lin);

    }
    public static void rodarMenuPrincipal() throws InterruptedException {//chama o metodo imprimir menu e controla a intera??o do usu?rio//
        jogadasP1[0]=0;
        jogadasP2[0]=0;
        tentativasP2[0]=quantidadeTentativas;
        tentativasP1[0]=quantidadeTentativas;

        String escolha="x",lin ="|-----------------------------------|";
        while (!(escolha.equals("4"))){
            imprimirMenu();
            System.out.print("|\t digite um numero de 1 a 4: \t|");
            escolha = input.next();
            switch (escolha) {
                case "1" -> modosDoJogo();
                case "2" -> MenuAjuda();//-> op??o ajuda********
                case "3" -> lerCreditos();
                case "4" -> sair();
                default -> System.out.println("|erro!\t\t\t\t\t\t\t\t|\n"+lin);
            }
        }
    }
    public static void imprimirModosDoJogo(){//define qual vai ser o modo de jogo
        String tit = "|   ~ __ ~ opcoes  do jogo ~ __ ~   |",
                lin = "|-----------------------------------|",
                mul = "multiplayer(1)", sin = "single player(2)",
                vmp = "voltar ao menu principal(3)";

        System.out.println(lin+"\n"+tit+"\n"+lin+"\n|\t\t\t"+mul+
                "\t\t\t|\n|\t\t   "+sin+" \t\t|\n|\t "+vmp+"\t|\n"+lin);
    }
    public static void modosDoJogo() throws InterruptedException {
        String escolha="x",lin ="|-----------------------------------|";
        imprimirModosDoJogo();
        while (!(escolha.equals("1")||escolha.equals("2")||escolha.equals("3"))) {
            System.out.print("|     digite um numero de 1 a 3     |");
            escolha = input.next();
            switch (escolha) {
                case "1" -> jogador1xJogador2(); //muda o nome do metodo jogarMultiplayer
                case "2" -> jogador1xComputador(); //muda nome jogarSinglePlayer (contra o computador)
                case "3" -> System.out.println(lin+"\n|voltando!!\t\t\t\t\t\t\t|");
                default -> System.out.println("| erro!!!                           |");
            }
        }
    }
    public static void iniciarJogos(){
        inicializarTabuleiro(tabuleiroJogador1);
        inicializarTabuleiro(tabuleiroJogador2);
        inicializarTabuleiro(jogoDoJogador1);
        inicializarTabuleiro(jogoDoJogador2);
        obterNomesDosJogadores();
    }
    public static void jogador1xJogador2() throws InterruptedException {
        iniciarJogos();
        escolherOpcaoDeModos();
        iniciarCombate(true,true);
    }
    public static void iniciarCombate(boolean modop1,boolean modop2) throws InterruptedException { // modop1 colocar no booleano
        int derrotajogador1=0;
        int derrotajogador2=0;
        int vezJogador1=0;
        int vezJogador2=0;
        imprimirTabuleiro(jogoDoJogador1,jogoDoJogador2);
        while (parteBarcoP1[0]!= (navioTamanho * quantidadeBarcos) && parteBarcoP2[0] != (navioTamanho*quantidadeBarcos)){
            vezJogador2++;
            vezJogador1++;

            if (jogadasP1[0]>=tentativasP1[0]){
                derrotajogador1++;
            }
            if (jogadasP2[0]>=tentativasP2[0]){
                derrotajogador2++;
            }

            if (vezJogador2%2==0 && derrotajogador2==0) {
                rodarCombate(modop1,nomeJogador2,jogadasP2,tentativasP2, parteBarcoP2, jogoDoJogador2, tabuleiroJogador2);
            }
            else if (vezJogador1%2!=0 && derrotajogador1==0) {
                rodarCombate(modop2,nomeJogador1,jogadasP1,tentativasP1, parteBarcoP1, jogoDoJogador1, tabuleiroJogador1);
            }
            else if (jogadasP1[0]>=tentativasP1[0]&&jogadasP2[0]>=tentativasP2[0]){
                break;
            }
        }
        if (parteBarcoP1[0]==navioTamanho*quantidadeBarcos){
            System.out.println(nomeJogador1+ """
                                                 | parabens marinheiro voc? destruiu |
                                                 | todos os navios                   |
                                                                                       """);        }
        else if (parteBarcoP2[0]==navioTamanho*quantidadeBarcos){
            System.out.println(nomeJogador2+ """
                                                 | parabens marinheiro voc? destruiu |
                                                 | todos os navios                   |
                                                                                       """);        }
        else {
            System.out.println("""
                                  | Os dois marinheiros foram         |
                                  | destruidos                       |""");
        }
    }
    public static void esperarUmMinuto() throws InterruptedException {
        Thread.sleep(1000);
    }
    public static void rodarCombate(boolean modo,String nome,int[] jogada,int[] tentativas,int[] parteBarco,String[][] jogo,String[][] hack) throws InterruptedException {
        boolean[] escolheLocalBomba={true};
        String resposta , lin ="|-----------------------------------|";
        System.out.println("\n  *__*  jogada do jogador: "+nome+  " *__*");
        //faz a jogada
        jogada[0]++;
        esperarUmMinuto();
        System.out.println(lin+"\n"+"| Jogada: " + jogada[0]+"\t\t\t\t\t\t    |");
        System.out.printf(lin+"\n"+"""
                | voce tem esta quantidade de       |
                | tentativas para ganhar:%s         |
                """,tentativas[0]);
        esperarUmMinuto();
        if (modo) {
            System.out.print(lin+"\n"+"| voce quer ir a loja? se sim       |\n|   digite (sim)     digite (nao)   |");
            resposta = input.next();
            while (!(resposta.equals("sim")) && !resposta.equals("nao")) {
                System.out.println("| resposta invalida!!!              |\n| digite novamente                  |");
                resposta = input.next();
            }
            if (resposta.equals("sim")) {
                irParaLoja(escolheLocalBomba, parteBarco, jogo, hack, tentativas);
            }
            esperarUmMinuto();
            if (escolheLocalBomba[0]) {
                System.out.print(lin + "\n" + """
                        | escolha uma coordenada para jogar |
                        | a bomba                           |""");

                linha = input.nextInt() - 1;
                coluna = input.nextInt() - 1;
                while (linha<=-1||linha >= tamanhoTabuleiro||coluna<=-1||coluna >= tamanhoTabuleiro){
                System.out.println("| erro!!!                           |");
                    System.out.print(lin + "\n" + """
                        | escolha uma coordenada para jogar |
                        | a bomba                           |""");

                    linha = input.nextInt() - 1;
                    coluna = input.nextInt() - 1;
                }
                esperarUmMinuto();
                //verifica se a jogada ? diferente
                while (!avaliarDiferentesJogadas(linha, coluna, jogo)) {
                    System.out.print(lin + "\n" + """
                            | Jogada ja feita, escolha outra    |
                            | jogada                            |""");
                    System.out.print("\n"+lin + "\n" + "| Escolha a linha e a coluna        |");
                    linha = input.nextInt() - 1;
                    coluna = input.nextInt() - 1;
                }
            }
            esperarUmMinuto();
        }
        if (!modo) {
            System.out.print(lin+"\n"+"""
                | escolha uma coordenada para jogar |
                | a bomba                           |""");
            linha = aleatorio.nextInt(0, tamanhoTabuleiro);
            coluna = aleatorio.nextInt(0, tamanhoTabuleiro);
            esperarUmMinuto();
            //verifica se a jogada ? diferente
            while (!avaliarDiferentesJogadas(linha, coluna, jogo)) {
                System.out.print(lin+"\n"+"""
                            | Jogada ja feita, escolha outra    |
                            | jogada                            |""");
                System.out.print(lin+"\n"+"| Escolha a linha e a coluna        |");
                linha = aleatorio.nextInt(0, tamanhoTabuleiro);
                coluna = aleatorio.nextInt(0, tamanhoTabuleiro);
            }
            esperarUmMinuto();
        }
        //verifica se alguma parte do barco foi encontrada
        if (escolheLocalBomba[0]) {
            informaPartesEbombasEncontradas(linha,coluna,parteBarco,hack,jogo,lin);
        }
        esperarUmMinuto();
        imprimirTabuleiro(jogoDoJogador1,jogoDoJogador2);
    }
    public static void informaPartesEbombasEncontradas(int linha,int coluna,int[]parteBarco,String[][] hack,String[][] jogo,String lin) throws InterruptedException {
        if (partesNaviosEncontradas(linha, coluna, hack)) {
            parteBarco[0]++;
            esperarUmMinuto();
            System.out.println(lin+"\n"+"| total de partes destruidas: " + parteBarco[0]+"       |");
            esperarUmMinuto();
            mostrarBarco(linha, coluna, jogo, hack);
            esperarUmMinuto();
        }
        //se nenhuma parte foi encontrada ele considera como bomba
        else {
            mostrarBomba(linha, coluna, jogo);
            esperarUmMinuto();
            System.out.print(lin+"\n| Nenhum barco encontrado!          |\n"+lin+"\n");
        }
    }
    public static boolean avaliarDiferentesJogadas(int f, int c,String[][] jogo){
        return jogo[f][c].equals("*");
    }
    //atribui o valor do barco
    public static void mostrarBarco (int f,int c,String[][] jogo,String[][] hack)
    {
        jogo[f][c]=hack[f][c];
    }
    //atribui o valor bomba para o indice
    public static void mostrarBomba (int f,int c,String[][] jogo)
    {
        jogo[f][c]="O";
    }
    //verifica se alguma parte foi encontrada
    public static boolean partesNaviosEncontradas(int f, int c,String[][] hack){
        return hack[f][c].equals("-") || hack[f][c].equals("|") ;
    }
    public static void irParaLoja (boolean[]escolheBomba,int[]parteBarco,String[][] jogo,String[][]hack,int[] tentativas) throws InterruptedException {
        int resposta=0; String lin ="|-----------------------------------|";
        while (resposta!=1&&resposta!=2) {
            System.out.print(lin+"\n|-~-~-~-~-~-~-~-Loja$-~-~-~-~-~-~-~-|\n"+lin+"\n");
            System.out.printf("""
                                  | Bem vindo a nossa loja, aqui o seu  |
                                  | dinheiro sao suas tentativas!!      |
                                  %s
                                  |   Dicas(1)    |R$ -4 tentativas     |
                                  | Litle Boy(2)  |R$ -6 tentativas     |
                                  %s""",lin,lin);
            System.out.print("\n| escolha                           |");
            resposta = input.nextInt();
            switch (resposta) {
                case 1-> {
                    int linha;
                    System.out.print(lin+"\n|informe a linha que voce quer saber|" +
                            "\n| se tem barco                      |");
                    linha = input.nextInt() - 1;
                    while (linha<=-1||linha >= tamanhoTabuleiro){
                        System.out.println("| erro!!!                           |");
                        System.out.print(lin + "\n" + """
                        | escolha uma coordenada para jogar |
                        | a bomba                           |""");
                        linha = input.nextInt() - 1;
                        coluna = input.nextInt() - 1;
                    }
                    darDicas(linha, hack);
                    tentativas[0] -= 4;
                }
                case 2->{
                    int linha;
                    int coluna;
                    System.out.println("| Ativando  bomba            |");
                    System.out.println("""
                                            | Escolha a linha e a coluna em que |
                                            | voce que soltar a Little Boy      |
                                            """);
                    System.out.println("*X* sao aceitas as colunas de 0 a "+(tamanhoTabuleiro-navioTamanho)+"*X*");
                    System.out.println("| informe a linha e a coluna        |");
                    linha=input.nextInt()-1;
                    coluna=input.nextInt()-1;
                    while (coluna>=(tamanhoTabuleiro-navioTamanho)){
                        System.out.println("""
                                        | valor nao aceito!!!               |
                                        | informe a linha e a coluna:       |
                                                                               """);
                        linha=input.nextInt()-1;
                        coluna=input.nextInt()-1;
                        while (linha<=-1||linha >= tamanhoTabuleiro||coluna<=-1||coluna >= tamanhoTabuleiro){
                            System.out.println("erro");
                            System.out.print(lin + "\n" + """
                        | escolha uma coordenada para jogar |
                        | a bomba                           |""");

                            linha = input.nextInt() - 1;
                            coluna = input.nextInt() - 1;
                        }
                    }
                    System.out.println("| codigo: 00000000                  |");
                    jogarBomba(parteBarco,linha,coluna,jogo,hack,lin);
                    tentativas[0] -= 6;
                    escolheBomba[0]=false;
                }
                default ->  System.out.println(lin+"\n| resposta invalida                 |");
            }
        }
    }
    public static void jogarBomba(int[]parteBarco,int linha,int coluna,String[][] jogo,String[][] hack,String lin) throws InterruptedException {
        for (int i = coluna; i <= navioTamanho+coluna ; i++) {
            informaPartesEbombasEncontradas(linha,i,parteBarco,hack,jogo,lin);
        }
    }
    public static void darDicas(int x,String[][] hack) {
        int count =0; String lin ="|-----------------------------------|";
        for (int i = 0; i < tamanhoTabuleiro ; i++) {
            if (hack[x][i].equals("-")||hack[x][i].equals("|")){
                System.out.println(lin+"| dica da linha                     |\n"+lin);
                System.out.println("| linha: "+(x+1)+"                          |\n"+lin);
                System.out.println("| coluna: "+(i+1)+"                         |\n"+lin);
                count++;
                break;
            }
        }
        if (count==0){
            System.out.println("| nao tem barco nessa linha...      |");
        }
    }
    public static void jogador1xComputador() throws InterruptedException {
        iniciarJogos();
        jogarAleatorio(nomeJogador1, tabuleiroJogador1);
        jogarAleatorio(nomeJogador2, tabuleiroJogador2);
        iniciarCombate(false,true);
    }
    public static void escolherOpcaoDeModos(){
        Scanner ler = new Scanner(System.in);
        String op = "x", lin ="|-----------------------------------|";
        while (!(op.equals("1")||op.equals("2")||op.equals("3"))){
            System.out.print(lin+"\n|  voce quer navios em posicoes     |\n"+
                    "|  aleatoria ou quer escolher as    |\n"+
                    "|  posicoes do navio?               |\n"+
                    "|  posicoes aletorias(1)            |\n"+
                    "|  escolher posicoes(2)             |\n"+lin);
            op=ler.next();
            switch (op){
                case "1" -> {
                    jogarAleatorio(nomeJogador1, tabuleiroJogador1);
                    jogarAleatorio(nomeJogador2, tabuleiroJogador2);
                }
                case "2" ->{
                    escolherJogoAdversario(nomeJogador1,tabuleiroJogador2);
                    escolherJogoAdversario(nomeJogador2,tabuleiroJogador1);
                }
                case "3" -> System.out.println("| voltando                          |");
                default -> System.out.println( "| erro!!!                           |");
            }
        }
    }
    public static void jogarAleatorio(String nome,String[][] x){
        System.out.println("|           modo aleatorio          |\n|-----------------------------------|");
        Random aleatorio = new Random();
        while (confereQuantidadeBarcos(x)){
            possibilidade = aleatorio.nextInt(1,4);
            linha = aleatorio.nextInt(0,tamanhoTabuleiro);
            coluna = aleatorio.nextInt(0, tamanhoTabuleiro);
            verificaCasaVazia(x, possibilidade, linha, coluna);
            while (!(verificaCasaVazia(x, possibilidade, linha, coluna))){
                linha=aleatorio.nextInt(0, tamanhoTabuleiro);
                coluna=aleatorio.nextInt(0, tamanhoTabuleiro);
                verificaCasaVazia(x, possibilidade, linha, coluna);
            }
            gerarEscolha(x, possibilidade, linha, coluna);
            imprimirTabuleiro(tabuleiroJogador1, tabuleiroJogador2);
        }
    }
    public static boolean confereQuantidadeBarcos (String[][]x) {
        int count = 0;
        for (int i = 0; i < tamanhoTabuleiro; i++) {
            for (int j = 0; j < tamanhoTabuleiro; j++) {
                if (!(x[i][j].equals(casaVazia))) {
                    count++;
                }
            }
        }
        return count != navioTamanho * quantidadeBarcos;
    }
    public static void escolherJogoAdversario(String nome,String[][] x){
        String lin =       "|-----------------------------------|";
        System.out.println("|           modo escolha            |\n"+lin);
        imprimirTabuleiro(jogoDoJogador1,jogoDoJogador2);
        System.out.println("\n*__*"+nome+" escolha 4 barcos para o jogo do seu adversario *__*");
        while (confereQuantidadeBarcos(x)) {
            System.out.print(lin+"\n" + """
                                     | escolha se voce que colocar na:   |
                                     |           horizontal(1)           |
                                     |            vertical(2)            |
                                     |            diagonal(3)            |""");
            possibilidade=input.nextInt();
            while (possibilidade < 1 || possibilidade > 3){
                System.out.print("""
                                      | Numero nao aceito!                |
                                      | Tente um numero entre 1 e 3!      |""");
                possibilidade=input.nextInt();
            }
            System.out.print(lin+"\n"+"""
                        | em qual linha e em qual coluna    |
                        | voce quer colocar o barco?        |""");
            linha=input.nextInt()-1;
            coluna=input.nextInt()-1;
            while (linha<=-1||linha >= tamanhoTabuleiro||coluna<=-1||coluna >= tamanhoTabuleiro){
                System.out.println("| erro!!!                           |");
                System.out.print(lin + "\n" + """
                        | escolha uma coordenada para jogar |
                        | a bomba                           |""");

                linha = input.nextInt() - 1;
                coluna = input.nextInt() - 1;
            }
            verificaCasaVazia(x, possibilidade, linha, coluna);
            if (!(verificaCasaVazia(x, possibilidade, linha, coluna))){
                System.out.print("""
                        | Nao cabe um barco aqui!           |
                        | Escolha novamente!                |""");
                System.out.print("\n"+"""
                        | em qual linha e em qual coluna    |
                        | voce quer colocar o barco?        |""");
                linha=input.nextInt()-1;
                coluna=input.nextInt()-1;
                while (linha<=-1||linha >= tamanhoTabuleiro||coluna<=-1||coluna >= tamanhoTabuleiro){
                    System.out.println("erro");
                    System.out.print(lin + "\n" + """
                        | escolha uma coordenada para jogar |
                        | a bomba                           |""");

                    linha = input.nextInt() - 1;
                    coluna = input.nextInt() - 1;
                }
            }
            System.out.println(lin);
            gerarEscolha(x, possibilidade, linha, coluna);
            imprimirTabuleiro(tabuleiroJogador1, tabuleiroJogador2);
        }
    }
    public static boolean verificaCasaVazia (String[][] jogo,int x,int y,int z) {
        int count = 0;
        boolean t = false;
        if (x == 1) {
            if (verificaEspacoBarcoLinha(z)){
                for (int j = 0; j < navioTamanho; j++) {
                    if (jogo[y][z + j].equals(casaVazia)) {
                        count++;
                    }
                }
            }
            if (!(verificaEspacoBarcoLinha(z))){
                for (int j = 0; j < navioTamanho; j++) {
                    if (jogo[y][z - j].equals(casaVazia)) {
                        count++;
                    }
                }
            }
            if (count >=  navioTamanho){
                return t = true;
            }else {
                return false;
            }
        }
        if (x == 2) {
            if (verificaEspacoBarcoColuna(y)){
                for (int j = 0; j < navioTamanho; j++) {
                    if (jogo[y + j][z].equals(casaVazia)) {
                        count++;
                    }
                }
            }
            if (!(verificaEspacoBarcoColuna(y))){
                for (int j = 0; j < navioTamanho; j++) {
                    if (jogo[y - j][z].equals(casaVazia)) {
                        count++;
                    }
                }
            }
            if (count >=  navioTamanho){
                return t = true;
            }else return false;
        }
        if (x == 3) {
            t = true;
        }
        return t;
    }
    public static void gerarEscolha (String[][] jogo,int x,int y,int z){
        if (x==1) {
            if (verificaEspacoBarcoLinha(z)){
                jogo[y][z]="|";
                jogo[y][z+(navioTamanho-1)]="|";
                for (int j = 1; j < navioTamanho-1; j++)
                    jogo[y][z + j] = "-";
            }
            else {
                jogo[y][z]="|";
                jogo[y][z-(navioTamanho-1)]="|";
                for (int j = 1; j < navioTamanho-1; j++)
                    jogo[y][z - j] = "-";
            }
        }
        if (x==2) {
            if (verificaEspacoBarcoColuna(y)){
                jogo[y+navioTamanho-1][z]="|";
                jogo[y][z]="|";
                for (int j = 1; j < navioTamanho-1; j++)
                    jogo[y+j][z] = "-";

            }
            else {
                jogo[y-(navioTamanho-1)][z]="|";
                jogo[y][z]="|";
                for (int j = 1; j < navioTamanho-1; j++)
                    jogo[y-j][z] = "-";
            }
        }
        if (x==3){
            if (verificaEspacoDiagonalPrincipal1(jogo, y, z)){ // diagonal principal para baixo
                jogo[y][z] = "|";
                jogo[y+(navioTamanho-1)][z+(navioTamanho-1)] = "|";
                for (int i = 1; i < navioTamanho-1; i++) {
                    jogo[y+i][z+i] = "-";
                }
            } else if (verificaEspacoDiagonalSecundaria1(jogo, y, z)){ // diagonal secund?ria para baixo
                jogo[y][z] = "|";
                jogo[y+navioTamanho-1][z-navioTamanho+1] = "|";
                for (int i = 1; i < navioTamanho-1; i++) {
                    jogo[y+i][z-i] = "-";
                }
            } else if (verificaEspacoDiagonalPrincipal2(jogo, y, z)){ //diagonal principal para cima
                jogo[y][z] = "|";
                jogo[y-navioTamanho+1][z-navioTamanho+1] = "|";
                for (int i = 1; i < navioTamanho-1; i++) {
                    jogo[y-i][z-i] = "-";
                }
            } else if (verificaEspacoDiagonalSecundaria2(jogo, y, z)){ //diagonal secund?ria para cima
                jogo[y][z] = "|";
                jogo[y-navioTamanho+1][z+navioTamanho-1] = "|";
                for (int i = 1; i < navioTamanho-1; i++) {
                    jogo[y-i][z+i] = "-";
                }
            }
        }
    }
    public static boolean verificaEspacoBarcoLinha(int z){
        boolean t = false;
        if (z+navioTamanho>tamanhoTabuleiro && z-navioTamanho<0){ //verifica se nao extrapola o espa?o
            return false;
        }
        else if (z+navioTamanho<=tamanhoTabuleiro){ //verifica se tem o espa?o
            t = true;
        }
        return t;
    }
    public static boolean verificaEspacoBarcoColuna(int y){
        boolean t = false;
        if (y+navioTamanho>tamanhoTabuleiro && y-navioTamanho<0){ //verifica se nao extrapola o espa?o
            return false;
        }
        else if (y+navioTamanho<=tamanhoTabuleiro){ //verifica se tem o espa?o
            t = true;
        }
        return t;
    }
    public static boolean verificaEspacoDiagonalPrincipal1(String[][] jogo,int y,int z){ //diagonal principal para baixo
        int count = 0;
        boolean t = false;
        if (y+navioTamanho>tamanhoTabuleiro && z+navioTamanho>tamanhoTabuleiro){ //verifica se nao extrapola o tabuleiro
            return false;
        }
        else if (y+navioTamanho<=tamanhoTabuleiro && z+navioTamanho<=tamanhoTabuleiro){ //verifica se tem espaco
            for (int j = 0; j < navioTamanho; j++) {
                if (jogo[y + j][z + j].equals(casaVazia)) {
                    count++;
                }
            }if (count >=  navioTamanho){
                return t = true;
            }else {
                return false;
            }
        }
        return t;
    }
    public static boolean verificaEspacoDiagonalSecundaria1(String[][] jogo,int y,int z){ // diagonal secund?ria para baixo
        int count = 0;
        boolean t = false;
        if (y+navioTamanho>tamanhoTabuleiro && z-navioTamanho<0){
            return false;
        }
        else if (y+navioTamanho<=tamanhoTabuleiro && z-navioTamanho>0){
            for (int j = 0; j < navioTamanho; j++) {
                if (jogo[y + j][z - j].equals(casaVazia)) {
                    count++;
                }
            }if (count >=  navioTamanho){
                return t = true;
            }else {
                return false;
            }
        }
        return t;
    }
    public static boolean verificaEspacoDiagonalPrincipal2(String[][] jogo,int y,int z){ //diagonal principal para cima
        int count = 0;
        boolean t = false;
        if (y-navioTamanho<0 && z-navioTamanho<0){
            return false;
        }
        else if(y-navioTamanho>0 &&z-navioTamanho>0){
            for (int j = 0; j < navioTamanho; j++) {
                if (jogo[y - j][z - j].equals(casaVazia)) {
                    count++;
                }
            }if (count >=  navioTamanho){
                return t = true;
            }else {
                return false;
            }
        }
        return t;
    }
    public static boolean verificaEspacoDiagonalSecundaria2(String[][] jogo,int y,int z){ //diagonal secund?ria para cima
        int count = 0;
        boolean t = false;
        if (z+navioTamanho>tamanhoTabuleiro && y-navioTamanho<0){
            return false;
        }
        else if (y-navioTamanho>0 && z+navioTamanho<=tamanhoTabuleiro){
            for (int j = 0; j < navioTamanho; j++) {
                if (jogo[y - j][z + j].equals(casaVazia)){
                    count++;
                }
            }if (count >=  navioTamanho){
                return t = true;
            }else {
                return false;
            }
        }
        return t;
    }
    public static void MenuAjuda() throws InterruptedException {
        String escolha;
        while (true){

            System.out.print("""
                           |  ~ ~ ~ ~ __ ~ ajuda ~ __ ~ ~ ~ ~  |",
                           |-----------------------------------|
                           | Aonde voce precisa de ajuda?      |
                           |                                   |
                           |         multiplayers(1)           |
                           |         singleplayer(2)           |
                           |             Loja(3)               |
                           |                                   |
                           |    voltar ao menu principal(4)    |""");
            escolha = input.next();
            System.out.println("|-----------------------------------|");
            switch (escolha) {
                case "1" -> {
                    System.out.println("""
                            |---------Ajuda multiplayer---------|
                            |                                   |
                            | O jogo tem proporcoes de 7x7, com |
                            | 4 navios de 4 casas cada.         |
                            | Os jogadores podem optar por,     |
                            | escolher a posicao do barcos do   |
                            | seu adverario ou deixar que o     |
                            | computador escolha por voce.      |
                            | todos os jogadores tem 21         |
                            | tentativas que podem diminuir no  |
                            | decorrer do jogo                  |
                            | Ganha o jogo o primeiro player que|
                            | destruir todos os barcos Se todos |
                            | os players perderem suas          |
                            | tentativas nenhum player ganha e  |
                            | o jogo acaba Se um player perder  |
                            | suas tentativas e o outro player  |
                            | nao o segundo player ira jogar ate|
                            |perder suas tentativas ou ganhar o |
                            | jogo.                             |
                            | Durante o jogo:                   |
                            | Os players terao que informar a   |
                            | linha e a coluna em que querem    |
                            | jogar as bombas Se tiver alguma   |
                            | parte de um barco ali ele         |
                            | informara a quantidade de partes  |
                            | destruidas e mostrara o barco no  |
                            | jogo Se nao tiver barco sera      |
                            | informado que nao tem e mostrara  |
                            | a bomba no mapa Em todas as       |
                            | rodadas ele podera acessar a loja.|
                            |-----------------------------------|""");
                    voltarAoMenuPrincipal("1");
                }
                case "2" -> {
                    System.out.println("""
                            |--------Ajuda single player--------|
                            |                                   |
                            | O jogo tem proporcoes de 7x7, com |
                            | 4 navios de 4 casas cada.         |
                            | o jogador vai enfrentar o         |
                            | computador com navios em posicoes |
                            | aleatorias.                       |
                            | somente o jogador podera acessar  |
                            | a loja. se o jogador gastar todas |
                            | as suas tentativas ele perde.     |
                            | o jogo finaliza quando todos os   |
                            | navios de um dos tabuleiros forem |
                            | destruidos.                       |
                            | Durante o jogo:                   |
                            | O jogador tera que informar a     |
                            | linha e a coluna em que querem    |
                            | jogar as bombas Se tiver alguma   |
                            | parte de um barco ali ele         |
                            | informara a quantidade de partes  |
                            | destruidas e mostrara o barco no  |
                            | jogo Se nao tiver barco sera      |
                            | informado que nao tem e mostrara  |
                            | a bomba no mapa Em todas as       |
                            | rodadas ele podera acessar a loja.|
                            |-----------------------------------|""");
                    voltarAoMenuPrincipal("1");
                }
                case "3" -> {
                    System.out.println(""" 
                            |----------------loja---------------|
                            |                                   |
                            | Durante o jogo sera perguntado ao |
                            | player se ele que ir a loja Ele   |
                            | pode escolher entre ir a loja ou  |
                            | continuar o jogo sem ir nela Se   |
                            | ele for a loja ele tera 2 escolhas|
                            | de itens a dica e a little boy    |
                            | A dica funciona da seguinte       |
                            | maneira o player tera que informar|
                            | a linha em que quer saber se tem  |
                            | barco ou nao O player escolhendo a|
                            | loja vendera 4 tentativas para ter|
                            | a dica A little boy e uma bomba   |
                            | especial onde ela atinge 5 casas  |
                            | de uma vez so, o player tera que  |
                            | informar a linha e a coluna       |
                            | Escolhendo a little boy o player  |
                            | vendera 6 tentativas              |
                            |-----------------------------------|""");
                    voltarAoMenuPrincipal("1");
                }
                case "4"-> rodarMenuPrincipal();
                default ->  System.out.println("| erro!!!\t\t\t\t\t\t\t|");
            }}}

    public static void voltarAoMenuPrincipal(String x ){
        String escolha = "x", lin ="|-----------------------------------|";
        while (!(escolha.equals(x))){

            System.out.print("| digite ("+x+") para voltar ao menu    |\n"+lin);
            escolha = input.next();
            if (x.equals(escolha)) {
                System.out.println("|voltando!!\t\t\t\t\t\t\t|");
            } else {
                System.out.println("| erro!!!\t\t\t\t\t\t\t|");
            }
        }
    }

    public static void lerCreditos(){
        String lin ="|-----------------------------------|" ,tit= "\n|   ~ ~ ~ __   creditos  __ ~ ~ ~   |";

        System.out.println(lin+tit+"\n"+lin+"\n|jogo desenvolvido por:             |\n"+"""
                       | * ~ * ~ -Ariana Mesquita- ~ * ~ * |
                       | <....> -Jose Victor Rojas- <....> |
                       |    ~ _ - Pedro  Ferreira - _ ~    |""");

        voltarAoMenuPrincipal("1");

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
    }
    public static void obterNomesDosJogadores() {
        String lin ="|-----------------------------------|";
        System.out.print(lin+"\n|  Digite o nome do Jogador 1:      |");
        nomeJogador1 = input.next();
        System.out.print(lin+"\n|  Digite o nome do Jogador 2:      |");
        nomeJogador2 = input.next();
    }
    public static void inicializarTabuleiro(String[][] x){
        for (int i = 0; i < tamanhoTabuleiro ; i++){
            Arrays.fill(x[i],casaVazia);
        }
    }
    public static void imprimirTabuleiro(String [][] y,String [][] x){
        System.out.printf("\t\t*__* %s \t\t\t\t\t\t-__- %s \n",nomeJogador1,nomeJogador2);
        System.out.print("|-~-~=~-~-=-~-~=*__*=~-~-=-~-~=~-~-|||-~-~=~-~-=-~-~=*__*=~-~-=-~-~=~-~-|\n|\t\t");
        for (int i = 1; i <=tamanhoTabuleiro; i++)
            System.out.printf("%d\t", i );
        System.out.print("|\t\t");
        for (int i = 1; i <=tamanhoTabuleiro; i++)
            System.out.printf("%s\t", i );
        System.out.println("|");
        for (int i = 0; i < tamanhoTabuleiro; i++)
        {
            System.out.printf("|\t%s|",(i+1));
            for (int j = 0; j < tamanhoTabuleiro; j++)
            {
                System.out.printf("\t%s",y[i][j]);
            }
            System.out.print("\t|\t");
            System.out.printf("%s|",(i+1));
            for (int j = 0; j < tamanhoTabuleiro; j++)
            {
                System.out.printf("\t%s", x[i][j]);
            }
            System.out.print("\t|");
            System.out.println();
        }
        System.out.print("|-~-~=~-~-=-~-~=*__*=~-~-=-~-~=~-~-|||-~-~=~-~-=-~-~=*__*=~-~-=-~-~=~-~-|\n");
    }
    public static void main(String[] args) throws InterruptedException {
        rodarMenuPrincipal();
    }
}