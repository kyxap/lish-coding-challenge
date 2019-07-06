package io.github.kyxap;

import lombok.Getter;

import java.util.List;
import java.util.PriorityQueue;

public class EquitableShare {
    private final Integer requestedOrderCount;
    private Integer highPrioReq = 0;
    private Integer maxReqPos = 0;
    private Integer assignedOrderCount = 0;

    private final PriorityQueue<PartnerWorker> highPriorityQueue = new PriorityQueue<>();
    private final PriorityQueue<PartnerWorker> lowPriorityQueue = new PriorityQueue<>();

    @Getter
    private final PriorityQueue<PartnerWorker> assignedPriorityQueue = new PriorityQueue<>();

    public EquitableShare(final List<PartnerWorker> partnerWorkersList, final int requestedOrderCount) {
        this.requestedOrderCount = requestedOrderCount;

        partnerWorkersList.forEach(worker -> {
            if (worker.getPartner().getMinOrders() != 0) {
                highPrioReq += worker.getPartner().getMinOrders();
                highPriorityQueue.add(worker);
            } else {
                lowPriorityQueue.add(worker);
            }
            maxReqPos += worker.getPartner().getMaxOrders();
        });
    }

    public void shareOrders() {
        if (highPrioReq > requestedOrderCount) {
            throw new RuntimeException("We got very little orders. We can't guarantee that all high priority Partners got their minimum");
        } else if (requestedOrderCount > maxReqPos) {
            throw new RuntimeException("We got to many orders. We can't handle that amount with current Partner list");
        }

        assignOrders(highPrioReq, highPriorityQueue);

        lowPriorityQueue.addAll(highPriorityQueue);
        highPriorityQueue.clear();

        assignOrders(requestedOrderCount - assignedOrderCount, lowPriorityQueue);

        assignedPriorityQueue.addAll(lowPriorityQueue);
        lowPriorityQueue.clear();
    }

    private void assignOrders(final int count, final PriorityQueue<PartnerWorker> queue) {
        for (int i = 0; i < count; i++) {
            final PartnerWorker peak = queue.poll();
            peak.addOrders(1);
            queue.add(peak);
            assignedOrderCount++;
        }
    }

    public PartnerWorker getPartnerWorkerByName(final String name) {
        return assignedPriorityQueue.stream().filter(f -> f.getPartner().getName().equals(name)).findFirst().get();
    }
}
