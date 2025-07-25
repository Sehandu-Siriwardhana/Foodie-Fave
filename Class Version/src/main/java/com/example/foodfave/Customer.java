package com.example.foodfave;

class Customer {
    private String firstName;
    private String lastName;
    private int burgersRequired;

    public Customer(String firstName, String lastName, int burgersRequired) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.burgersRequired = burgersRequired;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public int getBurgersRequired() {
        return burgersRequired;
    }
}

