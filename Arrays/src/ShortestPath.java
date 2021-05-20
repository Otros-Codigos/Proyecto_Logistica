
import java.util.*;

import java.lang.*;
import java.io.*;
  
class ShortestPath {
	
	static int V = 0;
    
    public ShortestPath (int numV)
    {
    	this.V = numV;
    }
    
    int minDistance(double dist[], Boolean sptSet[])
    {
        double min = Integer.MAX_VALUE;
		int min_index = -1;
  
        for (int v = 0; v < V; v++)
            if (sptSet[v] == false && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }
  
        return min_index;
    }
  
    void printSolution(double[] dist)
    {
        System.out.println("Vertex \t\t Distance from Source");
        for (int i = 0; i < V; i++)
        {
        	if (dist[i] < 10000)
        	{
        		System.out.println(i + " \t\t " + dist[i]);
        	}
        }
            
    }
  
    void dijkstra(double graph[][], int src)
    {
    	Stopwatch timer = new Stopwatch();
    	
        double dist[] = new double[V]; // The output array. dist[i] will hold the shortest distance from src to i
        Boolean sptSet[] = new Boolean[V];

        for (int i = 0; i < V; i++) {
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }

        dist[src] = 0;
  
        for (int count = 0; count < V - 1; count++) {
        	
            double u = minDistance(dist, sptSet);
            sptSet[(int) u] = true;
            
            for (int v = 0; v < V; v++)
                if (!sptSet[v] && graph[(int) u][v] != 0 && dist[(int) u] != Integer.MAX_VALUE && dist[(int) u] + graph[(int) u][v] < dist[v])
                    dist[v] = dist[(int) u] + graph[(int) u][v];
        }
        
        double time = timer.elapsedTime();
		System.out.println("Tiempo tomado en encontrar la ruta : " + time + "\n---------------");

        printSolution(dist);
    }
  
    public static void main(String[] args) throws IOException
    {
        /* Let us create the example graph discussed above */
    	double graph[][] = new double[10000][10000]; 
    	double costo = 0.0;
    	String ruta = "./data/sub_grafo_bogota_en_arcos_haversiano_dos.txt";

		FileReader f = new FileReader(ruta);
		BufferedReader b = new BufferedReader(f);
		String cadena = b.readLine();

		while(cadena != null) 
		{
			String[] partes = cadena.split(",");
			int origen = Integer.parseInt(partes[0].trim());
			int vecino = Integer.parseInt(partes[1].trim());
			costo = Double.parseDouble(partes[2].trim());
			
			graph[origen][vecino] = costo;
			cadena = b.readLine();
		}
                                      
        ShortestPath t = new ShortestPath(10000);
        t.dijkstra(graph, 162);
        
    }
}
// This code is contributed by Aakash Hasija