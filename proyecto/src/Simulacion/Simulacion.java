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
	private int reloj,reloj_calentamiento,reloj_anterior_reparadores;
	private int tiempoCalentamiento=1000;
	
	boolean activarVariablesDesempenio=false;
	
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
	private Queue<String> maquinasEnColaReparacion;
	private Vector<String> maquinasConReparador;
	
	//Informacion para UI
	private Queue<String> maquinasAdicionalesCalentamiento;
	private Queue<String> maquinasEnColaRepacionCalentamiento;
	private Vector<String> maquinasConReparadorCalentamiento;	
	
	
	//private Queue<String> puestosLibres; //Maquinas que no pudieron ser reemplazadas No es necesario para interfaz grafica
	
	//variable desempeño
	private int relojAnterior;
	private int desempenioSumFuncionamiento;
	
	private int desempenioSumReparadores_ocupacion;//para calcular el promedio de ocupacion de todos los reparadores 
	

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
	private double desempenioTotal;
	private double desempenioSumReparadores_ocupacion_total;

	
	

	public double getDesempenioSumReparadores_ocupacion_total() {
		return desempenioSumReparadores_ocupacion_total;
	}


	public void setDesempenioSumReparadores_ocupacion_total(
			double desempenioSumReparadores_ocupacion_total) {
		this.desempenioSumReparadores_ocupacion_total = desempenioSumReparadores_ocupacion_total;
	}


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
		this.MAX_TIEMPO = tiempoMax+tiempoCalentamiento;		
		this.colaRepacion=0;		
		this.colaAdicionales= this.MAX_MAQUINAS_ADICIONALES = colaAdicionales;
		this.colaFuncionamiento= this.MAX_MAQUINAS=colaFuncionamiento;		
		this.reparadoresDisponibles = this.MAX_REPARADOR = cantReparadores;
		
		//variables de desempeño
		this.desempenioSumFuncionamiento=0; //representa la suma ponderada de los cambios (cantidad de nodos * (tiempo ahora - tiempo anterior))
		this.desempenioColaAdicional=0;
		this.desempenioColaReparador=0;
		
		this.maquinasAdicionales = new LinkedList<String>();
		this.maquinasEnColaReparacion = new LinkedList<String>();
		this.maquinasConReparador = new Vector<String>();
		//this.puestosLibres = new LinkedList<String>();
		
		//Informacion para UI
		this.maquinasAdicionalesCalentamiento = new LinkedList<String>();
		this.maquinasConReparadorCalentamiento= new Vector<String>();
		this.maquinasEnColaRepacionCalentamiento = new LinkedList<String>();
		
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
			this.maquinasConReparador.add(maquina); //Maquina que esta atendiendo un reparador
			//System.out.println(this.desempenioSumReparadores_ocupacion);
			this.desempenioSumReparadores_ocupacion += (this.MAX_REPARADOR-this.reparadoresDisponibles)*(reloj-reloj_anterior_reparadores);
			System.out.println("Evento: "+this.INDICADOR_FALLA+ " reparadores: " + (this.MAX_REPARADOR-this.reparadoresDisponibles)+" * Time: (" +this.reloj +" - "+this.reloj_anterior_reparadores  + ") = Value dre : "+ this.desempenioSumReparadores_ocupacion );
			
			
			reloj_anterior_reparadores=reloj;
			
			
			
			this.reparadoresDisponibles--;
			//Evento de reparacion
			this.listaEventos.add(new Evento<Integer, String, String>(this.reloj+this.tiempos.tiempoReparacion(), 
					this.INDICADOR_REPARACION,
					maquina));
			
			// Información UI
			
			this.addEventoResumenSimulacion(maquina, this.REPARACION);
			
			
		}
		else{
			this.colaRepacion++; //Se suma a la cola de repacion
			if(this.colaRepacion>this.desempenioColaReparador)this.desempenioColaReparador=this.colaRepacion;
			this.maquinasEnColaReparacion.add(maquina);
			
			// Información UI
			this.addEventoResumenSimulacion(maquina, this.COLAREPARACION);
			
		}
		
		if(this.colaAdicionales>0){
			this.colaAdicionales--; //Si hay maquinas adicionales se reasume.
			listaEventos.add(new Evento<Integer, String, String>(this.reloj+ this.tiempos.tiempoFalloMaquina(), 
					this.INDICADOR_FALLA, 
					this.maquinasAdicionales.peek()));
			
			// Información UI
			this.addEventoResumenSimulacion(this.maquinasAdicionales.poll(), this.REEMPLAZO);
			
			
		}else{
			
			
			//ESTO SE HACE PARA ACTIVAR LAS VARIABLES DESEMPEÑO UNA VEZ ACABE EL TIEMPO DE CALENTAMIENTO
			/* DESEMPENO
			 * FALLA UNA MAQUINA Y NO SE TIENE MAQUINAS ADICIONALES ENTONCES SE CAMBIA LA COLA DE FUNCIONAMIENTO, SE RECALCULA EL CUADRADO
			 * */
				
			//System.out.println(desempenioFuncionamiento);	
			this.desempenioSumFuncionamiento+=this.colaFuncionamiento*(this.reloj-this.relojAnterior);
			//System.out.println("Evento: "+this.INDICADOR_FALLA+ " Cola: " + this.colaFuncionamiento +" * Time: (" +this.reloj +" - "+this.relojAnterior  + ") = Value: "+ this.desempenioSumFuncionamiento );
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
		
		this.maquinasConReparador.remove(maquina); //Maquina que deja de reparar un reparador
			
		if(this.colaFuncionamiento< this.MAX_MAQUINAS){//tenemos espacio para ponerla a funcionar y se supone no se tienen maquinas adiconales
			
			/* DESEMPENO
			 * SE REPARO UNA MAQUINA Y SE TIENE UN HUECO ENTONCES SE CAMBIA LA COLA DE FUNCIONAMIENTO, SE RECALCULA EL CUADRADO
			 * */
			this.desempenioSumFuncionamiento+=this.colaFuncionamiento*(this.reloj-this.relojAnterior);
			
		
			this.relojAnterior=this.reloj;
			/* **************************/
			this.colaFuncionamiento++;

			listaEventos.add(new Evento<Integer, String, String>(this.reloj+ this.tiempos.tiempoFalloMaquina(), 
					this.INDICADOR_FALLA, 
					 maquina));
			
			// Información UI
			this.addEventoResumenSimulacion(maquina, this.REEMPLAZO);			
			
			
			//this.puestosLibres.poll(); //Puede servir para que cuando se pase a la parte grafica se sepa que lugar reemplazar
		}else{//si esta completo, se coloca como adicional
			this.colaAdicionales++;
			if(this.colaAdicionales>this.desempenioColaAdicional)this.desempenioColaAdicional=this.colaAdicionales;
			this.maquinasAdicionales.add(maquina);
			
			// Información UI
			this.addEventoResumenSimulacion(maquina, this.COLAADICIONAL);
			
		}
				
		if(this.colaRepacion>0){//se tienen maquinas a reparar
			this.listaEventos.add(new Evento<Integer, String, String> (this.reloj+this.tiempos.tiempoReparacion(),
					this.INDICADOR_REPARACION, this.maquinasEnColaReparacion.peek()));
			
			this.maquinasConReparador.add(this.maquinasEnColaReparacion.peek()); //Maquina que pasa a estar con reparador
			
			// Información UI
			this.addEventoResumenSimulacion(this.maquinasEnColaReparacion.poll(), this.REPARACION);
			
			
			this.colaRepacion--;
			
		}else{// cola del reparador libre
			
			
			this.desempenioSumReparadores_ocupacion +=  (this.MAX_REPARADOR-this.reparadoresDisponibles)*(reloj-reloj_anterior_reparadores);
			
			
			
			System.out.println("Evento: "+this.INDICADOR_REPARACION+ " reparadores: " + (this.MAX_REPARADOR-this.reparadoresDisponibles)+" * Time: (" +this.reloj +" - "+this.reloj_anterior_reparadores  + ") = Value dre : "+ this.desempenioSumReparadores_ocupacion );
			
			
			reloj_anterior_reparadores=reloj;
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
			
			if (this.reloj > tiempoCalentamiento && !activarVariablesDesempenio){
				
				reloj_calentamiento=this.reloj;// a pesar de que el tiempo de calentamiento es constante no sabemos el tiempo verdadero donde para.
			   
				relojAnterior=this.reloj;
				
				reloj_anterior_reparadores=this.reloj;
				//reloj_anterior_reparadores=relojAnterior;
				
				
				activarVariablesDesempenio=true;//activa el calculo del desempeño 
			    //relojAnterior=this.reloj;
			    this.desempenioSumFuncionamiento=0;
			    
			    System.out.println("estoy aqui " +this.reloj+"--" +tiempoCalentamiento);
			    this.desempenioSumReparadores_ocupacion=0;
			    System.out.println("estoy aqui"+desempenioSumReparadores_ocupacion);
			    System.out.println(" cantidad de disponibles"+ this.reparadoresDisponibles);
			    
			    this.resumenSimulacion.clear();
			    
			    int size= this.maquinasAdicionales.size();
			    
			    Object [] arreglo_con_maquinas_adicionales = this.maquinasAdicionales.toArray();
			    
			    for (int i=0;i<size;i++)
			    this.maquinasAdicionalesCalentamiento.add((String) arreglo_con_maquinas_adicionales[i]) ;
			    
			    size=this.maquinasConReparador.size();
			    
			    
			    for (int i=0;i<size;i++)
			    this.maquinasConReparadorCalentamiento.add( this.maquinasConReparador.get(i)) ;
			    
			    
			    
			    
			    
			    
			    size=this.maquinasEnColaReparacion.size();
			    
			    Object [] maquinasEnColaReparacion_arreglo=  this.maquinasEnColaReparacion.toArray();
			    for (int i=0;i<size;i++)
			    this.maquinasEnColaRepacionCalentamiento.add((String) maquinasEnColaReparacion_arreglo[i]);  
			    
			    
			    //this.maquinasAdicionales.clear();
			    //this.maquinasConReparador.clear();
			    //this.maquinasEnColaReparacion.clear();
			    
			    
			    
			    //System.out.println(this.getMaquinasConReparador());
				
			} 
			
			
			
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
			
			//relojAnterior=this.reloj;
			

		}while (this.reloj < this.MAX_TIEMPO);
		
		try {
		
		this.desempenioSumFuncionamiento += this.colaFuncionamiento*(this.reloj-this.relojAnterior);
		
		this.desempenioSumReparadores_ocupacion += (this.MAX_REPARADOR-this.reparadoresDisponibles)*(reloj-reloj_anterior_reparadores);
		System.out.println("reloj "+reloj+"reloj anterior reparadores"+reloj_anterior_reparadores+"reloj calentamiento "+this.reloj_calentamiento);
		
		desempenioSumReparadores_ocupacion_total += (double) this.desempenioSumReparadores_ocupacion/(double) (((this.reloj-this.reloj_calentamiento)*this.MAX_REPARADOR));
		
		//System.out.println( "sum" + this.desempenioSumReparadores_ocupacion+" max repa"+(this.MAX_REPARADOR-this.reparadoresDisponibles)+" reloj" +(reloj-reloj_anterior_reparadores) );
		
		
			
			
		desempenioTotal=(double)(desempenioSumFuncionamiento)/(double)(((this.reloj-this.reloj_calentamiento)*50));
		
		System.out.println("desempenioSumReparadores_ocupacion "+desempenioSumReparadores_ocupacion);
		System.out.println("tiempo "+this.reloj+"-"+this.reloj_calentamiento);
		
		
		} catch (Exception e){}
		
		/*System.out.println(resumenSimulacion);
		System.out.println("fina::"+(desempenioTotal*100));
		System.out.println("maquinas con el reparador: " + this.getMaquinasConReparador());
		System.out.println("maquinas adicionales: " +this.getMaquinasAdicionales());
		System.out.println("maquinas en cola de reparacion: " +this.getMaquinasEnColaReparacion());*/

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
			Simulacion s = new Simulacion(12345, 50,10,1,100)  ;
			s.starSimulacion();
			
			System.out.println("desempeño:: "+(s.getDesempenioTotal()*100));
			
			System.out.println("desempeño:: reparadores  "+(s.getDesempenioSumReparadores_ocupacion_total()));
		
		}
	
	public Vector<Vector<Object>> getResumenSimulacion(){
		return this.resumenSimulacion;
	}

	/**
	 * @return the desempenioFuncionamiento
	 */
	public int getDesempenioFuncionamiento() {
		return desempenioSumFuncionamiento;
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
		this.desempenioSumFuncionamiento = desempenioFuncionamiento;
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
	
	private void addEventoResumenSimulacion(String maquina, String estado){
		Vector<Object> evento= new Vector<Object>();
		evento.add(maquina);
		evento.add(estado);
		evento.add(this.reloj);
		
		this.resumenSimulacion.add(evento);
		
	}

	public Queue<String> getMaquinasAdicionales() {
		return maquinasAdicionales;
	}

	public void setMaquinasAdicionales(Queue<String> maquinasAdicionales) {
		this.maquinasAdicionales = maquinasAdicionales;
	}

	public Queue<String> getMaquinasEnColaReparacion() {
		return maquinasEnColaReparacion;
	}

	public void setMaquinasEnColaReparacion(Queue<String> maquinasEnColaReparacion) {
		this.maquinasEnColaReparacion = maquinasEnColaReparacion;
	}

	public Vector<String> getMaquinasConReparador() {
		return maquinasConReparador;
	}

	public void setMaquinasConReparador(Vector<String> maquinasConReparador) {
		this.maquinasConReparador = maquinasConReparador;
	}
	
	public Queue<String> getMaquinasAdicionalesCalentamiento() {
		return maquinasAdicionalesCalentamiento;
	}

	public void setMaquinasAdicionalesCalentamiento(
			Queue<String> maquinasAdicionalesCalentamiento) {
		this.maquinasAdicionalesCalentamiento = maquinasAdicionalesCalentamiento;
	}

	public Queue<String> getMaquinasEnColaRepacionCalentamiento() {
		return maquinasEnColaRepacionCalentamiento;
	}

	public void setMaquinasEnColaRepacionCalentamiento(
			Queue<String> maquinasEnColaRepacionCalentamiento) {
		this.maquinasEnColaRepacionCalentamiento = maquinasEnColaRepacionCalentamiento;
	}

	public Vector<String> getMaquinasConReparadorCalentamiento() {
		return maquinasConReparadorCalentamiento;
	}

	public void setMaquinasConReparadorCalentamiento(
			Vector<String> maquinasConReparadorCalentamiento) {
		this.maquinasConReparadorCalentamiento = maquinasConReparadorCalentamiento;
	}
	
	
	public int getDesempenioSumReparadores_ocupacion() {
		return desempenioSumReparadores_ocupacion;
	}

	public void setDesempenioSumReparadores_ocupacion(int desempenioSumReparadores_ocupacion) {
		this.desempenioSumReparadores_ocupacion = desempenioSumReparadores_ocupacion;
	}
	
	public double getDesempenioTotal() {
		return desempenioTotal;
	}

	public void setDesempenioTotal(double desempenioTotal) {
		this.desempenioTotal = desempenioTotal;
	}
}
