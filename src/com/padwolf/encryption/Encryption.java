package com.padwolf.encryption;

import java.util.Random;

public class Encryption {
	public final static char[] MASTER = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890`-=~!@#$%^&*()_+[]\\{}|;':\",./<>? ".toCharArray();
	public char[] unencoded;
	public char[] encoded;
	
	public Encryption(long seed){	
		unencoded = MASTER;
		encoded = generateEncryptionArray(seed);
		System.out.println(encoded);
	}
	
	public static String encode(String text, char[] encoded){
		String temp = "";
		int index = 0;
		for (int i = 0; i < text.length(); i++){
			boolean contains = false;
			for (char c : MASTER) {
			    if (c == text.charAt(i)) {
			        contains = true;
			        break;
			    }
			}
			//System.out.println(contains);
			if (!contains){
				if (text.charAt(i) == '\n') throw new IllegalArgumentException("Character 'newline' is not a recogniable character");
				if (text.charAt(i) == '\t') throw new IllegalArgumentException("Character 'indent' is not a recogniable character");
				throw new IllegalArgumentException("Character '" + text.charAt(i) + "' is not a recognizable character");
			} else{
				index = findChar(MASTER, text.charAt(i));
//				System.out.println(text.charAt(i) == ' ');
//				System.out.println(MASTER[index] == ' ');
//				System.out.println(MASTER[index]);
				temp += encoded[index];
			}
		}
		
		return temp;
	}
	
	public static String decode(String text, char[] encoded){
		String temp = "";
		int index = 0;
		for (int i = 0; i < text.length(); i++){
			boolean contains = false;
			for (char c : encoded) {
			    if (c == text.charAt(i)) {
			        contains = true;
			        break;
			    }
			}
			//System.out.println(contains);
			if (!contains){
				throw new IllegalArgumentException("Character '" + text.charAt(i) + "' is not a recognizable character");
			} else{
				index = findChar(encoded, text.charAt(i));
//				System.out.println(text.charAt(i) == encoded[index]);
//				System.out.println(text.charAt(i));
//				System.out.println(encoded[index]);
				temp += MASTER[index];
			}
		}
		
		return temp;
	}
	
	
	public static char[] generateEncryptionArray(long seed){
		char[] temp = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890`-=~!@#$%^&*()_+[]\\{}|;':\",./<>?".toCharArray();
		char[] op = new char[temp.length];
		int val = 0;
		
		//System.out.println(seed);
//		double random = seed * 100000;
		Random rand = new Random(seed);
		
		System.out.println(temp.length);
		
		for (int i = 0; i < op.length; i++)
		while (temp.length > 0){
			val = Math.abs(rand.nextInt()%temp.length);
//			System.out.println(temp[val]);
			op[i] = temp[val];
			temp = removeChar(temp, val);
//			System.out.println(findChar(temp, ' '));
		}
		
		System.out.println(op.length);
		System.out.println(Main.hasDuplicate(op));
		
		return op;
	}
	
	static int findChar(char[] ary, char value){
		for (int i = 0; i < ary.length; i++){
			if (ary[i] == value){
				return i;
			}
		}
		return -1;
	}
	
	private static char[] removeChar(char[] array, int index){
		char[] temp = new char[array.length-1];
		int nIndex = 0;
		for (int i = 0; i < temp.length; i++){
			if (i != index){
				temp[nIndex] = array[i];
				System.out.println(nIndex);
				nIndex++;
			}
		}
		return temp;
	}
}