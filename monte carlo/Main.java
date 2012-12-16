import java.io.*;
import java.util.*;

public class Main {
	static StringTokenizer _stk;
	static BufferedReader input;
	static String readln() throws Exception {
		String l = input.readLine();
		if(l!=null) _stk = new StringTokenizer(l, " ");
		return l;
	}
	static String next() {  return _stk.nextToken(); };
	static int nextInt() { return Integer.parseInt(next()); }
    
    static int calcularPotenciaModular(int base, int exponente, int modulo){
       
        int resultado = 1;
        for(int i = 1; i<=exponente; i++){
            resultado = (resultado*base)%modulo;
        
        } 
        
        return resultado;
    }
    public static void main(String[] args) throws Exception{
		input = new BufferedReader(new InputStreamReader(System.in));
        readln();
        int a, t, n;
        a = nextInt(); //Base --- a
        t = nextInt(); //Potencia  --- t		
        n = nextInt(); //modulo ---- n
        System.out.println("Potencia "+a+" "+t+" "+n); 
        System.out.println("Potencia "+a+" "+t+" "+n+" " + calcularPotenciaModular(a,t,n));
	}

}
