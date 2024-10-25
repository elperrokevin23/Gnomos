package ElGnomo;

import java.awt.Color;
import entorno.Entorno;
	

public class Fireball {
    private double x;
    private double y;
    private int angulo;
    private double velocidad; // velocidad de la bola de fuego
    private boolean moviendoDerecha; // dirección de movimiento
    private double ancho;
    private double alto;

    public Fireball(double x, double y, int angulo, boolean moviendoDerecha) {
        this.x = x;
        this.y = y;
        this.angulo = angulo;
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
        entorno.dibujarRectangulo(this.x, this.y, 50, 50, 0, Color.ORANGE);
    }

    // Métodos getters
    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
    public boolean chocoIzquierda(Entorno e) {
		return x <= 0;
	}
    public double getAncho() {
        return ancho;
    }

    public double getAlto() {
        return alto;
    }
    public boolean chocoTortuga(Tortugas[] tortuga) {
		for (int x = 0;x<tortuga.length;x++) {
			if (tortuga[x] != null
					&& tortuga[x].getX() <= this.x + this.ancho / 2
					&& tortuga[x].getX() >= this.x - this.ancho / 2
					&& (tortuga[x].getY() >= this.y - alto / 2 && tortuga[x].getY() <= this.y
							+ alto / 2)) {
				System.out.println("colision");
				return true;
			}
		}
		return false;
	}

	public boolean chocoDerecha(Entorno e) {
		return x >= e.ancho();
	}
}
