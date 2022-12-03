package util;

public class Time {
	
	public static float getNanoSecond() {
		return System.nanoTime() / 1000000000.f;
	}
	
}
