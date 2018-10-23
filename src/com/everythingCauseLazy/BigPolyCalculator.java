package com.everythingCauseLazy;

import java.util.Arrays;

public class BigPolyCalculator {

	public static final String ADD_COUNT = null;
	public static final String MUL_COUNT = null;
	
	
	public static int[] add(int[] p1, int[] p2, int mod) {
		
		int counter = 0;
		int index = 0;
	
		System.out.println("De lengtes zijn " + p1.length + " en " + p2.length) ;
		if(p2.length > p1.length) {
			int[] p3 = p1;
			p1 = p2;
			p2 = p3;
			
			
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
			System.out.println("De output is " + output[i] + " bij " + tDigit + " + " + p1[i]);
		}
		
		return moduloPoly(output, mod);
	}
	
	
	public static int[] multiply(int[] p1, int[] p2, int mod) {
		int indent = 0;
		int[] result = new int[p1.length + p2.length - 1];
		
		p1 = reverseInt(p1);
		p2 = reverseInt(p2);
		System.out.println("p1 = " + Arrays.toString(p1));
		System.out.println("p2 = " + Arrays.toString(p2));
		for(int i = 0; i < p2.length; i++) {
			int[] currentAdd = new int[p1.length + indent];
			for(int c = 0; c < indent; c++) {
				currentAdd[c] = 0;
			}
			for(int p = 0; p < p1.length; p++) {
				currentAdd[p + indent] = p1[p] * p2[i];
			}
			currentAdd = reverseInt(currentAdd);
			System.out.println("currentAdd= " + Arrays.toString(currentAdd) + " and result is currently: " + Arrays.toString(result));
			result = add(result, currentAdd, mod);
			System.out.println("result is currently: " + Arrays.toString(result));
			indent++;
		}
		
		
		return result;
	}
	
	public static int[] reverseInt(int[] array) {
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
	
		
		if(p2.length > p1.length) {
			
			System.out.println("Ervoor zijn de lengtes " + p2.length + " en " + p1.length);
			
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
		
		for(int i = 0; i < output[i]; i++) {
			if(output[i] < 0) {
				output[i] = mod + output[i];
			}
		}
		
		return moduloPoly(output, mod);
	}
	public static int[][] longPolyDivision(int[] poly1, int[] poly2, int mod) {
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
				break;
			}
			
			System.out.println("De degree van pol1 is: " + lcDegree2 + " en van pol2 is: " + lcDegree + " en pol lengte 1 is " + poly1.length + "en pol lengte 2 is: " + poly2.length + " en lc1 is " + lc1 + " en lc2 is " + lc2);
			
			int oDegree = output.length - (lcDegree2 - lcDegree) - 1;
			
			output[oDegree] += Math.floorDiv(lc1, lc2);
			
			System.out.println("Long division de vermenigvuldiging is " + Math.floorDiv(lc1, lc2) + "x^" + (lcDegree2 - lcDegree));
			
			for(int i = 0; i < poly2.length; i++) {
				
				if(poly2[i] == 0) {
					continue;
				}
				
				int outputDegree = poly1.length - (lcDegree2 - lcDegree + (poly2.length - i));
				poly1[outputDegree] -= Math.floorDiv(lc1, lc2) * poly2[i];
				
				System.out.println("De output index is: " + outputDegree + " en het getal is min " + Math.floorDiv(lc1, lc2) * poly2[i]);
			}
			
			for(int i = 0; i < poly1.length; i++) {
				if(poly1[i] != 0) {
					degree1 = poly1.length - i - 1;
					break;
				}
			}
		
			degree2 = lcDegree;
			
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
	public static boolean equalsPolyMod(int[] poly1, int[] poly2, int[] polyMod) {
		int[] remainderOne = longPolyDivision(poly1, polyMod, -1)[1];
		int[] remainderTwo = longPolyDivision(poly2, polyMod, -1)[1];
		
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
	public static int[] moduloPoly(int[] poly, int mod) {
		
		int[] output = new int[poly.length];
		
		for(int i = 0; i < poly.length; i++) {
			output[i] = poly[i] % mod;
		}
		
		return output;
	}

}
