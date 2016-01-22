package edu.empleartec.barcodeexample;

import com.google.zxing.BarcodeFormat;

final class BenchmarkItem {

    private final String path;
    private final int[] times;
    private int position;
    private boolean decoded;
    private BarcodeFormat format;

    BenchmarkItem(String path, int runs) {
        if (runs <= 0) {
            throw new IllegalArgumentException();
        }
        this.path = path;
        times = new int[runs];
        position = 0;
        decoded = false;
        format = null;
    }

    void addResult(int microseconds) {
        times[position] = microseconds;
        position++;
    }

    void setDecoded(boolean decoded) {
        this.decoded = decoded;
    }

    void setFormat(BarcodeFormat format) {
        this.format = format;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(30);
        result.append(decoded ? "DECODED " + format + ": " : "FAILED: ");
        result.append(path);
        result.append(" (");
        result.append(getAverageTime());
        result.append(" us average)");
        return result.toString();
    }

    /**
     * Calculates the average time but throws out the maximum as an outlier first.
     *
     * @return The average decoding time in microseconds.
     */
    int getAverageTime() {
        int size = times.length;
        int total = 0;
        int max = times[0];
        for (int x = 0; x < size; x++) {
            int time = times[x];
            total += time;
            if (time > max) {
                max = time;
            }
        }
        total -= max;
        size--;
        return size > 0 ? total / size : 0;
    }

}