package com.techelevator.tenmo.model;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;


public class RequestDTO {
    private Transaction.statusEnum transfer_status;
    private long transaction_id;

    public RequestDTO(Transaction.statusEnum transfer_status, long transaction_id) {
        this.transfer_status = transfer_status;
        this.transaction_id = transaction_id;
    }

    public RequestDTO() {

    }

    public Transaction.statusEnum getTransfer_status() {
        return transfer_status;
    }

    public void setTransfer_status(Transaction.statusEnum transfer_status) {
        this.transfer_status = transfer_status;
    }

    public long getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(long transaction_id) {
        this.transaction_id = transaction_id;
    }
}
