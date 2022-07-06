package com.techelevator.tenmo.model;

public class Transaction {

    public enum typeEnum {SEND, REQUEST, UNASSIGNED}

    ;

    public enum statusEnum {APPROVED, REJECTED, PENDING}

    ;
    private int transaction_id;
    private long send_account_id;
    private long receive_account_id;
    private statusEnum transfer_status = statusEnum.PENDING;
    private typeEnum transfer_type = typeEnum.UNASSIGNED;

    public Transaction(){};

    public Transaction(int transaction_id, long send_account_id, long receive_account_id, statusEnum transfer_status, typeEnum transfer_type) {
        this.transaction_id = transaction_id;
        this.send_account_id = send_account_id;
        this.receive_account_id = receive_account_id;
        this.transfer_status = transfer_status;
        this.transfer_type = transfer_type;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public long getSend_account_id() {
        return send_account_id;
    }

    public void setSend_account_id(long send_account_id) {
        this.send_account_id = send_account_id;
    }

    public long getReceive_account_id() {
        return receive_account_id;
    }

    public void setReceive_account_id(long receive_account_id) {
        this.receive_account_id = receive_account_id;
    }

    public statusEnum getTransfer_status() {
        return transfer_status;
    }

    public void setTransfer_status(statusEnum transfer_status) {
        this.transfer_status = transfer_status;
    }

    public typeEnum getTransfer_type() {
        return transfer_type;
    }

    public void setTransfer_type(typeEnum transfer_type) {
        this.transfer_type = transfer_type;
    }
}
