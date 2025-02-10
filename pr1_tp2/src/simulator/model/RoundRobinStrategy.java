package simulator.model;

import java.util.List;

public class RoundRobinStrategy implements LightSwitchingStrategy{

	private int _timeSlot;
	
	public RoundRobinStrategy(int timeSlot) {
		_timeSlot = timeSlot;
	}
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		
		//en otro caso devuelve currGreen+1 módulo la longitud de la lista roads
		int res = (currGreen + 1) % roads.size();
		
		//si la lista de carreteras entrantes es vacı́a, entonces devuelve -1.
		if(roads.isEmpty()) res = -1;
		
		//currGreen es -1 devuelve 0
		else if(currGreen == -1) res = 0;
		
		//si si currTime-lastSwitchingTime < timeSlot devuelve currGreen
		else if(currTime-lastSwitchingTime < _timeSlot) res = currGreen;
	
		return res;
	}

}
