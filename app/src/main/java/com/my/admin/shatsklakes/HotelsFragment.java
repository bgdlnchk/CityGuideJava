package com.my.admin.shatsklakes;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import jp.wasabeef.recyclerview.animators.ScaleInAnimator;


/**
 * A simple {@link Fragment} subclass.
 */
public class HotelsFragment extends Fragment {

    private ProgressBar mProgressBar;
    private FirebaseRecyclerAdapter<Model, HotelsFragment.HotelsViewHolder> mPeopleRVAdapter;
    private Button mCallButton;
    public String mPhoneNumber;

    public HotelsFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View hotels_view = inflater.inflate(R.layout.fragment_hotels, container, false);

        //Check Internet connection
        if (!isConnected(getActivity())){
            mBuiltDialog(getContext()).show();
        }

        //Load ProgressBar
        mProgressBar = hotels_view.findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);

        //Initialize Database
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Hotels");
        mDatabase.keepSynced(true);

        RecyclerView mPeopleRV = hotels_view.findViewById(R.id.myRecycleView);
        mPeopleRV.setItemAnimator(new ScaleInAnimator());

        DatabaseReference personsRef = FirebaseDatabase.getInstance().getReference().child("Hotels");
        Query personsQuery = personsRef.orderByKey();

        mPeopleRV.hasFixedSize();
        mPeopleRV.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions personsOptions = new FirebaseRecyclerOptions.Builder<Model>().setQuery(personsQuery, Model.class).build();

        mPeopleRVAdapter = new FirebaseRecyclerAdapter<Model, HotelsFragment.HotelsViewHolder>(personsOptions) {
            @Override
            protected void onBindViewHolder(@NonNull HotelsFragment.HotelsViewHolder holder, int position, @NonNull Model model) {
                holder.setTitle(model.getTitle());
                holder.setStreet(model.getStreet());
                holder.setPlace(model.getPlace());
                holder.setImage(getContext(), model.getImage());
                mPhoneNumber = model.getPhoneNumber();
            }

            //Set onCallButton ClickListener
            @Override
            public HotelsFragment.HotelsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_with_number, parent, false);
                mCallButton = view.findViewById(R.id.call_button);
                mCallButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent call_intent = new Intent(Intent.ACTION_DIAL);
                        call_intent.setData(Uri.parse("tel:"+ mPhoneNumber));
                        startActivity(call_intent);
                    }
                });
                mProgressBar.setVisibility(View.GONE);
                return new HotelsFragment.HotelsViewHolder(view);
            }
        };

        mPeopleRV.setAdapter(mPeopleRVAdapter);
        return hotels_view;
    }

    public boolean isConnected(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert mConnectivityManager != null;
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();

        if (mNetworkInfo != null && mNetworkInfo.isConnectedOrConnecting()){
            android.net.NetworkInfo mWifi = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mMobile = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            return (mMobile != null && mMobile.isConnectedOrConnecting()) || (mWifi != null && mWifi.isConnectedOrConnecting());

        }else {
            return false;
        }
    }

    public AlertDialog.Builder mBuiltDialog(Context c){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(c);
        mBuilder.setTitle("No Internet connection");
        mBuilder.setMessage("Please, check your Internet connection");

        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return mBuilder;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPeopleRVAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPeopleRVAdapter.stopListening();
    }

    //Add information from Database to RecycleView
    public static class HotelsViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public HotelsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitle(String title){
            TextView post_title = mView.findViewById(R.id.post_title);
            post_title.setText(title);
        }

        public void setStreet(String street){
            TextView post_street = mView.findViewById(R.id.post_street);
            post_street.setText(street);
        }

        public void setPlace(String place){
            TextView post_place = mView.findViewById(R.id.post_place);
            post_place.setText(place);
        }

        public void setImage(Context ctx, String image){
            ImageView post_image = mView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(post_image);
        }

    }

}
