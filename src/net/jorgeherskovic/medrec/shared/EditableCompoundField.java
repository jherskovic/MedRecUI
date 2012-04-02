package net.jorgeherskovic.medrec.shared;
import net.jorgeherskovic.medrec.shared.EditableField;


public class EditableCompoundField implements EditableField<String> {
	EditableField<?>[] sourceFields;
	
	public EditableCompoundField() {
		// TODO Auto-generated constructor stub
		sourceFields = new EditableField<?>[1];
	}

	public EditableCompoundField(EditableField<?>[] fields) {
		sourceFields = fields;
	}
	
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		StringBuffer result=new StringBuffer("");
		for (int i=0; i<sourceFields.length; i++) {
			result.append(sourceFields[i].getStringValue());
			result.append(" ");
		}
		return result.toString().trim();
	}

	@Override
	public String getStringValue() {
		// TODO Auto-generated method stub
		return this.getValue();
	}

	@Override
	public void setValue(String value) {
		// TODO Auto-generated method stub
		String[] parts=value.split(" ");
		for (int i=0;i<parts.length; i++) {
			sourceFields[i].setValue(parts[i]);
		}
	}

}
