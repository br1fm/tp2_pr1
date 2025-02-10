package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy{

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		
		List<Vehicle> list = new ArrayList<Vehicle>();
		list.add(q.getFirst());
		return list;
		
	}

}
