package gui;

import java.awt.BorderLayout;

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
 * PANEL PARA MOSTRAR LOS PROCESOS QUE ESTAN EN EJECUCION, LOS PROCESOS BLOQUEADOS Y EL LOG
 *
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

import javax.swing.border.TitledBorder;

import persistance.Settings;
import logic.Log;
import logic.ProcessesList;

public class MiddlePanel extends JPanel{
	
	
	private static final long serialVersionUID = 1L;
	/**
	 * PANEL PRINCIPAL
	 */
	private JPanel processPanel;
	private JLabel currentProcess;
	private JLabel lblImagen;
	/**
	 * LISTA DE PROCESOS BLOQUEADOS
	 */
	private JList<String> blockedList;
	/**
	 * MODELO PARA LA LISTA DE PROCESOS BLOQUEADOS
	 */
	private DefaultListModel<String> model=new DefaultListModel<String>();
	/**
	 * OBJETO DE TIPO LOG
	 */
	private Log logList;
	/**
	 * OBJETO DE TIPO JSCROLLPANE
	 */
	private JScrollPane jsp;
	/**
	 * HILO PARA GESTIONAR LOS PROCESOS BLOQUEADOS
	 */
	private final Thread th;
	/**
	 * OBJETO DE TIPO PROGRESSBAR PARA MIRAR EL TIEMPO QUE LLEVA EL PROCESO EJECUTANDOSE
	 */
	private JProgressBar progressBar;

	ImageIcon image;
	Font fuente;
	
	/**
	 * OBJETO ESTATICO DE LA CLASE
	 */
	private static MiddlePanel middlePanel;
	
	/**
	 * PATRON SINGLENTON
	 * @return
	 */
	public static MiddlePanel getMiddlePanel(){
		if (middlePanel==null) {
			middlePanel=new MiddlePanel();
			return middlePanel;
		} else {
			return middlePanel;
		}
	}	
	

	/**
	 * CONTRUCTOR DE LA CLASE PARA GESTIONAR LOS PROCESOS EN EJECUCION, BLOQUEADOS Y LOG.
	 */
	private MiddlePanel() {
		this.setLayout(new GridLayout(3,1,10,10));
		fuente = new Font("Tahoma",Font.BOLD,15);
		currentProcess=new JLabel("Procesador..");
		currentProcess.setFont(fuente);
		currentProcess.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.GRAY));
		
		image = new ImageIcon(getClass().getResource("/persistance/Processor.jpg"));  
		lblImagen = new JLabel("");
		lblImagen.setIcon(image);
		lblImagen.setBorder(BorderFactory.createEtchedBorder(Color.GREEN,Color.GREEN));
		lblImagen.setHorizontalAlignment(JLabel.CENTER);
		
		processPanel=new JPanel();
		processPanel.setLayout(new GridLayout(2,1));
		
		logList= Log.getLog();

		logList.setEditable(false);
		
		jsp=new JScrollPane(logList);
		jsp.setBorder(new TitledBorder("Log"));
		blockedList= new JList<String>();
		blockedList.setBorder(new TitledBorder("Procesos Bloqueados"));
		blockedList.setModel(model);
		
		
		progressBar=new JProgressBar();
		progressBar.setForeground(new Color(0,170,255));
		progressBar.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.BLUE));
		
		processPanel.setLayout(new BorderLayout());
		
		processPanel.setBorder(new TitledBorder("Procesador"));
		processPanel.add(currentProcess,BorderLayout.NORTH);
		processPanel.add(lblImagen,BorderLayout.CENTER);
		processPanel.add(progressBar,BorderLayout.SOUTH);
		
		th=new Thread(new Runnable() {
			
			@Override
			public void run() {
				ArrayList<logic.Process> arrayList;
				while (Settings.getSettings().isStatus()){
					model.clear();
					arrayList=ProcessesList.getProcessesList().getBlockedProcesses();
					repaint();
					for (int i = 0; i < arrayList.size(); i++) {
						model.addElement(arrayList.get(i).blockedToString());
					}
					
					try {
						Thread.sleep(300);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				JOptionPane.showMessageDialog(new JOptionPane(), "La simulación ha finalizado con normalidad. En el Log puede encontrar más detalles.");
				
			}
		});
		
		this.add(processPanel);
		this.add(blockedList);
		this.add(jsp);
	}


	/**
	 * METODOS SER Y GET DE LA CLASE
	 * @return
	 */
	public Thread getTh() {
		return th;
	}



	public JLabel getCurrentProcess() {
		return currentProcess;
	}



	public void setCurrentProcess(JLabel currentProcess) {
		this.currentProcess = currentProcess;
	}
	
	
	public JProgressBar getProgressBar() {
		return progressBar;
	}



	public void setProgressBar(JProgressBar progressBar) {
		this.progressBar = progressBar;
	}
}
