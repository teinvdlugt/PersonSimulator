public class Position {

	private int x, y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Position() {
		this.x = 0;
		this.y = 0;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Position north(int amount) {
		return new Position(this.getX(), this.getY() - amount);
	}

	public Position south(int amount) {
		return new Position(this.getX(), this.getY() + amount);
	}

	public Position east(int amount) {
		return new Position(this.getX() + amount, this.getY());
	}

	public Position west(int amount) {
		return new Position(this.getX() - amount, this.getY());
	}

	public boolean isValid(Placeble[][] placebles) {
		return !(this.getX() < 0 || this.getY() < 0
				|| this.getX() >= placebles[0].length || this.getY() >= placebles.length);
	}

	public boolean isFull(Placeble[][] placebles) {
		return this.isValid(placebles)
				&& placebles[this.getY()][this.getX()] == null;
	}

	public String toString() {
		return "(" + x + "," + y + ")";
	}
}

enum Direction {

	NORTH, EAST, SOUTH, WEST;

}

class Distance {
	
	private Direction direction;
	private int distance;
	
	public Distance(Direction direction, int distance) {
		this.setDirection(direction);
		this.setDistance(distance);
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
}















