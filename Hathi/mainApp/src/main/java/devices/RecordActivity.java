package devices;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidplot.Plot;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.smartcare.research.library.ConnectionManager;

import org.pnuemoniaproject.hathi.R;
import org.pnuemoniaproject.hathi.firstMenu;
import org.pnuemoniaproject.hathi.RecordingComplete;

import java.io.File;
import java.lang.ref.WeakReference;

import db.CVDVariables;

/**
 * This Activity is responsible for recording, displaying and uploading a physiological signal
 * using a SmartCare Analytics Ltd. Bluetooth pulse oximeter.
 */
public class RecordActivity extends Activity {

    private static final double UI_BUFFER_SIZE_SAMPLES = 1000;
    private Button backStepButton, nextStepButton;
    private boolean wantoExit;

    private ConnectionManager connectionManager;
    private XYPlot mPlot;
    private Button mControlButton;
    private TextView mHrDisplay;
    private TextView mSpo2Display;
    private TextView mSpo2Label;
    private SimpleXYSeries mSeries;
    private SmartCareHandler mHandler;

    /**
     * This Handler is responsible for handling messages from the ConnectionManager which may
     * contain data or error messages.
     */
    private static class SmartCareHandler extends Handler {

        // A weak reference won't suffer from memory leaks, which might otherwise occur with static
        // references to the activity context.
        private final WeakReference<RecordActivity> mmTarget;

        /**
         * Constructor for the ReceiveDataHandler.
         */
        public SmartCareHandler(RecordActivity target) {
            mmTarget = new WeakReference<>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            final RecordActivity target = mmTarget.get();
            // Each message contains an identification code in the Message,what field.
            switch (msg.what) {
                case ConnectionManager.MESSAGE_PACKET:
                    // New data packet received. Particular data can be retrieved using the
                    // corresponding key.
                    Bundle data = msg.getData();
                    int latestSignalVal = data.getInt(ConnectionManager.KEY_DATA_PLETH);
                    int latestHr = data.getInt(ConnectionManager.KEY_DATA_PULSE);
                    double latestSpo2 = data.getDouble(ConnectionManager.KEY_DATA_SPO2);
                    boolean seriesIsFull = target.mSeries.size() > UI_BUFFER_SIZE_SAMPLES;
                    if (seriesIsFull) {
                        target.mSeries.removeFirst();
                    }
                    target.mSeries.addLast(null, latestSignalVal);
                    if (seriesIsFull) {
                        target.mPlot.redraw();
                    }
                    target.mHrDisplay.setText(String.valueOf(latestHr));
                    target.mSpo2Display.setText(String.valueOf((int) latestSpo2));
                    break;
                case ConnectionManager.MESSAGE_UNKNOWN_OXIMETER:
                    Toast.makeText(target, "Unknown pulse oximeter", Toast.LENGTH_SHORT).show();
                    break;
                case ConnectionManager.MESSAGE_BLUETOOTH_OFF:
                    Toast.makeText(target, "Bluetooth is off", Toast.LENGTH_SHORT).show();
                    break;
                case ConnectionManager.MESSAGE_BLUETOOTH_UNAVAILABLE:
                    Toast.makeText(target, "Bluetooth is unavailable", Toast.LENGTH_SHORT).show();
                    break;
                case ConnectionManager.MESSAGE_CONNECTION_LOST:
                    Toast.makeText(target, "Bluetooth connection lost", Toast.LENGTH_SHORT).show();
                    break;
                case ConnectionManager.MESSAGE_RECORDING_STARTED:
                    Toast.makeText(target, "Recording started", Toast.LENGTH_SHORT).show();
                    break;
                case ConnectionManager.MESSAGE_RECORDING_ENDED:
                    Toast.makeText(target, "Recording finished", Toast.LENGTH_SHORT).show();
                    // Device has disconnected.
                    target.mControlButton.setText(target.getString(R.string.record_start));
                    target.mControlButton.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            target.connectToPulseOximeter();
                        }
                    });
                    target.mPlot.clear();
                    target.mPlot.redraw();
                    break;
                default:
                    Log.e(target.getString(R.string.app_name), "Unknown message ID from ConnectionManager");
            }
        }
    }

    /**
     * A task which manipulates the UI and starts the recording process. It creates and initialises
     * the ConnectionManager and begins recording. This is done asyncronously as it is time
     * consuming and would otherwise block the UI thread, causing the app not to respond.
     */
    private class Connector extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            Log.i(getString(R.string.app_name), "Connector task started");
            mControlButton.setText(getString(R.string.record_stop));
            mControlButton.setEnabled(false);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean success = connectionManager.connect();
            return success;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            mControlButton.setEnabled(true);

            if (!result) {
                Log.w(getString(R.string.app_name), "Cannot connect");
                mControlButton.setText(getString(R.string.record_start));
                mControlButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        connectToPulseOximeter();
                        //Do nothing
                    }
                });
                return;
            }

            Log.i(getString(R.string.app_name), "Ready to start recording.");
            connectionManager.startRecording();
            mControlButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    stopDisconnectExit();
                }
            });
        }
    }

    /**
     * Stops the recording process.
     */
    private void stopDisconnectExit() {
        connectionManager.disconnect();

        Toast.makeText(this, "Recording finished", Toast.LENGTH_SHORT).show();
        mPlot.clear();
        mHrDisplay.setText("--");
        mSpo2Display.setText("--");

        Intent next = new Intent(RecordActivity.this,
                RecordingComplete.class);
        startActivity(next);

        // initialiseSeries();

        /*
        mControlButton.setText("Start");
        mControlButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                connectToPulseOximeter();
            }
        });
        */
    };

    /**
     * Initialises the connection to the pulse oximeter by executing the Connector task.
     */
    private void connectToPulseOximeter() {
        new Connector().execute();
    }

    /**
     * Called when the activity is first created. Initialises the private fields of the Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pulseox_mainact);

        // Initialise the UI.
        initialiseViews();
        initialisePlot();
        initialiseSeries();
        initializeNavBarandFooter();
        initializeButtonListeners();
        // Create new ConnectionManager, which will connect to and manage the pulse oximeter.
        mHandler = new SmartCareHandler(this);
        //String outputDir = new File(Environment.getExternalStorageDirectory().toString(), getString(R.string.app_name)).toString();
        String outputDir = new File(Environment.getExternalStorageDirectory().toString(), "MobOx/"+CVDVariables.J_VARIABLES_P_ID+CVDVariables.J_VARIABLES_LAST_NAME).toString();
        if (connectionManager == null) {
            connectionManager = new ConnectionManager(this, mHandler, outputDir);
        }
    }

    private void initialiseViews() {
        mPlot = (XYPlot) findViewById(R.id.plot);
        mControlButton = (Button) findViewById(R.id.button);
        mHrDisplay = (TextView) findViewById(R.id.hr);
        mSpo2Display = (TextView) findViewById(R.id.spo2);
        mSpo2Label = (TextView) findViewById(R.id.spo2label);
        mSpo2Label.setText(Html.fromHtml("SpO<sub><small>2</small></sub>"));

        mControlButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                connectToPulseOximeter();
            }
        });
    }

    private void initialisePlot() {
        Paint transparentPaint = new Paint();
        transparentPaint.setColor(Color.WHITE);
        transparentPaint.setAlpha(255);
        mPlot.getLayoutManager().remove(mPlot.getLegendWidget());
        mPlot.setBorderStyle(Plot.BorderStyle.SQUARE, null, null);
        mPlot.setBorderPaint(null);
        mPlot.getGraphWidget().getRangeLabelPaint().setAlpha(0);
        mPlot.getGraphWidget().getDomainLabelPaint().setAlpha(0);
        mPlot.getGraphWidget().getRangeOriginLabelPaint().setAlpha(0);
        mPlot.getGraphWidget().getDomainOriginLabelPaint().setAlpha(0);
        mPlot.getGraphWidget().setBackgroundPaint(transparentPaint);
        mPlot.getGraphWidget().setGridBackgroundPaint(null);
        mPlot.setDomainLabel("");
        mPlot.setRangeLabel("");
        mPlot.setTitle("");
        mPlot.getGraphWidget().getRangeLabelPaint().setAlpha(0);
        mPlot.getGraphWidget().getDomainLabelPaint().setAlpha(0);
        mPlot.getGraphWidget().getDomainOriginLabelPaint().setAlpha(0);
        mPlot.getGraphWidget().getRangeOriginLabelPaint().setAlpha(0);
        mPlot.getGraphWidget().getRangeGridLinePaint().setAlpha(0);
        mPlot.getGraphWidget().getDomainGridLinePaint().setAlpha(0);
        mPlot.setPlotPadding(-61, -25, -10, -37); // L,T,R,B
        mPlot.setMarkupEnabled(false);
        mPlot.getGraphWidget().setCursorPosition(-1, -1);
        mPlot.getGraphWidget().getCursorLabelPaint().setAlpha(0);
        mPlot.getGraphWidget().getCursorLabelBackgroundPaint().setAlpha(0);
    }

    private void initialiseSeries() {
        mPlot.clear();
        mSeries = new SimpleXYSeries("pleth");
        mSeries.useImplicitXVals();
        mPlot.addSeries(mSeries, new LineAndPointFormatter(Color.BLUE, null, null, new PointLabelFormatter(Color.TRANSPARENT)));
    }

    private void initializeNavBarandFooter() {

        backStepButton = (Button) findViewById(R.id.btn_back_step);
        nextStepButton = (Button) findViewById(R.id.btn_next_step);

        backStepButton.setVisibility(View.VISIBLE);
        nextStepButton.setVisibility(View.INVISIBLE);

        nextStepButton.setEnabled(false);

    }
    public void initializeButtonListeners() {

        backStepButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                //		appLogger.LogEvent(EventType.BUTTON_PRESSED, "BackStepButton", "");
                gotoPreviousScreen();


            }
        });

    }

    protected void exitToHome() {
        Intent back = new Intent(RecordActivity.this,
                firstMenu.class);
        startActivity(back);
		/* add event to logger */
        //appLogger.LogEvent(EventType.ACTIVITY_ENDED, "Step1-Registration", "going back");
        RecordActivity.this.finish();
    }


    private void gotoPreviousScreen() {
		/*
		 * Place a dialog to prompt user that all the data will be lost by
		 * navigating to previous screen.
		 * on ok click execute below code. on cancel do nothing
		 *
		 */
        AlertDialog.Builder builder = new AlertDialog.Builder(RecordActivity.this);

        builder.setIcon(R.drawable.alert);

        builder.setMessage(getResources().getString(
                R.string.logout_prompt));

        // Setting OK Button
        builder
                .setPositiveButton( getResources().getString(R.string.string_ok)  ,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

//						{
//							alertDialog.dismiss();
//						}
                                wantoExit = true;

                                dialog.dismiss();
                                exitToHome();
                            }
                        });
        builder.setNegativeButton(
                getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        // Write your code here to execute after
                        // dialog
                        // closed
                        wantoExit = false;
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
//		prefs = getSharedPreferences(CVD_VARIABLES,
//				Context.MODE_PRIVATE);
//		editorShared = prefs.edit();
//		editorShared.clear();
//		editorShared.commit();
		/*
				if(wantoExit)
				{

				}*/

    }
    /**
     * Called when the Activity is destroyed. Where possible, the Bluetooth connection should be
     * shut down gracefully by the ConnectionManager.
     */
    @Override
    protected void onDestroy() {
        if (connectionManager.isRecording()) {
            connectionManager.disconnect();
        }
        super.onDestroy();
    }
}