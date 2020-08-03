package net.airstrafe.phatcat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class NameSetupActivity extends AppCompatActivity implements View.OnClickListener {
    private static String TAG = "phatcat setup";
    EditText displayNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_setup);
        findViewById(R.id.name_ok_button).setOnClickListener(this);
        displayNameEditText = findViewById(R.id.edit_display_name);
        displayNameEditText.setText(Api.user.name);
        // Move cursor to end of text
        displayNameEditText.setSelection(displayNameEditText.getText().length());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() != R.id.name_ok_button) {
            Log.w(TAG, "Onclick got invalid view " + view.getId());
            return;
        }
        String error = nameTextError();
        if (error == null) {
            startActivity(new Intent(getApplicationContext(), InitialHomeSetupActivity.class));
            return;
        }

    }

    private String nameTextError() {
        String text = displayNameEditText.getText().toString();
        if (text.length() < 3) {
            return getString(R.string.name_too_short);
        }
        if (text.length() > 24) {
            return getString(R.string.name_too_long);
        }
        return null;
    }
}