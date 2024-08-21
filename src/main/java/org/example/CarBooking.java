package org.example;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;


public class CarBooking {
    public static void main(String[] args) throws IOException {
        String filepath = "C:\\Project\\ExcelData\\CarBookingData\\CarBookingDetails.xlsx";
        Scanner scn = new Scanner(System.in);

        System.out.print("enter your mobile number : ");
        String mobileNumber = "";
        for (int i = 0; i < 1; i++) {
            mobileNumber = scn.nextLine();
            if (mobileNumber.matches("\\d{10}")) {
            } else {
                System.out.print("enter a valid 10 digit mobile number : ");
                i--;
            }
        }

        boolean alreadyBooked = false;

        try(FileInputStream inputStream = new FileInputStream(filepath);
                  XSSFWorkbook workbook = new XSSFWorkbook(inputStream)){
            XSSFSheet userSheet = workbook.getSheetAt(1);
            for (Row row:userSheet){
                Cell mobileNumberCell = row.getCell(0);
                if(mobileNumberCell != null && mobileNumberCell.getCellType()== CellType.STRING){
                    if(mobileNumberCell.getStringCellValue().equalsIgnoreCase(mobileNumber)){
                        System.out.println("By Using this number your already booked one car.");
                        System.out.println(" ");
                        System.out.println("      #---------------------------------------------------#");
                        System.out.println("      |                                                   |");
                        System.out.println("      |  Booking Details :-                               |");
                        System.out.println("      |  ------------------                               |");
                        System.out.println("      |  Customer Name : " + row.getCell(6));
                        System.out.println("      |  Car Model : " + row.getCell(1));
                        System.out.println("      |  Takeover Date : " + row.getCell(2));
                        System.out.println("      |  Return Date : " + row.getCell(3));
                        System.out.println("      |  Amount : " + row.getCell(5));
                        System.out.println("      |                                                   |");
                        System.out.println("      #---------------------------------------------------#");
                        System.out.println(" ");
                        alreadyBooked = true;
                    }
                }

            }
        }


        if(alreadyBooked == true){
            System.out.print("you want to another car (yes or no) : ");
            for(int i = 0;i<1;i++) {
                String repeatBooking = scn.nextLine();
                if (repeatBooking.equalsIgnoreCase("yes")) {
                    alreadyBooked = false;
                } else if (repeatBooking.equalsIgnoreCase("no")) {
                    System.out.println("Thank You");
                } else {
                    System.out.print("enter valid input (yes or no) : ");
                    i--;
                }
            }
        }


        if(alreadyBooked == false) {
            try (FileInputStream inputStream = new FileInputStream(filepath);
                 XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
                XSSFSheet carSheet = workbook.getSheetAt(0);


                String selectedCarBrand = null;
                String selectedCarModel = null;
                int totalRentOfaCar = 0;
                int advanceAmount = 0;
                int numberOfHours = 0;
                String bookingDateTime = null;
                String returnTime = null;
                String returnDate = null;
                int numberOfDays = 0;
                for (int k = 0; k < 1; k++) {
                    List<String> brands = new ArrayList<>();
                    int x = 0;
                    for (Row row : carSheet) {
                        if (x == 0) {
                            Cell cell = row.getCell(0);
                            x++;
                        } else {
                            Cell cell = row.getCell(0);
                            brands.add(String.valueOf(cell));
                        }
                    }
                    Set<String> uniqueBrandsSet = new HashSet<>();
                    for (int i = 0; i < brands.size(); i++) {
                        String brand = brands.get(i);
                        uniqueBrandsSet.add(brand.toUpperCase());
                    }
                    List<String> uniqueBrandsList = new ArrayList<>();
                    uniqueBrandsList.addAll(uniqueBrandsSet);
                    System.out.println(" ");
                    System.out.println("available Car Brands :");
                    for (int i = 0; i < uniqueBrandsList.size(); i++) {
                        System.out.println((i + 1) + "." + uniqueBrandsList.get(i));
                    }

                    System.out.println(" ");
                    System.out.print("select Car Brand (eg., 1): ");
                    int brandNumber = scn.nextInt();
                    if (brandNumber > uniqueBrandsList.size()) {
                        System.out.print("select a valid car brand that are shown in above list : ");
                        brandNumber = scn.nextInt();
                    }
                    selectedCarBrand = uniqueBrandsList.get(brandNumber - 1);

                    List<String> carModelsList = new ArrayList<>();
                    for (Row row : carSheet) {
                        Cell cell = row.getCell(0);
                        String carBrand = String.valueOf(cell);
                        if (carBrand.equalsIgnoreCase(selectedCarBrand)) {
                            String carModel = String.valueOf(row.getCell(1));
                            carModelsList.add(carModel);
                        }
                    }
                    System.out.println(" ");
                    System.out.println("available models of " + selectedCarBrand + " car brand :");
                    for (int i = 0; i < carModelsList.size(); i++) {
                        System.out.println((i + 1) + "." + carModelsList.get(i));
                    }

                    System.out.println(" ");
                    System.out.print("select model (eg., 1) : ");
                    int modelNumber = scn.nextInt();
                    if (modelNumber > uniqueBrandsList.size()) {
                        System.out.print("select a valid car model that are shown in above list : ");
                        modelNumber = scn.nextInt();
                    }
                    selectedCarModel = carModelsList.get(modelNumber - 1);

                    int dayPrice = 0;
                    int hourPrice = 0;
                    for (Row row : carSheet) {
                        String carModel = String.valueOf(row.getCell(1));
                        if (selectedCarModel.equalsIgnoreCase(carModel)) {
                            dayPrice = (int) (row.getCell(2)).getNumericCellValue();
                            hourPrice = (int) (row.getCell(3)).getNumericCellValue();
                        }
                    }

                    System.out.println(" ");
                    System.out.println(selectedCarModel + " car rent price :");
                    System.out.println("per-hour : " + hourPrice + "/-");
                    System.out.println("per-day : " + dayPrice + "/-");

                    System.out.println(" ");
                    scn.nextLine();
                    numberOfDays = 0;
                    numberOfHours = 0;
                    for (int i = 0; i < 1; i++) {
                        System.out.print("select your preferred option('day' or 'hour') : ");
                        String prefferedOption = scn.nextLine();
                        System.out.println(" ");
                        if (prefferedOption.equalsIgnoreCase("day")) {
                            System.out.print("enter how many days you want to book : ");
                            numberOfDays = scn.nextInt();
                        } else if (prefferedOption.equalsIgnoreCase("hour")) {
                            System.out.print("enter how many hours you want to book : ");
                            numberOfHours = scn.nextInt();
                            if (numberOfHours > 23) {
                                System.out.println("please choose day option instead of hour");
                                System.out.println(" ");
                                i--;
                                scn.nextLine();
                                numberOfHours = 0;
                            }
                        } else {
                            System.out.println("enter valid input");
                            i--;
                        }
                    }
                    System.out.println(" ");
                    totalRentOfaCar = 0;
                    if (numberOfDays > 0) {
                        totalRentOfaCar = numberOfDays * dayPrice;
                        System.out.println("Total Rent Amount : " + totalRentOfaCar + "/-");
                        System.out.println("In advance you need pay :" + (totalRentOfaCar / 5) + "/-");
                        System.out.println(" ");
                    }
                    if (numberOfHours > 0) {
                        totalRentOfaCar = numberOfHours * hourPrice;
                        System.out.println("Total Rent Amount : " + totalRentOfaCar + "/-");
                        System.out.println(" ");
                    }
                    advanceAmount = totalRentOfaCar / 5;


                    scn.nextLine();
                    for (int i = 0; i < 1; i++) {
                        System.out.print("you want to conform your booking ('yes' / 'no') : ");
                        String bookingConformation = scn.nextLine();
                        if (bookingConformation.equalsIgnoreCase("yes")) {
                            System.out.print("enter takeover date with time(dd-MM-yyyy HH:mm:ss) : ");
                            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                            LocalDateTime today = LocalDateTime.now();
                            while (true) {
                                try {
                                    bookingDateTime = scn.nextLine();
                                    LocalDateTime inputDate = LocalDateTime.parse(bookingDateTime, dateTimeFormatter);
                                    LocalDateTime maxDate = today.plusDays(30);
                                    LocalDateTime endDate = inputDate.plusDays(numberOfDays - 1);
                                    LocalDateTime endTime = inputDate.plusHours(numberOfHours);
                                    if (inputDate.isBefore(today)) {
                                        System.out.print("Invalid date. please enter future date : ");
                                    } else if (inputDate.isAfter(maxDate)) {
                                        System.out.print("enter a date within next 30 days : ");
                                    } else {
                                        returnDate = endDate.format(dateTimeFormatter);
                                        returnTime = endTime.format(dateTimeFormatter);
                                        break;
                                    }
                                } catch (DateTimeParseException e) {
                                    System.out.print("Invalid format/numbers. please enter valid date in this format(dd-MM-yyyy HH:mm:ss) : ");
                                }
                            }
                            System.out.println(" ");


                        } else if (bookingConformation.equalsIgnoreCase("no")) {
                            for (int j = 0; j < 1; j++) {
                                System.out.print("are you sure you want cancel your booking (yes/no) : ");
                                String lastCancelConformation = scn.nextLine();
                                if (lastCancelConformation.equalsIgnoreCase("yes")) {
                                    System.out.println("your booking cancelled.");
                                    System.exit(0);
                                } else if (lastCancelConformation.equalsIgnoreCase("no")) {
                                    for (int n = 0; n < 1; n++) {
                                        System.out.println("1. continue with this car");
                                        System.out.println("2. want to book another car");
                                        System.out.println("choose your option : ");
                                        int option = scn.nextInt();
                                        if (option == 1) {
                                            scn.nextLine();
                                            i--;
                                        } else if (option == 2) {
                                            k--;
                                        } else {
                                            System.out.println("enter valid input");
                                        }
                                    }
                                } else {
                                    System.out.println("choose valid input option");
                                    j--;
                                }
                            }

                        } else {
                            System.out.println("enter valid input");
                            i--;
                        }
                    }
                }

                System.out.print("enter your card details (xxxx xxxx xxxx xxxx) :");
                String cardNumber = "";
                for (int i = 0; i < 1; i++) {
                    cardNumber = scn.nextLine();
                    if (cardNumber.matches("\\d{4} \\d{4} \\d{4} \\d{4}")) {
                    } else {
                        System.out.print("enter card details in this format (xxxx xxxx xxxx xxxx) : ");
                        i--;
                    }
                }

                System.out.print("Enter your Name : ");
                String customerName = scn.nextLine();
                System.out.print("Enter your Address : ");
                String address = scn.nextLine();
                System.out.println(" ");

                System.out.println("                         ******  your booking conformed  ******");
                System.out.println(" ");

                System.out.println("        #----------------------------------------------------------------------#");
                System.out.println("        |  Booking Details :                                                   |");
                System.out.println("        |  -----------------                                                   |");
                System.out.println("        |  your Mobile Number : " + mobileNumber + "                                     |");
                System.out.println("        |  Name : " + customerName);
                System.out.println("        |  Delivery Address : " + address);
                System.out.println("        |  Car Brand : " + selectedCarBrand);
                System.out.println("        |  Car Model : " + selectedCarModel);
                System.out.println("        |  Advance amount you paid : " + advanceAmount);
                System.out.println("        |  Remaining Amount you need pay : " + (totalRentOfaCar - advanceAmount));

                if (numberOfHours > 0) {
                    System.out.println("        |  your takeover time : " + bookingDateTime + "                            |");
                    System.out.println("        |  your returning time : " + returnTime + "                           |");
                    System.out.println("        |                                                                      |");
                    System.out.println("        |  NOTE : please return our car before end time.                       |");
                    System.out.println("        |         you need to pay your remaining amount before taking our car. |");
                    System.out.println("        #----------------------------------------------------------------------#");
                    System.out.println(" ");
                    addToExcel(filepath, mobileNumber, selectedCarModel, bookingDateTime, returnTime, address, totalRentOfaCar,customerName);
                }
                if (numberOfDays > 0) {
                    System.out.println("        |  your takeover date : " + bookingDateTime + "                            |");
                    System.out.println("        |  your returning date : " + returnDate + "                           |");
                    System.out.println("        |                                                                      |");
                    System.out.println("        |  NOTE : please return our car before end date.                       |");
                    System.out.println("        |         you need to pay your remaining amount before taking our car. |");
                    System.out.println("        #----------------------------------------------------------------------#");
                    System.out.println(" ");
                    addToExcel(filepath, mobileNumber, selectedCarModel, bookingDateTime, returnDate, address, totalRentOfaCar,customerName);
                }

                System.out.println(" ");
                System.out.println("-----------------------------------------------------------------------------------------------");
                System.out.println("              Thank You for Booking with us, have a HAPPY and SAFE Journey ");


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void addToExcel(String filepath,String mobileNumber, String selectedCarModel, String bookingDateTime, String returnTime, String address,int totalRent,String customerName) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(filepath);
             XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            XSSFSheet userSheet = workbook.getSheetAt(1);

            int lastRowNumber = userSheet.getPhysicalNumberOfRows();
            Row row = userSheet.createRow(lastRowNumber);

            row.createCell(0).setCellValue(mobileNumber);
            row.createCell(1).setCellValue(selectedCarModel);
            row.createCell(2).setCellValue(bookingDateTime);
            row.createCell(3).setCellValue(returnTime);
            row.createCell(4).setCellValue(address);
            row.createCell(5).setCellValue(totalRent);
            row.createCell(6).setCellValue(customerName);

            try(FileOutputStream fileOutputStream = new FileOutputStream(filepath)){
                workbook.write(fileOutputStream);
            }

        }

    }

}
