package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bppgenetic.Element;
import bppgenetic.Generation;

public class MainGenetic {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		int elements[] = new int[120];

		try (BufferedReader rd = new BufferedReader(new FileReader("src/BPP_"+args[0]+".txt"))){
			
			String firstLine = rd.readLine();
			String[] init =firstLine.split(" ");
			int binCapacity = Integer.parseInt(init[0]);
			int nObjects = Integer.parseInt(init[1]);
			for (int i = 0; i< nObjects; i++){
				elements[i] = Integer.parseInt(rd.readLine());
			}
		}
		
		
		Generation gen = Generation.initFirstGeneration(Element.intArrToList(elements), 100);
		gen.countFitness();
	
		
		for(int i = 0; i < 1000; i++){
			gen.selection();
			gen.generateNextGeneration();
			gen.addMutations();
			gen.countFitness();
			System.out.println("Best fintess in generation ~"+
			gen.getGenerationNumber()+": "+gen.getBestFitValue() + " with " + gen.bestBinsCount + " used with " 
			+gen.numObjects()+" elements");
		}

	}
}

