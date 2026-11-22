package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import state.EstadoJogo;
import state.EstadoMenu;

public class SpaceInvaders extends JPanel implements ActionListener, KeyListener {

    public static final int LARGURA = 800;
    public static final int ALTURA = 600;

    private EstadoJogo estadoAtual;

    public SpaceInvaders() {
        setPreferredSize(new Dimension(LARGURA, ALTURA));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        estadoAtual = new EstadoMenu(this);

        Timer lacoDoJogo = new Timer(16, this);
        lacoDoJogo.start();
    }

    public void mudarEstado(EstadoJogo novo) {
        estadoAtual = novo;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        estadoAtual.atualizar();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g2) {
        super.paintComponent(g2);
        Graphics2D g = (Graphics2D) g2.create();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        estadoAtual.pintar(g);
        g.dispose();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        estadoAtual.teclaPressionada(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        estadoAtual.teclaSolta(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    public static void textoCentralizado(Graphics2D g, String texto, int y, Font fonte, Color cor) {
        g.setFont(fonte);
        g.setColor(cor);
        int largura = g.getFontMetrics().stringWidth(texto);
        g.drawString(texto, (LARGURA - largura) / 2, y);
    }
}