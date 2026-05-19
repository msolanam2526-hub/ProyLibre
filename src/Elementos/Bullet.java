package Elementos;

import javax.swing.*;
import java.awt.*;

public class Bullet {

    private int x, y;
    private boolean delJugador;
    private int velocidad;
    private Image img;

    public Bullet(int x, int y, boolean delJugador) {
        this.x = x;
        this.y = y;
        this.delJugador = delJugador;

        if (delJugador) {
            velocidad = 8;
            img = new ImageIcon("src/Img/laser/ultra laser.png").getImage()
                    .getScaledInstance(10, 20, Image.SCALE_SMOOTH);
        } else {
            velocidad = 6;
            img = new ImageIcon("src/Img/laser/laser enemigo.png").getImage()
                    .getScaledInstance(10, 20, Image.SCALE_SMOOTH);
        }
    }

    public void mover() {
        if (delJugador) y -= velocidad;
        else y += velocidad;
    }

    public void dibujar(Graphics g) {
        g.drawImage(img, x, y, null);
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public boolean esDelJugador() { return delJugador; }
}
