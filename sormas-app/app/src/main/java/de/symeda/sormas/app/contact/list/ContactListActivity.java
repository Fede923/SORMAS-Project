/*
 * SORMAS® - Surveillance Outbreak Response Management & Analysis System
 * Copyright © 2016-2018 Helmholtz-Zentrum für Infektionsforschung GmbH (HZI)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.symeda.sormas.app.contact.list;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.widget.AdapterView;

import org.joda.time.DateTime;

import java.util.List;
import java.util.Random;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import de.symeda.sormas.api.contact.FollowUpStatus;
import de.symeda.sormas.api.user.UserRight;
import de.symeda.sormas.app.BaseListActivity;
import de.symeda.sormas.app.PagedBaseListActivity;
import de.symeda.sormas.app.PagedBaseListFragment;
import de.symeda.sormas.app.R;
import de.symeda.sormas.app.backend.config.ConfigProvider;
import de.symeda.sormas.app.component.menu.PageMenuItem;
import de.symeda.sormas.app.contact.edit.ContactNewActivity;
import de.symeda.sormas.app.util.Callback;

public class ContactListActivity extends PagedBaseListActivity {

    private static FollowUpStatus[] statusFilters = new FollowUpStatus[]{null, FollowUpStatus.FOLLOW_UP, FollowUpStatus.COMPLETED,
            FollowUpStatus.CANCELED, FollowUpStatus.LOST, FollowUpStatus.NO_FOLLOW_UP};
    private ContactListViewModel model;

    public static void startActivity(Context context, FollowUpStatus listFilter) {
        BaseListActivity.startActivity(context, ContactListActivity.class, buildBundle(getStatusFilterPosition(statusFilters, listFilter)));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showPreloader();
        adapter = new ContactListAdapter(FollowUpStatus.FOLLOW_UP);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                // Scroll to the topmost position after cases have been inserted
                if (positionStart == 0) {
                    RecyclerView recyclerView = findViewById(R.id.recyclerViewForList);
                    if (recyclerView != null) {
                        recyclerView.scrollToPosition(0);
                    }
                }
            }
            @Override
            public void onItemRangeMoved(int positionStart, int toPosition, int itemCount) {
                RecyclerView recyclerView = findViewById(R.id.recyclerViewForList);
                if (recyclerView != null) {
                    recyclerView.scrollToPosition(0);
                }
            }
        });
        model = ViewModelProviders.of(this).get(ContactListViewModel.class);
        model.initializeViewModel();
        model.getContacts().observe(this, contacts -> {
            adapter.submitList(contacts);
            hidePreloader();
        });
        setOpenPageCallback(p -> {
            showPreloader();
            ((ContactListAdapter) adapter).setCurrentListFilter(statusFilters[((PageMenuItem) p).getPosition()]);
            model.getContactCriteria().followUpStatus(statusFilters[((PageMenuItem) p).getPosition()]);
            model.notifyCriteriaUpdated();
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        getIntent().putExtra("refreshOnResume", true);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (getIntent().getBooleanExtra("refreshOnResume", false)) {
            showPreloader();
            model.getContacts().getValue().getDataSource().invalidate();
        }
    }

    @Override
    public List<PageMenuItem> getPageMenuData() {
        return PageMenuItem.fromEnum(statusFilters, getContext());
    }

    @Override
    protected Callback getSynchronizeResultCallback() {
        // Reload the list after a synchronization has been done
        return () -> {
            showPreloader();
            model.getContacts().getValue().getDataSource().invalidate();
        };
    }

    @Override
    public int onNotificationCountChangingAsync(AdapterView parent, PageMenuItem menuItem, int position) {
        //TODO: Call database and retrieve notification count
        return (int) (new Random(DateTime.now().getMillis() * 1000).nextInt() / 10000000);
    }

    @Override
    protected PagedBaseListFragment buildListFragment(PageMenuItem menuItem) {
        if (menuItem != null) {
            FollowUpStatus listFilter = statusFilters[menuItem.getPosition()];
            return ContactListFragment.newInstance(listFilter);
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getNewMenu().setTitle(R.string.action_new_contact);
        return true;
    }

    @Override
    public void goToNewView() {
        ContactNewActivity.startActivity(getContext(), null);
        finish();
    }

    @Override
    public boolean isEntryCreateAllowed() {
        return ConfigProvider.hasUserRight(UserRight.CONTACT_CREATE);
    }

    @Override
    protected int getActivityTitle() {
        return R.string.heading_contacts_list;
    }

    @Override
    public void addFiltersToPageMenu() {
        // Not supported yet
    }

}
