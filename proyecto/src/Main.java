import javax.swing.JFrame;
import javax.swing.UIManager;

import  InterfazGrafica.tableroDeMaquinas;


import com.nilo.plaf.nimrod.NimRODLookAndFeel;
import com.nilo.plaf.nimrod.NimRODTheme;

public class Main {
	
	public static void main(String [] args )
	{
		
		try
		{				
			NimRODTheme nt = new NimRODTheme("recursos/NimRODThemeFile2.theme");
			NimRODLookAndFeel NimRODLF = new NimRODLookAndFeel();
			NimRODLookAndFeel.setCurrentTheme(nt);
			UIManager.setLookAndFeel( NimRODLF);
		}
		catch (Exception e){e.printStackTrace();}

	
		
		//System.out.println("hola");
		
		tableroDeMaquinas tm = new tableroDeMaquinas();
		tm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
	}

}
