package com.cds_jo.GalaxySalesApp;

import android.app.DialogFragment;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.DOWNLOAD_SERVICE;

public class Update_SFragment extends DialogFragment  {
    View form ;
    Button updatesystem;
    RelativeLayout rel;
    TextView tvResult;
    ProgressDialog pd;
    ImageView back;
    String url= "http://94.249.83.196:6326/api/ApkVersions/GetApkVersion/";
    String  Version,Apkold;
    final public  String staticLink="http://94.249.83.196:6326/";



    @Override
    public void onStart(){
        super.onStart();
        if (getDialog() == null)
            return;
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom( getDialog().getWindow().getAttributes());
        lp.width =700;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(lp);
        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setWindowAnimations(0);

    }
    @Override
    public View onCreateView(final LayoutInflater inflater   , ViewGroup container  ,Bundle savestate) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        form = inflater.inflate(R.layout.fragment_update__s, container, false);

        getDialog().setTitle("Galaxy International Group");
        Window window = getDialog().getWindow();
        window.setGravity(Gravity.TOP | Gravity.CENTER);
       /* if (GlobaleVar.LanType == 1) {
            LinearLayout linrtl = (LinearLayout) form.findViewById(R.id.LL2);
            linrtl.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        } else {
            LinearLayout linrtl = (LinearLayout) form.findViewById(R.id.LL2);
            linrtl.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
*/
        tvResult=(TextView)form.findViewById(R.id.tvResult);
        rel=(RelativeLayout)form.findViewById(R.id.rel);
        updatesystem=(Button)form.findViewById(R.id.updatesystem);
        if(ComInfo.ComNo==16){
            url+="1";
        }else if(ComInfo.ComNo==12){
            url+="2";
        }else{
            url+="1";
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                try {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    Apkold = sharedPreferences.getString("AppVersion", "0");

                    if (!Apkold.equals(Version)) {

                        updatesystem.setVisibility(View.VISIBLE);
                        rel.setVisibility(View.GONE);
                        tvResult.setVisibility(View.VISIBLE);


                        TextView title = (TextView) form.findViewById(R.id.titlee);
                        TextView version = (TextView) form.findViewById(R.id.version);

                        version.setVisibility(View.VISIBLE);
                        title.setVisibility(View.VISIBLE);
                    } else {
                        TextView title = (TextView) form.findViewById(R.id.titlee);
                        title.setVisibility(View.VISIBLE);
                        title.setText("النظام محدث بالفعل");
                        rel.setVisibility(View.GONE);

                    }


                }catch (Exception e){


                }
            }  }, 3000);   //5 seconds
        this.getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    updatesystem.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            get_name_Apk();
          //  startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://we.tl/t-WZLwTBlzkD")));
        }
    });
    back=(ImageView) form.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
            // Takes the response from the JSON request
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String  Apk = response.getString("Apk");
                      Version = response.getString("Version");



                    TextView title=(TextView)form.findViewById(R.id.titlee);
                    TextView version=(TextView)form.findViewById(R.id.version);

                    title.setText(Apk);
                    version.setText(Version);

                }
                // Try and catch are included to handle any errors due to JSON
                catch (JSONException e) {
                    // If an error occurs, this prints the error to the log
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                            .setContentText(String.valueOf(getResources().getString(R.string.errorup)))
                            .setCustomImage(R.drawable.error_new)
                            .setConfirmText(String.valueOf(getResources().getString(R.string.back)))
                            .show();
dismiss();
                }
            }
        },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)

                                .setContentText(String.valueOf(getResources().getString(R.string.errorup)))

                                .setCustomImage(R.drawable.error_new)
                                .setConfirmText(String.valueOf(getResources().getString(R.string.back)))
                                .show();
                        dismiss();

                    }
                }
        );
        requestQueue.add(obreq);


        return form;
    }


    private String get_name_Apk() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
            // Takes the response from the JSON request
            @Override
            public void onResponse(JSONObject response) {
                try {
                  String  Apk = response.getString("Apk");
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

                    DownloadManager downloadManager=(DownloadManager)getActivity().getSystemService(DOWNLOAD_SERVICE);
                    downloadManager.enqueue(request);

                    Toast.makeText(getActivity(),"start download",Toast.LENGTH_LONG).show();


                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("AppVersion",Version);
                    editor.commit();


                }
                // Try and catch are included to handle any errors due to JSON
                catch (JSONException e) {
                    // If an error occurs, this prints the error to the log
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                            .setContentText(String.valueOf(getResources().getString(R.string.errorup)))
                            .setCustomImage(R.drawable.error_new)
                            .setConfirmText(String.valueOf(getResources().getString(R.string.back)))
                            .show();
                    dismiss();

                }
            }
        },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)

                                .setContentText(String.valueOf(getResources().getString(R.string.errorup)))

                                .setCustomImage(R.drawable.error_new)
                                .setConfirmText(String.valueOf(getResources().getString(R.string.back)))
                                .show();
                        dismiss();

                    }
                }
        );
        requestQueue.add(obreq);




        return  "Apk";
    }
}
