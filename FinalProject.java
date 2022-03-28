import java.io.File;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;


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
		}
	}
	
	/**
	 * Method for finding shortest path
	 */
	private static void option1() {
		System.out.println("Preparing Graph...");
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
					prevStop=Integer.parseInt(split[3]);
					//System.out.println(prevStop);
					//System.out.println("");
				}
				//else we move on to the next 
				else {
					tripID=Integer.parseInt(split[0]);
					prevStop=Integer.parseInt(split[3]);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		input = new File("transfers.txt");
		try {
			scan = new Scanner(input);
			scan.nextLine();
			
			//adding tranfers.txt edges to the existing graph
			while(scan.hasNextLine()) {
				String in = scan.nextLine();
				String[] split = in.split(",");
				int from = map.getIndex(Integer.parseInt(split[0]));
				int to = map.getIndex(Integer.parseInt(split[1]));
				
				//checking the transfer type for the cost
				double weight =0;
				if(Integer.parseInt(split[2])==0) {
					weight=2;
				}
				else {
					weight=Double.parseDouble(split[3])/100;
				}
				
				DirectedEdge e = new DirectedEdge(from,to,weight);
				if(!graph.contains(e,from)) {
					graph.addEdge(e);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Graph is complete at this point
		
		Scanner inp = new Scanner(System.in);
		System.out.println("Enter StopId for starting stop (or enter 'quit' to exit): " );
		int stop1ID=0;
		int stop2ID=0;
		boolean quit=false;
		//get user input for the 2 stops
		while(true) {
			if (inp.hasNextInt()) {	
				int ID =inp.nextInt();
				if(map.contains(ID)) {
					if(stop1ID==0) {
						stop1ID=ID;
						System.out.println("Enter the 2nd bus stopId. ");
					}
					else {
						stop2ID=ID;
						break;
					}
				}
				else {
					System.out.println("Error. Enter a VALID bus stop (or 'quit' to exit): ");
				}
			}
			else if(inp.hasNext(("quit|Quit|q|Q"))){
				System.out.println("Exitting App.");
				quit=true;
				break;
			}
			else {
				System.out.println("Error. Enter a VALID bus stop ID (or 'quit' to exit): ");
				inp.next();
			}
		}
		if(!quit) {
			//getting the shortest path
			Dijkstra d = new Dijkstra();
			d.shortestPath(map.getIndex(stop1ID), graph);
			double[] distTo= d.getDistTo();
			DirectedEdge[] edgeTo=d.getEdgeTo();
			if(distTo[map.getIndex(stop2ID)]!=-1) {
				String path=Integer.toString(stop2ID);
				for(DirectedEdge e = edgeTo[map.getIndex(stop2ID)]; e != null; e = edgeTo[e.from()]) {
					path = map.getStopID(e.from())+"->"+path;
				}
				System.out.println("Path: "+path);
				System.out.println("Associated Cost: "+distTo[map.getIndex(stop2ID)]);
				
			}
			else {
				System.out.printf("No path exists between %d and %d",stop1ID,stop2ID);
			}
		}
		
	}
	private static void option2() {
		Scanner scan;
		File input = new File("stops.txt");
		try {
			scan = new Scanner(input);
			List<String> list=new ArrayList<>();
			list.add(scan.nextLine());
			ArrayList<String> tmp = null;
			TST t = new TST();
			int lineIndex=1;
			while(scan.hasNextLine()) {
				String in = scan.nextLine();
				list.add(in);
				String[] temp = in.split(",");
				String[] split=temp[2].split(" ");
				tmp = new ArrayList(Arrays.asList(split));
				while(tmp.get(0).equals("NB") ||tmp.get(0).equals("SB") ||
						tmp.get(0).equals("WB") || tmp.get(0).equals("EB") ||
						tmp.get(0).equals("FLAGSTOP")) {
					String keyword = tmp.remove(0);
					tmp.add(keyword);
					StringBuilder builder = new StringBuilder();
					int i=0;
					for (String value : tmp) {
					    builder.append(value);
					    i++;
					    if(i!=tmp.size())
					    	builder.append(" ");
					}
					temp[2]=builder.toString();
				}
				t.put(temp[2],lineIndex);
				lineIndex++;
			}
			
			
			//We can use keyWithPrefix() function as the valid input is only
			//full name or first few chars
			System.out.println("Enter the full Stop Name or it's first few letters. (or 'quit' to exit)-");
			boolean quit=false;
			//get user input for StopName
			String stopName="";
			scan = new Scanner(System.in);
			while(true) {
				if(scan.hasNext(("quit|Quit|q|Q"))){
					System.out.println("Exitting App.");
					quit=true;
					break;
				}
					
				stopName =scan.nextLine();
				LinkedList<String> matching = (LinkedList<String>) t.keysWithPrefix(stopName.toUpperCase());
				if(matching.size()!=0) {
					System.out.println("Info of stops with matching criteria:\n");
					System.out.println(list.get(0));
					for(String s : matching) {
						System.out.println(list.get((int) t.get(s)));
					}
					break;
				}
				else {
					System.out.println("Error. No matched found, enter a correct Stop Name (or 'quit' to exit): ");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static void option3() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter Arrival Time in 24h format->hh:mm:ss (or enter 'quit' to exit): " );
		boolean quit=false;
		//get user input for Arrival Time
		String time="";
		while(true) {
			if(scan.hasNext(("quit|Quit|q|Q"))){
				System.out.println("Exitting App.");
				quit=true;
				break;
			}
		
			else if (scan.hasNextLine()) {	
				time =scan.nextLine();
				//use the java LocalTime library to see if its a valid time
				try {
					LocalTime.parse(time);
					//user input is required as hh:mm:ss but file could be h:mm:ss
					if(time.charAt(0)=='0') {
						StringBuilder sb = new StringBuilder(time);
						sb.deleteCharAt(0);
						time=sb.toString();
					}
					break;
				}
				catch (DateTimeParseException e) {
					System.out.println("Error. Enter a VALID Arrival Time in 24h format->hh:mm:ss (or 'quit' to exit): ");
				}
			}
		}
		
		if(!quit) {
			System.out.println("Getting your Information ready...");
			File input = new File("stop_times.txt");
			
			//Making a list of all the trips that match the arrival_time
			List<Trip> list=new ArrayList<Trip>();  
			try {
				scan = new Scanner(input);
				scan.nextLine();
				while(scan.hasNextLine()) {
					String in = scan.nextLine();
					String[] split = in.split(",");
					if(split[1].replace(" ", "").equals(time)) {
						Trip t = new Trip();
						t.tripId=Integer.parseInt(split[0]);
						t.arrivalTime=split[1];
						t.departureTime=split[2];
						t.stopID=Integer.parseInt(split[3]);
						t.stopSequence=Integer.parseInt(split[4]);
						if(!split[5].equals("")) {
							t.stopHeadsign=Integer.parseInt(split[5]);
						}
						t.pickupType=Integer.parseInt(split[6]);
						t.dropOffType=Integer.parseInt(split[7]);
						if(split.length==9) {
							t.shapeDistTraveled=Double.parseDouble(split[8]);
						}
						list.add(t);
					}
				}
				Collections.sort(list);
				if(list.isEmpty()) {
					System.out.println("Sorry no routes macthing input arrival time :( .");
				}
				else {
					System.out.println("Ready :)!!");
					System.out.println();
					System.out.println("| Trip ID| Arr Time| Dep Time|StopID|Seq|HS|PU|DO|  SDT  |");
					System.out.println("|--------|---------|---------|------|---|--|--|--|-------|");
					for(Trip t:list) {
						System.out.println(t);
					}
				}
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	
	}
	

}
