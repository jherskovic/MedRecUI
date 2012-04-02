package net.jorgeherskovic.medrec.shared;

public class EditableStringField implements EditableField<String> {
	private String myString="";
	
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return myString;
	}
	
	public String getStringValue() {
		return myString;
	}

	@Override
	public void setValue(String value) {
		// TODO Auto-generated method stub
		myString=value;
	}

	public EditableStringField(String value) {
		myString=value;
	}
	
	public EditableStringField() {
		myString="";
	}
}
