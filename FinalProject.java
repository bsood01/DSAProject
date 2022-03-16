import java.io.File;
import java.util.Arrays;
import java.util.Scanner;


public class FinalProject {

	public static void main(String[] args) {
		/*System.out.println("Welcome To the Bus Management System!!");
		Scanner input = new Scanner(System.in);
		String[] tmp = {"find a shortest path", "search for a bus stop",
				"search for all trips given an arrival time"};
		
		for(int i=0; i<3;i++) {
			System.out.printf("Enter %d"+" to "+tmp[i]+"\n",i+1);
		}
		System.out.println("Or enter 'quit' to exit");
		
		//User input to see which feature they want to use
		while(true) {
			if (input.hasNextInt()) {	
				int option =input.nextInt();
				if(option==1) {
					option1();
					break;
				}
				else if(option==2) {
					option2();
					break;
				}
				else if(option==3) {
					option3();
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
		}*/
		option1();
	}
	private static void option1() {
		Scanner scan;
		File input = new File("stops.txt");
		Map map = new Map();
		//Mapping all stop ID's to a array Index
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
		//Make a new graph of size of the no. of stops
		Digraph graph=new Digraph(map.size());
		input = new File("stop_times.txt");
		try {
			scan = new Scanner(input);
			scan.nextLine();
			int tripID=0;
			int prevStop=0;
			//making a graph with all the trips in stop_times.txt
			while(scan.hasNextLine()) {
				String in = scan.nextLine();
				String[] split = in.split(",");
				//check of we're on the same trip ID
				if(Integer.parseInt(split[0])==tripID) {
					int from = map.getIndex(prevStop);
					//System.out.println(from);
					int to = map.getIndex(Integer.parseInt(split[3]));
					//System.out.println(to);
					//cost associated with stop_times.txt is 1
					DirectedEdge e = new DirectedEdge(from,to,1);
					//important so as to not add multiple edge between same vertices
					if(!graph.contains(e,from)) {
						graph.addEdge(e);
					}
					//save stopID for this line
					prevStop=Integer.parseInt(split[3]);
					//System.out.println(prevStop);
					//System.out.println("");
				}
				//else we move on to the next 
				else {
					tripID=Integer.parseInt(split[0]);
					//save stopID for this line
					prevStop=Integer.parseInt(split[3]);
				}
				
				
			}
			for(DirectedEdge e:graph.edges() ) {
				System.out.println(e.toString());
			}
			System.out.println(graph.E());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private static void option2() {
		
	}
	private static void option3() {
	
	}

}
