package BusinessLogicLayer;

import DataAccessLayer.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.sql.SQLException;
import java.text.ParseException;

public class Logic 
{
    //class's
    sqlDbConnection Conn = new sqlDbConnection();
    
    
    //variables


    //methods

    public void storeCustomer(customer details) /* remove customer ID, booking number and customer Paid;*/
    {
        Conn.insertCustomer(details.customerName, details.customerSurname, details.customerPhoneNumber);
    }

    public void storeEvent(event details) /* remove customer ID, eventId;*/
    {
        Conn.insertEvent(details.eventDate, details.eventName, details.eventType, details.eventVenue, details.eventNumberOfPeople, details.eventCost, details.themeFlag, details.theme, details.decorationFlag, details.decorationDetails);
    }

    public customer getCustomerinput(customer client)
    {
        return client;
    }


    //setDate format
    public String DateFormat(int day,int month,int year, int hours, int minutes, int seconds)
    {
        String newDate;
        //DD-MM-YYYY
        newDate = day + "-" + month + "-" + year + " " + hours + "-" + minutes + "-" + seconds;;
        // SimpleDateFormat sdf = new SimpleDateFormat("DD-MM-YYYY");
        // Date date = sdf.parse(newDate);
        return newDate;
    }


    /* Starting logic */
    //one booking a day. (checks dataBase if there is an event stored on the day.)
    public Boolean oneBookingDayCheck(String FormattedDate) throws SQLException
    {
        Boolean dateExists = false;

        if (Conn.bookingCheck(FormattedDate)) 
        {
            dateExists = true;
        }

        return dateExists;
    }

    //confirm the booking by 15 days prior to the event by giving 50 % of the calculated total price.
    public boolean processConfirmationPayment(String eventDate, Float paidAmount, Float amountOutstanding)
    {
        boolean confirmationFlag = false;
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH-mm-ss");

        Date eventDateFormat = new Date();
        try 
        {
            eventDateFormat = sdf.parse(eventDate);
        } 
        catch (ParseException e) 
        {
            System.out.println("Caught exception");
            e.printStackTrace();
        }

        long dateDiff = dateDiffToday(eventDateFormat); /* subtrack event date from todaysDate */

        Float halfOutStandingAmount = amountOutstanding * 0.5f;
        if (dateDiff >= 15 && paidAmount >= halfOutStandingAmount) 
        {
            confirmationFlag = true;
        }

        return confirmationFlag;
    }
    
    public Long dateDiffToday(Date eventDate)
    {
        //getting current date
        Date today =java.util.Calendar.getInstance().getTime();

        long diffInMillies = Math.abs(eventDate.getTime() - today.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return diff;
    }
    
    //If the total number of people is above 40 there is a 15% discount from the calculated total adult's meal price.
    public float discountCheck(int amountOfPeople, Float eventCost)
    {
        float newMealCost = eventCost;
        float discountPercentage = 0.15f;

        if (amountOfPeople > 40)
        {
            newMealCost = newMealCost - (newMealCost * discountPercentage);
        }

        return newMealCost;
    }


    
    /* Ending logic */
    //Miss. Raheal should be able to see all her bookings,
    public ArrayList<String> viewAllBookings()
    {   
        ArrayList<String> allBookings = Conn.viewEvents(); 

        return allBookings;
    }

    //see all confirmed/non confirmed bookings
    public ArrayList<String> viewAllConfirmedCases()
    {
        ArrayList<String> viewAllCOnfirmed = Conn.viewConfirmedBookings(1);  

        return viewAllCOnfirmed;
    }

    public ArrayList<String> viewAllNonConfirmedCases()
    {
        ArrayList<String> viewAllNonCOnfirmed = Conn.viewConfirmedBookings(0); 

        return viewAllNonCOnfirmed;
    }

    

    //The system must send a notification to the client once the booking is confirmed by Miss. Rachael.
    /* Use this method to set the confirmation status on the customer object to true */
    public boolean ConfirmedByMissRachael(event PlannedEvent)
    {
        return true;
    }


    /* extra features */
    //show available dates for customers
    public ArrayList<String> viewAvailableDates()
    {
        ArrayList<String> allAvailable = new ArrayList<String>(); 

        return allAvailable;
    }

    public adultFood getAdultMealValues(adultFood amountOfMeals)
    {
        //classes

        //vars
        int chickenCost = 25;
        int steakCost = 50;
        int gammonRoastCost = 30;
        int pastaCost = 25;
        int soupCost = 15;
        int lobsterBisqueCost = 70;

        //methods
        amountOfMeals.chickenMeals = amountOfMeals.chickenMeals * chickenCost;
        amountOfMeals.steakMeals = amountOfMeals.steakMeals * steakCost;
        amountOfMeals.gammonRoastMeals = amountOfMeals.gammonRoastMeals * gammonRoastCost;
        amountOfMeals.pastaMeals = amountOfMeals.pastaMeals * pastaCost;
        amountOfMeals.soupMeals = amountOfMeals.soupMeals * soupCost;
        amountOfMeals.lobsterBisqueMeals = amountOfMeals.lobsterBisqueMeals * lobsterBisqueCost;

        return amountOfMeals;
    }

    public childFood getChildMealValues(childFood amountOfMeals)
    {
        //classes
        
        //vars
        int hotDogCost = 10;
        int chickenburgerCost = 20;
        int beefBurgerCost = 20;
        int fishChipsCost = 30;
        int pizzaCost = 25;
        int pieCost = 10;

        //methods
        amountOfMeals.hotDogMeals = amountOfMeals.hotDogMeals * hotDogCost;
        amountOfMeals.chickenburgerMeals = amountOfMeals.chickenburgerMeals * chickenburgerCost;
        amountOfMeals.beefBurgerMeals = amountOfMeals.beefBurgerMeals * beefBurgerCost;
        amountOfMeals.fishChipsMeals = amountOfMeals.fishChipsMeals * fishChipsCost;
        amountOfMeals.pizzaMeals = amountOfMeals.pizzaMeals * pizzaCost;
        amountOfMeals.pieMeals = amountOfMeals.pieMeals * pieCost;

        return amountOfMeals;
    }


    //this method is used to calculate the cost of the event
    public float calculateEventCosts(int NumberOfPeople, adultFood adultAmount, childFood childAmount)
    {
        //classes

        //vars
        float eventCost = 0;
        float entryCost = 100; //default number if 100 can change to any figure

        //method
        eventCost = (NumberOfPeople * entryCost) + adultAmount.sumTotal() + childAmount.sumTotal();


        return eventCost;
    }

    public void updateEventPaidInDB(event info)
    {
        Conn.updateEventPaid(info.eventDate, info.eventCost);
    }

    public void updateEventInfo(event info)
    {
        Conn.updateEvent(info.eventDate, info.eventName, info.eventType, info.eventVenue, info.eventNumberOfPeople , info.eventCost, info.themeFlag, info.theme, info.decorationFlag, info.decorationDetails);
    }
}