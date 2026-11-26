# Centinell Firewall
Centinell Suite Defense — v0.2.0

Offline firewall policy simulator. Does not intercept, inspect or block actual network traffic. No server, accounts or paid API required. Local data is stored in browser/WebView storage; export JSON for backup. Do not enter confidential network information in a public demo.

## Browser
Open `www/index.html` locally.

## Android build
Install Node.js 20+, JDK 21 and Android Studio with the required Android SDK. Run `npm install`, then `npm run android:setup` (once), `npm run android:sync`, and `npm run android:open`. In Android Studio, test on an emulator or device and generate a signed Android App Bundle for Play Console. Verify current target SDK and publishing requirements before submission. Signing keys must never be committed.

## Validation checklist
Add an ALLOW TCP rule for 192.0.2.10 port 443. Evaluate matching and nonmatching packets; expect ALLOW and default DENY respectively. Test wildcard rules, priority, invalid input, deletion, reload and report export. Test Android offline and on multiple screen sizes. Native build and device tests are not yet performed.

## Ten modules
Overview, Policies, Simulator, Assets, Incidents, Audit, Services, Activity, Reports, Settings. Includes local persistence, rule editing and reordering, validated JSON backup restore, and manual incident tracking. Service review is static policy analysis, not a live scan. Android compilation and end-to-end testing remain pending.
