package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;

public class Tiro implements Serializable {

    private static final long serialVersionUID = 1L;

    private int x;
    private int y;
    private int velocidade;
    private int tamX = 3;
    private int tamY = 8;

    public Tiro(int inicioX, int inicioY) {
        this.x = inicioX;
        this.y = inicioY;
        this.velocidade = 10;
    }

    public void pintar(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, tamX, tamY);
    }

    public void atualiza() {
        y -= velocidade;
    }

    public boolean destroy() {
        return y < 0;
    }

    public boolean colideCom(Inimigo inimigo) {
        return x + tamX >= inimigo.getX() && x <= inimigo.getX() + inimigo.getTam()
            && y + tamY >= inimigo.getY() && y <= inimigo.getY() + inimigo.getTam();
    }
}