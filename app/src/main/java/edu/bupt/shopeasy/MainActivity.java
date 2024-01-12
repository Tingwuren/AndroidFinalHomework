package edu.bupt.shopeasy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import java.util.ArrayList;
import edu.bupt.shopeasy.database.ProductDatabaseHelper;
import edu.bupt.shopeasy.fragment.BaseFragment;
import edu.bupt.shopeasy.fragment.CartFragment;
import edu.bupt.shopeasy.fragment.HomeFragment;
import edu.bupt.shopeasy.fragment.TypeFragment;
import edu.bupt.shopeasy.fragment.UserFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private int fragmentIndex;
    private BaseFragment tempFragment;
    private RadioGroup bottom_fragment;

    private ProductDatabaseHelper productDatabaseHelper;


    ArrayList<BaseFragment> fragmentArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(TAG, "OnCreate");
        bottom_fragment = findViewById(R.id.fragment_group);
        bottom_fragment.check(R.id.home_bottom);

        fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new HomeFragment());
        fragmentArrayList.add(new TypeFragment());
        fragmentArrayList.add(new CartFragment());
        fragmentArrayList.add(new UserFragment());

        Listener();

        BaseFragment homeFragment = fragmentArrayList.get(0);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameLayout, homeFragment).commit();
        tempFragment = homeFragment;
    }
    private void Listener() {
        bottom_fragment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Log.i(TAG, "id = " + i);
                if (i == R.id.home_bottom) {
                    fragmentIndex = 0;
                } else if (i == R.id.type_bottom) {
                    fragmentIndex = 1;
                } else if (i == R.id.cart_bottom) {
                    fragmentIndex = 2;
                } else if (i == R.id.user_bottom) {
                    fragmentIndex = 3;
                }
                BaseFragment toFragment = getFragment(fragmentIndex);
                switchFragment(tempFragment, toFragment);
            }
        });
    }
    private BaseFragment getFragment(int fragmentIndex) {
        if (fragmentArrayList != null && fragmentArrayList.size() > 0) {
            BaseFragment fragment = fragmentArrayList.get(fragmentIndex);
            return fragment;
        }
        return null;
    }
    private void switchFragment(BaseFragment fromFragment, BaseFragment toFragment) {
        if(tempFragment != toFragment) {
            tempFragment = toFragment;
            if(toFragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if(!toFragment.isAdded()) {
                    if(fromFragment != null) {
                        fromFragment.saveData();
                        transaction.hide(fromFragment);
                    }
                    transaction.add(R.id.frameLayout, toFragment).commit();
                } else {
                    if(fromFragment != null) {
                        fromFragment.saveData();
                        transaction.hide(fromFragment);
                    }
                    toFragment.refreshData();
                    transaction.show(toFragment).commit();
                }
            }
        }
    }
}