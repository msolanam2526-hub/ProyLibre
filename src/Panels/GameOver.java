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
        title.setForeground(new Color(220, 50, 50));
        title.setBounds(210, 35, 380, 45);
        add(title);

        // Tabla
        String[] cols = {"#", "Jugador", "Nave", "Puntos", "Tiempo"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        for (int i = 0; i < partidas.size(); i++) {
            Partida p = partidas.get(i);
            model.addRow(new Object[]{ i + 1, p.getJugador(), p.getNave(), p.getPuntos(), p.getTiempo() + "s" });
        }

        JTable table = new JTable(model);
        table.setFont(new Font("Courier New", Font.PLAIN, 12));
        table.setForeground(new Color(0, 255, 255));
        table.setBackground(new Color(0, 0, 0, 0));
        table.setOpaque(false);
        table.setRowHeight(25);
        table.setShowGrid(false);
        table.setSelectionBackground(new Color(0, 139, 139, 120));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Courier New", Font.BOLD, 12));
        header.setForeground(new Color(0, 255, 255));
        header.setBackground(new Color(0, 60, 60, 200));
        header.setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(0, 139, 139, 180)));
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scroll.setBounds(220, 100, 360, 300);
        add(scroll);
    }

    private void estilizarBoton(JButton btn) {
        btn.setFont(new Font("Courier New", Font.BOLD, 13));
        btn.setForeground(new Color(0, 255, 255));
        btn.setBackground(new Color(0, 80, 80));
        btn.setBorder(BorderFactory.createLineBorder(new Color(0, 139, 139, 180)));
        btn.setFocusPainted(false);
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
