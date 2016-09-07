package db.utils;

import android.os.Environment;

import org.pnuemoniaproject.hathi.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import db.CVDVariables;

/**
 * Created by wolf4510 on 07/09/2016.
 */
public class johnUsefulMethods {


    public static void saveInCSVFile(ArrayList<String> basicData) throws IOException {

        String Encounters = "/sdcard/PatientBasicDetails.csv";
        FileWriter writer = new FileWriter(Encounters);
        basicData.add("\n");

        CSVUtils.writeLine(writer, Arrays.asList("Date", "Patient ID", "First Name", "Last Name", "Age", "Gender", "Current Time In Millis"));
        CSVUtils.writeLine(writer, basicData);
    }


    public static int writeData(ArrayList<String> data) {

        int rowPointer = -1;
        PrintWriter csvWriter;
        //String outputDir = new File(Environment.getExternalStorageDirectory().toString(), "MobOx").toString();
        String outputDir = new File(Environment.getExternalStorageDirectory().toString(), "MobOx").toString();
        try {

            StringBuffer oneLineStringBuffer = new StringBuffer();

            File file = new File("/sdcard/PatientDetailsFinal.csv");
            if (!file.exists()) {
                file = new File("/sdcard/PatientDetailsFinal.csv");
            }
            csvWriter = new PrintWriter(new FileWriter(file, true));


            //csvWriter.print(data);
            //csvWriter.append('\n');
            //csvWriter.print("world");
            oneLineStringBuffer.append(data);
            // oneLineStringBuffer.append("\n");

            StringBuffer newStringBuffer1 = new StringBuffer(oneLineStringBuffer.toString().replaceAll("\\[", ""));
            StringBuffer newStringBuffer2 = new StringBuffer(newStringBuffer1.toString().replaceAll("\\]", ""));
            csvWriter.println(newStringBuffer2);

            //csvWriter.flush();
            csvWriter.close();


            rowPointer = count("/sdcard/PatientDetailsFinal.csv");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rowPointer;

    }


    public static int count(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }

    public static void initializePatientVariables(){
        CVDVariables.J_VARIABLES_ROW_POINTER = "";
        CVDVariables.J_VARIABLES_FIRST_NAME = "";
        CVDVariables.J_VARIABLES_LAST_NAME = "";
        CVDVariables.J_VARIABLES_P_ID = "";
    }


}
