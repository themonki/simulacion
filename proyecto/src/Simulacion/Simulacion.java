package Simulacion;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Vector;
import java.util.Map.Entry;


public class Simulacion {
	
	//private LinkedList<Evento<Integer, String>> listaEventos;
	private PriorityQueue<Evento<Integer, String>> listaEventos;
	private int reloj;
	private Generador tiempos;
	private int colaRepacion;
	private int colaAdicionales;
	private int colaFuncionamiento;
	private boolean ocupado;
	
	public Simulacion(int seed, int colaAdicionales){
		
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
		this.colaAdicionales= colaAdicionales;
		this.colaFuncionamiento=50;
		colaRepacion=0;
		ocupado=false;
		
	}
	
	public void eventoFallo(){
		
		//Nuevo evento de fallo.
		listaEventos.add(new Evento<Integer, String>(this.reloj+ tiempos.tiempoFalloMaquina(), "F"));
		if(!ocupado){
			ocupado=true;
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
	
	public void eventoReparacion(){
	}
	
	public void starSimulacion()
	{
		Evento<Integer, String> uno= new Evento<Integer, String>(0, "F");
		listaEventos.add(uno);
		imprimir(uno);
		
		while (this.reloj <= 80){
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
	
	private void imprimir(Evento e){
		System.out.println(" " + this.reloj + ", E=" + e.getTipoEvento()+ ", CA=" + this.colaAdicionales + 
				", CR=" + this.colaRepacion + ", CF" + this.colaFuncionamiento+ ", LEF=" + this.listaEventos.toString());
	}
	

	public static void main(String [] args )
		{
		
		//Pruebas:
			Simulacion s = new Simulacion(456778, 0);
			s.starSimulacion();
		
		}

}
