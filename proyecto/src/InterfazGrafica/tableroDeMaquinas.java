package InterfazGrafica;

import java.awt.Color;
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
	
	public tableroDeMaquinas (){
		
		super("SIMULACIÓN DE MAQUINAS");
		this.setLayout(new FlowLayout());
		iniciarPanelMaquinas();
		
		this.setSize(500, 500);
		this.setVisible(true);
		
	}
	
	public void iniciarPanelMaquinas (){
		int filas=10;
		int columnas=5;
		GridLayout grilla= new GridLayout(columnas, filas,10,10);
		
		panelMaquinas= new JPanel(grilla);
		
		
		int numero_maquina=0;
		for(int i= 0;i<columnas;i++){
			for  (int j=0;j<filas;j++){
				
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
		
		GridLayout grilla= new GridLayout(2, 10,10,10);
		panelReparacion= new JPanel();
		
		
		
	}
	
	
	
	

}
