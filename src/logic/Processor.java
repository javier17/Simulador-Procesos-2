package logic;

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
 * ESTA CLASE PERMMITE GESTIONAR LOS PROCESOS
 */

import java.util.Timer;
import javax.swing.JProgressBar;
import persistance.Settings;
import gui.MiddlePanel;

public class Processor {
	
	/**
	 * TIEMPO QUE SE LE VA A ASIGNAR A L PROCESADOR
	 */
	private int time;//Seconds
	/**
	 * OBJETO DE TIPO PROCESS
	 */
	private Process current;
	/**
	 * VARIABLE PARA CONTAR LOS SEGUNDOS QUE LLEVA UN PROCESO EN EJECUCION
	 */
	private int secondsCount;
	/**
	 * OBJETO DE TIPO TIMER
	 */
	Timer timer;
	/**
	 * OBJETO DE ESTA MISMA CLASE DE TIPO ESTATICO
	 */
	private static Processor processor;  
	/**
	 * OBJETO DE LAS CLASE PROCESSESLIST 
	 */
	private ProcessesList pList;
	/**
	 * BARRA DE PROGRESO PARA MIRAR EL TIEMPO QUE LLEVA EL PROCESO EJECUTANDOSE
	 */
	JProgressBar progressBar;
	
	private final Thread th;
	
	/**
	 * PATRON SINGLENTON QUE PERMITE SOLO UNA INSTANCIA DE LA CLASE
	 * @return
	 */
	public static Processor getProcessor(){
		if (processor == null) {
			processor = new Processor(); 
			return processor;
		} else {
			return processor;
		}
	}
	
	/**
	 * CONSTRUCTOR DE LA CLASE PROCESSOR PARA INICIAR LA SIMULACION 
	 */
	private Processor() {
		pList=ProcessesList.getProcessesList();
		th=new Thread(new Runnable() {
			
			@Override
			public void run() throws NullPointerException{
				while (Settings.getSettings().isStatus()) {
					if (current!=null) {
						ProcessesList.getProcessesList().getStartProcesses().add(current);
						Log.getLog().readyMessage(current);
						current=null;
					}else {
						MiddlePanel.getMiddlePanel().getCurrentProcess().setText("");
					}
					try {
						executeProcess();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					MiddlePanel.getMiddlePanel().getProgressBar().setMinimum(0);
					MiddlePanel.getMiddlePanel().getProgressBar().setValue(0);
					try {
						MiddlePanel.getMiddlePanel().getProgressBar().setMaximum(current.getTime());
					} catch (NullPointerException e) {
						// TODO: handle exception
					}
					
					while (secondsCount<time && current!=null) {
						MiddlePanel.getMiddlePanel().getCurrentProcess().setText("Proceso "+	current.getId()+ ", " +
								current.getName()+ ", " +
								current.getTime()+ " segundos, van " +
								current.getElapsedTime()+" segundos.");
						
						if (current.getBlockedTime()!=-1) {
							if (current.getBlockedTime()==0) {
								ProcessesList.getProcessesList().getBlockedProcesses().add(current);
								Log.getLog().blockedMessage(current);
								ProcessesList.getProcessesList().getBlockedProcesses().get(
										ProcessesList.getProcessesList().getBlockedProcesses().size()-1).getTh().start();
								current=null;
								break;
							}else {
								current.setBlockedTime(current.getBlockedTime()-1);
							}
							
						}
						
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						secondsCount++;
						
						MiddlePanel.getMiddlePanel().repaint();
						
						current.setElapsedTime(current.getElapsedTime()+1);
						MiddlePanel.getMiddlePanel().getProgressBar().setValue(current.getElapsedTime());
												
						if (current.getTime()==current.getElapsedTime()) {
							ProcessesList.getProcessesList().getEndedProcesses().add(current);
							Log.getLog().endedMessage(current);
							current=null;
							break;
						}
						

						
					}
				}
				
			}
		});
	}
	
	/**
	 * METODO PARA GESTIONAR LOS PROCESOS QUE SE ESTAN EJECUTANDO
	 * @throws InterruptedException
	 */
	public void executeProcess() throws InterruptedException{
		try {
			if (pList.getStartProcesses().isEmpty()==false || pList.getBlockedProcesses().isEmpty()==false) {
				current=pList.getStartProcesses().get(0);
				Log.getLog().execMessage(current);
				pList.getStartProcesses().remove(0);
				secondsCount=0;
			} 	else {
				Thread.sleep(1000);
				Settings.getSettings().changeStatus();
			}
		} catch (IndexOutOfBoundsException e) {
			// TODO: handle exception
		}
			
	}

	public Thread getTh() {
		return th;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
}
