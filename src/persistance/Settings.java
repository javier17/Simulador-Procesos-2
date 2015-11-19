package persistance;
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
 * ESTA CLASE QUE PERMMITE CAMBIAR DE ESTADO UN PROCESO
 *
 */

public class Settings {
	
	/**
	 * OBJETO ESTATICO DE LA CLASE 
	 */
	private static Settings settings;
	/**
	 * METODO DE TIPO BOOLEANO
	 */
	private boolean status=false;;
	/**
	 * METOD DE TIPO BOOLEANO PARA OBSERVAR SU ESTADO
	 * @return
	 */
	public boolean isStatus() {
		return status;
	}
	/**
	 * METODO PARA CAMBIAR EL ESTADO DE UN PROCESO
	 */
	public void changeStatus() {
		if (status) {
			status=false;
			System.out.println("El programa ha terminado.");
		}else {
			status=true;
			System.out.println("El programa ha iniciado.");
		}
	}
	/**
	 * PATRON SINGLENTON
	 * @return
	 */
	public static Settings getSettings(){
		if (settings==null) {
			settings=new Settings();
			return settings;
		} else {
			return settings;
		}
	}

}
