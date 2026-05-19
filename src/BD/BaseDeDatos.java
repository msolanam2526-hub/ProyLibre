package BD;

import Elementos.Partida;

import java.sql.*;
import java.util.ArrayList;

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
            //revisa si se ha guardado correctamente si se guarda correctamente devuelve un numero superior a 0
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

    public static ArrayList<Partida> cargarPartidas() {
        ArrayList<Partida> partidas = new ArrayList<>();
        String query = "select * from partida order by puntuacion_jugador desc limit 10";
        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int puntuacionJugador = rs.getInt("puntuacion_jugador");
                int tiempoSobrevivido = rs.getInt("tiempo_sobrevivido");
                String nave = rs.getString("id_nave");
                String usuario = rs.getString("id_usuario");

                Partida partida = new Partida(puntuacionJugador, tiempoSobrevivido, nave, usuario);
                partidas.add(partida);
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error al encontrar jugadr existente");
        }
        return partidas;
    }
}