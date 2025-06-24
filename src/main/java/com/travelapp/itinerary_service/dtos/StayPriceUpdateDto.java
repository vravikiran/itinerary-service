package com.travelapp.itinerary_service.dtos;

public class StayPriceUpdateDto {
	private String stayId;
	private String roomId;
	private double updatedPrice;

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

	public double getUpdatedPrice() {
		return updatedPrice;
	}

	public void setUpdatedPrice(double updatedPrice) {
		this.updatedPrice = updatedPrice;
	}
}
