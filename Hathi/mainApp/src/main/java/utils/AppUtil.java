package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class AppUtil {

	/**
	 * checks if the internet is available or not?
	 * 
	 * @param context
	 * @return
	 */

	public static final String HST = "-1000";
	public static final String AKST = "-0900";
	public static final String PST = "-0800";
	public static final String MST = "-0700";
	public static final String CST = "-0600";
	public static final String EST = "-0500";

	public static int DENSITYVALUE = 0;

	public static String timeZone = null;

	public static boolean isInternetAvailable(Context context) {
		ConnectivityManager conMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo wifi = conMgr
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		final NetworkInfo mobile = conMgr
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NETORK_FAILURE_REASON = null;
		if (wifi.getState() == NetworkInfo.State.CONNECTED
				|| wifi.getState() == NetworkInfo.State.CONNECTING) {
			CURRENT_NETWORK = ConnectivityManager.TYPE_WIFI;
			return true;

		} else if (mobile.getState() == NetworkInfo.State.CONNECTED
				|| mobile.getState() == NetworkInfo.State.CONNECTING) {
			CURRENT_NETWORK = ConnectivityManager.TYPE_MOBILE;
			return true;
		} else if (conMgr.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED
				|| conMgr.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
			return true;

		} else if (conMgr.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED
				|| conMgr.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING) {
			return true;

		} else if (mobile.getState() == NetworkInfo.State.DISCONNECTED
				|| mobile.getState() == NetworkInfo.State.DISCONNECTING) {

			NETORK_FAILURE_REASON = mobile.getReason();
			CURRENT_NETWORK = ConnectivityManager.TYPE_MOBILE;
		} else if (wifi.getState() == NetworkInfo.State.DISCONNECTED
				|| wifi.getState() == NetworkInfo.State.DISCONNECTING) {
			CURRENT_NETWORK = ConnectivityManager.TYPE_WIFI;
			NETORK_FAILURE_REASON = wifi.getReason();

		} else if (mobile.getState() == NetworkInfo.State.DISCONNECTED
				|| mobile.getState() == NetworkInfo.State.DISCONNECTING) {

			NETORK_FAILURE_REASON = mobile.getReason();
			CURRENT_NETWORK = ConnectivityManager.TYPE_MOBILE;
		} else if (wifi.getState() == NetworkInfo.State.DISCONNECTED
				|| wifi.getState() == NetworkInfo.State.DISCONNECTING) {
			CURRENT_NETWORK = ConnectivityManager.TYPE_WIFI;
			NETORK_FAILURE_REASON = wifi.getReason();

		}
		return false;
	}

	public static final String getCurrentNetworkFailureReason() {
		return NETORK_FAILURE_REASON;
	}

	public final static Pattern EMAIL_ADDRESS_PATTERN = Pattern
			.compile("[a-zA-Z0-9+._%-+]{1,256}" + "@"
					+ "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "."
					+ "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+");

	public static boolean checkEmail(String email) {
		return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
	}

	public static boolean isEmailValid(String email) {
		boolean isValid = false;

		// Initialize reg ex for email.
		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;
		// Make the comparison case-insensitive.
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}

		return isValid;
	}

	public static boolean isValidAadharNumber(EditText aadhar) {

		boolean flag = false;

		if (aadhar != null && aadhar.getText().toString().length() == 12) {

			flag = true;

		} else {

			flag = false;

		}

		return flag;
	}

	public static boolean isPhoneNumberValid(String phoneNumber) {
		boolean isValid = false;
		/*
		 * // Initialize reg ex for phone number. String expression =
		 * "^(1?(-?\\d{3})-?)?(\\d{3})(-?\\d{3,4})$"; CharSequence inputStr =
		 * phoneNumber; Pattern pattern = Pattern.compile(expression); Matcher
		 * matcher = pattern.matcher(inputStr); if (matcher.matches()) {
		 */
		if (phoneNumber.length() == 10) {
			isValid = true;
		}
		return isValid;
	}

	public static boolean isFloatNumberValid(String floatNumber) {
		boolean isValid = false;

		// Initialize reg ex for float number.
		// String expression = "^\\d*[0-9](|.\\d*[0-9]|)*$";
		String expression = "((-|\\+)?[0-9]+(\\.[0-9]+)?)+";
		CharSequence inputStr = floatNumber;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	public static boolean isNumericString(String numericnum) {
		boolean isValid = false;

		// Initialize reg ex for numeric number.
		String expression = "(*[0-9])";
		CharSequence inputStr = numericnum;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	public static boolean isAphaNumeric(String alphnumericnum) {
		boolean isValid = false;

		// Initialize reg ex for alpha numeric number.

		String expression = "^[a-zA-Z0-9]+$";
		CharSequence inputStr = alphnumericnum;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	public static String getTimeZoneString() {

		TimeZone tz1 = Calendar.getInstance().getTimeZone();

		Log.v("TIMEZONE STRING",
				"   "
						+ tz1.getDisplayName(tz1.inDaylightTime(Calendar
								.getInstance().getTime()), TimeZone.SHORT));

		return " "
				+ tz1.getDisplayName(
						tz1.inDaylightTime(Calendar.getInstance().getTime()),
						TimeZone.SHORT);

	}

	public static boolean isExternalStorageAvailable(Context context) {

		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// We can read and write the media
			mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// We can only read the media
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
		} else {
			// Something else is wrong. It may be one of many other states, but
			// all we need
			// to know is we can neither read nor write
			mExternalStorageAvailable = mExternalStorageWriteable = false;
		}

		if (mExternalStorageAvailable && mExternalStorageWriteable) {

			// Toast.makeText(context,"External storage is available",
			// Toast.LENGTH_LONG).show();

			return true;
		} else {

			Toast.makeText(
					context,
					"Cannot take Photos as media is unmounted. Ensure SDCARD is mounted",
					Toast.LENGTH_LONG).show();

			return false;
		}

	}

	public static File createImageInExternalStoragePublicDirectory(
			String filename) {

		File path = Environment.getExternalStoragePublicDirectory("uDrove");
		File file = new File(path, filename + ".jpg");
		path.mkdirs();

		return file;

	}

	public static String getImagePathInExternalStoragePublicDirectory(
			String filename) {

		File path = Environment.getExternalStoragePublicDirectory("uDrove");
		File file = new File(path, filename + ".jpg");
		path.mkdirs();

		if (file.exists()) {

			return file.getPath();
		}

		return "";
	}

	public static void deleteImageFromExternalStoragePublicDirectory(
			String filename) {

		File path = Environment.getExternalStoragePublicDirectory("uDrove");
		File file = new File(path, filename + ".jpg");
		file.delete();
	}

	public static File getCacheDirectory(Context context) {
		String sdState = Environment.getExternalStorageState();
		File cacheDir;

		if (sdState.equals(Environment.MEDIA_MOUNTED)) {
			File sdDir = Environment.getExternalStorageDirectory();

			// TODO : Change your diretcory here
			cacheDir = new File(sdDir, "data/tac/images");
		} else
			cacheDir = context.getCacheDir();

		if (!cacheDir.exists())
			cacheDir.mkdirs();
		return cacheDir;
	}

	/*
	 * public static void setImagetoView(Uri filePath, ImageView imageView,
	 * ContentResolver cr) {
	 * 
	 * try {
	 * 
	 * Bitmap bmp = MediaStore.Images.Media.getBitmap(cr, filePath);
	 * ByteArrayOutputStream bos = new ByteArrayOutputStream();
	 * bmp.compress(CompressFormat.JPEG, 80, bos);
	 * 
	 * int width = bmp.getWidth(); int height = bmp.getHeight();
	 * 
	 * int newWidth = imageView.getWidth(); int newHeight =
	 * imageView.getHeight();
	 * 
	 * float scaleWidth = ((float) newWidth) / width; float scaleHeight =
	 * ((float) newHeight) / height;
	 * 
	 * Matrix matrix = new Matrix(); matrix.postScale(scaleWidth, scaleHeight);
	 * matrix.postRotate(90);
	 * 
	 * Bitmap resizedBitmap = Bitmap.createBitmap(bmp, 0, 0, width, height,
	 * matrix, true);
	 * 
	 * BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);
	 * imageView.setImageDrawable(bmd);
	 * imageView.setScaleType(ScaleType.CENTER);
	 * 
	 * bmp = null; bos = null; resizedBitmap = null; bmd = null;
	 * 
	 * } catch (Exception e) { // TODO: handle exception
	 * 
	 * Log.v("AppUtil", e.getMessage());
	 * 
	 * }
	 * 
	 * }
	 */
	public static String timeHoursMinutesString(double hrs) {

		if (hrs > 0) {
			int noh = (int) hrs;
			int nom = (int) ((hrs - noh) * 60);
			return String.format("%02d:%02d", noh, nom);
		} else {
			return "00:00";
		}
	}

	public static int CURRENT_NETWORK = ConnectivityManager.TYPE_WIFI;
	public static String NETORK_FAILURE_REASON = null;

	public static void writeServiceMetric(String message, String date)
			throws IOException, ClassNotFoundException {

		try {
			File root = new File(Environment.getExternalStorageDirectory(),
					"Mainline_Logs");
			if (!root.exists()) {
				root.mkdirs();

			}

			File gpxfile = new File(root, "Logs.txt");

			FileWriter writer = new FileWriter(gpxfile, true);

			PrintWriter pw = new PrintWriter(writer);

			pw.println();

			pw.println(message + "--->" + date + " ");

			pw.flush();

			pw.print("\r\n");

			if (message.contains("Difference In Time(Milliseconds)")) {

				pw.println("--------------------------------------------------------------------------------");

				pw.print("\r\n");

			}

			/*
			 * if (message.equalsIgnoreCase("Response time")) { After
			 * compression
			 * 
			 * //pw.print("\r\n");
			 * 
			 * long difference = ParentActivity.responsetime.getTime() -
			 * ParentActivity.requesttime.getTime();
			 * 
			 * pw.println("Time Difference----->" + difference + "msec");
			 * 
			 * pw.print("\r\n");
			 * 
			 * pw.println(
			 * "-----------------------------------------------------------------------"
			 * );
			 * 
			 * }
			 */

			writer.close();

			pw.close();

		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	public static String convertSqliteDateToDate(String inputdate) {

		String formattedDate = "-1";

		try {

			if (inputdate != null) {

				DateFormat originalFormat = new SimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ENGLISH);

				DateFormat targetFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm");

				Date date = originalFormat.parse(inputdate);

				formattedDate = targetFormat.format(date);

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return formattedDate;

	}
	
	
	public static boolean isDeviceTeluguLocaleSelected(){
		
		String locale = Locale.getDefault().getLanguage();
		
		if(locale!=null&&locale.equals("te")){
			
			return true;
		}else{
			
			return false;
		}
		
		
	}
	

	public static String getRandomNumber() {
		/*
		 * Random generator = new Random(); int r = generator.nextInt(1000);
		 */
		return UUID.randomUUID().toString();

	}

	public static String getDeviceIMEI(Context context) {

		TelephonyManager mngr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		return mngr.getDeviceId();
	}

	public static String getDefaultUsername(Context ctx) {

		TelephonyManager telephonyManager = (TelephonyManager) ctx
				.getSystemService(Context.TELEPHONY_SERVICE);
		String did = telephonyManager.getDeviceId();

		return String.valueOf(did);

	}

	public static String getDefaultPassword(Context context) {

		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		String did = telephonyManager.getDeviceId();

		return did + "Abc";
	}

	public static boolean isValidPhoneNumber(String phonenumber) {

		boolean result = false;

		if (phonenumber != null) {

			Pattern p = Pattern
					.compile("[7-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]");

			Matcher m = p.matcher(phonenumber);

			if (m.find() && m.group().equals(phonenumber)) {

				result = true;

			} else {

				return result;
			}

		} else {

			return result;
		}
		return result;

	}

	public static boolean isNumberic(String number) {

		boolean result = false;

		if (number != null) {

			Pattern p = Pattern.compile("\\d+");

			Matcher m = p.matcher(number);

			if (m.find() && m.group().equals(number)) {

				result = true;

			} else {

				return result;
			}

		} else {

			return result;
		}
		return result;

	}

}
