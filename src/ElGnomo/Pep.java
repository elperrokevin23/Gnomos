package ElGnomo;

import java.awt.Image;
import java.util.ArrayList;
import ElGnomo.Juego.MovimientoEstado;
import entorno.Entorno;
import entorno.Herramientas;

public class Pep {
	private double x;
	private double y;
	private int ancho;
	private int alto;
	static boolean saltando = false; // Estado de salto de Pep.
    private int anguloFireball; // Ángulo de lanzamiento de las bolas de fuego.
    private ArrayList<Fireball> fireballs; // Lista de bolas de fuego lanzadas por Pep.
	private double factorDesplazamiento; // Factor de desplazamiento horizontal de Pep.
	private double impulso; // Impulso de salto de Pep.
	private double limiteDeSaltoY; // Altura máxima a la que puede saltar Pep.
	private boolean derecha; // Indica si Pep está mirando a la derecha.
	private double gravedad = 8; // Gravedad que afecta a Pep.
	private Image imagenDerecha; // Imagen de Pep mirando a la derecha.
	private Image imagenIzquierda; // Imagen de Pep mirando a la izquierda.
	private boolean vivo; // Estado de vida de Pep.
	private boolean cayendo; // Estado de caída de Pep.
	private boolean terminarSalto; // Control para finalizar el salto.
	private boolean inmortal; // Estado de inmortalidad de Pep.
	private long tiempoInmortalInicio; // Tiempo de inicio de la inmortalidad.
	

	public Pep(double x, double y, int ancho, int alto, double f,
			boolean der, int tiempoCongelado) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.factorDesplazamiento = f;
		this.derecha = der;
		vivo = true;
        this.anguloFireball = 0;
        this.fireballs = new ArrayList<>();
        this.imagenDerecha = Herramientas.cargarImagen("pepDerecha.png");
        this.imagenIzquierda = Herramientas.cargarImagen("pepIzquierda.png");
        this.impulso = 6.5;
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
	
	// Activa el estado de inmortalidad de Pep.
	public void activarInmortalidad() {
        this.inmortal = true;
     // Registra el tiempo de inicio.
        this.tiempoInmortalInicio = System.currentTimeMillis();
    }
	
	// Actualiza el estado de inmortalidad.
	public void actualizarInmortalidad() {
        if (this.inmortal && (System.currentTimeMillis() - this.tiempoInmortalInicio) > 4000) {
        	// Se desactiva la inmortalidad después de 4 segundos.
            this.inmortal = false;
        }
    }
	
	public boolean esInmortal() {
		// Retorna si Pep es inmortal.
        return this.inmortal;
    }
	
	
	 // Dibuja a Pep en el entorno.
	public void dibujar(Entorno e) {
		if (vivo)
			e.dibujarImagen(derecha ? imagenDerecha : imagenIzquierda, x, y, 0,
					0.3);

		else {
				e.dibujarImagen(derecha ? imagenDerecha : imagenIzquierda, x, y, 0,
						0.3);
		}
	}
	
	// Mueve a Pep hacia la izquierda.
	public void moverIzquierda(Entorno e) {
		x -= factorDesplazamiento;
	}
	
	// Mueve a Pep hacia la derecha.
	public void moverDerecha(Entorno e) {
		x += factorDesplazamiento;
	} 
	
	// Hace que Pep salte.
	public void saltar() {
		if (vivo && !saltando && !terminarSalto && !estaCayendo()) {	
    		saltando = true;
    		// Define el límite del salto.
    		limiteDeSaltoY = getY() - 120;
		}
	}
	
	// Hace que Pep caiga.
	public void caer() {
		cayendo = true;
		if (vivo) {
			// Aplica la gravedad en la caída.
			y += gravedad;
		}
	}
	
	// Retorna si Pep está cayendo.
	public boolean estaCayendo() {
		return cayendo;
	}
	
	// Detiene la caída de Pep.
	public void dejarDeCaer() {
		cayendo = false;
	}

	// Verifica si Pep llegó al fondo del entorno.
	public boolean llegoFondo(Entorno e) {
		return y > e.alto();
	}
	
	// Hace que Pep mire a la izquierda.
	public void mirarIzquierda() {
		this.derecha = false;
	}

	// Hace que Pep mire a la derecha.
	public void mirarDerecha() {
		this.derecha = true;
	}
	
	// Retorna si Pep está saltando.
	public boolean estaSaltando() {
		return saltando || terminarSalto;
	}
	
	// Controla el movimiento durante el salto.
	public void moverSalto(MovimientoEstado movimientoSalto)	{
		if (vivo && saltando) {
			if (getY() > limiteDeSaltoY)
			{
				// Aumenta la altura en el salto.
				y -= gravedad + impulso;	
			}
			else
			{
				saltando = false;
				terminarSalto = true;
			}
		}
		else {
			terminarSalto = false;
		}
	}
	

	// Verifica si Pep está sobre alguna isla.
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
	
	// Retorna si Pep tiene vidas restantes.

	// Retorna si Pep está vivo.
	public boolean estaVivo() {
		return vivo;
	}
	// Lanza una bola de fuego.
	public void lanzarBola() {
		// Define el ángulo
        this.anguloFireball = (derecha) ? 0 : 180;
        Fireball fireball = new Fireball(this.x, this.y, derecha);
     // Añade la nueva bola de fuego a la lista
        fireballs.add(fireball); 
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



   //Verifica si Pep colisiona con alguna Tortuga.
public boolean chocoAlgunEnemigo(Tortugas[] tortuga) {
	for (Tortugas tort : tortuga) {
		if ( (tort != null) && this.estaVivo()) {
			// Coordenadas de los bordes de las tortuga
			double tortugaXIzquierda = tort.getX() - 10;
			double tortugaXDerecha = tort.getX() + 10;
			double tortugaYSuperior = tort.getY() - 10;
			double tortugaYInferior = tort.getY() + 10;
			
			// Coordenadas de los bordes de pep
			double pepXIzquierda = this.getX() - this.getAncho() / 2;
            double pepXDerecha = this.getX() + this.getAncho() / 2;
            double pepYSuperior = this.getY() - this.getAlto() / 2;
            double pepYInferior = this.getY() + this.getAlto() / 2;
            
         // Verifica si hay colisión
            if (tortugaXIzquierda <= pepXDerecha && tortugaXDerecha >= pepXIzquierda && tortugaYInferior >= pepYSuperior && tortugaYSuperior <= pepYInferior) {
			return true;
		}
	}
	}
	return false;
}

//Verifica si Pep colisiona con alguna bomba.
public boolean chocoConBomba(Bombas[] bomba) {
    for (Bombas bomb : bomba) {
        if (bomb != null && this.estaVivo()) {
            // Coordenadas de los bordes de la bomba
            double bombaXIzquierda = bomb.getX() - 10;  // 10 es la mitad del ancho de la bomba
            double bombaXDerecha = bomb.getX() + 10;
            double bombaYSuperior = bomb.getY() - 10;   // 10 es la mitad de la altura de la bomba
            double bombaYInferior = bomb.getY() + 10;

            // Coordenadas de los bordes de pep
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

   // Retorna si Pep está mirando hacia la derecha.
public boolean mirandoDerecha() {
	return this.derecha;
}
public boolean chocoIzquierda(Entorno e) {
	return x - ancho / 2 <= 0;
}

public boolean chocoDerecha(Entorno e) {
	return x + ancho / 2 >= e.ancho();
}


   //Cambia el estado de Pep a muerto.
public void morir(Entorno e) {
    if (vivo) {
    	// Marca a Pep como no vivo.
        vivo = false;
    }
}



}
