package net.jorgeherskovic.medrec.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface DoneReconcilingEventHandler extends EventHandler {
	void onDoneReconciling(DoneReconcilingEvent event); 
}
