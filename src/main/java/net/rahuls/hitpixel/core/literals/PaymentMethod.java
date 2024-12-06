package net.rahuls.hitpixel.core.literals;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentMethod {

    PAYPAL(1, "PayPal"),
    NET_BANKING(2, "Net Banking"),
    CREDIT_CARD(3, "Credit Card"),
    DEBIT_CARD(4, "Debit Card"),
    AMAZON_PAY(5, "Amazon Pay"),
    APPLE_PAY(6, "Apple Pay"),
    ALIPAY(7, "Ali Pay");

    private final int id;
    private final String name;

    public static PaymentMethod fromId(Integer id) {
        if (id == null) {
            return null;
        }
        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            if (paymentMethod.id == id) {
                return paymentMethod;
            }
        }
        return null;
    }

    public static PaymentMethod fromName(String name) {
        if (name == null) {
            return null;
        }
        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            if (paymentMethod.name.equalsIgnoreCase(name)) {
                return paymentMethod;
            }
        }
        return null;
    }
}
