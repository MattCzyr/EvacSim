package evac;

import java.time.Instant;

class HurricaneData {
	final Position position;
	final Velocity velocity;
	final Instant time;
	final double wind;
	final double gusts;
	final int category;
	
	HurricaneData(Position position, Velocity velocity, Instant time, double wind, double gusts, int category) {
		this.position = position;
		this.velocity = velocity;
		this.time = time;
		this.wind = wind;
		this.gusts = gusts;
		this.category = category;
	}
}
