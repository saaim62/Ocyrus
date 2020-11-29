package com.app.ocyrus.utills;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ScrollLoadRecyclerView extends RecyclerView {
    /**
     * The M loading.
     */
    private boolean mLoading = false;
    /**
     * The M loading enabled.
     */
    private boolean mLoadingEnabled = true;

    /**
     * The M on load listener.
     */
    @Nullable
    private OnLoadListener mOnLoadListener;

    /**
     * The interface On load listener.
     */
    public interface OnLoadListener {
        /**
         * On load.
         */
        void onLoad();
    }

    /**
     * Instantiates a new Scroll load recycler view.
     *
     * @param context the context
     */
    public ScrollLoadRecyclerView(Context context) {
        this(context, null);
    }

    /**
     * Instantiates a new Scroll load recycler view.
     *
     * @param context the context
     * @param attrs   the file_paths
     */
    public ScrollLoadRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Instantiates a new Scroll load recycler view.
     *
     * @param context  the context
     * @param attrs    the file_paths
     * @param defStyle the def style
     */
    public ScrollLoadRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Sets on load listener.
     *
     * @param onLoadListener the on load listener
     */
    public void setOnLoadListener(@Nullable OnLoadListener onLoadListener) {
        mOnLoadListener = onLoadListener;
    }

    /**
     * On scrolled.
     *
     * @param dx the dx
     * @param dy the dy
     */
    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);

        if (mLoadingEnabled && !mLoading) {
            LayoutManager layoutManager = getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                if (gridLayoutManager.findLastVisibleItemPosition() / gridLayoutManager.getSpanCount()
                        == (gridLayoutManager.getItemCount() - 1) / gridLayoutManager.getSpanCount()) {
                    setLoading(true, true);
                }
            } else {
                throw new IllegalStateException("Use GridLayoutManager");
            }
        }
    }

    /**
     * Sets loading.
     *
     * @param loading the loading
     */
    public void setLoading(boolean loading) {
        setLoading(loading, false);
    }

    /**
     * Sets loading enabled.
     *
     * @param loadingEnabled the loading enabled
     */
    public void setLoadingEnabled(boolean loadingEnabled) {
        if (mLoadingEnabled != loadingEnabled) {
            mLoadingEnabled = loadingEnabled;

            Adapter adapter = (Adapter) getAdapter();
            adapter.progressVisibility = mLoadingEnabled ? VISIBLE : GONE;

            if (adapter.progressVisibility == VISIBLE) {
                getAdapter().notifyItemInserted(adapter.getItemCount());
            } else {
                getAdapter().notifyItemRemoved(adapter.getItemCount());
            }
        }
    }

    /**
     * Sets loading.
     *
     * @param loading the loading
     * @param notify  the notify
     */
    public void setLoading(boolean loading, boolean notify) {
        mLoading = loading;

        if (notify && mOnLoadListener != null) {
            mOnLoadListener.onLoad();
        }
    }

    /**
     * The type Adapter.
     *
     * @param <VH> the type parameter
     */
    public abstract static class Adapter<VH extends ViewHolder> extends RecyclerView.Adapter<VH> {
        /**
         * The constant VIEW_TYPE_PROGRESS.
         */
        private static final int VIEW_TYPE_PROGRESS = ProgressViewHolder.class.hashCode();

        /**
         * The Progress visibility.
         */
        private int progressVisibility = VISIBLE;

        /**
         * On create view holder vh.
         *
         * @param parent   the parent
         * @param viewType the view type
         * @return the vh
         */
        @Override
        public final VH onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_PROGRESS) {
                ProgressBar progressBar = new ProgressBar(parent.getContext(), null, android.R.attr.progressBarStyleInverse);
                progressBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                float paddingInDp = 16;
                int paddingInPx = (int) (paddingInDp * parent.getContext().getResources().getDisplayMetrics().density);
                progressBar.setPadding(0, paddingInPx, 0, paddingInPx);

                //noinspection unchecked
                return (VH) new ProgressViewHolder(progressBar);
            } else {
                return onCreateDataViewHolder(parent, viewType);
            }
        }

        /**
         * On bind view holder.
         *
         * @param holder   the holder
         * @param position the position
         */
        @Override
        public final void onBindViewHolder(VH holder, int position) {
            if (!(holder instanceof ProgressViewHolder)) {
                onBindDataViewHolder(holder, position);
            }
        }

        /**
         * Gets item view type.
         *
         * @param position the position
         * @return the item view type
         */
        @Override
        public final int getItemViewType(int position) {
            if (position < getDataItemCount()) {
                return super.getItemViewType(position);
            } else {
                return VIEW_TYPE_PROGRESS;
            }
        }

        /**
         * Gets item count.
         *
         * @return the item count
         */
        @Override
        public final int getItemCount() {
            if (getDataItemCount() == 0) {
                return 0;
            } else {
                if (progressVisibility == VISIBLE) {
                    return getDataItemCount() + 1;
                } else {
                    return getDataItemCount();
                }
            }
        }

        /**
         * On create data view holder vh.
         *
         * @param parent   the parent
         * @param viewType the view type
         * @return the vh
         */
        public abstract VH onCreateDataViewHolder(ViewGroup parent, int viewType);

        /**
         * On bind data view holder.
         *
         * @param holder   the holder
         * @param position the position
         */
        public abstract void onBindDataViewHolder(VH holder, int position);

        /**
         * Gets data item count.
         *
         * @return the data item count
         */
        public abstract int getDataItemCount();

        /**
         * Gets data item view type.
         *
         * @return the data item view type
         */
        public abstract int getDataItemViewType();
    }

    /**
     * The type Progress view holder.
     */
    private static class ProgressViewHolder extends ViewHolder {

        /**
         * Instantiates a new Progress view holder.
         *
         * @param itemView the item view
         */
        ProgressViewHolder(View itemView) {
            super(itemView);
        }
    }
}
