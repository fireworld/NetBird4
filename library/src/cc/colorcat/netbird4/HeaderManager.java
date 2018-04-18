package cc.colorcat.netbird4;

/**
 * Created by cxx on 18-4-18.
 * xx.ch@outlook.com
 */
public interface HeaderManager {
    HeaderManager EMPTY = new HeaderManager() {
        @Override
        public void saveFromResponse(String url, Headers headers) {
        }

        @Override
        public Headers loadForRequest(String url) {
            return Headers.EMPTY;
        }
    };

    void saveFromResponse(String url, Headers headers);

    Headers loadForRequest(String url);
}
