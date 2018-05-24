package garvit.spidy.daanpaatra;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    RadioGroup rg_RadioGroup;
    RadioButton rb_Ngo, rb_User;
    EditText ed_Email, ed_Password;
    Button btn_login;
    TextView register;
    Intent intent;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String userName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();
        ed_Email = findViewById(R.id.ed_loginEmail);
        ed_Password = findViewById(R.id.ed_loginPassword);
        btn_login = findViewById(R.id.btn_login);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            databaseReference=firebaseDatabase.getReference("user_profile/"+firebaseAuth.getCurrentUser().getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String type=(String)dataSnapshot.child("type").getValue();
                    Log.v("dsd",type + "  "+String.valueOf(type.equals("N")));
                    if(type.equals("N")){
                        Toast.makeText(MainActivity.this, "Welcome " + userName, Toast.LENGTH_LONG).show();
                        intent = new Intent(MainActivity.this, DataViewActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(MainActivity.this, "Welcome " + userName, Toast.LENGTH_LONG).show();
                        intent = new Intent(MainActivity.this, DonationItemActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
        btn_login.setOnClickListener(view -> {
            String email, password;

            email = ed_Email.getText().toString();
            password = ed_Password.getText().toString();

            if (isNotNull(ed_Email) &&
                    isNotNull(ed_Password)) {


                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener((authResultTask) -> {
                    if (authResultTask.isSuccessful()) {
                        databaseReference=firebaseDatabase.getReference("user_profile/"+firebaseAuth.getCurrentUser().getUid());
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String type=(String)dataSnapshot.child("type").getValue();
                                Log.v("dsd",type + "  "+String.valueOf(type.equals("N")));
                                if(type.equals("N")){
                                    Toast.makeText(MainActivity.this, "Welcome " + userName, Toast.LENGTH_LONG).show();
                                    intent = new Intent(MainActivity.this, DataViewActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(MainActivity.this, "Welcome " + userName, Toast.LENGTH_LONG).show();
                                    intent = new Intent(MainActivity.this, DonationItemActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    } else {

                        Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, authResultTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });
            }
        });

        register = findViewById(R.id.Register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, ChoiceActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean isNotNull(EditText ed) {
        if (TextUtils.isEmpty(ed.getText().toString())) {
            ed.setError("Please fill the field");
            return false;
        }
        return true;

    }
}
