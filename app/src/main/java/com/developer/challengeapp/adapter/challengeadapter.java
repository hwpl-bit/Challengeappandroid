package com.developer.challengeapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.challengeapp.R;
import com.developer.challengeapp.model.ChallengeModel;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class challengeadapter extends RecyclerView.Adapter<challengeadapter.viewholder> {
    private ArrayList<ChallengeModel> challenglist;
    private Context mcontext;


    public challengeadapter(ArrayList<ChallengeModel> challenglist, Context mcontext) {
        this.challenglist = challenglist;
        this.mcontext = mcontext;
    }

    // setclick listner
    private OnChallengitemClickListener onChallengitemClickListener;
    @NonNull
    @Override
    public challengeadapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.challenge_item,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull challengeadapter.viewholder holder, int position) {
        ChallengeModel data=challenglist.get(position);
        holder.txtv_challenge_item_name.setText(data.getName());
//        holder.avtarview.setImageURI(Uri.parse(data.getAvatar()));
        Picasso.get().load(data.getAvatar()).noFade().fit().into(holder.avtarview);
    }

    @Override
    public int getItemCount() {
        return challenglist.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        TextView txtv_challenge_item_name;
        CircleImageView avtarview;
        public viewholder(@NonNull View itemView) {
            super(itemView);
        txtv_challenge_item_name=itemView.findViewById(R.id.txtv_name);
        avtarview=itemView.findViewById(R.id.img_avtar);
        }
    }

    // update fields

    public void setOnChallengitemClickListener(OnChallengitemClickListener onChallengitemClickListener) {
        this.onChallengitemClickListener = onChallengitemClickListener;
    }

    public void updateData(ArrayList<ChallengeModel> viewModels) {
        challenglist.clear();
        challenglist.addAll(viewModels);
        notifyDataSetChanged();
    }

    public interface OnChallengitemClickListener {
        void onItemClick(View view, ChallengeModel viewModel);

    }
}
