package Simulacion;

import java.util.Comparator;
import java.util.PriorityQueue;

import org.omg.CORBA.PRIVATE_MEMBER;


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
	private String INDICADOR_FALLA="F";
	private String INDICADOR_REPARACION="R";

	
	/**
	 * @param seed Indica la semilla para las distribuciones
	 * @param colaFuncionamiento indica la cantidad de maquinas que deben de estar funcionando
	 * @param colaAdicionales indica la cantidad de maquinas adicionales disponibles
	 * @param cantReparadores indica la cantidad de reparadores que se tienen
	 * @param tiempoMax indica la cantidad de tiempo maximo invertido en la simulacion
	 */
	public Simulacion(int seed, int colaFuncionamiento, int colaAdicionales, int cantReparadores, int tiempoMax){
		
		//listaEventos = new LinkedList<Evento<Integer,String>>();
		this.listaEventos = new PriorityQueue<Evento<Integer,String>>(11, new Comparator<Evento<Integer, String>>() {

			@Override
			public int compare(Evento<Integer, String> o1,
					Evento<Integer, String> o2) {
				int compare = o1.getTiempo().compareTo(o2.getTiempo());
				return compare;
			}
		});
		this.reloj=0;
		this.tiempos = new Generador(seed);
		this.MAX_TIEMPO = tiempoMax;		
		this.colaRepacion=0;		
		this.colaAdicionales= this.MAX_MAQUINAS_ADICIONALES = colaAdicionales;
		this.colaFuncionamiento= this.MAX_MAQUINAS=colaFuncionamiento;		
		this.reparadoresDisponibles = this.MAX_REPARADOR = cantReparadores;
	}
	
	/**
	 * Función que crea un evento de fallo de una maquina en el sistema
	 * 
	 */
	public void eventoFallo(){
				
		if(this.colaFuncionamiento<=0){
			//no ahi maquinas a fallar
			return;
		}
		
		//Nuevo evento de fallo.
		listaEventos.add(new Evento<Integer, String>(this.reloj+ this.tiempos.tiempoFalloMaquina(), this.INDICADOR_FALLA));
		if(this.reparadoresDisponibles>0){
			this.reparadoresDisponibles--;
			//Evento de reparacion
			this.listaEventos.add(new Evento<Integer, String>(this.reloj+this.tiempos.tiempoReparacion(), this.INDICADOR_REPARACION));
		}
		else{
			this.colaRepacion++; //Se suma a la cola de repacion
		}
		
		if(this.colaAdicionales>0){
			this.colaAdicionales--; //Si hay maquinas adicionales se reasume.
		}else{
			this.colaFuncionamiento--; //Si no hay maquinas adicionales se resta a la cola de funcionamiento
		}
		
		
	}
	
	/**
	 * Función que genera el evento de reparación de una maquina en el sistema
	 */
	public void eventoReparacion(){
			
		if(this.colaFuncionamiento< this.MAX_MAQUINAS){//tenemos espacio para ponerla a funcionar
			this.colaFuncionamiento++;
		}else{//si esta completo, se coloca como adicional
			this.colaAdicionales++;
		}
				
		if(this.colaRepacion>0){//se tienen maquinas a reparar
			this.listaEventos.add(new Evento<Integer, String> (this.reloj+this.tiempos.tiempoReparacion(),this.INDICADOR_REPARACION));
			this.colaRepacion--;
		}else{// cola del reparador libre
			this.reparadoresDisponibles++;
		}
	}
	
	/**
	 * Funcion que inicia la simulacion del sistema con las variables de entrada dadas
	 */
	public void starSimulacion()
	{
		for(int i=0;i<50;i++){//ANALIZAR ESTO
			Evento<Integer, String> uno= new Evento<Integer, String>(i*8, this.INDICADOR_FALLA);
			this.listaEventos.add(uno);
			imprimir(uno);
		}
		do{
			Evento<Integer, String> evento = this.listaEventos.poll();
			
			//if((Integer) evento.getTiempo()>=this.MAX_TIEMPO){break;}//GENERABA EL EVENTO QUE SEGUIA DESPUES DEL MAX :P
			this.reloj=(Integer) evento.getTiempo();
			String tipoEvento = (String) evento.getTipoEvento();
			
			if(tipoEvento.equals(this.INDICADOR_FALLA)){
				this.eventoFallo();
				imprimir(evento);
			}else{
				this.eventoReparacion();
				imprimir(evento);
			}
		}while (this.reloj < this.MAX_TIEMPO);
		
	}
	
	/**
	 * @param e
	 */
	private void imprimir(Evento<Integer, String> e){
		System.out.println(" " + this.reloj + ", E=" + e.getTipoEvento()+ ", CA=" + this.colaAdicionales + 
				", CR=" + this.colaRepacion + ", CF=" + this.colaFuncionamiento+ ", NR=" + this.reparadoresDisponibles +", LEF=" + this.listaEventos.toString());
	}
	

	/**
	 * @param args
	 */
	public static void main(String [] args )
		{
		
		//Pruebas:
			Simulacion s = new Simulacion(12345, 50,8,7,1300);
			s.starSimulacion();
		
		}

}
