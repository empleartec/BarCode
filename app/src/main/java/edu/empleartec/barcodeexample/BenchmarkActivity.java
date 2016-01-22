package edu.empleartec.barcodeexample;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

public final class BenchmarkActivity extends Activity {

    private View runBenchmarkButton;
    private TextView textView;
    private AsyncTask<Object,Object,String> benchmarkTask;

    private final View.OnClickListener runBenchmark = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (benchmarkTask == null) {
                String path = Environment.getExternalStorageDirectory().getPath() + "/zxingbenchmark";
                benchmarkTask = new BenchmarkAsyncTask(BenchmarkActivity.this, path);
                runBenchmarkButton.setEnabled(false);
                textView.setText(R.string.benchmark_running);
                benchmarkTask.execute(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        }
    };

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.benchmark);
        runBenchmarkButton = findViewById(R.id.benchmark_run);
        runBenchmarkButton.setOnClickListener(runBenchmark);
        textView = (TextView) findViewById(R.id.benchmark_help);
        benchmarkTask = null;
    }

    void onBenchmarkDone(String message) {
        textView.setText(message + "\n\n" + getString(R.string.benchmark_help));
        runBenchmarkButton.setEnabled(true);
        benchmarkTask = null;
    }

}