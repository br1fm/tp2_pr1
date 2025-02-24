package simulator.factories;

import java.awt.Event;

import org.json.JSONObject;

public class NewJunctionEventBuilder extends Builder<Event>{

	public NewJunctionEventBuilder(String typeTag, String desc) {
		super(typeTag, desc);
		
	}

	@Override
	protected Event create_instance(JSONObject data) {
		return new NewJunctionEvent();
	}

}
