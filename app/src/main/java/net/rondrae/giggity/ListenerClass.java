package net.rondrae.giggity;

import net.rondrae.giggity.models.VideoClass;

/**
 * This class holds interfaces to handle the clicking of items in recyclerviews
 *
 */
public class ListenerClass {

public ListenerClass(){

}
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(VideoClass item);
    }
}
