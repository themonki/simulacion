package Simulacion;


import java.util.Random;

public class Generador {
	
	private Random random;
	
	//Se inicia con una semilla para tener los mismos valores en los diferentes
	//escenarios y poder realizar comparaciones
	public Generador(int seed){
		random = new Random();
		random.setSeed(seed);
	}
	
	//Tiempo en que vuelve a fallar una maquina 
	public int tiempoFalloMaquina(){
		int r;
		r = (int) (Math.floor(random.nextDouble()*(13)) + 10);
		return r;
		
	}
	
	public int tiempoReparacion(){
		int r;
		r = (int) (Math.floor(random.nextDouble()*(7)) + 5);
		return r;
		
	}
	
	public static void main(String [] args )
	{
		
		/*System.out.println("hola");
		Generador g;
		g = new Generador(15863);
		for ( int i = 0 ; i< 5; i++)
		{
		System.out.println(g.tiempoFalloMaquina());
		System.out.println(g.tiempoReparacion());
		}*/
		
		
	}

}

