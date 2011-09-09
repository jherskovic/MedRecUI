package net.jorgeherskovic.medrec.client;

import com.google.gwt.user.client.ui.FlexTable;

final public class DraggableFlexTable extends FlexTable {
	private FlexTableRowDragController RowDragController;
	
	public DraggableFlexTable(FlexTableRowDragController dragController) {
		this.setRowDragController(dragController);
	}

	public FlexTableRowDragController getRowDragController() {
		return RowDragController;
	}

	public void setRowDragController(FlexTableRowDragController rowDragController) {
		RowDragController = rowDragController;
	}

}
