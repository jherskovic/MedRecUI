package net.jorgeherskovic.medrec.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class DoneReconcilingEvent extends GwtEvent<DoneReconcilingEventHandler> {

	public static Type<DoneReconcilingEventHandler> TYPE = new Type<DoneReconcilingEventHandler>();
	
	public DoneReconcilingEvent() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<DoneReconcilingEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(DoneReconcilingEventHandler handler) {
		// TODO Auto-generated method stub
		handler.onDoneReconciling(this);
	}

}
