package sk.uniza.fri.II008.s3.model;

public class Navigation
{
	public interface Point
	{
		public Location getLocation();
	}

	public enum Location
	{
		A(0), B(1), C(2);

		public final int id;

		private Location(int id)
		{
			this.id = id;
		}
	}

	private static final double[][] distances =		// km
	{
		{
			0, 0.4, 1.14
		},
		{
			1.52, 0, 0.74
		},
		{
			0.78, 1.18, 0
		}
	};

	public static double getDistance(Point pointA, Point pointB)
	{
		return distances[pointA.getLocation().id][pointB.getLocation().id];
	}

	public static double getDistance(Location locationA, Location locationB)
	{
		return distances[locationA.id][locationB.id];
	}

	public static double getDuration(Point pointA, Point pointB, double speed)
	{
		return getDuration(pointA.getLocation(), pointB.getLocation(), speed);
	}

	public static double getDuration(Location locationA, Location locationB, double speed)
	{
		return 1f * (getDistance(locationA, locationB) / speed) * 3600;
	}
}
