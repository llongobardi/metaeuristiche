package bppaco;

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
	private int numBinBestSol;
	
	public Estimator(int spaceSize){
		
		this.numBinBestSol = 100;
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
		double maxContent = (double)solution.getMaxContent();
		
		for (Bin b : solution.getBinList()){
			toDivide+= Math.pow(solution.getObjInBin(b)/maxContent, K);
		}
		
		fitness = toDivide/solution.getBinList().size();
		
		if (fitness > bestCost){
			
			bestCost = fitness;
			this.bestSolution = solution;
			this.numBinBestSol = solution.numBins();
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
