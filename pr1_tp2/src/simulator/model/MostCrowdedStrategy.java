package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy{

	private int _timeSlot;
	
	public MostCrowdedStrategy(int timeSlot) {
		
		_timeSlot = timeSlot;
		
	}
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		
		//devuelve la carretera entrante con la cola más larga, realizando una búsqueda circular (en qs) 
		//desde la posición currGreen+1 módulo el número de carreteras entrantes al cruce
		int res = circularSearch((currGreen+1) % roads.size(), qs);;
		
		//si la lista de carreteras entrantes es vacı́a, entonces devuelve -1
		if(roads.isEmpty()) res = -1;
		
		//si todos los semáforos de las carreteras entrantes están en rojo, pone verde 
		//el semáforo de la carretera entrante con la cola más larga empezando en 0
		else if(currGreen == -1) res = circularSearch(0, qs);
		
		//si currTime-lastSwitchingTime < timeSlot, devuelve currGreen
		else if(currTime-lastSwitchingTime < _timeSlot) res = currGreen;
		
		return res;
	}
	
	private int circularSearch(int starting_pos, List<List<Vehicle>> qs) {
		
		int max = 0;
		int i_max = 0;
		
		for(int i = starting_pos; i < qs.size(); ++i) {
			int size = qs.get(i).size();
			if(size > max) {
				max = size;
				i_max = i;
			}
		}
		
		return i_max;
	}
	
}
