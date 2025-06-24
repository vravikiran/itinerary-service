package com.travelapp.itinerary_service.repositories;

import org.slf4j.Logger;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.DeleteResult;
import com.travelapp.itinerary_service.entities.Itinerary;
import com.travelapp.itinerary_service.enums.CustomItineraryStatusEnum;
import com.travelapp.itinerary_service.util.Constants;

@Repository
public class ItineraryCustomRepositoryImpl implements ItineraryCustomRepository {
	Logger logger = LoggerFactory.getLogger(ItineraryCustomRepositoryImpl.class);
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public List<Itinerary> fetchItinerariesByDestinationType(String destinationType) {
		Query query = new Query(Criteria.where(Constants.DESTINATION_TYPES).in(destinationType.toUpperCase()).and(Constants.ACTIVE).is(true));
		List<Itinerary> itineraries = mongoTemplate.find(query, Itinerary.class);
		return itineraries;
	}

	@Override
	public List<Itinerary> fetchItinerariesByStayAndRoom(String stayId, String roomId) {
		Query query = new Query(Criteria.where(Constants.ROOM_ID).is(roomId).and(Constants.STAY_ID).is(stayId).and(Constants.ACTIVE).is(true));
		List<Itinerary> itineraries = mongoTemplate.find(query, Itinerary.class);
		return itineraries;
	}

	@Override
	public Itinerary updateItinerary(Map<String, Object> updatedFields, String id) {
		Query query = new Query(Criteria.where(Constants.ID).is(id));
		Update update = new Update();
		updatedFields.entrySet().stream()
				.filter(entry -> !Constants.ITINERARY_IMMUTABLE_FIELDS.contains(entry.getKey()))
				.forEach(entry -> update.set(entry.getKey(), entry.getValue()));
		mongoTemplate.updateFirst(query, update, Itinerary.class);
		return mongoTemplate.findById(id, Itinerary.class);
	}

	@Override
	public void deleteCustomItrUntouched() {
		LocalDate cutOffDate = LocalDate.now().minusDays(30);
		Query query = new Query(Criteria.where(Constants.IS_PREDEFINED).is(false).and(Constants.UPDATED_DATE)
				.lt(cutOffDate).and(Constants.STATUS).is(CustomItineraryStatusEnum.DRAFT.name()));
		DeleteResult deleteResult = mongoTemplate.remove(query, Itinerary.class);
		logger.info("Count of unmodified custom itineraries from last 30 days as on " + LocalDate.now() + ":: "
				+ deleteResult.getDeletedCount());
	}

	@Override
	public void archiveUnTouchedCustItrFromPastSixMonths() {
		LocalDate cutOffDate = LocalDate.now().minusMonths(6);
		Query query = new Query(
				Criteria.where(Constants.UPDATED_DATE).lt(cutOffDate).and(Constants.IS_PREDEFINED).is(false));
		Update update = new Update().set(Constants.ACTIVE, false);
		mongoTemplate.updateMulti(query, update, Itinerary.class);
	}

	@Override
	public List<Itinerary> findItinerariesByLocation(String location, boolean isVehicleIncluded,
			boolean isStayIncluded) {
		Criteria criteria = Criteria.where("location").is(location.toUpperCase()).and(Constants.ACTIVE).is(true)
				.and(Constants.IS_PREDEFINED).is(true);
		if (isVehicleIncluded) {
			criteria.and(Constants.IS_VEHICLE_INCLUDED).is(isVehicleIncluded);
		}
		if (isStayIncluded) {
			criteria.and(Constants.IS_STAY_INCLUDED).is(isStayIncluded);
		}
		Query query = new Query(criteria);
		List<Itinerary> itineraries = mongoTemplate.find(query, Itinerary.class);
		return itineraries;
	}
}
