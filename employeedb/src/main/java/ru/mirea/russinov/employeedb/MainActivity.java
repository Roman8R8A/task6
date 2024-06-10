package ru.mirea.russinov.employeedb;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView text = findViewById(R.id.text);
        AppDatabase db = App.getInstance().getDatabase();
        EmployeeDao employeeDao = db.employeeDao();
        Employee hero = new Employee();
        hero.id = 2;
        hero.name = "Imaginative SuperHero2";
        hero.power = 20000;
        employeeDao.insert(hero);
        List<Employee> employees = employeeDao.getAll();
        hero = employeeDao.getById(2);
        employeeDao.update(hero);
        String res = hero.name + " " + hero.power;
        text.setText(res);
        Log.d(TAG, res);
    }
}