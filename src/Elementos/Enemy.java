package Elementos;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Enemy {

    private int x, y;
    private int ancho = 100;
    private int alto = 100;
    private int velocidad = 2;
    private int direccion = 1; // 1 derecha, -1 izquierda
    private int vida = 400;
    private int vidaMax = 400;
    private Image img;

    // Array con las rutas de los enemigos
    private static String[] rutas = {
            "src/Img/nave mala/first enemi.png",
            "src/Img/nave mala/second enemi.png",
            "src/Img/nave mala/baliza V16.png"
    };

    public Enemy() {
        // Elige una nave enemiga aleatoria
        String ruta = rutas[new Random().nextInt(rutas.length)];
        img = new ImageIcon(ruta).getImage()
                .getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);

        // Empieza en una posición X aleatoria arriba
        x = new Random().nextInt(800 - ancho);
        y = 30;
    }

    public void mover() {
        x += velocidad * direccion;/*mirate esto*/

        // Rebota en las paredes
        if (x <= 0) {
            x = 0;
            direccion = 1;
        }
        if (x >= 800 - ancho) {
            x = 800 - ancho;
            direccion = -1;
        }
    }

    public boolean estaMuerto() {
        return vida <= 0;
    }

    public void dibujar(Graphics g) {
        // Dibuja la nave
        g.drawImage(img, x, y, null);

        // Barra de vida - fondo gris
        g.setColor(new Color(80, 80, 80));
        g.fillRect(x, y - 12, ancho, 8);

        // Barra de vida - color burdeos
        int vidaAncho = (int) ((double) vida / vidaMax * ancho);
        g.setColor(new Color(128, 0, 32));
        g.fillRect(x, y - 12, vidaAncho, 8);

        // Texto vida
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 9));
        g.drawString(vida + "/" + vidaMax, x + 5, y - 4);
    }

    public void recibirDanio(int danio) {
        vida -= danio;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }
}