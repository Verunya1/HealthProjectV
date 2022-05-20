package com.example.healthprojectv.bottomSheetFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.healthprojectv.R;

/**
 * A fragment representing a list of Items.
 */
public class RecordChooseBottom extends Fragment implements View.OnClickListener {
    private Button analyzes, doctor, medicines;
    View rootView;

    public RecordChooseBottom() {
        // require a empty public constructor
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.choose_record, container, false);

        analyzes = (Button) rootView.findViewById(R.id.analyzes);
        analyzes.setOnClickListener(this);

        doctor = (Button) rootView.findViewById(R.id.doctor);
        doctor.setOnClickListener(this);

        medicines = (Button) rootView.findViewById(R.id.medicines);
        medicines.setOnClickListener(this);

        return rootView;

    }
public interface OnSelectedButtonListener{
        void OnButtonSelected();
}
    @Override
    public void onClick(View v) {
        /*switch (v.getId()) {
            case R.id.analyzes:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new RecordAddAnalyzes()).commit();
                break;
            case R.id.doctor:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new RecordAddDoctor()).commit();
                break;
            case R.id.medicines:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new RecordAddMedicines()).commit();
                break;
        }*/
    }
}