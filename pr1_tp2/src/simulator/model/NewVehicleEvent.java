package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event{

	private String _id;
	private int _maxSpeed;
	private int _contClass;
	private List<String> _itinerary;
	
	//Constructor
	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
		  super(time);
		  
		  _id = id;
		  _maxSpeed = maxSpeed;
		  _contClass = contClass;
		  _itinerary = itinerary;

		}
	 
	
	@Override
	void execute(RoadMap map) {
		
		List<Junction> itinerary = new ArrayList<>();
		for(String  it : _itinerary) {
			
			itinerary.add(map.getJunction(it));
		}
		//List<Junction> itinerary = map.getJunctions();
		// Crear el nuevo vehiculo
		Vehicle newVehicle = new Vehicle(_id, _maxSpeed, _contClass, itinerary);
		
		//Añadirlo al RoadMap
		map.addVehicle(newVehicle);
		newVehicle.moveToNextRoad();
	}

	
}
