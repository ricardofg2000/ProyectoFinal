package start;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * La clase Log se utiliza para imprimir y almacenar registros de eventos en diferentes niveles de detalle.
 */
public class Log {

	/**
	 * Los diferentes tipos de registro que se pueden generar.
	 */
	public enum Tipo {
		INFO, DEBUG, ERROR
	}

	/** 
	 * El archivo de registro de errores.
	 */
	private final String ERROR_LOG_FILE = "errorlog.txt";

	/**
	 * Crea un nuevo registro de log con el nivel de detalle INFO y el mensaje especificado.
	 *
	 * @param txt el mensaje del log
	 */
	public Log(String txt) {
		this(Tipo.INFO, txt);
	}

	/**
	 * Crea un nuevo registro de log con el tipo y el mensaje especificados.
	 *
	 * @param tipo el tipo de log (INFO, DEBUG, ERROR)
	 * @param txt el mensaje del log
	 */
	public Log(Tipo tipo, String txt) {
		switch (tipo) {
		case INFO:
			System.out.println("INFO : " + txt);
			break;
		case DEBUG:
			System.out.println("DEBUG: " + txt);
			break;
		case ERROR:
			System.out.println("ERROR: " + txt);
			guardarErrorLog(txt);
			break;
		}
	}

	/**
	 * Guarda el registro de error en el archivo de log.
	 *
	 * @param error el mensaje de error a guardar
	 */
	private void guardarErrorLog(String error) {
		LocalDateTime tiempo = LocalDateTime.now();
		String marcaDeTiempo = tiempo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

		String logEntry = "[" + marcaDeTiempo + "]: " + error;

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(ERROR_LOG_FILE, true))) {
			writer.write(logEntry);
			writer.newLine();
		} catch (IOException e) {
			Log log = new Log(Log.Tipo.ERROR, "Error al guardar en el archivo de log: " + e.getMessage());
			System.err.println("Error al guardar en el archivo de log: " + e.getMessage());
		}
	}
}
