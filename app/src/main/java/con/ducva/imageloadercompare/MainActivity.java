package con.ducva.imageloadercompare;

import android.database.Cursor;
import android.net.Uri;
import android.os.Debug;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.common.logging.FLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import con.ducva.imageloadercompare.adapter.BaseAdapter;
import con.ducva.imageloadercompare.view.PreCachingLayoutManager;

public class MainActivity extends AppCompatActivity {

    private static final int DEFAULT_MESSAGE_SIZE = 1024;

    @BindView(R.id.loader_select)
    Spinner spLibrary;

    @BindView(R.id.source_select)
    Spinner spSource;

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @BindView(R.id.stats_display)
    TextView tvStats;

    private List<String> urls;
    private BaseAdapter baseAdapter;
    private int spanCount = 3;
    private int currentLib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        spLibrary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                handlerLibraryChange(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spSource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                handlerSourceChange(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        recyclerView.setLayoutManager(new PreCachingLayoutManager(this, spanCount));
    }

    private void initData() {
        urls = new ArrayList<>();
    }

    private void loadLocalUrls() {
        Uri externalContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.Media._ID};
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(externalContentUri, projection, null, null, null);
            urls.clear();
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            String imageId;
            Uri imageUri;
            while (cursor.moveToNext()) {
                imageId = cursor.getString(columnIndex);
                imageUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageId);
                urls.add(imageUri.toString());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            baseAdapter.addAll(urls);
        }
    }

    private void loadWebUrl() {
        //TODO: later
    }

    private void handlerSourceChange(int position) {
        //TODO: later
    }

    private void handlerLibraryChange(int position) {
        currentLib = position;
        if (position == Constant.TYPE.Glide.ordinal()) {
            baseAdapter = new BaseAdapter(this, Constant.TYPE.Glide, spanCount);
        } else if (position == Constant.TYPE.Fresco.ordinal()) {
            baseAdapter = new BaseAdapter(this, Constant.TYPE.Fresco, spanCount);
        } else if (position == Constant.TYPE.Picasso.ordinal()) {
            baseAdapter = new BaseAdapter(this, Constant.TYPE.Picasso, spanCount);
        } else if (position == Constant.TYPE.ImageLoader.ordinal()) {
            baseAdapter = new BaseAdapter(this, Constant.TYPE.ImageLoader, spanCount);
        }

        if (urls.size() > 0) {
            baseAdapter.addAll(urls);
        } else {
            loadLocalUrls();
        }
        recyclerView.setAdapter(baseAdapter);
    }

    private void updateStats() {
        final Runtime runtime = Runtime.getRuntime();
        final long heapMemory = runtime.totalMemory() - runtime.freeMemory();
        final StringBuilder sb = new StringBuilder(DEFAULT_MESSAGE_SIZE);
        // When changing format of output below, make sure to sync "run_comparison.py" as well
        sb.append("Heap: ");
        Utils.appendSize(sb, heapMemory);
        sb.append(" Java ");
        Utils.appendSize(sb, Debug.getNativeHeapSize());
        sb.append(" native\n");
        final String message = sb.toString();
        tvStats.setText(message);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_col_1) {
            spanCount = 1;
        } else if (id == R.id.menu_col_2) {
            spanCount = 2;
        } else if (id == R.id.menu_col_3) {
            spanCount = 3;
        } else if (id == R.id.menu_col_4) {
            spanCount = 4;
        }
        if (spanCount > 1) {
            recyclerView.setLayoutManager(new PreCachingLayoutManager(this, spanCount));
        } else {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setInitialPrefetchItemCount(10);
            recyclerView.setLayoutManager(linearLayoutManager);
        }
        handlerLibraryChange(currentLib);
        return super.onOptionsItemSelected(item);
    }
}
