import java.math.BigInteger;


public class Fermat_extendido {


	private Generador generador;
	
	long  primo1=0,primo2 = 0;



	public long getPrimo2() {
		return primo2;
	}

	public void setPrimo2(long primo2) {
		this.primo2 = primo2;
	}

	public long getPrimo1() {
		return primo1;
	}

	public void setPrimo1(long primo1) {
		this.primo1 = primo1;
	}

	public Fermat_extendido(){



	}

	//http://www.slideshare.net/fivefingers/potencias-modulares


	public boolean  MillerRabinPrimalityTest(double certain, long number) {

		generador= new Generador(12345);



		if (number>2 && ispar(number)) return false;// por que el 2 es par y es primo 

		long s=0;
		long t= number-1;

		//System.out.println("t::"+t +"  s::"+s);
		while (ispar(t)){
			s++;
			t=t/2;

		}

		//System.out.println("t::"+t +"  s::"+s);

		// log_2(1/e)= ln(1/e)/ln 2
		double e=certain;		
		double k = Math.log(1/e) / Math.log(2);

		k= Math.ceil(k /2);

		for (int j=0;j<k;j++) {

			long x= generador.aleatorio(number);
			if(!debeCumplir(x,t,s,number)) return false;

		}


		return true;




	}


	public boolean debeCumplir(long a,long t , long s, long number){



		long x=a;


		long modulo = calcularPotenciaModular(x,t,number);
		//System.out.println("modulo:: "+modulo+ "number"+ number+ " a:: "+x);


		if (modulo==1) {

			//System.out.println(x + "::::true");
			return true;}
		else {


			for (int i=0;i<=s;i++){
				int  potencia = (int) ( Math.pow(2,i)*t);

				long modulo2 = calcularPotenciaModular(x,potencia,number);

				//System.out.println("modulo2:: "+modulo2+ "number"+ number+ " a:: "+x+"i"+i+"  potencia::"+potencia);

				if(modulo2 == number-1   ){ 
					//System.out.println(x + "true");

					return true;

				}

			}


		}

		//System.out.println(x + " a  false");

		return false ;





	}




	public  long calcularPotenciaModular(long base, long exponente, long modulo){


		long resultado = 1;
		for(int i = 1; i<=exponente; i++){
			resultado = (resultado*base)%modulo;
			
			

		} 
		
		

		return resultado;
	}




	public void buscarPrimoGrande(){


		Generador generadorGrandes= new Generador(543);
		

		while(true){

			long mayorDeMillon=generadorGrandes.aleatorioMillon();


			//System.out.println( mayorDeMillon );
			//System.out.println(MillerRabinPrimalityTest(0.001, mayorDeMillon ));

			if (MillerRabinPrimalityTest(0.001, mayorDeMillon )) 

			{


				//System.out.println(MillerRabinPrimalityTest(0.001, mayorDeMillon ));
				primo1=mayorDeMillon; 

				break;


			}

		}



		while(true){

			long mayorDeMillon=generadorGrandes.aleatorioMillon();


			//System.out.println( mayorDeMillon );
			//System.out.println(MillerRabinPrimalityTest(0.001, mayorDeMillon ));

			if (primo1!=primo2 && MillerRabinPrimalityTest(0.001, mayorDeMillon )) 

			{


				//System.out.println(MillerRabinPrimalityTest(0.001, mayorDeMillon ));
				primo2=mayorDeMillon; 

				break;


			}

		}


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

		Fermat_extendido ferma = new Fermat_extendido();

		Integer [] a= { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89 , 97};

		//for (int i=0;i<10;i++)primo 11906481 primo21726343
		//
		//c cliente 97  z::3291240128983n::695565083571System.out.println(ferma.MillerRabinPrimalityTest(0.001, 1726343 ));


		


	}




}
