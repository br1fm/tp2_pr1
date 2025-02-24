package simulator.model;

public class InterCityRoad extends Road{

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);

	}
	
	public void reduceTotalContamination(){
		
		int tc = getTotalCO2();
		int x = determineWeatherFactor();
		
		setTotalCO2(((100-x)*tc)/100);
	}
	
	public void updateSpeedLimit() {
		
		if(getTotalCO2() > getContLimit()) {
			setSpeedLimit(getSpeedLimit() / 2);
		}
		else setSpeedLimit(getMaxSpeed());
		
	}
	
	public int calculateVehicleSpeed(Vehicle v) {
		
		int speed = getSpeedLimit();
		
		if(getWeather() == Weather.STORM) speed = ((speed*8) / 10);
		
		return speed;
	}
	
	private int determineWeatherFactor() {
		
		int x = 0;
		
		switch(this.getWeather()) {
		
		case Weather.SUNNY:
			
			x = 2;
			
			break;
			
		case Weather.CLOUDY:
			
			x = 3;
			
			break;
		
		case Weather.RAINY:
			
			x = 10;
			
			break;
			
		case Weather.WINDY:
			
			x = 15;
			
			break;
			
		case Weather.STORM:
			
			x = 20;
			
			break;
		
		}
		
		return x;
		
	}
	
}