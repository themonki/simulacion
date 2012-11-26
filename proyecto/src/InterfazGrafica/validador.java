package InterfazGrafica;

public class validador {
	
	public validador(){}
	
	public String validar(String campo,String dato,boolean isnumber,int infLimit, int supLimit){
		
		String mensaje = "";
		if (isnumber){
			
			try {
			int numero= Integer.parseInt(dato);
			
			if(numero >supLimit || numero<infLimit )
			{
				mensaje= "El dato esta fuera de rango "+ infLimit+"-"+ supLimit +" Campo: "+campo+"\n";
				
			}
			}
			catch(Exception e ){
				mensaje = "El tipo de dato debe ser  numerico: "+dato+ " Campo: "+campo+"\n";
				
			}
			
			
			
			
		}
		else{
			
			if (dato!=null && dato.equals("")){
				int numero= dato.length();
				if(numero >supLimit || numero<infLimit )
				{
					mensaje= "No puede esceder los  "+ infLimit+"-"+ supLimit +" Caracteres en el Campo: "+campo+"\n";
					
				}
			}
			else { mensaje = "campo vacio: "+campo+"\n" ;}
			
			
		}
		
		
		return mensaje;
	}

}
