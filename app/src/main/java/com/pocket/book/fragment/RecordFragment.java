package com.pocket.book.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.pocket.book.MainActivity;
import com.pocket.book.MyApplication;
import com.pocket.book.R;
import com.pocket.book.database.DBRecord;
import com.pocket.book.database.RecordsDataSource;
import com.pocket.book.model.Record;
import com.pocket.book.utils.Constants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class RecordFragment extends Fragment {

    private static final String TAG = "RecordFragment";

    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 0;



    private OnFragmentInteractionListener mListener;

    private boolean isPhotoTaken = false;
    FloatingActionButton fabSave, fabCamera;
    private EditText etTitle, etDescription;
    private ImageView recordImage;

    private RecordsDataSource dataSource;

    public RecordFragment() {
        // Required empty public constructor
    }


    public static RecordFragment newInstance(String param1, String param2) {

        RecordFragment fragment = new RecordFragment();

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);


        dataSource = new RecordsDataSource(getActivity());
        dataSource.open();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View rootView = inflater.inflate(R.layout.fragment_record, container, false);

        etTitle = (EditText) rootView.findViewById(R.id.title);
        etDescription = (EditText) rootView.findViewById(R.id.description);
        recordImage = (ImageView)rootView.findViewById(R.id.record_image);


        fabSave = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fabSave.setImageDrawable(ContextCompat.getDrawable(getContext(), android.R.drawable.ic_menu_save));
        fabSave.setEnabled(false);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveRecord();

            }
        });


        fabCamera = (FloatingActionButton) rootView.findViewById(R.id.fab_top);
        fabCamera.setImageDrawable(ContextCompat.getDrawable(getContext(), android.R.drawable.ic_menu_camera));
        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    selectImage();

            }
        });



        return rootView;
    }



    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(false);
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


    private void cameraIntent(){

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 0);
    }


    private void selectImage() {

        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Photo")) {

                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {

                        galleryIntent();

                } else if (items[item].equals("Cancel")) {

                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }

    private void galleryIntent(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"),1);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){

            if( requestCode == SELECT_FILE){

                onSelectFromGalleryResult(data);

            } else if (requestCode == REQUEST_CAMERA) {

                onCaptureImageResult(data);
            }
        }


    }

    private void onSelectFromGalleryResult(Intent data){

        Bitmap bm = null;
        if (data != null) {

            if(bm != null) {
                bm.recycle();
                bm = null;
            }

            try {
                bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        recordImage.setImageBitmap(bm);

        setSaveButtonEnable();
    }

    private void onCaptureImageResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        recordImage.setImageBitmap(bm);

        setSaveButtonEnable();
    }

    private void setSaveButtonEnable() {

        fabSave.setEnabled(true);
    }

    private void saveRecord(){


        Record record = new Record();

        record.setTitle(etTitle.getText().toString());
        record.setDescription(etTitle.getText().toString());
        record.setCreatedDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        record.setImagePath(saveImageFile());


        if( dataSource.createRecord(record) != null){

            Record createdRecord = dataSource.createRecord(record);

            Log.e(TAG, "Title: "+ createdRecord.getTitle() + " Desc: "+ createdRecord.getDescription()+ " Date: " +
                    createdRecord.getCreatedDate() + " Image path: "+ createdRecord.getImagePath() );


        }


    }


    private String saveImageFile() {


        String filename = getFilename();

        Bitmap bitmap = ((BitmapDrawable)recordImage.getDrawable()).getBitmap();

        new SaveImage(filename, bitmap).execute("");

        return filename;
    }

    private String getFilename(){

        File file = new File(Environment.getExternalStorageDirectory().toString() + File.separator + Constants.PIC_FILE_DIR);

        if (!file.exists()) {
            file.mkdirs();
        }

        String uriSting = (file.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg");

        return uriSting;
    }

    private class SaveImage extends AsyncTask<String, Void, String> {

        protected ProgressDialog pDialog;
        String filename;
        Bitmap bitmap;

        public SaveImage(String filename, Bitmap bitmap){

            this.filename = filename;
            this.bitmap = bitmap;
        }

        @Override
        protected String doInBackground(String... params) {

            FileOutputStream out = null;
            try {
                out = new FileOutputStream(filename);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return "Executed";

        }

        @Override
        protected void onPostExecute(String result) {

            pDialog.dismiss();
            ((MainActivity)getActivity()).showHomePage();
        }

        @Override
        protected void onPreExecute() {

            pDialog = new ProgressDialog(getActivity());
            pDialog.setCancelable(false);
            pDialog.setMessage("Saving Image....");
            pDialog.show();

        }

    }




}
