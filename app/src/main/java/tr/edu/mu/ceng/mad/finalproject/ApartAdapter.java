package tr.edu.mu.ceng.mad.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ApartAdapter extends BaseAdapter {

    private List<Aparts> aparts;
    private LayoutInflater inflater;

    public ApartAdapter(List<Aparts> aparts, Activity activity) {
        this.aparts = aparts;
        inflater=activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return aparts.size();
    }

    @Override
    public Object getItem(int i) {
        return aparts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View rowView;
        rowView=inflater.inflate(R.layout.listview_row,null);

        TextView apartName = rowView.findViewById(R.id.apartName);

        apartName.setText(aparts.get(i).getType1());

        TextView apartLocation = rowView.findViewById(R.id.apartLocation);

        apartLocation.setText(aparts.get(i).getType2());

        TextView apartPhoneNumber = rowView.findViewById(R.id.apartPhoneNumber);

        apartPhoneNumber.setText(aparts.get(i).getType3());

        ImageView apartImage = rowView.findViewById(R.id.apartImage);

        apartImage.setImageResource(aparts.get(i).getPicId());

        return rowView;
    }
}
