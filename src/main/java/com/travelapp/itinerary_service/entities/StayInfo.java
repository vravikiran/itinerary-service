package com.travelapp.itinerary_service.entities;

public class StayInfo {
	private String stayId;
	private String roomId;
	private double price;
	private String stayName;
	private int noOfRooms;

	public int getNoOfRooms() {
		return noOfRooms;
	}

	public void setNoOfRooms(int noOfRooms) {
		this.noOfRooms = noOfRooms;
	}

	public String getStayName() {
		return stayName;
	}

	public void setStayName(String stayName) {
		this.stayName = stayName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getStayId() {
		return stayId;
	}

	public void setStayId(String stayId) {
		this.stayId = stayId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
}
