package mz.ustm.wificampusscanner;

public class WifiNetwork {
    private final String ssid;
    private final int rssi;

    public WifiNetwork(String ssid, int rssi) {
        this.ssid = ssid;
        this.rssi = rssi;
    }

    public String getSsid() { return ssid; }
    public int getRssi() { return rssi; }

    public String getQuality() {
        if (rssi >= -50) return "Excelente";
        if (rssi >= -60) return "Bom";
        if (rssi >= -70) return "Regular";
        return "Fraco";
    }
}
