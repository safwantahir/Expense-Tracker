package com.carplay.expensetracker;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
public class UpdateExpenseActivity extends AppCompatActivity {
    private EditText etCategory, etAmount, etDate, etDescription;
    private Button btnUpdateExpense;
    private AppDatabase db;
    private Expense expense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_expense);

        etCategory = findViewById(R.id.etCategory);
        etAmount = findViewById(R.id.etAmount);
        etDate = findViewById(R.id.etDate);
        etDescription = findViewById(R.id.etDescription);
        btnUpdateExpense = findViewById(R.id.btnUpdateExpense);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "expense-database").allowMainThreadQueries().build();

        int expenseId = getIntent().getIntExtra("expenseId", -1);
        expense = db.expenseDao().getExpenseById(expenseId);

        if (expense != null) {
            etCategory.setText(expense.getCategory());
            etAmount.setText(String.valueOf(expense.getAmount()));
            etDate.setText(expense.getDate());
            etDescription.setText(expense.getDescription());
        }

        btnUpdateExpense.setOnClickListener(v -> {
            updateExpense();
        });
    }

    private void updateExpense() {
        String category = etCategory.getText().toString().trim();
        String amountStr = etAmount.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if (category.isEmpty() || amountStr.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountStr);

        expense.setCategory(category);
        expense.setAmount(amount);
        expense.setDate(date);
        expense.setDescription(description);

        db.expenseDao().update(expense);

        Toast.makeText(this, "Expense updated", Toast.LENGTH_SHORT).show();
        finish();
    }
}
