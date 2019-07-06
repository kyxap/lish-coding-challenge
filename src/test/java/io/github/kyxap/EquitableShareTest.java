package io.github.kyxap;

import com.google.gson.Gson;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EquitableShareTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private EquitableShare equitableShare;

    @Test
    public void testOnePartnerMinOrdersOnly() {
        final Partner partner1 = new Partner("chef1", 10);
        final Partner partner2 = new Partner("chef2", 10, 5);
        final PartnerWorker partnerWorker = new PartnerWorker(partner1);
        final PartnerWorker partnerWorker2 = new PartnerWorker(partner2);
        final List<PartnerWorker> partnerWorkerList = new ArrayList<PartnerWorker>() {
            {
                add(partnerWorker);
                add(partnerWorker2);
            }
        };

        equitableShare = new EquitableShare(partnerWorkerList, 5);
        equitableShare.shareOrders();

        printAsJson(equitableShare.getAssignedPriorityQueue());

        assertEquals(0, (int) equitableShare.getPartnerWorkerByName("chef1").getAssignedOrders());
        assertEquals(5, (int) equitableShare.getPartnerWorkerByName("chef2").getAssignedOrders());
    }

    @Test
    public void testToManyRequests() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("We got to many orders. We can't handle that amount with current Partner list");

        final List<PartnerWorker> partnerWorkerList = new ArrayList<>();
        equitableShare = new EquitableShare(partnerWorkerList, 5);
        equitableShare.shareOrders();
    }

    @Test
    public void testNotEnoughOrdersForMin() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("We got very little orders. We can't guarantee that all high priority Partners got their minimum");
        final Partner partner2 = new Partner("chef2", 10, 5);
        final PartnerWorker partnerWorker2 = new PartnerWorker(partner2);
        final List<PartnerWorker> partnerWorkerList = new ArrayList<PartnerWorker>() {{
            add(partnerWorker2);
        }};

        equitableShare = new EquitableShare(partnerWorkerList, 4);
        equitableShare.shareOrders();
    }

    @Test
    public void testMinOrderOnly() {
        final Partner partner1 = new Partner("chef1", 10, 5);
        final Partner partner2 = new Partner("chef2", 10, 5);
        final Partner partner3 = new Partner("chef3", 10);
        final PartnerWorker partnerWorker = new PartnerWorker(partner1);
        final PartnerWorker partnerWorker2 = new PartnerWorker(partner2);
        final PartnerWorker partnerWorker3 = new PartnerWorker(partner3);
        final List<PartnerWorker> partnerWorkerList = new ArrayList<PartnerWorker>() {
            {
                add(partnerWorker);
                add(partnerWorker2);
                add(partnerWorker3);
            }
        };

        equitableShare = new EquitableShare(partnerWorkerList, 10);
        equitableShare.shareOrders();

        printAsJson(equitableShare.getAssignedPriorityQueue());

        assertEquals(5, (int) equitableShare.getPartnerWorkerByName("chef1").getAssignedOrders());
        assertEquals(5, (int) equitableShare.getPartnerWorkerByName("chef2").getAssignedOrders());
        assertEquals(0, (int) equitableShare.getPartnerWorkerByName("chef3").getAssignedOrders());
    }

    @Test
    public void testNoMinOrder() {
        final Partner partner1 = new Partner("chef1", 10);
        final Partner partner2 = new Partner("chef2", 10);
        final Partner partner3 = new Partner("chef3", 10);
        final PartnerWorker partnerWorker = new PartnerWorker(partner1);
        final PartnerWorker partnerWorker2 = new PartnerWorker(partner2);
        final PartnerWorker partnerWorker3 = new PartnerWorker(partner3);
        final List<PartnerWorker> partnerWorkerList = new ArrayList<PartnerWorker>() {
            {
                add(partnerWorker);
                add(partnerWorker2);
                add(partnerWorker3);
            }
        };

        equitableShare = new EquitableShare(partnerWorkerList, 10);
        equitableShare.shareOrders();

        printAsJson(equitableShare.getAssignedPriorityQueue());

        assertEquals(4, (int) equitableShare.getPartnerWorkerByName("chef1").getAssignedOrders());
        assertEquals(3, (int) equitableShare.getPartnerWorkerByName("chef2").getAssignedOrders());
        assertEquals(3, (int) equitableShare.getPartnerWorkerByName("chef3").getAssignedOrders());
    }


    /**
     * For debug and visualization purposes
     */
    private void printAsJson(final Object o) {
        final Gson g = new Gson();
        System.out.println(g.toJson(o));
    }
}
