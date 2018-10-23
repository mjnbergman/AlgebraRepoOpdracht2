


package com.everythingCauseLazy;

import java.io.*;
import java.nio.file.Files;
import java.util.regex.Pattern;

/**
 * 
 * @author Maiko Bergman
 * 
 * A class that parses input and output. It can read a file in the specified format and pass the parsed data
 * to further classes. It also acts as an endpoint for the programme, after the calculations have finished
 * the output data is outputted by this class in the required format to an output file called 'output.txt'.
 *
 */

public class InputOutputParser {

	public InputOutputParser() {
		
	}
	
	// This function just reads a file with the specified name
	private BufferedReader openFileFromString(String file) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(file));
		return reader;
	}
	
	/*
	 * This function does the actual parsing of the input file
	 */
	public void parseFile(String file) {
		try {
			BufferedReader bf = openFileFromString(file);
			
			String line = null;
			
			int radix = -1;
			int modulus = 0;
			String poly1 = "";
			String poly2 = "";
			String poly3 = "";
			String operation = "";
			
			// Regex to match the required functions
			String operatorMatchPattern = "\\[(add-poly|subtract-poly|multiply|long-div-poly|equals-poly-mod|inverse|reduce)\\]";
			
		    while ((line = bf.readLine()) != null) {
     
		        // If this is the line containing the radix, the same logic is applied to further if statements
		        if(line.indexOf("[mod] ") != -1) {
		        	
		        	// Extract the radius from the string
		        	modulus = Integer.parseInt(line.substring(line.indexOf("[mod] ") + "[mod] ".length()));
		        }
		        else if(line.indexOf("[f] ") != -1) {
		        	poly1 = line.substring(line.indexOf("[f] ") + "[f] ".length());
		        }
		        else if(line.indexOf("[g] ") != -1) {
		        	poly2 = line.substring(line.indexOf("[g] ") + "[g] ".length());
		        }
		        else if(line.indexOf("[h] ") != -1) {
		        	poly3 = line.substring(line.indexOf("[h] ") + "[h] ".length());
		        }
		        // If this line matches the regex, AKA it's the line specifying the function
		        else if(line.matches(operatorMatchPattern)) {
		        	System.out.println("Deze lijn is een operator!!!");
		        	operation = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
		        }
		        
		    }
		    
			//System.out.println("De radix is: " + radix);
			System.out.println("Het eerste getal is: " + poly1);
			System.out.println("Het tweede getal is: " + poly2);
			//System.out.println("Het tweede getal is: " + poly3);
			System.out.println("De modulus is: " + modulus);
			System.out.println("De operatie is: " + operation);
			
			//System.out.print("De array vorm van getal 1 is: ");
		//	outputArray(stringToArray(poly1));
			
			// Compile the found data into a ParsedInputData object for ease of use.
		//	ParsedInputData pd = new ParsedInputData(radix, modulus, poly1, poly2);
			
			boolean error = false;
			boolean poNeg = false;
			boolean negNeg = false;
			

			
			
			// Pre-define a ParsedOutputDataObject for further use
			ParsedOutputData pod = new ParsedOutputData();
			
			// Switch over the operation, calling the appropriate static function in the BigPolyCalculator class
			// Also catches any exceptions caused by for instance a digit in one of the input number being
			// bigger than the radix.
			
			//try {
				switch(operation) {
				case "add-poly":
					int[] o = BigPolyCalculator.add(stringToArray(poly1), stringToArray(poly2), modulus);
					printPoly(o);
					break;
				case "multiply":
					int[] p = BigPolyCalculator.multiply(stringToArray(poly1), stringToArray(poly2), modulus);
					printPoly(p);
					break;
				case "subtract-poly":
					int[] ot = BigPolyCalculator.sub(stringToArray(poly1), stringToArray(poly2), modulus);
					printPoly(ot);
					break;
				case "long-div-poly":
					int[][] ott = BigPolyCalculator.longPolyDivision(stringToArray(poly1), stringToArray(poly2), modulus);
					printPoly(ott[0]);
					System.out.println("En de remainder: ");
					printPoly(ott[1]);
					
					break;
				case "equals-poly-mod":
					
					boolean ottt = BigPolyCalculator.equalsPolyMod(stringToArray(poly1), stringToArray(poly2), stringToArray(poly3));
					System.out.println("De polynimals zijn equal mod poly3: " + ottt);
					
					break;
				}
			//}catch(Exception ex) {
				//System.out.println("ERROR! " + ex);
				//error = true;
			//}	
			
			//this.outputData(pod, error);
			
				
		} catch(IOException ex) {
			System.out.println("Could not open or read file!\nCheck if the filename is correct!");
		}


		
	}
	/*
	 * An output data function, outputs the data in the specified format
	 * TODO: ADD the ans-a, ans-b and ans-d output lines.
	 */
	public void outputData(ParsedOutputData pod, boolean error) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"))) {

			String ansString = "[answer] " + (error ? "ERROR" : pod.getAnswer());
			String countAddString = "[count-add] " + BigPolyCalculator.ADD_COUNT;
			String countMulString = "[count-mul] " + BigPolyCalculator.MUL_COUNT;

			bw.write(ansString);
			bw.newLine();
			bw.write(countAddString);
			bw.newLine();
			bw.write(countMulString);
			
			// no need to close it.
			//bw.close();

			//System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		}
	}
	
	public static int[] stringToArray(String n1) {
		//System.out.println("n1 first = " + n1.replaceAll("\\{", "").replaceAll("\\}", ""));
		n1 = n1.replaceAll("\\{", "").replaceAll("\\}", "");
		//System.out.println("output length = " + (int) Math.ceil((n1.length())/2.0));
		int[] output = new int[(int)Math.ceil((n1.length())/2.0)];
		int index = 0;
		
		for(int i = 0; i < n1.length(); i++) {
			if(n1.charAt(i) != ',' && n1.charAt(i) != ' ') {
				//System.out.println("n1.charAt = " + n1.charAt(i) + " and output[i] = " + (int) (n1.charAt(i) - 48));
				output[index] = (int) n1.charAt(i) - 48;
				index++;
			}
		}
		return output;
	}
	
	public static void outputArray(int[] array) {
		for(int i = 0; i < array.length; i++) {
			System.out.println(array[i]);
		}
	}
	
	public static void printPoly(int[] poly) {
		String result = "";
		int degree = poly.length - 1;
		for(int i = 0; i < poly.length; i++) {
			if(poly[i] != 0) {
				if(i != 0) {
					if(poly[i] < 0) {
						result += "-";
					}
					else {
						result += "+";
					}
				}
				if(Math.abs(poly[i]) != 1 || degree == 0) {
					result += Math.abs(poly[i]);
				}
				if(degree != 0 && degree != 1) {
					result += "X^" + degree;
				}
				else if(degree == 1) {
					result += "X";
				}
			}
			degree--;
		}
		if(result == "") {
			result = "0";
		}
		System.out.println(result);
	}
}
