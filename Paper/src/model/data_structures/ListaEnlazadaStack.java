package model.data_structures;

import java.util.Iterator;


public class ListaEnlazadaStack <T extends Comparable> implements IListaEnlazadaStack <T> 
{
	private Node<T> topePila;
	private int numeroElementos;
	
	public ListaEnlazadaStack()
	{
		topePila = null;
		numeroElementos = 0;
	}
	
	public int darTamaño() 
	{
		return numeroElementos;
	}
	
	public Node<T> devolverCabezaPila()
	{
		return topePila;
	}
	
	public void push (T elemento)
	{
		Node<T> nuevo = new Node<T>(elemento);
		
		if (topePila == null)
		{
			topePila = nuevo;
		}
		else
		{
			nuevo.cambiarSiguiente(topePila);
			topePila = nuevo;
		}
		
		numeroElementos++;
	}

	public T pop ()
	{		
		if (topePila != null)
		{
			T elemento = (T) topePila.darData();
			
			Node<T> nuevoTope = topePila.darSiguiente();
			topePila.cambiarSiguiente(null);
			topePila = nuevoTope;
			numeroElementos--;
			
			return elemento;
		}
		else
		{
			return null;
		}
	}

	public boolean isEmpty()
	{
		boolean vacia = true;
		
		if (topePila != null)
		{
			vacia = false;
		}
		
		return vacia; 
	}
	
	public Iterator<T> iterator() {
		return null;
	}

}
