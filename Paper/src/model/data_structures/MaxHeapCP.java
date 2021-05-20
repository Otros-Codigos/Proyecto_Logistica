package model.data_structures;

public class MaxHeapCP <T extends Comparable<T>>
{
	private T[] arregloHeap;
	private int tamañoHeap;
	public MaxHeapCP(int tam)
	{
		tamañoHeap = 0;
		arregloHeap = (T[]) new Comparable[tam+1];
	}
	
	public int darTamaño()
	{
		return tamañoHeap;
	}
	
	public T[] darHeap()
	{
		return arregloHeap;
	}

	public void verificarHeapTamaño()
	{
		if(arregloHeap.length-1 == tamañoHeap)
		{
			T[] prov = (T[]) new Comparable[tamañoHeap+10];
			
			for(int i=1; i < arregloHeap.length; i++)
			{
				prov[i] = arregloHeap[i];
			}
			
			arregloHeap = prov;
		}
	}
	
	private boolean less (int pos1, int pos2)
	{
		return arregloHeap[pos1].compareTo(arregloHeap[pos2]) < 0;
	}
	
	private void exchange (int pos1, int pos2)
	{
		T tempo = arregloHeap[pos1];
		arregloHeap[pos1] = arregloHeap[pos2];
		arregloHeap[pos2] = tempo;
	}

	public void swim (int k)
	{
		while (k > 1 && less(k/2, k))
		{
			exchange(k, k/2);
			k = k/2;
		}
	}

	public void añadir (T elem)
	{
		verificarHeapTamaño();
		arregloHeap[++tamañoHeap] = elem;
		swim(tamañoHeap);
	}

	private void sink (int k)
	{
		while (2*k <= tamañoHeap)
		{
			int j = 2*k;
			
			if (j < tamañoHeap && less(j, j+1))
			{
				j++;
			}
			
			if (!less(k,j))
			{
				break;
			}
			
			exchange(k, j);
			k = j;
		}
	}

	public T devolverMax ()
	{
		T max = arregloHeap[1];
		exchange(1, tamañoHeap--);
		
		sink(1);
		arregloHeap[++tamañoHeap] = null;
		
		tamañoHeap--;
		
		return max;
	}

	public T darMax()
	{
		return arregloHeap[1];
	}

	public boolean esVacia()
	{
		boolean vacio = false;
		
		if (tamañoHeap == 0)
		{
			vacio = true;
		}
		
		return vacio;
	}

	public boolean isEmpty() {
		return tamañoHeap == 0;
	}
	
}
