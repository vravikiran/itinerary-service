package com.travelapp.itinerary_service.repositories;

import java.util.List;
import java.util.Map;

import com.travelapp.itinerary_service.entities.Itinerary;

public interface ItineraryCustomRepository {
	public List<Itinerary> fetchItinerariesByDestinationType(String destinationType);

	public List<Itinerary> fetchItinerariesByStayAndRoom(Long stayId, int roomId);

	public Itinerary updateItinerary(Map<String, Object> updatedFields, String id);

	public void deleteCustomItrUntouched();

	public void archiveUnTouchedCustItrFromPastSixMonths();

	public List<Itinerary> findItinerariesByLocation(String location, boolean isVehicleIncluded,
			boolean isStayIncluded);
}
