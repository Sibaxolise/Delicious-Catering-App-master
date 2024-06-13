package BusinessLogicLayer;

public class customer 
{
    //People order food from her for different events
    
    //Once the booking has taken place she gives a booking number.
    //The client must be able to register into the system with required details such as name, surname and phone number etc.
    public int customerID;
    public String customerName;
    public String customerSurname;
    public String customerPhoneNumber;

    //input constructor
    public customer(String CustomerName, String CustomerSurname,String CustomerPhoneNumber)
    {
        this.customerName = CustomerName;
        this.customerSurname = CustomerSurname;
        this.customerPhoneNumber = CustomerPhoneNumber;
    }

    //output constructor
    public customer(int CustomerID, String CustomerName, String CustomerSurname,String CustomerPhoneNumber)
    {
        this.customerID = CustomerID;
        this.customerName = CustomerName;
        this.customerSurname = CustomerSurname;
        this.customerPhoneNumber = CustomerPhoneNumber;
    }

    //empty contructor 
    public customer()
    {

    }
}
