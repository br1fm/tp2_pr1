package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;


public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy>  {

	
	public RoundRobinStrategyBuilder(String typeTag, String desc) {
		super(typeTag, desc);
		
	}

	@Override
	protected LightSwitchingStrategy create_instance(JSONObject data) {
		//Coge el valor de timeslot del json data y si no hay pone 1 por defecto
		int timeSlot = data.has("timesolt") ? data.getInt("timesolt") : 1;
		
		//Crea instancia
		return new RoundRobinStrategy(timeSlot);
	}

}
