package com.beaconoftheeagle.centinellfirewall;

import android.app.*;
import android.content.*;
import android.net.VpnService;
import android.os.*;
import java.io.*;
import java.util.concurrent.atomic.AtomicLong;

public class FirewallVpnService extends VpnService {
 public static final String STOP="centinell.STOP";
 public static volatile boolean active=false;
 public static final AtomicLong dropped=new AtomicLong();
 private ParcelFileDescriptor tunnel;
 private Thread reader;
 private volatile boolean running;
 @Override public synchronized int onStartCommand(Intent intent,int flags,int id){
  if(intent!=null && STOP.equals(intent.getAction())){shutdown();return START_NOT_STICKY;}
  if(running)return START_NOT_STICKY;
  NotificationManager nm=getSystemService(NotificationManager.class);
  if(Build.VERSION.SDK_INT>=26)nm.createNotificationChannel(new NotificationChannel("firewall","Firewall protection",NotificationManager.IMPORTANCE_LOW));
  PendingIntent stop=PendingIntent.getService(this,1,new Intent(this,FirewallVpnService.class).setAction(STOP),PendingIntent.FLAG_IMMUTABLE|PendingIntent.FLAG_UPDATE_CURRENT);
  Notification n=new Notification.Builder(this,"firewall").setSmallIcon(android.R.drawable.ic_lock_lock).setContentTitle("Centinell Firewall").setContentText("Isolation mode: all routed traffic blocked").setOngoing(true).addAction(new Notification.Action.Builder(null,"Stop isolation",stop).build()).build();
  if(Build.VERSION.SDK_INT>=34)startForeground(91,n,android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE);else startForeground(91,n);
  try{
   tunnel=new Builder().setSession("Centinell Isolation").setMtu(1500).addAddress("10.88.0.2",32).addAddress("fd00:88::2",128).addRoute("0.0.0.0",0).addRoute("::",0).addDnsServer("192.0.2.53").setBlocking(true).establish();
   if(tunnel==null){shutdown();return START_NOT_STICKY;}
   running=true;active=true;dropped.set(0);
   final ParcelFileDescriptor fd=tunnel;
   reader=new Thread(()->{try{FileInputStream in=new FileInputStream(fd.getFileDescriptor());byte[] packet=new byte[32768];while(running){int size=in.read(packet);if(size<0)break;if(size>0)dropped.incrementAndGet();}}catch(IOException ignored){}finally{if(running)new Handler(Looper.getMainLooper()).post(()->shutdown());}},"centinell-isolation");
   reader.start();
  }catch(Exception e){shutdown();}
  return START_NOT_STICKY;
 }
 private synchronized void shutdown(){running=false;active=false;if(tunnel!=null){try{tunnel.close();}catch(IOException ignored){}tunnel=null;}if(reader!=null){reader.interrupt();reader=null;}stopForeground(STOP_FOREGROUND_REMOVE);stopSelf();}
 @Override public void onRevoke(){shutdown();super.onRevoke();}
 @Override public void onDestroy(){shutdown();super.onDestroy();}
}
