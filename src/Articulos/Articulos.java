package Articulos;

public class Articulos {

	protected String nombre;
	protected String codigo;
	protected double plazo;
	protected boolean reservado;
	
	public Articulos(String nombre, String codigo, double plazo, boolean reservado) {
		super();
		this.nombre = nombre;
		this.codigo = codigo;
		this.plazo = plazo;
		this.reservado = reservado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public double getPlazo() {
		return plazo;
	}

	public void setPlazo(double plazo) {
		this.plazo = plazo;
	}

	public boolean isReservado() {
		return reservado;
	}

	public void setReservado(boolean reservado) {
		this.reservado = reservado;
	}
	
}
