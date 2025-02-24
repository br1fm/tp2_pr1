package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy  {

	private int _timeSlot;
	
	public MostCrowdedStrategy(int timeSlot){
		_timeSlot = timeSlot;
	}
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		// 1.-
		if(roads.isEmpty()) return -1;
		
		//2.-
		if (currGreen == -1) {
			return circularSearch(qs,0);
		}
		
		//3.-
		if((currTime - lastSwitchingTime) < _timeSlot) return currGreen;

		//4.-
		return circularSearch(qs,(currGreen + 1)%roads.size());
		
	}

	//Metodo encontrar lista mas larga de manera circular
	private int circularSearch(List<List<Vehicle>> qs,int startIndex) {
		int maxQueue = -1;
		int selectRoad = startIndex;
		
		//Recorre la lista de manera circular
		for(int i = 0; i<qs.size(); ++i) {
			
			int index = (startIndex+i)%qs.size();
			
			//Si encontramos una cola mas grande se actualiza, si hay dos iguales la primera prevalece
			if(qs.get(index).size() > maxQueue) {
				maxQueue = qs.get(index).size();
				selectRoad = index;
			}
		}
		return selectRoad;
	}

}
