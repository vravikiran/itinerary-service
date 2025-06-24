package com.travelapp.itinerary_service.util;

import java.util.Set;

public class Constants {
	public static final String DESTINATION_TYPES = "destinationTypes";
	public static final String ROOM_ID = "stayInfo.roomId";
	public static final String STAY_ID = "stayInfo.stayId";
	public static final Set<String> ITINERARY_IMMUTABLE_FIELDS = Set.of("location", "stayInfo", "vehicleinfo",
			"isCustom", "customerId", "createdDate");
	public static final String ID = "_id";
	public static final String IS_PREDEFINED = "isPredefined";
	public static final String UPDATED_DATE = "updatedDate";
	public static final String STATUS = "status";
	public static final String ACTIVE = "active";
	public static final String IS_VEHICLE_INCLUDED = "isVehicleIncluded";
	public static final String IS_STAY_INCLUDED = "isStayIncluded";
	public static final String LOCATION = "location";
}
