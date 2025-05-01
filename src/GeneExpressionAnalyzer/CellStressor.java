package GeneExpressionAnalyzer;

/**
 * Cell stressor class
 * 
 * @author emilyekstrum
 * @version 4/28/25
 */
class Stressor implements Comparable<Stressor> {
	private String type;
	private double impactFactor; 

	/**
	 * Constructs a cell stressor with type and impact factor
	 * on gene expression levels
	 * @param type
	 * @param impactFactor
	 */
	public Stressor(String type, double impactFactor) {
		this.type = type;
		this.impactFactor = impactFactor;
	}

	/**
	 * Accessor for the stressor type
	 * @return stressor type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Accessor for the impact factor
	 * @return impact factor
	 */
	public double getImpact() {
		return impactFactor;
	}

	/**
	 * Applies the stressor to the gene, changes expression level, and prints if the gene is affected or not
	 * @param gene
	 */
	public void applyStressorToGene(Gene gene) {
		if (gene.isAffectedBy(this)) {
			System.out.println("Applying stressor: " + type + " to gene: " + gene.getName());
			gene.modifyExpression(impactFactor); 
		} else {
			System.out.println("Gene " + gene.getName() + " is not affected by stressor: " + type);
		}
	}

	/**
	 * Compares stressor types
	 */
	@Override
	public int compareTo(Stressor other) {
		return Double.compare(Math.abs(other.impactFactor), Math.abs(this.impactFactor));
	}
}
