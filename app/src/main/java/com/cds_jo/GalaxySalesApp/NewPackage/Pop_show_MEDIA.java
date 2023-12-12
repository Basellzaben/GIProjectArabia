package com.cds_jo.GalaxySalesApp.NewPackage;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.Context.DOWNLOAD_SERVICE;
import static android.os.Build.VERSION.SDK_INT;
import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.cds_jo.GalaxySalesApp.DB;
import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.SqlHandler;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;


public class Pop_show_MEDIA extends DialogFragment {
    View form ;
    SqlHandler sqlHandler;
    String url="";
    String name="";
    PDFView webView;
    ImageView image;
    ListView mediatitlelist;
    boolean ERROR_Download_OR_SHARE;
    VideoView videoView;
    int flag=0;
    TextView title;
    String phone="";
    String email="basellalzaben@gmail.com";

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSION_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    @Override
    public void onStart()
    {
        super.onStart();
        if (getDialog() == null)
            return;

        int dialogWidth =  WindowManager.LayoutParams.MATCH_PARENT; // specify a value here
        int dialogHeight =WindowManager.LayoutParams.MATCH_PARENT;//400; // specify a value here

        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        form= inflater.inflate(R.layout.fragment_pop_show__m_e_d_i_a, container, false);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String nameCust= sharedPreferences.getString("CustNo", "");

//email="basellalzaben@gmail.com";
        LocaleHelper LocaleHelper=new LocaleHelper();
        if(LocaleHelper.getlanguage(getActivity()).equals("ar"))
        {            phone= DB.GetValue(getActivity(),"CustomersN","WhatsApp","id= '"+nameCust+"'");
            email=DB.GetValue(getActivity(),"CustomersN","Email","id= '"+nameCust+"'");

        }
        else {
            phone=DB.GetValue(getActivity(),"CustomersN","WhatsApp","EnglishName='"+nameCust+"'");
            email=DB.GetValue(getActivity(),"CustomersN","Email","EnglishName='"+nameCust+"'");

            //  CustNm.setText(c1.getString(c1.getColumnIndex("EnglishName")));

        }





        mediatitlelist=(ListView) form.findViewById(R.id.mediatitlelist);

        webView = (PDFView) form.findViewById(R.id.webview);
        videoView = (VideoView) form.findViewById(R.id.VideoView);
        image = (ImageView) form.findViewById(R.id.image);
        webView.setVisibility(View.INVISIBLE);
        videoView.setVisibility(View.INVISIBLE);
        //videoView.setVisibility(View.INVISIBLE);
        //webView.setVisibility(View.VISIBLE);
        // ShowPdf();
        //ShowImage();
        // ShowVideo();
        FillMedia();

        title=(TextView)form.findViewById(R.id.title);

        mediatitlelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                flag=position;
                if(position==0) {
                    title.setText("Pdf");
                    if(isInternetAvailable(getActivity())){
                        //moh54
                        String urls=DB.GetValue(getActivity(),"CheackOpenPdf","ifnull(count(*),0)","Item_No="+getArguments().getString("itemid")+" and OrderNo="+getArguments().getString("OrderNo"));
                           if(urls.equals("0")) {
                               String i = "Insert Into CheackOpenPdf (Item_No,OrderNo,Open) values (" + getArguments().getString("itemid") + "," + getArguments().getString("OrderNo") + ",1)";
                               sqlHandler.executeQuery(i);
                           }
                          ShowPdf("");
                        //openPDF();
                    }else{
                        Toast.makeText(getActivity(),getResources().getString(R.string.interneterror),Toast.LENGTH_SHORT).show();

                    }
                }
                else if(position==1){
                        if(isInternetAvailable(getActivity())){
                            title.setText(getResources().getString(R.string.Image));


                            ShowImage("");
                        }else{
                            Toast.makeText(getActivity(),String.valueOf(getResources().getString(R.string.interneterror)),Toast.LENGTH_SHORT).show();

                        }
                }
            else
                {
                    if(isInternetAvailable(getActivity())){
                        title.setText("Video");

                        ShowVideo("");
                    }else{
                        Toast.makeText(getActivity(),String.valueOf(getResources().getString(R.string.interneterror)),Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });






        ImageView gmail=(ImageView)form.findViewById(R.id.gmail);
        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ERROR_Download_OR_SHARE  || !isInternetAvailable(getActivity()))
                    Toast.makeText(getActivity(),getResources().getString(R.string.interneterror),Toast.LENGTH_SHORT).show();
                else {
                    downloadPdfContent(url, name);
                    Toast.makeText(getActivity(),getResources().getString(R.string.Pleasewait),Toast.LENGTH_LONG).show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            if(email.equals("-1")||email.equals(""))
                                email="basellalzaben@gmail.com";
                            SharePdfViaEmail(name, email, name + "- TQ PDF");
                        }
                    }, 6000);

                    //     insert();

                }
            }
        });

        ImageView whatssap=(ImageView)form.findViewById(R.id.whatsapp);
        whatssap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ERROR_Download_OR_SHARE || !isInternetAvailable(getActivity()))
                    Toast.makeText(getActivity(),getResources().getString(R.string.interneterror),Toast.LENGTH_SHORT).show();
                else {
               //     downloadPdfContent(url, name);
                   // WhatsAppShere();
                 //   Toast.makeText(getActivity(),getResources().getString(R.string.interneterror),Toast.LENGTH_LONG).show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            try {
                                if((phone.equals("-1"))||(phone.equals(""))) {
                                    Toast.makeText(getActivity(), "رقم العميل ليس مخزن", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                SharePdfViaWhatsapp(name,phone);
                            } catch (UnsupportedEncodingException e) {
                                ;
                            }

                            //    SharePdfViaEmail(name, "basellalzaben@gmail.com", name + "- Galaxy International Group");
                        }
                    }, 6000);   //5 seconds
                }
            }
        });





        return form;
    }

    public void ShowImage(String mStringUrl){

        webView.setVisibility(View.GONE);
        image.setVisibility(View.VISIBLE);
        //   if()
        //String mStringUrl = "https://i.pinimg.com/736x/8d/d2/02/8dd202e87ae0187d56ef62cc7e5f5f7f.jpg";

        videoView.setVisibility(View.GONE);
        //webView.getSettings().setJavaScriptEnabled(true);
        //webView.loadUrl(mStringUrl);

     /*   final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setTitle("");
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        webView = (PDFView) form.findViewById(R.id.webview);
        webView.setVisibility(View.VISIBLE);
        webView.getSettings().setDomStorageEnabled(true);
        if (SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pDialog.dismiss();
            }
        });
        webView.loadUrl(mStringUrl);


        WebSettings webSettings = webView.getSettings();
        webSettings.setBuiltInZoomControls(true);

        String urls=DB.GetValue(getActivity(),"itemsn","Image","Id='"+getArguments().getString("itemid")+"'");
        //  webView.loadUrl("https://cf-images.us-east-1.prod.boltdns.net/v1/static/1078702682/c2d3a443-e956-4841-aaf7-ff70e1e1fceb/05944796-ab0e-4a4a-b760-f5849f60ca79/1280x720/match/image.jpg");
        url=urls;
        name=random()+".jpg";
        webView.loadUrl(urls);
*/
      File  image1 = new File("//sdcard/Android/PDF/"+getArguments().getString("itemid")+".jpg");
        try {
            if (image1.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(image1.getAbsolutePath());
                image.setImageBitmap(myBitmap);
            }
        }
        catch (Exception ex){}
    }

    public void ShowVideo(String mStringUrl) {

        webView.setVisibility(View.GONE);
        image.setVisibility(View.GONE);

        videoView.setVisibility(View.VISIBLE);

/*        Uri video = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
        //  Uri video = Uri.parse(mStringUrl);
        videoView.setVideoURI(video);
        videoView.setZOrderOnTop(true);
        videoView.setBackgroundColor(Color.TRANSPARENT);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                videoView.start();
            }
        });
    }*/

        videoView.setVideoURI(Uri.parse("//sdcard/Android/Cv_Images/ff1.mp4"));
        videoView.setVideoURI(Uri.parse("//sdcard/Android/Cv_Images/ff1.mp4"));
        videoView.setMediaController(new MediaController(getActivity()));
        videoView.requestFocus();
        videoView.start();
    }
    public void ShowPdf(String mStringUrl)  {

        webView.setVisibility(View.VISIBLE);
        videoView.setVisibility(View.GONE);
        image.setVisibility(View.GONE);
    /*    final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setTitle(getActivity().getString(R.string.app_name));
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        webView = (WebView) form.findViewById(R.id.webview);
        webView.setVisibility(View.VISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pDialog.dismiss();
            }
        });
        String pdf = "http://www.adobe.com/devnet/acrobat/pdfs/pdf_open_parameters.pdf";
        pdf="https://ed6410ca-1896-4997-9c66-2a2635b810c4.filesusr.com/ugd/f9b4aa_567c18eb86d94d3baa5641ba32bc193a.pdf";
//  pdf="https://www.noor-book.com/%D9%83%D8%AA%D8%A7%D8%A8-%D9%86%D8%B8%D8%B1%D9%8A%D8%A9-%D8%A7%D9%84%D9%81%D8%B3%D8%AA%D9%82-pdf";

        pdf=DB.GetValue(getActivity(),"itemsn","ProchurePath","Id='"+getArguments().getString("itemid")+"'");
        url=pdf;
        pdf="file:///sdcard/Android/Cv_Images/Ff.pdf";
        // name="pdfG.pdf";

        //webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
        webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
        name=random()+".pdf";*/

        File imgFile = new  File("//sdcard/Android/PDF/"+getArguments().getString("itemid")+".pdf");
        webView.fromFile(imgFile)
              //  .defaultPage(0)
                //.onPageChange()
                .enableAnnotationRendering(true)
                // .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(getActivity()))
                .spacing(0) // in dp
                //.onPageError(this)
                .load();

    }
/*
    private void openPDF() {

        // Get the File location and file name.
        File file = new File(Environment.getExternalStorageDirectory(), "Download/TRENDOCEANS.pdf");
        Log.d("pdfFIle", "" + file);

        // Get the URI Path of file.
        Uri uriPdfPath = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".provider", file);
        Log.d("pdfPath", "" + uriPdfPath);

        // Start Intent to View PDF from the Installed Applications.
        Intent pdfOpenIntent = new Intent(Intent.ACTION_VIEW);
        pdfOpenIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pdfOpenIntent.setClipData(ClipData.newRawUri("", uriPdfPath));
        pdfOpenIntent.setDataAndType(uriPdfPath, "application/pdf");
        pdfOpenIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION |  Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        try {
            startActivity(pdfOpenIntent);
        } catch (ActivityNotFoundException activityNotFoundException) {
            Toast.makeText(getActivity(),"There is no app to load corresponding PDF",Toast.LENGTH_LONG).show();

        }
    }
*/

    // downloadPdfContent(pdf);

    private void FillMedia() {
        //   filter = (EditText) form.findViewById(R.id.et_Search_filter);
        // String query = "";
        sqlHandler = new SqlHandler(getActivity());


        Bundle bundle = this.getArguments();





        // String KeyId=DB.GetValue(getActivity(),"ItemsKeysN","KeyId","ItemId='"+itemid+"'");


        ArrayList<Media_Modle> cls_invf_List = new ArrayList<Media_Modle>();
        cls_invf_List.clear();





        for(int i=0;i<2;i++) {
            Media_Modle Media_Modle = new Media_Modle();
            if(i==0)
                Media_Modle.setTitle("PDF");
            else if(i==1)
                Media_Modle.setTitle("Image");
            else if(i==2)
                Media_Modle.setTitle("Video Example");



            //   Media_Modle.setUrl("");
            cls_invf_List.add(Media_Modle);
        }

        Media_adapter cls_invf_adapter = new Media_adapter(
                getActivity(), cls_invf_List);
        mediatitlelist.setAdapter(cls_invf_adapter);


      /*  ArrayList<keys_modle> cls_invf_List = new ArrayList<keys_modle>();
        cls_invf_List.clear();
        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    keys_modle keys_modle = new keys_modle();
                    keys_modle.setKey(c1.getString(c1
                            .getColumnIndex("DescrA")));
                    keys_modle.setItemNo(c1.getString(c1
                            .getColumnIndex("ItemNo")));


                    //   query = "Select ItemNo, Desc  from ItemsCheck where TransNo='"+getArguments().getString("transno")+"' and ItemNo='"+getArguments().getString("itemid")+"'";

                    String item=DB.GetValue(getActivity(),"ItemsCheck","KeyId","KeyId='"+c1.getString(c1
                            .getColumnIndex("ItemNo"))+"'");
                    if(item.equals("-1")){
                        keys_modle.setCheck(false);

                    }
                    else {
                        keys_modle.setCheck(true);

                    }
                    cls_invf_List.add(keys_modle);

                } while (c1.moveToNext());

            }
            c1.close();
        }

        Cls_Keys_adapter cls_invf_adapter = new Cls_Keys_adapter(
                getActivity(), cls_invf_List);
        lv.setAdapter(cls_invf_adapter);

*/



    }
/*public  void showmedia() {
    final ProgressDialog pDialog = new ProgressDialog(getActivity());
    pDialog.setTitle(getActivity().getString(R.string.app_name));
    pDialog.setMessage("Loading...");
    pDialog.setIndeterminate(false);
    pDialog.setCancelable(false);
    WebView webView = (WebView) rootView.findViewById(R.id.web_view);
    webView.getSettings().setJavaScriptEnabled(true);
    webView.setWebViewClient(new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            pDialog.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            pDialog.dismiss();
        }
    });
    String pdf = "http://www.adobe.com/devnet/acrobat/pdfs/pdf_open_parameters.pdf";
    webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
}*/

    public void downloadPdfContent(String urlToDownload,String name){

        try {
            URL url = new URL(urlToDownload);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url + ""));
            request.setTitle("fileName");
            String substring = name.substring(Math.max(name.length() - 3, 0));
            if(substring.equals("png"))
                request.setMimeType("Application/jpeg");
            else
                request.setMimeType("image/"+substring);
            request.allowScanningByMediaScanner();
            request.setAllowedOverMetered(true);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "GalaxyInternational/" +name);
            DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);
        }catch (Exception e){
            ERROR_Download_OR_SHARE=true;
        }

    }

    private void SharePdfViaEmail( String name,String email,String subject) {

        try {
            String PLACEHOLDER="";
            if(flag==0) {
                PLACEHOLDER = "/mnt/sdcard/Android/PDF/"+getArguments().getString("itemid")+".pdf";
            }else if(flag==1)
            {
                PLACEHOLDER = "/mnt/sdcard/Android/PDF/"+getArguments().getString("itemid")+".jpg";
            }
            File filelocation = new File(PLACEHOLDER);


          /*  Uri path = Uri.fromFile(filelocation);
            File f = new File(filelocation);*/
            // Uri uri = Uri.fromFile(f);
            Uri path = FileProvider.getUriForFile(
                    getActivity(),
                    getActivity().getApplicationContext()
                            .getPackageName() + ".provider", filelocation);
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("vnd.android.cursor.dir/email");
            String to[] = {email};
            emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
            emailIntent.putExtra(Intent.EXTRA_STREAM, path);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            startActivity(Intent.createChooser(emailIntent, "Send email..."));
        }catch (Exception e){
            ERROR_Download_OR_SHARE=true;


        }
    }



    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean isInternetAvailable(Context context) {
       /* ConnectivityManager cm = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            try {
                URL url = new URL("http://www.google.com/");
                HttpURLConnection urlc = (HttpURLConnection)url.openConnection();
                urlc.setRequestProperty("User-Agent", "test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1000); // mTimeout is in seconds
                urlc.connect();
                if (urlc.getResponseCode() == 200) {
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                Log.i("warning", "Error checking internet connection", e);
                return false;
            }
        }

        return false;*/


        return true;
        // boolean online = hostAvailable("www.google.com", 80);

    }
   /* public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }*/


    private void SharePdfViaWhatsapp( String name,String phone) throws UnsupportedEncodingException {
        if(whatsappInstalledOrNot()){
            String PLACEHOLDER="";
            if(flag==0) {
                 PLACEHOLDER = "/mnt/sdcard/Android/PDF/"+getArguments().getString("itemid")+".pdf";
            }else if(flag==1)
            {
                PLACEHOLDER = "/mnt/sdcard/Android/PDF/"+getArguments().getString("itemid")+".jpg";
            }
            File f = new File(PLACEHOLDER);
           // Uri uri = Uri.fromFile(f);
            Uri uri = FileProvider.getUriForFile(
                    getActivity(),
                    getActivity().getApplicationContext()
                            .getPackageName() + ".provider", f);

            Intent share = new Intent("android.intent.action.MAIN");

            share.setAction(Intent.ACTION_SEND);
            //share.putExtra(Intent.EXTRA_TEXT, "");
            //share.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));

            share.setPackage("com.whatsapp");
            https://api.whatsapp.com/send?phone=" + number

            share.putExtra("jid", /*"962779176141"*/phone + "@s.whatsapp.net");
            share.putExtra(Intent.EXTRA_STREAM, uri);
            String substring = name.substring(Math.max(name.length() - 2, 0));

            if(flag==0) {
               share.setType("application/pdf");}
            else if(flag==1)
                share.setType("Application/jpeg");



            //share.setType("application/pdf");

            startActivity(share);}else{
            Uri uri = Uri.parse("market://details?id=com.whatsapp");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            Toast.makeText(getActivity(), getResources().getString(R.string.WhatsApperror),
                    Toast.LENGTH_SHORT).show();
            startActivity(goToMarket);
        }
    }


    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(8);
        char tempChar;
        for (int i = 0; i < 5; i++){
            tempChar = (char) (generator.nextInt(96) + 75);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    public void insert() {
        Intent intent = new Intent(
                ContactsContract.Intents.SHOW_OR_CREATE_CONTACT,
                ContactsContract.Contacts.CONTENT_URI);
        intent.setData(Uri.parse("tel:911"));//specify your number here
        intent.putExtra(ContactsContract.Intents.Insert.COMPANY, "Emergency USA");
        startActivity(intent);
        Toast.makeText(getActivity(), "Record inserted", Toast.LENGTH_SHORT).show();
    }

    private boolean whatsappInstalledOrNot() {
        PackageManager pm = getActivity().getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

public void WhatsAppShere()
{
   // ImageView imgflag = (ImageView) findViewById(R.id.ima);
    image.buildDrawingCache();
    Bitmap bm=image.getDrawingCache();

    OutputStream fOut = null;
    Uri outputFileUri;

    try {
        File root = new File(Environment.getExternalStorageDirectory()
                + File.separator + "FoodMana" + File.separator);
        root.mkdirs();
        File sdImageMainDirectory = new File(root, "myPicName.jpg");
        outputFileUri = Uri.fromFile(sdImageMainDirectory);
        fOut = new FileOutputStream(sdImageMainDirectory);
    } catch (Exception e) {
        Toast.makeText(getActivity(), "Error occured. Please try again later.", Toast.LENGTH_SHORT).show();
    }

    try {
        bm.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        fOut.flush();
        fOut.close();
    } catch (Exception e) {
    }


    PackageManager pm=getActivity().getPackageManager();
    try {

        Toast.makeText(getActivity(), "Sharing Via Whats app !", Toast.LENGTH_LONG).show();
        Intent waIntent = new Intent(Intent.ACTION_SEND);
        waIntent.setType("image/*");
        waIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
        //Check if package exists or not. If not then code
        //in catch block will be called
        waIntent.setPackage("com.whatsapp");
        waIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(Environment.getExternalStorageDirectory()
                + File.separator + "FoodMana" + File.separator+"myPicName.jpg"));
        waIntent.putExtra(Intent.EXTRA_TEXT, "test");
        startActivity(Intent.createChooser(waIntent, "Share with"));

    } catch (PackageManager.NameNotFoundException e) {
        Toast.makeText(getActivity(), "WhatsApp not Installed", Toast.LENGTH_SHORT).show();

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/html");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your ward's academic details are here");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Please find the details attached....");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "test");
        startActivity(emailIntent);
        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        //    finish();
            Log.i("Finished Data", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(),
                    "No way you can share Reciepies,enjoy alone :-P", Toast.LENGTH_SHORT).show();
        }

    }




}

}
