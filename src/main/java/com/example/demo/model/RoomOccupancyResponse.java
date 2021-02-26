package com.example.demo.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RoomOccupancyResponse {
    RoomUsage economyRoomUsage;
    RoomUsage premiumRoomUsage;

    @Value
    @Builder
    public static class RoomUsage {
        BigDecimal totalSpending;
        long usedRooms;
    }
}
