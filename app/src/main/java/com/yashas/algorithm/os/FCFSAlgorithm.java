package com.yashas.algorithm.os;

import java.util.ArrayList;

public class FCFSAlgorithm {
    private final int n;
    private final ArrayList<Integer> processes;
    private final ArrayList<Integer> burstTime;
    private final ArrayList<Integer> turnAroundTime;
    private final ArrayList<Integer> arrivalTime;
    private final ArrayList<Integer> waitingTime;
    private final ArrayList<Integer> completionTime;
    private float averageTurnAroundTime = 0;
    private float averageWaitingTime = 0;

    public FCFSAlgorithm(int size, ArrayList<Integer> process, ArrayList<Integer> burstTime, ArrayList<Integer> arrivalTime) {
        this.n = size;
        this.processes = process;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        waitingTime = new ArrayList<>(n);
        turnAroundTime = new ArrayList<>(n);
        completionTime = new ArrayList<>(n);
        findAvgTime();
    }

    public void findWaitingTime() {
        ArrayList<Integer> service_time = new ArrayList<>(n);
        service_time.add(arrivalTime.get(0));
        waitingTime.add(0);
        for (int i = 1; i < n; i++) {
            int wasted = 0;
            service_time.add(service_time.get(i - 1) + burstTime.get(i - 1));
            waitingTime.add(service_time.get(i) - arrivalTime.get(i));
            if (waitingTime.get(i) < 0) {
                wasted = Math.abs(waitingTime.get(i));
                waitingTime.set(i, 0);
            }
            service_time.set(i, service_time.get(i) + wasted);
        }

    }

    public void findTurnAroundTime() {
        for (int i = 0; i < n; i++)
            turnAroundTime.add(burstTime.get(i) + waitingTime.get(i));
    }

    public void findAvgTime() {

        findWaitingTime();

        findTurnAroundTime();

//        System.out.println("Processes " + " Burst Time " + " Arrival Time "
//                + " Waiting Time " + " Turn-Around Time "
//                + " Completion Time \n");
        int total_wt = 0, total_tat = 0;

        for (int i = 0; i < n; i++) {
            total_wt = total_wt + waitingTime.get(i);
            total_tat = total_tat + turnAroundTime.get(i);
            completionTime.add(turnAroundTime.get(i) + arrivalTime.get(i));
//            System.out.println("\tP" + (i + 1) + "\t\t" + burstTime.get(i) + "\t\t\t"
//                    + arrivalTime.get(i) + "\t\t\t" + waitingTime.get(i) + "\t\t\t"
//                    + turnAroundTime.get(i) + "\t\t\t" + completionTime.get(i));
        }

        System.out.println(processes);

        averageTurnAroundTime = (float) total_wt / (float) n;
        averageWaitingTime = (float) total_tat / (float) n;

//        System.out.println("Average waiting time = "
//                + averageTurnAroundTime);
//        System.out.println("\nAverage turn around time = "
//                + averageWaitingTime);
    }

    public ArrayList<Integer> getTurnAroundTime() {
        return turnAroundTime;
    }

    public ArrayList<Integer> getWaitingTime() {
        return waitingTime;
    }

    public float getAverageTurnAroundTime() {
        return averageTurnAroundTime;
    }

    public float getAverageWaitingTime() {
        return averageWaitingTime;
    }

    public ArrayList<Integer> getCompletionTime() {
        return completionTime;
    }
}