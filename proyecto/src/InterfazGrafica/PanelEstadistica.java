/**
 * 
 */
package InterfazGrafica;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import Utilidades.Estilos;

/**
 *
 *
 */
public class PanelEstadistica extends JPanel {
	
	public PanelEstadistica(Vector<Object> obj) {
		// TODO Auto-generated constructor stub
		super();
		
		
		//El vector de objetos debe de venir organizado
		/*
		int index = 0;
		int numMachAva = (Integer)(obj.get(index));
		index++;		
		int numRep = (Integer)(obj.get(index));;
		index++;
		int maxQueRep = (Integer)(obj.get(index));;
		index++;
		int maxQueMachAva = (Integer)(obj.get(index));;
		index++;
		double costValue = (Double)(obj.get(index));;
		index++;
		double percValue = (Double)(obj.get(index));;
		index++;
		*/
		
		int numMachAva = 1;
		int numRep = 1;
		int maxQueRep = 1;
		int maxQueMachAva = 1;
		double costValue = 1;
		double percValue = 1;
		
		this.setLayout(new GridLayout( 3, 1, 10, 10 ));
		JLabel etq1 []= {new JLabel("Number Machine Avalaible"),new JLabel("Number Repairers")};
		JLabel val1 [] = {new JLabel(Integer.toString(numMachAva)),new JLabel(Integer.toString(numRep))};
		JLabel etq2 []= {new JLabel("Maximum queue repair"),new JLabel("Maximum queue machines available")};
		JLabel val2 [] = {new JLabel(Integer.toString(maxQueRep)),new JLabel(Integer.toString(maxQueMachAva))};
		JLabel etq3 []= {new JLabel("Cost Value"),new JLabel("Percentage value")};
		JLabel val3 [] = {new JLabel(Double.toString(costValue)),new JLabel(Double.toString(percValue*100)+" %")};
		
		createSeccion("Input variables",etq1,val1);
		createSeccion("Performance variables",etq2,val2);
		createSeccion("Cost function",etq3,val3);
		
		
	}
	
	public void createSeccion(String nameSeccion,JLabel etiquetas [], JLabel valores[]){
		int cantidad=valores.length;
		
		JPanel tmp = new JPanel();
		tmp.setLayout(new GridLayout( cantidad, 2, 10, 10 ));
		
		TitledBorder borde =BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(Estilos.colorBorder, Estilos.colorLightBorder), nameSeccion);
		borde.setTitleFont(Estilos.fontTitulo);
		borde.setTitleColor(Estilos.colorTitulo);
		borde.setTitleJustification(TitledBorder.LEFT);
		tmp.setBorder(borde);	
		
		
		for(int i =0;i<cantidad;i++){
			JLabel etq = etiquetas[i];
			etq.setFont(Estilos.fontLabels);
			tmp.add(etq);
			JLabel var = valores[i];
			var.setFont(Estilos.fontLabels);
			tmp.add(var);
			
		}
		
		this.add(tmp);
		
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame v = new JFrame("prueba");
		
		v.add(new PanelEstadistica(new Vector<Object>()));
		v.setSize(400, 400);
		v.setVisible(true);
	}

}
