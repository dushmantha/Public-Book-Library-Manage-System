package com.book.sl.thlibarary;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.UUID;

import DataBase.Book;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BookAddActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @BindView(R.id.title_field)
    EditText title;
    @BindView(R.id.description_field)
    EditText description;
    @BindView(R.id.author_field)
    EditText author;
    @BindView(R.id.quantity_field)
    EditText quantity;
    @BindView(R.id.rackNo_field)
    EditText rackNo;
    @BindView(R.id.imageView)
    ImageView imgView;
    Uri imagePathUri = null;
    String coverImageUrl = null;
    private static final String CAPTURE_IMAGE_FILE_PROVIDER = "com.book.sl.thlibarary.fileprovider";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        ButterKnife.bind(this);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("book");

    }

    private void addBook() {
        String bookTitle = title.getText().toString().trim();
        String bookDescription = description.getText().toString().trim();
        String bookAuthor = author.getText().toString().trim();
        String bookQuantity = quantity.getText().toString().trim();
        String bookRackNo = rackNo.getText().toString().trim();


        if (!TextUtils.isEmpty(bookTitle) && !TextUtils.isEmpty(bookDescription) && !TextUtils.isEmpty(bookAuthor) && !TextUtils.isEmpty(bookQuantity) && !TextUtils.isEmpty(bookRackNo)) {
            String id = databaseReference.push().getKey();
            Book user = new Book(id, bookTitle, bookDescription, bookAuthor,bookQuantity,bookRackNo,coverImageUrl,"");
            databaseReference.child(id).setValue(user);
            Toast.makeText(this, "Book has being Added", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "please fill your details", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.added_button)
    public void bookAdd(View view) {
        if (imgView != null && imagePathUri != null){
            uploadImage();
        }else {
            Toast.makeText(this, "Please add book cover page.", Toast.LENGTH_LONG).show();
            return;
        }
        addBook();
    }

    @OnClick(R.id.cancel_button)
    public void cancel(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.imageView)
    public void clickCoverImage(View view) {
       selectImage();
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(BookAddActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    File file = null;
                    file = getOutputMediaFile();
                    imagePathUri = FileProvider.getUriForFile( BookAddActivity.this, CAPTURE_IMAGE_FILE_PROVIDER, file);
                    Log.d("uri", imagePathUri.toString());
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imagePathUri);
                    startActivityForResult(cameraIntent, 0);


                } else if (items[item].equals("Choose from Library")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    String profileImageFilepath = imagePathUri.getPath().replace("//", "/");
                    Log.d("path", profileImageFilepath);
                    imgView.setImageURI(null);
                    imgView.setImageURI(imagePathUri);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    imagePathUri= imageReturnedIntent.getData();
                    imgView.setImageURI(null);
                    imgView.setImageURI(imageReturnedIntent.getData());

                }
                break;
        }
    }

    private void uploadImage(){
         final ProgressDialog dialog = new ProgressDialog(this);
        coverImageUrl = (UUID.randomUUID().toString()  + ".jpg");
        dialog.setMessage("Uploading...");
        //
        // dialog.show();
        StorageReference riversRef = storageReference.child("images/"+ coverImageUrl);
        riversRef.putFile(imagePathUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                        //dialog.dismiss();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                })

        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                dialog.setMessage("Uploaded" + (int)progress+ "");

            }
        });

    }


    private static File getOutputMediaFile() {


        String imageFileName = "prof.png";
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(),
                "AppFolder");
        File storageDir = new File(mediaStorageDir + "/Profile_Images");
        if (!storageDir.exists())
        {
            storageDir.mkdirs();
        }
        File image = new File(storageDir, imageFileName);
        return image;

}
}
