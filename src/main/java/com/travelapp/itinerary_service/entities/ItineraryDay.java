package com.travelapp.itinerary_service.entities;

import lombok.Data;

import java.util.List;

@Data
public class ItineraryDay {
	private int dayNumber;
	private String description;
	private List<String> activities;
	private String title;
}
