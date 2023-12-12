package com.cds_jo.GalaxySalesApp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.File;

public class testAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        PDFView pdf =(PDFView) findViewById(R.id.pdf);
        File imgFile = new  File("//sdcard/Android/Cv_Images/ff1.pdf");
        pdf.fromFile(imgFile)
                .defaultPage(50)
                //.onPageChange()
                .enableAnnotationRendering(true)
               // .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(10) // in dp
                //.onPageError(this)
                .load();
    }
}