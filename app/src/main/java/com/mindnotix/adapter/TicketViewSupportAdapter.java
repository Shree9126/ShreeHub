package com.mindnotix.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindnotix.listener.RecyclerViewClickListener;
import com.mindnotix.model.contactsupport.view.Supportdetailview;
import com.mindnotix.utils.DownloadDocTask;
import com.mindnotix.youthhub.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Sridharan on 2/20/2018.
 */

public class TicketViewSupportAdapter extends RecyclerView.Adapter<TicketViewSupportAdapter.MyViewHolder> {

    private static final String TAG = TicketViewSupportAdapter.class.getSimpleName();
    static Activity activity;
    final Set<Target> protectedFromGarbageCollectorTargets = new HashSet<>();
    ArrayList<Supportdetailview> supportdetailviewArrayList;
    AsyncTask<URL, Void, Bitmap> downloadTask;
    private RecyclerViewClickListener mListener;
    private ProgressDialog mProgressDialog;


    public TicketViewSupportAdapter(Activity activity, ArrayList<Supportdetailview> supportdetailviewArrayList, RecyclerViewClickListener mListener) {
        this.activity = activity;
        this.supportdetailviewArrayList = supportdetailviewArrayList;
        this.mListener = mListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_ticket_list, parent, false);


        return new MyViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.txtSubject.setText(supportdetailviewArrayList.get(position).getMessage());
        holder.txtUserName.setText(supportdetailviewArrayList.get(position).getFirst_name()
                .concat(" " + supportdetailviewArrayList.get(position).getLast_name()));


        String image_path = supportdetailviewArrayList.get(position).getImage_path()
                .concat(supportdetailviewArrayList.get(position).getProfile_image());


        if (supportdetailviewArrayList.get(position).getDownload_file().equals(""))
            holder.linearDownloadImage.setVisibility(View.GONE);
        else
            holder.linearDownloadImage.setVisibility(View.VISIBLE);


        Log.d(TAG, "onBindViewHolder: image_path " + image_path);
        Picasso.with(activity)
                .load(image_path)
                .placeholder(R.drawable.profile_pic) //this is optional the image to display while the url image is downloading
                .error(R.drawable.profile_pic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                .into(holder.ivProfile);

    }

    @Override
    public int getItemCount() {
        return supportdetailviewArrayList.size();
    }

    // Custom method to convert string to url
    protected URL stringToURL(String urlString) {
        try {
            URL url = new URL(urlString);
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Uri saveImageToInternalStorage(Bitmap bitmap, String download_file) {
        // Initialize ContextWrapper
        ContextWrapper wrapper = new ContextWrapper(activity);

        final String appDirectoryName = "YouthHub/Ticket";
        final File imageRoot = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), appDirectoryName);

        imageRoot.mkdirs();
        final File file = new File(imageRoot, download_file);
        // Initializing a new file
        // The bellow line return a directory in internal storage

        try {
            // Initialize a new OutputStream
            OutputStream stream = null;

            // If the output file exists, it can be replaced or appended to it
            stream = new FileOutputStream(file);

            // Compress the bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            // Flushes the stream
            stream.flush();

            // Closes the stream
            stream.close();

        } catch (IOException e) // Catch the exception
        {
            e.printStackTrace();
        }

        // Parse the gallery image url to uri
        Uri savedImageURI = Uri.parse(file.getAbsolutePath());

        // Return the saved image Uri
        return savedImageURI;
    }

    private void loadBitmap(String url, String download_file) {
        mProgressDialog.show();
        Target bitmapTarget = new BitmapTarget(download_file);
        protectedFromGarbageCollectorTargets.add(bitmapTarget);
        Picasso.with(activity).load(url).into(bitmapTarget);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecyclerViewClickListener mListener;
        private CircleImageView ivProfile;
        private TextView txtUserName;
        private TextView txtSubject;
        private LinearLayout linearDownloadImage;


        public MyViewHolder(View itemView, RecyclerViewClickListener mListener) {

            super(itemView);

            this.mListener = mListener;
            this.linearDownloadImage = (LinearLayout) itemView.findViewById(R.id.linearDownloadImage);
            this.linearDownloadImage.setOnClickListener(this);
            this.txtSubject = (TextView) itemView.findViewById(R.id.txtSubject);
            this.txtUserName = (TextView) itemView.findViewById(R.id.txtUserName);
            this.ivProfile = (CircleImageView) itemView.findViewById(R.id.ivProfile);

            // Initialize the progress dialog
            mProgressDialog = new ProgressDialog(activity);
            mProgressDialog.setIndeterminate(true);
            // Progress dialog horizontal style
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            // Progress dialog title
            mProgressDialog.setTitle("Download");
            // Progress dialog message
            mProgressDialog.setMessage("Please wait, we are downloading your image file...");
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.linearDownloadImage:

                    Log.d(TAG, "onClick: file type " + supportdetailviewArrayList.get(getAdapterPosition()).getDownload_file());
                    if (supportdetailviewArrayList.get(getAdapterPosition()).getDownload_file().contains("pdf")) {
                        String image_path = supportdetailviewArrayList.get(getAdapterPosition()).getImage_download()
                                .concat(supportdetailviewArrayList.get(getAdapterPosition()).getDownload_file());

                        new DownloadDocTask(activity, image_path);
                    } else {
                        String image_path = supportdetailviewArrayList.get(getAdapterPosition()).getImage_download()
                                .concat(supportdetailviewArrayList.get(getAdapterPosition()).getDownload_file());


                        Log.d(TAG, "onClick: image path of download " + image_path);


                        loadBitmap(image_path, supportdetailviewArrayList.get(getAdapterPosition()).getDownload_file());

                    }


                    break;
            }

        }


    }

    class BitmapTarget implements Target {

        String download_file;

        public BitmapTarget(String download_file) {
            this.download_file = download_file;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {

            Log.d(TAG, "onBitmapLoaded: ");
            //handle bitmap
            protectedFromGarbageCollectorTargets.remove(this);


            Uri uri = saveImageToInternalStorage(bitmap, download_file);
            Log.d(TAG, "onBitmapLoaded: path of image = " + uri.getPath());
            mProgressDialog.dismiss();
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            protectedFromGarbageCollectorTargets.remove(this);
            Log.d(TAG, "onBitmapLoaded:onBitmapFailed ");
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            Log.d(TAG, "onBitmapLoaded: onPrepareLoad");

        }
    }
}






