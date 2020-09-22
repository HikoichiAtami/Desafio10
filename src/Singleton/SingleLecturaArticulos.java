package Singleton;

public class SingleLecturaArticulos {


	private static SingleLecturaArticulos read;
	
	public static SingleLecturaArticulos getSingle() {
		if(read == null) {
			synchronized (SingleLecturaArticulos.class){
				if(read == null) {
					read = new SingleLecturaArticulos();
				}
			}
		}
		return read;
	}
	
}
