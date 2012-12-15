import java.math.BigInteger;
public class Fermat_extendido {
	
	private int numero_primo;
	private Generador generador;
	
	public Fermat_extendido(int numero_primo){
		
		this.numero_primo=numero_primo;
		generador= new Generador(12345);
	}
	
	
	
	 
	public boolean  MillerRabinPrimalityTest(int certain, long number) {
	 
		if (number>2 && ispar(number)) return false;
		
		double s=0.0;
		double t= number-1;
		
		while (ispar(t)){
			s++;
			t=t/2;
			
		}
		
		System.out.println("t::"+t +"  s::"+s);
		
		//for (int i=0;i<10;i++){
		double x = generador.aleatorio(number);
		
		if (Math.pow(x,t)%number==1) return true;
		else {
			for (int i=0;i<s;i++){
				
				if(Math.pow(x,Math.pow(2,i)*t)%number == number-1   ) return true;
				
			}
			
			
		}
				
		System.out.println(x);
		//}
		
		
		
		
		
		
		
		
		
		/* Factor n  1 as 2s t where t is odd. */
		return false;
		
		
	   /* BigInteger n = new BigInteger("");
	    int certainty = certain;
	    System.out.println(n.toString() + " is " + (n.isProbablePrime(certainty) ? "probably prime" : "composite"));*/
	  
	}




	private boolean ispar(double t) {
		// TODO Auto-generated method stub
		if (t%2==0) return true;
		return false;
	}
	
	
	public static void main (String args[]){
		
		Fermat_extendido ferma = new Fermat_extendido(0);
		
		Integer [] a= { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89 , 97};
		
		for (int i=0;i<10;i++)
		System.out.println(ferma.MillerRabinPrimalityTest(0, a[i]));
		
		
		
	}
	
	
	

}
