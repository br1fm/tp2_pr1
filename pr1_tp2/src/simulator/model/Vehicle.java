package simulator.model;

import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject{

	private List<Junction> _itinerary;
	private int _maxSpeed;
	private int _currentSpeed;
	private VehicleStatus _state;
	Road _road;
	private int _location;
	private int _contClass;
	private int _totalCO2;
	private int _totalDistance;
	
	protected Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		  super(id);
		  
	}
	
	void advance(int currTime){
		
		if(_state == VehicleStatus.TRAVELING) {
			int old_location = _location;
			_location = Integer.min(_road.getLength(), _location + _currentSpeed);
			
			int d = _location - old_location;
			
		}
		
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
