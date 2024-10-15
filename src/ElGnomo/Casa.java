package ElGnomo;
import entorno.Entorno;
import java.awt.Image;

public class Casa {
	private double x;
	private double y;
	private int ancho;
	private int alto;
	public Casa(double x, double y, int ancho, int alto) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
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
	public void dibujar(Entorno entorno, Image imagenCasa) {
        entorno.dibujarImagen(imagenCasa, this.x, this.y, 0);
    }
}
