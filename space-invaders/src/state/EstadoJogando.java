package state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;

import game.Inimigo;
import game.Nave;
import game.SpaceInvaders;
import game.Tiro;

public class EstadoJogando implements EstadoJogo, Serializable {

    private static final long serialVersionUID = 1L;

    private transient SpaceInvaders jogo;

    private int fase;
    private int pontos;
    private Nave nave;
    private ArrayList<Tiro> tiros;
    private ArrayList<Inimigo> inimigos;

    private int direcao = 0;
    private boolean spacePressionado = false;

    public EstadoJogando(SpaceInvaders jogo, int fase, int pontos) {
        this.jogo = jogo;
        this.fase = fase;
        this.pontos = pontos;

        nave = new Nave();
        tiros = new ArrayList<Tiro>();
        inimigos = new ArrayList<Inimigo>();

        int linhas = (fase == 1) ? 3 : 5;
        int velocidade = (fase == 1) ? 2 : 3;
        int colunas = 10;

        for (int i = 0; i < linhas * colunas; i++) {
            inimigos.add(new Inimigo(
                50 + (i % colunas) * 60,
                50 + (i / colunas) * 50,
                velocidade));
        }
    }

    // Depois de carregar do arquivo, o painel precisa ser religado
    public void setJogo(SpaceInvaders jogo) {
        this.jogo = jogo;
        direcao = 0;
        spacePressionado = false;
    }

    public void atualizar() {
        nave.movimenta(direcao);

        if (spacePressionado && nave.podeAtirar()) {
            tiros.add(nave.atirar());
        }

        // Tiros: movimento e colisão
        for (int i = 0; i < tiros.size(); i++) {
            Tiro tiro = tiros.get(i);
            tiro.atualiza();

            if (tiro.destroy()) {
                tiros.remove(i);
                i--;
                continue;
            }

            for (int j = 0; j < inimigos.size(); j++) {
                if (tiro.colideCom(inimigos.get(j))) {
                    inimigos.remove(j);
                    tiros.remove(i);
                    i--;            
                    pontos += 10 * fase;
                    break;
                }
            }
        }

        // Inimigos qnd batem na bord voltam
        boolean bateuNaBorda = false;
        for (Inimigo inimigo : inimigos) {
            if ((inimigo.getX() + inimigo.getTam() >= SpaceInvaders.LARGURA && inimigo.getDirecao() == 1) ||
                (inimigo.getX() <= 0 && inimigo.getDirecao() == -1)) {
                bateuNaBorda = true;
                break;
            }
        }
        for (Inimigo inimigo : inimigos) {
            if (bateuNaBorda) {
                inimigo.trocarDirecao();
            } else {
                inimigo.atualizar();
            }
        }

        // Troca de estado - derrota, próxima fase ou vitória
        for (Inimigo inimigo : inimigos) {
            if (inimigo.getY() + inimigo.getTam() >= Nave.Y) {
                Salvamento.apagar();
                jogo.mudarEstado(new EstadoFim(jogo, false, pontos));
                return;
            }
        }

        if (inimigos.isEmpty()) {
            if (fase == 1) {
                jogo.mudarEstado(new EstadoTransicao(jogo, 2, pontos));
            } else {
                Salvamento.apagar();
                jogo.mudarEstado(new EstadoFim(jogo, true, pontos));
            }
        }
    }

    public void pintar(Graphics2D g) {
        for (Inimigo inimigo : inimigos) {
            inimigo.pintar(g);
        }
        nave.pintar(g);
        for (Tiro tiro : tiros) {
            tiro.pintar(g);
        }

        g.setFont(new Font("Consolas", Font.BOLD, 18));
        g.setColor(Color.WHITE);
        g.drawString("FASE " + fase, 10, 25);
        g.drawString("PONTOS: " + pontos, 640, 25);
    }

    public void teclaPressionada(int tecla) {
        if (tecla == KeyEvent.VK_D) direcao = 1;
        if (tecla == KeyEvent.VK_A) direcao = -1;
        if (tecla == KeyEvent.VK_SPACE) spacePressionado = true;

        if (tecla == KeyEvent.VK_P) {
            // solta as teclas, senão a nave continua andando ao voltar da pausa
            direcao = 0;
            spacePressionado = false;
            jogo.mudarEstado(new EstadoPausado(jogo, this)); // guarda ESTE objeto
        }
    }

    public void teclaSolta(int tecla) {
        if (tecla == KeyEvent.VK_D && direcao == 1) direcao = 0;
        if (tecla == KeyEvent.VK_A && direcao == -1) direcao = 0;
        if (tecla == KeyEvent.VK_SPACE) spacePressionado = false;
    }
}
