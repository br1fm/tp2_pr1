package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject implements Comparable<Vehicle>{

	private List<Junction> _itinerary;
	private int _maxSpeed;
	private int _currentSpeed;
	private VehicleStatus _state;
	Road _road;
	private int _location;
	private int _contClass;
	private int _totalCO2;
	private int _totalDistance;

	//Constructor vehiculo 
	protected Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary){
		
		  super(id);
		  //Comprobacion valores, excepcion si no son validos
		  if(maxSpeed < 0 || itinerary.size() < 2 ||
		     contClass > 10 || contClass < 0) throw new IllegalArgumentException();
		  
		  _maxSpeed = maxSpeed;
		  _contClass = contClass;
		  //Copia para evitar modificar
		  _itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
		  
	}

	
	@Override
	void advance(int time){
		
		if(_state == VehicleStatus.TRAVELING) {

			int old_location = _location;
			// a) 
			_location = Integer.min(_road.getLength(), _location + _currentSpeed);
			// b)
			int c = (_location - old_location) * _contClass;
			_totalCO2 += c;
			_road.addContamination(c);
			// c)
			if(_location >= road.getLength()){
				//metodo Junction
				_state = VehicleStatus.WAITING;
			}
			 
			
		}
		
	}
	
	@Override
	public JSONObject report() {
		// TODO Auto-generated method stubA
		return null;
	}
	//Getters
	public List<Junction> getItinerary() {
		return _itinerary;
	}

	public int getMaxSpeed() {
		return _maxSpeed;
	}

	public int getCurrentSpeed() {
		return _currentSpeed;
	}

	public VehicleStatus getState() {
		return _state;
	}

	public Road getRoad() {
		return _road;
	}

	public int getLocation() {
		return _location;
	}

	public int getContClass() {
		return _contClass;
	}

	public int getTotalCO2() {
		return _totalCO2;
	}

	public int getTotalDistance() {
		return _totalDistance;
	}
	
	
	
	void setSpeed(int s) /*throws excepcion*/{
		if(s < 0) /*lanzar expcecion*/;
		_currentSpeed = Integer.min(_currentSpeed, _maxSpeed);
	}
	
	void setContaminationClass(int c) /*throws excepcion*/{
		if(c < 0 || c > 10) /*lanzar excepcion*/;
		_contClass = c;
	}

	@Override
	public int compareTo(Vehicle o) {
		return Integer.compare(_location, o.getLocation());
	}
	
}
