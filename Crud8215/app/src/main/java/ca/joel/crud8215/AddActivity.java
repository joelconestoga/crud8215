package ca.joel.crud8215;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddActivity extends AppCompatActivity {

    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btnCancel = (Button) findViewById(R.id.btnCancel);
        setupButton();
    }

    private void setupButton() {
        btnCancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AddActivity.this, MainActivity.class);
                        //intent.putExtra("info", edtInfo.getText().toString());
                        startActivity(intent);
                    }
                }
        );
    }
}
