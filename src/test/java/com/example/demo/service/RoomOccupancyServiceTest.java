package com.example.demo.service;

import java.math.BigDecimal;
import java.util.stream.Stream;

import com.example.demo.model.RoomOccupancyResponse;
import com.example.demo.model.RoomOccupancyResponse.RoomUsage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class RoomOccupancyServiceTest {

    private final RoomOccupancyService roomOccupancyService = new RoomOccupancyService();

    @ParameterizedTest
    @MethodSource("parameters")
    void shouldCalculateRoomOccupancy(int economyRooms, int premiumRooms, RoomOccupancyResponse expectedResult) {
        // when
        final RoomOccupancyResponse result = roomOccupancyService.calculate(economyRooms, premiumRooms);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> parameters() {
        return Stream.of(
            Arguments.of(3, 3, createResult(3, 167, 3, 738)),
            Arguments.of(5, 7, createResult(4, 189, 6, 1054)),
            Arguments.of(7, 2, createResult(4, 189, 2, 583)),
            Arguments.of(1, 7, createResult(1, 45, 7, 1153))
        );
    }

    private static RoomOccupancyResponse createResult(int economyRoomUsage, int economyTotalSpending, int premiumRoomUsage, int premiumTotalSpending) {
        return RoomOccupancyResponse.builder()
            .economyRoomUsage(RoomUsage.builder()
                .usedRooms(economyRoomUsage)
                .totalSpending(BigDecimal.valueOf(economyTotalSpending))
                .build())
            .premiumRoomUsage(RoomUsage.builder()
                .usedRooms(premiumRoomUsage)
                .totalSpending(BigDecimal.valueOf(premiumTotalSpending))
                .build())
            .build();
    }

}