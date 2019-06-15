public class Email
{
    String email;

    public Email(String email)
    {
        this.email=email;
    }

    public void emailCustomerSpecialOffer(IEmailSender sender)
    {
        String msg = "Congratulations! Your purchase history has earned you a 10% discount on your next purchase!";
        sender.sendEmail(email, "10% off your next order!", msg);
    }


}
