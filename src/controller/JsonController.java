package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import model.Producto;
import start.Log;

/**
 * La clase JsonController proporciona métodos para escribir y leer datos en formato JSON.
 */
public class JsonController {

    /** El objeto Gson utilizado para la serialización y deserialización JSON. */
    private Gson g = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Escribe un objeto JSON en un archivo.
     *
     * @param <T> el tipo genérico
     * @param fileName el nombre del archivo
     * @param obj el objeto a escribir en formato JSON
     */
    public <T> void writeJsonInFile(String fileName, T obj) {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(fileName));
            bufferedWriter.write(g.toJson(obj));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (bufferedWriter != null)
                    bufferedWriter.close();
            } catch (IOException e) {
                Log log = new Log(Log.Tipo.ERROR, e.getMessage());
            }
        }
    }

    /**
     * Lee una lista de productos desde un archivo JSON.
     *
     * @param fileName el nombre del archivo
     * @return una lista de productos
     */
    @SuppressWarnings("unchecked")
    public List<Producto> leerListProductos(String fileName) {
        List<Producto> productos = null;
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(fileName));
            productos = g.fromJson(bufferedReader, new TypeToken<List<Producto>>() {
            }.getType());
        } catch (FileNotFoundException e) {
            Log log = new Log(Log.Tipo.ERROR, e.getMessage());
        } finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException e) {
                Log log = new Log(Log.Tipo.ERROR, e.getMessage());
            }
        }
        return productos;
    }

}
