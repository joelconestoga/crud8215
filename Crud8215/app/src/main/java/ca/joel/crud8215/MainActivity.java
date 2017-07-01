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

public class MainActivity extends AppCompatActivity {

    StudentAdapter adapter;
    FloatingActionButton flbAdd;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHandler(this, DB_FILE, null, 1);

        setupAddButton();
        setupListView();
    }

    private void setupListView() {
        adapter = new StudentAdapter(getApplicationContext(), R.layout.layout_student);
        ListView lsvStudents = (ListView) findViewById(R.id.lsvStudents);
        lsvStudents.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadAllStudents();
    }

    private void loadAllStudents() {
        adapter.clear();
        adapter.addAll(db.getAll());
    }

    private void setupAddButton() {
        flbAdd = (FloatingActionButton) findViewById(R.id.flbAdd);
        flbAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, AddActivity.class);
                        //intent.putExtra("info", edtInfo.getText().toString());
                        startActivity(intent);
                    }
                }
        );
    }

    private class StudentAdapter extends ArrayAdapter<Student> {

        public StudentAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.layout_student, null);
            }

            Student student = getItem(position);

            TextView txvId = (TextView) convertView.findViewById(R.id.txvId);
            txvId.setText(String.valueOf(student.getId()));

            TextView txvName = (TextView) convertView.findViewById(R.id.txvName);
            txvName.setText(student.getFirstName() + " " + student.getLastName());

            TextView txvMark = (TextView) convertView.findViewById(R.id.txvMark);
            txvMark.setText("Marks: " + String.valueOf(student.getMark()));

            Button btnDelete = (Button) convertView.findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener(createDeleteClickFor(student));

            Button btnEdit = (Button) convertView.findViewById(R.id.btnEdit);
            btnEdit.setOnClickListener(createEditClickFor(student));

            return convertView;
        }

        private View.OnClickListener createDeleteClickFor(final Student student) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.delete(student);
                    showToast();
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

        private View.OnClickListener createEditClickFor(final Student student) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, AddActivity.class);
                    intent.putExtra("student", student);
                    startActivity(intent);
                }
            };
        }
    }

}

