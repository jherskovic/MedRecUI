package net.jorgeherskovic.medrec.shared;

public interface EditableField<T> {
	public T getValue();
	public String getStringValue();
	public void setValue(String value);
}
