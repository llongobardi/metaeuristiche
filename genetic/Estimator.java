package genetic;

public class Estimator implements FitnessEstimator{

	@Override
	public int estimateFitness(Individual subject) {
		
		int fitness = 0;
		
		for(int i = 0; i < subject.getGeneSize(); i++){
			fitness += subject.getGene(i) == 1 ? 1 : 0;
		}
		
		return fitness;
	}

}
