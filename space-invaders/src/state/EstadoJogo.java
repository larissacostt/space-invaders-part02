package state;

import java.awt.Graphics2D;

public interface EstadoJogo {
    void atualizar();
    void pintar(Graphics2D g);
    void teclaPressionada(int tecla);
    void teclaSolta(int tecla);
}