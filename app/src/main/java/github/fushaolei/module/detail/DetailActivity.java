package github.fushaolei.module.detail;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import github.fushaolei.base.BaseActivity;
import github.fushaolei.constant.GlobalConstant;
import github.fushaolei.entity.User;
import github.fushaolei.utils.GlideHelper;
import github.fushaolei.R;
import github.fushaolei.entity.FileEntity;
import github.fushaolei.utils.MMKVHelper;
import github.fushaolei.utils.ProgressDialogHelper;
import github.fushaolei.utils.ToastHelper;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/9
 * @desc: 图片详情页
 */
public class DetailActivity extends BaseActivity<DetailPresenter> implements DetailContract.View {
    private ImageView imageView;
    private FileEntity entity;
    private MaterialToolbar toolbar;

    @Override
    protected void initialize() {
        imageView = findViewById(R.id.iv_detail);
        Intent intent = getIntent();
        if (intent != null) {
            entity = intent.getParcelableExtra("entity");
        }
        toolbar = findViewById(R.id.tool_bar);

        toolbar.setNavigationOnClickListener((v) -> finish());
        toolbar.setOnMenuItemClickListener((v) -> {
            switch (v.getItemId()) {
                case R.id.detail_delete:
                    showDialog();
                    break;
                case R.id.detail_copy:
                    copy();
                    break;
                case R.id.detail_edit:
                    Toast.makeText(this, "edit", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        });
    }

    private void copy() {
        User user = MMKVHelper.getUser();
        //获取剪贴板管理器
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型
        ClipData mClipData = ClipData.newPlainText("Label", GlobalConstant.INSTANCE.JSDELIVR_PREFIX + user.getName() + "/" + user.getRepo() + "/" + entity.getPath());
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        ToastHelper.show("已复制链接到剪切板");
    }

    @Override
    protected void initView() {
        GlideHelper.loadImageCDN(entity.getPath(), imageView);
    }

    @Override
    protected DetailPresenter initPresenter() {
        return new DetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    private void showDialog() {
        new MaterialAlertDialogBuilder(this)
                .setMessage("确定删除吗？")
                .setPositiveButton("确定", (dialog, which) -> {
                    rootPresenter.delete(entity.getPath(), entity.getSha());

                })
                .setNegativeButton("取消", null)
                .show();

    }

    @Override
    public void showLoading() {
        ProgressDialogHelper.showProgressDialog(this, "删除中...");
    }

    @Override
    public void hideLoading() {
        ProgressDialogHelper.hideProgressDialog();
    }

    @Override
    public void deleteSuccess() {
        ToastHelper.show("删除成功！");
        DetailActivity.this.setResult(233);
        finish();
    }

    @Override
    public void deleteFail() {
        ToastHelper.show("删除失败...");
    }
}