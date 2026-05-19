package form;

import Panels.GameOver;
import Panels.GamePanel;
import Panels.MenuPanel;

import javax.swing.*;

public class Main {

    static JFrame frame;

    public static void main(String[] args) {
        frame = new JFrame("Space Game");
        frame.setSize(800, 625);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        showMenu();

        frame.setVisible(true);
    }

    public static void showMenu() {
        frame.setContentPane(new MenuPanel(frame));
        frame.revalidate();
        frame.repaint();
    }

    public static void showGame(String nombre, String skin) {
        frame.setContentPane(new GamePanel(frame, nombre, skin));
        frame.revalidate();
        frame.repaint();
    }

    public static void showGameOver(String nombre, String skin, int puntos, double tiempo) {
        frame.setContentPane(new GameOver(frame, nombre, skin, puntos, tiempo));
        frame.revalidate();
        frame.repaint();
    }
}
