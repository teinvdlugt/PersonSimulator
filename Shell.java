import java.util.Scanner;


public class Shell implements Runnable {

	private Scanner scanner = new Scanner(System.in);
	
	private World world;
	
	public Shell(World world) {
		this.world = world;
	}
	
	@Override
	public void run() {
		while (1 + 1 == 2) {
			
			System.out.print("../> ");
			
			String input = scanner.nextLine();
			world.doStuff(input);
		}
		
	}
	
}
