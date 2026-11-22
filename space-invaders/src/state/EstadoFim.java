package state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import game.SpaceInvaders;

public class EstadoFim implements EstadoJogo {

    private SpaceInvaders jogo;
    private boolean venceu;
    private int pontos;

    public EstadoFim(SpaceInvaders jogo, boolean venceu, int pontos) {
        this.jogo = jogo;
        this.venceu = venceu;
        this.pontos = pontos;
    }

    public void atualizar() { }

    public void pintar(Graphics2D g) {
        Font grande = new Font("Consolas", Font.BOLD, 40);
        Font normal = new Font("Consolas", Font.BOLD, 20);

        if (venceu) {
            SpaceInvaders.textoCentralizado(g, "VOCE GANHOU !!", 260, grande, Color.GREEN);
        } else {
            SpaceInvaders.textoCentralizado(g, "VOCE PERDEU!! :(", 260, grande, Color.RED);
        }
        SpaceInvaders.textoCentralizado(g, "Pontos: " + pontos, 310, normal, Color.WHITE);
        SpaceInvaders.textoCentralizado(g, "ENTER - Voltar ao menu", 380, normal, Color.WHITE);
    }

    public void teclaPressionada(int tecla) {
        if (tecla == KeyEvent.VK_ENTER) {
            jogo.mudarEstado(new EstadoMenu(jogo));
        }
    }

    public void teclaSolta(int tecla) { }
}