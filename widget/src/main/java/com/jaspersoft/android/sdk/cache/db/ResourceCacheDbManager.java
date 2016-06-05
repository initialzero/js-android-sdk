package com.jaspersoft.android.sdk.cache.db;

import android.content.Context;

import com.jaspersoft.android.sdk.network.AuthorizedClient;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
public class ResourceCacheDbManager {

    private final AccountsDatabase accountsDatabase;
    private final ResourceCacheDatabase resourceCacheDatabase;

    ResourceCacheDbManager(AccountsDatabase accountsDatabase, ResourceCacheDatabase resourceCacheDatabase) {
        this.accountsDatabase = accountsDatabase;
        this.resourceCacheDatabase = resourceCacheDatabase;
    }

    public String getCachedFile(AuthorizedClient client, String resourceUri, int page, int filtersHash) {
        long accountId = accountsDatabase.getAccountId(client);
        if (accountId == -1) return null;

        return resourceCacheDatabase.getCachedFileName(accountId, resourceUri, page, filtersHash);
    }

    public boolean addResourceCache(AuthorizedClient client, String resourceUri, int page, int filtersHash, String filepath) {
        long accountId = accountsDatabase.getAccountId(client);
        if (accountId == -1) {
            accountId = accountsDatabase.addAccount(client);
        }

        return resourceCacheDatabase.getCachedFileName(accountId, resourceUri, page, filtersHash) == null
                || resourceCacheDatabase.addResourceCache(accountId, resourceUri, page, filtersHash, filepath);
    }

    public static class Builder {

        private final Context context;

        public Builder(Context context) {
            this.context = context;
        }

        public ResourceCacheDbManager build() {
            AccountsDatabase accountsDatabase = new AccountsDatabase(new AccountsDbHelper(context));
            ResourceCacheDatabase resourceCacheDatabase = new ResourceCacheDatabase(new ResourceCacheDbHelper(context));
            return new ResourceCacheDbManager(accountsDatabase, resourceCacheDatabase);
        }
    }
}
