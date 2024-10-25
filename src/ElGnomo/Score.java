package ElGnomo;

import java.awt.Color;
import entorno.Entorno;

public class Score {
	private int puntos;

	public Score() {
		puntos = 0;
	}

	public void agarrandoGnomo() {
		puntos += 10;
	}
	public void agarraElGnomo() {
		puntos -= 10;
	}
	public void ganador() {
		puntos = 100;
	}

	public void dibujar(Entorno e) {
		e.cambiarFont("Arial", 32, Color.WHITE);
		e.escribirTexto("Puntos: " + puntos, 600,
				100);
	}

	public int getScore() {
		return puntos;
	}
}
