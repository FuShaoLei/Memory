package github.fushaolei;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.material.appbar.MaterialToolbar;

public class SettingActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;
    private EditText user, repo, token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        toolbar = findViewById(R.id.tool_bar);
        user = findViewById(R.id.et_user);
        repo = findViewById(R.id.et_repo);
        token = findViewById(R.id.et_token);
        toolbar.setNavigationOnClickListener((v) -> {
            finish();
        });
        toolbar.setOnMenuItemClickListener((v) -> {
            switch (v.getItemId()) {
                case R.id.setting_save:
                    if (isLegal(user, repo, token)) {
                        Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();
                        MMKVHelper.saveUser(new User(user.getText().toString(), repo.getText().toString(), token.getText().toString()));
                        finish();
                    } else {
                        Toast.makeText(this, "请填写！", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            return true;
        });

    }

    public boolean isLegal(EditText... editTexts) {
        for (EditText e : editTexts) {
            if (e == null || e.getText().toString() == null || e.getText().toString().length() == 0) {
                return false;
            }
        }
        return true;
    }
}