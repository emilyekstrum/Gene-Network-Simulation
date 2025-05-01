package GeneExpressionAnalyzer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Gene network graph implemented with a nested HashMap
 * 
 * @author emilyekstrum
 * @version 4/28/25
 */
public class GeneNetwork {
	private Map<Gene, Map<Gene, String>> geneNetwork = new HashMap<>(); 
	
	public void addConnection(Gene g1, Gene g2, String regulation) {
		if (!geneNetwork.containsKey(g1)) {
			geneNetwork.put(g1, new HashMap<>()); 
		}
		geneNetwork.get(g1).put(g2, regulation);

		if (!geneNetwork.containsKey(g2)) {
			geneNetwork.put(g2, new HashMap<>());
		}
		geneNetwork.get(g2).put(g1, regulation);
	}
	
	/**
	 * Determines if two genes, g1 and g2, have a connection and if 
	 * the connection is positive or negative (upregulate or downregulate)
	 * @param g1
	 * @param g2
	 * @return positive, negative, or no connection
	 */
	public String getRegulation(Gene g1, Gene g2) {
	    if (geneNetwork.containsKey(g1)) {
	        Map<Gene, String> neighbors = geneNetwork.get(g1);
	        if (neighbors.containsKey(g2)) {
	            return neighbors.get(g2); 
	        }
	    }
	    return "No connection";
	}

	/**
	 * Searches for connections between genes in the regulatory network
	 * 
	 * Algorithm:
	 * 1. Initializes two data structures to search: a HashSet that contains genes already looked at (visited)
	 * and a queue implemented using a LinkedList that contains the gene to search through (queue)
	 * 2. While the queue is not empty, the top gene on the queue is removed as the current gene being looked at
	 * 3. Checks if the name of the current gene matches the name of the target gene that is searched for
	 * 4. If the name of the current gene from the queue matches the target gene name, the gene has been found
	 * and the method returns "yes"
	 * 5. Otherwise, the current gene looked at is added to the visited set and a map of the gene 'neighbors' is 
	 * initialized with the gene network of the current gene
	 * 6. If the neighbors map is not null, each key value (name) of the neighbors in the gene network 
	 * is checked if the gene is in the visited set.
	 * 7. If the neighbor gene is not in the visited set, the neighbor is added to the queue
	 * 8. Returns "no" that the target gene is not in the gene network of the starting gene
	 * 
	 * @param start - First gene entered for search
	 * @param targetName - Second gene entered for search
	 * @return status of the connection (yes/no)
	 */
	public String searchGene(Gene start, String targetName) {
		String foundResult = "No connection found";
		Set<Gene> visited = new HashSet<>();
		Queue<Gene> queue = new LinkedList<>();
		queue.add(start);

		while (!queue.isEmpty()) {
			Gene current = queue.remove();
			if (current.getName().equals(targetName)) {
					foundResult = "Yes, " + getRegulation(start, current);
			}
			visited.add(current);

			Map<Gene, String> neighbors = geneNetwork.get(current);

			if(neighbors != null) {
				for(Gene neighbor : neighbors.keySet()) {
					if(!visited.contains(neighbor)) {
						queue.add(neighbor);
					}
				}
			}
		}
		return foundResult;
	}

	/**
	 * Removes a gene from the gene network
	 * 
	 * Algorithm: 
	 * 1. Checks if the gene network contains the gene to be removed
	 * 2. If the gene network contains the gene to remove, the neighbors, or connected genes in the network,
	 * are looped through as the key value set (gene name)
	 * 3. For each loop through the gene network of the gene to be removed, the gene to be removed
	 * is removed from the other gene's networks
	 * 4. Finally, the gene to removed is removed from its own gene network
	 * 
	 * @param gene to remove
	 */
	public void removeGene(Gene gene) {
		if (geneNetwork.containsKey(gene)) {
			for (Gene neighbor : geneNetwork.get(gene).keySet()) {
				geneNetwork.get(neighbor).remove(gene);
			}
			geneNetwork.remove(gene);
		}
	}

	/**
	 * Prints the gene regulatory network in the console
	 * @param gene - prints the network for this gene
	 */
	public void printNetwork(Gene gene) {
		if (!geneNetwork.containsKey(gene)) {
			System.out.println(gene.getName() + " has no connections.");
			return;
		}

		System.out.println("Gene nework for " + gene.getName() + ":");
		System.out.print(gene.getName());

		Map<Gene, String> neighbors = geneNetwork.get(gene);
		for (Map.Entry<Gene, String> entry : neighbors.entrySet()) {
			System.out.print(" -[" + entry.getValue() + "]-> " + entry.getKey().getName());
		}
		System.out.println("");
	}
}
