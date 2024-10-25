package ElGnomo;

import java.awt.Color;

import entorno.Entorno;
import entorno.InterfaceJuego;
import entorno.Herramientas;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import entorno.Board;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Juego extends InterfaceJuego {

	private static final int TIEMPO_DE_COSO_POWER_UP = 350;
	private static final int ALTO_ESCENARIO = 600;
	private static final int ANCHO_ESCENARIO = 800;
	private static final int ALTO_DEL_SECTOR_DE_PUNTUACION = 100;
	private Entorno entorno;
	private Board board;
	private Isla[] islas;
	private Pep pep;
	private Gnomos[] gnomo;
	private Image fondo;
	private Image fondoWin;
	private int cantidadDeTics;
	private Score puntuacion;
	private int cantidadDeEnemigos;
	private int scoreFinal = 0;
	private long ultimoDisparo = 0; // Tiempo del último disparo en milisegundos
	private final long INTERVALO_DISPARO = 3000; // 3000 ms = 3 segundos
	private int puntos = 0;
	private Fireball[] fireballs;
	private Herramientas herramientas;
	private Image Casa;
	private Casa casa;
	private Image imagenDeVigas;
	private Tortugas[] tortuga;
	boolean juegoTerminado = false;
	Image GameOverImage = null;
	private Image ganadorImage = null;
	private boolean haGanado = false; // Estado de la victoria
	private int gnomostocados = 0;
	private int gnomosperdidos = 0;
	private boolean haPerdido = false;
	

	public Juego() {
		int unAncho = 150;
		int unAlto = 10;
		int tiempoCongelado = 200; 
				
		fondo = Herramientas.cargarImagen("imagenes/fondo.jpg");
		entorno = new Entorno(
				this,
				"Gnomos",
				ANCHO_ESCENARIO, ALTO_DEL_SECTOR_DE_PUNTUACION + ALTO_ESCENARIO);
		imagenDeVigas = Herramientas.cargarImagen("imagenes/grasss.png");
		Casa = Herramientas.cargarImagen("imagenes/casaa.png");
		
		islas = new Isla[15]; // Array para las 15 islas

		// Fila 1: Una viga centrada
		islas[0] = new Isla(entorno.ancho() / 2, ALTO_ESCENARIO / 6, unAncho, unAlto, imagenDeVigas);

		// Fila 2: Dos vigas (izquierda y derecha)
		islas[1] = new Isla(entorno.ancho() - 495, (ALTO_ESCENARIO / 6) * 2, unAncho, unAlto, imagenDeVigas);
		islas[2] = new Isla((entorno.ancho() - 552) * 2, (ALTO_ESCENARIO / 6) * 2, unAncho, unAlto, imagenDeVigas);

		// Fila 3: Tres vigas (distribuidas uniformemente)
		islas[3] = new Isla(entorno.ancho() - 590, (ALTO_ESCENARIO / 6) * 3, unAncho, unAlto, imagenDeVigas);
		islas[4] = new Isla(entorno.ancho() / 2, (ALTO_ESCENARIO / 6) * 3, unAncho, unAlto, imagenDeVigas);
		islas[5] = new Isla(entorno.ancho() - 215, (ALTO_ESCENARIO / 6) * 3, unAncho, unAlto, imagenDeVigas);

		// Fila 4: Cuatro vigas (distribuidas uniformemente)
		islas[6] = new Isla(entorno.ancho() - 680, (ALTO_ESCENARIO / 6) * 4, unAncho, unAlto, imagenDeVigas);
		islas[7] = new Isla(entorno.ancho() - 495, (ALTO_ESCENARIO / 6) * 4, unAncho, unAlto, imagenDeVigas);
		islas[8] = new Isla(entorno.ancho() - 310, (ALTO_ESCENARIO / 6) * 4, unAncho, unAlto, imagenDeVigas);
		islas[9] = new Isla(entorno.ancho() - 130, (ALTO_ESCENARIO / 6) * 4, unAncho, unAlto, imagenDeVigas);

		// Fila 5: Cinco vigas (distribuidas uniformemente)
		islas[10] = new Isla(entorno.ancho() - 770, (ALTO_ESCENARIO / 6) * 5, unAncho, unAlto, imagenDeVigas);
		islas[11] = new Isla(entorno.ancho() - 580, (ALTO_ESCENARIO / 6) * 5, unAncho, unAlto, imagenDeVigas);
		islas[12] = new Isla((entorno.ancho() / 2), (ALTO_ESCENARIO / 6) * 5, unAncho, unAlto, imagenDeVigas);
		islas[13] = new Isla(entorno.ancho() - 220, (ALTO_ESCENARIO / 6) * 5, unAncho, unAlto, imagenDeVigas);
		islas[14] = new Isla(entorno.ancho() - 35, (ALTO_ESCENARIO / 6) * 5, unAncho, unAlto, imagenDeVigas);
		pep = new Pep((entorno.ancho()/2), (entorno.alto()/6)-45, 20, 30,
				2.5, true, tiempoCongelado);
		gnomo = new Gnomos[4];
		gnomo[0] = new Gnomos((entorno.ancho()/2)+5,(entorno.alto()/6)-40,10,10,"gnomo3.png");
		gnomo[1] = new Gnomos((entorno.ancho()/2)-5,(entorno.alto()/6)-40,10,10,"gnomo3.png");
		gnomo[2] = new Gnomos((entorno.ancho()/2)+10,(entorno.alto()/6)-40,10,10,"gnomo3.png");
		gnomo[3] = new Gnomos((entorno.ancho()/2)-10,(entorno.alto()/6)-40,10,10,"gnomo3.png");
		tortuga = new Tortugas[4];
		tortuga[0] = new Tortugas(entorno.ancho()-690,(entorno.alto()-700),10,10,"tortuga2.png");
		tortuga[1] = new Tortugas(entorno.ancho()-590,(entorno.alto()-700),10,10,"tortuga2.png");
		tortuga[2] = new Tortugas(entorno.ancho()-215,(entorno.alto()-700),10,10,"tortuga2.png");
		tortuga[3] = new Tortugas(entorno.ancho()-105,(entorno.alto()-700),10,10,"tortuga2.png");
		casa = new Casa((entorno.ancho()/2)-5, (entorno.alto() / 6)-47, 40, 30);
		cantidadDeTics = 0;
		puntuacion = new Score();
		entorno.cambiarFont("Arial", 18, Color.WHITE);
		entorno.cambiarFont("Arial", 20, Color.WHITE);
		entorno.iniciar();
		herramientas.loop("sonidos/Undertale.wav");

	}

	public void tick() {
			entorno.dibujarImagen(fondo, entorno.ancho() / 2, entorno.alto() / 2 , 0, 1.17);
			for (Isla v : islas) {
				v.dibujar(entorno);
			}
            pep.actualizarInmortalidad();
			pep.dibujar(entorno);
			for (int i = 0; i < gnomo.length; i++) {
			    if (gnomo[i] != null) {  // Verifica si el gnomo existe
			        gnomo[i].dibujar(entorno);  // Dibuja el gnomo
			        
			        if (!gnomo[i].estaSobreAlgunaIsla(islas)) {
			            gnomo[i].caer();  // Si no está sobre una isla, cae
			            gnomo[i].cambiarDireccionSiTocaIsla(islas);
		                if (gnomo[i].llegoFondo(entorno)) {
		                	gnomo[i].reSpawn();
		                }
			        } 
			        
			                  else {
			            if (gnomo[i].estaVivo()) {  // Si está vivo, puede moverse y chequear colisiones
			                gnomo[i].mover();

			                if (gnomo[i].chocoDerecha(entorno) || gnomo[i].chocoIzquierda(entorno)) {
			                    gnomo[i].cambiarDireccion();  // Si choca con los bordes, cambia de dirección
			                }
			         

			                // Verifica si choca con el héroe (Pep)
			                if (gnomo[i].chocoAlHeroe(pep) && pep.getY() > 300) {
			                    gnomo[i].reSpawn();
			                    pep.activarInmortalidad();

			                    // Si el jugador ha tocado exactamente 20 gnomos, marca como ganador y almacena el score final
			                    if (gnomostocados == 20 && !haGanado) {
			                        haGanado = true;  // Marca como ganador
			                        scoreFinal = puntuacion.getScore();  // Guarda el puntaje final
			                    }
			                    	
			                    	
			                }
			                if (gnomo[i].fueChocadoPorUnEnemigo(tortuga)) {
			                	gnomo[i].reSpawn();
			                }
			            }
			        }
			    }
			}
			
			for (int j = 0; j < tortuga.length; j++) {
			    if (tortuga[j] != null) {
			    	tortuga[j].dibujar(entorno);
			        if (!tortuga[j].estaSobreAlgunaIsla(islas)) {
			            tortuga[j].caer();
			        } else {
			        	if(tortuga[j].estaVivo()) {
			            tortuga[j].moverEnIsla(islas);
			            tortuga[j].mover();
			            if (tortuga[j].chocoDerecha(entorno) || tortuga[j].chocoIzquierda(entorno)) {
			                tortuga[j].cambiarDireccion();
			            }
			        }
			        
			        // Este `else` debe ir si la tortuga no está viva y debe verificar la colisión
			    }
			}
			}
			    


	    long tiempoActual = System.currentTimeMillis();

	    pep.actualizarFireballs(); // Mueve las bolas de fuego
        pep.dibujarFireballs(entorno); // Dibuja las bolas de fuego
        casa.dibujar(entorno, Casa);
        puntuacion.dibujar(entorno);
        if (pep.estaVivo()) {
        	pep.setApoyado(true);
        	pep.dibujar(entorno);
            if (!pep.estaSobreAlgunaIsla(islas)) {
                pep.caer();
                if (pep.llegoFondo(entorno)) {
                    pep.morir(entorno);
                    herramientas.loop("sonidos/Super-Mario-Bros.wav");
                }
                
            } else {
                if (entorno.estaPresionada('C')) { {
                        pep.lanzarBola();
                    }
                }
                if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
                    pep.moverIzquierda(entorno);
                    pep.mirarIzquierda();
                }
                if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
                    pep.moverDerecha(entorno);
                    pep.mirarDerecha();
                    
                }
                if (entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
                	pep.setSaltando(true);
                }
                if(!pep.esInmortal()) {
                if (pep.chocoAlgunEnemigo(tortuga)) {
                    pep.morir(entorno);
                    herramientas.loop("sonidos/Super-Mario-Bros.wav");
                }
                }
        }
            pep.caerSubir();
            } 
        else {
            // Si Pep está muerto y no ha ganado, mostrar "Game Over"
            if (!juegoTerminado) {
                juegoTerminado = true;
                GameOverImage = Herramientas.cargarImagen("GameOver.gif");
            }
            entorno.dibujarImagen(GameOverImage, entorno.ancho() / 2, entorno.alto() / 2, 0, 1.17);
        }
    }
	public static void main(String[] args) {
		Juego juego = new Juego();
	}

}
