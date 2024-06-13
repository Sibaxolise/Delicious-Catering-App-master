package PresentationLayer;

public enum Menu {
    NEW("1. Make a new booking."),
    EDIT("2. Edit a booking."),
    CONFIRMATION("3. Check confirmation status."),
    PAYMENT("4. Make a payment."),
    BOOKINGS("5. View all bookings."),
    CONFBOOK("6. View all confirmed bookings."),
    UNCONFBOOK("7. View all non-confirmed bookings."),
    EXIT("8. Exit from the application.");

    public final String label;

    private Menu(String lbl) {
       this.label = lbl;
    }
    //Method will be invoked and will print menu items which the user will wish to act upon
    public static void DisplayMenu() {
        System.out.println("Main Menu");
        System.out.println("=========================================================");
        System.out.println(Menu.NEW.label);
        System.out.println(Menu.EDIT.label);
        System.out.println(Menu.CONFIRMATION.label);
        System.out.println(Menu.PAYMENT.label);
        System.out.println(Menu.BOOKINGS.label);
        System.out.println(Menu.CONFBOOK.label);
        System.out.println(Menu.UNCONFBOOK.label);
        System.out.println(Menu.EXIT.label);
        System.out.println("=========================================================");
    }
}

