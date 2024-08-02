package com.example.comp1786_l6_firebase;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

  // Remember to change your Database URl here
  private final String DATABASE_URL = "your_database_url";
  FirebaseDatabase database;
  private EditText etTitle, etISBN, etPrice;
  private Button btnAddBook, btnViewBooks;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    etTitle = findViewById(R.id.bookTitleText);
    etISBN = findViewById(R.id.isbnText);
    etPrice = findViewById(R.id.priceText);
    btnAddBook = findViewById(R.id.add_book);
    btnViewBooks = findViewById(R.id.view_book);

    // NOTE: Reference to your Realtime database
    database = FirebaseDatabase.getInstance(DATABASE_URL);


    btnAddBook.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Book b = new Book();
        b.setBookTitle(etTitle.getText().toString());
        b.setBookISBN(etISBN.getText().toString());
        b.setBookPrice(Float.parseFloat(etPrice.getText().toString()));
        addBook(b);
      }
    });

    btnViewBooks.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });


  }

  private boolean addBook(Book b) {
    try {
      DatabaseReference myRef = database.getReference("books");
      myRef
          .push()
          .setValue(b)
          .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
              Toast.makeText(
                  MainActivity.this,
                  "Book added successfully",
                  Toast.LENGTH_SHORT
              ).show();
            }
          })
          .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
              // Log the error for debugging
              Log.e("MainActivity", "Error adding book", e);
              Toast.makeText(
                  MainActivity.this,
                  "Error adding book",
                  Toast.LENGTH_SHORT
              ).show();
            }
          });
      return true;
    } catch (Exception e) {
      Log.e("MainActivity", "Error adding book", e);
      return false;
    }
  }
}