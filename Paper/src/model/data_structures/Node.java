package model.data_structures;

public class Node <T extends Comparable> implements Comparable<Node>
{
	public T data;
	Node<T> siguiente;
	
	public Node (T elemento)
	{
		data = elemento;
		siguiente = null;
	}
	
	public T darData()
	{
		return data;
	}
	
	public Node<T> darSiguiente()
	{
		return siguiente;
	}
	public void cambiarSiguiente(Node<T> primerElemento)
	{
		siguiente = primerElemento;
	}

	@Override
	public int compareTo(Node o) {
		return 0;
	}
	
}
