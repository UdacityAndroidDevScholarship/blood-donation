package com.udacity.nanodegree.blooddonation.util.location;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

public class FetchPlaceTask extends AsyncTask<Object, Void, String> {


    private boolean mIsAddressRequired;
    private AddressParseListener mListener;

    public FetchPlaceTask(Context context, Location location, boolean isAddressRequired, AddressParseListener listener) {
        this.mIsAddressRequired = isAddressRequired;
        this.mListener = listener;


        execute(context, location);
    }

    @Override
    protected String doInBackground(Object... objects) {
        Location location = (Location) objects[1];
        try {
            List<Address> addresses = new Geocoder((Context) objects[0]).getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.size() > 0 && addresses.get(0) != null) {
                Address address = addresses.get(0);
                if (mIsAddressRequired)
                    return getCompleteAddress(address);
                else
                    return getLocality(address);

            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s == null || s.isEmpty())
            s = "";
        mListener.onAddressParsed(s);
    }

    /**
     * Helper method to get complete address from the received location.
     *
     * @param address
     * @return
     */
    private String getCompleteAddress(Address address) {
        String addressString = address.getAddressLine(0);

        if (addressString != null && !addressString.isEmpty())
            return addressString;
        else return getLocality(address);
    }

    /**
     * Method to get locality of Address. (City level address.)
     *
     * @param address
     * @return
     */
    private String getLocality(Address address) {
        String completeAddress = "";
        if (address.getSubLocality() != null)
            completeAddress += address.getSubLocality();
        if (address.getLocality() != null)
            completeAddress += ", " + address.getLocality();
        return completeAddress;
    }

    public interface AddressParseListener {
        void onAddressParsed(String addressString);
    }

}
