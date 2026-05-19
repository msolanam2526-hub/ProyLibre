package Panels;
import form.Main;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel/**/ {
    private JFrame frame;
    private JTextField campoNombre;
    private int skinIndex = 0;

    // Array con las rutas de las skins
    private String[] skinRutas = {
            "src/Img/Skins/nave caza.png",
            "src/Img/Skins/nave caza 2.png",
            "src/Img/Skins/nave morada.png",
            "src/Img/Skins/nave roja.png",
            "src/Img/Skins/nave_morada.png",
            "src/Img/Skins/2amrkr6tmcie1.png"
    };

    private String[] skinNombres = {
            "Caza 1", "Caza 2", "Morada", "Roja", "Moradaa", "Especial"
    };

    private JLabel imagenNave;
    private JLabel nombreNave;
    private Image fondo;

    public MenuPanel(JFrame frame) {
        this.frame = frame; //guardas esa ventana en la variable global de la clase.
        setLayout(null); //quita posiciones automaticas y nos permite acomodar las cosas a nuestro gusto.
        setPreferredSize(new Dimension(800, 600));
        fondo = new ImageIcon("src/Img/fondoss/tierra.jpg").getImage();

        // Título
        JLabel titulo = new JLabel("SPACE GAME", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 40));
        titulo.setForeground(new Color(0, 139, 139));
        titulo.setBounds(200, 30, 400, 60);
        add(titulo);

        // Campo nombre
        JLabel labelNombre = new JLabel("Introduce tu nombre:", SwingConstants.CENTER);
        labelNombre.setForeground(Color.white);
        labelNombre.setFont(new Font("Arial", Font.BOLD, 18));
        labelNombre.setBounds(270, 110, 260, 30);
        add(labelNombre);

        campoNombre = new JTextField();
        campoNombre.setBounds(270, 145, 260, 35);
        campoNombre.setFont(new Font("Arial", Font.BOLD, 16));
        add(campoNombre);

        // Label selección nave
        JLabel labelNave = new JLabel("Selecciona tu nave:", SwingConstants.CENTER);
        labelNave.setForeground(Color.white);
        labelNave.setFont(new Font("Arial", Font.BOLD, 18));
        labelNave.setBounds(270, 210, 260, 30);
        add(labelNave);

        // Imagen de la nave
        imagenNave = new JLabel();
        imagenNave.setBounds(350, 250, 100, 100);
        imagenNave.setHorizontalAlignment(SwingConstants.CENTER);
        add(imagenNave);

        // Nombre de la nave
        nombreNave = new JLabel(skinNombres[skinIndex], SwingConstants.CENTER);
        nombreNave.setForeground(Color.white);
        nombreNave.setFont(new Font("Arial", Font.BOLD, 16));
        nombreNave.setBounds(270, 360, 260, 30);
        add(nombreNave);

        // Botón anterior
        JButton btnAnterior = new JButton("◀");
        btnAnterior.setBounds(250, 285, 50, 40);
        btnAnterior.setBackground(new Color(0, 139, 139));
        btnAnterior.setForeground(Color.WHITE);
        btnAnterior.setFocusPainted(false);
        btnAnterior.addActionListener(e -> {
            skinIndex = skinIndex - 1; // Restamos 1 normalmente
            if (skinIndex < 0) {
                skinIndex = skinRutas.length - 1; // es 5 porque skinrute.length es 6 - 1 = 5
            }
            actualizarNave(); // Refrescamos la imagen en la pantalla
        });
        add(btnAnterior);


        // Botón siguiente
        JButton btnSiguiente = new JButton("▶");
        btnSiguiente.setBounds(500, 285, 50, 40);
        btnSiguiente.setBackground(new Color(0, 139, 139));
        btnSiguiente.setForeground(Color.WHITE);
        btnSiguiente.setFocusPainted(false);
        btnSiguiente.addActionListener(e -> {
            skinIndex = skinIndex + 1; // Sumamos 1
            if (skinIndex >= skinRutas.length) {
                skinIndex = 0; // Si se pasa del límite, vuelve a la primera nave
            }
            actualizarNave(); // Refrescamos la pantalla
        });
        add(btnSiguiente);

        // Botón jugar
        JButton btnJugar = new JButton("JUGAR");
        btnJugar.setBounds(320, 430, 160, 50);
        btnJugar.setBackground(new Color(0, 200, 100));
        btnJugar.setForeground(Color.WHITE);
        btnJugar.setFont(new Font("Arial", Font.BOLD, 20));
        btnJugar.setFocusPainted(false);
        btnJugar.addActionListener(e -> {
            String nombre = campoNombre.getText().trim();
            if (nombre.isEmpty() || nombre.length() > 30) {
                JOptionPane.showMessageDialog(frame, "¡Introduce tu nombre!", "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                Main.showGame(nombre, skinRutas[skinIndex]);
            }
        });
        add(btnJugar);

        // Cargar imagen inicial
        actualizarNave();
    }

    private void actualizarNave() {
        ImageIcon icon = new ImageIcon(skinRutas[skinIndex]);
        Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        imagenNave.setIcon(new ImageIcon(img));
        nombreNave.setText(skinNombres[skinIndex]);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Fondo
        if (fondo != null) {
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        }

        // Recuadro semitransparente en el centro
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(0, 0, 0, 190)); // negro con transparencia (0-255)
        g2d.fillRoundRect(200, 20, 400, 500, 30, 30); // x, y, ancho, alto, redondeo

        // Borde con glow cyan
        g2d.setColor(new Color(0, 139, 139, 180));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(200, 20, 400, 500, 30, 30);
    }

}