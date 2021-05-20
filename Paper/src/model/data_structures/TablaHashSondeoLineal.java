package model.data_structures;

import java.util.Iterator;

public class TablaHashSondeoLineal <K extends Comparable<K> ,V extends Comparable<V>> implements ITablaHash<K, V>
{
	private int Capacidad;  				
	private int Datos;						

	private K[] keys;
	private ListaEnlazadaQueue<V>[] vals;

	public TablaHashSondeoLineal(int m)
	{
		Capacidad = m;
		Datos = 0;

		keys = (K[]) new Comparable[Capacidad];
		vals = new ListaEnlazadaQueue[Capacidad];
	}

	private int rehashes;

	public int darRehashes()
	{
		return rehashes;
	}

	public double darFactorCarga()
	{
		return (double)Datos/Capacidad;
	}

	public int darDatos()
	{
		return Datos;
	}

	public int darCapacidad()
	{
		return Capacidad;
	}

	private int hash(K key)
	{
		return (key.hashCode() & 0x7fffffff) % Capacidad;
	}

	private void resize(int cap)
	{
		TablaHashSondeoLineal<K, V> temporal = new TablaHashSondeoLineal<K, V>(cap);
		for (int i = 0; i < Capacidad; i++)
		{
			if (keys[i] != null)
			{
				Node<V> actual = vals[i].darPrimerElemento();

				while(actual != null)
				{
					V eliminado = (V) actual.darData();
					temporal.putInSet(keys[i], eliminado);
					actual = actual.darSiguiente();
				}

			}
		}

		this.keys = temporal.keys;
		this.vals = temporal.vals;

		this.Capacidad = temporal.Capacidad; 
		this.Datos = temporal.Datos;
	}

	@Override
	public void putInSet(K key, V valor) 
	{
		if (key != null)
		{
			double d = Datos;
			double c = Capacidad;

			if (d >= c*0.75)
			{
				resize(Capacidad*2);
				rehashes++;
			}

			int parar = hash(key);
			int i = parar;

			do
			{
				if (keys[i] == null)
				{
					vals[i] = new ListaEnlazadaQueue<>();

					keys[i] = key;
					vals[i].enqueue(valor);

					Datos++;

					return;
				}
				else
				{
					if (keys[i].equals(key))
					{
						vals[i].enqueue(valor);
						Datos++;
						return;
					}
				}

				i = (i+1) % Capacidad;
			}
			while(i != parar);

		}

	}

	@Override
	public Iterator<V> getSetTotal(K key) 
	{
		if(key!=null)
		{
			int i = hash(key);
			int conta = 0;
			for (i = hash(key); keys[i] != null && conta < Capacidad; i = (i+1) % Capacidad)
			{
				if(keys[i].equals(key))
				{
					return vals[i].iterator();
				}
				conta++;
			}

		}
		return null;
	}

	public boolean contains(K key) 
	{
		return  getSetTotal(key) != null;
	}

	@Override
	public Iterator<V> deleteSet(K key) 
	{

		if (!contains(key))
			return null;

		int i = hash(key);
		ListaEnlazadaQueue<V> retornar = vals[i];

		while (!key.equals(keys[i]))
		{
			i = (i + 1) % Capacidad;
		}

		keys[i] = null;
		vals[i] = null;

		i = (i + 1) % Capacidad;

		while (keys[i] != null) 
		{
			K keyAMatar = keys[i];
			ListaEnlazadaQueue<V> valoresAMatar = vals[i];

			keys[i] = null;
			vals[i] = null;

			Datos--;


			Node<V> actual = valoresAMatar.darPrimerElemento();

			while(actual.darSiguiente()!=null)
			{
				V dataEliminar = (V) actual.darData();
				putInSet(keyAMatar, dataEliminar);

				actual.darSiguiente();
			}

			i=(i+1) % Capacidad;
		}

		Datos--;

		if (Datos > 0 && Datos == Capacidad/8) 
		{
			resize(Capacidad/2);
			rehashes++;
		}



		return retornar.iterator();
	}

	@Override
	public Iterator<K> keys() 
	{
		ListaEnlazadaQueue<K> listaDeDatos = new ListaEnlazadaQueue<K>();

		for(int i = 0; i < Capacidad; i++)
		{
			if(keys[i] != null)
			{
				listaDeDatos.enqueue(keys[i]);
			}
		}

		return listaDeDatos.iterator();
	}

	// ................. DEVOLVER EL PRIMERO PARA EL GRAFO !!

	@Override
	public V getSet(K idGrafo) 
	{
		if(idGrafo==null)
		{
			System.out.println("Micos y tigrillos.");
		}
		for (int i=hash(idGrafo); keys[i]!=null; i=(i+1)%Capacidad)
		{
			if(keys[i].equals(idGrafo))
			{
				return  (V) vals[i].darPrimerElemento().darData();
			}
		}
		return null;
	}
}