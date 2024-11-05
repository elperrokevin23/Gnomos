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
	private double factorDesplazamiento;
	private boolean vivo;
	private Image imagen;
	private int gravedad;
	private boolean cayendo;
	private boolean moviendoseALaDerecha;
	private boolean estaEnIslaInferior;
	
	private static Random rand = new Random();

	public Gnomos(double x, double y, int ancho, int alto) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.imagen = Herramientas.cargarImagen("gnomo.png");
		this.factorDesplazamiento = 1.0;
	    this.vivo = true;
	    this.gravedad = 5;
	    cayendo = false;
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
			if ((x + ancho / 2 >= islas[z].getX() - islas[z].getAncho() / 2) //Verifica que el borde izquierdo del gnomo esté a la derecha o sobre el borde izquierdo de la isla
					&& (x - +ancho / 2 <= islas[z].getX() + islas[z].getAncho() //Verifica que el borde derecho del gnomo esté a la izquierda o sobre el borde derecho de la isla
							/ 2)
					&& (y + alto / 2 <= islas[z].getY() + islas[z].getAlto() // Verifica que la parte inferior del gnomo esté arriba o sobre el borde inferior de la isla
							/ 2)
					&& (y + alto / 2 >= islas[z].getY() - islas[z].getAlto() //Verifica que la parte superior del gnomo esté abajo o sobre el borde superior de la isla
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
		if ((h != null) && this.estaVivo()) { 
			// Coordenadas de los bordes de pep
			double pepXIzquierda = h.getX() - 10;
			double pepXDerecha = h.getX() + 10;
			double pepYSuperior = h.getY() - 10;
			double pepYInferior = h.getY() + 10;
			
			// Coordenadas de los bordes del gnomo
			double gnomoXIzquierda = this.getX() - this.getAncho() / 2;
            double gnomoXDerecha = this.getX() + this.getAncho() / 2;
            double gnomoYSuperior = this.getY() - this.getAlto() / 2;
            double gnomoYInferior = this.getY() + this.getAlto() / 2;
            
         // Verifica si hay colisión
            if (pepXIzquierda <= gnomoXDerecha && pepXDerecha >= gnomoXIzquierda && pepYInferior >= gnomoYSuperior && pepYSuperior <= gnomoYInferior) {
			return estaEnIslaInferior;
		}
	}
	return false;
}


	public void morir() {
		vivo = false;
	}

	public boolean fueChocadoPorUnEnemigo(Tortugas[] tortuga) {
		for (Tortugas tort : tortuga) {
			if ( (tort != null) && this.estaVivo()) {
				// Coordenadas de los bordes de las tortuga
				double tortugaXIzquierda = tort.getX() - 10;
				double tortugaXDerecha = tort.getX() + 10;
				double tortugaYSuperior = tort.getY() - 10;
				double tortugaYInferior = tort.getY() + 10;
				
				// Coordenadas de los bordes del gnomo
				double gnomoXIzquierda = this.getX() - this.getAncho() / 2;
	            double gnomoXDerecha = this.getX() + this.getAncho() / 2;
	            double gnomoYSuperior = this.getY() - this.getAlto() / 2;
	            double gnomoYInferior = this.getY() + this.getAlto() / 2;
	            
	         // Verifica si hay colisión
	            if (tortugaXIzquierda <= gnomoXDerecha && tortugaXDerecha >= gnomoXIzquierda && tortugaYInferior >= gnomoYSuperior && tortugaYSuperior <= gnomoYInferior) {
				return true;
			}
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
	public boolean chocoConBomba(Bombas bomba) {
	        if (bomba != null && this.estaVivo()) {
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

