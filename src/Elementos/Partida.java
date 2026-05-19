package Elementos;

public class Partida {
    private final int puntos;
    private final int tiempo;
    private final String nave;
    private final String jugador;

    public Partida(int puntos, int tiempo, String nave, String jugador) {
        this.puntos = puntos;
        this.tiempo = tiempo;
        this.nave = nave;
        this.jugador = jugador;
    }

    public int getPuntos() {
        return puntos;
    }

    public int getTiempo() {
        return tiempo;
    }

    public String getNave() {
        return nave;
    }

    public String getJugador() {
        return jugador;
    }
}
