package com.example.rasekgala.firstaid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class AboutApp extends Activity {

    private TextView aboutStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        //change activity title
        this.setTitle("About App");

        aboutStory = (TextView)findViewById(R.id.textFeedBack);
        aboutStory.setMovementMethod(new ScrollingMovementMethod());
        //set the text
        aboutStory.setText("Once an executable image has been memory mapped into a processes virtual memory it can start to execute. As only the very start of the image is physically pulled into memory it will soon access an area of virtual memory that is not yet in physical memory. When a process accesses a virtual address that does not have a valid page table entry, the processor will report a page fault to Linux.\n" +
                "The page fault describes the virtual address where the page fault occurred and the type of memory access that caused.\n" +
                "Linux must find the vm_area_struct that represents the area of memory that the page fault occurred in. As searching through the vm_area_struct data structures is critical to the efficient handling of page faults, these are linked together in an AVL (Adelson-Velskii and Landis) tree structure. If there is no vm_area_struct data structure for this faulting virtual address, this process has accessed an illegal virtual address. Linux will signal the process, sending a SIGSEGV signal, and if the process does not have a handler for that signal it will be terminated.\n");


    }


    /*
*This method is used to change the action of back button on android
 */
    @Override
    public void onBackPressed() {
        //when pressed we move back to Allmessage activity
        //to avoid it from exitting the app
        startActivity(new Intent(this, AllMessageActivity.class));
        this.finish();
    }
}
