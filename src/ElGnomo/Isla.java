package ElGnomo;

import entorno.Entorno; // para interactuar con el entorno gráfico
import java.awt.Image; // para manejar imágenes

public class Isla {
	private double x; // Coordenada x de la posición de la isla
	private double y; // Coordenada y de la posición de la isla
	private int ancho; // Ancho de la isla
	private int alto; // Alto de la isla
	private Image imagen; // Imagen que representa visualmente la isla
	private IslaTipo tipo; // Tipo de isla, definido en el enum IslaTipo

	// Constructor de la clase Isla
	public Isla(double x, double y, int ancho, int alto, Image imagen, IslaTipo tipo ) {
		this.x = x; // Inicializa la posición x de la isla
		this.y = y; // Inicializa la posición y de la isla
		this.ancho = ancho;  // Inicializa el ancho de la isla
		this.alto = alto; // Inicializa el alto de la isla
		this.imagen = imagen; // Asigna la imagen para representar la isla
		this.tipo = tipo; // Asigna el tipo de isla
	}

	public void dibujar(Entorno e) {
		// Dibuja la imagen de la isla en la posición especificada
		e.dibujarImagen(imagen, x, y, 0, 1.0);
	}
	
	// Método para obtener la coordenada x de la isla
	public double getX() {
		return this.x;
	}

	// Método para obtener la coordenada y de la isla
	public double getY() {
		return this.y;
	}

	// Método para obtener el ancho de la isla
	public int getAncho() {
		return this.ancho;
	}

	// Método para obtener el alto de la isla
	public double getAlto() {
		return this.alto;
	}

	// Método para verificar si la isla es del tipo Inferior
	public boolean esIslaInferior() {
		return tipo == IslaTipo.Inferior;
	}

	// Método para verificar si la isla es del tipo CasaGnomos
	public boolean esIslaDeGnomos() {
		return tipo == IslaTipo.CasaGnomos;
	}

	// Enumeración para definir el IslaTipo
	public enum IslaTipo {
		// Tipo de isla donde están las casas de los gnomos
		CasaGnomos,
		// Tipo de isla en la parte superior del entorno
		Superior,
		// Tipo de isla en la parte inferior del entorno
		Inferior;
	}
}
