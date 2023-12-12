package header;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.support.v4.app.Fragment;

import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.ComInfo;
import com.cds_jo.GalaxySalesApp.Companies;
import com.cds_jo.GalaxySalesApp.DB;
import com.cds_jo.GalaxySalesApp.GalaxyLoginActivity;
import com.cds_jo.GalaxySalesApp.NewLoginActivity;
import com.cds_jo.GalaxySalesApp.NewPackage.LocaleHelper;
import com.cds_jo.GalaxySalesApp.PopSmallMenue;
import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.Select_Customer;
import com.cds_jo.GalaxySalesApp.Update_SFragment;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Hp on 20/11/2016.
 */
public class Header_Frag extends Fragment {

    private ImageView Option,Img_Setting, sort;
    private SimpleSideDrawer mNav;
    Methdes.MyTextView tv_CpmpanyName , tv_UserNm ;
    private ArrayList<Main_List_Itme> myList_Setting;
    private ListView listView;
    String lan  = "en";
    String RepType;
LinearLayout LL2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        RepType= sharedPreferences.getString("TypeRep", "-1");
        View view=inflater.inflate(R.layout.header_frag,container,false);
        sort =(ImageView)view.findViewById(R.id.imageView62);
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                FragmentManager Manager = getActivity().getFragmentManager();
                PopSmallMenue obj = new PopSmallMenue();
                bundle.putString("Msg", "ff" + "   " + "CusNm");
                obj.setArguments(bundle);
                obj.show(Manager, null);

            }
        });
        lan =  Locale.getDefault().getDisplayLanguage();
        lan=  sharedPreferences.getString("Lan", "");
        lan= LocaleHelper.getlanguage(getActivity());//.setLocale(getActivity(), "ar");

       // View view=inflater.inflate(R.layout.header_frag_ar,container, false);

        if(lan.equalsIgnoreCase("ar"))
        {
            LinearLayout linrtl=(LinearLayout)view.findViewById(R.id.LL2);
            linrtl.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        else
        {
            LinearLayout linrtl=(LinearLayout)view.findViewById(R.id.LL2);
            linrtl.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }


       // Initi(view);

      /*  mNav = new SimpleSideDrawer(getActivity());
        if(lan.equalsIgnoreCase("en")) {
            mNav.setLeftBehindContentView(R.layout.setting_list);

        }else{
            mNav.setRightBehindContentView(R.layout.setting_list);

        }*/
        Initi(view);
        mNav = new SimpleSideDrawer(getActivity());
        if(lan.equalsIgnoreCase("en")) {
            mNav.setLeftBehindContentView(R.layout.setting_list);

        }else{
            mNav.setRightBehindContentView(R.layout.setting_list);

        }

         if(RepType.equals("1")) {
             if (ComInfo.ComNo== Companies.Afrah.getValue()) {
                 FillData_Setting();

             }
             else
             {
                 FillData_Setting1();
             }

         }else
         {
             FillData_Setting2();
         }
        Option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShwoPop(v);
            }
        });
        tv_CpmpanyName = (Methdes.MyTextView)view.findViewById(R.id.tv_CpmpanyName);
        tv_UserNm = (Methdes.MyTextView)view.findViewById(R.id.tv_UserNm);
         String UserID=sharedPreferences.getString("UserID", "-1");
         String TypeDesc;


        TypeDesc= DB.GetValue(this.getActivity(),"manf","TypeDesc","man='"+UserID+"'");
        tv_UserNm.setText( sharedPreferences.getString("UserName", "")+"/"+TypeDesc);

        tv_CpmpanyName.setText( DB.GetValue(this.getActivity(),"ComanyInfo","CompanyNm","1=1" ) +"  "  +DB.GetValue(this.getActivity(),"ComanyInfo","CompanyID","1=1"));
        Img_Setting=(ImageView)view.findViewById(R.id.imageView6);

        if(lan.equalsIgnoreCase("en")){
            Img_Setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mNav.toggleLeftDrawer();
                    //   mNav.toggleLeftDrawer();
                    NotificationFun();
                }
            });
        }   else
        {
            Img_Setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mNav.toggleRightDrawer();
                    // mNav.toggleRightDrawer();
                    NotificationFun();
                }
            });
        }

        Button   update=(Button)mNav.findViewById(R.id.updatesystemm);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("Scr", "update");
                FragmentManager Manager = getActivity().getFragmentManager();
                Update_SFragment obj = new Update_SFragment();
                obj.setArguments(bundle);
                obj.show(Manager, null);

            }
        });





        return view;
    }

    private void Initi_ar(View view) {

        Option=(ImageView)view.findViewById(R.id.imageView5);
        Img_Setting=(ImageView)view.findViewById(R.id.imageView6);
        Img_Setting=(ImageView)view.findViewById(R.id.imageView6);
        mNav = new SimpleSideDrawer(getActivity());
       // mNav.setRightBehindContentView(R.layout.setting_list);
         mNav.setLeftBehindContentView(R.layout.setting_list);


    }
    private void Initi(View view) {

        Option=(ImageView)view.findViewById(R.id.imageView5);
        Img_Setting=(ImageView)view.findViewById(R.id.imageView6);
      //  mNav = new SimpleSideDrawer(getActivity());
     //   mNav.setRightBehindContentView(R.layout.setting_list);




    }

    private void ShwoPop(View view) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.inflate(R.menu.menu_pop_anim);
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.item_0:
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        String Login = sharedPreferences.getString("Login", "No");
//                        if (Login.toString().equals("No")) {
                        Intent intent = new Intent(getActivity().getApplicationContext(), NewLoginActivity.class);
                        startActivity(intent);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                       // getActivity().startActivity(intent);

//                        }
                }
                return true;
            }
        });
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////

    private void NotificationFun() {
        listView=(ListView)mNav.findViewById(R.id.listView11);
        listView.setAdapter(new Setting_List_Adapter(getActivity(),myList_Setting));

    }

    private void FillData_Setting1() {
        String []arr=getResources().getStringArray(R.array.Main_Item1);
        myList_Setting=new ArrayList<>();
        Main_List_Itme itme=new Main_List_Itme();

        itme=new Main_List_Itme();
        itme.setTitle(arr[0]);
        itme.setImg(R.mipmap.see11);
        myList_Setting.add(itme);


        itme=new Main_List_Itme();
        itme.setTitle(arr[1]);
        itme.setImg(R.mipmap.see11);
        myList_Setting.add(itme);

        itme=new Main_List_Itme();
        itme.setTitle(arr[2]);
        itme.setImg(R.mipmap.see11);
        myList_Setting.add(itme);

        itme=new Main_List_Itme();
        itme.setTitle(arr[3]);
        itme.setImg(R.mipmap.see11);
        myList_Setting.add(itme);


        itme=new Main_List_Itme();
        itme.setTitle(arr[4]);
        itme.setImg(R.mipmap.see11);
        myList_Setting.add(itme);


        itme=new Main_List_Itme();
        itme.setTitle(arr[5]);
        itme.setImg(R.mipmap.see11);
        myList_Setting.add(itme);

        itme=new Main_List_Itme();
        itme.setTitle(arr[6]);
        itme.setImg(R.mipmap.see11);
        myList_Setting.add(itme);

        itme=new Main_List_Itme();
        itme.setTitle(arr[7]);
        itme.setImg(R.mipmap.see13);
        myList_Setting.add(itme);

        itme=new Main_List_Itme();
        itme.setTitle(arr[8]);
        itme.setImg(R.mipmap.see2);
        myList_Setting.add(itme);


        itme=new Main_List_Itme();
        itme.setTitle(arr[9]);
        itme.setImg(R.mipmap.see2);
        myList_Setting.add(itme);

        itme=new Main_List_Itme();
        itme.setTitle(arr[10]);
        itme.setImg(R.mipmap.see3);
        myList_Setting.add(itme);


        itme=new Main_List_Itme();
        itme.setTitle(arr[11]);
        itme.setImg(R.mipmap.see3);
        myList_Setting.add(itme);

        itme=new Main_List_Itme();
        itme.setTitle(arr[12]);
        itme.setImg(R.mipmap.see3);
        myList_Setting.add(itme);

        itme=new Main_List_Itme();
        itme.setTitle(arr[13]);
        itme.setImg(R.mipmap.see3);
        myList_Setting.add(itme);

        itme=new Main_List_Itme();
        itme.setTitle(arr[14]);
        itme.setImg(R.mipmap.see3);
        myList_Setting.add(itme);

        itme=new Main_List_Itme();
        itme.setTitle(arr[15]);
        itme.setImg(R.mipmap.see3);
        myList_Setting.add(itme);

        itme=new Main_List_Itme();
        itme.setTitle(arr[16]);
        itme.setImg(R.mipmap.see3);
        myList_Setting.add(itme);

        itme=new Main_List_Itme();
        itme.setTitle(arr[17]);
        itme.setImg(R.mipmap.see3);
        myList_Setting.add(itme);

        itme=new Main_List_Itme();
        itme.setTitle(arr[18]);
        itme.setImg(R.mipmap.see3);
        myList_Setting.add(itme);


        itme=new Main_List_Itme();
        itme.setTitle(arr[19]);
        itme.setImg(R.mipmap.see3);
        myList_Setting.add(itme);


        itme=new Main_List_Itme();
        itme.setTitle(arr[20]);
        itme.setImg(R.mipmap.see3);
        myList_Setting.add(itme);


        itme=new Main_List_Itme();
        itme.setTitle(arr[21]);
        itme.setImg(R.mipmap.see7);
        myList_Setting.add(itme);

        itme=new Main_List_Itme();
        itme.setTitle(arr[22]);
        itme.setImg(R.mipmap.see13);
        myList_Setting.add(itme);


        itme=new Main_List_Itme();
        itme.setTitle(arr[23]);
        itme.setImg(R.mipmap.see3);
        myList_Setting.add(itme);

        itme=new Main_List_Itme();
        itme.setTitle(arr[24]);
        itme.setImg(R.mipmap.see3);
        myList_Setting.add(itme);

        itme=new Main_List_Itme();
        itme.setTitle(arr[25]);
        itme.setImg(R.mipmap.see18);
        myList_Setting.add(itme);

        itme=new Main_List_Itme();
        itme.setTitle("سند الصرف");
        itme.setImg(R.mipmap.see18);
        myList_Setting.add(itme);

        itme=new Main_List_Itme();
        itme.setTitle("طلب مرتجع");
        itme.setImg(R.mipmap.see7);
        myList_Setting.add(itme);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String UserID=sharedPreferences.getString("UserID", "-1");
        String ManType = DB.GetValue(this.getActivity(), "manf", "ManType", "man='" + UserID + "'");

        if(ManType.equals("2")){

            itme=new Main_List_Itme();
            itme.setTitle("طلبات بيع المندوبين");
            itme.setImg(R.mipmap.see3);
            myList_Setting.add(itme);

            itme=new Main_List_Itme();
            itme.setTitle("طلبات مرتجع المندوبين");
            itme.setImg(R.mipmap.see3);
            myList_Setting.add(itme);

        }else{

            itme=new Main_List_Itme();
            itme.setTitle("طلباتي");
            itme.setImg(R.mipmap.see3);
            myList_Setting.add(itme);

        }
    }





    private void FillData_Setting2() {
        String []arr=getResources().getStringArray(R.array.Main_Item2);
        myList_Setting=new ArrayList<>();
        Main_List_Itme itme=new Main_List_Itme();


        itme=new Main_List_Itme();
        itme.setTitle(arr[0]);
        itme.setImg(R.mipmap.see11);
        myList_Setting.add(itme);

        itme=new Main_List_Itme();
        itme.setTitle(arr[1]);
        itme.setImg(R.mipmap.see11);
        myList_Setting.add(itme);


        itme=new Main_List_Itme();
        itme.setTitle(arr[2]);
        itme.setImg(R.mipmap.see7);
        myList_Setting.add(itme);


        itme=new Main_List_Itme();
        itme.setTitle(arr[3]);
        itme.setImg(R.mipmap.see18);
        myList_Setting.add(itme);

        itme=new Main_List_Itme();
        itme.setTitle("سند الصرف");
        itme.setImg(R.mipmap.see18);
        myList_Setting.add(itme);

        itme=new Main_List_Itme();
        itme.setTitle("طلب مرتجع");
        itme.setImg(R.mipmap.see7);
        myList_Setting.add(itme);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String UserID=sharedPreferences.getString("UserID", "-1");
        String ManType = DB.GetValue(this.getActivity(), "manf", "ManType", "man='" + UserID + "'");


       if(ManType.equals("2")){
           itme=new Main_List_Itme();
           itme.setTitle("طلبات بيع المندوبين");
           itme.setImg(R.mipmap.see3);
           myList_Setting.add(itme);
           itme=new Main_List_Itme();
           itme.setTitle("طلبات مرتجع المندوبين");
           itme.setImg(R.mipmap.see3);
           myList_Setting.add(itme);
       }else{

        itme=new Main_List_Itme();
        itme.setTitle("طلباتي");
        itme.setImg(R.mipmap.see3);
        myList_Setting.add(itme);}

    }
    private void FillData_Setting() {
        String []arr=getResources().getStringArray(R.array.Main_Item3);
        myList_Setting=new ArrayList<>();

        Main_List_Itme itme=new Main_List_Itme();
        itme.setTitle(arr[0]);
        itme.setImg(R.mipmap.see3);
        myList_Setting.add(itme);






        itme=new Main_List_Itme();
        itme.setTitle(arr[1]);
        itme.setImg(R.mipmap.see3);
        myList_Setting.add(itme);


        itme=new Main_List_Itme();
        itme.setTitle(arr[2]);
        itme.setImg(R.mipmap.see3);
        myList_Setting.add(itme);






        itme=new Main_List_Itme();
        itme.setTitle(arr[3]);
        itme.setImg(R.mipmap.see7);
        myList_Setting.add(itme);

        itme=new Main_List_Itme();
        itme.setTitle(arr[4]);
        itme.setImg(R.mipmap.see7);
        myList_Setting.add(itme);

        itme=new Main_List_Itme();
        itme.setTitle(arr[5]);
        itme.setImg(R.mipmap.see7);
        myList_Setting.add(itme);



        itme=new Main_List_Itme();
        itme.setTitle("سند الصرف");
        itme.setImg(R.mipmap.see18);
        myList_Setting.add(itme);

        itme=new Main_List_Itme();
        itme.setTitle("طلب مرتجع");
        itme.setImg(R.mipmap.see7);
        myList_Setting.add(itme);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String UserID=sharedPreferences.getString("UserID", "-1");
        String ManType = DB.GetValue(this.getActivity(), "manf", "ManType", "man='" + UserID + "'");


        if(ManType.equals("2")){

            itme=new Main_List_Itme();
            itme.setTitle("طلبات بيع المندوبين");
            itme.setImg(R.mipmap.see3);
            myList_Setting.add(itme);

            itme=new Main_List_Itme();
            itme.setTitle("طلبات مرتجع المندوبين");
            itme.setImg(R.mipmap.see3);
            myList_Setting.add(itme);

        }else{

            itme=new Main_List_Itme();
            itme.setTitle("طلباتي");
            itme.setImg(R.mipmap.see3);
            myList_Setting.add(itme);

        }
    }

  ///////////////////////////////////////////////////////////////////////////////////////////////////

}
