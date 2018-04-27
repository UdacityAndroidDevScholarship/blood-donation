package com.udacity.nanodegree.blooddonation.data.source;

import com.udacity.nanodegree.blooddonation.data.model.Receiver;
import com.udacity.nanodegree.blooddonation.data.model.User;

/**
 * Created by riteshksingh on Apr, 2018
 */
public interface DonationDataSource {
  void writeNewUser(String userId, User user);
  void writeReceiverDetails(String userId, Receiver receiver);
}
