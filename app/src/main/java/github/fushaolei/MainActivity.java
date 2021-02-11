package github.fushaolei;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //请求状态码
    private static int REQUEST_PERMISSION_CODE = 1;
    private int SELECT_PHOTO = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().hide();
        Log.e("=>", "MainActivity onCreate");
        init();
        initView();
        if (MMKVHelper.isUser()) {
            httpBegin();
        }
        permissionChecking();
    }

    private void permissionChecking() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                Log.e("MainActivity", "申请的权限为：" + permissions[i] + ",申请结果：" + grantResults[i]);
            }
        }

    }

    private void init() {
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.tool_bar);
        navigationView = findViewById(R.id.navigation_view);

        toolbar.setNavigationOnClickListener((v) -> {
            drawerLayout.open();
        });
        toolbar.setOnMenuItemClickListener((v) -> {
            switch (v.getItemId()) {
                case R.id.album_upload:
                    selectImg();
                    break;
                case R.id.update:
                    if (MMKVHelper.isUser()) {
                        httpBegin();
                    } else {
                        Toast.makeText(this, "未配置！", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            return true;
        });
        navigationView.setNavigationItemSelectedListener((v) -> {
            drawerLayout.close();
            switch (v.getItemId()) {
                case R.id.setting:
                    startActivity(new Intent(MainActivity.this, SettingActivity.class));
                    break;
            }
            return true;
        });
    }

    private void selectImg() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            String absolutePath = UploadHelper.getImageAbsolutePath(this, uri);
            Log.e("getImageAbsolutePath => ", absolutePath);
            uploadBegin(absolutePath);
        }
    }


    private void uploadBegin(String filePath) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubService service = retrofit.create(GitHubService.class);
        User user = MMKVHelper.getUser();
        Log.e("=>", user.toString());

        CreateBody body = new CreateBody("android api test", UploadHelper.encodeImageToBase64(filePath));
        Log.e("=> ", body.toString());
        String fileName = UploadHelper.getFileName(filePath);
        Log.e("fileName => ", fileName);
        Call<BaseResponse> call
                = service.insertRepo("token " + user.getToken(),
                user.getName(),
                user.getRepo(),
                fileName,
                body);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                int code = response.code();
                Toast.makeText(MainActivity.this, code + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void httpBegin() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubService service = retrofit.create(GitHubService.class);

        User user = MMKVHelper.getUser();
        Log.e("=>", user.toString());
        Call<List<FileEntity>> listCall = service.getRepoList(user.getName(), user.getRepo());
        listCall.enqueue(new Callback<List<FileEntity>>() {
            @Override
            public void onResponse(Call<List<FileEntity>> call, Response<List<FileEntity>> response) {
                Log.e("=> ", response.code() + "");
                adapter.replaceData(response.body());
                adapter.notifyDataSetChanged();
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