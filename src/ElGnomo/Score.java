package ElGnomo;

import java.awt.Color;
import entorno.Entorno;

public class Score {
	private int puntos;
	private int gnomosPerdidos;
	private int gnomosSalvados;
	private int enemigosEliminados;
	private long tiempoInicio;

	public Score() {
		puntos = 0;
		gnomosPerdidos = 0;
		gnomosSalvados = 0;
		enemigosEliminados = 0;
		tiempoInicio = System.currentTimeMillis();
	}

	public void sumarPuntos(int pun) {
		puntos += pun;
	}
	public void sumarGnomosPerdidos (int pun) {
		gnomosPerdidos += pun;
	}
	public void sumarGnomosSalvados (int pun) {
		gnomosSalvados += pun;
	}
	public void cantEnemigosEliminados (int pun) {
		enemigosEliminados += pun;
	}
	public void dibujar(Entorno e) {
		e.cambiarFont("Arial", 32, Color.WHITE);
		e.escribirTexto("Puntos: " + puntos, 560,
				200);
		e.cambiarFont("Arial", 28, Color.GREEN);
		e.escribirTexto("Gnomos salvados: " + gnomosSalvados, 500, 100);
		e.cambiarFont("Arial", 28, Color.RED);
		e.escribirTexto("Gnomos perdidos: " + gnomosPerdidos, 500, 50);
		e.cambiarFont("Arial", 28, Color.GREEN);
		e.escribirTexto("Enemigos eliminados: " + enemigosEliminados,500,150);
		long tiempoActual = System.currentTimeMillis();
		int tiempoTranscurridoSegundos = (int) ((tiempoActual - tiempoInicio) / 1000);

		// Dibujar el tiempo en pantalla
		e.cambiarFont("Arial", 28, Color.BLACK);
		e.escribirTexto("Tiempo: " + tiempoTranscurridoSegundos + " s", 10, 30);
	}

	public int getScore() {
		return puntos;
	}
}
