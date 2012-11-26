package InterfazGrafica;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.ScrollPane;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import Utilidades.Button;
import Utilidades.Estilos;

public class tableroDeMaquinas extends JFrame{
	
	JPanel panelMaquinas;
	JPanel panelReparacion;
	JPanel panelDisponibles,panelDatosDeEntrada;
	JScrollPane scrollReparacion,scrollDisponibles; 
	Color color_disponible=new Color(170,225,150); 
	Color color_no_disponible= new Color(225,150,150);
	int filas=5;
	int columnas=10;
	
	JTextField numero_maquinas_extras,tiempo,numero_eventos;
	JTextField numero_reparadores;
	
	JLabel numero_maquinas_extras_label,tiempo_label,eventos_label;
	JLabel numero_reparadores_label;
	
	public tableroDeMaquinas (){
		
		super("Machine Simulation");
		this.setLayout(new FlowLayout());
		iniciarPanelMaquinas();
		iniciarPanelReparacion();
		iniciarPanelDisponibles();
		iniciarDatosDeEntrada();
		
		this.add(new Button("Start Simulation"));
		
		this.setSize(1200, 800);
		this.setVisible(true);
		
	}
	
	public void iniciarPanelMaquinas (){
		
		GridLayout grilla= new GridLayout(filas, columnas,10,10);
		
		panelMaquinas= new JPanel(grilla);
		
		
		int numero_maquina=0;
		for(int i= 0;i<filas;i++){
			for  (int j=0;j<columnas;j++){
				
				JLabel l=new JLabel(""+numero_maquina);
				numero_maquina++;
			
				//l.setBackground(Color.GREEN);
				l.setBackground(color_disponible);
				l.setOpaque(true);
				l.setIcon(new ImageIcon("images/images.gif"));
				l.setBorder(BorderFactory.createEtchedBorder(Color.gray, Color.DARK_GRAY));
				
				panelMaquinas.add(l);
				
				
				
			}
			
		}
		TitledBorder borde =BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(Estilos.colorBorder, Estilos.colorLightBorder), "Machine Set");
		borde.setTitleFont(Estilos.fontTitulo);
		borde.setTitleColor(Estilos.colorTitulo);
		borde.setTitleJustification(TitledBorder.LEFT);
		panelMaquinas.setBorder(borde);
		//panelMaquinas.setBorder(BorderFactory.createEtchedBorder(Color.gray, Color.DARK_GRAY, "Machine Set"));
		panelMaquinas.setVisible(true);
		this.add(panelMaquinas);
		
	
		
	}
	
	public void iniciarPanelReparacion(){
		
		
		
		
		panelReparacion= new JPanel();
		BoxLayout box=new BoxLayout(panelReparacion,BoxLayout.Y_AXIS);
		
		panelReparacion.setLayout(box);
		//GridLayout grilla= new GridLayout();
		//panelReparacion= new JPanel(new FlowLayout());//grilla);
		
		int number =0;
		
		Component[] components = panelMaquinas.getComponents(); 
		
		for (int i=0;i<25;i++){
			

			
			JLabel l =(JLabel) panelMaquinas.getComponent(i);
			JLabel l2= new JLabel(l.getText());
			
			l2.setBackground(color_no_disponible);
			l2.setOpaque(true);
			
			l2.setIcon(l.getIcon());
			l2.setBorder(l.getBorder());
			
			
			panelReparacion.add(l2);
			
			
		};
				
				
				//System.out.println(number);
				
				//panelReparacion.add(l);
			
			
		scrollReparacion= new JScrollPane();
		TitledBorder borde =BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(Estilos.colorBorder, Estilos.colorLightBorder), "Repair Set");
		borde.setTitleFont(Estilos.fontTitulo);
		borde.setTitleColor(Estilos.colorTitulo);
		borde.setTitleJustification(TitledBorder.LEFT);
		scrollReparacion.setBorder(borde);
		
		
		
		scrollReparacion.setViewportView(panelReparacion);
		
		scrollReparacion.setPreferredSize(new Dimension(150, 500));
		
		this.add(scrollReparacion);
		
		
	}
	
	
	public void iniciarPanelDisponibles(){
		
		
		panelDisponibles= new JPanel();
		BoxLayout box=new BoxLayout(panelDisponibles,BoxLayout.Y_AXIS);
		
		panelDisponibles.setLayout(box);
		//GridLayout grilla= new GridLayout();
		//panelReparacion= new JPanel(new FlowLayout());//grilla);
		
		int number =0;
		
		Component[] components = panelDisponibles.getComponents(); 
		
		for (int i=0;i<25;i++){
			

			
			JLabel l =(JLabel) panelMaquinas.getComponent(i);
			JLabel l2= new JLabel(l.getText());
			
			l2.setBackground(color_disponible);
			l2.setOpaque(true);
			
			l2.setIcon(l.getIcon());
			l2.setBorder(l.getBorder());
			
			
			panelDisponibles.add(l2);
			
			
		};
				
				
				//System.out.println(number);
				
				//panelReparacion.add(l);
			
			
		scrollDisponibles= new JScrollPane();
		TitledBorder borde =BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(Estilos.colorBorder, Estilos.colorLightBorder), "Availables");
		borde.setTitleFont(Estilos.fontTitulo);
		borde.setTitleColor(Estilos.colorTitulo);
		borde.setTitleJustification(TitledBorder.LEFT);
		scrollDisponibles.setBorder(borde);
		
		
		
		scrollDisponibles.setViewportView(panelDisponibles);
		
		scrollDisponibles.setPreferredSize(new Dimension(150, 500));
		
		this.add(scrollDisponibles);
		
		
	}
	
	public void iniciarDatosDeEntrada(){
		panelDatosDeEntrada= new JPanel(new GridLayout(4,2,10,10));
		TitledBorder borde =BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(Estilos.colorBorder, Estilos.colorLightBorder), "Incoming Data");
		borde.setTitleFont(Estilos.fontTitulo);
		borde.setTitleColor(Estilos.colorTitulo);
		borde.setTitleJustification(TitledBorder.LEFT);
		panelDatosDeEntrada.setBorder(borde);
		//panelDatosDeEntrada.setPreferredSize(new Dimension(350, 200));
		
		
		
		 numero_maquinas_extras= new JTextField(10);
		 numero_reparadores= new JTextField(10);
		 tiempo= new JTextField(10);
		 numero_eventos= new JTextField(10) ;
		 
		 
		 tiempo_label= new JLabel("Timer Finish");
		 tiempo_label.setFont(Estilos.fontLabels);
		 eventos_label= new JLabel("Events Number");
		 eventos_label.setFont(Estilos.fontLabels);		 
				
		
		 numero_maquinas_extras_label= new JLabel("Number Machine Avalaible");
		 numero_maquinas_extras_label.setFont(Estilos.fontLabels);
		 numero_reparadores_label= new JLabel("Number Repairers");
		 numero_reparadores_label.setFont(Estilos.fontLabels);
		 
		 
		 
		 panelDatosDeEntrada.add(numero_maquinas_extras_label);
		 panelDatosDeEntrada.add(numero_maquinas_extras);
		 panelDatosDeEntrada.add(numero_reparadores_label);
		 panelDatosDeEntrada.add(numero_reparadores);
		 panelDatosDeEntrada.add(tiempo_label);
		 panelDatosDeEntrada.add(tiempo);
		 panelDatosDeEntrada.add(eventos_label);
		 panelDatosDeEntrada.add(numero_eventos);
		 
		 
		 this.add(panelDatosDeEntrada);
		
		
	}
	
	
	
	

}
