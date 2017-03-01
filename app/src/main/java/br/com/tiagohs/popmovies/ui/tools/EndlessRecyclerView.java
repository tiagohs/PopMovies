package br.com.tiagohs.popmovies.ui.tools;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by HP-HP on 22-04-2016.
 */
public abstract class EndlessRecyclerView extends RecyclerView.OnScrollListener {

    public static String TAG = EndlessRecyclerView.class.getSimpleName();

    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 5; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private int current_page = 1;

    private RecyclerView.LayoutManager mLayoutManager;

    public EndlessRecyclerView(RecyclerView.LayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    public EndlessRecyclerView(GridLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
        // Increase visible threshold based on number of columns
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLayoutManager.getItemCount();

        // Check the layout manager type in order to determine the last visible position
        if (mLayoutManager instanceof LinearLayoutManager) {

            firstVisibleItem = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();

        } else if (mLayoutManager instanceof GridLayoutManager) {

            firstVisibleItem = ((GridLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
        }


        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached

            // Do something
            current_page++;

            onLoadMore(current_page);

            loading = true;
        }
    }

    public abstract void onLoadMore(int current_page);

}
