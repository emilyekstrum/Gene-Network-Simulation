package GeneExpressionAnalyzer;

import java.util.Map;

/**
 * Epithelial (skin) cell class derived from Cell class
 * 
 * @author emilyekstrum
 * @version 4/28/25
 */
public class EpithelialCell extends Cell{

	public EpithelialCell(String name, Map<String, Gene> genes) {
		super("Epithelial Cell", genes);
	}

	/**
	 * Simulates the epithelial cell responding to the stressor by amplifying 
	 * the impact of heat or pH stressors
	 * Changes gene expression of genes that are sensitive to the stressor
	 * in the cell
	 * 
	 * Algorithm:
	 * 1. Prints the epithelial cell is responding to the entered stress
	 * 2. Creates a new impact factor specific to the epithelial cell
	 * 3. Checks of the entered stressor is a stressor that epithelial cells respond
	 * to (heat or pH change)
	 * 4. If the epithelial cell does respond to the stressor, the epithelial impact factor
	 * is amplified by x1.2
	 * 5. Loops through all genes in the epithelial cell, checks if the gene is 
	 * affected by the stressor, and adjusts the expression by calling .modifyExpression()
	 * for the gene passing the epithelial impact factor value
	 * 6. Prints out gene expression in response to stressor
	 * 
	 * @param cell stressor type
	 */
	@Override
	public void respondToStress(Stressor stressor) {
		System.out.println("Epithelial cell responding to stressor: " + stressor.getType());
		double epithelialImpactFactor = stressor.getImpact();
		if(stressor.getType().equals("heat") || stressor.getType().equals("pH")) {
			epithelialImpactFactor *= 1.2;
		}
		for (Gene gene : genes.values()) {
			if (gene.isAffectedBy(stressor)) {
				gene.modifyExpression(epithelialImpactFactor);
			}
		}
	}
}
