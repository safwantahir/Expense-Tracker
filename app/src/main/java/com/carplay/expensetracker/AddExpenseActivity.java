package com.carplay.expensetracker;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
public class AddExpenseActivity extends AppCompatActivity {
    private Spinner spinnerCategory;
    private EditText etAmount, etDate, etDescription;
    private Button btnSaveExpense;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expense_activity);

        spinnerCategory = findViewById(R.id.spinnerCategory);
        etAmount = findViewById(R.id.etAmount);
        etDate = findViewById(R.id.etDate);
        etDescription = findViewById(R.id.etDescription);
        btnSaveExpense = findViewById(R.id.btnSaveExpense);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "expense-database").allowMainThreadQueries().build();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.expense_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        btnSaveExpense.setOnClickListener(v -> {
            saveExpense();
        });
    }

    private void saveExpense() {
        String category = spinnerCategory.getSelectedItem().toString();
        String amountStr = etAmount.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if (category.isEmpty() || amountStr.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountStr);

        Expense expense = new Expense();
        expense.setCategory(category);
        expense.setAmount(amount);
        expense.setDate(date);
        expense.setDescription(description);

        db.expenseDao().insert(expense);

        Toast.makeText(this, "Expense saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}