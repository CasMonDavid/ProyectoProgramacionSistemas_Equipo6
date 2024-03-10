import java.awt.Color;

import javax.swing.*;

public class Ventana extends JFrame {
	Escaner escan;
	
	public Ventana() {
		escan = new Escaner();
		setVisible(true);
		setLayout(null);
		setTitle("Escaner DML");
		setSize(900,900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		panelMenu();
	}
	
	// COLOCA UN PANEL VACIO ENCIMA DE LA VENTANA
	public void panelMenu() {
		JPanel pnlPrincipal = new JPanel();
		pnlPrincipal.setSize(900,900);
		pnlPrincipal.setLocation(0,0);
		pnlPrincipal.setBackground(Color.white);
		pnlPrincipal.setLayout(null);
		this.add(pnlPrincipal);
		
		this.repaint();
		this.revalidate();
	}
}
