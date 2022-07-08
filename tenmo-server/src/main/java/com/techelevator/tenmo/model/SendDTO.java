package com.techelevator.tenmo.model;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
public class SendDTO {
    private String receiverUserName;
    private BigDecimal amount;


    public SendDTO(String receiverUserName, BigDecimal amount) {
        this.receiverUserName = receiverUserName;
        this.amount = amount;
    }

   public SendDTO(){};

    public String getReceiverUserName() {
        return receiverUserName;
    }

    public void setUserId(String receiverUserName) {
        this.receiverUserName = receiverUserName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
