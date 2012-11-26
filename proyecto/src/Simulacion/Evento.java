package Simulacion;

public class Evento<Tiempo,TipoEvento> {

	private Tiempo tiempo;
	private TipoEvento tipoEvento;

	public Evento(Tiempo tiempo, TipoEvento tipoEvento){
		this.tiempo = tiempo;
		this.tipoEvento = tipoEvento;
	}

	public Tiempo getTiempo(){
		return this.tiempo;
	}

	public TipoEvento getTipoEvento(){
		return this.tipoEvento;
	}

	public void setTiempo(Tiempo nuevoTiempo){
		this.tiempo = nuevoTiempo;
	}

	public void setTipoEvento(TipoEvento nuevoTipoEvento){
		this.tipoEvento= nuevoTipoEvento;				
	}

	public String toString(){
		String a;
		a= "("+this.tiempo + "," + this.tipoEvento+ ")";
		return a;
	}

}
