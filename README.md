# Gene-Network-Simulation
Simulates gene expression change in response to cellular stressors

## Introduction

This program simulates changes in gene expression in response to external cellular stressors. The program initializes a generic mammalian cell with several genes and their protein products:

- HSPA & HSPA5 -> heat shock protein family A
- SMARCA2 -> probable global transcription activator SNF2L2
- TP53 -> p35 protein
- VEGFA -> vascular endothelial growth factor A
- USP -> universal stress protein
- SCL2A1 -> glucose transporter protein (GLUT1)
- FOLH1 -> prostate-specific membrane antigen (PSMA)

Additional genes can be loaded into the program via two paths:

- Small scale: Manually enter the gene name and starting expression level (TPM)
- Batch: Enter .txt file with gene names and starting expression levels (TPM)
  Input file format: .txt file with gene name and expression level separated by a space. See example .txt file.

The cell type can also be changed to muscle or epithelial cell types. The response of genes will vary when exposed to certain stressors based on the cell type. Currently, cells can be exposed to heat, pH change, mechanical, and hypoxic stress. The associated 'impact factor' with each stress serves to designate the stress severity. 

## Usage
Download all .java files and load in your current workspace. To use the console version, run the ConsoleExpressionAnalyzer.java file. 

## Author & Contact
Emily Ekstrum, Creighton University

emilyekstrum@creighton.edu
