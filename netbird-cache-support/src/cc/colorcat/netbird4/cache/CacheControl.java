package cc.colorcat.netbird4.cache;

import cc.colorcat.netbird4.Headers;

/**
 * Created by cxx on 17-10-30.
 * xx.ch@outlook.com
 */
public final class CacheControl {
    public static final String HEADER_NAME_MAX_AGE = "NetBird-Max-Age";
    public static final long MAX_AGE_NO_CACHE = 0L;
    public static final long MAX_AGE_FOREVER = -1L;

    public static final String HEADER_NAME_CACHE_DATE = "NetBird-Cache-Date";
    public static final long TIME_INVALIDATE = -1L;

    public static long parseMaxAge(Headers headers) {
        String maxAgeHeader = headers.value(HEADER_NAME_MAX_AGE);
        long maxAge = MAX_AGE_NO_CACHE;
        if (maxAgeHeader != null && !maxAgeHeader.isEmpty()) {
            maxAge = Long.parseLong(maxAgeHeader);
        }
        return maxAge;
    }

    public static long parseSaveDate(Headers headers) {
        String saveDateHeader = headers.value(HEADER_NAME_CACHE_DATE);
        long saveDate = TIME_INVALIDATE;
        if (saveDateHeader != null && !saveDateHeader.isEmpty()) {
            saveDate = Long.parseLong(saveDateHeader);
        }
        return saveDate;
    }

    private CacheControl() {
        throw new AssertionError("no instance");
    }
}
