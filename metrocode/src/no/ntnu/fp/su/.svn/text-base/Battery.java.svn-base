package no.ntnu.fp.su;
/**
 * A battery.
 * 
 * @author petterw
 */
public class Battery {
	private long start;
	private long delay;
	/**
	 * Creates a new battery which will have low power after a random length of time.
	 */
	public Battery() {
		replace();
	}
	/**
	 * 
	 * @return Returns true if the battery power is low, otherwise false.
	 */
	public boolean low() {
		return System.currentTimeMillis()> start+delay;
	}
	/**
	 * Replace the battery.
	 */
	public void replace() {
		start=System.currentTimeMillis();
		delay=(long) (-1*Math.log(Math.random()) * Constants.batteryMean * 1000);
	}

}
