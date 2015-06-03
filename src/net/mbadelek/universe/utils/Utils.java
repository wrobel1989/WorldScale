package net.mbadelek.universe.utils;

public class Utils {
	
	// if value outside range, then ends up at some of the range value
	public static double trim(double value, double rangeStart, double rangeEnd) {
		if (value > rangeEnd)
			return rangeEnd;
		if (value < rangeStart)
			return rangeStart;
		return value;
	}
}
