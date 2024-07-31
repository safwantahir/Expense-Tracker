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

public class BudgetManagementActivity extends AppCompatActivity {
    private Spinner spinnerCategory;
    private EditText etLimit;
    private Button btnSaveBudget;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_management);

        spinnerCategory = findViewById(R.id.spinnerCategory);
        etLimit = findViewById(R.id.etLimit);
        btnSaveBudget = findViewById(R.id.btnSaveBudget);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "expense-database").allowMainThreadQueries().build();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.expense_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        btnSaveBudget.setOnClickListener(v -> {
            saveBudget();
        });
    }

    private void saveBudget() {
        String category = spinnerCategory.getSelectedItem().toString();
        String limitStr = etLimit.getText().toString().trim();

        if (category.isEmpty() || limitStr.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double limit = Double.parseDouble(limitStr);

        Budget budget = new Budget();
        budget.setCategory(category);
        budget.setLimit(limit);

        db.budgetDao().insert(budget);

        Toast.makeText(this, "Budget saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}
