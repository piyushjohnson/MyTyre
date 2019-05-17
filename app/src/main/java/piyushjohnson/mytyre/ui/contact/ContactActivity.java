package piyushjohnson.mytyre.ui.contact;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

import piyushjohnson.mytyre.R;

public class ContactActivity extends AppCompatActivity {

    private static final String TAG = "ContactActivity";

    private NavController navController;
    private List<String> shareUrls;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        shareUrls = new ArrayList<>();
        populateShareUrls();
        navController = Navigation.findNavController(this, R.id.contact_nav_host_fragment);
    }

    private void populateShareUrls() {
        shareUrls.add("https://twitter.com/home?status=https%3A//www.justdial.com/Jaipur/The-Tyre-Planet-Radhagovind-Nagar-Pratap-Nagar/0141PX141-X141-181214123448-N1B5_BZDET");
        shareUrls.add("https://www.facebook.com/sharer/sharer.php?u=https%3A//www.justdial.com/Jaipur/The-Tyre-Planet-Radhagovind-Nagar-Pratap-Nagar/0141PX141-X141-181214123448-N1B5_BZDET");
        shareUrls.add("https://twitter.com/home?status=https%3A//www.justdial.com/Jaipur/The-Tyre-Planet-Radhagovind-Nagar-Pratap-Nagar/0141PX141-X141-181214123448-N1B5_BZDET");
    }


    public void onShare(View view) {
        startActivity(createShareIntent(Integer.parseInt(view.getTag().toString())));
    }

    public Intent createShareIntent(int pos) {
        Intent shareIntent = new Intent(Intent.ACTION_VIEW);
        shareIntent.setData(Uri.parse(shareUrls.get(pos)));
        return shareIntent;
    }
}
