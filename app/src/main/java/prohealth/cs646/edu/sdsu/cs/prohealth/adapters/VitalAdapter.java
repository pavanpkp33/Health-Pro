package prohealth.cs646.edu.sdsu.cs.prohealth.adapters;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import prohealth.cs646.edu.sdsu.cs.prohealth.R;
import prohealth.cs646.edu.sdsu.cs.prohealth.model.VitalInformation;



public class VitalAdapter extends BaseAdapter {
    String vitalType;
    Context c;
    ArrayList<VitalInformation> vitalList;

    public VitalAdapter(Context context, ArrayList<VitalInformation> list, String type){
        c = context;
        vitalList = list;
        vitalType = type;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return vitalList.size();
    }

    @Override
    public Object getItem(int position) {
        return vitalList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return vitalList.get(position).getId();
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
        VitalInformation vInfo = vitalList.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) c
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.vital_row, parent, false);

        }

        TextView tvVitalHeader = (TextView) convertView.findViewById(R.id.tvVitalHeader);
        TextView tvVitalIcon = (TextView) convertView.findViewById(R.id.tvVitalIcon);
        TextView tvVitalLastUpdated = (TextView) convertView.findViewById(R.id.tvVitalLastUpdated);
        TextView tvVitalValue = (TextView) convertView.findViewById(R.id.tvVitalValue);
        TextView tvVitalOpt = (TextView) convertView.findViewById(R.id.tvVitalOpt);
        LinearLayout vitalColor = (LinearLayout) convertView.findViewById(R.id.vitalColor);

        try{
            tvVitalHeader.setText(vInfo.getVitalHeader());
            tvVitalLastUpdated.setText(vInfo.getVitalLastUpdated());
            tvVitalValue.setText(vInfo.getVitalValue());

            if(vitalType.equalsIgnoreCase("BMI")){
                tvVitalOpt.setVisibility(convertView.GONE);
                tvVitalIcon.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_bmi,0, 0, 0);
                int color = ContextCompat.getColor(c,R.color.colorAccent);
                vitalColor.setBackgroundColor(color);
            }else if(vitalType.equalsIgnoreCase("BGC")){
                tvVitalOpt.setVisibility(convertView.VISIBLE);
                tvVitalOpt.setText("mmol/L");
                tvVitalIcon.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_blood_glucose,0, 0, 0);
                int color = ContextCompat.getColor(c,R.color.colorSecAccent);
                vitalColor.setBackgroundColor(color);
            }else{
                tvVitalOpt.setVisibility(convertView.VISIBLE);
                tvVitalOpt.setText("bpm");
                tvVitalIcon.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_heart_pulse,0, 0, 0);
                int color = ContextCompat.getColor(c,R.color.colorPrimaryDark);
                vitalColor.setBackgroundColor(color);
            }
            notifyDataSetChanged();
        }catch(Exception e){
            e.printStackTrace();
        }


        return convertView;
    }
}
