package service;

import domain.checkout.CartItem;
import util.LoggerUtil;

import static util.CommonTimeUtil.delay;

public class PriceValidatorService {

    public Boolean isCarItemInvalid(CartItem cartItem)
    {
        Integer itemId = cartItem.getItemId();
        LoggerUtil.log("isCarItemInvalid for item : " +cartItem);
        delay(500);
        if(itemId == 7 || itemId ==9 || itemId ==11)
            return true;
        return false;
    }
}
