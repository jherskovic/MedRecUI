package net.jorgeherskovic.medrec.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface FinishedLoadingEventHandler extends EventHandler {
	void onFinishedLoading(FinishedLoadingEvent event);
}
