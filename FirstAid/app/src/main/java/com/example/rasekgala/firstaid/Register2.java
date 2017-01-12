package com.example.rasekgala.firstaid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Register2 extends AppCompatActivity {

    private EditText edtName, edtSurname, edtIdnumber, edtEmail, edtPhone;
    private RadioGroup group;
    private RadioButton selected;
    private String gender;
    private SharedPreferences data;
    private MyDatabase database;
    private String status;
    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        this.setTitle("Registration");

        //find views
        edtName = (EditText)findViewById(R.id.editTextName);
        edtSurname = (EditText)findViewById(R.id.editTextSurname);
        edtIdnumber = (EditText)findViewById(R.id.editTextIDentity);
        edtEmail = (EditText)findViewById(R.id.editTextEmail);
        edtPhone = (EditText)findViewById(R.id.editTextPhone);

        group = (RadioGroup)findViewById(R.id.group);
        gender = "";

        //instantiate database class
        database = new MyDatabase(this);

        //get the patient
        patient = database.getPatientDatails();

        //test if the patient is registered
        //if yes then we take the patient to the All messege else we leave  the patient
        //to continue with registration
        if(patient != null)
        {
            //nevidate to All message activity if patient has registered
            Intent i = new Intent(getBaseContext(),AllMessageActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_next) {


            setValues();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    *This method set the values
     */
    public void setValues()
    {
        String name, surname, idNumber, genders, email, phone;
        name = edtName.getText().toString();
        surname = edtSurname.getText().toString();
        idNumber = edtIdnumber.getText().toString();
        email = edtEmail.getText().toString();
        phone = edtPhone.getText().toString();

        //validate if the values are in the right format
        //test if name length is greater than one
        if(name.length() < 1)
        {
            Toast.makeText(this, "Enter valid name", Toast.LENGTH_LONG).show();
            edtName.setBackgroundColor(Color.RED);
            return;
        }

        //test if surname length is greater than one
        if(surname.length() < 1)
        {
            Toast.makeText(this, "Enter valid surname", Toast.LENGTH_LONG).show();
            edtSurname.setBackgroundColor(Color.RED);
            return;
        }
        //test if ID length is equal to 13
        if(idNumber.length() != 13)
        {
            Toast.makeText(this, "Enter valid ID number", Toast.LENGTH_LONG).show();
            edtIdnumber.setBackgroundColor(Color.RED);
            return;
        }
        //test if email length is greater than one
        if(email.length() < 1 && !email.contains("@"))
        {
            Toast.makeText(this, "Enter valid email address", Toast.LENGTH_LONG).show();
            edtEmail.setBackgroundColor(Color.RED);
            return;
        }
        //test if phone length is equal to 10
        if(phone.length() != 10)
        {
            Toast.makeText(this, "Enter valid phone number", Toast.LENGTH_LONG).show();
            edtPhone.setBackgroundColor(Color.RED);
            return;
        }

        //test if the gender is selected and set the right one based on selection
        int id = group.getCheckedRadioButtonId();
        if(id == R.id.radioButtonFemale)
        {
            gender = "Female";
        }else if(id == R.id.radioButtonMale){
            gender = "Male";
        }

        if(gender.equals(""))
        {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_LONG).show();

            return;
        }

        //create the patient object and set values
        Patient  patient = new Patient();
        patient.setName(name);
        patient.setSurname(surname);
        patient.setGender(gender);
        patient.setEmail(email);
        patient.setIdNumber(Long.parseLong(idNumber));
        patient.setPhoneNo(phone);
        Intent intent = new Intent(this, Register.class);
        intent.putExtra("patient",patient);
        startActivity(intent);//start  registration part 2 activity
    }
}
