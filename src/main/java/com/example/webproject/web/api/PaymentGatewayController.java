package com.example.webproject.web.api;

import com.example.webproject.stripe.StripeClient;
import com.stripe.model.Charge;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/payment")
public class PaymentGatewayController {
    private final StripeClient stripeClient;

    PaymentGatewayController(StripeClient stripeClient) {
        this.stripeClient = stripeClient;
    }

    @PostMapping("/charge")
    public Charge chargeCard(@RequestHeader(value="token") String token,
                             @RequestHeader(value="amount") BigDecimal amount) throws Exception {
        return stripeClient.chargeNewCard(token, amount);
    }
}
