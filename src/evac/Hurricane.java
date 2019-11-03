package evac;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

class Hurricane {
	final String name;
	
	/** Angle of travel of the hurricane, in degrees from east */
	final float trajectory;
	
	final float velocity;
	
	final float startWindSpeed;
	final float endWindSpeed;
	
	int category;
	
	final List<HurricaneData> data;
	final List<DangerZone> dangerZones;
	
	Hurricane(String name, Position position, float velocity, float startWindSpeed, float endWindSpeed, float trajectory) {
		this.name = name;
		this.trajectory = trajectory;
		this.velocity = velocity;
		this.startWindSpeed = startWindSpeed;
		this.endWindSpeed = endWindSpeed;
		data = new ArrayList<HurricaneData>();
		dangerZones = new ArrayList<DangerZone>();
		// TODO change from Instant.now()
		HurricaneData hd = new HurricaneData(position, Instant.now(), startWindSpeed, startWindSpeed);
		data.add(hd);
	}
	
	/*Hurricane(String name, List<HurricaneData> data, List<DangerZone> dangerZones) {
		this.name = name;
		this.data = data;
		this.dangerZones = dangerZones;
	}*/
	
	public void updateCategory(HurricaneData data) {
		if (data.wind <= 95) {
			category = 1;
		} else if (data.wind <= 110) {
			category = 2;
		} else if (data.wind <= 129) {
			category = 3;
		} else if (data.wind <= 156) {
			category = 4;
		} else {
			category = 5;
		}
	}
}
