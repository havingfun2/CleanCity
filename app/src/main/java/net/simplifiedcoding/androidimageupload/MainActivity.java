package net.simplifiedcoding.androidimageupload;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Declaring views
    private Button buttonChoose;
    private Button buttonUpload;
    private ImageView imageView;
    private EditText editText;
    String path;
    PrefManager pref;
    int i1;
    //Image request code
    private int PICK_IMAGE_REQUEST = 1;

    //Latitude longitude Initialisation
    Double latitude,longitude;

    //Bitmap to get image from gallery
    private Bitmap bitmap;
    String name;
    ProgressDialog p;
    //Uri to store the image uri
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        pref = new PrefManager(getApplicationContext());
        //Intent From MapsActivity
        Bundle data=getIntent().getExtras();
         latitude=data.getDouble("latitude");
         longitude=data.getDouble("longitude");


        //Initializing views
        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        imageView = (ImageView) findViewById(R.id.imageView);
        editText = (EditText) findViewById(R.id.editTextName);

        //Setting clicklistener
        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
    }


    /*
    * This is the method responsible for image upload
    * We need the full image path and the name for the image in this method
    * */
    public void uploadMultipart() {
         name = editText.getText().toString().trim();

        Random r = new Random();
         i1 = r.nextInt(100000 - 1) + 1;
        if(filePath!=null) {
            path = getPath(filePath);
            new UploadImage().execute();
        }
        else{
            Toast.makeText(this,"Please Select Image",Toast.LENGTH_SHORT ).show();
        }

    }


    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }




    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {
            showFileChooser();
        }
        if (v == buttonUpload) {
            uploadMultipart();
        }
    }

    class UploadImage extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {// handled by UI threads

            p = new ProgressDialog(MainActivity.this);
            p.setMessage("Loading...");
            p.show();
        }
        @Override
        protected String doInBackground(Void... params) {

            //getting name for the image


            //getting the actual path of the image


            //Uploading code
            try {

                String uploadId = UUID.randomUUID().toString();

                Calendar c = Calendar.getInstance();

                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                String formattedDate = df.format(c.getTime());

                //Creating a multi part request
                new MultipartUploadRequest(MainActivity.this, uploadId, Constants.UPLOAD_URL)
                        .addFileToUpload(path, "image") //Adding file
                        .addParameter("name", name) //Adding text parameter to the request
                        .addParameter("lat", String.valueOf(latitude))
                        .addParameter("long", String.valueOf(longitude))
                        .addParameter("mobile",pref.getmobile())
                        .addParameter("date",formattedDate)
                        .addParameter("status", String.valueOf(0))
                        .addParameter("compid","CP-"+String.valueOf(i1))
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(2)
                        .startUpload(); //Starting the upload



            } catch (Exception exc) {
                Toast.makeText(MainActivity.this, exc.getMessage(), Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            p.dismiss();
            Intent i = new Intent(MainActivity.this,MapsActivity.class);
            startActivity(i);
            finish();

        }
    }
    }
