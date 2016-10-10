package org.pnuemoniaproject.hathi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import db.CVDVariables;
import db.utils.GMailSender;
import db.utils.Mail;
import db.utils.SendMailTask;
import db.utils.johnUsefulMethods;
import devices.RecordActivity;

public class firstMenu extends AppCompatActivity {

    private static Button newPatient, oldPatient, quickMeasure;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_menu);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        johnUsefulMethods.initializePatientVariables();

        newPatient = (Button) findViewById(R.id.createNew);
        oldPatient = (Button) findViewById(R.id.lookOld);
        quickMeasure = (Button) findViewById(R.id.quick);


        newPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(firstMenu.this, johnactivity.class));
                finish();
            }
        });

        oldPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(firstMenu.this, johnactivity.class));
                //finish();
            }
        });

        quickMeasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Log.i("SendMailActivity", "Send Button Clicked.");

                String fromEmail = "reports@starstudy.org";
                String fromPassword = "R3p0rt5z";

                List<String> toEmailList = Arrays.asList("john.prince.ox@gmail.com", "john.prince@eng.ox.ac.uk");
                Log.i("SendMailActivity", "To List: " + toEmailList);
                String emailSubject = "Test Subject";
                String emailBody = "Test Content \n \n \n If you get this, then the app is working!";
                new SendMailTask(firstMenu.this).execute(fromEmail,
                        fromPassword, toEmailList, emailSubject, emailBody);

                String preamble = "/storage/emulated/0/";
                String attachResult = sendEmailAttachment("john.prince@eng.ox.ac.uk", "Test Subject", "Main Text", preamble + "PatientDetailsFinal.csv");
                Log.i("Attach Result", attachResult);
                Toast.makeText(firstMenu.this, "Email Attachment: "+ attachResult, Toast.LENGTH_SHORT).show();

                //finish();
            }
        });

    }


    public static String sendEmailAttachment(String emailTo, String subject, String text, String filepath){
        //Sending an email with a file attachment

        final String username = "reports@starstudy.org";
        final String password = "R3p0rt5z";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {


            Multipart mp = new MimeMultipart();

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("reports@starstudy.org"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(emailTo));
            message.setSubject(subject);


            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setText(text);
            mbp1.setContent(text, "text/html");
            mp.addBodyPart(mbp1);

            MimeBodyPart mbp = new MimeBodyPart();
            FileDataSource fds = new FileDataSource(filepath);
            mbp.setDataHandler(new DataHandler(fds));
            mbp.setFileName(fds.getName());
            mp.addBodyPart(mbp);

            message.setContent(mp);

            Transport.send(message);
            return "Email Sent";

        } catch (MessagingException e) {

            return "Error sending email: "+ e.toString();
            //			throw new RuntimeException(e);
        }
    }


}

