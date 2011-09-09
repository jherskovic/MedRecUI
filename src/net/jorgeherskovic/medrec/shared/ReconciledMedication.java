package net.jorgeherskovic.medrec.shared;

/*
 * A ReconciledMedication presents only one of the Consolidation's medications to the world.
 * It also implements single medications as a consolidation with only one filled element and
 * a selection already in place. 
 */

public class ReconciledMedication {
	private Consolidation myConsolidation;
	
	public ReconciledMedication(Medication med) {
		super();
		this.myConsolidation=new Consolidation(med, new Medication(), 1.0, "Unique");
		this.myConsolidation.setSelection(0);
	}
	
	public ReconciledMedication(Consolidation cons, int selection) {
		super();
		this.myConsolidation=cons;
		this.myConsolidation.setSelection(selection);
	}
	
	public Consolidation getConsolidation() {
		return myConsolidation;
	}
	
	public Medication getSelectedMedication() {
		return myConsolidation.getSelectedMedication();
	}

}
