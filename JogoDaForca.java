// Jogo da Forca

import java.io.*;
import java.util.*;

// Classe para carregar palavras e salvar pontuação
class ArquivoUtil {
    public static List<String> carregarPalavras(String caminho) {
        List<String> palavras = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                palavras.add(linha.trim().toUpperCase());
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo de palavras. Usando padrão.");
            palavras = List.of("JAVA", "OBJETO", "CLASSE");
        }
        return palavras;
    }

    public static void salvarPontuacao(String nome, int pontos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("pontuacao.txt", true))) {
            writer.write(nome + ": " + pontos + " pontos");
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao salvar pontuação.");
        }
    }
}

// Classe do jogador
class Jogador {
    private String nome;
    private int pontos;

    public Jogador(String nome) {
        this.nome = nome;
        this.pontos = 0;
    }

    public void adicionarPonto() {
        pontos++;
    }

    public int getPontos() {
        return pontos;
    }

    public String getNome() {
        return nome;
    }
}

// Classe principal do jogo
public class JogoDaForca {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> palavras = ArquivoUtil.carregarPalavras("palavras.txt");
        Random random = new Random();
        String palavra = palavras.get(random.nextInt(palavras.size()));

        System.out.print("Digite seu nome: ");
        String nome = scanner.nextLine();
        Jogador jogador = new Jogador(nome);

        Set<Character> letrasCertas = new HashSet<>();
        Set<Character> letrasErradas = new HashSet<>();
        int tentativas = 6;

        while (tentativas > 0) {
            boolean ganhou = true;
            System.out.print("Palavra: ");
            for (char c : palavra.toCharArray()) {
                if (letrasCertas.contains(c)) {
                    System.out.print(c + " ");
                } else {
                    System.out.print("_ ");
                    ganhou = false;
                }
            }

            if (ganhou) {
                System.out.println("\nParabéns, você venceu!");
                jogador.adicionarPonto();
                break;
            }

            System.out.println("\nTentativas restantes: " + tentativas);
            System.out.print("Digite uma letra: ");
            char letra = scanner.nextLine().toUpperCase().charAt(0);

            if (palavra.contains(String.valueOf(letra))) {
                letrasCertas.add(letra);
            } else {
                if (!letrasErradas.contains(letra)) {
                    letrasErradas.add(letra);
                    tentativas--;
                }
            }
        }

        if (tentativas == 0) {
            System.out.println("Você perdeu! A palavra era: " + palavra);
        }

        System.out.println("Pontuação final de " + jogador.getNome() + ": " + jogador.getPontos());
        ArquivoUtil.salvarPontuacao(jogador.getNome(), jogador.getPontos());
    }
}
