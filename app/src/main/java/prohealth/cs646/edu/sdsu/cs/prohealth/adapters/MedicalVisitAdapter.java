package prohealth.cs646.edu.sdsu.cs.prohealth.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import prohealth.cs646.edu.sdsu.cs.prohealth.R;
import prohealth.cs646.edu.sdsu.cs.prohealth.model.MedicalVisit;


public class MedicalVisitAdapter extends BaseAdapter {
    Context c;
    ArrayList<MedicalVisit> medVisitList;

    public MedicalVisitAdapter(Context context, ArrayList<MedicalVisit> list){
        c = context;
        medVisitList = list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return medVisitList.size();
    }

    @Override
    public Object getItem(int position) {
        return medVisitList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return medVisitList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MedicalVisit medObj = medVisitList.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) c
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.medical_row, parent, false);

        }

        TextView tvDrFirstName = (TextView) convertView.findViewById(R.id.tvMedicalFirstName);
        TextView tvDrLastName = (TextView) convertView.findViewById(R.id.tvMedicalDoctorName);
        TextView tvDrDepartment = (TextView) convertView.findViewById(R.id.tvMedicalSpecialization);
        TextView tvDrLastUpdated = (TextView) convertView.findViewById(R.id.tvMedicalLastUpdated);

        try{
            tvDrFirstName.setText(medObj.getDrFirstName());
            tvDrLastName.setText(medObj.getDrLastName());
            tvDrLastUpdated.setText(medObj.getDateOfVisit());
            tvDrDepartment.setText(medObj.getDepartment());
            notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }

        return convertView;
    }
}
