package ch.webing.ntb_client;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import static ch.webing.ntb_client.constant.ConstantsSharedPreference.JWT_TOKEN;
import static ch.webing.ntb_client.constant.ConstantsSharedPreference.NAMESPACE;

/**
 * Created by antic-software-ing on 21.09.2017.
 */

abstract class AbstractAppContentActivity extends AppCompatActivity {

    protected String jwtToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences(NAMESPACE, MODE_PRIVATE);
        jwtToken = prefs.getString(JWT_TOKEN, null);
        if (jwtToken == null) {
            startActivity(new Intent(AbstractAppContentActivity.this, LoginActivity.class));
            finish();
        }

        initView(savedInstanceState);
        loadData();
    }

    protected abstract void initView(Bundle savedInstanceState);
    protected abstract void loadData();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_about:
                Toast.makeText(AbstractAppContentActivity.this, "NoticeBook: Antic Marjan, Roman Steiner", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_action_logout:
                Toast.makeText(AbstractAppContentActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = getSharedPreferences(NAMESPACE, MODE_PRIVATE).edit();
                editor.remove(JWT_TOKEN);
                editor.commit();
                startActivity(new Intent(AbstractAppContentActivity.this, LoginActivity.class));
                finish();
                break;
        }

        return true;
    }
}
