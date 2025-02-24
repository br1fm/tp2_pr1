package simulator.model;

public class NewJunctionEvent extends Event{

	private String _id;
	private LightSwitchingStrategy _lsStrategy;
	private DequeuingStrategy _dqStrategy;
	private int _xCoor;
	private int _yCoor;
	 
	//Constructor
	public NewJunctionEvent(int time, String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		  super(time);
		  
		  _id = id;
		  _lsStrategy = lsStrategy;
		  _dqStrategy = dqStrategy;
		  _xCoor = xCoor;
		  _yCoor = yCoor;
		}
 
	@Override
	void execute(RoadMap map) {
		// Crear el nuevo cruce
		Junction newJunction = new Junction(_id,_lsStrategy,_dqStrategy,_xCoor,_yCoor);
		
		//Añadirlo al RoadMap
		map.addJunction(newJunction);
	}

	
}
