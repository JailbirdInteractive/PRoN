package net.rondrae.giggity;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.wang.avi.AVLoadingIndicatorView;

import net.rondrae.giggity.models.CatItem;

/**
 * TODO: Add Proper images and picking functionality
 *
 *
 */
public class PickerActivity extends AppCompatActivity implements CatItemFragment.OnListFragmentInteractionListener,MaterialSearchBar.OnSearchActionListener {
Fragment f1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_picker);
        MaterialSearchBar searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        searchBar.setHint("Enter Search you perv");
        searchBar.setSpeechMode(true);
        searchBar.inflateMenu(R.menu.main_menu);
        //searchBar.getMenu().setOnMenuItemClickListener(this);
        searchBar.setCardViewElevation(5);
        //enable searchbar callbacks
        searchBar.setOnSearchActionListener(this);
        super.onCreate(savedInstanceState);

        f1 = new CatItemFragment();
        android.support.v4.app.FragmentManager fM = getSupportFragmentManager();

        fM.beginTransaction().replace(R.id.container, f1)
                .commit();
        AVLoadingIndicatorView view = (AVLoadingIndicatorView) findViewById(R.id.avi);
        view.smoothToHide();

    }

    @Override
    public void onListFragmentInteraction(CatItem item) {
        Toast.makeText(getApplicationContext(),item.getCategory(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }
}
