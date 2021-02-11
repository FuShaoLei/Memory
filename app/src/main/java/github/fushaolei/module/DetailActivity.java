package github.fushaolei.module;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;

import github.fushaolei.utils.MMKVHelper;
import github.fushaolei.R;
import github.fushaolei.entity.FileEntity;
import github.fushaolei.entity.User;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/9
 * @desc: 图片详情页
 */
public class DetailActivity extends AppCompatActivity {
    private ImageView imageView;
    private FileEntity entity;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        if (intent != null) {
            entity = intent.getParcelableExtra("entity");
        }

//        getSupportActionBar().hide();

        imageView = findViewById(R.id.iv_detail);

        User user = MMKVHelper.getUser();
        Glide.with(this)
                .load("https://cdn.jsdelivr.net/gh/" + user.getName() + "/" + user.getRepo() + "/" + entity.getPath())
                .into(imageView);


        toolbar = findViewById(R.id.tool_bar);

        toolbar.setNavigationOnClickListener((v) -> finish());
//        toolbar.setTitle(entity.getName());
        toolbar.setOnMenuItemClickListener((v) -> {
            switch (v.getItemId()) {
                case R.id.detail_delete:
                    Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.detail_share:
                    Toast.makeText(this, "share", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.detail_edit:
                    Toast.makeText(this, "edit", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        });

    }

}