package evac;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

class Hurricane {
	final String name;
	
	/** Angle of travel of the hurricane, in degrees from east */
	final float trajectory;
	
	final float startSpeed;
	final float endSpeed;
	
	final List<HurricaneData> data;
	final List<DangerZone> dangerZones;
	
	Hurricane(String name, Position position, float startSpeed, float endSpeed, float trajectory) {
		this.name = name;
		this.trajectory = trajectory;
		this.startSpeed = startSpeed;
		this.endSpeed = endSpeed;
		data = new ArrayList<HurricaneData>();
		dangerZones = new ArrayList<DangerZone>();
		// TODO change from Instant.now()
		HurricaneData hd = new HurricaneData(position, Instant.now(), startSpeed, startSpeed);
		data.add(hd);
	}
	
	/*Hurricane(String name, List<HurricaneData> data, List<DangerZone> dangerZones) {
		this.name = name;
		this.data = data;
		this.dangerZones = dangerZones;
	}*/
}
