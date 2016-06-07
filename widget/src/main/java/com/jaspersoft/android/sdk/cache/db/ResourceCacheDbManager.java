package com.jaspersoft.android.sdk.cache.db;

import android.content.Context;

import com.jaspersoft.android.sdk.network.AuthorizedClient;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
public class ResourceCacheDbManager {

    private final AccountsTable accountsTable;
    private final ResourceCacheTable resourceCacheTable;

    ResourceCacheDbManager(AccountsTable accountsTable, ResourceCacheTable resourceCacheTable) {
        this.accountsTable = accountsTable;
        this.resourceCacheTable = resourceCacheTable;
    }

    public String getCachedFile(AuthorizedClient client, String resourceUri, int page, int filtersHash) {
        long accountId = accountsTable.getAccountId(client);
        if (accountId == -1) return null;

        return resourceCacheTable.getCachedFileName(accountId, resourceUri, page, filtersHash);
    }

    public boolean addResourceCache(AuthorizedClient client, String resourceUri, int page, int filtersHash, String filepath) {
        long accountId = accountsTable.getAccountId(client);
        if (accountId == -1) {
            accountId = accountsTable.addAccount(client);
        }

        return resourceCacheTable.getCachedFileName(accountId, resourceUri, page, filtersHash) == null
                || resourceCacheTable.addResourceCache(accountId, resourceUri, page, filtersHash, filepath);
    }

    public boolean deleteResourceCache(AuthorizedClient client) {
        return accountsTable.removeAccount(client);
    }

    public boolean deleteResourceCache(@NotNull AuthorizedClient client, @NotNull String resourceUri, @NotNull Integer filtersHash, @Nullable Integer page) {
        long accountId = accountsTable.getAccountId(client);
        return accountId != -1 && resourceCacheTable.deleteResourceCache(accountId, resourceUri, filtersHash, page) != 0;
    }

    public static class Builder {

        private final Context context;

        public Builder(Context context) {
            this.context = context;
        }

        public ResourceCacheDbManager build() {
            AccountsTable accountsTable = new AccountsTable(new AccountsDbHelper(context));
            ResourceCacheTable resourceCacheTable = new ResourceCacheTable(new ResourceCacheDbHelper(context));
            return new ResourceCacheDbManager(accountsTable, resourceCacheTable);
        }
    }
}
