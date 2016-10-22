package bppaco;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class InitializeBPP {
	
	private HashMap <Integer,Integer> objects;
	private int binCapacity;
	private int nBinBestSol;
	
	public static InitializeBPP model = null;

	private InitializeBPP(String s){
		objects = new HashMap<>();
		try (BufferedReader rd = new BufferedReader(new FileReader("src/BPP_"+s+".txt"))){
			System.out.println("Running Instance " + s);
			String firstLine = rd.readLine();
			String[] init =firstLine.split(" ");
			binCapacity = Integer.parseInt(init[0]);
			int nObjects = Integer.parseInt(init[1]);
			for (int i = 0; i< nObjects; i++){
				objects.put(i, Integer.parseInt(rd.readLine()));
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error reading file");
			e.printStackTrace();
		}
	}
	
	public static void Initialize(String s){
		model = new InitializeBPP(s);
	}
	
	public HashMap <Integer,Integer> getObjects(){
		return objects;
	}
	
	public int getBinCapacity(){
		return binCapacity;
	}
	
	public int getBestSolution(){
		return nBinBestSol;
	}
	
	public List<Integer> getItemSet(){
		
		List<Integer> result = new LinkedList<>();
		
		
		for(Integer i : objects.keySet())
			result.add(new Integer(i));
		
		return result;
	}

}


