package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.MoveFirstStrategy;

public class MoveFirstStrategyBuilder extends Builder<DequeuingStrategy> {

	public MoveFirstStrategyBuilder(String typeTag, String desc) {
		super(typeTag, desc);
	}

	@Override
	protected DequeuingStrategy create_instance(JSONObject data) {
		// Crea la instancia
		return new MoveFirstStrategy();
	}

}
