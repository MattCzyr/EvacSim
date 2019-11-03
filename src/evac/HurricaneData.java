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
	
	/** Hurricane category, 1-5 */
	int category;
	
	HurricaneData(Position position, Instant time, float wind, float gusts) {
		this.position = position;
		this.time = time;
		this.wind = wind;
		this.gusts = gusts;
	}
	
	public void updateCategory() {
		if (wind <= 95) {
			category = 1;
		} else if (wind <= 110) {
			category = 2;
		} else if (wind <= 129) {
			category = 3;
		} else if (wind <= 156) {
			category = 4;
		} else {
			category = 5;
		}
	}
	
}
