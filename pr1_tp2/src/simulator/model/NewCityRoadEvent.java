package simulator.model;

public class NewCityRoadEvent extends NewRoadEvent{
	
	public NewCityRoadEvent(int time, String id, String srcJun, String destJunc, int length, int maxSpeed, int co2Limit, Weather weather) {
		  super(time,id, srcJun, destJunc, length, maxSpeed, co2Limit, weather);
		}

	@Override
	protected Road createRoad(String id, Junction srcJun, Junction destJunc,int length, int maxSpeed, int co2Limit, 
			Weather weather) {
		return new CityRoad(id, srcJun, destJunc, maxSpeed, co2Limit, length, weather);
	}

	
}
