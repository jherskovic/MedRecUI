package net.jorgeherskovic.medrec.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;

@SuppressWarnings("unused")
public class RedrawEvent extends GwtEvent<RedrawEventHandler> {

	public static Type<RedrawEventHandler> TYPE = new Type<RedrawEventHandler>();

	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<RedrawEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(RedrawEventHandler handler) {
		handler.onRedraw(this);
	}

}
