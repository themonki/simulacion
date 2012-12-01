import Simulacion.Simulacion;


public class Pruebas {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int numIteraciones =1000;
		int colaFuncionamiento=50;
		int colaAdicionales=1;
		int cantReparadores=1;
		int tiempoMax=500;
	
		double sumatoria=0;
		
		for(int i = 1; i <= numIteraciones; i++){			
			Simulacion sim = new Simulacion(i+50, colaFuncionamiento, colaAdicionales, cantReparadores, tiempoMax);
			sim.starSimulacion();
			sumatoria+=sim.getDesempenioTotal();			
		}
		Double promedio = sumatoria/(double)(numIteraciones);
		System.out.println("Promedio de el rendimiento para la instancia:");
		System.out.println("Número de maquinas en funcionamiento:\t\t" + colaFuncionamiento);
		System.out.println("Número de maquinas Adicionales:\t\t\t"+ colaAdicionales);
		System.out.println("Número de Reparadores:\t\t\t\t"+cantReparadores);
		System.out.println("El tiempo de parada de la simulación es en:\t"+ tiempoMax);
		System.out.println();
		System.out.println("El promedio de porcentaje de desempeño calculado del funcionamiento de la maquina es:");
		System.out.println((promedio*100) +"%");
		

	}

}
