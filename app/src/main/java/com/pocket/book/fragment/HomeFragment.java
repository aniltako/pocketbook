package com.pocket.book.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pocket.book.MyApplication;
import com.pocket.book.R;
import com.pocket.book.adapter.RecordAdapter;
import com.pocket.book.database.DBRecord;
import com.pocket.book.database.RecordsDataSource;
import com.pocket.book.model.Record;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";


    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private RecordAdapter adapter;

    private RecordsDataSource dataSource;
    private ArrayList<Record> arrayList;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataSource = new RecordsDataSource(getActivity());
        dataSource.open();

        arrayList = dataSource.getAllRecords();
        if (arrayList != null){

            Log.e(TAG, "Total size " + arrayList.size());
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setImageDrawable(ContextCompat.getDrawable(getContext(), android.R.drawable.ic_input_add));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onFragmentInteraction();

            }
        });


        if(arrayList != null && arrayList.size() > 0){

            recyclerView = (RecyclerView) rootView.findViewById(R.id.recordList);

            adapter = new RecordAdapter(getActivity(), arrayList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
            adapter.setClickListener(new RecordAdapter.ItemClickListener() {
                @Override
                public void onClick(View view, int position) {

                   Log.e("Position", ""+position);

                }
            });



        }




        return rootView;
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }





}
