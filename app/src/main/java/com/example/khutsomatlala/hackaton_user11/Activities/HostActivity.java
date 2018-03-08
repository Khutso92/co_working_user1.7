package com.example.khutsomatlala.hackaton_user11.Activities;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.khutsomatlala.hackaton_user11.R;
import com.example.khutsomatlala.hackaton_user11.model_for_admin_app.Amenities;
import com.example.khutsomatlala.hackaton_user11.model_for_admin_app.Slide;
import com.example.khutsomatlala.hackaton_user11.model_for_admin_app.working_hours;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

public class HostActivity extends AppCompatActivity {

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRefPlaces, mDatabaseRefSlide, mDatabaseRefWorkingHours, mDataRefFeat;
    private ImageView imageView;
    FirebaseDatabase database;

    // fields of the details
    String placeLongitude, placeLatitude, placeAddress, placeCell, placeHours, placeInfo, placeName, PlacePrice, placeWebsite, openTime, closeTime, featTit1e1, featTit1e2, featTit1e3;

    String urI, uri2, uri3;
    String featUri, featUri2, featUri3;


    private Uri imgUri, imgUri2, imgUri3;
    private Uri FeatimgUri, FeatimgUri2, FeatimgUri3;
    int i = 0, f = 0;

    public static final String FB_STORAGE_PATH = "new_places/";

    public static final int REQUEST_CODE_S1 = 1;
    public static final int REQUEST_CODE_S2 = 12;
    public static final int REQUEST_CODE_S3 = 123;

    public static final int REQUEST_CODE_F1 = 124;
    public static final int REQUEST_CODE_F2 = 125;
    public static final int REQUEST_CODE_F3 = 251;
    boolean valid = false;
    private EditText EdtPlaceName, EdtPlaceInfor, EdtAddress, EdtCell, edtWorkingHours, EdtWebsite, edtLongitude, edtLatitude, edtPrice, edtCloseTime, edtOpenTime, edtFeatT1, edtFeatT2, edtFeatT3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_place);


        mStorageRef = FirebaseStorage.getInstance().getReference();

        mDatabaseRefPlaces = FirebaseDatabase.getInstance().getReference("new_places");
        mDataRefFeat = FirebaseDatabase.getInstance().getReference("new_Features");
        database = FirebaseDatabase.getInstance();


        mDatabaseRefWorkingHours = database.getReference().child("new_working_hours");

        imageView = (ImageView) findViewById(R.id.ImageView);

        EdtPlaceName = (EditText) findViewById(R.id.EdtPlaceName);
        EdtPlaceInfor = (EditText) findViewById(R.id.EdtPlaceInfor);
        EdtAddress = (EditText) findViewById(R.id.EdtAddress);
        EdtCell = (EditText) findViewById(R.id.EdtCell);
        edtWorkingHours = (EditText) findViewById(R.id.edtWorkingHours);
        EdtWebsite = (EditText) findViewById(R.id.EdtWebsite);
        edtLongitude = (EditText) findViewById(R.id.edtLongitude);
        edtLatitude = (EditText) findViewById(R.id.edtLatitude);
        edtPrice = (EditText) findViewById(R.id.edtPrice);
        edtCloseTime = (EditText) findViewById(R.id.edtCloseTime);
        edtOpenTime = (EditText) findViewById(R.id.edtOpenTime);

        edtFeatT1 = (EditText) findViewById(R.id.edtFeatT1);
        edtFeatT2 = (EditText) findViewById(R.id.edtFeatT2);
        edtFeatT3 = (EditText) findViewById(R.id.edtFeatT3);

    }

    //Browse image to upload for pic 1
    public void upSlidePic(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_CODE_S1);
    }

    //Browse image to upload for pic 2
    public void upSlidePic2(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_CODE_S2);
    }

    //Browse image to upload for pic 3
    public void upSlidePic3(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_CODE_S3);
    }


    //Browse image to upload for pic 1
    public void upLoadFeatPic1(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_CODE_F1);
    }

    public void upLoadFeatPic2(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_CODE_F2);
    }


    public void upLoadFeatPic3(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_CODE_F3);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Slide pic 1
        if (requestCode == REQUEST_CODE_S1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                imageView.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Slide pic 2
        if (requestCode == REQUEST_CODE_S2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri2 = data.getData();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri2);
                imageView.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        //Slide pic 3
        if (requestCode == REQUEST_CODE_S3 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri3 = data.getData();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri3);
                imageView.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        // Feature 1
        if (requestCode == REQUEST_CODE_F1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            FeatimgUri = data.getData();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), FeatimgUri);
                imageView.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        // Feature 2
        if (requestCode == REQUEST_CODE_F2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            FeatimgUri2 = data.getData();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), FeatimgUri2);
                imageView.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Feature 3
        if (requestCode == REQUEST_CODE_F3 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            FeatimgUri3 = data.getData();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), FeatimgUri3);
                imageView.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Slide pics
    public String getIamgeExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    public String getIamgeExt2(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    public String getIamgeExt3(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    //Feat 1 - 3

    public String getIamgeExtFeat1(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public String getIamgeExtFeat2(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public String getIamgeExtFeat3(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    //Uploading image
    @SuppressWarnings("VisibleForTests")
    public void btnUpload_Click(View view) {


        /*if (EdtPlaceName.length() > 0 &&
                EdtPlaceInfor.length() > 0 &&
                EdtAddress.length() > 0 &&
                edtLongitude.length() > 0 &&
                EdtCell.length() > 0 &&
                edtWorkingHours.length() > 0 &&
                EdtWebsite.length() > 0 &&
                edtLongitude.length()>0 &&
                edtLatitude.length() >0) {*/

        if (imgUri != null) {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("saving");
            dialog.show();

            //Get the storage reference for slide pics
            StorageReference ref = mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + getIamgeExt(imgUri));
            final StorageReference ref2 = mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + getIamgeExt2(imgUri2));
            final StorageReference ref3 = mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + getIamgeExt3(imgUri3));


            //Get the storage reference for feat icon
            final StorageReference refFeat1 = mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + getIamgeExtFeat1(FeatimgUri));
            final StorageReference refFeat2 = mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + getIamgeExtFeat2(FeatimgUri2));
            final StorageReference refFeat3 = mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + getIamgeExtFeat3(FeatimgUri3));


            //for silde 1
            ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    placeLongitude = edtLongitude.getText().toString().trim();

                    placeCell = EdtCell.getText().toString().trim();
                    placeHours = edtWorkingHours.getText().toString().trim();
                    placeInfo = EdtPlaceInfor.getText().toString().trim();
                    placeName = EdtPlaceName.getText().toString();
                    PlacePrice = edtPrice.getText().toString();
                    placeWebsite = EdtWebsite.getText().toString().trim();

                    urI = taskSnapshot.getDownloadUrl().toString();

                    String key = mDatabaseRefPlaces.push().getKey();

//               details details = new details( , placeAddress, placeCell, placeHours, placeInfo, placeName, Integer.parseInt(PlacePrice), placeWebsite, urI);


                    //for silde 3
                    ref3.putFile(imgUri3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            uri3 = taskSnapshot.getDownloadUrl().toString();


                            //for silde 2
                            ref2.putFile(imgUri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    uri2 = taskSnapshot.getDownloadUrl().toString();


                                    mDatabaseRefSlide = FirebaseDatabase.getInstance().getReference("new_Slide").child(placeName);

//                                    if (i == 0) {
//
//                                        Slide slide1 = new Slide(urI);
//                                        String imge1 = mDatabaseRefSlide.push().getKey();
//                                        mDatabaseRefSlide.child(imge1).setValue(slide1);
//
//                                        Slide slide2 = new Slide(uri2);
//                                        String imge2Id = mDatabaseRefSlide.push().getKey();
//                                        mDatabaseRefSlide.child(imge2Id).setValue(slide2);
//
//                                        Slide slide3 = new Slide(uri3);
//                                        String imge3Id = mDatabaseRefSlide.push().getKey();
//                                        mDatabaseRefSlide.child(imge3Id).setValue(slide3);
//                                        i++;
//                                    }


                                    //for Feat1 1
                                    refFeat1.putFile(FeatimgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                            featUri = taskSnapshot.getDownloadUrl().toString();

                                            //for Feat1 2
                                            refFeat2.putFile(FeatimgUri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                    featUri2 = taskSnapshot.getDownloadUrl().toString();

                                                    //for Feat1 3
                                                    refFeat3.putFile(FeatimgUri3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                            featUri3 = taskSnapshot.getDownloadUrl().toString();

                                                            featTit1e1 = edtFeatT1.getText().toString().trim();
                                                            featTit1e2 = edtFeatT2.getText().toString().trim();
                                                            featTit1e3 = edtFeatT3.getText().toString().trim();

                                                         //   Amenities feat = new Amenities(featUri, featUri2, featUri3, featTit1e1, featTit1e2, featTit1e3);
                                                         //   mDataRefFeat.child(placeName).setValue(feat);

                                                        }
                                                    });

                                                }
                                            });


                                        }
                                    });
                                }
                            });

                        }
                    });


                    //   mDatabaseRefPlaces.child(key).child("details").setValue(details);
                    saveHours();
                    //saveSlide();
                    // SaveFeat();*/


                    //clearing the EditText
                    EdtPlaceName.getText().clear();
                    EdtPlaceInfor.getText().clear();
                    EdtAddress.getText().clear();
                    EdtCell.getText().clear();

                    edtWorkingHours.getText().clear();
                    EdtWebsite.getText().clear();
                    edtLongitude.getText().clear();
                    edtLatitude.getText().clear();
                    edtPrice.getText().clear();
                    Toast.makeText(getApplicationContext(), "saved", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                }
            })

                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            //Show upload progress

                            double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            dialog.setMessage((int) progress + "%");
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "Please upload a picture", Toast.LENGTH_SHORT).show();


        }
       /* } else {

            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        }*/


    }

    //Working hours
    public void saveHours() {

        if (valid == false) {

            openTime = edtOpenTime.getText().toString().trim();
            closeTime = edtCloseTime.getText().toString().trim();

            mDatabaseRefWorkingHours.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    working_hours hours = new working_hours(closeTime, openTime);
                    mDatabaseRefWorkingHours.child(placeName).setValue(hours);
                    valid = true;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } else {
            Toast.makeText(this, " already saved", Toast.LENGTH_SHORT).show();
        }

    }


}
