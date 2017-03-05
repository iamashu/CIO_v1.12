package com.example.ashutosh.music_player;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomMenuButton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;

public class Home extends AppCompatActivity {

    private static final String URL_DATA = "http://www.radiomirchi.com/more/mirchi-top-20" ;
    private RecyclerView rv ;
    private RecyclerView.Adapter adapter ;
    ArrayList<String> arrayList ;
    ArrayList<String> arrayList1 ;
    private ImageView party  ;
    private ImageView love  ;
    private ImageView sad  ;
    private ImageView dance  ;
    private ImageView motivation  ;
    private ImageView journey ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BoomMenuButton bmb = (BoomMenuButton) findViewById(R.id.boom);

        rv = (RecyclerView) findViewById(R.id.rview) ;
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv.setHasFixedSize(true);

        loadRecyclerViewData() ;

        party = (ImageView) findViewById(R.id.iv1) ;
        love = (ImageView) findViewById(R.id.iv2) ;
        sad = (ImageView) findViewById(R.id.iv3) ;
        dance = (ImageView) findViewById(R.id.iv4) ;
        motivation = (ImageView) findViewById(R.id.iv5) ;
        journey = (ImageView) findViewById(R.id.iv6) ;

        party.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Main2Activity.class) ;
                intent.putExtra("category" , "one") ;
                startActivity(intent);
            }
        });
        love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Main2Activity.class) ;
                intent.putExtra("category" , "two") ;
                startActivity(intent);
            }
        });
        sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Main2Activity.class) ;
                intent.putExtra("category" , "three") ;
                startActivity(intent);
            }
        });
        dance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Main2Activity.class) ;
                intent.putExtra("category" , "four") ;
                startActivity(intent);
            }
        });
        motivation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Main2Activity.class) ;
                intent.putExtra("category" , "five") ;
                startActivity(intent);
            }
        });
        journey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Main2Activity.class) ;
                intent.putExtra("category" , "six") ;
                startActivity(intent);
            }
        });

        for(int i=0 ; i<bmb.getPiecePlaceEnum().pieceNumber() ; i++)
        {
            SimpleCircleButton.Builder builder = new SimpleCircleButton.Builder()
                    .normalImageRes(R.drawable.ic_menu_gallery) ;
            bmb.addBuilder(builder);
        }
    }


    private void loadRecyclerViewData()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this) ;
        progressDialog.setMessage("Loading data...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try
                        {
                            Document document = Jsoup.parse(response) ;
                            Elements arr1 = document.getElementsByTag("h2") ;
                            Elements arr2 = document.select("div.movieImg img") ;
                            arrayList = new ArrayList<String>() ;
                            arrayList1 = new ArrayList<String>() ;
                            Iterator i1 = arr1.listIterator() ;

                            while(i1.hasNext())
                            {
                                arrayList.add(i1.next().toString().replace("<h2>", "").replace("</h2>", "").replace("Video", "").replace("Song", "")) ;
                            }
                            for(Element el : arr2)
                            {
                                    String s = "http://www.radiomirchi.com" + el.attr("src");
                                    arrayList1.add(s) ;
                            }

                            adapter = new MyAdapter(arrayList,arrayList1,getApplicationContext()) ;
                            rv.setAdapter(adapter);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) ;

        RequestQueue requestQueue = Volley.newRequestQueue(this) ;
        requestQueue.add(stringRequest) ;
    }

}
