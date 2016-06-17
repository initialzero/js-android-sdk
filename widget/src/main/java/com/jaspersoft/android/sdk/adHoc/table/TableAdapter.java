package com.jaspersoft.android.sdk.adHoc.table;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder> {

    private List<List<String>> data;

    public TableAdapter(List<List<String>> data) {
        this.data = data;
    }

    @Override
    public TableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView dataView = new TextView(parent.getContext());
        return new TableViewHolder(dataView);
    }

    @Override
    public void onBindViewHolder(TableViewHolder holder, int position) {
        int rowIndex = position / data.get(0).size();
        int columnIndex = position % data.get(0).size();
        String header = data.get(rowIndex).get(columnIndex);
        holder.setText(header);
        holder.setColor(rowIndex % 2 == 0 ? Color.parseColor("#FFFFFF") : Color.parseColor("#F4F4F4"));
    }

    @Override
    public int getItemCount() {
        return data.size() * data.get(0).size();
    }

    public static class TableViewHolder extends RecyclerView.ViewHolder {
        private TextView dataView;

        public TableViewHolder(View itemView) {
            super(itemView);
            dataView = (TextView) itemView;
        }

        public void setText(String text) {
            dataView.setText(text);
        }

        public void setColor(int color) {
            dataView.setBackgroundColor(color);
        }
    }
}
