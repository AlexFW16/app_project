package uk.bradford.app_project.fragments;


import static android.view.View.GONE;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import uk.bradford.app_project.Cipher;
import uk.bradford.app_project.R;

public class DescriptionFragment extends Fragment {

    private final Cipher cipher;

    private TextView textView;

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
        Button usageButton = rootView.findViewById(R.id.descriptionUsageButton);
        Button descriptionButton = rootView.findViewById(R.id.descriptionDescriptionButton);

        LinearLayout parentView = rootView.findViewById(R.id.description_parent);

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
        if (cipher == null) {
            Toast.makeText(requireActivity(), R.string.cipher_is_null_error, Toast.LENGTH_SHORT).show();
            Log.e("DescriptionFragment", "cipher field of DescriptionFragment is null");
        } else {
            textView.setText(cipher.getUsage());
        }
    }

    public void onDescriptionButtonClick(View v) {
        if (cipher == null) {
            Toast.makeText(requireActivity(), R.string.cipher_is_null_error, Toast.LENGTH_SHORT).show();
            Log.e("DescriptionFragment", "cipher field of DescriptionFragment is null");
        } else {
            textView.setText(cipher.getDescription());
        }

    }

}
