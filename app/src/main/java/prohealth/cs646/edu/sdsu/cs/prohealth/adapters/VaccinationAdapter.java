package prohealth.cs646.edu.sdsu.cs.prohealth.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import prohealth.cs646.edu.sdsu.cs.prohealth.R;
import prohealth.cs646.edu.sdsu.cs.prohealth.model.Vaccination;


public class VaccinationAdapter extends BaseAdapter {
    Context c;
    ArrayList<Vaccination> vaccinationList;


    public VaccinationAdapter(Context context, ArrayList<Vaccination> list){
        c = context;
        vaccinationList = list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {

        return vaccinationList.size();
    }

    @Override
    public Object getItem(int position) {
        return vaccinationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return vaccinationList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Vaccination vacObj = vaccinationList.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) c
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.vaccination_row, parent, false);

        }

        TextView tvVacName = (TextView) convertView.findViewById(R.id.tvVaccinationValue);
        TextView tvLastAdministered = (TextView) convertView.findViewById(R.id.tvVaccinationLastUpdated);
        try {


            tvVacName.setText(vacObj.getVaccination());
            tvLastAdministered.setText(vacObj.getAdministeredDate());
            notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }

        return convertView;
    }
}
