package no.ntnu.fp.su;

/**
 * An alarm unit with a sensor and a battery.
 * 
 * @author petterw
 */
public class AlarmUnit {
	private Sensor sensor = new Sensor();
	private Battery battery = new Battery();
	private String id;
	/**
	 * 
	 * @param id alarm unit identifier.
	 */
	public AlarmUnit(String id) { 
		this.id=id;
	}
	/**
	 * 
	 * @return The identifier of the alarm unit.
	 */
	public String getId() {
		return id;
	}
	/**
	 * 
	 * @return The sensor connected to the alarm unit.
	 */
	public Sensor getSensor() {
		return sensor;
	}
	/**
	 * 
	 * @return The battery powering the alarm unit.
	 */
	public Battery getBattery() {
		return battery;
	}
}
