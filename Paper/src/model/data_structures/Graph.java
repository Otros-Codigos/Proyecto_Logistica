package model.data_structures;

import java.util.ArrayList;
import java.util.Iterator;

public class Graph<K extends Comparable<K>,V extends Comparable<V>> 
{
	//Declaración 

	private int V;									//Cantidad de vertices
	private int E;									//Cantidad de arcos

	public TablaHashSondeoLineal<K,V> vertis; 		//K es el ID, V es su info-valores. --> La tabla son los vertices!
	public ListaEnlazadaQueue<Arco> arcos;			//Son los adjacentes conectados por arcos.

	private int conectados;							//Indice del componenete conectado.			

	//Constructor

	public Graph (int V)
	{
		this.V = V;
		this.E = 0;

		vertis  = new TablaHashSondeoLineal<K, V>(V);
		conectados = 0;
		arcos=new ListaEnlazadaQueue<Arco>();
	}

	//Dar atributos

	public int darV()
	{
		return V;
	}

	public int darE()
	{
		return E;
	}

	// ....................................................................... Metodos

	public void addEdge (K idVerticeInicial, K idVerticeFinal, double cost)
	{
		
		if( vertis.contains(idVerticeInicial) || vertis.contains(idVerticeFinal))
		{			
			Vertice inicial = (Vertice) vertis.getSet(idVerticeInicial);
			Vertice fin = (Vertice) vertis.getSet(idVerticeFinal);

			Arco i_f = new Arco(inicial, fin, cost);
			// Arco f_i = new Arco(fin, inicial, cost);

			inicial.agregarArco(i_f);
			// fin.agregarArco(f_i);
			
			arcos.enqueue(i_f);

			E++;
		}
	}	

	public V getInfoVertex (K idVertice)
	{
		V infoBuscado = null;

		if(vertis.contains(idVertice))
		{
			Vertice buscado = (Vertice) vertis.getSet(idVertice);
			infoBuscado = (V) buscado.darInfo();
		}

		return infoBuscado;
	}

	public void setInfoVertex (K idVertice, V infoVertice)
	{
		if (vertis.contains(idVertice))
		{
			Vertice buscarPaModi = (Vertice) vertis.getSet(idVertice);
			buscarPaModi.cambiarInfo(infoVertice);
		}
	}

	public double getCostArc (K idVertexIn, K idVertexFin)
	{

		Node<Arco> arqui = arcos.darPrimerElemento();
		double costo = -1;

		int pos = 0;
		while(pos < arcos.darTamanio())
		{
			if(arqui.darData().darInicial().darId() == idVertexIn && arqui.darData().darFinal().darId() == idVertexFin)
			{
				costo = arqui.darData().darCostoHaversiano();
			}

			arqui = arqui.darSiguiente();
			pos = pos + 1;
		}

		return costo;
	}

	public void setCostArc (K idVertexIn, K idVertexFin, double cost)
	{
		Node<Arco> arqui = arcos.darPrimerElemento();

		int pos = 0;
		while(pos < arcos.darTamanio())
		{
			if(arqui.darData().darInicial().darId() == idVertexIn && arqui.darData().darFinal().darId() == idVertexFin)
			{
				arqui.darData().cambiarCostoHaversiano(cost);
			}

			arqui = arqui.darSiguiente();
			pos = pos + 1;
		}
	}

	public void addVertex (K idVertex, V infoVertex)
	{
		vertis.putInSet(idVertex, infoVertex);
		V++;
	}

	public Iterable <K> adj (K idVertex)
	{
		Vertice vertecito = (Vertice) vertis.getSet(idVertex);
		return (Iterable<K>) vertecito.iterator();
	}

	public void uncheck()
	{
		Iterator<K> idVertices = vertis.keys();

		while(idVertices.hasNext())
		{
			K idActual = idVertices.next();

			Vertice actual = (Vertice) vertis.getSet(idActual);
			actual.quitarVisitado();
		}
	}

	public void dfs (K s)
	{
		//1. Obtenemos el vertice.
		Vertice referenteS = (Vertice) vertis.getSet(s);

		//2. Vemos a donde me puedo mover.
		Iterator<Arco> veci = referenteS.iterator();

		//3. Miro si donde estoy esta marcado, para detener la recursión.
		if(referenteS.revisarVisitado() == true)
		{
			return;
		}
		// 4. Sino estoy marcado, sigo con el algoritmo.
		else
		{
			//Me marco 
			referenteS.marcarVisitado(1);

			//Sigo a los otros
			while(veci.hasNext())
			{
				Arco actual = veci.next();

				//El final es a donde me muevo.
				K idActual = (K) actual.darFinal().darId();
				dfs(idActual);
			}
		}
	}
	
	//Obtiene la cantidad de componentes conectados en el grafo.
	public int cc()
	{
		//Descamrco todos los vertices.
		uncheck();
		//Reinicio la cantidad de componentes conectados
		conectados = 0;
		
		//Voy a recorrer todos los vertices.
		Iterator verticIds = vertis.keys();
		
		while(verticIds.hasNext())
		{
			//Voy a recuperar el vetice con base a su id actual.
			K veticeActualID = (K) verticIds.next();
			Vertice verticeActual = (Vertice) vertis.getSet(veticeActualID);
			
			//Sino esta marcado se refiere a una nueva componente.
			if(!verticeActual.revisarVisitado())
			{
				//Los voy acumulando
				conectados = conectados +1 ;
			}
			
			//Marco la totalidad de la componente.
			dfs(veticeActualID);
		}
		
		return conectados;	
	}
	
	public Iterator<K> getCC(K idVertex)
	{
		ArrayList<Vertice> alcanzados = new ArrayList<Vertice>();
		cc();
		
		Vertice buscado = (Vertice) vertis.getSet(idVertex);
		int indiceComponenteBuscado = buscado.darCC();
		
		Iterator verticesIds = vertis.keys();
		
		while(verticesIds.hasNext())
		{
			K idActual = (K) verticesIds.next();
			Vertice actual = (Vertice) vertis.getSet(idActual);
			int indiceComponenteActual = actual.darCC();
			
			if(indiceComponenteActual == indiceComponenteBuscado)
			{
				alcanzados.add(actual);
			}
			
		}
		
		return (Iterator<K>) alcanzados.iterator();
	}
	
	public boolean existeVertice(K id)
	{
		return vertis.contains(id);
	}
}
