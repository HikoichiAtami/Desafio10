package MainClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Articulos.Articulos;
import Articulos.Libros;
import Articulos.Peliculas;
import Singleton.SingleLecturaArticulos;
import Usuarios.Users;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		
		ArrayList<Articulos> articulo = new ArrayList<>(); //Array que almacena los articulos disponibles
		ArrayList<Users> userList = new ArrayList<Users>(); //Array que almacena los datos de los usuarios
		
		lecturaUsuarios(userList); // llamado al metodo para leer datos de usuarios
		lecturaPeliculas(articulo); //llamado al metodo para leer datos de peliculas
		lecturaLibros(articulo); //llamado al metodo para leer datos de libros
		menu(userList, articulo); // llamado al metodo menu
		
	}
		
	public static void menu(ArrayList<Users>userList, ArrayList<Articulos>articulo) throws IOException { //metodo menu
		
		String user, pass,opcion, reserva; //se declaran variables tipo String auxiliares para almacenar datos ingresados por cliente
		Scanner user1 = new Scanner(System.in); // se declara scanner auxiliar
		
		System.out.println("Ingrese su nombre de usuario"); 
		user = user1.nextLine(); //se almacena nombre ingresado por cliente mediante scanner a variable user
		System.out.println("Ingrese su clave"); 
		pass = user1.nextLine(); //se almacena contraseña ingresada por cliente mediante scanner a variable pass
		for(int i = 0; i<userList.size();i++) { //ciclo for para recorrer el array de usuarios
			if(userList.get(i).getName().equals(user) && userList.get(i).getPass().equals(pass)) { //se evalua si los datos son correctos
				System.out.println("Bienvenido a Dragon's Den " + user); //si es true se muestra menu personalizado con nombre de cliente
				System.out.println("- - - - - - - - - - - - - - -");
				System.out.println("¿Que desea hacer?");
				System.out.println("1- Reservar un articulo"); 
				System.out.println("2- Buscar un articulo");
				System.out.println("0- Salir");
				opcion = user1.nextLine(); // se almacena opcion ingresada por cliente en variable
				if(opcion.compareTo("1") == 0) { //se evalua si la opcion es 1 se ingresa al menu para reservar articulo
					System.out.println("Ingrese el codigo del articulo");
					reserva = user1.nextLine(); //se almacena el codigo del articulo ingresado
					for(int j = 0; j<articulo.size(); j++) { //ciclo for para recorrer array de articulos
						if(articulo.get(j).getCodigo().equals(reserva) && articulo.get(j).isReservado() == false) { //se evalua si existe el codigo
							System.out.println("Reserva de " + articulo.get(j).getNombre());//y si el articulo esta disponible para reserva	se procede con la misma
							if(articulo.get(j).getCodigo().contains("l")) { //se evalua si el codigo pertenece a libros ej( l555)
								escrituraLibros(); //llamado al metodo para crear la reserva de libros
							} else if(articulo.get(j).getCodigo().contains("p")) { //se evalua si el codigo pertenece a peliculas ej (p5555)
								escrituraPeliculas(reserva, articulo); //llamado al metodo para crear la reserva de peliculas
							}
						} else if(articulo.get(j).getCodigo().equals(reserva) && articulo.get(j).isReservado() == true){ //se evalua si existe el codigo y si 
							System.out.println("No es posible reservar el articulo solicitado debido a que no esta disponible"); // el articulo esta disponible para reserva, si retorna true se envia mensaje que no
							System.out.println("Volviendo al menu"); //es posible reservar
							menu(userList, articulo); //llamado al metodo menu
						}
					}
				} else if(opcion.compareToIgnoreCase("2") == 0) { //se evalua si ingresa opcion para buscar articulos
					System.out.println("Ingrese un nombre para buscar en la lista de articulos");
					opcion = user1.nextLine(); //se almacena el dato ingresado por cliente en variable
					System.out.println("Buscando articulos que contengan " + opcion + " en su nombre"); //mensaje de transicion 
					for(int j =0; j<articulo.size(); j++) { //ciclo for para recorrer array de articulos
						if(articulo.get(j).getNombre().contains(opcion)) { //se evaluan las posiciones que contengan el dato ingresado
							System.out.println(articulo.get(j)); //se muestran coincidencias		
						}
					}
					System.out.println("Busqueda finalizada"); // se muestra mensaje de termino
					System.out.println("Volviendo al menu principal");
					System.out.println("- - - - - - - - - - - - - - - -");
					menu(userList, articulo); //llamado al metodo menu
				} else if(opcion.compareToIgnoreCase("0") == 0) { //se evalua si presiona opcion para salir
					System.out.println("Gracias por comprar en Dragon's Den"); //mensaje despedida
					System.out.println("Que tenga un buen dia");
					System.exit(0); //termino programa
				} else { //en caso que ingrese una opcion diferente a las anteriores
					System.out.println("Ha ingresado una opcion no valida"); //se informa sobre el error
					System.out.println("Volviendo al menu"); 
					menu(userList,articulo); //llamado al metodo main
				}
			 
				
			}
		}
		System.out.println("No se encuentra registrado"); //en caso que durante el ciclo no se encuentre el user y pass
		System.out.println("Para poder acceder a la reserva de articulos le sugerimos acercarse a una sucursal de" //se muestra mensaje de despedida
				+ " Dragon's Den para registrarse"); //mensajes informativos
		System.out.println("Que tenga un buen dia");
		System.exit(0); //termino programa
	}

	public static void lecturaUsuarios(ArrayList<Users>userList) throws IOException  { //metodo para la lectura de usuarios desde excel
		
		FileInputStream file = new FileInputStream(new File("C:\\Users\\Hikoichi\\eclipse-workspace\\Desafio10\\usuarios.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);
        for(int i=sheet.getFirstRowNum();i<=sheet.getLastRowNum();i++){ //ciclo for para recorrer desde la primera fila a la ultima
   		 	Row rowUserss=sheet.getRow(i); //se iguala valor de la fila actual al objeto de tipo row
   		 	Cell cellUser1 = rowUserss.getCell(0); //se asigna el valor de la celda 0 al objeto tipo Cell
   		 	Cell cellPass1 = rowUserss.getCell(1);
   		 	Users userLibrary = new Users(cellUser1.getStringCellValue(), cellPass1.getStringCellValue());
   		 	userList.add(userLibrary);
        }	
	}
	
	public static void lecturaPeliculas(ArrayList<Articulos>articulo) throws IOException{ //metodo para la lectura de peliculas desde excel
		 
		//creacion de archivo, libro y hoja
		 FileInputStream file = new FileInputStream(new File("C:\\Users\\Hikoichi\\eclipse-workspace\\Desafio10\\peliculas.xlsx"));
         XSSFWorkbook workbook = new XSSFWorkbook(file);
         XSSFSheet sheet = workbook.getSheetAt(0);
         if(SingleLecturaArticulos.getSingle() != null) {
        	 for(int i=sheet.getFirstRowNum();i<=sheet.getLastRowNum();i++){ //ciclo for para recorrer desde la primera fila a la ultima
        		 Row rowPeliculas=sheet.getRow(i); //se iguala valor de la fila actual al objeto de tipo row
        		 Cell cellPeliculas = rowPeliculas.getCell(0); //se asigna el valor de la celda 0 al objeto tipo Cell
        		 Cell cellPeliculas1 = rowPeliculas.getCell(1);//se asigna el valor de la celda 1 al objeto tipo Cell
        		 Cell cellPeliculas2 = rowPeliculas.getCell(2);//se asigna el valor de la celda 2 al objeto tipo Cell
        		 Cell cellPeliculas3 = rowPeliculas.getCell(3);//se asigna el valor de la celda 3 al objeto tipo Cell
        		 Cell cellPeliculas4 = rowPeliculas.getCell(4);//se asigna el valor de la celda 4 al objeto tipo Cell
        		 Cell cellPeliculas5 = rowPeliculas.getCell(5);//se asigna el valor de la celda 5 al objeto tipo Cell
        		 Peliculas pelicula= new Peliculas(cellPeliculas.getStringCellValue(), cellPeliculas1.getNumericCellValue(), //se crea nuevo objeto pelicula de tipo Peliculas y se asignan valores
        				 cellPeliculas2.getStringCellValue(),cellPeliculas3.getNumericCellValue() , cellPeliculas4.getStringCellValue(),
        				 cellPeliculas5.getBooleanCellValue() );

        		 articulo.add(pelicula); //se añade el objeto al array de articulo    
        		 file.close();//se cierra archivo para no gastar recursos adicionales
         }
         	}
   }
         

	public static void lecturaLibros(ArrayList<Articulos> articulo) throws IOException { //metodo para leer libros desde excel
		
		//creacion de archivo, libro y hoja
		  FileInputStream file = new FileInputStream(new File("C:\\Users\\Hikoichi\\eclipse-workspace\\Desafio10\\libros.xlsx"));
          XSSFWorkbook workbook = new XSSFWorkbook(file);
          XSSFSheet sheet = workbook.getSheetAt(0);
          for(int i=sheet.getFirstRowNum();i<=sheet.getLastRowNum();i++){ //ciclo for para recorrer desde la primera fila a la ultima                    	  
             Row rowLibros=sheet.getRow(i); //se iguala valor de la fila actual al objeto de tipo row
             Cell cellLibros = rowLibros.getCell(0);//se asigna el valor de la celda 0 al objeto tipo Cell
         	 Cell cellLibros1 = rowLibros.getCell(1);//se asigna el valor de la celda 1 al objeto tipo Cell
         	 Cell cellLibros2 = rowLibros.getCell(2);//se asigna el valor de la celda 2 al objeto tipo Cell
         	 Cell cellLibros3 = rowLibros.getCell(3);//se asigna el valor de la celda 3 al objeto tipo Cell
         	 Cell cellLibros4 = rowLibros.getCell(4);//se asigna el valor de la celda 4 al objeto tipo Cell
         	 Cell cellLibros5 = rowLibros.getCell(5);//se asigna el valor de la celda 5 al objeto tipo Cell
         	Libros libros = new Libros(cellLibros.getStringCellValue(), cellLibros1.getStringCellValue(),
         			cellLibros2.getNumericCellValue(),cellLibros3.getStringCellValue() , cellLibros4.getNumericCellValue(),
         			cellLibros5.getBooleanCellValue() ); //se crea nuevo objeto libros de tipo Libros y se asignan valores
              articulo.add(libros);////se añade el objeto al array de articulo     
              file.close();//se cierra archivo para no gastar recursos adicionales
          }
	}
	
	public static void escrituraLibros() throws IOException {
		System.out.println("libros");
		//		FileInputStream file = new FileInputStream(new File("C:\\Users\\EDUTECNO\\Downloads\\Desafio10\\libros.xlsx"));
//        XSSFWorkbook workbook = new XSSFWorkbook(file);
//        XSSFSheet sheet = workbook.getSheetAt(0);
//        for(int i=sheet.getFirstRowNum();i<=sheet.getLastRowNum();i++){      
//       	 Row rowLibros=sheet.getRow(i); 
//       	Cell cellLibros5 = rowLibros.getCell(5);
//       	cellLibros5.setCellValue("verdadero");
//       	}
	}
	
	public static void escrituraPeliculas(String reserva, ArrayList<Articulos> articulo) throws IOException {
		
		int a = 0;
		for(int i = 0; i<articulo.size(); i++) {
			if(articulo.get(i).getCodigo().equals(reserva)) {
				a = articulo.get(i).getCodigo().indexOf(reserva);
				System.out.println(a);
			}
		}
//		String ruta = "C:\\Users\\Hikoichi\\eclipse-workspace\\Desafio10\\peliculas.xlsx";
//		FileInputStream file = new FileInputStream(new File(ruta));
//		XSSFWorkbook libro = new XSSFWorkbook(file);
//		XSSFSheet sheet = libro.getSheetAt(0);
//		 for(int i=sheet.getFirstRowNum();i<=sheet.getLastRowNum();i++){   
//			 Row rowPeliculas=sheet.getRow(i);
//			 Cell cellLibros = rowPeliculas.getCell(5);
//			// Cell celda.setCellType(CellType.BOOLEAN);
//				cellLibros.setCellValue(true);
//				FileOutputStream cierre = new FileOutputStream(ruta);
//				libro.write(cierre);
//				cierre.flush();
//				cierre.close();
//				System.out.println("peliculas");
//		 }
		 
		
	}
}
