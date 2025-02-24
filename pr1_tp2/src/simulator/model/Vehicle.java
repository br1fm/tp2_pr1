package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject implements Comparable<Vehicle> {

	private List<Junction> _itinerary;
	private int _maxSpeed;
	private int _currentSpeed;
	private VehicleStatus _state;
	Road _road; // Si no tiene carretera null por defecto
	private int _location;
	private int _contClass;
	private int _totalCO2;
	private int _totalDistance;
	private int _lastSeenJunction;
	// Constructor vehiculo
	protected Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {

		super(id);

		// Comprobacion valores, excepcion si no son validos
		if (maxSpeed < 1 || itinerary.size() < 2 || contClass > 10 || contClass < 0)
			throw new IllegalArgumentException("Argumentos incorrectos en la clase vehiculo");

		_maxSpeed = maxSpeed;
		_contClass = contClass;
		_state = VehicleStatus.PENDING;
		// Copia para evitar modificar
		_itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));

	} 
	
	/*
	void moveToNextRoad() {
		

		if (_state != VehicleStatus.PENDING && _state != VehicleStatus.WAITING)
			throw new IllegalArgumentException("El vehiculo no esta en pending o waiting");
		else {
			if (_state == VehicleStatus.PENDING) {
				
				// Bucar primer y segundo cruce
				Junction firstJunction = _itinerary.get(0);
				Junction nextJunciton = _itinerary.get(1);

				// Buscar carretera que conecta ambos cruces
				Road firstRoad = firstJunction.roadTo(nextJunciton);
				
				// Comprobar que existe carretera
				if (firstRoad != null) { 

					// Meter el vehiculo en la carretera
					firstRoad.enter(this);
					_road = firstRoad;
					_state = VehicleStatus.TRAVELING;
				}
			} else if (_state == VehicleStatus.WAITING) {

				// Salir de la carretera actual
				_road.exit(this);

				// Obtener el cruce actual
				Junction actualJunction = _road.getDest();

				// Buscar el indice del cruce en el itinerario
				int index = _itinerary.indexOf(actualJunction);

				// Comprobar que el cruce no es el ultimo
				if (index != -1 && index + 1 < _itinerary.size()) {

					// Buscar el siguiente cruce en el itinerario
					Junction nextJunction = _itinerary.get(index + 1);

					// Obtener la carretera entre el cruce actual y el siguiente
					Road nextRoad = actualJunction.roadTo(nextJunction);

					// Comprobar que existe carretera
					if (nextRoad != null) {

						// Meter el vehiculo en la carretera
						nextRoad.enter(this);
						_road = nextRoad;
						_state = VehicleStatus.TRAVELING;
						_location = 0;
					}
 
					// El cruce era el ultimo por lo tanto finaliza itinerario
				} else {
					_state = VehicleStatus.ARRIVED;
					_road = null;
					_location = 0;
					_currentSpeed = 0;
				}
			}
		}
	}

	@Override
	void advance(int time) {

		if (_state == VehicleStatus.TRAVELING) { // El vehiculo esta traveling

			int old_location = _location;
			// a)
			_location = Integer.min(_road.getLength(), _location + _currentSpeed);
			// b)
			int c = (_location - old_location) * _contClass;
			_totalCO2 += c;
			_road.addContamination(c);
			_totalDistance += (_location - old_location);
			// c)
			if (_location >= _road.getLength()) {
				Junction dest = _road.getDest();
				dest.enter(this);
				_currentSpeed = 0;
				_location = 0;
				_state = VehicleStatus.WAITING;
			}

		}

	}
	*/
	
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
			if(d >= _road.getLength()){
				
				_location = _road.getLength();
				_currentSpeed = 0;
				_road.getDest().enter(this);
				_state = VehicleStatus.WAITING;
				_lastSeenJunction++;
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
					_road = j.roadTo(_itinerary.get(_lastSeenJunction));
				}
				
				_road.enter(this);
				_state = VehicleStatus.TRAVELING;
				
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

		// Solo se incluye road y location si _state = traveling o waiting
		if (_state == VehicleStatus.TRAVELING || _state == VehicleStatus.WAITING) {
			json.put("road", _road.getId());
			json.put("location", _location);

		}

		return json;
	}
	
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

	// Getters
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
	void setSpeed(int s) {
		if (s < 0)
			throw new IllegalArgumentException();
		if (_state == VehicleStatus.TRAVELING)
			_currentSpeed = Integer.min(s, _maxSpeed);
		else
			_currentSpeed = 0;
	}

	// Contaminacion al valor c
	void setContClass(int c) {
		if (c < 0 || c > 10)
			throw new IllegalArgumentException();
		_contClass = c;
	}

	@Override
	public int compareTo(Vehicle o) {
		return Integer.compare(_location, o.getLocation());
	}

	// Setters Opcional
	/*
	 * private void set_itinerary(List<Junction> itinerary) { this._itinerary =
	 * itinerary; }
	 * 
	 * private void set_maxSpeed(int maxSpeed) { this._maxSpeed = maxSpeed; }
	 * 
	 * private void set_currentSpeed(int currentSpeed) { if(_state !=
	 * VehicleStatus.TRAVELING) _currentSpeed = 0; _currentSpeed = currentSpeed; }
	 * 
	 * private void set_state(VehicleStatus state) { _state = state; if(state !=
	 * VehicleStatus.TRAVELING ) _currentSpeed = 0; }
	 * 
	 * private void set_road(Road road) { _road = road; }
	 * 
	 * private void set_location(int location) { _location = location; }
	 * 
	 * private void set_contClass(int contClass) { _contClass = contClass; }
	 * 
	 * private void set_totalCO2(int _totalCO2) { this._totalCO2 = _totalCO2; }
	 * 
	 * private void set_totalDistance(int _totalDistance) { this._totalDistance =
	 * _totalDistance; }
	 */
}