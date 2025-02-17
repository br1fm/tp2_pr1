package simulator.model;

import java.awt.Taskbar.State;
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
	private int _lastSeenJunction; // No especifica en el guion?

	//Constructor vehiculo 
	protected Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary){
		
		  super(id);
		  
		  //Comprobacion valores, excepcion si no son validos
		  if(maxSpeed < 1 || itinerary.size() < 2 ||
		     contClass > 10 || contClass < 0) throw new IllegalArgumentException();
		  
		  _maxSpeed = maxSpeed;
		  _contClass = contClass;
		  _lastSeenJunction = 0; // 
		  _location = 0; // innecesario se inicializa automaticamente en 0?
		  _state = VehicleStatus.PENDING;
		  _road = null; // innecesario se inicializa automaticamente en null?
		  
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
			int d = _location - old_location; //f en vez de d como indica en el guion?
			int c = d * _contClass;
			_totalCO2 += c;
			_totalDistance += d;
			_road.addContamination(c);
			
			// c)
			if(d == _road.getLength()){
				
				Junction j = _itinerary.get(_lastSeenJunction);
				j.enter(this);
				_currentSpeed = 0;
				_location = 0;
				_state = VehicleStatus.WAITING;
				
				//TODO: metodo Junction
				
				/*It is recommended to keep track of the index of the last junction encountered.
				 *This starts at 0 and is incremented by 1 when entering a junction's queue.*/
				
				//USAR _lastJunction????
				
			}

			// Mi solucion para el apratado c)
			if(_location >= _road.getLength()){
				Junction j_dest = _road.getDest();
				j_dest.enter(this);
				_currentSpeed = 0;
				_location = 0;
				_road = null; // Sale de la carretera
				_state = VehicleStatus.WAITING;
			}
			
		}
		
	}
	
	void moveToNextRoad() /*Lanzar excepción*/ {
		
		if(_state != VehicleStatus.PENDING && _state != VehicleStatus.WAITING) /*Lanzar excepción*/;
		else {
			
			if(_road != null || _lastSeenJunction > 0) _road.exit(this);
				
			if(_lastSeenJunction == _itinerary.size()) {
				_state = VehicleStatus.ARRIVED;
				_road = null;
			}
			
			else {
				
				Junction j = _itinerary.get(_lastSeenJunction);
				_lastSeenJunction++;
				
				if(_state == VehicleStatus.WAITING) {
					
					_road = j.roadTo(_itinerary.get(_lastSeenJunction));
				}
				else /*estado = pending llegados a este punto*/ { 
					//No sé como hacer que entre en su primera carretera
					//int next_junc = _lastSeenJunction + 1;
					_road = j.roadTo(_itinerary.get(_lastSeenJunction));
				}
				
				_road.enter(this);
				_state = VehicleStatus.TRAVELING;
				
			}
					
		}
		
	}

	//Mi Solucion MoveToNextRoad:
	void moveToNextRoad() {
		
		if(_state != VehicleStatus.PENDING || _state != VehicleStatus.WAITING) throw new IllegalArgumentException("El estado solo puede ser PENDING o WAITING");
		else {
			if(_state == VehicleStatus.PENDING) {
		
		
			// Bucar primer y segundo cruce
			Junction firstJunction = _itinerary.get(0);
			Junction nextJunciton = _itinerary.get(1);
			
			// Buscar carretera que conecta ambos cruces
			Road firstRoad = firstJunction.roadTo(nextJunciton);
			
			//Comprobar que existe carretera
			if(firstRoad != null) {
				
				//Meter el vehiculo en la carretera
				firstRoad.enter(this);
			    _road = firstRoad;
				_state = VehicleStatus.WAITING;
			}
		}else if (_state == VehicleStatus.WAITING) {
			
			//Salir de la carretera actual
			_road.exit(this);
			
			//Obtener el cruce actual
			Junction actualJunction = _road.getDest();
			
			// Buscar el indice del cruce en el itinerario
			int index = _itinerary.indexOf(actualJunction);
			
			// Comprobar que el cruce no es el ultimo 
			if(index != -1 && index + 1 < _itinerary.size()) {
			
				// Buscar el siguiente cruce en el itinerario
				Junction nextJunction = _itinerary.get( index + 1);
				
				// Obtener la carretera entre el cruce actual y el siguiente
				Road nextRoad = actualJunction.roadTo(nextJunction);
				
				// Comprobar que existe carretera
				if(nextRoad != null) {
					
					// Meter el vehiculo en la carretera
					nextRoad.enter(this);
					_road = nextRoad; 
				}
			
			// El cruce era el ultimo por lo tanto finaliza itinerario	
			}else {
				_state = VehicleStatus.ARRIVED;
				}
			}
		}
	}



	
	@Override
	public JSONObject report() {
		
		JSONObject json = new JSONObject();
        json.put("id", _id);
        json.put("speed", _currentSpeed);
        json.put("distance", _totalDistance);
        json.put("co2", _totalCO2);
        json.put("class", _contClass);
        json.put("status", _state.toString());
	
	//Solo se incluye road y location si state pending o waiting	
        if(!(_state == VehicleStatus.PENDING || _state == VehicleStatus.ARRIVED)) {
        	json.put("road", _road.getId());
        	json.put("location", _location);
        }
        
        return json;
	}

	//Para que sirve esto?
	@Override
	public String toString() {
		return "id: " + _id +
		           ", speed: " + _currentSpeed +
		           ", distance: " + _totalDistance +
		           ", co2: " + _totalCO2 +
		           ", class: " + _contClass +
		           ", status: " + _state +
		           ", road: " + _road.getId() +
		           ", location: " + _location;
	}
	
	//Getters
	public List<Junction> getItinerary() {
		return _itinerary;
	}

	public int getMaxSpeed() {
		return _maxSpeed;
	}

	public int getSpeed() {
		return _currentSpeed;
	}

	public VehicleStatus getStatus() {
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
	
	
	// Velocidad al valor entre el minimo de vactual y maxspeed
	void setSpeed(int s){
		
		if(s < 0) throw new IllegalArgumentException("La velocidad no puede ser negativa");
		if(_state == VehicleStatus.TRAVELING) _currentSpeed = Integer.min(s, _maxSpeed);
		
	}
	
	// Contaminacion al valor c
	void setContClass(int c){
		if(c < 0 || c > 10) throw new IllegalArgumentException("El grado de contaminación debe ser un valor entre 0 y 10");
		_contClass = c;
	}

	@Override
	public int compareTo(Vehicle o) {
		return Integer.compare(_location, o.getLocation());
	}
	
}
