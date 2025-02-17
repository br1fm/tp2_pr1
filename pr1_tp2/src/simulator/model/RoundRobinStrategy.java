package simulator.model;

import java.util.List;

public class RoundRobinStrategy implements LightSwitchingStrategy{

	private int _timeSlot;
	//Constructor
	public RoundRobinStrategy(int timeSlot) {
		_timeSlot = timeSlot;
	}
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {

		// 1.-si la lista de carreteras entrantes es vacı́a, entonces devuelve -1.
		if(roads.isEmpty()) return -1;
		
		//2.-currGreen es -1 devuelve 0
		if(currGreen == -1) return 0;
		
		//3.-si currTime-lastSwitchingTime < timeSlot devuelve currGreen
		if((currTime - lastSwitchingTime) < _timeSlot) return currGreen;
	
		//4.-en otro caso devuelve currGreen+1 módulo la longitud de la lista roads
		return (currGreen + 1 )% roads.size();

		
	}

}
