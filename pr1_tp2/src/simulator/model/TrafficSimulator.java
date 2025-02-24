package simulator.model;


import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.json.JSONObject;

public class TrafficSimulator {
	
	private RoadMap _map;
	private Queue<Event> _eventQueue;
	private int _time;
	
	//Constructor por defecto
	public TrafficSimulator() {
		_map = new RoadMap();
		_eventQueue = new PriorityQueue<>();
	}
	
	public void addEvent(Event e) {
		if(e._time <= _time) throw new IllegalArgumentException("No se pueden a�adir eventos del pasado");
		_eventQueue.add(e);
	}
	
	public void reset() {
		_time = 0;
		_eventQueue.clear();
		_map.reset();
	}
	public void advance() {
		//i)
		++_time;
		
		//ii)
		
		/*while(!_eventQueue.isEmpty() && _time == _eventQueue.peek().getTime()) {
			//.pol() Saca y elimina de la cola
			Event e = _eventQueue.poll();
			//Ejecuta el evento
			e.execute(_map);
		}*/
		
		Iterator<Event> e_it = _eventQueue.iterator();
		boolean exit = false;
		
		while(e_it.hasNext() && !exit) {
			Event e = e_it.next();
			if(e.getTime() == _time) {
				e.execute(_map);
				e_it.remove();
			}
			else exit = true;
		}
		
		//iii)
		Iterator<Junction> j_it = _map.getJunctions().iterator();
		while(j_it.hasNext()) {
			j_it.next().advance(_time);
		}
		
		//iv)
		Iterator<Road> r_it = _map.getRoads().iterator();
		while(r_it.hasNext()) {
			r_it.next().advance(_time);
		}
		
	}
	
	
	
	public JSONObject report() {
		JSONObject json = new JSONObject();
		
		json.put("time", _time);
		json.put("state", _map.report());
		return json;
		
	}
	
	public String toString() {
		return report().toString();
	}
}
