package net.shreygupta.medispencer.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.shreygupta.medispencer.R;
import net.shreygupta.medispencer.fragments.PatientPrescriptions;
import net.shreygupta.medispencer.fragments.PatientProfile;

public class PatientAdapter extends FragmentPagerAdapter{

    private Context mContext;

    public PatientAdapter(Context context,FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new PatientProfile();
        }
        else {
            return new PatientPrescriptions();
        }
    }

    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.profile);
        }
        else {
            return mContext.getString(R.string.patient_prescription);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}