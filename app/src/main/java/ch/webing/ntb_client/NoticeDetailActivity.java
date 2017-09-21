package ch.webing.ntb_client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ch.webing.ntb_client.model.Notice;
import ch.webing.ntb_client.rest.NoticeEndpoints;
import ch.webing.ntb_client.rest.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ch.webing.ntb_client.constant.ContantsActivityIntents.NOTICE_ID;

public class NoticeDetailActivity extends AbstractAppContentActivity {
    private int noticeId;
    private EditText txtTitle;
    private EditText txtText;
    private Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_notice_detail);
        txtTitle = (EditText) findViewById(R.id.noticeDetailActivity_inputTitle);
        txtText = (EditText) findViewById(R.id.noticeDetailActivity_inputText);
        btnDelete = (Button) findViewById(R.id.noticeDetailActivity_btnDelete);
        Bundle extras = getIntent().getExtras();
        noticeId = getIntent().hasExtra(NOTICE_ID) ? extras.getInt(NOTICE_ID) : 0;

        if (noticeId == 0) {
            btnDelete.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void loadData() {
        if (noticeId > 0) {
            final NoticeEndpoints apiService = RestClient.getRestClient().create(NoticeEndpoints.class);
            Call<Notice> call = apiService.getNote("Bearer " + jwtToken, noticeId);
            call.enqueue(new Callback<Notice>() {
                @Override
                public void onResponse(Call<Notice> call, Response<Notice> response) {
                    if (response.isSuccessful()) {
                        Notice notice = response.body();
                        populateInputFields(notice);
                    } else {
                        Toast.makeText(NoticeDetailActivity.this, "api error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Notice> call, Throwable t) {
                    Toast.makeText(NoticeDetailActivity.this, "App Error!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void populateInputFields(Notice notice) {
        txtTitle.setText(notice.getTitle());
        txtText.setText(notice.getText());
    }

    public void onSave(View view) {
        String title = txtTitle.getText().toString().trim();
        String text = txtText.getText().toString().trim();

        if (!title.isEmpty() && !text.isEmpty()) {
            if (noticeId > 0) {
                Notice notice = new Notice(noticeId, title, text);
                updateItem(notice);
            } else {
                Notice notice = new Notice(noticeId, title, text);
                saveItem(notice);
            }
        } else {
            Toast.makeText(NoticeDetailActivity.this, "Input fields title and text are required!", Toast.LENGTH_LONG).show();
            return;
        }
    }

    private void saveItem(Notice notice) {
        final NoticeEndpoints apiService = RestClient.getRestClient().create(NoticeEndpoints.class);
        Call<Notice> call = apiService.addNote("Bearer " + jwtToken, notice);
        call.enqueue(new Callback<Notice>() {
            @Override
            public void onResponse(Call<Notice> call, Response<Notice> response) {
                if (response.isSuccessful()) {
                    loadOverview();
                } else {
                    Toast.makeText(NoticeDetailActivity.this, "api error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Notice> call, Throwable t) {
                Toast.makeText(NoticeDetailActivity.this, "App Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateItem(Notice notice) {
        final NoticeEndpoints apiService = RestClient.getRestClient().create(NoticeEndpoints.class);
        Call<Notice> call = apiService.updateNote("Bearer " + jwtToken, noticeId, notice);
        call.enqueue(new Callback<Notice>() {
            @Override
            public void onResponse(Call<Notice> call, Response<Notice> response) {
                if (response.isSuccessful()) {
                    loadOverview();
                } else {
                    Toast.makeText(NoticeDetailActivity.this, "api error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Notice> call, Throwable t) {
                Toast.makeText(NoticeDetailActivity.this, "App Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onDelete(View view) {
        if (noticeId > 0) {
            deleteItem();
        } else {
            Toast.makeText(NoticeDetailActivity.this, "You cannot delete empty notice", Toast.LENGTH_LONG).show();
            return;
        }
    }

    private void deleteItem() {
        final NoticeEndpoints apiService = RestClient.getRestClient().create(NoticeEndpoints.class);
        Call<Notice> call = apiService.deleteNote("Bearer " + jwtToken, noticeId);
        call.enqueue(new Callback<Notice>() {
            @Override
            public void onResponse(Call<Notice> call, Response<Notice> response) {
                if (response.isSuccessful()) {
                    loadOverview();
                } else {
                    Toast.makeText(NoticeDetailActivity.this, "api error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Notice> call, Throwable t) {
                Toast.makeText(NoticeDetailActivity.this, "App Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onCancel(View view) {
        loadOverview();
    }

    private void loadOverview() {
        Intent intent = new Intent(NoticeDetailActivity.this, NoticeOverviewActivity.class);
        startActivity(intent);
        finish();
    }
}
