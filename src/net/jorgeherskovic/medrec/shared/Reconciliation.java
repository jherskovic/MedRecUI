package net.jorgeherskovic.medrec.shared;

import java.util.ArrayList;


public abstract class Reconciliation {
	protected final ArrayList<ReconciledMedication> reconciledMeds;

	protected final ArrayList<Consolidation> consolidatedMeds;
	protected final ArrayList<String[]> originalLists;
			
	public Reconciliation() {
		// TODO Auto-generated constructor stub
		this.reconciledMeds = new ArrayList<ReconciledMedication>();
		this.consolidatedMeds = new ArrayList<Consolidation>();
		this.originalLists= new ArrayList<String[]>();
	}

	public ArrayList<Consolidation> getConsolidatedMeds() {
		return this.consolidatedMeds;
	}
	
	public ArrayList<ReconciledMedication> getReconciledMeds() {
		return this.reconciledMeds;
	}
}
