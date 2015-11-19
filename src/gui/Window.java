package gui;

/**
 * UNIVERSIDAD PEDAGOGICA Y TECNOLOGICA DE COLOMBIA
 * FACULTAD DE INGENIERIA
 * ESCUELA DE SISTEMAS Y COMPUTACION
 * SISTEMAS OPERATIVOS
 * SOFTWARE SIMULACION DE PROCESOS
 * @author CARLOS JAVIER PITA AMAYA
 * @author JHON JAIRO ZIPA CARO
 */

/**
 * 
 * VENTANA PRINCIPAL DE LA APLICACION
 *
 */

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Window extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	/**
	 * OBJETO PARA AGREGAR EL PANEL DE PROCESOS LISTOS
	 */
	private StartPanel startPanel;
	/**
	 * OBJETO PARA AGREGAR EL PANEL DE BLOQUEADOS Y LOG
	 */
	private MiddlePanel middlePanel;
	/**
	 * OBJETO PARA AGREGAR EL PANEL DE PROCESOS FINALIZADOS
	 */
	private EndedPanel endedPanel;
	/**
	 * CONSTRUCTOR DE LA CLASE PARA CREAR LA VENTANA PRINCIPAL
	 */
	private JMenuBar barra;
	private JMenu menu;
	private JMenuItem itemProcesos;
	
	public Window(){
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(1100, 740);
		this.setTitle("Procesos");
		this.setLocationRelativeTo(null);
		this.setLayout(new GridLayout(1, 3));
		this.setResizable(true);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				close();
			}
		});
		
		barra = new JMenuBar();
		menu = new JMenu("ARCHIVO");
		itemProcesos = new JMenuItem("Salir");
		itemProcesos.setIcon(new ImageIcon(getClass().getResource("/persistance/001_05.png")));
		itemProcesos.addActionListener(this);
		
		menu.add(itemProcesos);
		barra.add(menu);
		
		setIconImage(new ImageIcon(getClass().getResource("/persistance/Processor.jpg")).getImage());
		
		startPanel=StartPanel.getStartPanel();
		middlePanel=MiddlePanel.getMiddlePanel();
		endedPanel=EndedPanel.getEndedPanel();
		
		
		this.add(startPanel);
		this.add(middlePanel);
		this.add(endedPanel);
		
		setJMenuBar(barra);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==itemProcesos){
			close();
		}
	}
	
	public void close(){
		if(JOptionPane.showConfirmDialog(rootPane, "¿Desea realmente salir del sistema?","Salir del sistema",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
			System.exit(0);
	}
	

}
