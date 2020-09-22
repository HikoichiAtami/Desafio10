package Articulos;

public class Peliculas extends Articulos {

	private double duracion;
	private String calidad;
	
	public Peliculas(String nombre, double duracion,String calidad,double plazo, String codigo,  boolean reservado) {
		super(nombre, codigo, plazo, reservado);
		this.calidad = calidad;
		this.duracion = duracion;
		// TODO Auto-generated constructor stub
	}
	
	
	public double getDuracion() {
		return duracion;
	}

	public void setDuracion(double duracion) {
		this.duracion = duracion;
	}

	public String getCalidad() {
		return calidad;
	}

	public void setCalidad(String calidad) {
		this.calidad = calidad;
	}
	
	@Override
	public String toString() {
		return "Peliculas Nombre : " + getNombre() + ", duracion : " + duracion + ", calidad : " + calidad  + ", Codigo : " + getCodigo() + ", "
				+ "Plazo : " + getPlazo() + ", Estado Reserva : " + isReservado();
		
	}


}
