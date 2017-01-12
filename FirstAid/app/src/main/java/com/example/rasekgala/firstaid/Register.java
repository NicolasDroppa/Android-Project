package com.example.rasekgala.firstaid;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

public class Register extends AppCompatActivity {

    private EditText edtStreetName, edtCity, edtPostal, edtDoctorCode;
    MyDatabase database;
    // Progress Dialog Object
    ProgressDialog prgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.setTitle("Registration Part 2");

        //find views
        edtStreetName = (EditText)findViewById(R.id.editTextAddressLine1);
        edtCity = (EditText)findViewById(R.id.editTextCity);
        edtPostal = (EditText)findViewById(R.id.editTextPostalCode);
        edtDoctorCode = (EditText)findViewById(R.id.editTextDoctorCode);
        database = new MyDatabase(this);

        //set the progress bar
        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Completing registration. Please wait...");
        prgDialog.setCancelable(false);
    }

     /*
    *This method is onClick when the button is clicked
    * receive the view
     */

    public void register(View view)
    {
        //get the values from the views
        String street, city, code, doctorCode;
        street = edtStreetName.getText().toString();
        city = edtCity.getText().toString();
        code = edtPostal.getText().toString();
        doctorCode = edtDoctorCode.getText().toString();

        SharedPreferences sharedPreference = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREFF), Context.MODE_PRIVATE);

        String token = sharedPreference.getString(getString(R.string.FCM_TOKEN),"");

        //validate the values to ensure they are in order
        //check if the street is not empty
        if(street.length() < 1)
        {
            Toast.makeText(this, "Enter valid street name", Toast.LENGTH_LONG).show();
            edtStreetName.setBackgroundColor(Color.RED);
            return;
        }
        //check if the city is not empty
        if(city.length() < 1)
        {
            Toast.makeText(this, "Enter valid city", Toast.LENGTH_LONG).show();
            edtCity.setBackgroundColor(Color.RED);
            return;
        }
        //check if the postal code is equal to 4
        if(code.length() != 4)
        {
            Toast.makeText(this, "Enter valid postal code", Toast.LENGTH_LONG).show();
            edtPostal.setBackgroundColor(Color.RED);
            return;
        }
        //check if the doctor code is equal to 9
        if(doctorCode.length() != 9)
        {
            Toast.makeText(this, "Enter valid doctor code", Toast.LENGTH_LONG).show();
            edtDoctorCode.setBackgroundColor(Color.RED);
            return;
        }

        if(token.equals(""))
        {
            Toast.makeText(this, "Please check network connection", Toast.LENGTH_LONG).show();
            return;
        }

        //set values to object
        Address address = new Address();
        address.setStreetName(street);
        address.setPostalcode(code);
        address.setCity(city);

        //get  the patient object from the intent
        final Patient patient = (Patient) getIntent().getSerializableExtra("patient");
        patient.setDoctorCode(Integer.parseInt(doctorCode));


        try{

            // Show ProgressBar
            prgDialog.show();

            //create the httpClient object
            AsyncHttpClient client = new AsyncHttpClient();
            //create RequestParameter object
            RequestParams params = new RequestParams();
            //put values into the request
            params.put("phone","patient");
            params.put("name",patient.getName());
            params.put("surname",patient.getSurname());
            params.put("id", String.valueOf(patient.getIdNumber()));
            params.put("gender",patient.getGender());
            params.put("email",patient.getEmail());
            params.put("doctor_code", String.valueOf(patient.getDoctorCode()));
            params.put("city",address.getCity());
            params.put("street",address.getStreetName());
            params.put("postal",address.getPostalcode());
            params.put("phone_number", patient.getPhoneNo());



            params.put("token", token);


            // Make Http call to register.php with data of user

            client.post("http://192.168.42.29/doctor/register.php", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(String response) {

                    try {

                        // Get JSON object
                        JSONObject obj = new JSONObject(response);
                        System.out.println(obj.get("feedback"));
                        System.out.println(obj.get("phone"));

                        // Hide ProgressBar
                        prgDialog.hide();
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        //when the messaage is received then move call register Patient method to nevigate to another activity
                        if (String.valueOf(obj.get("feedback")).equals("Registration successful")) {
                            //store the patient into the sqlite database
                            registerPatient(patient);

                            SharedPreferences sharedPreference = getApplicationContext().getSharedPreferences(getString(R.string.DOC_PREF), Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor = sharedPreference.edit();

                            editor.putString(getString(R.string.DOC_PHONE),String.valueOf(obj.get("phone")));
                            editor.apply();

                        }
                    }catch (Exception e)
                    {

                    }
                }

                @Override
                public void onFailure(int statusCode, Throwable error, String content) {

                    // Hide ProgressBar
                    prgDialog.hide();

                    Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_LONG).show();
                    if (statusCode == 404) {
                        Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                    } else if (statusCode == 500) {
                        Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });

        }catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), "Error Occured, Internet connection failure", Toast.LENGTH_LONG).show();
        }
    }

    public void registerPatient(Patient patient)
    {
        //store the user data into the sqlite database and move to All message activity
        database.storePatient(patient);
        startActivity(new Intent(getApplicationContext(), AllMessageActivity.class));
        finish();//finish the registration activity
    }
}
