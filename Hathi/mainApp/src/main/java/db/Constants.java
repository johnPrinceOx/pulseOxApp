package db;

import java.util.HashMap;
import java.util.Iterator;


public class Constants {
	public static final String TAG = "hathiPneumonia";
	
	//types of users
	public final static String USER_TYPE_ADMIN = "admin";
	public final static String USER_TYPE_ASHA = "asha";
	public final static String USER_TYPE_DOCTOR = "doctor";

	public final static String USER_TYPE_HOSPITALSTREAM = "hospi";
	public final static String USER_TYPE_COMMUNITYSTREAM = "commi";

	
	//isRiskAssessmentCompleted?
	public final static String RA_NOT_COMPLETED = "Incomplete";
	public final static String RA_COMPLETED = "Finished";

	//PHCs that the ASHAs belong to; - for HT_PILOT dated: 15Nov 2012
	public final static int PHC_DEFAULT = -1;
	public final static int PHC_GANPAVARAM = 1;
	public final static int PHC_GOLLAVANITHIPPA = 2;
	public final static int PHC_YANDAGANDI = 3;
	
	//months
	public final static int MONTH_JAN = 1;
	public final static int MONTH_FEB = 2;
	public final static int MONTH_MARCH = 3;
	public final static int MONTH_APRIL = 4;
	public final static int MONTH_MAY = 5;
	public final static int MONTH_JUNE = 6;
	public final static int MONTH_JULY = 7;
	public final static int MONTH_AUG = 8;
	public final static int MONTH_SEP = 9;
	public final static int MONTH_OCT = 10;
	public final static int MONTH_NOV = 11;
	public final static int MONTH_DEC = 12;
	public final static int MONTH_UNKNOWN = -1;
	
	//Questionnaire Screen CheckBoxes
	public final static String CHECKBOX_1= "1";
	public final static String CHECKBOX_2= "2";
	public final static String CHECKBOX_3= "3";
	public final static String CHECKBOX_4= "4";
	public final static String CHECKBOX_5= "5";
	public final static String CHECKBOX_6= "6";
	
	public static final String PUSHLINK_API_KEY = "bpsve5pogjnopf2u";
	
	//csv file name for BP outputs
	public static final String CSV_FILENAME = "HealthTrackerCVD_BPmeasure_";
	
	public class EventCalendar
	{
		private HashMap<Integer, Integer> Eventmonth;
		private HashMap<Integer, Integer> EventYear;
		
		/**
		 * @return the eventmonth
		 */
		public HashMap<Integer, Integer> getEventmonth() {
			return Eventmonth;
		}


		/**
		 * @return the eventYear
		 */
		public HashMap<Integer, Integer> getEventYear() {
			return EventYear;
		}

		
		public EventCalendar() {
			super();
			this.Eventmonth = new HashMap<Integer,Integer>();
			this.EventYear = new HashMap<Integer,Integer>();
		}
	}
	

	public int getEventmonth(int num, EventCalendar eCal)
	{
		int answer = -1;
		Iterator<Integer> iterator_ETL = eCal.getEventmonth().keySet().iterator();

		while (iterator_ETL.hasNext()) {
			String key = iterator_ETL.next().toString();

			 
			System.out.print("key" + ": " + key);
			if(Integer.valueOf(key)==num)
			{	
				int value = eCal.getEventmonth().get(num);
				answer= value;
				//String value = (String) eCal.getEventmonth().get(key).toString();
				
				//answer= Integer.valueOf(value);
				
			}
			
		}
		return answer;
		
	}
	
	public int getEventYear(int num, EventCalendar eCal)
	{
		int answer = -1;
		Iterator<Integer> iterator_ETL = eCal.getEventYear().keySet().iterator();

		while (iterator_ETL.hasNext()) {
			String key = iterator_ETL.next().toString();

			// Map.Entry me = (Map.Entry)iterator.next();
			System.out.print("key" + ": " + key);
			if(Integer.valueOf(key)==num)
			{	
				String value = eCal.getEventYear().get(num).toString();
				answer= Integer.valueOf(value);
				
			}
			
		}
		return answer;
		
	}
	
	public EventCalendar insertDates()
	{
		EventCalendar eCal = new EventCalendar();		 
		//item1
		 eCal.Eventmonth.put(1, MONTH_AUG);
		 eCal.EventYear.put(1, 1947);
		//item2
		 eCal.Eventmonth.put(2, MONTH_JAN);
		 eCal.EventYear.put(2, 1948);
		//item3
		 eCal.Eventmonth.put(3, MONTH_SEP );
		 eCal.EventYear.put(3, 1948);
		//item4
		 eCal.Eventmonth.put(4, MONTH_JAN );
		 eCal.EventYear.put(4, 1950);
		//item5
		 eCal.Eventmonth.put(5, MONTH_NOV );
		 eCal.EventYear.put(5, 1956);
		//item6
		 eCal.Eventmonth.put(6, MONTH_UNKNOWN );
		 eCal.EventYear.put(6, 1962);
		//item7
		 eCal.Eventmonth.put(7, MONTH_JULY );
		 eCal.EventYear.put(7, 1969);
		//item8
		 eCal.Eventmonth.put(8, MONTH_UNKNOWN );
		 eCal.EventYear.put(8, 1970);
		//item9
		 eCal.Eventmonth.put(9, MONTH_UNKNOWN );
		 eCal.EventYear.put(9, 1971); 
		//item10
		 eCal.Eventmonth.put(10, MONTH_UNKNOWN );
		 eCal.EventYear.put(10, 1975);
		//item11
		 eCal.Eventmonth.put(11, MONTH_UNKNOWN );
		 eCal.EventYear.put(11, 1977);
		//item12
		 eCal.Eventmonth.put(12, MONTH_UNKNOWN );
		 eCal.EventYear.put(12, 1977);
		//item13
		 eCal.Eventmonth.put(13, MONTH_UNKNOWN );
		 eCal.EventYear.put(13, 1979); 
		//item14
		 eCal.Eventmonth.put(14, MONTH_UNKNOWN );
		 eCal.EventYear.put(14, 1983);
		//item15
		 eCal.Eventmonth.put(15, MONTH_OCT );
		 eCal.EventYear.put(15, 1984);
		//item16
		 eCal.Eventmonth.put(16, MONTH_UNKNOWN );
		 eCal.EventYear.put(16, 1985);
		//item17
		 eCal.Eventmonth.put(17, MONTH_UNKNOWN );
		 eCal.EventYear.put(17, 1986); 
		//item18
		 eCal.Eventmonth.put(18, MONTH_DEC );
		 eCal.EventYear.put(18, 1988);
		//item19
		 eCal.Eventmonth.put(19, MONTH_UNKNOWN );
		 eCal.EventYear.put(19, 1989);
		//item20
		 eCal.Eventmonth.put(20, MONTH_UNKNOWN );
		 eCal.EventYear.put(20, 1991);
		//item21
		 eCal.Eventmonth.put(21, MONTH_MAY );
		 eCal.EventYear.put(21, 1991);
		//item22
		 eCal.Eventmonth.put(22, MONTH_DEC );
		 eCal.EventYear.put(22, 1992);
		//item23
		 eCal.Eventmonth.put(23, MONTH_DEC );
		 eCal.EventYear.put(23, 1994);
		//item24
		 eCal.Eventmonth.put(24, MONTH_UNKNOWN );
		 eCal.EventYear.put(24, 1994);
		//item25
		 eCal.Eventmonth.put(25, MONTH_SEP );
		 eCal.EventYear.put(25, 1995);
		//item26
		 eCal.Eventmonth.put(26, MONTH_JAN );
		 eCal.EventYear.put(26, 1996);
		//item27
		 eCal.Eventmonth.put(27, MONTH_UNKNOWN );
		 eCal.EventYear.put(27, 1996);
		//item28
		 eCal.Eventmonth.put(28, MONTH_UNKNOWN );
		 eCal.EventYear.put(28, 2001);
		//item29
		 eCal.Eventmonth.put(29, MONTH_SEP );
		 eCal.EventYear.put(29, 2001);
		//item30
		 eCal.Eventmonth.put(30, MONTH_JULY );
		 eCal.EventYear.put(30, 2003);
		//item31
		 eCal.Eventmonth.put(31, MONTH_MAY );
		 eCal.EventYear.put(31, 2004);
		//item32
		 eCal.Eventmonth.put(32, MONTH_UNKNOWN );
		 eCal.EventYear.put(32, 2004);
		 
		 
		return eCal;
		
	}
	
	
//Deprecated by Arvind 1/10/12
//	private String TEMP_UNAME = "temp_uname"; //user entered
//	private String TEMP_PASS = "temp_pass";   //user
//	private String SET_UNAME;				  //db	
//	private String SET_PASS;				  //db
//
//	public Constants(String uname, String pass) {
//		Log.d(TAG,"username:" + uname + "password" + pass);
//        this.TEMP_UNAME = md5(uname);
//        this.TEMP_PASS=md5(pass);
//        this.SET_UNAME="ibme";
//        this.SET_PASS="test";
//    }
//	 public String getUname() {
//	        return this.TEMP_UNAME;
//	    }
//	 public String getPass() {
//	        return this.TEMP_PASS;
//	    }
//	
//	
////	 private Cursor getAuth() {
////	        // Run query
////	        Uri uri = CVDBase.CONTENT_URI_CREDENTIALS;
////	        String[] projection = new String[] { 
////	            CVDBase.USER_ID};
////	     // Constructs a selection clause with a replaceable parameter
////	        String mSelectionClause =  CVDBase.USER_ID + "= ?";
////	        
//////	        String selection = CVDBase.USER_ID + " = '"
//////	            + (pUser) + "'";
////	     // Defines an array to contain the selection arguments
////	        String[] selectionArgs = users;
////	        
////	        Cursor mCursor = getContentResolver().query(
////	        	    uri,  // The content URI of the words table
////	        	    projection,                       // The columns to return for each row
////	        	    mSelectionClause                   // Either null, or the word the user entered
////	        	    selectionArgs,                    // Either empty, or the string the user entered
////	        	    null);                       // The sort order for the returned rows
////	        
////	        return managedQuery(uri, projection, selection, selectionArgs,
////	            sortOrder);
////	      }
//	
//	private String md5(String in) {
//		MessageDigest digest;
//		try {
//			digest = MessageDigest.getInstance("MD5");
//			digest.reset();
//			digest.update(in.getBytes());
//
//			byte[] a = digest.digest();
//
//			int len = a.length;
//
//			StringBuilder sb = new StringBuilder(len << 1);
//
//			for (int i = 0; i < len; i++) {
//
//				sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
//
//				sb.append(Character.forDigit(a[i] & 0x0f, 16));
//
//			}
//
//			return sb.toString();
//
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//
//	}
//
//	public Boolean verify() {
//		// match md5(uname) and md5(pass) with those in the database.
//		// for now hardcode the string to see the match
//		//String sArray[] = new String[] { "Array 1", "Array 2", "Array 3" };
//		Log.d(TAG, getUname()); //gives the MD5 encrypted Username
//		Log.d(TAG, getPass());	//gives the MD5 encrypted password
//		Log.d(TAG,SET_UNAME); //should be pulled from user db
//		Log.d(TAG,SET_PASS); // should be pulled from user db
//		Log.d(TAG,md5(SET_UNAME));
//		Log.d(TAG,md5(SET_PASS));
//		//ideally - match(getUname(), getPass()) in sqlite db and return { "Asha name", "Asha id", "Asha's village" } 
//		
//		
//		if (getUname().equals(md5(SET_UNAME)) && getPass().equals(md5(SET_PASS))) {
//			Log.d(TAG, "credentials authenticated");
//			return true;
//					
//		} else {
//			
//			Log.d(TAG, "credentials don't match");
//			return false;
//		}
//		//ideally - return List<String> with {result(true/false) , ASha name, Asha ID , ASha village, timestamp of start of usage}
//	}
	
	
}
