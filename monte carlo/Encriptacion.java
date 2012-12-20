


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
        System.out.println("valor de p::"+p);
        System.out.println("valor de q::"+q);
        System.out.println("valor de z::"+z);
        
        //1 906 481 * 1 726 343 = 3 291 240 128 983
        // 3 291 240 128 983 
        
       
        
        f = (p-1)*(q-1);
        
        BigInteger fBigInt= new BigInteger(Long.toString(f));
        //f = p.subtract(BigInteger.valueOf(1));
        //f = f.multiply(q.subtract(BigInteger.valueOf(1)));
        // Elegimos un e coprimo de y menor que n
        
        
        BigInteger rand;
        System.out.println("aqui1"+fBigInt);
        
       
        do {
        	 rand = new BigInteger(21, new Random());// 2^21 es algo mas que dos millones
        	
        	
        	 //System.out.println("compare::"+(rand.compareTo(fBigInt)));
        	
        }
            while((rand.compareTo(fBigInt) != -1) ||
		 (rand.gcd(fBigInt).compareTo(BigInteger.valueOf(1)) != 0));
        System.out.println("aqui2");
        
       
        BigInteger sBigInt = rand.modInverse(fBigInt);
        
        n= rand.longValue();
        f=fBigInt.longValue();
        s=sBigInt.longValue();
        
        
        System.out.println("valor de s::"+s);
        System.out.println("valor de f::"+f);
        System.out.println("valor de n::"+n);
        
        
        
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
        if(args.length != 1) {
            System.out.println("Sintaxis: java RSA [tamaño de los primos]");
            System.out.println("por ejemplo: java RSA 512");
            args = new String[1];
            args[0]="1024";
        }
        int tamPrimo = Integer.parseInt(args[0]);
        Encriptacion rsa = new Encriptacion(tamPrimo);

        /*System.out.println("Tam Clave: ["+ tamPrimo + "]n");

        System.out.println("p: [" + rsa.damep().toString(16).toUpperCase() + "]");
        System.out.println("q: [" + rsa.dameq().toString(16).toUpperCase() + "]n");

        System.out.println("Clave publica (n,e)");
        System.out.println("n: [" + rsa.damen().toString(16).toUpperCase() + "]");
        System.out.println("e: [" + rsa.damee().toString(16).toUpperCase() + "]n");

        System.out.println("Clave publica (n,d)");
        System.out.println("n: [" + rsa.damen().toString(16).toUpperCase() + "]");
        System.out.println("d: [" + rsa.damed().toString(16).toUpperCase() + "]n");*/

        /*System.out.println("Texto a encriptar: ");
        String textoPlano = ( new BufferedReader(new
                        InputStreamReader(System.in))).readLine();
        
        BigInteger[] textoCifrado = rsa.encripta(textoPlano);
        
        System.out.println("nTexto encriptado: [");
        for(int i=0; i<textoCifrado.length; i++) {
            System.out.print(textoCifrado[i].toString(16).toUpperCase());
            if(i != textoCifrado.length-1)
                System.out.println("");
        }
        System.out.println("]n");
        
        String recuperarTextoPlano = rsa.desencripta(textoCifrado);
        System.out.println("Texto desencritado: ["+ recuperarTextoPlano +"]");*/
    }
}



