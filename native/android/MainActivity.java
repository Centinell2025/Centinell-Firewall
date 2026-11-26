package com.beaconoftheeagle.centinellfirewall;
import android.os.Bundle;
import com.getcapacitor.BridgeActivity;
public class MainActivity extends BridgeActivity {
 @Override public void onCreate(Bundle savedInstanceState){registerPlugin(FirewallPlugin.class);super.onCreate(savedInstanceState);}
}
