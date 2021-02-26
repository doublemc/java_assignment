package com.example.demo.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.RoomOccupancyResponse;
import com.example.demo.model.RoomOccupancyResponse.RoomUsage;
import org.springframework.stereotype.Service;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static java.util.Comparator.reverseOrder;

@Service
public class RoomOccupancyService {

    private static final List<BigDecimal> POTENTIAL_GUESTS = List.of(
        valueOf(23),
        valueOf(45),
        valueOf(155),
        valueOf(374),
        valueOf(22),
        valueOf(99),
        valueOf(100),
        valueOf(101),
        valueOf(115),
        valueOf(209)
    );

    private static final BigDecimal ECONOMY_LIMIT = valueOf(100);

    public RoomOccupancyResponse calculate(final long availableEconomyRooms, final long availablePremiumRooms) {

        final var premiumGuests = new ArrayList<BigDecimal>();
        final var economyGuests = new ArrayList<BigDecimal>();

        final var enoughEconomyRooms = POTENTIAL_GUESTS.stream()
            .filter(this::isEconomy)
            .count() <= availableEconomyRooms;

        POTENTIAL_GUESTS.stream()
            .sorted(reverseOrder())
            .forEach(price -> {
                if (!(isEconomy(price))) {
                    if (premiumGuests.size() < availablePremiumRooms) {
                        premiumGuests.add(price);
                    }
                } else {
                    if (premiumGuests.size() < availablePremiumRooms && !enoughEconomyRooms) {
                        premiumGuests.add(price);
                    } else if (economyGuests.size() < availableEconomyRooms) {
                        economyGuests.add(price);
                    }
                }

            });

        return RoomOccupancyResponse.builder()
            .economyRoomUsage(RoomUsage.builder()
                .usedRooms(economyGuests.size())
                .totalSpending(sumAll(economyGuests))
                .build())
            .premiumRoomUsage(RoomUsage.builder()
                .usedRooms(premiumGuests.size())
                .totalSpending(sumAll(premiumGuests))
                .build())
            .build();
    }

    private BigDecimal sumAll(final List<BigDecimal> prices) {
        return prices.stream()
            .reduce(BigDecimal::add)
            .orElse(ZERO);
    }

    private boolean isEconomy(final BigDecimal price) {
        return price.compareTo(ECONOMY_LIMIT) < 0;
    }
}
