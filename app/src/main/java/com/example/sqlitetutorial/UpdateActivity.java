package com.example.sqlitetutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText title_input, author_input, pages_input;
    Button update_button,delete_button;
    String id, title, author, pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title_input = findViewById(R.id.title_input2);
        author_input = findViewById(R.id.author_input2);
        pages_input = findViewById(R.id.pages_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        // Получение данных из предыдущей активности
        getAndSetIntentData();

        update_button.setOnClickListener(view -> {
            String updatedTitle = title_input.getText().toString().trim();
            String updatedAuthor = author_input.getText().toString().trim();
            String updatedPages = pages_input.getText().toString().trim();

            if (!updatedTitle.isEmpty() && !updatedAuthor.isEmpty() && !updatedPages.isEmpty()) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                int result = myDB.updateData(id, updatedTitle, updatedAuthor, updatedPages);
                if (result > 0) {
                    Toast.makeText(UpdateActivity.this, "Successfully Updated!", Toast.LENGTH_SHORT).show();
                    finish(); // Завершаем текущую активность после успешного обновления
                } else {
                    Toast.makeText(UpdateActivity.this, "Failed to Update.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(UpdateActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            }

        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

        // Настройка тулбара (панели инструментов)
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if(ab!= null){
            ab.setTitle(title);
        }
        // Получение иконки "назад" из ActionBar
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.baseline_arrow_back_24);

        // Установка цвета иконки (в данном случае, оранжевого)
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.orange), PorterDuff.Mode.SRC_ATOP);

        // Установка иконки "назад" с установленным цветом
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button addButton = findViewById(R.id.update_button);
        addButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.your_button_color)));

        Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.your_button_color)));


    }

    // Получение данных из предыдущей активности и заполнение полей
    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("title") &&
                getIntent().hasExtra("author") && getIntent().hasExtra("pages")) {
            // Получение данных из Intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            author = getIntent().getStringExtra("author");
            pages = getIntent().getStringExtra("pages");

            // Установка данных в поля
            title_input.setText(title);
            author_input.setText(author);
            pages_input.setText(pages);
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    // Обработка нажатия на кнопку "назад" в тулбаре
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Выполняем переход назад
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete "+ title+ " ?");
        builder.setMessage("Are you sure you want to delete "+ title + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);

                // Возвращение на главный экран (MainActivity)
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);

                // Завершение текущей активности, чтобы она не оставалась в стеке
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Действие при нажатии кнопки "No", здесь можно оставить пустым
            }
        });
        builder.create().show();
    }
}
