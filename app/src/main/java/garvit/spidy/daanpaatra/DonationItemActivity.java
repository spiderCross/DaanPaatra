package garvit.spidy.daanpaatra;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class DonationItemActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DonationItemPojo don;
    StorageReference storageReference;
    FirebaseStorage firebaseStorage;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_item);
        Button logout= findViewById(R.id.button3);
        Button submit= findViewById(R.id.submit);
        EditText Name = findViewById(R.id.ContactPerson);
        EditText Contact = findViewById(R.id.ContactNumber);
        EditText Description = findViewById(R.id.Description);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("/donations");
        String x=databaseReference.push().getKey();
       firebaseStorage= FirebaseStorage.getInstance();
       storageReference=firebaseStorage.getReference("itemimages");
        don= new DonationItemPojo();
findViewById(R.id.ImageRead).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i,1);
    }
});

submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        don.setName(Name.getEditableText().toString());
        don.setContact(Contact.getEditableText().toString());
        don.setDescription(Description.getEditableText().toString());
        storageReference.child(x).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                don.setImageUrl(task.getResult().getDownloadUrl().toString());
                databaseReference.child(x).setValue(don).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(DonationItemActivity.this, "Successfully added.", Toast.LENGTH_SHORT).show();
                            Name.setText("");
                            Description.setText("");
                            Contact.setText("");
                            ImageView imageView=findViewById(R.id.itemimage);
                            imageView.setImageURI(null);
                        }
                        else
                            Toast.makeText(DonationItemActivity.this, "Problem Occurred", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
});
logout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        firebaseAuth.signOut();
        finish();
    }
});

    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1){
            ImageView imageView=findViewById(R.id.itemimage);
            imageView.setImageURI(data.getData()==null? Uri.parse(""):data.getData());
imageUri= data.getData();
        }
    }
}
