package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.inventoryapp.data.BookContract.BookEntry;
import com.example.android.inventoryapp.data.BookDbHelper;

public class EditorActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText priceEditText;
    private EditText quantityEditText;
    private EditText supplierNameEditText;
    private EditText phoneNumberEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        nameEditText = findViewById(R.id.book_name_edit_text);
        priceEditText = findViewById(R.id.book_price_edit_text);
        quantityEditText = findViewById(R.id.book_quantity_edit_text);
        supplierNameEditText = findViewById(R.id.book_supplier_name);
        phoneNumberEditText = findViewById(R.id.book_supplier_phone_number_edit_text);
    }

    private void insertBook() {
        String bookNameString = nameEditText.getText().toString().trim();

        String bookPriceString = priceEditText.getText().toString().trim();
        int bookPriceInteger = Integer.parseInt(bookPriceString);

        String bookQuantityString = quantityEditText.getText().toString().trim();
        int bookQuantityInteger = Integer.parseInt(bookQuantityString);

        String bookSupplierNameString = supplierNameEditText.getText().toString().trim();

        String bookSupplierPhoneNumberString = phoneNumberEditText.getText().toString().trim();
        int bookSupplierPhoneNumberInteger = Integer.parseInt(bookSupplierPhoneNumberString);

        BookDbHelper DbHelper = new BookDbHelper(this);
        SQLiteDatabase db = DbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_BOOK_NAME, bookNameString);
        values.put(BookEntry.COLUMN_PRICE, bookPriceInteger);
        values.put(BookEntry.COLUMN_QUANTITY, bookQuantityInteger);
        values.put(BookEntry.COLUMN_SUPPLIER_NAME, bookSupplierNameString);
        values.put(BookEntry.COLUMN_PHONE_NUMBER, bookSupplierPhoneNumberInteger);

        long newRowId = db.insert(BookEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Toast.makeText(this, "Error with saving book", Toast.LENGTH_SHORT).show();
            Log.d("Error message", "Doesn't insert row on table");

        } else {
            Toast.makeText(this, "Book saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
            Log.d("successfully message", "insert row on table");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                insertBook();
                finish();

            case R.id.action_delete:
                // Do nothing for now
                return true;

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}