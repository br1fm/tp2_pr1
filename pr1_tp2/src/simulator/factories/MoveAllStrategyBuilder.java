package simulator.factories;

import org.json.JSONObject;


import simulator.model.DequeuingStrategy;
import simulator.model.MoveAllStrategy;

public class MoveAllStrategyBuilder extends Builder<DequeuingStrategy> {

	public MoveAllStrategyBuilder(String typeTag, String desc) {
		super(typeTag, desc);
	}

	@Override
	protected DequeuingStrategy create_instance(JSONObject data) {
		//Crea la instancia
		return new MoveAllStrategy();
	}

}
