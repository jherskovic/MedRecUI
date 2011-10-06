/**
 * 
 */
package net.jorgeherskovic.medrec.shared;

import com.google.gwt.i18n.client.DateTimeFormat;
import java.util.Date;

/**
 * @author jherskovic
 * 
 */
public class Medication {
	private String medicationName;
	private String dose;
	private String units;
	private String formulation;
	private String instructions;
	private Date startDate;
	private Date endDate;
	private String provenance;
	static Date nullDate = new Date(0);
	static DateTimeFormat myDTFormat = DateTimeFormat.getFormat("yyyy.MM.dd");
	static DateTimeFormat myOutputFormat = DateTimeFormat
			.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT);

	/**
	 * @return the provenance
	 */
	public String getProvenance() {
		return provenance;
	}

	/**
	 * @param provenance
	 *            the provenance to set
	 */
	public void setProvenance(String provenance) {
		this.provenance = toProperCase(provenance);
	}

	/**
	 * @return the medicationName
	 */
	public String getMedicationName() {
		return medicationName;
	}

	/**
	 * @param medicationName
	 *            the medicationName to set
	 */
	public void setMedicationName(String medicationName) {
		this.medicationName = toProperCase(medicationName);
	}

	/**
	 * @return the dose
	 */
	public String getDose() {
		return dose;
	}

	/**
	 * @param dose
	 *            the dose to set
	 */
	public void setDose(String dose) {
		this.dose = toProperCase(dose);
	}

	/**
	 * @return the units
	 */
	public String getUnits() {
		return units;
	}

	/**
	 * @param units
	 *            the units to set
	 */
	public void setUnits(String units) {
		this.units = units;
	}

	/**
	 * @return the formulation
	 */
	public String getFormulation() {
		return formulation;
	}

	/**
	 * @param formulation
	 *            the formulation to set
	 */
	public void setFormulation(String formulation) {
		this.formulation = toProperCase(formulation);
	}

	/**
	 * @return the instructions
	 */
	public String getInstructions() {
		return instructions;
	}

	/**
	 * @param instructions
	 *            the instructions to set
	 */
	public void setInstructions(String instructions) {
		this.instructions = toProperCase(instructions);
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStartDateString() {
		if (startDate.equals(Medication.nullDate)) {
			return "";
		}

		return myOutputFormat.format(startDate);
	}

	public String getEndDateString() {
		if (endDate.equals(Medication.nullDate)) {
			return "";
		}

		return myOutputFormat.format(endDate);
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
	
	public Medication(String medicationName, String dose, String units,
			String formulation, String instructions, String provenance,
			Date startDate, Date endDate) {
		super();
		this.medicationName = toProperCase(medicationName);
		this.dose = toProperCase(dose);
		this.units = toProperCase(units);
		this.formulation = toProperCase(formulation);
		this.instructions = toProperCase(instructions);
		this.provenance = toProperCase(provenance);
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Medication() {
		super();
		this.medicationName = "";
		this.dose = "";
		this.units = "";
		this.formulation = "";
		this.instructions = "";
		this.provenance = "";
		this.startDate = Medication.nullDate;
		this.endDate = Medication.nullDate;
	}

	public boolean isEmpty() {
		return medicationName.equals("") & dose.equals("")
				& formulation.equals("") & instructions.equals("");
	}

	/**
	 * @param singleString
	 *            Takes a single pipe-delimited string and breaks it into its
	 *            component fields
	 */
	public Medication(String singleString) {
		String[] tokens = singleString.split("[|]");
		this.medicationName = tokens[0];
		this.dose = tokens[1];
		this.units = tokens[2];
		this.formulation = tokens[3];
		this.instructions = tokens[4];
		this.provenance = tokens[5];
		try {
			this.startDate = myDTFormat.parse(tokens[6]);
		} catch (Exception e) {
			this.startDate = Medication.nullDate;
		}
		try {
			this.endDate = myDTFormat.parse(tokens[7]);
		} catch (Exception e) {
			this.endDate = Medication.nullDate;
		}
	}

	public boolean equals(Medication other) {
		return (other.getMedicationName() == this.getMedicationName()
				&& other.getDose() == this.getDose()
				&& other.getUnits() == this.getUnits()
				&& other.getFormulation() == this.getFormulation() 
				&& other.getInstructions() == this.getInstructions());
	}

}
