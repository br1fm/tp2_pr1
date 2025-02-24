package simulator.model;

public class CityRoad extends Road{

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		
	}

	@Override
	void reduceTotalContamination() {
		int cont;
		
		if(getWeather() == Weather.STORM || getWeather() == Weather.WINDY) {
			cont = getTotalCO2() - 10;
		}
		else{
			cont = getTotalCO2() - 2;
		}
		
		setTotalCO2(Integer.max(cont, 0));
	}
	
	@Override
	void updateSpeedLimit() {
		setSpeedLimit(getMaxSpeed()); 
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		
		int s = getSpeedLimit();
		int f = v.getContClass();
		
		return ((11-f)*s)/11;
	}

}