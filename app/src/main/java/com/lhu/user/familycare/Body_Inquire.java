package com.lhu.user.familycare;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.lhu.user.familycare.db.BodyStatus;
import com.lhu.user.familycare.db.BodyStatusDAO;
import com.lhu.user.familycare.tool.IMGtool;

import java.util.ArrayList;

public class Body_Inquire extends AppCompatActivity {
    private Context context;
    private ImageView bg_quire;
    private ListView listView;
    private BodyStatusDAO bodyStatusDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_inquire);
        context = this;
        bg_quire = (ImageView) findViewById(R.id.bg_quire);
        bg_quire.setImageBitmap(IMGtool.readBitMap(context,R.drawable.body_inquire));
        listView = (ListView) findViewById(R.id.body_quire_list);
        bodyStatusDAO =new BodyStatusDAO(context);
        try {
            System.out.println();
            ArrayList<BodyStatus> bodyStatuses =bodyStatusDAO.getBodyStatuses_List();
            if(bodyStatuses == null){
                Toast.makeText(context,"bodyStatuses == null",Toast.LENGTH_SHORT).show();
                return;
            }
            ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,bodyStatuses);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getApplicationContext(), "你選擇的是第" + (position+1)+"個", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
        }

//
    }

}
