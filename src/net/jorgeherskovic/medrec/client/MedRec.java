package net.jorgeherskovic.medrec.client;

import net.jorgeherskovic.medrec.shared.Consolidation;
import net.jorgeherskovic.medrec.shared.Medication;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DragEndEvent;
import com.google.gwt.event.dom.client.DragEndHandler;
import com.google.gwt.event.dom.client.DragEvent;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.FlexTable;

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
		lblConsolidatedRecord
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		dockPanel.add(lblConsolidatedRecord, DockPanel.NORTH);

		DraggableFlexTable consolidatedTable = new DraggableFlexTable(row_dc);
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
		lblReconciledRecord
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		dockPanel_1.add(lblReconciledRecord, DockPanel.NORTH);

		DraggableFlexTable reconciledTable = new DraggableFlexTable(row_dc);
		reconciledTable.setStyleName("TableDesign");
		dockPanel_1.add(reconciledTable, DockPanel.CENTER);

		/* Instantiate drop controllers */
		FlexTableRowDropController ct_dc = new FlexTableRowDropController(
				consolidatedTable);
		FlexTableRowDropController rc_dc = new FlexTableRowDropController(
				reconciledTable);

		/* Connect the drop controllers to the drag controller */
		row_dc.registerDropController(ct_dc);
		row_dc.registerDropController(rc_dc);

		Button btnDone = new Button("Done");
		absolutePanel.add(btnDone, 737, 570);
		btnDone.setSize("53px", "30px");

		setTableHeadings(consolidatedTable, new String[] { "", "Entry",
				"Origin", "Medication", "Dosage", "Frequency", "Start Date",
				"End Date", "Form", "Relation" });
		setTableHeadings(reconciledTable, new String[] { "", "Entry", "Origin",
				"Medication", "Dosage", "Frequency", "Start Date", "End Date",
				"Form", "Alerts" });

		/* Read data */
		SampleData myData = new SampleData();

		populateConsolidatedTable(consolidatedTable, myData.consolidatedMeds);
		populateReconciledTable(reconciledTable, myData.reconciledMeds);

		final InlineLabel nlnlblDragging = new InlineLabel("");
		nlnlblDragging.setVisible(true);
		absolutePanel.add(nlnlblDragging, 0, 574);

	}

	public void setTableHeadings(HTMLTable t, String[] headings) {
		HTMLTable.CellFormatter h = t.getCellFormatter();

		for (int i = 0; i < headings.length; i++) {
			t.setHTML(0, i, headings[i]);
			h.setStyleName(0, i, "TableHeading");
		}
	}

	public void populateConsolidatedTable(DraggableFlexTable t,
			Consolidation[] cons) {
		HTMLTable.CellFormatter cf = t.getCellFormatter();
		int currentRow = 1;

		for (int i = 0; i < cons.length; i++) {
			Medication m1 = cons[i].getMed1();
			Medication m2 = cons[i].getMed2();
			String lineSeparator;
			int col = 0;

			/* Create an HTML widget and make it draggable */

			HTML handle = new HTML();
			handle.setHTML("&nbsp;&nbsp;≣&nbsp;&nbsp;");

			t.setWidget(currentRow, col++, handle);
			t.getRowDragController().makeDraggable(handle);

			t.setText(currentRow, col++, Integer.toString(i + 1));
			t.setHTML(currentRow, col++, m1.getProvenance());

			t.setHTML(currentRow, col++, m1.getMedicationName());
			t.setHTML(currentRow, col++, m1.getDose() + " " + m1.getUnits());
			t.setHTML(currentRow, col++, m1.getInstructions());
			t.setHTML(currentRow, col++, m1.getStartDateString());
			t.setHTML(currentRow, col++, m1.getEndDateString());
			t.setHTML(currentRow, col++, m1.getFormulation());
			t.setText(currentRow, col++, cons[i].getExplanation());

			String thisStyle = "NoReconciliation";
			if (cons[i].getScore() > 0.1) {
				thisStyle = "PartialReconciliation";
			}
			if (cons[i].getScore() > 0.99) {
				thisStyle = "FullReconciliation";
			}
			t.getRowFormatter().addStyleName(currentRow, "TableDesign");
			t.getRowFormatter().addStyleName(currentRow, thisStyle);

			/*
			 * Apply the TableDesign style to each cell individually to get
			 * borders
			 */
			int numCells = t.getCellCount(currentRow);
			for (int j = 0; j < numCells; j++) {
				cf.setStyleName(currentRow, j, "TableDesign");
			}
			currentRow += 1;

			if (!m2.isEmpty()) {
				col = 0;
				HTML second_handle = new HTML();
				second_handle.setHTML("&nbsp;&nbsp;≣&nbsp;&nbsp;");

				t.setWidget(currentRow, col++, second_handle);
				t.getRowDragController().makeDraggable(second_handle);

				t.setText(currentRow, col++, Integer.toString(i + 1));
				t.setHTML(currentRow, col++, m2.getProvenance());

				t.setHTML(currentRow, col++, m2.getMedicationName());
				t.setHTML(currentRow, col++, m2.getDose() + " " + m2.getUnits());
				t.setHTML(currentRow, col++, m2.getInstructions());
				t.setHTML(currentRow, col++, m2.getStartDateString());
				t.setHTML(currentRow, col++, m2.getEndDateString());
				t.setHTML(currentRow, col++, m2.getFormulation());
				t.setText(currentRow, col++, cons[i].getExplanation());

				t.getRowFormatter().addStyleName(currentRow, "TableDesign");
				t.getRowFormatter().addStyleName(currentRow, thisStyle);

				/*
				 * Apply the TableDesign style to each cell individually to get
				 * borders
				 */
				for (int j = 0; j < numCells; j++) {
					cf.setStyleName(currentRow, j, "TableDesign");
				}
				currentRow += 1;

			}
		}

		return;
	}

	public void populateReconciledTable(DraggableFlexTable t, Medication[] meds) {
		HTMLTable.CellFormatter cf = t.getCellFormatter();

		for (int i = 0; i < meds.length; i++) {
			Medication m = meds[i];
			int col = 0;

			HTML handle = new HTML("&nbsp;&nbsp;≣&nbsp;&nbsp;");
			t.setWidget(i + 1, col++, handle);
			t.getRowDragController().makeDraggable(handle);

			t.setText(i + 1, col++, Integer.toString(i + 1));
			t.setHTML(i + 1, col++, m.getProvenance());
			t.setHTML(i + 1, col++, m.getMedicationName());
			t.setHTML(i + 1, col++, m.getDose() + " " + m.getUnits());
			t.setHTML(i + 1, col++, m.getInstructions());
			t.setHTML(i + 1, col++, m.getStartDateString());
			t.setHTML(i + 1, col++, m.getEndDateString());
			t.setHTML(i + 1, col++, m.getFormulation());
			t.setText(i + 1, col++, "");

			String thisStyle = "FullReconciliation";
			t.getRowFormatter().addStyleName(i + 1, "TableDesign");
			t.getRowFormatter().addStyleName(i + 1, thisStyle);

			/*
			 * Apply the TableDesign style to each cell individually to get
			 * borders
			 */
			int numCells = t.getCellCount(i + 1);
			for (int j = 0; j < numCells; j++) {
				cf.setStyleName(i + 1, j, "TableDesign");
			}
		}

		return;
	}
}
