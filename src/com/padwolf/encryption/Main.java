package com.padwolf.encryption;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
	public static void main(String[] args){
		Scanner inpt = new Scanner(System.in);
		char[] encoder = null;
		String cmd;
		long seed = 0;
		boolean toFile = false;
		File oFile = null, iFile = null;;
		System.out.println("$|Encryption Enigma|$");
		System.out.println("  -----------------");
		System.out.println();
		while (true){
			if (inpt.hasNextLine()){
				cmd = inpt.nextLine();
				switch (cmd){
				case "seed":
					System.out.print("Please enter the seed you would like to use:\n> ");
					if (inpt.hasNextLong()){
						seed = inpt.nextLong();
					}
					encoder = Encryption.generateEncryptionArray(seed);
					break;
				case "file":
					System.out.print("Do you want to encrypt/decrypt a file? (Y/N):\n> ");
					if (inpt.hasNextLine()){
						String answer = inpt.nextLine();
						if (answer.equalsIgnoreCase("y")){
							System.out.print("Input file:\n> ");
							if  (inpt.hasNextLine()){
								iFile = new File(inpt.nextLine());
							}
							if (!iFile.exists()){
								System.err.println("File " + iFile.getName() + " does not exist");
								iFile = null;
								break;
							}
							System.out.print("Output file:\n> ");
							if  (inpt.hasNextLine()){
								String file = inpt.nextLine();
								oFile = new File(file);
								if (file.equals("")){
									oFile = new File(iFile.getAbsolutePath() + ".encr");
								} else {
									oFile = new File(file);
								}
							}
							if (!oFile.exists()){
								try {
									oFile.createNewFile();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							toFile = true;
						} else if (answer.equalsIgnoreCase("n")){
							toFile = false;
						} else {
							System.err.println("Invalid Argument");
						}
					}
					break;
				case "encrypt":
					if (toFile){
						try {
							PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(oFile)));
							
							String[] otpt_s = readAllLines(iFile);
							String otpt = "";
							if (otpt_s.length > 0){
								otpt = otpt_s[0];
								for (int i = 1; i < otpt_s.length; i++){
									otpt += System.lineSeparator() + otpt_s[i];
								}
							}
							
							try {
								otpt = Encryption.encode(otpt, encoder);
							} catch(NullPointerException e){
								long rSeed = (long) (Math.random() * 1000000000);
								otpt = Encryption.encode(otpt, Encryption.generateEncryptionArray(rSeed));
								System.out.println(rSeed);
							}
							
							out.print(otpt);
							out.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						System.out.print("Please enter the text to encrypt:\n> ");
						if (inpt.hasNextLine()){
							String inpt2 = inpt.nextLine();
							try {
								System.out.println(Encryption.encode(inpt2, encoder));
							} catch (NullPointerException e){
								long rSeed = (long) (Math.random() * 1000000000);
								System.out.println(Encryption.encode(inpt2, Encryption.generateEncryptionArray(rSeed)));
								System.out.println("Seed: " + rSeed);
							}
						}
					}
					break;
				case "decrypt":
					if (toFile){
						if (encoder == null){
							System.err.println("ERROR: No seed was provided. Unable to decrypt data");
						} else {
							try {
								PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(oFile)));
								
								String[] otpt_s = readAllLines(iFile);
								String otpt = "";
								if (otpt_s.length > 0){
									otpt = otpt_s[0];
									for (int i = 1; i < otpt_s.length; i++){
										otpt += System.lineSeparator() + otpt_s[i];
									}
								}
								
								otpt = Encryption.decode(otpt, encoder);
								out.print(otpt);
								out.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					} else {
						if (encoder == null){
							System.err.println("ERROR: No seed was provided. Unable to decrypt data");
						} else {
							System.out.print("Please enter the text to decode:\n> ");
							if (inpt.hasNextLine()){
								System.out.println(Encryption.decode(inpt.nextLine(), encoder));
							}
						}
					}
					break;
				case "":
					break;
				case "exit":
					System.out.println("Goodbye");
					inpt.close();
					System.exit(0);
					break;
				default:
					System.out.println();
					System.out.println("Encryption Help:");
					System.out.println("Type command and enter input as prompted.");
					System.out.println("Seed               Set the seed. If the seed is not set, the value is random");
					System.out.println("File               Set whether to use a file, and if so, set the input and output files");
					System.out.println("Encrypt            Encode either the file set previously or input you are prompted to enter");
					System.out.println("Decrypt            Decode either the file set previously or input you are prompted to enter");
					System.out.println("Help               Pulls up this dialogue.");
					System.out.println();
				}
					
			}
		}
		
		/*char[] encoder = Encryption.generateEncryptionArray((int) (Math.random() * 10000000));
		String encrypted = Encryption.encode("Hello World!", encoder);
		System.out.print("Self test: ");
		System.out.println(Encryption.decode(encrypted, encoder));
		System.out.println("If the prevous line did not say \"Hello World!\", there is a bug.\nPlease report it so it can be squashed.");
		
		System.out.println();
		System.out.println();
		
		if (args.length < 3 || args[1].equalsIgnoreCase("--help") || args[1].equalsIgnoreCase("-h")){
			System.out.println("Syntax: Encryption [input type] [encode or decode] [file or input]");
			System.out.println("-f                 Define input type as file");
			System.out.println("-r                 Define input type as raw");
			System.out.println("-e                 Encode");
			System.out.println("-d                 Decode");
			System.out.println("-s seed            Define Seed. If not defined, seed is random");
		} else {
			if (args[0].equalsIgnoreCase("-f")){
				try {
					File iFile = new File(args[2]), oFile;
					BufferedReader in = new BufferedReader(new FileReader(iFile));
					if (args.length <= 3 && args[3].equalsIgnoreCase("-o")){
						oFile = new File(args[4]);
					} else {
						if (args[1].equalsIgnoreCase("-e")){
							oFile = new File(iFile.getName() + ".encr");
						} else if (args[1].equalsIgnoreCase("-d") && iFile.getName().endsWith(".encr")){
							oFile = new File(iFile.getName().substring(-5, 0));
						} else {
							oFile = null;
							System.err.println("Invalid Argument");
						}
					}
					
					PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(oFile)));
					if (!oFile.exists()){
						oFile.createNewFile();
					}
					
					String content = "";
					String line = in.readLine();
					while (line != null){
						content += line + "\n";
						line = in.readLine();
					}
					
					if (args[1].equalsIgnoreCase("-e")){
						String eCtnt = Encryption.encode(content, encoder);
						out.print(eCtnt);
					} else if (args[1].equalsIgnoreCase("-d")){
						String dCtnt = Encryption.decode(content, encoder);
						out.println(dCtnt);
					}
					in.close();
					out.close();
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}*/
	}
	
	/*static void removeLastLine(File file){
		try {
			PrintWriter w = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			BufferedReader r = new BufferedReader(new FileReader(file));
			
			String line, lastLine;
			
			line = r.readLine();
			while (line != null){
				lastLine = line;
				line = r.readLine();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	static String[] readAllLines(File file) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		String tmp = "";
		try {
			int chr = br.read();
			while (chr > -1){
				tmp += (char)chr;
				chr = br.read();
			}
			
			String[] otpt = tmp.split(System.lineSeparator());
			br.close();
			return otpt;
		} catch (IOException e) {
			e.printStackTrace();
		}
		br.close();
		return null;
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
