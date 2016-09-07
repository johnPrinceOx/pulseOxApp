package https;

import java.io.File;

import android.os.Environment;

public class HTTPConstants {

	public static final int RETRY_COUNT = 5;

	public static final String FLAG_OPENMRS = "flagtoopenmrs";

	// OpenMRS service URLs

	/*
	 * public static final String DEFAULT_USERNAME = "airwind";
	 * 
	 * public static final String DEFAULT_PASSWORD = "HT";
	 */

	// 183.82.97.129:8080

	// public static final String HOST_IP = "<enter IP:port here>"; // dev

	// public static final String HOST_IP = "<enter IP:port here>"; // training


	
	

    public static final String HOST_IP = ""; // Evaluation
	
	
	 //public static final String HOST_IP = "220.227.243.138:8080"; // Training
	
	
	

	public static final String OPENMRS_URL = "https://" + HOST_IP
			+ "/openmrs/ws/rest/v1";

	// public static final String DB_MAIN_RCT = "Dev_SHIntervention"; // dev
	// public static final String
	// DB_MAIN_RCT="Training_SHIntervention";//training
	
	
	public static final String PACKAGE_NAME="org.pnuemoniaproject.hathi";
	
	
	public static final String DB_MAIN_RCT = "Eval_SHIntervention";

	public static String FILE_DOWNLOADED_PATH = Environment
			.getExternalStorageDirectory() + File.separator + DB_MAIN_RCT;

	public static String FILE_DOWNLOAD_SERVER_URL = "";

	public static String USERS_FILE_NAME = "users.csv";

	public static String VILLAGE_FILE_NAME = "village.csv";

	public static String PHC_FILE_NAME = "phc.csv";

	public static String PATIENT_FILE_NAME = "patient.csv";

}
