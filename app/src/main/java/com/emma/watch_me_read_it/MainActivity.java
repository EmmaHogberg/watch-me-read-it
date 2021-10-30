package com.emma.watch_me_read_it;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;


public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1000;
    private ImageView imageView;
    private Button captureButton;
    private Button detectTextButton;
    private TextView textView;
    private InputImage image;
    private BottomNavigationView navigationView;
    private String text = "";
    ActivityResultLauncher<Intent> activityResultLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Set variables
        imageView = (ImageView) findViewById(R.id.imageView);
        captureButton = (Button) findViewById(R.id.captureImageButton);
        detectTextButton = (Button) findViewById(R.id.detectTextButton);
        textView = (TextView) findViewById(R.id.detectedTextView);
        navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                if (item.getItemId() == R.id.textToSpeech) {

                        Intent speechIntent = new Intent(MainActivity.this, TextToSpeechActivity.class);
                        speechIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(speechIntent);
                        finish();

                        return true;
                }
                return false;
            }
        });


        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {

            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle bundle = result.getData().getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    imageView.setImageBitmap(bitmap);
                    image = InputImage.fromBitmap(bitmap, 0);
                    recognizeTextFromImage();
                }
            }
        });


        // CaptureButton click listener
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // If system os is >=marshmallow, request runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED) {

                        // Permission not enabled, request it
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        // Show popup to request permissions
                        requestPermissions(permission, PERMISSION_CODE);

                    } else {
                        // Permission already granted
                        dispatchTakePictureIntent();
                    }
                } else {

                    // System os < marshmallow
                    dispatchTakePictureIntent();
                }
            }
        });
    }




    // Take picture intent
    private void dispatchTakePictureIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            activityResultLauncher.launch(intent);
        } else {
            Toast.makeText(MainActivity.this, "There is no app that support this action",
                    Toast.LENGTH_SHORT).show();
        }
    }



    // Analyze the image and set recognized text to textview
    private void recognizeTextFromImage() {

        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        Task<Text> result =
                recognizer.process(image)
                        .addOnSuccessListener(new OnSuccessListener<Text>() {
                            @Override
                            public void onSuccess(Text visionText) {
                                for (Text.TextBlock block : visionText.getTextBlocks()) {
                                    text += block.getText() + "\n";
                                }
                                textView.setText(text);
                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(MainActivity.this, "Something went wrong, please try again",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
    }




    // Handling permission result
    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Called when user presses Allow or Deny from permission request popup
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission from popup was granted
                    dispatchTakePictureIntent();
                }
                else {
                    // Permission from popup was granted
                    Toast.makeText(this, "Permission was denied...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}













