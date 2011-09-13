package net.jorgeherskovic.medrec.shared;

public class Consolidation {
	private Medication[] meds;
	private int selected;
	private double score;
	private String explanation;
	public final int length;
	
	public Consolidation(Medication med1, Medication med2, double score,
			String explanation) {
		super();
		this.meds = new Medication[2];
		this.selected = -1;
		this.meds[0] = med1;
		this.meds[1] = med2;
		this.score = score;
		this.explanation = explanation;
		this.length = this.meds.length;
	}

	/**
	 * @return the med1
	 */
	public Medication getMed1() {
		return meds[0];
	}

	/**
	 * @return the med2
	 */
	public Medication getMed2() {
		return meds[1];
	}

	public Medication getMed(int index) {
		return meds[index];
	}

	public int findMed(Medication med) {
		for (int i = 0; i < meds.length; i++) {
			if (meds[i].equals(med)) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * @return the score
	 */
	public double getScore() {
		return score;
	}

	public Medication getSelectedMedication() {
		if (selected == -1) {
			return null;
		}
		return meds[selected];
	}

	public void setSelection(int index) {
		if (index < 0 || index > meds.length) {
			throw new ArrayIndexOutOfBoundsException(
					"The selected medication does not exist.");
		}

		this.selected = index;
	}

	/**
	 * @return the explanation
	 */
	public String getExplanation() {
		return explanation;
	}
	
	public boolean same_meds(Consolidation other) {
		for (int i=0; i<this.length; i++) {
			boolean match_for_this_row=false;
			
			for (int j=0; j<other.length; j++) {
				if (this.getMed(i).equals(other.getMed(j))) {
					match_for_this_row=true;
					break;
				}
			
			}
			if (!match_for_this_row) {
				return false;
			}
		}
		return true;
	}
}
