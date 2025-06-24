package com.travelapp.itinerary_service.entities;

import java.util.List;
import java.util.Objects;

public class ItineraryDay {
	private int dayNumber;
	private String description;
	private List<String> activities;
	private String title;

	public int getDayNumber() {
		return dayNumber;
	}

	public void setDayNumber(int dayNumber) {
		this.dayNumber = dayNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getActivities() {
		return activities;
	}

	public void setActivities(List<String> activities) {
		this.activities = activities;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public int hashCode() {
		return Objects.hash(activities, dayNumber, description, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItineraryDay other = (ItineraryDay) obj;
		return Objects.equals(activities, other.activities) && dayNumber == other.dayNumber
				&& Objects.equals(description, other.description) && Objects.equals(title, other.title);
	}
}
