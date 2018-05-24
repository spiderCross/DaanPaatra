package garvit.spidy.daanpaatra;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

public class NgoLoginActivity extends AppCompatActivity {

    EditText ed_Name, ed_Regd, ed_Contact, ed_Address, ed_Email, ed_Password, ed_RePassword;
    TextView tv_Name, tv_Regd, tv_Contact, tv_Address, tv_Email;
    Button btn_Confirm, btn_Submit, btn_Cancel;
    LinearLayout layout_NgoLogin, layout_NgoConfirm;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ngoProfileReference;
    NGO_Pojo myNgo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_login);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        ngoProfileReference = firebaseDatabase.getReference("user_profile");


        ed_Name = findViewById(R.id.ed_Name_NGO);
        ed_Regd = findViewById(R.id.ed_RegdNumber_NGO);
        ed_Contact = findViewById(R.id.ed_Number_NGO);
        ed_Address = findViewById(R.id.ed_Address_NGO);
        ed_Email = findViewById(R.id.ed_Email_NGO);
        ed_Password = findViewById(R.id.ed_Password_NGO);
        ed_RePassword = findViewById(R.id.ed_Password_Re_NGO);

        tv_Name = findViewById(R.id.tv_Name_NGO);
        tv_Address = findViewById(R.id.tv_Address_NGO);
        tv_Regd = findViewById(R.id.tv_RegdNumber_NGO);
        tv_Contact = findViewById(R.id.tv_Number_NGO);
        tv_Email = findViewById(R.id.tv_Email_NGO);

        btn_Submit = findViewById(R.id.btn_Submit_NGO);
        btn_Confirm = findViewById(R.id.btn_Confirm_Ngo);
        btn_Cancel = findViewById(R.id.btn_Cancel_Ngo);

        myNgo = new NGO_Pojo();

        layout_NgoLogin = findViewById(R.id.layout_NgoLogin);
        layout_NgoConfirm = findViewById(R.id.layout_Ngo_Confirm);

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isNotNull(ed_Name) &&
                        isNotNull(ed_Regd) &&
                        isNotNull(ed_Contact) &&
                        isNotNull(ed_Address) &&
                        isNotNull(ed_Email) &&
                        isNotNull(ed_Password) &&
                        isNotNull(ed_RePassword) &&
                        ed_Password.getText().toString().trim()
                                .equals(ed_RePassword.getText().toString().trim())) {


                    myNgo.setName(ed_Name.getEditableText().toString());
                    myNgo.setAddress(ed_Address.getEditableText().toString());
                    myNgo.setContact(Long.parseLong(ed_Contact.getEditableText().toString()));
                    myNgo.setEmail(ed_Email.getEditableText().toString());
                    myNgo.setRegdId(ed_Regd.getEditableText().toString());
                    myNgo.setType("N");
                    tv_Name.setText(ed_Name.getText());
                    tv_Contact.setText(ed_Contact.getText());
                    tv_Regd.setText(ed_Regd.getText());
                    tv_Address.setText(ed_Address.getText());
                    tv_Email.setText(ed_Email.getText());

                    layout_NgoLogin.setVisibility(View.GONE);
                    layout_NgoConfirm.setVisibility(View.VISIBLE);

                }
            }
        });


        btn_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.createUserWithEmailAndPassword(ed_Email.getEditableText().toString()
                        , ed_Password.getEditableText().toString()).addOnSuccessListener((authResult) -> {
                    ngoProfileReference.child(firebaseAuth.getCurrentUser().getUid()).runTransaction(new Transaction.Handler() {
                        @Override
                        public Transaction.Result doTransaction(MutableData mutableData) {
                            if (mutableData.getValue(NGO_Pojo.class) == null) {
                                mutableData.setValue(myNgo);
                                return Transaction.success(mutableData);
                            } else {
                                mutableData.setValue(myNgo);
                                return Transaction.success(mutableData);
                            }
                        }

                        @Override
                        public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

                            if (databaseError == null) {
                                Toast.makeText(NgoLoginActivity.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(NgoLoginActivity.this, MainActivity.class);
                                (NgoLoginActivity.this).finish();
                                startActivity(i);
                            } else
                                Toast.makeText(NgoLoginActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                });

            }
        });


        btn_Cancel.setOnClickListener(view1 ->

        {
            layout_NgoConfirm.setVisibility(View.GONE);
            layout_NgoLogin.setVisibility(View.VISIBLE);
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
