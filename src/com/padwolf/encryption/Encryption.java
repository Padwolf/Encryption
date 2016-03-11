package com.padwolf.encryption;

import java.util.Random;

public class Encryption {
	public static final char[] MASTER = ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890`-=~!@#$%^&*()_+[]\\{}|;':\",./<>? \t" + System.lineSeparator()).toCharArray();
	public static final String S_MASTER = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890`-=~!@#$%^&*()_+[]\\{}|;':\",./<>? \t" + System.lineSeparator();
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
			
			if (!contains){
				//if (text.charAt(i) == '\n') throw new IllegalArgumentException("Character 'newline' is not a recogniable character");
				//if (text.charAt(i) == '\t') throw new IllegalArgumentException("Character 'indent' is not a recogniable character");
				throw new IllegalArgumentException("Character '" + text.charAt(i) + "' is not a recognizable character");
			} else{
				index = findChar(MASTER, text.charAt(i));
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
			
			if (!contains){
				throw new IllegalArgumentException("Character '" + text.charAt(i) + "' is not a recognizable character");
			} else{
				index = findChar(encoded, text.charAt(i));
				temp += MASTER[index];
			}
		}
		
		return temp;
	}
	
	
	public static char[] generateEncryptionArray(long seed){
		char[] temp = ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890`-=~!@#$%^&*()_+[]\\{}|;':\",./<>? \t" + System.lineSeparator()).toCharArray();
		char[] op = new char[temp.length];
		int val = 0;
		
		Random rand = new Random(seed);
		
		for (int i = 0; i < op.length; i++){
			val = Math.abs(rand.nextInt()%temp.length);
			op[i] = temp[val];
					temp = removeChar(temp, val);
		}
		
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
	
	static char[] removeChar(char[] array, int index){
		char[] temp = new char[array.length-1];
		int nIndex = 0;
		for (int i = 0; i < array.length; i++){
			if (i != index){
				temp[nIndex] = array[i];
				nIndex++;
			}
		}
		return temp;
	}
}