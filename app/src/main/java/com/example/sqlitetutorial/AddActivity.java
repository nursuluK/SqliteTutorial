package com.example.sqlitetutorial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AddActivity extends AppCompatActivity {

    EditText title_input, author_input,pages_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        title_input = findViewById(R.id.title_input);
        author_input = findViewById(R.id.author_input);
        pages_input = findViewById(R.id.pages_input);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                myDB.addBook(title_input.getText().toString().trim(),
                        author_input.getText().toString().trim(),
                        Integer.valueOf(pages_input.getText().toString().trim()));

                // Возвращение на главный экран (MainActivity)
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);

                // Завершение текущей активности, чтобы она не оставалась в стеке
                finish();
            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button addButton = findViewById(R.id.add_button);
        addButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.your_button_color)));


        // Получаем иконку "назад" из ActionBar
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.baseline_arrow_back_24);

        // Устанавливаем цвет иконки (в данном случае, оранжевый)
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.orange), PorterDuff.Mode.SRC_ATOP);

        // Устанавливаем иконку "назад" с установленным цветом
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
}