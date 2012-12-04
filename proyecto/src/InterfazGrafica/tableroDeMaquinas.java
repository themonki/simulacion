package InterfazGrafica;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;


import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;

import Simulacion.Simulacion;
import Utilidades.Button;
import Utilidades.Estilos;

public class tableroDeMaquinas extends JFrame{
	
	Vector<Vector<Object>> ResumenSimulacion;
	
	
	private JPanel panelMaquinas,panelReparador_cola;
	private JPanel panelReparacion;
	private JPanel panelDisponibles,panelDatosDeEntrada;
	private JTabbedPane paneltab;
	private JPanel panelPpal;
	private PanelEstadistica panelEstadistica;
	private JScrollPane scrollReparacion,scrollDisponibles; 
	private Color color_disponible=new Color(170,225,150); 
	private Color color_no_disponible= new Color(225,150,150);
	private Color color_reparador=Color.yellow;
	private int filas=5;
	private int columnas=10;
	
	private JTextField numero_maquinas_extras,tiempo,numero_eventos;
	private JTextField numero_reparadores;
	
	private JLabel numero_maquinas_extras_label,tiempo_label,eventos_label;
	private JLabel numero_reparadores_label;
	
	private Timer time ;
	private JScrollPane scrollReparador;
	private JPanel panelReparador;
	int eventoActual=0;

	private JPanel panelDatosDeEstado;

	private JLabel numero_maquinas_extras_label_estado;

	private JTextField numero_maquinas_extras_estado;

	private JTextField tiempo_estado;

	private JLabel tiempo_estado_label;

	private JTextField cola_reparacion_estado;

	private JLabel cola_reparacion_estado_label;

	private JTextField maquinas_set;

	private JLabel maquinas_set_label;
	
	private tableroDeMaquinas tbmInit;
	
	public tableroDeMaquinas (){
		
		
		
		super("Simulación de Maquinas");
		this.setLayout(new FlowLayout());
		
		panelEstadistica= new PanelEstadistica();
		
		panelPpal= new JPanel();
		paneltab= new JTabbedPane();
		panelReparador_cola= new JPanel();		
		BoxLayout box=new BoxLayout(panelReparador_cola,BoxLayout.Y_AXIS);		
		panelReparador_cola.setLayout(box);	
		
		
		iniciarPanelMaquinas();
		iniciarPanelReparacion();
		iniciarPanelReparador();
		iniciarPanelDisponibles();
		iniciarDatosDeEntrada();
		
		
		Button start= new Button("Iniciar Simulación");
		Button skip= new Button("Skip ");
		skip.setEnabled(false);
		Button restart = new Button("Reiniciar");
		restart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				tbmInit.dispose();
				tbmInit=null;
				
				tbmInit = new tableroDeMaquinas();
				tbmInit.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);				
				
			}
		});
		
		JPanel jpButtons = new JPanel(new GridLayout(3,1,10,10));
		start.addActionListener(new manejador());
		jpButtons.add(start);
		jpButtons.add(skip);
		jpButtons.add(restart);
		
		panelPpal.add(jpButtons);
		
		iniciarDatosDeEstado();
		
		paneltab.add(panelPpal,"Simulación");
		paneltab.add(panelEstadistica,"Estadística");
		this.add(paneltab);
		
		panelPpal.setPreferredSize(new Dimension(1180, 700));
		
		
		
		
		this.setSize(1220, 800);
		this.setVisible(true);
		
		
		
		
		time= new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
				correrEvento(ResumenSimulacion.get(eventoActual));
				eventoActual++;
				}
				catch(Exception ef){
					
					time.stop();
				}
			}
		});
		
		this.tbmInit=this;
		
		
		
	}
	
	
	protected void correrEvento(Vector<Object> vector) {
		
		String idMaquina=(String) vector.get(0);
		String moverA=(String) vector.get(1);
		Integer reloj=(Integer) vector.get(2);
		
		if(moverA.equals("irAReparador"))
		{
			
			 agregarPanel(panelReparador,idMaquina,color_reparador);
				
			
			
			
		}
		else if(moverA.equals("irAFabrica")){
			
			agregarPanel(panelMaquinas,idMaquina,color_disponible);
		}
		else if(moverA.equals("irAColaAdicional")){
			agregarPanel(panelDisponibles,idMaquina,color_disponible);
			
			
		}
		else if(moverA.equals("irAColaReparacion")){
			
			agregarPanel(panelReparacion,idMaquina,color_no_disponible);
		}
		
		
		
		 numero_maquinas_extras_estado.setText(""+panelDisponibles.getComponentCount());		
		 tiempo_estado.setText(""+reloj) ;		 
		 cola_reparacion_estado.setText(""+panelReparacion.getComponentCount());
		 maquinas_set.setText(""+panelMaquinas.getComponentCount());
		
	}


	public void iniciarPanelMaquinas (){
		
		GridLayout grilla= new GridLayout(filas, columnas,10,10);
		
		panelMaquinas= new JPanel();
		
		
		int numero_maquina=0;
		for(int i= 0;i<filas;i++){
			for  (int j=0;j<columnas;j++){
				
				JLabel l=new JLabel(""+numero_maquina);
				
			
				//l.setBackground(Color.GREEN);
				l.setBackground(color_disponible);
				l.setOpaque(true);
				l.setIcon(new ImageIcon("images/images.gif"));
				l.setBorder(BorderFactory.createEtchedBorder(Color.gray, Color.DARK_GRAY));
				//l.setHorizontalTextPosition(JLabel.CENTER);
				//l.setPreferredSize(new Dimension(75, 80));
				l.setName(""+numero_maquina);
				panelMaquinas.add(l);
				
				numero_maquina++;
				
			}
			
		}
		TitledBorder borde =BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(Estilos.colorBorder, Estilos.colorLightBorder), "Maquinas Funcionando");
		borde.setTitleFont(Estilos.fontTitulo);
		borde.setTitleColor(Estilos.colorTitulo);
		borde.setTitleJustification(TitledBorder.LEFT);
		panelMaquinas.setBorder(borde);
		//panelMaquinas.setBorder(BorderFactory.createEtchedBorder(Color.gray, Color.DARK_GRAY, "Machine Set"));
		panelMaquinas.setPreferredSize(new Dimension(850, 500));
		panelMaquinas.setBackground(Color.WHITE);
		panelMaquinas.setVisible(true);
		panelPpal.add(panelMaquinas);
		
	
		
	}
	
	//---------------------PANEL DE REPARACION-------------------------
	
	
	
	public void iniciarPanelReparacion(){
		
		
		
		
		panelReparacion= new JPanel();
		BoxLayout box=new BoxLayout(panelReparacion,BoxLayout.Y_AXIS);		
		panelReparacion.setLayout(box);			
		scrollReparacion= new JScrollPane();
		TitledBorder borde =BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(Estilos.colorBorder, Estilos.colorLightBorder), "Cola de  Re.");
		borde.setTitleFont(Estilos.fontTitulo);
		borde.setTitleColor(Estilos.colorTitulo);
		borde.setTitleJustification(TitledBorder.LEFT);
		scrollReparacion.setBorder(borde);
		
		
		
		scrollReparacion.setViewportView(panelReparacion);
		
		scrollReparacion.setPreferredSize(new Dimension(150, 300));
		
		panelReparador_cola.add(scrollReparacion);
		
		
	}
	///--------------------------------panel reparador
	
	public void iniciarPanelReparador(){
		
		
		
		
		panelReparador= new JPanel();
		BoxLayout box=new BoxLayout(panelReparador,BoxLayout.Y_AXIS);		
		panelReparador.setLayout(box);			
		scrollReparador= new JScrollPane();
		TitledBorder borde =BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(Estilos.colorBorder, Estilos.colorLightBorder), "Reparador");
		borde.setTitleFont(Estilos.fontTitulo);
		borde.setTitleColor(Estilos.colorTitulo);
		borde.setTitleJustification(TitledBorder.LEFT);
		scrollReparador.setBorder(borde);
		
		
		
		scrollReparador.setViewportView(panelReparador);
		
		scrollReparador.setPreferredSize(new Dimension(150, 200));
		
		panelReparador_cola.add(scrollReparador);
		
		panelPpal.add(panelReparador_cola);
		
	}
	
	//---------------------  ------------------------
	public JLabel buscarJLabelMaquina(String idMaquina){
		String id=""+idMaquina;
		/*Busca en los tres panels el objeto label que representa la maquina al pasar por referencia el objeto a otra variable que en efecto es la misma
		 * se borra del panel o container donde estaba anteriormente*/
		for (int i=0;i<panelMaquinas.getComponentCount();i++){
			
			JLabel temp =(JLabel) panelMaquinas.getComponent(i);
			if (temp.getName().equals(id)) return temp;
		}
		
		
		for (int i=0;i<panelDisponibles.getComponentCount();i++){
			
			JLabel temp =(JLabel) panelDisponibles.getComponent(i);
			if (temp.getName().equals(id)) return temp;
		}
		
		for (int i=0;i<panelReparacion.getComponentCount();i++){
			
			JLabel temp =(JLabel) panelReparacion.getComponent(i);
			if (temp.getName().equals(id)) return temp;
		}
		
		for (int i=0;i<panelReparador.getComponentCount();i++){
			
			JLabel temp =(JLabel) panelReparador.getComponent(i);
			if (temp.getName().equals(id)) return temp;
		}
		
		
		
		
		
		return null;
		
		
	}
	
	public void agregarPanel(JPanel panel ,String idMaquina,Color color){
		
	

			JLabel l=buscarJLabelMaquina(idMaquina);
			
			if(l==null){
				
				l = new JLabel(idMaquina);
				l.setName(idMaquina);
				System.out.println(idMaquina);
				
			}
		
			//l.setBackground(Color.GREEN);
			l.setBackground(color);
			l.setOpaque(true);
			l.setIcon(new ImageIcon("images/images.gif"));
			l.setBorder(BorderFactory.createEtchedBorder(Color.gray, Color.DARK_GRAY));
			//l.setHorizontalTextPosition(JLabel.CENTER);
			
			
			panel.add(l);						
		
		panel.updateUI();
		
		panelDisponibles.updateUI();
		panelReparacion.updateUI();
		panelMaquinas.updateUI();
		panelReparador.updateUI();
		
	}
	
	
	public void borrarPanel(JPanel panel ,int cantidad){
		
		
		for (int i=0;i<cantidad;i++){
					
			panel.remove(0);						
		};
		panel.updateUI();
		
	}
	
	//-----------------------------------------------------
	
	public void iniciarPanelDisponibles(){
		
		
		panelDisponibles= new JPanel();
		BoxLayout box=new BoxLayout(panelDisponibles,BoxLayout.Y_AXIS);
		
		panelDisponibles.setLayout(box);
		
			
			
		scrollDisponibles= new JScrollPane();
		TitledBorder borde =BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(Estilos.colorBorder, Estilos.colorLightBorder), "Disponibles");
		borde.setTitleFont(Estilos.fontTitulo);
		borde.setTitleColor(Estilos.colorTitulo);
		borde.setTitleJustification(TitledBorder.LEFT);
		scrollDisponibles.setBorder(borde);
		
		
		
		scrollDisponibles.setViewportView(panelDisponibles);
		
		scrollDisponibles.setPreferredSize(new Dimension(150, 500));
		
		panelPpal.add(scrollDisponibles);
		
		
	}
	
	public void iniciarDatosDeEntrada(){
		panelDatosDeEntrada= new JPanel(new GridLayout(4,2,10,10));
		TitledBorder borde =BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(Estilos.colorBorder, Estilos.colorLightBorder), "Datos de Entrada");
		borde.setTitleFont(Estilos.fontTitulo);
		borde.setTitleColor(Estilos.colorTitulo);
		borde.setTitleJustification(TitledBorder.LEFT);
		panelDatosDeEntrada.setBorder(borde);
		//panelDatosDeEntrada.setPreferredSize(new Dimension(350, 200));
		
		
		
		 numero_maquinas_extras= new JTextField(10);
		 numero_reparadores= new JTextField(10);
		 tiempo= new JTextField(10);
		 numero_eventos= new JTextField(10) ;
		 
		 
		 tiempo_label= new JLabel("Tiempo de Finalización");
		 tiempo_label.setFont(Estilos.fontLabels);
		 eventos_label= new JLabel("Events Number");
		 eventos_label.setFont(Estilos.fontLabels);		 
				
		
		 numero_maquinas_extras_label= new JLabel("Numero de Disponibles");
		 numero_maquinas_extras_label.setFont(Estilos.fontLabels);
		 numero_reparadores_label= new JLabel("Numero de Reparadores");
		 numero_reparadores_label.setFont(Estilos.fontLabels);
		 
		 
		 
		 panelDatosDeEntrada.add(numero_maquinas_extras_label);
		 panelDatosDeEntrada.add(numero_maquinas_extras);
		 panelDatosDeEntrada.add(numero_reparadores_label);
		 panelDatosDeEntrada.add(numero_reparadores);
		 panelDatosDeEntrada.add(tiempo_label);
		 panelDatosDeEntrada.add(tiempo);
		 //panelDatosDeEntrada.add(eventos_label);
		 //panelDatosDeEntrada.add(numero_eventos);
		 
		 
		 panelPpal.add(panelDatosDeEntrada);
		
		
	}
	
	//-----------------------INICIAR DATOS DE ESTADO
	
	public void iniciarDatosDeEstado(){
		panelDatosDeEstado= new JPanel(new GridLayout(5,2,10,10));
		TitledBorder borde =BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(Estilos.colorBorder, Estilos.colorLightBorder), "Estado");
		borde.setTitleFont(Estilos.fontTitulo);
		borde.setTitleColor(Estilos.colorTitulo);
		borde.setTitleJustification(TitledBorder.LEFT);
		panelDatosDeEstado.setBorder(borde);
		//panelDatosDeEntrada.setPreferredSize(new Dimension(350, 200));
		
		
		
		 numero_maquinas_extras_estado= new JTextField(10);
		 numero_maquinas_extras_estado.setEditable(false);
		 tiempo_estado= new JTextField(10);
		 tiempo_estado.setEditable(false);
		 cola_reparacion_estado= new JTextField(10);
		 cola_reparacion_estado.setEditable(false);
		 maquinas_set= new JTextField(10);
		 maquinas_set.setEditable(false);
		 				
		
		 numero_maquinas_extras_label_estado= new JLabel("Disponibles:");
		 numero_maquinas_extras_label_estado.setFont(Estilos.fontLabels);
		 
		 tiempo_estado_label= new JLabel("Tiempo Actual:");
		 tiempo_estado_label.setFont(Estilos.fontLabels);
		 
		 cola_reparacion_estado_label = new JLabel("Cola de Reparación:");
		 cola_reparacion_estado_label.setFont(Estilos.fontLabels);
		 
		 maquinas_set_label= new JLabel("Maquinas Funcionando:");
		 maquinas_set_label.setFont(Estilos.fontLabels);
		 
		 
		 panelDatosDeEstado.add(numero_maquinas_extras_label_estado);
		 panelDatosDeEstado.add(numero_maquinas_extras_estado);
		 
		 panelDatosDeEstado.add(tiempo_estado_label);
		 
		 panelDatosDeEstado.add(tiempo_estado);
		 
		 panelDatosDeEstado.add(cola_reparacion_estado_label);
		 
		 panelDatosDeEstado.add(cola_reparacion_estado);
		 
		 
		 panelDatosDeEstado.add(maquinas_set_label);
		 panelDatosDeEstado.add(maquinas_set);
		 
		 
		 Button acelerar= new Button("+");
		 acelerar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (time.getDelay()-100>0)
				time.setDelay(time.getDelay()-100);
				
				
			}
		});
		
	
		 /*panelDatosDeEstado.add(acelerar);
		 panelDatosDeEstado.add(disminuir);*/
		 
		 
		 //panelDatosDeEntrada.add(eventos_label);
		 //panelDatosDeEntrada.add(numero_eventos);
		 
		 
		 panelPpal.add(panelDatosDeEstado);
		 panelPpal.add(new JLabel("SPEED::"));
		 panelPpal.add(acelerar);
	
		
		
	}
	
	
	//---------------------------------------------------
	
	
	public String validar() {
		
		validador valida= new validador();
		String mensaje="" ;
		mensaje+=valida.validar("Number Machine Avalaible",numero_maquinas_extras.getText(),true ,1, 50);
		
		mensaje+=valida.validar("Number Repairers",numero_reparadores.getText(),true ,1, 50);
	
		mensaje+=valida.validar("Timer Finish",tiempo.getText(),true ,10, 100000);
		
		//mensaje+=valida.validar("Events Number",numero_eventos.getText(),true ,10, 100);
		
		
		if (!mensaje.equals("")){
			JOptionPane.showMessageDialog(this,mensaje);
			
			
		}
		
		return mensaje;
		
		
		
	}
	
	private class manejador implements ActionListener {
		
		
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(validar().equals(""))
		{
			
			int numMachAva = Integer.parseInt(numero_maquinas_extras.getText());
			int numRepaAva = Integer.parseInt(numero_reparadores.getText());
			int maxTimerFinish = Integer.parseInt(tiempo.getText());
			
		//agregarPanel(panelDisponibles,Integer.parseInt(numero_maquinas_extras.getText()),color_disponible);
		//agregarPanel(panelReparacion,49,color_no_disponible);
		Simulacion s = new Simulacion(12345, 50,numMachAva,numRepaAva,maxTimerFinish);
		s.starSimulacion();
		
		
		//---------------------------------------------------------------------------------------
		
		for (int i=1;i<=numMachAva;i++){
			
			//String f= s.getMaquinasEnColaReparacion().peek();
			agregarPanel(panelMaquinas,""+(49+i) , color_disponible);
			//System.out.println(f);
			
		}
		
		
		
		
		
		//---------------------------------------------------------------------------------------
		int count_size=s.getMaquinasAdicionalesCalentamiento().size();
		
		for (int i=0;i<count_size;i++)
		{
			
			agregarPanel(panelDisponibles, s.getMaquinasAdicionalesCalentamiento().poll(), color_disponible);
			
			
			
		}
		
		
		for (int i=0;i<s.getMaquinasConReparadorCalentamiento().size();i++){
			
			agregarPanel(panelReparador, s.getMaquinasConReparadorCalentamiento().get(i), color_reparador);
			
			
		}
		
		System.out.println("Caliente Reparador:::"+ s.getMaquinasConReparadorCalentamiento());
		
		
		count_size=s.getMaquinasEnColaRepacionCalentamiento().size();
		for (int i=0;i<count_size;i++){
			
			//String f= s.getMaquinasEnColaReparacion().peek();
			agregarPanel(panelReparacion, s.getMaquinasEnColaRepacionCalentamiento().poll(), color_no_disponible);
			//System.out.println(f);
			
		}
		
		
		
		
		//  48
		
	
		
		
		//--------------------------------------------------------------------------------------
		
		ResumenSimulacion=s.getResumenSimulacion();
		
		System.out.println(ResumenSimulacion);
		
		int costo = maxTimerFinish*(numMachAva + 10*numRepaAva);
		
		panelEstadistica.init(s.getDesempenioTotal(),numMachAva,numRepaAva,s.getDesempenioSumReparadores_ocupacion_total(),costo,s.getDesempenioColaPromedio_total(),s.getDesempenioColaReparador());
		panelEstadistica.updateUI();
		
		time.start();
		
		}
		
		else {
			
			//agregarPanel(panelDisponibles,49,color_disponible);
			
			//borrarPanel(panelMaquinas, 1);
		}
		
		//time.start();
		
	}
	
	}

	
}
