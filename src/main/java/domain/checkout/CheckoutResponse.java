package domain.checkout;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class CheckoutResponse {

    CheckoutStatus checkoutStatus;
    double finalRate;

    List<CartItem> errorList=new ArrayList<>();

    public CheckoutResponse(CheckoutStatus checkoutStatus) {
        this.checkoutStatus = checkoutStatus;
    }

    public CheckoutResponse(CheckoutStatus checkoutStatus, double finalRate) {
        this.checkoutStatus = checkoutStatus;
        this.finalRate = finalRate;
    }

    public CheckoutResponse(CheckoutStatus checkoutStatus, List<CartItem> errorList) {
        this.checkoutStatus = checkoutStatus;
        this.errorList = errorList;
    }
}
