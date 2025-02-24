package simulator.model;

//import java.util.ArrayList;

import java.util.List;

import simulator.misc.Pair;

public class SetContClassEvent extends Event{

	List<Pair<String,Integer>> _cs;
	
	//Constructor
	public SetContClassEvent(int time, List<Pair<String,Integer>> cs) {
		  super(time);
		  if (cs == null ) throw new IllegalArgumentException("No puede ser null");
		  _cs = cs;
			}
	 
	
	@Override
	void execute(RoadMap map) {
		
		for(Pair<String,Integer> c: _cs) {
			
			Vehicle v = map.getVehicle(c.getFirst());
			if(v == null) throw new IllegalArgumentException("No existe la carretera");
			else v.setContClass(c.getSecond());
			
		}
	}

	
}
