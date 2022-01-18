package github.fushaolei.module.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import github.fushaolei.base.BaseActivity;
import github.fushaolei.constant.GlobalConstant;
import github.fushaolei.module.setting.SettingActivity;
import github.fushaolei.module.WebViewActivity;
import github.fushaolei.module.detail.DetailActivity;
import github.fushaolei.adapter.ImageAdapter;
import github.fushaolei.utils.GlideHelper;
import github.fushaolei.utils.MMKVHelper;
import github.fushaolei.R;
import github.fushaolei.utils.ProgressDialogHelper;
import github.fushaolei.utils.ToastHelper;
import github.fushaolei.utils.UploadHelper;
import github.fushaolei.entity.FileEntity;
import github.fushaolei.utils.ExtsKt;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/9
 * @desc: 主页
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    private SwipeRefreshLayout refreshLayout;
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
    private int ENTER_DETAIL = 101;

    private int RESULT_IMG = 233;

    @Override
    protected void initialize() {
        refreshLayout = findViewById(R.id.swipe_refresh);
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
            }
            return true;
        });
        navigationView.setNavigationItemSelectedListener((v) -> {
            drawerLayout.close();
            switch (v.getItemId()) {
                case R.id.setting:
                    startActivity(new Intent(MainActivity.this, SettingActivity.class));
                    break;
                case R.id.readme:
                    Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                    intent.putExtra("path", GlobalConstant.INSTANCE.README_URL);
                    startActivity(intent);
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
            startActivityForResult(intent, ENTER_DETAIL);
        });
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorBlack));
        refreshLayout.setOnRefreshListener(() -> {
            rootPresenter.getRepoList();
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
        } else if (requestCode == ENTER_DETAIL && resultCode == RESULT_IMG) {
            ExtsKt.Logg("有照片被删除了....");
            rootPresenter.getRepoList();
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
        rootPresenter.getRepoList();
    }

    @Override
    public void uploadFail() {
        ToastHelper.show("上传失败...");
    }

    @Override
    public void showRefresh() {
        if (refreshLayout != null && !refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideRefresh() {
        if (refreshLayout != null && refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
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