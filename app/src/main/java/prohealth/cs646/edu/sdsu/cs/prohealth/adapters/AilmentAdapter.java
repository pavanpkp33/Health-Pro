package prohealth.cs646.edu.sdsu.cs.prohealth.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import prohealth.cs646.edu.sdsu.cs.prohealth.R;
import prohealth.cs646.edu.sdsu.cs.prohealth.model.Ailment;

public class AilmentAdapter extends BaseAdapter{

    Context c;
    ArrayList<Ailment> ailmentList;

    public AilmentAdapter(Context context, ArrayList<Ailment> list){
        c = context;
        ailmentList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return ailmentList.size();
    }

    @Override
    public Object getItem(int position) {
        return ailmentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ailmentList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Ailment ailment = ailmentList.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) c
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.ailment_row, parent, false);

        }

        TextView tvAilmentValue = (TextView) convertView.findViewById(R.id.tvAilmentValue);
        TextView tvAilmentMedication = (TextView) convertView.findViewById(R.id.tvAilmentMedication);
        TextView tvAilmentLastUpdated = (TextView) convertView.findViewById(R.id.tvAilmentLastUpdated);
        try{

            tvAilmentValue.setText(ailment.getAilment());
            tvAilmentMedication.setText(ailment.getMedication());
            tvAilmentLastUpdated.setText(ailment.getEncounteredDate());
            notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }

        return convertView;
    }
}
