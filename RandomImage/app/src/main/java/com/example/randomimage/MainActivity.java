package com.example.randomimage;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.ClipData;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView,infoBtn;
    private Button loadImageButton, getLinkButton;
    private ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        loadImageButton = findViewById(R.id.loadImageButton);
        getLinkButton = findViewById(R.id.getLinkButton);
        infoBtn = findViewById(R.id.infoBtn);
        progressBar = findViewById(R.id.progressBar);

        fullscr();
        loadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressBar();
                // Load a random image using Unsplash API
                loadRandomImage();
            }
        });
        getLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAndCopyImageLink();
            }
        });
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, info.class));
            }
        });
    }
    private void loadRandomImage() {
        String unsplashApiUrl = "https://source.unsplash.com/random";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(unsplashApiUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                hideProgressBar();
                e.printStackTrace();
                Log.e("ERROR", "Network request failed", e);

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e("ERROR", "Unsuccessful response: " + response.code());
                    hideProgressBar();
                    return;
                }
                if (response.isSuccessful()) {
                    final String imageUrl = response.request().url().toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Picasso.get()
                                    .load(imageUrl)
                                    .into(imageView, new com.squareup.picasso.Callback() {
                                        @Override
                                        public void onSuccess() {
                                            imageView.setTag(imageUrl); // Set the tag with the image URL
                                            hideProgressBar();
                                        }

                                        @Override
                                        public void onError(Exception e) {
                                            hideProgressBar();
                                            Log.e("ERROR", "Picasso image load failed", e);
                                        }
                                    });
                        }
                    });

                }
            }
        });
    }
    private void getAndCopyImageLink() {
        Object tag = imageView.getTag();
        if (tag != null && tag instanceof String) {
            String imageUrl = (String) tag;
            copyToClipboard(imageUrl);
            showToast("Image URL copied to clipboard! ");
        } else {
            showToast("Image URL not available ");
        }
    }
    private void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Image URL", text);
        clipboard.setPrimaryClip(clip);
    }
    private void showToast(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,findViewById(R.id.custom_toast_container));
        TextView text = layout.findViewById(R.id.textToast);
        text.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
    private void fullscr(){
        //HIDE STATUSBAR AND NAVIGATION BAR
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }

    private void alert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish(); // Close the activity
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked "No," so dismiss the dialog
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            alert();
        }
        return super.onKeyDown(keyCode, event);
    }
}
