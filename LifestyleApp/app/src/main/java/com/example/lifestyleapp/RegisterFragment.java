package com.example.lifestyleapp;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    String account;
    String password;
    //定义URL字符串
    public static String URL_Register = "http://192.168.0.107:8080/ServletTest/RegisterServlet";

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_register, container, false);
        //响应取消按钮
        Button buttonCancel=(Button)view.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //关闭当前Fragment
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();   //从栈中弹出栈顶的Fragment
            }
        });

        //响应确定按钮
        Button buttonOk=(Button)view.findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //取得控件
                EditText editTextName=(EditText)view.findViewById(R.id.editTextName);
                EditText editTextPassword=(EditText)view.findViewById(R.id.editTextPassword);
                EditText editTextPassword2=(EditText)view.findViewById(R.id.editTextPassword2);
                EditText editTextEmail=(EditText)view.findViewById(R.id.editTextEmail);
                EditText editTextPhone=(EditText)view.findViewById(R.id.editTextPhone);
                RadioGroup radioGroupSex=(RadioGroup)view.findViewById(R.id.radioGroupSex);

                //获取控件中的数据
                account=editTextName.getText().toString();
                password=editTextPassword.getText().toString();
                String password2=editTextPassword2.getText().toString();
                String email=editTextEmail.getText().toString();
                String phone=editTextPhone.getText().toString();
                String sex="M";    //性别  M代表男 F代表女 默认为男
                //获得被选中的性别按钮
                int checkRadioId=radioGroupSex.getCheckedRadioButtonId();
                //如果选的是女，则置为false
                if(checkRadioId==R.id.radioButtonFemale)
                {
                    sex="F";
                }
                if(!password.equals(password2))
                {
                    //提示两次密码不一致
                    Snackbar snackbar=Snackbar.make(v,"两次输入的密码不一致！",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                //完成注册
                else if(!account.isEmpty()&&!password.isEmpty()&&!email.isEmpty()&&!phone.isEmpty())
                {
                    register(v,account,password,email,phone,sex);
                }
                else
                {
                    //提示注册信息填写完整
                    Snackbar snackbar=Snackbar.make(v,"请将注册信息填写完整！",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
        return view;
    }

    //注册函数
    private void register(View view,String account,String password,String email,String phone,String sex) {
        String RegisterUrl = URL_Register + "?account=" + account + "&password=" + password
                + "&email=" + email + "&phone=" + phone + "&sex=" + sex;
        new RegisterFragment.MyRegisterAsyncTask(view).execute(RegisterUrl);
    }

    //创建子类继承AsyncTask
    public class MyRegisterAsyncTask extends AsyncTask<String,Integer,String>
    {
        View view;
        //TextView textview;     //获取显示信息的TextView
        public MyRegisterAsyncTask(View v) {
            //textview = tv;
            view=v;
        }

        protected String doInBackground(String... params) {
            //和服务器建立HTTP连接
            HttpURLConnection connection = null;
            StringBuilder response = new StringBuilder();
            try {
                URL url = new URL(params[0]); // 声明一个URL
                connection = (HttpURLConnection) url.openConnection(); // 打开该URL连接
                connection.setRequestMethod("GET"); // 设置请求方法
                connection.setConnectTimeout(80000); // 设置连接建立的超时时间
                connection.setReadTimeout(80000); // 设置网络报文收发超时时间
                InputStream in = connection.getInputStream();  // 通过连接的输入流获取下发报文
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response.toString();
        }

        protected void onPostExecute(String s) {
            //如果注册成功，跳转
            if(s.equals("200"))
            {
                //tv.setText("注册成功！");
                Snackbar snackbar=Snackbar.make(view,"注册成功！",Snackbar.LENGTH_LONG);
                snackbar.show();
                //注册成功后，将注册的用户名和密码填入登陆页面
                MainActivity activity=(MainActivity)getActivity();
                if(activity!=null)
                {
                    activity.userName=account;
                    activity.password=password;
                }

                //返回登录页面
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();

            }
            else if(s.equals("300"))     //提示注册失败信息
            {
                Snackbar snackbar=Snackbar.make(view,"注册失败！",Snackbar.LENGTH_LONG);
                snackbar.show();
            }
            else
            {
                Snackbar snackbar=Snackbar.make(view,"当前用户名已被注册！",Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
    }
}
