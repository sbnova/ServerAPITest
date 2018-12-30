package com.cyj.serverapitest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cyj.serverapitest.utils.PasswordUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

                String firstPw = userPwEdt.getText().toString();
                String secondPw = userPwCheckEdt.getText().toString();

                if(!firstPw.equals(secondPw)){
                    Toast.makeText(mContext, "비밀번호가 서로 다릅니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                OkHttpClient okHttpClient = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder().add("user_id", userIdEdt.getText().toString())
                        .add("password", PasswordUtil.getEncrytedPassword(userPwEdt.getText().toString()))
                        .add("name", userNameEdt.getText().toString())
                        .add("phone", userPhoneEdt.getText().toString())
                        .add("email", userEmailEdt.getText().toString()).build();

                Request request = new Request.Builder().url("http://api-dev.lebit.kr/auth").put(requestBody).build();

                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, "서버 통신실패", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseBody = response.body().string();
                        Log.d("회원가입리스폰스",responseBody);

                        try {
                            JSONObject root = new JSONObject(responseBody);
                            int code = root.getInt("code");
                            String message = root.getString("message");

                            Log.d("회원가입리스폰스","코드 : " + code);
                            Log.d("회원가입리스폰스","메세지 : " + message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
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
