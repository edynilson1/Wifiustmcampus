package mz.ustm.wificampusscanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText edtNetwork;

    private final ActivityResultLauncher<String[]> permissionLauncher =
        registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
            boolean fine = Boolean.TRUE.equals(result.get(Manifest.permission.ACCESS_FINE_LOCATION));
            boolean nearby = android.os.Build.VERSION.SDK_INT < 33 ||
                    Boolean.TRUE.equals(result.get(Manifest.permission.NEARBY_WIFI_DEVICES));
            if (fine || nearby) openResults();
            else Toast.makeText(this, "É necessário permitir o acesso às redes Wi-Fi.", Toast.LENGTH_LONG).show();
        });

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtNetwork = findViewById(R.id.edtNetwork);
        Button btn = findViewById(R.id.btnSearch);

        btn.setOnClickListener(v -> requestWifiPermissions());
    }

    private void requestWifiPermissions() {
        if (android.os.Build.VERSION.SDK_INT >= 33) {
            permissionLauncher.launch(new String[] {
                Manifest.permission.NEARBY_WIFI_DEVICES,
                Manifest.permission.ACCESS_FINE_LOCATION
            });
        } else {
            permissionLauncher.launch(new String[] { Manifest.permission.ACCESS_FINE_LOCATION });
        }
    }

    private void openResults() {
        String filter = edtNetwork.getText() == null ? "" : edtNetwork.getText().toString().trim();
        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra("FILTER", filter);
        startActivity(intent);
    }
}
