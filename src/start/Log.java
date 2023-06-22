package start;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {

	public enum Tipo {
		INFO, DEBUG, ERROR
	}

	private final String ERROR_LOG_FILE = "errorlog.txt";

	public Log(String txt) {
		this(Tipo.INFO, txt);
	}

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

	private void guardarErrorLog(String error) {
		LocalDateTime tiempo = LocalDateTime.now();
		String marcaDeTiempo = tiempo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

		String logEntry = "[" + marcaDeTiempo + "]: " + error;

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(ERROR_LOG_FILE, true))) {
			writer.write(logEntry);
			writer.newLine();
		} catch (IOException e) {
			System.err.println("Error al guardar en el archivo de log: " + e.getMessage());
		}
	}
}
