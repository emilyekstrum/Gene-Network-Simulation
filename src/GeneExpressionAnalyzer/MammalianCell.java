package GeneExpressionAnalyzer;

import java.util.Map;

/**
 * Human cell class derived from Cell class
 * 
 * @author emilyekstrum
 * @version 4/28/25
 */
class MammalianCell extends Cell {

	public MammalianCell(String name, Map<String, Gene> genes) {
		super("Generic Human Cell", genes);
	}

	/**
	 * Simulates the human cell responding to the stressor
	 * Changes gene expression of genes that are sensitive to the stressor
	 * in the cell
	 * 
	 * Algorithm:
	 * 1. Prints the cell responding to the entered stressor
	 * 2. For all the genes in the cell, check if the gene is affected by the stressor
	 * 3. If affected, the expression levels are modified by calling .modifyExpression() 
	 * for the gene
	 * 4. If the gene is not affected, no change to the expression is made
	 * 5. Prints out gene expression in response to stressor
	 * 
	 * @param stressor type
	 */
	@Override
	public void respondToStress(Stressor stressor) {
		System.out.println("Human cell responding to stressor: " + stressor.getType());
		for (Gene gene : genes.values()) {
			if (gene.isAffectedBy(stressor)) {
				gene.modifyExpression(stressor.getImpact());
			}
		}

	}
}
