package refactoring_guru.strategy.example;

import refactoring_guru.strategy.example.order.Order;
import refactoring_guru.strategy.example.strategies.PayByCreditCard;
import refactoring_guru.strategy.example.strategies.PayByPayPal;
import refactoring_guru.strategy.example.strategies.PayStrategy;

import java.io.*;
import java.util.*;

/**
 * EN: World first console e-commerce application.
 *
 * RU: Первый в мире консольный интерет магазин.
 */
public class Demo {
    public static Map<Integer, Integer> priceOnProducts = new HashMap<>();
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Order order = new Order();
    private static PayStrategy strategy;

    static {
        priceOnProducts.put(1, 2200);
        priceOnProducts.put(2, 1850);
        priceOnProducts.put(3, 1100);
        priceOnProducts.put(4, 890);
    }

    public static void main(String[] args) throws IOException {
        while (!order.isClosed()) {
            int cost;

            String continueChoice;
            do {
                System.out.print("Select a product:" + "\n" +
                        "1 - Mother board" + "\n" +
                        "2 - CPU" + "\n" +
                        "3 - HDD" + "\n" +
                        "4 - Memory" + "\n");
                int choice = Integer.parseInt(reader.readLine());
                cost = priceOnProducts.get(choice);
                System.out.print("Count: ");
                int count = Integer.parseInt(reader.readLine());
                order.setTotalCost(cost * count);
                System.out.print("You wish to continue selection? Y/N: ");
                continueChoice = reader.readLine();
            } while (continueChoice.equalsIgnoreCase("Y"));

            if (strategy == null) {
                System.out.println("Select a Payment Method" + "\n" +
                        "1 - PalPay" + "\n" +
                        "2 - Credit Card");
                String paymentMethod = reader.readLine();

                // EN: Client creates different strategies based on input from
                // user, application configuration, etc.
                //
                // RU: Клиент создаёт различные стратегии на основании
                // пользовательских данных, конфигурации и прочих параметров.
                if (paymentMethod.equals("1")) {
                    strategy = new PayByPayPal();
                } else if (paymentMethod.equals("2")) {
                    strategy = new PayByCreditCard();
                }

                // EN: Order object delegates gathering payment data to strategy
                // object, since only strategies know what data they need to
                // process a payment.
                //
                // RU: Объект заказа делегирует сбор платёжных данны стратегии,
                // т.к. только стратегии знают какие данные им нужны для приёма
                // оплаты.
                order.processOrder(strategy);
            }

            System.out.print("Pay " + order.getTotalCost() + " units or Continue shopping?  P/C: ");
            String proceed = reader.readLine();
            if (proceed.equalsIgnoreCase("P")) {
                // EN: Finally, strategy handles the payment.
                //
                // RU: И наконец, стратегия запускает приём платежа.
                if (strategy.pay(order.getTotalCost())) {
                    System.out.println("Payment has succeeded");
                } else {
                    System.out.println("FAIL! Check your data");
                }
                order.setClosed();
            }
        }
    }
}

