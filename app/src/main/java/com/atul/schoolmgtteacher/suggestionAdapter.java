package com.atul.schoolmgtteacher;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class suggestionAdapter extends RecyclerView.Adapter<suggestionAdapter.suggestionHolder> {

    private Context mCtx;
    private List<model_suggestion> suggestionList;

    public suggestionAdapter(Context mCtx, List<model_suggestion> suggestionList) {
        this.mCtx = mCtx;
        this.suggestionList = suggestionList;
    }

    @Override
    public suggestionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lyf = LayoutInflater.from(mCtx);
        View view = lyf.inflate(R.layout.suggestionlayout, null);
        return new suggestionHolder(view);
    }

    @Override
    public void onBindViewHolder(suggestionHolder holder, int position) {
        model_suggestion quo = suggestionList.get(position);
        holder.textViewdname.setText(quo.getName());
        holder.textViewsugge.setText(quo.getSugge());

    }

    @Override
    public int getItemCount() {
        return suggestionList.size();
    }

    class suggestionHolder extends RecyclerView.ViewHolder {

        TextView textViewdname, textViewsugge;

        public suggestionHolder(View itemView) {
            super(itemView);
            textViewdname = itemView.findViewById(R.id.namee);
            textViewsugge = itemView.findViewById(R.id.suggest);

        }
    }
}



