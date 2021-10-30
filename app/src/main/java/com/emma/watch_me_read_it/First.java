//package com.emma.watch_me_read_it;
//
//
//import androidx.activity.result.ActivityResult;
//import androidx.activity.result.ActivityResultCallback;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.Manifest;
//import android.content.ContentValues;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//public class MainActivity extends AppCompatActivity {
//
//    private static final int PERMISSION_CODE = 1000;
//    private static final int IMAGE_CAPTURE_CODE = 1001;
//    ImageView imageView;
//    Button captureButton;
//
//    Uri imageUri;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        imageView = (ImageView) findViewById(R.id.imageView);
//        captureButton = (Button) findViewById(R.id.captureImageButton);
//
//        // Button click listener
//        captureButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // If system os is >=marshmallow, request runtime permission
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
//                            PackageManager.PERMISSION_DENIED ||
//                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
//                                    PackageManager.PERMISSION_DENIED) {
//
//                        // Permission not enabled, request it
//                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
//                        // Show popup to request permissions
//                        requestPermissions(permission, PERMISSION_CODE);
//
//
//
//                    } else {
//                        // Permission already granted
//                        openCamera();
//
//
//                    }
//                } else {
//
//                    // System os < marshmallow
//                    openCamera();
//                }
//
//
//
//            }
//        });
//
//    }
//
//    private void openCamera() {
//
//        ContentValues values = new ContentValues();
//        values.put(MediaStore.Images.Media.TITLE, "New Picture");
//        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
//        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//
//        // Camera intent
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
//
//    }
//
//    // Handling permission result
//    @Override
//    public void onRequestPermissionsResult(int requestCode,  String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        // Called when user presses Allow or Deny from permission request popup
//        switch (requestCode) {
//            case PERMISSION_CODE: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // Permission from popup was granted
//                    openCamera();
//                }
//                else {
//                    // Permission from popup was granted
//                    Toast.makeText(this, "Permission was denied...", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        }
//
//
//
//
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Called when image was captured from camera
//        if (resultCode == RESULT_OK) {
//            // Set captured image to ImageView
//            imageView.setImageURI(imageUri);
//        }
//
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//----------------------------------------------------------------------------------------------------
//
//
//
//        package com.emma.watch_me_read_it;
//
//        import androidx.activity.result.ActivityResult;
//        import androidx.activity.result.ActivityResultCallback;
//        import androidx.activity.result.ActivityResultLauncher;
//        import androidx.activity.result.contract.ActivityResultContracts;
//        import androidx.appcompat.app.AppCompatActivity;
//
//        import android.Manifest;
//        import android.content.Intent;
//        import android.content.pm.PackageManager;
//        import android.graphics.Bitmap;
//        import android.os.Build;
//        import android.os.Bundle;
//        import android.provider.MediaStore;
//        import android.view.View;
//        import android.widget.Button;
//        import android.widget.ImageView;
//        import android.widget.TextView;
//        import android.widget.Toast;
//
//public class MainActivity extends AppCompatActivity {
//
//    private static final int PERMISSION_CODE = 1000;
//    private ImageView imageView;
//    private Button captureButton;
//    private Button detectTextButton;
//    private TextView textView;
//    ActivityResultLauncher<Intent> activityResultLauncher;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        imageView = (ImageView) findViewById(R.id.imageView);
//        captureButton = (Button) findViewById(R.id.captureImageButton);
//        detectTextButton = (Button) findViewById(R.id.detectTextButton);
//        textView = (TextView) findViewById(R.id.detectedTextView);
//
//        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//
//
//            @Override
//            public void onActivityResult(ActivityResult result) {
//                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
//                    Bundle bundle = result.getData().getExtras();
//                    Bitmap bitmap = (Bitmap) bundle.get("data");
//                    imageView.setImageBitmap(bitmap);
//                }
//
//            }
//        });
//
//        // Button click listener
//        captureButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // If system os is >=marshmallow, request runtime permission
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
//                            PackageManager.PERMISSION_DENIED ||
//                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
//                                    PackageManager.PERMISSION_DENIED) {
//
//                        // Permission not enabled, request it
//                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
//                        // Show popup to request permissions
//                        requestPermissions(permission, PERMISSION_CODE);
//
//
//
//                    } else {
//                        // Permission already granted
//                        openCamera();
//
//
//                    }
//                } else {
//
//                    // System os < marshmallow
//                    openCamera();
//                }
//
//
//
//
//
//
//
//            }
//        });
//
//    }
//
//    private void openCamera() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            activityResultLauncher.launch(intent);
//        } else {
//            Toast.makeText(MainActivity.this, "There is no app that support this action",
//                    Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    // Handling permission result
//    @Override
//    public void onRequestPermissionsResult(int requestCode,  String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        // Called when user presses Allow or Deny from permission request popup
//        switch (requestCode) {
//            case PERMISSION_CODE: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // Permission from popup was granted
//                    openCamera();
//                }
//                else {
//                    // Permission from popup was granted
//                    Toast.makeText(this, "Permission was denied...", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        }
//    }
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
