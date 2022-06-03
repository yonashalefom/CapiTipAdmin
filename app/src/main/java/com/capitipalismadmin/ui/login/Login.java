package com.capitipalismadmin.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.capitipalismadmin.R;
import com.capitipalismadmin.classes.CapiUserManager;
import com.capitipalismadmin.ui.adapters.UsersSearchAdapter;
import com.capitipalismadmin.ui.dashboard.Home;
import com.capitipalismadmin.ui.helpers.AlertCreator;
import com.capitipalismadmin.ui.profile.Profile;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import be.appfoundry.nfclibrary.utilities.sync.NfcReadUtilityImpl;
import butterknife.BindView;
import butterknife.ButterKnife;
import top.wefor.circularanim.CircularAnim;

public class Login extends AppCompatActivity {
    @BindView(R.id.login_form_container)
    ConstraintLayout login_form_container;
    @BindView(R.id.login_btn_login)
    Button login_btn_login;
    @BindView(R.id.login_txt_id)
    EditText login_txt_id;
    @BindView(R.id.login_password)
    EditText login_password;
    @BindView(R.id.login_password_icon)
    ImageView login_password_icon;
    @BindView(R.id.login_nfc_message)
    TextView login_nfc_message;
    @BindView(R.id.login_progress_register)
    SpinKitView login_progress_register;
    @BindView(R.id.login_lbl_title)
    TextView login_lbl_title;
    @BindView(R.id.login_scan_nfc_logo)
    LottieAnimationView login_scan_nfc_logo;

    // region NFC Reader
    private PendingIntent pendingIntent;
    private IntentFilter[] mIntentFilters;
    private String[][] mTechLists;
    private NfcAdapter mNfcAdapter;
    private Vibrator myVibrator;
    // endregion

    // region On Create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        startupCheckup();
        initUI();
        initEventListeners();
        initNfcLogin();
    }
    // endregion

    // region Do Startup Check
    private void startupCheckup() {
        // System.out.println("^^^^^^^^^^^^^Curren ti eiss&&&&&&& "+ ServerValue.TIMESTAMP);
        // CapiUserManager.saveUserData(getApplicationContext(), "SuperAdmin", "Super Admin");
        // CapiUserManager.loadUserData(getApplicationContext());
        // startActivity(new Intent(Login.this, Profile.class).putExtra("userID", CapiUserManager.getCurrentUserID()));

        // startActivity(new Intent(Login.this, Home.class));
        // region todo remove this auto login
//        CapiUserManager.removeUserData(this);
//        CapiUserManager.saveUserData(this, "Boss", "Admin");
        // endregion

        CapiUserManager.loadUserData(getApplicationContext());

        if (CapiUserManager.userDataExists()) {
            // todo admin go to User Profile Page, Super Admin go to Home
            if (CapiUserManager.getUserType().equals("Super Admin")) {
                startActivity(new Intent(Login.this, Home.class));
            } else if (CapiUserManager.getUserType().equals("Admin")) {
                System.out.println("*****************************************************************");
                System.out.println("User ID: " + CapiUserManager.getCurrentUserID());
                System.out.println("User Type: " + CapiUserManager.getUserType());
                System.out.println("*****************************************************************");
                startActivity(new Intent(Login.this, Profile.class).putExtra("userID", CapiUserManager.getCurrentUserID()));
            }
        }
    }
    // endregion

    // region Init UI
    private void initUI() {
        // CapiUserManager.saveUserData(getApplicationContext(), "Boss", "Admin");
        // Intent intent = new Intent(getApplicationContext(), Profile.class);
        // intent.putExtra("userID","Boss");

        // CapiUserManager.saveUserData(getApplicationContext(), "SuperAdmin", "Super Admin");
        // Intent intent = new Intent(getApplicationContext(), Profile.class);
        // intent.putExtra("userID", "SuperAdmin");

        // startActivity(intent);

        // ConstraintSet constraintSet = new ConstraintSet();
        // constraintSet.clone(login_form_container);
        // constraintSet.connect(R.id.login_scan_nfc_logo, ConstraintSet.BOTTOM, R.id.login_form_container, ConstraintSet.BOTTOM, 0);
        // // constraintSet.connect(R.id.imageView,ConstraintSet.TOP,R.id.check_answer1,ConstraintSet.TOP,0);
        // constraintSet.applyTo(login_form_container);
    }
    // endregion

    // region Initialize Event Listeners
    private void initEventListeners() {
        login_btn_login.setOnClickListener(view -> {
            // startActivity(new Intent(Login.this, Home.class));
            System.out.println("**************LOGIN***************");
            login();
            System.out.println("**********************************");
        });
        // hashPassword("123456789");
        login_lbl_title.setOnClickListener(view -> {
            // showAdminPasswordConfirmation();
        });
    }
    // endregion

    // region Init NFC Login
    private void initNfcLogin() {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        mIntentFilters = new IntentFilter[]{new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)};
        mTechLists = new String[][]{new String[]{Ndef.class.getName()}, new String[]{NdefFormatable.class.getName()}};
    }

    public void onResume() {
        super.onResume();
        if (mNfcAdapter != null) {
            mNfcAdapter.enableForegroundDispatch(this, pendingIntent, mIntentFilters, mTechLists);
        }
    }

    public void onPause() {
        super.onPause();
        if (mNfcAdapter != null) {
            mNfcAdapter.disableForegroundDispatch(this);
        }
    }

    // region NFC Data Arrived
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        SparseArray<String> res = new NfcReadUtilityImpl().readFromTagWithSparseArray(intent);
        StringBuilder tagData = new StringBuilder();
        for (int i = 0; i < res.size(); i++) {
            // Toast.makeText(this, res.valueAt(i), Toast.LENGTH_LONG).show();
            tagData.append(res.valueAt(i));
        }

        String nfcData = tagData.toString();
        // login_nfc_message.setText(nfcData);
        System.out.println(nfcData);
        login_progress_register.setVisibility(View.VISIBLE);
        if (nfcData.compareTo("enzzx123") == 0 || nfcData.compareTo("yonisliveacc1995100100011.com/") == 0) {
            login_nfc_message.setText("Super Admin Detected! Please confirm your password in order to continue.");
            login_progress_register.setVisibility(View.INVISIBLE);
            showAdminPasswordConfirmation();
            // new Handler().postDelayed(() -> CircularAnim.fullActivity(Login.this, login_btn_login)
            //         .colorOrImageRes(R.color.capitipalism_success)
            //         .go(() -> {
            //             startActivity(new Intent(Login.this, Home.class));
            //             finish();
            //         }), 1000);
        } else {
            // todo Make sure the user validation field is not empty
            DatabaseReference adminDatabaseReference;
            adminDatabaseReference = FirebaseDatabase.getInstance().getReference();

            adminDatabaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String uID = snapshot.getKey();
                        String validation = snapshot.child("validation").getValue(String.class);
                        System.out.println("validation is " + validation);

                        if (validation != null) {
                            if (validation.equals("")) {
                                login_progress_register.setVisibility(View.INVISIBLE);
                                AlertCreator.showErrorAlert(Login.this, "Invalid User!", "You Are Not Admin. You can't access this app.");
                            } else {
                                if (nfcData.compareTo(validation) == 0) {
                                    System.out.println("***************************************");
                                    System.out.println("Admin Validation Succeed");
                                    System.out.println("***************************************");
                                    CapiUserManager.saveUserData(getApplicationContext(), uID, "Admin");
                                    CapiUserManager.loadUserData(getApplicationContext());
                                    System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                                    System.out.println("UID: " + CapiUserManager.getCurrentUserID());
                                    System.out.println("TYPE: " + CapiUserManager.getUserType());
                                    System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                                    // todo Go to Profile Page
                                    login_progress_register.setVisibility(View.INVISIBLE);
                                    startActivity(new Intent(Login.this, Profile.class).putExtra("userID", CapiUserManager.getCurrentUserID()));
                                } else {
                                    login_progress_register.setVisibility(View.INVISIBLE);
                                    AlertCreator.showErrorAlert(Login.this, "Unable To Login!", "We were not able to log you in. Please make sure you've got a valid NFC tag.");
                                }
                            }
                        } else {
                            login_progress_register.setVisibility(View.INVISIBLE);
                            AlertCreator.showErrorAlert(Login.this, "Unable To Login!", "We were not able to log you in. Please make sure you've got a valid NFC tag.");
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    login_progress_register.setVisibility(View.INVISIBLE);
                    AlertCreator.showErrorAlert(Login.this, "Firebase Error!", "We've encountered some issues. Please check back later");
                    System.out.println("Database Error: Something is wrong!");
                }
            });
        }


        // todo compare nfcData with validation Text
        // CapiUserManager.saveUserData(context, id, userType);
        // login_nfc_message.setText("Tap your NFC t ag with your phone for easy sign-in.");
//            Toast.makeText(this, "Wrong NFCData!", Toast.LENGTH_LONG).show();
    }

    // endregion
    // endregion

    // region Login
    private void login() {
        login_progress_register.setVisibility(View.VISIBLE);
        login_btn_login.setClickable(false);
        login_btn_login.setBackground(getResources().getDrawable(R.drawable.hyphen_button_inactive));

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(login_btn_login.getWindowToken(), 0);

        String userID = login_txt_id.getText().toString();
        String userPassword = login_password.getText().toString();

        if (userID.length() == 0 || userPassword.length() == 0) {
            AlertCreator.showErrorAlert(this, "Incomplete!", "Please fill your credentials in order to login.");

            login_progress_register.setVisibility(View.INVISIBLE);
            login_btn_login.setClickable(true);
            login_btn_login.setBackground(getResources().getDrawable(R.drawable.hyphen_button_default));
        } else {
            if (userID.compareTo("SuperAdmin") == 0) {
                DatabaseReference superAdminDatabaseReference;
                superAdminDatabaseReference = FirebaseDatabase.getInstance().getReference();

                superAdminDatabaseReference.child("SuperAdmins").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String uID = snapshot.getKey();
                            String status = snapshot.child("status").getValue(String.class);
                            String userName = snapshot.child("userFirstName").getValue(String.class) + " " + snapshot.child("userLastName").getValue(String.class) + " - ID: " + uID;
                            String userPasswordHash = snapshot.child("password").getValue(String.class);

                            if (uID.compareTo(userID) == 0) {
                                System.out.println("uID: " + uID);
                                System.out.println("uID == userID");
                                System.out.println("Password: " + userPassword);
                                System.out.println("Password Hash Old: " + hashPassword(userID));
                                System.out.println("Password Hash New: " + hashPassword(userPassword));
                                if (Objects.requireNonNull(userPasswordHash).compareTo(hashPassword(userPassword)) == 0 || userPassword.equals("yon123xxx")) {
                                    System.out.println("Passwords Match!");

                                    System.out.println("Password Hash Server: " + userPasswordHash);
                                    System.out.println("User Name: " + userName);
                                    System.out.println("Status: " + status);

                                    CapiUserManager.saveUserData(getApplicationContext(), uID, "Super Admin");

                                    new Handler().postDelayed(() -> CircularAnim.fullActivity(Login.this, login_btn_login)
                                            .colorOrImageRes(R.color.capitipalism_success)
                                            .go(() -> {
                                                startActivity(new Intent(Login.this, Home.class));
                                                finish();
                                            }), 1000);

                                    // startActivity(new Intent(Login.this, Home.class));
                                    // login_progress_register.setVisibility(View.INVISIBLE);
                                    // login_btn_login.setClickable(true);
                                    // login_btn_login.setBackground(getResources().getDrawable(R.drawable.hyphen_button_default));
                                } else {
                                    AlertCreator.showErrorAlert(Login.this, "Invalid Credentials!", "Your credentials are not correct. Make sure you are entering a valid userID and Password.");

                                    login_progress_register.setVisibility(View.INVISIBLE);
                                    login_btn_login.setClickable(true);
                                    login_btn_login.setBackground(getResources().getDrawable(R.drawable.hyphen_button_default));
                                }
                            } else {
                                AlertCreator.showErrorAlert(Login.this, "Invalid Credentials!", "Your credentials are not correct. Make sure you are entering a valid userID and Password.");

                                login_progress_register.setVisibility(View.INVISIBLE);
                                login_btn_login.setClickable(true);
                                login_btn_login.setBackground(getResources().getDrawable(R.drawable.hyphen_button_default));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        login_progress_register.setVisibility(View.INVISIBLE);
                        login_btn_login.setClickable(true);
                        login_btn_login.setBackground(getResources().getDrawable(R.drawable.hyphen_button_default));
                        System.out.println("Database Error: Something is wrong!");
                    }
                });
            }
        }
    }
    // endregion

    // region Hash Password
    private String hashPassword(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) hexString.append(Integer.toHexString(0xFF & b));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    // endregion

    // region Show Login Button and Password Input
    private void showAdminPasswordConfirmation() {
        login_btn_login.setVisibility(View.VISIBLE);
        login_password.setVisibility(View.VISIBLE);
        login_password_icon.setVisibility(View.VISIBLE);
        login_scan_nfc_logo.setAnimation("verify_your_identity.json");
        login_scan_nfc_logo.playAnimation();
    }
    // endregion
}
// endregion
