package state;

import java.io.*;

import game.SpaceInvaders;

// Salva e carrega o EstadoJogando em um arquivo
public class Salvamento {

    private static final File ARQUIVO = new File("save.dat");

    public static boolean salvar(EstadoJogando estado) {
        try (ObjectOutputStream saida = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            saida.writeObject(estado);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static EstadoJogando carregar(SpaceInvaders jogo) {
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ARQUIVO))) {
            EstadoJogando estado = (EstadoJogando) entrada.readObject();
            estado.setJogo(jogo);
            return estado;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean existe() {
        return ARQUIVO.exists();
    }

    public static void apagar() {
        ARQUIVO.delete();
    }
}
