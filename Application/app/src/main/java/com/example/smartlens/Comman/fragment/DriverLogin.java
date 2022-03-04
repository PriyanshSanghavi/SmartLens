package com.example.smartlens.Comman.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.smartlens.DataBase.Model.DriverData;
import com.example.smartlens.DataBase.SharedPrefManagerNewD;
import com.example.smartlens.DataBase.URLs;
import com.example.smartlens.Driver.Activity.DriverForgotPassword;
import com.example.smartlens.Driver.Activity.DriverHome;
import com.example.smartlens.R;
import com.example.smartlens.Driver.Activity.DriverRegister;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class DriverLogin extends Fragment {

    TextView dl_fpassword, dl_signup;
    EditText dl_email, dl_password;
    Button dl_signin;
    ProgressDialog dl_pd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_driver_fragment, container, false);
        dl_fpassword = (TextView) rootView.findViewById(R.id.dl_fpassword);
        dl_signup = (TextView) rootView.findViewById(R.id.dl_signup);
        dl_email = (EditText) rootView.findViewById(R.id.dl_email);
        dl_password = (EditText) rootView.findViewById(R.id.dl_password);
        dl_signin = (Button) rootView.findViewById(R.id.dl_signin);
        AndroidNetworking.initialize(getActivity());

        dl_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    dl_pd = new ProgressDialog(getActivity());
                    dl_pd.show();
                    dl_pd.setContentView(R.layout.progress_dialog);
                    dl_pd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dl_pd.setCanceledOnTouchOutside(false);
                    checkLogin();
                }
            }
        });
        dl_fpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dfpassword = new Intent(getActivity(), DriverForgotPassword.class);
                startActivity(dfpassword);
            }
        });
        dl_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(getActivity(), DriverRegister.class);
                startActivity(register);
            }
        });
        return rootView;
    }

    public Boolean validate() {
        String email = dl_email.getText().toString().trim();
        String emailPatten = "[A-Za-z0-9._-]+@[a-zA-Z]+\\.+[A-Za-z]+";
        if (!email.matches(emailPatten)) {
            dl_email.setError("Please Enter Email Id");
            Toast.makeText(getActivity(),"Please Enter Valid Email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dl_password.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(),"Please Enter Password", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void checkLogin(){

        AndroidNetworking.post(URLs.ROOT_URL + "driver_login.php")
                .addBodyParameter("email",dl_email.getText().toString())
                .addBodyParameter("password",dl_password.getText().toString())
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dl_pd.dismiss();
                        try {
                            if(response.getString("status").equals("true")){
                                Gson gson = new Gson();
                                DriverData driverData = gson.fromJson(response.toString(),DriverData.class);
                                SharedPrefManagerNewD.getInstance(getActivity()).userLogin(driverData);
                                startActivity(new Intent(getActivity(), DriverHome.class));

                            }
                            else{
                                Toast.makeText(getActivity(),"Please Enter Correct email id or Password",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        dl_pd.dismiss();
                        Toast.makeText(getActivity(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_SHORT).show();

                    }
                });
    }

}
