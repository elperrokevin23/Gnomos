package ElGnomo;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Bombas {
	private double x;
    private double y;
    private double velocidad; // velocidad de la bola de fuego
    private boolean moviendoDerecha; // dirección de movimiento
	private Image imagen;

    public Bombas(double x, double y, boolean moviendoDerecha) {
        this.x = x;
        this.y = y;
        this.moviendoDerecha = moviendoDerecha;
		this.imagen = Herramientas.cargarImagen("bomb.png");
        this.velocidad = 6; // Ajusta la velocidad de la bola de fuego
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
		if (this != null) {
        entorno.dibujarImagen(this.imagen, this.x, this.y, 0);  // Dibujar imagen en la posición (x, y)
    }
	}

    // Métodos getters
    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
}
