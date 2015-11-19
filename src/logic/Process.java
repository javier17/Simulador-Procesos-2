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
 * ESTA CLASE PERMMITE CREAR PROCESOS CON SUS ATRIBUTOS
 *
 */

public class Process {

	/**
	 * ID DEL PROCESO
	 */
	private int id;
	/**
	 * NOMBRE DEL PROCESO
	 */
	private String name;
	/**
	 * TIEMPO QUE VA A DURAR EL PROCESO
	 */
	private int time; //In seconds 
	/**
	 * OBJETO DE LA CLASE DE TIPO ENUMERADO
	 */
	private State state;
	/**
	 * 
	 */
	private int elapsedTime;
	/**
	 * TIEMPO EN QUE EL PROCESO SE VA A BLOQUEAR
	 */
	private int blockedTime;
	/**
	 * TIEMPO QUE VA A DURAR BLOQUEADO
	 */
	private int blockedDuration;
	/**
	 * 
	 */
	private Thread th;
	/**
	 * CONSTRUCTOR PARA CREAR UN PROCESO CON LOS SIGUIENTES PARAMETROS
	 * @param id
	 * @param name
	 * @param time
	 */
	public Process(int id, String name, int time) {
		this.id=id;
		this.name=name;
		this.time=time;		
		this.elapsedTime=0;
		this.blockedTime=-1;
		this.blockedDuration=-1;
		Log.getLog().readyMessage(this);
		
	}
	/**
	 * CONSTRUCTOS PARA CREAR UN PROCESO CONLOS SIGUIENTES PARAMETROS
	 * @param id
	 * @param name
	 * @param time
	 * @param blockedTime
	 * @param blockedDuration
	 */
	public Process(int id, String name,  int time, int blockedTime, int blockedDuration) {
		this.id=id;
		this.name=name;
		this.time=time;		
		this.elapsedTime=0;
		this.blockedTime=blockedTime;
		this.blockedDuration=blockedDuration;
		Log.getLog().readyMessage(this);
		th=new Thread(new Runnable() {
			public void run() {
				while (getBlockedDuration()>0) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					setBlockedDuration(getBlockedDuration()-1);
				}
				blockedToReady();		
			}
		});
	}
	/**
	 * METODO PARA GESTIONAR LAS LISTAS DE LOS PROCESOS EN EJECUCION
	 */
	public void blockedToReady(){
		blockedTime=-1;
		Log.getLog().readyMessage(this);
		ProcessesList.getProcessesList().getStartProcesses().add(this);
		ProcessesList.getProcessesList().getBlockedProcesses().remove(this);
		
	}
	
	public String toString() {
		return "Proceso " + id + ", " + name 
				 + ", " + time + " segundos";
	}
	
	public String blockedToString() {
		return "Proceso " + id + ", " + name 
				 + ", " + blockedDuration + " segundos";
	}
	
	/**
	 * METODOS GET Y SET DE LOS ATRIBUTOS DE LA CLASE
	 * @return
	 */

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public int getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(int elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public int getBlockedTime() {
		return blockedTime;
	}

	public void setBlockedTime(int blockedTime) {
		this.blockedTime = blockedTime;
	}

	public int getBlockedDuration() {
		return blockedDuration;
	}

	public void setBlockedDuration(int blockedDuration) {
		this.blockedDuration = blockedDuration;
	}

	public Thread getTh() {
		return th;
	}
	
	
}
