import java.math.BigInteger;
public class Fermat_extendido {
	
	private int numero_primo;
	private Generador generador;
	
	public Fermat_extendido(int numero_primo){
		
		this.numero_primo=numero_primo;
		generador= new Generador(12345);
	}
	
	//http://www.slideshare.net/fivefingers/potencias-modulares
	
	 
	public boolean  MillerRabinPrimalityTest(int certain, long number) {
	 
		if (number>2 && ispar(number)) return false;// por que el 2 es par y es primo 
		
		double s=0.0;
		long t= number-1;
		
		//System.out.println("t::"+t +"  s::"+s);
		while (ispar(t)){
			s++;
			t=t/2;
			
		}
		
		System.out.println("t::"+t +"  s::"+s);
		
		// log_2(1/e)= ln(1/e)/ln 2
		double e=0.0001;		
		double k = Math.log(1/e) / Math.log(2);
		
		k= Math.ceil(k /2);
		
		//System.out.println("k::"+k);
		
		//System.out.println("t bits::"+Long.toBinaryString(t));
		
		
		
		
		
		//for (int j=0;j<k;j++) {
		
		long x = generador.aleatorio(number);
		
		long modulo = calcularPotenciaModular(x,t,number);
		System.out.println("modulo:: "+modulo+ "number"+ number+ " a:: "+x);
		
		
		if (modulo==1) {
			
			//System.out.println(x + "::::true");
			return true;}
		else {
			
			
			for (int i=0;i<=s;i++){
				int  potencia = (int) Math.pow(2,i);
				
				long modulo2 = calcularPotenciaModular(x,(long ) (potencia*t),number);
				
				System.out.println("modulo2:: "+modulo2+ "number"+ number+ " a:: "+x+"s"+s);
				
				if(modulo2 == number-1   ){ 
					//System.out.println(x + "true");
					
					return true;
					}
				
			}
			
			
		}
				
		//System.out.println(x + " a  false");
		
		return false ;
		
		
		
		//}
		
		
		
		
		
		
		/* Factor n  1 as 2s t where t is odd. */
		
		
		
	   /* BigInteger n = new BigInteger("");
	    int certainty = certain;
	    System.out.println(n.toString() + " is " + (n.isProbablePrime(certainty) ? "probably prime" : "composite"));*/
	  
	}




	public  long calcularPotenciaModular(long base, long  exponente, long modulo){
	       
        long resultado = 1;
        for(int i = 1; i<=exponente; i++){
            resultado = (resultado*base)%modulo;
        
        } 
        
        return resultado;
    }
	
	

	private boolean ispar(double t) {
		// TODO Auto-generated method stub
		if (t%2==0) {
			
			//System.out.println("es par ::"+t);
			return true;}
		
		
		//System.out.println("NO es par ::"+t);
		return false;
	}
	
	
	public static void main (String args[]){
		
		Fermat_extendido ferma = new Fermat_extendido(0);
		
		Integer [] a= { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89 , 97};
		
		//for (int i=0;i<10;i++)
		System.out.println(ferma.MillerRabinPrimalityTest(0, 1001));
		
		
		
	}
	
	
	

}
