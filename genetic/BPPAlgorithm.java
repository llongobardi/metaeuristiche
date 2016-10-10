package genetic;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BPPAlgorithm {
	
private BPPIndividual fittest;
	
	public BPPAlgorithm(BPPIndividual fittest){
		this.fittest = fittest;
	}
	
	public BPPopulation evolvePopulation(BPPopulation population){
		BPPopulation source = this.montecarloSelection(population);
		this.crossover(source);
		//this.mutate(source);				
		fittest = source.getFittest().getFitness() > fittest.getFitness() ? source.getFittest() : fittest;
		return source;
	}
	
	public BPPIndividual getFittest(){
		return fittest;
	}
	
	private void mutate(BPPopulation pop){
		
		for(int j=0;j<pop.getPopSize();j++){
			
			/*for(int i = 0; i < pop.getIndividual(j).getGeneSize(); i++){
				if(Math.random() <= 0.015){
					pop.getIndividual(j).setGene(i, 1 - pop.getIndividual(j).getGene(i));
				}
			}*/
		}
	}
	
	private void crossover(BPPopulation pop){
		Random randCutpoint = new Random();
		int cutpoint1_1, cutpoint1_2, cutpoint2_1,cutpoint2_2;
		
		
		for(int i = 0; i<pop.getPopSize(); i+=2){
			cutpoint1_1 = randCutpoint.nextInt(pop.getIndividual(i).getNumGenes());
			cutpoint1_2 = randCutpoint.nextInt(pop.getIndividual(i).getNumGenes());
			cutpoint2_1 = randCutpoint.nextInt(pop.getIndividual(i+1).getNumGenes());
			cutpoint2_2 = randCutpoint.nextInt(pop.getIndividual(i+1).getNumGenes());
			
			//Rearrange
			int temp = cutpoint1_1;
			if (cutpoint1_1 > cutpoint1_2){
				cutpoint1_1 = cutpoint1_2;
				cutpoint1_2 = temp;
			}
			
			temp = cutpoint2_1;
			if (cutpoint2_1 > cutpoint2_2){
				cutpoint2_1 = cutpoint2_2;
				cutpoint2_2 = temp;
			}
			
			List<Bin> binCross1 = new LinkedList<>(); //liste di bin da spostare
			List<Bin> binCross2 = new LinkedList<>();
			for (int j = cutpoint1_1; j<cutpoint1_2;j++){
				binCross1.add(pop.getIndividual(i).copyOfGene(j));
			}
			
			for (int j = cutpoint2_1; j<cutpoint2_2;j++){
				binCross2.add(pop.getIndividual(i+1).copyOfGene(j));
			}
			
			//Copying genes(bins)
			pop.getIndividual(i+1).addGene(cutpoint2_1,binCross1);
			pop.getIndividual(i).addGene(cutpoint1_2, binCross2);
			
			//per tenere traccia degli oggetti presenti nei bin che ho spostato
			List<Integer> objInBins1 = new LinkedList<>(); 
			List<Integer> objInBins2 = new LinkedList<>();
			
			//per salvare gli oggetti
			HashMap<Integer,Integer> save1 = new HashMap<>();
			HashMap<Integer,Integer> save2 = new HashMap<>();
			
			
			//Prendo la lista degli oggetti
			for (Bin b : binCross1){
				for (int k : b.getObjects().keySet()){
					objInBins1.add(k);
				}
			}
			
			for (Bin b : pop.getIndividual(i).getAllBins()){
				int l = 0;
				boolean delete = false;
				for (int k : b.getObjects().keySet()){
					if (!binCross1.contains(b)){ //se il bin che sto controllando non fa parte dei nuovi
						if (k == objInBins1.get(l)){//se il bin che sto valutando è uguale a un oggetto in quelli nuovi
							delete = true;
						} else if (k!= objInBins1.get(l)){//se k è diverso da uno degli oggetti dei bin nuovi, lo salvo
							save1.put(k, b.getObjects().get(k));
						}
					}
					l++;
				}
				if (delete){
					pop.getIndividual(i).deleteGene(pop.getIndividual(i).getAllBins().indexOf(b));
				}
			}
			
			
			HashMap<Integer, Integer> dff1 = new HashMap<>();
			//FUNZIONE PER RIMETTERE A POSTO 
			for (int k : save1.keySet()){ //la richiamo per ogni oggetto che ho salvato
				dff1.putAll(this.replacement(pop.getIndividual(i), k,save1.get(k)));
			}
			if (!dff1.isEmpty()){
				for (int k: dff1.keySet()){
					this.secondStep(k, dff1.get(k),pop.getIndividual(i));
				}		 
			}
			
			
			
			
			//Operazioni su seconda lista
			for (Bin b : binCross2){
				for (int k : b.getObjects().keySet()){
					objInBins2.add(k);
				}
			}
			
			for (Bin b : pop.getIndividual(i+1).getAllBins()){
				int l = 0;
				boolean delete = false;
				for (int k : b.getObjects().keySet()){
					if (!binCross1.contains(b)){ //se il bin che sto controllando non fa parte dei nuovi
						if (k == objInBins1.get(l)){//se il bin che sto valutando è uguale a un oggetto in quelli nuovi
							delete = true;
						} else if (k!= objInBins1.get(l)){//se k è diverso da uno degli oggetti dei bin nuovi, lo salvo
							save1.put(k, b.getObjects().get(k));
						}
					}
					l++;
				}
				if (delete){
					pop.getIndividual(i+1).deleteGene(pop.getIndividual(i+1).getAllBins().indexOf(b));
				}
			}
			
			HashMap<Integer, Integer> dff2 = new HashMap<>();
			//FUNZIONE PER RIMETTERE A POSTO 
			for (int k : save1.keySet()){ //la richiamo per ogni oggetto che ho salvato
				dff2.putAll(this.replacement(pop.getIndividual(i+1), k,save1.get(k)));
			}
			if (!dff2.isEmpty()){
				for (int k: dff1.keySet()){
					this.secondStep(k, dff2.get(k),pop.getIndividual(i+1));
				}		 
			}
			
		}		
	}
	
	
	/**
	 * Implements the first step of crossover and mutation (replacement)
	 * */
	private HashMap<Integer,Integer> replacement(BPPIndividual sol, int num, int volume){
			
		Random r = new Random();
		int cumulative = 0;
		int binSize = 0;
		HashMap<Integer,Integer> saveItems = new HashMap<>();
				
		Bin b = sol.getAllBins().get(r.nextInt(sol.getAllBins().size())); //scelta bin casuale
		
		for (int k:b.getObjects().values()){
			binSize += k;
		}
		
		for (int k : b.getObjects().keySet()){ //per ogni oggetto che si trova nel bin
			cumulative+= b.getObjects().get(k);
			if(volume>cumulative && binSize-cumulative+volume <= InitializeBPP.model.getBinCapacity()){
				b.getObjects().remove(k, b.getObjects().get(k));
				b.addObject(num, volume);
				break;
			}
			saveItems.put(k, b.getObjects().get(k));
		}
		return saveItems;	
	}
	
	/**
	 * Implements the second step of crossover (reassignment)
	 * */
	private void secondStep(int num, int volume, BPPIndividual ind){
		
		for (Bin b: ind.getAllBins()){
			if (volume<=b.getLeftCapacity()){
				b.addObject(num, volume);
				return;
			}
		}
		Bin newBin = new Bin();
		newBin.addObject(num, volume);
		ind.addGene(newBin);
	}
	
	
	
	private BPPopulation montecarloSelection(BPPopulation source){
		
		BPPopulation selection = new BPPopulation(false);
		
		double[] cumulativeOdd = new double[source.getPopSize()];
		double temp = 0;
		Random randSelector = new Random();
		
		for(int i = 0; i < source.getPopSize(); i++){
			cumulativeOdd[i] = source.getIndividual(i).getFitness() + temp;
			temp += cumulativeOdd[i];
		}
		
		for(int i = 0; i< selection.getPopSize(); i++){
			double newRandom = randSelector.nextDouble();
			
			for(int j = 0; j< source.getPopSize(); j++){
				if(newRandom <= cumulativeOdd[j]/cumulativeOdd[source.getPopSize()-1]){				
					selection.insertIndividual(i, source.getIndividual(j).copyOf());
					break;
					
				}
			}
		}
		
		
		return selection;
		
	}


}
