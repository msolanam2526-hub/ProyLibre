package Panels;

import Elementos.Bullet;
import Elementos.Enemy;
import form.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private JFrame frame;
    private String nombre;
    private String skinRuta;

    // Jugador
    private int jugadorX = 375;
    private int jugadorY = 450;
    private final int jugadorAncho = 50;
    private final int jugadorAlto = 50;
    private final int velocidad = 5;
    private final Image jugadorImg;
    private final Image vidaImg;      // Icono pequeño para mostrar las vidas
    private int vidas = 3;      // Vidas del jugador

    // Puntuación
    private int score = 0;

    // Fondo
    private final Image fondo;

    // Enemigo
    private Enemy enemigo;

    // Balas
    private ArrayList<Bullet> balas = new ArrayList<>();
    private double ultimoDisparoJugador = 0;
    private double ultimoDisparoEnemigo = 0;
    private double proximoIntervaloEnemigo = 0;

    // Tiempo Dificultad
    private double startTime = System.currentTimeMillis();

    // Movimiento
    private boolean arriba, abajo, izquierda, derecha;
    private Timer gameLoop;

    // HUD
    private final int HUD_Y = 550;  // Donde empieza la barra inferior

    public GamePanel(JFrame frame, String nombre, String skinRuta) {
        this.frame = frame;
        this.nombre = nombre;
        this.skinRuta = skinRuta;

        setLayout(null);
        setPreferredSize(new Dimension(800, 600));
        setFocusable(true);

        // Imagen de la nave del jugador
        jugadorImg = new ImageIcon(skinRuta).getImage()
                .getScaledInstance(jugadorAncho, jugadorAlto, Image.SCALE_SMOOTH);

        // Icono pequeño para las vidas (misma nave pero 25x25)
        vidaImg = new ImageIcon(skinRuta).getImage()
                .getScaledInstance(25, 25, Image.SCALE_SMOOTH);

        fondo = new ImageIcon("src/Img/fondoss/espacial12595.jpg").getImage();

        enemigo = new Enemy();

        // Teclado
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W -> arriba = true;
                    case KeyEvent.VK_S -> abajo = true;
                    case KeyEvent.VK_A -> izquierda = true;
                    case KeyEvent.VK_D -> derecha = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W -> arriba = false;
                    case KeyEvent.VK_S -> abajo = false;
                    case KeyEvent.VK_A -> izquierda = false;
                    case KeyEvent.VK_D -> derecha = false;
                }
            }
        });

        // Game loop - se ejecuta cada 16ms lo que equivale a 60FPS standard
        gameLoop = new Timer(16, e -> {
            moverJugador();
            enemigo.mover();
            dispararJugador();
            dispararEnemigo();
            moverBalas();
            comprobarColisiones();
            repaint();
        });
        gameLoop.start();

        SwingUtilities.invokeLater(() -> requestFocusInWindow());
    }

    // --- MOVIMIENTO ---
    private void moverJugador() {
        if (arriba    && jugadorY > 200)                jugadorY -= velocidad;
        if (abajo     && jugadorY < 500 - jugadorAlto)  jugadorY += velocidad;
        if (izquierda && jugadorX > -5)                 jugadorX -= velocidad;
        if (derecha   && jugadorX < 790 - jugadorAncho) jugadorX += velocidad;
    }

    // --- DISPAROS ---
    private void dispararJugador() {
        double ahora = System.currentTimeMillis();

        // El jugador dispara solo cada 500ms
        if (ahora - ultimoDisparoJugador >= 500) {
            balas.add(new Bullet(jugadorX + jugadorAncho / 2 - 5, jugadorY, true));
            ultimoDisparoJugador = ahora;
        }
    }

    private void dispararEnemigo() {
        double ahora = System.currentTimeMillis();
        if (ahora - ultimoDisparoEnemigo >= proximoIntervaloEnemigo) {
            balas.add(new Bullet(enemigo.getX() + enemigo.getAncho() / 2 - 5,
                    enemigo.getY() + enemigo.getAlto(), false));
            ultimoDisparoEnemigo = ahora;
            proximoIntervaloEnemigo = calcularIntervaloEnemigo();
        }
    }

    private double calcularIntervaloEnemigo() {
        /*lo mismo de calcular el tiempo entre balas de jugador*/
        double tiempoJuego = (System.currentTimeMillis() - startTime) / 1000;
        double rand = Math.random();

        if (tiempoJuego < 40) {
            if (rand < 0.60) return 4000;
            else if (rand < 0.90) return 2000;
            else return 500;
        } else if (tiempoJuego < 120) {
            if (rand < 0.40) return 3000;
            else if (rand < 0.80) return 1000;
            else return 500;
        } else {
            if (rand < 0.33) return 3000;
            else if (rand < 0.66) return 1000;
            else return 500;
        }
    }

    // --- BALAS ---
    private void moverBalas() {
        // Recorremos al revés para poder eliminar sin problemas de índices
        for (int i = balas.size() - 1; i >= 0; i--) {
            balas.get(i).mover();
            // Eliminamos balas que salen de pantalla
            if (balas.get(i).getY() < 0 || balas.get(i).getY() > 600) {
                balas.remove(i);
            }
        }
    }

    // --- COLISIONES ---
    private void comprobarColisiones() {
        // Rectángulo que ocupa el jugador en pantalla
        Rectangle rectJugador = new Rectangle(jugadorX, jugadorY, jugadorAncho, jugadorAlto);
        // Rectángulo que ocupa el enemigo en pantalla
        Rectangle rectEnemigo = new Rectangle(enemigo.getX(), enemigo.getY(),
                enemigo.getAncho(), enemigo.getAlto());

        for (int i = balas.size() - 1; i >= 0; i--) {
            Bullet b = balas.get(i);
            // Rectángulo de la bala (5px ancho, 15px alto)
            Rectangle rectBala = new Rectangle(b.getX(), b.getY(), 5, 15);

            if (b.esDelJugador()) {
                // Bala del jugador choca con el enemigo
                if (rectBala.intersects(rectEnemigo)) {
                    enemigo.recibirDanio(10);  // Le resta vida al enemigo
                    score += 100;            // Suma 100 puntos
                    balas.remove(i);         // Elimina la bala

                    // Si el enemigo muere, aparece uno nuevo
                    if (enemigo.estaMuerto()) {
                        enemigo = new Enemy();
                    }
                }
            } else {
                // Bala del enemigo choca con el jugador
                if (rectBala.intersects(rectJugador)) {
                    vidas--;            // Resta una vida
                    balas.remove(i);    // Elimina la bala

                    // Si no quedan vidas, game over
                    if (vidas <= 0) {
                        gameOver();
                    }
                }
            }
        }
    }

    private void gameOver() {
        gameLoop.stop();  // Para el Timer
        double tiempo = ((double) System.currentTimeMillis() - startTime) / 1000;
        Main.showGameOver(nombre, skinRuta, score, tiempo);
    }

    // --- DIBUJO ---

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Fondo - ocupa toda la pantalla
        if (fondo != null) {
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        }

        // Nave del jugador
        if (jugadorImg != null) {
            g.drawImage(jugadorImg, jugadorX, jugadorY, this);
        }

        // Enemigo
        enemigo.dibujar(g);

        // Balas
        for (Bullet b : balas) b.dibujar(g);

        // --- HUD --- (se dibuja al final para que quede por encima de todo)

        // Fondo HUD negro sólido
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 550, 800, 50);

        // Línea separadora cyan
        g.setColor(new Color(0, 139, 139));
        g.fillRect(0, 550, 800, 2);

        // VIDAS - esquina inferior izquierda
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 13));
        g.drawString("VIDAS:", 10, 580);
        for (int i = 0; i < vidas; i++) {
            g.drawImage(vidaImg, 70 + i * 35, 560, this);
        }

        // PUNTUACIÓN - esquina inferior derecha
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("SCORE: " + score, 620, 580);
    }
}