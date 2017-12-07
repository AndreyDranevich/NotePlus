package com.example.andrew.criminalintent;


import android.support.v4.app.Fragment;
import java.util.UUID;

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
//        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
//        return CrimeFragment.newInstance(crimeId);
    }
}
