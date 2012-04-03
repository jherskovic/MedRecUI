/**
 * 
 */
package net.jorgeherskovic.medrec.shared;

import com.google.gwt.i18n.shared.DateTimeFormat;
import java.util.Date;

/**
 * @author jherskovic
 * 
 */
public class Medication {
	private EditableStringField medicationName;
	private EditableStringField dose;
	private EditableStringField units;
	private EditableCompoundField doseAndUnits;
	private EditableStringField formulation;
	private EditableStringField instructions;
	private EditableDateField startDate;
	private EditableDateField endDate;
	private boolean parsed;
	private EditableStringField provenance;
	private String originalString;
	static final Date nullDate = new Date(0);
	
	/**
	 * @return the provenance
	 */
	public String getProvenance() {
		return provenance.getValue();
	}

	/**
	 * @param provenance
	 *            the provenance to set
	 */
	public void setProvenance(String provenance) {
		this.provenance.setValue(provenance);
	}

	public EditableStringField getProvenanceField() {
		return provenance;
	}
	
	/**
	 * @return the medicationName
	 */
	public String getMedicationName() {
		return medicationName.getValue();
	}

	public EditableStringField getMedicationNameField() {
		return medicationName;
	}
	
	/**
	 * @param medicationName
	 *            the medicationName to set
	 */
	public void setMedicationName(String medicationName) {
		this.medicationName.setValue(medicationName);
	}

	/**
	 * @return the dose
	 */
	public String getDose() {
		return dose.getValue();
	}

	/**
	 * @param dose
	 *            the dose to set
	 */
	public void setDose(String dose) {
		this.dose.setValue(toProperCase(dose));
	}

	public EditableStringField getDoseField() {
		return dose;
	}
	
	/**
	 * @return the units
	 */
	public String getUnits() {
		return units.getValue();
	}

	/**
	 * @param units
	 *            the units to set
	 */
	public void setUnits(String units) {
		this.units.setValue(units);
	}

	public EditableStringField getUnitsField() {
		return units;
	}
	
	public EditableCompoundField getDoseAndUnitsField() {
		return doseAndUnits;
	}
	
	public String getDoseAndUnits() {
		return doseAndUnits.getStringValue();
	}
	
	/**
	 * @return the formulation
	 */
	public String getFormulation() {
		return formulation.getValue();
	}

	/**
	 * @param formulation
	 *            the formulation to set
	 */
	public void setFormulation(String formulation) {
		this.formulation.setValue(toProperCase(formulation));
	}
	
	public EditableStringField getFormulationField() {
		return formulation;
	}

	/**
	 * @return the instructions
	 */
	public String getInstructions() {
		return instructions.getValue();
	}

	/**
	 * @param instructions
	 *            the instructions to set
	 */
	public void setInstructions(String instructions) {
		this.instructions.setValue(toProperCase(instructions));
	}

	public EditableStringField getInstructionsField() {
		return instructions;
	}
	
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate.getValue();
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate.setValue(startDate);
	}

	public void setStartDate(String startDate) {
		this.startDate.setValue(startDate);
	}
	
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate.getValue();
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate.setValue(endDate);
	}

	public void setEndDate(String endDate) {
		this.endDate.setValue(endDate);
	}
	
	public String getStartDateString() {
		return startDate.getStringValue();
	}

	public String getEndDateString() {
		return endDate.getStringValue();
	}
	
	public EditableDateField getStartDateField() {
		return startDate;
	}
	
	public EditableDateField getEndDateField() {
		return endDate;
	}
	
	public String getOriginalString() {
		return originalString;
	}

	public void setOriginalString(String originalString) {
		this.originalString = originalString;
	}

	public boolean isParsed() {
		return parsed;
	}

	public void setParsed(boolean parsed) {
		this.parsed = parsed;
	}

	private static String toProperCase(String name) {
		  if (name == null || name.trim().length() == 0) {
		    return name;
		  }

		  String new_name = name.trim().toLowerCase();
		  char[] charArray = new_name.toCharArray();
		  charArray[0] = Character.toUpperCase(charArray[0]);
		  return new String(charArray);
		}
	
	private void setupDoseAndUnits() {
		EditableStringField[] d_u = new EditableStringField[2];
		d_u[0]=this.dose;
		d_u[1]=this.units;
		this.doseAndUnits = new EditableCompoundField(d_u);
	}
	
	public Medication(String medicationName, String dose, String units,
			String formulation, String instructions, String provenance,
			Date startDate, Date endDate) {
		super();
		this.medicationName = new EditableStringField(toProperCase(medicationName));
		this.dose = new EditableStringField(toProperCase(dose));
		this.units = new EditableStringField(toProperCase(units));
		this.formulation = new EditableStringField(toProperCase(formulation));
		this.instructions = new EditableStringField(toProperCase(instructions));
		this.provenance = new EditableStringField(provenance);
		this.startDate = new EditableDateField(startDate);
		this.endDate = new EditableDateField(endDate);
		this.originalString="";
		this.parsed=true;
		
		setupDoseAndUnits();
	}

	public Medication() {
		super();
		this.medicationName = new EditableStringField();
		this.dose = new EditableStringField();
		this.units = new EditableStringField();
		this.formulation = new EditableStringField();
		this.instructions = new EditableStringField();
		this.provenance = new EditableStringField();
		this.startDate = new EditableDateField();
		this.endDate = new EditableDateField();
		this.originalString="";
		this.parsed=false;
		
		setupDoseAndUnits();
	}

	public boolean isEmpty() {
		return medicationName.getStringValue().equals("") & dose.getStringValue().equals("")
				& formulation.getStringValue().equals("") & instructions.getStringValue().equals("");
	}

	/**
	 * @param singleString
	 *            Takes a single pipe-delimited string and breaks it into its
	 *            component fields
	 */
	public Medication(String singleString) {
		String[] tokens = singleString.split("[|]");
		this.medicationName = new EditableStringField(tokens[0]);
		this.dose = new EditableStringField(tokens[1]);
		this.units = new EditableStringField(tokens[2]);
		this.formulation = new EditableStringField(tokens[3]);
		this.instructions = new EditableStringField(tokens[4]);
		this.provenance = new EditableStringField(tokens[5]);
		this.startDate = new EditableDateField(tokens[6]);
		this.endDate = new EditableDateField(tokens[7]);
		this.originalString=singleString;
		this.parsed=true;
		
		setupDoseAndUnits();
	}

	public boolean equals(Medication other) {
		return (other.getMedicationName() == this.getMedicationName()
				&& other.getDose() == this.getDose()
				&& other.getUnits() == this.getUnits()
				&& other.getFormulation() == this.getFormulation() 
				&& other.getInstructions() == this.getInstructions());
	}

	public String toJSON() {
		StringBuffer output=new StringBuffer();
		output.append("{\n\t\"medicationName\": \"");
		output.append(this.getMedicationName());
		output.append("\",\n");
		output.append("\t\"dose\": \"");
		output.append(this.getDose());
		output.append("\",\n");
		output.append("\t\"units\": \"");
		output.append(this.getUnits());
		output.append("\",\n");
		output.append("\t\"formulation\": \"");
		output.append(this.getFormulation());
		output.append("\",\n");
		output.append("\t\"instructions\": \"");
		output.append(this.getInstructions());
		output.append("\",\n");
		output.append("\t\"parsed\": \"");
		if (this.isParsed()) {
			output.append("true\"");
		} else {
			output.append("false\"");
		}
		output.append("\n}\n");
		return output.toString();
	}
}
