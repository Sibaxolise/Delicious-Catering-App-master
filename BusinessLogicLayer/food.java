package BusinessLogicLayer;

public abstract class food 
{
    /* Booking Questions */
    //The client can update the selected food menu before booking confirmation.
    //Food- selection from the menu
    int eventID;
    int numberOfDishes;
    int vegetarianMealAmount;
    int veganMealAmount;

    public abstract int sumTotal();
}