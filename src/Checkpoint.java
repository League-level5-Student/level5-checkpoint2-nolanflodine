import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Checkpoint {
	public static void main(String[] args) {
		/*
		 * The auto-mgp.data file contains the miles-per-gallon data on various different types of cars.
		 * Each line of the file provides the data for one CarMPGEntry object.
		 * (source: http://archive.ics.uci.edu/ml/datasets)
		 * 
		 * Use the method provided to get an ArrayList of CarMPGEntry objects. Convert the ArrayList into a Stream.
		 * Using streams, perform the following:
		 *
		 * 1. Print the entire list.
		 * 
		 * 
		 * 2. Print the miles per gallon of each entry.
		 * 
		 * 
		 * 3. Print alphabetized car names of the list.
		 * 
		 * 
		 * 4. Print the list with all 8 cylinder cars removed.
		 * 
		 * 
		 * 5. Print only the cars with "toyota" in the name.
		 */
		ArrayList<CarMPGEntry> ce = readCarMPGEntryDataFromFile();
		Stream<CarMPGEntry> str1 = ce.stream();
		str1.forEach(y -> System.out.println(y));
		System.out.println("Vroom");
		
		Stream<CarMPGEntry> str2 = ce.stream();
		str2.forEach(y -> System.out.println(y.mpg));
		System.out.println("vroooomm");
		
		Stream<CarMPGEntry> str3 = ce.stream();
		str3.sorted((c1,c2) -> c1.carName.compareTo(c2.carName)).forEach(y -> System.out.println(y.carName));
		
		Stream<CarMPGEntry> str4 = ce.stream();
		str4.filter(y -> y.cylinders!=8).forEach(y -> System.out.println(y.cylinders));
		
		Stream<CarMPGEntry> str5 = ce.stream();
		str5.filter(y -> y.carName.contains("toyota")).forEach(y -> System.out.println(y.carName));
	}
	
	public static ArrayList<CarMPGEntry> readCarMPGEntryDataFromFile(){
		ArrayList<CarMPGEntry> carList = new ArrayList<CarMPGEntry>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("auto-mpg.data"));
			
			String line = br.readLine();
			while(line != null) {
				String entry = "";
				ArrayList<String> splitEntry = new ArrayList<String>();
				for(int i = 0; i < line.length(); i++) {
					char c = line.charAt(i);
					if(c == ' ' || c == '\t') {
						if(entry.length() > 0) {
							splitEntry.add(entry);
							entry = "";
						}
					}else if(c =='\"') {
						i++;
						c = line.charAt(i);
						while(c != '\"') {
							entry += c;
							c = line.charAt(++i);
						}
						splitEntry.add(entry);
					}else {
						entry += c;
					}
				}
				
				CarMPGEntry cmpg = new CarMPGEntry();
				
				cmpg.mpg = Float.parseFloat(splitEntry.get(0));
				cmpg.cylinders = Integer.parseInt(splitEntry.get(1));
				cmpg.displacement = Float.parseFloat(splitEntry.get(2));
				try {
					cmpg.horsePower = Float.parseFloat(splitEntry.get(3));
					
				}catch(NumberFormatException e) {
					cmpg.horsePower = -1;
				}
				cmpg.weight = Float.parseFloat(splitEntry.get(4));
				cmpg.acceleration = Float.parseFloat(splitEntry.get(5));
				cmpg.modelYear  = Integer.parseInt(splitEntry.get(6));
				cmpg.origin = Integer.parseInt(splitEntry.get(7));
				cmpg.carName = splitEntry.get(8);
				carList.add(cmpg);

				line = br.readLine();
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return carList;
	}
}
