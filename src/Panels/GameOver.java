package Panels;

import BD.BaseDeDatos;
import Elementos.Partida;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;

public class GameOver extends JPanel {

    private Image fondo;

    public GameOver(JFrame frame, String nombre, String skin, int puntos, double tiempo) {

        skin = skin.replace(".png", "").replace("src/Img/Skins/", "");
        BaseDeDatos.guardarDatos(nombre, skin, puntos, (int) tiempo);
        ArrayList<Partida> partidas = BaseDeDatos.cargarPartidas();

        try { fondo = new ImageIcon("src/Img/fondo.png").getImage(); }
        catch (Exception ignored) {}

        setLayout(null);

        // Título
        JLabel title = new JLabel("GAME OVER", SwingConstants.CENTER);
        title.setFont(new Font("Courier New", Font.BOLD, 34));
        title.setForeground(new Color(0, 140, 140));
        title.setBounds(210, 35, 380, 45);
        add(title);

        // Tabla
        String[] columnas = {"#", "Jugador", "Nave", "Puntos", "Tiempo"};
        DefaultTableModel tabla = new DefaultTableModel(columnas, 0) { //es 0 porque se adapta a los datos
            public boolean isCellEditable(int r, int c) { return false; }
            //Evita que se puedan editarlas columnas
        };

        //Partia a partida crea las filas con un array de objeto sin determinar que contiene los datos de la fila
        int count = 1;
        for (Partida p : partidas) {
            tabla.addRow(new Object[]{ count, p.getJugador(), p.getNave(), p.getPuntos(), p.getTiempo() + "s" });
            count++;
        }

        //crea la tabla existente en el java swing
        JTable table = new JTable(tabla);
        table.setFont(new Font("Courier New", Font.PLAIN, 12));
        table.setForeground(new Color(0, 140, 140));
        table.setBackground(new Color(0, 0, 0, 0));
        table.setOpaque(false);
        table.setRowHeight(25);
        table.setShowGrid(false);
        table.setSelectionBackground(new Color(0, 140, 140, 180));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Courier New", Font.BOLD, 12));
        header.setForeground(new Color(0, 140, 140));
        header.setBackground(new Color(0, 0, 0, 0));
        header.setReorderingAllowed(false); //para que el usuario no pueda reordenar las tablas

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);
        tablePanel.setBounds(220, 100, 360, 300);
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(0, 139, 139, 180)));
        tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);
        tablePanel.add(table, BorderLayout.CENTER);
        add(tablePanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (fondo != null)
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(0, 0, 0, 190));
        g2d.fillRoundRect(200, 20, 400, 500, 30, 30);

        g2d.setColor(new Color(0, 139, 139, 180));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(200, 20, 400, 500, 30, 30);
    }
}
