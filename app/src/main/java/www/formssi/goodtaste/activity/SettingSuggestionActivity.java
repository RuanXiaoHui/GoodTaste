package www.formssi.goodtaste.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import www.formssi.goodtaste.R;

public class SettingSuggestionActivity extends AppCompatActivity {

    private TextView tvTitle;
    private ImageView ivReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_suggestion);
        tvTitle = (TextView) findViewById(R.id.tv_backTitleBar_title);
        tvTitle.setText("意见反馈");
        ivReturn = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        ivReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
