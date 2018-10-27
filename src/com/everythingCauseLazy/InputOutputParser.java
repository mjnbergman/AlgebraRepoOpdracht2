


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
			
			int degree = 0;
			int modulus = 0;
			String poly1 = "";
			String poly2 = "";
			String poly3 = "";
			String operation = "";
			String modPoly = "";
			String polyA = "";
			String polyB = "";
			
			// Regex to match the required functions
			String operatorMatchPattern = "\\[(add-poly|subtract-poly|multiply-poly|long-div-poly|equals-poly-mod|euclid-poly|irreducible|find-irred|add-field|subtract-field|multiply-field|division-field|inverse-field|display-field)\\]";
			
		    while ((line = bf.readLine()) != null) {
     
		        // If this is the line containing the radix, the same logic is applied to further if statements
		        if(line.indexOf("[mod] ") != -1) {
		        	
		        	// Extract the radius from the string
		        	modulus = Integer.parseInt(line.substring(line.indexOf("[mod] ") + "[mod] ".length()));
		        }
		        else if(line.indexOf("[deg] ") != -1) {        	
		        	// Extract the degree from the string
		        	degree = Integer.parseInt(line.substring(line.indexOf("[deg] {") + "[deg] {".length(), line.indexOf("}")));
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
		        else if(line.indexOf("[a] ") != -1) {
		        	polyA = line.substring(line.indexOf("[a] ") + "[a] ".length());
		        }
		        else if(line.indexOf("[b] ") != -1) {
		        	polyB = line.substring(line.indexOf("[b] ") + "[b] ".length());
		        }
		        else if(line.indexOf("[mod-poly] ") != -1) {
		        	modPoly = line.substring(line.indexOf("[mod-poly] ") + "[mod-poly] ".length());
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
			System.out.println("De degree is: " + degree);
			
			//System.out.println("{1, 0, 0}");
			//outputArray(BigPolyCalculator.reverseInt(new int[] {1, 0, 0}));
			//System.out.println("{1, 0, 0, 1}");
			//outputArray(BigPolyCalculator.reverseInt(new int[] {1, 0, 0, 1}));
			//System.out.println("{1, 1, 0}");
			//outputArray(BigPolyCalculator.reverseInt(new int[] {1, 1, 0}));
			//System.out.println("{1, 2, 3}");
			//outputArray(BigPolyCalculator.reverseInt(new int[] {1, 2, 3}));
			//System.out.println("{1, 2, 3, 4, 5, 6, 7, 8}");
			//outputArray(BigPolyCalculator.reverseInt(new int[] {1, 2, 3, 4, 5, 6, 7, 8}));
			
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
				case "display-poly":
					int[] r = BigPolyCalculator.moduloPoly(stringToArray(poly1), modulus);
					printPoly(r);
					break;
				case "add-poly":
					int[] o = BigPolyCalculator.add(stringToArray(poly1), stringToArray(poly2), modulus);
					printPoly(o);
					break;
				case "multiply-poly":
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
					
					boolean ottt = BigPolyCalculator.equalsPolyMod(stringToArray(poly1), stringToArray(poly2), stringToArray(poly3), modulus);
					System.out.println("De polynimals zijn equal mod poly3: " + ottt);
					
					break;
				case "euclid-poly":
					
					int[][] otttt = BigPolyCalculator.polyEuclid(stringToArray(poly1), stringToArray(poly2), modulus);
					System.out.println("De GCD van ");
				//	outputArray(stringToArray(poly1));
					BigPolyCalculator.outputPolyStyle(stringToArray(poly1));
					System.out.println(" en ");
					BigPolyCalculator.outputPolyStyle(stringToArray(poly2));
					System.out.println(" is: ");
					outputArray(otttt[0]);
					System.out.println("En de x is: ");
					outputArray(otttt[1]);
					System.out.println("En de y is: ");
					outputArray(otttt[2]);
					
					break;
				case "irreducible":
					
					boolean ottttt = BigPolyCalculator.testIrreducible(stringToArray(poly1), modulus);
					System.out.println("De polynimals zijn equal mod poly3: " + ottttt);
					
					break;
				case "find-irred":
					
					int[] otttttt = BigPolyCalculator.findRandomIrreduciblePoly(degree, modulus);
					System.out.println("De random gevonden irreducible polynomial is: ");
					outputArray(otttttt);
					boolean otBool = BigPolyCalculator.testIrreducible(otttttt, modulus);
					System.out.println("En even een snelle irreducibility check!\n" + otBool);
					
					break;
				case "add-field":
					int[] ok = BigPolyCalculator.fAdd(stringToArray(polyA), stringToArray(polyB), stringToArray(modPoly), modulus);
					printPoly(ok);
					break;
				case "subtract-field":
					int[] okk =  BigPolyCalculator.fSub(stringToArray(polyA), stringToArray(polyB), stringToArray(modPoly), modulus);
					printPoly(okk);
					break;
				case "multiply-field":
					int[] okkk =  BigPolyCalculator.fMultiply(stringToArray(polyA), stringToArray(polyB), stringToArray(modPoly), modulus);
					printPoly(okkk);
					break;
				case "division-field":
					int[] op = BigPolyCalculator.fDivision(stringToArray(polyA), stringToArray(polyB), stringToArray(modPoly), modulus);
					printPoly(op);
				case "display-field":
					int[] opp = BigPolyCalculator.displayField(stringToArray(polyA), stringToArray(modPoly), modulus);
					printPoly(opp);
					break;
				case "inverse-field":
					int[] oppp = BigPolyCalculator.inverseField(stringToArray(polyA), stringToArray(modPoly), modulus);
					printPoly(oppp);
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
		
		int index = 0;
		
		String[] temps = n1.split(",");
		int[] output = new int[temps.length];
		
		for(int i = 0; i < temps.length; i++) {
	//		System.out.println("Element: " + temps[i]);
			temps[i].replaceAll(" ", "");
			output[i] = Integer.parseInt(temps[i]);
		}
		
		
		
		return output;
	}
	
	public static void outputArray(int[] array) {
		
		System.out.print("{" + array[0]);
		for(int i = 1; i < array.length; i++) {
			System.out.print(", " + array[i]);
		}
		System.out.println("}");
		
	}
	
	public static void printPoly(int[] poly) {
		String result = "";
		int degree = poly.length - 1;
		for(int i = 0; i < poly.length; i++) {
			if(poly[i] != 0) {
				if(i != 0) {
					if(poly[i] < 0 && result != "") {
						result += "-";
					}
					else if(result != ""){
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
