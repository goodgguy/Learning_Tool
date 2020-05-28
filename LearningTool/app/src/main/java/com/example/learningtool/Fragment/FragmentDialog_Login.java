package com.example.learningtool.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.learningtool.Interface.getUserInterface;
import com.example.learningtool.R;
import com.example.learningtool.model.Users;
import com.example.learningtool.ultil.Server;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentDialog_Login extends DialogFragment {
    Button btnLogin;
    TextView txtusername,txtpassword,txtSignup,txtExit;
    getUserInterface getUserInterface;
    public static FragmentDialog_Login newInstance()
    {
        return new FragmentDialog_Login();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogTheme);
        getUserInterface= (com.example.learningtool.Interface.getUserInterface) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.custom_dialog_login,container,false);
        btnLogin=view.findViewById(R.id.btn_login);
        txtusername=view.findViewById(R.id.txtusername);
        txtpassword=view.findViewById(R.id.txtpassword);
        txtSignup=view.findViewById(R.id.link_signup);
        txtExit=view.findViewById(R.id.link_exit);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setCancelable(false);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=txtusername.getText().toString();
                String password=txtpassword.getText().toString();
                final RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
                String url= Server.UserLogin+username+Server.UserLogin1+password;
                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                if (response.has("status")) {
                                    Toast.makeText(getActivity(),"Tài khoản không tòn tại",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    try {
                                        int Id=response.getInt("Id");
                                        String Name=response.getString("Name");
                                        String Email=response.getString("Email");
                                        String Phone=response.getString("Phone");
                                        String Address=response.getString("Address");
                                        String Username=response.getString("Username");
                                        String password=response.getString("Password");
                                        String Avartar=response.getString("Avatar");
                                        int RoleId=response.getInt("RoleId");
                                        Users users=new Users(Id,Name,Email,Phone,Address,Username,password,Avartar,RoleId);
                                        dismiss();
                                        getUserInterface.get(users);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getActivity(),"Không tồn tại tài khoản này",Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                requestQueue.add(jsonObjectRequest);
            }
        });
        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment= FragmentDialog_SignUp.newInstance();
                dialogFragment.show(getFragmentManager(),"tag1");
            }
        });
        txtExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserInterface.getExit(true);
            }
        });
    }


}
