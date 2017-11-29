package com.example.khutsomatlala.hackaton_user11;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.khutsomatlala.hackaton_user11.adapter.ImagesAdapter;
import com.example.khutsomatlala.hackaton_user11.model.slideImagePojo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class PlaceDetailsActivity extends FragmentActivity implements OnMapReadyCallback {
    //slide
    List<PlaceDetailsActivity> catalogList;
    LinearLayoutManager linearLayoutManager;
    List<PlaceDetailsActivity> catalogLists, catalogListCars, catalogListFurniture, catalogListPhones;
    DatabaseReference getDatabaseImages;
    ImagesAdapter Slideadapter;


    //FIREBASE CONNECTION
    private DatabaseReference Slidedatabase;
    private StorageReference mStorageReference;

    private ProgressDialog mDialog;

    @Override
    protected void onStart() {
        super.onStart();
    }
    //--- end of slide



    String call, lat, lon, PlaceName, infor, address, hours, pic1, price, location, NumberofUser, email, feat1Title, feat2Title, feat3Title, feat1Pic, feat2Pic, feat3Pic, uid,user_name;
    LinearLayout SendTextLinearLayout;
    TextView placeName, placeLocation, txtInformation, ratingDisplayTextView, readAllReviews, txtPrice,txtHours;

    ImageView feat1P, feat2P, feat3P;


    ImageView PlacePic;
    RatingBar ratingRatingBar;
    long reviews;
    int RateNumber;
    private FirebaseAuth mAuth;
    private GoogleMap mMap;

    //messaging list
    public static final String ANONYMOUS = "anonymous";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;

    private ListView mMessageListView;
    private MessageAdapter mMessageAdapter;
    private EditText mMessageEditText;
    private Button mSendButton, ftitle1;

    private String mUsername, rateMessage;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCommentsDatabaseReference, mRateDatabaseReference;
    EditText message;

    List<FriendlyMessage> mComments;
    private FirebaseAuth mFirebaseAuth;


    //pic slide
    private ImageView imageView;

    //Instantiate adapter object globally
    private ImageAdapter adapter;

    //Instantiate database object globally
    private DatabaseReference database;
    //Initialize array of images of type string
    private List<String> images = new ArrayList<>();

    //rating
    int mTotalRating = 0;
    long mNumberofUser = 0;
    float mAverage = 0;
    Button ftitle2, ftitle3;
    RatingBar ratingBar;

    //transition
    boolean visible;

    //Date format - yyyy.MM.dd.HH.mm.ss
    String dayStamp = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
    String timeStamp = new SimpleDateFormat("HH.mm").format(new Date());

    // availability checked
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        message = findViewById(R.id.messageEditText);
        mComments = new ArrayList<>();
        Intent i = getIntent();

        lat = i.getStringExtra("lat");
        lon = i.getStringExtra("lon");
        call = i.getStringExtra("call");
        PlaceName = i.getStringExtra("name");
        infor = i.getStringExtra("infor");
        address = i.getStringExtra("address");
        hours = i.getStringExtra("hours");
        pic1 = i.getStringExtra("pic1");
        price = i.getStringExtra("price");
        location = i.getStringExtra("location");
        email = i.getStringExtra("email");
        user_name = i.getStringExtra("user_name");


        feat1Title = i.getStringExtra("feat1Title");
        feat2Title = i.getStringExtra("feat2Title");
        feat3Title = i.getStringExtra("feat3Title");

        feat1Pic = i.getStringExtra("feat1Pic");
        feat2Pic = i.getStringExtra("feat2Pic");
        feat3Pic = i.getStringExtra("feat3Pic");



        SendTextLinearLayout = findViewById(R.id.linearLayout);
        placeName = findViewById(R.id.txt_placeName);
        placeLocation = findViewById(R.id.txt_location);
        ratingBar = findViewById(R.id.rating_rating_bar);

        txtHours =  findViewById(R.id.txtHours);

        ftitle1 = findViewById(R.id.btn_reviews);
        ftitle2 = findViewById(R.id.btn_ratingStars);
        ftitle3 = findViewById(R.id.btn_title3);

        readAllReviews = findViewById(R.id.txtReadAll);
        txtInformation = findViewById(R.id.txtInformation);
        PlacePic = findViewById(R.id.ivPlacePic);
        txtPrice = findViewById(R.id.txtPrice);

        feat1P = findViewById(R.id.feat1Pic);
        feat2P = findViewById(R.id.feat2Pic);
        feat3P = findViewById(R.id.feat3Pic);


        placeName.setText(PlaceName);
        placeLocation.setText(address);
        txtInformation.setText(" " + infor);
        SendTextLinearLayout.setVisibility(View.GONE);

        ftitle1.setText("" + feat1Title);
        ftitle2.setText("" + feat2Title);
        ftitle3.setText("" + feat3Title);

        txtHours.setText(hours);
        txtPrice.setText("R" +price + " per hour");

        Glide.with(this)
                .load(pic1)
                .centerCrop()
                .into(PlacePic);

        Glide.with(this)
                .load(feat1Pic)
                .override(70, 70)
                //.centerCrop()
                .into(feat1P);


        Glide.with(this)
                .load(feat2Pic)
                .override(70, 70)
                .into(feat2P);


        Glide.with(this)
                .load(feat3Pic)
                .override(70, 70)
                .into(feat3P);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // ------------------- reviews sections
        mUsername = ANONYMOUS;
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mCommentsDatabaseReference = mFirebaseDatabase.getReference().child("comments");


        // mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("places");

        mRateDatabaseReference = mFirebaseDatabase.getReference();

        // Initialize references to views

        mMessageListView = findViewById(R.id.messageListView);

        mMessageEditText = findViewById(R.id.messageEditText);

        mSendButton = findViewById(R.id.sendButton);

        // Initialize message ListView and its adapter
        final List<FriendlyMessage> friendlyMessages = new ArrayList<>();
        mMessageAdapter = new MessageAdapter(this, R.layout.item_message, friendlyMessages);
        mMessageListView.setAdapter(mMessageAdapter);


        //   again check if the user is already logged in or not
        if (mFirebaseAuth.getCurrentUser() == null) {

//            User not logged in
            finish();
            startActivity(new Intent(getApplicationContext(), AuthActivity.class));
        }

        //  fetch and  display the user details

        final FirebaseUser user = mFirebaseAuth.getCurrentUser();

        if (user != null) {

            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();


            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.

            uid = user.getUid();

            mUsername = name;
        }


        // Enable Send button when there's text to send
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});

        // Send button sends a message and clears the EditText
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mComments.clear();
                // TODO: Sending data to the DB
                FriendlyMessage friendlyMessage = new FriendlyMessage(mMessageEditText.getText().toString(), mUsername ,"time");

                String key = mCommentsDatabaseReference.push().getKey();


                mCommentsDatabaseReference.child(PlaceName).child(key).setValue(friendlyMessage);

                // Clear input box
                mMessageEditText.setText("");
            }
        });

        // mCommentsDatabaseReference.addValueEventListener(new ValueEventListener()
        mCommentsDatabaseReference.child(PlaceName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//

                //Fectching information from database
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    final FriendlyMessage friendlyMessage = snapshot.getValue(FriendlyMessage.class);

                    reviews = dataSnapshot.getChildrenCount();

                    //  ftitle1.setText(reviews + "\nreviews");
                    //readAllReviews.setText("Read all " + reviews + " reviews");
                    mComments.add(friendlyMessage);

                }

                //Init adapter
                mMessageAdapter = new MessageAdapter(PlaceDetailsActivity.this, R.layout.image_item, mComments);

                mMessageListView.setAdapter(mMessageAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //pic slide

        imageView = findViewById(R.id.image);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        /*
        * Initialize adapter and send parameters in constructor
        * Parameters
        * this = Send current activity object to adapter
        * images = List of image url strings Send array to adapter. Will call notifyDatasetChanged on this adapter later.
        **/
        adapter = new ImageAdapter(this, images);
        //viewPager.setAdapter(adapter);


        // Queries Firebase for places endpoint
        FirebaseDatabase.getInstance().getReference().child("places").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {

                    images.clear();
                    if (dataSnapshot.hasChildren()) {

                        System.out.println(dataSnapshot.getChildrenCount());

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            System.out.println(snapshot.child("urI").getValue());

                            //   images.add(snapshot.child("urI").getValue().toString());
                            //images.add(snapshot.child("url2").getValue().toString());
                            //images.add(snapshot.child("url3").getValue().toString());

                        }
                        if (images.size() > 0) {

                            adapter.notifyDataSetChanged();
                        } else {

                            System.out.println("No new items added");
                        }

                    } else {
                        System.out.println("No children found");
                    }
                } else {
                    System.out.println("No Such reference found or value is empty");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Main Activity", "Failed to read value.", error.toException());
            }
        });

        mAuth = FirebaseAuth.getInstance();


        //reading the rating bar
       /* mRateDatabaseReference.child("Rating").child(PlaceName).child(mAuth.getCurrentUser().getDisplayName()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (dataSnapshot.getValue() == null) {

                    mRateDatabaseReference.child("Rating").child(PlaceName).child(mAuth.getCurrentUser().getDisplayName()).setValue(0);
                } else {

                     ratingBar.setRating(Float.parseFloat(dataSnapshot.getValue().toString()));


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });*/

        //getting number total number of users and calc. the average
   /*     mRateDatabaseReference.child("Rating").child(PlaceName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                mNumberofUser = dataSnapshot.getChildrenCount();


                NumberofUser = String.valueOf(mNumberofUser);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    mTotalRating = mTotalRating + Integer.parseInt(snapshot.getValue().toString());
                }
                try {

                    mAverage = (float) ((mTotalRating / mNumberofUser));

                 *//*   DecimalFormat decimalFormat = new DecimalFormat("#.##");

                    ftitle2.setText(decimalFormat.format(mAverage) + "\n Stars");*//*

                    mTotalRating = 0;

                } catch (ArithmeticException e) {
                    Toast.makeText(PlaceDetailsActivity.this, "error - " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
*/

        //rating  the place

  /*      ratingRatingBar = findViewById(R.id.rating_rating_bar);
        ratingDisplayTextView = findViewById(R.id.rating_display_text_View);
        ratingRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(final RatingBar ratingBar, float v, boolean b) {
                if (ratingRatingBar.getRating() == 1) {
                    rateMessage = "Hated it";
                    RateNumber = 1;

                } else if ((int) v == 2) {
                    rateMessage = "Disliked it";
                    RateNumber = 2;
                } else if ((int) v == 3) {
                    rateMessage = "It's OK";
                    RateNumber = 3;
                } else if ((int) v == 4) {
                    rateMessage = "Liked it";
                    RateNumber = 4;
                } else {
                    rateMessage = "Loved it";
                    RateNumber = 5;
                }

                ratingDisplayTextView.setText("" + rateMessage);

                //sending the rating
                mRateDatabaseReference.child("Rating").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        // mRateDatabaseReference.child("Rating").child(PlaceName).child(mAuth.getCurrentUser().getDisplayName()).setValue(RateNumber);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }


        });
*/



  //slide
        catalogList = new ArrayList<>();
        final List<slideImagePojo> image = new ArrayList<>();

        /**
         * PLACES PICTURES IN LISTVIEW
         */
        Slidedatabase= FirebaseDatabase.getInstance().getReference().child("slide").child(PlaceName).child("pictures");

        Slidedatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                catalogList.clear();
                for (DataSnapshot catalogSnapshot : dataSnapshot.getChildren()) {

                    slideImagePojo image1= catalogSnapshot.getValue(slideImagePojo.class);


                    image1.setImage(image1.getImage());
                    image.add(image1);
                    LinearLayoutManager layoutManager
                            = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

                    RecyclerView ListViewCatalog =  findViewById(R.id.recycler_view);
                    Slideadapter = new ImagesAdapter(PlaceDetailsActivity.this, image);
//                    Toast.makeText(CatalogActivity.this, ""+catalog.getCatalogtitle(), Toast.LENGTH_SHORT).show();
                    ListViewCatalog.setLayoutManager(layoutManager);

                    ListViewCatalog.setAdapter(Slideadapter);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PlaceDetailsActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void GoToBook(View view) {

        Intent i = new Intent(PlaceDetailsActivity.this, book_new.class);
        i.putExtra("pic", pic1);
        i.putExtra("name", PlaceName);
        i.putExtra("price", price);
        i.putExtra("email", email);
        i.putExtra("mUsername", user_name);
        i.putExtra("user_uid", uid);


        startActivity(i);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Intent intent = getIntent();

        try {

            String PlaceName = intent.getStringExtra("name");

            Double lat = Double.parseDouble(intent.getStringExtra("lat"));
            Double lon = Double.parseDouble(intent.getStringExtra("lon"));

            // Add a marker in co_space and move the camera
            LatLng co_space = new LatLng(lat, lon);
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            mMap.addMarker(new MarkerOptions().position(co_space).title(PlaceName));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(co_space, 15));
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(this, "NullPointerException  ", Toast.LENGTH_SHORT).show();
        }
    }

    public void GoToReview(View view) {

        Intent intent = new Intent(this, ReviewActivity.class);
        intent.putExtra("PlaceName", PlaceName);
        intent.putExtra("user_name", user_name);
        startActivity(intent);
    }



    public void readMore (View view){
//        final ViewGroup transitionsContainer = (ViewGroup) view.findViewById(R.id.transitions_container);
//        TextView readMoreDetails = (TextView)findViewById(R.id.readMoreDetails);
//        TransitionSet set = new TransitionSet()
//                .addTransition(new Scale(0.7f))
//                .addTransition(new Fade())
//                .setInterpolator(visible ? new LinearOutSlowInInterpolator() :
//                        new FastOutLinearInInterpolator());
//
//        TransitionManager.beginDelayedTransition(transitionsContainer, set);
//        readMoreDetails.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        Intent intent = new Intent(this,Read_more.class);
        intent.putExtra("infor", infor);
        startActivity(intent);
    }
}
