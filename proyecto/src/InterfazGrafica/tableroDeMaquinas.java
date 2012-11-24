package InterfazGrafica;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import Utilidades.Estilos;

public class tableroDeMaquinas extends JFrame{
	
	JPanel panelMaquinas;
	JPanel panelReparacion;
	Color color_disponible=new Color(170,225,150); 
	Color color_no_disponible= new Color(225,170,150);
	int filas=5;
	int columnas=10;
	
	public tableroDeMaquinas (){
		
		super("SIMULACIÓN DE MAQUINAS");
		this.setLayout(new FlowLayout());
		iniciarPanelMaquinas();
		iniciarPanelReparacion();
		
		this.setSize(500, 500);
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
		
		
		GridLayout grilla= new GridLayout(filas, columnas,10,10);
		
		panelReparacion= new JPanel(grilla);
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
			System.out.println(i);
			
		};
				
				
				//System.out.println(number);
				
				//panelReparacion.add(l);
			
			
		
		TitledBorder borde =BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(Estilos.colorBorder, Estilos.colorLightBorder), "Repair Set");
		borde.setTitleFont(Estilos.fontTitulo);
		borde.setTitleColor(Estilos.colorTitulo);
		borde.setTitleJustification(TitledBorder.LEFT);
		panelReparacion.setBorder(borde);
		//panelMaquinas.setBorder(BorderF
		
		this.add(panelReparacion);
		
		
	}
	
	
	
	

}
