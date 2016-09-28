package jarmandocordova.restdemo.demo.main.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import jarmandocordova.restdemo.R;
import jarmandocordova.restdemo.demo.global.gateway.itunes.ITunesTrack;


/**
 * Created by jarma on 7/18/2016.
 */
public class TracksRVAdapter extends RecyclerView.Adapter<TracksRVAdapter.MyViewHolder> {
    private ITunesTrack[] array;
    private View.OnClickListener onClickListener;

    public TracksRVAdapter(ITunesTrack[] array, View.OnClickListener onClickListener) {
        this.array = array;
        this.onClickListener = onClickListener;
    }

    @Override
    public int getItemCount() {
        return array.length;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //view item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvitem_track, parent, false);
        //holder fill
        MyViewHolder holder = new MyViewHolder(view, onClickListener);
        //return holder
        return holder;
    }

    @Override //aqui se setea
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ITunesTrack track = array[position];
        holder.teTrackName.setText(track.getTrackName());
        holder.teArtistName.setText(track.getArtistName());
        holder. previewUrl = track.getPreviewUrl();
        holder.itemView.setTag(holder);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView teTrackName;
        TextView teArtistName;
        ImageView ivIcon;//TODO change icon to show pause action, etc.
        String previewUrl;
        View itemView;

        public String getPreviewUrl() {
            return previewUrl;
        }

        public ImageView getIvIcon() {
            return ivIcon;
        }

        public MyViewHolder(View itemView, View.OnClickListener onClickListener) {
            super(itemView);
            teTrackName = (TextView) itemView.findViewById(R.id.tename);
            teArtistName = (TextView) itemView.findViewById(R.id.teArtistName);
            ivIcon =  (ImageView) itemView.findViewById(R.id.ivIcon);
            this.itemView = itemView;
            itemView.setOnClickListener(onClickListener);
        }
    }

    public ITunesTrack[] getArray() {
        return array;
    }

    public void setArray(ITunesTrack[] array) {
        this.array = array;
    }
}