package ElGnomo;

import java.awt.Color;
import entorno.Entorno;
	

public class Fireball {
    private double x;
    private double y;
    private double velocidad; // velocidad de la bola de fuego
    private boolean moviendoDerecha; // dirección de movimiento
    private final int ancho = 20;  // Ancho de la fireball
    private final int alto = 20;   // Alto de la fireball

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
    public boolean chocoConBomba(Bombas bomba) {
	        if (bomba != null && this != null) {
	            // Coordenadas de los bordes de la bola de fuego
	            double bombaXIzquierda = bomba.getX() - 10;  // 10 es la mitad del ancho de la fireball
	            double bombaXDerecha = bomba.getX() + 10;
	            double bombaYSuperior = bomba.getY() - 10;   // 10 es la mitad de la altura de la fireball
	            double bombaYInferior = bomba.getY() + 10;

	            // Coordenadas de los bordes de la tortuga
	            double pepXIzquierda = this.getX() - this.getAncho() / 2;
	            double pepXDerecha = this.getX() + this.getAncho() / 2;
	            double pepYSuperior = this.getY() - this.getAlto() / 2;
	            double pepYInferior = this.getY() + this.getAlto() / 2;

	            // Verifica si hay colisión
	            if (bombaXIzquierda <= pepXDerecha && bombaXDerecha >= pepXIzquierda &&
	                bombaYInferior >= pepYSuperior && bombaYSuperior <= pepYInferior) {
	                return true;  // Colisión detectada
	            }
	        }
	    return false;  // No hubo colisión
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
    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }
}
