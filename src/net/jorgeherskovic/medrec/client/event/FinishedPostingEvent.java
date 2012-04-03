package net.jorgeherskovic.medrec.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;

@SuppressWarnings("unused")
public class FinishedPostingEvent extends GwtEvent<FinishedPostingEventHandler> {
	
	public static Type<FinishedPostingEventHandler> TYPE = new Type<FinishedPostingEventHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<FinishedPostingEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(FinishedPostingEventHandler handler) {
		handler.onFinishedPosting(this);
	}

}
