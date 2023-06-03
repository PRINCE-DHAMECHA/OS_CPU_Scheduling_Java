// Author: Prince Dhamecha

package SchedulingAlgorithms;


import java.util.*;


public class Scheduler {
    // Storing number of processes
    private final int number_of_processes;
    // Gantt chart where ith element describe which process is there at time interval i to i+1
    // i.e. gantt[2]->P1 describes that process P1 will be there in time interval 2 to 3
    // Choosing ArrayList because we don't know its size
    private final ArrayList<String> gantt;
    // Keeping array of process
    private Process[] processes;
    // Variables that store averages
    private double average_waiting_time, average_turn_around_time, average_response_time;


    // Constructor that initializes processes and other variables
    public Scheduler(int number_of_processes, int[] arrival_times, int[] burst_times) {
        this.number_of_processes = number_of_processes;
        this.average_waiting_time = -1;
        this.average_turn_around_time = -1;
        this.average_response_time = -1;
        gantt = new ArrayList<>();
        // Setting the number of processes in processes Array
        processes = new SchedulingAlgorithms.Process[number_of_processes];
        for (int i = 0; i < number_of_processes; i++) {
            // Creating individual processes with the help of arrival and burst time
            processes[i] = new SchedulingAlgorithms.Process(arrival_times[i], burst_times[i], "P" + (i + 1));
        }
        setFCFS();
    }


    // Getters
    public Process[] getProcesses() {
        return processes;
    }


    public int getnumber_of_processes() {
        return number_of_processes;
    }


    public double getAverage_waiting_time() {
        return average_waiting_time;
    }


    public double getAverage_turn_around_time() {
        return average_turn_around_time;
    }


    public double getAverage_response_time() {
        return average_response_time;
    }


    public ArrayList<String> getGantt() {
        return gantt;
    }


    // method to print Gantt chart
    public void print_gantt_chart() {
        System.out.println(gantt);
    }


    // Setting completing times of all processes using the Gantt chart
    private void setCompletion_times() {
        // Tracking the number of processes for which we already calculated the completion time
        int count = 0;
        // We start from the end of the Gantt chart because we know that by doing this whatever process we encounter, it will be the last time interval for that particular process
        for (int i = gantt.size() - 1; i >= 0 && count < number_of_processes; i--) {
            // We are storing the process name as Pn, so we can extract n from the name
            // After extracting the process number we're checking whether we already know the completion time or not
            // In simple words, according to our logic of starting from last, we'll not consider repetition as we know it'll not be its completion time
            if (!gantt.get(i).equals("*") && processes[Integer.parseInt(gantt.get(i).substring(1)) - 1].getCompletion_time() == -1) {
                // setting completion time
                processes[Integer.parseInt(gantt.get(i).substring(1)) - 1].setCompletion_time(i + 1);
                // increasing count to keep track that whether we processed all processes or not
                count++;
            }
        }
    }


    // Same logic and execution as above (setCompletion_times), But we're starting from the beginning which is pretty much straightforward to understand in the context of First CPU time
    private void setFirst_CPU_times() {
        int count = 0;
        for (int i = 0; i < gantt.size() && count < number_of_processes; i++) {
            if (!gantt.get(i).equals("*") && processes[Integer.parseInt(gantt.get(i).substring(1)) - 1].getFirst_CPU_time() == -1) {
                processes[Integer.parseInt(gantt.get(i).substring(1)) - 1].setFirst_CPU_time(i);
                count++;
            }
        }
    }


    // Setting response time and calculating average response time for all process
    // We know that Response Time= First CPU Time - Arrival Time
    private void setResponse_times() {
        double average = 0;
        for (Process p : processes) {
            p.setResponse_time(p.getFirst_CPU_time() - p.getArrival_time());
            average += p.getResponse_time();
        }
        average_response_time = Math.round(average / number_of_processes * 100) / 100.0;
    }


    // Turn Around Time = Completion Time - Arrival Time
    // How much total time process spend before completion and after arrival
    private void setTurn_around_times() {
        double average = 0;
        for (Process p : processes) {
            p.setTurn_around_time(p.getCompletion_time() - p.getArrival_time());
            average += p.getTurn_around_time();
        }
        average_turn_around_time = Math.round(average / number_of_processes * 100) / 100.0;
    }


    // Waiting Time = Turn Around Time - Burst Time
    // How much time process is ideal between completion time and arrival time
    private void setWaiting_time() {
        double average = 0;
        for (Process p : processes) {
            p.setWaiting_time(p.getTurn_around_time() - p.getBurst_time());
            average += p.getWaiting_time();
        }
        average_waiting_time = Math.round(average / number_of_processes * 100) / 100.0;
    }


    // Method to clear schedules before making a new schedule
    private void clear_schedule() {
        // sorting according to arrival time to reduce the overhead of writing in individual schedule algorithms
        Arrays.sort(processes, Comparator.comparing(Process::getArrival_time));
        gantt.clear();
        for (Process p : processes) {
            p.setCompletion_time(-1);
            p.setFirst_CPU_time(-1);
            p.setResponse_time(-1);
            p.setTurn_around_time(-1);
            p.setWaiting_time(-1);
        }
    }


    // setting all times for all processes
    private void setSchedule() {
        // sorting back according to process name i.e. P1, P2, P3,....
        Arrays.sort(processes, Comparator.comparing(process -> Integer.parseInt(process.getProcess_name().substring(1))));
        setCompletion_times();
        setFirst_CPU_times();
        setResponse_times();
        setTurn_around_times();
        setWaiting_time();
    }


    // FCFS:- First come, first served.
    //Start processing a process as soon as the process arrives
    public void setFCFS() {
        // Clearing schedule and setting process in ascending order of arrival time
        clear_schedule();
        // current_time to keep track of every time interval
        int current_time = 0;
        // Loop until we didn't process all processes
        for (int i = 0; i < number_of_processes; ) {
            // If process arrived then process it
            if (processes[i].getArrival_time() <= current_time) {
                String process_name = processes[i].getProcess_name();
                int burst_time = processes[i].getBurst_time();
                // Set it in Gantt chart for burst times
                while (burst_time-- > 0) {
                    gantt.add(process_name);
                    current_time++;
                }
                i++;
            }
            // Add "*" When no process arrives, and we can't process any process
            else {
                gantt.add("*");
                current_time++;
            }
        }
        setSchedule();
    }


    // SJF:- Process the shortest job first from the ready process
    public void setSJF() {
        clear_schedule();
        // Keep track of the completed process, current_time for a current time interval, the index for process with minimum burst time and minimum_burst_time for finding minimum burst time
        int completed_process = 0, current_time = 0, index = -1, minimum_burst_time = Integer.MAX_VALUE;
        while (completed_process < number_of_processes) {
            // Check all processes for arrival time
            for (int i = 0; i < number_of_processes; i++) {
                if (processes[i].getArrival_time() <= current_time) {
                    // checking completion time so didn't add the process again
                    if (processes[i].getBurst_time() < minimum_burst_time && processes[i].getCompletion_time() == -1) {
                        index = i;
                        minimum_burst_time = processes[i].getBurst_time();
                    }
                }
                // We already sorted the process in increasing order of arrival time, So once we encounter a larger arrival time we'll not proceed further
                else {
                    break;
                }
            }
            // If there exists any process then we schedule it for the whole burst time
            if (index >= 0) {
                int burst_time = processes[index].getBurst_time();
                while (burst_time-- > 0) {
                    gantt.add(processes[index].getProcess_name());
                    current_time++;
                }
                processes[index].setCompletion_time(current_time);
                completed_process++;
                minimum_burst_time = Integer.MAX_VALUE;
                index = -1;
            } else {
                gantt.add("*");
                current_time++;
            }
        }
        setSchedule();
    }
    // Above Approach is not fully optimised as we're traversing the whole array again, So with the help of other data structures like Queue we can make a more optimised version as we've done in SRTF and Round Robin.
    // For the sack of demonstrating the purpose of other approaches we'll keep this code otherwise In the code of SRTF we just need to execute a process for its burst time at the time of first and only execution, So by changing a few lines of code we'll get an optimised version of SJF.
    // Understand the idea and approach of SRTF, Then you can understand how just replacing a few lines of SRTF can work as SJF
    // Code needs to be changed to make it SJF from SRTF
    //    int burst_time = processes[index].getBurst_time();
    //                while (burst_time-- > 0) {
    //        gantt.add(processes[index].getProcess_name());
    //        current_time++;
    //    }


    // SRTF :- The Shortest Remaining Time First
    //Whichever process has the shortest remaining time we process it until any other process arrives with less remaining time than the current one
    void setSRTF() {
        clear_schedule();
        // Priority queue for getting process with the lowest burst time
        PriorityQueue<Process> pq = new PriorityQueue<>(Comparator.comparing(Process::getBurst_time));
        // Keep track of the next process to add to the queue using the index
        int index = 0, current_time = 0;
        while (pq.size() != 0 || index < number_of_processes) {
            // If the index is valid and the process arrives then add it to the queue
            if (index < number_of_processes && current_time >= processes[index].getArrival_time()) {
                //Add all eligible process
                while (index < number_of_processes && current_time >= processes[index].getArrival_time()) {
                    pq.add(processes[index]);
                    index++;
                }
            }
            // If q is not empty process the process with the lowest burst time
            if (!pq.isEmpty()) {
                Process p = pq.poll();
                // process for only one interval and then check for any arrived process
                gantt.add(p.getProcess_name());
                // If the process is remaining to complete then add it back to the queue with reduced burst time
                if (p.getBurst_time() > 1) {
                    pq.add(new Process(p.getArrival_time(), p.getBurst_time() - 1, p.getProcess_name()));
                }
            }
            // If no process can be processed then add "*" for that interval
            else {
                gantt.add("*");
            }
            current_time++;
        }
        setSchedule();
    }


    // Round Robin:- Execute process for a given time quantum and add last in the queue with decreasing remaining time
    void setRR(int time_quantum) {
        clear_schedule();
        // Keep queue for FIFO behaviour
        Queue<Process> pq = new LinkedList<>();
        // Keep track of next process to add in queue using index
        int index = 0, current_time = 0;
        // Break when queue is empty and we processed all processes
        while (pq.size() != 0 || index < number_of_processes) {
            // Add all arrived process within current_time
            if (index < number_of_processes && current_time >= processes[index].getArrival_time()) {
                while (index < number_of_processes && current_time >= processes[index].getArrival_time()) {
                    pq.add(processes[index]);
                    index++;
                }
            }
            // If the queue is not empty, execute the process for a given quantum of time
            if (!pq.isEmpty()) {
                Process p = pq.poll();
                // If remaining time is less than time_quantum then process for remaining time
                int process_period = Math.min(p.getBurst_time(), time_quantum);
                int temp_process_period = process_period;
                while (process_period-- > 0) {
                    gantt.add(p.getProcess_name());
                    current_time++;
                }
                // Add processes which arrived while we're executing the current process
                if (index < number_of_processes && current_time >= processes[index].getArrival_time()) {
                    while (index < number_of_processes && current_time >= processes[index].getArrival_time()) {
                        pq.add(processes[index]);
                        index++;
                    }
                }
                // Add back current process if remaining
                if (p.getBurst_time() - temp_process_period > 0) {
                    pq.add(new Process(p.getArrival_time(), p.getBurst_time() - temp_process_period, p.getProcess_name()));
                }
            }
            // Add "*" if no process is remaining
            else {
                gantt.add("*");
                current_time++;
            }
        }
        setSchedule();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("\n\tGantt Chart:\n\t").append(gantt.toString()).append("\n\n");
        s.append("\tProcess\t   AT\tBT\tCT\tTT\tWT\tRT\tFirst CPU\n");
        for (int i = 0; i < number_of_processes; i++) {
            s.append("\t").append(processes[i].getProcess_name()).append("\t\t   ").append(processes[i].getArrival_time()).append("\t").append(processes[i].getBurst_time()).append("\t").append(processes[i].getCompletion_time()).append("\t").append(processes[i].getTurn_around_time()).append("\t").append(processes[i].getWaiting_time()).append("\t").append(processes[i].getResponse_time()).append("\t").append(processes[i].getFirst_CPU_time()).append("\n");
        }
        s.append("\n\tAverage Turn Around Time: ").append(average_turn_around_time).append("\n");
        s.append("\tAverage Response Time: ").append(average_response_time).append("\n");
        s.append("\tAverage Waiting Time: ").append(average_waiting_time).append("\n");
        return s.toString();
    }
}

