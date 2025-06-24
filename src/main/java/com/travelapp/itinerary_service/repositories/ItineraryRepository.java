package com.travelapp.itinerary_service.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.travelapp.itinerary_service.entities.Itinerary;

@Repository
public interface ItineraryRepository extends MongoRepository<Itinerary, String>, ItineraryCustomRepository {
	/*@Query("{ 'location': ?0,'active':?3,'isPredefined':?4,'isVehicleIncluded': ?1,'isStayIncluded': ?2 } ] }")
	public List<Itinerary> findItinerariesByLocationAndIsVehicleIncluded(String location, boolean isVehicleIncluded,
			boolean isStayIncluded, boolean active, boolean isPredefined);*/

	@Query("{'vehicleinfo.vehicleId':?0}")
	public List<Itinerary> findItinerariesByVehicleId(String vehicleId);

	@Query("{'customerId':?0}")
	public List<Itinerary> findItinerariesByCustomerid(long customerId);
}
