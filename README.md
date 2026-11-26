# Centinell Firewall
Centinell Suite Defense — v0.1.0

Offline firewall policy simulator. Does not intercept, inspect or block actual network traffic. No server, accounts or paid API required. Local data is stored in browser/WebView storage; export JSON for backup. Do not enter confidential network information in a public demo.

## Browser
Open `www/index.html` locally.

## Android build
Install Node.js 20+, JDK 21 and Android Studio with the required Android SDK. Run `npm install`, then `npm run android:setup` (once), `npm run android:sync`, and `npm run android:open`. In Android Studio, test on an emulator or device and generate a signed Android App Bundle for Play Console. Verify current target SDK and publishing requirements before submission. Signing keys must never be committed.

## Validation checklist
Add an ALLOW TCP rule for 192.0.2.10 port 443. Evaluate matching and nonmatching packets; expect ALLOW and default DENY respectively. Test wildcard rules, priority, invalid input, deletion, reload and report export. Test Android offline and on multiple screen sizes. Native build and device tests are not yet performed.
