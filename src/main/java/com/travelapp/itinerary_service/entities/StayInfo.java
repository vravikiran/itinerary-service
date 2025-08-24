package com.travelapp.itinerary_service.entities;

import lombok.Data;

@Data
public class StayInfo {
	private Long stayId;
	private int roomId;
	private double price;
	private String stayName;
	private int noOfRooms;
}
