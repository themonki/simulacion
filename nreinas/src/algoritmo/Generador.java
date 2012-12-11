/**
 * 
 */
package algoritmo;

import java.util.Random;

/**
 * @author "Edgar Andrés Moncada"
 * 
 */
public class Generador {

	private Random random;
	
	/**
	 * Se inicia con una semilla para tener los mismos valores en los diferentes
	 * escenarios y poder realizar comparaciones.	 * 
	 * @param seed la semilla
	 */
	public Generador(int seed) {
		this.random = new Random();
		//this.random.setSeed(seed);
	}
	
	/**
	 * Genera numeros aleatorios entre 1 y nPosibles
	 * @param nPosibles maxima cantidad de números a generar
	 * @return número aleatorio generado
	 */
	public int a(int nPosibles){
		int r;
		r = (int) (Math.floor(random.nextDouble() * (nPosibles))) + 1;
		return r;		
	}
	
	public static void main(String []args){
		Generador g = new Generador(1223);
		for(int i =0 ; i < 100; i++)System.out.println(g.a(8));
		
	}

}
