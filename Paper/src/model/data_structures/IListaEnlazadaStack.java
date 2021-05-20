package model.data_structures;

//TODO Bobby

public interface IListaEnlazadaStack <T extends Comparable> extends Iterable <T>
{
	public int darTama�o();
	public Node<T> devolverCabezaPila();
	
	public void push (T elemento);
	public T pop ();
	
	public boolean isEmpty();
}
