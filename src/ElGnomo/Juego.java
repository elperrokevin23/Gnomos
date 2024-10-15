package ElGnomo;

import java.awt.Color;

import entorno.Entorno;
import entorno.InterfaceJuego;
import entorno.Herramientas;
import java.awt.Image;
import java.io.File;

public class Juego extends InterfaceJuego {

	private static final int TIEMPO_DE_COSO_POWER_UP = 350;
	private static final int ALTO_ESCENARIO = 600;
	private static final int ANCHO_ESCENARIO = 800;
	private static final int ALTO_DEL_SECTOR_DE_PUNTUACION = 100;
	private Entorno entorno;
	private Isla[] islas;
	private Pep pep;
	private Gnomos[] gnomo;
	private Image fondo;
	private Image fondoGameOver;
	private Image fondoWin;
	private int cantidadDeTics;
	private Score puntuacion;
	private int cantidadDeEnemigos;
	private int scoreFinal = 0;
	private long ultimoDisparo = 0; // Tiempo del último disparo en milisegundos
	private final long INTERVALO_DISPARO = 3000; // 3000 ms = 3 segundos
	private int puntos = 0;
	private GrupoDeFireballs fireballs;
	private Image Casa;
	private Casa casa;
	private Image imagenDeVigas;

	public Juego() {
		int unAncho = 100;
		int unAlto = 20;
		int tiempoCongelado = 200; 
				
		fondo = Herramientas.cargarImagen("fondo.jpg");
		entorno = new Entorno(
				this,
				"Gnomos",
				ANCHO_ESCENARIO, ALTO_DEL_SECTOR_DE_PUNTUACION + ALTO_ESCENARIO);
		imagenDeVigas = Herramientas.cargarImagen("grasss.png");
		Casa = Herramientas.cargarImagen("casaa.png");
		
		Isla[] islas = {
			    // Fila 1: Una viga centrada
			    new Isla(entorno.ancho() / 2, ALTO_ESCENARIO / 6, unAncho, unAlto, imagenDeVigas),

			    // Fila 2: Dos vigas (izquierda y derecha)
			    new Isla(entorno.ancho() -495, (ALTO_ESCENARIO / 6) * 2, unAncho, unAlto, imagenDeVigas),
			    new Isla((entorno.ancho() -552) * 2, (ALTO_ESCENARIO / 6) * 2, unAncho, unAlto, imagenDeVigas),

			    // Fila 3: Tres vigas (distribuidas uniformemente)
			    new Isla(entorno.ancho() -590, (ALTO_ESCENARIO / 6) * 3, unAncho, unAlto, imagenDeVigas),
			    new Isla(entorno.ancho() / 2, (ALTO_ESCENARIO / 6) * 3, unAncho, unAlto, imagenDeVigas),
			    new Isla((entorno.ancho() -215), (ALTO_ESCENARIO / 6) * 3, unAncho, unAlto, imagenDeVigas),

			    // Fila 4: Cuatro vigas (distribuidas uniformemente)
			    new Isla(entorno.ancho() -680, (ALTO_ESCENARIO / 6) * 4, unAncho, unAlto, imagenDeVigas),
			    new Isla(entorno.ancho() -495, (ALTO_ESCENARIO / 6) * 4, unAncho, unAlto, imagenDeVigas),
			    new Isla(entorno.ancho() -310, (ALTO_ESCENARIO / 6) * 4, unAncho, unAlto, imagenDeVigas),
			    new Isla(entorno.ancho() -130, (ALTO_ESCENARIO / 6) * 4, unAncho, unAlto, imagenDeVigas),

			    // Fila 5: Cinco vigas (distribuidas uniformemente)
			    new Isla(entorno.ancho()- 730, (ALTO_ESCENARIO / 6) * 5, unAncho, unAlto, imagenDeVigas),
			    new Isla(entorno.ancho() - 580, (ALTO_ESCENARIO / 6) * 5, unAncho, unAlto, imagenDeVigas),
			    new Isla((entorno.ancho() / 2), (ALTO_ESCENARIO / 6) * 5, unAncho, unAlto, imagenDeVigas),
			    new Isla(entorno.ancho() -220, (ALTO_ESCENARIO / 6) * 5, unAncho, unAlto, imagenDeVigas),
			    new Isla(entorno.ancho() -80, (ALTO_ESCENARIO / 6) * 5, unAncho, unAlto, imagenDeVigas),
			};
		this.islas = islas;
		pep = new Pep(entorno.ancho() -700, entorno.alto() -555, 20, 30,
				3, true, tiempoCongelado);
		Gnomos[] gnomo = {
				new Gnomos(entorno.ancho()/2, (entorno.alto() / 6) -40, 10, 10,"gnomo3.png"),
				new Gnomos(entorno.ancho()/2, (entorno.alto() / 6) -40, 10,
						10, "gnomo3.png"),
				new Gnomos(entorno.ancho()/2, (entorno.alto() / 6) -40, 10,
						10, "gnomo3.png"),
				new Gnomos(entorno.ancho()-110, (entorno.alto() -185) -40, 10,
						10, "gnomo3.png" )};
		casa = new Casa((entorno.ancho()/2)-5, (entorno.alto() / 6)-47, 40, 30);
		this.gnomo = gnomo;
		cantidadDeEnemigos = gnomo.length;
		cantidadDeTics = 0;
		fireballs = new GrupoDeFireballs();
		puntuacion = new Score();
		entorno.cambiarFont("Arial", 18, Color.WHITE);
		entorno.cambiarFont("Arial", 20, Color.WHITE);
		entorno.iniciar();

	}

	public void tick() {

		if (cantidadDeEnemigos > 0) {
			entorno.dibujarImagen(fondo, entorno.ancho() / 2, entorno.alto() / 2 , 0, 1.17);
			for (Isla v : islas) {
				v.dibujar(entorno);
			}
			pep.dibujar(entorno);
			for (Gnomos grom:gnomo) {
				if (!grom.estaSobreAlgunaIsla(islas)) {
			    grom.caer();
				}
				else {
				if (grom.estaVivo() && grom.chocoAlHeroe(pep)) {
					puntos += 30;
					grom.morir();
				}
				if (grom.estaVivo()) {
					grom.mover();
				}
			}
				grom.dibujar(entorno);
			}


	    long tiempoActual = System.currentTimeMillis();

	    pep.actualizarFireballs(); // Mueve las bolas de fuego
        pep.dibujarFireballs(entorno); // Dibuja las bolas de fuego
        casa.dibujar(entorno, Casa);
        if(pep.estaVivo()) {
        	if(!pep.estaSobreAlgunaIsla(islas)) {
        		pep.caer();
        	}
        	else {
        		if (entorno.sePresiono('C')) {
        	        // Verifica si han pasado al menos 5 segundos desde el último disparo
        	        if (tiempoActual - ultimoDisparo >= INTERVALO_DISPARO) {
        	            pep.lanzarBola();  // Lanza la bola de fuego
        	            ultimoDisparo = tiempoActual;  // Actualiza el tiempo del último disparo
        	        }
        	    }
        		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) && pep.estaVivo() && pep.estaSobreAlgunaIsla(islas)){
        	        pep.moverIzquierda(entorno);
        	        pep.mirarIzquierda();
        	    }
        		if (entorno.estaPresionada(entorno.TECLA_DERECHA) && pep.estaVivo() && pep.estaSobreAlgunaIsla(islas)) {
        	        pep.moverDerecha(entorno);
        	        pep.mirarDerecha();
        	    }
        	}
        }
		}}
	public static void main(String[] args) {
		Juego juego = new Juego();
	}

}
