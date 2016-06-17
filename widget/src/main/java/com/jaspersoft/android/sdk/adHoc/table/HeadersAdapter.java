package com.jaspersoft.android.sdk.adHoc.table;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
public class HeadersAdapter extends RecyclerView.Adapter<HeadersAdapter.HeadersViewHolder> {

    private List<String> headers;

    public HeadersAdapter(List<String> headers) {
        this.headers = headers;
    }

    @Override
    public HeadersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView headerView = new TextView(parent.getContext());
        return new HeadersViewHolder(headerView);
    }

    @Override
    public void onBindViewHolder(HeadersViewHolder holder, int position) {
        String header = headers.get(position);
        holder.setText(header);
    }

    @Override
    public int getItemCount() {
        return headers.size();
    }

    public static class HeadersViewHolder extends RecyclerView.ViewHolder {
        private TextView header;

        public HeadersViewHolder(View itemView) {
            super(itemView);
            header = (TextView) itemView;
        }

        public void setText(String text) {
            header.setText(text);
        }
    }
}
