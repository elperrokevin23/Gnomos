package ElGnomo;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
import java.util.Random;

public class Gnomos {
	
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
	private boolean estaEnIslaInferior;
	
	private static Random rand = new Random();

	public Gnomos(double x, double y, int ancho, int alto) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.imagen = Herramientas.cargarImagen("gnomo.png");
		this.puntos = 100;
		this.random = new Random();
		this.factorDesplazamiento = 1.0;
	    this.ultimoCambioDireccion = System.currentTimeMillis(); 
	    this.vivo = true;
	    this.gravedad = 5;
	    cayendo = false;
	    this.direccion = 1;
	    this.moviendoseALaDerecha = rand.nextBoolean(); // movimiento random del gnomo
	    this.estaEnIslaInferior = false;
	}

	public Gnomos(int i, int j, int k, int l, int m, boolean b, int tiempoCongelado) {
		// TODO Auto-generated constructor stub
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

	/* public void reSpawn() {
		y = 0;
	} */


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
				estaEnIslaInferior = islas[z].esIslaInferior();
				return true;
			}
		}
		return false;
	}
	public void cayoSobreUnaIsla(Isla[] islas) {
		if(cayendo && estaSobreAlgunaIsla(islas)) {
			cayendo = false;
			if (rand.nextBoolean()) {
				cambiarDireccion();
			}
		}
	}

	public boolean chocoAlHeroe(Pep h) {
		if ((x >= h.getX() - h.getAncho() / 2)
				&& (x <= h.getX() + h.getAncho() / 2)
				&& (y <= h.getY() + h.getAlto() / 2)
				&& (y >= h.getY() - h.getAlto() / 2)) {
			return estaEnIslaInferior;
		}
		return false;
	}

	public int getPuntos() {
		return puntos;
	}

	public void morir() {
		vivo = false;
	}

	public boolean fueChocadoPorUnEnemigo(Tortugas[] tortuga) {
		for (Tortugas h : tortuga) {
			if (this.estaVivo() && h != null
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
		if (vivo) {
			y += gravedad;
			cayendo = true;
		}
	}
	
	public boolean ultimaIslaEsInferior() {
		return estaEnIslaInferior;
	}
	public boolean chocoConBomba(Bombas[] bomba) {
	    for (Bombas bomb : bomba) {
	        if (bomb != null && this.estaVivo()) {
	            // Coordenadas de los bordes de la bola de fuego
	            double bombaXIzquierda = bomb.getX() - 10;  // 10 es la mitad del ancho de la fireball
	            double bombaXDerecha = bomb.getX() + 10;
	            double bombaYSuperior = bomb.getY() - 10;   // 10 es la mitad de la altura de la fireball
	            double bombaYInferior = bomb.getY() + 10;

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
	    }
	    return false;  // No hubo colisión
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

