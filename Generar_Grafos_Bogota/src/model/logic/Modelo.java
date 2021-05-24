package model.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;

import model.data_structures.Arco;
import model.data_structures.Graph;
import model.data_structures.ListaEnlazadaQueue;
import model.data_structures.Node;
import model.data_structures.TablaHashSondeoLineal;
import model.data_structures.Vertice;


public class Modelo 
{
	//Atributos necesarios para la lectura desde txt
	private Vertice vertiAgregar;
	private Vertices_Bogota_Info infoVertice;
	private Haversine costoHaversiano;

	//El grafo, su nombre lo dice todo
	private Graph cositaBienHecha = new Graph(1);

	//Atributos encesarios para la carga del JSON de las estaciones de policía
	private String parteDelaEstacion;
	private EstPol porAgregar; 
	private ListaEnlazadaQueue estaciones;
	private boolean coordenadas=false;

	//Atributos necesarios para la carga del JSON del grafo
	private String parteDelVerti;
	private Vertices_Bogota_Info infoPorAgregar;
	private Vertice vertiPorAgregar;
	private ArrayList<Vertice<Integer, Vertices_Bogota_Info>> listaVertices=new ArrayList<Vertice<Integer, Vertices_Bogota_Info>>();
	private boolean coordenadasGrafo=false;

	private int origenPorIngresar, destinoPorIngresar;
	private double costoPorAgregar;

	///////////////////////////////////////////////////////Constructor

	public Modelo()
	{
		parteDelaEstacion = "";
		parteDelVerti="";
	}

	//////////////////////////////////////////////////////Dar

	public Graph<Integer, Vertices_Bogota_Info> darGrafo()
	{
		return cositaBienHecha;
	}

	public ListaEnlazadaQueue<EstPol> darEstaciones()
	{
		return estaciones;
	}

	/////////////////////////////////////////////////////////////////////////////////LECTURA de TXT 

	public void leerTxtVertix(String archivo) throws FileNotFoundException, IOException 
	{
		FileReader f = new FileReader(archivo);
		BufferedReader b = new BufferedReader(f);
		String cadena = b.readLine();

		while( cadena != null) 
		{	    	  
			infoVertice = new Vertices_Bogota_Info(0, 0);
			vertiAgregar = new Vertice(0, infoVertice);

			String[] partes = cadena.split(",");

			int ID = Integer.parseInt(partes[0]);
			double Longitud = Double.parseDouble(partes[1]);
			double Latitud = Double.parseDouble(partes[2]);

			infoVertice.asignarLon(Longitud);
			infoVertice.asignarLat(Latitud);

			vertiAgregar.asignarId(ID);
			vertiAgregar.cambiarInfo(infoVertice);

			cositaBienHecha.addVertex(ID, vertiAgregar);
			cadena = b.readLine();

		}

		int totalVertices = cositaBienHecha.darV()-1;
		System.out.println("Total de Vertices: " + totalVertices + "\n--------------");
		b.close(); 
	}

	public void leerTxtArc(String archivo) throws FileNotFoundException, IOException 
	{
		costoHaversiano = new Haversine();
		double costo = 0.0;

		FileReader f = new FileReader(archivo);
		BufferedReader b = new BufferedReader(f);
		String cadena = b.readLine();

		while( cadena != null) 
		{
			if(!cadena.contains("#"))
			{
				String[] partes = cadena.split(" ");
				int origen = Integer.parseInt(partes[0]);

				for (int i = 1; i< partes.length; i++)
				{
					int vecino = Integer.parseInt(partes[i]);

					if(cositaBienHecha.existeVertice(origen) && cositaBienHecha.existeVertice(vecino))
					{
						Vertices_Bogota_Info infoOrigen = (Vertices_Bogota_Info) cositaBienHecha.getInfoVertex(origen);
						Vertices_Bogota_Info infoVecino = (Vertices_Bogota_Info) cositaBienHecha.getInfoVertex(vecino);

						costo = costoHaversiano.distance(infoOrigen.darLat(), infoOrigen.darLon(), infoVecino.darLat(), infoVecino.darLon());

						cositaBienHecha.addEdge(origen, vecino, costo);
					}
				}
			}
			cadena = b.readLine();
		}

		System.out.println("Numero de arcos: " + cositaBienHecha.darE() + "\n---------------");
		b.close(); 
	}

	////////////////////////////////////////////////////////////////////////////////////

	public void prueba ()
	{
		ListaEnlazadaQueue arcos = cositaBienHecha.arcos;
		Node actual = arcos.darPrimerElemento();
		String ruta = "./data/sub_grafo_bogota_en_arcos_haversiano.txt";
		String contenido = "";

		try {

			File file = new File(ruta);
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			int c = 0;

			while (actual != null)
			{
				Arco aux= (Arco) actual.data;

				Vertice vertiActual = aux.darInicial();
				Vertices_Bogota_Info infoVertiActu = (Vertices_Bogota_Info) vertiActual.darInfo();

				double lon = infoVertiActu.darLon();
				double lat = infoVertiActu.darLat();

				//Si esta dentro del rango
				if(lon>=-74.094723 && lon<=-74.062707 && lat>=4.597714 && lat<=4.621360)
				{
					contenido = aux.darInicial().darId() + "," + aux.darFinal().darId() + "," + aux.darCostoHaversiano() + "\n";
					bw.write(contenido);
					c++;
				}
				
				actual= actual.darSiguiente();
			}

			System.out.println(" ~ El archivo contiene : " + c + " arcos.");
			bw.close();

		} catch (Exception e) { e.printStackTrace(); }

	}
	
	public void pruebaDos ()
	{
		ListaEnlazadaQueue arcos = cositaBienHecha.arcos;
		Node actual = arcos.darPrimerElemento();
		String ruta = "./data/sub_grafo_bogota_en_arcos_haversiano_dos.txt";
		String contenido = "";

		try {

			File file = new File(ruta);
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			int c = 0;

			while (actual != null)
			{
				Arco aux= (Arco) actual.data;
				int id_inicio = (int) aux.darInicial().darId();
				int id_fin = (int) aux.darFinal().darId();
				
				//Si esta dentro del rango
				if(id_inicio < 10000 & id_fin < 10000)
				{
					contenido = aux.darInicial().darId() + "," + aux.darFinal().darId() + "," + aux.darCostoHaversiano() + "\n";
					bw.write(contenido);
					c++;
				}
				
				actual= actual.darSiguiente();
			}

			System.out.println(" ~ El archivo contiene : " + c + " arcos.");
			bw.close();

		} catch (Exception e) { e.printStackTrace(); }

	}

}

