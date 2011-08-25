package com.madgag.spongycastle.eclipse;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Security;

import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.openssl.PEMReader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SpongyCastleDemoActivity extends Activity {
	private static final String TAG = "SCDA";

	static {
		Security.addProvider(new BouncyCastleProvider());
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        for (String sampleFile : asList("id_rsa_sample")) {
        	show(sampleFile);
        }
    }

	private void show(String sampleFile) {
		try {
        	PEMReader r = new PEMReader(new InputStreamReader(getAssets().open(sampleFile)));
			Object obj = r.readObject();
			
	        Log.i(TAG, "Gah "+obj);
			TextView tv = (TextView) findViewById(R.id.resultText);
			tv.setText(obj.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}