package com.mindnotix.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindnotix.model.Wishlist;
import com.mindnotix.youthhub.AboutFragment;
import com.mindnotix.youthhub.R;

import java.util.ArrayList;

/**
 * Created by Admin on 01-02-2018.
 */

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder> {

    Context context;
    ArrayList<Wishlist> whislist;
    AboutFragment aboutFragment;


    public WishListAdapter(FragmentActivity activity, ArrayList<Wishlist> whislist) {
        this.context = activity;
        this.whislist = whislist;
        aboutFragment = new AboutFragment();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_wishlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvSkills.setText(whislist.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return whislist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvSkills;
        ImageView ImageDelete;

        public ViewHolder(View itemView) {
            super(itemView);

            tvSkills = itemView.findViewById(R.id.tvSkills);
            ImageDelete = itemView.findViewById(R.id.ImageDelete);
            ImageDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int id = view.getId();

            switch (id) {

                case R.id.ImageDelete:

                    DeleteDilaog(getAdapterPosition(), whislist);

                    break;
            }

        }
    }

    public void DeleteDilaog(final int adapterPosition, final ArrayList<Wishlist> whislist) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_delete);
        Button btCancel = dialog.findViewById(R.id.btCancel);
        Button btOkay = dialog.findViewById(R.id.btOkay);

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                aboutFragment.deleteWhishlist(adapterPosition, whislist, dialog);
                whislist.remove(adapterPosition);
                notifyItemRemoved(adapterPosition);
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.show();

    }


}
