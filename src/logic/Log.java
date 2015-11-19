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
 * ESTA CLASE PERMMITE CREAR UN LOG EN DONDE SE VA AGREGANDO INFORMACION DE LOS ESTADOS
 * POR LOS QUE PASA UN PROCESO
 *
 */
import java.util.Date;
import javax.swing.JTextArea;

public class Log extends JTextArea{
	
	
	private static final long serialVersionUID = 1L;
	/**
	 * OBJETO DE LA CLASE DE TIPO ESTATICO
	 */
	private static Log log;
	/**
	 * OBJETO DE TIPO DATE
	 */
	Date sysdate;
	/**
	 * PATRON SINGLENTON
	 * @return
	 */
	public static Log getLog(){
		if (log==null) {
			log=new Log();
			return log;
		} else {
			return log;
		}
	}
	/**
	 * CONSTRUCTOR VACIO
	 */
	private Log(){
	}
	/**
	 * METODO PARA AGREGAR AL LOG LOS PROCESOS LISTOS O CREADOS
	 * @param p
	 */
	public void readyMessage(Process p) {
		sysdate=new Date();
		this.setText(this.getText()+"\n"+sysdate.toString()+" El proceso con ID "+p.getId()+" está " +State.LISTO);
	}
	/**
	 * METODO PARA AGREGAR AL LOG LOS PROCESOS QUE PASAN A ESTADO BLOQUEADO
	 * @param p
	 */
	public void blockedMessage(Process p) {
		sysdate=new Date();
		this.setText(this.getText()+"\n"+sysdate.toString()+" El proceso con ID "+p.getId()+" está " +State.BLOQUEADO);
	}
	/**
	 * METODO PARA AGREGAR AL LOG CUANDO UN PROCESO SE ESTA EJECUTANDO
	 * @param p
	 */
	public void execMessage(Process p) {
		sysdate=new Date();
		this.setText(this.getText()+"\n"+sysdate.toString()+" El proceso con ID "+p.getId()+" está " +State.EN_EJECUCION);
	}
	/**
	 * METODO PARA AGREGAR AL LOG CUANDO UN PROCESO HA FINALIZADO
	 * @param p
	 */
	public void endedMessage(Process p) {
		sysdate=new Date();
		this.setText(this.getText()+"\n"+sysdate.toString()+" El proceso con ID "+p.getId()+" está " +State.FINALIZADO);
	}

}
