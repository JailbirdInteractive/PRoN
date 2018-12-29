package net.rondrae.giggity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.rondrae.giggity.CatItemFragment.OnListFragmentInteractionListener;
import net.rondrae.giggity.dummy.DummyContent.DummyItem;
import net.rondrae.giggity.models.CatItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyCatItemRecyclerViewAdapter extends RecyclerView.Adapter<MyCatItemRecyclerViewAdapter.ViewHolder> {

    private final List<CatItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context mContext;

    public MyCatItemRecyclerViewAdapter(List<CatItem> items, OnListFragmentInteractionListener listener, Context context) {
        mValues = items;
        mListener = listener;
        mContext=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getCategory());
        //holder.mContentView.setText(mValues.get(position).content);
        if(!mValues.get(position).getCatUrl().isEmpty())
        Picasso.get().load(mValues.get(position).getCatUrl()).placeholder(R.drawable.food).into(holder.mContentView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    didTapButton(v);
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }
    public void didTapButton(View view) {
        final Animation myAnim = AnimationUtils.loadAnimation(mContext, R.anim.bounce);
        BounceInterpolator interpolator = new BounceInterpolator(0.1, 20);
        myAnim.setInterpolator(interpolator);

        view.startAnimation(myAnim);
    }
    class BounceInterpolator implements android.view.animation.Interpolator {
        double mAmplitude = 1;
        double mFrequency = 10;

        BounceInterpolator(double amplitude, double frequency) {
            mAmplitude = amplitude;
            mFrequency = frequency;
        }

        public float getInterpolation(float time) {
            return (float) (-1 * Math.pow(Math.E, -time/ mAmplitude) *
                    Math.cos(mFrequency * time) + 1);
        }
    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final ImageView mContentView;
        public CatItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_text);
            mContentView = (ImageView) view.findViewById(R.id.item_image);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdView.getText() + "'";
        }
    }
}
