import java.awt.Color;


public class Fence implements Placeble {

	private int width, height;
	
	public Fence() {
		this.width = 16;
		this.height = 1;
	}
	
	public Fence(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public Color getColor() {
		return Color.CYAN;
	}

	@Override
	public Position update(Placeble[][] placebles, Position initialPosition) {
		return initialPosition;
	}

	@Override
	public boolean writeToLog() {
		return false;
	}

	@Override
	public boolean mustBeCached() {
		return false;
	}

	@Override
	public Position getCachedPosition() {
		return null;
	}

}
