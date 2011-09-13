package net.jorgeherskovic.medrec.client;

import net.jorgeherskovic.medrec.client.event.RedrawEvent;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

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

	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get();
		rootPanel.setSize("800px", "600px");

		/* Read data */
		SampleData myData = new SampleData();

		SimpleEventBus bus = new SimpleEventBus();

		AbsolutePanel absolutePanel = new AbsolutePanel();
		rootPanel.add(absolutePanel, 0, 0);
		absolutePanel.setSize("800px", "600px");

		FlexTableRowDragController row_dc = new FlexTableRowDragController(
				absolutePanel);

		AbsolutePanel absolutePanel_1 = new AbsolutePanel();
		absolutePanel_1.setStyleName("provenance");
		absolutePanel.add(absolutePanel_1, 0, 0);
		absolutePanel_1.setSize("800px", "52px");

		Label lblNewLabel = new Label("List 1 comes from");
		absolutePanel_1.add(lblNewLabel, 10, 0);

		TextBox list1origin = new TextBox();
		absolutePanel_1.add(list1origin, 10, 18);
		list1origin.setSize("291px", "18px");

		Label lblListComes = new Label("List 2 comes from");
		absolutePanel_1.add(lblListComes, 489, 0);
		lblListComes.setSize("105px", "18px");

		TextBox list2origin = new TextBox();
		absolutePanel_1.add(list2origin, 489, 18);
		list2origin.setSize("291px", "18px");

		AbsolutePanel consolidatedPanel = new AbsolutePanel();
		absolutePanel.add(consolidatedPanel, 0, 58);
		consolidatedPanel.setSize("800px", "252px");

		DockPanel dockPanel = new DockPanel();
		dockPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		consolidatedPanel.add(dockPanel, 0, 0);
		dockPanel.setSize("800px", "252px");

		Label lblConsolidatedRecord = new Label("Consolidated Record");
		lblConsolidatedRecord.setStyleName("big-label");
		lblConsolidatedRecord
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		dockPanel.add(lblConsolidatedRecord, DockPanel.NORTH);

		DraggableFlexTable consolidatedTable = new DraggableFlexTable(row_dc,
				myData.consolidatedMeds);
		consolidatedTable.setStyleName("TableDesign");
		dockPanel.add(consolidatedTable, DockPanel.CENTER);

		AbsolutePanel reconciledPanel = new AbsolutePanel();
		absolutePanel.add(reconciledPanel, 0, 316);
		reconciledPanel.setSize("800px", "252px");

		DockPanel dockPanel_1 = new DockPanel();
		dockPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		reconciledPanel.add(dockPanel_1, 0, 0);
		dockPanel_1.setSize("800px", "252px");

		Label lblReconciledRecord = new Label("Reconciled Record");
		lblReconciledRecord.setStyleName("big-label");
		lblReconciledRecord
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		dockPanel_1.add(lblReconciledRecord, DockPanel.NORTH);

		DraggableFlexTable reconciledTable = new DraggableFlexTable(row_dc,
				myData.reconciledMeds);
		reconciledTable.setStyleName("TableDesign");
		dockPanel_1.add(reconciledTable, DockPanel.CENTER);

		/* Instantiate drop controllers */
		FlexTableRowDropController ct_dc = new FlexTableRowDropController(
				consolidatedTable, bus);
		FlexTableRowDropController rc_dc = new FlexTableRowDropController(
				reconciledTable, bus);

		/* Connect the drop controllers to the drag controller */
		row_dc.registerDropController(ct_dc);
		row_dc.registerDropController(rc_dc);

		Button btnDone = new Button("Done");
		absolutePanel.add(btnDone, 737, 570);
		btnDone.setSize("53px", "30px");

		String[] consolidatedHeadings = new String[] { "", "#", "Origin",
				"Medication", "Dosage", "Freq.", "Start<br>Date", "End<br>Date",
				"Form", "Relation" };
		String[] reconciledHeadings = new String[] { "", "#", "Origin",
				"Medication", "Dosage", "Freq.", "Start<br>Date", "End<br>Date",
				"Form", "Alerts" };

		@SuppressWarnings("unused")
		ReconciledRenderer recRenderer = new ReconciledRenderer(
				reconciledTable, reconciledHeadings, bus);
		@SuppressWarnings("unused")
		ConsolidatedRenderer conRenderer = new ConsolidatedRenderer(
				consolidatedTable, consolidatedHeadings, bus);

		// Start by drawing the initial tables
		bus.fireEvent(new RedrawEvent()); 
	}

}
