package ca.joel.crud8215;

import android.content.Intent;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static ca.joel.crud8215.DBHandler.DB_FILE;

public class AddActivity extends AppCompatActivity {

    TextView txvId;
    EditText edtFirstName;
    EditText edtLastName;
    EditText edtMark;
    Button btnCancel;
    Button btnAdd;
    Button btnSave;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        db = new DBHandler(this, DB_FILE, null, 1);

        setupComponents();
        setupButtons();

        getIncomingData();
    }

    private void setupComponents() {
        txvId = (TextView) findViewById(R.id.txvId);
        edtFirstName = (EditText) findViewById(R.id.edtFirstName);
        edtLastName = (EditText) findViewById(R.id.edtLastName);
        edtMark = (EditText) findViewById(R.id.edtMarks);
    }

    private void setupButtons() {
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goBackToMain();
                    }
                }
        );

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveStudent();
                        showToast();
                        goBackToMain();
                    }
                }
        );

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateStudent();
                        showToast();
                        goBackToMain();
                    }
                }
        );
    }

    private void getIncomingData() {
        Student student = (Student) getIntent().getSerializableExtra("student");

        if (student == null)
            setupAddingStudent();
        else
            setupEditing(student);
    }

    private void setupAddingStudent() {
        //btnAdd.setVisibility(View.VISIBLE);
        btnSave.setVisibility(View.GONE);
    }

    private void setupEditing(Student student) {
        //btnAdd.setVisibility(View.GONE);
        btnSave.setVisibility(View.VISIBLE);

        txvId.setText(String.valueOf(student.getId()));
        edtFirstName.setText(student.getFirstName());
        edtLastName.setText(student.getLastName());
        edtMark.setText(String.valueOf(student.getMark()));
    }

    private void saveStudent() {
        String strMark = edtMark.getText().toString();
        Integer mark = "".equals(strMark) ? 0 : Integer.parseInt(strMark);
        int id = db.insert(new Student(
                0,
                edtFirstName.getText().toString(),
                edtLastName.getText().toString(),
                mark));
        txvId.setText(String.valueOf(id));
    }

    private void updateStudent() {
        String strMark = edtMark.getText().toString();
        Integer mark = "".equals(strMark) ? 0 : Integer.parseInt(strMark);
        db.update(new Student(
                Integer.parseInt(txvId.getText().toString()),
                edtFirstName.getText().toString(),
                edtLastName.getText().toString(),
                mark));
    }

    private void showToast() {
        MyToast.toast(this,
                "Student " + txvId.getText() + " - " +
                edtFirstName.getText() + " " +
                edtLastName.getText() +
                " saved.");
    }

    private void goBackToMain() {
        Intent intent = new Intent(AddActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
