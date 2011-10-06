package net.jorgeherskovic.medrec.client;

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
		this.rows_to_remove=new LinkedList<Integer>();
	}

	public void associateMedList(List<? extends Consolidation> medList) {
		this.medList=medList;
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

}
