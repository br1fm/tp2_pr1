package simulator.model;

import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class Road extends SimulatedObject{

	private Junction _srcJunc;
	private Junction _destJunc;
	private int _maxSpeed;
	private int _speedLimit;
	private int _contLimit;
	private int _totalCO2;
	private int _length;
	private Weather _weather;
	private List<Vehicle> _vehicles;
	
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		 super(id);
	}

	@Override
	void advance(int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JSONObject report() {
		return null;
	}

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

	void setWeather(Weather _weather) {
		this._weather = _weather;
	}

	void setVehicles(List<Vehicle> _vehicles) {
		this._vehicles = _vehicles;
	}
	
	
}
