package com.travelapp.itinerary_service.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelapp.itinerary_service.dtos.StayPriceUpdateDto;
import com.travelapp.itinerary_service.dtos.VehiclePriceUpdateDto;

@Service
public class ProducerService {
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	public void publishVehiclPriceUpdate(VehiclePriceUpdateDto vehiclePriceUpdateDto) throws JsonProcessingException {
		kafkaTemplate.send("vehpriceupdttopic", objectMapper.writeValueAsString(vehiclePriceUpdateDto));
	}

	public void publishStayPriceUpdate(StayPriceUpdateDto stayPriceUpdateDto) throws JsonProcessingException {
		kafkaTemplate.send("staypriceupdttopic", objectMapper.writeValueAsString(stayPriceUpdateDto));
	}
}
