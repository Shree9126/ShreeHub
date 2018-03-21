package com.mindnotix.youthhub.ExploreFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codewaves.youtubethumbnailview.ImageLoader;
import com.codewaves.youtubethumbnailview.ThumbnailView;
import com.mindnotix.model.explore.explorerView.Content_list;
import com.mindnotix.utils.DownloadDocTask;
import com.mindnotix.youthhub.R;
import com.mindnotix.youthhub.YoutubeVideoActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Admin on 22-02-2018.
 */

public class TopicViewAdapter extends RecyclerView.Adapter<TopicViewAdapter.MyViewHolder> {

    private static final String TAG = TopicViewAdapter.class.getSimpleName();
    final Set<Target> protectedFromGarbageCollectorTargets = new HashSet<>();
    ArrayList<Content_list> topic_lists;
    Context context;
    String path_image;
    String path_pdf;
    private ProgressDialog mProgressDialog;


    public TopicViewAdapter(Context context, ArrayList<Content_list> topic_lists,
                            String path_image,
                            String path_pdf) {
        this.topic_lists = topic_lists;
        this.context = context;
        this.path_image = path_image;
        this.path_pdf = path_pdf;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_explorer_load_images, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.setIsRecyclable(false);

        Content_list content_list = topic_lists.get(position);

        if (content_list.getContent_type().equals("1")) {


            holder.pdfLayout.setVisibility(View.GONE);
            holder.loadContentLayout.setVisibility(View.VISIBLE);
            holder.ImageLayout.setVisibility(View.GONE);
            holder.PicassoImage.setVisibility(View.GONE);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.TextViewloadContentLayout.setText(Html.fromHtml(content_list.getContent().trim(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                holder.TextViewloadContentLayout.setText(Html.fromHtml(content_list.getContent().trim()));
            }


        } else if (content_list.getContent_type().equals("2")) {

            holder.pdfLayout.setVisibility(View.GONE);
            holder.loadContentLayout.setVisibility(View.GONE);
            holder.ImageLayout.setVisibility(View.GONE);
            holder.PicassoImage.setVisibility(View.VISIBLE);


            Picasso.with(context).
                    load(path_image + content_list.getContent())
                    .into(holder.imageViews, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.image4_progress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            holder.image4_progress.setVisibility(View.GONE);
                        }
                    });


        } else if (content_list.getContent_type().equals("3")) {

            holder.pdfLayout.setVisibility(View.GONE);
            holder.loadContentLayout.setVisibility(View.GONE);
            holder.ImageLayout.setVisibility(View.VISIBLE);
            holder.PicassoImage.setVisibility(View.GONE);

            holder.ImageViewid.loadThumbnail(content_list.getContent(), new ImageLoader() {
                @Override
                public Bitmap load(String url) throws IOException {
                    return Picasso.with(context).load(url).get();
                }
            });


        } else {

            holder.pdfLayout.setVisibility(View.VISIBLE);
            holder.loadContentLayout.setVisibility(View.GONE);
            holder.ImageLayout.setVisibility(View.GONE);
            holder.PicassoImage.setVisibility(View.GONE);

            holder.pdfText.setText(content_list.getContent());


        }


    }

    @Override
    public int getItemCount() {
        return topic_lists.size();
    }

    public void downloadFile(final String path, Context context) {
        try {
            URL url = new URL(path);

            URLConnection ucon = url.openConnection();
            ucon.setReadTimeout(5000);
            ucon.setConnectTimeout(10000);

            InputStream is = ucon.getInputStream();
            BufferedInputStream inStream = new BufferedInputStream(is, 1024 * 5);

            File file = new File(context.getDir("filesdir", Context.MODE_PRIVATE) + "/topic.pdf");

            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();

            FileOutputStream outStream = new FileOutputStream(file);
            byte[] buff = new byte[5 * 1024];

            int len;
            while ((len = inStream.read(buff)) != -1) {
                outStream.write(buff, 0, len);
            }

            outStream.flush();
            outStream.close();
            inStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView TextViewloadContentLayout;
        View loadContentLayout;
        TextView pdfText;
        View pdfLayout;
        View linearDownloadImage;
        View ImageLayout;
        ThumbnailView ImageViewid;
        View PicassoImage;
        ImageView imageViews;
        ProgressBar image4_progress;


        public MyViewHolder(View view) {
            super(view);

            TextViewloadContentLayout = view.findViewById(R.id.TextViewloadContentLayout);
            loadContentLayout = view.findViewById(R.id.loadContentLayout);
            pdfText = view.findViewById(R.id.pdfText);
            pdfLayout = view.findViewById(R.id.pdfLayout);
            ImageLayout = view.findViewById(R.id.ImageLayout);
            ImageViewid = view.findViewById(R.id.thumbnail);
            PicassoImage = view.findViewById(R.id.PicassoImage);
            imageViews = view.findViewById(R.id.imageViews);
            image4_progress = view.findViewById(R.id.image4_progress);
            linearDownloadImage = view.findViewById(R.id.linearDownloadImage);


            // Initialize the progress dialog
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setIndeterminate(true);
            // Progress dialog horizontal style
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            // Progress dialog title
            mProgressDialog.setTitle("Download");
            // Progress dialog message
            mProgressDialog.setMessage("Please wait, we are downloading your image file...");


            ImageViewid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, YoutubeVideoActivity.class);
                    intent.putExtra("youtube_url", topic_lists.get(getAdapterPosition()).getContent());
                    context.startActivity(intent);
                }
            });

            pdfLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(context, WebPdfActivity.class);
                    intent.putExtra("pdfurl", topic_lists.get(getAdapterPosition()).getContent());
                    intent.putExtra("path_pdf", path_pdf);
                    context.startActivity(intent);
                }
            });


            linearDownloadImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new DownloadDocTask(context, path_pdf.concat(topic_lists.get(getAdapterPosition()).getContent()));
                    //   downloadFile(path_pdf.concat(topic_lists.get(getAdapterPosition()).getContent()),context);
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }

}


