package net.jorgeherskovic.medrec.client.event;

import net.jorgeherskovic.medrec.client.DraggableFlexTable;

import com.google.gwt.event.shared.GwtEvent;

public class RowDroppedEvent extends GwtEvent<RowDroppedEventHandler> {
	public static Type<RowDroppedEventHandler> TYPE = new Type<RowDroppedEventHandler>();

	private DraggableFlexTable sourceTable;
	private int sourceRow;
	private DraggableFlexTable destTable;
	private int destRow;

	public RowDroppedEvent(DraggableFlexTable sourceTable,
			DraggableFlexTable destTable, int sourceRow, int destRow) {
		this.sourceRow = sourceRow;
		this.sourceTable = sourceTable;
		this.destRow = destRow;
		this.destTable = destTable;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<RowDroppedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RowDroppedEventHandler handler) {
		handler.onRowDropped(this);
	}

	/**
	 * @return the sourceTable
	 */
	public DraggableFlexTable getSourceTable() {
		return sourceTable;
	}

	/**
	 * @return the sourceRow
	 */
	public int getSourceRow() {
		return sourceRow;
	}

	/**
	 * @return the destTable
	 */
	public DraggableFlexTable getDestTable() {
		return destTable;
	}

	/**
	 * @return the destRow
	 */
	public int getDestRow() {
		return destRow;
	}

}
