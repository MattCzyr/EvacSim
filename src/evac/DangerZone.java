package evac;

import java.time.Instant;

class DangerZone {
	final Polygon polygon;
	final int dangerLevel;
	final Instant time;
	
	public DangerZone(Polygon polygon, int dangerLevel, Instant time) {
		this.polygon = polygon;
		this.dangerLevel = dangerLevel;
		this.time = time;
	}
}
