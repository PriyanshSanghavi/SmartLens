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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.smartlens.DataBase.Model.DriverData;
import com.example.smartlens.DataBase.Model.TpoliceData;
import com.example.smartlens.DataBase.SharedPrefManagerNewD;
import com.example.smartlens.DataBase.SharedPrefManagerNewP;
import com.example.smartlens.DataBase.URLs;
import com.example.smartlens.Driver.Activity.DriverHome;
import com.example.smartlens.TPolice.Activity.TpoliceForgotPassword;
import com.example.smartlens.R;
import com.example.smartlens.TPolice.Activity.TPoliceHome;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class PoliceLogin extends Fragment {
    TextView tpl_fpassword;
    EditText tpl_email,tpl_password;
    Button tpl_signin;
    ProgressDialog tpl_pd;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.login_police_fragment,container,false);
        tpl_fpassword = (TextView) rootView.findViewById(R.id.tpl_fpassword);
        tpl_email = (EditText) rootView.findViewById(R.id.tpl_email);
        tpl_password = (EditText) rootView.findViewById(R.id.tpl_password);
        tpl_signin= (Button) rootView.findViewById(R.id.tpl_signin);
        AndroidNetworking.initialize(getActivity());

        tpl_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    tpl_pd = new ProgressDialog(getActivity());
                    tpl_pd.show();
                    tpl_pd.setContentView(R.layout.progress_dialog);
                    tpl_pd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    tpl_pd.setCanceledOnTouchOutside(false);
                    checkLogin();

                }
            }
        });
        tpl_fpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fpassword = new Intent(getActivity(), TpoliceForgotPassword.class);
                startActivity(fpassword);
            }
        });
        return rootView;
    }
    public Boolean validate() {
        String email = tpl_email.getText().toString().trim();
        String emailPatten = "[A-Za-z0-9._-]+@[a-zA-Z]+\\.+[A-Za-z]+";
        if (!email.matches(emailPatten)) {
            tpl_email.setError("Please Enter Email Id");
            Toast.makeText(getActivity(),"Please Enter Valid Email", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (tpl_password.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(),"Please Enter Password", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void checkLogin(){

        AndroidNetworking.post(URLs.ROOT_URL + "tpolice_login.php")
                .addBodyParameter("email",tpl_email.getText().toString())
                .addBodyParameter("password",tpl_password.getText().toString())
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        tpl_pd.dismiss();
                        try {
                            if(response.getString("status").equals("true")){
                                Gson gson = new Gson();
                                TpoliceData tpoliceData = gson.fromJson(response.toString(), TpoliceData.class);
                                SharedPrefManagerNewP.getInstance(getActivity()).userLogin(tpoliceData);
                                startActivity(new Intent(getActivity(), TPoliceHome.class));
                            }
                            else{
                                Toast.makeText(getActivity(),"Please Enter Correct email id or Password",Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        tpl_pd.dismiss();
                        Toast.makeText(getActivity(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
