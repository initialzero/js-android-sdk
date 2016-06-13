package com.jaspersoft.android.sdk.sample;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jaspersoft.android.sdk.adHoc.Legend;

import java.util.List;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
public class LegendBottomDialogFragment extends BottomSheetDialogFragment implements AdapterView.OnItemClickListener {

    private List<Legend> legends;
    private LegendAdapter adapter;
    private LegendUpdateListener legendUpdateListener;

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    public void setLegends(List<Legend> legends) {
        this.legends = legends;
    }

    public void setLegendUpdateListener(LegendUpdateListener legendUpdateListener) {
        this.legendUpdateListener = legendUpdateListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        legendUpdateListener.onLegendsChanged(legends);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.fragment_legends, null);
        ListView legendsList = (ListView) contentView.findViewById(R.id.listView);
        adapter = new LegendAdapter(legends);
        legendsList.setAdapter(adapter);
        legendsList.setOnItemClickListener(this);
        dialog.setContentView(contentView);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        legends.get(position).setEnabled(!legends.get(position).isEnabled());
        adapter.notifyDataSetChanged();
    }

    private class LegendAdapter extends BaseAdapter {

        private List<Legend> legends;
        private LayoutInflater lInflater;

        public LegendAdapter(List<Legend> legends) {
            this.legends = legends;
            lInflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return legends.size();
        }

        @Override
        public Legend getItem(int position) {
            return legends.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = lInflater.inflate(R.layout.item_legend, parent, false);
            }

            Legend legend = getItem(position);

            view.setAlpha(legend.isEnabled() ? 1f : 0.5f);
            view.findViewById(R.id.legendColor).setBackgroundColor(legend.getColor());
            ((TextView) view.findViewById(R.id.legendTitle)).setText(legend.getLabel());

            return view;
        }
    }

    public interface LegendUpdateListener {
        void onLegendsChanged(List<Legend> legends);
    }
}
