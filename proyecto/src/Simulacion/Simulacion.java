package Simulacion;

import java.util.LinkedList;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;


public class Simulacion {
	
	private LinkedList<Evento<Integer, String>> listaEventos;
	private int reloj;
	private Generador tiempos;
	private int colaRepacion;
	private int colaAdicionales;
	private boolean ocupado;
	
	public Simulacion(int seed, int colaAdicionales){
		
		listaEventos = new LinkedList<Evento<Integer,String>>();
		reloj=0;
		tiempos = new Generador(seed);	
		this.colaAdicionales= colaAdicionales;
		colaRepacion=0;
		ocupado=false;
		
	}
	
	public void eventoFallo(){
		
	}
	
	public void eventoReparacion(){
		
	}
	
	public void starSimulacion()
	{
		Evento<Integer, String> uno= new Evento<Integer, String>(0, "F");
		listaEventos.add(uno);
		imprimir(uno);
		
		while (this.reloj <= 80){
			Evento<Integer, String> evento = listaEventos.getFirst();
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
		System.out.println(" " + this.reloj + ", " + e.getTipoEvento()+ ", " + this.colaAdicionales + 
				", " + this.colaRepacion + ", " + this.listaEventos.toString());
	}

}
