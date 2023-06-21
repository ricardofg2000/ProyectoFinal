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

public class JsonController {
	
	private Gson g = new GsonBuilder().setPrettyPrinting().create();


	public <T> void writeJsonInFile(String fileName, T obj) {
		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(fileName));
			bufferedWriter.write(g.toJson(obj));
		}
		catch (IOException e) { System.err.println(e.getMessage()); }
		finally {
			try { if (bufferedWriter != null) bufferedWriter.close();   }
			catch (IOException e) { System.err.println(e.getMessage()); }
		}
	}
	

	@SuppressWarnings("unchecked")
	public List<Producto> leerListProductos(String fileName) {
	    List<Producto> productos = null;
	    BufferedReader bufferedReader = null;
	    try {
	        bufferedReader = new BufferedReader(new FileReader(fileName));
	        productos = g.fromJson(bufferedReader, new TypeToken<List<Producto>>(){}.getType());
	    } catch (FileNotFoundException e) {
	        System.err.println(e.getMessage());
	    } finally {
	        try {
	            if (bufferedReader != null)
	                bufferedReader.close();
	        } catch (IOException e) {
	            System.err.println(e.getMessage());
	        }
	    }
	    return productos;
	}

}
