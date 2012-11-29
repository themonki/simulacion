package Simulacion;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Vector;

import org.omg.CORBA.PRIVATE_MEMBER;



/**
 * @author monki 
 *
 */
public class Simulacion {
	
	private PriorityQueue<Evento<Integer, String, String>> listaEventos;
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
	private Queue<String> maquinasAdicionales;
	private Queue<String> maquinasReparacion;
	//private Queue<String> puestosLibres; //Maquinas que no pudieron ser reemplazadas No es necesario para interfaz grafica
	
	//variable desempeño
	private int relojAnterior;
	private int desempenioFuncionamiento;
	private int desempenioColaReparador;
	private int desempenioColaAdicional;
	
	// Revisar eventos por maquina
    private Vector<Vector<Evento<Integer, String, String>>> eventosMaquina;
    
    // Para interfaz grafica
    private Vector<Vector<Object>> resumenSimulacion;
    private String REPARACION="irAReparador";
    private String COLAREPARACION="irAColaReparacion";
    private String COLAADICIONAL="irAColaAdicional";
    private String REEMPLAZO="irAFabrica";

	
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
		
		//variables de desempeño
		this.desempenioFuncionamiento=this.MAX_MAQUINAS; //representa la suma ponderada de los cambios (cantidad de nodos * (tiempo ahora - tiempo anterior))
		this.desempenioColaAdicional=0;
		this.desempenioColaReparador=0;
		
		this.maquinasAdicionales = new LinkedList<String>();
		this.maquinasReparacion = new LinkedList<String>();
		//this.puestosLibres = new LinkedList<String>();
		
		//Inicializar maquinas adicionales
		for(int i=0; i< this.colaAdicionales; i++){
			this.maquinasAdicionales.add(""+(this.MAX_MAQUINAS+i)); 
		}
		
		
		
		
		//Informativo
		eventosMaquina = new Vector<Vector<Evento<Integer,String,String>>>(this.MAX_MAQUINAS+ this.MAX_MAQUINAS_ADICIONALES);
		for(int i=0; i< this.MAX_MAQUINAS + this.MAX_MAQUINAS_ADICIONALES ; i++){
			eventosMaquina.add(new Vector<Evento<Integer,String,String>>());
			
		}
		
		this.resumenSimulacion = new Vector<Vector<Object>>();
	
		
	}
	
	/**
	 * Función que crea un evento de fallo de una maquina en el sistema
	 * 
	 */
	public void eventoFallo(String maquina){	
		
		if(this.reparadoresDisponibles>0){
			this.reparadoresDisponibles--;
			//Evento de reparacion
			this.listaEventos.add(new Evento<Integer, String, String>(this.reloj+this.tiempos.tiempoReparacion(), 
					this.INDICADOR_REPARACION,
					maquina));
			
			// Información UI
			Vector<Object> evento= new Vector<Object>();
			evento.add(maquina);
			evento.add(this.REPARACION);
			evento.add(this.reloj);
			this.resumenSimulacion.add(evento);
		}
		else{
			this.colaRepacion++; //Se suma a la cola de repacion
			if(this.colaRepacion>this.desempenioColaReparador)this.desempenioColaReparador=this.colaRepacion;
			this.maquinasReparacion.add(maquina);
			// Información UI
			Vector<Object> evento= new Vector<Object>();
			evento.add(maquina);
			evento.add(this.COLAREPARACION);
			evento.add(this.reloj);
			this.resumenSimulacion.add(evento);
		}
		
		if(this.colaAdicionales>0){
			this.colaAdicionales--; //Si hay maquinas adicionales se reasume.
			listaEventos.add(new Evento<Integer, String, String>(this.reloj+ this.tiempos.tiempoFalloMaquina(), 
					this.INDICADOR_FALLA, 
					this.maquinasAdicionales.peek()));
			
			// Información UI
			Vector<Object> evento= new Vector<Object>();
			evento.add(this.maquinasAdicionales.poll());
			evento.add(this.REEMPLAZO);
			evento.add(this.reloj);
			this.resumenSimulacion.add(evento);
			
		}else{
			
			/* DESEMPENO
			 * FALLA UNA MAQUINA Y NO SE TIENE MAQUINAS ADICIONALES ENTONCES SE CAMBIA LA COLA DE FUNCIONAMIENTO, SE RECALCULA EL CUADRADO
			 * */
			this.desempenioFuncionamiento+=this.colaFuncionamiento*(this.reloj-this.relojAnterior);
			//System.out.println("Evento: "+this.INDICADOR_FALLA+ " Cola: " + this.colaFuncionamiento +" * Time: (" +this.reloj +" - "+this.relojAnterior  + ") = Value: "+ this.desempenioFuncionamiento );
			this.relojAnterior=this.reloj;
			/* **************************/			
			this.colaFuncionamiento--; //Si no hay maquinas adicionales se resta a la cola de funcionamiento
			 
			//this.puestosLibres.add(maquina);			
		}
		
		
	}
	
	/**
	 * Función que genera el evento de reparación de una maquina en el sistema
	 */
	public void eventoReparacion(String maquina){
			
		if(this.colaFuncionamiento< this.MAX_MAQUINAS){//tenemos espacio para ponerla a funcionar y se supone no se tienen maquinas adiconales
			
			/* DESEMPENO
			 * SE REPARO UNA MAQUINA Y SE TIENE UN HUECO ENTONCES SE CAMBIA LA COLA DE FUNCIONAMIENTO, SE RECALCULA EL CUADRADO
			 * */
			this.desempenioFuncionamiento+=this.colaFuncionamiento*(this.reloj-this.relojAnterior);
			//System.out.println("Evento: "+this.INDICADOR_REPARACION+ " Cola: " + this.colaFuncionamiento +" * Time: (" +this.relojAnterior +" - "+this.reloj  + ") = Value: "+ this.desempenioFuncionamiento );
			this.relojAnterior=this.reloj;
			/* **************************/
			this.colaFuncionamiento++;

			listaEventos.add(new Evento<Integer, String, String>(this.reloj+ this.tiempos.tiempoFalloMaquina(), 
					this.INDICADOR_FALLA, 
					 maquina));
			// Información UI
			Vector<Object> evento= new Vector<Object>();
			evento.add(maquina);
			evento.add(this.REEMPLAZO);
			evento.add(this.reloj);
			this.resumenSimulacion.add(evento);
			
			//this.puestosLibres.poll(); //Puede servir para que cuando se pase a la parte grafica se sepa que lugar reemplazar
		}else{//si esta completo, se coloca como adicional
			this.colaAdicionales++;
			if(this.colaAdicionales>this.desempenioColaAdicional)this.desempenioColaAdicional=this.colaAdicionales;
			this.maquinasAdicionales.add(maquina);
			
			// Información UI
			Vector<Object> evento= new Vector<Object>();
			evento.add(maquina);
			evento.add(this.COLAADICIONAL);
			evento.add(this.reloj);
			this.resumenSimulacion.add(evento);
		}
				
		if(this.colaRepacion>0){//se tienen maquinas a reparar
			this.listaEventos.add(new Evento<Integer, String, String> (this.reloj+this.tiempos.tiempoReparacion(),
					this.INDICADOR_REPARACION, this.maquinasReparacion.peek()));
			
			// Información UI
			Vector<Object> evento= new Vector<Object>();
			evento.add(this.maquinasReparacion.poll());
			evento.add(this.REPARACION);
			evento.add(this.reloj);
			this.resumenSimulacion.add(evento);
			
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
			Evento<Integer, String, String> uno= new Evento<Integer, String, String>((i*20)+ this.tiempos.tiempoFalloMaquina(),
					this.INDICADOR_FALLA,
					""+i);
			this.listaEventos.add(uno);
			eventosMaquina.get(i).add(uno);
			//imprimir(uno);
		}
		this.relojAnterior=this.reloj; 
		//System.out.println("LEF= " + this.listaEventos);
		do{
			Evento<Integer, String, String> evento = this.listaEventos.poll();
			this.reloj=(Integer) evento.getTiempo();
			String tipoEvento = (String) evento.getTipoEvento();
			String maquina = (String) evento.getMaquina();
			
			//Informativo
			eventosMaquina.get(Integer.parseInt(maquina)).add(evento);
			
			if(tipoEvento.equals(this.INDICADOR_FALLA)){
				this.eventoFallo(maquina);
				imprimir(evento);
			}else{
				this.eventoReparacion(maquina);
				imprimir(evento);
			}
			

		}while (this.reloj < this.MAX_TIEMPO);
		
		
		//System.out.println(getResumenSimulacion());

	}
	
	/**
	 * @param e
	 */
	private void imprimir(Evento<Integer, String, String> e){
		/*System.out.println(" " + this.reloj + ", E=" + e.getTipoEvento()+ ", M= " + e.getMaquina()+ ", CA=" + this.colaAdicionales + 
				", CR=" + this.colaRepacion + ", CF=" + this.colaFuncionamiento+ ", NR=" + this.reparadoresDisponibles +", LEF=" + this.listaEventos.toString()
				+ ", MA= " + this.maquinasAdicionales + ", MR= " + this.maquinasReparacion + "PL= " + this.puestosLibres);
	*/}
	

	/**
	 * @param args
	 */
	public static void main(String [] args )
		{
		
		//Pruebas:
			Simulacion s = new Simulacion(12345, 50,1,1,500);
			s.starSimulacion();
		
		}
	
	public Vector<Vector<Object>> getResumenSimulacion(){
		return this.resumenSimulacion;
	}

	/**
	 * @return the desempenioFuncionamiento
	 */
	public int getDesempenioFuncionamiento() {
		return desempenioFuncionamiento;
	}

	/**
	 * @return the desempenioColaReparador
	 */
	public int getDesempenioColaReparador() {
		return desempenioColaReparador;
	}

	/**
	 * @return the desempenioColaAdicional
	 */
	public int getDesempenioColaAdicional() {
		return desempenioColaAdicional;
	}

	/**
	 * @param desempenioFuncionamiento the desempenioFuncionamiento to set
	 */
	public void setDesempenioFuncionamiento(int desempenioFuncionamiento) {
		this.desempenioFuncionamiento = desempenioFuncionamiento;
	}

	/**
	 * @param desempenioColaReparador the desempenioColaReparador to set
	 */
	public void setDesempenioColaReparador(int desempenioColaReparador) {
		this.desempenioColaReparador = desempenioColaReparador;
	}

	/**
	 * @param desempenioColaAdicional the desempenioColaAdicional to set
	 */
	public void setDesempenioColaAdicional(int desempenioColaAdicional) {
		this.desempenioColaAdicional = desempenioColaAdicional;
	}

}
