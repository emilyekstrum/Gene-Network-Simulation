package GeneExpressionAnalyzer;

import java.util.ArrayList;
import java.util.List;

/**
 *  Space and time analysis program for GeneExpressionAnalyzer project.
 *  Generates two gene network graphs: a sparse graph and a dense graph.
 *  The sparse graph has 100 genes and each gene is only connected to two other genes.
 *  The dense graph has 20 genes, but makes connections between every gene. 
 *  Genes for both graphs are randomly generated.
 *  The search method for both cases is BFS - what is implemented for the 
 *  GeneNetwork search method
 *  
 *  Complexity analysis:
 *  Time - time (ns) required to search the gene networks for a connection
 *  Space - total nodes and edges of the graph
 *  
 *  @author emilyekstrum
 *  @version 5/4/2025
 */

public class ComplexityTester {

	public static void main(String[] args) {
        GeneNetwork sparseNetwork = new GeneNetwork();
        GeneNetwork denseNetwork = new GeneNetwork();

        // Sparse gene network graph of (100 genes)
        List<Gene> sparseGenes = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            sparseGenes.add(new Gene("g" + i, Math.random()));
        }
        // Connects genes sequentially, positive labels for edges/connections
        for (int i = 0; i < 99; i++) {
            sparseNetwork.addConnection(sparseGenes.get(i), sparseGenes.get(i + 1), "positive");
        }

        // Dense gene network graph (20 genes)
        List<Gene> denseGenes = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            denseGenes.add(new Gene("G" + i, Math.random()));
        }
        // Connects genes with a nested loop - every gene is connected to each other
        for (int i = 0; i < denseGenes.size(); i++) {
            for (int j = i + 1; j < denseGenes.size(); j++) {
                denseNetwork.addConnection(denseGenes.get(i), denseGenes.get(j), "negative");
            }
        }

        // Run BFS search test on sparse graph
        long start = System.nanoTime();
        String result = sparseNetwork.searchGene(sparseGenes.get(0), sparseGenes.get(99).getName());
        long end = System.nanoTime();
        System.out.println("Sparse Graph Search Result: " + result);
        System.out.println("Time complexity: " + (end - start) + " ns");
        System.out.println("Space complexity: Nodes = " + sparseNetwork.getTotalNodes() + ", Connections = " + sparseNetwork.getTotalConnections() + "\n");

        // Run BFS search test on dense graph
        start = System.nanoTime();
        result = denseNetwork.searchGene(denseGenes.get(0), denseGenes.get(19).getName());
        end = System.nanoTime();
        System.out.println("Dense Graph Search Result: " + result);
        System.out.println("Time complexity: " + (end - start) + " ns");
        System.out.println("Space complexity: Nodes = " + denseNetwork.getTotalNodes() + ", Connections = " + denseNetwork.getTotalConnections());
    }

}
