package net.jorgeherskovic.medrec.client;

import net.jorgeherskovic.medrec.client.event.FinishedPostingEvent;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;

public class MedRecResponseSender {
	private final SimpleEventBus bus;

	public MedRecResponseSender(String jsonURL, String response, SimpleEventBus bus) {
		super();
		// Get the base JSON array
		String url = URL.encode(jsonURL);
		this.bus = bus;
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
		builder.setHeader("Content-Type", "application/x-www-form-urlencoded");
		try {
			builder.sendRequest("recjson="+response, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					System.out.println("Couldn't post JSON");
				}

				public void onResponseReceived(Request request,
						Response response) {
					MedRecResponseSender.this.bus
							.fireEvent(new FinishedPostingEvent());
				}
			});
		} catch (com.google.gwt.http.client.RequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
