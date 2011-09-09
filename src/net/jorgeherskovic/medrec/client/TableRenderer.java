package net.jorgeherskovic.medrec.client;

import java.util.List;

import net.jorgeherskovic.medrec.shared.Consolidation;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;

public abstract class TableRenderer {
	private DraggableFlexTable attachedTable;
	private List<? extends Consolidation> medList;
	private String[] headings;

	public TableRenderer(DraggableFlexTable table,
						 String[] headings, 
			List<? extends Consolidation> meds) {
		this.attachedTable = table;
		this.medList = meds;
		this.headings = headings;
	}

	@SuppressWarnings("unchecked")
	public List<Consolidation> getMedList() {
		return (List<Consolidation>) medList;
	}

	public DraggableFlexTable getAttachedTable() {
		return attachedTable;
	}

	public void applyStyleToAllCellsInRow(int rownum, String styleName) {
		CellFormatter cf = attachedTable.getCellFormatter();
		for (int j = 0; j < attachedTable.getCellCount(rownum); j++) {
			cf.setStyleName(rownum, j, styleName);
		}
	}

	public void insertMed(int above_what, Consolidation med) {
		this.getMedList().add(above_what, med);
	}

	public void deleteMed(int which_one) {
		medList.remove(which_one);
	}

	public void renderTableHeadings(String style) {
		HTMLTable.CellFormatter h = attachedTable.getCellFormatter();

		for (int i = 0; i < headings.length; i++) {
			attachedTable.setHTML(0, i, headings[i]);
			h.setStyleName(0, i, style);
		}
	}

	public abstract void renderTable();
}
