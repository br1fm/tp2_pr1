package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject{
	
	private List<Road> _incomming_roads;
	private Map<Junction,Road> _outgoing_roads;
	private int _greenLightIndex;
	private int _lastSwitchingTime;
	private List<List<Vehicle>> _queues;
	private Map<Road,List<Vehicle>> _queueByRoad;
	private LightSwitchingStrategy _lsStrategy;
	private DequeuingStrategy _dqStrategy;
	private int _xCoor;
	private int _yCoor;
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		  super(id);

		
		 if(lsStrategy == null || lsStrategy == null || xCoor < 0 || yCoor < 0) throw new IllegalArgumentException("Argumentos incorrectos para el objeto de tipo Junction");  
		  _lsStrategy = lsStrategy;
		  _dqStrategy = dqStrategy;
		  _xCoor = xCoor;
		  _yCoor = yCoor;
		  _incomming_roads = new ArrayList<Road>();
		  _outgoing_roads = new HashMap<Junction, Road>();
		  _queues = new ArrayList<List<Vehicle>>();
		  _queueByRoad = new HashMap<Road, List<Vehicle>>();
		  _greenLightIndex = -1;
		  _lastSwitchingTime = 0; // Innecesario se inicializa en 0 automaticamente?
		  
		}

	@Override
	void advance(int time) {

		if(_greenLightIndex != -1) {
			
			List<Vehicle> q =_incomming_roads.get(_greenLightIndex).getVehicles();
			List<Vehicle> l = _dqStrategy.dequeue(q);

			//Creando este iterador no los eliminas ni de q ni de _queueByRoad que son los colas de donde hay que eliminar no?
			Iterator<Vehicle> it = l.iterator();
			
			//iterador empieza antes del primer elemento de la lista.
			while(it.hasNext()) {
				Vehicle v = it.next();
				v.moveToNextRoad();
				it.remove();
			}

			//Mi solucion paara el primer apartado 
			//i)
		//Lista de vehiculos con semaforo en verde en la carretera
		List<Vehicle> vehiclesToMove = _incomming_roads.get(_greenLightIndex).getVehicles();
		//Estrategia para extraccion de cola
		List<Vehicle> avance = _dqStrategy.dequeue(vehiclesToMove);
			    
	    
		
	    // Mover cada vehículo a su siguiente carretera y eliminarlo de la cola actual
	    vehiclesToMove.removeAll(avance);
		
		for (Vehicle v : avance) {
	    	// Obtiene la carretera actual del vehículo
	        Road currentRoad = v.getRoad();  
	        if (currentRoad != null) {
	        
	        	// Obtiene la cola de esta carretera
	        	List<Vehicle> queue = _queueByRoad.get(currentRoad); 
	        
	        	if (queue != null) {
	        		// Elimina el vehículo de la cola
	        		queue.remove(v); 
	        	}
	        }
	        // Llama al método que mueve el vehículo a su siguiente carretera
	        v.moveToNextRoad(); 
	    }
		}

		//ii)
		//Calcular indice de la siguiente carretera en verde
		int new_greenLight = _lsStrategy.chooseNextGreen(_incomming_roads, _queues, _greenLightIndex, _lastSwitchingTime, time);

		if(new_greenLight != _greenLightIndex) {
			_greenLightIndex = new_greenLight;
			_lastSwitchingTime = time;
		}
		
	}
	
	void addIncommingRoad(Road r) {
		
		if(r.getDest() != this) throw new IllegalArgumentException("Esta carretera no es entrante"); 
		
		_incomming_roads.add(r);
		
		//Se crea la Lista de colas
		List<Vehicle> vehicleQueue = new LinkedList<>();
		_queues.add(vehicleQueue);
		_queueByRoad.put(r,vehicleQueue);
		
	}
	
	void addOutGoingRoad(Road r) {
		
		if(r.getSrc() == this) {
			
			if(_outgoing_roads.containsKey(r.getDest())) {
				_outgoing_roads.put(r.getDest(), r);
			}else {
				throw new IllegalArgumentException("Esta carretera no es saliente"); 
			}
		}else {
			throw new IllegalArgumentException("Esta carretera no llega al cruce"); 
		}
		
	}
	
	void enter(Vehicle v) {
		
		Road r = v.getRoad();
		List<Vehicle> vehicleQueue = _queueByRoad.get(r);
		vehicleQueue.add(v);
		
		_queueByRoad.put(r,vehicleQueue);
		
	}
	
	Road roadTo(Junction j) {
		return _outgoing_roads.get(j);
	}
	
	@Override
	public JSONObject report() {
		
		JSONObject json = new JSONObject();
        json.put("id", _id);
        
        String greenRoad = "none";
        if (_greenLightIndex >= 0 && _greenLightIndex < _incomming_roads.size()) {
            greenRoad = _incomming_roads.get(_greenLightIndex).getId();
        }
        json.put("green", greenRoad);
        
        JSONArray queuesArray = new JSONArray();
        for (Road road : _incomming_roads) {
            JSONObject queueJson = new JSONObject();
            queueJson.put("road", road.getId());
            
            JSONArray vehiclesArray = new JSONArray();
            List<Vehicle> queue = _queueByRoad.get(road);
            if (queue != null) {
                for (Vehicle v : queue) {
                    vehiclesArray.put(v.getId());
                }
            }
            queueJson.put("vehicles", vehiclesArray);
            
            queuesArray.put(queueJson);
        }
        json.put("queues", queuesArray);
        
        return json;
	}
	
}
