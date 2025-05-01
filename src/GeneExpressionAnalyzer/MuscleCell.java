package GeneExpressionAnalyzer;

import java.util.Map;

/**
 * Muscle cell class derived from Cell class
 * 
 * @author emilyekstrum
 * @version 4/28/25
 */
public class MuscleCell extends Cell{

	public MuscleCell(String name, Map<String, Gene> genes) {
		super("Muscle Cell", genes);
	}

	/**
	 * Simulates the muscle cell responding to the stressor by amplifying
	 * the impact of mechanical and hypoxic stress
	 * Changes gene expression of genes that are sensitive to the stressor
	 * in the cell
	 * 
	 * Algorithm:
	 * 1. Prints the muscle cell is responding to the entered stress
	 * 2. Creates a new impact factor specific to the muscle cell
	 * 3. Checks of the entered stressor is a stressor that muscle cells respond
	 * to (mechnical stress or hypoxia)
	 * 4. If the muscle cell does respond to the stressor, the mucsle impact factor
	 * is amplified by x1.2
	 * 5. Loops through all genes in the muscle cell, checks if the gene is 
	 * affected by the stressor, and adjusts the expression by calling .modifyExpression()
	 * for the gene passing the muscle impact factor value
	 * 6. Prints out gene expression in response to stressor
	 * 
	 * @param cell stressor
	 */
	@Override
	public void respondToStress(Stressor stressor) {
		System.out.println("Muscle cell responding to stressor: " + stressor.getType());
		double muscleImpactFactor = stressor.getImpact();
		if(stressor.getType().equals("mechanical") || stressor.getType().equals("hypoxia")) {
			muscleImpactFactor *= 1.2;
		}

		for (Gene gene : genes.values()) {
			if (gene.isAffectedBy(stressor)) {
				gene.modifyExpression(muscleImpactFactor);
			}
		}

		for (Gene gene : genes.values()) {
			if(!gene.isAffectedBy(stressor)) {
				System.out.println(gene.getName() + " - Expression (transcripts per million): " + gene.getExpressionLevel());
			}
		}
	}

}
