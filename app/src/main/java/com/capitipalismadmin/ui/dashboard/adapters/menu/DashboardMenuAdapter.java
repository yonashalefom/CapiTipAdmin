package com.capitipalismadmin.ui.dashboard.adapters.menu;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capitipalismadmin.R;
import com.capitipalismadmin.classes.downloaders.image.PicassoDownloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DashboardMenuAdapter extends RecyclerView.Adapter<DashboardMenuAdapter.MyViewHolder> {
    private Context mContext;
    private List<Menu> menuList;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, count;
        ImageView thumbnail, moreOptions;

        MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            count = view.findViewById(R.id.count);
            thumbnail = view.findViewById(R.id.thumbnail);
            moreOptions = view.findViewById(R.id.more_options);
        }
    }

    public DashboardMenuAdapter(Context mContext, List<Menu> menuList) {
        this.mContext = mContext;
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_home_dashboard_menus_single_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Menu menu = menuList.get(position);
        holder.title.setText(menu.getName());
        holder.count.setText(menu.getNumOfSongs());
        System.out.println("*****************************************************");
        System.out.println("Thumbnail: " + menu.getThumbnail());
        System.out.println("*****************************************************");
        PicassoDownloader.downloadLocalImage(mContext, menu.getThumbnail(), holder.thumbnail);
        holder.thumbnail.setOnClickListener(view -> {
            switch (menu.getIdentifier()) {
                case "VOUCHER":
                    Toast.makeText(mContext, "You Clicked: Voucher", Toast.LENGTH_SHORT).show();
                    // mContext.startActivity(new Intent(mContext, Voucher.class));
                    break;
                case "TOPUP":
                    Toast.makeText(mContext, "You Clicked: Topup", Toast.LENGTH_SHORT).show();
                    break;
                case "ELECTRICITY":
                    // Toast.makeText(mContext, "You Clicked: Electricity", Toast.LENGTH_SHORT).show();
                    // mContext.startActivity(new Intent(mContext, ElectricityBill.class));
                    break;
                case "WATER":
                    Toast.makeText(mContext, "You Clicked: Water", Toast.LENGTH_SHORT).show();
                    break;
                case "ACTIVITY":
                    Toast.makeText(mContext, "You Clicked: Activity", Toast.LENGTH_SHORT).show();
                    break;
                case "WITHDRAW":
                    Toast.makeText(mContext, "You Clicked: Withdraw", Toast.LENGTH_SHORT).show();
                    break;
                case "TRAFFIC":
                    Toast.makeText(mContext, "You Clicked: Voucher", Toast.LENGTH_SHORT).show();

                    break;
                case "BUS":
                    Toast.makeText(mContext, "You Clicked: Topup", Toast.LENGTH_SHORT).show();
                    // mContext.startActivity(new Intent(mContext, BusTicket.class));
                    break;
                case "TRAIN":
                    // Toast.makeText(mContext, "You Clicked: Electricity", Toast.LENGTH_SHORT).show();
                    // mContext.startActivity(new Intent(mContext, TrainTicket.class));
                    break;
                case "LOTTERY":
                    Toast.makeText(mContext, "You Clicked: Water", Toast.LENGTH_SHORT).show();
                    break;
                case "CINEMA":
                    Toast.makeText(mContext, "You Clicked: Activity", Toast.LENGTH_SHORT).show();
                    break;
                case "PARKS":
                    Toast.makeText(mContext, "You Clicked: Withdraw", Toast.LENGTH_SHORT).show();
                    break;
            }
        });
        Picasso.with(mContext).load(R.drawable.ic_global_vertical_dots).into(holder.moreOptions);
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }
}

/**
 * Showing popup menu when tapping on 3 dots
 * <p>
 * Click listener for popup menu items
 * <p>
 * Click listener for popup menu items
 * <p>
 * Click listener for popup menu items
 * <p>
 * Click listener for popup menu items
 * <p>
 * Click listener for popup menu items
 * <p>
 * Click listener for popup menu items
 * <p>
 * Click listener for popup menu items
 * <p>
 * Click listener for popup menu items
 * <p>
 * Click listener for popup menu items
 * <p>
 * Click listener for popup menu items
 * <p>
 * Click listener for popup menu items
 * <p>
 * Click listener for popup menu items
 */
//    private void showPopupMenu(View view) {
// inflate menu
//        PopupMenu popup = new PopupMenu(mContext, view);
//        MenuInflater inflater = popup.getMenuInflater();
//        inflater.inflate(R.menu.fragment_home_menu, popup.getMenu());
//        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
//        popup.show();
//    }

/**
 * Click listener for popup menu items
 */
//class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
//
//    public MyMenuItemClickListener() {
//    }
//
//    @Override
//    public boolean onMenuItemClick(MenuItem menuItem) {
//        switch (menuItem.getItemId()) {
//            case R.id.action_add_favourite:
//                Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.action_play_next:
//                Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
//                return true;
//            default:
//        }
//        return false;
//    }
//}
//
//    @Override
//    public int getItemCount() {
//        return menuList.size();
//    }
//
//    private void servicesOnClickHandler() {
//        Intent agentServices = new Intent(mContext, AgentServices.class);
//        mContext.startActivity(agentServices);
//    }
//
//    private void transaction() {
//        Intent agentServices = new Intent(mContext, TransactionActivity.class);
//        mContext.startActivity(agentServices);
//    }
//
//    private void paymentsOnClickHandler() {
//        Intent payments = new Intent(mContext, Accounts.class);
//        mContext.startActivity(payments);
//    }
//}