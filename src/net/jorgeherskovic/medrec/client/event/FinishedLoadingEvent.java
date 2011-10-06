package net.jorgeherskovic.medrec.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;

@SuppressWarnings("unused")
public class FinishedLoadingEvent extends GwtEvent<FinishedLoadingEventHandler> {
	
	public static Type<FinishedLoadingEventHandler> TYPE = new Type<FinishedLoadingEventHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<FinishedLoadingEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(FinishedLoadingEventHandler handler) {
		handler.onFinishedLoading(this);
	}

}
