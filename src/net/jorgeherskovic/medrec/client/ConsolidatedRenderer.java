package net.jorgeherskovic.medrec.client;

import java.util.List;

import com.google.gwt.user.client.ui.HTML;
import net.jorgeherskovic.medrec.shared.Consolidation;
import net.jorgeherskovic.medrec.shared.Medication;

public class ConsolidatedRenderer extends TableRenderer {

	public ConsolidatedRenderer(DraggableFlexTable table, String[] headings,
			List<Consolidation> meds) {
		super(table, headings, meds);
	}

	@Override
	public void renderTable() {
		DraggableFlexTable t = this.getAttachedTable();
		List<Consolidation> cons = this.getMedList();

		t.removeAllRows();
		this.renderTableHeadings("TableHeadings");
		int currentRow = 1;

		for (int i = 0; i < cons.size(); i++) {
			Consolidation this_cons = cons.get(i);
			Medication m1 = this_cons.getMed1();
			Medication m2 = this_cons.getMed2();
			int col = 0;

			/* Create an HTML widget and make it draggable */

			HTML handle = new HTML();
			handle.setHTML("&nbsp;&nbsp;≣&nbsp;&nbsp;");

			t.setWidget(currentRow, col++, handle);
			t.getRowDragController().makeDraggable(handle);

			t.setText(currentRow, col++, Integer.toString(i + 1));
			t.setHTML(currentRow, col++, m1.getProvenance());

			t.setHTML(currentRow, col++, m1.getMedicationName());
			t.setHTML(currentRow, col++, m1.getDose() + " " + m1.getUnits());
			t.setHTML(currentRow, col++, m1.getInstructions());
			t.setHTML(currentRow, col++, m1.getStartDateString());
			t.setHTML(currentRow, col++, m1.getEndDateString());
			t.setHTML(currentRow, col++, m1.getFormulation());
			t.setText(currentRow, col++, this_cons.getExplanation());

			String thisStyle = "NoReconciliation";
			if (this_cons.getScore() > 0.1) {
				thisStyle = "PartialReconciliation";
			}
			if (this_cons.getScore() > 0.99) {
				thisStyle = "FullReconciliation";
			}
			t.getRowFormatter().addStyleName(currentRow, "TableDesign");
			t.getRowFormatter().addStyleName(currentRow, thisStyle);

			/*
			 * Apply the TableDesign style to each cell individually to get
			 * borders
			 */
			this.applyStyleToAllCellsInRow(currentRow, "TableDesign");
			currentRow += 1;

			if (!m2.isEmpty()) {
				col = 0;
				HTML second_handle = new HTML();
				second_handle.setHTML("&nbsp;&nbsp;≣&nbsp;&nbsp;");

				t.setWidget(currentRow, col++, second_handle);
				t.getRowDragController().makeDraggable(second_handle);

				t.setText(currentRow, col++, Integer.toString(i + 1));
				t.setHTML(currentRow, col++, m2.getProvenance());

				t.setHTML(currentRow, col++, m2.getMedicationName());
				t.setHTML(currentRow, col++, m2.getDose() + " " + m2.getUnits());
				t.setHTML(currentRow, col++, m2.getInstructions());
				t.setHTML(currentRow, col++, m2.getStartDateString());
				t.setHTML(currentRow, col++, m2.getEndDateString());
				t.setHTML(currentRow, col++, m2.getFormulation());
				t.setText(currentRow, col++, this_cons.getExplanation());

				t.getRowFormatter().addStyleName(currentRow, "TableDesign");
				t.getRowFormatter().addStyleName(currentRow, thisStyle);

				/*
				 * Apply the TableDesign style to each cell individually to get
				 * borders
				 */
				this.applyStyleToAllCellsInRow(currentRow, "TableDesign");
				currentRow += 1;

			}
		}

		return;

	}

}
