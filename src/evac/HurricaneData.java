package evac;

import java.time.Instant;

class HurricaneData {
	/** The position of this hurricane */
	final Position position;
	
	/** The time this data was recorded */
	final Instant time;
	
	/** Average wind speed, in MPH */
	final float wind;
	
	/** Fastest recorded wind speed, in MPH */
	final float gusts;
	
	HurricaneData(Position position, Instant time, float wind, float gusts) {
		this.position = position;
		this.velocity = velocity;
		this.time = time;
		this.wind = wind;
		this.gusts = gusts;
	}
	
}
