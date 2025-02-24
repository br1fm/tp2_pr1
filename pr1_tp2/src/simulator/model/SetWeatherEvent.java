package simulator.model;

//import java.util.ArrayList;

import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event{

	List<Pair<String,Weather>> _ws;
	
	//Constructor
	public SetWeatherEvent(int time, List<Pair<String,Weather>> ws) {
		  super(time);
		  if (ws == null ) throw new IllegalArgumentException("No puede ser null");
		  _ws = ws;
			}
	 
	
	@Override
	void execute(RoadMap map) {
		
		for(Pair<String,Weather> w: _ws) {
			
			Road r = map.getRoad(w.getFirst());
			if(r == null) throw new IllegalArgumentException("No existe la carretera");
			else r.setWeather(w.getSecond());
			
		}
	}

	
}
