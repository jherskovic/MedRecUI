package net.jorgeherskovic.medrec.shared;

/*
 * A ReconciledMedication presents only one of the Consolidation's medications to the world.
 * It also implements single medications as a consolidation with only one filled element and
 * a selection already in place. 
 */

public class ReconciledMedication extends Consolidation {
	
	public ReconciledMedication(Medication med) {
		super(med, new Medication(), 1.0, "Unique");
		this.setSelection(0);
	}
	
	public ReconciledMedication(Consolidation cons, int selection) {
		super(cons.getMed1(), cons.getMed2(), cons.getScore(), cons.getExplanation());
		this.setSelection(selection);
	}
}
