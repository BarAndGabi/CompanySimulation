package Gabriel_Lempert.model;

import java.io.Serializable;

public class Preference implements Serializable {
	private PreferenceType pType;
	private int hoursChange;
	private final int REGULAR_START_HOUR = 8;
	private int currentStartHour;

	public Preference(PreferenceType pType, int hourChange) {
		this.hoursChange = hourChange;
		this.pType = pType;
		this.currentStartHour = this.REGULAR_START_HOUR;
		this.setStartHour();
	}

	public Preference(PreferenceType pType) {
		this.hoursChange = 0;
		this.pType = pType;
		this.currentStartHour = this.REGULAR_START_HOUR;

	}

	public void setStartHour() {
		int changeTemp = this.hoursChange;
		switch (pType) {
		case EARLY_START:
			while (changeTemp > 0) {
				this.currentStartHour--;
				if (this.currentStartHour == -1)
					this.currentStartHour = 23;
				changeTemp--;
			}
			break;
		case LATE_START:
			while (changeTemp > 0) {
				this.currentStartHour++;
				if (this.currentStartHour == 24)
					this.currentStartHour = 0;
				changeTemp--;
			}
			break;
		case HOME:
			this.currentStartHour = -1;

		}

	}

	public int getStartHour() {
		return this.currentStartHour;
	}

	public PreferenceType getPreferenceType() {
		return this.pType;
	}

	public int getHourChange() {
		return this.hoursChange;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Preference))
			return false;
		Preference temp = (Preference) obj;
		if (temp.getPreferenceType() == this.pType)
			return true;
		else
			return false;
	}

	@Override
	public String toString() {
		return this.pType.name() + ", with: " + this.getHourChange() + " change.";
	}
}
