package com.example.sakshi.contentsfromfile_acad;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import static android.R.attr.path;

public class MainActivity extends AppCompatActivity {
    //declaring buttons, Textview and EditText
    Button add, delete;
    TextView textview;
    EditText editText;
    String filedata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //associating components with their ids
        add= (Button) findViewById(R.id.add);
        editText = (EditText) findViewById(R.id.data);
        delete = (Button) findViewById(R.id.delete);
        textview = (TextView) findViewById(R.id.datafromfile);
        filedata = editText.getText().toString();
        //on click listener for add button
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //executing the asyncTask
                new addFileData().execute();
            }
        });
        //on click listener for delete button
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File myFile = new File(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath())+File.separator+"test.txt");
                //deletes file
                boolean deleted = myFile.delete();
                if(deleted){
                    Toast.makeText(MainActivity.this, "File Deleted", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Can not delete file", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private class addFileData extends AsyncTask<String,String,String> {


        @Override
        protected void onPreExecute() {
            Toast.makeText(MainActivity.this, "Data adding to file", Toast.LENGTH_SHORT).show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            File txtFile=new File(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath())+File.separator+"test.txt");
            try
            {   //output stream for writing data to file
                FileOutputStream fout=new FileOutputStream(txtFile);
                OutputStreamWriter output=new OutputStreamWriter(fout);
                //adding data to file
                output.append(filedata);
                output.close();
                fout.close();
                Toast.makeText(MainActivity.this, "Data added Successfully!", Toast.LENGTH_SHORT).show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                File myFile = new File(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath())+File.separator+"test.txt");
                StringBuilder text = new StringBuilder();
                //obtaining input bytes from file
                FileInputStream fIn = new FileInputStream(myFile);
                BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
                String line;
                //reading text from file
                while ((line = myReader.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
                //setting  text to textView
                textview.setText(text.toString());
                myReader.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }
}
