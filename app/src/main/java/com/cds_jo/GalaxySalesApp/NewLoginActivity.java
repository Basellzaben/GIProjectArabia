package com.cds_jo.GalaxySalesApp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.Authenticator;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cds_jo.GalaxySalesApp.NewPackage.GalaxyNewHomeActivity;
import com.cds_jo.GalaxySalesApp.NewPackage.GloblaVar;
import com.cds_jo.GalaxySalesApp.NewPackage.MainNewActivity;
import com.cds_jo.GalaxySalesApp.assist.CallWebServices;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;


import javax.mail.Transport;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;



import static android.Manifest.permission.READ_CONTACTS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class NewLoginActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {



    private static final int REQUEST_READ_CONTACTS = 0;
    private static final String[] DUMMY_CREDENTIALS = new String[]{

    };
    String startdate,allowday;
     Handler _handler = new Handler();
    TextView tv;
   // final public  String staticLink="http://10.0.1.60:6325/";
    final public  String staticLink="http://94.249.83.196:6326/";
    RequestQueue requestQueue;

    Drawable greenProgressbar;
    RelativeLayout.LayoutParams lp;
    private UserLoginTask mAuthTask = null;
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
   SqlHandler sqlHandler;
    String Apk;

    String email;
    String password;
     String urltime = "http://94.249.83.196:6326/api/ApkVersions/GetApkVersion/";

    /*
    SqlHandlerMed sqlHandlerMed;
*/
    void hideSoftKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception ex) {


        }

    }
    String  Apknew;
    String  Apkold;
    private ImageView Login_Img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_new_login);
        setContentView(R.layout.activity_galaxy_login);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(NewLoginActivity.this);
   /*     Apkold = sharedPreferences.getString("AppVersion", "0");


/*

        if(ComInfo.ComNo==16){
            urltime+="1";
        }else if(ComInfo.ComNo==13){
            urltime+="2";
        }

        RequestQueue requestQueue = Volley.newRequestQueue(NewLoginActivity.this);

        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, urltime, (String) null, new Response.Listener<JSONObject>() {
            // Takes the response from the JSON request
            @Override
            public void onResponse(JSONObject response) {
                try {
                      Apknew = response.getString("Version");

                    if(!Apkold.equals(Apknew))
                    {
                    AlertDialog alertDialog = new AlertDialog.Builder(
                                NewLoginActivity.this).create();
                    alertDialog.setTitle("تحديث التطبيق");
                    alertDialog.setMessage("يتوفر تحديث جديد للتطبيق قد يحتوي على حل بعض المشاكل التي تواجهها بالاضافة الى بعض التعديلات التي توفر تجربة افضل لاستخدام التطبيقَ. يمكنك تثبيت التحديث بعد تنزيله من خلال ملف التنزيلات");
                    alertDialog.setIcon(R.drawable.updated);
                    alertDialog.setButton("تذكيري لاحقا", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://we.tl/t-WZLwTBlzkD")));


                    }
                    });
                    alertDialog.setButton2("مـوافق", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            get_name_Apk(urltime);
                        }
                    });
                    alertDialog.show();


                }
                }
                // Try and catch are included to handle any errors due to JSON
                catch (JSONException e) {
                    // If an error occurs, this prints the error to the log
                    new SweetAlertDialog(NewLoginActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                            .setContentText(String.valueOf(getResources().getString(R.string.errorup)))
                            .setCustomImage(R.drawable.error_new)
                            .setConfirmText(String.valueOf(getResources().getString(R.string.back)))
                            .show();

                }
            }
        },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        new SweetAlertDialog(NewLoginActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)

                                .setContentText(String.valueOf(getResources().getString(R.string.errorup)))

                                .setCustomImage(R.drawable.error_new)
                                .setConfirmText(String.valueOf(getResources().getString(R.string.back)))
                                .show();

                    }
                }
        );
        requestQueue.add(obreq);
*/





        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor2 = preferences.edit();
        editor2.putString("update","0");
        editor2.apply();


        Get_ServerDateTimeandCallCheckPermision();
       // Toast.makeText(this, "معلومات الدخول غير صghjgحيحة", Toast.LENGTH_SHORT).show();


        hideSoftKeyboard();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // Set up the login form.
        //startActivity(new Intent(this,GalaxyLoginActivity.class));
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
         sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Login_Img = (ImageView) findViewById(R.id.imageView4);
        ShimmerTextView shimmer_tv = (ShimmerTextView) findViewById(R.id.shimmer_tv);
        Shimmer shimmer = new Shimmer();
        shimmer.setRepeatCount(-1)
                .setDuration(2000)
                .setStartDelay(1500)
                .setDirection(Shimmer.ANIMATION_DIRECTION_RTL);
        shimmer_tv.setTypeface(Typeface.createFromAsset(this.getAssets(), "Hacen Tunisia Lt.ttf"));
        shimmer.start(shimmer_tv);

        mEmailView.setText(sharedPreferences.getString("UserNameD", "admin"));
        mPasswordView.setText(sharedPreferences.getString("User_Password", "").replaceAll("[^\\d.]", ""));

        this.setTitle(sharedPreferences.getString("CompanyNm", ""));
        populateAutoComplete();


        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Login", "No");
        editor.commit();



        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        //   Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        Login_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            //   Get_ServerDateTimeandCallCheckPermision();
                EditText  mEmailView = (EditText) findViewById(R.id.email);

                SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(NewLoginActivity.this);
                String allowday= sharedPreferences1.getString("allowday", "-1");
                String startdate= sharedPreferences1.getString("startdate", "-1");
                String ServerDate = DB.GetValue(NewLoginActivity.this, "ServerDateTime", "SERVERDATE", "1=1");


                if(allowday.equals("-1") || startdate.equals("-1") ||ServerDate.equals("-1") )
                {
                    attemptLogin();

                }
              else  if (!mEmailView.toString().equalsIgnoreCase("admin")) {

                     // ServerDate  = sharedPreferences1.getString("SERVERDATE", "-1");

                    int day = 0;
                    if (startdate != null && ServerDate != null && !allowday.equals("-1"))
                    {
                          day = get_count_of_days(ServerDate,startdate);
                    }
                    else
                        allowday = "-1";
                    // updatecominfo();
                    if (allowday.equals("-1") || startdate.equals("-1")) {
                        attemptLogin();
                    } else {
                        if (day > Integer.parseInt(allowday) || day == 0)
                            Toast.makeText(NewLoginActivity.this, "لا تملك صلاحية أستخدام للنظام ", Toast.LENGTH_SHORT).show();
                        else
                            attemptLogin();
                    }
                }else{
                    attemptLogin();
                }
               // attemptLogin();



            }
        });

               /* mLoginFormView = findViewById(R.id.login_form);
                mProgressView = findViewById(R.id.login_progress);*/


    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */


    private void attemptLogin() {
        try {
            // Reset errors.
            mEmailView.setError(null);
            mPasswordView.setError(null);

            // Store values at the time of the login attempt.
             email = mEmailView.getText().toString();
             password = mPasswordView.getText().toString();


            boolean cancel = false;
            View focusView = null;

            // Check for a valid password, if the user entered one.
            if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
                mPasswordView.setError(getString(R.string.error_invalid_password));
                focusView = mPasswordView;
                cancel = true;
            }

            // Check for a valid email address.
            if (TextUtils.isEmpty(email)) {
                mEmailView.setError(getString(R.string.error_field_required));
                focusView = mEmailView;
                cancel = true;
            }

            if (cancel) {
                // There was an error; don't attempt login and focus the first
                // form field with an error.
                focusView.requestFocus();
            } else {
                if (email.toString().equalsIgnoreCase("admin"))
                {
                    ComInfo.ComNo = 1;
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("UserName", "Administrator System ");
                    editor.putString("UserID", "admin");
                    editor.putString("Login", "Yes");
                    editor.putString("TypeRep","1");
                    editor.commit();
                    Intent k = new Intent(NewLoginActivity.this, GalaxyNewHomeActivity.class);
                    startActivity(k);
                    return;
                }

                if (email.toString().equalsIgnoreCase("admin") && (password.toString().equalsIgnoreCase("") || (password.toString().equalsIgnoreCase("1")))) {
                    ComInfo.ComNo = 1;
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("UserName", "Administrator System ");
                    editor.putString("UserID", "admin");
                    editor.putString("Login", "Yes");
                    editor.putString("TypeRep","1");
                    editor.commit();
                    Intent k = new Intent(NewLoginActivity.this, GalaxyNewHomeActivity.class);
                    startActivity(k);
                    return;
                }
                else {
                    SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(NewLoginActivity.this);
                    //String UserNameD= sharedPreferences.getString("TypeRep", "-1");
                    String Userl="-1";

                    SharedPreferences.Editor editor1 = sharedPreferences1.edit();


                    editor1.commit();
                  String  UserNameD = sharedPreferences1.getString("UserNameD", "-1");
                   String  count = (DB.GetValue(NewLoginActivity.this, "manf", "ifnull(count(*),0)", "1=1"));
                   String  count1 = (DB.GetValue(NewLoginActivity.this, "manfN", "ifnull(count(*),0)", "1=1"));
                  //mohw55
                  if(UserNameD.equals(email.toString())&&((!count.equals("0"))||(!count1.equals("0"))))
                  {
                      Userl="1";
                  }
                    ComInfo.ComNo = Integer.parseInt(DB.GetValue(NewLoginActivity.this, "ComanyInfo", "CompanyID", "1=1"));
                    if (ComInfo.ComNo == Companies.Atls.getValue() ) {

                                SqlHandler sqlHandler = new SqlHandler(this);

                                String query = "SELECT  name   from  manf where man = '" + email.toString() + "' And password='" + mPasswordView.getText().toString().replaceAll("[^\\d.]", "") + "'";
                                if (GlobaleVar.LanType == 1)
                                    query = "SELECT  name   from  manf where (man = '" + email.toString() + "'  or LoginName = '" + email.toString() + "') And password='" + mPasswordView.getText().toString() + "'";
                                else
                                    query = "SELECT  MEName   from  manf where (man = '" + email.toString() + "'  or LoginName = '" + email.toString() + "') And password='" + mPasswordView.getText().toString() + "'";
                                String s;

                                s = (DB.GetValue(NewLoginActivity.this, "manf", "man", "man ='" + email.toString() + "' or LoginName = '" + email.toString() + "'"));

                                Cursor c1 = sqlHandler.selectQuery(query);


                                if (c1 != null && c1.getCount() > 0) {
                                    c1.moveToFirst();
                                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();


                                    editor.putString("UserName", String.valueOf(c1.getString(0)));


                                    editor.putString("UserID", s.trim());
                                    editor.putString("User_Password", mPasswordView.getText().toString().trim());
                                    editor.putString("Login", "Yes");
                                    editor.commit();

                                    c1.close();


                                    ComInfo.ComNo = Integer.parseInt(DB.GetValue(NewLoginActivity.this, "ComanyInfo", "CompanyID", "1=1"));

                         /*
                            ActivityManager activitymanager;
                            List<ActivityManager.RunningAppProcessInfo> RAP;
                            activitymanager = (ActivityManager)getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);

                            RAP = activitymanager.getRunningAppProcesses();
                            for(ActivityManager.RunningAppProcessInfo processInfo: RAP ){

                               if (processInfo.processName.equalsIgnoreCase("AutoPostLocation")){
                                 //  stopService(new Intent(NewLoginActivity.this, AutoPostLocation.class));


                               }

                            }*/

                                    /*if(ComInfo.ComNo==4) {*/
                                    try {
                                        //

                                        //     stopService(new Intent(NewLoginActivity.this, AutoPostLocation.class));
                                        //    startService(new Intent(NewLoginActivity.this, AutoPostLocation.class));


                                        // }
                                    } catch (Exception ex) {
                                        Toast.makeText(this, ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                                    }


                                    Intent k = new Intent(NewLoginActivity.this, GalaxyNewHomeActivity.class);
                                    startActivity(k);

                                    return;
                                } else {
                                    new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                                            .setTitleText("نظام المبيعات")
                                            .setContentText("معلومات الدخول غير صحيحة ")
                                            .setCustomImage(R.drawable.error_new)
                                            .show();

                                    // Toast.makeText(this, "معلومات الدخول غير صحيحة", Toast.LENGTH_SHORT).show();
                                    return;
                                }


                    }
                    else {
                        if (Userl.equals("-1")) {
                            final Handler _handler = new Handler();
                            sqlHandler = new SqlHandler(this);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    CallWebServices ws = new CallWebServices(NewLoginActivity.this);
                                    ws.GetUserDefinition(email.toString());
                                    try {
                                        Integer i;
                                        String q = "";
                                        JSONObject js = new JSONObject(We_Result.Msg);
                                        JSONArray Id = js.getJSONArray("Id");
                                        JSONArray NameA = js.getJSONArray("NameA");
                                        JSONArray NameE = js.getJSONArray("NameE");
                                        JSONArray UserName = js.getJSONArray("UserName");
                                        JSONArray Password = js.getJSONArray("Password");
                                        JSONArray TypeRep = js.getJSONArray("TypeRep");


                            /*      q = "Delete from GetUserDefinition";
                                  sqlHandler.executeQuery(q);
                                  q = "delete from sqlite_sequence where name='GetUserDefinition'";
                                  sqlHandler.executeQuery(q);*/

                                        for (i = 0; i < Id.length(); i++) {
                                            //      if (TableNo.get(i).toString().equals("9")){
/*
                                      q = "Insert INTO GetUserDefinition(UserNo,NameA,NameE,UserName,Password,TypeRep) values ('"
                                              + Id.get(i).toString()
                                              + "','" + NameA.get(i).toString()
                                              + "','" + NameE.get(i).toString()
                                              + "','" + UserName.get(i).toString()
                                              + "','" + Password.get(i).toString()
                                              + "','" + TypeRep.get(i).toString()

                                              + "')";
                                      sqlHandler.executeQuery(q);*/
                                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(NewLoginActivity.this);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("UserDId", Id.get(i).toString());
                                            editor.putString("NameA", NameA.get(i).toString());
                                            editor.putString("NameE", NameE.get(i).toString());
                                            editor.putString("UserNameD", UserName.get(i).toString());
                                            editor.putString("PasswordD", Password.get(i).toString());
                                            editor.putString("TypeRep", TypeRep.get(i).toString());
                                            editor.commit();


                                        }
                                        final int total = i;
                                        _handler.post(new Runnable() {

                                            public void run() {
                                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(NewLoginActivity.this);
                                                String UserNameD = sharedPreferences.getString("UserNameD", "-1");
                                                String PasswordD = sharedPreferences.getString("PasswordD", "-1");
                                                String UserDId = sharedPreferences.getString("UserDId", "-1");
                                                //  String  UserName = (DB.GetValue(NewLoginActivity.this, "UserDefinition", "UserName", "1=1"));
                                                //    String  Password2 = (DB.GetValue(NewLoginActivity.this, "UserDefinition", "Password", "UserName='"+email.toString()+"'"));
                                                String UserNo = "-1";// = (DB.GetValue(NewLoginActivity.this, "UserDefinition", "UserNo", "UserName='"+email.toString()+"'and Password='"+password.toString()+"' "));
                                                if (email.toString().equals(UserNameD.toString()) && password.toString().equals(PasswordD.toString())) {
                                                    UserNo = "1";
                                                }
                                                if (!(UserNo.equals("-1"))) {
                                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                                    editor.putString("UserName", email.toString());

                                                    editor.putString("UserID", UserDId);
                                                    editor.putString("User_Password", password.toString());
                                                    editor.putString("Login", "Yes");
                                                    editor.commit();
                                                    Intent k = new Intent(NewLoginActivity.this, UpdateDataToMobileActivity.class);
                                                    startActivity(k);
                                                } else {
                                                    new SweetAlertDialog(NewLoginActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                                                            .setTitleText("نظام المبيعات")
                                                            .setContentText("معلومات الدخول غير صحيحة ")
                                                            .setCustomImage(R.drawable.error_new)
                                                            .show();

                                                    // Toast.makeText(this, "معلومات الدخول غير صحيحة", Toast.LENGTH_SHORT).show();
                                                    return;

                                                }

                                            }
                                        });

                                    } catch (final Exception e) {
                                        _handler.post(new Runnable() {

                                            public void run() {
                                                new SweetAlertDialog(NewLoginActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                                                        .setTitleText("نظام المبيعات")
                                                        .setContentText("معلومات الدخول غير صحيحة ")
                                                        .setCustomImage(R.drawable.error_new)
                                                        .show();

                                                // Toast.makeText(this, "معلومات الدخول غير صحيحة", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                        });
                                    }
                                }
                            }).start();

                        }
                        else {
                            // SharedPreferences sharedPreferences2 = PreferenceManager.getDefaultSharedPreferences(NewLoginActivity.this);
                            //String UserNameD= sharedPreferences.getString("TypeRep", "-1");
                            //   String Userl="-1";

                            String RepType = sharedPreferences1.getString("TypeRep", "-1");
                            if (RepType.equals("1")) {
                                SqlHandler sqlHandler = new SqlHandler(this);

                                String query = "SELECT  name   from  manf where man = '" + email.toString() + "' And password='" + mPasswordView.getText().toString().replaceAll("[^\\d.]", "") + "'";
                                if (GlobaleVar.LanType == 1)
                                    query = "SELECT  name   from  manf where (man = '" + email.toString() + "'  or LoginName = '" + email.toString() + "') And password='" + mPasswordView.getText().toString() + "'";
                                else
                                    query = "SELECT  MEName   from  manf where (man = '" + email.toString() + "'  or LoginName = '" + email.toString() + "') And password='" + mPasswordView.getText().toString() + "'";
                                String s;

                                s = (DB.GetValue(NewLoginActivity.this, "manf", "man", "man ='" + email.toString() + "' or LoginName = '" + email.toString() + "'"));

                                Cursor c1 = sqlHandler.selectQuery(query);


                                if (c1 != null && c1.getCount() > 0) {
                                    c1.moveToFirst();
                                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();


                                    editor.putString("UserName", String.valueOf(c1.getString(0)));


                                    editor.putString("UserID", s.trim());
                                    editor.putString("User_Password", mPasswordView.getText().toString().trim());
                                    editor.putString("Login", "Yes");
                                    editor.commit();

                                    c1.close();


                                    ComInfo.ComNo = Integer.parseInt(DB.GetValue(NewLoginActivity.this, "ComanyInfo", "CompanyID", "1=1"));

                         /*
                            ActivityManager activitymanager;
                            List<ActivityManager.RunningAppProcessInfo> RAP;
                            activitymanager = (ActivityManager)getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);

                            RAP = activitymanager.getRunningAppProcesses();
                            for(ActivityManager.RunningAppProcessInfo processInfo: RAP ){

                               if (processInfo.processName.equalsIgnoreCase("AutoPostLocation")){
                                 //  stopService(new Intent(NewLoginActivity.this, AutoPostLocation.class));


                               }

                            }*/

                                    /*if(ComInfo.ComNo==4) {*/
                                    try {
                                        //

                                        //     stopService(new Intent(NewLoginActivity.this, AutoPostLocation.class));
                                        //    startService(new Intent(NewLoginActivity.this, AutoPostLocation.class));


                                        // }
                                    } catch (Exception ex) {
                                        Toast.makeText(this, ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                                    }


                                    Intent k = new Intent(NewLoginActivity.this, GalaxyNewHomeActivity.class);
                                    startActivity(k);

                                    return;
                                } else {
                                    new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                                            .setTitleText("نظام المبيعات")
                                            .setContentText("معلومات الدخول غير صحيحة ")
                                            .setCustomImage(R.drawable.error_new)
                                            .show();

                                    // Toast.makeText(this, "معلومات الدخول غير صحيحة", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } else {

                                SqlHandler sqlHandler = new SqlHandler(this);
                                String query1;// = "SELECT  name   from  manfN where man = '" + email.toString() + "' And password='" + mPasswordView.getText().toString().replaceAll("[^\\d.]", "") + "'";
                                if (GlobaleVar.LanType == 1)
                                    query1 = "SELECT  name   from  manfN where (man = '" + email.toString() + "'  or UserName = '" + email.toString() + "') And password='" + mPasswordView.getText().toString() + "'";
                                else
                                    query1 = "SELECT  MEName    from  manfN where (man = '" + email.toString() + "'  or UserName = '" + email.toString() + "') And password='" + mPasswordView.getText().toString() + "'";
                                String s1 = (DB.GetValue(NewLoginActivity.this, "manfN", "man", "man ='" + email.toString() + "' or UserName = '" + email.toString() + "'"));
                                Cursor c2 = sqlHandler.selectQuery(query1);


                                if (c2 != null && c2.getCount() > 0) {
                                    c2.moveToFirst();
                                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("UserName", String.valueOf(c2.getString(0)));
                                    editor.putString("UserID", s1.trim());
                                    editor.putString("User_Password", mPasswordView.getText().toString().trim());
                                    editor.putString("Login", "Yes");
                                    editor.commit();

                                    c2.close();


                                    ComInfo.ComNo = Integer.parseInt(DB.GetValue(NewLoginActivity.this, "ComanyInfo", "CompanyID", "1=1"));
                                    Intent k = new Intent(NewLoginActivity.this, GalaxyNewHomeActivity.class);
                                    startActivity(k);

                                    return;
                         /*
                            ActivityManager activitymanager;
                            List<ActivityManager.RunningAppProcessInfo> RAP;
                            activitymanager = (ActivityManager)getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);

                            RAP = activitymanager.getRunningAppProcesses();
                            for(ActivityManager.RunningAppProcessInfo processInfo: RAP ){

                               if (processInfo.processName.equalsIgnoreCase("AutoPostLocation")){
                                 //  stopService(new Intent(NewLoginActivity.this, AutoPostLocation.class));


                               }

                            }*/

                                    /*if(ComInfo.ComNo==4) {*/
                               /*     try {
                                        //

                                        //     stopService(new Intent(NewLoginActivity.this, AutoPostLocation.class));
                                        //    startService(new Intent(NewLoginActivity.this, AutoPostLocation.class));


                                        // }
                                    } catch (Exception ex) {
                                        Toast.makeText(this, ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                                    }*/



                                } else {
                                    new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                                            .setTitleText("نظام المبيعات")
                                            .setContentText("معلومات الدخول غير صحيحة ")
                                            .setCustomImage(R.drawable.error_new)
                                            .show();

                                    // Toast.makeText(this, "معلومات الدخول غير صحيحة", Toast.LENGTH_SHORT).show();
                                /*    Intent k = new Intent(NewLoginActivity.this, GalaxyNewHomeActivity.class);
                                    startActivity(k);*/
                                    return;
                                }
                            }
                        }
                    }

                }


                // mAuthTask.execute((Void) null);
            }

        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public boolean checkServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.cds_jo.GalaxySalesApp.AutoPostLocation"
                    .equalsIgnoreCase(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 0;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {

    }

    public void BtnSkip(View view) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserName", "Test");
        editor.putString("UserID", "26");
        editor.putString("Login", "Yes");
        editor.commit();

        Intent k = new Intent(NewLoginActivity.this, GalaxyNewHomeActivity.class);
        startActivity(k);
    }


    public static void sendEmailWithAttachments(String host, String port,
                                                final String userName, final String password, String toAddress,
                                                String subject, String message, String[] attachFiles)
            throws AddressException, MessagingException {
        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", userName);
        properties.put("mail.password", password);

        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
        Session session = Session.getInstance(properties, auth);

        // creates a new e-mail message
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());

        // creates message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        // messageBodyPart.setContent(message, "text/html");

        // creates multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // adds attachments
       /* if (attachFiles != null && attachFiles.length > 0) {
            for (String filePath : attachFiles) {
                MimeBodyPart attachPart = new MimeBodyPart();

                 try {
                    attachPart.attachFile(filePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                multipart.addBodyPart(attachPart);
            }
        }
*/
        // sets the multi-part as e-mail's content
        msg.setContent(multipart);

        // sends the e-mail
        Transport.send(msg);

    }


    private void sendMail(String email, String subject, String messageBody) {
        Session session = createSessionObject();

        try {
            Message message = createMessage("maen.naamneh@yahoo.com", subject, messageBody, session);
            new SendMailTask().execute(message);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    private Session createSessionObject() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        return Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("zain.m.naamneh@gmail.com", "zain12345");
            }
        });
    }


    private Message createMessage(String email, String subject, String messageBody, Session session) throws
            MessagingException, UnsupportedEncodingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("zain.m.naamneh", "zain12345"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("maen.naamneh@yahoo.com", "mae12n.naamneh@yahoo.com"));
        message.setSubject(subject);
        //message.setContent(subject, "text; charset=utf-8");
        return message;
    }


    public class SendMailTask extends AsyncTask<Message, Void, Void> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(NewLoginActivity.this, "Please wait", "Sending mail", true, false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }

        protected Void doInBackground(javax.mail.Message... messages) {
            try {

                Transport.send(messages[0]);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }

    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

 /*  @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        // finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    @Override
    public void onDestroy() {
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }







    private void Get_ServerDateTimeandCallCheckPermision() {
String q;
      //  final Handler _handler = new Handler();
        sqlHandler = new SqlHandler(this);
        q = "Delete from ServerDateTime";
        sqlHandler.executeQuery(q);

        q = "delete from sqlite_sequence where name='ServerDateTime'";
        sqlHandler.executeQuery(q);


        new Thread(new Runnable() {
            @Override
            public void run() {
                CallWebServices ws = new CallWebServices(NewLoginActivity.this);
                ws.Get_ServerDateTime();
                try {
                    Integer i;
                    String q = "";
                    JSONObject js = new JSONObject(We_Result.Msg);

                    String js_ServerDate = js.getString("SERVERDATE");
                    String js_ServerTime = js.getString("SERVERTIME");
                    String js_MYEAR = js.getString("MYEAR");
                    String js_MMONTH = js.getString("MMONTH");
                    String js_MDAY = js.getString("MDAY");
                    String js_MHOUR= js.getString("MHOUR");
                    String js_MMINUTE = js.getString("MMINUTE");
                    String js_MSECOND = js.getString("MSECOND");
                    String js_DAYWEEK = js.getString("DAYWEEK");

                    q = "INSERT INTO ServerDateTime(ServerDate ,ServerTime,MYEAR,MMONTH,MDAY,MHOUR,MMINUTE,MSECOND,DAYWEEK ) values ('"
                            + js_ServerDate
                            + "','" + js_ServerTime
                            + "','" + js_MYEAR
                            + "','" + js_MMONTH
                            + "','" + js_MDAY
                            + "','" + js_MHOUR
                            + "','" + js_MMINUTE
                            + "','" + js_MSECOND
                            + "','" + js_DAYWEEK
                            + "')";
                    sqlHandler.executeQuery(q);

                    _handler.post(new Runnable() {

                        public void run() {
updatecominfo();
                        }
                    });
                } catch (final Exception e) {
                }


            }
        }).start();
    }


    public void updatecominfo(){
        tv = new TextView(getApplicationContext());
        lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setLayoutParams(lp);
        tv.setPadding(10, 15, 10, 15);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        tv.setTextColor(Color.WHITE);
        tv.setBackgroundColor(Color.BLUE);
        tv.setTypeface(tv.getTypeface(), Typeface.BOLD);

        final Handler _handler = new Handler();
        final ProgressDialog custDialog = new ProgressDialog(NewLoginActivity.this);


        custDialog.setProgressStyle(custDialog.STYLE_HORIZONTAL);
        custDialog.setCanceledOnTouchOutside(false);
        custDialog.setIndeterminate(false);
        custDialog.setProgress(0);
        custDialog.setMax(100);
        custDialog.setMessage("  الرجاء الانتظار ..." + "  العمل جاري على نسخ البيانات  ");
        tv.setText("معلــومــات الـمؤسـسة");
        custDialog.setCustomTitle(tv);
        custDialog.setProgressDrawable(greenProgressbar);
        custDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                CallWebServices ws = new CallWebServices(NewLoginActivity.this);
                ws.GetcompanyInfo();
                try {
                    Integer i;
                    String q = "";
                    JSONObject js = new JSONObject(We_Result.Msg);
                    JSONArray js_ID = js.getJSONArray("ID");
                    JSONArray js_CompanyID = js.getJSONArray("CompanyID");
                    JSONArray js_CompanyNm = js.getJSONArray("CompanyNm");
                    JSONArray js_UserNm = js.getJSONArray("UserNm");
                    JSONArray js_TaxAcc1 = js.getJSONArray("TaxAcc1");
                    JSONArray js_TaxAcc2 = js.getJSONArray("TaxAcc2");
                    JSONArray js_Notes = js.getJSONArray("Notes");
                    JSONArray js_Address = js.getJSONArray("Address");
                    JSONArray js_Permession = js.getJSONArray("Permession");
                    JSONArray js_CompanyMobile = js.getJSONArray("CompanyMobile");
                    JSONArray js_CompanyMobile2 = js.getJSONArray("CompanyMobile2");
                    JSONArray js_SuperVisorMobile = js.getJSONArray("SuperVisorMobile");
                    JSONArray js_SalInvoiceUnit = js.getJSONArray("SalInvoiceUnit");
                    JSONArray js_PoUnit = js.getJSONArray("PoUnit");
                    JSONArray js_AllowSalInvMinus = js.getJSONArray("AllowSalInvMinus");
                    JSONArray js_GPSAccurent = js.getJSONArray("GPSAccurent");
                    JSONArray js_NumOfInvPerVisit = js.getJSONArray("NumOfInvPerVisit");
                    JSONArray js_NumOfPayPerVisit = js.getJSONArray("NumOfPayPerVisit");
                    JSONArray js_EnbleHdrDiscount = js.getJSONArray("EnbleHdrDiscount");
                    JSONArray js_AllowDeleteInvoice = js.getJSONArray("AllowDeleteInvoice");
                    JSONArray js_VisitWeekNo = js.getJSONArray("VisitWeekNo");
                    JSONArray js_ACC_Cash = js.getJSONArray("Acc_Cash");

                    JSONArray LAT = js.getJSONArray("lat");
                    JSONArray LONG = js.getJSONArray("long1");



                    JSONArray allowday = js.getJSONArray("allowday");
                    JSONArray startdate = js.getJSONArray("startdate");



                    SharedPreferences   sharedPreferences = PreferenceManager.getDefaultSharedPreferences(NewLoginActivity.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("allowday", allowday.get(0).toString());
                    editor.putString("startdate", startdate.get(0).toString());

                    editor.commit();


                    q = "Delete from ComanyInfo";
                    sqlHandler.executeQuery(q);
                    q = " delete from sqlite_sequence where name='ComanyInfo'";
                    sqlHandler.executeQuery(q);
                    for (i = 0; i < js_ID.length(); i++) {
                        q = "INSERT INTO ComanyInfo(ID,CompanyID,CompanyNm,UserNm,TaxAcc1,TaxAcc2,Notes,Address,Permession ,CompanyMobile,CompanyMobile2,SuperVisorMobile" +
                                ",SalInvoiceUnit,PoUnit,AllowSalInvMinus,GPSAccurent,NumOfInvPerVisit,NumOfPayPerVisit,EnbleHdrDiscount,AllowDeleteInvoice,VisitWeekNo,Acc_Cash,lat,long) values ('"
                                + js_ID.get(i).toString()
                                + "','" + js_CompanyID.get(i).toString()
                                + "','" + js_CompanyNm.get(i).toString()
                                + "','" + js_UserNm.get(i).toString()
                                + "','" + js_TaxAcc1.get(i).toString()
                                + "','" + js_TaxAcc2.get(i).toString()
                                + "','" + js_Notes.get(i).toString()
                                + "','" + js_Address.get(i).toString()
                                + "','" + js_Permession.get(i).toString()
                                + "','" + js_CompanyMobile.get(i).toString()
                                + "','" + js_CompanyMobile2.get(i).toString()
                                + "','" + js_SuperVisorMobile.get(i).toString()
                                + "','" + js_SalInvoiceUnit.get(i).toString()
                                + "','" + js_PoUnit.get(i).toString()
                                + "','" + js_AllowSalInvMinus.get(i).toString()
                                + "','" + js_GPSAccurent.get(i).toString()
                                + "','" + js_NumOfInvPerVisit.get(i).toString()
                                + "','" + js_NumOfPayPerVisit.get(i).toString()
                                + "','" + js_EnbleHdrDiscount.get(i).toString()
                                + "','" + js_AllowDeleteInvoice.get(i).toString()
                                + "','" + js_VisitWeekNo.get(i).toString()
                                + "','" + js_ACC_Cash.get(i).toString()

                                + "','" + LAT.get(i).toString()
                                + "','" + LONG.get(i).toString()

                                + "')";
                        sqlHandler.executeQuery(q);

                        //  editor.commit();
                        custDialog.setMax(js_ID.length());
                        custDialog.incrementProgressBy(1);

                        if (custDialog.getProgress() == custDialog.getMax()) {
                            custDialog.dismiss();
                        }
                    }
                    final int total = i;
                    _handler.post(new Runnable() {

                        public void run() {

                            custDialog.dismiss();
//Toast.makeText(NewLoginActivity.this,"done",Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (final Exception e) {
                    custDialog.dismiss();
                    _handler.post(new Runnable() {

                        public void run() {


                        }
                    });
                }
            }
        }).start();
    }
    public int get_count_of_days(String Created_date_String, String Expire_date_String) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());

        Date Created_convertedDate = null, Expire_CovertedDate = null, todayWithZeroTime = null;
        try {
            Created_convertedDate = dateFormat.parse(Created_date_String);
            Expire_CovertedDate = dateFormat.parse(Expire_date_String);

            Date today = new Date();

            todayWithZeroTime = dateFormat.parse(dateFormat.format(today));
        } catch (ParseException e) {
            e.printStackTrace();


            System.out.print("errt +"+e.getErrorOffset());
        }

        int c_year = 0, c_month = 0, c_day = 0;

        if (Created_convertedDate.after(todayWithZeroTime)) {
            Calendar c_cal = Calendar.getInstance();
            c_cal.setTime(Created_convertedDate);
            c_year = c_cal.get(Calendar.YEAR);
            c_month = c_cal.get(Calendar.MONTH);
            c_day = c_cal.get(Calendar.DAY_OF_MONTH);

        } else {
            Calendar c_cal = Calendar.getInstance();
            c_cal.setTime(todayWithZeroTime);
            c_year = c_cal.get(Calendar.YEAR);
            c_month = c_cal.get(Calendar.MONTH);
            c_day = c_cal.get(Calendar.DAY_OF_MONTH);
        }


/*Calendar today_cal = Calendar.getInstance();
int today_year = today_cal.get(Calendar.YEAR);
int today = today_cal.get(Calendar.MONTH);
int today_day = today_cal.get(Calendar.DAY_OF_MONTH);
*/

        Calendar e_cal = Calendar.getInstance();
        e_cal.setTime(Expire_CovertedDate);

        int e_year = e_cal.get(Calendar.YEAR);
        int e_month = e_cal.get(Calendar.MONTH);
        int e_day = e_cal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(c_year, c_month, c_day);
        date2.clear();
        date2.set(e_year, e_month, e_day);

        long diff =   date1.getTimeInMillis() -date2.getTimeInMillis();

        float dayCount = (float) diff / (24 * 60 * 60 * 1000);



        return ((int) dayCount);
    }

    private void getUnixTime() {
        String mUrl= "http://10.0.1.60:6325/api/ApkVersions/GetApkVersion/1";
        InputStreamVolleyRequest request = new InputStreamVolleyRequest(Request.Method.GET, mUrl,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {
                        // TODO handle the response
                        try {
                            if (response!=null) {

                                FileOutputStream outputStream;
                                String name="nameiaApk.apk";
                                outputStream = openFileOutput(name, Context.MODE_PRIVATE);
                                outputStream.write(response);
                                outputStream.close();
                                Toast.makeText(NewLoginActivity.this, "Download complete.", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
                            e.printStackTrace();
                        }
                    }
                } ,new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO handle the error
                error.printStackTrace();
            }
        }, null);
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext(), new HurlStack());
        mRequestQueue.add(request);
    }

    private String get_name_Apk(String url) {
        requestQueue = Volley.newRequestQueue(NewLoginActivity.this);

        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
            // Takes the response from the JSON request
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Apk = response.getString("Apk");
                    String  Version = response.getString("Version");



                    String getUri= staticLink +Apk;
                    DownloadManager.Request request=new DownloadManager.Request(Uri.parse(getUri));
                    String title= URLUtil.guessFileName(getUri,null,null);
                    request.setTitle(title);
                    request.setDescription("Download File Please wait ...");
                    String cooki= CookieManager.getInstance().getCookie(getUri);
                    request.addRequestHeader("cooki",cooki);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,title);

                    DownloadManager downloadManager=(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                    downloadManager.enqueue(request);

                    Toast.makeText(NewLoginActivity.this,"start download",Toast.LENGTH_LONG).show();


                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(NewLoginActivity.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("AppVersion",Version);
                    editor.commit();


                }
                // Try and catch are included to handle any errors due to JSON
                catch (JSONException e) {
                    // If an error occurs, this prints the error to the log
                    new SweetAlertDialog(NewLoginActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                            .setContentText(String.valueOf(getResources().getString(R.string.errorup)))
                            .setCustomImage(R.drawable.error_new)
                            .setConfirmText(String.valueOf(getResources().getString(R.string.back)))
                            .show();

                }
            }
        },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        new SweetAlertDialog(NewLoginActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)

                                .setContentText(String.valueOf(getResources().getString(R.string.errorup)))

                                .setCustomImage(R.drawable.error_new)
                                .setConfirmText(String.valueOf(getResources().getString(R.string.back)))
                                .show();

                    }
                }
        );
        requestQueue.add(obreq);




        return  Apk;
    }
}
class InputStreamVolleyRequest extends Request<byte[]> {
    private final Response.Listener<byte[]> mListener;
    private Map<String, String> mParams;

    //create a static map for directly accessing headers
    public Map<String, String> responseHeaders ;

    public InputStreamVolleyRequest(int method, String mUrl ,Response.Listener<byte[]> listener,
                                    Response.ErrorListener errorListener, HashMap<String, String> params) {
        // TODO Auto-generated constructor stub

        super(method, mUrl, errorListener);
        // this request would never use cache.
        setShouldCache(false);
        mListener = listener;
        mParams=params;
    }

    @Override
    protected Map<String, String> getParams()
            throws com.android.volley.AuthFailureError {
        return mParams;
    };


    @Override
    protected void deliverResponse(byte[] response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {

        //Initialise local responseHeaders map with response headers received
        responseHeaders = response.headers;

        //Pass the response data here
        return Response.success( response.data, HttpHeaderParser.parseCacheHeaders(response));
    }
















}