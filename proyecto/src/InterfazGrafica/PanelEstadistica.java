/**
 * 
 */
package InterfazGrafica;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
		principal = new JPanel();
		principal.add(new JLabel("No dispoble"));
		this.setViewportView(principal);
	}
	
	public PanelEstadistica(double percValuearg) {
		// TODO Auto-generated constructor stub
		super();
		
		
		init( percValuearg);
		
	}
	
	public void init(double percValuearg){
		
		principal.removeAll();
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
				double percValue = percValuearg;
				
				principal.setLayout(new GridLayout(3,1,20,20));
				
				//BoxLayout o= new BoxLayout(principal, BoxLayout.Y_AXIS);
				
				
				JLabel etq1 []= {
						new JLabel("Numero de Maquinas Disponibles"),
						new JLabel("Numero de Reaparadores")
						};
				JLabel val1 [] = {
						new JLabel(Integer.toString(numMachAva)),
						new JLabel(Integer.toString(numRep))
						};
				JLabel etq2 []= {
						new JLabel("Maxima cola de Reparación"),
						new JLabel("Cola de Reparacion Promedia"),
						new JLabel("Cola de Maquinas Disponibles Promedio"),
						new JLabel("Porcentaje de Funcionamiento Maquinas")
						};
				JLabel val2 [] = {
						new JLabel(Integer.toString(maxQueRep)),
						new JLabel(Integer.toString(aveQueRep)),
						new JLabel(Integer.toString(aveQueMachAva)),
						new JLabel(Double.toString(percValue*100)+" %")
						
						};
				JLabel etq3 []= {
						new JLabel("Cost Value"),
						
						};
				JLabel val3 [] = {
						new JLabel(Double.toString(costValue)),
						
				};
				
				createSeccion("Datos de Entrada::",etq1,val1);
				createSeccion("Variables de Rendimiento::",etq2,val2);
				createSeccion("Funcion de Costo::",etq3,val3);
				
				
				JPanel aux=new JPanel(); 
				aux.add(principal);
				this.setViewportView(aux);
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
		
		v.add(new PanelEstadistica(0));
		v.setSize(400, 400);
		v.setVisible(true);
	}

}
