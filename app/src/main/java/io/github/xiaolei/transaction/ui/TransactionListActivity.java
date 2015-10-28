package io.github.xiaolei.transaction.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Date;

import io.github.xiaolei.enterpriselibrary.utility.DateTimeUtils;
import io.github.xiaolei.transaction.R;
import io.github.xiaolei.transaction.adapter.TransactionListPagerAdapter;
import io.github.xiaolei.transaction.adapter.TransactionNavigatorAdapter;
import io.github.xiaolei.transaction.viewmodel.TransactionFilterType;
import io.github.xiaolei.transaction.viewmodel.TransactionNavigatorItem;

public class TransactionListActivity extends BaseActivity {
    private ViewHolder mViewHolder;
    private Date mTransactionDate = DateTimeUtils.getStartTimeOfDate(new Date());
    private TransactionNavigatorAdapter mSpinnerAdapter;
    private TransactionListPagerAdapter mTransactionListPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);

        initialize();
    }

    private void initialize() {
        mViewHolder = new ViewHolder(this);
        setupToolbar(false);
        setTitle("");
        mViewHolder.spinnerTransactionNavigator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                TransactionNavigatorItem selectedItem = (TransactionNavigatorItem) adapterView.getItemAtPosition(position);
                mSpinnerAdapter.setSelectedItem(selectedItem.transactionFilterType);
                query(selectedItem.transactionFilterType, mTransactionDate);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        setupTransactionNavigator();

        mTransactionListPagerAdapter = new TransactionListPagerAdapter(getSupportFragmentManager(), mTransactionDate, mTransactionDate);
        mViewHolder.viewPagerFragmentList.setAdapter(mTransactionListPagerAdapter);
    }

    private void query(TransactionFilterType transactionFilterType, Date date) {
        mTransactionListPagerAdapter.changeDateRange(date, transactionFilterType);

        if (mTransactionListPagerAdapter.getCount() > 0) {
            mViewHolder.viewPagerFragmentList.setCurrentItem(Math.max(0, mTransactionListPagerAdapter.getCount() - 1), false);
        }
    }

    private void setupTransactionNavigator() {
        ArrayList<TransactionNavigatorItem> items = new ArrayList<TransactionNavigatorItem>();
        items.add(new TransactionNavigatorItem(TransactionFilterType.TODAY,
                R.drawable.ic_calendar_black_24dp, R.string.transaction_navigator_by_day));
        items.add(new TransactionNavigatorItem(TransactionFilterType.THIS_WEEK,
                R.drawable.ic_calendar_black_24dp, R.string.transaction_navigator_by_week));
        items.add(new TransactionNavigatorItem(TransactionFilterType.THIS_MONTH,
                R.drawable.ic_calendar_black_24dp, R.string.transaction_navigator_by_month));
        items.add(new TransactionNavigatorItem(TransactionFilterType.THIS_YEAR,
                R.drawable.ic_calendar_black_24dp, R.string.transaction_navigator_by_year));

        mSpinnerAdapter = new TransactionNavigatorAdapter(this, items, mTransactionDate);
        mViewHolder.spinnerTransactionNavigator.setAdapter(mSpinnerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_transaction_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private class ViewHolder {
        public Spinner spinnerTransactionNavigator;
        public ViewPager viewPagerFragmentList;

        public ViewHolder(Activity activity) {
            spinnerTransactionNavigator = (Spinner) activity.findViewById(R.id.spinnerTransactionNavigator);
            viewPagerFragmentList = (ViewPager) activity.findViewById(R.id.viewPagerFragmentList);
        }
    }
}
