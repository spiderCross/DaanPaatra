package garvit.spidy.daanpaatra;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChoiceActivity extends AppCompatActivity {
    Button btn_User, btn_Ngo;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);


        btn_Ngo = findViewById(R.id.btn_NGO);
        btn_User = findViewById(R.id.btn_User);

        btn_Ngo.setOnClickListener(view -> {
            intent = new Intent(ChoiceActivity.this, NgoLoginActivity.class);
            startActivity(intent);
        });

        btn_User.setOnClickListener(view -> {
            intent = new Intent(ChoiceActivity.this, UserLoginActivity.class);
            startActivity(intent);
        });
    }
}
