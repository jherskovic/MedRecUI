package net.jorgeherskovic.medrec.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.jorgeherskovic.medrec.shared.Consolidation;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;

final public class DraggableFlexTable extends FlexTable {
	private FlexTableRowDragController RowDragController;
	private List<? extends Consolidation> medList;
	private Map<HTML, Consolidation> rowMapping;
	private LinkedList<Integer> rows_to_remove;
	// Stores row boundaries for the table to drop targets can match visual
	// targets.
	// In other words, for each row, it stores where the drop SHOULD go.
	private ArrayList<Integer> actual_target_rows;

	/**
	 * @return the rowMapping
	 */
	public Map<HTML, Consolidation> getRowMapping() {
		return rowMapping;
	}

	public DraggableFlexTable(FlexTableRowDragController dragController,
			List<? extends Consolidation> medList) {
		this.setRowDragController(dragController);
		this.medList = medList;
		this.rowMapping = new HashMap<HTML, Consolidation>();
		this.rows_to_remove = new LinkedList<Integer>();
		this.actual_target_rows = new ArrayList<Integer>();
	}

	public void associateMedList(List<? extends Consolidation> medList) {
		this.medList = medList;
	}

	public void registerRemovalRequest(int rownum) {
		this.rows_to_remove.add(rownum);
	}

	public int getRowToRemove() {
		if (this.rows_to_remove.isEmpty()) {
			return -1;
		}
		return this.rows_to_remove.remove();
	}

	public void clearTargetRows() {
		this.actual_target_rows.clear();
	}

	public void setTargetRow(int row, int target) {
		while (this.actual_target_rows.size() <= row) {
			this.actual_target_rows.add(new Integer(0));
		}
		this.actual_target_rows.set(row, new Integer(target));
	}

	public boolean isRowRemovable(int rownum) {
		return this.rows_to_remove.contains(new Integer(rownum));
	}

	public FlexTableRowDragController getRowDragController() {
		return RowDragController;
	}

	public void setRowDragController(
			FlexTableRowDragController rowDragController) {
		RowDragController = rowDragController;
	}

	public int getDropTargetRow(int intendedRow) {
		return this.actual_target_rows.get(intendedRow);
	}

	@SuppressWarnings("unchecked")
	public List<Consolidation> getMedList() {
		return (List<Consolidation>) medList;
	}

	public void insertMed(int above_what, Consolidation med) {
		this.getMedList().add(above_what, med);
	}

	public void deleteMed(int which_one) {
		medList.remove(which_one);
	}

	public int getCumulativeOffsetHeight(int upToRow) {
		int sum = 0;
		upToRow = upToRow < this.getRowCount() ? upToRow
				: this.getRowCount() - 1;
		upToRow = this.getDropTargetRow(upToRow);
		for (int i = 0; i <= upToRow; i++) {
			sum += this.getRowFormatter().getElement(i).getOffsetHeight();
		}

		return sum;
	}
}
