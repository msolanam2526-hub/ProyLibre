package Panels;
import BD.BaseDeDatos;
import Elementos.Partida;

import javax.swing.*;
import java.util.ArrayList;

public class GameOver extends JPanel {
    public GameOver(JFrame frame, String nombre, String skin, int puntos, double tiempo) {
        ArrayList<Partida> partidas;

        skin = skin.replace(".png", "");
        String nombreSkin = skin.replace("src/Img/Skins/", "");
        int tiempoInt = (int) tiempo;
        BaseDeDatos.guardarDatos(nombre, nombreSkin, puntos, tiempoInt);

        partidas = BaseDeDatos.cargarPartidas();
    }
}
