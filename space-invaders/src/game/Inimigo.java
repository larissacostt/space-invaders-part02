package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;

public class Inimigo implements Serializable {

    private static final long serialVersionUID = 1L;

    private static BufferedImage desenho = carregarImagem();

    private int x;
    private int y;
    private int velocidade;
    private int direcao;

    public Inimigo(int inicioX, int inicioY, int velocidade) {
        this.x = inicioX;
        this.y = inicioY;
        this.velocidade = velocidade;
        this.direcao = 1;
    }

    private static BufferedImage carregarImagem() {
        try {
            return ImageIO.read(Inimigo.class.getResource("/imagens/inimigo.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Não foi possivel carregar a imagem do inimigo");
            return null;
        }
    }

    public void atualizar() {
        x += velocidade * direcao;
    }

    public void trocarDirecao() {
        direcao = direcao * -1;
        y += 15;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getTam() { return 50; }
    public int getDirecao() { return direcao; }

    public void pintar(Graphics2D g) {
        if (desenho != null) {
            g.drawImage(desenho, x, y, getTam(), getTam(), null);
        }
    }
}