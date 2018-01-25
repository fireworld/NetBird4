package cc.colorcat.netbird4;

/**
 * Created by cxx on 18-1-25.
 * xx.ch@outlook.com
 */
interface ProgressListener {

    void onChanged(long finished, long total, int percent);
}
