package util;

public class Vector2 implements Comparable<Vector2> {
	
	public float x, y;
	
	/*
	 * CONSTRUCTOR
	 */
	
	public Vector2() {
		this(0.f, 0.f);
	}
	
	public Vector2(Vector2 vec) {
		this(vec.x, vec.y);
	}
	
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/*
	 * ADDITION
	 */
	public Vector2 addEqual(Vector2 other) {
		x += other.x;
		y += other.y;
		
		return this;
	}
	
	public Vector2 add(Vector2 other) {
		return new Vector2(x + other.x, y + other.y);
	}
	
	public Vector2 add(int other) {
		return new Vector2(x + other, y + other);
	}
	
	/*
	 * SUBSTRACTION 
	 */
	public Vector2 substractEqual(Vector2 other) {
		x -= other.x;
		y -= other.y;
		
		return this;
	}
	
	public Vector2 substract(Vector2 other) {
		return new Vector2(x - other.x, y - other.y);
	}
	
	/*
	 * MULTIPLICATION
	 */
	public Vector2 multiplyEqual(float other) {
		x *= other;
		y *= other;
		
		return this;
	}
	
	public Vector2 multiply(float other) {
		return new Vector2(x * other, y * other);
	}
	
	public float getSize() {
		return (float) Math.sqrt(x * x + y * y);
	}
	
	public Vector2 normalize() {
		float amount = Mathf.invSqrt(x * x + y * y);
		x *= amount;
		y *= amount;
		
		return this;
	}
	
	@Override
	public String toString() {
		return String.format("[%.2f %.2f]", x, y);
	}

	@Override
	public int compareTo(Vector2 other) {
		return Float.compare(getSize(), other.getSize());
	}
	
}
