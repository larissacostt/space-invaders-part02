package state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import game.SpaceInvaders;

public class EstadoPausado implements EstadoJogo {

    private SpaceInvaders jogo;
    private EstadoJogando pausado; // o jogo congelado, exatamente como estava
    private Font fonte = new Font("Consolas", Font.BOLD, 20);
    private String mensagem = "";

    public EstadoPausado(SpaceInvaders jogo, EstadoJogando pausado) {
        this.jogo = jogo;
        this.pausado = pausado;
    }

    public void atualizar() { }

    public void pintar(Graphics2D g) {
        pausado.pintar(g);

        g.setColor(new Color(0, 0, 0, 170)); // escurece a tela
        g.fillRect(0, 0, SpaceInvaders.LARGURA, SpaceInvaders.ALTURA);

        SpaceInvaders.textoCentralizado(g, "PAUSADO", 250, new Font("Consolas", Font.BOLD, 40), Color.YELLOW);
        SpaceInvaders.textoCentralizado(g, "P - Continuar", 310, fonte, Color.WHITE);
        SpaceInvaders.textoCentralizado(g, "S - Salvar jogo", 345, fonte, Color.WHITE);
        SpaceInvaders.textoCentralizado(g, "M - Voltar ao menu", 380, fonte, Color.WHITE);
        SpaceInvaders.textoCentralizado(g, mensagem, 440, fonte, Color.GREEN);
    }

    public void teclaPressionada(int tecla) {
        if (tecla == KeyEvent.VK_P) {
            jogo.mudarEstado(pausado); // volta para o MESMO objeto
        }
        if (tecla == KeyEvent.VK_S) {
            mensagem = Salvamento.salvar(pausado) ? "Jogo salvo!" : "Erro ao salvar";
        }
        if (tecla == KeyEvent.VK_M) {
            jogo.mudarEstado(new EstadoMenu(jogo));
        }
    }

    public void teclaSolta(int tecla) { }
}
