package ca.joel.crud8215;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import static ca.joel.crud8215.DBHandler.DB_FILE;

//Java class with all the logic for the main screen
public class MainActivity extends AppCompatActivity {

    StudentAdapter adapter;
    FloatingActionButton flbAdd;
    DBHandler db;

    //App initialization, where execution starts
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Code auto-generated, just kept unchanged
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create the database handler
        db = new DBHandler(this, DB_FILE, null, 1);

        //Setup the screen components
        setupAddButton();
        setupListView();
    }

    //Reload all students when the screen reloads
    @Override
    protected void onStart() {
        super.onStart();
        loadAllStudents();
    }

    //Setup the Add floating button click to open AddActivity
    private void setupAddButton() {
        flbAdd = (FloatingActionButton) findViewById(R.id.flbAdd);
        flbAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Create the intent for opening another activity
                        Intent intent = new Intent(MainActivity.this, AddActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    //Setup the main List View which will show all students
    private void setupListView() {
        //Create an adapter for placing the students using their specific layout
        adapter = new StudentAdapter(getApplicationContext(), R.layout.layout_student);

        //Set the adapter on the List View
        ListView lsvStudents = (ListView) findViewById(R.id.lsvStudents);
        lsvStudents.setAdapter(adapter);
    }

    //Get all students from database and add into the adapter
    private void loadAllStudents() {
        adapter.clear();
        adapter.addAll(db.getAll());
    }

    //Adapter class that handles students into the List View
    private class StudentAdapter extends ArrayAdapter<Student> {

        //Constructor
        public StudentAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);
        }

        //Method called for each element of the Adapter
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            //Null Pointer prevention
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.layout_student, null);
            }

            //Get the current element (student)
            Student student = getItem(position);

            //Setup the List View element with student data
            TextView txvId = (TextView) convertView.findViewById(R.id.txvId);
            txvId.setText(String.valueOf(student.getId()));

            TextView txvName = (TextView) convertView.findViewById(R.id.txvName);
            txvName.setText(student.getFirstName() + " " + student.getLastName());

            TextView txvMark = (TextView) convertView.findViewById(R.id.txvMark);
            txvMark.setText("Marks: " + String.valueOf(student.getMark()));

            //Setup the List View DELETE button for this student
            Button btnDelete = (Button) convertView.findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener(createDeleteClickFor(student));

            //Setup the List View EDIT button for this student
            Button btnEdit = (Button) convertView.findViewById(R.id.btnEdit);
            btnEdit.setOnClickListener(createEditClickFor(student));

            return convertView;
        }

        //Setup the DELETE click
        private View.OnClickListener createDeleteClickFor(final Student student) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Delete the student on database
                    db.delete(student);
                    //Show a toast message
                    showToast();
                    //Reload the students List View
                    loadAllStudents();
                }

                private void showToast() {
                    MyToast.toast(getContext(), "Student " + student.getId() + " - " +
                            student.getFirstName() + " " +
                            student.getLastName() +
                            " deleted.");
                }
            };
        }

        //Setup the EDIT click
        private View.OnClickListener createEditClickFor(final Student student) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Create the intent to open AddActivity
                    Intent intent = new Intent(MainActivity.this, AddActivity.class);
                    //Pass the student to be edited on AddActivity
                    intent.putExtra("student", student);
                    startActivity(intent);
                }
            };
        }
    }

}

