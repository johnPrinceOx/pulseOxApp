package db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//import Constants;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class UserList {
		//throws user details in local db 
	 Context myContext;
	
	 	
     private String enteredUname;
     private String enteredUpass;
     private String uLocality,uType,uID,uPHC = "";
     private boolean isAuthenticated = false;
	
     
     public UserList(Context myContext, String enteredUname,
 			String enteredUpass) {
 		super();
 		this.myContext = myContext;
 		this.enteredUname = enteredUname;
 		this.enteredUpass = enteredUpass;
 		//checkDBforUserList();
 		//insertMissingData();
 		//authenticate from db
 		//all that's fine - but here we will just do
 		
 	}

     
	// list of registered users
	private final static String[] users = {"john", "arv89", "elina",
		"mdv", "elinaC", "htdp", "htpm",
		"pmasha","ssasha","jhasha","djasha","msasha","svasha","nnasha","gkasha","nrasha","pvasha","gsasha" };
	private final static String[] pass = { "john","test", "test2",
		"test3",  "test2c", "dht13", "mht14",
		"ashak", "ashal","asham","ashan","ashao","ashap","ashaq","ashar","ashas","ashat","ashau" };
	
	private final static String[] userID = {"13", "99", "98",
		"91",  "51", "52", "53",
		"21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };
	private final static String[] type = { Constants.USER_TYPE_ADMIN, Constants.USER_TYPE_ADMIN, Constants.USER_TYPE_HOSPITALSTREAM,
		Constants.USER_TYPE_HOSPITALSTREAM, Constants.USER_TYPE_COMMUNITYSTREAM,Constants.USER_TYPE_DOCTOR,Constants.USER_TYPE_DOCTOR,
		Constants.USER_TYPE_ASHA, Constants.USER_TYPE_ASHA, Constants.USER_TYPE_ASHA, Constants.USER_TYPE_ASHA, Constants.USER_TYPE_ASHA,
		Constants.USER_TYPE_ASHA, Constants.USER_TYPE_ASHA, Constants.USER_TYPE_ASHA, Constants.USER_TYPE_ASHA, 
		Constants.USER_TYPE_ASHA, Constants.USER_TYPE_ASHA };
	private final static String[] location = { "oxf", "oxf", "oxf",
		"oxf", "oxf", "Gollavanithippa", "Yendagandi",
		"Ardhavaram", "Saripalle", "Kommara", "Valluru", "L.V.N.Puram", 
		"Gutlapadu", "Nagidi Palem", "Gollavanithippa", "Yandagandi", "Panduva", "Sagupadu" };
	private final static int[] under_phc = { Constants.PHC_DEFAULT, Constants.PHC_DEFAULT, Constants.PHC_DEFAULT,
		Constants.PHC_DEFAULT, Constants.PHC_DEFAULT, Constants.PHC_GOLLAVANITHIPPA, Constants.PHC_YANDAGANDI,
		Constants.PHC_GANPAVARAM, Constants.PHC_GANPAVARAM, Constants.PHC_GANPAVARAM, Constants.PHC_GANPAVARAM, Constants.PHC_GOLLAVANITHIPPA, 
		Constants.PHC_GOLLAVANITHIPPA, Constants.PHC_GOLLAVANITHIPPA, Constants.PHC_GOLLAVANITHIPPA, Constants.PHC_YANDAGANDI, 
		Constants.PHC_YANDAGANDI, Constants.PHC_YANDAGANDI };
	

	public static String[] getUsers() {
		return users;
	}

	public int checkUser(String pUser) {
		/*returns pos of User in Array*/
		int returnStatement = -1;
		int count =-1;
		for (String u1 : users) {
			count++;
			if (pUser.equalsIgnoreCase(u1)) {
				returnStatement = count;
				break;
			}

		}
		return returnStatement;
	}
	
	public boolean checkForUserAuth() {
		
		//scan through a list and check if user is there
		int UserPosition = checkUser(enteredUname);
		
		if(UserPosition!=-1)
		{
			//if user exists,does password match ?
			String storedPassword = retrieveEncrpytedPass(UserPosition);
			
			//location and type can be passed on to the next activity
			uLocality = retrieveLocation(UserPosition);
			uType = retrieveType(UserPosition);		
			uID = retrieveUID(UserPosition);
			uPHC = retrieveUPHC(UserPosition);
			if (storedPassword.equals(md5(enteredUpass))) {
				Log.d(Constants.TAG, "credentials authenticated");
				isAuthenticated= true;
						
			} else {
				
				Log.d(Constants.TAG, "credentials don't match");
				isAuthenticated= false;
			}
			//pull up his auth details and commit to shared preferences
			
		}
		return isAuthenticated;
	}

	
	

	/**
	 * @return the uID
	 */
	public String getuID() {
		return uID;
	}

	/**
	 * @return the uLocality
	 */
	public String getuLocality() {
		return uLocality;
	}

	/**
	 * @return the uType
	 */
	public String getuType() {
		return uType;
	}

	public String getUnderPhc() {
		return uPHC;
	}
	
	private String retrieveLocation(int userPosition) {
		return location[userPosition];
		
	}

	private String retrieveUID(int userPosition) {
		return userID[userPosition];
	}
	
	private String retrieveType(int userPosition) {
		return type[userPosition];
		
	}

	private String retrieveEncrpytedPass(int uPos) {
		
		return md5(pass[uPos]);
	}
	private String retrieveUPHC(int userPosition) {
		return String.valueOf(under_phc[userPosition]);
	}
	

	/**
	 * @return the pass
	 */
	public String getPass() {
		return md5(enteredUpass);
	}

	private String md5(String in) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
			digest.reset();
			digest.update(in.getBytes());

			byte[] a = digest.digest();

			int len = a.length;

			StringBuilder sb = new StringBuilder(len << 1);

			for (int i = 0; i < len; i++) {

				sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));

				sb.append(Character.forDigit(a[i] & 0x0f, 16));

			}

			return sb.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return null;

	}
//	private void insertUserList()
//	{
//		ContentValues values = new ContentValues();
//		values.put(CVDBase.USER, "");
//		values.put(CVDBase.TYPE, "3/10/12");
//        values.put(CVDBase.VILLAGE, "23:00");
//        values.put(CVDBase.USER_ID, "23:01");
//        values.put(CVDBase.USER_PASS, "145");        
//        //values.put(CVDBase.CREATED, System.currentTimeMillis());
//        myContext.getContentResolver().insert(CVDBase.CONTENT_URI_BG, values);
//        Log.i(Constants.TAG, "insertion successful");
//	}
	
	
	private boolean isUserIDPresent() {
		 Uri uri = CVDBase.CONTENT_URI_CREDENTIALS;
	        String[] projection = new String[] { 
	            CVDBase.USER_ID};
	        String[] mSelectionArgs = getUsers();
	        String mSelectionClause =  CVDBase.USER_ID + "= ?";
	        Cursor mCursor = myContext.getContentResolver().query(
	        	    uri,  // The content URI of the words table
	        	    projection,                       // The columns to return for each row
	        	    mSelectionClause,                   // Either null, or the word the user entered
	        	    mSelectionArgs,                    // Either empty, or the string the user entered
	        	    null);                       // The sort order for the returned rows
	        
	        if (null == mCursor) {
	            /*
	             * Insert code here to handle the error. Be sure not to use the cursor! You may want to
	             * call android.util.Log.e() to log this error.
	             *
	             */
	        	return false;
	        // If the Cursor is empty, the provider found no matches
	        } else if (mCursor.getCount() < 1) {

	            /*
	             * Insert code here to notify the user that the search was unsuccessful. This isn't necessarily
	             * an error. You may want to offer the user the option to insert a new row, or re-type the
	             * search term.
	             */
	        	return false;
	        } else {
	            // Insert code here to do something with the results
	        	return true;
	        }
		
	}
	
}
