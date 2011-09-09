package net.jorgeherskovic.medrec.client;

import java.util.ArrayList;

import net.jorgeherskovic.medrec.shared.ReconciledMedication;

import com.google.gwt.user.client.ui.FlexTable;

public class TableRenderer {
	private DraggableFlexTable attachedTable;
	private ArrayList<ReconciledMedication> medList;
	
	public TableRenderer(DraggableFlexTable table, ArrayList<ReconciledMedication> meds) {
		this.attachedTable=table;
		this.medList=meds;
	}
}
