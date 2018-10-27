package com.everythingCauseLazy;

import java.util.Arrays;

public class BigPolyCalculator {

	public static final String ADD_COUNT = null;
	public static final String MUL_COUNT = null;
	
	
	public static int[] add(int[] oP1, int[] oP2, int mod) {
		
		int counter = 0;
		int index = 0;
		int[] p1;
		int[] p2;
	
		//System.out.println("De lengtes zijn " + p1.length + " en " + p2.length) ;
		if(oP2.length > oP1.length) {
			p1 = oP2.clone();
			p2 = oP1.clone();
		}
		else {
			p1 = oP1.clone();
			p2 = oP2.clone();
		}
		
		counter = Math.abs(p1.length - p2.length);
		int output[] = new int[p1.length];
		
		for(int i = 0; i < p1.length; i++) {
			
			int tDigit = 0;

			if(counter > 0) {
				tDigit = 0;
				counter--;
			}else {
				tDigit = p2[index];
				index++;
			}
			
			output[i] = tDigit + p1[i];
			//System.out.println("De output is " + output[i] + " bij " + tDigit + " + " + p1[i]);
		}
		
	//	System.out.println("Dus de subresult is: ");
	//	InputOutputParser.outputArray(output);
		
		return moduloPoly(output, mod);
	}
	
	
	public static int[] multiply(int[] pa1, int[] pa2, int mod) {
		int indent = 0;
		int[] result = new int[pa1.length + pa2.length - 1];
		
		int[] oP1 = pa1;
		int[] oP2 = pa2;
		
		//reverse ints to indent (move by powers of X)
		int[] p1 = reverseInt(oP1);
		int[] p2 = reverseInt(oP2);
		
		

		
	//	System.out.println("p1 = " + Arrays.toString(p1));
	//	System.out.println("p2 = " + Arrays.toString(p2));
		for(int i = 0; i < p2.length; i++) {
			int[] currentAdd = new int[p1.length + indent]; //indent by powers of X
			for(int c = 0; c < indent; c++) {
				currentAdd[c] = 0;
			}
			for(int p = 0; p < p1.length; p++) {
				currentAdd[p + indent] = p1[p] * p2[i];
			}
	//		System.out.println("Pre reversen is het");
	//		InputOutputParser.outputArray(currentAdd);
	//		System.out.println("Met inputs");
	//		InputOutputParser.outputArray(p1);
	//		InputOutputParser.outputArray(p2);
			currentAdd = reverseInt(currentAdd);
			//System.out.println("currentAdd= " + Arrays.toString(currentAdd) + " and result is currently: " + Arrays.toString(result));
			result = add(result, currentAdd, mod);
			//System.out.println("result is currently: " + Arrays.toString(result));
			indent++;
		}
		
		p1 = reverseInt(oP1);
		p2 = reverseInt(oP2);
		
		
	/*	System.out.println("De mult van ");
		InputOutputParser.outputArray(oP1);
		System.out.println(" en ");
		InputOutputParser.outputArray(oP2);
		System.out.println(" is ");
		InputOutputParser.outputArray(result);
		*/
		return moduloPoly(result,mod); //return modulated result
	}
	
	public static int[] reverseInt(int[] array) {
		//reverse array of ints
		for(int i=0; i<array.length/2; i++){
			  int temp = array[i];
			  array[i] = array[array.length -i -1];
			  array[array.length -i -1] = temp;
		}
		return array;
	}
	
	
	public static int[] sub(int[] p1, int[] p2, int mod) {
		
		int counter = 0;
		int index = 0;
	
		//A + B = -B + A
		if(p2.length > p1.length) {
			
	//		System.out.println("Ervoor zijn de lengtes " + p2.length + " en " + p1.length);
			
			for(int i = 0; i < p2.length; i++) {
				p2[i] *= -1;
			}
			
			return add(p2, p1, mod);
		}
		
		counter = Math.abs(p1.length - p2.length);
		int output[] = new int[p1.length];
		
		for(int i = 0; i < p1.length; i++) {
			
			int tDigit = 0;

			if(counter != 0) {
				tDigit = 0;
				counter--;
			}else {
				tDigit = p2[index];
				index++;
			}
			
			output[i] = p1[i] - tDigit;
		}
		
		for(int i = 0; i < output.length; i++) {
			if(output[i] < 0) {
				output[i] = mod + output[i];
			}
		}
		
//		System.out.println("Dus de subresult is: ");
//		InputOutputParser.outputArray(output);
		
		return moduloPoly(output, mod);
	}
	public static int[][] longPolyDivision(int[] pody1, int[] pody2, int mod) {
		
		int[] poly1 = stripLeadingZeroes(pody1.clone());
		int[] poly2 = stripLeadingZeroes(pody2.clone());
		
		int degree1 = poly1.length - 1;
		int degree2 = poly2.length - 1;
		
		int[] output = new int[poly1.length];
		int[] remainder = poly1;
		int[][] returnable = new int[2][poly1.length];
		
		int q = 0;
		
		int[] r = poly1;
	
		
		int polyIndex = 0;
		
		while(degree1 >= degree2) {
			
			int lcDegree = 0;
			int lcDegree2 = 0;
			
			int lc1 = 0;
			int lc2 = 0;
			
			for(int i = 0; i < poly2.length; i++) {
				if(poly2[i] != 0) {
					lcDegree = poly2.length - i - 1;
					lc2 = poly2[i];
					break;
				}
			}
			for(int i = 0; i < poly1.length; i++) {
				if(poly1[i] != 0) {
					lcDegree2 = poly1.length - i - 1;
					lc1 = poly1[i];
					break;
				}
			}
			
			if(lc1 < lc2) {
				lc1 += mod;
			}
			
	//		System.out.println("De degree van pol1 is: " + lcDegree2 + " en van pol2 is: " + lcDegree + " en pol lengte 1 is " + poly1.length + "en pol lengte 2 is: " + poly2.length + " en lc1 is " + lc1 + " en lc2 is " + lc2);
			
			int oDegree = output.length - (lcDegree2 - lcDegree) - 1;
			
	//		System.out.println("De place to be is : " + oDegree + " want het verschil in degrees is " + (lcDegree2 - lcDegree));
			
			output[oDegree] += Math.floorDiv(lc1, lc2);
			
	//		System.out.println("Long division de vermenigvuldiging is " + Math.floorDiv(lc1, lc2) + "x^" + (lcDegree2 - lcDegree));
			
			for(int i = 0; i < poly2.length; i++) {
				
				if(poly2[i] == 0) {
					continue;
				}
				
				int outputDegree = poly1.length - (lcDegree2 - lcDegree + (poly2.length - i));
				poly1[outputDegree] -= Math.floorDiv(lc1, lc2) * poly2[i];
				
				if(poly1[outputDegree] < 0 || poly1[outputDegree] > mod) {
					poly1 = moduloPoly(poly1, mod);
				}
				
	//			System.out.println("De output index is: " + outputDegree + " en het getal is min " + Math.floorDiv(lc1, lc2) * poly2[i] + " dus de nieuwe coefficient is: " + poly1[outputDegree]);
			}
			
			for(int i = 0; i < poly1.length; i++) {
				if(poly1[i] != 0) {
					degree1 = poly1.length - i - 1;
					break;
				}
			}
		
			degree2 = lcDegree;
	//		System.out.println("De nieuwe degrees zijn: " + degree1 + " en " + degree2);
			
			if(checkPolyZero(poly1)) {
				break;
			}
			
		}
		
		if(mod == -1) {
			returnable[0] = output;
			returnable[1] = poly1;
			
			return returnable;
		}
		
		returnable[0] = moduloPoly(output, mod);
		returnable[1] = moduloPoly(poly1, mod);
		
		return returnable;
	}
	public static boolean equalsPolyMod(int[] poly1, int[] poly2, int[] polyMod, int modulus) {
		
		int[] moddedPoly1 = moduloPoly(poly1, modulus);
		int[] moddedPoly2 = moduloPoly(poly2, modulus);
		
		int[] remainderOne = longPolyDivision(moddedPoly1, polyMod, modulus)[1];
		int[] remainderTwo = longPolyDivision(moddedPoly2, polyMod, modulus)[1];
		
		System.out.println("De remainder van poly1 is: ");
		InputOutputParser.outputArray(remainderOne);
		
		System.out.println("De remainder van poly2 is: ");
		InputOutputParser.outputArray(remainderTwo);
		
		if(remainderTwo.length > remainderOne.length) {
			int[] remainderTemp = remainderOne;
			remainderOne = remainderTwo;
			remainderTwo = remainderTemp;
		}
		
		int leadingZeroes = remainderOne.length - remainderTwo.length;
		int index = 0;
		
		for(int i = 0; i < remainderOne.length; i++)
		{
			
			int tDigit = 0;
			
			if(leadingZeroes > 0) {
				tDigit = 0;
				leadingZeroes--;
			}else {
				tDigit = remainderTwo[index];
				index++;
			}
			
			if(tDigit != remainderOne[i]) {
				return false;
			}
		}
		return true;
	}
	public static int[][] polyEuclid(int[] iPoly1, int[] iPoly2, int mod) {
		
		int[] poly1 = moduloPoly(iPoly1.clone(), mod);
		int[] poly2 = moduloPoly(iPoly2.clone(), mod);
		
		int[][] polySet = sortPolySize(poly1, poly2);
		int[] biggestPoly = stripLeadingZeroes(polySet[0]);
		int[] smallestPoly = stripLeadingZeroes(polySet[1]);
		
		int[][] polyResult; //= longPolyDivision(biggestPoly.clone(), smallestPoly, mod);
		
	//	System.out.println("Made it");
		
		int[] polyQuotient; //= stripLeadingZeroes(polyResult[0]);
		int[] polyRemainder = biggestPoly; //= stripLeadingZeroes(polyResult[1]);
		
		int[] x = {1};
		int[] y = {0};
		
		int[] xAux;
		int[] yAux;
		
		int[] u = {0};
		int[] v = {1};
		
		int[] a = poly1;
		int[] b = poly2;
		
	/*	xAux = x;
		yAux = y;
		
		x = u;
		y = v;
		
		u = sub(xAux, multiply(polyQuotient, u, mod), mod);
		v = sub(yAux, multiply(polyQuotient, v, mod), mod);
		
		System.out.println("u: ");
		InputOutputParser.outputArray(u);
		System.out.println("v: ");
		InputOutputParser.outputArray(v);
		System.out.println("x: ");
		InputOutputParser.outputArray(x);
		System.out.println("y: ");
		InputOutputParser.outputArray(y);
		System.out.println("Quotient: ");
		InputOutputParser.outputArray(polyResult[0]);
		System.out.println("Remainder: ");
		InputOutputParser.outputArray(polyResult[1]);
		System.out.println("Van: ");
		InputOutputParser.outputArray(biggestPoly); // Is passed by reference fuck
		System.out.println("Van: ");
		InputOutputParser.outputArray(smallestPoly);
		
		*/
		
		while(!checkPolyZero(b)) {
			
	//		System.out.println("Before biggest poly: ");
	//		InputOutputParser.outputArray(smallestPoly);
			
			//polySet = sortPolySize(moduloPoly(a, mod), moduloPoly(b, mod));
			
			biggestPoly = a;
			smallestPoly = b;
			
		//	biggestPoly = stripLeadingZeroes(polySet[0]);
		//	smallestPoly = stripLeadingZeroes(polySet[1]);
			
			polyResult = longPolyDivision(biggestPoly.clone(), smallestPoly, mod);
			
			polyQuotient = stripLeadingZeroes(polyResult[0]);
			polyRemainder = stripLeadingZeroes(polyResult[1]);
			
			a = b;
			b = polyRemainder;
			
	//		System.out.println("After biggest poly: ");
	//		InputOutputParser.outputArray(smallestPoly);
			
			xAux = x.clone();
			yAux = y.clone();
			
			x = u.clone();
			y = v.clone();
			
			u = sub(xAux, multiply(polyQuotient, u, mod), mod);			
			v = sub(yAux, multiply(polyQuotient, v, mod), mod);
			
	/*		System.out.println("u: ");
			InputOutputParser.outputArray(u);
			System.out.println("v: ");
			InputOutputParser.outputArray(v);
			System.out.println("x: ");
			InputOutputParser.outputArray(x);
			System.out.println("y: ");
			InputOutputParser.outputArray(y);
			System.out.println("Quotient: ");
			InputOutputParser.outputArray(polyResult[0]);
			System.out.println("Remainder: ");
			InputOutputParser.outputArray(polyResult[1]);
			System.out.println("Van: ");
			InputOutputParser.outputArray(biggestPoly);
			System.out.println("Van: ");
			InputOutputParser.outputArray(smallestPoly); */
			
		}
		
	//	System.out.println("x: ");
	//	InputOutputParser.outputArray(x);
		int gcdDegree = 0, gcd = 0;
		
		for(int i = 0; i < smallestPoly.length; i++) {
			if(smallestPoly[i] != 0) {
				gcdDegree = smallestPoly.length - i - 1;
				gcd = smallestPoly[i];
				System.out.println("Gevonden de gcd is: " + gcd + " met degree: " + gcdDegree);
				break;
			}
		}
		
		if(gcd != 1) {
			int[][] polyResultPappie = longPolyDivision(smallestPoly, new int[] {gcd}, mod);
			smallestPoly = polyResultPappie[0];
			polyResultPappie = longPolyDivision(x, new int[] {gcd}, mod);
			x = polyResultPappie[0];
			polyResultPappie = longPolyDivision(y, new int[] {gcd}, mod);
			y = polyResultPappie[0];
		}
		
		return new int[][] {smallestPoly.clone(), x.clone(), y.clone()};
		
	}
	public static int[] moduloPoly(int[] poly, int mod) {
		//Modulate every coefficient of each power in polynomial by mod
		
		int[] output = new int[poly.length];
		
		for(int i = 0; i < poly.length; i++) {
			output[i] = poly[i] % mod;
			if(output[i] < 0) {
				output[i] += mod;
			}
		}
		
		return output;
	}
	public static int[][] sortPolySize(int[] poly1, int[] poly2){
		if(poly1.length > poly2.length) {
			return new int[][] {poly1, poly2};
		}
		else if(poly2.length > poly1.length) {
			return new int[][] {poly2, poly1};
		}
		else {
			for(int i = 0; i < poly1.length; i++) {
				if(poly1[i] > poly2[i]) {
					return new int[][] {poly1, poly2};
				}else if(poly2[i] > poly1[i]) {
					return new int[][] {poly2, poly1};
				}
			}
		}
		
		return new int[][] {poly1, poly2};
	}
	public static boolean checkPolyZero(int[] poly) {
		for(int i = 0; i < poly.length; i++) {
			if(poly[i] != 0) {
				return false;
			}
		}
		return true;
	}
	public static boolean checkPolyOne(int[] poly) {
		
		for(int i = 0; i < poly.length - 1; i++) {
			if(poly[i] != 0) {
				return false;
			}
		}
		
		if(poly[poly.length - 1] != 1) {
			return false;
		}
		
		return true;
	}
	public static void outputPolyStyle(int[] poly) {
		
		int startIndex = 0;
		
		for(int i = 0; i < poly.length; i++) {
			if(poly[i] != 0) {
				startIndex = i;
				int firstDigit = poly[i];
				int digitDegree = poly.length - i - 1;
				
				if(firstDigit != 0) {
					if(firstDigit != 1) {
						System.out.print(firstDigit);
					}
					
					if(digitDegree != 0) {
						if(digitDegree == 1) {
							System.out.print("X");
						}
						else {
							System.out.print("X^" + digitDegree);
						}
					}
				}
				break;
			}
		}
		
		
		
		for(int i = startIndex + 1; i < poly.length; i++) {
			
			int digit = poly[i];
			
			if(digit == 0) {
				continue;
			}
			
			int degree = poly.length - i - 1;
			if(digit < 0) {
				System.out.print(" - ");
				digit *= -1;
			}
			else {
				System.out.print(" + ");
			}
			
			if(digit != 1 || degree == 0) {
				System.out.print(digit);
			}
			
			
			if(degree == 0) {
				continue;
			}
			else if(degree == 1) {
				System.out.print("X");
			}
			else {
				System.out.print("X^" + degree);
			}
		}
	}
	public static int[] stripLeadingZeroes(int[] poly) {
		//Remove leading zeroes from array
		int[] output = new int[0];
		boolean first = true;
		int index = 0;
		
		for(int i = 0; i < poly.length; i++) {
			if(poly[i] != 0 && first) {
				first = false;
				output = new int[poly.length - i];
			}
			if(!first) {
				output[index] = poly[i];
				index++;
			}
		}
		
		if(output.length == 0) {
			output = new int[1];
		}
		
		return output;
		
	}
	public static boolean testIrreducible(int[] poly1, int mod) {
		
		int t = 1;
		int q = mod;
		
		int maxDegree = poly1.length - 1;
		
		int[] euclidOutput = (polyEuclid(poly1, constructTestPoly(q, t), mod)[0]);
		
		System.out.println("De test poly is met " + q + " en " + t + " is: ");
		InputOutputParser.outputArray(constructTestPoly(q, t));
		
		boolean gcdOne = checkPolyOne(euclidOutput);
		
		while(gcdOne) {
			
			System.out.println("De euclid output is: ");
			InputOutputParser.outputArray(euclidOutput);
			
			t++;
			euclidOutput = (polyEuclid(poly1, constructTestPoly(q, t), mod)[0]);
			gcdOne = checkPolyOne(euclidOutput);
			
			if(t == maxDegree) {
				return true;
			}
		}
		return false;
		
	}
	public static int[] constructTestPoly(int q, int t) {
		
		int degree = q;
		
		for(int i = 1; i < t; i++) {
			degree *= q;
		}
		
		int[] output = new int[degree + 1];
		
		output[0] = 1;
		output[output.length - 1] = -1;
		
		return output;
		
	}
	public static int[] findRandomIrreduciblePoly(int degree, int mod) {
		
		while(true) {
			int[] randomPoly = new int[degree + 1];
			
			for(int i = 0; i < degree + 1; i++) {
				int randomCoefficient = (int)(Math.random() * (mod + 1));
				randomPoly[i] = randomCoefficient;
			}
			
			if(testIrreducible(randomPoly, mod)) {
				return randomPoly;
			}
		}
	}
	
	public static int[] fAdd(int[] iP1, int[] iP2, int[] modP, int mod) {
		int[] tAns = add(iP1, iP2, mod); //add polys and get the modulated result back
		
		//modulo the given polynomial
		int[][] resModPoly = longPolyDivision(tAns, modP, mod);
		int[] result = resModPoly[1];
		
		return result;
	}
	public static int[] fSub(int[] iP1, int[] iP2, int[] modP, int mod) {
		//a - b = -b + a
		int[] tP1 = iP1.clone();
		int[] tP2 = new int[iP2.length];
		
		for(int i = 0; i < iP2.length; i++) {
			tP2[i] = -1 * iP2[i];
		}
		int[] tAns = fAdd(tP1, tP2, modP, mod);
		
		for(int i = 0; i < tAns.length; i++) {
			if(tAns[i] < 0) {
				tAns[i] += mod; //Convert negative to positive
			}
		}
		
		return tAns;
	}
	
	public static int[] fMultiply(int[] iP1, int[] iP2, int[] modP, int mod) {
		int[] tAns = multiply(iP1, iP2, mod); //multiply polys and get the modulated result back
		
		//modulo the given polynomial
		int[][] resModPoly = longPolyDivision(tAns, modP, mod);
		int[] result = resModPoly[1];
		
		return result;
	}
	
	public static int[] fDivision(int[] iP1, int[] iP2, int[] modP, int mod) {
		
		//Try to longdivide
		int[][] tAns = longPolyDivision(iP1, iP2, mod); //multiply polys and get the modulated result back
		if(testZero(tAns[0])) {
			//Polynomial could not be divided, so multiply by modP
			int[] tP1 = multiply(iP1, modP, mod);
			tAns = longPolyDivision(tP1, iP2, mod);
		};
		//modulo the given polynomial
		int[][] resModPoly = longPolyDivision(tAns[0], modP, mod);
		int[] result = resModPoly[1];
		
		return result;
	}
	
	public static int[] displayField(int[] iP1, int[] modP, int mod) {
		//modulate by number
		int[] tP1 = moduloPoly(iP1, mod);
		//modulo the given polynomial
		int[][] resModPoly = longPolyDivision(tP1, modP, mod);
		int[] result = resModPoly[1];
		
		return result;
	}
	
	public static int[] inverseField(int[] iP1, int[] modP, int mod) {
		//Applly euclid to poly and modpoly
		int[][] tAns = polyEuclid(iP1.clone(), modP.clone(), mod);
		int[] test = new int[1];
		test[0] = 1;
		InputOutputParser.outputArray(tAns[0]);
		InputOutputParser.outputArray(test);
		
		if(!Arrays.equals(stripLeadingZeroes(tAns[0]), test)) {
			//not invertible
			//System.out.println("Not invertible");
			return new int[0];
		}
		else {
			return fAdd(tAns[1],modP,modP, mod);
		}
	}
	
	public static boolean equalsField(int[] iP1, int[] iP2, int[] modP, int mod) {
		InputOutputParser.printPoly(displayField(iP1, modP, mod));
		InputOutputParser.printPoly(displayField(iP2, modP, mod));
		
		//Check if both have the same Field element
		return Arrays.equals(stripLeadingZeroes(displayField(iP1, modP, mod)), stripLeadingZeroes(displayField(iP2, modP, mod)));
	}
	
	public static boolean isPrimitiveField(int[] iP1, int[] modP, int mod) {
		int[] pNums = {2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97}; //prime numbers under 100
		pNums = Arrays.stream(pNums).filter(x -> (mod - 1) % x == 0).toArray(); //only prime divisors of q - 1
		boolean prime = true;
		
		for(int i = 0; i < pNums.length; i++) {
			//System.out.print("powpolyfield= ");
			//InputOutputParser.printPoly(powPolyField(iP1, modP, mod, (mod-1) / pNums[i]));
			//System.out.print("displayField= ");
			//InputOutputParser.printPoly(displayField(powPolyField(iP1, modP, mod, (mod-1) / pNums[i]), modP, mod));
			
			//Check if the a^((q-1)/p) == 1
			if(Arrays.equals(stripLeadingZeroes(displayField(powPolyField(iP1, modP, mod, (mod-1) / pNums[i]), modP, mod)), new int[] {1})) {
				prime = false;
				break;
			}
		}
		return prime;
	}
	
	public static int[] powPolyField(int[] iP1, int[] modP, int mod, int pow) {
		//Polynomial ^ pow = poly * poly ... * poly
		if(pow == 0) {
			return new int[] {1};
		}
		int[] tAns = multiply(iP1, iP1, mod);
		for(int i = 0; i < pow - 1; i++) {
			tAns = multiply(tAns, iP1, mod);
		}
		
		//modulo the given polynomial
		int[][] resModPoly = longPolyDivision(tAns, modP, mod);
		int[] result = resModPoly[1];
		
		return result;
	}
	
	public static boolean testZero (int[] num) {
		//Test whether a polynomial array consists of only zeroes (zero polynomial)
		boolean zero = true;
		for(int i = 0; i < num.length; i++) {
			if(num[i] != 0) {
				zero = false;
				break;
			}
		}
		return zero;
	}
}
