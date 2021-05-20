package model.data_structures;

import java.util.ArrayList;
import java.util.Iterator;

public class Vertice <K extends Comparable<K>, V extends Comparable<V>> implements Comparable
{
	private K id;
	private V informacion; 
	private int match;

	private ArrayList<Arco> vecinos;
	private int visitado;

	public Vertice(K ID, V pInfo)
	{
		vecinos = new ArrayList<Arco>();
		id = ID;
		informacion = pInfo;
		
		visitado=0;
		match = 0;
	}
	
	public K darId()
	{
		return id;
	}
	
	public V darInfo()
	{
		return informacion;
	}
	
	public int darCC()
	{
		return visitado;
	}
	
	public int darMatch()
	{
		return match;
	}

	public void cambiarInfo(V nuevaInfo)
	{
		informacion = nuevaInfo;
	}

	public void agregarArco(Arco v)
	{
		vecinos.add(v);
	}
	
	public boolean esVecino(Arco v)
	{
		return vecinos.contains(v);
	}
	
	public int numVecinos()
	{
		return vecinos.size();
	}
	
	public void eliminarVecino(Arco v)
	{
		vecinos.remove(v);
	}
	
	public Iterator<Arco> iterator() 
	{
		return vecinos.iterator();
	}

	public boolean revisarVisitado()
	{
		if(visitado==0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public void marcarVisitado(int marca)
	{
		visitado = marca;
	}
	public void quitarVisitado()
	{
		visitado = 0;
	}

	public void asignarId(K ID)
	{
		id = ID;
	}
	
	public void aumentarMatch()
	{
		match++;
	}
	
	@Override
	public int compareTo(Object o) {
		return 0;
	}

}