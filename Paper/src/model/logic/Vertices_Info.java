package model.logic;

public class Vertices_Info implements Comparable<Vertices_Info>
{
	private int id;
	private double longitud;
	private double latitud;
	
	public Vertices_Info(double iLongitud, double iLatitud)
	{
		longitud = iLongitud;
		latitud = iLatitud;
	}
	
	
	public double darLon()
	{
		return longitud;
	}

	public double darLat()
	{
		return latitud;
	}
	
	public void asignarLon(double longi)
	{
		longitud = longi;
	}
	
	public void asignarLat(double lati)
	{
		latitud = lati;
	}
	
	
	public int darId()
	{
		return id;
	}
	
	public void asignarId(int nuevo)
	{
		id = nuevo;
	}

	@Override
	public int compareTo(Vertices_Info o) {
		return 0;
	}
	
}