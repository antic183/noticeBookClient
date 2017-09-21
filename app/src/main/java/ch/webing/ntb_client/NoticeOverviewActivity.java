package ch.webing.ntb_client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ch.webing.ntb_client.model.Notice;
import ch.webing.ntb_client.rest.NoticeEndpoints;
import ch.webing.ntb_client.rest.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ch.webing.ntb_client.constant.ContantsActivityIntents.*;

public class NoticeOverviewActivity extends AbstractAppContentActivity {

    private List<Notice> notices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_notice_overview);
        notices = new ArrayList<>();
    }

    /*
    TODO: in next version prevent to load data every time
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        Gson gson = new Gson();
        String result = gson.toJson(notices);
        savedInstanceState.putString("notices", result);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Gson gson = new Gson();
        String tmpNotices = savedInstanceState.getString("notices");
        notices = gson.fromJson(tmpNotices, new TypeToken<List<Notice>>(){}.getType());
    }*/


    @Override
    protected void loadData() {
        final NoticeEndpoints apiService = RestClient.getRestClient().create(NoticeEndpoints.class);
//        Toast.makeText(NoticeOverviewActivity.this, "loadAllData()!!!", Toast.LENGTH_SHORT).show();
        Call<List<Notice>> call = apiService.getNotes("Bearer " + jwtToken);
        call.enqueue(new Callback<List<Notice>>() {
            @Override
            public void onResponse(Call<List<Notice>> call, Response<List<Notice>> response) {
                if (response.isSuccessful()) {
                    notices = response.body();
                    showNoticeList();
                } else {
                    Toast.makeText(NoticeOverviewActivity.this, "api error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Notice>> call, Throwable t) {
                Toast.makeText(NoticeOverviewActivity.this, "App Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showNoticeList() {
        ListView myListView;
        final OwnAdapter ownAdapter;

        myListView = (ListView) findViewById(R.id.noticeOverview_list_view);
        ownAdapter = new OwnAdapter(this, notices);
        myListView.setAdapter(ownAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Notice clickedNotice = (Notice) adapter.getItemAtPosition(position);

                Intent intent = new Intent(NoticeOverviewActivity.this, NoticeDetailActivity.class);
                intent.putExtra(NOTICE_ID, clickedNotice.getId());
                startActivity(intent);
            }
        });
    }

    public void onWillAddNewNotice(View view) {
        Intent intent = new Intent(NoticeOverviewActivity.this, NoticeDetailActivity.class);
        startActivity(intent);
    }
}
