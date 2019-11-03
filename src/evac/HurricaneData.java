package evac;

import java.time.Instant;

class HurricaneData {
	/** The position of this hurricane */
	final Position position;
	
	/** The time this data was recorded */
	final Instant time;
	
	/** Wind speed, in MPH */
	final float wind;
	
	float category;
	
	Velocity velocity;
	
	HurricaneData(Position position, Velocity velocity, Instant time, float wind) {
		this.position = position;
		this.velocity = velocity;
		this.time = time;
		this.wind = wind;
		updateCategory();
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
