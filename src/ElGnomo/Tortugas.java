package ElGnomo;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
import java.util.Random;

public class Tortugas {
	private double x;
	private double y;
	private int ancho;
	private int alto;
	private Image imagenVivo;
	private int puntos;
	private double factorDesplazamiento;
	private boolean vivo;
	private Image imagenDerecha;
	private Image imagenIzquierda;
	private Random random;
	private long ultimoCambioDireccion; // Tiempo de la última inversión de dirección
	private int gravedad;
	private boolean cayendo;
	private int direccion;
	private boolean moviendoseALaIzquierda;

	public Tortugas(double x, double y, int ancho, int alto, String rutaImagen) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.imagenDerecha = Herramientas.cargarImagen("imagenes/tortuga2.png");
		this.imagenIzquierda = Herramientas.cargarImagen("imagenes/tortuga3.png");
		this.puntos = 100;
		this.random = new Random();
		this.factorDesplazamiento = 2.3;
	    this.ultimoCambioDireccion = System.currentTimeMillis(); 
	    this.vivo = true;
	    this.gravedad = 5;
	    cayendo = false;
	    this.direccion = 1;
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



	public void abajo() {
		y += factorDesplazamiento;
	}

	public boolean llegoFondo(Entorno e) {
		return y > e.alto();
	}

	public void reSpawn() {
		y = 0;
	}


	public boolean estaVivo() {
		return vivo;
	}

	public boolean estaSobreAlgunaIsla(Isla[] islas) {
		for (int z = 0; z < islas.length; z++) {
			if ((x + ancho / 2 >= islas[z].getX() - islas[z].getAncho() / 2)
					&& (x - +ancho / 2 <= islas[z].getX() + islas[z].getAncho()
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
					&& (x - +ancho / 2 <= islas[z].getX() + islas[z].getAncho()
							/ 2)
					&& (y + alto / 2 <= islas[z].getY() + islas[z].getAlto()
							/ 2)
					&& (y + alto / 2 >= islas[z].getY() - islas[z].getAlto()
							/ 2)) {
				 cambiarDireccion();
			}
		}
	}
	

	public boolean chocoAlHeroe(Pep h) {
		if ((x >= h.getX() - h.getAncho() / 2)
				&& (x <= h.getX() + h.getAncho() / 2)
				&& (y <= h.getY() + h.getAlto() / 2)
				&& (y >= h.getY() - h.getAlto() / 2)) {
			return true;
		}
		return false;
	}

	public int getPuntos() {
		return puntos;
	}

	public void morir() {
		vivo = false;
	}

	public boolean fueChocadoPorUnEnemigo(Gnomos[] enemigos) {
		for (Gnomos h : enemigos) {
			if (this.estaVivo() && h.estaVivo()
					&& (x >= h.getX() - h.getAncho() / 2)
					&& (x <= h.getX() + h.getAncho() / 2)
					&& (y <= h.getY() + h.getAlto() / 2)
					&& (y >= h.getY() - h.getAlto() / 2)) {
				return true;
			}
		}
		return false;
	}
	public boolean chocoFireball(Fireball[] fireballs) {
		for (int x = 0;x<fireballs.length;x++) {
			if (fireballs[x] != null
					&& fireballs[x].getX() <= this.x + this.ancho / 2
					&& fireballs[x].getX() >= this.x - this.ancho / 2
					&& (fireballs[x].getY() >= this.y - alto / 2 && fireballs[x].getY() <= this.y
							+ alto / 2)) {
				System.out.println("colision");
				return true;
			}
		}
		return false;
	}
	public int indiceDeFireballQueChoco(Fireball[] fireballs) {
		for (int k = 0;k < fireballs.length;k++) {
			if (fireballs[k] != null
					&& fireballs[k].getX() <= this.x + this.ancho / 2
					&& fireballs[k].getX() >= this.x - this.ancho / 2
					&& (fireballs[k].getY() >= this.y - alto / 2 && fireballs[k].getY() <= this.y
							+ alto / 2)) {
				return k;
			}
		}
		return 0;
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

	public boolean chocoDerecha(Entorno e) {
		return x + ancho / 2 >= e.ancho();
	}

	public void dibujar(Entorno entorno) {
		if (estaVivo()) {
        entorno.dibujarImagen(moviendoseALaIzquierda ? imagenIzquierda : imagenDerecha,this.x,this.y,0);  // Dibujar imagen en la posición (x, y)
    }
	}
}
