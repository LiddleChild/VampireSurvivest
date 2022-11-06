package util.math;

public class Mathf {
	
	public static float invSqrt(float x) {
	    float xhalf = 0.5f * x;
	    int i = Float.floatToIntBits(x);
	    i = 0x5f3759df - (i >> 1);
	    x = Float.intBitsToFloat(i);
	    x *= (1.5f - xhalf * x * x);
	    return x;
	}
	
	public static float[] unitVector(float x, float y) {
		float amount = Mathf.invSqrt(x * x + y * y);
		return new float[]{x * amount, y * amount};
	}
	
}
