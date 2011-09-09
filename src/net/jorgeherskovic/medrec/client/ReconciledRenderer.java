package net.jorgeherskovic.medrec.client;

import java.util.List;

import com.google.gwt.user.client.ui.HTML;

import net.jorgeherskovic.medrec.shared.Consolidation;
import net.jorgeherskovic.medrec.shared.Medication;
import net.jorgeherskovic.medrec.shared.ReconciledMedication;

public class ReconciledRenderer extends TableRenderer {

	public ReconciledRenderer(DraggableFlexTable table, String[] headings,
			List<ReconciledMedication> meds) {
		super(table, headings, meds);
	}

	@Override
	public void renderTable() {
		DraggableFlexTable t = this.getAttachedTable();
		List<Consolidation> meds = this.getMedList();

		t.removeAllRows();
		this.renderTableHeadings("TableHeadings");

		for (int i = 0; i < meds.size(); i++) {
			Medication m = meds.get(i).getSelectedMedication();
			int col = 0;

			HTML handle = new HTML("&nbsp;&nbsp;≣&nbsp;&nbsp;");
			t.setWidget(i + 1, col++, handle);
			t.getRowDragController().makeDraggable(handle);

			t.setText(i + 1, col++, Integer.toString(i + 1));
			t.setHTML(i + 1, col++, m.getProvenance());
			t.setHTML(i + 1, col++, m.getMedicationName());
			t.setHTML(i + 1, col++, m.getDose() + " " + m.getUnits());
			t.setHTML(i + 1, col++, m.getInstructions());
			t.setHTML(i + 1, col++, m.getStartDateString());
			t.setHTML(i + 1, col++, m.getEndDateString());
			t.setHTML(i + 1, col++, m.getFormulation());
			t.setText(i + 1, col++, "");

			t.getRowFormatter().addStyleName(i + 1, "TableDesign");
			t.getRowFormatter().addStyleName(i + 1, "FullReconciliation");

			/*
			 * Apply the TableDesign style to each cell individually to get
			 * borders
			 */
			this.applyStyleToAllCellsInRow(i + 1, "TableDesign");
		}

		return;

	}

}
