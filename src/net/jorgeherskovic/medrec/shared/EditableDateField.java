package net.jorgeherskovic.medrec.shared;

import java.util.Date;
import com.google.gwt.i18n.shared.DateTimeFormat;

public class EditableDateField implements EditableField<Date> {
	Date myDate = Medication.nullDate;
	static final DateTimeFormat myDTFormat = DateTimeFormat.getFormat("yyyy.MM.dd");
	static final DateTimeFormat myOutputFormat = DateTimeFormat
			.getFormat(DateTimeFormat.PredefinedFormat.DATE_MEDIUM);


	public EditableDateField() {
		// TODO Auto-generated constructor stub
	}
	
	public EditableDateField(String value) {
		this.setValue(value);
	}

	public EditableDateField(Date value) {
		myDate=value;
	}
	
	@Override
	public Date getValue() {
		// TODO Auto-generated method stub
		return myDate;
	}

	@Override
	public String getStringValue() {
		if (myDate.equals(Medication.nullDate)) {
			return "";
		}
		return myOutputFormat.format(myDate);
	}
	
	@Override
	public void setValue(String value) {
		// TODO Auto-generated method stub
		try {
			myDate=myDTFormat.parse(value);
		} catch (IllegalArgumentException e) {
			myDate= Medication.nullDate;
		}
	}
	
	public void setValue(Date date) {
		myDate=date;
	}

}
