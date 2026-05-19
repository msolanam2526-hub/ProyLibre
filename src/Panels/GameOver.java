package Panels;
import BD.BaseDeDatos;
import javax.swing.*;

public class GameOver extends JPanel {
    public GameOver(JFrame frame, String nombre, String skin, int puntos, double tiempo) {
        skin = skin.replace(".png", "");
        String nombreSkin = skin.replace("src/Img/Skins/", "");
        int tiempoInt = (int) tiempo;
        BaseDeDatos.guardarDatos(nombre, nombreSkin, puntos, tiempoInt);
    }
}
