import javax.swing.JFrame;

import game.SpaceInvaders;

public class App {
    public static void main(String[] args) throws Exception {
        JFrame janela = new JFrame("Space Invaders");

        SpaceInvaders invasaoAlienigena = new SpaceInvaders();
        janela.add(invasaoAlienigena);

        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setResizable(false);
        janela.pack();
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);

        invasaoAlienigena.requestFocusInWindow();
    }
}