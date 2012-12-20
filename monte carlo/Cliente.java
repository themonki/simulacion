import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.Vector;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
 class Cliente extends JFrame {
   private JTextField campoIntroducir;
   private JTextArea areaPantalla;
   private ObjectOutputStream salida;
   private ObjectInputStream entrada;
   private String mensaje = "";
   private String mensaje_envio="";
   private String servidorChat;
   private Socket cliente;
   
   int s_f_recibido;

   long n ;// n de la formula de encriptar 
   
   long z; // f de la formula de encriptacion 
   
   private Encriptacion encripta;
  
 
   // inicializar servidorChat y configurar GUI
   public void setEncripta(Encriptacion encripta) {
	this.encripta = encripta;
}

public Cliente( String host )
   {
      super( "Cliente" );
 
      servidorChat = host; // establecer el servidor al que se va a conectar este cliente
 
      Container contenedor = getContentPane();
 
      // crear campoIntroducir y registrar componente de escucha
      campoIntroducir = new JTextField();
      campoIntroducir.setEditable( false );
      campoIntroducir.addActionListener(
         new ActionListener() {
 
            // enviar mensaje al servidor
            public void actionPerformed( ActionEvent evento )
            {
               enviarDatos( evento.getActionCommand() );
               campoIntroducir.setText( "" );
            }
         } 
      );
 
      contenedor.add( campoIntroducir, BorderLayout.NORTH );
 
      // crear areaPantalla
      areaPantalla = new JTextArea();
      contenedor.add( new JScrollPane( areaPantalla ),
         BorderLayout.CENTER );
 
      setSize( 300, 150 );
      setVisible( true );
 
   } // fin del constructor de Cliente
 
   // conectarse al servidor y procesar mensajes del servidor
   public void ejecutarCliente()
   {
      // conectarse al servidor, obtener flujos, procesar la conexión
      try {
         conectarAServidor(); // Paso 1: crear un socket para realizar la conexión
         obtenerFlujos();      // Paso 2: obtener los flujos de entrada y salida
         procesarConexion(); // Paso 3: procesar la conexión
      }
 
      // el servidor cerró la conexión
      catch ( EOFException excepcionEOF ) {
         System.err.println( "El cliente termino la conexión" );
      }
 
      // procesar los problemas que pueden ocurrir al comunicarse con el servidor
      catch ( IOException excepcionES ) {
         excepcionES.printStackTrace();
      }
 
      finally {
         cerrarConexion(); // Paso 4: cerrar la conexión
      }
 
   } // fin del método ejecutarCliente
 
   // conectarse al servidor
   private void conectarAServidor() throws IOException
   {     
      mostrarMensaje( "Intentando realizar conexión\n" );
 
      // crear Socket para realizar la conexión con el servidor
      cliente = new Socket( InetAddress.getByName( servidorChat ), 12345 );
 
      // mostrar la información de la conexión
      mostrarMensaje( "Conectado a: " +
         cliente.getInetAddress().getHostName() );
   }
 
   // obtener flujos para enviar y recibir datos
   private void obtenerFlujos() throws IOException
   {
      // establecer flujo de salida para los objetos
      salida = new ObjectOutputStream( cliente.getOutputStream() );     
      salida.flush(); // vacíar búfer de salida para enviar información de encabezado
 
      // establecer flujo de entrada para los objetos
      entrada = new ObjectInputStream( cliente.getInputStream() );
 
      mostrarMensaje( "\nSe recibieron los flujos de E/S\n" );
   }
 
   // procesar la conexión con el servidor
   private void procesarConexion() throws IOException
   {
      // habilitar campoIntroducir para que el usuario del cliente pueda enviar mensajes
      establecerCampoTextoEditable( true );
 
      do { // procesar mensajes enviados del servidor
 
         // leer mensaje y mostrarlo en pantalla
         try {
            mensaje = ( String ) entrada.readObject();
            
            System.out.println("recibido"+mensaje);
            if(s_f_recibido==1){
            	
            System.out.println("recibido    N "+mensaje);
            n=Long.parseLong(mensaje);
            
            s_f_recibido++;
            }
            else if(s_f_recibido==2 ){
            	
            	z=Long.parseLong(mensaje);
            	
            	 System.out.println("recibido   f  "+mensaje);
            	 
            	 
            	 long a= mensaje_envio.hashCode();
            	 
            	 //c= a^n mod z
            	 
            	 System.out.println("c cliente "+a  +"  z::"+z +"n::" +n );
          
            	 BigInteger aBig= new BigInteger(""+a);
            	 BigInteger nBig= new BigInteger(""+n);
            	 BigInteger zBig= new BigInteger(""+z);
            	 
            	 
            	 BigInteger  c= calcularPotenciaModular(aBig, nBig, zBig);
            	 
            	 System.out.println("nunca pase eee" +c);
            	 //salida.writeObject("");
                 //salida.flush();
            	 
            	 
            	 s_f_recibido=0;
            }
            
            mostrarMensaje( "\n" + mensaje );
         }
 
         // atrapar los problemas que pueden ocurrir al leer del servidor
         catch ( ClassNotFoundException excepcionClaseNoEncontrada ) {
            mostrarMensaje( "\nSe recibió un objeto de tipo desconocido" );
         }
 
      } while ( !mensaje.equals( "SERVIDOR>>> TERMINAR" ) );
 
   } // fin del método procesarConexion
 
   // cerrar flujos y socket
   private void cerrarConexion()
   {
      mostrarMensaje( "\nCerrando conexión" );
      establecerCampoTextoEditable( false ); // deshabilitar campoIntroducir
 
      try {
         salida.close();
         entrada.close();
         cliente.close();
      }
      catch( IOException excepcionES ) {
         excepcionES.printStackTrace();
      }
   }
 
   // enviar mensaje al servidor
   private void enviarDatos( String mensaje )
   {
      // enviar objeto al servidor
      try {
    	  
    	  
    	 
    	 mensaje_envio=mensaje;
    	 
    	 if(s_f_recibido==0){
         salida.writeObject( "mandame");
         salida.flush();
         s_f_recibido++;
         
    	 }
    	 
         
         
         
         mostrarMensaje( "\nCLIENTE>>> " + mensaje );
      }
 
      // procesar los problemas que pueden ocurrir al enviar el objeto
      catch ( IOException excepcionES ) {
         areaPantalla.append( "\nError al escribir el objeto" );
      }
   }
 
   // método utilitario que es llamado desde otros subprocesos para manipular a
   // areaPantalla en el subproceso despachador de eventos
   private void mostrarMensaje( final String mensajeAMostrar )
   {
      // mostrar mensaje del subproceso de ejecución de la GUI
      SwingUtilities.invokeLater(
         new Runnable() {  // clase interna para asegurar que la GUI se actualice apropiadamente
 
            public void run() // actualiza areaPantalla
            {
               areaPantalla.append( mensajeAMostrar );
               areaPantalla.setCaretPosition(
                  areaPantalla.getText().length() );
            }
 
         }  // fin de la clase interna
 
      ); // fin de la llamada a SwingUtilities.invokeLater
   }
 
   // método utilitario que es llamado desde otros subprocesos para manipular a
   // campoIntroducir en el subproceso despachador de eventos
   private void establecerCampoTextoEditable( final boolean editable )
   {
      // mostrar mensaje del subproceso de ejecución de la GUI
      SwingUtilities.invokeLater(
         new Runnable() {  // clase interna para asegurar que la GUI se actualice apropiadamente
 
            public void run()  // establece la capacidad de modificar campoIntroducir
            {
               campoIntroducir.setEditable( editable );
            }
 
         }  // fin de la clase interna
 
      ); // fin de la llamada a SwingUtilities.invokeLater
   }
   
   
   
   
   
   
   public long encripta(String mensaje)
   {
   	
	   Fermat_extendido fermat_extendido = new Fermat_extendido();
   	
	   long hash = mensaje.hashCode();
	   
	   //long codigo = fermat_extendido.calcularPotenciaModular(hash, n, z);
	   
	   
      /* int i;
       byte[] temp = new byte[1];
       byte[] digitos = mensaje.getBytes();
       BigInteger[] bigdigitos = new BigInteger[digitos.length];
       
       for(i=0; i<bigdigitos.length;i++){
           temp[0] = digitos[i];
           bigdigitos[i] = new BigInteger(temp);
       }
       
       BigInteger[] encriptado = new BigInteger[bigdigitos.length];
       
       for(i=0; i<bigdigitos.length; i++)
           encriptado[i] = bigdigitos[i].modPow(n,z);
       
       return(encriptado);*/
   	return 1;
   }
 
   public static void main( String args[] )
   {
      //JFrame.setDefaultLookAndFeelDecorated(true);
      Cliente aplicacion;
 
      if ( args.length == 0 )
         aplicacion = new Cliente( "127.0.0.1" );
      else
         aplicacion = new Cliente( args[ 0 ] );
 
      aplicacion.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      aplicacion.ejecutarCliente();
   }
   
   public  BigInteger calcularPotenciaModular(BigInteger base, BigInteger exponente, BigInteger modulo){


	   BigInteger resultado= new BigInteger("1");
	   
	   BigInteger resta= BigInteger.valueOf(1);
	   BigInteger cero= BigInteger.valueOf(1);
		  
		
		for(; !exponente.equals(cero); exponente=exponente.subtract(resta)){
			
			resultado = (resultado.multiply(base)).mod(modulo) ;
			
			
			
			

		} 
		
		System.out.println("acabe");
		
		

		return resultado;
	}
 
} // fin de la clase Cliente
