package net.jorgeherskovic.medrec.shared;

import java.util.ArrayList;

import net.jorgeherskovic.medrec.client.MedRecJSONData;
import net.jorgeherskovic.medrec.client.event.FinishedLoadingEvent;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public class Lists extends Reconciliation {
	public final ArrayList<String> names;
	public final ArrayList<String> uris;
	public final ArrayList<String> dates;
	private final SimpleEventBus bus;

	public Lists(String jsonURL, SimpleEventBus bus) {
		super();
		this.bus=bus;
		String url = URL.encode(jsonURL);
		this.names = new ArrayList<String>();
		this.uris = new ArrayList<String>();
		this.dates = new ArrayList<String>();
		
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);

		try {
			builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					System.out.println("Couldn't retrieve JSON");
				}

				public void onResponseReceived(Request request,
						Response response) {
					Lists.this.parseData(response.getText());
					Lists.this.bus
							.fireEvent(new FinishedLoadingEvent());
				}
			});
		} catch (com.google.gwt.http.client.RequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void parseData(String text) {
		JSONValue myData = JSONParser.parseStrict(text);
		JSONArray myLists = myData.isArray();
		assert (myLists != null);

		for (int i=0; i < myLists.size(); i++) {
			JSONObject this_list=myLists.get(i).isObject();
			JSONObject metadata=this_list.get("meta").isObject();
			JSONArray meds=this_list.get("meds").isArray();
			
			names.add(metadata.get("name").isString().stringValue());
			uris.add(metadata.get("URL").isString().stringValue());
			dates.add(metadata.get("date").isString().stringValue());

			this.originalLists.add(new String[meds.size()]);
			
			for (int j=0; j < meds.size(); j++) {
				this.originalLists.get(i)[j]=meds.get(j).isString().stringValue();
			}
		}
	}
}
