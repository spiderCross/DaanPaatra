package garvit.spidy.daanpaatra;

import android.content.Intent;
import android.location.Address;
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

public class UserLoginActivity extends AppCompatActivity {

    EditText ed_Name, ed_Contact, ed_Address, ed_Email, ed_Password, ed_RePassword;
    TextView tv_Name, tv_Contact, tv_Address, tv_Email;
    Button btn_Confirm_User, btn_Cancel_User, btn_Submit_User;
    LinearLayout layout_UserLogin, layout_UserConfirm;
    UserPojo myUser;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userProfileReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        userProfileReference=firebaseDatabase.getReference("user_profile");
        myUser= new UserPojo();
        ed_Name = findViewById(R.id.ed_Name_User);
        ed_Contact = findViewById(R.id.ed_PhoneNumber_User);
        ed_Address = findViewById(R.id.ed_Address_User);
        ed_Email = findViewById(R.id.ed_Email_User);
        ed_Password = findViewById(R.id.ed_Password_User);
        ed_RePassword = findViewById(R.id.ed_Password_Re_User);

        tv_Name = findViewById(R.id.tv_Name_User);
        tv_Contact = findViewById(R.id.tv_Number_User);
        tv_Address = findViewById(R.id.tv_Address_User);
        tv_Email = findViewById(R.id.tv_Email_User);

        btn_Submit_User = findViewById(R.id.btn_Submit_User);
        btn_Confirm_User = findViewById(R.id.btn_Confirm_User);
        btn_Cancel_User = findViewById(R.id.btn_Cancel_User);

        layout_UserLogin = findViewById(R.id.layout_UserLogin);
        layout_UserConfirm = findViewById(R.id.layout_User_Confirm);

        btn_Submit_User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (UserLoginActivity.this.isNotNull(ed_RePassword) &&
                        UserLoginActivity.this.isNotNull(ed_Password) &&
                        UserLoginActivity.this.isNotNull(ed_Name) &&
                        UserLoginActivity.this.isNotNull(ed_Email) &&
                        UserLoginActivity.this.isNotNull(ed_Contact) &&
                        UserLoginActivity.this.isNotNull(ed_Address) &&
                        ed_Password.getText().toString().trim()
                                .equals(ed_RePassword.getText().toString().trim())) {

                    myUser.setName(ed_Name.getEditableText().toString());
                    myUser.setAddress(ed_Address.getEditableText().toString());
                    myUser.setEmail(ed_Email.getEditableText().toString());
                    myUser.setContact(Long.parseLong(ed_Contact.getText().toString()));
                    myUser.setType("D");
                    tv_Name.setText(ed_Name.getText());
                    tv_Contact.setText(ed_Contact.getText());
                    tv_Address.setText(ed_Address.getText());
                    tv_Email.setText(ed_Email.getText());

                    layout_UserLogin.setVisibility(View.GONE);
                    layout_UserConfirm.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_Confirm_User.setOnClickListener(view -> {
            firebaseAuth.createUserWithEmailAndPassword(ed_Email.getEditableText().toString()
                    ,ed_Password.getEditableText().toString()).addOnSuccessListener((authResult)->{
                userProfileReference.child(firebaseAuth.getCurrentUser().getUid()).runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData mutableData) {
                        if(mutableData.getValue(UserPojo.class)==null){
                            mutableData.setValue(myUser);
                            return Transaction.success(mutableData);
                        }else{
                        mutableData.setValue(myUser);
                        return Transaction.success(mutableData);
                    }}

                    @Override
                    public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

                        if(databaseError==null) {
                         Toast.makeText(UserLoginActivity.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(UserLoginActivity.this,MainActivity.class);
                            (UserLoginActivity.this).finish();
                            startActivity(i);
                        }else
                            Toast.makeText(UserLoginActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            });


        });

        btn_Cancel_User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_UserConfirm.setVisibility(View.GONE);
                layout_UserLogin.setVisibility(View.VISIBLE);
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
