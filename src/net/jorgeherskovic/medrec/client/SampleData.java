package net.jorgeherskovic.medrec.client;

import java.util.ArrayList;

import net.jorgeherskovic.medrec.shared.Consolidation;
import net.jorgeherskovic.medrec.shared.Medication;
import net.jorgeherskovic.medrec.shared.ReconciledMedication;

public class SampleData {
	private String[] medlist1 = { "Zoloft|50|mg|oral|qd|EHR|2006.05.22|",
			"Vicodin|5/500|mg|oral|qd|EHR|2006.05.22|",
			"Levaquin|10|mg|oral|qd|EHR|2009.02.25|2009.03.10" };
	private String[] medlist2 = {
			"Sertraline HCL|50|mg|oral|qd|Patient|2007.03.22|",
			"Acetaminophen|325|mg|oral|qd|Patient|2007.03.22|" };
	private String[] reconciled = {
			"Quinapril|40|mg|oral|bid|Both|2000.12.15|",
			"Warfarin|5|mg|oral|qd|Both|2008.09.17|",
			"Lipitor|10|mg|oral|qd|Both|2000.12.15|",
			"Sotalol|80|mg|oral|qd|Both|2000.12.15|" };

	public ArrayList<ReconciledMedication> reconciledMeds;

	public ArrayList<Consolidation> consolidatedMeds;

	public SampleData() {
		this.reconciledMeds = new ArrayList<ReconciledMedication>(5);
		@SuppressWarnings("unused")
		boolean b = this.reconciledMeds.add(new ReconciledMedication(new Medication(reconciled[0])));
		b=this.reconciledMeds.add(new ReconciledMedication(new Consolidation(new Medication(reconciled[1]), new Medication(reconciled[1]), 1.0, "Identical"), 0));
		b=this.reconciledMeds.add(new ReconciledMedication(new Medication(reconciled[2])));
		b=this.reconciledMeds.add(new ReconciledMedication(new Medication(reconciled[3])));
		
		this.consolidatedMeds = new ArrayList<Consolidation>(5);
		b=this.consolidatedMeds.add(new Consolidation(new Medication(medlist1[0]), new Medication(
				medlist2[0]), 0.6, "Brand/Generic"));
		b=this.consolidatedMeds.add(new Consolidation(new Medication(medlist1[1]), new Medication(
						medlist2[1]), 0.6, "Brand/Generic"));
		b=this.consolidatedMeds.add(new Consolidation(new Medication(medlist1[2]),
						new Medication(), 0.0, "Unique"));

	}

}
