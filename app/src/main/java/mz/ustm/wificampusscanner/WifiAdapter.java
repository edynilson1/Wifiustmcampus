package mz.ustm.wificampusscanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class WifiAdapter extends RecyclerView.Adapter<WifiAdapter.ViewHolder> {
    private final List<WifiNetwork> networks;

    public WifiAdapter(List<WifiNetwork> networks) { this.networks = networks; }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wifi, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        WifiNetwork n = networks.get(position);
        h.ssid.setText("📶 " + n.getSsid());
        h.rssi.setText("RSSI: " + n.getRssi() + " dBm");
        h.quality.setText("Sinal: " + n.getQuality());
    }

    @Override public int getItemCount() { return networks.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ssid, rssi, quality;
        ViewHolder(View item) {
            super(item);
            ssid = item.findViewById(R.id.txtSsid);
            rssi = item.findViewById(R.id.txtRssi);
            quality = item.findViewById(R.id.txtQuality);
        }
    }
}
