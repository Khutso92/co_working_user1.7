package com.example.khutsomatlala.hackaton_user11.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khutsomatlala.hackaton_user11.R;
import com.example.khutsomatlala.hackaton_user11.model_for_admin_app.Slide;
import com.example.khutsomatlala.hackaton_user11.model_for_admin_app.details;
import com.example.khutsomatlala.hackaton_user11.model_for_admin_app.features;
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

/**
 * Created by Admin on 1/19/2018.
 */

public class Sixth_Host extends AppCompatActivity {
    ImageView first_pic, second_pic, third_pic;
    Button  first_amenities, second_amenities, third_amenities, save;
    String price, in, out, hours, phone, infor, PlaceName, btn_first_pic, btn_second_pic, btn_third_pic, featTit1e1, featTit1e2, featTit1e3, placeAddress;
    String urI, uri2, uri3;
    int i = 0;

    Boolean uploadFeature = false, uploadPics = false;

    EditText edtFeatT1, edtFeatT2, edtFeatT3;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRefPlaces, mDatabaseRefSlide, mDatabaseRefWorkingHours, mDataRefFeat;
    private ImageView imageView;

    FirebaseDatabase database;
    private Uri imgUri, imgUri2, imgUri3;
    private Uri FeatimgUri, FeatimgUri2, FeatimgUri3;
    String featUri, featUri2, featUri3, latlon;
    int numberOfPlaces = 1;
    //public static final String FB_STORAGE_PATH = "new_places/";
    public static final String FB_STORAGE_PATH = "events/";
    public static final int REQUEST_CODE_S1 = 1;
    public static final int REQUEST_CODE_S2 = 12;
    public static final int REQUEST_CODE_S3 = 123;

    public static final int REQUEST_CODE_F1 = 124;
    public static final int REQUEST_CODE_F2 = 125;
    public static final int REQUEST_CODE_F3 = 251;

    private CheckBox chkIos, chkAndroid, chkWindows;
    TextView txt_seeall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sixth);


        //addListenerOnChkIos();
        //addListenerOnButton();


        mStorageRef = FirebaseStorage.getInstance().getReference();
        //   mDatabaseRefPlaces = FirebaseDatabase.getInstance().getReference("new_places");
        mDatabaseRefPlaces = FirebaseDatabase.getInstance().getReference("property");
        mDataRefFeat = FirebaseDatabase.getInstance().getReference("new_Features");
        database = FirebaseDatabase.getInstance();
        mDatabaseRefWorkingHours = database.getReference().child("new_working_hours");

        first_pic = findViewById(R.id.btn_first_pic);
        second_pic = findViewById(R.id.btn_second_pic);
        third_pic = findViewById(R.id.btn_third_pic);
        first_amenities = findViewById(R.id.btn_first_amenities);
        second_amenities = findViewById(R.id.btn_second_amenities);
        third_amenities = findViewById(R.id.btn_third_amenities);

        save = findViewById(R.id.btn_save_host);
        imageView = findViewById(R.id.ImageView);
        txt_seeall = findViewById(R.id.txt_seeall);



        txt_seeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialog alert = new ViewDialog();

                alert.showDialog(Sixth_Host.this, "");
            }
        });

        edtFeatT1 = findViewById(R.id.edtFeatT1);
        edtFeatT2 = findViewById(R.id.edtFeatT2);
        edtFeatT3 = findViewById(R.id.edtFeatT3);

        Intent i = getIntent();
        price = i.getStringExtra("price");
        in = i.getStringExtra("timein");
        out = i.getStringExtra("timeout");
        hours = i.getStringExtra("hour");
        phone = i.getStringExtra("phone");
        infor = i.getStringExtra("infor");
        PlaceName = i.getStringExtra("name");
        latlon = i.getStringExtra("latlon");
        placeAddress = i.getStringExtra("placeAddress");


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                    if (PicSaver() == true) {

                        Intent i = new Intent(Sixth_Host.this, MainMenuFragment.class);
                        startActivity(i);
                        PicSaver();

                    } else {
                        Toast.makeText(Sixth_Host.this, " upload all pics", Toast.LENGTH_SHORT).show();
                    }
                } catch (NullPointerException e) {

                    Toast.makeText(Sixth_Host.this, "- enter all text fields \n - upload all 3 pics ", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }



    public void addListenerOnChkIos() {

        chkIos = (CheckBox) findViewById(R.id.chkIos);

        chkIos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    Toast.makeText(Sixth_Host.this,
                            "Bro, try Android :)", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

 /*   public void addListenerOnButton() {

        chkIos = (CheckBox) findViewById(R.id.chkIos);
        chkAndroid = (CheckBox) findViewById(R.id.chkAndroid);
        chkWindows = (CheckBox) findViewById(R.id.chkWindows);

    }
*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Slide pic 1
        if (requestCode == REQUEST_CODE_S1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                first_pic.setImageBitmap(bm);
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
                second_pic.setImageBitmap(bm);
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
                third_pic.setImageBitmap(bm);
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

    public Boolean PicSaver() {


        /*if (EdtPlaceName.length() > 0 &&
                EdtPlaceInfor.length() > 0 &&
                EdtAddress.length() > 0 &&
                edtLongitude.length() > 0 &&
                EdtCell.length() > 0 &&
                edtWorkingHours.length() > 0 &&
                EdtWebsite.length() > 0 &&
                edtLongitude.length()>0 &&
                edtLatitude.length() >0) {*/

        if (imgUri != null && imgUri2 != null && imgUri3 != null) {
            uploadPics = true;
            final ProgressDialog dialog = new ProgressDialog(this);

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


                    urI = taskSnapshot.getDownloadUrl().toString();

                    String key = mDatabaseRefPlaces.push().getKey();

                    //details details = new details( latlon, placeAddress, phone, hours, infor, PlaceName, Integer.parseInt(price),  urI);
                    details details = new details("34.058653", "-118.443135", placeAddress, phone, hours, infor, PlaceName, Integer.parseInt(price), urI);



                    if (numberOfPlaces == 1){
                    mDatabaseRefPlaces.push().child("details").setValue(details);
                        numberOfPlaces++;
                    }
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


                                    mDatabaseRefSlide = FirebaseDatabase.getInstance().getReference("new_Slide").child(PlaceName);
                                    if (i == 0) {

                                        Slide slide1 = new Slide(urI);
                                        String imge1 = mDatabaseRefSlide.push().getKey();
                                        mDatabaseRefSlide.child(imge1).setValue(slide1);

                                        Slide slide2 = new Slide(uri2);
                                        String imge2Id = mDatabaseRefSlide.push().getKey();
                                        mDatabaseRefSlide.child(imge2Id).setValue(slide2);

                                        Slide slide3 = new Slide(uri3);
                                        String imge3Id = mDatabaseRefSlide.push().getKey();
                                        mDatabaseRefSlide.child(imge3Id).setValue(slide3);
                                        i++;
                                    }


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

                                                                features feat = new features(featUri, featUri2, featUri3, featTit1e1, featTit1e2, featTit1e3);
                                                                mDataRefFeat.child(PlaceName).setValue(feat);

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

                    Toast.makeText(getApplicationContext(), "saved", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), MainMenuFragment.class);
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
            Toast.makeText(getApplicationContext(), " upload all pictures", Toast.LENGTH_SHORT).show();


        }
       /* } else {

            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        }*/

        return uploadPics;
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


    public void saveHours() {
        mDatabaseRefWorkingHours.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                working_hours hours = new working_hours(in, out);
                mDatabaseRefWorkingHours.child(PlaceName).setValue(hours);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

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


    public class ViewDialog {



        public void showDialog(Activity activity, String msg) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.activity_amenities);

            /*TextView text = (TextView) dialog.findViewById(R.id.txt_seeall);
            text.setText(msg);*/

            Button btn_ok =  dialog.findViewById(R.id.btn_ok);
            final CheckBox chk_wifi=  dialog.findViewById(R.id.chkIos1);
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();


                    if (chk_wifi.isChecked()){

                        Toast.makeText(Sixth_Host.this  , "wifi", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            dialog.show();

        }
    }

}
