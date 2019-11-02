package evac;

import java.util.List;

class Hurricane {
	final List<HurricaneData> data;
	final List<DangerZone> dangerZones;
	
	Hurricane(List<HurricaneData> data, List<DangerZone> dangerZones) {
		this.data = data;
		this.dangerZones = dangerZones;
	}
}
