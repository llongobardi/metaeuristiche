package genetic;

public class BPPEstimator implements FitnessEstimator {

	@Override
	public int estimateFitness(BPPIndividual subject) {
		int numG = subject.getNumGenes();
		int fitness = 0;
		for (int i = 0; i<numG; i++){
			fitness += Math.pow(
					(InitializeBPP.model.getBinCapacity()-subject.getGene(i).getLeftCapacity())/InitializeBPP.model.getBinCapacity()
					,2);
		}
		fitness = fitness/numG;
		return fitness;
	}
	
	

}
