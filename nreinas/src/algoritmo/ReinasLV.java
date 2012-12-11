/**
 * 
 */
package algoritmo;

import java.util.Vector;

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
	
	public ReinasLV(int inputSize, int seed){
		this.inputSize = inputSize;
		this.solution = new int [this.inputSize];
		this.col = new Vector<Integer> (0,1);
		this.diag45 = new Vector<Integer> (0,1);
		this.diag135 = new Vector<Integer> (0,1);
		generador = new Generador(seed);
		
		
	}
	
	/**
	 * Procedimiento que coloca reinas al azar y determina si es o no una solución factible.
	 * @return
	 */
	public boolean procedureReinasLV(){
		
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
					/*System.out.println(ok.toString());
					System.out.println();
					*/
				}				
			}
			
			if(nb==0) return false; // No se encontraron soluciones factibles
			int selectPosFac = ok.get(generador.a(nb)-1);// se escoge una de las soluciones factibles
			this.col.add(selectPosFac);
			this.diag45.add((selectPosFac-k+1));
			this.diag135.add((selectPosFac+k-1));
			this.solution[k-1] = selectPosFac;
			ok.clear();
		}
		
		return true;
	}
	
	public void starReinasLV(){
		while(!this.procedureReinasLV()){
			System.out.println("No encontro solución");
			System.out.println(this.toString());
			this.clearAll();
		}
		System.out.println("Encontro Solución");
		System.out.println(this.toString());

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
	
	public static void main (String args[]){
		
		ReinasLV r = new ReinasLV(8, 10);
		r.starReinasLV();
		
	}
	
}
