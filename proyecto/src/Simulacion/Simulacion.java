package Simulacion;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import org.omg.CORBA.PRIVATE_MEMBER;



/**
 * @author monki
 *
 */
public class Simulacion {
	
	//private LinkedList<Evento<Integer, String>> listaEventos;
	private PriorityQueue<Evento<Integer, String, String>> listaEventos;
	private int reloj;
	private Generador tiempos;
	private int colaRepacion;
	private int colaAdicionales;
	private int colaFuncionamiento;
	private int reparadoresDisponibles;
	private int desempenioFuncionamiento;
	private int MAX_MAQUINAS;
	private int MAX_REPARADOR;
	private int MAX_MAQUINAS_ADICIONALES;
	private int MAX_TIEMPO;
	private String INDICADOR_FALLA="F";
	private String INDICADOR_REPARACION="R";
	private Queue<String> maquinasAdicionales;
	private Queue<String> maquinasReparacion;
	private Queue<String> puestosLibres; //Maquinas que no pudieron ser reemplazadas

	
	/**
	 * @param seed Indica la semilla para las distribuciones
	 * @param colaFuncionamiento indica la cantidad de maquinas que deben de estar funcionando
	 * @param colaAdicionales indica la cantidad de maquinas adicionales disponibles
	 * @param cantReparadores indica la cantidad de reparadores que se tienen
	 * @param tiempoMax indica la cantidad de tiempo maximo invertido en la simulacion
	 */
	public Simulacion(int seed, int colaFuncionamiento, 
			int colaAdicionales, int cantReparadores, int tiempoMax){
		
		//listaEventos = new LinkedList<Evento<Integer,String>>();
		this.listaEventos = new PriorityQueue<Evento<Integer,String,String>>(11, new Comparator<Evento<Integer, String, String>>() {

			@Override
			public int compare(Evento<Integer, String, String> o1,
					Evento<Integer, String, String> o2) {
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
		
		
		this.maquinasAdicionales = new LinkedList<String>();
		this.maquinasReparacion = new LinkedList<String>();
		this.puestosLibres = new LinkedList<String>();
		
		//Inicializar maquinas adicionales
		for(int i=0; i< this.colaAdicionales; i++){
			this.maquinasAdicionales.add("A"+(i+1));
		}
		
	}
	
	/**
	 * Función que crea un evento de fallo de una maquina en el sistema
	 * 
	 */
	public void eventoFallo(String maquina){
				
		/*if(this.colaFuncionamiento<=0){
			//no ahi maquinas a fallar
			return;
		}*/ //CREO QUE AHORA NO HACE FALTA.
		

		
		if(this.reparadoresDisponibles>0){
			this.reparadoresDisponibles--;
			//Evento de reparacion
			this.listaEventos.add(new Evento<Integer, String, String>(this.reloj+this.tiempos.tiempoReparacion(), 
					this.INDICADOR_REPARACION,
					maquina));
		}
		else{
			this.colaRepacion++; //Se suma a la cola de repacion
			this.maquinasReparacion.add(maquina);
		}
		
		if(this.colaAdicionales>0){
			this.colaAdicionales--; //Si hay maquinas adicionales se reasume.
			listaEventos.add(new Evento<Integer, String, String>(this.reloj+ this.tiempos.tiempoFalloMaquina(), 
					this.INDICADOR_FALLA, 
					this.maquinasAdicionales.poll()));
		}else{
			this.colaFuncionamiento--; //Si no hay maquinas adicionales se resta a la cola de funcionamiento
			this.puestosLibres.add(maquina);
		}
		
		
	}
	
	/**
	 * Función que genera el evento de reparación de una maquina en el sistema
	 */
	public void eventoReparacion(String maquina){
			
		if(this.colaFuncionamiento< this.MAX_MAQUINAS){//tenemos espacio para ponerla a funcionar
			this.colaFuncionamiento++;
			listaEventos.add(new Evento<Integer, String, String>(this.reloj+ this.tiempos.tiempoFalloMaquina(), 
					this.INDICADOR_FALLA, 
					 maquina));
			this.puestosLibres.poll(); //Puede servir para que cuando se pase a la parte grafica se sepa que lugar reemplazar
		}else{//si esta completo, se coloca como adicional
			this.colaAdicionales++;
			this.maquinasAdicionales.add(maquina);
		}
				
		if(this.colaRepacion>0){//se tienen maquinas a reparar
			this.listaEventos.add(new Evento<Integer, String, String> (this.reloj+this.tiempos.tiempoReparacion(),
					this.INDICADOR_REPARACION, this.maquinasReparacion.poll()));
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
		for(int i=0;i<this.MAX_MAQUINAS;i++){//ANALIZAR ESTO
			Evento<Integer, String, String> uno= new Evento<Integer, String, String>((i*8)+ this.tiempos.tiempoFalloMaquina(),
					this.INDICADOR_FALLA,
					""+(i+1));
			this.listaEventos.add(uno);
			//imprimir(uno);
		}
		System.out.println("LEF= " + this.listaEventos);
		do{
			Evento<Integer, String, String> evento = this.listaEventos.poll();
			
			//if((Integer) evento.getTiempo()>=this.MAX_TIEMPO){break;}//GENERABA EL EVENTO QUE SEGUIA DESPUES DEL MAX :P
			this.reloj=(Integer) evento.getTiempo();
			String tipoEvento = (String) evento.getTipoEvento();
			String maquina = (String) evento.getMaquina();
			
			if(tipoEvento.equals(this.INDICADOR_FALLA)){
				this.eventoFallo(maquina);
				imprimir(evento);
			}else{
				this.eventoReparacion(maquina);
				imprimir(evento);
			}
		}while (this.reloj < this.MAX_TIEMPO);
		
	}
	
	/**
	 * @param e
	 */
	private void imprimir(Evento<Integer, String, String> e){
		System.out.println(" " + this.reloj + ", E=" + e.getTipoEvento()+ ", M= " + e.getMaquina()+ ", CA=" + this.colaAdicionales + 
				", CR=" + this.colaRepacion + ", CF=" + this.colaFuncionamiento+ ", NR=" + this.reparadoresDisponibles +", LEF=" + this.listaEventos.toString()
				+ ", MA= " + this.maquinasAdicionales + ", MR= " + this.maquinasReparacion + "PL= " + this.puestosLibres);
	}
	

	/**
	 * @param args
	 */
	public static void main(String [] args )
		{
		
		//Pruebas:
			Simulacion s = new Simulacion(12345, 50,1,1,2000);
			s.starSimulacion();
		
		}

}
