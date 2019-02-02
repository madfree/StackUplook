package com.madfree.stackuplook;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class ItemAdapter extends PagedListAdapter<Item, ItemAdapter.ItemViewHolder> {

    private Context mContext;

    protected ItemAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.mContext = context;
    }

    @NonNull
    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemViewHolder itemViewHolder, int position) {

        Item item = getItem(position);

        if (item != null) {
            Glide.with(mContext)
                    .load(item.owner.profile_image)
                    .into(itemViewHolder.profilePicture);

            itemViewHolder.profileName.setText(item.owner.display_name);
        } else {
            Toast.makeText(mContext, "No data", Toast.LENGTH_SHORT).show();
        }



    }

    private static DiffUtil.ItemCallback<Item> DIFF_CALLBACK = new DiffUtil.ItemCallback<Item>() {
        @Override
        public boolean areItemsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem.answer_id == newItem.answer_id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem.equals(newItem);
        }
    };

    class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView profilePicture;
        TextView profileName;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            profilePicture = itemView.findViewById(R.id.image_view);
            profileName = itemView.findViewById(R.id.name_text_view);
        }
    }
}
