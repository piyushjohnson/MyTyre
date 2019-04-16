package piyushjohnson.mytyre.ui.contact;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import piyushjohnson.mytyre.R;

public class ContactActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "ContactActivity";

    private ListView contactList;
    private List<String> urls;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        contactList = findViewById(R.id.contact_list);

        urls = new ArrayList<>();
        populateUrls();
        contactList.setOnItemClickListener(this);
    }

    private void populateUrls() {
        urls.add("https://piyushjohnson.github.io/MyTyre/team_members.html");
        urls.add("https://piyushjohnson.github.io/MyTyre/user_license.html");
        urls.add("https://piyushjohnson.github.io/MyTyre/privacy_policy.html");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putString("loadUrl",urls.get(position));
    }
}
