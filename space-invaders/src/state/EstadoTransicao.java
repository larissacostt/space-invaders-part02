package state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import game.SpaceInvaders;

// Tela "FASE 2" entre uma fase e outra
public class EstadoTransicao implements EstadoJogo {

    private SpaceInvaders jogo;
    private int proximaFase;
    private int pontos;
    private int tempo = 0;

    public EstadoTransicao(SpaceInvaders jogo, int proximaFase, int pontos) {
        this.jogo = jogo;
        this.proximaFase = proximaFase;
        this.pontos = pontos;
    }

    public void atualizar() {
        tempo++;
        if (tempo > 120) {
            comecar();
        }
    }

    private void comecar() {
        jogo.mudarEstado(new EstadoJogando(jogo, proximaFase, pontos));
    }

    public void pintar(Graphics2D g) {
        SpaceInvaders.textoCentralizado(g, "FASE " + proximaFase, 280, new Font("Consolas", Font.BOLD, 48), Color.GREEN);
        SpaceInvaders.textoCentralizado(g, "Os inimigos estao mais rapidos!", 330, new Font("Consolas", Font.BOLD, 20), Color.WHITE);
    }

    public void teclaPressionada(int tecla) {
        if (tecla == KeyEvent.VK_ENTER) comecar();
    }

    public void teclaSolta(int tecla) { }
}
