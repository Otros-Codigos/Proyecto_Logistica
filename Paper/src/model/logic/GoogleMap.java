package model.logic;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import model.data_structures.Arco;
import model.data_structures.Graph;
import model.data_structures.ListaEnlazadaQueue;
import model.data_structures.Node;
import model.data_structures.TablaHashSondeoLineal;
import model.data_structures.Vertice;

public class GoogleMap {

	public void imprmirElMejorGrafoDeLaHistoria(Graph base, ArrayList<Integer> listaVertices)
	{

		TablaHashSondeoLineal vertex = base.vertis;
		ListaEnlazadaQueue arcos = base.arcos;

		String colVerti = darColorAleatorio();
		String colArco = darColorAleatorio();

		try 
		{
			FileWriter file = new FileWriter("./paint/Google Api/vertices.js");

			file.write("citymap = { \n");

			for (int i = 0; i< listaVertices.size();i++)
			{
				Vertice aux = (Vertice) vertex.getSet(listaVertices.get(i));

				if(aux != null)
				{
					Vertices_Info info = (Vertices_Info) aux.darInfo();

					file.write("\t" + aux.darId() + ": {\n");
					file.write("\t center: {lat: " + info.darLat() + ", lng: " + info.darLon() + "},  \n");
					file.write("\t population: " + 25 + ",   \n");
					file.write("\t color: '" + colVerti + "', \n");
					file.write("\t titulo: " + "'Hola'" + "\n");

					if(i == base.darV()-1) file.write("\t } \n");
					else 			       file.write("\t }, \n");
				}
			}

			file.write("}");

			file.close();

		}
		catch (Exception e){
			e.printStackTrace();
		}

		try 
		{
			FileWriter file = new FileWriter("./paint/Google Api/arcos.js");

			file.write("cityroad = { \n");

			Node actual = arcos.darPrimerElemento();

			while (actual!=null)
			{
				Arco aux= (Arco) actual.data;

				Vertice inicio = aux.darInicial();
				Vertices_Info infoInicio = (Vertices_Info) inicio.darInfo();

				Vertice fin = aux.darFinal();
				Vertices_Info infoFin = (Vertices_Info) fin.darInfo();

				if(inicio != null & fin != null)
				{
					file.write("\t" + inicio.darId() + fin.darId() + ": {\n");
					file.write("\t inicio: {lat: " + infoInicio.darLat() + ", lng: " + infoInicio.darLon() + "},  \n");
					file.write("\t fin: {lat: " + infoFin.darLat() + ", lng: " + infoFin.darLon() + "},  \n");
					file.write("\t color: '" + colArco + "', \n");

					if (actual.darSiguiente()==null)
					{
						file.write("\t  }  \n");
					}
					else
					{
						file.write("\t  }  ,\n");
					}
				}

				actual = actual.darSiguiente();
			}

			file.write("}");

			file.close();

		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		abrirArchivo();

	}


	private String darColorAleatorio() 
	{
		Color color = new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
		String stringColor = Integer.toHexString(color.getRGB());
		return stringColor;
	}

	private void abrirArchivo()
	{
		String osName = System.getProperty("os.name");
		File file = new File("./paint/Google Api/grafoMapGoogle.html");
		try {

			if (osName.startsWith("Windows")) 			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file.getAbsolutePath());
			else if (osName.startsWith("Mac OS X")) 	Runtime.getRuntime().exec("open " + file.getAbsolutePath());
			else 										System.out.println("Please open a browser and go to " + file.getAbsolutePath());

		} 
		catch (IOException e) {
			System.out.println("Failed to start a browser to open the url " + file.getAbsolutePath());
			e.printStackTrace();
		}
	}

}
