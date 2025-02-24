package simulator.model;

public abstract class NewRoadEvent extends Event{

	String _id;
	String _srcJunc;
	String _destJunc;
	int _maxSpeed;
	int _co2Limit;
	int _length;
	Weather _weather;

	
	
	NewRoadEvent(int time,String id, String srcJunc, String destJunc,int length , int maxSpeed, int co2Limit, Weather weather) {
		super(time);
		_id = id;
		_srcJunc = srcJunc;
		_destJunc = destJunc;
		_maxSpeed = maxSpeed;
		_co2Limit = co2Limit;
		_length = length;
		_weather = weather;
	}
 
	@Override
	void execute(RoadMap map) {
		
		Junction src = map.getJunction(_srcJunc);
		Junction dest = map.getJunction(_destJunc);
		
		
		//Crea nueva carretera
		Road newRoad = createRoad(_id, src, dest, _length, _co2Limit, _maxSpeed, _weather);
		
		//añade al mapa de carreteras
		map.addRoad(newRoad);
	}

	protected abstract Road createRoad(String id, Junction srcJunc, Junction destJunc,int length , int maxSpeed, int co2Limit, Weather weather);
}
