package com.capitipalismadmin.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.capitipalismadmin.R;
import com.capitipalismadmin.classes.CapiUserManager;
import com.capitipalismadmin.ui.helpers.AlertCreator;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginColorConfirmation extends AppCompatActivity {
    @BindView(R.id.login_confirmation_page_main_background)
    ConstraintLayout login_confirmation_page_main_background;
    // region Initializations
    // region Color Inputs
    @BindView(R.id.color_code_input_1)
    ConstraintLayout color_code_input_1;
    @BindView(R.id.color_code_input_2)
    ConstraintLayout color_code_input_2;
    @BindView(R.id.color_code_input_3)
    ConstraintLayout color_code_input_3;
    @BindView(R.id.color_code_input_4)
    ConstraintLayout color_code_input_4;
    @BindView(R.id.color_code_input_5)
    ConstraintLayout color_code_input_5;
    @BindView(R.id.color_code_input_6)
    ConstraintLayout color_code_input_6;
    @BindView(R.id.color_code_input_7)
    ConstraintLayout color_code_input_7;
    @BindView(R.id.color_code_input_8)
    ConstraintLayout color_code_input_8;
    @BindView(R.id.color_code_input_9)
    ConstraintLayout color_code_input_9;
    @BindView(R.id.color_code_input_10)
    ConstraintLayout color_code_input_10;
    @BindView(R.id.color_code_input_clear)
    ConstraintLayout color_code_input_clear;
    @BindView(R.id.color_code_input_erase)
    ConstraintLayout color_code_input_erase;
    // endregion

    // region Color Codes Preview
    @BindView(R.id.color_code_preview_1)
    ConstraintLayout color_code_preview_1;
    @BindView(R.id.color_code_preview_2)
    ConstraintLayout color_code_preview_2;
    @BindView(R.id.color_code_preview_3)
    ConstraintLayout color_code_preview_3;
    @BindView(R.id.color_code_preview_4)
    ConstraintLayout color_code_preview_4;
    @BindView(R.id.color_code_preview_5)
    ConstraintLayout color_code_preview_5;
    // endregion

    @BindView(R.id.login_btn_login)
    Button login_btn_login;
    @BindView(R.id.login_progress_color_confirmation_page)
    SpinKitView login_progress_color_confirmation_page;

    StringBuilder finalColorCode = new StringBuilder();
    private String userID;
    private String userType;

    private boolean cameFromWallet = false;
    private boolean cameFromChildUsersPasswordChange = false;
    private boolean cameFromLogin = false;
    private String walletName;
    private String walletAddress;
    private String walletMnemonic;
    private String walletXpub;
    private String userIDPassedFromLogin;

    private int passwordStatus = 0; // 0 = neutral 1 = password_filled -1 = password_not_fully_filled
    final int[] currentColorCode = new int[]{0, 0, 0, 0, 0};
    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setAppTheme();
        setContentView(R.layout.activity_login_color_confirmation);
        ButterKnife.bind(this);

        if (getIntent().getStringExtra("userID") != null) {
            userID = getIntent().getStringExtra("userID");
        }

        if (getIntent().getStringExtra("userType") != null) {
            userType = getIntent().getStringExtra("userType");
        }

        if (getIntent().getStringExtra("walletInfoSet") != null) {
            System.out.println("WALLET INFO SET");
            cameFromWallet = true;
            userType = getIntent().getStringExtra("userType");
            walletName = getIntent().getStringExtra("walletName");
            walletAddress = getIntent().getStringExtra("walletAddress");
            walletMnemonic = getIntent().getStringExtra("walletMnemonic");
            walletXpub = getIntent().getStringExtra("walletXpub");
        }

        if (getIntent().getStringExtra("cameFromLogin") != null) {
            cameFromLogin = true;
            userIDPassedFromLogin = getIntent().getStringExtra("uID");
        }

        if (getIntent().getStringExtra("changeUserColorPassword") != null) {
            cameFromChildUsersPasswordChange = true;
            userID = getIntent().getStringExtra("currentlySelectedUserId");
        }

        initColorCodeInput();
    }

    // region Init UI
    private void initColorCodeInput() {
        initColorCodeInputsEventHandler();
        renderColorCode();
    }

    // region Set click handlers for color password inputs
    private void initColorCodeInputsEventHandler() {
        color_code_input_1.setOnClickListener(v -> {
            colorCodeInputOnClickHandler(1);
        });
        color_code_input_2.setOnClickListener(v -> {
            colorCodeInputOnClickHandler(2);
        });
        color_code_input_3.setOnClickListener(v -> {
            colorCodeInputOnClickHandler(3);
        });
        color_code_input_4.setOnClickListener(v -> {
            colorCodeInputOnClickHandler(4);
        });
        color_code_input_5.setOnClickListener(v -> {
            colorCodeInputOnClickHandler(5);
        });
        color_code_input_6.setOnClickListener(v -> {
            colorCodeInputOnClickHandler(6);
        });
        color_code_input_7.setOnClickListener(v -> {
            colorCodeInputOnClickHandler(7);
        });
        color_code_input_8.setOnClickListener(v -> {
            colorCodeInputOnClickHandler(8);
        });
        color_code_input_9.setOnClickListener(v -> {
            colorCodeInputOnClickHandler(9);
        });
        color_code_input_10.setOnClickListener(v -> {
            colorCodeInputOnClickHandler(10);
        });
        // ------------------------------
        color_code_input_clear.setOnClickListener(v -> {
            resetColorCodeInput();
        });
        color_code_input_erase.setOnClickListener(v -> {
            clearLastColorCode();
        });
        login_btn_login.setOnClickListener(v -> {
            if (passwordStatus == 1) {
                if (cameFromChildUsersPasswordChange) {
                    sendNewPasswordInfo();
                } else {
                    AlertCreator.showErrorAlert(LoginColorConfirmation.this, "Developer Note", "Please don't forge to uncomment the next line");
//                    finishVerification();
                }
            } else if (passwordStatus == 0) {
                AlertCreator.showErrorAlert(LoginColorConfirmation.this, "Enter Credentials", "Please enter your color password in order to login.");
            }
        });
    }
    // endregion
    // endregion

    // region Finish Login
    private void finishVerification() {
        if (finalColorCode.length() == 5) {
            login_progress_color_confirmation_page.setVisibility(View.VISIBLE);
            login_btn_login.setClickable(false);
            login_btn_login.setBackground(getResources().getDrawable(R.drawable.hyphen_button_inactive));
            DatabaseReference databaseReference;
            if (cameFromLogin) {
                databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userIDPassedFromLogin);
            } else {
                databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(CapiUserManager.getCurrentUserID());
            }
//            String groupID = GroupManager.getGroupID();
//            String ownerID = GroupManager.getOwnerID();

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final String[] loginStatus = {"NOT_SET"};
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String uID = snapshot.getKey();
                        String userColorPassword = dataSnapshot.child("colorPassword").getValue(String.class);
                        if (userColorPassword != null) {
                            if (Objects.requireNonNull(userColorPassword).compareTo(String.valueOf(finalColorCode)) == 0) {
                                loginStatus[0] = "VERIFICATION_SUCCESSFUL";
                            } else {
                                loginStatus[0] = "UNABLE_TO_VERIFY";
                            }
                        } else {
                            loginStatus[0] = "UNABLE_TO_VERIFY";
                        }
                    }

                    // region Login Finalize
                    if (loginStatus[0].equals("VERIFICATION_SUCCESSFUL")) {
                        Intent i = new Intent().putExtra("VERIFICATION_RESULT", "VERIFICATION_SUCCESSFUL");

                        if (cameFromWallet) {
                            i.putExtra("walletInfoSet", "TRUE");
                            i.putExtra("walletName", walletName);
                            i.putExtra("walletAddress", walletAddress);
                            i.putExtra("walletMnemonic", walletMnemonic);
                            i.putExtra("walletXpub", walletXpub);
                        }

                        if (cameFromLogin) {
                            i.putExtra("cameFromLogin", "TRUE");
                            i.putExtra("uID", userIDPassedFromLogin);
                        }

                        setResult(RESULT_OK, i);
                        finish();
                    } else if (loginStatus[0].equals("UNABLE_TO_VERIFY")) {
                        AlertCreator.showErrorAlert(LoginColorConfirmation.this, "Unable To Verify!", "We were not able to verify its you. Please make sure you've entered a valid color code.");
                        login_progress_color_confirmation_page.setVisibility(View.GONE);
                        login_btn_login.setClickable(true);
                        login_btn_login.setBackground(getResources().getDrawable(R.drawable.hyphen_button_default));
                    }
                    // endregion
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    login_progress_color_confirmation_page.setVisibility(View.GONE);
                    login_btn_login.setClickable(true);
                    // login_btn_login.setBackground(getResources().getDrawable(R.drawable.hyphen_button_default));
                    System.out.println("Database Error: Something is wrong!");
                }
            });
        }
    }
    // endregion

    // region Finish Login
    private void sendNewPasswordInfo() {
        if (finalColorCode.length() == 5) {
            Intent i = new Intent().putExtra("VERIFICATION_RESULT", "NEW_PASSWORD_SET");
            i.putExtra("NEW_COLOR_CODE", String.valueOf(finalColorCode));
            i.putExtra("currentlySelectedUserId", userID);
            setResult(RESULT_OK, i);
            finish();
        } else {
            AlertCreator.showErrorAlert(LoginColorConfirmation.this, "Error!", "There was an error! Please try again.");
        }
    }
    // endregion

    // region Color Code Related
    private void resetColorCodeInput() {
        Arrays.fill(currentColorCode, 0);
        renderColorCode();
    }

    private void clearLastColorCode() {
        for (int i = currentColorCode.length - 1; i > -1; i--) {
            if (currentColorCode[i] > 0) {
                currentColorCode[i] = 0;
                break;
            }
        }
        renderColorCode();
    }

    private void renderColorCode() {
        finalColorCode.setLength(0);
        int counter = 1;
        int filledPassword = 0;
        for (int a : currentColorCode) {
            if (a != 0) {
                filledPassword++;
            }
            if (counter == 1) {
                color_code_preview_1.setBackgroundResource(getColorCodeDrawableViaColorCode(a));
            } else if (counter == 2) {
                color_code_preview_2.setBackgroundResource(getColorCodeDrawableViaColorCode(a));
            } else if (counter == 3) {
                color_code_preview_3.setBackgroundResource(getColorCodeDrawableViaColorCode(a));
            } else if (counter == 4) {
                color_code_preview_4.setBackgroundResource(getColorCodeDrawableViaColorCode(a));
            } else if (counter == 5) {
                color_code_preview_5.setBackgroundResource(getColorCodeDrawableViaColorCode(a));
            }
            finalColorCode.append(a);
            counter++;
        }

        if (filledPassword == 0) {
            passwordStatus = 0;
            login_btn_login.setClickable(true);
            // login_btn_login.setBackground(getResources().getDrawable(R.drawable.hyphen_button_default));
        } else if (filledPassword > 0 && filledPassword < 5) {
            passwordStatus = -1;
            login_btn_login.setClickable(false);
//            login_btn_login.setBackground(getResources().getDrawable(R.drawable.hyphen_button_inactive));
        } else if (filledPassword == 5) {
            passwordStatus = 1;
            login_btn_login.setClickable(true);
            // login_btn_login.setBackground(getResources().getDrawable(R.drawable.hyphen_button_default));
        }

        System.out.println("*************************************");
        System.out.println("Color Code: " + finalColorCode);
        System.out.println("Password Status: " + passwordStatus);
        System.out.println("Filled Password: " + filledPassword);
        System.out.println("*************************************");
    }

    private int getColorCodeDrawableViaColorCode(int colorCode) {
        if (colorCode == 1) {
            return R.drawable.color_password_button_1;
        } else if (colorCode == 2) {
            return R.drawable.color_password_button_2;
        } else if (colorCode == 3) {
            return R.drawable.color_password_button_3;
        } else if (colorCode == 4) {
            return R.drawable.color_password_button_4;
        } else if (colorCode == 5) {
            return R.drawable.color_password_button_5;
        } else if (colorCode == 6) {
            return R.drawable.color_password_button_6;
        } else if (colorCode == 7) {
            return R.drawable.color_password_button_7;
        } else if (colorCode == 8) {
            return R.drawable.color_password_button_8;
        } else if (colorCode == 9) {
            return R.drawable.color_password_button_9;
        } else if (colorCode == 10) {
            return R.drawable.color_password_button_10;
        } else {
            return R.drawable.color_password_placeholder;
        }
    }

    private void colorCodeInputOnClickHandler(int colorCode) {
        int counter = 0;
        for (int a : currentColorCode) {
            if (a == 0) {
                currentColorCode[counter] = colorCode;
                break;
            }
            counter++;
        }
        renderColorCode();
    }
    // endregion
}