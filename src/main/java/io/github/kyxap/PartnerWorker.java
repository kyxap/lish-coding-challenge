package io.github.kyxap;

import lombok.Getter;
import lombok.NonNull;

public class PartnerWorker implements Comparable<PartnerWorker> {
    public PartnerWorker(final Partner partner) {
        this.partner = partner;
    }

    @Getter
    @NonNull
    private final Partner partner;

    @Getter
    private Integer assignedOrders = 0;

    public void addOrders(final int quantity) {
        final int tmp = assignedOrders += quantity;
        if (partner.getMaxOrders() >= tmp) {
            assignedOrders = tmp;
        } else {
            throw new RuntimeException(partner.getName() + " can handle only: " + partner.getMaxOrders() + " orders per day");
        }
    }

    public void delOrders(final int quantity) {
        final int tmp = assignedOrders -= quantity;
        if (tmp >= 0) {
            assignedOrders = tmp;
        } else {
            throw new RuntimeException(partner.getName() + " it doesn't have so many assigned orders");
        }
    }

    @Override
    public int compareTo(final PartnerWorker o) {
        return this.getAssignedOrders().compareTo(o.getAssignedOrders());
    }


}
