package eula;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;

import org.pnuemoniaproject.hathi.Constants;
import org.pnuemoniaproject.hathi.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Eula {
	// package com.google.android.divideandconquer;
	private static String ASSET_EULA = "EULA";
	private static final String PREFERENCE_EULA_ACCEPTED = "eula.accepted";
	private static final String PREFERENCES_EULA = "eula";
	static AlertDialog alertDialog = null;

	static interface OnEulaAgreedTo {
		void onEulaAgreedTo();
	}

	public static boolean show(final Activity activity) {

		Button agree = null;
		Button disagree = null;

		final SharedPreferences preferences = activity.getSharedPreferences(
				PREFERENCES_EULA, Context.MODE_PRIVATE);

		if (!preferences.getBoolean(PREFERENCE_EULA_ACCEPTED, false)) {
			Log.d(Constants.TAG, "First time entry: Display License");

			final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					activity);

			LayoutInflater inflater = activity.getLayoutInflater();
			View dialoglayout = inflater.inflate(R.layout.agreement, null);
			
			
			TextView licenseterms = (TextView)dialoglayout.findViewById(R.id.terms_description);
			
			licenseterms.setText(readEula(activity));

			agree = (Button) dialoglayout.findViewById(R.id.btn_agree);
			agree.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					accept(preferences);

					if (activity instanceof OnEulaAgreedTo) {
						((OnEulaAgreedTo) activity).onEulaAgreedTo();
					}

					alertDialog.cancel();
				}
			});

			disagree = (Button) dialoglayout.findViewById(R.id.btn_disagree);
			
			
			disagree.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {

					refuse(activity);
					alertDialog.cancel();
					// System.exit(0);
				}
			});

			alertDialogBuilder.setCancelable(false);
			alertDialogBuilder.setView(dialoglayout);
			alertDialog = alertDialogBuilder.create();
			alertDialog.show();

			/*
			 * final AlertDialog.Builder builder = new
			 * AlertDialog.Builder(activity);
			 * 
			 * builder.setTitle(R.string.eula_title);
			 * 
			 * builder.setCancelable(true);
			 * builder.setPositiveButton(R.string.eula_accept, new
			 * DialogInterface.OnClickListener() { public void
			 * onClick(DialogInterface dialog, int which) { accept(preferences);
			 * 
			 * if (activity instanceof OnEulaAgreedTo) { ((OnEulaAgreedTo)
			 * activity).onEulaAgreedTo(); }
			 * 
			 * } });
			 * 
			 * builder.setNegativeButton(R.string.eula_refuse, new
			 * DialogInterface.OnClickListener() { public void
			 * onClick(DialogInterface dialog, int which) { refuse(activity);
			 * 
			 * } });
			 * 
			 * builder.setOnCancelListener(new
			 * DialogInterface.OnCancelListener() { public void
			 * onCancel(DialogInterface dialog) { refuse(activity);
			 * 
			 * } });
			 * 
			 * builder.setMessage(readEula(activity)); builder.create().show();
			 */

			return false;
		}

		return true;
	}

	private static void accept(SharedPreferences preferences) {
		preferences.edit().putBoolean(PREFERENCE_EULA_ACCEPTED, true).commit();
		Log.d(Constants.TAG, "License accepted- starting next activity");

	}

	private static void refuse(Activity activity) {
		activity.finish();
		Log.d(Constants.TAG, "License Rejected!");
	}

	private static CharSequence readEula(Activity activity) {
		BufferedReader in = null;

		try {
			in = new BufferedReader(new InputStreamReader(activity.getAssets()
					.open(ASSET_EULA)));
			String line;
			StringBuilder buffer = new StringBuilder();
			while ((line = in.readLine()) != null)
				buffer.append(line).append('\n');
			return buffer;
		} catch (IOException e) {
			return "";
		} finally {
			closeStream(in);
		}
	}

	private static void closeStream(Closeable stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				// Ignore
			}
		}
	}
}
