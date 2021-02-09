package github.fushaolei;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageView;
    private FileEntity entity;
    private TextView name;
    private ImageView back;
    private ImageView delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        if (intent != null) {
            entity = (FileEntity) intent.getSerializableExtra("entity");
        }

        getSupportActionBar().hide();
        imageView = findViewById(R.id.iv_detail);
        name = findViewById(R.id.tv_name);
        back = findViewById(R.id.iv_back);
        delete = findViewById(R.id.iv_delete);

        name.setText(entity.getName());

        Glide.with(this)
                .load("https://cdn.jsdelivr.net/gh/fushaolei/img2/" + entity.getPath())
                .into(imageView);

        back.setOnClickListener(this);
        delete.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_delete:
                Toast.makeText(this, "sha:" + entity.getSha(), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}