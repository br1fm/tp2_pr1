package simulator.model;

public class NewInterCityRoadEvent extends NewRoadEvent{
	
	NewInterCityRoadEvent(int time, String id, String srcJunc, String destJunc, int length, int maxSpeed, int co2Limit,
			Weather weather) {
		super(time, id, srcJunc, destJunc, length, maxSpeed, co2Limit,  weather);
	}

	@Override
	protected Road createRoad(String id, Junction srcJun, Junction destJunc,int length, int maxSpeed, int co2Limit, 
			Weather weather) {
		return new InterCityRoad(id, srcJun, destJunc, maxSpeed, co2Limit, length, weather);
	}
}
