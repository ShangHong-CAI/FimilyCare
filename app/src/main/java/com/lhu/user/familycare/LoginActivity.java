package com.lhu.user.familycare;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lhu.user.familycare.db.User;
import com.lhu.user.familycare.db.UserDAO;
import com.lhu.user.familycare.tool.IMGtool;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import okhttp3.OkHttpClient;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    private Context context;
    private EditText ET_account, ET_password;
    private ImageView bg_login;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        mAuth = FirebaseAuth.getInstance();
        UserDAO userDAO = new UserDAO(context);
        User user = userDAO.getUser();
        if (!isConnected()) {
            Toast.makeText(context, "請檢察網路連線", Toast.LENGTH_SHORT).show();
        } else if (user != null) {
            String account = user.getID();
            String password = user.getPassword();
            if (!account.isEmpty() && !password.isEmpty()) {
                Toast.makeText(context, "登入中，請稍後", Toast.LENGTH_LONG).show();
                mAuth.signInWithEmailAndPassword(account, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    Intent intent = new Intent();
                                    intent.setClass(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }

                                // ...
                            }
                        });

            }
        }
        findView();
        /*
        * 1.判斷網路連線
        * 2.檢查是否登入
        * 3.登入，須在本APP上驗證帳號密碼格式是否正確
        * 4.用web Service檢查帳號是否存在資料庫，密碼是否正確
        * 5.若已登入，則此畫面不顯示，直接進主畫面
        * 6.註冊(連到畫面)及忘記密碼
        * */

    }

    public void doLogin(final String email, final String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //建立User物件
                            User mUser = new User();
                            mUser.setID(user.getEmail());
                            mUser.setName(user.getDisplayName());
                            mUser.setPassword(password);
                            //進行db處理
                            UserDAO userDAO = new UserDAO(context);
                            if (userDAO.getUser() == null) {
                                userDAO.insert(mUser);
                            } else if (userDAO.getUser().getID() != mUser.getID()) {
                                userDAO.delete();
                                userDAO.insert(mUser);
                            }
                            //結束後進入主畫面
                            Intent intent = new Intent();
                            intent.setClass(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });

    }

    public void findView() {
        ET_account = (EditText) findViewById(R.id.ET_account);
        ET_password = (EditText) findViewById(R.id.ET_password);
        bg_login = (ImageView) findViewById(R.id.bg_login);
        bg_login.setImageBitmap(IMGtool.readBitMap(context, R.drawable.login));
//        Picasso.with(context).load(R.drawable.login).into(bg_login);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.S_login:
                if (!isConnected()) {
                    Toast.makeText(context, "請檢察網路連線", Toast.LENGTH_SHORT).show();
                } else {
                    String account = ET_account.getText().toString().trim();
                    String password = ET_password.getText().toString().trim();
                    if (!account.isEmpty() && !password.isEmpty()) {
                        Toast.makeText(context, "登入中，請稍後", Toast.LENGTH_LONG).show();
                        doLogin(account,password);
                    } else
                        Toast.makeText(context, "輸入錯誤", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.S_registered:
                Intent intent2 = new Intent();
                intent2.setClass(LoginActivity.this, RegisteredActivity.class);
                startActivity(intent2);
                break;
            case R.id.S_forget_PW:
                Toast.makeText(this, "請等待下一版的更新，敬請期待", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    class TestWeb extends Thread {
        private String account;
        private String password;
        private InputStream inputStream = null;
        private StringBuilder str = new StringBuilder();
        private OkHttpClient client = new OkHttpClient();
        private InputStreamReader reader;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                String result = str.toString();
                if (result.equals("輸入錯誤")) {
                    Toast.makeText(context, "登入失敗", Toast.LENGTH_SHORT).show();
                } else {
//                    Gson gson = new GsonBuilder()
//                            .serializeNulls()
//                            .create();
//                    System.out.println(result);
//                    User user = gson.fromJson(result,User.class);
//                    UserDAO userDAO = new UserDAO(context);
//                    if(userDAO.getUser()==null) {
//                        userDAO.insert(user);
//                    }else if(userDAO.getUser().getID()!=user.getID()){
//                        userDAO.delete();
//                        userDAO.insert(user);
//                    }


                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        };

        public TestWeb(String account, String password) {
            this.account = account;
            this.password = password;
        }

        @Override
        public void run() {
            super.run();
            URL url = null;
            try {
//                String URLSTR = String.format("http://140.131.7.63//family_care/callback/login_check.aspx?M_Password=%s&M_ID=%s",password,account);
//                url = new URL(URLSTR);
//                // 替換成 OkHttp 2.0 ---------------------------------------
//                Request request = new Request.Builder().url(url).build();
//                Response response = client.newCall(request).execute();
//                inputStream = response.body().byteStream();
//                reader = new InputStreamReader(inputStream, "UTF-8");
//                char[] buffer = new char[1];
//                while (reader.read(buffer) != -1) {
//                    str.append(new String(buffer));
//                }
                runOnUiThread(r);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
//                    outputStream.close();
                } catch (Exception e) {

                }
                //setTitle("完成");
            }
        }
    }


}
