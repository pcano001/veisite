package com.veisite.utils.tasks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tarea que se ejecuta y reporta su progreso a un ProgreeReporte
 * @author josemaria
 *
 */
public abstract class ProgressableTask {
	
	private static Logger logger = LoggerFactory.getLogger(ProgressableTask.class);
	
	private static final int TASK_NOINITIATED = 0;
	private static final int TASK_RUNNING = 1;
	private static final int TASK_DONE = 2;
	private static final int TASK_ERROR = 3;
	private static final int TASK_CANCELED = 4;
	
	/**
	 * Guarda un titulo descriptivo de la tarea
	 */
	private String title;
	
	/**
	 * Guarda mensaje de la ejecución actual de la area, es decir lo que esta haciendo
	 */
	private String jobDoing = "";
	
	/**
	 * Guarda mensaje de error
	 */
	private String errorMessage = "";
	
	/**
	 * Guardaa la excepcion ocurrida en la ejecucion de la tarea
	 */
	private Throwable exception;
	
	/**
	 * Mantiene el estado de la tarea.
	 */
	private int state=TASK_NOINITIATED;
	
	/**
	 * Señala si la tarea no conoce el nivel de progreso
	 */
	private boolean indeterminateProgress=true;

	/**
	 * Lista de listener que escuchan el progreso de la tarea.
	 */
	private List<ProgressEventListener> _listeners = new ArrayList<ProgressEventListener>();

	/**
	 * Guarada el nivel de avance de la tarea
	 */
	private int avance=0;

	/**
	 * Metodo que deben implementar las subclases para hacer la ejecución real de la 
	 * tarea.
	 * @throws Throwable
	 */
	public abstract void doInBackground() throws Throwable;

	
	public ProgressableTask() {
	}

	public ProgressableTask(String title, String jobDoing) {
		this.title = title;
		this.jobDoing=jobDoing;
	}
	
	
	/**
	 * Metodo para comenzar la ejecución de la tarea en background
	 * @throws GaslabException 
	 */
	public void start() throws TaskException {
		if (this.state==TASK_RUNNING) return; 
		if (this.state!=TASK_NOINITIATED) 
			throw new TaskException("La tarea ya ha sido ejecutada");
		Thread th = new Thread(new Runnable() {
			public void run() {
				try {
					initTask();
					doInBackground();
					if (!isError()) endTask();
				} catch (Throwable t) {
					logger.debug("Error en ejecución de tarea",t);
					errorTask(t.getMessage(), t);
				}
			}
		});
		th.start();
	}
	
	private void initTask() {
		this.state = TASK_RUNNING;
		ProgressEvent evt = new ProgressEvent(this, ProgressEvent.JOB_INIT, 0);
		fireEvent(evt);
	}
	
	private void endTask() {
		this.state = TASK_DONE;
		ProgressEvent evt = new ProgressEvent(this, ProgressEvent.JOB_END, 100);
		fireEvent(evt);
	}
	
	private void errorTask(String errorMessage, Throwable exception) {
		this.state = TASK_ERROR;
		setErrorMessage(errorMessage);
		setException(exception);
		ProgressEvent evt = new ProgressEvent(this, ProgressEvent.JOB_ERROR, this.avance, exception);
		fireEvent(evt);
	}
	
	public void notifyProgress(int avance, String jobDoing) {
		this.avance=Math.min(100, Math.max(0,avance));
		if (jobDoing!=null) this.jobDoing=jobDoing;
		ProgressEvent evt = new ProgressEvent(this, ProgressEvent.JOB_RUNNIG, avance);
		fireEvent(evt);
	}
	
	public void notifyError(String jobDoing, String errorMessage, Throwable exception) {
		if (jobDoing!=null) this.jobDoing=jobDoing;
		errorTask(errorMessage, exception);
	}

	
	/**
	 * @return the initiated
	 */
	public boolean isRunning() {
		return (state==TASK_RUNNING);
	}

	/**
	 * @return the onError
	 */
	public boolean isError() {
		return (state==TASK_ERROR);
	}

	/**
	 * @return the canceled
	 */
	public boolean isCanceled() {
		return (state==TASK_CANCELED);
	}

	/**
	 * @return the finished
	 */
	public boolean isFinished() {
		return (state==TASK_DONE);
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the exception
	 */
	public Throwable getException() {
		return exception;
	}

	/**
	 * @param exception the exception to set
	 */
	public void setException(Throwable exception) {
		this.exception = exception;
	}

	/**
	 * Establece el mensaje de error y una exception
	 * @param errorMessage
	 * @param exception
	 */
	public void setError(String errorMessage, Throwable exception) {
		setErrorMessage(errorMessage);
		setException(exception);
	}


	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}


	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}


	/**
	 * @return the jobDoing
	 */
	public String getJobDoing() {
		return jobDoing;
	}


	/**
	 * @param jobDoing the jobDoing to set
	 */
	public void setJobDoing(String jobDoing) {
		this.jobDoing = jobDoing;
	}


	/**
	 * @return the indeterminateProgress
	 */
	public boolean isIndeterminateProgress() {
		return indeterminateProgress;
	}


	/**
	 * @param indeterminateProgress the indeterminateProgress to set
	 */
	public void setIndeterminateProgress(boolean indeterminateProgress) {
		this.indeterminateProgress = indeterminateProgress;
	}


	/**
	 * Gestión de eventos.
	 * @param listener
	 */
	public synchronized void addEventListener(ProgressEventListener listener) {
		_listeners.add(listener);
	}

	public synchronized void removeEventListener(ProgressEventListener listener) {
		_listeners.remove(listener);
	}

	// call this method whenever you want to notify
	// the event listeners of the particular event
	private synchronized void fireEvent(ProgressEvent evt) {
		Iterator<ProgressEventListener> i = _listeners.iterator();
		while (i.hasNext()) {
			((ProgressEventListener) i.next()).taskReport(evt);
		}
	}

}
