// Author: Prince Dhamecha

package SchedulingAlgorithms;

public class Test {
    public static void main(String[] args) {
        int[] arrival_times = {50, 5, 60, 4, 2, 4, 7, 5, 19};
        int[] burst_times = {4, 2, 5, 4, 8, 9, 2, 4, 5};
        int number_of_processes = arrival_times.length;
        Schedule sc = new Schedule(number_of_processes, arrival_times, burst_times);

        System.out.println("\n\tFCFS:");
        System.out.println("\t------------------------------------------");
        System.out.println(sc);

        sc.setSJF();
        System.out.println("\n\tSJF:");
        System.out.println("\t------------------------------------------");
        System.out.println(sc);

        sc.setSRTF();
        System.out.println("\n\tSRTF:");
        System.out.println("\t------------------------------------------");
        System.out.println(sc);


        sc.setRR(2);
        System.out.println("\n\tRR:");
        System.out.println("\t------------------------------------------");
        System.out.println(sc);

    }
}
