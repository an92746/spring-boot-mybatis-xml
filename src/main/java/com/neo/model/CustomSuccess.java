package com.neo.model;

public class CustomSuccess {

    private CustomError error;

    private Currency  currency;

    public CustomError getError() {
        return error;
    }

    public void setError(CustomError error) {
        this.error = error;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
