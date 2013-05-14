package br.com.androidzin.pontopro.database;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import br.com.androidzin.pontopro.model.Checkin;

public class CheckinLoader extends AsyncTaskLoader<List<Checkin>> {

	// We hold a reference to the Loader�s data here.
	private List<Checkin> mData;
	public static final String ACTION_SELECTOR_CHANGED = "pontopro.date.selector";
	private static final String TAG = null;
	private NavigationSelectorListener mObserver;
	

	public CheckinLoader(Context context) {
		// Loaders may be used across multiple Activitys (assuming they aren't
		// bound to the LoaderManager), so NEVER hold a reference to the context
		// directly. Doing so will cause you to leak an entire Activity's
		// context.
		// The superclass constructor will store a reference to the Application
		// Context instead, and can be retrieved with a call to getContext().
		super(context);
	}

	public synchronized void setIntervalToGet(String stringExtra) {
		// TODO Auto-generated method stub
		// Handle the data do load
	}
	
	public synchronized String getIntervalToGet(){
		return "";
	}

	@Override
	public List<Checkin> loadInBackground() {
		// TODO Auto-generated method stub
		// This method is called on a background thread and should generate a
		// new set of data to be delivered back to the client.
		Log.i(TAG, "+++ loadInBackground() called! +++");
		List<Checkin> data = new ArrayList<Checkin>();

		// TODO: Perform the query here and add the results to 'data'.
		DatabaseManager databaseManager = new DatabaseManager(getContext());
		return data;
	}

	@Override
	protected void onStartLoading() {
		// TODO Auto-generated method stub

		if (mData != null) {
			// Deliver any previously loaded data immediately.
			Log.i(TAG, "+++ Delivering previously loaded data to the client...");
			deliverResult(mData);
		}

		// register listening for updates
		if (mObserver != null) {
			mObserver = new NavigationSelectorListener(this);
		}
		if (takeContentChanged()) {
			// When the observer detects a new installed application, it will
			// call
			// onContentChanged() on the Loader, which will cause the next call
			// to
			// takeContentChanged() to return true. If this is ever the case (or
			// if
			// the current data is null), we force a new load.
			Log.i(TAG, "+++ A content change has been detected... so force load! +++");
			forceLoad();
		} else if (mData == null) {
			// If the current data is null... then we should make it non-null!
			// :)
			Log.i(TAG, "+++ The current data is data is null... so force load! +++");
			forceLoad();
		}

		super.onStartLoading();
	}

	@Override
	public void deliverResult(List<Checkin> data) {
		if (isReset()) {
			// The Loader has been reset; ignore the result and invalidate the
			// data.
			releaseResources(data);
			return;
		}

		// Hold a reference to the old data so it doesn't get garbage collected.
		// We must protect it until the new data has been delivered.
		List<Checkin> oldData = mData;
		mData = data;

		if (isStarted()) {
			// If the Loader is in a started state, deliver the results to the
			// client. The superclass method does this for us.
			super.deliverResult(data);
		}

		// Invalidate the old data as we don't need it any more.
		if (oldData != null && oldData != data) {
			releaseResources(oldData);
		}
	}

	@Override
	protected void onStopLoading() {
		// The Loader is in a stopped state, so we should attempt to cancel the
		// current load (if there is one).
		cancelLoad();

		// Note that we leave the observer as is. Loaders in a stopped state
		// should still monitor the data source for changes so that the Loader
		// will know to force a new load if it is ever started again.
	}

	@Override
	protected void onReset() {
		// Ensure the loader has been stopped.
		onStopLoading();

		// At this point we can release the resources associated with 'mData'.
		if (mData != null) {
			releaseResources(mData);
			mData = null;
		}

		// The Loader is being reset, so we should stop monitoring for changes.
		if (mObserver != null) {
			// TODO: unregister the observer
			getContext().unregisterReceiver(mObserver);
			mObserver = null;
		}

	}

	@Override
	public void onCanceled(List<Checkin> data) {
		// Attempt to cancel the current asynchronous load.
		super.onCanceled(data);

		// The load has been canceled, so we should release the resources
		// associated with 'data'.
		releaseResources(data);
	}

	private void releaseResources(List<Checkin> data) {
		// For a simple List, there is nothing to do. For something like a
		// Cursor, we
		// would close it in this method. All resources associated with the
		// Loader
		// should be released here.
	}

}
