import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

//1. Define Strategy Interface
public interface PaymentStrategy {
    void pay(double amount);
}

//2. Create 2 Concrete Strategies
class CreditCardPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Credit Card Payment: " + amount);
    }
}
class PayPalPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("PayPal Payment: " + amount);
    }
}

//3. Create the Context class
class ShoppingCart{
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void checkout(double amount) {
        paymentStrategy.pay(amount);
    }
}

//4. Create the Client class
class ShoppingApp{
    public static void main(String[] args) {
        /*
        double price = 250.00;

        ShoppingCart cart = new ShoppingCart();

        cart.setPaymentStrategy(new CreditCardPayment());
        cart.checkout(price);

        cart.setPaymentStrategy(new PayPalPayment());
        cart.checkout(price);
         */

        //BufferedReader to get payment amount and payment method
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        double paymentAmount = 0;
        int paymentMethod = 0;
        try {
            System.out.print("Enter a payment amount: ");
            String input1 = reader.readLine();
            paymentAmount = Double.parseDouble(input1);

            System.out.print("Enter a payment method. Select 1 for credit card, Select 2 for paypal: ");
            String input2 = reader.readLine();
            paymentMethod = Integer.parseInt(input2);

        } catch (IOException e) {
            System.out.println("IO error: " + e.getMessage());
        }

        ShoppingCart cart = new ShoppingCart();

        if(paymentAmount != 0) { //check if payment amount is > 0
            if (paymentMethod == 1) {
                cart.setPaymentStrategy(new CreditCardPayment());
                cart.checkout(paymentAmount);
            } else if (paymentMethod == 2) {
                cart.setPaymentStrategy(new PayPalPayment());
                cart.checkout(paymentAmount);
            } else {
                System.out.println("Invalid payment method");
            }
        } else {
            System.out.println("Invalid payment amount");
        }
    }
}
