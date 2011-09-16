package net.jorgeherskovic.medrec.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface RedrawEventHandler extends EventHandler {
	void onRedraw(RedrawEvent event);
}
