package com.capitipalismadmin.ui.dashboard.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capitipalismadmin.R;
import com.capitipalismadmin.ui.adapters.UsersSearchAdapter;
import com.capitipalismadmin.ui.dashboard.adapters.menu.DashboardMenuAdapter;
import com.capitipalismadmin.ui.dashboard.adapters.menu.Menu;
import com.capitipalismadmin.ui.search.SearchUser;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private DatabaseReference userDatabase;
    int userSize=0;
    // region Menus Container
    private RecyclerView foundUsersRecyclerView;
    private SpinKitView dashboard_search_user_progress_indicator;
    private boolean isMoreToolsShown = false;
    // endregion

    // region Users List Containers
    private UsersSearchAdapter searchAdapter;
    private ArrayList<String> userIDList;
    private ArrayList<String> userNameList;
    private ArrayList<String> userNewUserList;
    private ArrayList<String> userSearchTagList;
    private ArrayList<String> userValidationList;
    private ArrayList<Long> userBalanceList;
    private ArrayList<String> profileImageList;
    private ArrayList<String> userStatusList;
    // endregion
    // region

    // region Util Initializations
    private static List<Menu> dashboardMenus;
    private static DashboardMenuAdapter menusContainerAdapter;
    private static int spanCount;
    private Context context;
    // endregion

    private static View view;

    private List<Object> contents;

    private static final int GLANCE = 0;
    private static final int MAIN_MENU = 1;
    private static final int RECENT = 2;

    // region Constructor
    public DashboardFragmentAdapter(List<Object> contents) {
        spanCount = 3;
        this.contents = contents;
    }
    // endregion

    // region Get View Type
    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return GLANCE;
            case 1:
                return MAIN_MENU;
            default:
                return RECENT;
        }
    }
    // endregion

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        View view = null;
        context = parent.getContext();
        switch (viewType) {
            case GLANCE: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_home_dashboard_account_quick_info, parent, false);
                ConstraintLayout quick_info_all_users_icon = view.findViewById(R.id.quick_info_all_users_icon);
                TextView userCount=view.findViewById(R.id.user_count_text);
                getUserCount(userCount);

//                userCount.setText("200");

//                if(getUserCount()!=0){
//                                   userCount.setText(getUserCount());
//
//                }
                quick_info_all_users_icon.setOnClickListener(view -> context.startActivity(new Intent(context, SearchUser.class)));
                return new RecyclerView.ViewHolder(view) {

                };
            }
            case MAIN_MENU: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_dashboard_menus, parent, false);
                TextView moreTools = view.findViewById(R.id.fragment_home_dashboard_menus_more_tools);
                ImageView fragment_home_dashboard_menus_search_icon = view.findViewById(R.id.fragment_home_dashboard_menus_search_icon);
                fragment_home_dashboard_menus_search_icon.setOnClickListener(view -> context.startActivity(new Intent(context, SearchUser.class)));
                // moreTools.setOnClickListener(view -> {
                //     showMoreTools();
                //     Toast.makeText(view.getContext(), "cl", Toast.LENGTH_LONG).show();
                // });
                return new RecyclerView.ViewHolder(view) {
                };
            }
            // case RECENT: {
            //     view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_dashboard_account_recent_transactions, parent, false);
            //     return new RecyclerView.ViewHolder(view) {
            //
            //     };
            // }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case GLANCE:
                break;
            case MAIN_MENU:
                initMainMenuItems();
                break;
            case RECENT:
                // initRecentTransactions();
                break;
        }

        initUI();
    }

    // region Initialize UI
    private void initUI() {
        userIDList = new ArrayList<>();
        userNameList = new ArrayList<>();
        userSearchTagList = new ArrayList<>();
        userValidationList = new ArrayList<>();
        userBalanceList = new ArrayList<>();
        profileImageList = new ArrayList<>();
        userStatusList = new ArrayList<>();
        userNewUserList=new ArrayList<>();



        // initMenuItems();
    }
    // endregion

    // region Separate Component Initializations
    // region Init Home Menus
    private void initMainMenuItems() {
        // region UI Elements
        //    @BindView(R.id.fragment_home_dashboard_menus_container)

        // dashboardMenus = new ArrayList<>();
        // menusContainerAdapter = new DashboardMenuAdapter(view.getContext(), dashboardMenus);
        // RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(view.getContext(), spanCount);
        // menusContainer.setLayoutManager(mLayoutManager);
        // menusContainer.setAdapter(menusContainerAdapter);
        // prepareMenus();
        listAllUsers();
    }

    private void listAllUsers() {
        foundUsersRecyclerView = view.findViewById(R.id.fragment_home_dashboard_menus_container);
        foundUsersRecyclerView.setHasFixedSize(true);
        foundUsersRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        dashboard_search_user_progress_indicator = view.findViewById(R.id.dashboard_search_user_progress_indicator);
        DatabaseReference rootDatabaseReference = FirebaseDatabase.getInstance().getReference();

        DatabaseReference allUsers = rootDatabaseReference.child("Users");
//        Query chatQuery = allUsers.orderByChild("date");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userIDList.clear();
                userNameList.clear();
                profileImageList.clear();
                userStatusList.clear();
                foundUsersRecyclerView.removeAllViews();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uID = snapshot.getKey();
                    String status = snapshot.child("status").getValue(String.class);
                    String userName = snapshot.child("userFirstName").getValue(String.class) + " " + snapshot.child("userLastName").getValue(String.class) + " - ID: " + uID;
                    String profilePic = snapshot.child("image").getValue(String.class);
                    String validation = snapshot.child("validation").getValue(String.class);
                    String searchTag = snapshot.child("searchTag").getValue(String.class);
                    Long balance=snapshot.child("balance").getValue(Long.class);
                    String newUser=snapshot.child("new_user").getValue(String.class);

                    userIDList.add(uID);
                    userNameList.add(userName);
                    userSearchTagList.add(searchTag);
                    userValidationList.add(validation);
                    profileImageList.add(profilePic);
                    userStatusList.add(status);
                    userBalanceList.add(balance);
                    userNewUserList.add(newUser);

                    // if (searchedString.length() > 0) {
                    //     if (counter == 15)
                    //         break;
                    // } else {
                    //     foundUsersCount.setText("ALL USERS");
                    //     if (counter == 50)
                    //         break;
                    // }
                }

                // foundUsersCount.setText("ALL USERS");
                // foundUsersCount.setVisibility(View.VISIBLE);
                dashboard_search_user_progress_indicator.setVisibility(View.GONE);
                System.out.println("*************FOUND USERS*************");
                searchAdapter = new UsersSearchAdapter(view.getContext(), userIDList, profileImageList, userNewUserList,userNameList,userBalanceList,userValidationList,userSearchTagList, userStatusList);
                foundUsersRecyclerView.setAdapter(searchAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("*************DB ERROR*************");
                // foundUsersCount.setText("ERROR! UNABLE TO LOAD. PLEASE CHECK YOUR CONNECTION.");
                // foundUsersCount.setVisibility(View.VISIBLE);
                // search_user_progress_indicator.setVisibility(View.GONE);
            }
        };
        allUsers.addListenerForSingleValueEvent(eventListener);
    }

    private void prepareMenus() {
        // Home.getInstance().getString(R.string.myString);
        // Resources res = getResources();
        // String[] planets = res.getString(R.array.planets_array);


        String vouchersTitle = context.getResources().getString(R.string.home_screen_dashboard_menu_item_voucher);
        String vouchersDescription = context.getResources().getString(R.string.home_screen_dashboard_menu_item_voucher_description);

        String topupTitle = context.getResources().getString(R.string.home_screen_dashboard_menu_item_topup);
        String topupDescription = context.getResources().getString(R.string.home_screen_dashboard_menu_item_topup_description);

        String electricityTitle = context.getResources().getString(R.string.home_screen_dashboard_menu_item_electricity_bill);
        String electricityDescription = context.getResources().getString(R.string.home_screen_dashboard_menu_item_electricity_bill_description);

        String waterTitle = context.getResources().getString(R.string.home_screen_dashboard_menu_item_water_bill);
        String waterDescription = context.getResources().getString(R.string.home_screen_dashboard_menu_item_water_bill_description);

        String activityTitle = context.getResources().getString(R.string.home_screen_dashboard_menu_item_activity);
        String activityDescription = context.getResources().getString(R.string.home_screen_dashboard_menu_item_activity_description);

        String withdrawTitle = context.getResources().getString(R.string.home_screen_dashboard_menu_item_withdraw);
        String withdrawDescription = context.getResources().getString(R.string.home_screen_dashboard_menu_item_withdraw_description);

        Menu vouchers = new Menu(vouchersTitle, vouchersDescription, R.drawable.ic_fragment_home_dashboard_menu_item_voucher, "VOUCHER");
        dashboardMenus.add(vouchers);
        Menu topup = new Menu(topupTitle, topupDescription, R.drawable.ic_fragment_home_dashboard_menu_item_topup, "TOPUP");
        dashboardMenus.add(topup);
        Menu electricity = new Menu(electricityTitle, electricityDescription, R.drawable.ic_fragment_home_dashboard_menu_item_electricity, "ELECTRICITY");
        dashboardMenus.add(electricity);
        Menu water = new Menu(waterTitle, waterDescription, R.drawable.ic_fragment_home_dashboard_menu_item_water, "WATER");
        dashboardMenus.add(water);
        Menu activity = new Menu(activityTitle, activityDescription, R.drawable.ic_fragment_home_dashboard_menu_item_activity, "ACTIVITY");
        dashboardMenus.add(activity);
        Menu withdraw = new Menu(withdrawTitle, withdrawDescription, R.drawable.ic_fragment_home_dashboard_menu_item_withdraw, "WITHDRAW");
        dashboardMenus.add(withdraw);


        // Menu withdraw1 = new Menu(withdrawTitle, withdrawDescription, R.drawable.ic_fragment_home_dashboard_menu_item_withdraw, "WITHDRAW");
        // dashboardMenus.add(withdraw1);

        // menusContainerAdapter.notifyDataSetChanged();
    }

    private void showMoreTools() {
        String trafficPenaltyTitle = "Traffic Penalty";
        String trafficPenaltyDescription = "Pay Traffic Penalty";

        String busTicketTitle = "Bus";
        String busTicketDescription = "Buy Bus Tickets";

        String trainTicketTitle = "Train";
        String trainTicketDescription = "Buy Train Tickets";

        String lotteryTicketTitle = "Lottery";
        String lotteryTicketDescription = "Buy Lottery Ticket";

        String cinemaTicketTitle = "Cinema";
        String cinemaTicketDescription = "Buy Cinema Ticket";

        String parksTicketTitle = "Parks";
        String parksTicketDescription = "Buy Park Ticket";

        if (!isMoreToolsShown) {
            Menu trafficPenalty = new Menu(trafficPenaltyTitle, trafficPenaltyDescription, R.drawable.ic_fragment_home_dashboard_menu_item_traffic_penality, "TRAFFIC");
            Menu busTicket = new Menu(busTicketTitle, busTicketDescription, R.drawable.ic_fragment_home_dashboard_menu_item_bus, "BUS");
            Menu trainTicket = new Menu(trainTicketTitle, trainTicketDescription, R.drawable.ic_fragment_home_dashboard_menu_item_train, "TRAIN");
            Menu lotteryTicket = new Menu(lotteryTicketTitle, lotteryTicketDescription, R.drawable.ic_fragment_home_dashboard_menu_item_lottery, "LOTTERY");
            Menu cinemaTicket = new Menu(cinemaTicketTitle, cinemaTicketDescription, R.drawable.ic_fragment_home_dashboard_menu_item_cinema, "CINEMA");
            Menu parksTicket = new Menu(parksTicketTitle, parksTicketDescription, R.drawable.ic_fragment_home_dashboard_menu_item_parks, "PARKS");

            dashboardMenus.add(busTicket);
            dashboardMenus.add(trainTicket);
            dashboardMenus.add(cinemaTicket);
            dashboardMenus.add(parksTicket);
            dashboardMenus.add(trafficPenalty);
            dashboardMenus.add(lotteryTicket);
            isMoreToolsShown = true;
        } else {
            dashboardMenus.remove(11);
            dashboardMenus.remove(10);
            dashboardMenus.remove(9);
            dashboardMenus.remove(8);
            dashboardMenus.remove(7);
            dashboardMenus.remove(6);

            isMoreToolsShown = false;
        }
        menusContainerAdapter.notifyDataSetChanged();
        foundUsersRecyclerView.setAdapter(menusContainerAdapter);
    }

    private void initMainMenuEventHandlers() {

    }
    // endregion

    // endregion

    // class MoreTools extends RecyclerView.ViewHolder {
    //
    //     public MoreTools(@NonNull View itemView) {
    //         super(itemView);
    //         TextView moreItems = view.findViewById(R.id.fragment_home_dashboard_menus_more_tools);
    //         moreItems.setOnClickListener(view -> {
    //             Toast.makeText(itemView.getContext(), "Clicked", Toast.LENGTH_LONG).show();
    //         });
    //     }
    // }
    //region get User Count
    public void getUserCount(TextView textView){
        userDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        userDatabase
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // get total available quest
                       int  userSize = (int) dataSnapshot.getChildrenCount();
                        System.out.println("User Suze IS "+userSize);
                        textView.setText(String.valueOf(userSize));
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


    }
    //endregion
}
