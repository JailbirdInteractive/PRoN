package net.rondrae.giggity;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;
import com.squareup.picasso.Picasso;

import net.rondrae.giggity.models.VideoClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Main Activity Contains the search bar as well as a Navigation menu for searching through categories
 * The categories are loaded from the categories.json file and then added to the menu
 * Selecting a category from the menu puts the category string into the api request url and perfoms a search
 */
public class MainActivity extends AppCompatActivity implements MaterialSearchBar.OnSearchActionListener, View.OnClickListener, PopupMenu.OnMenuItemClickListener, ListenerClass.OnListFragmentInteractionListener {

    private MaterialSearchBar searchBar;
    ImageView image1, image2, image3;
    private RecyclerView recyclerView;
    Context context;
    SNavigationDrawer sNavigationDrawer;
    ProgressBar progressBar;
    TextView catText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sNavigationDrawer = findViewById(R.id.navigationDrawer);
        progressBar = findViewById(R.id.simpleProgressBar);
        catText=findViewById(R.id.cat_txt);
        context = this;
        //Setting up searchbar
        searchBar = findViewById(R.id.searchBar);
        searchBar.setHint("Enter Search you perv");
        searchBar.setSpeechMode(true);
        searchBar.inflateMenu(R.menu.main_menu);
        searchBar.getMenu().setOnMenuItemClickListener(this);
        searchBar.setCardViewElevation(5);
        searchBar.setNavButtonEnabled(true);
        searchBar.inflateMenu(R.menu.main_menu);
        searchBar.getMenu().setOnMenuItemClickListener(this);
        searchBar.setOnSearchActionListener(this);


       //initializing the recyclerview
        recyclerView = findViewById(R.id.vid_list);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));



       }

    @Override
    protected void onResume() {
        setUpMenu();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * This method calls ths loadJSONFromAsset method and then gets the string for the name of the category
     * It then creates a MenuItem to be added to the Navigation Menu
     * A menu item accepts a string and a resource ID for an image.
     */
    private void setUpMenu() {
//List of MenuItems to be used in Navigation Menu
        List<com.shrikanthravi.customnavigationdrawer2.data.MenuItem> menuItems = new ArrayList<>();


//Parsing the JSON file into strings
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("categories");

            //Looping through the categories
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                Log.d("Details-->", jo_inside.getString("category"));
                String cat = jo_inside.getString("category");

                //creating and adding the MenuItems to the list
                menuItems.add(new com.shrikanthravi.customnavigationdrawer2.data.MenuItem(cat, R.drawable.food));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
       //setting the MenuItem list to the menu
        sNavigationDrawer.setMenuItemList(menuItems);

       //setting the behavior for clicking on a menuitem
        sNavigationDrawer.setOnMenuItemClickListener(new SNavigationDrawer.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClicked(int position) {
                //onMenuItemClicked accepts an int for the position in the list of the item clicked
                System.out.println("Position " + position);
                //using the position we can search the list and get the menuitem that was clicked, and its title/name
                String title = sNavigationDrawer.getMenuItemList().get(position).getTitle();
                //we then use that title and add it to the url for the api request and call our NetworkTask class, passing the url into it
               catText.setText(title.toUpperCase());
                new NetworkTask().execute("http://www.pornhub.com/webmasters/search?id=44bc40f3bc04f65b7a35&thumbsize=medium&category=" + title);

            }

        });
    }

    /**
     * This method is used to read the JSON file categories.json into a String
     * @return
     */
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = context.getAssets().open("categories.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    /**
     * Behavior for when a search is submitted
     * @param text
     */
    @Override
    public void onSearchConfirmed(CharSequence text) {
      //hides keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
      catText.setText(text.toString().toUpperCase());
        new NetworkTask().execute("http://www.pornhub.com/webmasters/search?id=44bc40f3bc04f65b7a35&search="+text+"&thumbsize=medium");



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

/**
 * Behavior for when a button on the search bar is clicked
 * currently used to trigger the navigation menu opening and closing
 */
    @Override
    public void onButtonClicked(int buttonCode) {
        Log.e("Button", "" + buttonCode);
        switch (buttonCode) {
            case 2:
                if (!sNavigationDrawer.isDrawerOpen()) {
                    sNavigationDrawer.openDrawer();
                } else {
                    sNavigationDrawer.closeDrawer();
                }
                break;
        }

    }

    /**
     * Method for making http request to API
     * Accepts a String url, makes an http request using okHttp and returns a JSON response formatted as a string
     * @param url
     * @return
     * @throws IOException
     */
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
        Intent intent = new Intent(getApplicationContext(), ImagePage.class);
        String url = "";
        switch (v.getId()) {
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }

    /**
     * Behavior for when an item in the recyclerview is clicked
     * creates intent, adds the videoID of the object as an extra in the intent
     * starts the ImagePage Activity to show the video
     * @param item
     */
    @Override
    public void onListFragmentInteraction(VideoClass item) {

        Intent intent = new Intent(getApplicationContext(), ImagePage.class);
        String url = "";
        url = item.videoID;
        intent.putExtra("url", url);
        startActivity(intent);
    }

    /**
     * NetworkTask Handles sending the http request on a background thread
     * Accepts a String parameter which is the API request url
     * Shows a progressbar while the background work is happening and then hides it after
     * Gets result from api, parses it as JSON objects, creates VideoClass objects and then populates the recyclerview
     *
     *
     */
    public class NetworkTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            //showing progressbar
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
           //calling getPorn method to make request
            try {
                String result = getPorn(strings[0]);
                return result;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
          //parsing JSON
            try {

                JSONObject object = new JSONObject(s);
                JSONArray array = object.getJSONArray("videos");

                ArrayList<VideoClass> videoClasses = new ArrayList<>();
                //calling "fromJson" method from the VideoClass Class to create a List of VideoClass objects
                videoClasses = VideoClass.fromJson(array);
                //setting adapter for recyclerview
                MyVidAdapter adapter = new MyVidAdapter(videoClasses, MainActivity.this, context);
                recyclerView.setAdapter(adapter);


            } catch (JSONException e){
                e.printStackTrace();
            }
            catch (NullPointerException n){
                n.printStackTrace();
            }
progressBar.setVisibility(View.INVISIBLE);
            super.onPostExecute(s);
        }
    }

}
