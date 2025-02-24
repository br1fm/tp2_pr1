package simulator.factories;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {

	private Map<String,Builder<T>> _builders;
	private LinkedList<JSONObject> _builders_info; 
	
	public BuilderBasedFactory() {
		_builders = new HashMap<>();
		_builders_info = new LinkedList<>();
	}
	public BuilderBasedFactory(List<Builder<T>> builders) {
		this();
		
		for(Builder<T> b: builders) {
			add_builder(b);
		}
	}
	
	public void add_builder(Builder<T> b) {
		if(b != null) {
		
		_builders_info.add(b.get_info());
		_builders.put(b.get_type_tag(), b);
		
		}
	}
	@Override
	public T create_instance(JSONObject info) {
		if(info == null) {
			throw new IllegalArgumentException("info cannot be null");
		}
		
		//Busca el builder en el mapa
		Builder<T> builder = _builders.get(info.getString("type"));
		
		if(builder != null) {
			//Extraer datos , si no hay se devuelve un json vacio
			JSONObject data = info.has("data") ? info.getJSONObject("data") : new JSONObject();
		
			//Crear la instancia
			T instance = builder.create_instance(data);
			
			//Si la instancia no es nula se devuelve
			if(instance != null) return instance;
		
		}else throw new IllegalArgumentException("Unrecognized info:" + info.toString());
		return null;
	}

	@Override
	public List<JSONObject> get_info() {
		return Collections.unmodifiableList(_builders_info);
	}

}
