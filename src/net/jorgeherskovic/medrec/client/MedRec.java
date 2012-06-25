package net.jorgeherskovic.medrec.client;

import org.adamtacy.client.ui.effects.events.EffectCompletedEvent;
import org.adamtacy.client.ui.effects.events.EffectCompletedHandler;
import org.adamtacy.client.ui.effects.impl.Fade;

import net.jorgeherskovic.medrec.client.event.DoneReviewingListsEvent;
import net.jorgeherskovic.medrec.client.event.DoneReviewingListsEventHandler;
import net.jorgeherskovic.medrec.client.event.FinishedLoadingEvent;
import net.jorgeherskovic.medrec.client.event.FinishedPostingEvent;
import net.jorgeherskovic.medrec.client.event.FinishedPostingEventHandler;
import net.jorgeherskovic.medrec.client.event.RedrawEvent;
import net.jorgeherskovic.medrec.client.event.FinishedLoadingEventHandler;
import net.jorgeherskovic.medrec.shared.Reconciliation;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MedRec implements EntryPoint {

	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	/**
	 * This is the entry point method.
	 */
	
	private Reconciliation myData;
	private final SimpleEventBus bus = new SimpleEventBus();

	public void onModuleLoad() {
		final String JSON_URL;
		final String RESPONSE_URL;
		
		//private static final String JSON_URL = "sample.json";
		if (Window.Location.getParameterMap().containsKey("json_src"))
		{
			JSON_URL=Window.Location.getParameter("json_src");
		} else {
			JSON_URL = "sample.json";	
		};

		if (Window.Location.getParameterMap().containsKey("response"))
		{
			RESPONSE_URL=Window.Location.getParameter("response");
		} else
		{
			RESPONSE_URL="fake_response_url";
		}
		
		final RootLayoutPanel rootPanel = RootLayoutPanel.get();

		final String[] consolidatedHeadings = new String[] { "&nbsp;",
				"Origin", "Medication", "Dosage", "Sig", "Start", "End",
				"Form", "Relation" };
		final String[] reconciledHeadings = new String[] { "&nbsp;", "Origin",
				"Medication", "Dosage", "Sig", "Start", "End", "Form",
				"Alerts" };

		rootPanel.getElement().getStyle().setPosition(Position.RELATIVE); // Fix required for drag & drop
		
		final AbsolutePanel absolutePanel = new AbsolutePanel();
		
		// Need to use absolute pixel sizes at creation time because we create these widgets "in the dark", so to speak
		absolutePanel.setPixelSize(Window.getClientWidth(), Window.getClientHeight()-150);
		
		//rootPanel.add(absolutePanel);
		FlexTableRowDragController row_dc = new FlexTableRowDragController(
				absolutePanel);

		final SplitLayoutPanel rest = new SplitLayoutPanel();
		rest.setPixelSize(absolutePanel.getOffsetWidth(), absolutePanel.getOffsetHeight());
		//rest.setSplitPosition("557px");

		//final SplitLayoutPanel rest_of_rest = new SplitLayoutPanel();
		//rest_of_rest.setSize("800px", "557px");
		//final AbsolutePanel consolidatedPanel = new AbsolutePanel();
		// absolutePanel.add(consolidatedPanel, 0, 58);
		//consolidatedPanel.setSize("800px", "252px");
		//rest_of_rest.addNorth(consolidatedPanel, 252);
		//rest.addNorth(rest_of_rest, 557);

		final DockLayoutPanel upperDockPanel = new DockLayoutPanel(Unit.PX);
		//dockPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		rest.addNorth(upperDockPanel, 252);
		//upperDockPanel.setSize("800px", "252px");

		Label lblTopPanel = new Label("Unreconciled Record");
		lblTopPanel.setStyleName("big-label");
		lblTopPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		upperDockPanel.addNorth(lblTopPanel, 30);

		final DraggableFlexTable consolidatedTable = new DraggableFlexTable(
				row_dc, null);
		consolidatedTable.setStyleName("TableDesign");
		upperDockPanel.add(consolidatedTable);
		FlexTableRowDropController ct_dc = new FlexTableRowDropController(
				consolidatedTable, this.bus);
		@SuppressWarnings("unused")
		final ConsolidatedRenderer conRenderer = new ConsolidatedRenderer(
				consolidatedTable, consolidatedHeadings, bus);

		final DockLayoutPanel bottomPanel = new DockLayoutPanel(Unit.PX);
		//bottomPanel.setSize("800px", "30px");
		final Button btnDone = new Button("Done");
		btnDone.setStyleName("doneButton");
		btnDone.setSize("53px", "23px");
		// spacer
		AbsolutePanel spacer = new AbsolutePanel();
		spacer.setSize("80px", "23px");
		bottomPanel.addEast(spacer, 80);
		bottomPanel.addEast(btnDone, 53);
		//bottomPanel.add(new Label("PUF!"));
		//rest.addSouth(bottomPanel, 30);

		//final AbsolutePanel reconciledPanel = new AbsolutePanel();
		//reconciledPanel.setSize("800px", "252px");
		//rest_of_rest.add(reconciledPanel);

		final DockLayoutPanel lowerDockPanel = new DockLayoutPanel(Unit.PX);
		//dockPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		//lowerDockPanel.setSize("800px", "252px");

		Label lblBottomPanel = new Label("Reconciled Record");
		lblBottomPanel.setStyleName("big-label");
		lblBottomPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lowerDockPanel.addNorth(lblBottomPanel, 30);

		final DraggableFlexTable reconciledTable = new DraggableFlexTable(
				row_dc, null);
		reconciledTable.setStyleName("TableDesign");
		
		rest.addSouth(bottomPanel, 23);
		lowerDockPanel.add(reconciledTable);

		rest.add(lowerDockPanel);
		absolutePanel.add(rest);
		
		FlexTableRowDropController rc_dc = new FlexTableRowDropController(
				reconciledTable, bus);
		
		final ReconciledRenderer recRenderer = new ReconciledRenderer(
				reconciledTable, reconciledHeadings, bus);

		/* Resize behavior */
		final UIResizer resizer=new UIResizer() {
			public void resizeUI(int width, int height) {
				String new_width=width+"px";
				
				//rootPanel.setHeight(height+"px");
				//rootPanel.setWidth(new_width);
				rootPanel.setPixelSize(width, height);
				//absolutePanel.setHeight(height+"px");
				//absolutePanel.setWidth(new_width);
				absolutePanel.setPixelSize(width, height);
				rest.setPixelSize(width, height);
				
				//rest.setHeight((height)+"px");
				//rest_of_rest.setWidth(new_width);
				//rest_of_rest.setHeight((height-44)+"px");
				//consolidatedPanel.setWidth(new_width);
				upperDockPanel.setWidth(new_width);
				lowerDockPanel.setWidth(new_width);
				//bottomPanel.setWidth(new_width);
				//reconciledPanel.setWidth(new_width);
				//btnDone.removeFromParent();
				//bottomPanel.addWest(btnDone, width-83, 0);
			}
			
		};
		
		ResizeHandler rh=new ResizeHandler() {
			public void onResize(ResizeEvent event) {
				int height=event.getHeight() - 150; // Cheap trick
				int width=event.getWidth();
				
				resizer.resizeUI(width, height);
			}
		};
		Window.addResizeHandler(rh);

		/* Instantiate drop controllers */

		/* Connect the drop controllers to the drag controller */
		row_dc.registerDropController(ct_dc);
		row_dc.registerDropController(rc_dc);

		// Adjust sizes
		resizer.resizeUI(Window.getClientWidth(), Window.getClientHeight());
		
		bus.addHandler(FinishedLoadingEvent.TYPE,
				new FinishedLoadingEventHandler() {

					public void onFinishedLoading(FinishedLoadingEvent event) {
						reconciledTable.associateMedList(myData
								.getReconciledMeds());
						consolidatedTable.associateMedList(myData
								.getConsolidatedMeds());

						RootPanel.get("LoadingLabel").setVisible(false);

						bus.fireEvent(new RedrawEvent());
					}

				});
		
		/* Read data */
		this.myData = new MedRecJSONData(JSON_URL, bus);
		//this.myData = new SampleData();
		//this.bus.fireEvent(new FinishedLoadingEvent());

		btnDone.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				//rootPanel.setVisible(false);
				Fade f=new Fade(rootPanel.getElement());
				new MedRecResponseSender(RESPONSE_URL, recRenderer.toJSON(), bus);
				//RootPanel.get("insert_results_here").add(new HTML(recRenderer.toJSON()));
				f.play();
			}
		});
		
		bus.addHandler(FinishedPostingEvent.TYPE, new FinishedPostingEventHandler() {
			@Override
			public void onFinishedPosting(FinishedPostingEvent event) {
				RootPanel.get("insert_results_here").add(new Label("Done."));
			}
		});

		bus.addHandler(DoneReviewingListsEvent.TYPE, new DoneReviewingListsEventHandler() {
			@Override
			public void onDoneReviewingLists(DoneReviewingListsEvent event) {
				rootPanel.add(absolutePanel);
			}
			
		});
		
		final ReviewLists reviewListsForm=new ReviewLists(rootPanel, bus, myData);
	}
}
