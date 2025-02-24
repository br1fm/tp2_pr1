package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveAllStrategy implements DequeuingStrategy{

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		
		List<Vehicle> list = new ArrayList<Vehicle>();
		list.addAll(q);
		return list;
		
	}

}