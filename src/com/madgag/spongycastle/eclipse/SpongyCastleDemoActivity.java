package com.madgag.spongycastle.eclipse;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.security.Security;

import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.openssl.PEMReader;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        ViewGroup vg=(ViewGroup)findViewById(R.id.main_layout);
        for (String sampleFile : asList("id_rsa_sample", "id_ecdsa_sample")) {
        	viewFor(sampleFile,vg);
        }
    }

	private View viewFor(String sampleFile,ViewGroup vg) {
		try {
        	PEMReader r = new PEMReader(new InputStreamReader(getAssets().open(sampleFile)));
			Object obj = r.readObject();
			r.close();
			View v=LayoutInflater.from(this).inflate(android.R.layout.simple_list_item_2, vg, false);
			TextView t1 = (TextView) v.findViewById(android.R.id.text1);
			TextView t2 = (TextView) v.findViewById(android.R.id.text2);
			t1.setText(sampleFile);
			t2.setText(describe(obj));
			vg.addView(v);
			return v;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String describe(Object obj) {
		if (obj instanceof KeyPair) {
			return describeKeyPair((KeyPair) obj);
		}
		return obj.toString();
	}

	private String describeKeyPair(KeyPair kp) {
		return describe("privateKey="+kp.getPrivate()+" publicKey="+kp.getPublic());
	}
}