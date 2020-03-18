package com.example.lifestyleapp;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
public class LoginFragment extends Fragment {
    EditText editTextName;
    EditText editTextPassword;
    TextView textViewMessage;

    //定义URL字符串
    public static String URL_Login = "http://192.168.0.107:8080/ServletTest/LoginServlet";

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //加载Fragment的页面
        View v=inflater.inflate(R.layout.fragment_login, container, false);
        editTextName=(EditText)v.findViewById(R.id.editTextName);
        editTextPassword=(EditText)v.findViewById(R.id.editTextPassword);
        textViewMessage=(TextView)v.findViewById(R.id.textViewMessage);

        //响应登录按钮
        Button buttonLogin=(Button)v.findViewById(R.id.buttonLogin);
        //添加侦听器，响应点击
        buttonLogin.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //获取控件中的数据
                String loginName=editTextName.getText().toString();
                String loginPassword=editTextPassword.getText().toString();
                if(!loginName.isEmpty()&&!loginPassword.isEmpty())
                {
                    //登录
                    login(loginName,loginPassword,v);
                }
                else
                {
                    //提示登录账号和密码非空
                    Snackbar snackbar=Snackbar.make(v,"账号或密码不能为空！",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });

        //响应登录
        TextView textViewRegister=(TextView)v.findViewById(R.id.textViewRegister);
        //添加侦听器，响应点击
        textViewRegister.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //切换到FragmentRegister
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                RegisterFragment fragment=new RegisterFragment();
                //替换掉现有的Fragment
                fragmentTransaction.replace(R.id.fragment_container,fragment);
                //在后退栈中保存
                fragmentTransaction.addToBackStack("login");
                fragmentTransaction.commit();
            }
        });
        return v;
    }

    //登录函数
    private void login(String name,String password,View view)
    {
        String LoginUrl = URL_Login + "?account=" + name + "&password=" + password;
        new MyAsyncTask(view,textViewMessage).execute(LoginUrl);
    }

    //创建子类继承AsyncTask
    public static class MyAsyncTask extends AsyncTask<String,Integer,String>
    {
        View view;
        TextView textview;     //获取显示信息的TextView
        public MyAsyncTask(View v, TextView tv) {
            textview = tv;
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
            return response.toString(); // 这里返回的结果就作为onPostExecute方法的入参
        }

        protected void onPostExecute(String s) {
            //如果登录成功，跳转
            if(s.equals("successful"))
            {
                //tv.setText("登录成功！");
                Snackbar snackbar=Snackbar.make(view,"登录成功！",Snackbar.LENGTH_LONG);
                snackbar.show();
            }
            else      //如果登录失败，提示错误信息
            {
                //tv.setText("登录失败，密码不匹配或账号未注册！");
                Snackbar snackbar=Snackbar.make(view,"登录失败，密码不匹配或账号未注册！",Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
    }

    //重写onViewStateRestored函数
    public void onViewStateRestored(Bundle savedInstanceState)
    {
        super.onViewStateRestored(savedInstanceState);

        //将刚刚注册成功的用户名和密码赋到登录页面
        MainActivity activity=(MainActivity)getActivity();
        if(activity!=null)
        {
            if(activity.userName!=null)
            {
                editTextName.setText(activity.userName);
            }
            if(activity.password!=null)
            {
                editTextPassword.setText(activity.password);
            }
        }
    }
}
