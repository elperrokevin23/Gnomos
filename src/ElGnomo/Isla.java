package ElGnomo;

import java.awt.Color;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Image;

public class Isla {
	private double x;
	private double y;
	private int ancho;
	private int alto;
	private Image imagen;
	private IslaTipo tipo;

	public Isla(double x, double y, int ancho, int alto, Image imagen, IslaTipo tipo ) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.imagen = imagen;
		this.tipo = tipo;
	}

	public void dibujar(Entorno e) {
		e.dibujarRectangulo(x, y, ancho, alto, 0, Color.WHITE);
		e.dibujarImagen(imagen, x, y, 0, 1.0);
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
	
	public boolean esIslaInferior() {
		return tipo == IslaTipo.Inferior;
	}
	
	public boolean esIslaDeGnomos() {
		return tipo == IslaTipo.CasaGnomos;
	}
	
	public enum IslaTipo {
		CasaGnomos,
		Superior,
		Inferior;
	}
}