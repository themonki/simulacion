package Simulacion;

import java.util.Comparator;
import java.util.PriorityQueue;


/**
 * @author monki
 *
 */
public class Simulacion {
	
	//private LinkedList<Evento<Integer, String>> listaEventos;
	private PriorityQueue<Evento<Integer, String>> listaEventos;
	private int reloj;
	private Generador tiempos;
	private int colaRepacion;
	private int colaAdicionales;
	private int colaFuncionamiento;
	private int reparadoresDisponibles;
	private int MAX_MAQUINAS;
	private int MAX_REPARADOR;
	private int MAX_MAQUINAS_ADICIONALES;
	private int MAX_TIEMPO;

	
	/**
	 * @param seed Indica la semilla para las distribuciones
	 * @param colaFuncionamiento indica la cantidad de maquinas que deben de estar funcionando
	 * @param colaAdicionales indica la cantidad de maquinas adicionales disponibles
	 * @param cantReparadores indica la cantidad de reparadores que se tienen
	 * @param tiempoMax indica la cantidad de tiempo maximo invertido en la simulacion
	 */
	public Simulacion(int seed, int colaFuncionamiento, int colaAdicionales, int cantReparadores, int tiempoMax){
		
		//listaEventos = new LinkedList<Evento<Integer,String>>();
		listaEventos = new PriorityQueue<Evento<Integer,String>>(11, new Comparator<Evento<Integer, String>>() {

			@Override
			public int compare(Evento<Integer, String> o1,
					Evento<Integer, String> o2) {
				int compare = o1.getTiempo().compareTo(o2.getTiempo());
				return compare;
			}
		});
		reloj=0;
		tiempos = new Generador(seed);
		this.MAX_TIEMPO = tiempoMax;
		
		colaRepacion=0;		
		this.colaAdicionales= this.MAX_MAQUINAS_ADICIONALES = colaAdicionales;
		this.colaFuncionamiento= this.MAX_MAQUINAS=colaFuncionamiento;		
		this.reparadoresDisponibles = this.MAX_REPARADOR = cantReparadores;
	}
	
	/**
	 * Función que crea un evento de fallo de una maquina en el sistema
	 * 
	 */
	public void eventoFallo(){
		
		//Nuevo evento de fallo.
		listaEventos.add(new Evento<Integer, String>(this.reloj+ tiempos.tiempoFalloMaquina(), "F"));
		if(reparadoresDisponibles>0){
			reparadoresDisponibles--;
			//Evento de reparacion
			listaEventos.add(new Evento<Integer, String>(this.reloj+tiempos.tiempoReparacion(), "R"));
		}
		else{
			colaRepacion++; //Se suma a la cola de repacion
		}
		
		if(colaAdicionales>0){
			colaAdicionales--; //Si hay maquinas adicionales se reasume.
		}else{
			colaFuncionamiento--; //Si no hay maquinas adicionales se resta a la cola de funcionamiento
		}
		
		
	}
	
	/**
	 * Función que genera el evento de reparación de una maquina en el sistema
	 */
	public void eventoReparacion(){
		listaEventos.add(new Evento<Integer, String> (this.reloj+tiempos.tiempoReparacion(),"R"));
		if(colaFuncionamiento< MAX_MAQUINAS){//tenemos espacio para ponerla a funcionar
			colaFuncionamiento++;
		}else{//si esta completo, se coloca como adicional
			colaAdicionales++;
		}
		
		
		if(colaRepacion>0){//se tienen maquinas a reparar
			colaRepacion--;
		}else{// cola del reparador libre
			reparadoresDisponibles++;
		}
	}
	
	/**
	 * Funcion que inicia la simulacion del sistema con las variables de entrada dadas
	 */
	public void starSimulacion()
	{
		Evento<Integer, String> uno= new Evento<Integer, String>(0, "F");
		listaEventos.add(uno);
		imprimir(uno);
		
		while (this.reloj <= this.MAX_TIEMPO){
			Evento<Integer, String> evento = listaEventos.poll();
			reloj= (Integer) evento.getTiempo();
			String tipoEvento = (String) evento.getTipoEvento();
			
			if(tipoEvento.equals("F")){
				this.eventoFallo();
				imprimir(evento);
			}else{
				this.eventoReparacion();
				imprimir(evento);
			}
		}
		
	}
	
	/**
	 * @param e
	 */
	private void imprimir(Evento<Integer, String> e){
		System.out.println(" " + this.reloj + ", E=" + e.getTipoEvento()+ ", CA=" + this.colaAdicionales + 
				", CR=" + this.colaRepacion + ", CF" + this.colaFuncionamiento+ ", LEF=" + this.listaEventos.toString());
	}
	

	/**
	 * @param args
	 */
	public static void main(String [] args )
		{
		
		//Pruebas:
			Simulacion s = new Simulacion(456778, 50,1,1,80);
			s.starSimulacion();
		
		}

}
