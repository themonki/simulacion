package Simulacion;


import java.util.Random;

public class Generador {
	
	private Random random;
	
	public Generador(int seed){
		Random random;
		random = new Random();
		random.setSeed(seed);
	}
	
	public double tiempoFalloMaquina(){
		double r;
		r = //(int) (Math.floor(
				random.nextDouble();//));//*(12)) + 10);
		return r;
		
	}
	
	public int tiempoReparacion(){
		int r;
		r = (int) (Math.floor(random.nextDouble()*(6)) + 5);
		return r;
		
	}
	
	public static void main(String [] args )
	{
		
		System.out.println("hola");
		Generador g;
		g = new Generador(15963);
		System.out.println(g.tiempoFalloMaquina());
		//System.out.println(g.tiempoReparacion());
		
		
	}

}

