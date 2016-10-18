package newbppaco;

import akka.actor.ActorRef;

public class Estimator implements ArcEstimator {
	
	private final double alpha = 1.0;
	private final double beta = 2.0;
	private final double evaporation_rate = 0.5;
	private final int K = 2;
	private int[] etha; //nel caso di BPP, etha e' il peso dell'oggetto
	private double contributes[][];
	private double bestCost;
	private int spaceSize;
	private AntSolution bestSolution;
	private ActorRef graph;
	private int numBinBestSol;
	
	public Estimator(int spaceSize){
		
		this.numBinBestSol = 100;
		//this.graph = graph;
		this.contributes = new double[spaceSize][spaceSize];
		this.etha = new int[spaceSize];
		this.bestCost = 0.0;
		this.spaceSize = spaceSize;
		this.bestSolution = new AntSolution();
		for(Integer i : InitializeBPP.model.getItemSet())
			etha[i] = InitializeBPP.model.getObjects().get(i);
	}

	@Override
	public double getAlpha() {
		return alpha;
	}

	@Override
	public double getBeta() {
		return beta;
	}

	@Override
	public double getEvapRate() {
		return evaporation_rate;
	}
	
	public int[] getEtha() {
		return etha;
	}
	
	public double getContribute(int index1, int index2){ //get tau matrix
		return this.contributes[index1][index2];
	}
		
	@Override
	public double getBestSolutionCost(){
		return this.bestCost;
	}
	
	@Override
	public AntSolution getBestSolution(){
		return this.bestSolution;
	}
	
	public int[][] getCouples(){
		
		int couples[][] = new int[spaceSize][spaceSize];
		//Arrays.fill(couples, 0);
		for (Bin b: bestSolution.getBinList()){
			for (Integer i: b.getObjects().keySet()){
				for (Integer j: b.getObjects().keySet()){
					if (!(i.equals(j))){
						couples[i][j] = 1;
						couples[j][i] = 1;
					}
				}
			}
		}		
		return couples;
	}

	/**
	 * Called everytime an ant has finished.
	 * */
	@Override
	public void localUpdateRule(AntSolution solution) {
		
		
		double fitness = 0.0;
		
		double toDivide = 0.0;
		int maxContent = solution.getMaxContent();
		
		for (Bin b : solution.getBinList()){
			toDivide+= Math.pow(solution.getObjInBin(b)/maxContent, K);
		}
		
		fitness = toDivide/solution.getBinList().size();
		
		/*if (fitness > bestCost){
			
			bestCost = fitness;
			this.bestSolution = solution;
			//System.out.println("Ho trovato una fitness migliore e ho bin " + this.bestSolution.numBins() );
		}*/
		System.out.println("Numero bin soluzione before" + solution.numBins()+ " Numero bin migliore " + this.numBinBestSol);
		if (solution.numBins() < this.numBinBestSol){
			System.out.println("Sono entrato perche' la nuova sol ha " + solution.numBins() + " bin e quella migliore ne ha " + this.numBinBestSol);
			//this.numBinBestSol = solution.numBins();
			setBestSol(solution,solution.numBins());
			//this.bestSolution = solution;
			System.out.println("Nell'if, ora la best solution e' " + solution.numBins() + " e quella settata e' " + this.bestSolution.numBins());
		}
		
		System.out.println("Secondo il metodo il migliore e' " +this.numBinBestSol);
		
		double sum = 0;
		//update pheromone for single object (equazione 7)
		for (Bin b: solution.getBinList()){
			for (int obj : b.getObjects().keySet()){
				for (int obj1 : b.getObjects().keySet()){
					sum += contributes[obj][obj1];
				}
				(b.getSinglePheromones())[obj] = sum/b.getObjects().size();
			}
		}
	}
	
	public void setBestSol(AntSolution solution, int numBins){
		this.bestSolution = solution;
		this.numBinBestSol = numBins;
		
	}
	
	public int getBestSolBins(){
		return this.numBinBestSol;
	}
	
	public void setPheromones(double pher[][]){
		this.contributes =pher;
	}

}
