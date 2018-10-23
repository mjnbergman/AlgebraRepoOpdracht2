package com.everythingCauseLazy;

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
	public static int[] moduloPoly(int[] poly, int mod) {
		
		int[] output = new int[poly.length];
		
		for(int i = 0; i < poly.length; i++) {
			output[i] = poly[i] % mod;
		}
		
		return output;
	}

}
