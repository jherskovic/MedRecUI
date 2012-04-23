package net.jorgeherskovic.medrec.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;

public class DoneReviewingListsEvent extends
		GwtEvent<DoneReviewingListsEventHandler> {
	public static Type<DoneReviewingListsEventHandler> TYPE = new Type<DoneReviewingListsEventHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<DoneReviewingListsEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DoneReviewingListsEventHandler handler) {
		handler.onDoneReviewingLists(this);
	}

}
