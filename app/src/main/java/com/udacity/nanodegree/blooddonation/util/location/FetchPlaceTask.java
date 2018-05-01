package com.udacity.nanodegree.blooddonation.util.location;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;

import com.udacity.nanodegree.blooddonation.util.Util;

import java.io.IOException;
import java.util.List;

public class FetchPlaceTask extends AsyncTask<Void, Void, String> {


    private Geocoder mGeocoder;
    private Location mLocation;
    private boolean mIsAddressRequired;
    private AddressParseListener mListener;

    public FetchPlaceTask(Geocoder geocoder, Location location, boolean isAddressRequired, AddressParseListener listener) {
        mGeocoder = geocoder;
        mLocation = location;
        this.mIsAddressRequired = isAddressRequired;
        this.mListener = listener;


        execute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            List<Address> addresses = mGeocoder.getFromLocation(mLocation.getLatitude(), mLocation.getLongitude(), 1);
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
            s = Util.convertLatLongToAddress(mLocation.getLatitude(), mLocation.getLongitude());
        if (mListener != null)
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
