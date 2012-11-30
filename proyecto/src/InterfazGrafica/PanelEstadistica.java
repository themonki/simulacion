/**
 * 
 */
package InterfazGrafica;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.SwingConstants;

import Utilidades.Estilos;

/**
 *
 *
 */
public class PanelEstadistica extends JScrollPane {
	
	JPanel principal = new JPanel();
	
	public PanelEstadistica(){
		super();
		this.add(new JLabel("No dispoble"));
	}
	
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
		
		
		
		int numMachAva = 0;
		int numRep = 0;
		int maxQueRep = 0;
		int aveQueRep = 0;
		int aveQueMachAva = 0;
		double costValue = 0;
		double percValue = 0;
		
		principal.setLayout(new GridLayout( 3, 1, 10, 10 ));
		JLabel etq1 []= {
				new JLabel("Number Machine Avalaible"),
				new JLabel("Number Repairers")
				};
		JLabel val1 [] = {
				new JLabel(Integer.toString(numMachAva)),
				new JLabel(Integer.toString(numRep))
				};
		JLabel etq2 []= {
				new JLabel("Maximum queue repair"),
				new JLabel("Average queue reapir"),
				new JLabel("Average queue Machine Avalaible")
				};
		JLabel val2 [] = {
				new JLabel(Integer.toString(maxQueRep)),
				new JLabel(Integer.toString(aveQueRep)),
				new JLabel(Integer.toString(aveQueMachAva))
				};
		JLabel etq3 []= {
				new JLabel("Cost Value"),
				new JLabel("Percentage value")
				};
		JLabel val3 [] = {
				new JLabel(Double.toString(costValue)),
				new JLabel(Double.toString(percValue*100)+" %")
		};
		
		createSeccion("Input variables",etq1,val1);
		createSeccion("Performance variables",etq2,val2);
		createSeccion("Cost function",etq3,val3);
		
		this.setViewportView(principal);
		//this.setPreferredSize(new Dimension(150, 300));

		
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
			var.setHorizontalAlignment(4);
			var.setFont(Estilos.fontLabels);
			tmp.add(var);
			
		}
		
		principal.add(tmp);
		
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
