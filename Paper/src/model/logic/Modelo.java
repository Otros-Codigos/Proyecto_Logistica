package model.logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import model.data_structures.Arco;
import model.data_structures.DijkstraSP;
import model.data_structures.Graph;
import model.data_structures.ListaEnlazadaStack;
import model.data_structures.TablaHashSondeoLineal;
import model.data_structures.Vertice;

public class Modelo 
{
	private Vertice vertiAgregar;
	private Vertices_Info infoVertice;
	private ArrayList<Integer> vertices = new ArrayList<>();
	
	private Graph grafo = new Graph(1);

	// ...................................................................................LECTURA de TXT 

	public void leerTxtVertix(String archivo) throws FileNotFoundException, IOException 
	{
		FileReader f = new FileReader(archivo);
		BufferedReader b = new BufferedReader(f);
		String cadena = b.readLine();

		while(cadena != null) 
		{	    	  
			infoVertice = new Vertices_Info(0, 0);
			vertiAgregar = new Vertice(0, infoVertice);

			String[] partes = cadena.split(",");

			int ID = Integer.parseInt(partes[0].trim());
			double Longitud = Double.parseDouble(partes[1].trim());
			double Latitud = Double.parseDouble(partes[2].trim());
			
			infoVertice.asignarLon(Longitud);
			infoVertice.asignarLat(Latitud);

			vertiAgregar.asignarId(ID);
			vertiAgregar.cambiarInfo(infoVertice);

			grafo.addVertex(ID, vertiAgregar);
			vertices.add(ID);
			cadena = b.readLine();

		}

		int totalVertices = grafo.darV()-1;
		System.out.println("Total de Vertices: " + totalVertices);
		b.close(); 
	}

	public void leerTxtArc(String archivo) throws FileNotFoundException, IOException 
	{
		double costo = 0.0;

		FileReader f = new FileReader(archivo);
		BufferedReader b = new BufferedReader(f);
		String cadena = b.readLine();

		while( cadena != null) 
		{

			String[] partes = cadena.split(",");
			int origen = Integer.parseInt(partes[0].trim());
			int vecino = Integer.parseInt(partes[1].trim());
			costo = Double.parseDouble(partes[2].trim());

			grafo.addEdge(origen, vecino, costo);

			cadena = b.readLine();
		}

		System.out.println("Numero de arcos: " + grafo.darE());
		b.close(); 
	}
	
	// .. Dar listado de vertices

	public ArrayList<Integer> darVerticesDelGrafo()
	{
		return vertices;
	}


	// .......................................................... RUTA MÁS CORTA


	public Graph SPDosUbicaciones (int origen, int destino)
	{
		int totalVer = 1;
		double costo = 0;
		double distancia = 0;

		TablaHashSondeoLineal vertices =  grafo.vertis;
		Vertice inicio = (Vertice) vertices.getSet(origen);
		Vertice fin = (Vertice) vertices.getSet(destino);


		Graph mapita = new Graph(1);
		mapita.addVertex(inicio.darId(), inicio);
		mapita.addVertex(fin.darId(), fin);

		System.out.println("Vertice de inicio: " + inicio.darId());
		System.out.println("Vertice destino: " + fin.darId());

		// ... BUSCO EL CAMINO MÁS CORTO.
		Stopwatch timer = new Stopwatch();
		
		DijkstraSP SP = new DijkstraSP(grafo, inicio);
		ListaEnlazadaStack<Arco> ruta = SP.pathTo(fin);
		
		double time = timer.elapsedTime();
		System.out.println("Tiempo tomado en encontrar la ruta : " + time + "\n---------------");

		if (ruta != null)
		{			
			while(ruta.darTamaño() > 0)
			{
				Arco actual = (Arco) ruta.pop();

				Vertice inici = actual.darInicial();
				Vertice fini = actual.darFinal();

				int idInicio = (int) actual.darInicial().darId();
				int idDestino = (int) actual.darFinal().darId();
				double costoActu = actual.darCostoHaversiano();

				mapita.addVertex(idInicio, inici);
				mapita.addVertex(idDestino, fini);

				mapita.addEdge(idInicio, idDestino, costoActu);

				Vertices_Info infoDestino = (Vertices_Info) actual.darFinal().darInfo();
				double lon = infoDestino.darLon();
				double lat = infoDestino.darLat();

				System.out.println("Vamos en: " + idDestino);

				totalVer++;
				costo += actual.darCostoHaversiano();

			}

			System.out.println("-------------");
			System.out.println("El número total de vertices de la ruta es: "+ totalVer);
			System.out.println("La distancia/tiempo total es: "+ costo);
		}
		else 
		{
			System.out.println("No existe camino entre esos dos vertices.");
		}

		return mapita;

	}



}

