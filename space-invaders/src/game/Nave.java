package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;

public class Nave implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int Y = 500;
    public static final int TAMANHO = 100;

    private static BufferedImage desenho = carregarImagem();

    private int x;
    private int velocidade;
    private boolean podeAtirar;
    private int tempo;

    public Nave() {
        x = 350;
        velocidade = 4;
        podeAtirar = true;
        tempo = 0;
    }

    private static BufferedImage carregarImagem() {
        try {
            return ImageIO.read(Nave.class.getResource("/imagens/nave.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Não foi possivel carregar a imagem da nave");
            return null;
        }
    }

    public void pintar(Graphics2D g) {
        if (desenho != null) {
            g.drawImage(desenho, x, Y, TAMANHO, TAMANHO, null);
        }
    }

    public Tiro atirar() {
        podeAtirar = false;
        return new Tiro(x + TAMANHO / 2 - 1, Y - 5);
    }

    public void movimenta(int valor) {
        x += valor * velocidade;

        if (x < 0) x = 0;
        if (x > SpaceInvaders.LARGURA - TAMANHO) x = SpaceInvaders.LARGURA - TAMANHO;

        if (tempo >= 10) {
            podeAtirar = true;
            tempo = 0;
        }
        tempo++;
    }

    public boolean podeAtirar() {
        return podeAtirar;
    }
}