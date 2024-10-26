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
	private Image imagen;
	private Random random;
	private long ultimoCambioDireccion; // Tiempo de la última inversión de dirección
	private int gravedad;
	private boolean cayendo;
	private int direccion;
	private boolean moviendoseALaDerecha;

	public Tortugas(double x, double y, int ancho, int alto, String rutaImagen) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.imagen = Herramientas.cargarImagen("tortuga.png");
		this.puntos = 100;
		this.random = new Random();
		this.factorDesplazamiento = 2.0;
	    this.ultimoCambioDireccion = System.currentTimeMillis(); 
	    this.vivo = true;
	    this.gravedad = 5;
	    cayendo = false;
	    this.direccion = 1;
	    this.moviendoseALaDerecha = true; // Inicialmente moviéndose a la derecha
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
	if (moviendoseALaDerecha) {
		x -= factorDesplazamiento;
	}
	else {
		x += factorDesplazamiento;
	}
}
public void cambiarDireccion() {
    moviendoseALaDerecha = !moviendoseALaDerecha; // Invierte la dirección
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
		for (int x = 0; x < fireballs.length; x++) {
			if (fireballs[x] != null
					&& fireballs[x].getX() <= this.x + this.ancho / 2
					&& fireballs[x].getX() >= this.x - this.ancho / 2
					&& (fireballs[x].getY() >= y - alto / 2 && fireballs[x].getY() <= y
							+ alto / 2)) {
				return true;
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

	public void dibujar(Entorno entorno) {
		if (estaVivo()) {
        entorno.dibujarImagen(this.imagen, this.x, this.y, 0);  // Dibujar imagen en la posición (x, y)
    }
	}
}