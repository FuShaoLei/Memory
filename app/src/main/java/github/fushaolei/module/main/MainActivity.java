package github.fushaolei.module.main;

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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import github.fushaolei.base.BaseActivity;
import github.fushaolei.module.SettingActivity;
import github.fushaolei.module.detail.DetailActivity;
import github.fushaolei.service.GitHubService;
import github.fushaolei.adapter.ImageAdapter;
import github.fushaolei.utils.GlideHelper;
import github.fushaolei.utils.MMKVHelper;
import github.fushaolei.R;
import github.fushaolei.utils.ProgressDialogHelper;
import github.fushaolei.utils.ToastHelper;
import github.fushaolei.utils.UploadHelper;
import github.fushaolei.entity.BaseResponse;
import github.fushaolei.entity.CreateBody;
import github.fushaolei.entity.FileEntity;
import github.fushaolei.entity.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/9
 * @desc: 主页
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    private RecyclerView recyclerView;
    private MaterialToolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private List<FileEntity> list;
    private ImageAdapter adapter;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //请求状态码
    private static int REQUEST_PERMISSION_CODE = 1;
    private int SELECT_PHOTO = 100;

    @Override
    protected void initialize() {
        recyclerView = findViewById(R.id.recycler_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.tool_bar);
        navigationView = findViewById(R.id.navigation_view);

        list = new ArrayList<>();
        adapter = new ImageAdapter(R.layout.item_image, list);

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
                        rootPresenter.getRepoList();
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

        GridLayoutManager glm = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(glm);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            list = adapter.getData();
            intent.putExtra("entity", list.get(position));
            startActivity(intent);
        });
    }

    @Override
    protected void initView() {
        permissionChecking();
        if (MMKVHelper.isUser()) {
            rootPresenter.getRepoList();
        }
    }

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
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
            showUploadDialog(uri);
        }
    }

    private void showUploadDialog(Uri uri) {
        View view = LayoutInflater.from(this).inflate(R.layout.alert_image, null);
        ImageView imgView = view.findViewById(R.id.alert_img);
        GlideHelper.loadImageByUri(uri, imgView);
        new MaterialAlertDialogBuilder(this)
                .setTitle("确定上传吗？")
                .setView(view)
                .setPositiveButton("确定", (dialog, which) -> {
                    rootPresenter.uploadFileThread(UploadHelper.getImageAbsolutePath(this, uri));
                })
                .setNegativeButton("取消", null)
                .show();
    }

    @Override
    public void updateRecycler(List<FileEntity> entityList) {
        adapter.replaceData(entityList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
        ProgressDialogHelper.showProgressDialog(this, "上传中...");
    }

    @Override
    public void hideLoading() {
        ProgressDialogHelper.hideProgressDialog();
    }

    @Override
    public void uploadSuccess() {
        ToastHelper.show("上传成功！");
    }

    @Override
    public void uploadFail() {
        ToastHelper.show("上传失败...");
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
}