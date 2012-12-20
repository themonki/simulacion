

import java.math.BigInteger;
import java.util.*;
import java.io.*;

/**
 *
 * @author Algunas personas
 */
public class RSA {

    int tamPrimo;
    BigInteger n, q, p;
    BigInteger totient;
    BigInteger e, d;

    /** Constructor de la clase RSA */
    public RSA(int tamPrimo) {
        this.tamPrimo = tamPrimo;
        generaPrimos();             //Genera p y q
        generaClaves();             //Genera e y d
    }
    
    public void generaPrimos()
    {
        p = new BigInteger(tamPrimo, 10, new Random());
        do q = new BigInteger(tamPrimo, 10, new Random());
            while(q.compareTo(p)==0);
    }
    
    public void generaClaves()
    {
        // n = p * q
        n = p.multiply(q);
        // toltient = (p-1)*(q-1)
        totient = p.subtract(BigInteger.valueOf(1));
        totient = totient.multiply(q.subtract(BigInteger.valueOf(1)));
        // Elegimos un e coprimo de y menor que n
        do e = new BigInteger(2 * tamPrimo, new Random());
            while((e.compareTo(totient) != -1) ||
		 (e.gcd(totient).compareTo(BigInteger.valueOf(1)) != 0));
        // d = e^1 mod totient
        d = e.modInverse(totient);
    }
    
    /**
     * Encripta el texto usando la clave pública
     *
     * @param   mensaje     Ristra que contiene el mensaje a encriptar
     * @return   El mensaje cifrado como un vector de BigIntegers
     */
    public BigInteger[] encripta(String mensaje)
    {
        int i;
        byte[] temp = new byte[1];
        byte[] digitos = mensaje.getBytes();
        BigInteger[] bigdigitos = new BigInteger[digitos.length];
        
        for(i=0; i<bigdigitos.length;i++){
            temp[0] = digitos[i];
            bigdigitos[i] = new BigInteger(temp);
        }
        
        BigInteger[] encriptado = new BigInteger[bigdigitos.length];
        
        for(i=0; i<bigdigitos.length; i++)
            encriptado[i] = bigdigitos[i].modPow(e,n);
        
        return(encriptado);
    }
    
    /**
     * Desencripta el texto cifrado usando la clave privada
     *
     * @param   encriptado       Array de objetos BigInteger que contiene el texto cifrado
     *                           que será desencriptado
     * @return  The decrypted plaintext
     */
    public String desencripta(BigInteger[] encriptado) {
        BigInteger[] desencriptado = new BigInteger[encriptado.length];
        
        for(int i=0; i<desencriptado.length; i++)
            desencriptado[i] = encriptado[i].modPow(d,n);
        
        char[] charArray = new char[desencriptado.length];
        
        for(int i=0; i<charArray.length; i++)
            charArray[i] = (char) (desencriptado[i].intValue());
        
        return(new String(charArray));
    }
    
    public BigInteger damep() {return(p);}
    public BigInteger dameq() {return(q);}
    public BigInteger dametotient() {return(totient);}
    public BigInteger damen() {return(n);}
    public BigInteger damee() {return(e);}
    public BigInteger damed() {return(d);}
    
    
    
    public static void main(String[] args) throws IOException {
        if(args.length != 1) {
            System.out.println("Sintaxis: java RSA [tamaño de los primos]");
            System.out.println("por ejemplo: java RSA 512");
            args = new String[1];
            args[0]="1024";
        }
        int tamPrimo = Integer.parseInt(args[0]);
        RSA rsa = new RSA(tamPrimo);

        System.out.println("Tam Clave: ["+ tamPrimo + "]n");

        System.out.println("p: [" + rsa.damep().toString(16).toUpperCase() + "]");
        System.out.println("q: [" + rsa.dameq().toString(16).toUpperCase() + "]n");

        System.out.println("Clave publica (n,e)");
        System.out.println("n: [" + rsa.damen().toString(16).toUpperCase() + "]");
        System.out.println("e: [" + rsa.damee().toString(16).toUpperCase() + "]n");

        System.out.println("Clave publica (n,d)");
        System.out.println("n: [" + rsa.damen().toString(16).toUpperCase() + "]");
        System.out.println("d: [" + rsa.damed().toString(16).toUpperCase() + "]n");

        System.out.println("Texto a encriptar: ");
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
        System.out.println("Texto desencritado: ["+ recuperarTextoPlano +"]");
    }
}
