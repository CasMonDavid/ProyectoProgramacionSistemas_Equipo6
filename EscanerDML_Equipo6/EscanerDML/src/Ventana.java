    import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.*;

public class Ventana extends JFrame {
	Escaner escanear;
	List<PropiedadesCadena> resultados;
	public Ventana() {
		escanear = new Escaner();
		resultados = new ArrayList<>();
		setVisible(true);
		setLayout(null);
		setTitle("Scan");
		setSize(1100,800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon("src/icon.png").getImage());
		setResizable(false);
		
		panelMenu();
	}
	
	public void panelMenu() {
		JPanel pnlPrincipal = new JPanel();
		pnlPrincipal.setSize(1100,800);
		pnlPrincipal.setLocation(0,0);
		pnlPrincipal.setBackground(new Color(30,31,34,255));
		pnlPrincipal.setLayout(null);

		JPanel pnlLine = new JPanel();
		pnlLine.setSize(1100, 1);
		pnlLine.setLocation(0, 40);
		pnlLine.setBackground(new Color(53,53,56,255));

		JPanel pnlLineTwo = new JPanel();
		pnlLineTwo.setSize(1, 800);
		pnlLineTwo.setLocation(80, 40);
		pnlLineTwo.setBackground(new Color(53,53,56,255));

		JLabel lblImagen = new JLabel(new ImageIcon(new ImageIcon("src/icon.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		lblImagen.setBounds(10, 8, 24, 24);
		pnlPrincipal.add(lblImagen);

		JLabel jlScam = new JLabel("Texto.txt");
		jlScam.setBounds(39, 8, 100, 24);
		jlScam.setForeground(new Color(215,215,215));
		jlScam.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pnlPrincipal.add(jlScam);

		int intSeparacion = 45;
		for (int i = 0; i < 28; i++) {
			JLabel jlRenglon = new JLabel((i+1) + "");
			jlRenglon.setFont(new Font("Tahoma", Font.PLAIN, 13));
			jlRenglon.setForeground(new Color(73,73,76));
			jlRenglon.setBounds(20, intSeparacion, 20, 20);
			pnlPrincipal.add(jlRenglon);
			intSeparacion += 25;
		}

		JLabel jlStartFondo = new JLabel(new ImageIcon(new ImageIcon("src/triangulo.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		jlStartFondo.setBounds(1042, 5, 30, 30);
		jlStartFondo.setVisible(false);

		JLabel jlStartButton = new JLabel(new ImageIcon(new ImageIcon("src/start.png").getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
		jlStartButton.setBounds(1050, 12, 16, 16);
		pnlPrincipal.add(jlStartButton);
		pnlPrincipal.add(jlStartFondo);

		JLabel jlTablaFondo = new JLabel(new ImageIcon(new ImageIcon("src/triangulo.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		jlTablaFondo.setBounds(993, 5, 30, 30);
		jlTablaFondo.setVisible(false);

		JLabel jlTabla = new JLabel(new ImageIcon(new ImageIcon("src/celulas.png").getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
		jlTabla.setBounds(1000, 12, 16, 16);
		pnlPrincipal.add(jlTabla);
		pnlPrincipal.add(jlTablaFondo);

		JTextPane jtaTexto = new JTextPane();
		jtaTexto.setBounds(85, 45, 1080, 780);
		jtaTexto.setBackground(null);
		jtaTexto.setForeground(new Color(215,215,215));
		jtaTexto.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jtaTexto.setCaretColor(new Color(215,215,215));

		StyledDocument doc = jtaTexto.getStyledDocument();

		((AbstractDocument) doc).setDocumentFilter(new DocumentFilter() {
			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				super.insertString(fb, offset, string, attr);
				// Aplica estilos específicos a los caracteres insertados
				applyStyles(doc, offset, string.length());
			}

			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
				super.remove(fb, offset, length);
				// Reaplica los estilos después de eliminar caracteres
				applyStyles(doc, offset, 0);
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
				super.replace(fb, offset, length, text, attrs);
				// Aplica estilos específicos a los caracteres reemplazados
				applyStyles(doc, offset, text.length());
			}

			// Método para aplicar estilos a ciertos caracteres
			private void applyStyles(StyledDocument doc, int offset, int length) throws BadLocationException {
				SimpleAttributeSet orangeAttrs = new SimpleAttributeSet();
				StyleConstants.setForeground(orangeAttrs, new Color(247, 166, 26));

				SimpleAttributeSet greenAttrs = new SimpleAttributeSet();
				StyleConstants.setForeground(greenAttrs, new Color(44,156,74));

				SimpleAttributeSet blueAttrs = new SimpleAttributeSet();
				StyleConstants.setForeground(blueAttrs, new Color(68,116,178));

				SimpleAttributeSet whiteAttrs = new SimpleAttributeSet();
				StyleConstants.setForeground(whiteAttrs, new Color(215,215,215));

				String text = doc.getText(offset, length);

				AttributeSet attrs = whiteAttrs;

				if (!resultados.isEmpty()) {
					for (int i = resultados.size(); i < escanear.iniciarApp(jtaTexto.getText()).size(); i++) {
						resultados.add(escanear.iniciarApp(jtaTexto.getText()).get(i));
					}
				}else {
					resultados = escanear.iniciarApp(jtaTexto.getText());
				}

				for (int i = 0; i < text.length(); i++) {
					attrs = whiteAttrs;

					if (!resultados.isEmpty()) {
						for (int z = 0; z < resultados.size(); z++) {
							if (resultados.get(z).getResultados()[2] == i) {
								switch (resultados.get(z).getResultados()[0]) {
									case 1:
										attrs = orangeAttrs;
										break;
									case 2:
										attrs = greenAttrs;
										break;
									case 3:
										attrs = blueAttrs;
										break;
									default:
										attrs = whiteAttrs;
										break;
								}
								break;
							}
						}
					}
					doc.setCharacterAttributes(offset + i, 1, attrs, false);
				}

				if (!resultados.isEmpty()) {
					for (int i = 0; i < resultados.size(); i++) {
						if (resultados.get(i).getResultados()[2] == 0) {
							for (int z = resultados.get(i).getResultados()[1]; z >= 0; z--) {
								doc.setCharacterAttributes(offset - z, 1, attrs, false);
							}
							doc.setCharacterAttributes(offset + 1, 1, whiteAttrs, false);
							resultados.get(i).getResultados()[2] = 1;
						}
					}
				}
			}
		});

		//eventos
		jlStartButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				jlStartFondo.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				jlStartFondo.setVisible(false);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				escanear.iniciarApp(jtaTexto.getText());
			}
		});

		//eventos
		jlTabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				jlTablaFondo.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				jlTablaFondo.setVisible(false);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				frameTabla();
			}
		});

		pnlPrincipal.add(jtaTexto);
		pnlPrincipal.add(pnlLine);
		pnlPrincipal.add(pnlLineTwo);
		this.add(pnlPrincipal);
		this.repaint();
		this.revalidate();
	}

	public void frameTabla(){
		JFrame frameTabla = new JFrame();
		frameTabla.setVisible(true);
		frameTabla.setTitle("Tabla");
		frameTabla.setSize(500,800);
		frameTabla.setLocationRelativeTo(null);
		frameTabla.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameTabla.setIconImage(new ImageIcon("src/icon.png").getImage());
		frameTabla.setResizable(false);

		panelTabla(frameTabla);
	}
	public void panelTabla(JFrame frame) {
		JPanel pnlPrincipal = new JPanel(new BorderLayout());
		pnlPrincipal.setBackground(Color.WHITE);

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("No.");
		model.addColumn("Línea");
		model.addColumn("TOKEN");
		model.addColumn("Tipo");
		model.addColumn("Código");

		if (!resultados.isEmpty()) {
			for (int i = 0; i < resultados.size(); i++) {
				int tipo = 0;
				int codigo = 0;
				switch (resultados.get(i).getCadena().toLowerCase()){
					case "select" :
						tipo = 1;
						codigo = 10;
						break;
					case "from" :
						tipo = 1;
						codigo = 11;
						break;
					case "where" :
						tipo = 1;
						codigo = 12;
						break;
					case "in" :
						tipo = 1;
						codigo = 13;
						break;
					case "and" :
						tipo = 1;
						codigo = 14;
						break;
					case "or" :
						tipo = 1;
						codigo = 15;
						break;
					case "create" :
						tipo = 1;
						codigo = 16;
						break;
					case "table" :
						tipo = 1;
						codigo = 17;
						break;
					case "char" :
						tipo = 1;
						codigo = 18;
						break;
					case "numeric" :
						tipo = 1;
						codigo = 19;
						break;
					case "not" :
						tipo = 1;
						codigo = 20;
						break;
					case "null" :
						tipo = 1;
						codigo = 21;
						break;
					case "constraint" :
						tipo = 1;
						codigo = 22;
						break;
					case "key" :
						tipo = 1;
						codigo = 23;
						break;
					case "primary" :
						tipo = 1;
						codigo = 24;
						break;
					case "foreign" :
						tipo = 1;
						codigo = 25;
						break;
					case "references" :
						tipo = 1;
						codigo = 26;
						break;
					case "insert" :
						tipo = 1;
						codigo = 27;
						break;
					case "into" :
						tipo = 1;
						codigo = 28;
						break;
					case "values" :
						tipo = 1;
						codigo = 29;
						break;
					case "," :
						tipo = 5;
						codigo = 50;
						break;
					case "." :
						tipo = 5;
						codigo = 51;
						break;
					case "(" :
						tipo = 5;
						codigo = 52;
						break;
					case ")" :
						tipo = 5;
						codigo = 53;
						break;
					case "'" :
						tipo = 5;
						codigo = 54;
						break;
					case "d" :
						tipo = 6;
						codigo = 61;
						break;
					case "a" :
						tipo = 6;
						codigo = 62;
						break;
					case "+" :
						tipo = 7;
						codigo = 70;
						break;
					case "-" :
						tipo = 7;
						codigo = 71;
						break;
					case "*" :
						tipo = 7;
						codigo = 72;
						break;
					case "/" :
						tipo = 7;
						codigo = 73;
						break;
					case ">" :
						tipo = 8;
						codigo = 81;
						break;
					case "<" :
						tipo = 8;
						codigo = 82;
						break;
					case "=" :
						tipo = 8;
						codigo = 83;
						break;
					case ">=" :
						tipo = 8;
						codigo = 84;
						break;
					case "<=" :
						tipo = 8;
						codigo = 85;
						break;

				}
				model.addRow(new Object[]{(i+1), resultados.get(i).getNumLinea(), resultados.get(i).getCadena(), tipo, codigo});
			}
		}

		JTable table = new JTable(model);
		table.setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(table);

		pnlPrincipal.add(scrollPane, BorderLayout.CENTER);
		frame.add(pnlPrincipal);
	}
}