package genetic;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BPPIndividual {
	
	private int fitness = 0;
	private BPPEstimator estimator;
	private List<Bin> binGenes;
	private HashMap<Integer,Integer> objects;
	
	
	public BPPIndividual(BPPEstimator estimator){
		this.estimator = estimator;
		binGenes = new LinkedList<>();
		//objects = new HashMap<>();
	}
	
	public void setObjects(HashMap<Integer,Integer> obj){
		this.objects = obj;
	}
	
	public void generateIndividual(){
		Random rand = new Random();
		int k = rand.nextInt(objects.size());
		for (int i =k; i!=k-1; i=(i+1)%objects.size()){ //circular for
			this.addObject(i, objects.get(i));
		}
	}
	
	public void addObject(int num, int volume){
		for(Bin b : binGenes){
			if (b.getLeftCapacity()>=volume){
				b.addObject(num,volume);
				return;
			}
		}
		Bin b = new Bin();
		b.addObject(num, volume);
		binGenes.add(b);
	}
	
	public BPPIndividual copyOf(){
		BPPIndividual copy = new BPPIndividual(estimator);
		/*for(int i = 0; i < geneSize; i++){
			copy.setGene(i, genes[i]);
		}*/
		
		return copy;
	}
	
	public int getNumGenes(){
		return this.binGenes.size();
	}
	
	public void addGene(int pos, List<Bin> b){
		//some of the crossover complexity
		binGenes.addAll(pos,b);
	}
	
	public void addGene(Bin b){
		binGenes.add(b);
	}
	
	public void deleteGene(int position){
		this.binGenes.remove(position);
	}
	
	public Bin copyOfGene(int position){
		return this.binGenes.get(position).copyOf();
	}
	
	public Bin getGene(int position){
		return binGenes.get(position);
	}
	
	public int getFitness(){
		this.fitness = estimator.estimateFitness(this);
		return fitness;
	}
	
	public List<Bin> getAllBins(){
		return this.binGenes;
	}
	
	@Override
	public String toString(){
		
		String res = "";
		
		/*for(int i = 0; i < this.geneSize;i++){
			res += "["+ genes[i]+"]";
		}*/
		
		return res;
		
	}


}
