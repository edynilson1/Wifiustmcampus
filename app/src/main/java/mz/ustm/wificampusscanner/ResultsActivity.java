package mz.ustm.wificampusscanner;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class ResultsActivity extends AppCompatActivity {

    private WifiManager wifiManager;
    private WifiAdapter adapter;

    private final List<WifiNetwork> networks = new ArrayList<>();

    private String filter = "";
    private TextView summary;

    private boolean receiverRegistered = false;

    // Pedido de permissões
    private final ActivityResultLauncher<String[]> permissionLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.RequestMultiplePermissions(),
                    result -> {

                        boolean fineLocation =
                                Boolean.TRUE.equals(
                                        result.get(Manifest.permission.ACCESS_FINE_LOCATION)
                                );

                        boolean nearbyWifi = true;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            nearbyWifi =
                                    Boolean.TRUE.equals(
                                            result.get(Manifest.permission.NEARBY_WIFI_DEVICES)
                                    );
                        }

                        if (fineLocation && nearbyWifi) {
                            startScan();
                        } else {
                            summary.setText(
                                    "Permissões necessárias para pesquisar redes Wi-Fi."
                            );

                            Toast.makeText(
                                    this,
                                    "Permita Localização e Dispositivos próximos.",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
            );

    // Recebe o resultado da pesquisa Wi-Fi
    private final BroadcastReceiver wifiScanReceiver =
            new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {

                    if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(
                            intent.getAction())) {

                        boolean success =
                                intent.getBooleanExtra(
                                        WifiManager.EXTRA_RESULTS_UPDATED,
                                        false
                                );

                        showResults(success);
                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_results);

        // Receber o filtro enviado pela MainActivity
        filter = getIntent().getStringExtra("FILTER");

        if (filter == null) {
            filter = "";
        }

        filter = filter.trim();

        // Componentes da tela
        summary = findViewById(R.id.txtSummary);

        RecyclerView recycler =
                findViewById(R.id.recyclerWifi);

        recycler.setLayoutManager(
                new LinearLayoutManager(this)
        );

        adapter = new WifiAdapter(networks);

        recycler.setAdapter(adapter);

        // Wi-Fi Manager
        wifiManager =
                (WifiManager) getApplicationContext()
                        .getSystemService(Context.WIFI_SERVICE);

        // Registrar receptor
        registerWifiReceiver();

        // Começar pesquisa
        checkPermissionsAndStart();
    }

    /**
     * Registra o BroadcastReceiver para receber
     * os resultados da pesquisa Wi-Fi.
     */
    private void registerWifiReceiver() {

        if (receiverRegistered) {
            return;
        }

        IntentFilter intentFilter =
                new IntentFilter(
                        WifiManager.SCAN_RESULTS_AVAILABLE_ACTION
                );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            registerReceiver(
                    wifiScanReceiver,
                    intentFilter,
                    Context.RECEIVER_NOT_EXPORTED
            );

        } else {

            registerReceiver(
                    wifiScanReceiver,
                    intentFilter
            );
        }

        receiverRegistered = true;
    }

    /**
     * Verifica as permissões necessárias.
     */
    private void checkPermissionsAndStart() {

        List<String> permissions =
                new ArrayList<>();

        // Para pesquisa de redes Wi-Fi,
        // localização precisa é necessária.
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {

            permissions.add(
                    Manifest.permission.ACCESS_FINE_LOCATION
            );
        }

        // Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.NEARBY_WIFI_DEVICES
            ) != PackageManager.PERMISSION_GRANTED) {

                permissions.add(
                        Manifest.permission.NEARBY_WIFI_DEVICES
                );
            }
        }

        if (!permissions.isEmpty()) {

            permissionLauncher.launch(
                    permissions.toArray(
                            new String[0]
                    )
            );

        } else {

            startScan();
        }
    }

    /**
     * Inicia a pesquisa das redes Wi-Fi.
     */
    private void startScan() {

        if (wifiManager == null) {

            summary.setText(
                    "Wi-Fi não está disponível neste dispositivo."
            );

            return;
        }

        // Verificar se o Wi-Fi está ligado
        if (!wifiManager.isWifiEnabled()) {

            summary.setText(
                    "Ative o Wi-Fi e tente novamente."
            );

            Toast.makeText(
                    this,
                    "Ative o Wi-Fi.",
                    Toast.LENGTH_LONG
            ).show();

            return;
        }

        // Verificar permissões
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {

            checkPermissionsAndStart();
            return;
        }

        // Verificar se a localização do aparelho está ligada
        if (!isLocationEnabled()) {

            summary.setText(
                    "Ative a localização do telefone."
            );

            Toast.makeText(
                    this,
                    "A localização precisa estar ligada para pesquisar redes Wi-Fi.",
                    Toast.LENGTH_LONG
            ).show();

            try {

                Intent intent =
                        new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS
                        );

                startActivity(intent);

            } catch (Exception ignored) {
            }

            return;
        }

        summary.setText(
                "A procurar redes Wi-Fi..."
        );

        // Fazer nova pesquisa
        boolean started = wifiManager.startScan();

        if (!started) {

            // Mesmo que o novo scan seja recusado,
            // tenta mostrar os resultados disponíveis.
            showResults(false);

        }
    }

    /**
     * Verifica se a localização do aparelho está ligada.
     */
    private boolean isLocationEnabled() {

        LocationManager locationManager =
                (LocationManager) getSystemService(
                        Context.LOCATION_SERVICE
                );

        if (locationManager == null) {
            return false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

            return locationManager.isLocationEnabled();

        } else {

            try {

                return locationManager.isProviderEnabled(
                        LocationManager.GPS_PROVIDER
                ) || locationManager.isProviderEnabled(
                        LocationManager.NETWORK_PROVIDER
                );

            } catch (Exception e) {

                return false;
            }
        }
    }

    /**
     * Obtém e mostra as redes encontradas.
     */
    private void showResults(boolean updated) {

        if (wifiManager == null) {
            return;
        }

        // Verificar novamente a permissão
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {

            summary.setText(
                    "Permissão de localização não concedida."
            );

            return;
        }

        List<ScanResult> results;

        try {

            results = wifiManager.getScanResults();

        } catch (SecurityException e) {

            summary.setText(
                    "Sem permissão para obter redes Wi-Fi."
            );

            return;
        }

        if (results == null) {

            summary.setText(
                    "Nenhuma rede encontrada."
            );

            return;
        }

        networks.clear();

        // Percorrer TODAS as redes encontradas
        for (ScanResult result : results) {

            if (result == null) {
                continue;
            }

            String ssid = result.SSID;

            // Rede sem nome
            if (ssid == null || ssid.trim().isEmpty()) {

                ssid = "(Rede oculta)";
            } else {

                ssid = ssid.trim();
            }

            // Aplicar filtro
            boolean matchesFilter;

            if (filter.isEmpty()) {

                matchesFilter = true;

            } else {

                matchesFilter =
                        ssid.toLowerCase(Locale.ROOT)
                                .contains(
                                        filter.toLowerCase(Locale.ROOT)
                                );
            }

            if (matchesFilter) {

                networks.add(
                        new WifiNetwork(
                                ssid,
                                result.level
                        )
                );
            }
        }

        // Ordenar pela força do sinal
        // -40 vem antes de -70
        networks.sort(
                Comparator.comparingInt(
                        WifiNetwork::getRssi
                ).reversed()
        );

        adapter.notifyDataSetChanged();

        // Mostrar quantidade
        if (filter.isEmpty()) {

            summary.setText(
                    networks.size()
                            + " rede(s) encontrada(s)"
            );

        } else {

            summary.setText(
                    networks.size()
                            + " rede(s) encontrada(s) para \""
                            + filter
                            + "\""
            );
        }

        // Se não encontrou nenhuma
        if (networks.isEmpty()) {

            if (filter.isEmpty()) {

                summary.setText(
                        "Nenhuma rede Wi-Fi encontrada."
                );

            } else {

                summary.setText(
                        "Nenhuma rede encontrada para \""
                                + filter
                                + "\""
                );
            }
        }

        // Se o scan novo não foi atualizado,
        // informa que foram usados resultados disponíveis.
        if (!updated) {

            Toast.makeText(
                    this,
                    "Foram usados os resultados Wi-Fi disponíveis.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    @Override
    protected void onDestroy() {

        if (receiverRegistered) {

            try {

                unregisterReceiver(
                        wifiScanReceiver
                );

            } catch (Exception ignored) {
            }

            receiverRegistered = false;
        }

        super.onDestroy();
    }
}