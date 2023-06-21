package start;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {

    public enum Tipo {INFO, DEBUG, ERROR}

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final String ERROR_LOG_FILE = "errorlog.json";

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

        EntradaError logErrorEntry = new EntradaError(marcaDeTiempo, error);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ERROR_LOG_FILE, true))) {
            writer.write(gson.toJson(logErrorEntry));
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error al guardar en el archivo de log: " + e.getMessage());
        }
    }

    private static class EntradaError {
        private String fecha;
        private String error;

        public EntradaError(String fecha, String error) {
            this.fecha = fecha;
            this.error = error;
        }
    }
}
