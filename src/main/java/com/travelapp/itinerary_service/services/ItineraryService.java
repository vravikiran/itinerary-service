package com.travelapp.itinerary_service.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travelapp.itinerary_service.dtos.AddressDto;
import com.travelapp.itinerary_service.dtos.ItineraryDto;
import com.travelapp.itinerary_service.dtos.RoomDto;
import com.travelapp.itinerary_service.dtos.StayDetailDto;
import com.travelapp.itinerary_service.dtos.StayPriceUpdateDto;
import com.travelapp.itinerary_service.dtos.VehicleDetailDto;
import com.travelapp.itinerary_service.dtos.VehiclePriceUpdateDto;
import com.travelapp.itinerary_service.entities.Itinerary;
import com.travelapp.itinerary_service.entities.StayInfo;
import com.travelapp.itinerary_service.entities.Transfer;
import com.travelapp.itinerary_service.entities.VehicleInfo;
import com.travelapp.itinerary_service.enums.CustomItineraryStatusEnum;
import com.travelapp.itinerary_service.repositories.ItineraryRepository;
import com.travelapp.itinerary_service.util.ItineraryUtils;

@Service
public class ItineraryService {
	@Autowired
	ItineraryRepository itineraryRepository;

	public Itinerary createItinerary(Itinerary itinerary) {
		return itineraryRepository.insert(itinerary);
	}

	public ItineraryDto getItineraryById(String id) {
		Itinerary itinerary = itineraryRepository.findById(id).get();
		ItineraryDto itineraryDto = convertItineraryObjToDto(itinerary);
		return itineraryDto;
	}

	public Itinerary deactivateItineraryById(String id) {
		Itinerary itinerary = itineraryRepository.findById(id).get();
		itinerary.setActive(false);
		return itineraryRepository.save(itinerary);
	}

	public List<Itinerary> fetchItinerariesBySearchCriteria(String location, boolean isVehicleIncluded,
			boolean isStayIncluded) {
		List<Itinerary> itineraries = itineraryRepository.findItinerariesByLocation(location.toUpperCase(),
				isVehicleIncluded, isStayIncluded);
		/*List<ItineraryDto> itineraryDtos = itineraries.stream().map(itinerary -> {
			ItineraryDto itineraryDto = convertItineraryObjToDto(itinerary);
			return itineraryDto;
		}).collect(Collectors.toList());*/
		return itineraries;
	}

	public List<Itinerary> fetchItinerariesByDestinationType(String destinationType) {
		List<Itinerary> itineraries = itineraryRepository.fetchItinerariesByDestinationType(destinationType);
		return itineraries;
	}

	public List<Itinerary> fetchItinerariesByVehicle(String vehicleId) {
		List<Itinerary> itineraries = itineraryRepository.findItinerariesByVehicleId(vehicleId.toUpperCase());
		return itineraries;
	}

	public List<Itinerary> findItinerariesByStayIdAndRoomId(String roomId, String stayId) {
		List<Itinerary> itineraries = itineraryRepository.fetchItinerariesByStayAndRoom(stayId, roomId);
		return itineraries;
	}

	public List<Itinerary> updateVehiclePriceForItineraries(VehiclePriceUpdateDto vehiclePriceUpdateDto) {
		List<Itinerary> itineraries = itineraryRepository
				.findItinerariesByVehicleId(vehiclePriceUpdateDto.getVehicleId().toUpperCase());
		List<Itinerary> updatedItineraries = itineraries.stream().map(itinerary -> {
			itinerary.getVehicleinfo().setPrice(vehiclePriceUpdateDto.getUpdatedPrice());
			return itinerary;
		}).collect(Collectors.toList());
		itineraryRepository.saveAll(updatedItineraries);
		return updatedItineraries;
	}

	public List<Itinerary> updateStayPricesForItineraries(StayPriceUpdateDto stayPriceUpdateDto) {
		List<Itinerary> itineraries = itineraryRepository.fetchItinerariesByStayAndRoom(
				stayPriceUpdateDto.getStayId().toUpperCase(), stayPriceUpdateDto.getRoomId().toUpperCase());
		System.out.println("size of itineraries :: " + itineraries.size());
		List<Itinerary> updatedItineraries = itineraries.stream().map(itinerary -> {
			itinerary.getStayInfo().setPrice(stayPriceUpdateDto.getUpdatedPrice());
			return itinerary;
		}).collect(Collectors.toList());
		itineraryRepository.saveAll(updatedItineraries);
		return updatedItineraries;
	}

	public Itinerary updateItinerary(String id, Map<String, Object> updatedFields) {
		return itineraryRepository.updateItinerary(updatedFields, id);
	}

	public Itinerary customizeItineraryWithupdatedStay(StayInfo stayInfo, long mobileNo, String id) throws Exception {
		Itinerary originalItinerary = itineraryRepository.findById(id).get();
		if (originalItinerary.isPredefined()) {
			Itinerary updatedItinerary = ItineraryUtils.deepCopy(originalItinerary);
			updatedItinerary.setStayInfo(stayInfo);
			updatedItinerary.setCreatedDate(LocalDate.now());
			updatedItinerary.setUpdatedDate(LocalDate.now());
			updatedItinerary.setStatus(CustomItineraryStatusEnum.DRAFT.name());
			updatedItinerary.setCustomerId(mobileNo);
			updatedItinerary.setPredefined(false);
			updatedItinerary.setBaseItineraryId(id);
			itineraryRepository.insert(updatedItinerary);
			return updatedItinerary;
		} else if (!originalItinerary.isPredefined() && (originalItinerary.getStatus()
				.equalsIgnoreCase(CustomItineraryStatusEnum.DRAFT.name())
				|| originalItinerary.getStatus().equalsIgnoreCase(CustomItineraryStatusEnum.BOOKING_PENDING.name()))) {
			originalItinerary.setStayInfo(stayInfo);
			originalItinerary = itineraryRepository.save(originalItinerary);
			return originalItinerary;
		} else {
			throw new Exception("Invalid Itinerary");
		}
	}

	public Itinerary customizeItineraryWithUpdatedVehicleInfo(VehicleInfo vehicleInfo, String id, long mobileNo)
			throws Exception {
		Itinerary originalItinerary = itineraryRepository.findById(id).get();
		if (originalItinerary.isPredefined()) {
			Itinerary updatedItinerary = ItineraryUtils.deepCopy(originalItinerary);
			updatedItinerary.setCustomerId(mobileNo);
			updatedItinerary.setCreatedDate(LocalDate.now());
			updatedItinerary.setUpdatedDate(LocalDate.now());
			updatedItinerary.setPredefined(false);
			updatedItinerary.setBaseItineraryId(id);
			updatedItinerary.setVehicleinfo(vehicleInfo);
			updatedItinerary.setVehicleIncluded(true);
			updatedItinerary.setStatus(CustomItineraryStatusEnum.DRAFT.name());
			updatedItinerary = itineraryRepository.insert(updatedItinerary);
			return updatedItinerary;
		} else if (!originalItinerary.isPredefined() && (originalItinerary.getStatus()
				.equalsIgnoreCase(CustomItineraryStatusEnum.DRAFT.name())
				|| originalItinerary.getStatus().equalsIgnoreCase(CustomItineraryStatusEnum.BOOKING_PENDING.name()))) {
			originalItinerary.setVehicleinfo(vehicleInfo);
			originalItinerary.setUpdatedDate(LocalDate.now());
			if (!originalItinerary.isVehicleIncluded()) {
				originalItinerary.setVehicleIncluded(true);
			}
			itineraryRepository.save(originalItinerary);
			return originalItinerary;
		} else {
			throw new Exception("Invalid Itinerary");
		}
	}

	public List<Itinerary> fetchItinerariesOfCustomer(long customerId) {
		return itineraryRepository.findItinerariesByCustomerid(customerId);
	}

	public Itinerary addTransfersToItinerary(String itineraryId, long customerId, Set<Transfer> transfers)
			throws Exception {
		Itinerary originalItinerary = itineraryRepository.findById(itineraryId).get();
		Itinerary updatedItinerary = null;
		if (originalItinerary.isVehicleIncluded()) {
			throw new Exception("Cannot add transfers, vehicle already included in the itinerary");
		} else if (originalItinerary.isPredefined()) {
			updatedItinerary = ItineraryUtils.deepCopy(originalItinerary);
			if (originalItinerary.getTransfers() == null) {
				updatedItinerary.setTransfers(transfers);
			} else {
				Set<Transfer> origTransfers = originalItinerary.getTransfers();
				origTransfers.addAll(transfers);
				updatedItinerary.setTransfers(origTransfers);
			}
			updatedItinerary.setBaseItineraryId(itineraryId);
			updatedItinerary.setCreatedDate(LocalDate.now());
			updatedItinerary.setUpdatedDate(LocalDate.now());
			updatedItinerary.setPredefined(false);
			updatedItinerary.setCustomerId(customerId);
			updatedItinerary.setStatus(CustomItineraryStatusEnum.DRAFT.name());
			updatedItinerary = itineraryRepository.insert(updatedItinerary);
			return updatedItinerary;
		} else {
			if (originalItinerary.getTransfers() == null) {
				originalItinerary.setTransfers(transfers);
			} else {
				Set<Transfer> origTransfers = originalItinerary.getTransfers();
				origTransfers.addAll(transfers);
				originalItinerary.setTransfers(origTransfers);
			}
			originalItinerary.setUpdatedDate(LocalDate.now());
			originalItinerary = itineraryRepository.save(originalItinerary);
			return originalItinerary;
		}

	}

	public void deleteCustomItineariesUntouched() {
		itineraryRepository.deleteCustomItrUntouched();
	}

	public void archiveUnTouchedCustItrFromPastSixMonths() {
		itineraryRepository.archiveUnTouchedCustItrFromPastSixMonths();
	}

	private ItineraryDto convertItineraryObjToDto(Itinerary itinerary) {
		double price = itinerary.getPrice();
		ItineraryDto itineraryDto = new ItineraryDto();
		itineraryDto.setActive(itinerary.isActive());
		itineraryDto.setId(itinerary.getId());
		itineraryDto.setDestinationTypes(itinerary.getDestinationTypes());
		itineraryDto.setDuration(itinerary.getDuration());
		itineraryDto.setGuideIncluded(itinerary.isGuideIncluded());
		itineraryDto.setImagesUri(itinerary.getImagesUri());
		itineraryDto.setItineraryDays(itinerary.getItineraryDays());
		itineraryDto.setPrice(itinerary.getPrice());
		itineraryDto.setItineraryName(itinerary.getItineraryName());
		itineraryDto.setLocation(itinerary.getLocation());
		itineraryDto.setPremiumType(itinerary.getPremiumType());
		itineraryDto.setVehicleIncluded(itinerary.isVehicleIncluded());
		itineraryDto.setStayIncluded(itinerary.isStayIncluded());
		if (itinerary.isStayIncluded()) {
			StayDetailDto stayDetailDto = new StayDetailDto();
			List<String> amenities = new ArrayList<>();
			amenities.add("bathroom");
			amenities.add("IronBoard");
			stayDetailDto.setAmenities(amenities);
			RoomDto roomDto = new RoomDto();
			roomDto.setBedType("single");
			roomDto.setRoomAmenities(amenities);
			roomDto.setNoOfBeds(2);
			roomDto.setRoomId(123);
			roomDto.setSize(250);
			stayDetailDto.setRoomDto(roomDto);
			AddressDto addressDto = new AddressDto();
			addressDto.setAddress_line1("line 1");
			addressDto.setAddress_line2("line 2");
			addressDto.setAddressId(1);
			addressDto.setCity("Hyderabad");
			addressDto.setState("Telangana");
			addressDto.setCountry("INDIA");
			addressDto.setZipCode(500072);
				stayDetailDto.setAddressDto(addressDto);
				stayDetailDto.setContactNo(1243423443);
				stayDetailDto.setHotelType("HOME STAY");
				stayDetailDto.setPrice(itinerary.getStayInfo().getPrice());
				stayDetailDto.setStayId("CHE_1234");
				itineraryDto.setStayDetailDto(stayDetailDto);
			price += stayDetailDto.getPrice();
			System.out.println(price);
		}
		if (itinerary.isVehicleIncluded()) {
			VehicleDetailDto vehicleDetailDto = new VehicleDetailDto();
			vehicleDetailDto.setNoOfSeats(4);
			vehicleDetailDto.setVehicleId(23);
			vehicleDetailDto.setVehicleType("SEDAN");
			vehicleDetailDto.setPrice(itinerary.getVehicleinfo().getPrice());
			itineraryDto.setVehicleDetailDto(vehicleDetailDto);
			price += vehicleDetailDto.getPrice();
			System.out.println(price);
		}
		System.out.println(price);
		itineraryDto.setPrice(price);
		return itineraryDto;
	}
}
