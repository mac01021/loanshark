package quartyard.loanshark;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
//import android.widget.TextView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.app.FragmentManager;






public class MainActivity extends FragmentActivity
{
   // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.
    DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mDemoCollectionPagerAdapter =
                new DemoCollectionPagerAdapter(
                        getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
    }
}

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
    public DemoCollectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new PageFragment();
        return fragment;
    }

    @Override
    public int getCount() {
        return 100;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }
}

//// Instances of this class are fragments representing a single
//// object in our collection.
//class PageFragment extends Fragment {
//    public static final String ARG_OBJECT = "object";
//
//    @Override
//    public View onCreateView(LayoutInflater inflater,
//            ViewGroup container, Bundle savedInstanceState) {
//        // The last two arguments ensure LayoutParams are inflated
//        // properly.
//        View rootView = inflater.inflate(
//                R.layout.page, container, false);
//        Bundle args = getArguments();
//        //((TextView) rootView.findViewById(mac.hello.R.id.principal_textbox)).setText(
//        //        Integer.toString(args.getInt(ARG_OBJECT)));
//        return rootView;
//    }
//}
