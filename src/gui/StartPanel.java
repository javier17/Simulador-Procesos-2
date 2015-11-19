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
 * PANEL DE PROCESOS LISTOS Y PARA AÑADIR PROCESOS A LA LISTA
 *
 */

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import persistance.Settings;
import logic.ProcessesList;
import logic.Processor;

public class StartPanel extends JPanel{
	
	
	private static final long serialVersionUID = 1L;

	private int id=0;
	
	private JList<String> startList;
	private DefaultListModel<String> model=new DefaultListModel<String>();
	private JPanel addForm;
	private JLabel nameLabel;
	private JLabel timeLabel;
	private JLabel blockedLabel;
	private JLabel blockedTimeLabel;
	private JLabel blockedDurationLabel;
	private JTextField nameField;
	private JTextField timeField;
	private JCheckBox blockedCheck;
	private JTextField blockedTimeField;
	private JTextField blockedDurationField;
	private JButton addProcess;
	private JButton startSim;
	private ProcessesList tmp;
	
	private JFrame processorData;
	private JLabel pTimeLabel;
	private JTextField pTimeField;
	private JButton start;
	
	private static StartPanel startPanel;
	/**
	 * PATRON SINGLENTON
	 * @return
	 */
	public static StartPanel getStartPanel(){
		if (startPanel==null) {
			startPanel=new StartPanel();
			return startPanel;
		} else {
			return startPanel;
		}
	}
	/**
	 * CONSTRUCTOR DE LA CLASE PARA CARGAR PROCESOS Y PARA AÑADIR PROCESOS
	 */
	private StartPanel() {
		this.setLayout(new GridLayout(2,1));
		tmp=ProcessesList.getProcessesList();
		addForm=new JPanel();
		addForm.setLayout(new GridLayout(7,2,10,15));
		nameLabel=new JLabel("Nombre del Proceso");
		timeLabel=new JLabel("Tiempo de Ejecución");
		blockedLabel=new JLabel("Bloqueo");
		blockedTimeLabel=new JLabel("Tiempo");
		blockedDurationLabel=new JLabel("Duración");
		nameField=new JTextField();
		timeField=new JTextField();
		blockedCheck=new JCheckBox();
		blockedTimeField=new JTextField();
		blockedTimeField.setEnabled(false);
		blockedDurationField=new JTextField();
		blockedDurationField.setEnabled(false);
		blockedCheck.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				isBlocked();
				
			}
		});
		
		
		addForm.add(nameLabel);
		addForm.add(nameField);
		addForm.add(timeLabel);
		addForm.add(timeField);
		addForm.add(blockedLabel);
		addForm.add(blockedCheck);
		addForm.add(blockedTimeLabel);
		addForm.add(blockedTimeField);
		addForm.add(blockedDurationLabel);
		addForm.add(blockedDurationField);
		
		startList= new JList<String>();
		startList.setBorder(new TitledBorder("Procesos Listos para Ejecución"));
		startList.setModel(model);
		
		final Thread th=new Thread(new Runnable() {
			
			@Override
			public void run() {
				ArrayList<logic.Process> arrayList;
				while (Settings.getSettings().isStatus()){
					model.clear();
					arrayList=ProcessesList.getProcessesList().getStartProcesses();
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
		
		processorData=new JFrame("Procesador");
		processorData.setLayout(new GridLayout(4,1));
		processorData.setSize(300,200);
		pTimeLabel=new JLabel("Tiempo Ejecución");
		pTimeField=new JTextField();
		start=new JButton("Iniciar");
		start.setIcon(new ImageIcon(getClass().getResource("/persistance/001_06.png")));
		processorData.add(pTimeLabel);
		processorData.add(pTimeField);
		processorData.add(start);
		
		
		addProcess=new JButton("Agregar Proceso");
		addProcess.setIcon(new ImageIcon(getClass().getResource("/persistance/001_01.png")));
		addProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (blockedCheck.isSelected()) {
					try {
						id=id+1;
						tmp.addProcess(id, 
								nameField.getText(), 
								Integer.parseInt(timeField.getText()),
								Integer.parseInt(blockedTimeField.getText()),
								Integer.parseInt(blockedDurationField.getText()));
								timeField.setText("");
								nameField.setText("");
								blockedTimeField.setText("");
								blockedDurationField.setText("");
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(new JOptionPane(), "A excepción del nombre, todos los datos deben ser valores numéricos.");
					}

				}else {
					
					try {
						id=id+1;
						tmp.addProcess(id, 
								nameField.getText(),
								Integer.parseInt(timeField.getText()));
								timeField.setText("");
								nameField.setText("");
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(new JOptionPane(), "A excepción del nombre, todos los datos deben ser valores numéricos.");
					}

				}
				
			}
		});
		
		startSim=new JButton("Iniciar Simulación");
		startSim.setIcon(new ImageIcon(getClass().getResource("/persistance/001_06.png")));
		startSim.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				processorData.setVisible(true);
				
			}
		});
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(tmp.getStartProcesses().isEmpty()){
					JOptionPane.showMessageDialog(null,"Por favor ingrese  procesos para iniciar la simulacion","Error: Lista Vacia",JOptionPane.ERROR_MESSAGE);
					processorData.setVisible(false);
				}else{
				try {
					Settings.getSettings().changeStatus();
					Processor.getProcessor().setTime(Integer.valueOf(pTimeField.getText()));
					addProcess.setEnabled(false);
					startSim.setEnabled(false);
					th.start();
					MiddlePanel.getMiddlePanel().getTh().start();
					EndedPanel.getEndedPanel().getTh().start();
					Processor.getProcessor().getTh().start();
					processorData.setVisible(false);
					
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(new JOptionPane(), "Ingrese valores numericos solamente.");
				}
				}

			}
		});
		
		addForm.add(addProcess);
		addForm.add(startSim);
		this.add(addForm);
		this.add(startList);
		
		
	}
	/**
	 * METODO PARA VERIFICAR SI UN PROCESO ESTA BLOQUEADO
	 */
	public void isBlocked(){
		if (blockedCheck.isSelected()) {
			blockedTimeField.setEnabled(true);
			blockedDurationField.setEnabled(true);
		} else {
			blockedTimeField.setEnabled(false);
			blockedDurationField.setEnabled(false);
		}
	}

	/**
	 * METODOS SET Y GET DE LA CLASE
	 * @return
	 */
	public JTextField getNameField() {
		return nameField;
	}

	public void setNameField(JTextField nameField) {
		this.nameField = nameField;
	}


	public JTextField getTimeField() {
		return timeField;
	}

	public void setTimeField(JTextField timeField) {
		this.timeField = timeField;
	}

}
