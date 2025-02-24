package simulator.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.Pair;

public class Main {
	
	public static void main(String[] args) {
		 
TrafficSimulator ts = new TrafficSimulator();
		
		ts.addEvent(new NewJunctionEvent(1,"j1", new RoundRobinStrategy(10), new MoveFirstStrategy(), 0, 0));
		ts.addEvent(new NewJunctionEvent(1,"j2", new RoundRobinStrategy(10), new MoveFirstStrategy(), 0, 0));
		ts.addEvent(new NewJunctionEvent(1,"j3", new RoundRobinStrategy(10), new MoveFirstStrategy(), 0, 0));
		ts.addEvent(new NewCityRoadEvent(1, "r1", "j1", "j2", 1000, 500, 100, Weather.SUNNY));
		ts.addEvent(new NewCityRoadEvent(1, "r2", "j2", "j3", 1000, 500, 100, Weather.SUNNY));
		ts.addEvent(new NewVehicleEvent(1,"v1", 50, 1, Arrays.asList("j1", "j2")));
		ts.addEvent(new NewVehicleEvent(1,"v2", 50, 1, Arrays.asList("j1", "j2")));
		ts.addEvent(new NewVehicleEvent(1,"v3", 50, 1, Arrays.asList("j1", "j2")));

		ts.addEvent(new SetWeatherEvent(3,Arrays.asList(new Pair<>("r1",Weather.CLOUDY),new Pair<>("r2",Weather.RAINY))));
		ts.addEvent( new SetContClassEvent(5, Arrays.asList(new Pair<>("v1",4),new Pair<>("v3",7))));

		ts.advance();
		
		ts.advance();
		
	}

}
