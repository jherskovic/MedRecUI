package net.jorgeherskovic.medrec.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface RowDroppedEventHandler extends EventHandler {
	void onRowDropped(RowDroppedEvent event);
}
