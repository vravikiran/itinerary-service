package com.travelapp.itinerary_service.dtos;

import java.util.List;

public class StayDetailDto {
	private String stayId;
	private List<String> amenities;
	private AddressDto addressDto;
	private long contactNo;
	private String hotelType;
	private RoomDto roomDto;
	private double price;

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

	public List<String> getAmenities() {
		return amenities;
	}

	public void setAmenities(List<String> amenities) {
		this.amenities = amenities;
	}

	public AddressDto getAddressDto() {
		return addressDto;
	}

	public void setAddressDto(AddressDto addressDto) {
		this.addressDto = addressDto;
	}

	public long getContactNo() {
		return contactNo;
	}

	public void setContactNo(long contactNo) {
		this.contactNo = contactNo;
	}

	public String getHotelType() {
		return hotelType;
	}

	public void setHotelType(String hotelType) {
		this.hotelType = hotelType;
	}

	public RoomDto getRoomDto() {
		return roomDto;
	}

	public void setRoomDto(RoomDto roomDto) {
		this.roomDto = roomDto;
	}

	public StayDetailDto() {
		super();
	}
}
