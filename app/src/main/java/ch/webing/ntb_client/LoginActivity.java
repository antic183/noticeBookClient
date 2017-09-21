package ch.webing.ntb_client;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ch.webing.ntb_client.model.Notice;
import ch.webing.ntb_client.model.User;
import ch.webing.ntb_client.rest.LoginEndpoints;
import ch.webing.ntb_client.rest.NoticeEndpoints;
import ch.webing.ntb_client.rest.RestClient;
import ch.webing.ntb_client.rest.TokenEndpoints;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ch.webing.ntb_client.constant.ConstantsSharedPreference.JWT_TOKEN;
import static ch.webing.ntb_client.constant.ConstantsSharedPreference.NAMESPACE;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText txtEmail;
    private EditText txtPassword;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkIsUserLoggedIn();
    }

    private boolean isConnected() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            return true;
        } else {
            return false;

        }
    }

    public void onLogin(View view) {
        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        if (!email.isEmpty() && !password.isEmpty()) {
            btnLogin.setEnabled(false);
            loadData(new User(email, password));
        } else {
            Toast.makeText(LoginActivity.this, "Input fields email and password are required!", Toast.LENGTH_LONG).show();
        }
    }

    private void initLayout() {
        setContentView(R.layout.activity_login);
        btnLogin = (Button) findViewById(R.id.loginActivity_btnLogin);
        txtEmail = (EditText) findViewById(R.id.loginActivity_inputEmail);
        txtPassword = (EditText) findViewById(R.id.loginActivity_inputPassword);
    }

    private void loadData(User user) {
        final LoginEndpoints apiService = RestClient.getRestClient().create(LoginEndpoints.class);
        Call<User> call = apiService.login(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                btnLogin.setEnabled(true);
                if (response.isSuccessful()) {
                    User user = response.body();
                    SharedPreferences.Editor editor = getSharedPreferences(NAMESPACE, MODE_PRIVATE).edit();
                    editor.putString(JWT_TOKEN, user.getJwtToken());
                    editor.commit();

                    startActivity(new Intent(LoginActivity.this, NoticeOverviewActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Incorrect user data!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                btnLogin.setEnabled(true);
                Toast.makeText(LoginActivity.this, "Request Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkIsUserLoggedIn();
    }

    private void checkIsUserLoggedIn() {
        SharedPreferences prefs = getSharedPreferences(NAMESPACE, MODE_PRIVATE);
        final String jwtToken = prefs.getString(JWT_TOKEN, null);
        if (prefs.getString(JWT_TOKEN, null) != null) {

            if (!isConnected()) {
                Toast.makeText(LoginActivity.this, "No internet connecton!", Toast.LENGTH_SHORT).show();
                initLayout();
                return;
            }

            final TokenEndpoints apiService = RestClient.getRestClient().create(TokenEndpoints.class);
            Call<Void> call = apiService.validateToken(jwtToken);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        startActivity(new Intent(LoginActivity.this, NoticeOverviewActivity.class));
                        finish();
                    } else {
                        initLayout();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    initLayout();
                    Toast.makeText(LoginActivity.this, "App Error!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            initLayout();
        }
    }
}