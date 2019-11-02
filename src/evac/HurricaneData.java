package evac;

import java.time.Instant;

class HurricaneData {
	final Position position;
	final Instant time;
	final float wind;
	final float gusts;
	final int category;
	
	HurricaneData(Position position, Instant time, float wind, float gusts, int category) {
		this.position = position;
		this.time = time;
		this.wind = wind;
		this.gusts = gusts;
		this.category = category;
	}
}
