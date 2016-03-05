package com.padwolf.encryption;

public class Main {
	public static void main(String[] args){
//		for (int i = 0; i < 250; i++) {
		char[] encoder = Encryption.generateEncryptionArray((int) (Math.random() * 10000000));
//		System.out.println(hasDuplicate(Encryption.MASTER));
		//System.out.println(Encryption.findChar(Encryption.MASTER, ' '));
		//System.out.println(Encryption.MASTER[93]);
		//System.out.println(Encryption.encode("Hello World!", encoder));
		//String encpt2 = Encryption.encode("a ", encoder);
		//System.out.println(encpt2);
		//System.out.println(Encryption.decode(encpt2, encoder));
//		String encrypted = Encryption.encode("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890`-=~!@#$%^&*()_+[]\\{}|;':\",./<>? ", encoder);
//		System.out.println(hasDuplicate(encrypted));
//		System.out.println(encrypted);
//		System.out.println(hasDuplicate(Encryption.decode(encrypted, encoder)));
//		System.out.println();
//		System.out.println();
//		System.out.println(encoder[94]);
//		System.out.println(Encryption.decode(encrypted, encoder));
//		}
	}
	
	static boolean hasDuplicate(String in){
		char[] chars = new char[in.length()];
		for (int i = 0; i < in.length(); i++){
			//System.out.println(Encryption.findChar(chars, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890`-=~!@#$%^&*()_+[]\\{}|;':\",./<>? ".charAt(i)) != -1);
			if (Encryption.findChar(chars, in.charAt(i)) != -1){
//				System.out.println(chars);
				return true;
			} else{
				chars[i] = in.charAt(i);
			}
//			System.out.println(chars);
		}
		return false;
	}
	
	static boolean hasDuplicate(char[] in){
		char[] chars = new char[in.length];
		for (int i = 0; i < in.length; i++){
			//System.out.println(Encryption.findChar(chars, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890`-=~!@#$%^&*()_+[]\\{}|;':\",./<>? ".charAt(i)) != -1);
			if (Encryption.findChar(chars, in[i]) != -1){
//				System.out.println(in[i] + ": " + chars);
				return true;
			} else{
				chars[i] = in[i];
			}
//			System.out.println(chars);
		}
		return false;
	}
}
