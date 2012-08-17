package net.jorgeherskovic.medrec.client;
import java.util.ArrayList;
import java.util.Stack;

import net.jorgeherskovic.medrec.client.event.DoneReviewingListsEvent;
import net.jorgeherskovic.medrec.client.event.FinishedLoadingEvent;
import net.jorgeherskovic.medrec.client.event.FinishedLoadingEventHandler;
import net.jorgeherskovic.medrec.shared.Lists;
import net.jorgeherskovic.medrec.shared.Reconciliation;

import org.adamtacy.client.ui.effects.events.EffectCompletedEvent;
import org.adamtacy.client.ui.effects.events.EffectCompletedHandler;
import org.adamtacy.client.ui.effects.impl.Fade;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.VerticalSplitPanel;
import com.google.gwt.user.client.ui.Widget;


public class ChooseLists {
	private final VerticalPanel myWindow;
	private final ArrayList<CheckBox> cbs;
	private final ArrayList<String> uris;
	private final Stack<Integer> selected;
	
	private void RenderTable(FlexTable table, String[] data) {
		table.removeAllRows();
		for (int i=0; i<data.length; i++) {
			HTML h = new HTML();
			h.addStyleName("Form");
			h.setText(data[i]);
			table.setWidget(i, 0, h);
			table.getRowFormatter().addStyleName(i, "SingleRowDesign");
			//table.getRowFormatter().addStyleName(i, "NoReconciliation");
		}
	}
		
	public ChooseLists(RootLayoutPanel rootPanel, final SimpleEventBus bus, final Lists data, final String responseURL) {		
		myWindow=new VerticalPanel();
		cbs=new ArrayList<CheckBox>();
		uris=new ArrayList<String>();
		selected=new Stack<Integer>();
		
		rootPanel.add(myWindow);
		final Button btnNewButton = new Button("New button");
		
		for (int i=0; i<data.getListCount(); i++) {
			DockPanel this_list = new DockPanel();
			//rest.setTopWidget(list1);
			myWindow.add(this_list);
			this_list.setSize("100%", "100%");
			
			final CheckBox rb=new CheckBox();
			cbs.add(rb);
			HorizontalPanel hp=new HorizontalPanel();
			hp.add(rb);
			
			final int this_index=i;
			rb.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					if (rb.getValue()) {
						selected.push(this_index);
					} else {
						selected.remove(selected.indexOf(this_index));
					}
					
					if (selected.size() > 2) {
						int old_one=selected.remove(0);
						cbs.get(old_one).setValue(false);
					}
					
					btnNewButton.setEnabled((selected.size() == 2));
					
					
				}
				
			});
			
			final Label this_label = new Label(data.names.get(i) + " (" + data.dates.get(i) + ")");
			uris.add(data.uris.get(i));
			this_label.setStyleName("big-label");
			hp.add(this_label);
			this_list.add(hp, DockPanel.NORTH);
			
			final FlexTable tblList = new FlexTable();
			tblList.setStyleName("TableDesign");
			this_list.add(tblList, DockPanel.CENTER);
			
		}
		

		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String list1=URL.encode(uris.get(selected.remove(0)));
				String list2=URL.encode(uris.get(selected.remove(0)));
				String reqURL=URL.encode(responseURL + "?" + "list1=" + list1 + "&" + "list2=" + list2);
				
				RequestBuilder builder=new RequestBuilder(RequestBuilder.GET, reqURL);
				try 
				{ builder.sendRequest(null, new RequestCallback() {

					@Override
					public void onResponseReceived(Request request,
							Response response) {
						// Done - redirect to the app proper.
						// The URL comes in the response.
						String newURL=URL.encode(response.getText());
						Window.Location.replace(newURL);
					}

					@Override
					public void onError(Request request, Throwable exception) {
						System.out.println("Couldn't POST list URLs");
					}
					
				});
				} 
				 catch (com.google.gwt.http.client.RequestException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				builder.setHeader("Content-Type", "application/x-www-form-urlencoded");
				Fade f=new Fade(myWindow.getElement());
				//RootPanel.get("insert_results_here").add(new HTML(recRenderer.toJSON()));
				f.addEffectCompletedHandler(new EffectCompletedHandler() {

					@Override
					public void onEffectCompleted(EffectCompletedEvent event) {
						// TODO Auto-generated method stub
						myWindow.removeFromParent();
						bus.fireEvent(new DoneReviewingListsEvent());
					}
				});
				
				f.play();
				
			}
		});
		btnNewButton.setText("Reconcile");
		btnNewButton.setStyleName("doneButton");
		btnNewButton.setEnabled(false);
		myWindow.add(btnNewButton);
		
		for (int i = 0; i<data.getListCount(); i++) {
			DockPanel thisPanel=((DockPanel) myWindow.getWidget(i));
			// Find the center widget
			for (int j=0; j < thisPanel.getWidgetCount(); j++) {
				Widget thisWidget=thisPanel.getWidget(j);
				if (thisPanel.getWidgetDirection(thisWidget) == DockPanel.CENTER) {
					FlexTable thisTable=(FlexTable) thisWidget;
					RenderTable(thisTable, data.getOriginalList(i));
				}
			}
						
		}
		}

}
