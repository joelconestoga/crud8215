package ca.joel.crud8215;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new StudentAdapter(getApplicationContext(), R.layout.layout_student);

        ListView lsvStudents = (ListView) findViewById(R.id.lsvStudents);
        lsvStudents.setAdapter(adapter);

        adapter.add(new Student(1, "Joel", "Matsu", 96));
        adapter.add(new Student(2, "Joel", "Matsu", 96));
        adapter.add(new Student(3, "Joel", "Matsu", 96));
    }

    class Student {
        int id;
        String firstName;
        String lastName;
        int mark;

        public Student(int id, String firstName, String lastName, int mark) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.mark = mark;
        }

        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public String getFirstName() {
            return firstName;
        }
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
        public String getLastName() {
            return lastName;
        }
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
        public int getMark() {
            return mark;
        }
        public void setMark(int mark) {
            this.mark = mark;
        }
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

            return convertView;
        }
    }

}

