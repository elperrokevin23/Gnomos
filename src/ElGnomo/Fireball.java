package ElGnomo;

import java.awt.Color;
import entorno.Entorno;
	

public class Fireball {
    private double x;
    private double y;
    private int angulo;
    private double velocidad; // velocidad de la bola de fuego
    private boolean moviendoDerecha; // dirección de movimiento

    public Fireball(double x, double y, boolean moviendoDerecha) {
        this.x = x;
        this.y = y;
        this.moviendoDerecha = moviendoDerecha;
        this.velocidad = 5; // Ajusta la velocidad de la bola de fuego
    }

    // Método para mover la bola de fuego
    public void mover() {
        if (moviendoDerecha) {
            this.x += velocidad; // Mueve a la derecha
        } else {
            this.x -= velocidad; // Mueve a la izquierda
        }
    }

    // Método para dibujar la bola de fuego en pantalla
    public void dibujar(Entorno entorno) {
        entorno.dibujarRectangulo(this.x, this.y, 20, 20, 0, Color.ORANGE);
    }

    // Métodos getters
    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
}
