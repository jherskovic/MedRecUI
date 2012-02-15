package net.jorgeherskovic.medrec.client;

import java.util.List;
import java.util.Map;

import org.adamtacy.client.ui.effects.events.EffectCompletedEvent;
import org.adamtacy.client.ui.effects.events.EffectCompletedHandler;
import org.adamtacy.client.ui.effects.impl.Fade;

import net.jorgeherskovic.medrec.client.event.RedrawEvent;
import net.jorgeherskovic.medrec.client.event.RowDroppedEvent;
import net.jorgeherskovic.medrec.shared.Consolidation;
import net.jorgeherskovic.medrec.shared.Medication;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;

public class ReconciledRenderer extends TableRenderer {

	private static String[] columnStyles = { "DragHandle", 
			"Origin", "Medication", "Dosage", "Frequency", "Date", "Date",
			"Form", "Alert" };

	public ReconciledRenderer(DraggableFlexTable table, String[] headings,
			SimpleEventBus bus) {
		super(table, headings, bus);
	}

	// returns the handle to the drag token
	private HTML renderParsedRow(DraggableFlexTable t, int rownum, Medication m) {
		int col = 0;

		HTML handle = new HTML(this.dragToken);
		t.setWidget(rownum, col++, handle);
		t.getRowDragController().makeDraggable(handle);

		// t.setText(rownum, col++, Integer.toString(rownum)); // No entry number
		t.setHTML(rownum, col++, m.getProvenance());
		t.setHTML(rownum, col++, m.getMedicationName());
		t.setHTML(rownum, col++, m.getDose() + " " + m.getUnits());
		t.setHTML(rownum, col++, m.getInstructions());
		t.setHTML(rownum, col++, m.getStartDateString());
		t.setHTML(rownum, col++, m.getEndDateString());
		t.setHTML(rownum, col++, m.getFormulation());
		t.setText(rownum, col++, "");

		t.getRowFormatter().addStyleName(rownum, "SingleRowDesign");
		t.getRowFormatter().addStyleName(rownum, "FullReconciliation");
		/*
		 * Apply the TableDesign style to each cell individually to get
		 * borders
		 */
		this.applyStyleToAllCellsInRow(rownum, "SingleRowDesign");
		this.applyStyleArrayToRow(rownum, columnStyles);
		
		// Every row is its own target in this table
		return handle;
	}
	
	private HTML renderUnparsedRow(DraggableFlexTable t, int rownum, Medication m) {
		int col = 0;

		HTML handle = new HTML(this.dragToken);
		t.setWidget(rownum, col++, handle);
		t.getRowDragController().makeDraggable(handle);
		
		t.setHTML(rownum, col++, m.getProvenance());
		t.setHTML(rownum, col++, m.getOriginalString());
		
		t.getRowFormatter().addStyleName(rownum, "SingleRowDesign");
		t.getRowFormatter().addStyleName(rownum, "FullReconciliation");
		this.applyStyleToAllCellsInRow(rownum, "SingleRowDesign");
		this.applyStyleArrayToRow(rownum, columnStyles);

		// Merge all cells to the right of the single text row 
		t.getFlexCellFormatter().setColSpan(rownum, col - 1, t.getCellCount(0) - 2);
		
		return handle;
	}
	
	private HTML renderRow(DraggableFlexTable t, int rownum, Medication m) {
		HTML result;
		if (m.isParsed())
			result=renderParsedRow(t, rownum, m);
		else
			result=renderUnparsedRow(t, rownum, m);
		
		t.setTargetRow(rownum, rownum);
		
		return result;
	}
	
	@Override
	public void renderTable() {
		DraggableFlexTable t = this.getAttachedTable();
		List<Consolidation> meds = t.getMedList();
		
		t.removeAllRows();
		t.clearTargetRows();
		
		Map<HTML, Consolidation> rowMapping = t.getRowMapping();
		rowMapping.clear();

		this.renderTableHeadings("TableHeading");
		t.setTargetRow(0, 0);

		for (int i = 0; i < meds.size(); i++) {
			Medication m = meds.get(i).getSelectedMedication();
			HTML handle=renderRow(t, i + 1, m);
			rowMapping.put(handle, meds.get(i));
		}
		
		int rr=t.getRowToRemove();
		while (rr > -1) {
			t.deleteMed(rr);
			Fade my_fade=new Fade(t.getRowFormatter().getElement(rr+1));
			my_fade.addEffectCompletedHandler(new EffectCompletedHandler() {
				public void onEffectCompleted(EffectCompletedEvent evt) {
					ReconciledRenderer.this.bus.fireEvent(new RedrawEvent());
				}
			});
			my_fade.setDuration(TableRenderer.FADE_DURATION);
			my_fade.play();
			 
			rr=t.getRowToRemove();
		}

		return;

	}

	@Override
	public void handleDroppedRow(RowDroppedEvent event) {
		HTML handle = (HTML) event.getSourceTable().getWidget(
				event.getSourceRow(), 0);
		Consolidation cons = event.getSourceTable().getRowMapping().get(handle);
		Medication selected = cons.getSelectedMedication();
		if (selected == null) {
			Window.alert("This should never happen.");
		}
		// Window.alert("Dragged medication:" +
		// selected.getMedicationName()+" "+selected.getDose());
		int targetPosition = event.getDestRow() - 1;

		this.getAttachedTable().insertMed(targetPosition, cons);
		// Find all rows in the original table that contain the medications in
		// this one, and delete them.
		// We will go in reverse order as not to restart the search.
		List<? extends Consolidation> otherList = event.getSourceTable()
				.getMedList();
		
		for (int i = otherList.size() - 1; i >= 0; i--) {
			if (otherList.get(i).same_meds(cons)) {
				//Fade new_fade=new Fade(event.getSourceTable().getRowFormatter().getElement(event.getSourceRow()));
				//new_fade.play();
				
				//otherList.remove(i);
				event.getSourceTable().registerRemovalRequest(i);
			}
		}
		bus.fireEvent(new RedrawEvent());
	}

}
