package simulator.model;

import java.util.List;

public class RoundRobinStrategy implements LightSwitchingStrategy{

	private int _timeSlot;
	
	public RoundRobinStrategy (int timeSlot){
		_timeSlot = timeSlot;
	}
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		// 1.-
		if(roads.isEmpty()) return -1;
		
		//2.-
		if(currGreen == -1) return 0;
		
		//3.-
		if((currTime - lastSwitchingTime) < _timeSlot) return currGreen;
	
		//4.-
		return (currGreen + 1 )% roads.size();
	
	}
	

}
