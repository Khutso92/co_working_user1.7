package com.example.khutsomatlala.hackaton_user11.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
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
import com.example.khutsomatlala.hackaton_user11.R;
import com.example.khutsomatlala.hackaton_user11.adapter.ImageAdapter;
import com.example.khutsomatlala.hackaton_user11.adapter.ImagesAdapter;
import com.example.khutsomatlala.hackaton_user11.adapter.MessageAdapter;
import com.example.khutsomatlala.hackaton_user11.adapter.ViewPagerAdapter;
import com.example.khutsomatlala.hackaton_user11.model_for_user_app.FriendlyMessage;
import com.example.khutsomatlala.hackaton_user11.model_for_user_app.Slide;
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

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class CWSDetailsActivity extends FragmentActivity implements OnMapReadyCallback {
    //slide
    private LinearLayout mDotsLayout;
    private ViewPager viewpager;
    private TextView[] mDots;

    private FirebaseDatabase mFirebaseDatabaseSlide;
    private DatabaseReference mPicDatabaseReferencSlide,mDatabaseReferenceAmenties;
    String slide1, slide2, slide3;
    ViewPagerAdapter adapterV;
    List<CWSDetailsActivity> catalogList;

    ImagesAdapter Slideadapter;


    String cws_amenity1, cws_amenity2, cws_amenity3, cws_amenity4, cws_amenity5;
    //FIREBASE CONNECTION
    private DatabaseReference Slidedatabase, mFeatdatabase;
    private StorageReference mStorageReference;

    private ProgressDialog mDialog;

    @Override
    protected void onStart() {
        super.onStart();
    }
    //--- end of slide


    String call, lat, lon, PlaceName, infor, address, hours, pic1, price, location, NumberofUser, email, feat1Title, feat2Title, feat3Title, feat1Pic, feat2Pic, feat3Pic, uid, user_name;
    LinearLayout SendTextLinearLayout;
    TextView placeName, placeLocation, txtInformation, readAllReviews, txtPrice, txtHours, parking_cws, ac_cws, meeting_cws, workshp_cws, kitchen_cws,tv_review;
    LinearLayout parking_cws_layout, ac_cws_layout, meeting_cws_layout, workshp_cws_layout, kitchen_cws_layout;
    ImageView feat1P, feat2P, feat3P;

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

    String mUsername, Feat1, Feat2, Feat3, FeatTitle1, FeatTitle2, FeatTitle3;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCommentsDatabaseReference, mDatabaseFeatures;
    EditText message;

    List<FriendlyMessage> mComments;
    private FirebaseAuth mFirebaseAuth;


    //Instantiate adapter object globally
    private ImageAdapter adapter;

    //Instantiate database object globally
    private DatabaseReference database;
    //Initialize array of images of type string
    private List<String> images = new ArrayList<>();


    Button ftitle2, ftitle3;
    RatingBar ratingBar;

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
        pic1 = i.getStringExtra("SpacePic");
        price = i.getStringExtra("price");
        location = i.getStringExtra("location");
        email = i.getStringExtra("email");
        user_name = i.getStringExtra("user_name");


        SendTextLinearLayout = findViewById(R.id.linearLayout);
        placeName = findViewById(R.id.txt_placeName);
        placeLocation = findViewById(R.id.txt_location);
        ratingBar = findViewById(R.id.rating_rating_bar);

        txtHours = findViewById(R.id.txtHours);
        tv_review = findViewById(R.id.tv_review);

        ftitle1 = findViewById(R.id.btn_reviews);
        ftitle2 = findViewById(R.id.btn_ratingStars);
        ftitle3 = findViewById(R.id.btn_title3);

        readAllReviews = findViewById(R.id.txtReadAll);
        txtInformation = findViewById(R.id.txtInformation);

        txtPrice = findViewById(R.id.txtPrice);

        feat1P = findViewById(R.id.feat1Pic);
        feat2P = findViewById(R.id.feat2Pic);
        feat3P = findViewById(R.id.feat3Pic);

        placeName.setText(PlaceName);
        placeLocation.setText(address);
        txtInformation.setText(" " + infor);
        SendTextLinearLayout.setVisibility(View.GONE);

        txtHours.setText(hours);
        txtPrice.setText("R" + price + " per hour");


        //This will from firebase



        parking_cws_layout = findViewById(R.id.parking_cws);
        ac_cws_layout = findViewById(R.id.wifi_cws);
        meeting_cws_layout = findViewById(R.id.meeting_cws);
        kitchen_cws_layout = findViewById(R.id.kitchen_cws);
        workshp_cws_layout = findViewById(R.id.workshop_cws);

        parking_cws = findViewById(R.id.txt_parking);
        ac_cws = findViewById(R.id.txt_wifi);
        meeting_cws = findViewById(R.id.txt_meeting);

        workshp_cws = findViewById(R.id.txt_workshop);
        kitchen_cws = findViewById(R.id.txt_kitchen);




        getFeature();

        FloatingActionButton fab = findViewById(R.id.fab);

        //maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // ------------------- reviews sections
        mUsername = ANONYMOUS;
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mCommentsDatabaseReference = mFirebaseDatabase.getReference().child("comments");

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
                FriendlyMessage friendlyMessage = new FriendlyMessage(mMessageEditText.getText().toString(), mUsername, "time");

                String key = mCommentsDatabaseReference.push().getKey();


                mCommentsDatabaseReference.child(PlaceName).child(key).setValue(friendlyMessage);

                // Clear input box
                mMessageEditText.setText("");
            }
        });


        mCommentsDatabaseReference.child(PlaceName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//

                //Fectching information from database
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    final FriendlyMessage friendlyMessage = snapshot.getValue(FriendlyMessage.class);

                    reviews = dataSnapshot.getChildrenCount();

                    if(reviews >0){

                        tv_review.setVisibility(View.GONE);
                    }


                    //readAllReviews.setText("Read all " + reviews + " reviews");
                    mComments.add(friendlyMessage);
                }

                //Init adapter
                mMessageAdapter = new MessageAdapter(CWSDetailsActivity.this, R.layout.image_item, mComments);

                mMessageListView.setAdapter(mMessageAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //pic slide


        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        /*
        * Initialize adapter and send parameters in constructor
        * Parameters
        * this = Send current activity object to adapter
        * images = List of image url strings Send array to adapter. Will call notifyDatasetChanged on this adapter later.
        **/
        adapter = new ImageAdapter(this, images);
        //viewPager.setAdapter(adapter);


        // Queries Firebase for places endpoint  Sihle
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


        //slide
        catalogList = new ArrayList<>();
        final List<Slide> image = new ArrayList<>();

        Slidedatabase = FirebaseDatabase.getInstance().getReference().child("new_Slide");

        Slidedatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChildren()) {
                    catalogList.clear();
                    for (DataSnapshot catalogSnapshot : dataSnapshot.child(PlaceName).getChildren()) {

                        Slide image1 = catalogSnapshot.getValue(Slide.class);
                        //  Toast.makeText(CWSDetailsActivity.this, "scroller not empty", Toast.LENGTH_SHORT).show();

                        image1.setPic1(image1.getPic1());

                        image.add(image1);

                        LinearLayoutManager layoutManager
                                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

                        RecyclerView ListViewCatalog = findViewById(R.id.recycler_view);
                        Slideadapter = new ImagesAdapter(CWSDetailsActivity.this, image);
//                    Toast.makeText(CatalogActivity.this, ""+catalog.getCatalogtitle(), Toast.LENGTH_SHORT).show();
                        ListViewCatalog.setLayoutManager(layoutManager);

                        ListViewCatalog.setAdapter(Slideadapter);
                    }

                } else {

                    //  Toast.makeText(CWSDetailsActivity.this, "no slide added", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(CWSDetailsActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Slide
        final List<String> images = new ArrayList<>();
        viewpager = findViewById(R.id.pager);

        mDotsLayout = findViewById(R.id.dots);
        mFirebaseDatabaseSlide = FirebaseDatabase.getInstance();
        mPicDatabaseReferencSlide = mFirebaseDatabaseSlide.getReference().child("Slides").child("cws").child(PlaceName);

        mPicDatabaseReferencSlide.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot != null) {

                    if (dataSnapshot.hasChildren()) {

                        slide1 = dataSnapshot.child("pic1").getValue().toString();
                        slide2 = dataSnapshot.child("pic2").getValue().toString();
                        slide3 = dataSnapshot.child("pic3").getValue().toString();

                        images.add(slide1);
                        images.add(slide2);
                        images.add(slide3);

                        adapterV = new ViewPagerAdapter(CWSDetailsActivity.this, images);
                        viewpager.setAdapter(adapterV);
                        addDotsIndicator(0);

                        viewpager.addOnPageChangeListener(viewListerner);
                    } else {

                      //  Toast.makeText(CWSDetailsActivity.this, "data snapshot is has no children", Toast.LENGTH_SHORT).show();
                    }
                } else {

                  //  Toast.makeText(CWSDetailsActivity.this, "data snapshot is null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void addDotsIndicator(int position) {

        mDots = new TextView[3];
        mDotsLayout.removeAllViews();
        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(25);
            mDots[i].setTextColor(getResources().getColor(R.color.colorPrimary));

            mDotsLayout.addView(mDots[i]);
        }

        if (mDots.length > 0) {

            mDots[position].setTextColor(getResources().getColor(R.color.colorAccent));
        }

    }

    ViewPager.OnPageChangeListener viewListerner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void GoToBook(View view) {

        Intent i = new Intent(CWSDetailsActivity.this, bookingActivity.class);
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
        intent.putExtra("user_uid", uid);
        startActivity(intent);
    }


    public void readMore(View view) {
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
        Intent intent = new Intent(this, Read_moreActivity.class);
        intent.putExtra("infor", infor);
        startActivity(intent);
    }

    public void getFeature() {

        mFeatdatabase = FirebaseDatabase.getInstance().getReference().child("new_Features").child(PlaceName);
        mFeatdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChildren()) {

                    Feat1 = dataSnapshot.child("feature1").getValue().toString();
                    Feat2 = dataSnapshot.child("feature2").getValue().toString();
                    Feat3 = dataSnapshot.child("feature3").getValue().toString();

                    FeatTitle1 = dataSnapshot.child("title1").getValue().toString();
                    FeatTitle2 = dataSnapshot.child("title2").getValue().toString();
                    FeatTitle3 = dataSnapshot.child("title3").getValue().toString();

                    //   System.out.print("Feature 1 " + Feat1);
                    //  System.out.print("Feature 2 " + Feat2);
                    //System.out.print("Feature 3 " + Feat3);

                    //Toast.makeText(getApplicationContext(), ""+dataSnapshot.getChildrenCount(), Toast.LENGTH_SHORT).show();

                    Glide.with(getApplicationContext())
                            .load(Feat1) //string
                            .override(80, 80)
                            .into(feat1P); //imageview

                    Glide.with(getApplicationContext())
                            .load(Feat2)
                            .override(80, 80)
                            .into(feat2P);

                    Glide.with(getApplicationContext())
                            .load(Feat3)
                            .override(80, 80)
                            .into(feat3P);

                    ftitle1.setText(FeatTitle1);
                    ftitle2.setText(FeatTitle2);
                    ftitle3.setText(FeatTitle3);


                } else {
                    // Toast.makeText(CWSDetailsActivity.this, "no feature added", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }





}
