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
 * ESTA CLASE PERMMITE CREAR LISTA DE PROCESOS BLOQUEADOS, FINALIZADOS, LISTOS, Y EN EJECUCION
 */

import java.util.ArrayList;

public class ProcessesList{

	/**
	 * ARRAYLIST DE PROCESOS EN EJECUCION 
	 */
	private ArrayList<Process> startProcesses;
	/**
	 * ARRAYLIST DE PROCESOS FINALIZADOS
	 */
	private ArrayList<Process> endedProcesses;
	/**
	 * ARRAYLIST DE PROCESOS BLOQUEADOS
	 */
	private ArrayList<Process> blockedProcesses;
	/**
	 * OBJETO DE ESTA MISMA CLASE DE TIPO ESTATICA
	 */
	private static ProcessesList processesList;
	
	/**
	 * PATRON SINGLENTON PARA CREAR UNA SOLA INSTANCIA DE LA CLASE
	 * @return
	 */
	public static ProcessesList getProcessesList(){
		if (processesList==null) {
			processesList=new ProcessesList();
			return processesList;
		} else {
			return processesList;
		}
	}
	/**
	 * CONTRUCTOR DE LA CLASE PARA INICIALIZAR LAS LISTAS
	 */
	private ProcessesList() {
		startProcesses=new ArrayList<Process>();
		endedProcesses=new ArrayList<Process>();
		blockedProcesses=new ArrayList<Process>();
		
	}
	/**
	 * METODOS GET Y SET DE LAS LISTAS 
	 * @return
	 */
	public ArrayList<Process> getBlockedProcesses() {
		return blockedProcesses;
	}
	public void setBlockedProcesses(ArrayList<Process> blockedProcesses) {
		this.blockedProcesses = blockedProcesses;
	}
	public ArrayList<Process> getStartProcesses() {
		return startProcesses;
	}
	public void setStartProcesses(ArrayList<Process> startProcesses) {
		this.startProcesses = startProcesses;
	}
	public ArrayList<Process> getEndedProcesses() {
		return endedProcesses;
	}
	public void setEndedProcesses(ArrayList<Process> endedProcesses) {
		this.endedProcesses = endedProcesses;
	}
	/**
	 * METODO PARA AGREGAR UN PROCESO CON LOS SIGUIENTES PARAMETROS
	 * @param id
	 * @param name
	 * @param time
	 */
	public void addProcess(int id, String name,  int time){
		startProcesses.add(new Process(id, name, time));		
	}
	/**
	 * METODO PARA AGREGAR UN PROCESO CON LOS SIGUIENTES PARAMETROS
	 * @param id
	 * @param name
	 * @param time
	 * @param blockedTime
	 * @param blockedDuration
	 */
	public void addProcess(int id, String name, int time, int blockedTime, int blockedDuration){
		startProcesses.add(new Process(id, name,  time, blockedTime, blockedDuration));		
	}
	
}
