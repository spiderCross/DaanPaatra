package garvit.spidy.daanpaatra;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class DataViewActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ArrayList<DonationItemPojo> list= new ArrayList<>();
    int[] images = {R.drawable.kcdasb, R.drawable.sports, R.drawable.donate};
    String[] name = {"Garvit Srivastava","Gaurav Bansal","Ashansa"};
    String[] donatedItemList = {"Winter Clothes", "Sports Equipment", "Regular Usage"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_view);
        ListView listView = findViewById(R.id.lv_donatedItemList);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
firebaseDatabase=FirebaseDatabase.getInstance();
databaseReference=firebaseDatabase.getReference("/donations");
databaseReference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            DonationItemPojo don = ds.getValue(DonationItemPojo.class);
            list.add(don);
        }
    customAdapter.notifyDataSetInvalidated();
    }
    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});

        /*TextView userName= findViewById(R.id.tv_DonarName);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("/user_profile");
        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserPojo user=dataSnapshot.getValue(UserPojo.class);
            userName.setText(user.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent =  new Intent(DataViewActivity.this,DonatedItemDescription.class);
                intent.putExtra("don", (Serializable) list.get(i));
                startActivity(intent);
            }
        });

    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = getLayoutInflater().inflate(R.layout.donated_item_list, null, true);

            ImageView imageView = view.findViewById(R.id.img_donatedItems);
            TextView tv = view.findViewById(R.id.tv_DonarName);
            TextView tv1 = view.findViewById(R.id.tv_DonatedItemList);
            Glide.with(DataViewActivity.this).load(list.get(i).getImageUrl()).apply(RequestOptions.overrideOf(250,250)).into(imageView);
            tv.setText(list.get(i).getName());
            tv1.setText(list.get(i).getDescription());

            return view;
        }
    }
}
