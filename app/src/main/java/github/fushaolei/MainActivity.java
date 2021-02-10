package github.fushaolei;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

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

    private MaterialToolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        init();
        initView();
        httpBegin();
    }

    private void init() {
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.tool_bar);
        navigationView = findViewById(R.id.navigation_view);

        toolbar.setNavigationOnClickListener((v) -> {
            drawerLayout.open();
        });
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

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                list = adapter.getData();
                intent.putExtra("entity", list.get(position));
                startActivity(intent);
            }
        });
    }


}