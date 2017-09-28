package com.theironyard.workorder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.TypeKey;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Processor {
    Map<Status, Set<WorkOrder>> workOrderHashMap = new HashMap<>();

    public Processor() {
        workOrderHashMap.putIfAbsent(Status.INITIAL, new HashSet<>());
        workOrderHashMap.putIfAbsent(Status.ASSIGNED, new HashSet<>());
        workOrderHashMap.putIfAbsent(Status.IN_PROGRESS, new HashSet<>());
        workOrderHashMap.putIfAbsent(Status.DONE, new HashSet<>());
    }

    public void processWorkOrders() {
        //    1. Run forever in a loop. Have the loop sleep for a five seconds (or longer).
        while (true) {
            readIt();
            moveIt();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }

    private void moveIt() {
        //    2. Have a map with Status as the key and a Set of work orders for the value
        System.out.println(workOrderHashMap);
        //          - move work orders from one map entry to the next (i.e., From IN_PROGRESS
        //              to DONE; from ASSIGNED TO IN_PROGRESS; from INITIAL to ASSIGNED).
        //              A work order should only transitioned once per loop.

        Set<WorkOrder> setAssigned = new HashSet<>();
        Set<WorkOrder> setInProgress = new HashSet<>();
        Set<WorkOrder> setDone = new HashSet<>();

        for (Status status : workOrderHashMap.keySet()) {
            switch (status) {
                case INITIAL: setAssigned = workOrderHashMap.get(Status.INITIAL);
                    break;
                case ASSIGNED: setInProgress = workOrderHashMap.get(Status.ASSIGNED);
                    break;
                case IN_PROGRESS:setDone = workOrderHashMap.get(Status.IN_PROGRESS);
                    break;
            }
        }

        workOrderHashMap.put(Status.INITIAL, new HashSet<>());
        workOrderHashMap.put(Status.ASSIGNED, setAssigned);
        workOrderHashMap.put(Status.IN_PROGRESS, setInProgress);
        workOrderHashMap.put(Status.DONE, setDone);

        for (WorkOrder wo : workOrderHashMap.get(Status.INITIAL)) {
            wo.setStatus(Status.ASSIGNED);
        }
        for (WorkOrder wo : workOrderHashMap.get(Status.ASSIGNED)) {
            wo.setStatus(Status.IN_PROGRESS);
        }
        for (WorkOrder wo : workOrderHashMap.get(Status.IN_PROGRESS)) {
            wo.setStatus(Status.DONE);
        }

        System.out.println(workOrderHashMap);
    }

    private void readIt() {
        File currentDirectory = new File(".");
        File files[] = currentDirectory.listFiles();
        for (File f : files) {
            if (f.getName().endsWith(".json")) {

                //1. Change the file into a WorkOrder Object
                ObjectMapper mapper = new ObjectMapper();
                try {
                    WorkOrder obj = mapper.readValue(f, WorkOrder.class);
                    //2. Add that new WorkOrder object to HashMap under the proper key
                    workOrderHashMap.get(Status.INITIAL).add(obj);
                    //3. Delete that json file
                    f.delete();
                    System.out.println(this.workOrderHashMap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String args[]) {
        //In Processor have a public static void main that creates an instance of
        //Processor and calls the instance method to that loops to process the work order files.

        Processor processor = new Processor();
        processor.processWorkOrders();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}






