package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import model.data_structures.Node;
import model.logic.Maps;
import model.logic.Modelo;
import view.View;

public class Controller {

	private Modelo modelo;
	private View view;
	
	public final static String VERTIBOB = "./data/bogota_vertices.txt";
	public final static String ARCAJUANJO = "./data/bogota_arcos.txt";
	public final static String ESTACIONES = "./data/estacionpolicia.geojson";
	public final static String CREADO="./data/grafoCreado.json";

	public Controller ()
	{
		view = new View();
		modelo = new Modelo();
	}

	public void run() throws FileNotFoundException, IOException 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		
		String dato = "";
		String respuesta = "";

		while( !fin )
		{
			view.printMenu();

			int option = lector.nextInt();

			int nHeap;


			int capIni;
			switch(option)
			
			{
			case 1:

				modelo.leerTxtVertix(VERTIBOB);
				modelo.leerTxtArc(ARCAJUANJO);
				
				System.out.println("=======================================");
				System.out.println("Generando TXT de arcos Bogotá Logistica por Sector");
				modelo.prueba();
				
				System.out.println("Generando TXT de arcos Bogotá Logistica por Id");
				modelo.pruebaDos();
				System.out.println("Se genero el TXT");
				System.out.println("=======================================");

				break;
				
			case 5:

				view.printMessage("--------- \n Hasta pronto !! \n---------"); 
				lector.close();
				fin = true;

				break;	

			default: 

				view.printMessage("--------- \n Opción Invalida !! \n---------");

				break;

			}
		}

	}	
}
