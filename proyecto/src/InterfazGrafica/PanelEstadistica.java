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
	
	public PanelEstadistica(Vector obj) {
		// TODO Auto-generated constructor stub
		super();
		
		
		
		this.setLayout(new GridLayout( 3, 1, 10, 10 ));
		JLabel e []= {new JLabel("1"),new JLabel("2"),new JLabel("3"),new JLabel("4")};
		JLabel va [] = {new JLabel("a"),new JLabel("b"),new JLabel("c"),new JLabel("d"),};
		JLabel e2 []= {new JLabel("1"),new JLabel("2"),new JLabel("3"),new JLabel("4")};
		JLabel va2 [] = {new JLabel("a"),new JLabel("b"),new JLabel("c"),new JLabel("d"),};
		JLabel e3 []= {new JLabel("1"),new JLabel("2"),new JLabel("3"),new JLabel("4")};
		JLabel va3 [] = {new JLabel("a"),new JLabel("b"),new JLabel("c"),new JLabel("d"),};
		
		createSeccion("Variables Desempeño",e,va);
		createSeccion("Variables Estado",e2,va2);
		createSeccion("Funcion",e3,va3);
		
		
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
		
		v.add(new PanelEstadistica(new Vector()));
		v.setSize(400, 400);
		v.setVisible(true);
	}

}
