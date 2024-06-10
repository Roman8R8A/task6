package ru.mirea.russinov.notebook;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import ru.mirea.russinov.notebook.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_EXTERNAL_STORAGE_PERMISSION = 1;
    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private EditText fileNameEditText;
    private EditText quoteEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fileNameEditText = findViewById(R.id.file_name_edit_text);
        quoteEditText = findViewById(R.id.quote_edit_text);

        // Проверка разрешений на чтение и запись во внешнее хранилище
        if (!hasPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) ||
                !hasPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE_PERMISSION);
        }
    }

    // Метод для проверки наличия разрешения
    private static boolean hasPermission(Context context, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    // Метод для сохранения файла
    public void saveFile(View view) {
        String fileName = fileNameEditText.getText().toString();
        String quote = quoteEditText.getText().toString();

        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(path, fileName + ".txt");

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter output = new OutputStreamWriter(fileOutputStream);
            output.write(quote);
            output.close();
            Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("MainActivity", "Ошибка при сохранении файла", e);
            Toast.makeText(this, "Не удалось сохранить файл", Toast.LENGTH_SHORT).show();
        }
    }

    // Метод для загрузки файла
    public void loadFile(View view) {
        String fileName = fileNameEditText.getText().toString();

        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(path, fileName + ".txt");

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            List<String> lines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            String listString = String.join("\n", lines);
            quoteEditText.setText(listString);
            Toast.makeText(this, "Файл загружен", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("MainActivity", "Ошибка при загрузке файла", e);
            Toast.makeText(this, "Не удалось загрузить файл", Toast.LENGTH_SHORT).show();
        }
    }
}