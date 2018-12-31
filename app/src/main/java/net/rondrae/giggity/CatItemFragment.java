package net.rondrae.giggity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import net.rondrae.giggity.dummy.DummyContent;
import net.rondrae.giggity.dummy.DummyContent.DummyItem;
import net.rondrae.giggity.models.CatItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A fragment representing a list of Video Categories.
 * <p/>
 * The Categories are acquired from The Redtube API via NetworkTask Class
 * Catgories are then made into CatItem objects and placed in recyclerview
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class CatItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 3;
    private OnListFragmentInteractionListener mListener;
ArrayList<CatItem>catItems;
    RecyclerView recyclerView;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CatItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CatItemFragment newInstance(int columnCount) {
        CatItemFragment fragment = new CatItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        catItems=new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catitem_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
           recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            new NetworkTask().execute(getString(R.string.categories_url));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(CatItem item);
    }

    /**
     * Accepts String ArrayList from Network Task Class, creates List of CatItem objects and sets Recyclerview Adapter
     * @param strings
     */
    private void setAdapter(List<String> strings){
        ArrayList<CatItem>items=new ArrayList<>();
for(String cat:strings){
    CatItem catItem=new CatItem(cat,"");
    items.add(catItem);
}

        recyclerView.setAdapter(new MyCatItemRecyclerViewAdapter(items, mListener,getActivity()));


    }

    /**
     * Sends Request to url
     * @param url
     * @return JSON Response as String
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

    /**
     * Network Task handles async sending and receiving of Url requests to Redtube Api
     * Gets categories, parses the JSON, creates and populates String array with category names and then calls setAdapter method
     *
     */
    private class NetworkTask extends AsyncTask<String, Void, String> {

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
                JSONArray array=object.getJSONArray("categories");
                List<String>strings=new ArrayList<String>(array.length());
                for(int i=0;i<array.length();i++){
                    JSONObject jsonObject=array.getJSONObject(i);
                    strings.add(jsonObject.getString("category"));
                }
                setAdapter(strings);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            super.onPostExecute(s);
        }
    }
}
