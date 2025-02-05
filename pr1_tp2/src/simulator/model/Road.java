package simulator.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONObject;

public abstract class Road extends SimulatedObject{

	private Junction _srcJunc;
	private Junction _destJunc;
	private int _maxSpeed;
	private int _speedLimit;
	private int _contLimit;
	private int _totalCO2;
	private int _length;
	private Weather _weather;
	private List<Vehicle> _vehicles;

	//Constructor
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather)/*throws excepcion*/{ {
		 super(id);
		if(maxSpeed < 0 || contLimit < 0 || length < 0 || srcJunc == null || destJunc == null || weather == null);/*throws excepcion*/
	}

	//Metodos abstractos de sus subclases
	abstract void updateSpeedLimit();
	
	abstract int calculateVehicleSpeed(Vehicle v);
	
	abstract void reduceTotalContamination();

	@Override
	void advance(int time) {
		// i)
		reduceTotalContamination();
		// ii)
		updateSpeedLimit();
		// iii)
		for(Vehicle v : _vehicles) {
			v.setSpeed(calculateVehicleSpeed(v));
			v.advance(time);
		}
		
		_vehicles.sort(Comparator.naturalOrder());
		
	}

	@Override
	public JSONObject report() {
		return null;
	}

	//Metodo meter vehiculo
	void enter(Vehicle v) /*throws excepcion*/{
		if (v.getLocation() != 0 || v.getCurrentSpeed() != 0) /*lanzar exception*/;
		_vehicles.add(v);
	}
	//Metodo sacar vehiculo
	void exit(Vehicle v) {
		_vehicles.remove(v);
	}
	//Geters
	public Junction getSrc() {
		return _srcJunc;
	}

	public Junction getDest() {
		return _destJunc;
	}

	public int getMaxSpeed() {
		return _maxSpeed;
	}

	public int getSpeedLimit() {
		return _speedLimit;
	}

	public int getContLimit() {
		return _contLimit;
	}

	public int getTotalCO2() {
		return _totalCO2;
	}

	public int getLength() {
		return _length;
	}

	public Weather getWeather() {
		return _weather;
	}

	public List<Vehicle> getVehicles() {
		return Collections.unmodifiableList(_vehicles);
	}
	//Añadir contaminacion
	void addContamination(int c) /*throws excepcion*/{
		if(c < 0) /*lanza excepcion*/;
		_totalCO2 += c;
	}
	//Seters
	void setSrc(Junction _srcJunc) {
		this._srcJunc = _srcJunc;
	}

	void setDest(Junction _destJunc) {
		this._destJunc = _destJunc;
	}

	void setMaxSpeed(int _maxSpeed) {
		this._maxSpeed = _maxSpeed;
	}

	void setSpeedLimit(int _speedLimit) {
		this._speedLimit = _speedLimit;
	}

	void setContLimit(int _contLimit) {
		this._contLimit = _contLimit;
	}

	void setTotalCO2(int _totalCO2) {
		this._totalCO2 = _totalCO2;
	}

	void setLength(int _length) {
		this._length = _length;
	}
	
	void setWeather(Weather w) /*throws excepcion*/{
		if(w == null) /*lanzar exception*/;
		_weather = w;
	}

	void setVehicles(List<Vehicle> _vehicles) {
		this._vehicles = _vehicles;
	}
	
}
