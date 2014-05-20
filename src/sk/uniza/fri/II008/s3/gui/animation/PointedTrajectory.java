package sk.uniza.fri.II008.s3.gui.animation;

public class PointedTrajectory implements Trajectory {
	private FactoryPosition [] positions;
	private float totalDistance;
	
	public PointedTrajectory(FactoryPosition... positions) {
		this.positions = positions;
		
		totalDistance = 0f;
		
		for(int i=1; i<positions.length; i++) {
			totalDistance += FactoryPosition.getDistance(positions[i-1], positions[i]);
		}
	}

	@Override
	public FactoryPosition getInterpolated(float coeficient) {
		float traveledDistance = coeficient*totalDistance;
		
		float previousPointDistance = 0f;
		float nextPointDistance = FactoryPosition.getDistance(positions[0], positions[1]);
		
		int nextPointIndex = 1;
		
		while(nextPointDistance < traveledDistance) {
			nextPointIndex++;
			
			previousPointDistance = nextPointDistance;
			nextPointDistance += FactoryPosition.getDistance(positions[nextPointIndex], positions[nextPointIndex-1]);
		}
		
		float coeficientInSegment = (traveledDistance - previousPointDistance) / (nextPointDistance-previousPointDistance);
		DirectTrajectory segment = new DirectTrajectory(positions[nextPointIndex-1], positions[nextPointIndex]);
		
		return segment.getInterpolated(coeficientInSegment);
	}
	
	public FactoryPosition[] getPositions() {
		return positions;
	}
	
	public float getLength() {
		return totalDistance;
	}
}
