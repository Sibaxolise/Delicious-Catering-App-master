package PresentationLayer;

import BusinessLogicLayer.*;
import DataAccessLayer.sqlDbConnection;


import javax.swing.text.ChangedCharSetException;
import javax.swing.*;
import java.sql.Date;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;

public class UserInterface
{
    static Scanner SC;
    public static void main(String[] args) throws SQLException
    {
        //classes
        SC = new Scanner(System.in);
        Logic logicController = new Logic();


        //variables
        boolean loopEnd3 = false;

        //methods

        WelcomeMsg(); //start of program
        customer client = getCustomerInfo(); //gets Initial customer info
        logicController.storeCustomer(client); /* uncomment when method is fixed */
        event eventObjInfo = new event(); //gets Initial event info
        



        /*make this a method if possible */
        /*if it is too difficult to implement in methods we can just make a nested switch statement */
        do
        {
            int choose = menuOptions();

            switch (choose) 
            {
                case 1:
                    eventObjInfo = getEventInfo();
                    logicController.storeEvent(eventObjInfo);
                    break;
                    
                case 2:
                    //edit a booking

                    //ask for the date of the booking
                    System.out.println("Enter date for event to change");
                    String newDate = dateConfirm();
                    //change everything but the date
                    event newEventInfo = setEventInfo(newDate);
                    //update DB method
                    logicController.updateEventInfo(newEventInfo);
                    break;

                case 3:
                    confirmationPayment(eventObjInfo);
                    break;

                case 4:
                    Payment(eventObjInfo);
                    break;
                    
                case 5:
                    ArrayList<String> allBookings = logicController.viewAllBookings();

                    for (String item : allBookings) {
                        System.out.println(item);
                    }
                    break;

                case 6:
                    ArrayList<String> allConfirmedBookings = logicController.viewAllConfirmedCases();

                    for (String item : allConfirmedBookings) {
                        System.out.println(item);
                    }
                    break;

                case 7:
                    ArrayList<String> allNonConfirmedBookings = logicController.viewAllNonConfirmedCases();

                    for (String item : allNonConfirmedBookings) {
                        System.out.println(item);
                    }
                    break;

                case 8:
                    loopEnd3 = true;
                    break;

                default:
                    System.out.println("Please enter a value between 1 and 6");
                    break;
            }


        } while (loopEnd3 != true);



        

        
        //closing 
        SC.close();
    }




    static void WelcomeMsg() 
    {
        System.out.println("Welcome to Delicious-Catering online booking system");
        System.out.println("To register please complete the following questions: ");
        System.out.println("---------------------------------------------------------"); 
    }

    static customer getCustomerInfo()
    {
        //classes
        SC = new Scanner(System.in);
        
        //method
        System.out.println("Please enter your first name:"); 
        String customerName = SC.nextLine();
        System.out.println("============"); 
        
        System.out.println("Please enter your last name:"); 
        String customerSurname = SC.nextLine();
        System.out.println("============"); 
        
        System.out.println("Please enter your Phone Number:"); 
        String customerPhoneNumber = SC.nextLine(); 
        System.out.println("============"); 

        
        customer cust = new customer(customerName, customerSurname, customerPhoneNumber);
        new Register(); //shows user they have been registered through a jframe pop-up
        return cust;
    }

    static String getFunctionDate()
    {
        //classes
        Logic logicController = new Logic();
        SC = new Scanner(System.in);
        //vars
        String formatDate;

        //method
        System.out.println("Please enter the day the event will take place.");
        int day = SC.nextInt();
        System.out.println("Please enter the month the event will take place.");
        int month = SC.nextInt();
        System.out.println("Please enter the year the event will take place.");
        int year = SC.nextInt();

        System.out.println("Please enter the hour the event will take place.");
        int hours = SC.nextInt();
        System.out.println("Please enter the minute the event will take place.");
        int minutes = SC.nextInt();
        int seconds = 0;

        formatDate = logicController.DateFormat(day, month, year, hours, minutes, seconds);

        return formatDate;
    }

    static String dateConfirm() throws SQLException
    {
        //classes
        SC = new Scanner(System.in);
        Logic LC = new Logic();
        //vars
        boolean dateConfirmFlag = false;
        String newDate = getFunctionDate();
        // boolean oneDayCheck = LC.oneBookingDayCheck(newDate);            /* This checks for One booking a day VERY IMPORTANT!!!! */
        // //method
        // while (oneDayCheck == false) 
        // {
        //     newDate = getFunctionDate();
        //     oneDayCheck = LC.oneBookingDayCheck(newDate);
        // }
        
        while (dateConfirmFlag != true)
        {
            System.out.println("Is this date correct: " + newDate);
            System.out.println("Please enter y for yes or n for no");
            String userInput = SC.nextLine().toLowerCase();

            switch (userInput) 
            {
                case "y":
                    dateConfirmFlag = true;
                    break;

                case "n":
                    newDate = getFunctionDate();
                    break;
            
                default:
                    System.out.println("Please enter y or n");
                    break;
            } 
        }
        

        return newDate;
    }

    static event getEventInfo() throws SQLException
    {
        //classes
        Logic LC = new Logic();
        SC = new Scanner(System.in);
        //vars
        boolean ThemeFlag = false;
        boolean DecorationFlag = false;
        boolean loopEnd = false;
        boolean loopEnd2 = false;
        
        //method
        String formatDate = dateConfirm();

        System.out.println("Enter event name: ");
        String EventName = SC.nextLine();

        System.out.println("Enter event type: ");
        String EventType = SC.nextLine();
        
        
        System.out.println("Enter event venue: ");
        String EventVenue = SC.nextLine();



        System.out.println("Enter number of people at event: ");
        int EventNumberOfPeople = SC.nextInt();

        

        //theme parameters
        do 
        {
            System.out.println("Do you have a theme? Y or N");
            String ThemeAnswer = SC.nextLine().toLowerCase();

            switch (ThemeAnswer) 
            {
                case "y":
                    ThemeFlag = true;
                    loopEnd = true;
                    break;

                case "n":
                    ThemeFlag = false;
                    loopEnd = true;
                    break;
            
                default:
                    System.out.println("Please enter y or n");
                    break;
            }
        } while (loopEnd != true);

        String Theme;
        if (ThemeFlag == true)
        {
            System.out.println("Enter theme of the event: ");
            Theme = SC.nextLine();
        }
        else
        {
            Theme = "NA";
        }
        
        
        
        //decoration parameters
        do 
        {
            System.out.println("Do you have decorations? Y or N");
            String ThemeAnswer = SC.nextLine().toLowerCase();
            
            switch (ThemeAnswer) 
            {
                case "y":
                ThemeFlag = true;
                loopEnd2 = true;
                break;
                
                case "n":
                ThemeFlag = false;
                loopEnd2 = true;
                break;
                
                default:
                System.out.println("Please enter y or n");
                break;
            }
        } while (loopEnd2 != true);
        
        
        String DecorationDetails;
        if (DecorationFlag == true) 
        {
            System.out.println("Enter decorations needed for the event: ");
            DecorationDetails = SC.nextLine();
        }
        else
        {
            DecorationDetails  = "NA";
        }


        //process adultFood values
        System.out.println("Number of chicken meals: ");
        int ChickenMeals = SC.nextInt();
        System.out.println("Number of Steak Meals: ");
        int SteakMeals = SC.nextInt();
        System.out.println("Number of GammonRoast Meals: ");
        int GammonRoastMeals = SC.nextInt();
        System.out.println("Number of Pasta Meals: ");
        int PastaMeals = SC.nextInt();
        System.out.println("Number of Soup Meals: ");
        int SoupMeals = SC.nextInt();
        System.out.println("Number of LobsterBisque Meals: ");
        int LobsterBisqueMeals = SC.nextInt();

        adultFood numberOfAdultMeals = new adultFood(ChickenMeals, SteakMeals, GammonRoastMeals, PastaMeals, SoupMeals, LobsterBisqueMeals);

        adultFood adultMealCost = LC.getAdultMealValues(numberOfAdultMeals);


        //process childFood values
        System.out.println("Number of HotDog Meals: ");
        int HotDogMeals = SC.nextInt();
        System.out.println("Number of Chickenburger Meals: ");
        int ChickenburgerMeals = SC.nextInt();
        System.out.println("Number of BeefBurger Meals: ");
        int BeefBurgerMeals = SC.nextInt();
        System.out.println("Number of FishChips Meals: ");
        int FishChipsMeals = SC.nextInt();
        System.out.println("Number of Pizza Meals: ");
        int PizzaMeals = SC.nextInt();
        System.out.println("Number of Pie Meals: ");
        int PieMeals = SC.nextInt();

        childFood numberOfChildMeals = new childFood(HotDogMeals, ChickenburgerMeals, BeefBurgerMeals, FishChipsMeals, PizzaMeals, PieMeals);

        childFood childMealCost = LC.getChildMealValues(numberOfChildMeals);
        //final calculation
        Float EventInitialCost = LC.calculateEventCosts(EventNumberOfPeople, adultMealCost, childMealCost);
        Float EventCost = LC.discountCheck(EventNumberOfPeople, EventInitialCost);



        event eventInfo = new event(formatDate, EventName, EventType, EventVenue, EventNumberOfPeople, EventCost, ThemeFlag, Theme, DecorationFlag, DecorationDetails);
        return eventInfo;
        



    }

    static event setEventInfo(String formatedDate) throws SQLException
    {
        //classes
        Logic LC = new Logic();
        SC = new Scanner(System.in);
        //vars
        boolean ThemeFlag = false;
        boolean DecorationFlag = false;
        boolean loopEnd = false;
        boolean loopEnd2 = false;
        
        //method
        String formatDate = formatedDate;

        System.out.println("Enter event name: ");
        String EventName = SC.nextLine();

        System.out.println("Enter event type: ");
        String EventType = SC.nextLine();
        
        
        System.out.println("Enter event venue: ");
        String EventVenue = SC.nextLine();



        System.out.println("Enter number of people at event: ");
        int EventNumberOfPeople = SC.nextInt();

        

        //theme parameters
        do 
        {
            System.out.println("Do you have a theme? Y or N");
            String ThemeAnswer = SC.nextLine().toLowerCase();

            switch (ThemeAnswer) 
            {
                case "y":
                    ThemeFlag = true;
                    loopEnd = true;
                    break;

                case "n":
                    ThemeFlag = false;
                    loopEnd = true;
                    break;
            
                default:
                    System.out.println("Please enter y or n");
                    break;
            }
        } while (loopEnd != true);

        String Theme;
        if (ThemeFlag == true)
        {
            System.out.println("Enter theme of the event: ");
            Theme = SC.nextLine();
        }
        else
        {
            Theme = "NA";
        }
        
        
        
        //decoration parameters
        do 
        {
            System.out.println("Do you have decorations? Y or N");
            String ThemeAnswer = SC.nextLine().toLowerCase();
            
            switch (ThemeAnswer) 
            {
                case "y":
                ThemeFlag = true;
                loopEnd2 = true;
                break;
                
                case "n":
                ThemeFlag = false;
                loopEnd2 = true;
                break;
                
                default:
                System.out.println("Please enter y or n");
                break;
            }
        } while (loopEnd2 != true);
        
        
        String DecorationDetails;
        if (DecorationFlag == true) 
        {
            System.out.println("Enter decorations needed for the event: ");
            DecorationDetails = SC.nextLine();
        }
        else
        {
            DecorationDetails  = "NA";
        }


        //process adultFood values
        System.out.println("Number of chicken meals: ");
        int ChickenMeals = SC.nextInt();
        System.out.println("Number of Steak Meals: ");
        int SteakMeals = SC.nextInt();
        System.out.println("Number of GammonRoast Meals: ");
        int GammonRoastMeals = SC.nextInt();
        System.out.println("Number of Pasta Meals: ");
        int PastaMeals = SC.nextInt();
        System.out.println("Number of Soup Meals: ");
        int SoupMeals = SC.nextInt();
        System.out.println("Number of LobsterBisque Meals: ");
        int LobsterBisqueMeals = SC.nextInt();

        adultFood numberOfAdultMeals = new adultFood(ChickenMeals, SteakMeals, GammonRoastMeals, PastaMeals, SoupMeals, LobsterBisqueMeals);

        adultFood adultMealCost = LC.getAdultMealValues(numberOfAdultMeals);


        //process childFood values
        System.out.println("Number of HotDog Meals: ");
        int HotDogMeals = SC.nextInt();
        System.out.println("Number of Chickenburger Meals: ");
        int ChickenburgerMeals = SC.nextInt();
        System.out.println("Number of BeefBurger Meals: ");
        int BeefBurgerMeals = SC.nextInt();
        System.out.println("Number of FishChips Meals: ");
        int FishChipsMeals = SC.nextInt();
        System.out.println("Number of Pizza Meals: ");
        int PizzaMeals = SC.nextInt();
        System.out.println("Number of Pie Meals: ");
        int PieMeals = SC.nextInt();

        childFood numberOfChildMeals = new childFood(HotDogMeals, ChickenburgerMeals, BeefBurgerMeals, FishChipsMeals, PizzaMeals, PieMeals);

        childFood childMealCost = LC.getChildMealValues(numberOfChildMeals);
        //final calculation
        Float EventInitialCost = LC.calculateEventCosts(EventNumberOfPeople, adultMealCost, childMealCost);
        Float EventCost = LC.discountCheck(EventNumberOfPeople, EventInitialCost);



        event eventInfo = new event(formatDate, EventName, EventType, EventVenue, EventNumberOfPeople, EventCost, ThemeFlag, Theme, DecorationFlag, DecorationDetails);
        return eventInfo;
        



    }
    // public adultFood(int ChickenMeals, int SteakMeals, int GammonRoastMeals, int PastaMeals, int SoupMeals, int LobsterBisqueMeals)
    // {
    //     this.chickenMeals = ChickenMeals;
    //     this.steakMeals = SteakMeals;
    //     this.gammonRoastMeals = GammonRoastMeals;
    //     this.pastaMeals = PastaMeals;
    //     this.soupMeals = SoupMeals;
    //     this.lobsterBisqueMeals = LobsterBisqueMeals;
    // }

    static void menu(customer client, event eventInfo) throws SQLException
    {
        SC = new Scanner(System.in);
        boolean loopEnd3 = false;

            
        
    }

    static void editBooking() throws SQLException
    {
        //classes
        SC = new Scanner(System.in);

        //vars
        boolean loopEnd4 = false;

        //method
        do
        {
        
        int choose = editMenuOptions();

            switch (choose) 
            {
            
                case 1:
                    getCustomerInfo(); //edit First Name, Last Name or phone number"
                    break;
            
                case 2:
                    getFunctionDate(); //edit Function Date
                    dateConfirm();
                    break;
            
                case 3:
                    getEventInfo(); //Event info like, location, venue, decoration, theme or food
                    break;
                    
                case 4:
                    loopEnd4 = true;
                    break;
                
                    
                default:
                    break;
            }
        
        } while (loopEnd4 != true);
    }

    static int menuOptions()
    {
        SC = new Scanner(System.in);
        /*
        System.out.println("Menu: ");
        System.out.println("================================================");
        System.out.println("1. Add a customer");
        System.out.println("2. Make a new booking");
        System.out.println("3. Edit a booking");
        System.out.println("4. Check confermation status");
        System.out.println("5. Make a payment");             
        System.out.println("6. Exit");
        */
        //Method will display the values of the enum items
        Menu.DisplayMenu();

        int choose = SC.nextInt(); 

        return choose;
    }

    static int editMenuOptions()
    {
        SC = new Scanner(System.in);

        System.out.println("What do you want to change: ");
        System.out.println("================================================");
        System.out.println("1. First Name, Last Name or phone number");
        System.out.println("2. Function Date");
        System.out.println("3. Event info like, location, venue, decoration, theme or food");          
        System.out.println("4. Exit");
        int choose = SC.nextInt(); 

        return choose;
    }

    static void confirmationPayment(event info)
    {
        SC = new Scanner(System.in);
        //event output constructor date
        Logic LC = new Logic();
        
        System.out.println("To confirm this event a deposit of 50% of the events cost is required 15 days before the event");
        Float paidAmount = info.eventCost * 0.5f;
        System.out.println("The amount required is R" + paidAmount + ". Do you want to process this payment. Please enter y or n");
        String userInput = SC.nextLine().toLowerCase();
        boolean acceptablePayment = false;
        boolean exitFlag = false;

        while (exitFlag == false) 
        {
            switch (userInput) 
            {
                case "y":
                    acceptablePayment = LC.processConfirmationPayment(info.eventDate, paidAmount, info.eventCost);
                    exitFlag = true;
                    break;

                case "n":
                    exitFlag = true;
                    break;
            
                default:
                    System.out.println("Please enter y or n");
                    break;
            }
        }
        
        
        if (acceptablePayment == true) 
        {
            System.out.println("The payment was processed successfully!");
        }
    }

    static void Payment(event info)
    {
        //classes
        SC = new Scanner(System.in);
        Logic LC = new Logic();
        //vars
        boolean exitFlag = false;

        //method
        //take in event cost and half it
        Float eventCostRemaining = info.eventCost * 0.5f;
        //then ask the user if they would like to pay the remainer of the price
        System.out.println("There is R" + eventCostRemaining + ". Would you like to process this payment? y or n");
        String userInput = SC.nextLine().toLowerCase();

        while (exitFlag == false) 
        {
            switch (userInput) 
            {
                case "y":
                    LC.updateEventPaidInDB(info);
                    exitFlag = true;
                    break;

                case "n":
                    System.out.println("Payment not processed");
                    exitFlag = true;
                    break;
            
                default:
                    System.out.println("Please enter y or n");
                    break;
            }
        }
        
        
    }

}