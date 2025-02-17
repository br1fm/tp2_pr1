package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy{

	private int _timeSlot;

	//Constructor
	public MostCrowdedStrategy(int timeSlot) {
		
		_timeSlot = timeSlot;
		
	}
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		
		// 1.-
		if(roads.isEmpty()) return -1;
		
		//2.-
		if (currGreen == -1) return circularSearch(qs,0);
		
		//3.-
		if((currTime - lastSwitchingTime) < _timeSlot) return currGreen;
		
		//4.-
		return circularSearch(qs,(currGreen + 1)%roads.size());
	}
	//Metodo mal planteado
	private int circularSearch(int starting_pos, List<List<Vehicle>> qs) {
		
		int max = 0;
		int i_max = 0;
		//Solo recorres desde la posicion inicial hasta el final, si starting_pos = 2 y el tamaño de qs es 4 solo recorrerias 2 elementos ultimos
		for(int i = starting_pos; i < qs.size(); ++i) {
			int size = qs.get(i).size();
			if(size > max) {
				max = size;
				i_max = i;
			}
		}
		
		return i_max;
	}

	//Metodo propuesto
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
