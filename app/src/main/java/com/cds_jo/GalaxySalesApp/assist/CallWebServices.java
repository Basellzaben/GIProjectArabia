package com.cds_jo.GalaxySalesApp.assist;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.cds_jo.GalaxySalesApp.Cls_SaleManDailyRound;
import com.cds_jo.GalaxySalesApp.ComInfo;
import com.cds_jo.GalaxySalesApp.Companies;
import com.cds_jo.GalaxySalesApp.CustLocations.CustLocaltions;
import com.cds_jo.GalaxySalesApp.NewPackage.Cls_SaleManDailyRoundMed;

import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.We_Result;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import java.io.EOFException;

///bvbvbv
public class CallWebServices  {
    Context context;
    Activity Activity;
    String IPAddress = "";
    //Namespace of the Webservice - can be found in WSDL
    private static String NAMESPACE = "http://tempuri.org/";
    //Webservice URL - WSDL File location
    //private  String URL = "http://192.168.1.60/Webservices/WebService1.asmx";
    private  String URL = "";
    int TIME_OUT_CONN = 50000;

    public  CallWebServices(Context _context ){
        context = _context;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        IPAddress =sharedPreferences.getString("ServerIP", "");
        //1 bustanji 2 bristage 3-salsel 4-goodsystem 5-tariget system 6-
        //URL = "http://"+IPAddress+"/GIWS/CV.asmx";//bustanji or prastaige
        //    URL = "http://"+IPAddress+":3755"+"/CV.asmx";//  salasel or goodsystem or good targit Or arabia or mo8bl
        URL = "http://"+IPAddress+"/CV.asmx";//  salasel or goodsystem or good targit Or arabia or mo8bl
        //    URL = "http://46.185.131.31:9090/CV.asmx";//شركة السلاسل
        //URL = "http://192.168.8.101/GIWS/CV.asmx";
        //URL = "http://92.253.93.52:3755/CV.asmx";
        //URL = "http://192.168.1.148/GIWS/CV.asmx";
        //URL = "http://10.0.1.166/GIWS/CV.asmx"; //Galaxy1
        //   URL = "http://10.0.1.104:82/CV.asmx"; //Galaxy2
        // URL = "http://192.168.43.36:82/CV.asmx"; //Galaxy2
        //   URL = "http://192.168.1.103:82/CV.asmx";
        //    URL = "http://10.0.1.104:82/CV.asmx";
      //    URL = "http://46.185.139.240:9700/CV.asmx";//electronic
        // URL = "http://94.249.83.196:3767/CV.asmx";
        //URL = "http://92.253.126.39:3750/CV.asmx";//شركة خط التجميل
        //URL = "http://192.168.0.103:3755/CV.asmx";
        //URL = "http://92.253.22.118:3733/CV.asmx";// Targit
// URL = "http://194.165.133.147:85/CV.asmx";// Okrania
        //URL = "http://92.253.127.230:92/CV.asmx";// mo8bl
//URL = "http://109.107.238.12:3755/CV.asmx";// السعد
//URL = "http://5.198.246.88:3755/CV.asmx";// عفرا
        //  URL = "http://79.173.250.118:3755/CV.asmx";// sector
//URL = "http://192.168.1.146:9767/CV.asmx";// khaldi
//URL = "http://10.0.1.60:92/CV.asmx";// servar galaxy
//URL = "http://10.0.1.104:9767/CV.asmx";// galaxy
        //URL = "http://192.168.137.1:82/CV.asmx";// galaxy
//URL = "http://94.249.83.196:7095/CV.asmx";// galaxy
     // URL = "http://10.0.1.60:92/CV.asmx";// ocraineh test
 //   URL = "http://192.168.1.146:9767/CV.asmx";// galaxy
 //   URL = "http://10.0.1.104:9767/CV.asmx";// galaxy
//  URL = "http://10.0.1.104:3689/CV.asmx";// electronic
     // URL = "http://192.168.1.146:9767/CV.asmx";// galaxy
//URL = "http://94.249.83.196:7092/CV.asmx";// galaxy
//URL = "http://94.249.83.196:7092/CV.asmx";// galaxyGetUserDefinition
        //moh
        //http://94.249.83.196:6852/CV.asmx
// URL = " http://10.0.1.104:9921/CV.asmx";
     //   URL = "http://10.0.1.104:9767/CV.asmx";s
   //    URL = "http://10.0.1.53:2485/CV.asmx";
      // URL = "http://94.249.83.196:5228/CV.asmx";
     //URL = "http://10.0.1.53:9752/CV.asmx";// الماسيه
  // URL = "http://37.220.120.178:3750/CV.asmx";// الماسيه
   //  URL = "http://37.220.121.20:3750/CV.asmx";// amrcaina
    // URL = "http://192.168.0.3:3750/CV.asmx";// amrcaina
      /// URL = "http://94.249.83.196:8055/CV.asmx";
      //  URL = "http://94.249.83.196:5228/CV.asmx"; //التقدم دعاية
      //  URL = "http://172.16.15.172:7323/CV.asmx";// فلسطين
   //  URL = "http://10.0.1.53:3759/CV.asmx";// الماسيه
     // URL = "http://192.168.1.140:3759/CV.asmx";// الماسيه
// URL = "http://37.220.120.178:3750/CV.asmx";// الماسيه
    // URL = "http://94.249.83.196:5228/CV.asmx"; //التقدم دعاية
   //  URL = "http://10.0.1.53:933/CV.asmx";//التقدم دعاية تست
   // URL = "http://10.0.1.40:3759/CV.asmx";//تست الماسيه
    //URL = "http://94.249.83.196:9072/CV.asmx";//تست الماسيه
// URL = "http://149.200.252.6:3750/CV.asmx";//جديد الماسيه
 //URL = "http://94.249.83.196:9072/CV.asmx";//جديد الماسيه
      //  URL = "http://94.249.83.196:9072/CV.asmx"; //تست ماسية (كاش فان) static
     //   URL = "http://94.249.83.196:93/CV.asmx"; //تست دعاية طبية (التقدم) static
  // URL = "http://10.0.1.50:9767/CV.asmx";  //لوكل تست الماسيه*9+
  // URL = "http://10.0.1.50:3562/CV.asmx";  //لوكل تست الماسيه
  //  URL = "http://192.168.1.60:3550/CV.asmx";  //لوكل تست الماسيه
    // URL = "http://176.29.108.202:3738/CV.asmx";
  // URL = "http://176.29.108.202:3756/CV.asmx";
 URL = "http://92.253.103.165:3751/CV.asmx"; //nwwah initial

 URL = "http://10.0.1.50:9767/CV.asmx";  //لوكل تست الماسيه*9+

 URL = "http://192.16فاتورة المبيعات8.43.36:3750/CV.asmx";

 URL = "http://46.185.131.143:3756/CV.asmx";
 URL = "http://10.0.1.50:3750/CV.asmx"; // khaldi test


       // URL = "http://10.0.1.120:3210/CV.asmx"; // khaldi test
      //  URL = "http://10.0.1.50:9767/CV.asmx";  //لوكل تست الماسيه*9+

 // URL = "http://192.168.100.121:9767/CV.asmx";



 //URL = "http://176.29.154.24:3750/CV.asmx";//أطلس



  URL = "http://10.0.1.60:6325/CV.asmx";//ماسية-نواه تيست سيرفر


    // URL = "http://149.200.252.6:3750/CV.asmx";//massiah initial



 // URL = "http://46.185.131.143:3756/CV.asmx";//arabia orbia


   URL = "http://92.253.103.165:3751/CV.asmx"; //nwwah initial


    }
    public void GetItem_D(String Item_no, int flag) {

        We_Result.Msg="";
        We_Result.ID =-1;


        SoapObject request = new SoapObject(NAMESPACE, "GetItem_D");

        PropertyInfo parm_itemno = new PropertyInfo();
        parm_itemno.setName("itemno");
        parm_itemno.setValue(Item_no);
        parm_itemno.setType(String.class);

        PropertyInfo parm_flag = new PropertyInfo();
        parm_flag.setName("flag");
        parm_flag.setValue(flag);
        parm_flag.setType(String.class);



        // Add the property to request object
        request.addProperty(parm_itemno);
        request.addProperty(parm_flag);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/GetItem_D",envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }
        //Return resTxt to calling object

    }

    public void GetManSalesRep(String OrderNo) {

        We_Result.Msg="";
        We_Result.ID =-1;

        SoapObject request = new SoapObject(NAMESPACE, "GetManSalesRepD");

        PropertyInfo parm_itemno = new PropertyInfo();
        parm_itemno.setName("OrderNo");
        parm_itemno.setValue(OrderNo);
        parm_itemno.setType(String.class);
        request.addProperty(parm_itemno);




        // Add the property to request object


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/GetManSalesRepD",envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }
        //Return resTxt to calling object

    }
    public void GetModelCusf(String Acc) {

        We_Result.Msg="";
        We_Result.ID =-1;

        SoapObject request = new SoapObject(NAMESPACE, "GetModelCusf");

        PropertyInfo parm_itemno = new PropertyInfo();
        parm_itemno.setName("Acc");
        parm_itemno.setValue(Acc);
        parm_itemno.setType(String.class);
        request.addProperty(parm_itemno);




        // Add the property to request object


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/GetModelCusf",envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }
        //Return resTxt to calling object

    }
    public void CusInvoiceFromBatch(String BatchNo, String Acc,String searchtype ) {
        We_Result.Msg =  " "    ;
        We_Result.ID =-1;
        String resTxt = null;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, "GetGatInvoiceFromBatch");
        // Property which holds input parameters
        PropertyInfo parm_BatchNo = new PropertyInfo();

        parm_BatchNo.setName("BatchNo");
        parm_BatchNo.setValue(BatchNo);
        parm_BatchNo.setType(String.class);
        request.addProperty(parm_BatchNo);

        PropertyInfo parm_Acc = new PropertyInfo();
        parm_Acc.setName("Acc");
        parm_Acc.setValue(Acc);
        parm_Acc.setType(String.class);
        request.addProperty(parm_Acc);

        PropertyInfo parm_st = new PropertyInfo();
        parm_st.setName("searchtype");
        parm_st.setValue(searchtype);
        parm_st.setType(String.class);
        request.addProperty(parm_st);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,TIME_OUT_CONN);
        Object  response =null;
        try {
            // Invoke web service
            androidHttpTransport.call("http://tempuri.org/GetGatInvoiceFromBatch", envelope);
            // Get the response
            SoapObject result  = (SoapObject) envelope.getResponse();


            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();
            resTxt = "Error occured";

        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();
            resTxt = "Error occured";
        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
            resTxt = "Error occured";
        }
        //Return resTxt to calling object

    }
    public void CusInvoiceDtl(String DocNo,String Flag ) {
        We_Result.Msg =  " "    ;
        We_Result.ID =-1;
        String resTxt = null;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, "GetGatCustInvoiceDtl");
        // Property which holds input parameters
        PropertyInfo parm_AccNo = new PropertyInfo();

        parm_AccNo.setName("DocNo");
        parm_AccNo.setValue(DocNo);
        parm_AccNo.setType(String.class);




        request.addProperty(parm_AccNo);


        PropertyInfo parm_Flag = new PropertyInfo();

        parm_Flag.setName("Flag");
        parm_Flag.setValue(Flag);
        parm_Flag.setType(String.class);




        request.addProperty(parm_Flag);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,300000000);
        Object  response =null;
        try {
            // Invoke web service
            androidHttpTransport.call("http://tempuri.org/GetGatCustInvoiceDtl", envelope);
            // Get the response
            SoapObject result  = (SoapObject) envelope.getResponse();


            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();
            resTxt = "Error occured";

        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();
            resTxt = "Error occured";
        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
            resTxt = "Error occured";
        }
        //Return resTxt to calling object

    }
    public void GetSalesOrderForApproval(String Order_No,String User_ID  , String ManLevel ,String SummeryFlag ,String Tr_Date) {
        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "Get_SalesOrderForApproval");

        PropertyInfo parm_User_ID = new PropertyInfo();
        parm_User_ID.setName("User_ID");
        parm_User_ID.setValue(User_ID);
        parm_User_ID.setType(String.class);
        request.addProperty(parm_User_ID);



        PropertyInfo parm_ManLevel = new PropertyInfo();
        parm_ManLevel.setName("ManLevel");
        parm_ManLevel.setValue(ManLevel);
        parm_ManLevel.setType(String.class);
        request.addProperty(parm_ManLevel);

        PropertyInfo parm_SummeryFlag = new PropertyInfo();
        parm_SummeryFlag.setName("SummeryFlag");
        parm_SummeryFlag.setValue(SummeryFlag);
        parm_SummeryFlag.setType(String.class);
        request.addProperty(parm_SummeryFlag);


        PropertyInfo parm_Tr_Date = new PropertyInfo();
        parm_Tr_Date.setName("Tr_Date");
        parm_Tr_Date.setValue(Tr_Date);
        parm_Tr_Date.setType(String.class);
        request.addProperty(parm_Tr_Date);


        PropertyInfo parm_Order_No = new PropertyInfo();
        parm_Order_No.setName("Order_No");
        parm_Order_No.setValue(Order_No);
        parm_Order_No.setType(String.class);
        request.addProperty(parm_Order_No);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL, 50000);
        Object  response =null;
        try {
            androidHttpTransport.call("http://tempuri.org/Get_SalesOrderForApproval", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public long UpdateSalesOrders_Status(String  User_ID ,String Order_NO ,String Op ,String Des  ) {
        We_Result.ID=-1;
        We_Result.Msg="";

        SoapObject request = new SoapObject(NAMESPACE, "Update_SalesOrders_Status");

        PropertyInfo parm_User_ID = new PropertyInfo();
        parm_User_ID.setName("User_ID");
        parm_User_ID.setValue(User_ID);
        parm_User_ID.setType(String.class);
        request.addProperty(parm_User_ID);



        PropertyInfo parm_Order_NO = new PropertyInfo();
        parm_Order_NO.setName("Order_NO");
        parm_Order_NO.setValue(Order_NO);
        parm_Order_NO.setType(String.class);
        request.addProperty(parm_Order_NO);





        PropertyInfo parm_Op= new PropertyInfo();
        parm_Op.setName("Op");
        parm_Op.setValue(Op);
        parm_Op.setType(String.class);
        request.addProperty(parm_Op);


        PropertyInfo parm_Des= new PropertyInfo();
        parm_Des.setName("Des");
        parm_Des.setValue(Des);
        parm_Des.setType(String.class);
        request.addProperty(parm_Des);







        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,TIME_OUT_CONN);
        Object  response =null;
        try {
            androidHttpTransport.call("http://tempuri.org/Update_SalesOrders_Status", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }
        return We_Result.ID;

    }
    public void GetAcc_Check( String Acc ) {
        We_Result.Msg =  " "    ;
        We_Result.ID =-1;
        String resTxt = null;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, "GetAcc_Check");




        PropertyInfo parm_Order_No = new PropertyInfo();
        parm_Order_No.setName("AccNo");
        parm_Order_No.setValue(Acc);
        parm_Order_No.setType(String.class);
        request.addProperty(parm_Order_No);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,TIME_OUT_CONN);
        Object  response =null;
        try {
            // Invoke web service
            androidHttpTransport.call("http://tempuri.org/GetAcc_Check", envelope);
            // Get the response
            SoapObject result  = (SoapObject) envelope.getResponse();


            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();
            resTxt = "Error occured";

        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();
            resTxt = "Error occured";
        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
            resTxt = "Error occured";
        }
        //Return resTxt to calling object

    }
    public void CusLastInvoice(String Acc ) {
        We_Result.Msg =  " "    ;
        We_Result.ID =-1;
        String resTxt = null;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, "GetGatCustLastInvoice");
        // Property which holds input parameters
        PropertyInfo parm_AccNo = new PropertyInfo();

        parm_AccNo.setName("Acc");
        parm_AccNo.setValue(Acc);
        parm_AccNo.setType(String.class);




        request.addProperty(parm_AccNo);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,300000000);
        Object  response =null;
        try {
            // Invoke web service
            androidHttpTransport.call("http://tempuri.org/GetGatCustLastInvoice", envelope);
            // Get the response
            SoapObject result  = (SoapObject) envelope.getResponse();


            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();
            resTxt = "Error occured";

        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();
            resTxt = "Error occured";
        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
            resTxt = "Error occured";
        }
        //Return resTxt to calling object

    }
    public void ShowCheckDtlDtl(  String AccNo ,  String   Voucher ) {
        We_Result.Msg =  " "    ;
        We_Result.ID =-1;


        SoapObject request = new SoapObject(NAMESPACE, "GatAccReport_Check");


        PropertyInfo parm_AccNo = new PropertyInfo();
        parm_AccNo.setName("AccNo");
        parm_AccNo.setValue(AccNo);
        parm_AccNo.setType(String.class);
        request.addProperty(parm_AccNo);



        PropertyInfo parm_Voucher = new PropertyInfo();
        parm_Voucher.setName("Voucher");
        parm_Voucher.setValue(Voucher);
        parm_Voucher.setType(String.class);
        request.addProperty(parm_Voucher);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,300000000);
        Object  response =null;
        try {
            // Invoke web service
            androidHttpTransport.call("http://tempuri.org/GatAccReport_Check", envelope);
            // Get the response
            SoapObject result  = (SoapObject) envelope.getResponse();


            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }

    }
    public void GetCustomerQtyByItem(String CustNo,String ItemNo ,String  Top ) {
        We_Result.Msg="";
        We_Result.ID=-1;
        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, "GetCustomerQtyByItem");

        PropertyInfo parm_CustNo = new PropertyInfo();

        parm_CustNo.setName("CustNo");
        parm_CustNo.setValue(CustNo);
        parm_CustNo.setType(String.class);
        request.addProperty(parm_CustNo);

        PropertyInfo parm_ItemNo = new PropertyInfo();
        parm_ItemNo.setName("ItemNo");
        parm_ItemNo.setValue(ItemNo);
        parm_ItemNo.setType(String.class);
        request.addProperty(parm_ItemNo);

        PropertyInfo parm_Top = new PropertyInfo();
        parm_Top.setName("Top");
        parm_Top.setValue(Top);
        parm_Top.setType(String.class);
        request.addProperty(parm_Top);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call("http://tempuri.org/GetCustomerQtyByItem", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();
            resTxt = "Error occured";

        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();
            resTxt = "Error occured";
        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
            resTxt = "Error occured";
        }


    }
    public void GetManSalesRepH(String CustNo1, String FromDate, String ToDate, String UserNo) {

        We_Result.Msg="";
        We_Result.ID =-1;

        SoapObject request = new SoapObject(NAMESPACE, "GetManSalesRepH");

        PropertyInfo parm_itemno = new PropertyInfo();
        parm_itemno.setName("CustNo1");
        parm_itemno.setValue(CustNo1);
        parm_itemno.setType(String.class);
        request.addProperty(parm_itemno);

        PropertyInfo parm_flag = new PropertyInfo();
        parm_flag.setName("FromDate");
        parm_flag.setValue(FromDate);
        parm_flag.setType(String.class);
        request.addProperty(parm_flag);

        PropertyInfo parm_ToDate = new PropertyInfo();
        parm_ToDate.setName("ToDate");
        parm_ToDate.setValue(ToDate);
        parm_ToDate.setType(String.class);
        request.addProperty(parm_ToDate);

        PropertyInfo parm_UserNo = new PropertyInfo();
        parm_UserNo.setName("UserNo");
        parm_UserNo.setValue(UserNo);
        parm_UserNo.setType(String.class);
        request.addProperty(parm_UserNo);



        // Add the property to request object
        request.addProperty(parm_itemno);
        request.addProperty(parm_flag);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/GetManSalesRepH",envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }
        //Return resTxt to calling object

    }
    public void GetManSalesRepH2(String CustNo1, String FromDate, String ToDate, String UserNo) {

        We_Result.Msg="";
        We_Result.ID =-1;

        SoapObject request = new SoapObject(NAMESPACE, "GetManSalesRepH2");

        PropertyInfo parm_itemno = new PropertyInfo();
        parm_itemno.setName("CustNo1");
        parm_itemno.setValue(CustNo1);
        parm_itemno.setType(String.class);
        request.addProperty(parm_itemno);

        PropertyInfo parm_flag = new PropertyInfo();
        parm_flag.setName("FromDate");
        parm_flag.setValue(FromDate);
        parm_flag.setType(String.class);
        request.addProperty(parm_flag);

        PropertyInfo parm_ToDate = new PropertyInfo();
        parm_ToDate.setName("ToDate");
        parm_ToDate.setValue(ToDate);
        parm_ToDate.setType(String.class);
        request.addProperty(parm_ToDate);

        PropertyInfo parm_UserNo = new PropertyInfo();
        parm_UserNo.setName("UserNo");
        parm_UserNo.setValue(UserNo);
        parm_UserNo.setType(String.class);
        request.addProperty(parm_UserNo);



        // Add the property to request object
        request.addProperty(parm_itemno);
        request.addProperty(parm_flag);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/GetManSalesRepH2",envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }
        //Return resTxt to calling object

    }

    public long Get_Rep_D(String OrderNo, String UserNo) {
        We_Result.Msg="";
        We_Result.ID =-1;

        if (ComInfo.ComNo== Companies.Afrah.getValue()) {
            URL = "http://176.29.108.202:3738/CV.asmx";

        }

        SoapObject request = new SoapObject(NAMESPACE, "GetManVisitD");
        PropertyInfo sayHelloPI = new PropertyInfo();
        sayHelloPI.setName("OrderNo");
        sayHelloPI.setValue(OrderNo);
        sayHelloPI.setType(String.class);
        request.addProperty(sayHelloPI);

        PropertyInfo sayHelloPI2 = new PropertyInfo();
        sayHelloPI2.setName("UserNo");
        sayHelloPI2.setValue(UserNo);
        sayHelloPI2.setType(String.class);
        request.addProperty(sayHelloPI2);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,50000);
        Object  response =null;
        try {

            androidHttpTransport.call( "http://tempuri.org/GetManVisitD", envelope);

            SoapObject result  = (SoapObject) envelope.getResponse();


            // Assign it to resTxt variable static variable
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

            if (We_Result.Msg.equalsIgnoreCase("anyType{}")){
                We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            }


        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"   ;// + String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }
        return   We_Result.ID ;

    }

    public long GetManVisitDF(String OrderNo, String UserNo) {
        We_Result.Msg="";
        We_Result.ID =-1;
        URL = "http://94.249.83.196:5228/CV.asmx";

        SoapObject request = new SoapObject(NAMESPACE, "GetManVisitDF");
        PropertyInfo sayHelloPI = new PropertyInfo();
        sayHelloPI.setName("OrderNo");
        sayHelloPI.setValue(OrderNo);
        sayHelloPI.setType(String.class);
        request.addProperty(sayHelloPI);

        PropertyInfo sayHelloPI2 = new PropertyInfo();
        sayHelloPI2.setName("UserNo");
        sayHelloPI2.setValue(UserNo);
        sayHelloPI2.setType(String.class);
        request.addProperty(sayHelloPI2);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,50000);
        Object  response =null;
        try {

            androidHttpTransport.call( "http://tempuri.org/GetManVisitDF", envelope);

            SoapObject result  = (SoapObject) envelope.getResponse();


            // Assign it to resTxt variable static variable
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

            if (We_Result.Msg.equalsIgnoreCase("anyType{}")){
                We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            }
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();
        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();
        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"   ;// + String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }
        return   We_Result.ID ;

    }




    public long Get_Rep_H(String CustNo1, String FromDate,String ToDate, String UserNo) {
        We_Result.Msg="";
        We_Result.ID =-1;

       /* if (ComInfo.ComNo== Companies.Afrah.getValue()) {
            URL = "http://176.29.108.202:3738/CV.asmx";

        }*/

        SoapObject request = new SoapObject(NAMESPACE, "GetManVisitH");
        PropertyInfo sayHelloPI = new PropertyInfo();
        sayHelloPI.setName("CustNo1");
        sayHelloPI.setValue(CustNo1);
        sayHelloPI.setType(String.class);
        request.addProperty(sayHelloPI);

        PropertyInfo sayHelloPI2 = new PropertyInfo();
        sayHelloPI2.setName("FromDate");
        sayHelloPI2.setValue(FromDate);
        sayHelloPI2.setType(String.class);
        request.addProperty(sayHelloPI2);

        PropertyInfo sayHelloPI3 = new PropertyInfo();
        sayHelloPI3.setName("ToDate");
        sayHelloPI3.setValue(ToDate);
        sayHelloPI3.setType(String.class);
        request.addProperty(sayHelloPI3);

        PropertyInfo sayHelloPI4 = new PropertyInfo();
        sayHelloPI4.setName("UserNo");
        sayHelloPI4.setValue(UserNo);
        sayHelloPI4.setType(String.class);
        request.addProperty(sayHelloPI4);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,50000);
        Object  response =null;
        try {

            androidHttpTransport.call( "http://tempuri.org/GetManVisitH", envelope);

            SoapObject result  = (SoapObject) envelope.getResponse();


            // Assign it to resTxt variable static variable
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

            if (We_Result.Msg.equalsIgnoreCase("anyType{}")){
                We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            }


        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"   ;// + String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }
        return   We_Result.ID ;

    }

    public long Get_Rep_H1(String CustNo1, String FromDate,String ToDate, String UserNo) {
        We_Result.Msg="";
        We_Result.ID =-1;
        URL = "http://94.249.83.196:5228/CV.asmx";
        //  URL = "http://10.0.1.53:933/CV.asmx";
        URL = "http://176.29.108.202:3738/CV.asmx";

        SoapObject request = new SoapObject(NAMESPACE, "GetSalesOrderH");
        PropertyInfo sayHelloPI = new PropertyInfo();
        sayHelloPI.setName("CustNo1");
        sayHelloPI.setValue(CustNo1);
        sayHelloPI.setType(String.class);
        request.addProperty(sayHelloPI);

        PropertyInfo sayHelloPI2 = new PropertyInfo();
        sayHelloPI2.setName("FromDate");
        sayHelloPI2.setValue(FromDate);
        sayHelloPI2.setType(String.class);
        request.addProperty(sayHelloPI2);

        PropertyInfo sayHelloPI3 = new PropertyInfo();
        sayHelloPI3.setName("ToDate");
        sayHelloPI3.setValue(ToDate);
        sayHelloPI3.setType(String.class);
        request.addProperty(sayHelloPI3);

        PropertyInfo sayHelloPI4 = new PropertyInfo();
        sayHelloPI4.setName("UserNo");
        sayHelloPI4.setValue(UserNo);
        sayHelloPI4.setType(String.class);
        request.addProperty(sayHelloPI4);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,50000);
        Object  response =null;
        try {

            androidHttpTransport.call( "http://tempuri.org/GetSalesOrderH", envelope);

            SoapObject result  = (SoapObject) envelope.getResponse();


            // Assign it to resTxt variable static variable
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

            if (We_Result.Msg.equalsIgnoreCase("anyType{}")){
                We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            }


        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"   ;// + String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }
        return   We_Result.ID ;

    }



    public long Get_Rep_D1(String OrderNo, String UserNo) {
        We_Result.Msg="";
        We_Result.ID =-1;
        URL = "http://94.249.83.196:5228/CV.asmx";
        // URL = "http://10.0.1.53:933/CV.asmx";
        URL = "http://176.29.108.202:3738/CV.asmx";

        SoapObject request = new SoapObject(NAMESPACE, "GetSalesOrderD");
        PropertyInfo sayHelloPI = new PropertyInfo();
        sayHelloPI.setName("OrderNo");
        sayHelloPI.setValue(OrderNo);
        sayHelloPI.setType(String.class);
        request.addProperty(sayHelloPI);

        PropertyInfo sayHelloPI2 = new PropertyInfo();
        sayHelloPI2.setName("UserNo");
        sayHelloPI2.setValue(UserNo);
        sayHelloPI2.setType(String.class);
        request.addProperty(sayHelloPI2);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,50000);
        Object  response =null;
        try {

            androidHttpTransport.call( "http://tempuri.org/GetSalesOrderD", envelope);

            SoapObject result  = (SoapObject) envelope.getResponse();


            // Assign it to resTxt variable static variable
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

            if (We_Result.Msg.equalsIgnoreCase("anyType{}")){
                We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            }


        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"   ;// + String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }
        return   We_Result.ID ;

    }

    public void CallClincVisitsReport(String DoctorNo, String FromDate,String ToDate,String UserNo) {
        We_Result.Msg =  " "    ;
        We_Result.ID =-1;
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, "GetClincReport");

        PropertyInfo parm_DoctorNo = new PropertyInfo();
        parm_DoctorNo.setName("DoctorNo");
        parm_DoctorNo.setValue(DoctorNo);
        parm_DoctorNo.setType(String.class);

        PropertyInfo parm_FDate = new PropertyInfo();
        parm_FDate.setName("FDate");
        parm_FDate.setValue(FromDate);
        parm_FDate.setType(String.class);


        PropertyInfo parm_TDate = new PropertyInfo();
        parm_TDate.setName("TDate");
        parm_TDate.setValue(ToDate);
        parm_TDate.setType(String.class);



        PropertyInfo parm_UserNo = new PropertyInfo();
        parm_UserNo.setName("UserNo");
        parm_UserNo.setValue(UserNo);
        parm_UserNo.setType(String.class);


        request.addProperty(parm_DoctorNo);
        request.addProperty(parm_FDate);
        request.addProperty(parm_TDate);
        request.addProperty(parm_UserNo);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,TIME_OUT_CONN);
        Object  response =null;
        try {
            // Invoke web service
            androidHttpTransport.call("http://tempuri.org/GetClincReport", envelope);
            // Get the response
            SoapObject result  = (SoapObject) envelope.getResponse();


            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  String.valueOf(context.getResources().getString(R.string.ConnectError))     ;
            ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();
            resTxt = "Error occured";

        } catch (EOFException eof ){
            We_Result.Msg =  String.valueOf(context.getResources().getString(R.string.ConnectError))     ;

            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();
            resTxt = "Error occured";
        }
        catch (Exception e) {
            We_Result.Msg =  String.valueOf(context.getResources().getString(R.string.ConnectError))     ;

            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
            resTxt = "Error occured";
        }
        //Return resTxt to calling object

    }
    public long SaveManLocationsNew(CustLocaltions obj) {
        if (ComInfo.ComNo== Companies.Afrah.getValue()) {
            URL = "http://176.29.108.202:3738/CV.asmx";

        }
        We_Result.Msg =  ""     ;
        We_Result.ID = -2;

        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, "SaveCustLocations");


        // CusNo   , DayNum ,End_Time  , ManNo ,Start_Time, Tr_Data  ,  no ,    OrderNo
        //       Duration  ,  VisitType  ,  X , Y  ,Locat ,  VisitType1  ,VisitType2,VisitType3 ,
        //      VisitType4 ,Po_Order , Notes

        PropertyInfo P_CustNo = new PropertyInfo();
        P_CustNo.setName("CustNo");
        P_CustNo.setValue(obj.getCustNo());
        P_CustNo.setType(String.class);
        request.addProperty(P_CustNo);

        PropertyInfo P_CustName = new PropertyInfo();
        P_CustName.setName("CustName");
        P_CustName.setValue(obj.getCustNm());
        P_CustName.setType(String.class);
        request.addProperty(P_CustName);

        PropertyInfo P_DayNum = new PropertyInfo();
        P_DayNum.setName("ManNo");
        P_DayNum.setValue(obj.getManNo());
        P_DayNum.setType(String.class);
        request.addProperty(P_DayNum);

        PropertyInfo P_Lat_X= new PropertyInfo();
        P_Lat_X.setName("Lat_X");
        P_Lat_X.setValue(obj.getLat_X());
        P_Lat_X.setType(String.class);
        request.addProperty(P_Lat_X);

        PropertyInfo P_Lat_Y= new PropertyInfo();
        P_Lat_Y.setName("Lat_Y");
        P_Lat_Y.setValue(obj.getLat_Y());
        P_Lat_Y.setType(String.class);
        request.addProperty(P_Lat_Y);

        PropertyInfo P_Locat= new PropertyInfo();
        P_Locat.setName("Locat");
        P_Locat.setValue(obj.getLocat());
        P_Locat.setType(String.class);
        request.addProperty(P_Locat);

        PropertyInfo P_Note= new PropertyInfo();
        P_Note.setName("Note");
        P_Note.setValue(obj.getNote());
        P_Note.setType(String.class);
        request.addProperty(P_Note);




        PropertyInfo P_Tr_Date= new PropertyInfo();
        P_Tr_Date.setName("Tr_Date");
        P_Tr_Date.setValue(obj.getTr_Date());
        P_Tr_Date.setType(String.class);
        request.addProperty(P_Tr_Date);

        PropertyInfo P_PersonNm= new PropertyInfo();
        P_PersonNm.setName("PersonNm");
        P_PersonNm.setValue(obj.getPersonNm());
        P_PersonNm.setType(String.class);
        request.addProperty(P_PersonNm);

        PropertyInfo P_MobileNo= new PropertyInfo();
        P_MobileNo.setName("MobileNo");
        P_MobileNo.setValue(obj.getMobileNo());
        P_MobileNo.setType(String.class);
        request.addProperty(P_MobileNo);

        PropertyInfo P_Stutes= new PropertyInfo();
        P_Stutes.setName("Stutes");
        P_Stutes.setValue(obj.getStutes());
        P_Stutes.setType(String.class);
        request.addProperty(P_Stutes);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {
            androidHttpTransport.call( "http://tempuri.org/SaveCustLocations", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            // Assign it to resTxt variable static variable
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            //Print error
            e.printStackTrace();
            //Assign error message to resTxt
           // resTxt = "Error occured";
        }
        return   We_Result.ID;
        //Return resTxt to calling object

    }
    public long SaveManVisitsNew(Cls_SaleManDailyRound obj) {

        We_Result.Msg = "";
        We_Result.ID = -2;
        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, "SaveALLVisits");


        // CusNo   , DayNum ,End_Time  , ManNo ,Start_Time, Tr_Data  ,  no ,    OrderNo
        //       Duration  ,  VisitType  ,  X , Y  ,Locat ,  VisitType1  ,VisitType2,VisitType3 ,
        //      VisitType4 ,Po_Order , Notes

        PropertyInfo P_CusNo = new PropertyInfo();
        P_CusNo.setName("CusNo");
        P_CusNo.setValue(obj.getCusNo());
        P_CusNo.setType(String.class);
        request.addProperty(P_CusNo);

        PropertyInfo P_DayNum = new PropertyInfo();
        P_DayNum.setName("DayNum");
        P_DayNum.setValue(obj.getDayNum());
        P_DayNum.setType(String.class);
        request.addProperty(P_DayNum);

        PropertyInfo P_End_Time = new PropertyInfo();
        P_End_Time.setName("End_Time");
        P_End_Time.setValue(obj.getEnd_Time());
        P_End_Time.setType(String.class);
        request.addProperty(P_End_Time);

        PropertyInfo P_Start_Time = new PropertyInfo();
        P_Start_Time.setName("Start_Time");
        P_Start_Time.setValue(obj.getStart_Time());
        P_Start_Time.setType(String.class);
        request.addProperty(P_Start_Time);

        PropertyInfo P_Tr_Data = new PropertyInfo();
        P_Tr_Data.setName("Tr_Data");
        P_Tr_Data.setValue(obj.getTr_Data());
        P_Tr_Data.setType(String.class);
        request.addProperty(P_Tr_Data);

        PropertyInfo P_ManNo = new PropertyInfo();
        P_ManNo.setName("ManNo");
        P_ManNo.setValue(obj.getManNo());
        P_ManNo.setType(String.class);
        request.addProperty(P_ManNo);

        PropertyInfo P_no = new PropertyInfo();
        P_no.setName("no");
        P_no.setValue(obj.getNo());
        P_no.setType(String.class);
        request.addProperty(P_no);

        PropertyInfo P_OrderNo = new PropertyInfo();
        P_OrderNo.setName("OrderNo");
        P_OrderNo.setValue(obj.getOrderNo());
        P_OrderNo.setType(String.class);
        request.addProperty(P_OrderNo);

        PropertyInfo P_Duration = new PropertyInfo();
        P_Duration.setName("Duration");
        P_Duration.setValue(obj.getDuration());
        P_Duration.setType(String.class);
        request.addProperty(P_Duration);

        PropertyInfo P_VisitType = new PropertyInfo();
        P_VisitType.setName("VisitType");
        P_VisitType.setValue("");
        P_VisitType.setType(String.class);
        request.addProperty(P_VisitType);

        PropertyInfo P_X = new PropertyInfo();
        P_X.setName("X");
        P_X.setValue("");
        P_X.setType(String.class);
        request.addProperty(P_X);

        PropertyInfo P_Y = new PropertyInfo();
        P_Y.setName("Y");
        P_Y.setValue("");
        P_Y.setType(String.class);
        request.addProperty(P_Y);

        PropertyInfo P_Locat = new PropertyInfo();
        P_Locat.setName("Locat");
        P_Locat.setValue("");
        P_Locat.setType(String.class);
        request.addProperty(P_Locat);

        PropertyInfo P_VisitType1 = new PropertyInfo();
        P_VisitType1.setName("VisitType1");
        P_VisitType1.setValue("");
        P_VisitType1.setType(String.class);
        request.addProperty(P_VisitType1);

        PropertyInfo P_VisitType2 = new PropertyInfo();
        P_VisitType2.setName("VisitType2");
        P_VisitType2.setValue("");
        P_VisitType2.setType(String.class);
        request.addProperty(P_VisitType2);

        PropertyInfo P_VisitType3 = new PropertyInfo();
        P_VisitType3.setName("VisitType3");
        P_VisitType3.setValue("");
        P_VisitType3.setType(String.class);
        request.addProperty(P_VisitType3);

        PropertyInfo P_VisitType4 = new PropertyInfo();
        P_VisitType4.setName("VisitType4");
        P_VisitType4.setValue("");
        P_VisitType4.setType(String.class);
        request.addProperty(P_VisitType4);

        PropertyInfo P_Po_Order = new PropertyInfo();
        P_Po_Order.setName("Po_Order");
        P_Po_Order.setValue("");
        P_Po_Order.setType(String.class);
        request.addProperty(P_Po_Order);

        PropertyInfo P_Notes = new PropertyInfo();
        P_Notes.setName("Notes");
        P_Notes.setValue("");
        P_Notes.setType(String.class);
        request.addProperty(P_Notes);

        PropertyInfo P_COMPUTERNAME = new PropertyInfo();
        P_COMPUTERNAME.setName("COMPUTERNAME");
        P_COMPUTERNAME.setValue("");
        P_COMPUTERNAME.setType(String.class);
        request.addProperty(P_COMPUTERNAME);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object response = null;
        try {
            androidHttpTransport.call("http://tempuri.org/SaveALLVisits", envelope);
            SoapObject result = (SoapObject) envelope.getResponse();
            // Assign it to resTxt variable static variable
            We_Result.Msg = result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (Exception e) {
            We_Result.Msg = "عملية الاتصال بالسيرفر لم تتم بنجاح";//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            //Print error
            e.printStackTrace();
            //Assign error message to resTxt
            resTxt = "Error occured";
        }
        return We_Result.ID;
        //Return resTxt to calling object
    }
    public long SaveManVisitsNew1(Cls_SaleManDailyRoundMed obj) {

        We_Result.Msg =  ""     ;
        We_Result.ID = -2;

        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, "SaveALLVisits1");


        // CusNo   , DayNum ,End_Time  , ManNo ,Start_Time, Tr_Data  ,  no ,    OrderNo
        //       Duration  ,  VisitType  ,  X , Y  ,Locat ,  VisitType1  ,VisitType2,VisitType3 ,
        //      VisitType4 ,Po_Order , Notes

        PropertyInfo P_CusNo = new PropertyInfo();
        P_CusNo.setName("CusNo");
        P_CusNo.setValue(obj.getCusNo());
        P_CusNo.setType(String.class);
        request.addProperty(P_CusNo);

        PropertyInfo P_DayNum = new PropertyInfo();
        P_DayNum.setName("DayNum");
        P_DayNum.setValue(obj.getDayNum());
        P_DayNum.setType(String.class);
        request.addProperty(P_DayNum);

        PropertyInfo P_End_Time= new PropertyInfo();
        P_End_Time.setName("End_Time");
        P_End_Time.setValue(obj.getEnd_Time());
        P_End_Time.setType(String.class);
        request.addProperty(P_End_Time);

        PropertyInfo P_Start_Time= new PropertyInfo();
        P_Start_Time.setName("Start_Time");
        P_Start_Time.setValue(obj.getStart_Time());
        P_Start_Time.setType(String.class);
        request.addProperty(P_Start_Time);

        PropertyInfo P_Tr_Data= new PropertyInfo();
        P_Tr_Data.setName("Tr_Data");
        P_Tr_Data.setValue(obj.getTr_Data());
        P_Tr_Data.setType(String.class);
        request.addProperty(P_Tr_Data);

        PropertyInfo P_ManNo= new PropertyInfo();
        P_ManNo.setName("ManNo");
        P_ManNo.setValue(obj.getManNo());
        P_ManNo.setType(String.class);
        request.addProperty(P_ManNo);




        PropertyInfo P_no= new PropertyInfo();
        P_no.setName("no");
        P_no.setValue(obj.getNo());
        P_no.setType(String.class);
        request.addProperty(P_no);

        PropertyInfo P_OrderNo= new PropertyInfo();
        P_OrderNo.setName("OrderNo");
        P_OrderNo.setValue(obj.getOrderNo());
        P_OrderNo.setType(String.class);
        request.addProperty(P_OrderNo);

        PropertyInfo P_Duration= new PropertyInfo();
        P_Duration.setName("Duration");
        P_Duration.setValue(obj.getDuration());
        P_Duration.setType(String.class);
        request.addProperty(P_Duration);

        PropertyInfo P_VisitType= new PropertyInfo();
        P_VisitType.setName("VisitType");
        P_VisitType.setValue(obj.getVisitType());
        P_VisitType.setType(String.class);
        request.addProperty(P_VisitType);

        PropertyInfo P_X= new PropertyInfo();
        P_X.setName("X");
        P_X.setValue(obj.getX());
        P_X.setType(String.class);
        request.addProperty(P_X);

        PropertyInfo P_Y= new PropertyInfo();
        P_Y.setName("Y");
        P_Y.setValue(obj.getY());
        P_Y.setType(String.class);
        request.addProperty(P_Y);

        PropertyInfo P_Locat= new PropertyInfo();
        P_Locat.setName("Locat");
        P_Locat.setValue(obj.getLocat());
        P_Locat.setType(String.class);
        request.addProperty(P_Locat);

        PropertyInfo P_VisitType1= new PropertyInfo();
        P_VisitType1.setName("VisitType1");
        P_VisitType1.setValue(obj.getVisitType1());
        P_VisitType1.setType(String.class);
        request.addProperty(P_VisitType1);

        PropertyInfo P_VisitType2= new PropertyInfo();
        P_VisitType2.setName("VisitType2");
        P_VisitType2.setValue(obj.getVisitType2());
        P_VisitType2.setType(String.class);
        request.addProperty(P_VisitType2);

        PropertyInfo P_VisitType3= new PropertyInfo();
        P_VisitType3.setName("VisitType3");
        P_VisitType3.setValue(obj.getVisitType3());
        P_VisitType3.setType(String.class);
        request.addProperty(P_VisitType3);

        PropertyInfo P_VisitType4= new PropertyInfo();
        P_VisitType4.setName("VisitType4");
        P_VisitType4.setValue(obj.getVisitType4());
        P_VisitType4.setType(String.class);
        request.addProperty(P_VisitType4);

        PropertyInfo P_Po_Order= new PropertyInfo();
        P_Po_Order.setName("Po_Order");
        P_Po_Order.setValue(obj.getPo_Order());
        P_Po_Order.setType(String.class);
        request.addProperty(P_Po_Order);

        PropertyInfo P_Notes= new PropertyInfo();
        P_Notes.setName("Notes");
        P_Notes.setValue(obj.getNotes());
        P_Notes.setType(String.class);
        request.addProperty(P_Notes);


        PropertyInfo P_COMPUTERNAME= new PropertyInfo();
        P_COMPUTERNAME.setName("COMPUTERNAME");
        P_COMPUTERNAME.setValue(obj.getCOMPUTERNAME());
        P_COMPUTERNAME.setType(String.class);
        request.addProperty(P_COMPUTERNAME);






        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,TIME_OUT_CONN);
        Object  response =null;
        try {
            androidHttpTransport.call( "http://tempuri.org/SaveALLVisits1", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            // Assign it to resTxt variable static variable
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
        } catch (Exception e) {  We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            //Print error
            e.printStackTrace();
            //Assign error message to resTxt
            resTxt = "Error occured";
        }
        return   We_Result.ID;
        //Return resTxt to calling object

    }
    public long SaveCustNotes(Cls_CustNotes obj) {

        We_Result.Msg =  ""     ;
        We_Result.ID = -2;

        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, "SaveALLCustNotes");


        PropertyInfo P_CustNo = new PropertyInfo();
        P_CustNo.setName("CustNo");
        P_CustNo.setValue(obj.getCustNo());
        P_CustNo.setType(String.class);
        request.addProperty(P_CustNo);

        PropertyInfo P_ManNo = new PropertyInfo();
        P_ManNo.setName("ManNo");
        P_ManNo.setValue(obj.getManNo());
        P_ManNo.setType(String.class);
        request.addProperty(P_ManNo);

        PropertyInfo getNotes= new PropertyInfo();
        getNotes.setName("Notes");
        getNotes.setValue(obj.getNotes());
        getNotes.setType(String.class);
        request.addProperty(getNotes);

        PropertyInfo P_NotesDate= new PropertyInfo();
        P_NotesDate.setName("NotesDate");
        P_NotesDate.setValue(obj.getNotesDate());
        P_NotesDate.setType(String.class);
        request.addProperty(P_NotesDate);




        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {
            androidHttpTransport.call( "http://tempuri.org/SaveALLCustNotes", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            // Assign it to resTxt variable static variable
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            //Print error
            e.printStackTrace();
            //Assign error message to resTxt
            resTxt = "Error occured";
        }
        return   We_Result.ID;
        //Return resTxt to calling object
    }


    public long PostManTrans(String ManNo,String CustNo,String ScreenCode,String ActionNo,String TransNo, String Trans_Date,String TabletId,String BattryCharge ,String   Notes) {
        if (ComInfo.ComNo== Companies.Afrah.getValue()) {
            URL = "http://176.29.108.202:3738/CV.asmx";

        }
        We_Result.Msg = "";
        We_Result.ID = -1;
        SoapObject request = new SoapObject(NAMESPACE, "PostManTransAction");

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);


        PropertyInfo parm_CustNo = new PropertyInfo();
        parm_CustNo.setName("CustNo");
        parm_CustNo.setValue(CustNo);
        parm_CustNo.setType(String.class);
        request.addProperty(parm_CustNo);


        PropertyInfo parm_ScreenCode= new PropertyInfo();
        parm_ScreenCode.setName("ScreenCode");
        parm_ScreenCode.setValue(ScreenCode);
        parm_ScreenCode.setType(String.class);
        request.addProperty(parm_ScreenCode);


        PropertyInfo parm_ActionNo= new PropertyInfo();
        parm_ActionNo.setName("ActionNo");
        parm_ActionNo.setValue(ActionNo);
        parm_ActionNo.setType(String.class);
        request.addProperty(parm_ActionNo);


        PropertyInfo parm_TransNo = new PropertyInfo();
        parm_TransNo.setName("TransNo");
        parm_TransNo.setValue(TransNo);
        parm_TransNo.setType(String.class);
        request.addProperty(parm_TransNo);


        PropertyInfo parm_Trans_Date = new PropertyInfo();
        parm_Trans_Date.setName("Trans_Date");
        parm_Trans_Date.setValue(Trans_Date);
        parm_Trans_Date.setType(String.class);
        request.addProperty(parm_Trans_Date);


        PropertyInfo parm_TabletId = new PropertyInfo();
        parm_TabletId.setName("TabletId");
        parm_TabletId.setValue(TabletId);
        parm_TabletId.setType(String.class);
        request.addProperty(parm_TabletId);


        PropertyInfo parm_BattryCharge = new PropertyInfo();
        parm_BattryCharge.setName("BattryCharge");
        parm_BattryCharge.setValue(BattryCharge);
        parm_BattryCharge.setType(String.class);
        request.addProperty(parm_BattryCharge);




        PropertyInfo parm_Notes = new PropertyInfo();
        parm_Notes.setName("Notes");
        parm_Notes.setValue(Notes);
        parm_Notes.setType(String.class);
        request.addProperty(parm_Notes);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object response = null;
        try {

            androidHttpTransport.call("http://tempuri.org/PostManTransAction", envelope);
            SoapObject result = (SoapObject) envelope.getResponse();
            We_Result.Msg = result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (Exception e) {
            We_Result.Msg = "عملية الاتصال بالسيرفر لم تتم بنجاح";//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }
        return We_Result.ID;
    }
    public long PostManAtten( String ID,String UserID, String ActionNo,String ActionDate,String ActionTime,String Coor_X ,String Coor_Y  ,String ManAddress ,String Notes
            ,String Img ,String BattryLevel,String TabletName ,String DayNo  ,String DayNm) {
        if (ComInfo.ComNo== Companies.Afrah.getValue()) {
            URL = "http://176.29.108.202:3738/CV.asmx";

        }
        We_Result.Msg = "";
        We_Result.ID = -1;
        SoapObject request = new SoapObject(NAMESPACE, "PostManAtten");

        PropertyInfo parm_Id = new PropertyInfo();
        parm_Id.setName("ID");
        parm_Id.setValue(ID);
        parm_Id.setType(String.class);
        request.addProperty(parm_Id);


        PropertyInfo parm_ManId = new PropertyInfo();
        parm_ManId.setName("UserID");
        parm_ManId.setValue(UserID);
        parm_ManId.setType(String.class);
        request.addProperty(parm_ManId);


        PropertyInfo parm_ActionNo= new PropertyInfo();
        parm_ActionNo.setName("ActionNo");
        parm_ActionNo.setValue(ActionNo);
        parm_ActionNo.setType(String.class);
        request.addProperty(parm_ActionNo);


        PropertyInfo parm_ActionDate = new PropertyInfo();
        parm_ActionDate.setName("ActionDate");
        parm_ActionDate.setValue(ActionDate);
        parm_ActionDate.setType(String.class);
        request.addProperty(parm_ActionDate);


        PropertyInfo parm_ActionTime = new PropertyInfo();
        parm_ActionTime.setName("ActionTime");
        parm_ActionTime.setValue(ActionTime);
        parm_ActionTime.setType(String.class);
        request.addProperty(parm_ActionTime);


        PropertyInfo parm_Coor_X = new PropertyInfo();
        parm_Coor_X.setName("Coor_X");
        parm_Coor_X.setValue(Coor_X);
        parm_Coor_X.setType(String.class);
        request.addProperty(parm_Coor_X);


        PropertyInfo parm_Coor_Y = new PropertyInfo();
        parm_Coor_Y.setName("Coor_Y");
        parm_Coor_Y.setValue(Coor_Y);
        parm_Coor_Y.setType(String.class);
        request.addProperty(parm_Coor_Y);


        PropertyInfo parm_ManAddress = new PropertyInfo();
        parm_ManAddress.setName("ManAddress");
        parm_ManAddress.setValue(ManAddress);
        parm_ManAddress.setType(String.class);
        request.addProperty(parm_ManAddress);




        PropertyInfo parm_Notes = new PropertyInfo();
        parm_Notes.setName("Notes");
        parm_Notes.setValue(Notes);
        parm_Notes.setType(String.class);
        request.addProperty(parm_Notes);



        PropertyInfo parmImg= new PropertyInfo();
        parmImg.setName("Img");
        parmImg.setValue(Img);
        parmImg.setType(String.class);
        request.addProperty(parmImg);




        PropertyInfo parmBattryLevel = new PropertyInfo();
        parmBattryLevel.setName("BattryLevel");
        parmBattryLevel.setValue(BattryLevel);
        parmBattryLevel.setType(String.class);
        request.addProperty(parmBattryLevel);



        PropertyInfo parm_TabletName = new PropertyInfo();
        parm_TabletName.setName("TabletName");
        parm_TabletName.setValue(TabletName);
        parm_TabletName.setType(String.class);
        request.addProperty(parm_TabletName);



        PropertyInfo parm_DayNo = new PropertyInfo();
        parm_DayNo.setName("DayNo");
        parm_DayNo.setValue(DayNo);
        parm_DayNo.setType(String.class);
        request.addProperty(parm_DayNo);



        PropertyInfo parm_DayNm= new PropertyInfo();
        parm_DayNm.setName("DayNm");
        parm_DayNm.setValue(DayNm);
        parm_DayNm.setType(String.class);
        request.addProperty(parm_DayNm);







        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object response = null;
        try {

            androidHttpTransport.call("http://tempuri.org/PostManAtten", envelope);
            SoapObject result = (SoapObject) envelope.getResponse();
            We_Result.Msg = result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (Exception e) {
            We_Result.Msg = "عملية الاتصال بالسيرفر لم تتم بنجاح";//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }
        return We_Result.ID;
    }
    public long PostManVac( String Id, String ManId, String FromDate, String ToDate, String Note,String ProcedureType , String VacationType,String Op) {

        We_Result.Msg = "";
        We_Result.ID = -1;
        SoapObject request = new SoapObject(NAMESPACE, "PostManVac");

        PropertyInfo parm_Id = new PropertyInfo();
        parm_Id.setName("Id");
        parm_Id.setValue(Id);
        parm_Id.setType(String.class);
        request.addProperty(parm_Id);


        PropertyInfo parm_ManId = new PropertyInfo();
        parm_ManId.setName("ManId");
        parm_ManId.setValue(ManId);
        parm_ManId.setType(String.class);
        request.addProperty(parm_ManId);


        PropertyInfo parm_FromDate = new PropertyInfo();
        parm_FromDate.setName("FromDate");
        parm_FromDate.setValue(FromDate);
        parm_FromDate.setType(String.class);
        request.addProperty(parm_FromDate);


        PropertyInfo parm_ToDate = new PropertyInfo();
        parm_ToDate.setName("ToDate");
        parm_ToDate.setValue(ToDate);
        parm_ToDate.setType(String.class);
        request.addProperty(parm_ToDate);


        PropertyInfo parm_Note = new PropertyInfo();
        parm_Note.setName("Note");
        parm_Note.setValue(Note);
        parm_Note.setType(String.class);
        request.addProperty(parm_Note);


        PropertyInfo parm_ProcedureType = new PropertyInfo();
        parm_ProcedureType.setName("ProcedureType");
        parm_ProcedureType.setValue(ProcedureType);
        parm_ProcedureType.setType(String.class);
        request.addProperty(parm_ProcedureType);


        PropertyInfo parm_VacationType = new PropertyInfo();
        parm_VacationType.setName("VacationType");
        parm_VacationType.setValue(VacationType);
        parm_VacationType.setType(String.class);
        request.addProperty(parm_VacationType);


        PropertyInfo parm_Op = new PropertyInfo();
        parm_Op.setName("Op");
        parm_Op.setValue(Op);
        parm_Op.setType(String.class);
        request.addProperty(parm_Op);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object response = null;
        try {

            androidHttpTransport.call("http://tempuri.org/PostManVac", envelope);
            SoapObject result = (SoapObject) envelope.getResponse();
            We_Result.Msg = result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (Exception e) {
            We_Result.Msg = "عملية الاتصال بالسيرفر لم تتم بنجاح";//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }
        return We_Result.ID;
    }
    public void Get_Man_Photo(String ManNo) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "Get_Man_Photo");
        // Property which holds input parameters

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call("http://tempuri.org/Get_Man_Photo", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void GetDisCount(String Bransh) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GetDisCount");
        // Property which holds input parameters

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("Bransh");
        parm_ManNo.setValue(Bransh);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call("http://tempuri.org/GetDisCount", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void Getcode_d(String UserId) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "Getcode_d");
        // Property which holds input parameters

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("UserId");
        parm_ManNo.setValue(UserId);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call("http://tempuri.org/Getcode_d", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void SetVisits(String ManNo) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "SetVisits");
        // Property which holds input parameters

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);




        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call("http://tempuri.org/SetVisits", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void Setcode_d(String UserId,String Code) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "Setcode_d");
        // Property which holds input parameters

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("UserId");
        parm_ManNo.setValue(UserId);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);


        PropertyInfo parm_Code = new PropertyInfo();
        parm_Code.setName("Code");
        parm_Code.setValue(Code);
        parm_Code.setType(String.class);
        request.addProperty(parm_Code);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call("http://tempuri.org/Setcode_d", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void setcode(String barcode,String orderno) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "setcode");
        // Property which holds input parameters

        PropertyInfo parm_barcode = new PropertyInfo();
        parm_barcode.setName("barcode");
        parm_barcode.setValue(barcode);
        parm_barcode.setType(String.class);
        request.addProperty(parm_barcode);

        PropertyInfo parm_orderno = new PropertyInfo();
        parm_orderno.setName("orderno");
        parm_orderno.setValue(orderno);
        parm_orderno.setType(String.class);
        request.addProperty(parm_orderno);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call("http://tempuri.org/setcode", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void Get_DetailsMan(String ManNo) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "Get_DetailsMan");
        // Property which holds input parameters

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call("http://tempuri.org/Get_DetailsMan", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void GetMaxOrder1(int ManNo,int type) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GetMaxOrder1");
        // Property which holds input parameters

        PropertyInfo parm_ManNo = new PropertyInfo();

        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);
        PropertyInfo parm_type = new PropertyInfo();
        parm_type.setName("type");
        parm_type.setValue(type);
        parm_type.setType(String.class);
        request.addProperty(parm_type);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call("http://tempuri.org/GetMaxOrder1", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public long SaveInovicePayments(String Json ) {
        try {
            We_Result.Msg="";
            We_Result.ID =-1;
            SoapObject request = new SoapObject(NAMESPACE, "SaveInovicePayments");
            PropertyInfo StrJson = new PropertyInfo();
            StrJson.setName("JsonStr");
            StrJson.setValue(Json);
            StrJson.setType(String.class);
            request.addProperty(StrJson);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet=true;

            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            Object  response =null;


            androidHttpTransport.call( "http://tempuri.org/SaveInovicePayments", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح";
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح";
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"   ;// + String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }
        return  We_Result.ID;

    }
    public void Get_ManQuotaReport1(String man_ID, String FromDate,String ToDate,String Country) {

        We_Result.Msg="";
        We_Result.ID =-1;

        SoapObject request = new SoapObject(NAMESPACE, "Get_SalesManQuotaReport");

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(man_ID);
        parm_ManNo.setType(String.class);

        PropertyInfo parm_Country = new PropertyInfo();
        parm_Country.setName("Country");
        parm_Country.setValue("-1");
        parm_Country.setType(String.class);

        PropertyInfo parm_FDate = new PropertyInfo();
        parm_FDate.setName("FromDate");
        parm_FDate.setValue(FromDate);
        parm_FDate.setType(String.class);


        PropertyInfo parm_TDate = new PropertyInfo();
        parm_TDate.setName("ToDate");
        parm_TDate.setValue(ToDate);
        parm_TDate.setType(String.class);





        // Add the property to request object
        request.addProperty(parm_ManNo);
        request.addProperty(parm_Country);

        request.addProperty(parm_FDate);
        request.addProperty(parm_TDate);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/Get_SalesManQuotaReport",envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }
        //Return resTxt to calling object

    }
    public void GatTableCollections(String Man_No,String FDate,String TDate,String CustNo) {

        We_Result.Msg="";
        We_Result.ID =-1;
        if (CustNo.equalsIgnoreCase("")){
            CustNo = "-1";
        }
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, "GatTableCollections");
        // Property which holds input parameters
        PropertyInfo parm_Man_No = new PropertyInfo();
        parm_Man_No.setName("manNo");
        parm_Man_No.setValue(Man_No);
        parm_Man_No.setType(String.class);


        PropertyInfo parm_FDate = new PropertyInfo();
        parm_FDate.setName("FDate");
        parm_FDate.setValue(FDate);
        parm_FDate.setType(String.class);




        PropertyInfo parm_ToDate = new PropertyInfo();
        parm_ToDate.setName("TDate");
        parm_ToDate.setValue(TDate);
        parm_ToDate.setType(String.class);


        PropertyInfo parm_Flag = new PropertyInfo();
        parm_Flag.setName("custNo");
        parm_Flag.setValue(CustNo);
        parm_Flag.setType(String.class);


        // Add the property to request object
        request.addProperty(parm_Man_No);
        request.addProperty(parm_FDate);
        request.addProperty(parm_ToDate);
        request.addProperty(parm_Flag);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invoke web service
            androidHttpTransport.call("http://tempuri.org/GatTableCollections", envelope);

            SoapObject result  = (SoapObject) envelope.getResponse();


            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }

    }
    public void CallReport(String AccNo, String FromDate,String ToDate,String UserNo) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request ;
        if (ComInfo.ComNo== Companies.Afrah.getValue()) {
          request = new SoapObject(NAMESPACE, "GetAccReportT");

       }
       else
       {
            request = new SoapObject(NAMESPACE, "GetAccReport");
     }


        PropertyInfo parm_AccNo = new PropertyInfo();

        parm_AccNo.setName("AccNo");

        parm_AccNo.setValue(AccNo);
        parm_AccNo.setType(String.class);

        PropertyInfo parm_FDate = new PropertyInfo();
        parm_FDate.setName("FDate");
        parm_FDate.setValue(FromDate);
        parm_FDate.setType(String.class);


        PropertyInfo parm_TDate = new PropertyInfo();
        parm_TDate.setName("TDate");
        parm_TDate.setValue(ToDate);
        parm_TDate.setType(String.class);



        PropertyInfo parm_UserNo = new PropertyInfo();
        parm_UserNo.setName("UserNo");
        parm_UserNo.setValue(UserNo);
        parm_UserNo.setType(String.class);

        // Add the property to request object
        request.addProperty(parm_AccNo);
        request.addProperty(parm_FDate);
        request.addProperty(parm_TDate);
        request.addProperty(parm_UserNo);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            if (ComInfo.ComNo== Companies.Afrah.getValue()) {
                androidHttpTransport.call("http://tempuri.org/GetAccReportT", envelope);

            }
            else
            {
                androidHttpTransport.call("http://tempuri.org/GetAccReport", envelope);
            }

            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }
        //Return resTxt to calling object

    }
    public void SaveEnterQty(String Json) {
        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "EnterQty");
        PropertyInfo sayHelloPI = new PropertyInfo();
        sayHelloPI.setName("JsonStr");

        sayHelloPI.setValue(Json);
        sayHelloPI.setType(String.class);
        request.addProperty(sayHelloPI);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,40000);
        Object  response =null;
        try {
            androidHttpTransport.call( "http://tempuri.org/EnterQty", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            // Assign it to resTxt variable static variable
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.Msg =  result.getProperty("Msg").toString().replace("#M"," الكمية غير متاحة من المادة").replace("#QTY","كمية المستودع ");

            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void SaveTrandferStoreQty(String Json) {
        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "SaveStoreQty");
        PropertyInfo sayHelloPI = new PropertyInfo();
        sayHelloPI.setName("JsonStr");

        sayHelloPI.setValue(Json);
        sayHelloPI.setType(String.class);
        request.addProperty(sayHelloPI);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,40000);
        Object  response =null;
        try {
            androidHttpTransport.call( "http://tempuri.org/SaveStoreQty", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            // Assign it to resTxt variable static variable
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.Msg =  result.getProperty("Msg").toString().replace("#M"," الكمية غير متاحة من المادة").replace("#QTY","كمية المستودع ");

            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void GetAccNo(String AccNo, String webMethName) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GetAccNo");

        PropertyInfo parm_AccNo = new PropertyInfo();

        parm_AccNo.setName("AccNo");
        parm_AccNo.setValue(AccNo);
        parm_AccNo.setType(String.class);



        request.addProperty(parm_AccNo);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call("http://tempuri.org/GetAccNo", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();


            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }
        //Return resTxt to calling object

    }
    public void GetRndNum() {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "Get_RndNum");




        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call("http://tempuri.org/Get_RndNum", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void GetAccNoBanks() {

        We_Result.Msg="";
        We_Result.ID =-1;

        SoapObject request = new SoapObject(NAMESPACE, "GetBanks");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/GetBanks", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void Get_Stores() {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GetStores");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call("http://tempuri.org/GetStores", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void GetCustomers(String ManNo) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request;
        if (ComInfo.ComNo== Companies.Afrah.getValue()) {
             request = new SoapObject(NAMESPACE, "GetCustomersT");

        }
        else
        {
             request = new SoapObject(NAMESPACE, "GetCustomers");
        }

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            if (ComInfo.ComNo== Companies.Afrah.getValue()) {
                androidHttpTransport.call("http://tempuri.org/GetCustomersT", envelope);

            }
            else {
                androidHttpTransport.call("http://tempuri.org/GetCustomers", envelope);
            }
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void GetCustomers_man(String ManNo) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request;
        if (ComInfo.ComNo== Companies.Afrah.getValue()) {
             request = new SoapObject(NAMESPACE, "GetCustomers_manT");

        }
        else
        {
             request = new SoapObject(NAMESPACE, "GetCustomers_man");
        }


        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            if (ComInfo.ComNo== Companies.Afrah.getValue()) {
                androidHttpTransport.call("http://tempuri.org/GetCustomers_manT", envelope);

            }
            else
            {
                androidHttpTransport.call("http://tempuri.org/GetCustomers_man", envelope);
            }

            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void GetCustomersMsg(String ManNo) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GetCustomersMsg");

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/GetCustomersMsg", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void Get_CustLastPrice(String ManNo) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "LastCustPrice");

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,TIME_OUT_CONN);

        try {

            androidHttpTransport.call("http://tempuri.org/LastCustPrice", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;//+ String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void GetTab_Password() {
        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "Get_Tab_Password");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/Get_Tab_Password", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//;+ String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void GetGetManPermession(String ManNo) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GetManPermession");

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/GetManPermession", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;//+ String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void Get_ManExceptions(String ManNo) {

        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GetManException1");

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/GetManException1", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;//+ String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void GetOrdersSerials(String ManNo) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GetMaxOrdersMed");

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/GetMaxOrdersMed", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;//+ String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void GetOrdersSerials1(String ManNo) {
   if (ComInfo.ComNo== Companies.Afrah.getValue()) {
       URL = "http://176.29.108.202:3738/CV.asmx";

        }
        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GetMaxOrders");

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/GetMaxOrders", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;//+ String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }




    public long JsonSaveExchange(String JsonStr ) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "SaveExchange");


        PropertyInfo parm_myear = new PropertyInfo();
        parm_myear.setName("JsonStr");
        parm_myear.setValue(JsonStr);
        parm_myear.setType(String.class);
        request.addProperty(parm_myear);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/SaveExchange", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;//+ String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }

        return   We_Result.ID;

    }

    public void Search_PurchesOrders(String V_TYPE , String  myear) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "Search_PurchesOrders");

        PropertyInfo parm_V_TYPE = new PropertyInfo();
        parm_V_TYPE.setName("V_TYPE");
        parm_V_TYPE.setValue(V_TYPE);
        parm_V_TYPE.setType(String.class);
        request.addProperty(parm_V_TYPE);


        PropertyInfo parm_myear = new PropertyInfo();
        parm_myear.setName("myear");
        parm_myear.setValue(myear);
        parm_myear.setType(String.class);
        request.addProperty(parm_myear);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/Search_PurchesOrders", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;//+ String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void GetLookUp(String ManNo) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GetLookUp");

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/GetLookUp", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;//+ String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void GetItems( String ManNo ) {
        SoapObject request;
        if (ComInfo.ComNo== Companies.Afrah.getValue()) {
             request = new SoapObject(NAMESPACE, "GetItemsT");

        }
        else
        { request = new SoapObject(NAMESPACE, "GetItems");

        }


        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            if (ComInfo.ComNo== Companies.Afrah.getValue()) {

                androidHttpTransport.call("http://tempuri.org/GetItemsT", envelope);

            }
            else
            {
                androidHttpTransport.call("http://tempuri.org/GetItems", envelope);

            }

            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();
            We_Result.Msg="";
            We_Result.ID =-1;

        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();
            We_Result.Msg="";
            We_Result.ID =-1;
        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;//+ String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
            We_Result.Msg="";
            We_Result.ID =-1;
        }

    }



    public void Get_CustomerQty( String itemNo) {

        We_Result.Msg="";
        We_Result.ID =-1;

        SoapObject request = new SoapObject(NAMESPACE, "Get_CustomerQty");
        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("CustomerNo");
        parm_ManNo.setValue(itemNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/Get_CustomerQty", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;//+ String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }

    }

    public void GetUnites() {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GetUnites");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call("http://tempuri.org/GetUnites", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;//+ String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void GetUnitItems() {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GetUnitItems");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/GetUnitItems", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;//+ String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }

    }
    public void Getcurf() {
        We_Result.Msg="";
        We_Result.ID =-1;

        SoapObject request = new SoapObject(NAMESPACE, "Getcurf");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call("http://tempuri.org/Getcurf", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;//+ String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }
    }
    public void deptf( String ManNo) {

        We_Result.Msg="";
        We_Result.ID =-1;

        SoapObject request = new SoapObject(NAMESPACE, "deptf");
        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/deptf", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;//+ String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }

    }
    public void Get_SalesOrderChanges(String User_ID, String Order_No ) {
        We_Result.Msg =  " "    ;
        We_Result.ID =-1;
        String resTxt = null;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, "Get_SalesOrderChange");



        PropertyInfo parm_User_ID = new PropertyInfo();
        parm_User_ID.setName("User_ID");
        parm_User_ID.setValue(User_ID);
        parm_User_ID.setType(String.class);
        request.addProperty(parm_User_ID);
        PropertyInfo parm_Order_No = new PropertyInfo();
        parm_Order_No.setName("Order_No");
        parm_Order_No.setValue(Order_No);
        parm_Order_No.setType(String.class);
        request.addProperty(parm_Order_No);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,TIME_OUT_CONN);
        Object  response =null;
        try {
            // Invoke web service
            androidHttpTransport.call("http://tempuri.org/Get_SalesOrderChange", envelope);
            // Get the response
            SoapObject result  = (SoapObject) envelope.getResponse();


            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();
            resTxt = "Error occured";

        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();
            resTxt = "Error occured";
        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
            resTxt = "Error occured";
        }

    }


    public long  save_ReturnRequest(String Json) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "save_ReturnRequest");
        PropertyInfo StrJson = new PropertyInfo();
        StrJson.setName("JsonStr");
        StrJson.setValue(Json);
        StrJson.setType(String.class);
        request.addProperty(StrJson);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call( "http://tempuri.org/save_ReturnRequest", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            // We_Result.Msg =  result.getProperty("Msg").toString().replace("#B","الباتش").replace("#I"," المادة").replace("#M","الكمية غير متاحة من المادة ");
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"   ;// + String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }
        return  We_Result.ID;

    }

    public long  Save_po(String Json, String webMethName) {

        if (ComInfo.ComNo== Companies.Afrah.getValue()) {
            URL = "http://176.29.108.202:3738/CV.asmx";

        }
        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        PropertyInfo StrJson = new PropertyInfo();
        StrJson.setName("JsonStr");
        StrJson.setValue(Json);
        StrJson.setType(String.class);
        request.addProperty(StrJson);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call( "http://tempuri.org/Insert_PurshOrder", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            // We_Result.Msg =  result.getProperty("Msg").toString().replace("#B","الباتش").replace("#I"," المادة").replace("#M","الكمية غير متاحة من المادة ");
            We_Result.Msg =  result.getProperty("Msg").toString().replace("#I"," المادة").replace("#M","الكمية غير متاحة من المادة ");
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"   ;// + String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }
        return  We_Result.ID;

    }
    public long  Insert_CustomerQTY(String Json) {


        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "Insert_CustomerQTY");
        PropertyInfo StrJson = new PropertyInfo();
        StrJson.setName("JsonStr");
        StrJson.setValue(Json);
        StrJson.setType(String.class);
        request.addProperty(StrJson);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call( "http://tempuri.org/Insert_CustomerQTY", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            // We_Result.Msg =  result.getProperty("Msg").toString().replace("#B","الباتش").replace("#I"," المادة").replace("#M","الكمية غير متاحة من المادة ");
            We_Result.Msg =  result.getProperty("Msg").toString().replace("#I"," المادة").replace("#M","الكمية غير متاحة من المادة ");
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"   ;// + String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }
        return  We_Result.ID;

    }
    public long  Save_ItemRecepit(String Json ) {
        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "Insert_ItemRecepit");
        PropertyInfo StrJson = new PropertyInfo();
        StrJson.setName("JsonStr");
        StrJson.setValue(Json);
        StrJson.setType(String.class);
        request.addProperty(StrJson);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call( "http://tempuri.org/Insert_ItemRecepit", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            // We_Result.Msg =  result.getProperty("Msg").toString().replace("#B","الباتش").replace("#I"," المادة").replace("#M","الكمية غير متاحة من المادة ");
            We_Result.Msg =  result.getProperty("Msg").toString().replace("#I"," المادة").replace("#M","الكمية غير متاحة من المادة ");
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"   ;// + String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }
        return  We_Result.ID;

    }
    public void Save_VisitImages(String CustomerNo,String OrderDate,String UserNo,String Visit_OrderNo,String Order_No
            , String ImageTime ,String ImageDesc, String ImgBase64 ) {


        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, "Save_Visit_Images");
        PropertyInfo parm_ImgBase64 = new PropertyInfo();
        parm_ImgBase64.setName("ImgBase64");
        parm_ImgBase64.setValue(ImgBase64);
        parm_ImgBase64.setType(String.class);
        request.addProperty(parm_ImgBase64);


        PropertyInfo sayCustomerNo = new PropertyInfo();
        sayCustomerNo.setName("CustomerNo");
        sayCustomerNo.setValue(CustomerNo);
        sayCustomerNo.setType(String.class);
        request.addProperty(sayCustomerNo);


        PropertyInfo saOrderDate = new PropertyInfo();
        saOrderDate.setName("OrderDate");
        saOrderDate.setValue(OrderDate);
        sayCustomerNo.setType(String.class);
        request.addProperty(saOrderDate);

        PropertyInfo sayUserNo = new PropertyInfo();
        sayUserNo.setName("UserNo");
        sayUserNo.setValue(UserNo);
        sayUserNo.setType(String.class);
        request.addProperty(sayUserNo);




        PropertyInfo sayOrder_No = new PropertyInfo();
        sayOrder_No.setName("Order_No");
        sayOrder_No.setValue(Order_No);
        sayOrder_No.setType(String.class);
        request.addProperty(sayOrder_No);



        PropertyInfo sayImageTime = new PropertyInfo();
        sayImageTime.setName("ImageTime");
        sayImageTime.setValue(ImageTime);
        sayImageTime.setType(String.class);
        request.addProperty(sayImageTime);


        PropertyInfo sayImageDesc = new PropertyInfo();
        sayImageDesc.setName("ImageDesc");
        sayImageDesc.setValue(ImageDesc);
        sayImageDesc.setType(String.class);
        request.addProperty(sayImageDesc);





        PropertyInfo parm_Visit_OrderNo= new PropertyInfo();
        parm_Visit_OrderNo.setName("Visit_OrderNo");
        parm_Visit_OrderNo.setValue(Visit_OrderNo);
        parm_Visit_OrderNo.setType(String.class);
        request.addProperty(parm_Visit_OrderNo);




        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call( "http://tempuri.org/Save_Visit_Images", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();
            resTxt = "Error occured";

        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();
            resTxt = "Error occured";
        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"   ;// + String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
            resTxt = "Error occured";
        }
        //Return resTxt to calling object

    }
 /*public void Save_VisitImages( String ImgBase64 ) {


     String resTxt = null;

     SoapObject request = new SoapObject(NAMESPACE, "Save_Visit_Images2");
     PropertyInfo parm_ImgBase64 = new PropertyInfo();
     parm_ImgBase64.setName("ImgBase64");
     parm_ImgBase64.setValue(ImgBase64);
     parm_ImgBase64.setType(String.class);
     request.addProperty(parm_ImgBase64);





     SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
             SoapEnvelope.VER11);
     envelope.dotNet=true;

     envelope.setOutputSoapObject(request);

     HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
     Object  response =null;
     try {

         androidHttpTransport.call( "http://tempuri.org/Save_Visit_Images2", envelope);
         SoapObject result  = (SoapObject) envelope.getResponse();
         We_Result.Msg =  result.getProperty("Msg").toString();
         We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



     } catch (NullPointerException   en){
         We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
         We_Result.ID = Long.parseLong("-404");
         en.printStackTrace();
         resTxt = "Error occured";

     } catch (EOFException eof ){
         We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
         We_Result.ID = Long.parseLong("-404");
         eof.printStackTrace();
         resTxt = "Error occured";
     }
     catch (Exception e) {
         We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"   ;// + String.valueOf(e.getMessage().toString());
         We_Result.ID = Long.parseLong("-404");
         e.printStackTrace();
         resTxt = "Error occured";
     }
     //Return resTxt to calling object

 }*/
    public void SavePrepareQty(String Json ) {
        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "SavePrepareQty");
        PropertyInfo sayHelloPI = new PropertyInfo();
        sayHelloPI.setName("JsonStr");
        sayHelloPI.setValue(Json);
        sayHelloPI.setType(String.class);
        request.addProperty(sayHelloPI);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call( "http://tempuri.org/SavePrepareQty", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();



            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg = "عملية الاتصال بالسيرفر لم تتم بنجاح";// + String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
        }

    }
    public void ItemCostReport(String ItemNo, String webMethName) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GatMobileItemCost");

        PropertyInfo parm_AccNo = new PropertyInfo();

        parm_AccNo.setName("ItemNo");
        parm_AccNo.setValue(ItemNo);
        parm_AccNo.setType(String.class);

        PropertyInfo parm_FDate = new PropertyInfo();
        parm_FDate.setName("FDate");
        parm_FDate.setValue("FDate");
        parm_FDate.setType(String.class);


        PropertyInfo parm_TDate = new PropertyInfo();
        parm_TDate.setName("TDate");
        parm_TDate.setValue("TDate");
        parm_TDate.setType(String.class);


        request.addProperty(parm_AccNo);
        request.addProperty(parm_FDate);
        request.addProperty(parm_TDate);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call("http://tempuri.org/GatMobileItemCost", envelope);

            SoapObject result  = (SoapObject) envelope.getResponse();


            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg = "عملية الاتصال بالسيرفر لم تتم بنجاح";// + String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }

    }
    public void GetManf() {

        if (ComInfo.ComNo== Companies.Afrah.getValue()) {
            URL = "http://176.29.108.202:3738/CV.asmx";

        }
        We_Result.Msg="";
        We_Result.ID =-1;
        try {
            SoapObject request = new SoapObject(NAMESPACE, "GetManf");
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            Object  response =null;


            androidHttpTransport.call("http://tempuri.org/GetManf", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;//+ String.valueOf( e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void GetDrivers() {

        We_Result.Msg="";
        We_Result.ID =-1;
        try {
            SoapObject request = new SoapObject(NAMESPACE, "GetDrivers");
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            Object  response =null;


            androidHttpTransport.call("http://tempuri.org/GetDrivers", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;//+ String.valueOf( e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void GetCusLastTrans(String AccNo, String Man) {

        We_Result.Msg="";
        We_Result.ID =-1;
        try {


            SoapObject request = new SoapObject(NAMESPACE, "GetCusLastTrans");

            PropertyInfo parm_AccNo = new PropertyInfo();
            parm_AccNo.setName("AccNo");
            parm_AccNo.setValue(AccNo);
            parm_AccNo.setType(String.class);
            request.addProperty(parm_AccNo);



            PropertyInfo parm_Man = new PropertyInfo();
            parm_Man.setName("Man");
            parm_Man.setValue(Man);
            parm_Man.setType(String.class);
            request.addProperty(parm_Man);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            Object  response =null;


            androidHttpTransport.call("http://tempuri.org/GetCusLastTrans", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;//+ String.valueOf( e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }

    public void GetSaleManPath(String No,String DayNum ,String Tr_Date) {

        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, "GetSaleManPath");

        PropertyInfo parm_No = new PropertyInfo();

        parm_No.setName("No");
        parm_No.setValue(No);
        parm_No.setType(String.class);
        request.addProperty(parm_No);

        PropertyInfo parm_DayNum = new PropertyInfo();
        parm_DayNum.setName("DayNum");
        parm_DayNum.setValue(DayNum);
        parm_DayNum.setType(String.class);
        request.addProperty(parm_DayNum);

        PropertyInfo parm_Tr_Date = new PropertyInfo();
        parm_Tr_Date.setName("Tr_Date");
        parm_Tr_Date.setValue(Tr_Date);
        parm_Tr_Date.setType(String.class);
        request.addProperty(parm_Tr_Date);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call("http://tempuri.org/GetSaleManPath", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();
            resTxt = "Error occured";

        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();
            resTxt = "Error occured";
        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
            resTxt = "Error occured";
        }


    }
    public long SavePayment(String Json) {
        We_Result.Msg =  "";
        We_Result.ID = -1;
        SoapObject request = new SoapObject(NAMESPACE, "SavePayment");
        PropertyInfo sayHelloPI = new PropertyInfo();
        sayHelloPI.setName("JsonStr");

        sayHelloPI.setValue(Json);
        sayHelloPI.setType(String.class);
        request.addProperty(sayHelloPI);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,40000);
        Object  response =null;
        try {
            We_Result.ID = -1;
            androidHttpTransport.call( "http://tempuri.org/SavePayment", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            // Assign it to resTxt variable static variable
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }

        return    We_Result.ID;
    }
    public void Get_Offers_Groups(String UserNo) {

        We_Result.Msg="";
        We_Result.ID =-1;


        SoapObject request = new SoapObject(NAMESPACE, "Get_Offers_Groups");

        PropertyInfo parm_UserNo = new PropertyInfo();
        parm_UserNo.setName("UserNo");
        parm_UserNo.setValue(UserNo);
        parm_UserNo.setType(String.class);
        request.addProperty(parm_UserNo);




        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {
            androidHttpTransport.call("http://tempuri.org/Get_Offers_Groups", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"   ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void Get_Offers_Dtl_Cond() {

        We_Result.Msg="";
        We_Result.ID =-1;

        try {
            SoapObject request = new SoapObject(NAMESPACE, "Get_Offers_Dtl_Cond");

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet=true;
            // Set output SOAP object
            envelope.setOutputSoapObject(request);
            // Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            Object  response =null;

            androidHttpTransport.call("http://tempuri.org/Get_Offers_Dtl_Cond", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        }

        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"        ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void GetOffers_Hdr(String UserNo) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "Offers_Hdr");
        PropertyInfo parm_UserNo = new PropertyInfo();
        parm_UserNo.setName("UserNo");
        parm_UserNo.setValue(UserNo);
        parm_UserNo.setType(String.class);
        request.addProperty(parm_UserNo);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {
            androidHttpTransport.call("http://tempuri.org/Offers_Hdr", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void Get_Offers_Dtl_Gifts() {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "Get_Offers_Dtl_Gifts");
        // Property which holds input parameters

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {
            androidHttpTransport.call("http://tempuri.org/Get_Offers_Dtl_Gifts", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }
        //Return resTxt to calling object

    }
    public void Get_Cust_Bill_Info(String ManNo, String Cust,String ItemNo) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "Cust_Bill_Info");
        // Property which holds input parameters

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);



        PropertyInfo parm_Cust = new PropertyInfo();
        parm_Cust.setName("Cust");
        parm_Cust.setValue(Cust);
        parm_Cust.setType(String.class);
        request.addProperty(parm_Cust);

        PropertyInfo parm_ItemNo = new PropertyInfo();
        parm_ItemNo.setName("ItemNo");
        parm_ItemNo.setValue(ItemNo);
        parm_ItemNo.setType(String.class);
        request.addProperty(parm_ItemNo);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call("http://tempuri.org/Cust_Bill_Info", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void GetCodeDefinition(String ManNo) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "Get_CodeDefinition");

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);






        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call("http://tempuri.org/Get_CodeDefinition", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void GetSalesManAtt(String ManNo ) {
        if (ComInfo.ComNo== Companies.Afrah.getValue()) {
            URL = "http://176.29.108.202:3738/CV.asmx";

        }
        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GetSalesManAtt");
        // Property which holds input parameters

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);



        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call("http://tempuri.org/GetSalesManAtt", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void GetManVacations(String ManNo ) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GetManVacations");
        // Property which holds input parameters

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);



        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call("http://tempuri.org/GetManVacations", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void TrnsferQtyFromMobile(String ManNo, String Max_Order,String TDate) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "TrnsferQty");
        // Property which holds input parameters

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);



        PropertyInfo parm_Max_Order = new PropertyInfo();
        parm_Max_Order.setName("Max_Order");
        parm_Max_Order.setValue(Max_Order);
        parm_Max_Order.setType(String.class);
        request.addProperty(parm_Max_Order);

        PropertyInfo parm_TDate = new PropertyInfo();
        parm_TDate.setName("TDate");
        parm_TDate.setValue(TDate);
        parm_TDate.setType(String.class);
        request.addProperty(parm_TDate);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call("http://tempuri.org/TrnsferQty", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void Search_PurchesOrders(String V_TYPE, String myear,String flg) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "Search_PurchesOrders");
        // Property which holds input parameters

        PropertyInfo parm_V_TYPE= new PropertyInfo();
        parm_V_TYPE.setName("V_TYPE");
        parm_V_TYPE.setValue(V_TYPE);
        parm_V_TYPE.setType(String.class);
        request.addProperty(parm_V_TYPE);



        PropertyInfo parm_myear = new PropertyInfo();
        parm_myear.setName("myear");
        parm_myear.setValue(myear);
        parm_myear.setType(String.class);
        request.addProperty(parm_myear);

        PropertyInfo parm_flg = new PropertyInfo();
        parm_flg.setName("flg");
        parm_flg.setValue(flg);
        parm_flg.setType(String.class);
        request.addProperty(parm_flg);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call("http://tempuri.org/Search_PurchesOrders", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void GatManSalesOrdeDtl(String orderno) {

        We_Result.Msg="";
        We_Result.ID =-1;

        // Create request
        SoapObject request = new SoapObject(NAMESPACE, "GatManSalesOrderD");
        // Property which holds input parameters
        PropertyInfo parm_Man_No = new PropertyInfo();
        parm_Man_No.setName("OrderNo");
        parm_Man_No.setValue(orderno);
        parm_Man_No.setType(String.class);


        // Add the property to request object
        request.addProperty(parm_Man_No);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invoke web service
            androidHttpTransport.call("http://tempuri.org/GatManSalesOrderD", envelope);

            SoapObject result  = (SoapObject) envelope.getResponse();


            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (Exception   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح" +en.getMessage() ;
            We_Result.ID = Long.parseLong("-404");


        }

    }


    public void GatManSalesOrdereturnDtl(String orderno) {

        We_Result.Msg="";
        We_Result.ID =-1;

        // Create request
        SoapObject request = new SoapObject(NAMESPACE, "GatManReturnRequestD");
        // Property which holds input parameters
        PropertyInfo parm_Man_No = new PropertyInfo();
        parm_Man_No.setName("OrderNo");
        parm_Man_No.setValue(orderno);
        parm_Man_No.setType(String.class);


        // Add the property to request object
        request.addProperty(parm_Man_No);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invoke web service
            androidHttpTransport.call("http://tempuri.org/GatManReturnRequestD", envelope);

            SoapObject result  = (SoapObject) envelope.getResponse();


            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (Exception   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح" +en.getMessage() ;
            We_Result.ID = Long.parseLong("-404");


        }

    }

    public void GatManStatesOrder(String FromDate,String ToDate,String UserNo) {

        We_Result.Msg="";
        We_Result.ID =-1;

        // Create request
        SoapObject request = new SoapObject(NAMESPACE, "GatManStatesOrder");
        // Property which holds input parameters
        PropertyInfo parm_Man_No = new PropertyInfo();
        parm_Man_No.setName("FromDate");
        parm_Man_No.setValue(FromDate);
        parm_Man_No.setType(String.class);


        PropertyInfo parm_FDate = new PropertyInfo();
        parm_FDate.setName("ToDate");
        parm_FDate.setValue(ToDate);
        parm_FDate.setType(String.class);




        PropertyInfo parm_ToDate = new PropertyInfo();
        parm_ToDate.setName("UserNo");
        parm_ToDate.setValue(UserNo);
        parm_ToDate.setType(String.class);



        // Add the property to request object
        request.addProperty(parm_Man_No);
        request.addProperty(parm_FDate);
        request.addProperty(parm_ToDate);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invoke web service
            androidHttpTransport.call("http://tempuri.org/GatManStatesOrder", envelope);

            SoapObject result  = (SoapObject) envelope.getResponse();


            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (Exception   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح" +en.getMessage() ;
            We_Result.ID = Long.parseLong("-404");


        }

    }

    public void GatManSalesOrdereturn(String FromDate,String ToDate,String UserNo) {

        We_Result.Msg="";
        We_Result.ID =-1;

        // Create request
        SoapObject request = new SoapObject(NAMESPACE, "GatManReturnRequest");
        // Property which holds input parameters
        PropertyInfo parm_Man_No = new PropertyInfo();
        parm_Man_No.setName("FromDate");
        parm_Man_No.setValue(FromDate);
        parm_Man_No.setType(String.class);


        PropertyInfo parm_FDate = new PropertyInfo();
        parm_FDate.setName("ToDate");
        parm_FDate.setValue(ToDate);
        parm_FDate.setType(String.class);




        PropertyInfo parm_ToDate = new PropertyInfo();
        parm_ToDate.setName("UserNo");
        parm_ToDate.setValue(UserNo);
        parm_ToDate.setType(String.class);



        // Add the property to request object
        request.addProperty(parm_Man_No);
        request.addProperty(parm_FDate);
        request.addProperty(parm_ToDate);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invoke web service
            androidHttpTransport.call("http://tempuri.org/GatManReturnRequest", envelope);

            SoapObject result  = (SoapObject) envelope.getResponse();


            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (Exception   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح" +en.getMessage() ;
            We_Result.ID = Long.parseLong("-404");


        }

    }


    public void GatManSalesOrde(String FromDate,String ToDate,String UserNo) {

        We_Result.Msg="";
        We_Result.ID =-1;

        // Create request
        SoapObject request = new SoapObject(NAMESPACE, "GatManSalesOrde");
        // Property which holds input parameters
        PropertyInfo parm_Man_No = new PropertyInfo();
        parm_Man_No.setName("FromDate");
        parm_Man_No.setValue(FromDate);
        parm_Man_No.setType(String.class);


        PropertyInfo parm_FDate = new PropertyInfo();
        parm_FDate.setName("ToDate");
        parm_FDate.setValue(ToDate);
        parm_FDate.setType(String.class);




        PropertyInfo parm_ToDate = new PropertyInfo();
        parm_ToDate.setName("UserNo");
        parm_ToDate.setValue(UserNo);
        parm_ToDate.setType(String.class);



        // Add the property to request object
        request.addProperty(parm_Man_No);
        request.addProperty(parm_FDate);
        request.addProperty(parm_ToDate);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invoke web service
            androidHttpTransport.call("http://tempuri.org/GatManSalesOrde", envelope);

            SoapObject result  = (SoapObject) envelope.getResponse();


            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (Exception   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح" +en.getMessage() ;
            We_Result.ID = Long.parseLong("-404");


        }

    }


    /*public void GatManSalesOrde(String FromDatei, String ToDatei,String UserNoi) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GatManSalesOrde");
        // Property which holds input parameters

        PropertyInfo FromDate = new PropertyInfo();
        FromDate.setName("FromDate");
        FromDate.setValue(FromDatei);
        FromDate.setType(String.class);
        request.addProperty(FromDate);



        PropertyInfo ToDate = new PropertyInfo();
        ToDate.setName("ToDate");
        ToDate.setValue(ToDatei);
        ToDate.setType(String.class);
        request.addProperty(ToDate);

        PropertyInfo UserNo = new PropertyInfo();
        UserNo.setName("UserNo");
        UserNo.setValue(UserNoi);
        UserNo.setType(String.class);
        request.addProperty(UserNo);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call("http://tempuri.org/GatManSalesOrde", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (Exception e) {
            We_Result.Msg =  e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
System.out.print("thiserr :"+e.getMessage());
        }


    }
*/
    public void GetItemQty(String ManNo, String ItemNo) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GetItemQty");
        // Property which holds input parameters

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);



        PropertyInfo parm_ItemNo = new PropertyInfo();
        parm_ItemNo.setName("ItemNo");
        parm_ItemNo.setValue(ItemNo);
        parm_ItemNo.setType(String.class);
        request.addProperty(parm_ItemNo);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call("http://tempuri.org/GetItemQty", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }

    public void TrnsferQtyFromMobileBatch(String ItemNo, String StoreNo ) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "TrnsferQtyBatch");
        // Property which holds input parameters

        PropertyInfo parm_ItemNo = new PropertyInfo();
        parm_ItemNo.setName("ItemNo");
        parm_ItemNo.setValue(ItemNo);
        parm_ItemNo.setType(String.class);
        request.addProperty(parm_ItemNo);



        PropertyInfo parm_StoreNo = new PropertyInfo();
        parm_StoreNo.setName("StoreNo");
        parm_StoreNo.setValue(StoreNo);
        parm_StoreNo.setType(String.class);
        request.addProperty(parm_StoreNo);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,1000);

        try {
            androidHttpTransport.call("http://tempuri.org/TrnsferQtyBatch", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }
        //Return resTxt to calling object

    }
    public void Get_ItemPatchs() {
        We_Result.Msg="";
        We_Result.ID =-1;
        String resTxt = null;

        try {
            SoapObject request = new SoapObject(NAMESPACE, "GatItemPatchs");
            // Property which holds input parameters

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet=true;
            // Set output SOAP object
            envelope.setOutputSoapObject(request);
            // Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            Object  response =null;

            androidHttpTransport.call("http://tempuri.org/GatItemPatchs", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        }

  /* } catch (NullPointerException   en){
        We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
        We_Result.ID = Long.parseLong("-404");
        en.printStackTrace();
        resTxt = "Error occured";

      } catch (EOFException eof ){
        We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
        We_Result.ID = Long.parseLong("-404");
        eof.printStackTrace();
        resTxt = "Error occured";
    }*/
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"        ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
            resTxt = "Error occured";
        }
        //Return resTxt to calling object

    }
    public long Save_Sal_Invoice(String Json,String Json1) {
        We_Result.Msg =  "";
        We_Result.ID = -1;

        SoapObject request = new SoapObject(NAMESPACE, "Insert_Sale_Invoice");
        PropertyInfo sayHelloPI = new PropertyInfo();
        sayHelloPI.setName("JsonStr");
        sayHelloPI.setValue(Json);
        sayHelloPI.setType(String.class);
        request.addProperty(sayHelloPI);
        PropertyInfo sayHelloPI1 = new PropertyInfo();
        sayHelloPI1.setName("JsonStr1");
        sayHelloPI1.setValue(Json1);
        sayHelloPI1.setType(String.class);
        request.addProperty(sayHelloPI1);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,40000);
        Object  response =null;
        try {
            androidHttpTransport.call( "http://tempuri.org/Insert_Sale_Invoice", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (Exception e) {

            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
        }
        return   We_Result.ID;

    }
    public long Save_store_pepr(String Json) {
        We_Result.Msg =  "";
        We_Result.ID = -1;

        SoapObject request = new SoapObject(NAMESPACE, "Save_store_pepr");
        PropertyInfo sayHelloPI = new PropertyInfo();
        sayHelloPI.setName("JsonStr");
        sayHelloPI.setValue(Json);
        sayHelloPI.setType(String.class);
        request.addProperty(sayHelloPI);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,40000);
        Object  response =null;
        try {
            androidHttpTransport.call( "http://tempuri.org/Save_store_pepr", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (Exception e) {

            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
        }
        return   We_Result.ID;

    }

    public long  Save_Ret_Sal_Invoice(String Json) {
        We_Result.Msg = "";
        We_Result.ID = -1;
        SoapObject request = new SoapObject(NAMESPACE, "Insert_Ret_Sale_Invoice");
        PropertyInfo sayHelloPI = new PropertyInfo();
        sayHelloPI.setName("JsonStr");
        sayHelloPI.setValue(Json);
        sayHelloPI.setType(String.class);
        request.addProperty(sayHelloPI);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object response = null;
        try {

            androidHttpTransport.call("http://tempuri.org/Insert_Ret_Sale_Invoice", envelope);

            SoapObject result = (SoapObject) envelope.getResponse();


            We_Result.Msg = result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());


        } catch (Exception e) {
            We_Result.Msg = "عملية الاتصال بالسيرفر لم تتم بنجاح";//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();


        }
        return   We_Result.ID;
    }

    public void SaveCustLocation(String Json) {
        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "SaveCustLocation");
        PropertyInfo sayHelloPI = new PropertyInfo();
        sayHelloPI.setName("JsonStr");
        sayHelloPI.setValue(Json);
        sayHelloPI.setType(String.class);
        request.addProperty(sayHelloPI);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {
            androidHttpTransport.call( "http://tempuri.org/SaveCustLocation", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            // Assign it to resTxt variable static variable
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");

            e.printStackTrace();


        }
        //Return resTxt to calling object


    }

    public long  SaveManVisits(String Json ) {
        try {
            We_Result.Msg="";
            We_Result.ID =-1;
            SoapObject request = new SoapObject(NAMESPACE, "SaveManVisits");
            PropertyInfo StrJson = new PropertyInfo();
            StrJson.setName("JsonStr");
            StrJson.setValue(Json);
            StrJson.setType(String.class);
            request.addProperty(StrJson);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet=true;

            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            Object  response =null;


            androidHttpTransport.call( "http://tempuri.org/SaveManVisits", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح";
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح";
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"   ;// + String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }
        return  We_Result.ID;

    }
    public long SaveManLoactios(String Json) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "SaveManLoactions");
        PropertyInfo sayHelloPI = new PropertyInfo();
        sayHelloPI.setName("JsonStr");
        sayHelloPI.setValue(Json);
        sayHelloPI.setType(String.class);
        request.addProperty(sayHelloPI);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call( "http://tempuri.org/SaveManLoactions", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            // Assign it to resTxt variable static variable
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            //Print error
            e.printStackTrace();
            //Assign error message to resTxt

        }
        return   We_Result.ID;

    }
    public void GetcompanyInfo() {


         //  URL = "http://176.29.108.202:3738/CV.asmx";


        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GetCopmanyInfo");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call("http://tempuri.org/GetCopmanyInfo", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }

    }
    public void GetSalesOrderStutes(String User_ID  , String ManLevel ) {
        We_Result.Msg="";
        We_Result.ID=-1;
        SoapObject request = new SoapObject(NAMESPACE, "Get_SalesOrderStutes");

        PropertyInfo parm_User_ID = new PropertyInfo();
        parm_User_ID.setName("User_ID");
        parm_User_ID.setValue(User_ID);
        parm_User_ID.setType(String.class);
        request.addProperty(parm_User_ID);



        PropertyInfo parm_ManLevel = new PropertyInfo();
        parm_ManLevel.setName("ManLevel");
        parm_ManLevel.setValue(ManLevel);
        parm_ManLevel.setType(String.class);
        request.addProperty(parm_ManLevel);



        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,1000);
        Object  response =null;
        try {
            androidHttpTransport.call("http://tempuri.org/Get_SalesOrderStutes", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void GetDelvary() {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GetDelvary");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call("http://tempuri.org/GetDelvary", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }

    }
    public void GetSerial() {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GetSerial");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call("http://tempuri.org/GetSerial", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }

    }
    public void GetCASHCUST(String ManNo) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GetCASHCUST");

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/GetCASHCUST", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }


    public void GetCustCard(String ManNo) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GetCustCard");

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/GetCustCard", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }

    public void Get_Items_Categs(String ManNo) {

        We_Result.Msg="";
        We_Result.ID =-1;


        SoapObject request = new SoapObject(NAMESPACE, "GetItems_Categ");
        PropertyInfo ParmUsere = new PropertyInfo();
        ParmUsere.setName("ManNo");
        ParmUsere.setValue(ManNo);
        ParmUsere.setType(String.class);
        request.addProperty(ParmUsere);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {
            androidHttpTransport.call("http://tempuri.org/GetItems_Categ", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");


        }


    }
    public void Get_Cust_Categs(String ManNo) {

        We_Result.Msg="";
        We_Result.ID =-1;


        SoapObject request = new SoapObject(NAMESPACE, "Get_Cust_Categ");
        PropertyInfo ParmUsere = new PropertyInfo();
        ParmUsere.setName("ManNo");
        ParmUsere.setValue(ManNo);
        ParmUsere.setType(String.class);
        request.addProperty(ParmUsere);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {
            androidHttpTransport.call("http://tempuri.org/Get_Cust_Categ", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");


        }


    }
    public void SaveDoctorReport(String Json, String SummeryVisit/*,String Lockups*/) {
        We_Result.Msg =  " "         ;
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "SaveDoctorReport");
        PropertyInfo ParmJson = new PropertyInfo();
        ParmJson.setName("JsonStr");
        ParmJson.setValue(Json);
        ParmJson.setType(String.class);
        request.addProperty(ParmJson);

        PropertyInfo ParmSummeryVisit = new PropertyInfo();
        ParmSummeryVisit.setName("SummeryVisit");
        ParmSummeryVisit.setValue(SummeryVisit);
        ParmSummeryVisit.setType(String.class);
        request.addProperty(ParmSummeryVisit);



      /*  PropertyInfo Lockupss = new PropertyInfo();
        Lockupss.setName("Lockups");
        Lockupss.setValue(Lockups);
        Lockupss.setType(String.class);
        request.addProperty(ParmJson);
*/
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,40000);
        Object  response =null;
        try {
            androidHttpTransport.call( "http://tempuri.org/SaveDoctorReport", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }
        //Return resTxt to calling object

    }

    public void GetAreas() {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GetAreas");




        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call("http://tempuri.org/GetAreas", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }


    }
    public void ShareUsedCodeNew(String Json) {
        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "UpdateManCodeDetails");
        PropertyInfo sayHelloPI = new PropertyInfo();
        sayHelloPI.setName("JsonStr");
        sayHelloPI.setValue(Json);
        sayHelloPI.setType(String.class);
        request.addProperty(sayHelloPI);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call( "http://tempuri.org/UpdateManCodeDetails", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();

            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");

        }


    }
    public void ShareUsedCode(String Json) {
        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "SaveUsedCodes");
        PropertyInfo sayHelloPI = new PropertyInfo();
        sayHelloPI.setName("JsonStr");
        sayHelloPI.setValue(Json);
        sayHelloPI.setType(String.class);
        request.addProperty(sayHelloPI);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call( "http://tempuri.org/SaveUsedCodes", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();

            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");

        }


    }
    public void SaveCashCust(String Json) {
        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "SaveCashCust");
        PropertyInfo sayHelloPI = new PropertyInfo();
        sayHelloPI.setName("JsonStr");
        sayHelloPI.setValue(Json);
        sayHelloPI.setType(String.class);
        request.addProperty(sayHelloPI);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call( "http://tempuri.org/SaveCashCust", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();

            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;
            We_Result.ID = Long.parseLong("-404");

        }

    }
    public void CallManLocationReport(String Man_No, String SuperVisor,String  FDate,String TDate,String Flag ) {

        We_Result.Msg="";
        We_Result.ID =-1;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, "GetManLocations");
        // Property which holds input parameters
        PropertyInfo parm_Man_No = new PropertyInfo();
        parm_Man_No.setName("Man_No");
        parm_Man_No.setValue(Man_No);
        parm_Man_No.setType(String.class);

        PropertyInfo parm_SuperVisor = new PropertyInfo();
        parm_SuperVisor.setName("SuperVisor");
        parm_SuperVisor.setValue(SuperVisor);
        parm_SuperVisor.setType(String.class);


        PropertyInfo parm_FDate = new PropertyInfo();
        parm_FDate.setName("FDate");
        parm_FDate.setValue(FDate);
        parm_FDate.setType(String.class);




        PropertyInfo parm_ToDate = new PropertyInfo();
        parm_ToDate.setName("TDate");
        parm_ToDate.setValue(TDate);
        parm_ToDate.setType(String.class);


        PropertyInfo parm_Flag = new PropertyInfo();
        parm_Flag.setName("Flag");
        parm_Flag.setValue(Flag);
        parm_Flag.setType(String.class);


        // Add the property to request object
        request.addProperty(parm_Man_No);
        request.addProperty(parm_SuperVisor);
        request.addProperty(parm_FDate);
        request.addProperty(parm_ToDate);
        request.addProperty(parm_Flag);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invoke web service
            androidHttpTransport.call("http://tempuri.org/GetManLocations", envelope);

            SoapObject result  = (SoapObject) envelope.getResponse();


            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }

    }
    public void UpdataCardMan(String Man_No,String Name,String phone,String Email,String Image) {

        We_Result.Msg="";
        We_Result.ID =-1;

        SoapObject request = new SoapObject(NAMESPACE, "UpdataCardMan");

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("manNo");
        parm_ManNo.setValue(Man_No);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);

        PropertyInfo parm_Name = new PropertyInfo();
        parm_Name.setName("Name");
        parm_Name.setValue(Name);
        parm_Name.setType(String.class);
        request.addProperty(parm_Name);


        PropertyInfo parm_phone = new PropertyInfo();
        parm_phone.setName("Mobile");
        parm_phone.setValue(phone);
        parm_phone.setType(String.class);
        request.addProperty(parm_phone);

        PropertyInfo parm_email = new PropertyInfo();
        parm_email.setName("Email");
        parm_email.setValue(Email);
        parm_email.setType(String.class);
        request.addProperty(parm_email);


        PropertyInfo parm_img = new PropertyInfo();
        parm_img.setName("Image");
        parm_img.setValue(Image);
        parm_img.setType(String.class);
        request.addProperty(parm_img);



        // Add the property to request object







        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/UpdataCardMan",envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }
        //Return resTxt to calling object


    }
    public void GetManVisitDtl (String Man_No, String Tr_Date ) {

        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, "GetManVisitDtl");

        PropertyInfo parm_Man_No = new PropertyInfo();
        parm_Man_No.setName("Man_No");
        parm_Man_No.setValue(Man_No);
        parm_Man_No.setType(String.class);

        PropertyInfo parm_Tr_Date = new PropertyInfo();
        parm_Tr_Date.setName("Tr_Date");
        parm_Tr_Date.setValue(Tr_Date);
        parm_Tr_Date.setType(String.class);





        request.addProperty(parm_Man_No);
        request.addProperty(parm_Tr_Date);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {
            // Invoke web service
            androidHttpTransport.call("http://tempuri.org/GetManVisitDtl", envelope);
            // Get the response
            SoapObject result  = (SoapObject) envelope.getResponse();


            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();
            resTxt = "Error occured";

        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();
            resTxt = "Error occured";
        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
            resTxt = "Error occured";
        }
        //Return resTxt to calling object

    }
    public void GetCheckQty(String ManNo, String Qty , String itemNo ) {

        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, "getCheckQTY");

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);

        PropertyInfo parm_Qty = new PropertyInfo();
        parm_Qty.setName("Qty");
        parm_Qty.setValue(Qty);
        parm_Qty.setType(String.class);

        PropertyInfo parm_itemNo= new PropertyInfo();
        parm_itemNo.setName("itemNo");
        parm_itemNo.setValue(itemNo);
        parm_itemNo.setType(String.class);



        request.addProperty(parm_ManNo);
        request.addProperty(parm_Qty);
        request.addProperty(parm_itemNo);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {
            // Invoke web service
            androidHttpTransport.call("http://tempuri.org/getCheckQTY", envelope);
            // Get the response
            SoapObject result  = (SoapObject) envelope.getResponse();


            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();
            resTxt = "Error occured";

        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();
            resTxt = "Error occured";
        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
            resTxt = "Error occured";
        }
        //Return resTxt to calling object

    }

    public void CallAllManLocation (String Man_No, String SuperVisor,String  FDate,String FTime,String ToTime,String Flag ) {

        String resTxt = null;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, "GetAllManLocations");
        // Property which holds input parameters
        PropertyInfo parm_Man_No = new PropertyInfo();
        parm_Man_No.setName("Man_No");
        parm_Man_No.setValue(Man_No);
        parm_Man_No.setType(String.class);

        PropertyInfo parm_SuperVisor = new PropertyInfo();
        parm_SuperVisor.setName("SuperVisor");
        parm_SuperVisor.setValue(SuperVisor);
        parm_SuperVisor.setType(String.class);


        PropertyInfo parm_FDate = new PropertyInfo();
        parm_FDate.setName("FDate");
        parm_FDate.setValue(FDate);
        parm_FDate.setType(String.class);




        PropertyInfo parm_FTime = new PropertyInfo();
        parm_FTime.setName("FTime");
        parm_FTime.setValue(FTime);
        parm_FTime.setType(String.class);

        PropertyInfo parm_TTime = new PropertyInfo();
        parm_TTime.setName("ToTime");
        parm_TTime.setValue(ToTime);
        parm_TTime.setType(String.class);


        PropertyInfo parm_Flag = new PropertyInfo();
        parm_Flag.setName("Flag");
        parm_Flag.setValue(Flag);
        parm_Flag.setType(String.class);


        // Add the property to request object
        request.addProperty(parm_Man_No);
        request.addProperty(parm_SuperVisor);
        request.addProperty(parm_FDate);
        request.addProperty(parm_FTime);
        request.addProperty(parm_TTime);
        request.addProperty(parm_Flag);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {
            // Invoke web service
            androidHttpTransport.call("http://tempuri.org/GetAllManLocations", envelope);
            // Get the response
            SoapObject result  = (SoapObject) envelope.getResponse();


            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();
            resTxt = "Error occured";

        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();
            resTxt = "Error occured";
        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
            resTxt = "Error occured";
        }
        //Return resTxt to calling object

    }

    public void SendRequestPermission(String ManNo,String CustNo, String OrderNo, String Desc  ,String Type, String Amt) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "SendRequestPermission");


        PropertyInfo Info_ManNo = new PropertyInfo();
        Info_ManNo.setName("ManNo");
        Info_ManNo.setValue(ManNo);
        Info_ManNo.setType(String.class);
        request.addProperty(Info_ManNo);


        PropertyInfo Info_CustNo = new PropertyInfo();
        Info_CustNo.setName("CustNo");
        Info_CustNo.setValue(CustNo);
        Info_CustNo.setType(String.class);
        request.addProperty(Info_CustNo);



        PropertyInfo Info_OrderNo = new PropertyInfo();
        Info_OrderNo.setName("OrderNo");
        Info_OrderNo.setValue(OrderNo);
        Info_OrderNo.setType(String.class);
        request.addProperty(Info_OrderNo);


        PropertyInfo Info_Desc = new PropertyInfo();
        Info_Desc.setName("Desc");
        Info_Desc.setValue(Desc);
        Info_Desc.setType(String.class);
        request.addProperty(Info_Desc);


        PropertyInfo Info_Type = new PropertyInfo();
        Info_Type.setName("Type");
        Info_Type.setValue(Type);
        Info_Type.setType(String.class);
        request.addProperty(Info_Type);


        PropertyInfo Info_Amt = new PropertyInfo();
        Info_Amt.setName("Amt");
        Info_Amt.setValue(Amt);
        Info_Amt.setType(String.class);
        request.addProperty(Info_Amt);



        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,40000);

        try {

            androidHttpTransport.call( "http://tempuri.org/SendRequestPermission", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();



            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (Exception e) {

            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");


        }


    }
    public void Get_Request_Permission (String ManNo,String CustNo, String OrderNo, String Type ) {

        We_Result.Msg="";
        We_Result.ID =-1;

        SoapObject request = new SoapObject(NAMESPACE, "GetRequestPermission");


        PropertyInfo Info_ManNo = new PropertyInfo();
        Info_ManNo.setName("ManNo");
        Info_ManNo.setValue(ManNo);
        Info_ManNo.setType(String.class);
        request.addProperty(Info_ManNo);


        PropertyInfo Info_CustNo = new PropertyInfo();
        Info_CustNo.setName("CustNo");
        Info_CustNo.setValue(CustNo);
        Info_CustNo.setType(String.class);
        request.addProperty(Info_CustNo);



        PropertyInfo Info_OrderNo = new PropertyInfo();
        Info_OrderNo.setName("OrderNo");
        Info_OrderNo.setValue(OrderNo);
        Info_OrderNo.setType(String.class);
        request.addProperty(Info_OrderNo);




        PropertyInfo Info_Type = new PropertyInfo();
        Info_Type.setName("Type");
        Info_Type.setValue(Type);
        Info_Type.setType(String.class);
        request.addProperty(Info_Type);




        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call("http://tempuri.org/GetRequestPermission", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
        }


    }
    public void GetItems_service( String ManNo ) {


        SoapObject request = new SoapObject(NAMESPACE, "GetItems_service");

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/GetItems_service", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();
            We_Result.Msg="";
            We_Result.ID =-1;

        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();
            We_Result.Msg="";
            We_Result.ID =-1;
        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;//+ String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
            We_Result.Msg="";
            We_Result.ID =-1;
        }

    }
    public void Get_ServerDateTime() {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GetServerDateTime");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {
            androidHttpTransport.call("http://tempuri.org/GetServerDateTime", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"   ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public String Tafkeet(String Amt) {

        We_Result.Msg = "";
        We_Result.ID = -1;
        SoapObject request = new SoapObject(NAMESPACE, "Get_Tafkeet");

        PropertyInfo parm_Amt = new PropertyInfo();
        parm_Amt.setName("Amt");
        parm_Amt.setValue(Amt);
        parm_Amt.setType(String.class);
        request.addProperty(parm_Amt);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object response = null;
        try {

            androidHttpTransport.call("http://tempuri.org/Get_Tafkeet", envelope);
            SoapObject result = (SoapObject) envelope.getResponse();
            We_Result.Msg = result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (Exception e) {
            We_Result.Msg = "عملية الاتصال بالسيرفر لم تتم بنجاح";//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }
        return We_Result.Msg;
    }
    public void Get_Price_List(String ManNo) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GetPriceList");

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/GetPriceList", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void SaveCusImnages( String Acc,String ImageDesc ,String ImageBase64 ) {

        We_Result.Msg="";
        We_Result.ID =-1;
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, "Save_Cusf_Attachament");
        PropertyInfo Parm_Acc = new PropertyInfo();
        Parm_Acc.setName("Acc");
        Parm_Acc.setValue(Acc);
        Parm_Acc.setType(String.class);
        request.addProperty(Parm_Acc);


        PropertyInfo sayImageDesc = new PropertyInfo();
        sayImageDesc.setName("ImageDesc");
        sayImageDesc.setValue(ImageDesc);
        sayImageDesc.setType(String.class);
        request.addProperty(sayImageDesc);


        PropertyInfo parm_ImageBase64= new PropertyInfo();
        parm_ImageBase64.setName("ImageBase64");
        parm_ImageBase64.setValue(ImageBase64);
        parm_ImageBase64.setType(String.class);
        request.addProperty(parm_ImageBase64);




        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call( "http://tempuri.org/Save_Cusf_Attachament", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID =Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();
            resTxt = "Error occured";

        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();
            resTxt = "Error occured";
        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"   ;// + String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
            resTxt = "Error occured";
        }
        //Return resTxt to calling object

    }
    public String PostCustCardAndGetAcc(String CusName,String OrderNo, String Area , String CustType, String Mobile , String Acc
            , String Lat,String Lng,String GpsLocation  ,String UserID ,String COMPUTERNAME,

                                        String Sat,
                                                String Sun,
                                                String Mon,
                                                String Tues,
                                                String Wens,
                                                String Thurs,
                                                String Frid



    ) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "SaveCustCardAndGetAcc");
////////////////////////////////////////////////////
        PropertyInfo Para_Sat= new PropertyInfo();
        Para_Sat.setName("Sat");
        Para_Sat.setValue(Sat);
        Para_Sat.setType(String.class);
        request.addProperty(Para_Sat);

        PropertyInfo Para_Sun= new PropertyInfo();
        Para_Sun.setName("Sun");
        Para_Sun.setValue(Sun);
        Para_Sun.setType(String.class);
        request.addProperty(Para_Sun);

        PropertyInfo Para_Mon= new PropertyInfo();
        Para_Mon.setName("Mon");
        Para_Mon.setValue(Mon);
        Para_Mon.setType(String.class);
        request.addProperty(Para_Mon);

        PropertyInfo Para_Tues= new PropertyInfo();
        Para_Tues.setName("Tues");
        Para_Tues.setValue(Tues);
        Para_Tues.setType(String.class);
        request.addProperty(Para_Tues);

        PropertyInfo Para_Wens= new PropertyInfo();
        Para_Wens.setName("Wens");
        Para_Wens.setValue(Wens);
        Para_Wens.setType(String.class);
        request.addProperty(Para_Wens);

        PropertyInfo Para_Thurs= new PropertyInfo();
        Para_Thurs.setName("Thurs");
        Para_Thurs.setValue(Thurs);
        Para_Thurs.setType(String.class);
        request.addProperty(Para_Thurs);

        PropertyInfo Para_Frid= new PropertyInfo();
        Para_Frid.setName("Frid");
        Para_Frid.setValue(Frid);
        Para_Frid.setType(String.class);
        request.addProperty(Para_Frid);


////////////////////////////////////////////////////
        PropertyInfo Para_CusName = new PropertyInfo();
        Para_CusName.setName("CusName");
        Para_CusName.setValue(CusName);
        Para_CusName.setType(String.class);
        request.addProperty(Para_CusName);


        PropertyInfo Param_OrderNo = new PropertyInfo();
        Param_OrderNo.setName("OrderNo");
        Param_OrderNo.setValue(OrderNo);
        Param_OrderNo.setType(String.class);
        request.addProperty(Param_OrderNo);

        PropertyInfo Param_Area = new PropertyInfo();
        Param_Area.setName("Area");
        Param_Area.setValue(Area);
        Param_Area.setType(String.class);
        request.addProperty(Param_Area);


        PropertyInfo Parm_CustType = new PropertyInfo();
        Parm_CustType.setName("CustType");
        Parm_CustType.setValue(CustType);
        Parm_CustType.setType(String.class);
        request.addProperty(Parm_CustType);

        PropertyInfo Parm_Mobile = new PropertyInfo();
        Parm_Mobile.setName("Mobile");
        Parm_Mobile.setValue(Mobile);
        Parm_Mobile.setType(String.class);
        request.addProperty(Parm_Mobile);



        PropertyInfo Parm_Acc = new PropertyInfo();
        Parm_Acc.setName("Acc");
        Parm_Acc.setValue(Acc);
        Parm_Acc.setType(String.class);
        request.addProperty(Parm_Acc);

        PropertyInfo Parm_Lat = new PropertyInfo();
        Parm_Lat.setName("Lat");
        Parm_Lat.setValue(Lat);
        Parm_Lat.setType(String.class);
        request.addProperty(Parm_Lat);


        PropertyInfo Parm_Long = new PropertyInfo();
        Parm_Long.setName("Lng");
        Parm_Long.setValue(Lng);
        Parm_Long.setType(String.class);
        request.addProperty(Parm_Long);


        PropertyInfo Parm_GpsLocation = new PropertyInfo();
        Parm_GpsLocation.setName("GpsLocation");
        Parm_GpsLocation.setValue(GpsLocation);
        Parm_GpsLocation.setType(String.class);
        request.addProperty(Parm_GpsLocation);





        PropertyInfo parm_UserID= new PropertyInfo();
        parm_UserID.setName("UserID");
        parm_UserID.setValue(UserID);
        parm_UserID.setType(String.class);
        request.addProperty(parm_UserID);


        PropertyInfo parm_COMPUTERNAME= new PropertyInfo();
        parm_COMPUTERNAME.setName("COMPUTERNAME");
        parm_COMPUTERNAME.setValue(COMPUTERNAME);
        parm_COMPUTERNAME.setType(String.class);
        request.addProperty(parm_COMPUTERNAME);



        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/SaveCustCardAndGetAcc", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


        return We_Result.ID+"";  }

    public String PostCustCardAndGetAccBefore(String CusName,String OrderNo, String Area , String CustType, String Mobile , String Acc
            , String Lat,String Lng,String GpsLocation  ,String UserID ,String COMPUTERNAME,

                                        String Sat,
                                        String Sun,
                                        String Mon,
                                        String Tues,
                                        String Wens,
                                        String Thurs,
                                        String Frid,
                                              String desc



    ) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "SaveCustCardAndGetAcc_Before");
////////////////////////////////////////////////////

        PropertyInfo Para_desc = new PropertyInfo();
        Para_desc.setName("desc ");
        Para_desc.setValue(desc );
        Para_desc.setType(String.class);
        request.addProperty(Para_desc);



        PropertyInfo Para_Sat= new PropertyInfo();
        Para_Sat.setName("Sat");
        Para_Sat.setValue(Sat);
        Para_Sat.setType(String.class);
        request.addProperty(Para_Sat);

        PropertyInfo Para_Sun= new PropertyInfo();
        Para_Sun.setName("Sun");
        Para_Sun.setValue(Sun);
        Para_Sun.setType(String.class);
        request.addProperty(Para_Sun);

        PropertyInfo Para_Mon= new PropertyInfo();
        Para_Mon.setName("Mon");
        Para_Mon.setValue(Mon);
        Para_Mon.setType(String.class);
        request.addProperty(Para_Mon);

        PropertyInfo Para_Tues= new PropertyInfo();
        Para_Tues.setName("Tues");
        Para_Tues.setValue(Tues);
        Para_Tues.setType(String.class);
        request.addProperty(Para_Tues);

        PropertyInfo Para_Wens= new PropertyInfo();
        Para_Wens.setName("Wens");
        Para_Wens.setValue(Wens);
        Para_Wens.setType(String.class);
        request.addProperty(Para_Wens);

        PropertyInfo Para_Thurs= new PropertyInfo();
        Para_Thurs.setName("Thurs");
        Para_Thurs.setValue(Thurs);
        Para_Thurs.setType(String.class);
        request.addProperty(Para_Thurs);

        PropertyInfo Para_Frid= new PropertyInfo();
        Para_Frid.setName("Frid");
        Para_Frid.setValue(Frid);
        Para_Frid.setType(String.class);
        request.addProperty(Para_Frid);


////////////////////////////////////////////////////
        PropertyInfo Para_CusName = new PropertyInfo();
        Para_CusName.setName("CusName");
        Para_CusName.setValue(CusName);
        Para_CusName.setType(String.class);
        request.addProperty(Para_CusName);


        PropertyInfo Param_OrderNo = new PropertyInfo();
        Param_OrderNo.setName("OrderNo");
        Param_OrderNo.setValue(OrderNo);
        Param_OrderNo.setType(String.class);
        request.addProperty(Param_OrderNo);

        PropertyInfo Param_Area = new PropertyInfo();
        Param_Area.setName("Area");
        Param_Area.setValue(Area);
        Param_Area.setType(String.class);
        request.addProperty(Param_Area);


        PropertyInfo Parm_CustType = new PropertyInfo();
        Parm_CustType.setName("CustType");
        Parm_CustType.setValue(CustType);
        Parm_CustType.setType(String.class);
        request.addProperty(Parm_CustType);

        PropertyInfo Parm_Mobile = new PropertyInfo();
        Parm_Mobile.setName("Mobile");
        Parm_Mobile.setValue(Mobile);
        Parm_Mobile.setType(String.class);
        request.addProperty(Parm_Mobile);



        PropertyInfo Parm_Acc = new PropertyInfo();
        Parm_Acc.setName("Acc");
        Parm_Acc.setValue(Acc);
        Parm_Acc.setType(String.class);
        request.addProperty(Parm_Acc);

        PropertyInfo Parm_Lat = new PropertyInfo();
        Parm_Lat.setName("Lat");
        Parm_Lat.setValue(Lat);
        Parm_Lat.setType(String.class);
        request.addProperty(Parm_Lat);


        PropertyInfo Parm_Long = new PropertyInfo();
        Parm_Long.setName("Lng");
        Parm_Long.setValue(Lng);
        Parm_Long.setType(String.class);
        request.addProperty(Parm_Long);


        PropertyInfo Parm_GpsLocation = new PropertyInfo();
        Parm_GpsLocation.setName("GpsLocation");
        Parm_GpsLocation.setValue(GpsLocation);
        Parm_GpsLocation.setType(String.class);
        request.addProperty(Parm_GpsLocation);





        PropertyInfo parm_UserID= new PropertyInfo();
        parm_UserID.setName("UserID");
        parm_UserID.setValue(UserID);
        parm_UserID.setType(String.class);
        request.addProperty(parm_UserID);


        PropertyInfo parm_COMPUTERNAME= new PropertyInfo();
        parm_COMPUTERNAME.setName("COMPUTERNAME");
        parm_COMPUTERNAME.setValue(COMPUTERNAME);
        parm_COMPUTERNAME.setType(String.class);
        request.addProperty(parm_COMPUTERNAME);



        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/SaveCustCardAndGetAcc_Before", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


        return We_Result.ID+"";  }
    public void GET_CustReportInformationCust(String AccNo) {

        We_Result.Msg = "";
        We_Result.ID = -1;

        SoapObject request = new SoapObject(NAMESPACE, "GET_CustReportInformationCust");

        PropertyInfo parm_AccNo = new PropertyInfo();

        parm_AccNo.setName("AccNo");

        parm_AccNo.setValue(AccNo);
        parm_AccNo.setType(String.class);


        // Add the property to request object
        request.addProperty(parm_AccNo);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/GET_CustReportInformationCust", envelope);
            SoapObject result = (SoapObject) envelope.getResponse();
            We_Result.Msg = result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());


        } catch (NullPointerException en) {
            We_Result.Msg = "عملية الاتصال بالسيرفر لم تتم بنجاح";
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof) {
            We_Result.Msg = "عملية الاتصال بالسيرفر لم تتم بنجاح";
            We_Result.ID = Long.parseLong("-404");

        } catch (Exception e) {
            We_Result.Msg = "عملية الاتصال بالسيرفر لم تتم بنجاح";
            We_Result.ID = Long.parseLong("-404");

        }
        //Return resTxt to calling obje

    }

    public void GetAccReportSummary(String AccNo) {

        We_Result.Msg="";
        We_Result.ID =-1;

        SoapObject request = new SoapObject(NAMESPACE, "GetAccReportSummary");

        PropertyInfo parm_AccNo = new PropertyInfo();

        parm_AccNo.setName("AccNo");

        parm_AccNo.setValue(AccNo);
        parm_AccNo.setType(String.class);



        // Add the property to request object
        request.addProperty(parm_AccNo);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/GetAccReportSummary", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }
        //Return resTxt to calling object

    }

    public void GET_CustReportCatch(String AccNo) {

        We_Result.Msg="";
        We_Result.ID =-1;

        SoapObject request = new SoapObject(NAMESPACE, "GET_CustReportCatch");

        PropertyInfo parm_AccNo = new PropertyInfo();

        parm_AccNo.setName("AccNo");

        parm_AccNo.setValue(AccNo);
        parm_AccNo.setType(String.class);



        // Add the property to request object
        request.addProperty(parm_AccNo);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/GET_CustReportCatch", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }
        //Return resTxt to calling object

    }
    public void GET_CustReportBill(String AccNo) {

        We_Result.Msg="";
        We_Result.ID =-1;

        SoapObject request = new SoapObject(NAMESPACE, "GET_CustReportBill");

        PropertyInfo parm_AccNo = new PropertyInfo();

        parm_AccNo.setName("AccNo");

        parm_AccNo.setValue(AccNo);
        parm_AccNo.setType(String.class);



        // Add the property to request object
        request.addProperty(parm_AccNo);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/GET_CustReportBill", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }
        //Return resTxt to calling object

    }
    public void GET_CustReportSalesPayoff(String AccNo) {

        We_Result.Msg="";
        We_Result.ID =-1;

        SoapObject request = new SoapObject(NAMESPACE, "GET_CustReportSalesPayoff");

        PropertyInfo parm_AccNo = new PropertyInfo();

        parm_AccNo.setName("AccNo");

        parm_AccNo.setValue(AccNo);
        parm_AccNo.setType(String.class);



        // Add the property to request object
        request.addProperty(parm_AccNo);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/GET_CustReportSalesPayoff", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }
        //Return resTxt to calling object

    }
    public void GET_CustReportSellingRequest(String AccNo) {

        We_Result.Msg="";
        We_Result.ID =-1;

        SoapObject request = new SoapObject(NAMESPACE, "GET_CustReportSellingRequest");

        PropertyInfo parm_AccNo = new PropertyInfo();

        parm_AccNo.setName("AccNo");

        parm_AccNo.setValue(AccNo);
        parm_AccNo.setType(String.class);



        // Add the property to request object
        request.addProperty(parm_AccNo);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/GET_CustReportSellingRequest", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }
        //Return resTxt to calling object

    }
    public void GET_CustReportCollections(String AccNo) {

        We_Result.Msg="";
        We_Result.ID =-1;

        SoapObject request = new SoapObject(NAMESPACE, "GatTableCollections_Cust");

        PropertyInfo parm_AccNo = new PropertyInfo();
        parm_AccNo.setName("AccNo");
        parm_AccNo.setValue(AccNo);
        parm_AccNo.setType(String.class);



        // Add the property to request object
        request.addProperty(parm_AccNo);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/GatTableCollections_Cust", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }
        //Return resTxt to calling object

    }
    public void GET_CustReportManVisit(String AccNo) {

        We_Result.Msg="";
        We_Result.ID =-1;

        SoapObject request = new SoapObject(NAMESPACE, "GET_CustReportManVisit");

        PropertyInfo parm_AccNo = new PropertyInfo();

        parm_AccNo.setName("AccNo");

        parm_AccNo.setValue(AccNo);
        parm_AccNo.setType(String.class);



        // Add the property to request object
        request.addProperty(parm_AccNo);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/GET_CustReportManVisit", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }
        //Return resTxt to calling object

    }
    public void GetDiscountDept(String ManNo) {

        We_Result.Msg="";
        We_Result.ID =-1;

        SoapObject request = new SoapObject(NAMESPACE, "GetDeptDiscount");

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);



        // Add the property to request object
        request.addProperty(parm_ManNo);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/GetDeptDiscount", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }
        //Return resTxt to calling object

    }
    public void GET_CustReportAccountStatement(String AccNo) {

        We_Result.Msg="";
        We_Result.ID =-1;

        SoapObject request = new SoapObject(NAMESPACE, "GET_CustReportAccountStatement");

        PropertyInfo parm_AccNo = new PropertyInfo();

        parm_AccNo.setName("AccNo");

        parm_AccNo.setValue(AccNo);
        parm_AccNo.setType(String.class);



        // Add the property to request object
        request.addProperty(parm_AccNo);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/GET_CustReportAccountStatement", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }
        //Return resTxt to calling object

    }

    public void getCountry() {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "getCountry");




        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call("http://tempuri.org/getCountry", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }


    }
    public void GET_Report_Home(String Cust_No, String Man_No, String Op, String Flg, String FromDate, String ToDate, String VisitOrderNo, String TransOrderNo,String Country) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GET_Report_Home");


        PropertyInfo parm_CustNo1 = new PropertyInfo();
        parm_CustNo1.setName("Cust_No");
        parm_CustNo1.setValue(Cust_No);
        parm_CustNo1.setType(String.class);
        request.addProperty(parm_CustNo1);




        PropertyInfo parm_ManNo= new PropertyInfo();
        parm_ManNo.setName("Man_No");
        parm_ManNo.setValue(Man_No);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);


        PropertyInfo parm_Op = new PropertyInfo();
        parm_Op.setName("Op");
        parm_Op.setValue(Op);
        parm_Op.setType(String.class);
        request.addProperty(parm_Op);


        PropertyInfo parm_Flg = new PropertyInfo();
        parm_Flg.setName("Flg");
        parm_Flg.setValue(Flg);
        parm_Flg.setType(String.class);
        request.addProperty(parm_Flg);


        PropertyInfo parm_FromDate1 = new PropertyInfo();
        parm_FromDate1.setName("FromDate");
        parm_FromDate1.setValue(FromDate);
        parm_FromDate1.setType(String.class);
        request.addProperty(parm_FromDate1);

        PropertyInfo parm_ToDate1 = new PropertyInfo();
        parm_ToDate1.setName("ToDate");
        parm_ToDate1.setValue(ToDate  );
        parm_ToDate1.setType(String.class);
        request.addProperty(parm_ToDate1);

        PropertyInfo parm_VisitOrderNo = new PropertyInfo();
        parm_VisitOrderNo.setName("VisitOrderNo");
        parm_VisitOrderNo.setValue(VisitOrderNo);
        parm_VisitOrderNo.setType(String.class);
        request.addProperty(parm_VisitOrderNo);

        PropertyInfo parm_TransOrderNo = new PropertyInfo();
        parm_TransOrderNo.setName("TransOrderNo");
        parm_TransOrderNo.setValue(TransOrderNo);
        parm_TransOrderNo.setType(String.class);
        request.addProperty(parm_TransOrderNo);

        PropertyInfo parm_Country = new PropertyInfo();
        parm_Country.setName("Country");
        parm_Country.setValue(Country);
        parm_Country.setType(String.class);
        request.addProperty(parm_Country);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call("http://tempuri.org/GET_Report_Home", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }


    }
    public void GET_Report_Home_RouteActivity(String Cust_No, String Man_No, String Op, String Flg, String FromDate, String ToDate, String VisitOrderNo, String TransOrderNo,String Country) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GET_Report_Home_RouteActivity");


        PropertyInfo parm_CustNo1 = new PropertyInfo();
        parm_CustNo1.setName("Cust_No");
        parm_CustNo1.setValue(Cust_No);
        parm_CustNo1.setType(String.class);
        request.addProperty(parm_CustNo1);




        PropertyInfo parm_ManNo= new PropertyInfo();
        parm_ManNo.setName("Man_No");
        parm_ManNo.setValue(Man_No);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);


        PropertyInfo parm_Op = new PropertyInfo();
        parm_Op.setName("Op");
        parm_Op.setValue(Op);
        parm_Op.setType(String.class);
        request.addProperty(parm_Op);


        PropertyInfo parm_Flg = new PropertyInfo();
        parm_Flg.setName("Flg");
        parm_Flg.setValue(Flg);
        parm_Flg.setType(String.class);
        request.addProperty(parm_Flg);


        PropertyInfo parm_FromDate1 = new PropertyInfo();
        parm_FromDate1.setName("FromDate");
        parm_FromDate1.setValue(FromDate);
        parm_FromDate1.setType(String.class);
        request.addProperty(parm_FromDate1);

        PropertyInfo parm_ToDate1 = new PropertyInfo();
        parm_ToDate1.setName("ToDate");
        parm_ToDate1.setValue(ToDate  );
        parm_ToDate1.setType(String.class);
        request.addProperty(parm_ToDate1);

        PropertyInfo parm_VisitOrderNo = new PropertyInfo();
        parm_VisitOrderNo.setName("VisitOrderNo");
        parm_VisitOrderNo.setValue(VisitOrderNo);
        parm_VisitOrderNo.setType(String.class);
        request.addProperty(parm_VisitOrderNo);

        PropertyInfo parm_TransOrderNo = new PropertyInfo();
        parm_TransOrderNo.setName("TransOrderNo");
        parm_TransOrderNo.setValue(TransOrderNo);
        parm_TransOrderNo.setType(String.class);
        request.addProperty(parm_TransOrderNo);

        PropertyInfo parm_Country = new PropertyInfo();
        parm_Country.setName("Country");
        parm_Country.setValue(Country);
        parm_Country.setType(String.class);
        request.addProperty(parm_Country);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call("http://tempuri.org/GET_Report_Home_RouteActivity", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }


    }
    public void GET_Report_Home_RouteScore(String Cust_No, String Man_No, String Op, String Flg, String FromDate, String ToDate, String VisitOrderNo, String TransOrderNo,String Country) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GET_Report_Home_RouteScore");


        PropertyInfo parm_CustNo1 = new PropertyInfo();
        parm_CustNo1.setName("Cust_No");
        parm_CustNo1.setValue(Cust_No);
        parm_CustNo1.setType(String.class);
        request.addProperty(parm_CustNo1);




        PropertyInfo parm_ManNo= new PropertyInfo();
        parm_ManNo.setName("Man_No");
        parm_ManNo.setValue(Man_No);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);


        PropertyInfo parm_Op = new PropertyInfo();
        parm_Op.setName("Op");
        parm_Op.setValue(Op);
        parm_Op.setType(String.class);
        request.addProperty(parm_Op);


        PropertyInfo parm_Flg = new PropertyInfo();
        parm_Flg.setName("Flg");
        parm_Flg.setValue(Flg);
        parm_Flg.setType(String.class);
        request.addProperty(parm_Flg);


        PropertyInfo parm_FromDate1 = new PropertyInfo();
        parm_FromDate1.setName("FromDate");
        parm_FromDate1.setValue(FromDate);
        parm_FromDate1.setType(String.class);
        request.addProperty(parm_FromDate1);

        PropertyInfo parm_ToDate1 = new PropertyInfo();
        parm_ToDate1.setName("ToDate");
        parm_ToDate1.setValue(ToDate  );
        parm_ToDate1.setType(String.class);
        request.addProperty(parm_ToDate1);

        PropertyInfo parm_VisitOrderNo = new PropertyInfo();
        parm_VisitOrderNo.setName("VisitOrderNo");
        parm_VisitOrderNo.setValue(VisitOrderNo);
        parm_VisitOrderNo.setType(String.class);
        request.addProperty(parm_VisitOrderNo);

        PropertyInfo parm_TransOrderNo = new PropertyInfo();
        parm_TransOrderNo.setName("TransOrderNo");
        parm_TransOrderNo.setValue(TransOrderNo);
        parm_TransOrderNo.setType(String.class);
        request.addProperty(parm_TransOrderNo);

        PropertyInfo parm_Country = new PropertyInfo();
        parm_Country.setName("Country");
        parm_Country.setValue(Country);
        parm_Country.setType(String.class);
        request.addProperty(parm_Country);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call("http://tempuri.org/GET_Report_Home_RouteScore", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }


    }
    public void GET_Report_Payments(String Cust_No, String Man_No, String Op, String Flg, String FromDate, String ToDate, String VisitOrderNo, String TransOrderNo,String Country) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GET_Report_Payments");


        PropertyInfo parm_CustNo1 = new PropertyInfo();
        parm_CustNo1.setName("Cust_No");
        parm_CustNo1.setValue(Cust_No);
        parm_CustNo1.setType(String.class);
        request.addProperty(parm_CustNo1);




        PropertyInfo parm_ManNo= new PropertyInfo();
        parm_ManNo.setName("Man_No");
        parm_ManNo.setValue(Man_No);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);


        PropertyInfo parm_Op = new PropertyInfo();
        parm_Op.setName("Op");
        parm_Op.setValue(Op);
        parm_Op.setType(String.class);
        request.addProperty(parm_Op);


        PropertyInfo parm_Flg = new PropertyInfo();
        parm_Flg.setName("Flg");
        parm_Flg.setValue(Flg);
        parm_Flg.setType(String.class);
        request.addProperty(parm_Flg);


        PropertyInfo parm_FromDate1 = new PropertyInfo();
        parm_FromDate1.setName("FromDate");
        parm_FromDate1.setValue(FromDate);
        parm_FromDate1.setType(String.class);
        request.addProperty(parm_FromDate1);

        PropertyInfo parm_ToDate1 = new PropertyInfo();
        parm_ToDate1.setName("ToDate");
        parm_ToDate1.setValue(ToDate  );
        parm_ToDate1.setType(String.class);
        request.addProperty(parm_ToDate1);

        PropertyInfo parm_VisitOrderNo = new PropertyInfo();
        parm_VisitOrderNo.setName("VisitOrderNo");
        parm_VisitOrderNo.setValue(VisitOrderNo);
        parm_VisitOrderNo.setType(String.class);
        request.addProperty(parm_VisitOrderNo);

        PropertyInfo parm_TransOrderNo = new PropertyInfo();
        parm_TransOrderNo.setName("TransOrderNo");
        parm_TransOrderNo.setValue(TransOrderNo);
        parm_TransOrderNo.setType(String.class);
        request.addProperty(parm_TransOrderNo);

        PropertyInfo parm_Country = new PropertyInfo();
        parm_Country.setName("Country");
        parm_Country.setValue(Country);
        parm_Country.setType(String.class);
        request.addProperty(parm_Country);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call("http://tempuri.org/GET_Report_Payments", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }


    }
    public void GET_Report_Home_Sales_Details(String Cust_No, String Man_No, String Op, String Flg, String FromDate, String ToDate, String VisitOrderNo, String TransOrderNo,String Country) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GET_Report_Home_Sales_Details");


        PropertyInfo parm_CustNo1 = new PropertyInfo();
        parm_CustNo1.setName("Cust_No");
        parm_CustNo1.setValue(Cust_No);
        parm_CustNo1.setType(String.class);
        request.addProperty(parm_CustNo1);




        PropertyInfo parm_ManNo= new PropertyInfo();
        parm_ManNo.setName("Man_No");
        parm_ManNo.setValue(Man_No);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);


        PropertyInfo parm_Op = new PropertyInfo();
        parm_Op.setName("Op");
        parm_Op.setValue(Op);
        parm_Op.setType(String.class);
        request.addProperty(parm_Op);


        PropertyInfo parm_Flg = new PropertyInfo();
        parm_Flg.setName("Flg");
        parm_Flg.setValue(Flg);
        parm_Flg.setType(String.class);
        request.addProperty(parm_Flg);


        PropertyInfo parm_FromDate1 = new PropertyInfo();
        parm_FromDate1.setName("FromDate");
        parm_FromDate1.setValue(FromDate);
        parm_FromDate1.setType(String.class);
        request.addProperty(parm_FromDate1);

        PropertyInfo parm_ToDate1 = new PropertyInfo();
        parm_ToDate1.setName("ToDate");
        parm_ToDate1.setValue(ToDate  );
        parm_ToDate1.setType(String.class);
        request.addProperty(parm_ToDate1);

        PropertyInfo parm_VisitOrderNo = new PropertyInfo();
        parm_VisitOrderNo.setName("VisitOrderNo");
        parm_VisitOrderNo.setValue(VisitOrderNo);
        parm_VisitOrderNo.setType(String.class);
        request.addProperty(parm_VisitOrderNo);

        PropertyInfo parm_TransOrderNo = new PropertyInfo();
        parm_TransOrderNo.setName("TransOrderNo");
        parm_TransOrderNo.setValue(TransOrderNo);
        parm_TransOrderNo.setType(String.class);
        request.addProperty(parm_TransOrderNo);

        PropertyInfo parm_Country = new PropertyInfo();
        parm_Country.setName("Country");
        parm_Country.setValue(Country);
        parm_Country.setType(String.class);
        request.addProperty(parm_Country);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call("http://tempuri.org/GET_Report_Home_Sales_Details", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }


    }
    public void GET_Report_Home_TransHeader(String Cust_No, String Man_No, String Op, String Flg, String FromDate, String ToDate, String VisitOrderNo, String TransOrderNo,String Country) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GET_Report_Home_Trans_Header");


        PropertyInfo parm_CustNo1 = new PropertyInfo();
        parm_CustNo1.setName("Cust_No");
        parm_CustNo1.setValue(Cust_No);
        parm_CustNo1.setType(String.class);
        request.addProperty(parm_CustNo1);




        PropertyInfo parm_ManNo= new PropertyInfo();
        parm_ManNo.setName("Man_No");
        parm_ManNo.setValue(Man_No);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);


        PropertyInfo parm_Op = new PropertyInfo();
        parm_Op.setName("Op");
        parm_Op.setValue(Op);
        parm_Op.setType(String.class);
        request.addProperty(parm_Op);


        PropertyInfo parm_Flg = new PropertyInfo();
        parm_Flg.setName("Flg");
        parm_Flg.setValue(Flg);
        parm_Flg.setType(String.class);
        request.addProperty(parm_Flg);


        PropertyInfo parm_FromDate1 = new PropertyInfo();
        parm_FromDate1.setName("FromDate");
        parm_FromDate1.setValue(FromDate);
        parm_FromDate1.setType(String.class);
        request.addProperty(parm_FromDate1);

        PropertyInfo parm_ToDate1 = new PropertyInfo();
        parm_ToDate1.setName("ToDate");
        parm_ToDate1.setValue(ToDate  );
        parm_ToDate1.setType(String.class);
        request.addProperty(parm_ToDate1);

        PropertyInfo parm_VisitOrderNo = new PropertyInfo();
        parm_VisitOrderNo.setName("VisitOrderNo");
        parm_VisitOrderNo.setValue(VisitOrderNo);
        parm_VisitOrderNo.setType(String.class);
        request.addProperty(parm_VisitOrderNo);

        PropertyInfo parm_TransOrderNo = new PropertyInfo();
        parm_TransOrderNo.setName("TransOrderNo");
        parm_TransOrderNo.setValue(TransOrderNo);
        parm_TransOrderNo.setType(String.class);
        request.addProperty(parm_TransOrderNo);

        PropertyInfo parm_Country = new PropertyInfo();
        parm_Country.setName("Country");
        parm_Country.setValue(Country);
        parm_Country.setType(String.class);
        request.addProperty(parm_Country);





        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call("http://tempuri.org/GET_Report_Home_Trans_Header", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }


    }
    public void GET_Report_Home_SalesValues(String Cust_No, String Man_No, String Op, String Flg, String FromDate, String ToDate, String VisitOrderNo, String TransOrderNo,String Country) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GET_Report_Home_SalesValues");


        PropertyInfo parm_CustNo1 = new PropertyInfo();
        parm_CustNo1.setName("Cust_No");
        parm_CustNo1.setValue(Cust_No);
        parm_CustNo1.setType(String.class);
        request.addProperty(parm_CustNo1);




        PropertyInfo parm_ManNo= new PropertyInfo();
        parm_ManNo.setName("Man_No");
        parm_ManNo.setValue(Man_No);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);


        PropertyInfo parm_Op = new PropertyInfo();
        parm_Op.setName("Op");
        parm_Op.setValue(Op);
        parm_Op.setType(String.class);
        request.addProperty(parm_Op);


        PropertyInfo parm_Flg = new PropertyInfo();
        parm_Flg.setName("Flg");
        parm_Flg.setValue(Flg);
        parm_Flg.setType(String.class);
        request.addProperty(parm_Flg);


        PropertyInfo parm_FromDate1 = new PropertyInfo();
        parm_FromDate1.setName("FromDate");
        parm_FromDate1.setValue(FromDate);
        parm_FromDate1.setType(String.class);
        request.addProperty(parm_FromDate1);

        PropertyInfo parm_ToDate1 = new PropertyInfo();
        parm_ToDate1.setName("ToDate");
        parm_ToDate1.setValue(ToDate  );
        parm_ToDate1.setType(String.class);
        request.addProperty(parm_ToDate1);

        PropertyInfo parm_VisitOrderNo = new PropertyInfo();
        parm_VisitOrderNo.setName("VisitOrderNo");
        parm_VisitOrderNo.setValue(VisitOrderNo);
        parm_VisitOrderNo.setType(String.class);
        request.addProperty(parm_VisitOrderNo);

        PropertyInfo parm_TransOrderNo = new PropertyInfo();
        parm_TransOrderNo.setName("TransOrderNo");
        parm_TransOrderNo.setValue(TransOrderNo);
        parm_TransOrderNo.setType(String.class);
        request.addProperty(parm_TransOrderNo);

        PropertyInfo parm_Country = new PropertyInfo();
        parm_Country.setName("Country");
        parm_Country.setValue(Country);
        parm_Country.setType(String.class);
        request.addProperty(parm_Country);





        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call("http://tempuri.org/GET_Report_Home_SalesValues", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }


    }
    public void GetCounterKey(String StoreNo, String Flag) {

        String resTxt = null;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, "CounterKey");
        // Property which holds input parameters

        PropertyInfo parm_StoreNo = new PropertyInfo();
        parm_StoreNo.setName("StoreNo");
        parm_StoreNo.setValue(StoreNo);
        parm_StoreNo.setType(String.class);
        request.addProperty(parm_StoreNo);



        PropertyInfo parm_Flag = new PropertyInfo();
        parm_Flag.setName("Flag");
        parm_Flag.setValue(Flag);
        parm_Flag.setType(String.class);
        request.addProperty(parm_Flag);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {
            androidHttpTransport.call("http://tempuri.org/CounterKey", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }
        //Return resTxt to calling object

    }
    public void GetBarCode(String Item_No) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GetBarCode");

        PropertyInfo parm_AccNo = new PropertyInfo();

        parm_AccNo.setName("Item_No");
        parm_AccNo.setValue(Item_No);
        parm_AccNo.setType(String.class);



        request.addProperty(parm_AccNo);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call("http://tempuri.org/GetBarCode", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();


            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }
        //Return resTxt to calling object

    }
    public void Get_Item(String searsh) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "Get_Item");


        PropertyInfo parm_AccNo = new PropertyInfo();

        parm_AccNo.setName("searsh");
        parm_AccNo.setValue(searsh);
        parm_AccNo.setType(String.class);



        request.addProperty(parm_AccNo);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call("http://tempuri.org/Get_Item", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void SaveTransProduse(String Json,String TransNo,String Date,String ItemNo,String ItemName) {
        We_Result.Msg="";
        We_Result.ID =-1;

        SoapObject request = new SoapObject(NAMESPACE, "SaveDoctorReport");
        PropertyInfo sayHelloPI = new PropertyInfo();
        sayHelloPI.setName("JsonStr");

        sayHelloPI.setValue(Json);
        sayHelloPI.setType(String.class);
        request.addProperty(sayHelloPI);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,40000);

        try {
            androidHttpTransport.call( "http://tempuri.org/SaveDoctorReport", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();

            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }


    }




    public void PostApprovalReturnRequest(String OrderNo, String Notes, String States) {
        We_Result.Msg="";
        We_Result.ID =-1;

        SoapObject request = new SoapObject(NAMESPACE, "PostApprovalReturnRequest");

        PropertyInfo sayHelloPI = new PropertyInfo();
        sayHelloPI.setName("OrderNo");
        sayHelloPI.setValue(OrderNo);
        sayHelloPI.setType(String.class);
        request.addProperty(sayHelloPI);

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("Notes");
        parm_ManNo.setValue(Notes);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);

        PropertyInfo parm_OrderNo = new PropertyInfo();
        parm_OrderNo.setName("States");
        parm_OrderNo.setValue(States);
        parm_OrderNo.setType(String.class);
        request.addProperty(parm_OrderNo);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,40000);



        try {
            androidHttpTransport.call( "http://tempuri.org/PostApprovalReturnRequest", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();

            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }


    }
    public void PostApprovalSalesOrder(String OrderNo, String Notes, String States) {
        We_Result.Msg="";
        We_Result.ID =-1;

        SoapObject request = new SoapObject(NAMESPACE, "PostApprovalSalesOrder");

        PropertyInfo sayHelloPI = new PropertyInfo();
        sayHelloPI.setName("OrderNo");
        sayHelloPI.setValue(OrderNo);
        sayHelloPI.setType(String.class);
        request.addProperty(sayHelloPI);

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("Notes");
        parm_ManNo.setValue(Notes);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);

        PropertyInfo parm_OrderNo = new PropertyInfo();
        parm_OrderNo.setName("States");
        parm_OrderNo.setValue(States);
        parm_OrderNo.setType(String.class);
        request.addProperty(parm_OrderNo);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,40000);



        try {
            androidHttpTransport.call( "http://tempuri.org/PostApprovalSalesOrder", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();

            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }


    }


    public void Get_SaveDev(String Json,String ManNo,String OrderNo) {
        We_Result.Msg="";
        We_Result.ID =-1;

        SoapObject request = new SoapObject(NAMESPACE, "Get_SaveDev");
        PropertyInfo sayHelloPI = new PropertyInfo();
        sayHelloPI.setName("Json");

        sayHelloPI.setValue(Json);
        sayHelloPI.setType(String.class);
        request.addProperty(sayHelloPI);
        PropertyInfo parm_ManNo = new PropertyInfo();

        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);

        PropertyInfo parm_OrderNo = new PropertyInfo();

        parm_OrderNo.setName("OrderNo");
        parm_OrderNo.setValue(OrderNo);
        parm_OrderNo.setType(String.class);
        request.addProperty(parm_OrderNo);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,40000);



        try {
            androidHttpTransport.call( "http://tempuri.org/Get_SaveDev", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();

            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }


    }


    public void SetUpdateTime(String UserId, String UpdateTime) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "SetUpdateTime");
        // Property which holds input parameters

        PropertyInfo parm_UserId = new PropertyInfo();
        parm_UserId.setName("UserId");
        parm_UserId.setValue(UserId);
        parm_UserId.setType(String.class);
        request.addProperty(parm_UserId);

        PropertyInfo parm_UpdateTime = new PropertyInfo();
        parm_UpdateTime.setName("UpdateTime");
        parm_UpdateTime.setValue(UpdateTime);
        parm_UpdateTime.setType(String.class);
        request.addProperty(parm_UpdateTime);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call("http://tempuri.org/SetUpdateTime", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"  ;//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }

    public void GetDoctors(String ManNo) {

        We_Result.Msg="";
        We_Result.ID =-1;

        SoapObject request = new SoapObject(NAMESPACE, "GetDoctors");

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);



        // Add the property to request object
        request.addProperty(parm_ManNo);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/GetDoctors", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }
        //Return resTxt to calling object

    }
    public void GetSpecialization() {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GetSpecialization");




        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call("http://tempuri.org/GetSpecialization", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");

        }


    }
    public long GetMonth_Dates(String ManNo,String Month,String Year  ) {
        We_Result.ID=-1;
        We_Result.Msg="";

        SoapObject request = new SoapObject(NAMESPACE, "Get_Month_Dates");

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);



        PropertyInfo parm_Month = new PropertyInfo();
        parm_Month.setName("Month");
        parm_Month.setValue(Month);
        parm_Month.setType(String.class);
        request.addProperty(parm_Month);




        PropertyInfo parm_Year= new PropertyInfo();
        parm_Year.setName("Year");
        parm_Year.setValue(Year);
        parm_Year.setType(String.class);
        request.addProperty(parm_Year);



        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,1000);
        Object  response =null;
        try {
            androidHttpTransport.call("http://tempuri.org/Get_Month_Dates", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }
        return We_Result.ID;

    }
    public long Get_Month_S(String ManNo,String Month,String Year  ) {
        We_Result.ID=-1;
        We_Result.Msg="";

        SoapObject request = new SoapObject(NAMESPACE, "Get_Month_S");

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);



        PropertyInfo parm_Month = new PropertyInfo();
        parm_Month.setName("Month");
        parm_Month.setValue(Month);
        parm_Month.setType(String.class);
        request.addProperty(parm_Month);




        PropertyInfo parm_Year= new PropertyInfo();
        parm_Year.setName("Year");
        parm_Year.setValue(Year);
        parm_Year.setType(String.class);
        request.addProperty(parm_Year);



        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,1000);
        Object  response =null;
        try {
            androidHttpTransport.call("http://tempuri.org/Get_Month_S", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
//+ e.getMessage().toString();
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }
        return We_Result.ID;

    }
    public long Save_MonthlySedule(String Json ) {

        We_Result.Msg="";
        We_Result.ID =-1;

        SoapObject request = new SoapObject(NAMESPACE, "SaveMonthly_Schedule");
        PropertyInfo sayHelloPI = new PropertyInfo();
        sayHelloPI.setName("JsonStr");
        sayHelloPI.setValue(Json);
        sayHelloPI.setType(String.class);
        request.addProperty(sayHelloPI);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {
            androidHttpTransport.call( "http://tempuri.org/SaveMonthly_Schedule", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");

            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
// + String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


        return  We_Result.ID;
    }
    public void GetCustomers1(String ManNo) {

        String resTxt = null;
        //  SoapObject request = new SoapObject(NAMESPACE, "GetCustomers");
        SoapObject request = new SoapObject(NAMESPACE, "GetCustomers1");

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ManNo");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,TIME_OUT_CONN);
        Object  response =null;
        try {

            androidHttpTransport.call("http://tempuri.org/GetCustomers1", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();
            resTxt = "Error occured";

        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();
            resTxt = "Error occured";
        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
            resTxt = "Error occured";
        }


    }
    public void GetManfN() {

        String resTxt = null;
        try {

            SoapObject request = new SoapObject(NAMESPACE, "GetManf1");
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,50000);
            Object  response =null;


            androidHttpTransport.call("http://tempuri.org/GetManf1", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();
            resTxt = "Error occured";

        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();
            resTxt = "Error occured";
        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");;
            e.printStackTrace();
            resTxt = "Error occured";
        }

//
    }
    public void GetItemsN() {



        String resTxt = null;
        try {
            SoapObject request = new SoapObject(NAMESPACE, "GetItemsN");
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,50000);
            Object  response =null;


            androidHttpTransport.call("http://tempuri.org/GetItemsN", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();
            resTxt = "Error occured";

        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();
            resTxt = "Error occured";
        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
            resTxt = "Error occured";
        }

//


    }
    public void GetVists(String ClincNo, String UserNo) {

        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, "GetClincVisitNotes1");

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("ClincNo");
        parm_ManNo.setValue(ClincNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);


        parm_ManNo.setName("UserNo");
        parm_ManNo.setValue(UserNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object response = null;
        try {

            androidHttpTransport.call("http://tempuri.org/GetClincVisitNotes1", envelope);
            SoapObject result = (SoapObject) envelope.getResponse();
            We_Result.Msg = result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException en) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();
            resTxt = "Error occured";

        } catch (EOFException eof) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();
            resTxt = "Error occured";
        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
            resTxt = "Error occured";
        }

    }

    public void GetItemsMed() {

        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, "GetItemsMed");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,TIME_OUT_CONN);
        Object  response =null;
        try {

            androidHttpTransport.call("http://tempuri.org/GetItemsMed", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();
            resTxt = "Error occured";

        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();
            resTxt = "Error occured";
        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
            resTxt = "Error occured";
        }

    }
    public void deptfMed() {

        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, "deptfMed");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call("http://tempuri.org/deptfMed", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();
            resTxt = "Error occured";

        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();
            resTxt = "Error occured";
        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
            resTxt = "Error occured";
        }

    }
    public void Get_Offers_GroupsMed() {

        String resTxt = null;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, "Get_Offers_GroupsMed");
        // Property which holds input parameters

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {
            androidHttpTransport.call("http://tempuri.org/Get_Offers_GroupsMed", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());

        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();
            resTxt = "Error occured";

        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();
            resTxt = "Error occured";
        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
            resTxt = "Error occured";
        }
        //Return resTxt to calling object

    }
    public void Save_Sal_InvoiceMed(String Json) {
        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, "Insert_Sale_InvoiceMed");
        PropertyInfo sayHelloPI = new PropertyInfo();
        sayHelloPI.setName("JsonStr");
        sayHelloPI.setValue(Json);
        sayHelloPI.setType(String.class);
        request.addProperty(sayHelloPI);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,40000);
        Object  response =null;
        try {

            androidHttpTransport.call( "http://tempuri.org/Insert_Sale_InvoiceMed", envelope);

            SoapObject result  = (SoapObject) envelope.getResponse();



            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

            resTxt = "Error occured";

        }


    }
    public void SaveDoctorReportMed(String Json, String SummeryVisit/*,String Lockups*/) {
        We_Result.Msg =  " "         ;
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "SaveDoctorReportMed");
        PropertyInfo ParmJson = new PropertyInfo();
        ParmJson.setName("JsonStr");
        ParmJson.setValue(Json);
        ParmJson.setType(String.class);
        request.addProperty(ParmJson);

        PropertyInfo ParmSummeryVisit = new PropertyInfo();
        ParmSummeryVisit.setName("SummeryVisit");
        ParmSummeryVisit.setValue(SummeryVisit);
        ParmSummeryVisit.setType(String.class);
        request.addProperty(ParmSummeryVisit);



      /*  PropertyInfo Lockupss = new PropertyInfo();
        Lockupss.setName("Lockups");
        Lockupss.setValue(Lockups);
        Lockupss.setType(String.class);
        request.addProperty(ParmJson);
*/
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,40000);
        Object  response =null;
        try {
            androidHttpTransport.call( "http://tempuri.org/SaveDoctorReportMed", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }
        //Return resTxt to calling object

    }


    public void GetItemsKeysN() {

        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, "GetItemsKeysN");




        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call("http://tempuri.org/GetItemsKeysN", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();
            resTxt = "Error occured";

        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();
            resTxt = "Error occured";
        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
            resTxt = "Error occured";
        }


    }




    public void GET_ACCF() {
        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, "GET_ACCF");




        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call("http://tempuri.org/GET_ACCF", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();
            resTxt = "Error occured";

        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();
            resTxt = "Error occured";
        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
            resTxt = "Error occured";
        }


    }


    public void GetCustomerFavDays() {

        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, "GetCustomerFavDays");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,TIME_OUT_CONN);
        Object  response =null;
        try {

            androidHttpTransport.call("http://tempuri.org/GetCustomerFavDays", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();
            resTxt = "Error occured";

        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();
            resTxt = "Error occured";
        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
            resTxt = "Error occured";
        }

    }

    public void GetAreasN(String TabltNo) {

        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, "GetUserLookups");

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("TabltNo");
        parm_ManNo.setValue(TabltNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call("http://tempuri.org/GetUserLookups", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();
            resTxt = "Error occured";

        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();
            resTxt = "Error occured";
        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
            resTxt = "Error occured";
        }


    }
    public void GetUserDefinition(String UserN) {


            //URL = "http://176.29.108.202:3738/CV.asmx";


        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, "GetUserDefinition");

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("UserN");
        parm_ManNo.setValue(UserN);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Object  response =null;
        try {

            androidHttpTransport.call("http://tempuri.org/GetUserDefinition", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();
            resTxt = "Error occured";

        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();
            resTxt = "Error occured";
        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
            resTxt = "Error occured";
        }


    }
    public void Get_ClincNotes(String ClincNo, String UserNo ) {
        We_Result.Msg =  " "    ;
        We_Result.ID =-1;
        String resTxt = null;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, "GetClincVisitNotes");
        // Property which holds input parameters
        PropertyInfo parm_ClincNo = new PropertyInfo();

        parm_ClincNo.setName("ClincNo");
        parm_ClincNo.setValue(ClincNo);
        parm_ClincNo.setType(String.class);

        PropertyInfo parm_UserNo = new PropertyInfo();
        parm_UserNo.setName("UserNo");
        parm_UserNo.setValue(UserNo);
        parm_UserNo.setType(String.class);





        request.addProperty(parm_UserNo);
        request.addProperty(parm_ClincNo);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,TIME_OUT_CONN);
        Object  response =null;
        try {
            // Invoke web service
            androidHttpTransport.call("http://tempuri.org/GetClincVisitNotes", envelope);
            // Get the response
            SoapObject result  = (SoapObject) envelope.getResponse();


            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());



        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();
            resTxt = "Error occured";

        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();
            resTxt = "Error occured";
        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();
            resTxt = "Error occured";
        }
        //Return resTxt to calling object

    }

    public void GET_ManDelivery(String ManNo) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GET_ManDelivery");

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("man");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/GET_ManDelivery", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;//+ String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void GET_ManSalesHdr(String ManNo) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GET_ManSalesHdr");

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("man");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/GET_ManSalesHdr", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;//+ String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }
    public void GET_ManSalesDtl(String ManNo) {

        We_Result.Msg="";
        We_Result.ID =-1;
        SoapObject request = new SoapObject(NAMESPACE, "GET_ManSalesDtl");

        PropertyInfo parm_ManNo = new PropertyInfo();
        parm_ManNo.setName("man");
        parm_ManNo.setValue(ManNo);
        parm_ManNo.setType(String.class);
        request.addProperty(parm_ManNo);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call("http://tempuri.org/GET_ManSalesDtl", envelope);
            SoapObject result  = (SoapObject) envelope.getResponse();
            We_Result.Msg =  result.getProperty("Msg").toString();
            We_Result.ID = Long.parseLong(result.getProperty("ID").toString());
        } catch (NullPointerException   en){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"       ;
            We_Result.ID = Long.parseLong("-404");
            en.printStackTrace();


        } catch (EOFException eof ){
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"         ;
            We_Result.ID = Long.parseLong("-404");
            eof.printStackTrace();

        }
        catch (Exception e) {
            We_Result.Msg =  "عملية الاتصال بالسيرفر لم تتم بنجاح"    ;//+ String.valueOf(e.getMessage().toString());
            We_Result.ID = Long.parseLong("-404");
            e.printStackTrace();

        }


    }

}
