package mozilla.org.leavemanagement.helper;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;

import mozilla.org.leavemanagement.exception.NoInternetException;
import mozilla.org.leavemanagement.exception.ServiceUnreachableException;


public class AsyncTaskHelper extends AsyncTask<String, Void, Integer> {

    private OnBackgroundTaskListener mOnBackgroundTaskListener;
    private Activity mActivity;
    // private ProgressDialogHelper progressDialogHelper;

    public AsyncTaskHelper(Activity mActivity) {
        this.mActivity = mActivity;
    }

    /**
     * set loading text
     *
     * @param text dialog label
     */
    public void setProgressDialogText(String text) {

        /*if (progressDialogHelper != null) {
            //  progressDialogHelper.setText(text);
        }*/

    }

    /**
     * @param listener
     */
    public void setOnBackgroundListener(OnBackgroundTaskListener listener) {
        this.mOnBackgroundTaskListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //  progressDialogHelper.showDialog();
        mOnBackgroundTaskListener.onPreExecute();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        // progressDialogHelper.closeDialog();

        if (integer == mozilla.org.leavemanagement.util.ServiceStatus.OK.getStatus()) {

            mOnBackgroundTaskListener.onPostExecute();

        } else if (integer == mozilla.org.leavemanagement.util.ServiceStatus.JSON_EXCEPTION.getStatus()) {
            
        } else if (integer == mozilla.org.leavemanagement.util.ServiceStatus.NULL_EX.getStatus()) {

        } else if (integer == mozilla.org.leavemanagement.util.ServiceStatus.SERVICE_DOWN.getStatus()) {

        } else if (integer == mozilla.org.leavemanagement.util.ServiceStatus.NO_INTERNET.getStatus()) {

        } else {

        }


    }

    @Override
    protected Integer doInBackground(String... strings) {
        try {

            mOnBackgroundTaskListener.onBackground(strings);

            return mozilla.org.leavemanagement.util.ServiceStatus.OK.getStatus();
        } catch (JSONException jsonEx) {
            Log.e("JSON_EXCEPTION", jsonEx.toString());
            return mozilla.org.leavemanagement.util.ServiceStatus.JSON_EXCEPTION.getStatus();
        } catch (ServiceUnreachableException noServiceEx) {
            Log.e("ServiceException", noServiceEx.toString());
            return mozilla.org.leavemanagement.util.ServiceStatus.SERVICE_DOWN.getStatus();
        } catch (NoInternetException nulEx) {
            Log.e("NoInternetException", nulEx.toString());
            return mozilla.org.leavemanagement.util.ServiceStatus.NO_INTERNET.getStatus();
        } catch (IOException ioEx) {
            Log.e("IOException", ioEx.toString());
            return mozilla.org.leavemanagement.util.ServiceStatus.IO_EXCEPTION.getStatus();
        }
    }

    public interface OnBackgroundTaskListener {

        void onBackground(String... strings) throws IOException, JSONException;

        void onPreExecute();

        void onPostExecute();
    }
}
