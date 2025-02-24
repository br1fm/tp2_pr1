package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {
 
	List<Junction> _junctionList;
	List<Road> _roadList;
	List<Vehicle> _vehicleList;
	Map<String,Junction> _junctionMap;
	Map<String,Road> _roadMap;
	Map	<String,Vehicle> _vehicleMap;
	
	//Constructor
	RoadMap() {
		_junctionList = new ArrayList<Junction>();
		_roadList = new ArrayList<Road>();
		_vehicleList = new ArrayList<Vehicle>();
		_junctionMap = new HashMap<String,Junction>();
		_roadMap = new HashMap<String,Road>();
		_vehicleMap = new HashMap<String,Vehicle>();
	}
	
	void addJunction(Junction j) {
		
		//Comprueba que no hay ningun cruce con el mismo id
		if(_junctionMap.containsKey(j.getId())) {
			throw new IllegalArgumentException("No puede haber dos cruces con el mismo id");
		}
		
		//Añade a lista y mapa
		_junctionList.add(j);
		_junctionMap.put(j.getId(),j);
	}
	
	 
	void addRoad(Road r) {
		
		//Comprueba que no hay carreteras con el mismo id y que los cruces conectan
		if(_roadMap.containsKey(r.getId()) && 
			(!_roadMap.containsKey(r.getSrc().getId()) || !_roadMap.containsKey(r.getDest().getId()))) {
			throw new IllegalArgumentException();
		}
		
		//Añade lista y mapa
		_roadList.add(r);
		_roadMap.put(r.getId(),r);
	}
	 
	void addVehicle(Vehicle v) {
		
		if(_vehicleMap.containsKey(v.getId()) || !comprobarItinerario(v)) {
			throw new IllegalArgumentException("No se puede añadir el vehiculo");
		}
		
		_vehicleList.add(v);
		_vehicleMap.put(v.getId(),v);
		
	}
	
	private boolean comprobarItinerario(Vehicle v) {
		
		//Recorre el itinerario 
		for (int i = 0; i<v.getItinerary().size()-1; ++i) {
				
			//Comprueba que los cruces tienen una carretera que los conecta
			if( v.getItinerary().get(i).roadTo(v.getItinerary().get(i+1)) == null) {
			
				return false;
			}
		}
			return true;
	
	}
	
	//Getters
	public Junction getJunction(String id) {
		
		if(!_junctionMap.containsKey(id)) {
			return null;
		}
		return  _junctionMap.get(id);
	}
	
	
	public Road getRoad(String id) {
		
		if(!_roadMap.containsKey(id)) {
			return null;
		}
		return  _roadMap.get(id);
	}
	 
	public Vehicle getVehicle(String id) {
		
		if(!_vehicleMap.containsKey(id)) {
			return null;
		}
		return  _vehicleMap.get(id);
	}

	public List<Junction> getJunctions(){
		return  Collections.unmodifiableList(new ArrayList<>(_junctionList));
	}
	
	public List<Road> getRoads(){
		return  Collections.unmodifiableList(new ArrayList<>(_roadList));
	}
	
	public List<Vehicle> getVehicles(){
		return  Collections.unmodifiableList(new ArrayList<>(_vehicleList));
	}
	 
	void reset() {
		_junctionList.clear();
		 _roadList.clear();
		_vehicleList.clear();
		_junctionMap.clear();
		_roadMap.clear();
		_vehicleMap.clear();
	}
	
	public JSONObject report() {
		JSONObject json = new JSONObject();
		
		//Crear array JSON para cada lista
		JSONArray junctionArray = new JSONArray();
		for (Junction j : _junctionList) {
			junctionArray.put(j.report());
		}
		
		JSONArray roadArray = new JSONArray();
		for (Road r : _roadList) {
			roadArray.put(r.report());
		}
		
		JSONArray vehicleArray = new JSONArray();
		for (Vehicle v : _vehicleList) {
			vehicleArray.put(v.report());
		}
		
		//Agrega cada lista al json
		json.put("junctions", junctionArray);
		json.put("roads", roadArray);
		json.put("vehicles", vehicleArray);
		
		return json;
		
	}
}

		
	
