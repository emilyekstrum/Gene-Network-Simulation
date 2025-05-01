package GeneExpressionAnalyzer;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract cell class with name and HashMap of genes
 * 
 * @author emilyekstrum
 * @version 4/27/25
 */

abstract class Cell {
	public String name;
	public Map<String, Gene> genes = new HashMap<>();

	/**
	 * Constructs a cell with the cell name and map of genes
	 * Adds genes into cell
	 * @param name
	 * @param genes
	 */
	public Cell(String name, Map<String, Gene> genes) {
		this.name = name;
		this.genes = new HashMap<>(genes);
		for(Gene gene : genes.values()) {
			this.addGene(gene);
		}
	}

	/**
	 * Accessor for cell type
	 * @return cell type
	 */
	public String getCellType() {
		return this.name;
	}

	/**
	 * Adds a gene to the cell
	 * @param gene
	 */
	public void addGene(Gene gene) {
		genes.put(gene.getName(), gene);
	}

	/**
	 * Accessor for genes in the cell
	 * @return map of genes in the cell
	 */
	public Map<String, Gene> getGenes(){
	    return genes;
	}
	
	/**
	 * Prints current genes in the cell
	 */
	public void printGenes() {
	    if (genes.isEmpty()) {
	        System.out.println("No genes currently loaded in the cell.");
	        return;
	    }
	    System.out.print("Genes in the cell:");
	    for (Map.Entry<String, Gene> entry : genes.entrySet()) {
	        System.out.print(" " + entry.getKey());
	    }
	    System.out.println("");
	}

	/**
	 * Simulates cell response to a stressor
	 * @param stressor
	 */
	public abstract void respondToStress(Stressor stressor);
}

