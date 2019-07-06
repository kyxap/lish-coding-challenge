# Lish Coding Challenge for Software Engineer position

## Problem

Every day, Lish plans orders for subscribers to our daily catering service.  We partner with many different independent chefs and restaurants who prepare these orders.


It’s important that we give partners as equitable a share of orders as possible; however, each partner has a limit on the maximum number of orders that can be produced in a day. Some also have a guarantee to receive at least a minimum number of orders per day.  These limits differ per partner.


Write a program to assign orders to partners as fairly as possible, honoring these limits and guarantees.


## Assumptions

For this problem, we’ll simplify: only the number of orders matters. Ignore that orders may be different sizes or prices. There’s no preference on which partner gets which order.  “Fair” means that the count of orders is as equal as possible between partners without violating limits or guarantees. You only need to be fair for the day planned, without regard for larger time periods.


## Inputs

The number of orders for the day will be known ahead of time, and is given as an input.  The set of partners and their limits/guarantees are also inputs.


## Outputs

Your code should calculate the orders assigned per partner.


Represent the inputs / outputs using any data structure or format you see fit, and use any general-purpose programming language you’d like.


An error should be returned or an exception raised if any orders cannot be assigned, or any constraints cannot be honored.


If time allows, include tests that show your code works as expected.

## How to run

To run tests: 

```bash 
$ ./gradlew test
```

for debug and visualization purposes I have added posability to view positive test cases results as JSON output, so you should get something like:

```bash
$ ./gradlew test

> Task :test

io.github.kyxap.EquitableShareTest > testNoMinOrder STANDARD_OUT
    [{"partner":{"name":"chef3","maxOrders":10,"minOrders":0},"assignedOrders":3},{"partner":{"name":"chef2","maxOrders":10,"minOrders":0},"assignedOrders":3},{"partner":{"name":"chef1","maxOrders":10,"minOrders":0},"assignedOrders":4}]

io.github.kyxap.EquitableShareTest > testOnePartnerMinOrdersOnly STANDARD_OUT
    [{"partner":{"name":"chef1","maxOrders":10,"minOrders":0},"assignedOrders":0},{"partner":{"name":"chef2","maxOrders":10,"minOrders":5},"assignedOrders":5}]

io.github.kyxap.EquitableShareTest > testMinOrderOnly STANDARD_OUT
    [{"partner":{"name":"chef3","maxOrders":10,"minOrders":0},"assignedOrders":0},{"partner":{"name":"chef1","maxOrders":10,"minOrders":5},"assignedOrders":5},{"partner":{"name":"chef2","maxOrders":10,"minOrders":5},"assignedOrders":5}]

BUILD SUCCESSFUL in 0s
3 actionable tasks: 1 executed, 2 up-to-date

```
