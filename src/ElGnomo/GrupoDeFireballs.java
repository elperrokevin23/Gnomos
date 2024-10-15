package ElGnomo;
import entorno.Entorno;

public class GrupoDeFireballs {
	private static final int CANTIDAD_DE_FIREBALLS = 10;
	private Fireball[] fireballs;
	private int numeroDeFireball;

	public GrupoDeFireballs() {
		fireballs = new Fireball[CANTIDAD_DE_FIREBALLS];
		numeroDeFireball = 0;
	}

	public void habilitarBala(Fireball f) {
		fireballs[numeroDeFireball] = f;
		numeroDeFireball++;
		if (numeroDeFireball == CANTIDAD_DE_FIREBALLS) {
			numeroDeFireball = 0;
		}
	}

	public void desaparecerFireball(int indice) {
		fireballs[indice] = null;
	}

	public Fireball[] getArregloDeFireballs() {
		return fireballs;
	}

	public void dibujar(Entorno e) {
		for (int i = 0; i < fireballs.length; i++) {
			if (fireballs[i] != null) {
				fireballs[i].mover();
				fireballs[i].dibujar(e);
			}

		}
	}
}
