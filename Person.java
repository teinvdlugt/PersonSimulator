import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Person implements Placeble {

	private String christianName = "Unknown", surName = "Unknown";
	private Gender gender;
	private int hungerStatus = 1000;
	private int sleepStatus = 2000;
	private boolean inARestaurant;
	private boolean inAHotel;
	private Position cachedPosition;
	private boolean alive = true;

	public enum Gender {
		MALE, FEMALE
	}

	public Person(String christianName, String surName, Gender gender) {
		this.christianName = christianName;
		this.surName = surName;
		this.gender = gender;
	}

	public Person(Gender gender) {
		this.gender = gender;
	}

	@Override
	public int getWidth() {
		return 1;
	}

	@Override
	public int getHeight() {
		return 1;
	}

	@Override
	public Color getColor() {
		return Color.DARK_GRAY;
	}

	@Override
	public boolean writeToLog() {
		return true;
	}

	public boolean isAlive() {
		return alive;
	}

	public void makeDead() {
		alive = false;
	}

	public void setHungerStatus(int hungerStatus) {
		this.hungerStatus = hungerStatus;
	}

	public int getHungerStatus() {
		return hungerStatus;
	}

	public void decreaseHungerStatusBy(int toDecrease) {
		hungerStatus -= toDecrease;
		if (hungerStatus <= 0) {
			System.out.println(christianName + " " + surName + " has starved to death.");
			this.makeDead();
		}
	}

	public void increaseHungerStatusBy(int toIncrease) {
		hungerStatus += toIncrease;
	}
	
	public int getSleepStatus() {
		return sleepStatus;
	}
	
	public void setSleepStatus(int sleepStatus) {
		this.sleepStatus = sleepStatus;
	}
	
	public void decreaseSleepStatusBy(int toDecrease) {
		sleepStatus -= toDecrease;
		if (sleepStatus < 0) {
			System.out.println(christianName + " " + surName + " has died of exhaustion");
			this.makeDead();
		}
	}
	
	public void increaseSleepStatusBy(int toIncrease) {
		sleepStatus += toIncrease;
	}
 
	public String getChristianName() {
		return christianName;
	}

	public void setChristianName(String christianName) {
		this.christianName = christianName;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public static Distance findTheNearestRestaurant(Placeble[][] placebles,
			Position position) {
		int distance;
		
		int posX = position.getX();
		int posY = position.getY();

		ArrayList<Position> positions = new ArrayList<Position>();

		for (int i = 0; i < placebles.length; i++) {
			for (int j = 0; j < placebles[i].length; j++) {
				if (placebles[i][j] != null
						&& placebles[i][j] instanceof Restaurant) {
					positions.add(new Position(j, i));
				}
			}
		}

		ArrayList<Integer> distances = new ArrayList<Integer>();

		for (Position restaurantPosition : positions) {
			int resX = restaurantPosition.getX();
			int resY = restaurantPosition.getY();

			int distX = Math.abs(resX - posX);
			int distY = Math.abs(resY - posY);

			distances.add(distX + distY);
		}

		if (distances.size() == 0) {
			return null;
		}
		
		distance = distances.get(posOfMinValue(distances));

		Position posOfClosestRestaurant = positions
				.get(posOfMinValue(distances));

		// -----------------------------------------------------------------------

		int resX = posOfClosestRestaurant.getX();
		int resY = posOfClosestRestaurant.getY();

		int diffX = Math.abs(resX - posX);
		int diffY = Math.abs(resY - posY);

		if (diffX > diffY) {
			if (posX < resX) {
				return new Distance(Direction.EAST, distance);
			} else if (posX > resX) {
				return new Distance(Direction.WEST, distance);
			}
		}

		if (posY < resY) {
			return new Distance(Direction.SOUTH, distance);
		} else if (posY > resY) {
			return new Distance(Direction.NORTH, distance);
		} else {
			System.out.println("You are already in a restaurant");
			return null;
		}
	}

	public static Distance findTheNearestHotel(Placeble[][] placebles, Position position) {
		int distance;
		
		int posX = position.getX();
		int posY = position.getY();

		ArrayList<Position> positions = new ArrayList<Position>();

		for (int i = 0; i < placebles.length; i++) {
			for (int j = 0; j < placebles[i].length; j++) {
				if (placebles[i][j] != null
						&& placebles[i][j] instanceof Hotel) {
					positions.add(new Position(j, i));
				}
			}
		}

		ArrayList<Integer> distances = new ArrayList<Integer>();

		for (Position hotelPosition : positions) {
			int hotelX = hotelPosition.getX();
			int hotelY = hotelPosition.getY();

			int distanceX = Math.abs(hotelX - posX);
			int distanceY = Math.abs(hotelY - posY);

			distances.add(distanceX + distanceY);
		}

		if (distances.size() == 0) {
			return null;
		}

		distance = distances.get(posOfMinValue(distances));

		Position posOfClosestHotel = positions
				.get(posOfMinValue(distances));

		// -----------------------------------------------------------------------

		int hotelX = posOfClosestHotel.getX();
		int hotelY = posOfClosestHotel.getY();

		int diffX = Math.abs(hotelX - posX);
		int diffY = Math.abs(hotelY - posY);

		if (diffX > diffY) {
			if (posX < hotelX) {
				return new Distance(Direction.EAST, distance);
			} else if (posX > hotelX) {
				return new Distance(Direction.WEST, distance);
			}
		}

		if (posY < hotelY) {
			return new Distance(Direction.SOUTH, distance);
		} else if (posY > hotelY) {
			return new Distance(Direction.NORTH, distance);
		} else {
			System.out.println("You are already in a hotel");
			return null;
		}
	}
	
	public static int posOfMinValue(ArrayList<Integer> arrayList) {
		int currentPos = 0;
		int currentValue = arrayList.get(0);

		for (int i = 1; i < arrayList.size(); i++) {
			if (arrayList.get(i) < currentValue) {
				currentPos = i;
				currentValue = arrayList.get(i);
			}
		}

		return currentPos;

	}

	@Override
	public Position update(Placeble[][] placebles, Position initialPosition) {
		if (inARestaurant) { // Which means the Person is cached
			if (updateInARestaurant()) { // Which means the Person is allowed to go out the Restaurant
				Position pos = Square.findFreePlaceAroundPosition(placebles, cachedPosition);

				inARestaurant = pos == null;
				return pos;
			}
			return null; // The Person will be cached
		} else if (inAHotel) {
			if (updateInAHotel()) { // Which means the Person is allowed to go out the Hotel
				Position pos = Square.findFreePlaceAroundPosition(placebles, cachedPosition);

				inAHotel = pos == null;
				return pos;
			}
			return null; // The Person will be cached
		}

		// ----------------------------------------------------------------------------

		Direction direction;
		Random randomGenerator = new Random();

		int chanceNorth = 1;
		int chanceEast = 1;
		int chanceSouth = 1;
		int chanceWest = 1;

		if (hungerStatus < 500) {
			Distance resDist = findTheNearestRestaurant(placebles,
					initialPosition);
			
			int dirIncrease = 6;
			// If the Restaurant is far away, they must walk quicker 
			if (resDist != null && resDist.getDistance() > 120) {
				dirIncrease = (int) Math.floor(resDist.getDistance() / 20);
			}
			
			if (resDist != null) {
				switch (resDist.getDirection()) {
				case NORTH:
					chanceNorth += dirIncrease;
					break;
				case EAST:
					chanceEast += dirIncrease;
					break;
				case SOUTH:
					chanceSouth += dirIncrease;
					break;
				case WEST:
					chanceWest += dirIncrease;
				}
			}
		} else if (sleepStatus < 500) {
			Distance hotelDist = findTheNearestHotel(placebles, initialPosition);
			
			int dirIncrease = 6;
			if (hotelDist != null && hotelDist.getDistance() > 120) {
				dirIncrease = (int) Math.floor(hotelDist.getDistance() / 20);
			}
			
			if (hotelDist != null) {
				switch (hotelDist.getDirection()) {
				case NORTH:
					chanceNorth += dirIncrease;
					break;
				case EAST:
					chanceEast += dirIncrease;
					break;
				case SOUTH:
					chanceSouth += dirIncrease;
					break;
				case WEST:
					chanceWest += dirIncrease;
				}
			}
		}

		int chanceTotal = chanceNorth + chanceEast + chanceSouth + chanceWest;

		int random = randomGenerator.nextInt(chanceTotal);

		if (random < chanceTotal - chanceEast - chanceSouth - chanceWest) {
			direction = Direction.NORTH;
		} else if (random < chanceTotal - chanceSouth - chanceWest) {
			direction = Direction.EAST;
		} else if (random < chanceTotal - chanceWest) {
			direction = Direction.SOUTH;
		} else {
			direction = Direction.WEST;
		}

		// ------------------------------------------------------------------------------------

		int x = initialPosition.getX();
		int y = initialPosition.getY();

		switch (direction) {
		case NORTH:
			if (y != 0) {
				if (placebles[y - 1][x] != null) {
					if (placebles[y - 1][x] instanceof Person) {
						return initialPosition;
					} else if (placebles[y - 1][x] instanceof Restaurant) {
						if (hungerStatus < 500) {
							cachedPosition = initialPosition;
							this.inARestaurant = true;
							return null;
						} else {
							return initialPosition;
						}
					} else if (placebles[y - 1][x] instanceof Hotel) {
						if (sleepStatus < 500) {
							cachedPosition = initialPosition;
							this.inAHotel = true;
							return null;
						} else {
							return initialPosition;
						}
					} else if (placebles[y - 1][x] instanceof Fence) {
						return initialPosition;
					}
				}
				initialPosition.setY(y - 1);
				this.decreaseHungerStatusBy(1);
				this.decreaseSleepStatusBy(1);
			}
			break;
		case EAST:
			if (x != placebles[0].length - 1) {
				if (placebles[y][x + 1] != null) {
					if (placebles[y][x + 1] instanceof Person) {
						return initialPosition;
					} else if (placebles[y][x + 1] instanceof Restaurant) {
						if (hungerStatus < 500) {
							cachedPosition = initialPosition;
							this.inARestaurant = true;
							return null;
						} else {
							return initialPosition;
						}
					} else if (placebles[y][x + 1] instanceof Hotel) {
						if (sleepStatus < 500) {
							cachedPosition = initialPosition;
							this.inAHotel = true;
							return null;
						} else {
							return initialPosition;
						}
					} else if (placebles[y][x + 1] instanceof Fence) {
						return initialPosition;
					}
				}
				initialPosition.setX(x + 1);
				this.decreaseHungerStatusBy(1);
				this.decreaseSleepStatusBy(1);
			}
			break;
		case SOUTH:
			if (y != placebles.length - 1) {
				if (placebles[y + 1][x] != null) {
					if (placebles[y + 1][x] instanceof Person) {
						return initialPosition;
					} else if (placebles[y + 1][x] instanceof Restaurant) {
						if (hungerStatus < 500) {
							cachedPosition = initialPosition;
							this.inARestaurant = true;
							return null;
						} else {
							return initialPosition;
						}
					} else if (placebles[y + 1][x] instanceof Hotel) {
						if (sleepStatus < 500) {
							cachedPosition = initialPosition;
							this.inAHotel = true;
							return null;
						} else {
							return initialPosition;
						}
					} else if (placebles[y + 1][x] instanceof Fence) {
						return initialPosition;
					}
				}
				initialPosition.setY(y + 1);
				this.decreaseHungerStatusBy(1);
				this.decreaseSleepStatusBy(1);
			}
			break;
		case WEST:
			if (x != 0) {
				if (placebles[y][x - 1] != null) {
					if (placebles[y][x - 1] instanceof Person) {
						return initialPosition;
					} else if (placebles[y][x - 1] instanceof Restaurant) {
						if (hungerStatus < 500) {
							cachedPosition = initialPosition;
							this.inARestaurant = true;
							return null;
						} else {
							return initialPosition;
						}
					} else if (placebles[y][x - 1] instanceof Hotel) {
						if (sleepStatus < 500) {
							cachedPosition = initialPosition;
							this.inAHotel = true;
							return null;
						} else {
							return initialPosition;
						}
					} else if (placebles[y][x - 1] instanceof Fence) {
						return initialPosition;
					}
				}
				initialPosition.setX(x - 1);
				this.decreaseHungerStatusBy(1);
				this.decreaseSleepStatusBy(1);
			}
			break;
		}

		return alive ? initialPosition : null;
	}

	public boolean updateInARestaurant() {
		increaseHungerStatusBy(10);

		if (hungerStatus >= 1000) {
			return true;
		}

		return false;
	}
	
	public boolean updateInAHotel() {
		increaseSleepStatusBy(1);
		
		if (sleepStatus >= 2000) {
			return true;
		}
		
		return false;
	}

	@Override
	public boolean mustBeCached() {
		return inARestaurant || inAHotel;
	}

	@Override
	public Position getCachedPosition() {
		return cachedPosition;
	}
}