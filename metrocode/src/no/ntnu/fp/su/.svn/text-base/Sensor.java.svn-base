package no.ntnu.fp.su;
/**
 * A sensor.
 * 
 * @author petterw
 */
public class Sensor {
	private long start;
	private long delay;
	private boolean falseAlarm;
	/**
	 * Creates a new sensor which will register a sensor event after a random length of time.
	 */
	public Sensor() {
		reset();
	}
	/**
	 * 
	 * @return Returns true if the sensor has registered an event, otherwise false.
	 */
	public boolean check() {
		return System.currentTimeMillis()> start+delay;
	}
	/**
	 * Check whether the alarm is false. Valid between a call to check() having returned true 
	 * and the next call to reset.
	 * @return Whether the alarm is false.
	 */
	public boolean isFalseAlarm() {
		return falseAlarm;
	}
	/**
	 * Reset the sensor.
	 */
	public void reset() {
		start=System.currentTimeMillis();
		delay=(long) (-1*Math.log(Math.random()) * Constants.sensorMean * 1000);
		falseAlarm=Math.random()<Constants.falseAlarms;
	}

}
