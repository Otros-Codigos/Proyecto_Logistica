package model.data_structures;

public class MaxHeapCP <T extends Comparable<T>>
{
	private T[] arregloHeap;
	private int tama�oHeap;
	public MaxHeapCP(int tam)
	{
		tama�oHeap = 0;
		arregloHeap = (T[]) new Comparable[tam+1];
	}
	
	public int darTama�o()
	{
		return tama�oHeap;
	}
	
	public T[] darHeap()
	{
		return arregloHeap;
	}

	public void verificarHeapTama�o()
	{
		if(arregloHeap.length-1 == tama�oHeap)
		{
			T[] prov = (T[]) new Comparable[tama�oHeap+10];
			
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

	public void a�adir (T elem)
	{
		verificarHeapTama�o();
		arregloHeap[++tama�oHeap] = elem;
		swim(tama�oHeap);
	}

	private void sink (int k)
	{
		while (2*k <= tama�oHeap)
		{
			int j = 2*k;
			
			if (j < tama�oHeap && less(j, j+1))
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
		exchange(1, tama�oHeap--);
		
		sink(1);
		arregloHeap[++tama�oHeap] = null;
		
		tama�oHeap--;
		
		return max;
	}

	public T darMax()
	{
		return arregloHeap[1];
	}

	public boolean esVacia()
	{
		boolean vacio = false;
		
		if (tama�oHeap == 0)
		{
			vacio = true;
		}
		
		return vacio;
	}

	public boolean isEmpty() {
		return tama�oHeap == 0;
	}
	
}
