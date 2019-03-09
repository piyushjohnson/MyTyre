package piyushjohnson.mytyre;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.button.MaterialButton;

import androidx.appcompat.app.AppCompatActivity;

public class ScrollingActivity extends AppCompatActivity implements View.OnClickListener, VehicleTypesGridDialogFragment.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        MaterialButton tyreByVehicleBtn = findViewById(R.id.tyreByVehicleBtn);
        MaterialButton tyreBySizeBtn = findViewById(R.id.tyreBySizeBtn);

        tyreBySizeBtn.setOnClickListener(this);
        tyreBySizeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tyreBySizeBtn:
                break;
            case R.id.tyreByVehicleBtn:
                VehicleTypesGridDialogFragment vehicleTypesGridDialogFragment = VehicleTypesGridDialogFragment.newInstance(4);
                vehicleTypesGridDialogFragment.show(getSupportFragmentManager(),vehicleTypesGridDialogFragment.getTag());
                break;
        }
    }

    @Override
    public void onItemClicked(int position) {

    }
}
