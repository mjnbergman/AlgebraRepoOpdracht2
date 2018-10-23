package com.everythingCauseLazy;

import java.util.Arrays;

public class BigPolyCalculator {

	public static final String ADD_COUNT = null;
	public static final String MUL_COUNT = null;
	
	
	public static int[] add(int[] p1, int[] p2, int mod) {
		
		int counter = 0;
		int index = 0;
	
		
		if(p2.length > p1.length) {
			int[] p3 = p1;
			p1 = p2;
			p2 = p3;
			
			
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
			
			output[i] = tDigit + p1[i];
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
	
	public static int[] moduloPoly(int[] poly, int mod) {
		
		int[] output = new int[poly.length];
		
		for(int i = 0; i < poly.length; i++) {
			output[i] = poly[i] % mod;
		}
		
		return output;
	}

}
