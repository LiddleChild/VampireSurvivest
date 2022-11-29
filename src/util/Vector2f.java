package util;

public class Vector2f implements Comparable<Vector2f> {
	
	public float x, y;
	
	/*
	 * CONSTRUCTOR
	 */
	
	public Vector2f() {
		this(0.f, 0.f);
	}
	
	public Vector2f(Vector2f vec) {
		this(vec.x, vec.y);
	}
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/*
	 * ADDITION
	 */
	public Vector2f addEqual(Vector2f other) {
		x += other.x;
		y += other.y;
		
		return this;
	}
	
	public Vector2f add(Vector2f other) {
		return new Vector2f(x + other.x, y + other.y);
	}
	
	public Vector2f add(int other) {
		return new Vector2f(x + other, y + other);
	}
	
	/*
	 * SUBSTRACTION 
	 */
	public Vector2f subtractEqual(Vector2f other) {
		x -= other.x;
		y -= other.y;
		
		return this;
	}
	
	public Vector2f subtract(Vector2f other) {
		return new Vector2f(x - other.x, y - other.y);
	}
	
	/*
	 * MULTIPLICATION
	 */
	public Vector2f multiplyEqual(float other) {
		x *= other;
		y *= other;
		
		return this;
	}
	
	public Vector2f multiply(float other) {
		return new Vector2f(x * other, y * other);
	}
	
	public float getSize() {
		return (float) Math.sqrt(x * x + y * y);
	}
	
	public Vector2f normalize() {
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
	public int compareTo(Vector2f other) {
		return Float.compare(getSize(), other.getSize());
	}
	
	public boolean isInNorth(Vector2f other) {
		float sy = Math.abs(other.y - y);
		float sx = Math.abs(other.x - x);
		return other.y < y && sy >= sx;
	}
	
	public boolean isInSouth(Vector2f other) {
		float sy = Math.abs(other.y - y);
		float sx = Math.abs(other.x - x);
		return other.y > y && sy >= sx;
	}
	
	public boolean isInWest(Vector2f other) {
		float sy = Math.abs(other.y - y);
		float sx = Math.abs(other.x - x);
		return other.x < x && sx >= sy;
	}
	
	public boolean isInEast(Vector2f other) {
		float sy = Math.abs(other.y - y);
		float sx = Math.abs(other.x - x);
		return other.x > x && sx >= sy;
	}
	
	public boolean[] getDirection(Vector2f other) {
		return new boolean[] {
			isInNorth(other),
			isInEast(other),
			isInSouth(other),
			isInWest(other)
		};
	}
	
}
