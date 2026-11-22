package state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import game.SpaceInvaders;

public class EstadoMenu implements EstadoJogo {

    private SpaceInvaders jogo;
    private Font titulo = new Font("Consolas", Font.BOLD, 48);
    private Font texto = new Font("Consolas", Font.BOLD, 20);

    public EstadoMenu(SpaceInvaders jogo) {
        this.jogo = jogo;
    }

    public void atualizar() { }

    public void pintar(Graphics2D g) {
        SpaceInvaders.textoCentralizado(g, "SPACE INVADERS", 200, titulo, Color.GREEN);
        SpaceInvaders.textoCentralizado(g, "ENTER - Novo jogo", 300, texto, Color.WHITE);

        if (Salvamento.existe()) {
            SpaceInvaders.textoCentralizado(g, "C - Continuar jogo salvo", 340, texto, Color.WHITE);
        }

        SpaceInvaders.textoCentralizado(g, "A/D mover   ESPACO atirar   P pausar", 480, texto, Color.GRAY);
    }

    public void teclaPressionada(int tecla) {
        if (tecla == KeyEvent.VK_ENTER) {
            jogo.mudarEstado(new EstadoJogando(jogo, 1, 0));
        }
        if (tecla == KeyEvent.VK_C && Salvamento.existe()) {
            EstadoJogando salvo = Salvamento.carregar(jogo);
            if (salvo != null) {
                jogo.mudarEstado(salvo);
            }
        }
    }

    public void teclaSolta(int tecla) { }
}
