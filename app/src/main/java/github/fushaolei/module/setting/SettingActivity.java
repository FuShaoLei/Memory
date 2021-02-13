package github.fushaolei.module.setting;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import github.fushaolei.base.BaseActivity;
import github.fushaolei.R;
import github.fushaolei.entity.User;
import github.fushaolei.utils.MMKVHelper;
import github.fushaolei.utils.ToastHelper;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/9
 * @desc: 设置
 */
public class SettingActivity extends BaseActivity<SettingPresenter> implements SettingContract.View {
    private MaterialToolbar toolbar;
    private TextInputEditText user_name, repo, token;

    @Override
    protected void initialize() {
        toolbar = findViewById(R.id.tool_bar);
        user_name = findViewById(R.id.et_user);
        repo = findViewById(R.id.et_repo);
        token = findViewById(R.id.et_token);

        toolbar.setNavigationOnClickListener((v) -> {
            finish();
        });

        toolbar.setOnMenuItemClickListener((v) -> {
            switch (v.getItemId()) {
                case R.id.setting_save:
                    if (isLegal(user_name, repo, token)) {
                        String sUser = user_name.getText().toString();
                        String sRepo = repo.getText().toString();
                        String sToken = token.getText().toString();
                        rootPresenter.saveUser(new User(sUser, sRepo, sToken));
                    }
                    break;
            }
            return true;
        });
    }

    @Override
    protected void initView() {
        if (MMKVHelper.isUser()) {
            rootPresenter.init();
        }
    }

    @Override
    protected SettingPresenter initPresenter() {
        return new SettingPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void updateView(User user) {
        user_name.setText(user.getName());
        repo.setText(user.getRepo());
        token.setText(user.getToken());
    }

    @Override
    public void saveSuccess() {
        ToastHelper.show("保存成功！");
        finish();
    }

    @Override
    public void saveFail() {
        ToastHelper.show("保存失败...");
    }

    private boolean isLegal(TextInputEditText... editTexts) {
        for (TextInputEditText e : editTexts) {
            if (e == null || e.getText().toString() == null || e.getText().toString().length() == 0) {
                return false;
            }
        }
        return true;
    }
}
