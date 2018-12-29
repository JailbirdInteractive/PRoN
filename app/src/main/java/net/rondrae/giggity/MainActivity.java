package net.rondrae.giggity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import net.rondrae.giggity.models.CatItem;
import net.rondrae.giggity.models.VideoClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * TODO: Add JAVADOC
 *
 */
public class MainActivity extends AppCompatActivity implements MaterialSearchBar.OnSearchActionListener,View.OnClickListener, PopupMenu.OnMenuItemClickListener,ListenerClass.OnListFragmentInteractionListener {

    private MaterialSearchBar searchBar;
ImageView image1,image2,image3;
    private RecyclerView recyclerView;
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        searchBar.setHint("Enter Search you perv");
        searchBar.setSpeechMode(true);
        searchBar.inflateMenu(R.menu.main_menu);
        searchBar.getMenu().setOnMenuItemClickListener(this);
        searchBar.setCardViewElevation(5);
        searchBar.setNavButtonEnabled(true);
        //enable searchbar callbacks
        searchBar.setOnSearchActionListener(this);
recyclerView=findViewById(R.id.vid_list);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));



        /*image1=findViewById(R.id.image1);
        image2=findViewById(R.id.image2);
        image3=findViewById(R.id.image3);
        image2.setOnClickListener(this);
        image1.setOnClickListener(this);
        image3.setOnClickListener(this);*/
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

        //Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
new NetworkTask().execute("https://api.redtube.com/?data=redtube.Videos.searchVideos&output=json&search=hard&tags[]="+text+"&thumbsize=medium");
        /*Intent intent=new Intent(getApplicationContext(),ImagePage.class);
        startActivity(intent);*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onButtonClicked(int buttonCode) {

    }
    public String getPorn(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();

    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getApplicationContext(),ImagePage.class) ;
       String url="";
        switch (v.getId()){
       /* case R.id.image1:
        url= (String) v.getTag();
        intent.putExtra("url",url);
        startActivity(intent);
        break;
        case R.id.image2:
                url= (String) v.getTag();
                intent.putExtra("url",url);
            startActivity(intent);
                break;
            case R.id.image3:
                url= (String) v.getTag();
                intent.putExtra("url",url);
                startActivity(intent);
                break;*/
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        startActivity(new Intent(getApplicationContext(),PickerActivity.class));
        return false;
    }

    @Override
    public void onListFragmentInteraction(VideoClass item) {
        Intent intent=new Intent(getApplicationContext(),ImagePage.class) ;
        String url="";
        url=item.getVideoUrl();
        intent.putExtra("url",url);
        startActivity(intent);
    }


    private class NetworkTask extends AsyncTask<String, Void, String>{
       // ImageView[]imageViews={image3,image1,image2};

        @Override
        protected String doInBackground(String... strings) {
            try {
                String result=getPorn(strings[0]);
                return result;
            } catch (IOException e) {
                e.printStackTrace();
           return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject object=new JSONObject(s);
                JSONArray array=object.getJSONArray("videos");
                ArrayList<VideoClass> videoClasses=new ArrayList<>();
                videoClasses=VideoClass.fromJson(array);
                MyVidAdapter adapter=new MyVidAdapter(videoClasses,MainActivity.this,context);
                recyclerView.setAdapter(adapter);
                /*for(int i=0;i<3;i++){
                    JSONObject jsonObject=array.getJSONObject(i);
                    JSONObject object1=jsonObject.getJSONObject("video");
                    String imageUrl=object1.getString("default_thumb");
                    String vidUrl=object1.getString("url");
                    imageViews[i].setTag(vidUrl);
                    Picasso.get().load(imageUrl).into(imageViews[i]);
                    Log.w("Result","the image is "+imageUrl);

                }*/

            } catch (JSONException e) {
                e.printStackTrace();
            }

            super.onPostExecute(s);
        }
    }

}
