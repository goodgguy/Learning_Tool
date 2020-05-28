package com.example.learningtool.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.DialogTitle;
import androidx.fragment.app.DialogFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.learningtool.Interface.getIdUserInterface;
import com.example.learningtool.Interface.getUserInterface;
import com.example.learningtool.R;
import com.example.learningtool.activity.MainActivity;
import com.example.learningtool.model.Users;
import com.example.learningtool.ultil.Server;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class FragmentDialog_SignUp extends DialogFragment {
    TextView txtName,txtEmail,txtPhone,txtAddress,txtUsername,txtPassword;
    Button btnSignUp;
    String Name;
    String Email;
    String Phone;
    String Address;
    String Username;
    String password;
    String Avatar="https://cdn4.iconfinder.com/data/icons/people-avatar-flat-1/64/girl_ginger_curly_people_woman_teenager_avatar-512.png";
    int RoleId=0;
    getIdUserInterface getIdUserInterface;
    public static FragmentDialog_SignUp newInstance()
    {
        return new FragmentDialog_SignUp();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogTheme);
        getIdUserInterface= (com.example.learningtool.Interface.getIdUserInterface) getActivity();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.custom_dialog_signup,container,false);
        txtName=view.findViewById(R.id.input_name);
        txtEmail=view.findViewById(R.id.input_email);
        txtPhone=view.findViewById(R.id.input_phone);
        txtAddress=view.findViewById(R.id.input_address);
        txtUsername=view.findViewById(R.id.input_username);
        txtPassword=view.findViewById(R.id.input_password);
        btnSignUp=view.findViewById(R.id.btn_signup);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = txtName.getText().toString();
                Email = txtEmail.getText().toString();
                Phone = txtPhone.getText().toString();
                Address = txtAddress.getText().toString();
                Username = txtUsername.getText().toString();
                password = txtPassword.getText().toString();
                try {
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("Name",Name);
                    jsonBody.put("Email",Email);
                    jsonBody.put("Phone",Phone);
                    jsonBody.put("Address",Address);
                    jsonBody.put("Username",Username);
                    jsonBody.put("Password",password);
                    jsonBody.put("Avatar",null);
                    jsonBody.put("RoleId",0);

                    final String mRequestBody = jsonBody.toString();
                    Log.e("Body Body Body",mRequestBody);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.postUser, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            /*Toast.makeText(getActivity(),"Thành công",Toast.LENGTH_SHORT).show();*/
                            getUserID();
                            dismiss();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            try {
                                return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                                return null;
                            }
                        }

                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
                            String responseString = "";
                            if (response != null) {
                                responseString = String.valueOf(response.statusCode);
                            }
                            return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                        }
                    };

                    requestQueue.add(stringRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void getUserID()
    {
        Username = txtUsername.getText().toString();
        password = txtPassword.getText().toString();
        final RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        String url= Server.UserLogin+Username+Server.UserLogin1+password;
        Log.e("LASJDLAJDLA",url);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int Id=response.getInt("Id");
                            Log.e("TUANQUEN", String.valueOf(Id));
                            getIdUserInterface.getID(Id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}