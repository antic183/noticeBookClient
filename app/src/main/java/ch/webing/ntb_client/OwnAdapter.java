package ch.webing.ntb_client;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ch.webing.ntb_client.model.Notice;

/**
 * Created by antic-software-ing on 20.09.2017.
 */

public class OwnAdapter extends BaseAdapter {

    private List<Notice> noticeList;
    private NoticeOverviewActivity activity;

    public OwnAdapter(NoticeOverviewActivity activity, List<Notice> notices) {
        this.activity = activity;
        this.noticeList = notices;
    }

    @Override
    public int getCount() {
        return noticeList.size();
    }

    @Override
    public Object getItem(int position) {
        return noticeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return noticeList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View myRow = convertView == null ?
                layoutInflater.inflate(R.layout.own_notice_item, parent, false)
                : convertView;

        Notice currentNotice = noticeList.get(position);

        TextView textView = (TextView) myRow.findViewById(R.id.ownNoticeItem_title);
        textView.setText(currentNotice.getTitle());

        TextView textView2 = (TextView) myRow.findViewById(R.id.ownNoticeItem_text);
        textView2.setText(currentNotice.getText());

        return myRow;
    }
}
