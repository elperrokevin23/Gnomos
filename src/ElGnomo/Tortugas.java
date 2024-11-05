package ElGnomo;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Tortugas {
	private double x;
	private double y;
	private int ancho;
	private int alto;
	private double factorDesplazamiento;
	private boolean vivo;
	private Image imagenDerecha;
	private Image imagenIzquierda;
	private int gravedad;
	private boolean cayendo;
	private boolean moviendoseALaIzquierda;
	private long ultimoDisparo;

	public Tortugas(double x, double y, int ancho, int alto) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.imagenDerecha = Herramientas.cargarImagen("tortuga.png");
		this.imagenIzquierda = Herramientas.cargarImagen("tortuga3.png");
		this.factorDesplazamiento = 1.8;
	    this.vivo = true;
	    this.gravedad = 5;
	    this.cayendo = false;
	    this.moviendoseALaIzquierda = true; // Inicialmente moviéndose a la derecha
	}

	
	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public int getAncho() {
		return this.ancho;
	}

	public double getAlto() {
		return this.alto;
	}
	

public void mover() {
	if (moviendoseALaIzquierda) {
		x -= factorDesplazamiento;
	}
	else {
		x += factorDesplazamiento;
	}
}
public void cambiarDireccion() {
	moviendoseALaIzquierda = !moviendoseALaIzquierda; // Invierte la dirección
}



	public boolean estaVivo() {
		return vivo;
	}

	public boolean estaSobreAlgunaIsla(Isla[] islas) {
		for (int z = 0; z < islas.length; z++) {
			if ((x + ancho / 2 >= islas[z].getX() - islas[z].getAncho() / 2)
					&& (x - ancho / 2 <= islas[z].getX() + islas[z].getAncho()
							/ 2)
					&& (y + alto / 2 <= islas[z].getY() + islas[z].getAlto()
							/ 2)
					&& (y + alto / 2 >= islas[z].getY() - islas[z].getAlto()
							/ 2)) {
				return true;
			}
		}
		return false;
	}
	public void cambiarDireccionSiTocaIsla(Isla[] islas) {
		for (int z = 0; z < islas.length; z++) {
			if ((x + ancho / 2 >= islas[z].getX() - islas[z].getAncho() / 2)
					&& (x - ancho / 2 <= islas[z].getX() + islas[z].getAncho()
							/ 2)
					&& (y + alto / 2 <= islas[z].getY() + islas[z].getAlto()
							/ 2)
					&& (y + alto / 2 >= islas[z].getY() - islas[z].getAlto()
							/ 2)) {
				 cambiarDireccion();
			}
		}
	}

	public long getUltimoDisparo() {
	    return this.ultimoDisparo;
	}
	public void setUltimoDisparo(long tiempo) {
	    this.ultimoDisparo = tiempo;
	}


	public boolean chocoConFireball(Fireball[] fireballs) {
	    for (Fireball fireball : fireballs) {
	        if (fireball != null && this.estaVivo()) {
	            // Coordenadas de los bordes de la bola de fuego
	            double fireballXIzquierda = fireball.getX() - 10;  // 10 es la mitad del ancho de la fireball
	            double fireballXDerecha = fireball.getX() + 10;
	            double fireballYSuperior = fireball.getY() - 10;   // 10 es la mitad de la altura de la fireball
	            double fireballYInferior = fireball.getY() + 10;

	            // Coordenadas de los bordes de la tortuga
	            double tortugaXIzquierda = this.getX() - this.getAncho() / 2;
	            double tortugaXDerecha = this.getX() + this.getAncho() / 2;
	            double tortugaYSuperior = this.getY() - this.getAlto() / 2;
	            double tortugaYInferior = this.getY() + this.getAlto() / 2;

	            // Verifica si hay colisión
	            if (fireballXIzquierda <= tortugaXDerecha && fireballXDerecha >= tortugaXIzquierda &&
	                fireballYInferior >= tortugaYSuperior && fireballYSuperior <= tortugaYInferior) {
	                return true;  // Colisión detectada
	            }
	        }
	    }
	    return false;  // No hubo colisión
	}

	public boolean chocoConGnomo(Gnomos[] gnomos) {
	    for (Gnomos gnomo : gnomos) {
	        if (gnomo != null && gnomo.estaVivo()) {
	            // Coordenadas de la tortuga
	            double tortugaXMin = this.x - this.ancho / 2;
	            double tortugaXMax = this.x + this.ancho / 2;
	            double tortugaYMin = this.y - this.alto / 2;
	            double tortugaYMax = this.y + this.alto / 2;

	            // Coordenadas del gnomo
	            double gnomoXMin = gnomo.getX() - gnomo.getAncho() / 2;
	            double gnomoXMax = gnomo.getX() + gnomo.getAncho() / 2;
	            double gnomoYMin = gnomo.getY() - gnomo.getAlto() / 2;
	            double gnomoYMax = gnomo.getY() + gnomo.getAlto() / 2;

	            // Comprobar si las dos cajas (tortuga y gnomo) se superponen
	            boolean colisionX = (tortugaXMin <= gnomoXMax && tortugaXMax >= gnomoXMin);
	            boolean colisionY = (tortugaYMin <= gnomoYMax && tortugaYMax >= gnomoYMin);

	            if (colisionX && colisionY) {
	                return true;
	            }
	        }
	    }
	    return false;
	}
	public void caer() {
		// y += factorDesplazamiento+impulso*3/2;
		if (vivo) {
			y += gravedad;
			cayendo = true;
		}
	}
	public boolean chocoIzquierda(Entorno e) {
		return x - ancho / 2 <= 0;
	}
	public boolean chocoDerecha(Entorno e) {
		return x + ancho / 2 >= e.ancho();
	}
	public void moverEnIsla(Isla[] islas) {
	    for (int z = 0; z < islas.length; z++) {
	        // Verifica si la tortuga está sobre esta isla
	    	if ((x + ancho / 2 >= islas[z].getX() - islas[z].getAncho() / 2)
					&& (x - ancho / 2 <= islas[z].getX() + islas[z].getAncho()
							/ 2)
					&& (y + alto / 2 >= islas[z].getY() - islas[z].getAlto()
							/ 2)
					&& (y - alto / 2 <= islas[z].getY() + islas[z].getAlto()
							/ 2)) {

	            // Si llega al borde derecho de la isla, cambia de dirección a la izquierda
	            if (x - ancho / 2 <= islas[z].getX() - islas[z].getAncho() / 2) {
	            	moviendoseALaIzquierda = false;
	            }
	            // Si llega al borde izquierda de la isla, cambia de dirección a la derecha
	            else if (x + ancho / 2 >= islas[z].getX() + islas[z].getAncho() / 2) {
	            	moviendoseALaIzquierda = true;
	            }
	        }
	    }
	}


	public boolean izquierda() {
		return this.moviendoseALaIzquierda;
	}

	public void dibujar(Entorno entorno) {
		if (estaVivo()) {
        entorno.dibujarImagen(moviendoseALaIzquierda ? imagenIzquierda : imagenDerecha,this.x,this.y,0);  // Dibujar imagen en la posición (x, y)
    }
	}
}
