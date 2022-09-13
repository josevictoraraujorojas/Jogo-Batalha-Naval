import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Jogo{

    static String nomeJogador1, nomeJogador2;
    final static int TamanhoTabuleiro = 7; //se vc colocar para o usuario escolher, deixa do jeito que t?
    final static int NavioTamanho = 4;// se nao, precisa ser final e com letra maiuscula
    final static int QuantidadeBarcos = 4;
    final static int QuantidadeTentativas = (QuantidadeBarcos*NavioTamanho)+5;

    static String[][] tabuleiroJogador1 = new String[TamanhoTabuleiro][TamanhoTabuleiro];
    static String[][] tabuleiroJogador2 = new String[TamanhoTabuleiro][TamanhoTabuleiro];
    static String[][] jogoDoJogador1 = new String[TamanhoTabuleiro][TamanhoTabuleiro];
    static String[][] jogoDoJogador2 = new String[TamanhoTabuleiro][TamanhoTabuleiro];
    static int[] tentativasP1 = {QuantidadeTentativas};
    static int[] tentativasP2 = {QuantidadeTentativas};
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
        tentativasP2[0]=QuantidadeTentativas;
        tentativasP1[0]=QuantidadeTentativas;

        String escolha, lin ="|-----------------------------------|";
        while (true){
            imprimirMenu();
            System.out.print("|\t digite um numero de 1 a 4: \t|");
            escolha = input.next();
            switch (escolha) {
                case "1" -> modosDoJogo();
                case "2" -> acessarMenuAjuda();//-> opcao ajuda********
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
    //modo jogador contra jogador
    public static void jogador1xJogador2() throws InterruptedException {
        iniciarJogos();
        escolherOpcaoDeModos();
        iniciarCombate(true,true);
    }
    //faz com que o combate inicie e tenha um final
    public static void iniciarCombate(boolean modop1,boolean modop2) throws InterruptedException { // modop1 colocar no booleano
        int derrotajogador1=0;
        int derrotajogador2=0;
        int vezJogador1=0;
        int vezJogador2=0;
        imprimirTabuleiro(jogoDoJogador1,jogoDoJogador2);
        while (parteBarcoP1[0]!= (NavioTamanho * QuantidadeBarcos) && parteBarcoP2[0] != (NavioTamanho*QuantidadeBarcos)){
            vezJogador2++;
            vezJogador1++;
            if (jogadasP1[0]>=tentativasP1[0]){
                derrotajogador1++;
                System.out.println(nomeJogador1+"\n"+"""
                                                 |voce foi destuido durante o combate|
                                                 | seu barco afundou                   |
                                                                                       """);
            }
            if (jogadasP2[0]>=tentativasP2[0]){
                derrotajogador2++;
                System.out.println(nomeJogador2+"\n"+"""
                                                 |voce foi destuido durante o combate|
                                                 | seu barco afundou                 |
                                                                                       """);
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
        if (parteBarcoP1[0]==NavioTamanho*QuantidadeBarcos){
            System.out.println(nomeJogador1+ """
                                                 | parabens marinheiro voc? destruiu |
                                                 | todos os navios                   |
                                                                                       """);        }
        else if (parteBarcoP2[0]==NavioTamanho*QuantidadeBarcos){
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
    //faz com que espere algum tempo para imprimir algo
    public static void esperarUmSegundo() throws InterruptedException {
        Thread.sleep(1000);
    }
    //faz com que o combate se desenvolva
    public static void rodarCombate(boolean modo,String nome,int[] jogada,int[] tentativas,int[] parteBarco,String[][] jogo,String[][] hack) throws InterruptedException {
        boolean[] escolheLocalBomba={true};
        String resposta , lin ="|-----------------------------------|";
        System.out.println("\n  *__*  jogada do jogador: "+nome+  " *__*");
        //faz a jogada
        jogada[0]++;
        esperarUmSegundo();
        System.out.println(lin+"\n"+"| Jogada: " + jogada[0]+"\t\t\t\t\t\t    |");
        System.out.printf(lin+"\n"+"""
                | voce tem esta quantidade de       |
                | tentativas para ganhar:%s         |
                """,tentativas[0]);
        esperarUmSegundo();
        //modo escolha player
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
            esperarUmSegundo();
            if (escolheLocalBomba[0]) {
                System.out.print("\n"+lin + "\n" + """
                        | escolha uma coordenada para jogar |
                        | a bomba                           |""");

                linha = input.nextInt() - 1;
                coluna = input.nextInt() - 1;
                while (linha<=-1||linha >= TamanhoTabuleiro||coluna<=-1||coluna >= TamanhoTabuleiro){
                System.out.println("| erro!!!                           |");
                    System.out.print(lin + "\n" + """
                        | escolha uma coordenada para jogar |
                        | a bomba                           |""");

                    linha = input.nextInt() - 1;
                    coluna = input.nextInt() - 1;
                }
                esperarUmSegundo();
                //verifica se a jogada e diferente
                while (!avaliarDiferentesJogadas(linha, coluna, jogo)) {
                    System.out.print(lin + "\n" + """
                            | Jogada ja feita, escolha outra    |
                            | jogada                            |""");
                    System.out.print("\n"+lin + "\n" + "| Escolha a linha e a coluna        |");
                    linha = input.nextInt() - 1;
                    coluna = input.nextInt() - 1;
                    while (linha<=-1||linha >= TamanhoTabuleiro||coluna<=-1||coluna >= TamanhoTabuleiro){
                        System.out.println("| erro!!!                           |");
                        System.out.print(lin + "\n" + """
                        | escolha uma coordenada para jogar |
                        | a bomba                           |""");

                        linha = input.nextInt() - 1;
                        coluna = input.nextInt() - 1;
                    }
                }
            }
            esperarUmSegundo();
        }
        //modo de escolha do computador
        if (!modo) {
            System.out.print(lin+"\n"+"""
                | escolha uma coordenada para jogar |
                | a bomba                           |""");
            linha = aleatorio.nextInt(0, TamanhoTabuleiro);
            coluna = aleatorio.nextInt(0, TamanhoTabuleiro);
            esperarUmSegundo();
            //verifica se a jogada e diferente
            while (!avaliarDiferentesJogadas(linha, coluna, jogo)) {
                System.out.print(lin+"\n"+"""
                            | Jogada ja feita, escolha outra    |
                            | jogada                            |""");
                System.out.print(lin+"\n"+"| Escolha a linha e a coluna        |");
                linha = aleatorio.nextInt(0, TamanhoTabuleiro);
                coluna = aleatorio.nextInt(0, TamanhoTabuleiro);
            }
            esperarUmSegundo();
        }
        //verifica se alguma parte do barco foi encontrada
        if (escolheLocalBomba[0]) {
            informaPartesEbombasEncontradas(linha,coluna,parteBarco,hack,jogo,lin);
        }
        esperarUmSegundo();
        imprimirTabuleiro(jogoDoJogador1,jogoDoJogador2);
    }
    //informa as partes destruidas e onde tem bomba
    public static void informaPartesEbombasEncontradas(int linha,int coluna,int[]parteBarco,String[][] hack,String[][] jogo,String lin) throws InterruptedException {
        if (mostrarPartesNaviosEncontradas(linha, coluna, hack)) {
            parteBarco[0]++;
            esperarUmSegundo();
            System.out.println(lin+"\n"+"| total de partes destruidas: " + parteBarco[0]+"     |");
            esperarUmSegundo();
            mostrarBarco(linha, coluna, jogo, hack);
            esperarUmSegundo();
        }
        //se nenhuma parte foi encontrada ele considera como bomba
        else {
            mostrarBomba(linha, coluna, jogo);
            esperarUmSegundo();
            System.out.print(lin+"\n| Nenhum barco encontrado!          |\n"+lin+"\n");
        }
    }
    //avalia se a jogada ja foi feita
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
    public static boolean mostrarPartesNaviosEncontradas(int f, int c,String[][] hack){
        return hack[f][c].equals("-") || hack[f][c].equals("|") ;
    }
    //entra na loja
    public static void irParaLoja (boolean[]escolheBomba,int[]parteBarco,String[][] jogo,String[][]hack,int[] tentativas) throws InterruptedException {
        int resposta=0; String lin ="|-----------------------------------|";
        while (resposta!=1&&resposta!=2) {
            System.out.print(lin+"\n|-~-~-~-~-~-~-~-Loja$-~-~-~-~-~-~-~-|\n"+lin+"\n");
            System.out.printf("""
                                  | Bem vindo a nossa loja, aqui o seu  |
                                  | dinheiro sao suas tentativas!!      |
                                  %s
                                  |   Dicas(1)    |R$ -4 tentativas     |
                                  | Litle Boy(2)  |R$ -4 tentativas     |
                                  %s""",lin,lin);
            System.out.print("\n| escolha                           |");
            resposta = input.nextInt();
            switch (resposta) {
                //dica
                case 1-> {
                    int linha;
                    System.out.print(lin+"\n|informe a linha que voce quer saber|" +
                            "\n| se tem barco                      |");
                    linha = input.nextInt() - 1;
                    while (linha<=-1||linha >= TamanhoTabuleiro){
                        System.out.println("erro");
                        System.out.print(lin+"\n|informe a linha que voce quer saber|" +
                                "\n| se tem barco                      |");
                        linha = input.nextInt() - 1;
                    }
                    darDicas(linha, hack);
                    tentativas[0] -= 4;
                }
                //bomba especial
                case 2->{
                    int linha;
                    int coluna;
                    System.out.println("| Ativando  bomba            |");
                    System.out.println("""
                                            | Escolha a linha e a coluna em que |
                                            | voce que soltar a Little Boy      |
                                            """);
                    System.out.println("*X* sao aceitas as colunas de 0 a "+(TamanhoTabuleiro-2)+"*X*");
                    System.out.println("| informe a linha e a coluna        |");
                    linha=input.nextInt()-1;
                    coluna=input.nextInt()-1;
                    int count=0;
                        while (!verficaSeTemEspacoPraBomba(linha,coluna,jogo)||linha<0||linha >= TamanhoTabuleiro||coluna<0||coluna >= (TamanhoTabuleiro-2)){
                            count++;
                            System.out.println("erro");
                            System.out.print(lin + "\n" + """
                        | escolha uma coordenada para jogar |
                        | a bomba                           |""");
                            if (count==7){
                                System.out.println("erro");
                                System.out.print(lin + "\n" + """
                        | muitas tentativas para colocar a  |
                        | bomba seu protoclo foi cancelado  |""");
                            }

                            linha = input.nextInt() - 1;
                            coluna = input.nextInt() - 1;
                        }
                            System.out.println("| codigo: 00000000                  |");
                            jogarBomba(parteBarco, linha, coluna, jogo, hack, lin);
                            tentativas[0] -= 4;
                            escolheBomba[0] = false;


                }
                default ->  System.out.println(lin+"\n| resposta invalida                 |");
            }
        }
    }
    //verifica se tem lugar para a bomba
    public static boolean verficaSeTemEspacoPraBomba(int linha,int coluna,String[][] jogo){
        int count=0;
        for (int i = coluna; i <= 2+coluna ; i++) {
            if (jogo[linha][i].equals(casaVazia)){
                count++;
            }
        }
       return count==3;
    }
    //joga a bomba especial
    public static void jogarBomba(int[]parteBarco,int linha,int coluna,String[][] jogo,String[][] hack,String lin) throws InterruptedException {
        for (int i = coluna; i <= 2+coluna ; i++) {

            informaPartesEbombasEncontradas(linha,i,parteBarco,hack,jogo,lin);
        }
    }
    //mostra a dica
    public static void darDicas(int x,String[][] hack) {
        int count =0; String lin ="|-----------------------------------|";
        for (int i = 0; i < TamanhoTabuleiro ; i++) {
            if (hack[x][i].equals("-")||hack[x][i].equals("|")){
                System.out.println(lin+"\n"+"| dica da linha                     |\n"+lin);
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
    //jogador contra o computador
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
            linha = aleatorio.nextInt(0,TamanhoTabuleiro);
            coluna = aleatorio.nextInt(0, TamanhoTabuleiro);
            verificaCasaVazia(x, possibilidade, linha, coluna);
            while (!(verificaCasaVazia(x, possibilidade, linha, coluna))){
                linha=aleatorio.nextInt(0, TamanhoTabuleiro);
                coluna=aleatorio.nextInt(0, TamanhoTabuleiro);
                verificaCasaVazia(x, possibilidade, linha, coluna);
            }
            gerarEscolha(x, possibilidade, linha, coluna);
        }
    }
    public static boolean confereQuantidadeBarcos (String[][]x) {
        int count = 0;
        for (int i = 0; i < TamanhoTabuleiro; i++) {
            for (int j = 0; j < TamanhoTabuleiro; j++) {
                if (!(x[i][j].equals(casaVazia))) {
                    count++;
                }
            }
        }
        return count != NavioTamanho * QuantidadeBarcos;
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
            while (linha<=-1||linha >= TamanhoTabuleiro||coluna<=-1||coluna >= TamanhoTabuleiro){
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
                while (linha<=-1||linha >= TamanhoTabuleiro||coluna<=-1||coluna >= TamanhoTabuleiro){
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
        }
    }
    public static boolean verificaCasaVazia (String[][] jogo,int x,int y,int z) { //verifica se ja tem barcos lancados nas casas
        int count = 0;
        boolean t = false;
        if (x == 1) {
            if (verificaEspacoBarcoLinha(z)){ //verifica no sentido crescente da linha, em frente
                for (int j = 0; j < NavioTamanho; j++) {
                    if (jogo[y][z + j].equals(casaVazia)) {
                        count++;
                    }
                }
            }
            if (!(verificaEspacoBarcoLinha(z))){
                for (int j = 0; j < NavioTamanho; j++) { //verifica no sentido decrescente da linha, voltando, caso falte espaco
                    if (jogo[y][z - j].equals(casaVazia)) {
                        count++;
                    }
                }
            }
            if (count >=  NavioTamanho){
                return t = true;
            }else {
                return false;
            }
        }
        if (x == 2) {
            if (verificaEspacoBarcoColuna(y)){ //verifica no sentido crescente da coluna, descendo
                for (int j = 0; j < NavioTamanho; j++) {
                    if (jogo[y + j][z].equals(casaVazia)) {
                        count++;
                    }
                }
            }
            if (!(verificaEspacoBarcoColuna(y))){ //verifica no sentido decrescente da coluna, voltando, caso falte espaco
                for (int j = 0; j < NavioTamanho; j++) {
                    if (jogo[y - j][z].equals(casaVazia)) {
                        count++;
                    }
                }
            }
            if (count >= NavioTamanho){
                return t = true;
            }else return false;
        }
        if (x == 3) { //diagonais verificam casa vazia no metodo de verificacao
            t = true;
        }
        return t;
    }
    public static void gerarEscolha (String[][] jogo,int x,int y,int z){
        if (x==1) {
            if (verificaEspacoBarcoLinha(z)){ //verifica no sentido crescente da linha
                jogo[y][z]="|";
                jogo[y][z+(NavioTamanho-1)]="|";
                for (int j = 1; j < NavioTamanho-1; j++)
                    jogo[y][z + j] = "-";
            }
            else { //verifica no sentido decrescente da linha, voltando
                jogo[y][z]="|";
                jogo[y][z-(NavioTamanho-1)]="|";
                for (int j = 1; j < NavioTamanho-1; j++)
                    jogo[y][z - j] = "-";
            }
        }
        if (x==2) {
            if (verificaEspacoBarcoColuna(y)){ //verifica no sentido crescente da coluna, descendo
                jogo[y+NavioTamanho-1][z]="|";
                jogo[y][z]="|";
                for (int j = 1; j < NavioTamanho-1; j++)
                    jogo[y+j][z] = "-";
            }
            else { //verifica no sentido decrescente da coluna, subindo
                jogo[y-(NavioTamanho-1)][z]="|";
                jogo[y][z]="|";
                for (int j = 1; j < NavioTamanho-1; j++)
                    jogo[y-j][z] = "-";
            }
        }
        if (x==3){
            if (verificaEspacoDiagonalPrincipal1(jogo, y, z)){ // diagonal principal para baixo
                jogo[y][z] = "|";
                jogo[y+(NavioTamanho-1)][z+(NavioTamanho-1)] = "|";
                for (int i = 1; i < NavioTamanho-1; i++) {
                    jogo[y+i][z+i] = "-"; //soma na linha e na coluna
                }
            } else if (verificaEspacoDiagonalSecundaria1(jogo, y, z)){ // diagonal secundaria para baixo
                jogo[y][z] = "|";
                jogo[y+NavioTamanho-1][z-NavioTamanho+1] = "|";
                for (int i = 1; i < NavioTamanho-1; i++) {
                    jogo[y+i][z-i] = "-"; //soma na linha e diminui na coluna
                }
            } else if (verificaEspacoDiagonalPrincipal2(jogo, y, z)){ //diagonal principal para cima
                jogo[y][z] = "|";
                jogo[y-NavioTamanho+1][z-NavioTamanho+1] = "|";
                for (int i = 1; i < NavioTamanho-1; i++) {
                    jogo[y-i][z-i] = "-"; //diminui na linha e na coluna
                }
            } else if (verificaEspacoDiagonalSecundaria2(jogo, y, z)){ //diagonal secundaria para cima
                jogo[y][z] = "|";
                jogo[y-NavioTamanho+1][z+NavioTamanho-1] = "|";
                for (int i = 1; i < NavioTamanho-1; i++) {
                    jogo[y-i][z+i] = "-"; //diminui na linha e aumenta na coluna
                }
            }
        }
    }
    public static boolean verificaEspacoBarcoLinha(int z){
        boolean t = false;
        if (z+NavioTamanho>TamanhoTabuleiro && z-NavioTamanho<0){ //verifica se nao extrapola o tabuleiro
            return false;
        }
        else if (z+NavioTamanho<=TamanhoTabuleiro){ //verifica se tem o espaco no tabuleiro
            t = true;
        }
        return t;
    }
    public static boolean verificaEspacoBarcoColuna(int y){
        boolean t = false;
        if (y+NavioTamanho>TamanhoTabuleiro && y-NavioTamanho<0){ //verifica se nao extrapola o tabuleiro
            return false;
        }
        else if (y+NavioTamanho<=TamanhoTabuleiro){ //verifica se tem o espaco no tabuleiro
            t = true;
        }
        return t;
    }
    public static boolean verificaEspacoDiagonalPrincipal1(String[][] jogo,int y,int z){ //diagonal principal para baixo
        int count = 0;
        boolean t = false;
        if (y+NavioTamanho>TamanhoTabuleiro && z+NavioTamanho>TamanhoTabuleiro){ //verifica se nao extrapola o tabuleiro
            return false;
        }
        else if (y+NavioTamanho<=TamanhoTabuleiro && z+NavioTamanho<=TamanhoTabuleiro){ //verifica se tem o espaco no tabuleiro
            for (int j = 0; j < NavioTamanho; j++) {
                if (jogo[y + j][z + j].equals(casaVazia)) { //verifica se as casas estao vazias no espaco
                    count++;
                }
            }if (count >=  NavioTamanho){
                return t = true;
            }else {
                return false;
            }
        }
        return t;
    }
    public static boolean verificaEspacoDiagonalSecundaria1(String[][] jogo,int y,int z){ // diagonal secundaria para baixo
        int count = 0;
        boolean t = false;
        if (y+NavioTamanho>TamanhoTabuleiro && z-NavioTamanho<0){ //verifica se nao extrapola o tabuleiro
            return false;
        }
        else if (y+NavioTamanho<=TamanhoTabuleiro && z-NavioTamanho>0){ //verifica se tem o espaco no tabuleiro
            for (int j = 0; j < NavioTamanho; j++) {
                if (jogo[y + j][z - j].equals(casaVazia)) {//verifica se as casas estao vazias no espaco
                    count++;
                }
            }if (count >=  NavioTamanho){
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
        if (y-NavioTamanho<0 && z-NavioTamanho<0){//verifica se nao extrapola o tabuleiro
            return false;
        }
        else if(y-NavioTamanho>0 && z-NavioTamanho>0){ //verifica se tem o espaco no tabuleiro
            for (int j = 0; j < NavioTamanho; j++) {
                if (jogo[y - j][z - j].equals(casaVazia)){//verifica se as casas estao vazias no espaco
                    count++;
                }
            }if (count >=  NavioTamanho){
                return t = true;
            }else {
                return false;
            }
        }
        return t;
    }
    public static boolean verificaEspacoDiagonalSecundaria2(String[][] jogo,int y,int z){ //diagonal secundaria para cima
        int count = 0;
        boolean t = false;
        if (z+NavioTamanho>TamanhoTabuleiro && y-NavioTamanho<0){//verifica se nao extrapola o tabuleiro
            return false;
        }
        else if (y-NavioTamanho>0 && z+NavioTamanho<=TamanhoTabuleiro){//verifica se tem o espaco no tabuleiro
            for (int j = 0; j < NavioTamanho; j++) {
                if (jogo[y - j][z + j].equals(casaVazia)){//verifica se as casas estao vazias no espaco
                    count++;
                }
            }if (count >=  NavioTamanho){
                return t = true;
            }else {
                return false;
            }
        }
        return t;
    }
    public static void acessarMenuAjuda() throws InterruptedException {
        String escolha = "x";
        String lin ="|-----------------------------------|";
        while (!escolha.equals("4")){

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
                            | seu adversario ou deixar que o    |
                            | computador escolha por voce.      |
                            | todos os jogadores tem 21         |
                            | tentativas que podem diminuir no  |
                            | decorrer do jogo.                 |
                            | Ganha o jogo o primeiro player que|
                            | destruir todos os barcos. Se todos|
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
                            | jogar as bombas. Se tiver alguma  |
                            | parte de um barco ali ele         |
                            | informara a quantidade de partes  |
                            | destruidas e mostrara o barco no  |
                            | jogo. Se nao tiver barco sera     |
                            | informado que nao tem e mostrara  |
                            | a bomba no mapa. Em todas as      |
                            | rodadas ele podera acessar a loja.|
                            |-----------------------------------|""");
                    retornandoParaOMenuDeAjuda();

                }
                case "2" -> {
                    System.out.println("""
                            |--------Ajuda single player--------|
                            |                                   |
                            | O jogo tem proporcoes de 7x7, com |
                            | 4 navios de 4 casas cada.         |
                            | O jogador vai enfrentar o         |
                            | computador com navios em posicoes |
                            | aleatorias.                       |
                            | Somente o jogador podera acessar  |
                            | a loja. Se o jogador gastar todas |
                            | as suas tentativas ele perde.     |
                            | O jogo finaliza quando todos os   |
                            | navios de um dos tabuleiros forem |
                            | destruidos.                       |
                            | Durante o jogo:                   |
                            | O jogador tera que informar a     |
                            | linha e a coluna em que querem    |
                            | jogar as bombas. Se tiver alguma  |
                            | parte de um barco ali ele         |
                            | informara a quantidade de partes  |
                            | destruidas e mostrara o barco no  |
                            | jogo. Se nao tiver barco sera     |
                            | informado que nao tem e mostrara  |
                            | a bomba no mapa. Em todas as      |
                            | rodadas ele podera acessar a loja.|
                            |-----------------------------------|""");
                    retornandoParaOMenuDeAjuda();

                }
                case "3" -> {
                    System.out.println(""" 
                            |----------------loja---------------|
                            |                                   |
                            | Durante o jogo sera perguntado ao |
                            | player se ele que ir a loja. Ele  |
                            | pode escolher entre ir a loja ou  |
                            | continuar o jogo sem ir nela. Se  |
                            | ele for a loja ele tera 2 escolhas|
                            | de itens a dica e a little boy.   |
                            | A dica funciona da seguinte       |
                            | maneira o player tera que informar|
                            | a linha em que quer saber se tem  |
                            | barco ou nao. O player escolhendo |
                            | a loja vendera 4 tentativas para  |
                            | ter a dica A little boy e uma     |
                            | bomba especial onde ela atinge 4  |
                            | casas de uma vez so, o player tera|
                            | que informar a linha e a coluna.  |
                            | Escolhendo a little boy o player  |
                            | vendera 6 tentativas.             |
                            |-----------------------------------|""");

                    retornandoParaOMenuDeAjuda();
                }
                case "4"-> System.out.println("|voltando!!\t\t\t\t\t\t\t|");
                default ->  System.out.println("| erro!!!\t\t\t\t\t\t\t|");
            }
        }
    }
    public static void retornandoParaOMenuDeAjuda () {
        String escolha="x", lin ="|-----------------------------------|";
        while (!(escolha.equals("2"))){
            System.out.print("| deseja contiuar lendo as regras? sim(1) nao(2)|\n"+lin);
            escolha = input.next();
            switch (escolha){
                case "1" -> System.out.println("|   Continuar lendo   |");
                case "2"-> System.out.println("|voltando!!\t\t\t\t\t\t\t|");
                default -> System.out.println("| erro!!!\t\t\t\t\t\t\t|");}}

    }
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
        for (int i = 0; i < TamanhoTabuleiro ; i++){
            Arrays.fill(x[i],casaVazia);
        }
    }
    public static void imprimirTabuleiro(String [][] y,String [][] x){
        System.out.printf("\t\t*__* %s \t\t\t\t\t\t-__- %s \n",nomeJogador1,nomeJogador2);
        System.out.print("|-~-~=~-~-=-~-~=*__*=~-~-=-~-~=~-~-|||-~-~=~-~-=-~-~=*__*=~-~-=-~-~=~-~-|\n|\t\t");
        for (int i = 1; i <=TamanhoTabuleiro; i++)
            System.out.printf("%d\t", i );
        System.out.print("|\t\t");
        for (int i = 1; i <=TamanhoTabuleiro; i++)
            System.out.printf("%s\t", i );
        System.out.println("|");
        for (int i = 0; i < TamanhoTabuleiro; i++)
        {
            System.out.printf("|\t%s|",(i+1));
            for (int j = 0; j < TamanhoTabuleiro; j++)
            {
                System.out.printf("\t%s",y[i][j]);
            }
            System.out.print("\t|\t");
            System.out.printf("%s|",(i+1));
            for (int j = 0; j < TamanhoTabuleiro; j++)
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