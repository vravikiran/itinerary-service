package com.travelapp.itinerary_service.dtos;

import java.util.List;

public class RoomDto {
	private int roomId;
	private String bedType;
	private int size;
	private List<String> roomAmenities;
	private int noOfBeds;

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getBedType() {
		return bedType;
	}

	public void setBedType(String bedType) {
		this.bedType = bedType;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<String> getRoomAmenities() {
		return roomAmenities;
	}

	public void setRoomAmenities(List<String> roomAmenities) {
		this.roomAmenities = roomAmenities;
	}

	public int getNoOfBeds() {
		return noOfBeds;
	}

	public void setNoOfBeds(int noOfBeds) {
		this.noOfBeds = noOfBeds;
	}
}
