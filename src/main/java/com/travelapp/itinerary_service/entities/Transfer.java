package com.travelapp.itinerary_service.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Transfer {
	private String source;
	private String destination;
	private LocalDate date;
	private LocalTime time;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, destination, source, time);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transfer other = (Transfer) obj;
		return Objects.equals(date, other.date) && Objects.equals(destination, other.destination)
				&& Objects.equals(source, other.source) && Objects.equals(time, other.time);
	}
}
