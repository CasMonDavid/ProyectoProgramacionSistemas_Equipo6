import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Ventana extends JFrame {
	Escaner escan;
	JTextArea areaTexto;
	
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
	
	public void panelMenu() {
		JPanel pnlPrincipal = new JPanel();
		pnlPrincipal.setSize(900,900);
		pnlPrincipal.setLocation(0,0);
		pnlPrincipal.setBackground(Color.cyan);
		pnlPrincipal.setLayout(null);
		this.add(pnlPrincipal);
		
		areaTexto = new JTextArea();
		areaTexto.setBounds(50, 50, 800, 400);
		pnlPrincipal.add(areaTexto);
		
		JButton boton = new JButton("Analizar");
		boton.setBounds(400, 550, 100, 50);
		boton.addActionListener(e -> escan.iniciarApp(areaTexto.getText()));
		pnlPrincipal.add(boton);
		
		this.repaint();
		this.revalidate();
	}
}

