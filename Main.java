import java.util.Scanner;
import java.util.Random;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import java.io.File;
import java.io.IOException;

// Exceção personalizada para explosão da bomba
class BombaExplodiuException extends Exception {
    public BombaExplodiuException(String mensagem) {
        super(mensagem);
    }
}

public class Main {

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        Random geradorAleatorio = new Random();
        int fiosCortadosComSucesso = 0;

        System.out.println("Bem-vindo ao jogo de desarmamento de bomba!");
        System.out.println("Escolha um fio para cortar (1 a 6). Se cortar 2 fios sem explodir, você vence!");

        while (fiosCortadosComSucesso < 2) {
            try {
                System.out.print("Escolha um fio (1 a 6): ");
                int fioEscolhido = entrada.nextInt();

                if (fioEscolhido < 1 || fioEscolhido > 6) {
                    System.out.println("Por favor, escolha um fio válido entre 1 e 6.");
                    continue;
                }

                // Gerar número aleatório para decidir se explode
                int numeroAleatorio = geradorAleatorio.nextInt(100); // Gera um número entre 0 e 99

                if (numeroAleatorio % 2 == 0) {
                    reproduzirSom("explosao.wav"); // Reproduz o som de explosão
                    throw new BombaExplodiuException("A bomba explodiu! Jogo terminado.");
                } else {
                    fiosCortadosComSucesso++;
                    System.out.println("Fio seguro cortado! Você cortou " + fiosCortadosComSucesso + " fio(s) com sucesso.");
                }

            } catch (BombaExplodiuException e) {
                System.out.println(e.getMessage());
                break;
            } catch (Exception e) {
                System.out.println("Entrada inválida. Por favor, insira um número válido.");
                entrada.nextLine(); // Limpar o buffer
            }
        }

        if (fiosCortadosComSucesso == 2) {
            System.out.println("Parabéns! Você desarmou a bomba com sucesso!");
        } else {
            System.out.println("Infelizmente, você perdeu. Tente novamente.");
        }

        entrada.close();
    }

    // Método para reproduzir um som
    public static void reproduzirSom(String caminhoDoArquivo) {
        try {
            File arquivoSom = new File(caminhoDoArquivo);
            if (arquivoSom.exists()) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(arquivoSom);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();

                // Aguarda o áudio terminar de tocar
                Thread.sleep(clip.getMicrosecondLength() / 1000);
            } else {
                System.out.println("Arquivo de áudio não encontrado: " + caminhoDoArquivo);
            }
        } catch (Exception e) {
            System.out.println("Erro ao reproduzir o som: " + e.getMessage());
        }
    }
}
