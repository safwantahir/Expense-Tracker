package com.carplay.expensetracker;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "budgets")
public class Budget {
    @PrimaryKey
    @NonNull
    private String category;
    private double limit;

    // Getters and Setters
}
