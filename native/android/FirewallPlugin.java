package com.beaconoftheeagle.centinellfirewall;
import android.app.Activity;
import android.content.Intent;
import android.net.VpnService;
import androidx.activity.result.ActivityResult;
import androidx.core.content.ContextCompat;
import com.getcapacitor.*;
import com.getcapacitor.annotation.*;
@CapacitorPlugin(name="Firewall")
public class FirewallPlugin extends Plugin {
 @PluginMethod public void start(PluginCall call){
  try{Intent consent=VpnService.prepare(getContext());if(consent!=null)startActivityForResult(call,consent,"vpnConsent");else launch(call);}catch(Exception e){call.reject("VPN start failed",e);}
 }
 @ActivityCallback private void vpnConsent(PluginCall call,ActivityResult result){if(call==null)return;if(result.getResultCode()==Activity.RESULT_OK)launch(call);else call.reject("VPN permission denied");}
 private void launch(PluginCall call){try{ContextCompat.startForegroundService(getContext(),new Intent(getContext(),FirewallVpnService.class));call.resolve();}catch(Exception e){call.reject("Cannot start VPN",e);}}
 @PluginMethod public void stop(PluginCall call){getContext().stopService(new Intent(getContext(),FirewallVpnService.class));call.resolve();}
 @PluginMethod public void status(PluginCall call){JSObject o=new JSObject();o.put("active",FirewallVpnService.active);o.put("dropped",FirewallVpnService.dropped.get());o.put("mode","isolation");call.resolve(o);}
}
