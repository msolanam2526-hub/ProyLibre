package BD;

import java.sql.*;

public class BaseDeDatos {
    private static final String URL = "jdbc:mysql://localhost:3306/space_game";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "mysql";

    public static void guardarDatos(String nombre, String nombreSkin, int puntos, int tiempo) {
        if (!revisarJugadorExistente(nombre)) {
            guardarDatosJugadorNuevo(nombre);
        }
        guardarDatosPartida(puntos, tiempo, nombreSkin, nombre);
    }

    public static boolean revisarJugadorExistente(String nombre) {
        String query = "select * from usuario where id_usuario = ?";
        boolean existe = false;
        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {existe = true;}
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error al encontrar jugadr existente");
        }
        return existe;
    }

    public static void guardarDatosJugadorNuevo(String nombre) {
        String query = "insert into usuario (id_usuario) values (?)";
        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, nombre);
            int anadirFila = ps.executeUpdate();
            //comprobador de insert
            if (anadirFila > 0) {
                System.out.println("Jugador registrado exitosamente");
            }
            ps.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error al guardar jugador nuevo");
        }
    }

    public static void guardarDatosPartida(int puntos, int tiempo, String nombreSkin, String nombre) {
        String query = "insert into partida (puntuacion_jugador, tiempo_sobrevivido, id_nave, id_usuario) values (?, ?, ?, ?)";
        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, puntos);
            ps.setInt(2, tiempo);
            ps.setString(3, nombreSkin);
            ps.setString(4, nombre);
            int anadirFila = ps.executeUpdate();
            if (anadirFila > 0) {
                System.out.println("Partida guardada correctamente");
            }
            ps.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error al guardar partida");
        }
    }
}