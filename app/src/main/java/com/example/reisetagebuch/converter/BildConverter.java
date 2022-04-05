package com.example.reisetagebuch.converter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.room.TypeConverter;

import com.example.reisetagebuch.activities.MainActivity;
import com.example.reisetagebuch.add.AddEditReiseActivity;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class BildConverter {

    @TypeConverter
    public static byte[] fromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }

    @TypeConverter
    public static Bitmap toBitmap(byte[] byteArr){

        return BitmapFactory.decodeByteArray(byteArr, 0, byteArr.length);


    }

}
