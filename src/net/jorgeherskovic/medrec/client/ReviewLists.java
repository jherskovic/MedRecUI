package net.jorgeherskovic.medrec.client;

import net.jorgeherskovic.medrec.client.event.DoneReviewingListsEvent;
import net.jorgeherskovic.medrec.client.event.FinishedLoadingEvent;
import net.jorgeherskovic.medrec.client.event.FinishedLoadingEventHandler;
import net.jorgeherskovic.medrec.shared.Reconciliation;

import org.adamtacy.client.ui.effects.events.EffectCompletedEvent;
import org.adamtacy.client.ui.effects.events.EffectCompletedHandler;
import org.adamtacy.client.ui.effects.impl.Fade;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalSplitPanel;

public class ReviewLists {
	/**
	 * @wbp.parser.entryPoint
	 */
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
	
	public ReviewLists(RootPanel panelToAttachTo, final SimpleEventBus bus, final Reconciliation data) {
		final AbsolutePanel myWindow=new AbsolutePanel();
		myWindow.setSize("800px", "600px");
		panelToAttachTo.add(myWindow);
		
		VerticalSplitPanel allPanel = new VerticalSplitPanel();
		allPanel.setSplitPosition("557px");
		myWindow.add(allPanel, 0, 0);
		allPanel.setSize("800px", "600px");
		
		//VerticalSplitPanel rest = new VerticalSplitPanel();
		DockPanel rest = new DockPanel();
		//rest.setSplitPosition("50%");
		allPanel.setTopWidget(rest);
		//rest.setSize("100%", "100%");
		
		DockPanel list1 = new DockPanel();
		//rest.setTopWidget(list1);
		rest.add(list1, DockPanel.NORTH);
		list1.setSize("100%", "100%");
		
		Label lblList1 = new Label("List 1");
		lblList1.setStyleName("big-label");
		list1.add(lblList1, DockPanel.NORTH);
		
		final FlexTable tblList1 = new FlexTable();
		tblList1.setStyleName("TableDesign");
		list1.add(tblList1, DockPanel.CENTER);
		
		DockPanel list2 = new DockPanel();
		//rest.setBottomWidget(list2);
		rest.add(list2, DockPanel.SOUTH);
		list2.setSize("100%", "100%");
		
		Label lblList2 = new Label("List 2");
		lblList2.setStyleName("big-label");
		list2.add(lblList2, DockPanel.NORTH);
		
		final FlexTable tblList2 = new FlexTable();
		tblList2.setStyleName("TableDesign");
		list2.add(tblList2, DockPanel.CENTER);
		
		AbsolutePanel bottomPanel = new AbsolutePanel();
		allPanel.setBottomWidget(bottomPanel);
		bottomPanel.setSize("800px", "30px");
		
		Button btnNewButton = new Button("New button");
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
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
		bottomPanel.add(btnNewButton, 688, 0);
		
		bus.addHandler(FinishedLoadingEvent.TYPE, new FinishedLoadingEventHandler() {
			
			@Override
			public void onFinishedLoading(FinishedLoadingEvent event) {
				// TODO Auto-generated method stub
				ReviewLists.this.RenderTable(tblList1, data.getOriginalList(0));
				ReviewLists.this.RenderTable(tblList2, data.getOriginalList(1));
			}
		});
		}
}
