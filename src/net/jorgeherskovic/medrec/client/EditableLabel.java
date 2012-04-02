/**
 * 
 */
package net.jorgeherskovic.medrec.client;

import net.jorgeherskovic.medrec.shared.EditableField;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author jherskovic
 * @see http://thezukunft.com/2010/02/05/an-editable-label-for-gwt-with-uibinder-and-eventhandlers/ 
 * Based on the work by Jonas Huckestein
 */
public class EditableLabel extends Composite implements HasValueChangeHandlers<String> {

	private static EditableLabelUiBinder uiBinder = GWT
			.create(EditableLabelUiBinder.class);

	private EditableField<?> boundField;
	
	interface EditableLabelUiBinder extends UiBinder<Widget, EditableLabel> {
	}


	@UiField
	protected Label editLabel;

	@UiField
	protected DeckPanel deckPanel;

	@UiField
	protected TextArea editBox;

	@UiField
	protected FocusPanel focusPanel;

	/**
	 * Because this class has a default constructor, it can
	 * be used as a binder template. In other words, it can be used in other
	 * *.ui.xml files as follows:
	 * <ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder"
	 *   xmlns:g="urn:import:**user's package**">
	 *  <g:**UserClassName**>Hello!</g:**UserClassName>
	 * </ui:UiBinder>
	 * Note that depending on the widget that is used, it may be necessary to
	 * implement HasHTML instead of HasText.
	 */
	public EditableLabel(EditableField<?> f) {
		initWidget(uiBinder.createAndBindUi(this));
		deckPanel.showWidget(0);

		boundField=f;
		
		focusPanel.addFocusHandler(new FocusHandler() {
			//@Override
			public void onFocus(FocusEvent event) {
				switchToEdit();
			}
		});

		editLabel.addClickHandler(new ClickHandler() {
			//@Override
			public void onClick(ClickEvent event) {
				switchToEdit();
			}
		});

		editBox.addBlurHandler(new BlurHandler() {
			//@Override
			public void onBlur(BlurEvent event) {
				switchToLabel();
			}
		});

		editBox.addKeyPressHandler(new KeyPressHandler() {
			//@Override
			public void onKeyPress(KeyPressEvent event) {

				if (event.getCharCode() == KeyCodes.KEY_ENTER) {
					switchToLabel();
				}
				else if (event.getCharCode() == KeyCodes.KEY_ESCAPE) {
					editBox.setText(editLabel.getText()); // reset to the original value
				}
			}
		});
		
	}

	public EditableLabel(String lbl, EditableField<?> f) {
		this(f);
		this.setValue(lbl);
	}

	public void switchToEdit() {
		if(deckPanel.getVisibleWidget() == 1) return;
		editBox.setText(getValue());
		deckPanel.showWidget(1);
		editBox.setFocus(true);
	}

	public void switchToLabel() {
		if(deckPanel.getVisibleWidget() == 0) return;
		setValue(editBox.getText(), true); // fires events, too
		deckPanel.showWidget(0);
	}

	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<String> handler) {
		return addHandler(handler, ValueChangeEvent.getType());
	}

	public String getValue() {
		return editLabel.getText();
	}

	public void setValue(String value) {
		editLabel.setText(value);
		boundField.setValue(value);
		editBox.setText(value);
	}

	public void setValue(String value, boolean fireEvents) {
		if (fireEvents) ValueChangeEvent.fireIfNotEqual(this, getValue(), value);
		setValue(value);
	}


}
