package Simulacion;

public class Evento<Tiempo,TipoEvento, Maquina> {

	private Tiempo tiempo;
	private TipoEvento tipoEvento;
	private Maquina maquina;

	public Evento(Tiempo tiempo, TipoEvento tipoEvento, Maquina maquina){
		this.tiempo = tiempo;
		this.tipoEvento = tipoEvento;
		this.maquina = maquina;
	}

	public Tiempo getTiempo(){
		return this.tiempo;
	}

	public TipoEvento getTipoEvento(){
		return this.tipoEvento;
	}
	
	public Maquina getMaquina(){
		return this.maquina;
	}

	public void setTiempo(Tiempo nuevoTiempo){
		this.tiempo = nuevoTiempo;
	}

	public void setTipoEvento(TipoEvento nuevoTipoEvento){
		this.tipoEvento= nuevoTipoEvento;				
	}
	
	public void setMaquina(Maquina nuevaMaquina){
		this.maquina= nuevaMaquina;
	}

	public String toString(){
		String a;
		a= "("+this.tiempo + ", " + this.tipoEvento+ ", "+ this.maquina + ")";
		return a;
	}

}
