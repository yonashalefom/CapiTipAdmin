package com.capitipalismadmin.holders;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class UserHolderSearch extends RecyclerView.ViewHolder {
    private final String TAG = "CA/UserHolder";

    private Activity activity;
    private View view;
    private Context context;

    // Will handle user data

    private DatabaseReference userDatabase;
    private ValueEventListener userListener;

    public UserHolderSearch(Activity activity, View view, Context context) {
        super(view);

        this.activity = activity;
        this.view = view;
        this.context = context;
    }

    public View getView() {
        return view;
    }

    public void setHolder(String userID) {
        // final TextView user_holder_main_container = view.findViewById(R.id.user_holder_main_container);
        // final TextView userName = view.findViewById(R.id.user_name);
        // final TextView userStatus = view.findViewById(R.id.user_status);
        // final CircleImageView userImage = view.findViewById(R.id.user_image);
        //
        // if (userDatabase != null & userListener != null) {
        //     userDatabase.removeEventListener(userListener);
        // }

        // Initialize/Update user data

        // userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        // userListener = new ValueEventListener() {
        //     Timer timer; // Will be used to avoid flickering online status when changing activity
        //
        //     @Override
        //     public void onDataChange(DataSnapshot dataSnapshot) {
        //         try {
        //
        //             final String firstName = Objects.requireNonNull(dataSnapshot.child("userFirstName").getValue()).toString();
        //             final String lastName = Objects.requireNonNull(dataSnapshot.child("userLastName").getValue()).toString();
        //             final String userID = Objects.requireNonNull(dataSnapshot.child("userID").getValue()).toString();
        //             final String name = firstName + " " + lastName + " - ID: " + userID;
        //             final String status = Objects.requireNonNull(dataSnapshot.child("status").getValue()).toString();
        //             final String image = Objects.requireNonNull(dataSnapshot.child("image").getValue()).toString();
        //
        //             userName.setText(name);
        //             userStatus.setText(status);
        //
        //             if (!image.equals("default")) {
        //                 Picasso.with(context)
        //                         .load(image)
        //                         .resize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics()), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics()))
        //                         .centerCrop()
        //                         .networkPolicy(NetworkPolicy.OFFLINE)
        //                         .placeholder(R.drawable.img_user_placeholder)
        //                         .into(userImage, new Callback() {
        //                             @Override
        //                             public void onSuccess() {
        //
        //                             }
        //
        //                             @Override
        //                             public void onError() {
        //                                 Picasso.with(context)
        //                                         .load(image)
        //                                         .resize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics()), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics()))
        //                                         .centerCrop()
        //                                         .placeholder(R.drawable.img_user_placeholder)
        //                                         .error(R.drawable.img_user_placeholder)
        //                                         .into(userImage);
        //                             }
        //                         });
        //             } else {
        //                 userImage.setImageResource(R.drawable.img_user_placeholder);
        //             }
        //         } catch (Exception e) {
        //             Log.d(TAG, "userListener exception: " + e.getMessage());
        //             e.printStackTrace();
        //         }
        //     }
        //
        //     @Override
        //     public void onCancelled(DatabaseError databaseError) {
        //         Log.d(TAG, "userListener failed: " + databaseError.getMessage());
        //     }
        // };
        // userDatabase.addValueEventListener(userListener);

        // user_holder_main_container.setBackgroundColor(Color.RED);
        // user_holder_main_container.setOnClickListener(view -> {
        //     System.out.println("user holder clicked.");
        //     Intent userProfileIntent = new Intent(activity, Profile.class);
        //     userProfileIntent.putExtra("userID", userID);
        //     activity.startActivity(userProfileIntent);
        // });
    }
}
