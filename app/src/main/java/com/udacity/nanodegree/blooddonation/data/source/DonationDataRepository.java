package com.udacity.nanodegree.blooddonation.data.source;

import com.udacity.nanodegree.blooddonation.data.model.ReceiverDonorRequestType;
import com.udacity.nanodegree.blooddonation.data.model.User;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class DonationDataRepository implements DonationDataSource {

    private static DonationDataRepository INSTANCE = null;
    private final DonationDataSource mLocalDataSource;
    private final DonationDataSource mRemoteDataSource;

    private DonationDataRepository(DonationDataSource localDataSource,
                                   DonationDataSource remoteDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    public static DonationDataRepository getInstance(DonationDataSource localDataSource,
                                                     DonationDataSource remoteDataSource) {
        if (INSTANCE == null) {
            synchronized (DonationDataRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DonationDataRepository(localDataSource, remoteDataSource);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void saveNewUser(String userId, User user) {
        mRemoteDataSource.saveNewUser(userId, user);
    }

    @Override
    public void saveReceiverDetails(String userId, ReceiverDonorRequestType receiverDonorRequestType) {
        mRemoteDataSource.saveReceiverDetails(userId, receiverDonorRequestType);
    }

    @Override
    public void saveDonorDetails(String userId, ReceiverDonorRequestType receiverDonorRequestType) {
        mRemoteDataSource.saveDonorDetails(userId, receiverDonorRequestType);
    }
}
