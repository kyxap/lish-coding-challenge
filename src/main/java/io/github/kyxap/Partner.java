package io.github.kyxap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
public class Partner {
    public Partner(final String name, final Integer maxOrders) {
        this.name = name;
        this.maxOrders = maxOrders;
    }

    public Partner(final String name, final int maxOrders, final int minOrders) {
        this.name = name;
        this.maxOrders = maxOrders;
        this.minOrders = minOrders;
    }

    @Getter
    @NonNull
    private final String name;

    @Getter
    @NonNull
    private final Integer maxOrders;

    @Getter
    private Integer minOrders = 0;
}
