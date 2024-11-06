package ElGnomo;

import java.awt.Color;

import entorno.Entorno;
import entorno.InterfaceJuego;
import entorno.Herramientas;
import java.awt.Image;
import java.util.Random;

import ElGnomo.Isla.IslaTipo;

public class Juego extends InterfaceJuego {

	private static final int ALTO_ESCENARIO = 600;
	private static final int ANCHO_ESCENARIO = 800;
	private static final int ALTO_DEL_SECTOR_DE_PUNTUACION = 100;
	private static final int INTERVALO_SPAWN_GNOMO = 1500;
	private Entorno entorno;
	private Isla[] islas;
	private Pep pep;
	private Gnomos[] gnomo;
	private Image fondo;
	private Image fondoWin;
	private Score puntuacion;
	private long ultimoDisparo = 0; // Tiempo del último disparo en milisegundos
	private int puntos = 0;
	private Fireball[] fireballs;
	private Image Casa;
	private Casa casa;
	private Image imagenDeVigas;
	private Tortugas[] tortuga;
	boolean juegoTerminado = false;
	Image GameOverImage = null;
	private boolean haGanado;
	private Bombas[] bomba;
	private long tiempoUltimoGnomoSpawneado;
	private int puntosPerdidos;

	public Juego() {
		int unAncho = 100;
		int unAlto = 20;
		int tiempoCongelado = 200; 
		haGanado = false;
		fondoWin = Herramientas.cargarImagen("GANADOR.jpeg");
		puntosPerdidos = 0;
		

		fondo = Herramientas.cargarImagen("fondo.jpg");
		entorno = new Entorno(
				this,
				"Gnomos",
				ANCHO_ESCENARIO, ALTO_DEL_SECTOR_DE_PUNTUACION + ALTO_ESCENARIO);
		imagenDeVigas = Herramientas.cargarImagen("pasto.png");
		Casa = Herramientas.cargarImagen("casa.png");

		// Crea un array para almacenar 15 objetos de tipo Isla
		islas = new Isla[15];
		// Define un array con las posiciones x de cada isla en el entorno, calculadas en función del ancho del entorno
		int[] xxPos = {entorno.ancho() / 2,entorno.ancho() - 310,entorno.ancho() - 495,entorno.ancho() - 580,entorno.ancho() / 2,entorno.ancho() - 220,entorno.ancho() - 680,entorno.ancho() - 495,entorno.ancho() - 310,entorno.ancho() - 130,entorno.ancho() - 770,entorno.ancho() - 580,entorno.ancho() / 2,entorno.ancho() - 220,entorno.ancho() - 35};
		// Define un array con las posiciones y de cada isla en el entorno, divididas en secciones del alto total del escenario
		int[] yyPos = {ALTO_ESCENARIO / 6,(ALTO_ESCENARIO / 6) * 2,(ALTO_ESCENARIO / 6) * 2,(ALTO_ESCENARIO / 6) * 3,(ALTO_ESCENARIO / 6) * 3,(ALTO_ESCENARIO / 6) * 3,(ALTO_ESCENARIO / 6) * 4,(ALTO_ESCENARIO / 6) * 4,(ALTO_ESCENARIO / 6) * 4,(ALTO_ESCENARIO / 6) * 4,(ALTO_ESCENARIO / 6) * 5,(ALTO_ESCENARIO / 6) * 5,(ALTO_ESCENARIO / 6) * 5,(ALTO_ESCENARIO / 6) * 5,(ALTO_ESCENARIO / 6) * 5};
		// Define un array de tipos IslaTipo para cada isla, asignando a cada posición un tipo (CasaGnomos, Superior o Inferior)
		IslaTipo[] tipo = {IslaTipo.CasaGnomos,IslaTipo.Superior,IslaTipo.Superior,IslaTipo.Superior,IslaTipo.Superior,IslaTipo.Superior,IslaTipo.Inferior,IslaTipo.Inferior,IslaTipo.Inferior,IslaTipo.Inferior,IslaTipo.Inferior,IslaTipo.Inferior,IslaTipo.Inferior,IslaTipo.Inferior,IslaTipo.Inferior};
		for (int p = 0; p < islas.length; p++) {
			// Crea una nueva isla con las coordenadas x e y de xxPos y yyPos, con un ancho y alto dados, 
		    // la imagen de vigas y asignando el tipo correspondiente
			islas[p] = new Isla(xxPos[p], yyPos[p],unAncho,unAlto,imagenDeVigas,tipo[p]);
		}
		
		pep = new Pep(entorno.ancho() -750, entorno.alto() -265, 20, 30,
				4.5, true, tiempoCongelado);
		gnomo = new Gnomos[4];
		for (int i = 0;i < gnomo.length;i++) {
			gnomo[i] = new Gnomos(entorno.ancho()/2,(entorno.alto()/6)-40,10,10);
		}
		tortuga = new Tortugas[4];
		int[] xPos = {entorno.ancho() - 690, entorno.ancho() - 590, entorno.ancho() - 215, entorno.ancho() - 105};
		int yPos = entorno.alto() - 700;

		for (int i = 0; i < tortuga.length; i++) {
		    tortuga[i] = new Tortugas(xPos[i], yPos,10,10);
		}
		casa = new Casa((entorno.ancho()/2)-5, (entorno.alto() / 6)-47, 40, 30);
		fireballs = new Fireball[5];
		bomba = new Bombas[5];
		puntuacion = new Score();
		entorno.cambiarFont("Arial", 18, Color.WHITE);
		entorno.cambiarFont("Arial", 20, Color.WHITE);
		entorno.iniciar();
		Herramientas.loop("Undertale.wav");

	}

	public void tick() {
		long tiempoActual = System.currentTimeMillis();
		entorno.dibujarImagen(fondo, entorno.ancho() / 2, entorno.alto() / 2 , 0, 1.17);
		for (Isla v : islas) {
			v.dibujar(entorno);
		}
		pep.dibujar(entorno);
		puntuacion.dibujar(entorno);
		pep.actualizarInmortalidad();
		Random random = new Random();
		for (int i = 0; i < bomba.length; i++) {
		    // Comprueba si la bomba existe
		    if (bomba[i] != null) {
		        bomba[i].mover();
		        bomba[i].dibujar(entorno);

		        // Elimina la bomba si sale de los límites
		        if (bomba[i].getX() <= -140 || bomba[i].getX() >= entorno.ancho() + 140) {
		            bomba[i] = null; // Libera el espacio en el array
		        }
		    } 
		    // Si el espacio está libre, genera una nueva bomba desde una tortuga aleatoria
		    if (bomba[i] == null) {
		        // Selecciona una tortuga aleatoria
		        int indiceTortuga = random.nextInt(tortuga.length);
		        Tortugas tortugaSeleccionada = tortuga[indiceTortuga];

		        // Si la tortuga seleccionada es válida, crea una nueva bomba
		        if (tortugaSeleccionada != null) {
		            double xInicial = tortugaSeleccionada.getX();
		            double yInicial = tortugaSeleccionada.getY() + 5;
		            boolean moviendoDerecha = !tortugaSeleccionada.izquierda();

		            // Genera la bomba en la posición de la tortuga seleccionada
		            bomba[i] = new Bombas(xInicial, yInicial, moviendoDerecha);
		        }
		    }
		}

	    for (int i = 0; i < fireballs.length; i++) {
	        // Comprueba si la fireball existe
	        if (fireballs[i] != null) {
	            fireballs[i].mover();
	            fireballs[i].dibujar(entorno);

	            // Elimina la fireball si está fuera de los límites
	            if (fireballs[i].getX() <= 8 || fireballs[i].getX() >= entorno.ancho()-8) {
	                fireballs[i] = null; // Libera el espacio en el array
	            }
	            else {
	                // Verifica colisiones con cada bomba individualmente
	                for (int j = 0; j < bomba.length; j++) {
	                    if (bomba[j] != null && fireballs[i].chocoConBomba(bomba[j])) {
	                        // Solo elimina la fireball y la bomba específicas que colisionaron
	                        fireballs[i] = null;
	                        bomba[j] = null;
	                        break; // Sal del bucle de bombas para evitar más colisiones con esta fireball
	                    }
	                }
	            }
	        } 
	        // Lógica para lanzar una nueva fireball si hay un espacio disponible
	        else if (entorno.sePresiono('C') && pep.estaSobreAlgunaIsla(islas)) { // Cambia 'C' por la tecla que desees
	            if (tiempoActual - ultimoDisparo >= 5000) {
	            double xInicial = pep.getX(); // Centro en X
	            double yInicial = pep.getY() + 10; // Centro en Y
	            boolean moviendoDerecha = pep.mirandoDerecha();
	            fireballs[i] = new Fireball(xInicial, yInicial,moviendoDerecha);
	            ultimoDisparo = tiempoActual; // Actualiza el tiempo del último disparo
	            break; // Sal del bucle después de lanzar la fireball
	        }
	    }
	    }

		for (int i = 0; i < gnomo.length; i++) {
			if (gnomo[i] != null) {  // Verifica si el gnomo existe
				gnomo[i].dibujar(entorno);  // Dibuja el gnomo

				if (!gnomo[i].estaSobreAlgunaIsla(islas)) {
					gnomo[i].caer();  // Si no está sobre una isla, cae
					if (gnomo[i].llegoFondo(entorno)) {
						matarGnomo(i);
						puntuacion.sumarGnomosPerdidos(1);
						puntuacion.sumarPuntos(-10);
						puntosPerdidos++;
						if (puntosPerdidos == 20) {
							pep.morir(entorno);
							Herramientas.play("Super-Mario-Bros.wav");
						}
					}
				} else {
					if (gnomo[i] != null && gnomo[i].estaVivo()) {  // Si está vivo, puede moverse y chequear colisiones
						gnomo[i].cayoSobreUnaIsla(islas);
						gnomo[i].mover();

						if (gnomo[i].chocoDerecha(entorno) || gnomo[i].chocoIzquierda(entorno)) {
							gnomo[i].cambiarDireccion();  // Si choca con los bordes, cambia de dirección
						}
						if (gnomo[i] != null && gnomo[i].fueChocadoPorUnEnemigo(tortuga)) {
							puntuacion.sumarPuntos(-10);
							puntuacion.sumarGnomosPerdidos(1);
							matarGnomo(i);
							puntosPerdidos++;
							if (puntosPerdidos == 20) {
								pep.morir(entorno);
								Herramientas.play("Super-Mario-Bros.wav");
							}
						}
						for (int r = 0;r < bomba.length;r++) {
						if (gnomo[i] != null && bomba != null && gnomo[i].chocoConBomba(bomba[r])) {
							puntuacion.sumarPuntos(-10);
							puntuacion.sumarGnomosPerdidos(1);
							matarGnomo(i);
							bomba[r] = null;
							puntosPerdidos++;
							if (puntosPerdidos == 20) {
								pep.morir(entorno);
								Herramientas.play("Super-Mario-Bros.wav");
							}
						}
						}

						// Verifica si choca con el héroe (Pep)
						if (gnomo [i] != null && gnomo[i].chocoAlHeroe(pep)) {
							puntuacion.sumarPuntos(30);  // Aumenta los puntos al chocar con el héroe
							puntuacion.sumarGnomosSalvados(1);
							pep.activarInmortalidad();  // Activa inmortalidad
							matarGnomo(i);  // Matar y rescatar ocasionan lo mismo por lo cual utilizamos el mismo metodo
							puntos++;
							if (puntos == 10) {
		                        haGanado = true;  // Marca como ganador
		                        puntuacion.getScore();  // Guarda el puntaje final
		                        Herramientas.play("BOOEEE.wav");
		                        }
						}
					}
				}
			} 
			else {
				if (tiempoActual - tiempoUltimoGnomoSpawneado >= INTERVALO_SPAWN_GNOMO) {
					gnomo[i] = new Gnomos(entorno.ancho()/2,(entorno.alto()/6)-40,10,10);
					tiempoUltimoGnomoSpawneado = tiempoActual;
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
		            if (tortuga[j] != null && tortuga[j].chocoConFireball(fireballs)) {
		            	tortuga[j] = null;
		                Herramientas.play("ooof.wav"); // Reproduce el sonido solo una vez
		            	
		            	for (int k = 0;k < fireballs.length;k++) {
		            		fireballs[k] = null;
		            	}
		            	puntuacion.cantEnemigosEliminados(1);
		            	puntuacion.sumarPuntos(20);
		            }
		        }
		        
		        // Este `else` debe ir si la tortuga no está viva y debe verificar la colisión
		    }
		}
		}
		if (haGanado) {
			entorno.dibujarImagen(fondoWin, entorno.ancho() / 2, entorno.alto() / 2, 0, 1.17);
            return; // Sale del método para no procesar más lógica
		}

			MovimientoEstado movimiento = MovimientoEstado.Ninguno;
			pep.actualizarFireballs(); // Mueve las bolas de fuego
			pep.dibujarFireballs(entorno); // Dibuja las bolas de fuego
			casa.dibujar(entorno, Casa);
			if (pep.estaVivo()) {
				pep.dibujar(entorno);


				if (!pep.estaSobreAlgunaIsla(islas)) {
					pep.caer();
					if (pep.llegoFondo(entorno)) {
						pep.morir(entorno);
						Herramientas.play("Super-Mario-Bros.wav");
					}
				} else {
					pep.dejarDeCaer();
					if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) || entorno.estaPresionada('a')) {
						if (!pep.chocoIzquierda(entorno)) {
						movimiento = MovimientoEstado.Izquierda;
						pep.moverIzquierda(entorno);
						pep.mirarIzquierda();
					}
						else {
							movimiento = MovimientoEstado.Derecha;
				            pep.moverDerecha(entorno);
				            pep.mirarDerecha();
						}
					}
					if (entorno.estaPresionada(entorno.TECLA_DERECHA) || entorno.estaPresionada('d')) {
						if (!pep.chocoDerecha(entorno)) {
						movimiento = MovimientoEstado.Derecha;
						pep.moverDerecha(entorno);
						pep.mirarDerecha();
					}
						else {
							movimiento = MovimientoEstado.Izquierda;
							pep.moverIzquierda(entorno);
							pep.mirarIzquierda();
						}
					}
					if (entorno.estaPresionada(entorno.TECLA_ARRIBA) || entorno.estaPresionada(entorno.TECLA_ESPACIO)) {
						pep.saltar();
					}

					if (pep.chocoAlgunEnemigo(tortuga) && !pep.esInmortal()) {
						pep.morir(entorno);
						Herramientas.play("Super-Mario-Bros.wav");
					}
					if (pep.chocoConBomba(bomba) && !pep.esInmortal()) {
						pep.morir(entorno);
						Herramientas.play("Super-Mario-Bros.wav");
					}
				}
				if (pep.estaSaltando()) {
					pep.moverSalto(movimiento);
				}} else {
					if (!juegoTerminado) {
						juegoTerminado = true;
						GameOverImage = Herramientas.cargarImagen("GameOver.gif");
					}
					else {
						// Dibuja la imagen de "Game Over"
						entorno.dibujarImagen(GameOverImage, entorno.ancho() / 2 , entorno.alto() / 2,0, 1.17);
					}
				}
		}

	private void matarGnomo(int i) {
		gnomo[i].morir();
		gnomo[i] = null;
	}

	public static void main(String[] args) {
		Juego juego = new Juego();
	}



	public enum MovimientoEstado {
		Izquierda(-1),
		Ninguno(0),
		Derecha(1);

		private int valor;

		MovimientoEstado(int valor) {
			this.valor = valor;
		}

		public int getNumVal() {
			return valor;
		}

	}


}
