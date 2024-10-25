package ElGnomo;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;

import entorno.Entorno;
import entorno.Herramientas;

public class Pep {
	private double x;
	private double y;
	private int ancho;
	private int alto;
	private boolean saltando;
    private int anguloFireball;
    private ArrayList<Fireball> fireballs;
	private double factorDesplazamiento;
	// private double impulso;
	// private double limiteDeSalto;
	private boolean derecha;
	private double gravedad = 2.5;
	private Image imagenDerecha;
	private Image imagenIzquierda;
	private int vidas;
	private boolean vivo;
	private boolean cayendo;
	private Isla[] islas;
	private boolean apoyado;
	private int contadorSalto;
	private int tiempoSalto;
	private long tiempoInmortalInicio;
	private static final long DURACION_INMORTAL = 5000;
	private boolean inmortal;
	
	

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
        this.anguloFireball = 0;
        this.fireballs = new ArrayList<>();
        this.imagenDerecha = Herramientas.cargarImagen("imagenes/stepep.png");
        this.imagenIzquierda = Herramientas.cargarImagen("imagenes/stepe.png");
        this.contadorSalto = 0;
        this.apoyado = false;
        this.cayendo = false;
        this.saltando = false;
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

	public void dibujar(Entorno e) {
		if (vivo)
			e.dibujarImagen(derecha ? imagenDerecha : imagenIzquierda, x, y, 0,
					0.3);

	}
	public void activarInmortalidad() {
        this.inmortal = true;
        this.tiempoInmortalInicio = System.currentTimeMillis();
    }
	public void actualizarInmortalidad() {
        if (this.inmortal && (System.currentTimeMillis() - this.tiempoInmortalInicio) > DURACION_INMORTAL) {
            this.inmortal = false; // Se desactiva la inmortalidad después de 5 segundos
        }
    }
	public boolean esInmortal() {
        return this.inmortal;
    }

	public void moverIzquierda(Entorno e) {
		x -= factorDesplazamiento;
	}

	public void moverDerecha(Entorno e) {
		x += factorDesplazamiento;
	} 
	
	
	public void caer() {
		// y += factorDesplazamiento+impulso*3/2;
		if (vivo) {
			y += gravedad;
			cayendo = true;
		}
	}
	public boolean estaCayendo() {
		return cayendo;
	}
	public void caerSubir() {
		final double velocidad_caida = 183.0;
		final double velocidad_salto = 85.0;
		final int limite_salto = 0;

		// Verificar si está cayendo
		if (!apoyado && !saltando) {
			cayendo = true;
			y += velocidad_salto;
		} else {
			cayendo = false;
		}

		// Ejecutar salto
		if (saltando) {
			y -= velocidad_caida;
			contadorSalto++;
			// Terminar salto si excede el límite
			if (this.contadorSalto > limite_salto) {
				saltando = false;
				cayendo = true;
				contadorSalto = 0;
			}
		}
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
        Fireball fireball = new Fireball(this.x, this.y + 10, anguloFireball, derecha);
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
	    public boolean isApoyado() {
			return apoyado;
		}
	    public boolean isSaltando() {
			return saltando;
		}
	    public boolean isCayendo() {
			return cayendo;
		}
	    public void setSaltando(boolean saltando) {
			this.saltando = saltando;
		}
	    public void setCayendo(boolean cayendo) {
			this.cayendo = cayendo;
		}
	    public void setApoyado(boolean apoyado) {
			this.apoyado = apoyado;
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
            apoyado = true;
            return true;
        }
    }
    return false;
}
public boolean chocoAlgunEnemigo(Tortugas[] tortuga) {
	for (Tortugas e : tortuga) {
		if ((x + ancho / 3 >= e.getX() - e.getAncho() / 2)
				&& (x - ancho / 3 <= e.getX() + e.getAncho() / 2)
				&& (y + alto / 3 <= e.getY() + e.getAlto() / 2)
				&& (y + alto / 3 >= e.getY() - e.getAlto() / 2)
				&& e.estaVivo()) {
			return true;
		}
	}
	return false;
}
public Fireball dispararFireball() {
    // Ajusta el valor de "y" para disparar desde una posición más baja
    double alturaDisparo = this.y + 30;  // Baja el disparo 30 píxeles desde la posición de Pep

    // Crea un nuevo Fireball hacia la derecha o izquierda
    return new Fireball(this.x, alturaDisparo, 0, this.derecha);
}
public void morir(Entorno e) {
    if (vivo) {
        // Esta parte solo debe afectar a la variable 'vivo' y la posición de Pep, no su tamaño
        vivo = false;
    }
}



}
