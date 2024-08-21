package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ImplementingDataAndTime {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        System.out.print("enter booking date(dd-MM-yyyy HH:mm:ss) : ");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime today = LocalDateTime.now();
        while (true) {
            try {
                String bookingDate = scn.nextLine();
                LocalDateTime inputDate = LocalDateTime.parse(bookingDate, dateTimeFormatter);
                LocalDateTime maxDate = today.plusDays(30);
                if (inputDate.isBefore(today)) {
                    System.out.print("Invalid date. please enter future date : ");
                }else if(inputDate.isAfter(maxDate)) {
                    System.out.print("enter a date within next 30 days : ");
                }else {
                    break;
                }
            } catch (DateTimeParseException e) {
                System.out.print("Invalid format/numbers. please enter valid date in this format(dd-MM-yyyy) : ");
            }
        }
        System.out.println(" ");
        int numberOfDays = 5;
        LocalDateTime endDate = today.plusDays(numberOfDays-1);
        String returnDate = endDate.format(dateTimeFormatter);
    }
}
