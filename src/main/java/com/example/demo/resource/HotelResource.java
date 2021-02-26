package com.example.demo.resource;

import com.example.demo.model.RoomOccupancyResponse;
import com.example.demo.service.RoomOccupancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
class HotelResource {

    private final RoomOccupancyService roomOccupancyService;

    @GetMapping("/room-occupancy")
    RoomOccupancyResponse getRoomOccupancy(@RequestParam(defaultValue = "0") long availableEconomyRooms, @RequestParam(defaultValue = "0") long availablePremiumRooms) {
        return roomOccupancyService.calculate(availableEconomyRooms, availablePremiumRooms);
    }
}
