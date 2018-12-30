package com.cyj.serverapitest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends BaseActivity {

    private android.widget.EditText userIdEdt;
    private android.widget.EditText userPwEdt;
    private android.widget.EditText userPwCheckEdt;
    private android.widget.EditText userNameEdt;
    private android.widget.EditText userEmailEdt;
    private android.widget.EditText userPhoneEdt;
    private android.widget.Button singUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        bindViews();
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

        singUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                몇가지 검증 절차.
                if(userIdEdt.getText().toString().equals("")){
                    Toast.makeText(mContext, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
//                    강제종료
                }

//                1. 아이디는 반드시 6글자 이상
                if(userIdEdt.getText().toString().length() < 6 ){
                    Toast.makeText(mContext, "아이디는 6글자 이상이여야 합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

//                2. 비밀번호도 8글자 이상
                if(userPwEdt.getText().toString().length() < 8){
                    Toast.makeText(mContext, "비밀번호는 8글자 이상이여야 합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

//                3. 비밀번호와 다시 입력한 비밀번호가 같아야함.
                if(!userPwEdt.getText().toString().equals(userPwCheckEdt.getText().toString())){
                    Toast.makeText(mContext, "비밀번호가 서로 다릅니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }

    @Override
    public void setValues() {

    }

    @Override
    public void bindViews() {
        this.singUpBtn = (Button) findViewById(R.id.singUpBtn);
        this.userPhoneEdt = (EditText) findViewById(R.id.userPhoneEdt);
        this.userEmailEdt = (EditText) findViewById(R.id.userEmailEdt);
        this.userNameEdt = (EditText) findViewById(R.id.userNameEdt);
        this.userPwCheckEdt = (EditText) findViewById(R.id.userPwCheckEdt);
        this.userPwEdt = (EditText) findViewById(R.id.userPwEdt);
        this.userIdEdt = (EditText) findViewById(R.id.userIdEdt);

    }
}
