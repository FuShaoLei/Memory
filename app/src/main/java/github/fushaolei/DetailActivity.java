package github.fushaolei;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;

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
        Log.e("=>", "https://cdn.jsdelivr.net/gh/fushaolei/img2/" + entity.getPath());

        Glide.with(this)
                .load("https://cdn.jsdelivr.net/gh/fushaolei/img2/" + entity.getPath())
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