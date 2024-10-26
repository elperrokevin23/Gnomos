package ElGnomo;

import java.awt.Color;

import entorno.Entorno;
import entorno.InterfaceJuego;
import entorno.Herramientas;
import java.awt.Image;
import java.io.File;

import ElGnomo.Isla.IslaTipo;

public class Juego extends InterfaceJuego {

	private static final int TIEMPO_DE_COSO_POWER_UP = 350;
	private static final int ALTO_ESCENARIO = 600;
	private static final int ANCHO_ESCENARIO = 800;
	private static final int ALTO_DEL_SECTOR_DE_PUNTUACION = 100;
	private static final int INTERVALO_SPAWN_GNOMO = 1500;
	private static final int INTERVALO_SPAWN_TORTUGA = 2000;
	private Entorno entorno;
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
	private GrupoDeFireballs fireballs;
	private Image Casa;
	private Casa casa;
	private Image imagenDeVigas;
	private Tortugas[] tortuga;
	boolean juegoTerminado = false;
	Image GameOverImage = null;

	private long tiempoUltimoGnomoSpawneado;

	public Juego() {
		int unAncho = 100;
		int unAlto = 20;
		int tiempoCongelado = 200; 

		fondo = Herramientas.cargarImagen("fondo.jpg");
		entorno = new Entorno(
				this,
				"Gnomos",
				ANCHO_ESCENARIO, ALTO_DEL_SECTOR_DE_PUNTUACION + ALTO_ESCENARIO);
		imagenDeVigas = Herramientas.cargarImagen("pasto.png");
		Casa = Herramientas.cargarImagen("casa.png");

		islas = new Isla[15]; // Array para las 15 islas

		// Fila 1: Una viga centrada
		islas[0] = new Isla(entorno.ancho() / 2, ALTO_ESCENARIO / 6, unAncho, unAlto, imagenDeVigas,  IslaTipo.CasaGnomos);

		// Fila 2: Dos vigas (izquierda y derecha)
		islas[1] = new Isla(entorno.ancho() - 495, (ALTO_ESCENARIO / 6) * 2, unAncho, unAlto, imagenDeVigas, IslaTipo.Superior);
		islas[2] = new Isla((entorno.ancho() - 552) * 2, (ALTO_ESCENARIO / 6) * 2, unAncho, unAlto, imagenDeVigas, IslaTipo.Superior);

		// Fila 3: Tres vigas (distribuidas uniformemente)
		islas[3] = new Isla(entorno.ancho() - 590, (ALTO_ESCENARIO / 6) * 3, unAncho, unAlto, imagenDeVigas, IslaTipo.Superior);
		islas[4] = new Isla(entorno.ancho() / 2, (ALTO_ESCENARIO / 6) * 3, unAncho, unAlto, imagenDeVigas, IslaTipo.Superior);
		islas[5] = new Isla(entorno.ancho() - 215, (ALTO_ESCENARIO / 6) * 3, unAncho, unAlto, imagenDeVigas, IslaTipo.Superior);

		// Fila 4: Cuatro vigas (distribuidas uniformemente)
		islas[6] = new Isla(entorno.ancho() - 680, (ALTO_ESCENARIO / 6) * 4, unAncho, unAlto, imagenDeVigas, IslaTipo.Inferior);
		islas[7] = new Isla(entorno.ancho() - 495, (ALTO_ESCENARIO / 6) * 4, unAncho, unAlto, imagenDeVigas, IslaTipo.Inferior);
		islas[8] = new Isla(entorno.ancho() - 310, (ALTO_ESCENARIO / 6) * 4, unAncho, unAlto, imagenDeVigas, IslaTipo.Inferior);
		islas[9] = new Isla(entorno.ancho() - 130, (ALTO_ESCENARIO / 6) * 4, unAncho, unAlto, imagenDeVigas, IslaTipo.Inferior);

		// Fila 5: Cinco vigas (distribuidas uniformemente)
		islas[10] = new Isla(entorno.ancho() - 770, (ALTO_ESCENARIO / 6) * 5, unAncho, unAlto, imagenDeVigas, IslaTipo.Inferior);
		islas[11] = new Isla(entorno.ancho() - 580, (ALTO_ESCENARIO / 6) * 5, unAncho, unAlto, imagenDeVigas, IslaTipo.Inferior);
		islas[12] = new Isla((entorno.ancho() / 2), (ALTO_ESCENARIO / 6) * 5, unAncho, unAlto, imagenDeVigas, IslaTipo.Inferior);
		islas[13] = new Isla(entorno.ancho() - 220, (ALTO_ESCENARIO / 6) * 5, unAncho, unAlto, imagenDeVigas, IslaTipo.Inferior);
		islas[14] = new Isla(entorno.ancho() - 35, (ALTO_ESCENARIO / 6) * 5, unAncho, unAlto, imagenDeVigas, IslaTipo.Inferior);
		pep = new Pep(entorno.ancho() -700, entorno.alto() -555, 20, 30,
				0.8, true, tiempoCongelado);
		gnomo = new Gnomos[4];
		for (int i = 0;i < gnomo.length;i++) {
			gnomo[i] = new Gnomos(entorno.ancho()/2,(entorno.alto()/6)-40,10,10,"gnomo.png");
		}
		tortuga = new Tortugas[4];
		for (int j = 0;j < tortuga.length;j++) {
			tortuga[j] = new Tortugas(entorno.ancho()/2,(entorno.alto()/2)-100,10,10,"tortuga.png");
		}
		casa = new Casa((entorno.ancho()/2)-5, (entorno.alto() / 6)-47, 40, 30);
		cantidadDeTics = 0;
		fireballs = new GrupoDeFireballs();
		puntuacion = new Score();
		entorno.cambiarFont("Arial", 18, Color.WHITE);
		entorno.cambiarFont("Arial", 20, Color.WHITE);


		entorno.iniciar();

	}

	public void tick() {

		long tiempoActual = System.currentTimeMillis();
		entorno.dibujarImagen(fondo, entorno.ancho() / 2, entorno.alto() / 2 , 0, 1.17);
		for (Isla v : islas) {
			v.dibujar(entorno);
		}

		pep.dibujar(entorno);

		for (int i = 0; i < gnomo.length; i++) {
			if (gnomo[i] != null) {  // Verifica si el gnomo existe
				gnomo[i].dibujar(entorno);  // Dibuja el gnomo

				if (!gnomo[i].estaSobreAlgunaIsla(islas)) {
					gnomo[i].caer();  // Si no está sobre una isla, cae
					if (gnomo[i].llegoFondo(entorno)) {
						matarGnomo(i);
					}
				} else {
					if (gnomo[i].estaVivo()) {  // Si está vivo, puede moverse y chequear colisiones
						gnomo[i].cayoSobreUnaIsla(islas);
						gnomo[i].mover();

						if (gnomo[i].chocoDerecha(entorno) || gnomo[i].chocoIzquierda(entorno)) {
							gnomo[i].cambiarDireccion();  // Si choca con los bordes, cambia de dirección
						}

						// Verifica si choca con el héroe (Pep)
						if (gnomo[i].chocoAlHeroe(pep)) {
							puntuacion.sumarPuntos(30);  // Aumenta los puntos al chocar con el héroe
							matarGnomo(i);  // Matar y rescatar ocasionan lo mismo por lo cual utilizamos el mismo metodo
						}
					}
				}
			} else {
				if (tiempoActual - tiempoUltimoGnomoSpawneado >= INTERVALO_SPAWN_GNOMO) {
					gnomo[i] = new Gnomos(entorno.ancho()/2,(entorno.alto()/6)-40,10,10,"gnomo.png");
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
					if (tortuga[j].estaVivo()) {
						tortuga[j].mover();
					}
					if (tortuga[j].chocoDerecha(entorno) || tortuga[j].chocoIzquierda(entorno)) {
						tortuga[j].cambiarDireccion();
					}
				}
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
					}
				} else {
					pep.dejarDeCaer();
					if (entorno.sePresiono('C')) {
						if (tiempoActual - ultimoDisparo >= INTERVALO_DISPARO) {
							pep.lanzarBola();
							ultimoDisparo = tiempoActual;
						}
					}
					if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
						movimiento = MovimientoEstado.Izquierda;
						pep.moverIzquierda(entorno);
						pep.mirarIzquierda();
					}
					if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
						movimiento = MovimientoEstado.Derecha;
						pep.moverDerecha(entorno);
						pep.mirarDerecha();
					}
					if (entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
						pep.saltar();
					}
					if (pep.chocoAlgunEnemigo(tortuga)) {
						pep.morir(entorno);
					}
				}            
				if (pep.estaSaltando()) {
					if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
						movimiento = MovimientoEstado.Izquierda;
					}
					if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
						movimiento = MovimientoEstado.Derecha;
					}
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
		}}

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
