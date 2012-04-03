package net.jorgeherskovic.medrec.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface FinishedPostingEventHandler extends EventHandler {
	void onFinishedPosting(FinishedPostingEvent event);
}
