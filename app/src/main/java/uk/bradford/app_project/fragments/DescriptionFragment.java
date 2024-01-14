package uk.bradford.app_project.fragments;


import static android.view.View.GONE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import uk.bradford.app_project.Cipher;
import uk.bradford.app_project.R;

public class DescriptionFragment extends Fragment {

    private final Cipher cipher;

    private TextView textView;
    private Button usageButton;
    private Button descriptionButton;

    private LinearLayout parentView;

    public DescriptionFragment() {
        cipher = null;
    }

    public DescriptionFragment(Cipher cipher) {
        this.cipher = cipher;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.description, container, false);

        textView = rootView.findViewById(R.id.descriptionTextView);
        usageButton = rootView.findViewById(R.id.descriptionUsageButton);
        descriptionButton = rootView.findViewById(R.id.descriptionDescriptionButton);

        parentView = rootView.findViewById(R.id.description_parent);

        usageButton.setOnClickListener(this::onUsageButtonClick);
        descriptionButton.setOnClickListener(this::onDescriptionButtonClick);

        if (cipher == null) {
            textView.setText("");
            parentView.setVisibility(GONE);
            return rootView;
        }

        textView.setText(cipher.getDescription());

        return rootView;
    }

    public void onUsageButtonClick(View v) {

        textView.setText(cipher.getUsage());


    }

    public void onDescriptionButtonClick(View v) {
        textView.setText(cipher.getDescription());


    }

}
