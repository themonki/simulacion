

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
	public int aleatorio(long number){
		int r;
		r = (int) (Math.floor(random.nextDouble()*(number-1)+1));// mirar si esta bien 
		return r;
		
	}
	

	
	public static void main(String [] args )
	{
		/*
		Generador g;
		g = new Generador(15863);
		for ( int i = 0 ; i< 10; i++)
		{
		System.out.println(g.tiempoFalloMaquina());
		System.out.println(g.tiempoReparacion());
		}
		*/
		
	}

}

