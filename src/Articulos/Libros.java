package Articulos;

public class Libros extends Articulos {

	private String editorial;
	private double paginas;
	
	

	public Libros(String nombre, String editorial,double paginas, String codigo, double plazo, boolean reservado) {
		super(nombre, codigo, plazo, reservado);
		this.editorial = editorial;
		this.paginas = paginas;
		// TODO Auto-generated constructor stub
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public double getPaginas() {
		return paginas;
	}

	public void setPaginas(double paginas) {
		this.paginas = paginas;
	}

	@Override
	public String toString() {
		return "Libros [Nombre : " + getNombre() + ", editorial : " + editorial + ", paginas : " + paginas  + ", Codigo : " + getCodigo() + ", "
				+ "Plazo : " + getPlazo() + ", Estado Reserva : " + isReservado() + "]";
	}
}
