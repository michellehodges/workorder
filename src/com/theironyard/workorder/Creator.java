package com.theironyard.workorder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Creator {

    // This process is creating work orders and writing them to disk.

    public void createWorkOrders() {
        //read input, create work orders and write as json files
        //Set an id when the work order is created. Persist the work order to a file in JSON

        WorkOrder workOrder = getWorkOrderFromInput();

        File file = new File(workOrder.getId() + ".json");
        try {
            FileWriter fileWriter = new FileWriter(file);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(workOrder);

            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static WorkOrder getWorkOrderFromInput() {

        int id = 0;
        String description = "";
        String senderName = "";
        Status status = Status.INITIAL;

        WorkOrder workOrder = new WorkOrder(id, description, senderName, status);

        Scanner input = new Scanner(System.in);

        while (id == 0) {
            System.out.println("Welcome to WO Creation. Please enter an ID to start with.");
            id = input.nextInt();
            workOrder.setId(id);
        }

        while (description.equals("")) {
            System.out.println("Please enter a description.");
            description = input.nextLine();
            workOrder.setDescription(description);
        }

        while (senderName.equals("")) {
            System.out.println("Please enter the sender name.");
            senderName = input.nextLine();
            workOrder.setSenderName(senderName);
        }

        return workOrder;
    }


    public static void main(String args[]) {
        //In Creator have a public static void main that creates an instance of Creator
        //and calls the instance method that
        //loops to get the user input and create work order files.

        Creator creator = new Creator();
        creator.createWorkOrders();
    }
}
