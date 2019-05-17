package piyushjohnson.mytyre.ui.contact;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.List;

import piyushjohnson.mytyre.R;

public class ContactHomeFragment extends Fragment implements AdapterView.OnItemClickListener {


    private ListView contactList;
    private List<String> urls;

    public ContactHomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contactList = view.findViewById(R.id.contact_list);

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
        bundle.putString("loadUrl", urls.get(position));
        NavHostFragment.findNavController(this).navigate(R.id.ContactLinkView, bundle);
    }
}
