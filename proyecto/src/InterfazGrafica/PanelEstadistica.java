/**
 * 
 */
package InterfazGrafica;

import java.awt.GridLayout;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

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
		principal.add(new JLabel("No disponible"));
		this.setViewportView(principal);
	}
	
	public PanelEstadistica(double percValuearg,int numMachAva ,int numRepaAva,double DesempenioSumReparadores_ocupacion_total,int costo, 
			double ColaPromedio, int ColaReparador,double colaPromedioAdicional) {
		// TODO Auto-generated constructor stub
		super();
		
		
		init( percValuearg,numMachAva,numRepaAva,DesempenioSumReparadores_ocupacion_total,costo,ColaPromedio,ColaReparador, colaPromedioAdicional);
		
	}
	
	public void init(double percValuearg ,int numMachAvaarg ,int numRepaAvaarg, double desempenioSumReparadores_ocupacion_total, int costo,
			double colaPromedioReparador,int colaReparador, double colaPromedioAdicional){
		
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
				
				
				
				int numMachAva = numMachAvaarg;
				int numRep = numRepaAvaarg;
				int maxQueRep = colaReparador;
				double  aveQueRep = colaPromedioReparador;
				double aveQueMachAva = colaPromedioAdicional;
				double costValue = costo;
				double percValue = percValuearg;
				double porcentaje_ocupacion_reparadores=desempenioSumReparadores_ocupacion_total;
				
				DecimalFormat decimal= new DecimalFormat("0.00");
				
				
				
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
						new JLabel("Porcentaje de Funcionamiento Maquinas"),
						new JLabel("Porcentaje de ocupacion de los Reparadores")
						};
				JLabel val2 [] = {
						new JLabel(Integer.toString(maxQueRep)),
						new JLabel(decimal.format(aveQueRep)),
						new JLabel(decimal.format(aveQueMachAva)),
						new JLabel(decimal.format(percValue*100)+"%"),
						new JLabel(decimal.format(porcentaje_ocupacion_reparadores*100)+"%")
						
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
		/*JFrame v = new JFrame("prueba");
		
		v.add(new PanelEstadistica(0));
		v.setSize(400, 400);
		v.setVisible(true);*/
	}

}
