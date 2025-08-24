package com.travelapp.itinerary_service.entities;

import lombok.Data;

@Data
public class StayInfo {
	private String stayId;
	private String roomId;
	private double price;
	private String stayName;
	private int noOfRooms;
}
