package com.carplay.expensetracker;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface BudgetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Budget budget);

    @Query("SELECT * FROM budgets WHERE category = :category")
    Budget getBudgetByCategory(String category);
}
