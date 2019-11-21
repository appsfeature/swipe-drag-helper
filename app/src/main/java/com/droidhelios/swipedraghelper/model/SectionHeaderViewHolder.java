package com.droidhelios.swipedraghelper.model;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.droidhelios.swipedraghelper.R;


/**
 * @author Created by Abhijit Rao on 20-11-2019.
 */

public class SectionHeaderViewHolder extends RecyclerView.ViewHolder {

    public TextView sectionTitle;

    public SectionHeaderViewHolder(View itemView) {
        super(itemView);
        sectionTitle = itemView.findViewById(R.id.textview_section_header);
    }

}
