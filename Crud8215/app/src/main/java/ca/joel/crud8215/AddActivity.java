package ca.joel.crud8215;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static ca.joel.crud8215.DBHandler.DB_FILE;

//Java class for adding and editing a student
public class AddActivity extends AppCompatActivity {

    TextView txvId;
    EditText edtFirstName;
    EditText edtLastName;
    EditText edtMark;
    Button btnCancel;
    Button btnAdd;
    Button btnSave;
    DBHandler db;

    //App initialization, where execution starts
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Code auto-generated, just kept unchanged
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //Create the database handler
        db = new DBHandler(this, DB_FILE, null, 1);

        //Setup the screen components
        setupComponents();
        setupButtons();

        //Deals with incoming student for edition
        getIncomingData();
    }

    //Setup the references for the screen components
    private void setupComponents() {
        txvId = (TextView) findViewById(R.id.txvId);
        edtFirstName = (EditText) findViewById(R.id.edtFirstName);
        edtLastName = (EditText) findViewById(R.id.edtLastName);
        edtMark = (EditText) findViewById(R.id.edtMarks);
    }

    //Setup click event for the buttons Cancel/Add/Save
    private void setupButtons() {
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Cancel just go back to main screen
                        goBackToMain();
                    }
                }
        );

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Save on database
                        saveStudent();
                        //Show a toast with saved data
                        showToast();
                        //Return to main screen
                        goBackToMain();
                    }
                }
        );

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Save on database
                        updateStudent();
                        //Show a toast with updated data
                        showToast();
                        //Return to main screen
                        goBackToMain();
                    }
                }
        );
    }

    //Evaluates if there's a student for edition
    private void getIncomingData() {
        Student student = (Student) getIntent().getSerializableExtra("student");

        if (student == null)
            setupAddingStudent();
        else
            setupEditing(student);
    }

    //Hide the Save(update) button
    private void setupAddingStudent() {
        btnSave.setVisibility(View.GONE);
    }

    //Show the Save(update) button and load the student data for edition
    private void setupEditing(Student student) {
        btnSave.setVisibility(View.VISIBLE);

        txvId.setText(String.valueOf(student.getId()));
        edtFirstName.setText(student.getFirstName());
        edtLastName.setText(student.getLastName());
        edtMark.setText(String.valueOf(student.getMark()));
    }

    //Get the data from screen and save on database
    private void saveStudent() {
        //Check if mark was informed
        String strMark = edtMark.getText().toString();
        Integer mark = "".equals(strMark) ? 0 : Integer.parseInt(strMark);

        //Persist on database and retrieve the auto-generated Id
        int id = db.insert(new Student(
                0,
                edtFirstName.getText().toString(),
                edtLastName.getText().toString(),
                mark));

        //Update the screen with the new Id
        txvId.setText(String.valueOf(id));
    }

    //Update the database with the data from screen
    private void updateStudent() {
        String strMark = edtMark.getText().toString();
        Integer mark = "".equals(strMark) ? 0 : Integer.parseInt(strMark);
        db.update(new Student(
                Integer.parseInt(txvId.getText().toString()),
                edtFirstName.getText().toString(),
                edtLastName.getText().toString(),
                mark));
    }

    //Show a toast with the saved data
    private void showToast() {
        MyToast.toast(this,
                "Student " + txvId.getText() + " - " +
                edtFirstName.getText() + " " +
                edtLastName.getText() +
                " saved.");
    }

    //Go back to main screen
    private void goBackToMain() {
        Intent intent = new Intent(AddActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
