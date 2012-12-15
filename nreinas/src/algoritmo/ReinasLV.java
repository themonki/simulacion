/**
 * 
 */
package algoritmo;

import java.util.Vector;

import javax.xml.bind.util.ValidationEventCollector;

/**
 * @author "Edgar Andrés Moncada"
 *
 */
public class ReinasLV {
	
	/**
	 * Indica el tamaño de la entrada para el problema
	 */
	private int inputSize;
	
	private int solution [];
	
	private Vector<Integer> col , diag45 , diag135;
	
	private Generador generador;
	
	private Vector<Integer> pruebas;
	private float probabilidad;
	private float promedioAleatorio;
	private Vector<Float> aleatorios;
	



	public ReinasLV(int inputSize, int seed){
		this.inputSize = inputSize;
		this.solution = new int [this.inputSize];
		this.col = new Vector<Integer> (0,1);
		this.diag45 = new Vector<Integer> (0,1);
		this.diag135 = new Vector<Integer> (0,1);
		generador = new Generador(seed);
		this.pruebas = new Vector<Integer> (0,1);
		this.aleatorios = new Vector<Float> (0,1);
		
		
	}
	
	/**
	 * Procedimiento que coloca reinas al azar y determina si es o no una solución factible.
	 * @return
	 */
	public boolean procedureReinasLV(){
		this.promedioAleatorio=0;
		Vector<Integer> ok =new Vector<Integer> (0,1);
		for(int k = 1; k <=this.inputSize; k++){
			int nb = 0;
			for(int j = 1; j <= this.inputSize; j++){
				
				/*System.out.println(this.col.contains(j) + " : " + j+" --- " + this.col.toString());
				System.out.println(this.diag45.contains((j-k+1)) + " : " + (j-k+1)+" --- " + this.diag45.toString());
				System.out.println(this.diag135.contains((j+k-1)) + " : " + (j+k-1)+" --- " + this.diag135.toString());
				System.out.println();
				*/
				if(
						!this.col.contains(j) &&
						!this.diag45.contains((j-k+1)) &&
						!this.diag135.contains((j+k-1))
					){// se encontro una solución factible
					nb++;
					ok.add(j);	
					promedioAleatorio++;
					/*System.out.println(ok.toString());
					System.out.println();
					*/
				}				
			}
			
			if(nb==0) { promedioAleatorio /= k; return false;} // No se encontraron soluciones factibles
			int selectPosFac = ok.get(generador.a(nb)-1);// se escoge una de las soluciones factibles
			this.col.add(selectPosFac);
			this.diag45.add((selectPosFac-k+1));
			this.diag135.add((selectPosFac+k-1));
			this.solution[k-1] = selectPosFac;
			ok.clear();
		}
		
		promedioAleatorio /=this.inputSize;
		return true;
	}
	
	public void starReinasLV(){
		while(!this.procedureReinasLV()){
			//System.out.println("No encontro solución");
			//System.out.println(this.toString());
			pruebas.add(this.col.size());
			aleatorios.add(promedioAleatorio);
			this.clearAll();
		}
		//System.out.println("Encontro Solución");
		//System.out.println(this.toString());
		aleatorios.add(promedioAleatorio);
		this.probabilidad = (float) (1.0/(this.pruebas.size()+1));

	}
	
	public void clearAll(){
		this.col.clear();
		this.diag45.clear();
		this.diag135.clear();
		this.solution=new int[this.inputSize];
	}
	
	@Override
	public String toString(){
		String value="col: ";
		value+= this.col.toString() +"\n";
		value+="diag45: ";
		value+= this.diag45.toString() +"\n";
		value+="diag135: ";
		value+= this.diag135.toString() +"\n";
		value+="Soluction: [";
		for(int i = 0;i<this.solution.length;i++){
			value += this.solution[i] + ", ";
		}
		value+="]\n";
		return value;
	}
	

	/**
	 * @return the solution
	 */
	public int[] getSolution() {
		return solution;
	}

	/**
	 * @param solution the solution to set
	 */
	public void setSolution(int[] solution) {
		this.solution = solution;
	}
	
	
	public Vector<Integer> getPruebas() {
		return pruebas;
	}

	public void setPruebas(Vector<Integer> pruebas) {
		this.pruebas = pruebas;
	}

	public float getProbabilidad() {
		return probabilidad;
	}

	public void setProbabilidad(float probabilidad) {
		this.probabilidad = probabilidad;
	}
	
	public float getPromedioAleatorio() {
		return promedioAleatorio;
	}

	public void setPromedioAleatorio(float promedioAleatorio) {
		this.promedioAleatorio = promedioAleatorio;
	}
	
	public Vector<Float> getAleatorios() {
		return aleatorios;
	}

	public void setAleatorios(Vector<Float> aleatorios) {
		this.aleatorios = aleatorios;
	}
	
	public static void main (String args[]){
		
		Vector<Vector<Integer>> fracasos= new Vector<Vector<Integer>> (0,1);
		Vector<Integer> fracasosNum= new Vector<Integer>(0,1);
		Vector<Float> promedioReinas = new Vector<Float>(0,1);
		Vector<Float> probabilidades= new Vector<Float>(0,1); 
		Vector<Vector<Float>> promediosAl= new Vector<Vector<Float>> (0,1);
		Vector<Float> promedioAleatorio= new Vector<Float>(0,1);
		
		float probabilidadprom=0;
		for(int i=0; i<1000; i++)
		{
			ReinasLV r = new ReinasLV(8, 10);
			r.starReinasLV();
			promediosAl.add(r.getAleatorios());
			float ale=0;
			for(int j=0; j<r.getAleatorios().size(); j++){
				ale+= r.getAleatorios().elementAt(j);
			}
			promedioAleatorio.add(ale/r.getAleatorios().size());
			fracasos.add(r.getPruebas());
			fracasosNum.add(r.getPruebas().size());
			float reinas=0;
			for(int j=0; j<r.getPruebas().size(); j++){
				reinas+=r.getPruebas().elementAt(j);
			}
			reinas/= r.getPruebas().size();
			promedioReinas.add(reinas);
			probabilidades.add(r.getProbabilidad());
			probabilidadprom+= r.getProbabilidad();
		}
		probabilidadprom /=1000; 
		//System.out.println(fracasos);
		//System.out.println(fracasosNum);
		//System.out.println(promedioReinas);
		//System.out.println(probabilidades);
		System.out.println(promediosAl);
		System.out.println(probabilidadprom);
		System.out.println(promedioAleatorio);
		
		System.out.println();
		
	
		
		
		
		
		
		
	}
	
	
}
