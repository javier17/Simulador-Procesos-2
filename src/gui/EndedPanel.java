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

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import persistance.Settings;
import logic.ProcessesList;

/**
 * CLASE QUE GESTIONAR UN PANEL PARA LOS PTROCESOS FINALIZADOS
 *
 */

public class EndedPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	/**
	 * OBJETO DE TIPO JLIST PARA LISTAR LOS PROCESOS FINALIZADOS
	 */
	private JList<String> endedList;
	/**
	 * MODELO PARA LA LISTA
	 */
	private DefaultListModel<String> model=new DefaultListModel<String>();
	/**
	 * HILO PARA GESTIONAR LOS PROCESOS FINALIZADOS
	 */
	private final Thread th;
	/**
	 * OBJETO ESTATICO DE LA CLASE
	 */
	private static EndedPanel endedPanel;
	/**
	 * PATRON SINGLENTON
	 * @return
	 */
	public static EndedPanel getEndedPanel(){
		if (endedPanel==null) {
			endedPanel=new EndedPanel();
			return endedPanel;
		} else {
			return endedPanel;
		}
	}
	/**
	 * CONSTRUCTOR DE LA CLASE
	 */
	private EndedPanel() {
		this.setLayout(new GridLayout());
		endedList= new JList<String>();
		endedList.setBorder(new TitledBorder("Procesos Finalizados"));
		endedList.setModel(model);
		
		th=new Thread(new Runnable() {
			
			@Override
			public void run() {
				ArrayList<logic.Process> arrayList;
				while (Settings.getSettings().isStatus()){
					model.clear();
					arrayList=ProcessesList.getProcessesList().getEndedProcesses();
					repaint();
					for (int i = 0; i < arrayList.size(); i++) {
						model.addElement(arrayList.get(i).toString());
					}
					
					try {
						Thread.sleep(300);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				
			}
		});
		
	
		this.add(endedList);
	}

	public Thread getTh() {
		return th;
	}
}
