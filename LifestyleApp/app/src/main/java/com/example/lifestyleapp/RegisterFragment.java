package com.example.lifestyleapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {


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
                EditText editTextEmail=(EditText)view.findViewById(R.id.editTextEmail);
                EditText editTextPhone=(EditText)view.findViewById(R.id.editTextPhone);
                RadioGroup radioGroupSex=(RadioGroup)view.findViewById(R.id.radioGroupSex);

                //获取控件中的数据
                String name=editTextName.getText().toString();
                String password=editTextPassword.getText().toString();
                String email=editTextEmail.getText().toString();
                String phone=editTextPhone.getText().toString();
                boolean sex=true;    //性别  true代表男 false代表女 默认为女
                //获得被选中的性别按钮
                int checkRadioId=radioGroupSex.getCheckedRadioButtonId();
                //如果选的是女，则置为false
                if(checkRadioId==R.id.radioButtonFemale)
                {
                    sex=false;
                }

                //完成注册

                //注册成功后，将注册的用户名和密码填入登陆页面
                MainActivity activity=(MainActivity)getActivity();
                if(activity!=null)
                {
                    activity.userName=name;
                    activity.password=password;
                }

                //返回登录页面
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });
        return view;
    }

}
