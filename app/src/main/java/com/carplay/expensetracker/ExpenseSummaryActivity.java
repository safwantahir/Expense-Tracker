package com.carplay.expensetracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseSummaryActivity extends AppCompatActivity {
    private RecyclerView recyclerViewExpenses;
    private ExpenseAdapter expenseAdapter;
    private AppDatabase db;
    private List<Expense> expenseList;
    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_summary_activity);

        recyclerViewExpenses = findViewById(R.id.recyclerViewExpenses);
        recyclerViewExpenses.setLayoutManager(new LinearLayoutManager(this));
        pieChart = findViewById(R.id.pieChart);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "expense-database").allowMainThreadQueries().build();

        loadExpenses();
    }

    private void loadExpenses() {
        expenseList = db.expenseDao().getAllExpenses();
        expenseAdapter
