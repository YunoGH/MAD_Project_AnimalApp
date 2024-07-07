package com.example.animalApp.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.OutputStream

// Function to save a Bitmap image to external storage and return its Uri
fun savePhotoToExternalStorage(context: Context, bitmap: Bitmap, filename: String): Uri? {
    // Create content values to store image metadata
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, filename)  // Set the image display name
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg") // Set the image MIME type
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES) // Set the image relative path
    }

    val resolver = context.contentResolver
    var uri: Uri? = null

    // Insert the content values into the external content URI to get the image Uri
    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)?.also { imageUri ->
        uri = imageUri
        val outputStream: OutputStream? = resolver.openOutputStream(imageUri) // Get output stream for Uri
        outputStream?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it) // Compress and write bitmap stream output
        }
    }
    return uri // Return the Uri of the saved image
}

// Function to get a Bitmap from a given Uri depending on which SDK is used
fun getBitmapFromUri(context: Context, uri: Uri): Bitmap {
    return if (Build.VERSION.SDK_INT < 28) {
        // For Android versions below 28, use MediaStore to get the Bitmap
        MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
    } else {
        // For Android 28 and above, use ImageDecoder to get the Bitmap
        val source = ImageDecoder.createSource(context.contentResolver, uri)
        ImageDecoder.decodeBitmap(source)
    }
}
