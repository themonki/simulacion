


import java.math.BigInteger;
import java.util.*;
import java.io.*;


public class Encriptacion {

	int lengthPrimos =7;
    long z, q, p;
    long f;
    long n, s;
    Fermat_extendido fermat;

   
    public Encriptacion(int tamPrimo) {
    	
    	
    	fermat= new Fermat_extendido();
        this.lengthPrimos = tamPrimo;
        setPrimos(); // llama a la clase fermat  y al metodo buscarprimogrande el cual genera dos primos que son asignados a las variables p y q
        generarClaves();             
    }
    
    public void setPrimos()
    {
    	fermat.buscarPrimoGrande();
        p = fermat.getPrimo1();
        q = fermat.getPrimo2();
       
    }
    
    public void generarClaves()
    {
        //p=3;
        //q=2;
        z = p*q;
       
        
        //1 906 481 * 1 726 343 = 3 291 240 128 983
        // 3 291 240 128 983 
        
       
        
        f = (p-1)*(q-1);
        
        BigInteger fBigInt= new BigInteger(Long.toString(f));
        //f = p.subtract(BigInteger.valueOf(1));
        //f = f.multiply(q.subtract(BigInteger.valueOf(1)));
        // Elegimos un e coprimo de y menor que n
        
        
        BigInteger rand;
        //System.out.println("aqui1"+fBigInt);
        
       
        do {
        	 rand = new BigInteger(42, new Random());// 2^42 es algo mas que dos millones por dos millones lo cual es z
        	
        	
        	 //System.out.println("compare::"+(rand.compareTo(fBigInt)));
        	
        }
            while((rand.compareTo(fBigInt) != -1) ||
		 (rand.gcd(fBigInt).compareTo(BigInteger.valueOf(1)) != 0));
        //System.out.println("aqui2");
        
       
        BigInteger sBigInt = rand.modInverse(fBigInt);
        
        n= rand.longValue();
        f=fBigInt.longValue();
        s=sBigInt.longValue();
        
        
      
        
        
        
    }
    
    
    
    
    
   
    
    
    public String desencripta(BigInteger[] encriptado) {
        /*BigInteger[] desencriptado = new BigInteger[encriptado.length];
        
        for(int i=0; i<desencriptado.length; i++)
            desencriptado[i] = encriptado[i].modPow(s,z);
        
        char[] charArray = new char[desencriptado.length];
        
        for(int i=0; i<charArray.length; i++)
            charArray[i] = (char) (desencriptado[i].intValue());
        
        return(new String(charArray));*/
    	return null; 
    }
    
    public long getP() {return(p);}
    public long getQ() {return(q);}
    public long getF() {return(f);}
    public long getZ() {return(z);}
    public long getN() {return(n);}
    public long getS() {return(s);}
    
    
    public static void main(String[] args) throws IOException {
       

    }
}



