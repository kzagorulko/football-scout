package ru.zagorulko.footballscout;

//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ImageViewHolder> {

    private int[] images;
    private String[] texts;
    private OnItemListener onItemListener;

    RecyclerAdapter(int[] images, String[] texts, OnItemListener onItemListener) {
        this.images = images;
        this.texts = texts;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_image_text, parent, false);

        return new ImageViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        int image_id = images[position];
        String text = texts[position];
        holder.picture.setImageResource(image_id);

        holder.text.setText(text);


    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView picture;
        TextView text;
        OnItemListener onItemListener;

        ImageViewHolder(View itemView, OnItemListener onItemListener) {
            super(itemView);
            picture = itemView.findViewById(R.id.layoutImage);
            text = itemView.findViewById(R.id.layoutText);
            this.onItemListener = onItemListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemListener {
        void onItemClick(int position);
    }
}
