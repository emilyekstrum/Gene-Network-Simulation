package GeneExpressionAnalyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Cell gene expression analyzer driver program
 * 
 * @author emilyekstrum
 * @version 5/1/25
 */

public class ConsoleExpressionAnalyzer {

	public static void main(String[] args) {

		//Program introduction
		System.out.println("This progream simulates changing gene expression levels in a mammalian cell "
				+ "in response to external cellular stressors (measured in transcripts per million (TPM)).");
		System.out.println("Preloaded genes: \n - HSPA: heat shock \n - HSPA5: heat shock \n - SMARCA2: pH change \n - TP53: DNA damage \n "
				+ "- VEGFA: hypoxic conditions \n - USP: mechanical stress \n - SLC2A1: hypoxic conditions \n - FOLH1: heat stress");

		Scanner scanner = new Scanner(System.in);

		// Initialize cell 
		Cell cell = new MammalianCell("Mammalian Cell", new HashMap<>());

		// Create preloaded genes
		Gene HSPA = new Gene("hspa", 0.5);
		HSPA.addSensitivity("heat");

		Gene HSPA5 = new Gene("hspa5", 0.3);
		HSPA5.addSensitivity("heat");

		Gene SMARCA2 = new Gene("smarca2", 15);
		SMARCA2.addSensitivity("ph change");

		Gene tp53 = new Gene("tp53", 0.1);
		tp53.addSensitivity("dna damage");

		Gene VEGFA = new Gene("vegfa", 60);
		VEGFA.addSensitivity("hypoxia");

		Gene USP = new Gene("usp", 230);
		USP.addSensitivity("mechanical stress");

		Gene SLC2A1 = new Gene("slc2a1", 103);
		SLC2A1.addSensitivity("hypoxia");

		Gene FOLH1 = new Gene("folh1", 0.4);
		FOLH1.addSensitivity("heat");

		// Add preloaded genes to the cell
		cell.addGene(HSPA);
		cell.addGene(HSPA5);
		cell.addGene(SMARCA2);
		cell.addGene(tp53);
		cell.addGene(VEGFA);
		cell.addGene(USP);
		cell.addGene(SLC2A1);
		cell.addGene(FOLH1);

		// Initialize the gene regulatory network
		GeneNetwork network = new GeneNetwork();

		// Add connections to the regulatory network
		network.addConnection(HSPA, HSPA5, "positive");
		network.addConnection(VEGFA, tp53, "negative");
		network.addConnection(SMARCA2, tp53, "positive");
		network.addConnection(VEGFA, SLC2A1, "positive");
		network.addConnection(HSPA, FOLH1, "negative");
		network.addConnection(HSPA5, FOLH1, "negative");
		network.addConnection(VEGFA, HSPA, "negative");

		// User console options
		boolean running = true;
		while (running) {
			System.out.println("");
			System.out.println("---- Current Cell Type: " + cell.getCellType() + " ----" );
			if (cell instanceof EpithelialCell) {
				System.out.println("Epithelial cells are responsive to chemical and thermal stress.");
			} if (cell instanceof MuscleCell) {
				System.out.println("Muscle cells are highly responsive to mechanical and oxygen stress.");
			} 

			System.out.println("Choose an action: Add gene, Stress cell, Change cell, Connect genes, Search, List genes, Show network, Remove gene, Gene info, Quit");
			System.out.println("Need help deciding? Enter help to see option descriptions.");
			String action = scanner.nextLine().toLowerCase();

			switch (action) {
			case "add gene":
				System.out.println("");
				System.out.println("Enter 'F' to enter a file or 'M' to enter genes manually.");
				String addGeneAction = scanner.nextLine().toLowerCase();
				if(addGeneAction.equals("f")) {
					String fileName = "genes.txt";
					try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			            String line;
			            while ((line = br.readLine()) != null) {
			                String[] parts = line.split(" ");
			                if (parts.length == 3) {
			                        String geneName = parts[0].trim().toLowerCase(); 
			                        double expressionLevel = Double.parseDouble(parts[1].trim());
			                        String stressor = parts[2].trim().toLowerCase();
			                        if(!cell.genes.containsKey(geneName)) {
			                        	Gene newGene = new Gene(geneName, expressionLevel);
			                        	newGene.addSensitivity(stressor);
			                        	cell.addGene(newGene);
			                        }
			                } }
			            }
			         catch (IOException e) {
			            System.err.println("Error reading the file: " + e.getMessage());
			        }
					
				}
				if(addGeneAction.equals("m")) {
					System.out.println("Enter gene name:");
					String geneName = scanner.nextLine().toLowerCase();
					if(!cell.genes.containsKey(geneName)) {
						System.out.println("Enter initial expression level (0 to 1000 TPM):");
						double expressionLevel = Double.parseDouble(scanner.nextLine());
						Gene newGene = new Gene(geneName, expressionLevel);

						System.out.println("Enter number of stressors this gene is sensitive to:");
						int numSensitivities = Integer.parseInt(scanner.nextLine());
						for (int j = 0; j < numSensitivities; j++) {
							System.out.println("Enter stressor type " + (j+1) + ":");
							String stressorType = scanner.nextLine();
							newGene.addSensitivity(stressorType);
						}
						cell.addGene(newGene);
					}else {
						System.out.println("Gene already in database.");
					}
				}
				else {
					System.out.println("Genes loaded into cell.");
				}
				
				break;

			case "stress cell":
				System.out.println("");
				System.out.println("Enter stressor type (heat, pH change, mechanical, or hypoxia):");
				String type = scanner.nextLine().toLowerCase();
				System.out.println("Enter stressor impact factor (positive or negative number):");
				double impactFactor = Double.parseDouble(scanner.nextLine());
				if((impactFactor > 1.0) || (impactFactor < -1.0)) {
					System.out.println("Please enter a valid impact factor between -1 and 1.");
					System.out.println("Enter stressor impact factor (positive or negative number):");
					impactFactor = Double.parseDouble(scanner.nextLine());
				}
				cell.respondToStress(new Stressor(type, impactFactor));
				break;

			case "change cell":
				System.out.println("Cell type options: generic mammalian cell, muscle cell, epithelial cell");
				System.out.println("Enter the cell type you want to switch to: ");
				String cellType = scanner.nextLine().toLowerCase();

				Map<String, Gene> currentCellGenes = cell.getGenes();
				if(cellType.contains("mammalian")) {
					cell = new MammalianCell("Mammalian Cell", currentCellGenes);
					break;
				}
				if(cellType.contains("muscle")) {
					cell = new MuscleCell("Muscle Cell", currentCellGenes);
					break;

				}
				if(cellType.contains("epithelial")) {
					cell = new EpithelialCell("Epithelial Cell", currentCellGenes);
					break;
				}

			case "connect genes":
				System.out.println("");
				System.out.println("Enter first gene name:");
				String first = scanner.nextLine().toLowerCase();
				System.out.println("Enter second gene name:");
				String second = scanner.nextLine().toLowerCase();
				System.out.println("Enter regulation type (positive/negative):");
				String regulation = scanner.nextLine();
				Gene gene1 = cell.genes.get(first);
				Gene gene2 = cell.genes.get(second);
				if (gene1 != null && gene2 != null) {
					network.addConnection(gene1, gene2, regulation);
					System.out.println("Connected " + first + " and " + second);
				} else {
					System.out.println("One or both genes not found in current database.");
				}
				break;

			case "search":
				System.out.println("");
				System.out.println("Enter starting gene name:");
				String start = scanner.nextLine();
				System.out.println("Enter target gene name:");
				String target = scanner.nextLine();
				if(cell.genes.containsKey(target)) {
					Gene startGene = cell.genes.get(start);
					if (startGene != null) {
						String found = network.searchGene(startGene, target);
						System.out.println("Gene network connection between " + startGene.getName() + " and " + target + ": " + found);
					} else {
						System.out.println("Starting gene not found in current database.");
					}
				}
				else {
					System.out.println("Target gene not found in current database.");
				}
				break;

			case "list genes":
				System.out.println("");
				for (Gene gene : cell.genes.values()) {
					System.out.println(gene.getName() + " - Expression (transcripts per million): " + gene.getExpressionLevel());
				}
				break;

			case "show network":
				System.out.println("");
				System.out.println("Enter a gene to view it's network: ");
				String userGeneSelection = scanner.nextLine().toLowerCase();
				Gene geneSelection = cell.genes.get(userGeneSelection);
				if(geneSelection != null) {
					network.printNetwork(geneSelection);
				} else {
					System.out.println("Gene " + userGeneSelection + " not found in current database.");
				}
				break;

			case "remove gene":
				System.out.println("");
				System.out.println("Enter the name of the gene to remvove: ");
				String geneRemoveName = scanner.nextLine().toLowerCase();
				Gene geneToRemove = cell.genes.get(geneRemoveName);
				if(geneToRemove != null) {
					network.removeGene(geneToRemove);
					cell.genes.remove(geneRemoveName);
					System.out.println(geneRemoveName + " has been removed.");
				} else {
					System.out.println("Gene " + geneRemoveName + " not in current database");
				}
				break;	

			case "help":
				System.out.println("");
				System.out.println("Add gene: Add a gene to the simulation. Manually enter the gene name, starting expression level, and any stressors the gene is sensitive to."
						+ "\n	Or, upload genes.txt file containing gene name, expression level, and sensitivity separated by spaces.");
				System.out.println("Add stressor: Enter a cell stressor type and the impact factor. The impact factor \n 	must "
						+ "be between -1 and 1. A negative impact factor will decrease expression and a positive impact "
						+ "factor will increase expression.");
				System.out.println("Connect: Connect two genes in a gene regulatory network.");
				System.out.println("Search: Search the gene network for connections between genes.");
				System.out.println("List genes: Lists the current loaded genes and the expression levels within the cell.");
				System.out.println("Remove gene: Removes a gene from the cell.");
				System.out.println("Gene info: Prints information, such as response and funciton, on the preloaded genes.");
				break;
				
			case "gene info":
				cell.printGenes();
				System.out.println("Enter the gene name to get info: ");
				String geneForInfo = scanner.nextLine().toLowerCase();
				if(geneForInfo.contains("hspa")) {
					System.out.println("HSPA (heat shock protein family A) genes are upregulated in cells"
							+ "\nsubjected to high temperatures to protect cellular proteins from denaturation"
							+ "\n(protein unfolding). The upregulation of these genes and their products allows"
							+ "\ncells to have a degree of heat tolerance and survive during heat stress.");
					break;
				}
				if(geneForInfo.contains("smarca2")) {
					System.out.println("The SMARCA2 gene encodes for an ATPase subunit of a chromatin"
							+ "\nremodeling complex. In response to certain stress, like pH change, the gene product of SMARCA2 functions to adjust"
							+ "\nnucleosome positioning, which affects overall cell gene expression. In a way,"
							+ " \nthis protein functions as a master regulator of genes. Mutations to SMARC2 are linked to cancer"
							+ "\nand developmental issues.");
					break;
				}
				if(geneForInfo.contains("tp53")) {
					System.out.println("TP53 encodes for an essential tumor suppressor gene product that is invovled"
							+ "\nin regulating parts of the cell cycle. TP53 can be upregulated in response to DNA damage"
							+ "\nand prevents mutations to DNA by arresting the cell cycle, DNA repair, and even triggering"
							+ "\napoptosis (cell death).");
					break;					
				}
				if(geneForInfo.contains("vegfa")) {
					System.out.println("VEGFA (vascular endothelial growth factor A) encodes a signal protein that induces"
							+ "\nangiogensis (new blood vessel growth) when the cell is in hypoxic conditions. The VEGFA gene"
							+ "\nproduct is a trigger for a signal cascade that promotes cell proliferation, migration, and"
							+ "\nangiogenesis. This gene is essential for normal development, however, can become problematic"
							+ "\nfor cancer tumors.");
					break;
				}
				if(geneForInfo.contains("usp")) {
					System.out.println("USP gene, or the universal stress protein gene, encodes a protein that helps cells"
							+ "\nsuvive a variety of stressors. These stressors include nutrient deficiency, hypoxia,"
							+ "\nand toxin exposure. The protein product works to stabilize proteins and aid signalling"
							+ "\npathways to enhance resistance to stress.");
					break;
				}
				if(geneForInfo.contains("slc2a1")) {
					System.out.println("The SLC2A1 gene encodes a transmembrane protein, GLUT1 (glucose transporter 1),"
							+ "\nthat allows cells to take up glucose. Hypoxic conditions or metabolic stress cause"
							+ "\nGLUT1 upregulation, especially in tissues that require high amounts of energy, such as brain cells.");
					break;
				}
				if(geneForInfo.contains("folh")) {
					System.out.println("The FOLH1 (folate hydrolase 1) gene encodes a protein that is involved in "
							+ "\nfolate metabolism in the intestine. FOLH1 is often downregulated in resposne to heat."
							+ "\nUpregulation of FOLH1B is also linked to cancer progression, which is likely due to"
							+ "\nthe role it plays in nutrient uptake.");
					break;
				}
				else {
					System.out.println("Gene not found in current cell.");
				}
				break;

			case "quit":
				System.out.println("");
				System.out.println("Session ended.");
				running = false;
				break;

			default:
				System.out.println("");
				System.out.println("Unrecognized input. Please try again.");
				break;
			}
		}

		scanner.close();
	}
}
