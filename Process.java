// Author: Prince Dhamecha

package SchedulingAlgorithms;

public class Process{
    private int arrival_time;
    private int burst_time;
    private int completion_time;
    private int turn_around_time;
    private int waiting_time;
    private int response_time;
    private int first_CPU_time;
    private String process_name;

    Process(int arrival_time, int burst_time, String process_name){
        this.arrival_time=arrival_time;
        this.burst_time=burst_time;
        this.completion_time=-1;
        this.turn_around_time=-1;
        this.waiting_time=-1;
        this.response_time=-1;
        this.first_CPU_time=-1;
        this.process_name=process_name;
    }

    public int getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(int arrival_time) {
        this.arrival_time = arrival_time;
    }

    public int getBurst_time() {
        return burst_time;
    }

    public void setBurst_time(int burst_time) {
        this.burst_time = burst_time;
    }

    public int getCompletion_time() {
        return completion_time;
    }

    public void setCompletion_time(int completion_time) {
        this.completion_time = completion_time;
    }

    public int getTurn_around_time() {
        return turn_around_time;
    }

    public void setTurn_around_time(int turnaround_time) {
        this.turn_around_time = turnaround_time;
    }

    public int getWaiting_time() {
        return waiting_time;
    }

    public void setWaiting_time(int waiting_time) {
        this.waiting_time = waiting_time;
    }

    public int getResponse_time() {
        return response_time;
    }

    public void setResponse_time(int response_time) {
        this.response_time = response_time;
    }

    public int getFirst_CPU_time() {
        return first_CPU_time;
    }

    public void setFirst_CPU_time(int first_CPU_time) {
        this.first_CPU_time = first_CPU_time;
    }

    public String getProcess_name() {
        return process_name;
    }

    public void setProcess_name(String process_name) {
        this.process_name = process_name;
    }

    @Override
    public String toString() {
        return "Process{" +
                "arrival_time=" + arrival_time +
                ", burst_time=" + burst_time +
                ", completion_time=" + completion_time +
                ", response_time=" + response_time +
                ", first_cpu_time=" + first_CPU_time +
                ", turn_around_time=" + turn_around_time +
                ", process_name='" + process_name + '\'' +
                '}';
    }
}