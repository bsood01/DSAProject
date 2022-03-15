import java.io.File;
import java.util.Arrays;
import java.util.Scanner;


public class FinalProject {

	public static void main(String[] args) {
		System.out.println("Welcome To the Bus Management System!!");
		Scanner input = new Scanner(System.in);
		String[] tmp = {"find a shortest path", "search for a bus stop",
				"search for all trips given an arrival time"};
		
		for(int i=0; i<3;i++) {
			System.out.printf("Enter %d"+" to "+tmp[i]+"\n",i+1);
		}
		System.out.println("Or enter 'quit' to exit");
		while(true) {
			if (input.hasNextInt()) {	
				int option =input.nextInt();
				if(option==1||option==2||option==3) {
					System.out.println(option);
					break;
				}
				else {
					System.out.println("Error. Please Enter 1, 2 or 3 "
							+ "to select from options above (or 'quit' to exit): ");
				}
			}
			else if(input.hasNext(("quit|Quit|q|Q"))){
				System.out.println("Exitting App.");
				break;
			}
			else {
				System.out.println("Error. Please Enter 1, 2 or 3 "
					+ "to select from options above (or 'quit' to exit): ");
				input.next();
			}
		}
	}
	private void option1() {
		Scanner scan;
		File input = new File("stops.txt");
		Map map = new Map();
		try {
			scan = new Scanner(input);
			scan.nextLine();
			while(scan.hasNextLine()) {
				String in = scan.nextLine();
				String[] split = in.split(",");
				map.addStop(Integer.parseInt(split[0]));
			}
			System.out.println(map.getStopID(8756));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private void option2() {
		
	}
	private void option3() {
	
	}
	private void option4() {	
		
	}

}
