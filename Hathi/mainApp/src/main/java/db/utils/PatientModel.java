package db.utils;

public class PatientModel {

	PatientModel(PatientModel copyModel) {

		this._id = copyModel._id;
		//this.hh_id = copyModel.hh_id;
		this.patient_fname = copyModel.patient_fname;
		this.patient_lname = copyModel.patient_lname;
		this.age = copyModel.age;
		this.gender = copyModel.gender;
		//this.hdc = copyModel.hdc;
		//this.rdc = copyModel.rdc;
		this.encounter_status = copyModel.encounter_status;
		//this.is_head_of_hh = copyModel.is_head_of_hh;
		this.patient_ID = copyModel.patient_ID;
		this.locality_id = copyModel.locality_id;
	}

	public PatientModel(long patient_ID, String patient_fname,
			String patient_lname, String localityname, int status) {

		this.patient_ID = patient_ID;
		this.patient_fname = patient_fname;
		this.patient_lname = patient_lname;
		this.localityname = localityname;
		this.status = status;

	}

	public int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getPatient_ID() {
		return patient_ID;
	}

	public void setPatient_ID(long patient_ID) {
		this.patient_ID = patient_ID;
	}

	/* Constants */
	// interview Status
	public final static int DEFAULT_ENCOUNTER = -1; // default
													// YELLOW
	public final static int CVD_BASELINE = 1; // GREEN TICK
	public final static int NPHW_ASHA_VISIT_FIRST = 2;
	public final static int NPHW_ASHA_VISIT_SECOND = 3;
	public final static int NPHW_ASHA_VISIT_THIRD = 4;
	public final static int DOCTOR_VISIT_FIRST = 5;
	public final static int DOCTOR_VISIT_SECOND = 6;
	public final static int DOCTOR_VISIT_THIRD = 7;

	public final static int ENCOUNTER = 1;

	// Household Disposition Code
	//public final static int HDC_DEFAULT = -1;// default
	//public final static int HDC_1_OUT_AND_REPLACED = 1;
	//public final static int HDC_2_NEW_HOUSEHOLD = 2;
	//public final static int HDC_3_REFUSED_TO_TAKE_PART = 3;

	// Respondent Disposition Code
	//public final static int RDC_DEFAULT = -1;// default
	//public final static int RDC_1_NOT_AT_HOME = 1;
	//public final static int RDC_2_MOVED_OUT = 2;
	//public final static int RDC_3_NOT_ELIGIBLE = 3;
	//public final static int RDC_4_NOT_ALIVE = 4;
	//public final static int RDC_5_INCAPACITATED = 5;
	//public final static int RDC_6_REFUSED_TO_TAKE_PART = 6;

	// public final static int RDC_4_INTERVIEW_INCOMPLETE = 4;

	public PatientModel() {
		//	rdc = -1;
		encounter_status = -1;
		//hdc = -1;
		//is_head_of_hh = false;
		patient_fname = "";
		patient_lname = "";
		gender = "";
		patient_ID = -1;
		locality_id = -1;

	}

	long _id;
	String patient_fname;
	String patient_lname;
	int age;
	String gender;
	long locality_id;

	

	public String localityname;

	String last_encounter;

	public String getLast_encounter() {
		return last_encounter;
	}

	public void setLast_encounter(String last_encounter) {
		this.last_encounter = last_encounter;
	}

	public String getLocalityname() {
		return localityname;
	}

	public void setLocalityname(String localityname) {
		this.localityname = localityname;
	}

	public long getLocality_id() {
		return locality_id;
	}

	public void setLocality_id(long locality_id) {
		this.locality_id = locality_id;
	}

	//boolean is_head_of_hh;
	//long hh_id;
	//int hdc;
	//int rdc;
	int encounter_status;

	public int getEncounter_status() {
		return encounter_status;
	}

	public void setEncounter_status(int encounter_status) {
		this.encounter_status = encounter_status;
	}

	long patient_ID;

	public long getConsent_no() {
		return consent_no;
	}

	public void setConsent_no(long consent_no) {
		this.consent_no = consent_no;
	}

	long consent_no;

	public long get_id() {
		return _id;
	}

	public void set_id(long _id) {
		this._id = _id;
	}

	public String getPatient_fname() {
		return patient_fname;
	}

	public void setPatient_fname(String patient_fname) {
		this.patient_fname = patient_fname;
	}

	public String getPatient_lname() {
		return patient_lname;
	}

	public void setPatient_lname(String patient_lname) {
		this.patient_lname = patient_lname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

//	public boolean getIs_head_of_hh() {
//		return is_head_of_hh;
//	}

//	public void setIs_head_of_hh(boolean is_head_of_hh) {
//		this.is_head_of_hh = is_head_of_hh;
//	}

//	public long getHh_id() {
//		return hh_id;
//	}

//	public void setHh_id(long hh_id) {
//		this.hh_id = hh_id;
//	}

//	public int getHdc() {
//		return hdc;
//	}

//	public void setHdc(int hdc) {
//		this.hdc = hdc;
//	}

//	public int getRdc() {
//		return rdc;
//	}

//	public void setRdc(int rdc) {
//		this.rdc = rdc;
//	}

	public String getPatientFullName() {

		return patient_fname + " " + patient_lname;

	}
	
	
	private String telugu_name;

	public String getTelugu_name() {
		return telugu_name;
	}

	public void setTelugu_name(String telugu_name) {
		this.telugu_name = telugu_name;
	}
	

	

}
