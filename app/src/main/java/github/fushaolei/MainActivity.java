package github.fushaolei;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageAdapter adapter;
    private List<FileEntity> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        initView();
        httpBegin();
    }

    private void httpBegin() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubService service = retrofit.create(GitHubService.class);

        Call<List<FileEntity>> listCall = service.getRepoList();
        listCall.enqueue(new Callback<List<FileEntity>>() {
            @Override
            public void onResponse(Call<List<FileEntity>> call, Response<List<FileEntity>> response) {
                Log.e("=> ", response.code() + "");
                adapter.replaceData(response.body());
            }

            @Override
            public void onFailure(Call<List<FileEntity>> call, Throwable t) {
                Log.e("=> ", "onFailure");
            }
        });
    }

    private void initView() {
        list = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view);
        adapter = new ImageAdapter(R.layout.item_image, list);
        GridLayoutManager glm = new GridLayoutManager(this, 3);

        recyclerView.setLayoutManager(glm);
        recyclerView.setAdapter(adapter);

    }


}