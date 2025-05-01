package GeneExpressionAnalyzer;

import java.util.HashSet;
import java.util.Set;

/**
 * Gene class
 * Contains a name and expression level
 * 
 * @author emilyekstrum
 * @version 4/28/25
 */

class Gene {
	private String name;
	private double expressionLevel; 
	private Set<String> sensitiveToStressors = new HashSet<>();

	/**
	 * Constructs a gene with a name and expression level in transcripts per million (TPM)
	 * @param name
	 * @param expressionLevel 
	 */
	public Gene(String name, double expressionLevel) {
		this.name = name;
		this.expressionLevel = expressionLevel;
	}

	/**
	 * Accessor method for gene name
	 * @return gene name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Adds a stressor sensitivity to the gene
	 * @param stressorType
	 */
	public void addSensitivity(String stressorType) {
		sensitiveToStressors.add(stressorType);
	}

	/**
	 * Determines if the gene is affected by the entered stressor
	 * @param stressor
	 * @return true if affected, false if unaffected
	 */
	public boolean isAffectedBy(Stressor stressor) {
		return sensitiveToStressors.contains(stressor.getType());
	}

	/**
	 * Changes the gene expression level based on the impact factor 
	 * and returns the new expression level to 2 decimals
	 * 
	 * Algorithm:
	 * 1. Calculates the change in gene expression by multiplying the impact factor 
	 * parameter by the current expression level of the gene
	 * 2. Updates the expression level of the gene by adding the expression change
	 * 3. If the expression level is greater than 1000 TPM (upper limit), the cell dies
	 * 4. If the expression level goes below 0 TPM (lower limit), the expression is set to 0 TPM
	 * 5. Prints new expression level of the gene rounded to 2 decimal places
	 * 
	 * @param impactFactor
	 */
	public void modifyExpression(double impactFactor) {
		double expressionChange = impactFactor * expressionLevel;
		expressionLevel += expressionChange;
		if(expressionLevel > 1000) {
			System.out.println("The cell has died due to fatal overexpression of " + name);
		}
		if(expressionLevel <= 0) {
			expressionLevel = 0;
			System.out.println(name + " new expression: " + (double) Math.round(expressionLevel * 100)/100);
		}
		System.out.println(name + " new expression: " + (double) Math.round(expressionLevel * 100)/100);
	}

	/**
	 * Accessor method for the current expression level of the gene rounded to 2 decimals
	 * @return expression level
	 */
	public double getExpressionLevel() {
		return (double) Math.round(expressionLevel * 100)/100;
	}
}
