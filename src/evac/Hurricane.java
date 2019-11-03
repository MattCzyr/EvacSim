package evac;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

class Hurricane {
	final String name;
	
	final float startWindSpeed;
	final float endWindSpeed;
	
	final List<HurricaneData> data;
	final List<DangerZone> dangerZones;
	
	Hurricane(String name, Position position, Velocity velocity, float startWindSpeed, float endWindSpeed) {
		this.name = name;
		this.startWindSpeed = startWindSpeed;
		this.endWindSpeed = endWindSpeed;
		data = new ArrayList<HurricaneData>();
		dangerZones = new ArrayList<DangerZone>();
		HurricaneData hd = new HurricaneData(position, velocity, Main.modelStart, startWindSpeed);
		data.add(hd);
	}
	
	/*Hurricane(String name, List<HurricaneData> data, List<DangerZone> dangerZones) {
		this.name = name;
		this.data = data;
		this.dangerZones = dangerZones;
	}*/
	
}
