package ElGnomo;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;

import ElGnomo.Juego.MovimientoEstado;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.Herramientas;

public class Pep {
	private double x;
	private double y;
	private int ancho;
	private int alto;
	static boolean saltando = false;
    private int anguloFireball;
    private String direccion;
    private ArrayList<Fireball> fireballs;
	private double factorDesplazamiento;
	private double impulso;
	private double limiteDeSaltoY;
	private boolean derecha;
	private double gravedad = 3;
	private Image imagenDerecha;
	private Image imagenIzquierda;
	private Image imagenMuerto;
	private int vidas;
	private boolean vivo;
	private boolean cayendo;
	private boolean terminarSalto;
	private boolean inmortal;
	private long tiempoInmortalInicio;
	

	public Pep(double x, double y, int ancho, int alto, double f,
			boolean der, int tiempoCongelado) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.factorDesplazamiento = f;
		this.derecha = der;
		this.vidas = 5;
		vivo = true;
        this.direccion = "derecha";
        this.anguloFireball = 0;
        this.fireballs = new ArrayList<>();
        this.imagenDerecha = Herramientas.cargarImagen("stepep.png");
        this.imagenIzquierda = Herramientas.cargarImagen("stepe.png");
        this.impulso = 4;
        cayendo = false;
    	terminarSalto = false;
    	this.inmortal = false;
    	this.tiempoInmortalInicio = 0;
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
	public void activarInmortalidad() {
        this.inmortal = true;
        this.tiempoInmortalInicio = System.currentTimeMillis();
    }
	public void actualizarInmortalidad() {
        if (this.inmortal && (System.currentTimeMillis() - this.tiempoInmortalInicio) > 3000) {
            this.inmortal = false; // Se desactiva la inmortalidad después de 3 segundos
        }
    }
	public boolean esInmortal() {
        return this.inmortal;
    }

	public void dibujar(Entorno e) {
		if (vivo)
			e.dibujarImagen(derecha ? imagenDerecha : imagenIzquierda, x, y, 0,
					0.3);

		else {
				e.dibujarImagen(derecha ? imagenDerecha : imagenIzquierda, x, y, 0,
						0.3);
				e.cambiarFont("Arial", 18, Color.GREEN);
		}
	}

	public void moverIzquierda(Entorno e) {
		x -= factorDesplazamiento;
	}

	public void moverDerecha(Entorno e) {
		x += factorDesplazamiento;
	} 
	public void saltar() {
		if (vivo && !saltando && !terminarSalto && !estaCayendo()) {	
    		saltando = true;
    		limiteDeSaltoY = getY() - 120;
		}
	}
	public void caer() {
		// y += factorDesplazamiento+impulso*3/2;
		cayendo = true;
		if (vivo) {
			y += gravedad;
		}
	}
	public boolean estaCayendo() {
		return cayendo;
	}
	
	public void dejarDeCaer() {
		cayendo = false;
	}
	
	public boolean aterrizaSobreViga(boolean sobre) {
		if (sobre && cayendo) {
			cayendo = false;
			return true;
		}
		return false;
	}


	public boolean llegoFondo(Entorno e) {
		return y > e.alto();
	}


	public void mirarIzquierda() {
		this.derecha = false;
	}

	public void mirarDerecha() {
		this.derecha = true;
	}

	public boolean estaSaltando() {
		return saltando || terminarSalto;
	}
	
	public void moverSalto(MovimientoEstado movimientoSalto)	{
		if (vivo && saltando) {
			if (getY() > limiteDeSaltoY)
			{
				y -= gravedad + impulso;
				x += movimientoSalto.getNumVal() * factorDesplazamiento * 0.5;		
			}
			else
			{
				saltando = false;
				terminarSalto = true;
			}
		}
		
		if (estaCayendo() && terminarSalto) {
			x += movimientoSalto.getNumVal() * factorDesplazamiento * 0.5;
		}
		else {
			terminarSalto = false;
		}
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


	public boolean tengoVida() {
		return (vidas > 0);
	}

	public boolean estaVivo() {
		return vivo;
	}
	public void lanzarBola() {
        this.anguloFireball = (derecha) ? 0 : 180; // Define el ángulo si es necesario
        Fireball fireball = new Fireball(this.x, this.y, derecha);
        fireballs.add(fireball); // Añade la nueva bola de fuego a la lista
    }
	 public void actualizarFireballs() {
	        for (Fireball fireball : fireballs) {
	            fireball.mover(); // Mueve cada bola de fuego
	        }
	    }
	    // Método para dibujar las bolas de fuego
	    public void dibujarFireballs(Entorno e) {
	        for (Fireball fireball : fireballs) {
	            fireball.dibujar(e); // Dibuja cada bola de fuego
	        }
	    }
	    
public boolean aterrizaSobreIsla(Isla[] islas) {
    for (Isla isla : islas) {
        if ((x + ancho / 2 >= isla.getX() - isla.getAncho() / 2) &&
            (x - ancho / 2 <= isla.getX() + isla.getAncho() / 2) &&
            (y + alto / 2 >= isla.getY() - isla.getAlto() / 2) &&
            (y + alto / 2 <= isla.getY() + isla.getAlto() / 2) &&
            cayendo) {
            y = isla.getY() - alto / 2; // Asegúrate de que Pep no atraviese la isla
            cayendo = false; // Deja de caer
            saltando = false; // Deja de saltar
            return true;
        }
    }
    return false;
}
public boolean chocoAlgunEnemigo(Tortugas[] tortuga) {
	for (Tortugas e : tortuga) {
		if ( (e != null) && (x + ancho / 3 >= e.getX() - e.getAncho() / 2)
				&& (x - ancho / 3 <= e.getX() + e.getAncho() / 2)
				&& (y + alto / 3 <= e.getY() + e.getAlto() / 2)
				&& (y + alto / 3 >= e.getY() - e.getAlto() / 2)) {
			return true;
		}
	}
	return false;
}
public boolean mirandoDerecha() {
	return this.derecha;
}
public void morir(Entorno e) {
    if (vivo) {
        // Esta parte solo debe afectar a la variable 'vivo' y la posición de Pep, no su tamaño
        vivo = false;
    }
}



}
